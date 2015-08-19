/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.category.service;

import com.google.common.base.Strings;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryShoppingSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryShoppingSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.service.MemberBenefitService;
import com.skplanet.storeplatform.sac.display.common.vo.SupportDevice;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CategorySpecificShopping Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 12. 07. Updated by : 김형식, SK 플래닛.
 */
@Service
public class CategorySpecificShoppingServiceImpl implements CategorySpecificShoppingService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	@Autowired
	private DisplayCommonService displayCommonService;
	
//    @Autowired
//    private MemberBenefitService memberBenefitService;
	/**
	 * 
	 * <pre>
	 * 카테고리 쇼핑 상세 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @param header
	 *            공통헤더
	 * @return  카테고리  쇼핑 상세 조회
	 */
	@Override
	public CategoryShoppingSacRes searchSpecificShoppingDetail(SacRequestHeader header, CategoryShoppingSacReq req) {

		// 공통 응답 변수 선언
		TenantHeader tenantHeader = header.getTenantHeader();

        if (!Strings.isNullOrEmpty(req.getSaleDtUseYn()) &&
                !"Y".equals(req.getSaleDtUseYn()) && !"N".equals(req.getSaleDtUseYn())) {
            throw new StorePlatformException("SAC_DSP_0003", "saleDtUseYn", req.getSaleDtUseYn());
        }

        if (!Strings.isNullOrEmpty(req.getIncludeProdStopStatus()) &&
                !"Y".equals(req.getIncludeProdStopStatus()) && !"N".equals(req.getIncludeProdStopStatus())) {
            throw new StorePlatformException("SAC_DSP_0003", "includeProdStopStatus", req.getIncludeProdStopStatus());
        }

		// DB 조회 파라미터 생성
		Map<String, Object> reqMap = new HashMap<String, Object>();

		reqMap.put("tenantId", tenantHeader.getTenantId());
		reqMap.put("langCd", tenantHeader.getLangCd());
        reqMap.put("catalogId", req.getProductId());
        reqMap.put("specialProdId", Strings.emptyToNull(req.getSpecialProdId()));

        reqMap.put("filterApplyDt", StringUtils.defaultString(req.getSaleDtUseYn(), "N").equals("N"));
        reqMap.put("sellingOnly", StringUtils.defaultString(req.getIncludeProdStopStatus(), "Y").equals("N"));

		reqMap.put("imageCd", DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
		reqMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);

		if (!this.commonSupportDeviceShopping(header))
            return makeEmptyResponse();

		// ID list 조회
		List<MetaInfo> retMetaInfoList =  this.commonDAO.queryForList("Shopping.searchSpecificShoppingDetail", reqMap, MetaInfo.class);
		MetaInfo retMetaInfo = null;
		int selectInt = 0;

		for(int kk = 0 ;kk < retMetaInfoList.size(); kk++){
			if(retMetaInfoList.get(kk).getProdStatusCd().equals(DisplayConstants.DP_SALE_STAT_ING)){ // 판매중인것이 우선순위
				selectInt = kk;
				break;
			}
		}
		if(retMetaInfoList.size()>0){
			retMetaInfo= retMetaInfoList.get(selectInt);
		}
        if (retMetaInfo == null)
            return makeEmptyResponse();

        CategoryShoppingSacRes res = new CategoryShoppingSacRes();

        // TODO topMenuId -> menuId 작업 안함
//        retMetaInfo.setMileageInfo(memberBenefitService.getMileageInfo(tenantHeader.getTenantId(), retMetaInfo.getTopMenuId(), retMetaInfo.getProdId(), retMetaInfo.getProdAmt()));

        // 쇼핑 Response Generate
        Product product = this.responseInfoGenerateFacade.generateSpecificShoppingProduct(retMetaInfo);
        if (StringUtils.isNotEmpty(req.getSpecialProdId())) {
            product.setSpecialCouponId(retMetaInfo.getSpecialCouponId());
            product.setSpecialProdYn(retMetaInfo.getSpecialSale());
            product.setSpecialTypeCd(retMetaInfo.getSpecialTypeCd());
        }
        res.setProduct(product);
        res.setCommonResponse(new CommonResponse(1));

		return res;
	}

    private CategoryShoppingSacRes makeEmptyResponse() {
        CategoryShoppingSacRes res = new CategoryShoppingSacRes();
        res.setProduct(new Product());
        res.setCommonResponse(new CommonResponse(0));
        return res;
    }

    /**
	 * 쇼핑 지원 여부 .
	 * 
	 * @param header
	 *            header
	 * @return boolean
	 */
	private boolean commonSupportDeviceShopping(SacRequestHeader header) {

        String deviceModelCd = header.getDeviceHeader().getModel();
        if(StringUtils.isEmpty(deviceModelCd)){
			throw new StorePlatformException("SAC_DSP_0029");
		}

		// 단말 지원정보 조회
		SupportDevice supportDevice = this.displayCommonService.getSupportDeviceInfo(deviceModelCd);
		if(supportDevice == null){
			throw new StorePlatformException("SAC_DSP_0012", deviceModelCd);
		}

        return "Y".equals(supportDevice.getSclShpgSprtYn());
	}
}
