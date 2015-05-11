/*
] * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.freepass.service;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.VoucherListReq;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.VoucherListRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Coupon;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.service.MemberBenefitService;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * Voucher Service (CoreStoreBusiness)
 * 
 * Updated on : 2015. 4. 30. Updated by : 이태균, IS PLUS.
 */
@Service
public class VoucherServiceImpl implements VoucherService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	@Autowired
	HistoryInternalSCI historyInternalSCI;

	@Autowired
	private DisplayCommonService displayCommonService;

	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

	@Autowired
	private MemberBenefitService benefitService;

	@Autowired
	private CommonMetaInfoGenerator metaInfoGenerator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.freepass.service.VoucherService
	 * #searchVoucherList(com.skplanet.storeplatform .sac.client.display.vo.freepass.VoucherListReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public VoucherListRes searchVoucherList(VoucherListReq req, SacRequestHeader header) {
		// TODO Auto-generated method stub

		// 공통 응답 변수 선언
		VoucherListRes responseVO = null;
		CommonResponse commonResponse = new CommonResponse();

		Coupon coupon = null;
		List<Coupon> couponList = new ArrayList<Coupon>();

		Map<String, Object> reqMap = new HashMap<String, Object>();
		List<ProductBasicInfo> productBasicInfoList = null;
		MetaInfo retMetaInfo = null;

		// 정액제 상품 목록 조회
		req.setTenantId(header.getTenantHeader().getTenantId());
		req.setLangCd(header.getTenantHeader().getLangCd());
		req.setDeviceModelCd(header.getDeviceHeader().getModel());
		req.setBannerImageCd(DisplayConstants.DP_FREEPASS_BANNER_IMAGE_CD);
		req.setThumbnailImageCd(DisplayConstants.DP_FREEPASS_THUMBNAIL_IMAGE_CD);
		req.setProdStatusCd(DisplayConstants.DP_PASS_SALE_STAT_ING);
		req.setStandardModelCd(DisplayConstants.DP_ANY_PHONE_4MM);

		// 시작점 ROW Default 세팅
		if (req.getOffset() == 0) {
			req.setOffset(1);
		}
		// 페이지당 노출될 ROW 개수 Default 세팅
		if (req.getCount() == 0) {
			req.setCount(20);
		}

		// 페이지당 노출될 ROW 개수 Default 세팅
		if ("All".equals(req.getKind())) {
			req.setKind("");
		}

		// '+'로 연결 된 상품등급코드를 배열로 전달
		if (StringUtils.isNotEmpty(req.getTopMenuId())) {
			try {
				// String[] arrTopMenuId = URLDecoder.decode(req.getTopMenuId(),
				// "UTF-8").split("//+");
				String[] arrTopMenuId = StringUtils.split(req.getTopMenuId(), "+");
				req.setArrTopMenuId(arrTopMenuId);
			} catch (Exception e) {
				throw new StorePlatformException("SAC_DSP_0003", "topMenuId", req.getTopMenuId());
			}
		}

		// '+'로 연결 된 이용등급코드를 배열로 전달
		if (StringUtils.isNotEmpty(req.getCmpxProdGradeCd())) {
			try {
				String[] arrayCmpxProdGradeCd = URLDecoder.decode(req.getCmpxProdGradeCd(), "UTF-8").split("//+");
				for (int i = 0; i < arrayCmpxProdGradeCd.length; i++) {
					if (StringUtils.isNotEmpty(arrayCmpxProdGradeCd[i])) {
						if (!"PD004401".equals(arrayCmpxProdGradeCd[i]) && !"PD004402".equals(arrayCmpxProdGradeCd[i])
								&& !"PD004403".equals(arrayCmpxProdGradeCd[i])
								&& !"PD004404".equals(arrayCmpxProdGradeCd[i])) {
							this.log.debug("----------------------------------------------------------------");
							this.log.debug("유효하지않은 이용권 이용등급 코드 : " + arrayCmpxProdGradeCd[i]);
							this.log.debug("----------------------------------------------------------------");
							throw new StorePlatformException("SAC_DSP_0003", (i + 1) + " 번째 cmpxProdGradeCd",
									arrayCmpxProdGradeCd[i]);
						}
					}
				}
				req.setArrayCmpxProdGradeCd(arrayCmpxProdGradeCd);
			} catch (Exception e) {
				throw new StorePlatformException("SAC_DSP_0003", "cmpxProdGradeCd", req.getTopMenuId());
			}
		}

		productBasicInfoList = this.commonDAO.queryForList("Voucher.selectVoucherList", req, ProductBasicInfo.class);

		if (productBasicInfoList == null)
			throw new StorePlatformException("SAC_DSP_0009");

		// 정액제 상품 메타 조회
		if (productBasicInfoList != null && productBasicInfoList.size() > 0) {
			reqMap.put("tenantHeader", header.getTenantHeader());
			reqMap.put("deviceHeader", header.getDeviceHeader());
			reqMap.put("bannerImageCd", DisplayConstants.DP_FREEPASS_BANNER_IMAGE_CD);
			reqMap.put("thumbnailImageCd", DisplayConstants.DP_FREEPASS_THUMBNAIL_IMAGE_CD);
			reqMap.put("ebookThumbnailImageCd", DisplayConstants.DP_FREEPASS_EBOOK_THUMBNAIL_IMAGE_CD);
			for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
				reqMap.put("productBasicInfo", productBasicInfo);
				retMetaInfo = this.metaInfoService.getVoucherMetaInfo(reqMap);
				coupon = this.responseInfoGenerateFacade.generateVoucherProduct(retMetaInfo);
				couponList.add(coupon);
				commonResponse.setTotalCount(productBasicInfo.getTotalCount());
			}
		}

		responseVO = new VoucherListRes();
		responseVO.setCommonResponse(commonResponse);
		responseVO.setCouponList(couponList);
		return responseVO;
	}

}
