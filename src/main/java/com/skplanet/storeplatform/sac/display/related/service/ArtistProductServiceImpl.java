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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.related.AlbumProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.ArtistProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.ArtistProductSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.MemberBenefitService;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.MusicInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ArtistProductService 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 03. 03. Updated by : 유시혁.
 */
@Service
public class ArtistProductServiceImpl implements ArtistProductService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private final String VARIOUS_ARTISTS_ID = "2727"; //알수없는 아티스트 ID

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private MusicInfoGenerator musicGenerator;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

	@Autowired
    private MemberBenefitService memberBenefitService;
	
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

		ArtistProductSacRes artistProductSacRes = new ArtistProductSacRes();
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setTotalCount(0);
		artistProductSacRes.setCommonResponse(commonResponse);
		
		// 특정 아티스트 정보 조회
		this.log.debug("특정 아티스트 정보 조회");
		Map<String, Object> reqMapForArtist = getRequestMapForArtist(requestVO, requestHeader);
		MetaInfo artistMetaInfo = this.commonDAO.queryForObject("ArtistProduct.selectArtistInfo", reqMapForArtist,
				MetaInfo.class);
		
		if (artistMetaInfo == null) {
			this.log.debug("해당 아티스트 존재하지 않음. artistId: " + requestVO.getArtistId());
			return artistProductSacRes;
		}
		
		artistProductSacRes.setContributor(this.musicGenerator.generateArtistDetailContributor(artistMetaInfo));
		
		// 알수없는 아티스트의 경우 관련 곡을 추출하지 않음.
		//  알수없는 아티스트의 경우 관련 곡이 의미가 없음.
		//  관련곡의 갯수가 너무 많아서 쿼리 속도가 느림.
		if (VARIOUS_ARTISTS_ID.equals(requestVO.getArtistId())) {
			return artistProductSacRes;
		}

		// 특정 작가별 상품 조회
		this.log.debug("특정 아티스트별 상품(곡) 조회");
		Map<String, Object> reqMapForMusicList = getRequestMapForMusicList(requestVO, requestHeader);
		List<ProductBasicInfo> artistProductList = this.commonDAO.queryForList(
				"ArtistProduct.selectArtistProductList", reqMapForMusicList, ProductBasicInfo.class);
		List<Product> productList = new ArrayList<Product>();

		if (!artistProductList.isEmpty()) {
			Map<String, Object> reqMap = new HashMap<String, Object>();
			reqMap.put("tenantHeader", requestHeader.getTenantHeader());
			reqMap.put("deviceHeader", requestHeader.getDeviceHeader());
			reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);

			for (ProductBasicInfo productBasicInfo : artistProductList) {
				reqMap.put("productBasicInfo", productBasicInfo);
				reqMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
				MetaInfo retMetaInfo = null;
				retMetaInfo = this.commonDAO.queryForObject("RelatedProduct.selectMusicMetaInfo", reqMap,
						MetaInfo.class); // 뮤직 메타
				if (retMetaInfo != null) {
					// Tstore멤버십 적립율 정보
					retMetaInfo.setMileageInfo(memberBenefitService.getMileageInfo(requestHeader.getTenantHeader().getTenantId(), retMetaInfo.getTopMenuId(), retMetaInfo.getProdId(), retMetaInfo.getProdAmt()));
					
					Product product = null;
					product = this.responseInfoGenerateFacade.generateMusicProduct(retMetaInfo);
					product.setAccrual(this.commonGenerator.generateAccrual(retMetaInfo)); // 통계 건수 재정의
					product.setProductExplain(retMetaInfo.getProdBaseDesc()); // 상품 설명
					productList.add(product);
				}
			}
			commonResponse.setTotalCount(artistProductList.get(0).getTotalCount());
			artistProductSacRes.setProductList(productList);
		}
		this.log.debug("특정 아티스트별 상품(곡) 결과 : " + commonResponse.getTotalCount() + "건");
		return artistProductSacRes;
	}
	
	private Map<String, Object> getRequestMapForArtist(ArtistProductSacReq requestVO, SacRequestHeader requestHeader) {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("artistId", requestVO.getArtistId());
		return reqMap;
	}
	
	private Map<String, Object> getRequestMapForMusicList(ArtistProductSacReq requestVO, SacRequestHeader requestHeader) {
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("tenantId", requestHeader.getTenantHeader().getTenantId());
		reqMap.put("langCd", requestHeader.getTenantHeader().getLangCd());
		reqMap.put("deviceModelCd", requestHeader.getDeviceHeader().getModel());
		reqMap.put("mmDeviceModelCd", DisplayConstants.DP_ANY_PHONE_4MM);
		reqMap.put("orderedBy", requestVO.getOrderedBy() == null ? "issueDate" : requestVO.getOrderedBy());
		reqMap.put("artistId", requestVO.getArtistId());
		reqMap.put("prodGradeCds", parseProdGradeCd(requestVO.getProdGradeCd()));
		reqMap.put("offset", requestVO.getOffset() == null ? 1 : requestVO.getOffset());
		reqMap.put("count", requestVO.getCount() == null ? 20 : requestVO.getCount());
		return reqMap;
	}
	
	private String[] parseProdGradeCd(String prodGradeCd) {
		if (prodGradeCd == null) {
			return null;
		}
		String[] prodGradeCds = prodGradeCd.split("\\+");
		validateProdGradeCd(prodGradeCds);
		return prodGradeCds;
	}
	
	private void validateProdGradeCd(String[] prodGradeCds) {
		for (int i = 0; i < prodGradeCds.length; i++) {
			if (!"PD004401".equals(prodGradeCds[i]) && !"PD004402".equals(prodGradeCds[i])
					&& !"PD004403".equals(prodGradeCds[i]) && !"PD004404".equals(prodGradeCds[i])) {
				log.debug("----------------------------------------------------------------");
				log.debug("유효하지않은 상품 등급 코드 : " + prodGradeCds[i]);
				log.debug("----------------------------------------------------------------");

				throw new StorePlatformException("SAC_DSP_0003", (i + 1) + " 번째 prodGradeCd",
						prodGradeCds[i]);
			}
		}
	}

}
