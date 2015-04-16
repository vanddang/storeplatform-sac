/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import com.google.common.base.Strings;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.UserDownloadInfoRes;
import com.skplanet.storeplatform.sac.display.common.DisplayCryptUtils;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.localsci.sci.vo.GetUserDownloadInfoParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * UserDownloadInfoServiceImpl
 * </p>
 * Updated on : 2015. 03. 16 Updated by : 정희원, SK 플래닛.
 */
@Service
public class UserDownloadInfoServiceImpl implements UserDownloadInfoService {

    public static final String TENANT_TSTORE = "S01";
    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Override
    public UserDownloadInfoRes getUserDownloadInfo(GetUserDownloadInfoParam param) {
        Map<String, Object> req = new HashMap<String, Object>();
        if (Strings.isNullOrEmpty(param.getMdn()) || Strings.isNullOrEmpty(param.getPartProdId()) || Strings.isNullOrEmpty(param.getTenantId()))
            throw new IllegalArgumentException("mdn, prodId, tenantId는 필수값입니다.");

        // 자상품ID로 모상품ID를 조회한다
        String aid = getAidByPartProdId(param.getPartProdId());
        if(aid == null)
            throw new StorePlatformException("SAC_DSP_0005", param.getPartProdId());

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

//        if(!tenantS01 && !tenantParam)
//            return null;
//        else if(!tenantS01)
//            return new UserDownloadInfoRes(v.getLatestTenantId());
//        else {
//            if(Strings.isNullOrEmpty(v.getLatestTenantId())) {
//                return new UserDownloadInfoRes(param.getTenantId());
//            }
//            else {
//                Set<String> tenantSet = new HashSet<String>(Arrays.asList("S01", param.getTenantId()));
//                if(tenantSet.contains(v.getLatestTenantId()))
//                    return new UserDownloadInfoRes(v.getLatestTenantId());
//                else
//                    return new UserDownloadInfoRes(param.getTenantId());
//            }
//        }

        // == 0: NULL 반환
        if(!tenantS01 && !tenantParam) {
            return null;
        }
        // == 2: 최근 다운로드 TenantID(이하 LAST-DN-TID) 조회
        else if (tenantS01 && tenantParam) {
            // LAST-DN-TID == NULL: IAB-TID 반환
            if(Strings.isNullOrEmpty(v.getLatestTenantId())) {
                return new UserDownloadInfoRes(param.getTenantId());
            } else {
                Set<String> tenantSet = new HashSet<String>(Arrays.asList(TENANT_TSTORE, param.getTenantId()));
                if(tenantSet.contains(v.getLatestTenantId())) {
                    // LAST-DN-TID in [IAP-TID, 'S01'] : LAST-DN-TID 반환
                    return new UserDownloadInfoRes(v.getLatestTenantId());
                } else {
                    //LAST-DN-TID not in [IAP-TID, 'S01']: IAP-TID 반환
                    return new UserDownloadInfoRes(param.getTenantId());
                }
            }

        }
        // == 1
        else {
            //매핑된 상품의 Tenant ID 반환
            if (tenantS01)
                return new UserDownloadInfoRes(TENANT_TSTORE);
            else
                return new UserDownloadInfoRes(param.getTenantId());
        }
    }

    private String getAidByPartProdId(String partProdId) {
        Map<String, Object> req = new HashMap<String, Object>();

        req.put("prodRshpCd", DisplayConstants.DP_PARENT_CHILD_RELATIONSHIP_CD);
        req.put("partProdId", partProdId);

        return commonDAO.queryForObject("UserDownloadInfo.getAidByPartProdId", req, String.class);
    }

    public static class RawUserDownloadInfo {
        private String tenant1Yn;
        private String tenant2Yn;
        private String latestTenantId;

        public String getTenant1Yn() {
            return tenant1Yn;
        }

        public void setTenant1Yn(String tenant1Yn) {
            this.tenant1Yn = tenant1Yn;
        }

        public String getTenant2Yn() {
            return tenant2Yn;
        }

        public void setTenant2Yn(String tenant2Yn) {
            this.tenant2Yn = tenant2Yn;
        }

        public String getLatestTenantId() {
            return latestTenantId;
        }

        public void setLatestTenantId(String latestTenantId) {
            this.latestTenantId = latestTenantId;
        }
    }
}
