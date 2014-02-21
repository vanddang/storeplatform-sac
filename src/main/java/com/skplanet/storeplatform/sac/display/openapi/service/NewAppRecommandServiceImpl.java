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
import com.skplanet.storeplatform.sac.client.display.vo.openapi.NewAppRecommandSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.NewAppRecommandSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.sci.SellerSearchSCI;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacRes;
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
public class NewAppRecommandServiceImpl implements NewAppRecommandService {

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
	public NewAppRecommandSacRes searchNewAppRecommandList(SacRequestHeader requestheader,
			NewAppRecommandSacReq newAppRecommandSacReq) {
		TenantHeader tenantHeader = requestheader.getTenantHeader();

		newAppRecommandSacReq.setTenantId(tenantHeader.getTenantId());
		newAppRecommandSacReq.setLangCd(tenantHeader.getLangCd());
		newAppRecommandSacReq.setImageCd(DisplayConstants.DP_OPENAPI_APP_REPRESENT_IMAGE_CD);
		newAppRecommandSacReq.setPreviewImagecd(DisplayConstants.DP_OPENAPI_APP_PREVIEW_IMAGE_CD);

		NewAppRecommandSacRes response = new NewAppRecommandSacRes();
		CommonResponse commonResponse = new CommonResponse();
		String sellerKey = newAppRecommandSacReq.getSellerKey();

		int index = 0;
		int offset = 1; // default
		int count = 20; // default

		if (newAppRecommandSacReq.getOffset() != null) {
			offset = newAppRecommandSacReq.getOffset();
		}
		newAppRecommandSacReq.setOffset(offset); // set offset

		if (newAppRecommandSacReq.getCount() != null) {
			count = newAppRecommandSacReq.getCount();
		}
		count = offset + count - 1;
		newAppRecommandSacReq.setCount(count); // set count

		Identifier identifier = new Identifier();
		List<Product> productList = new ArrayList<Product>();

		Product product = null;

		String releaseType = newAppRecommandSacReq.getReleaseType();
		// String sellerKey = newAppRecommandSacReq.getSellerKey();

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(releaseType)) {
			throw new StorePlatformException("SAC_DSP_0002", "releaseType", releaseType);
		}

		// 필수 파라미터 체크
		// if (StringUtils.isEmpty(sellerKey)) {
		// throw new StorePlatformException("SAC_DSP_0002", "sellerKey", sellerKey);
		// }

		List<MetaInfo> downloadBestList = null;
		// OpenApi 다운로드 Best 상품 조회

		if (DisplayConstants.DP_OPENAPI_RELEASETYPE_WEEK.equals(releaseType)) {
			downloadBestList = this.commonDAO.queryForList("OpenApi.searchNewAppList", newAppRecommandSacReq,
					MetaInfo.class);
		} else {
			downloadBestList = this.commonDAO.queryForList("OpenApi.searchDayNewAppList", newAppRecommandSacReq,
					MetaInfo.class);
		}

		if (downloadBestList.size() != 0) {

			Iterator<MetaInfo> iterator = downloadBestList.iterator();
			List<Identifier> identifierList = null;
			String[] arrayProdudctId = new String[downloadBestList.size()];
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
						metaInfo.getImagePath(), metaInfo.getImageSize()));
				/*
				 * 신규 앱 출시 (신규 앱 출시 1일이내 조회시 정보 노출)
				 */
				if (!DisplayConstants.DP_OPENAPI_RELEASETYPE_WEEK.equals(releaseType)) {
					sourceList.add(this.commonGenerator.generateSource(DisplayConstants.DP_SOURCE_TYPE_PREVIEW,
							metaInfo.getPreviewImagePath(), metaInfo.getPreviewImageSize()));
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
				try {
					DetailInformationSacReq detailInformationSacReq = new DetailInformationSacReq();
					DetailInformationSacRes detailInformationSacRes = new DetailInformationSacRes();
					detailInformationSacReq.setSellerKey(metaInfo.getSellerMbrNo());
					detailInformationSacRes = this.sellerSearchSCI.detailInformation(detailInformationSacReq);
					company = detailInformationSacRes.getSellerMbr().get(0).getSellerCompany();

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
				newAppRecommandSacReq.setProdId(metaInfo.getProdId());
				newAppRecommandSacReq.setArrayProdudctId(arrayProdudctId);

				/*
				 * 미리보기 정보 조회 (신규 앱 출시 1일이내 조회시 정보 노출)
				 */
				if (!DisplayConstants.DP_OPENAPI_RELEASETYPE_WEEK.equals(releaseType)) {
					List<MetaInfo> previewList = new ArrayList<MetaInfo>();
					previewList = this.commonDAO.queryForList("OpenApi.getPreviewImageList", newAppRecommandSacReq,
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
