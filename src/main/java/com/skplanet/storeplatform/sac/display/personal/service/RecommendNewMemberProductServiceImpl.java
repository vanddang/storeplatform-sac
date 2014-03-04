/**
 * 
 */
package com.skplanet.storeplatform.sac.display.personal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.personal.RecommendNewMemberProductReq;
import com.skplanet.storeplatform.sac.client.display.vo.personal.RecommendNewMemberProductRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * 신규 가입 고객 추천 상품 조회.
 * 
 * Updated on : 2014. 2. 24. Updated by : 이석희, 아이에스 플러스.
 */
@Service
public class RecommendNewMemberProductServiceImpl implements RecommendNewMemberProductService {
	// private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	@Autowired
	private DisplayCommonService commonService;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.personal.service.PersonalAutoUpgradeService#searchAutoUpgradeList(com.
	 * skplanet.storeplatform.sac.client.display.vo.personal.PersonalAutoUpgradeReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public RecommendNewMemberProductRes recommendNewMemberProductList(SacRequestHeader header,
			RecommendNewMemberProductReq req) {

		CommonResponse commonResponse = new CommonResponse();
		RecommendNewMemberProductRes res = new RecommendNewMemberProductRes();
		List<Product> productList = new ArrayList<Product>();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		TenantHeader tenantHeader = header.getTenantHeader();

		req.setTenantId(tenantHeader.getTenantId());
		req.setLangCd(tenantHeader.getLangCd());
		req.setDeviceModelCd(deviceHeader.getModel());
		req.setAnyDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);

		int offset = 1; // default
		int count = 10; // default

		if (req.getOffset() != null) {
			offset = req.getOffset();
		}
		req.setOffset(offset); // set offset

		if (req.getCount() != null) {
			count = req.getCount();
		}
		count = offset + count - 1;
		req.setCount(count); // set count

		// 기준일시 조회
		String stdDt = this.commonService.getBatchStandardDateString(tenantHeader.getTenantId(), req.getListId());
		req.setStdDt(stdDt);

		List<MetaInfo> productIdList = new ArrayList<MetaInfo>();
		productIdList = this.commonDAO.queryForList("RecommendNewMemberProduct.searchRecommendNewMemberProductList",
				req, MetaInfo.class);

		if (productIdList != null) {
			commonResponse.setTotalCount(productIdList.get(0).getTotalCount());
		}
		Iterator<MetaInfo> iterator = productIdList.iterator();
		// List<Source> preveiwSourceList = new ArrayList<Source>();
		String topMenuId = "";
		String prodId = "";
		while (iterator.hasNext()) {
			MetaInfo productBaseInfo = iterator.next();
			topMenuId = productBaseInfo.getTopMenuId();
			prodId = productBaseInfo.getProdId();
			Map<String, Object> reqMap = new HashMap<String, Object>();
			reqMap.put("tenantHeader", tenantHeader);
			reqMap.put("deviceHeader", deviceHeader);
			reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);

			ProductBasicInfo productBasicInfo = new ProductBasicInfo();
			// APP 게임, FUN , 생활/위치 , 어학/교육
			if (DisplayConstants.DP_GAME_TOP_MENU_ID.equals(topMenuId)
					|| DisplayConstants.DP_FUN_TOP_MENU_ID.equals(topMenuId)
					|| DisplayConstants.DP_LIFE_LIVING_TOP_MENU_ID.equals(topMenuId)
					|| DisplayConstants.DP_LANG_EDU_TOP_MENU_ID.equals(topMenuId)) {

				productBasicInfo.setSvcGrpCd(DisplayConstants.DP_APP_PROD_SVC_GRP_CD);
				productBasicInfo.setProdId(productBaseInfo.getProdId());
				reqMap.put("productBasicInfo", productBasicInfo);
				reqMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
				MetaInfo retMetaInfo = this.metaInfoService.getAppMetaInfo(reqMap);

				if (retMetaInfo != null) {
					Product product = this.responseInfoGenerateFacade.generateAppProduct(retMetaInfo);
					productList.add(product);
				}

			} else if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)
					|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(topMenuId)) { // 이북/코믹

				// 채널 ID로 상품 조회
				productBasicInfo.setProdId(productBaseInfo.getChnlProdId());
				productBasicInfo.setContentsTypeCd(DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
				reqMap.put("productBasicInfo", productBasicInfo);

				reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
				MetaInfo retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap);

				if (retMetaInfo != null) {
					if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(retMetaInfo.getTopMenuId())) { // 이북일때
						Product product = this.responseInfoGenerateFacade.generateEbookProduct(retMetaInfo);
						productList.add(product);
					} else if (DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(retMetaInfo.getTopMenuId())) { // 코믹일때
						Product product = this.responseInfoGenerateFacade.generateComicProduct(retMetaInfo);
						productList.add(product);
					}
				}

			} else if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(topMenuId)) { // 뮤직

				// 음원 상품의 경우
				if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(topMenuId)) {
					// 배치완료 기준일시 조회
					reqMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
					// reqMap.put("stdDt", req.getStdDt().substring(0, 8)); //배치 완료 기준일시 현재 데이터 미일치로 아래 하드코딩
					reqMap.put("stdDt", "20131007");

					productBasicInfo.setProdId(productBaseInfo.getChnlProdId());
					productBasicInfo.setContentsTypeCd(DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
					productBasicInfo.setTopMenuId(topMenuId);
					reqMap.put("productBasicInfo", productBasicInfo);

					MetaInfo retMetaInfo = this.metaInfoService.getMusicMetaInfo(reqMap);
					if (retMetaInfo != null) {
						Product product = this.responseInfoGenerateFacade.generateMusicProduct(retMetaInfo);
						productList.add(product);
					}
				}

			} else if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)
					|| DisplayConstants.DP_TV_TOP_MENU_ID.equals(topMenuId)) { // 영화/방송

				// 채널 ID로 상품 조회
				productBasicInfo.setProdId(productBaseInfo.getChnlProdId());
				productBasicInfo.setContentsTypeCd(DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
				reqMap.put("productBasicInfo", productBasicInfo);
				reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
				MetaInfo retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap);

				if (retMetaInfo != null) {
					if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(retMetaInfo.getTopMenuId())) { // 영화일때
						Product product = this.responseInfoGenerateFacade.generateMovieProduct(retMetaInfo);
						productList.add(product);
					} else if (DisplayConstants.DP_TV_TOP_MENU_ID.equals(retMetaInfo.getTopMenuId())) { // 방송일때
						Product product = this.responseInfoGenerateFacade.generateBroadcastProduct(retMetaInfo);
						productList.add(product);
					}
				}

			} else if (DisplayConstants.DP_SHOPPING_TOP_MENU_ID.equals(topMenuId)) { // 쇼핑

				// 채널 ID로 상품 조회
				productBasicInfo.setCatalogId(productBaseInfo.getChnlProdId());
				productBasicInfo.setContentsTypeCd(DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
				reqMap.put("productBasicInfo", productBasicInfo);
				reqMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
				reqMap.put("imageCd", DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
				// 쇼핑 Meta 정보 조회
				MetaInfo retMetaInfo = this.metaInfoService.getShoppingMetaInfo(reqMap);
				if (retMetaInfo != null) {
					// 쇼핑 Response Generate
					Product product = this.responseInfoGenerateFacade.generateShoppingProduct(retMetaInfo);
					productList.add(product);
				}
			}

			res.setCommonResponse(commonResponse);
			res.setProductList(productList);
		}
		return res;
	}
}
