/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.best.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.best.BestAppSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.best.BestAppSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants.DP_APP_REPRESENT_IMAGE_CD;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 19. Updated by : 이석희, SK 플래닛.
 */
@Service
public class BestAppServiceImpl implements BestAppService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService commonService;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

//	@Autowired
//    private MemberBenefitService benefitService;
	
	/**
	 * 
	 * <pre>
	 * BEST 앱 리스트 조회.
	 * </pre>
	 * 
	 * @param requestheader
	 *            공통헤더
	 * @param bestAppReq
	 *            파라미터
	 * @return BEST 앱 리스트
	 */
	@Override
	public BestAppSacRes searchBestAppList(SacRequestHeader requestheader, BestAppSacReq bestAppReq) {
		final TenantHeader tenantHeader = requestheader.getTenantHeader();
		final DeviceHeader deviceHeader = requestheader.getDeviceHeader();

		this.log.debug("########################################################");
		this.log.debug("tenantHeader.getTenantId()	:	" + tenantHeader.getTenantId());
		this.log.debug("tenantHeader.getLangCd()	:	" + tenantHeader.getLangCd());
		this.log.debug("deviceHeader.getModel()		:	" + deviceHeader.getModel());
		this.log.debug("########################################################");

		bestAppReq.setTenantId(tenantHeader.getTenantId());
		bestAppReq.setLangCd(tenantHeader.getLangCd());
		bestAppReq.setDeviceModelCd(deviceHeader.getModel());

		BestAppSacRes response = new BestAppSacRes();
		CommonResponse commonResponse = new CommonResponse();

		// 파라미터 유효값 체크
		if (StringUtils.isNotEmpty(bestAppReq.getProdGradeCd())) {
			String[] arrayProdGradeCd = bestAppReq.getProdGradeCd().split("\\+");
			for (int i = 0; i < arrayProdGradeCd.length; i++) {
				if (StringUtils.isNotEmpty(arrayProdGradeCd[i])) {
					if (!"PD004401".equals(arrayProdGradeCd[i]) && !"PD004402".equals(arrayProdGradeCd[i])
							&& !"PD004403".equals(arrayProdGradeCd[i])) {
						this.log.debug("----------------------------------------------------------------");
						this.log.debug("유효하지않은 상품 등급 코드 : " + arrayProdGradeCd[i]);
						this.log.debug("----------------------------------------------------------------");

						throw new StorePlatformException("SAC_DSP_0003", (i + 1) + " 번째 prodGradeCd",
								arrayProdGradeCd[i]);
					}
				}
			}
		}

		int offset = 1; // default
		int count = 20; // default

		if (bestAppReq.getOffset() != null) {
			offset = bestAppReq.getOffset();
		}
		bestAppReq.setOffset(offset); // set offset

		if (bestAppReq.getCount() != null) {
			count = bestAppReq.getCount();
		}
		count = offset + count - 1;
		bestAppReq.setCount(count); // set count

		String stdDt = this.commonService.getBatchStandardDateString(tenantHeader.getTenantId(), bestAppReq.getListId());
		bestAppReq.setStdDt(stdDt); // 2014.01.28 이석희 수정 S01 하드코딩에서 헤더에서 get 한 TenantId

		// '+'로 연결 된 상품등급코드를 배열로 전달
		if (StringUtils.isNotEmpty(bestAppReq.getProdGradeCd())) {
			String[] arrayProdGradeCd = bestAppReq.getProdGradeCd().split("\\+");
			bestAppReq.setArrayProdGradeCd(arrayProdGradeCd);
		}

		// BEST 앱 상품 조회
		List<ProductBasicInfo> appList;

        if (!"ADM000000001".equals(bestAppReq.getListId())) {
            // 추천, 인기(매출), 인기신규 상품 조회
            appList = this.commonDAO.queryForList("BestApp.selectBestAppList", bestAppReq, ProductBasicInfo.class);
        } else {
            // 신규 상품조회
            appList = this.commonDAO.queryForList("BestApp.selectNewBestAppList", bestAppReq, ProductBasicInfo.class);
        }

        response.setCommonResponse(commonResponse);

        if(appList.isEmpty()) {
            commonResponse.setTotalCount(0);
            response.setProductList(new ArrayList<Product>());
            return response;
        }

        List<Product> productList = Lists.newArrayList();
        Map<String, Object> req = Maps.newHashMap();
        req.put("imageCd", DP_APP_REPRESENT_IMAGE_CD);
        req.put("tenantHeader", tenantHeader);
        req.put("deviceHeader", deviceHeader);

        for (ProductBasicInfo prodBasicInfo : appList) {
            req.put("productBasicInfo", prodBasicInfo);
            MetaInfo meta = metaInfoService.getAppMetaInfo( prodBasicInfo );

            if(meta == null)
                continue;

            productList.add(responseInfoGenerateFacade.generateAppProduct(meta));
        }

        commonResponse.setTotalCount(appList.get(0).getTotalCount());
        response.setProductList(productList);

		return response;
	}
}
