/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.related.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.related.ArtistProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.ArtistProductSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.MusicInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * ArtistProductService 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 03. 03. Updated by : 유시혁.
 */
@Service
public class ArtistProductServiceImpl implements ArtistProductService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private MusicInfoGenerator musicGenerator;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	/**
	 * 
	 * <pre>
	 * 특정 아티스트별 상품(곡) 조회.
	 * </pre>
	 * 
	 * @param requestVO
	 *            ArtistProductSacReq
	 * @param requestHeader
	 *            SacRequestHeader
	 * @return ArtistProductSacRes
	 */
	@Override
	public ArtistProductSacRes searchArtistProductList(ArtistProductSacReq requestVO, SacRequestHeader requestHeader) {

		// 헤더 값 세팅
		this.log.debug("헤더 값 세팅");
		requestVO.setTenantId(requestHeader.getTenantHeader().getTenantId());
		requestVO.setLangCd(requestHeader.getTenantHeader().getLangCd());
		requestVO.setDeviceModelCd(requestHeader.getDeviceHeader().getModel());
		requestVO.setMmDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);

		// 요청 값 세팅
		this.log.debug("요청 값 세팅");
		requestVO.setOffset(requestVO.getOffset() != null ? requestVO.getOffset() : 1);
		requestVO.setCount(requestVO.getCount() != null ? requestVO.getCount() : 20);
		requestVO.setOrderedBy(requestVO.getOrderedBy() != null ? requestVO.getOrderedBy() : "issuedDate");
		requestVO.setImageCd(DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);

		ArtistProductSacRes artistProductSacRes = new ArtistProductSacRes();
		CommonResponse commonResponse = new CommonResponse();
		Map<String, Object> reqMap = new HashMap<String, Object>();
		// MetaInfo retMetaInfo = null;
		Product product = null;

		// 특정 아티스트 정보 조회
		this.log.debug("특정 아티스트 정보 조회");
		MetaInfo artistMetaInfo = this.commonDAO.queryForObject("ArtistProduct.selectArtistInfo", requestVO,
				MetaInfo.class);

		if (artistMetaInfo != null) {
			artistProductSacRes.setContributor(this.musicGenerator.generateArtistDetailContributor(artistMetaInfo));

			// 특정 작가별 상품 조회
			this.log.debug("특정 아티스트별 상품(곡) 조회");
			List<MetaInfo> artistProductList = this.commonDAO.queryForList("ArtistProduct.selectArtistProductList",
					requestVO, MetaInfo.class);
			List<Product> productList = new ArrayList<Product>();

			if (!artistProductList.isEmpty()) {
				for (MetaInfo retMetaInfo : artistProductList) {
					product = this.responseInfoGenerateFacade.generateMusicProduct(retMetaInfo);
					productList.add(product);
				}
				commonResponse.setTotalCount(artistProductList.get(0).getTotalCount());
				artistProductSacRes.setProductList(productList);
			} else {
				commonResponse.setTotalCount(0);
			}
		} else {
			commonResponse.setTotalCount(0);
		}
		this.log.debug("특정 아티스트별 상품(곡) 결과 : " + commonResponse.getTotalCount() + "건");
		artistProductSacRes.setCommonResponse(commonResponse);
		return artistProductSacRes;
	}

}
