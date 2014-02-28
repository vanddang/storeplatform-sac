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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.NewAppRecommendSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.NewAppRecommendSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.sci.SellerSearchSCI;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
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
public class NewAppRecommendServiceImpl implements NewAppRecommendService {

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
	public NewAppRecommendSacRes searchNewAppRecommendList(SacRequestHeader requestheader,
			NewAppRecommendSacReq newAppRecommendSacReq) {
		TenantHeader tenantHeader = requestheader.getTenantHeader();

		newAppRecommendSacReq.setTenantId(tenantHeader.getTenantId());
		newAppRecommendSacReq.setLangCd(tenantHeader.getLangCd());
		newAppRecommendSacReq.setImageCd(DisplayConstants.DP_OPENAPI_APP_REPRESENT_IMAGE_CD);
		newAppRecommendSacReq.setPreviewImagecd(DisplayConstants.DP_OPENAPI_APP_PREVIEW_IMAGE_CD);

		NewAppRecommendSacRes response = new NewAppRecommendSacRes();
		CommonResponse commonResponse = new CommonResponse();

		int index = 0;
		int offset = 1; // default
		int count = 20; // default

		if (newAppRecommendSacReq.getOffset() != null) {
			offset = newAppRecommendSacReq.getOffset();
		}
		newAppRecommendSacReq.setOffset(offset); // set offset

		if (newAppRecommendSacReq.getCount() != null) {
			count = newAppRecommendSacReq.getCount();
		}
		count = offset + count - 1;
		newAppRecommendSacReq.setCount(count); // set count

		Identifier identifier = new Identifier();
		List<Product> productList = new ArrayList<Product>();

		Product product = null;

		String releaseType = newAppRecommendSacReq.getReleaseType();

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(releaseType)) {
			throw new StorePlatformException("SAC_DSP_0002", "releaseType", releaseType);
		}

		List<MetaInfo> newAppRecommend = null;
		// OpenApi 신규 앱 추천 상품 조회

		if (DisplayConstants.DP_OPENAPI_RELEASETYPE_WEEK.equals(releaseType)) {
			newAppRecommend = this.commonDAO.queryForList("OpenApi.searchNewAppList", newAppRecommendSacReq,
					MetaInfo.class);
		} else {
			newAppRecommend = this.commonDAO.queryForList("OpenApi.searchDayNewAppList", newAppRecommendSacReq,
					MetaInfo.class);
		}

		if (newAppRecommend.size() != 0) {

			Iterator<MetaInfo> iterator = newAppRecommend.iterator();
			List<Identifier> identifierList = null;
			String[] arrayProdudctId = new String[newAppRecommend.size()];
			while (iterator.hasNext()) {
				String company = null;

				MetaInfo metaInfo = iterator.next();

				product = new Product();
				identifierList = new ArrayList<Identifier>();

				identifier = this.commonGenerator.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD,
						metaInfo.getProdId());
				identifierList.add(identifier);

				product.setIdentifierList(identifierList); // 상품 Id

				product.setMenuList(this.commonGenerator.generateMenuList(metaInfo)); // 상품 메뉴정보

				List<Source> sourceList = new ArrayList<Source>();
				sourceList.add(this.commonGenerator.generateSource(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL,
						metaInfo.getImagePath()));
				/*
				 * 신규 앱 출시 (신규 앱 출시 1일이내 조회시 정보 노출)
				 */
				if (!DisplayConstants.DP_OPENAPI_RELEASETYPE_WEEK.equals(releaseType)) {
					sourceList.add(this.commonGenerator.generateSource(DisplayConstants.DP_SOURCE_TYPE_PREVIEW,
							metaInfo.getPreviewImagePath()));
					product.setProductDetailExplain(metaInfo.getProdDtlDesc()); // 상품 상세 설명

				}
				product.setSourceList(sourceList); // 상품 이미지 정보

				product.setPrice(this.commonGenerator.generatePrice(metaInfo)); // 상품가격

				product.setApp(this.appInfoGenerator.generateApp(metaInfo)); // App 상세정보

				product.setTitle(this.commonGenerator.generateTitle(metaInfo)); // 상품명

				product.setProductExplain(metaInfo.getProdBaseDesc()); // 상품 설명

				/*
				 * 판매자 정보중 회사명 Seller Key는 Null로 Set해서 공개하지 않는다.
				 */
				DetailInformationSacReq detailInformationSacReq = new DetailInformationSacReq();
				DetailInformationSacRes detailInformationSacRes = new DetailInformationSacRes();
				List<SellerMbrSac> sellerMbrSacList = new ArrayList<SellerMbrSac>();
				SellerMbrSac sellerMbrSac = new SellerMbrSac();
				sellerMbrSac.setSellerKey(metaInfo.getSellerMbrNo());
				sellerMbrSacList.add(sellerMbrSac);
				detailInformationSacReq.setSellerMbrSacList(sellerMbrSacList);

				try {
					detailInformationSacRes = this.sellerSearchSCI.detailInformation(detailInformationSacReq);
					Iterator<String> it = detailInformationSacRes.getSellerMbrListMap().keySet().iterator();
					List<SellerMbrSac> sellerMbrs = new ArrayList<SellerMbrSac>();
					sellerMbrSac = new SellerMbrSac();
					while (it.hasNext()) {
						String key = it.next();
						sellerMbrs = detailInformationSacRes.getSellerMbrListMap().get(key);
						company = sellerMbrs.get(0).getSellerCompany();
					}

				} catch (Exception e) {
					company = "";
				}

				metaInfo.setSellerMbrNo(null);
				metaInfo.setCompany(company);
				product.setDistributor(this.commonGenerator.generateDistributor(metaInfo));

				if (StringUtils.isNotEmpty(metaInfo.getProdId())) {
					arrayProdudctId[index] = metaInfo.getProdId();
				}
				index++;
				newAppRecommendSacReq.setProdId(metaInfo.getProdId());
				newAppRecommendSacReq.setArrayProdudctId(arrayProdudctId);

				/*
				 * 미리보기 정보 조회 (신규 앱 출시 1일이내 조회시 정보 노출)
				 */
				if (!DisplayConstants.DP_OPENAPI_RELEASETYPE_WEEK.equals(releaseType)) {
					List<MetaInfo> previewList = new ArrayList<MetaInfo>();
					previewList = this.commonDAO.queryForList("OpenApi.getPreviewImageList", newAppRecommendSacReq,
							MetaInfo.class);

					Iterator<MetaInfo> previewIterator = previewList.iterator();
					List<Source> preveiwSourceList = new ArrayList<Source>();
					Source source = new Source();
					while (previewIterator.hasNext()) {
						MetaInfo previewMetaInfo = previewIterator.next();
						source = this.commonGenerator.generatePreviewSourceList(previewMetaInfo);
						source.setImageCode(previewMetaInfo.getImageCode());
						preveiwSourceList.add(source);
					}
					product.setPreviewSourceList(preveiwSourceList);
				}

				productList.add(product);
				commonResponse.setTotalCount(metaInfo.getTotalCount());
			}
		}
		response.setCommonResponse(commonResponse);
		response.setProductList(productList);
		return response;
	}
}
