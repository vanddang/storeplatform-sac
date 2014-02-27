package com.skplanet.storeplatform.sac.display.localsci.sci.service;

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
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ProductInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ProductInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ProductInfoSacRes;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;

/**
 * 
 * 
 * 구매 내역 조회 시 필요한 상품 메타 정보 조회 서비스 구현체.
 * 
 * Updated on : 2014. 2. 24. Updated by : 오승민, 인크로스
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.localsci.sci.service.ProductInfoService#getProductList(com.skplanet.
	 * storeplatform.sac.client.internal.display.localsci.vo.ProductInfoSacReq)
	 */
	@Override
	public ProductInfoSacRes getProductList(ProductInfoSacReq req) {
		ProductInfoSacRes res = new ProductInfoSacRes();
		List<ProductInfo> productList = new ArrayList<ProductInfo>();
		List<String> prodIdList = req.getList();

		// 상품 기본 정보 List 조회
		List<ProductBasicInfo> productBasicInfoList = this.commonDAO.queryForList("ProductInfo.selectProductInfoList",
				prodIdList, ProductBasicInfo.class);

		this.log.debug("##### parameter cnt : {}", prodIdList.size());
		this.log.debug("##### selected product basic info cnt : {}", productBasicInfoList.size());

		if (productBasicInfoList != null) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("lang", "ko");
			paramMap.put("deviceModelNo", req.getDeviceModelNo());

			for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
				String topMenuId = productBasicInfo.getTopMenuId();
				String svcGrpCd = productBasicInfo.getSvcGrpCd();
				paramMap.put("productBasicInfo", productBasicInfo);

				this.log.debug("##### Top Menu Id : {}", topMenuId);
				this.log.debug("##### Service Group Cd : {}", svcGrpCd);

				// 상품 SVC_GRP_CD 조회
				// DP000203 : 멀티미디어
				// DP000206 : Tstore 쇼핑
				// DP000205 : 소셜쇼핑
				// DP000204 : 폰꾸미기
				// DP000201 : 애플리캐이션

				// APP 상품의 경우
				if (DisplayConstants.DP_APP_PROD_SVC_GRP_CD.equals(svcGrpCd)) {
					paramMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
					this.log.debug("##### Search for app product");
					ProductInfo product = this.commonDAO.queryForObject("ProductInfo.getAppMetaInfo", paramMap,
							ProductInfo.class);
					if (product != null) {
						productList.add(product);
					}
				} else if (DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD.equals(svcGrpCd)) { // 멀티미디어 타입일 경우
					// 영화/방송 상품의 경우
					paramMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
					if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)
							|| DisplayConstants.DP_TV_TOP_MENU_ID.equals(topMenuId)) {
						this.log.debug("##### Search for Vod specific product");
						ProductInfo product = this.commonDAO.queryForObject("ProductInfo.getVODMetaInfo", paramMap,
								ProductInfo.class);
						if (product != null) {
							if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)) {
								// TODO osm1021 특별한 처리가 필요할 경우 처리 필요
							} else {
								// TODO osm1021 특별한 처리가 필요할 경우 처리 필요
							}
							productList.add(product);
						}
					} else if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)
							|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(topMenuId)) { // Ebook / Comic 상품의 경우

						paramMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
						this.log.debug("##### Search for EbookComic specific product");
						ProductInfo product = this.commonDAO.queryForObject("ProductInfo.getEbookComicMetaInfo",
								paramMap, ProductInfo.class);
						if (product != null) {
							if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)) {
								// TODO osm1021 특별한 처리가 필요할 경우 처리 필요
							} else {
								// TODO osm1021 특별한 처리가 필요할 경우 처리 필요
							}
							productList.add(product);
						}
					}
				} else if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(topMenuId)) { // 음원 상품의 경우
					// 배치완료 기준일시 조회
					// TODO 기준 ListID가 없기 때문에 일단 멜론 Top 100으로 고정
					// String stdDt = this.displayCommonService.getBatchStandardDateString(tenantId,
					// "MELON_DP004901");
					// paramMap.put("stdDt", stdDt);
					productBasicInfo.setMenuId("DP004901");
					paramMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);

					// TODO osm1021 dummy data 꼭 삭제할것
					paramMap.put("stdDt", "20110806");

					this.log.debug("##### Search for Music specific product");
					ProductInfo product = this.commonDAO.queryForObject("ProductInfo.getMusicMetaInfo", paramMap,
							ProductInfo.class);
					if (product != null) {
						productList.add(product);
					}
				} else if (DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD.equals(svcGrpCd)) { // 쇼핑 상품의 경우
					paramMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
					paramMap.put("imageCd", DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);

					this.log.debug("##### Search for Shopping specific product");
					ProductInfo product = this.commonDAO.queryForObject("ProductInfo.getShoppingMetaInfo", paramMap,
							ProductInfo.class);
					if (product != null) {
						productList.add(product);
					}
				}
			}
		}
		res.setProductList(productList);
		return res;
	}
}
