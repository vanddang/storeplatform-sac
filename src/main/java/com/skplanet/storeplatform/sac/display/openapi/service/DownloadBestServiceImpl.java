/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.openapi.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.DownloadBestSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.DownloadBestSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.AppInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 1. 21. Updated by : 이석희, 인크로스.
 */
@Service
public class DownloadBestServiceImpl implements DownloadBestService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	@Autowired
	private DisplayCommonService commonService;
	@Autowired
	private CommonMetaInfoGenerator commonGenerator;
	@Autowired
	private AppInfoGenerator appInfoGenerator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.DownloadBestService#DownloadBestService(com.skplanet
	 * .storeplatform.sac.client.product.vo.downloadBestSacReqVO)
	 */
	@Override
	public DownloadBestSacRes searchDownloadBestList(SacRequestHeader requestheader,
			DownloadBestSacReq downloadBestSacReq) {
		TenantHeader tenantHeader = requestheader.getTenantHeader();
		DeviceHeader deviceHeader = requestheader.getDeviceHeader();

		downloadBestSacReq.setTenantId(tenantHeader.getTenantId());
		downloadBestSacReq.setLangCd(tenantHeader.getLangCd());
		downloadBestSacReq.setImageCd(DisplayConstants.DP_OPENAPI_APP_REPRESENT_IMAGE_CD);

		DownloadBestSacRes response = new DownloadBestSacRes();
		CommonResponse commonResponse = new CommonResponse();
		int totalCount = 0;

		List<Product> productList = new ArrayList<Product>();

		Product product = null;

		String stdDt = this.commonService.getBatchStandardDateString(tenantHeader.getTenantId(),
				downloadBestSacReq.getListId());
		downloadBestSacReq.setStdDt(stdDt); // 2014.01.28 이석희 수정 S01 하드코딩에서 헤더에서 get 한 TenantId

		String listId = downloadBestSacReq.getListId();
		String inquiryType = downloadBestSacReq.getInquiryType();
		String inquiryValue = downloadBestSacReq.getInquiryValue();
		String prodCharge = downloadBestSacReq.getProdCharge();

		if (downloadBestSacReq.getDummy() == null) {
			// dummy 호출이 아닐때
			// 필수 파라미터 체크
			if (StringUtils.isEmpty(listId)) {
				throw new StorePlatformException("SAC_DSP_0002", "listId", listId);
			}

			// 필수 파라미터 체크
			if (StringUtils.isEmpty(inquiryType)) {
				throw new StorePlatformException("SAC_DSP_0002", "inquiryType", inquiryType);
			}

			// 필수 파라미터 체크
			if (StringUtils.isEmpty(inquiryValue)) {
				throw new StorePlatformException("SAC_DSP_0002", "inquiryValue", inquiryValue);
			}

			// 필수 파라미터 체크
			if (StringUtils.isEmpty(prodCharge)) {
				throw new StorePlatformException("SAC_DSP_0002", "prodCharge", prodCharge);
			}

			List<MetaInfo> downloadBestList = null;
			// OpenApi 다운로드 Best 상품 조회
			downloadBestList = this.commonDAO.queryForList("OpenApi.searchDownloadBestList", downloadBestSacReq,
					MetaInfo.class);

			if (downloadBestList.size() != 0) {

				Iterator<MetaInfo> iterator = downloadBestList.iterator();
				List<Identifier> identifierList = new ArrayList<Identifier>();
				while (iterator.hasNext()) {

					MetaInfo metaInfo = iterator.next();

					product = new Product();

					Identifier identifier = this.commonGenerator.generateIdentifier(
							DisplayConstants.DP_EPISODE_IDENTIFIER_CD, metaInfo.getProdId());
					identifierList.add(identifier);
					product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo)); // 상품 ID

					/*
					 * 상품 이미지
					 */
					product.setSourceList(this.commonGenerator.generateSourceList(
							DisplayCommonUtil.getMimeType(metaInfo.getImagePath()),
							DisplayConstants.DP_SOURCE_TYPE_ORIGINAL, metaInfo.getImagePath(), metaInfo.getImageSize()));

					product.setPrice(this.commonGenerator.generatePrice(metaInfo)); // 상품가격
					product.setAccrual(this.commonGenerator.generateAccrual(metaInfo)); // 참여자 정보
					product.setApp(this.appInfoGenerator.generateApp(metaInfo)); // App 상세정보
					product.setTitle(this.commonGenerator.generateTitle(metaInfo)); // 상품명
					product.setProductExplain(metaInfo.getProdBaseDesc()); // 상품 설명
					product.setDistributor(this.commonGenerator.generateDistributor(metaInfo));

					productList.add(product);

				}

			}

		} else {

		}
		response.setCommonResponse(commonResponse);
		response.setProductList(productList);
		return response;
	}
}
