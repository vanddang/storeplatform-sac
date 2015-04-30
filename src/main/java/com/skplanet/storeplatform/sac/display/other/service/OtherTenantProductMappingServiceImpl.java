/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.other.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherTenantProductMappingRes;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherTenantProductMappingRes.TenantProductMapping;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherUserTenantReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherUserTenantRes;
import com.skplanet.storeplatform.sac.common.util.SacBeanUtils;
import com.skplanet.storeplatform.sac.display.common.DisplayCryptUtils;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.localsci.sci.service.UserDownloadInfoServiceImpl.RawUserDownloadInfo;

/**
 * <p>
 * OtherTenantProductMappingServiceImpl
 * </p>
 * Updated on : 2015. 01. 19 Updated by : 정희원, SK 플래닛.
 */
@Service
public class OtherTenantProductMappingServiceImpl implements OtherTenantProductMappingService {

	public static final String TENANT_TSTORE = "S01";
	
    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Override
    public OtherTenantProductMappingRes getTenantProductMapping(String prodId) {

        SapProductMapping mapg = commonDAO.queryForObject("OtherTenantProductMapping.getTenantProductMapping", prodId, SapProductMapping.class);
        if(mapg == null)
            throw new StorePlatformException("SAC_DSP_0005", prodId);

        OtherTenantProductMappingRes res = new OtherTenantProductMappingRes();
        res.setTenantProductMappingList(new ArrayList<TenantProductMapping>());

        List<TenantSalesStatus> tenantSalesStatusList = commonDAO.queryForList("OtherTenantProductMapping.getTenantSalesStatus", prodId, TenantSalesStatus.class);
        for (TenantSalesStatus tenantSalesStatus : tenantSalesStatusList) {
            String tProdId = SacBeanUtils.getProperty(mapg, "prodId" + tenantSalesStatus.getTenantId());
            if(tProdId == null)
                continue;

            res.getTenantProductMappingList().add(new TenantProductMapping(tenantSalesStatus.getTenantId(), tenantSalesStatus.getProdStatusCd(), tProdId));
        }

        return res;
    }

    
    
    /**
     * getTenantProductMapping VO
     */
    public static class SapProductMapping {
        private String prodIdS01;
        private String prodIdS02;
        private String prodIdS03;

        public String getProdIdS01() {
            return prodIdS01;
        }

        public void setProdIdS01(String prodIdS01) {
            this.prodIdS01 = prodIdS01;
        }

        public String getProdIdS02() {
            return prodIdS02;
        }

        public void setProdIdS02(String prodIdS02) {
            this.prodIdS02 = prodIdS02;
        }

        public String getProdIdS03() {
            return prodIdS03;
        }

        public void setProdIdS03(String prodIdS03) {
            this.prodIdS03 = prodIdS03;
        }
    }

    static class TenantSalesStatus {
        private String tenantId;
        private String prodStatusCd;

        public String getTenantId() {
            return tenantId;
        }

        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }

        public String getProdStatusCd() {
            return prodStatusCd;
        }

        public void setProdStatusCd(String prodStatusCd) {
            this.prodStatusCd = prodStatusCd;
        }
    }

	/* (non-Javadoc)
	 * @see com.skplanet.storeplatform.sac.display.other.service.OtherTenantProductMappingService#getUserTenant(com.skplanet.storeplatform.sac.client.display.vo.other.OtherUserTenantReq)
	 */
	@Override
	public OtherUserTenantRes getUserTenant(OtherUserTenantReq param) {

		//com.skplanet.storeplatform.sac.display.localsci.sci.service.UserDownloadInfoServiceImpl.getUserDownloadInfo 기능과 유사.
		//중복 코드이지만 Package 가 달라 별도 개발함.
		//차이점 : getUserDownloadInfo - 자상품ID (partProdId)로 AID 를 찾고,
		//				 getUserTenant - prodId 로 AID 를 찾는다.
		
        Map<String, Object> req = new HashMap<String, Object>();
        if (Strings.isNullOrEmpty(param.getMdn()) || Strings.isNullOrEmpty(param.getProdId()) || Strings.isNullOrEmpty(param.getTenantId()))
            throw new IllegalArgumentException("mdn, prodId, tenantId는 필수값입니다.");

        // ProdId로 AID를 조회한다
        String aid = getAidByProdId(param.getProdId());
        if(aid == null)
            throw new StorePlatformException("SAC_DSP_0005", param.getProdId());

        String key = DisplayCryptUtils.hashMdnAidKey(param.getMdn(), aid);
        if(Strings.isNullOrEmpty(key))
            throw new IllegalStateException();

        req.put("tenant1", TENANT_TSTORE);
        req.put("tenant2", param.getTenantId());
        req.put("mdnaidKey", key);
        req.put("aid", aid);

        RawUserDownloadInfo v = commonDAO.queryForObject("UserDownloadInfo.getRawUserDownloadInfo", req, RawUserDownloadInfo.class);

        /*
        ref #37318
        == 0: NULL 반환
        == 1: 매핑된 상품의 Tenant ID 반환
        == 2: 최근 다운로드 TenantID(이하 LAST-DN-TID) 조회
                LAST-DN-TID == NULL: IAB-TID 반환
                LAST-DN-TID in [IAP-TID, 'S01'] : LAST-DN-TID 반환
                LAST-DN-TID not in [IAP-TID, 'S01']: IAP-TID 반환
         */
        if(v == null)
            return null;

        boolean tenantS01 = "Y".equals(v.getTenant1Yn()),
                tenantParam = "Y".equals(v.getTenant2Yn());

        // == 0: NULL 반환
        if(!tenantS01 && !tenantParam) {
            return null;
        }
        // == 2: 최근 다운로드 TenantID(이하 LAST-DN-TID) 조회
        else if (tenantS01 && tenantParam) {
            // LAST-DN-TID == NULL: IAB-TID 반환
            if(Strings.isNullOrEmpty(v.getLatestTenantId())) {
                return new OtherUserTenantRes(param.getTenantId());
            } else {
                Set<String> tenantSet = new HashSet<String>(Arrays.asList(TENANT_TSTORE, param.getTenantId()));
                if(tenantSet.contains(v.getLatestTenantId())) {
                    // LAST-DN-TID in [IAP-TID, 'S01'] : LAST-DN-TID 반환
                    return new OtherUserTenantRes(v.getLatestTenantId());
                } else {
                    //LAST-DN-TID not in [IAP-TID, 'S01']: IAP-TID 반환
                    return new OtherUserTenantRes(param.getTenantId());
                }
            }

        }
        // == 1
        else {
            //매핑된 상품의 Tenant ID 반환
            if (tenantS01)
                return new OtherUserTenantRes(TENANT_TSTORE);
            else
                return new OtherUserTenantRes(param.getTenantId());
        }
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * @param prodId
	 * @return
	 */
	private String getAidByProdId(String prodId) {

        Map<String, Object> req = new HashMap<String, Object>();

        req.put("prodRshpCd", DisplayConstants.DP_PARENT_CHILD_RELATIONSHIP_CD);
        req.put("prodId", prodId);

        return commonDAO.queryForObject("UserDownloadInfo.getAidByProdId", req, String.class);
	}
}
