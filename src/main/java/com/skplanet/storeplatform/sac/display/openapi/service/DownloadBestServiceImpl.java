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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.DownloadBestSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.DownloadBestSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.sci.SellerSearchSCI;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
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
	@Autowired
	private SellerSearchSCI sellerSearchSCI;

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
		// DeviceHeader deviceHeader = requestheader.getDeviceHeader();

		downloadBestSacReq.setTenantId(tenantHeader.getTenantId());
		downloadBestSacReq.setLangCd(tenantHeader.getLangCd());
		downloadBestSacReq.setImageCd(DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);

		DownloadBestSacRes response = new DownloadBestSacRes();
		CommonResponse commonResponse = new CommonResponse();

		List<Product> productList = new ArrayList<Product>();

		Product product = null;

		String stdDt = this.commonService.getBatchStandardDateString(tenantHeader.getTenantId(),
				downloadBestSacReq.getListId());
		downloadBestSacReq.setStdDt(stdDt);

		int offset = 1; // default
		int count = 20; // default

		if (downloadBestSacReq.getOffset() != null) {
			offset = downloadBestSacReq.getOffset();
		}
		downloadBestSacReq.setOffset(offset); // set offset

		if (downloadBestSacReq.getCount() != null) {
			count = downloadBestSacReq.getCount();
		}
		count = offset + count - 1;
		downloadBestSacReq.setCount(count); // set count

		String inquiryType = downloadBestSacReq.getInquiryType();
		String inquiryValue = downloadBestSacReq.getInquiryValue();

		if ("2".equals(inquiryType)) {
			// 사업자 등록번호로 Selley Key 조회
			String[] arraySellerKey = null;
			DetailInformationSacReq detailInformationSacReq = new DetailInformationSacReq();
			List<SellerMbrSac> sellerMbrSacList = new ArrayList<SellerMbrSac>();
			SellerMbrSac sellerMbrSac = new SellerMbrSac();
			sellerMbrSac.setSellerBizNumber(inquiryValue);
			sellerMbrSacList.add(sellerMbrSac);
			detailInformationSacReq.setSellerMbrSacList(sellerMbrSacList);
			this.log.info("##### [SAC DSP LocalSCI] SAC Member Start : sellerSearchSCI.detailInformation");
			long start = System.currentTimeMillis();
			try {

				DetailInformationSacRes detailInformationSacRes = this.sellerSearchSCI
						.detailInformation(detailInformationSacReq);

				Iterator<String> it = detailInformationSacRes.getSellerMbrListMap().keySet().iterator();
				// sellerMbrSac = new SellerMbrSac();
				while (it.hasNext()) {
					String key = it.next();
					List<SellerMbrSac> sellerMbrs = detailInformationSacRes.getSellerMbrListMap().get(key);
					arraySellerKey = new String[sellerMbrs.size()];
					for (int i = 0; i < sellerMbrs.size(); i++) {
						arraySellerKey[i] = sellerMbrs.get(i).getSellerKey();
					}
				}
				// 조회된 Seller Key setting
				downloadBestSacReq.setArraySellerKey(arraySellerKey);
			} catch (Exception e) {
				downloadBestSacReq.setArraySellerKey(arraySellerKey);
			}
			this.log.info("##### [SAC DSP LocalSCI] SAC Member End : sellerSearchSCI.detailInformation");
			long end = System.currentTimeMillis();
			this.log.info("##### [SAC DSP LocalSCI] SAC Member sellerSearchSCI.detailInformation takes {} ms",
					(end - start));
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
						DisplayConstants.DP_SOURCE_TYPE_ORIGINAL, metaInfo.getImagePath()));

				product.setPrice(this.commonGenerator.generatePrice(metaInfo)); // 상품가격
				product.setAccrual(this.commonGenerator.generateAccrual(metaInfo)); // 참여자 정보
				product.setApp(this.appInfoGenerator.generateApp(metaInfo)); // App 상세정보
				product.setTitle(this.commonGenerator.generateTitle(metaInfo)); // 상품명
				product.setProductExplain(metaInfo.getProdBaseDesc()); // 상품 설명
				product.setDistributor(this.commonGenerator.generateDistributor(metaInfo));

				productList.add(product);

				commonResponse.setTotalCount(metaInfo.getTotalCount());
			}
		}

		response.setCommonResponse(commonResponse);
		response.setProductList(productList);
		return response;
	}
}
