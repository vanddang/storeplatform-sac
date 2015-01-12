/**
 *
 */
package com.skplanet.storeplatform.sac.display.feature.product.service;

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
import com.skplanet.storeplatform.sac.client.display.vo.feature.product.ProductListSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.product.ProductListSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.cache.vo.AlbumMeta;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.feature.list.vo.DisplayListCriteria;
import com.skplanet.storeplatform.sac.display.feature.list.vo.DisplayListFromDB;
import com.skplanet.storeplatform.sac.display.feature.product.vo.ListProduct;
import com.skplanet.storeplatform.sac.display.feature.product.vo.ListProductCriteria;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * ProductListServiceImpl
 *
 * Updated on : 2014. 10. 6.
 * Updated by : 문동선
 */
@Service
public class ProductListServiceImpl implements ProductListService{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

	@Autowired
	private DisplayCommonService displayCommonService;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	/**
	 * <pre>
	 * TB_DP_LISTPROD 테이블에서 listId를 기준으로 상품목록을 만들어주는 함수
	 * </pre>
	 * @param requestVO 상품 목록 조회 조건
	 * @param header
	 * @return 상품 목록
	 */
	@Override
	public ProductListSacRes searchProductList(ProductListSacReq requestVO, SacRequestHeader header) {
		ProductListSacRes response = new ProductListSacRes();

		String stdDt = getBatchStdDateStringFromDB(requestVO, header);
		ListProductCriteria lpCriteria = new ListProductCriteria(requestVO, stdDt);

		while(true) {
			List<ListProduct> prodListFromDB = commonDAO.queryForList( "ProductList.selectListProdList", lpCriteria, ListProduct.class);
			addListProductIntoResponse(header, response, prodListFromDB);

			if( responseGetEnoughProdList(response, requestVO) || noMoreProdToGet(prodListFromDB, lpCriteria.getCount()))
				break;
			else {
				setNextExpoOrdIntoCriteria(lpCriteria, prodListFromDB);
				lpCriteria.setCount(lpCriteria.getCount()-response.getProductList().size());
			}
		}

		setHasNextIntoResponse(response, requestVO);
		removeRedundantLastItem(response, requestVO.getCount());
		setStartKeyIntoResponse(response);
		response.setCount(response.getProductList().size());
		setStdDtIntoResponse(response, stdDt);
		setListIdAndEtcPropIntoResponse(response, requestVO, header);

		return response;
	}

	private void setListIdAndEtcPropIntoResponse(ProductListSacRes response, ProductListSacReq requestVO, SacRequestHeader header) {
		String tenantId = header.getTenantHeader().getTenantId();
		String listId = requestVO.getListId();
		int count = 1;
		DisplayListCriteria listCriteria = new DisplayListCriteria(tenantId, listId, "N", count);
		List<DisplayListFromDB> listsFromDB = commonDAO.queryForList( "DisplayList.selectDisplayList", listCriteria, DisplayListFromDB.class);
		if(!listsFromDB.isEmpty()){
    		response.setEtcProp(listsFromDB.get(0).getEtcProp());
    		response.setListId(listsFromDB.get(0).getListId());
    		response.setListNm(listsFromDB.get(0).getListNm());
		} else {
			response.setCount(0);
			response.setProductList(new ArrayList<Product>());
		}
	}

	private boolean responseGetEnoughProdList(ProductListSacRes response, ProductListSacReq requestVO) {
		return response.getProductList().size()>=requestVO.getCount();
	}

	private void setHasNextIntoResponse(ProductListSacRes response, ProductListSacReq requestVO) {
		if(response.getProductList().size()>requestVO.getCount())
			response.setHasNext("Y");
		else
			response.setHasNext("N");
	}

	private boolean noMoreProdToGet(List<ListProduct> prodListFromDB, int countExpected) {
		return prodListFromDB.size()<countExpected;
	}

	private void setNextExpoOrdIntoCriteria(ListProductCriteria lpCriteria, List<ListProduct> prodListFromDB) {
		String lastExpoOrd    = prodListFromDB.get(prodListFromDB.size()-1).getExpoOrd();
		String lastExpoOrdSub = prodListFromDB.get(prodListFromDB.size()-1).getExpoOrdSub();
		lpCriteria.setLastExpoOrd(   new Integer(lastExpoOrd   ));
		lpCriteria.setLastExpoOrdSub(new Integer(lastExpoOrdSub));
	}

	private void removeRedundantLastItem(ProductListSacRes response, int requestedCount) {
		List<Product> list = response.getProductList();
		while(list.size() > requestedCount)
			list.remove(list.size()-1);
	}

	private void setStdDtIntoResponse(ProductListSacRes response, String stdDt) {
		Date date = commonGenerator.generateDate(DisplayConstants.DP_DATE_REG, stdDt);
		response.setDate(date);
	}

	private String getBatchStdDateStringFromDB(ProductListSacReq requestVO,
			SacRequestHeader header) {
		TenantHeader tenantHeader = header.getTenantHeader();

		// 배치완료 기준일시 조회
		String stdDt = displayCommonService.getBatchStandardDateString(tenantHeader.getTenantId(), requestVO.getListId());

		// 기준일시 체크
		if (StringUtils.isEmpty(stdDt))
			throw new StorePlatformException("SAC_DSP_0003", "stdDt", stdDt);
		return stdDt;
	}

	private void addListProductIntoResponse(SacRequestHeader header, ProductListSacRes response, List<ListProduct> listProds) {
		List<Product> productList = response.getProductList();
		for(ListProduct listProd: listProds){
			Product p = getProduct(header, listProd);
			if(p!=null)
				productList.add(p);
		}
	}

	private void setStartKeyIntoResponse(ProductListSacRes response) {
		List<Product> prodList = response.getProductList();
		if(prodList.size()==0)
			return;
		String startKey = prodList.get(prodList.size()-1).getExpoOrd() + "/";
		startKey       += prodList.get(prodList.size()-1).getExpoOrdSub();
		response.setStartKey(startKey);
	}

	@Override
	public Product getProduct(SacRequestHeader header, ListProduct listProd) {
		Product product = null;
		String prodId=listProd.getProdId();
		String topMenuId=listProd.getTopMenuId();
		String svcGrpCd=listProd.getSvcGrpCd();

		MetaInfo metaInfo = null; // 메타정보 VO
		AlbumMeta albumMeta = null; // 메타정보 VO
		ProductBasicInfo productInfo = new ProductBasicInfo(); // 메타정보 조회용 상품 파라미터
		Map<String, Object> paramMap = new HashMap<String, Object>(); // 메타정보 조회용 파라미터

		// 메타정보 조회를 위한 파라미터 세팅
		productInfo.setProdId(prodId);
		productInfo.setPartProdId(prodId);
		productInfo.setCatalogId(prodId);
		productInfo.setTopMenuId(topMenuId);
		productInfo.setContentsTypeCd(listProd.getContentsTypeCd());
		productInfo.setSvcGrpCd(svcGrpCd);
		paramMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
		paramMap.put("tenantHeader", header.getTenantHeader());
		paramMap.put("deviceHeader", header.getDeviceHeader());
		paramMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
		paramMap.put("productBasicInfo", productInfo);

		// APP
		if (DisplayConstants.DP_GAME_TOP_MENU_ID.equals(topMenuId)
				|| DisplayConstants.DP_FUN_TOP_MENU_ID.equals(topMenuId)
				|| DisplayConstants.DP_LIFE_LIVING_TOP_MENU_ID.equals(topMenuId)
				|| DisplayConstants.DP_LANG_EDU_TOP_MENU_ID.equals(topMenuId)) {
			paramMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
			metaInfo = metaInfoService.getAppMetaInfo(paramMap);
			if(metaInfo!=null)
				product = responseInfoGenerateFacade.generateAppProduct(metaInfo);
		}// 이북
		else if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)){
			paramMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
			metaInfo = metaInfoService.getEbookComicMetaInfo(paramMap);
			if(metaInfo!=null)
				product = responseInfoGenerateFacade.generateEbookProduct(metaInfo);
		}// 코믹
		else if (DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(topMenuId)) {
			paramMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
			metaInfo = metaInfoService.getEbookComicMetaInfo(paramMap);
			if(metaInfo!=null)
				product = responseInfoGenerateFacade.generateComicProduct(metaInfo);
		}// 웹툰
		else if (DisplayConstants.DP_WEBTOON_TOP_MENU_ID.equals(topMenuId)) {
			paramMap.put("imageCd", DisplayConstants.DP_WEBTOON_TOP_MENU_ID);
			metaInfo = metaInfoService.getWebtoonMetaInfo(paramMap);
			if(metaInfo!=null)
				product = responseInfoGenerateFacade.generateWebtoonProduct(metaInfo);
		}
		// 영화
		else if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)){
			paramMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
			metaInfo = metaInfoService.getVODMetaInfo(paramMap);
			if(metaInfo!=null)
				product = responseInfoGenerateFacade.generateMovieProduct(metaInfo);
		}
		// 방송
		else if (DisplayConstants.DP_TV_TOP_MENU_ID.equals(topMenuId)) {
			paramMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
			metaInfo = metaInfoService.getVODMetaInfo(paramMap);
			if(metaInfo!=null)
				product = responseInfoGenerateFacade.generateBroadcastProduct(metaInfo);
		}
		// 통합뮤직||폰꾸미기
		else if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(topMenuId)||
				DisplayConstants.DP_DISPLAY_PHONE_TOP_MENU_ID.equals(topMenuId)) {
			paramMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
			//멀티미디어(뮤직)||폰꾸미기(링,벨)
			if(svcGrpCd.equals("DP000203")|| svcGrpCd.equals("DP000204")) {
                paramMap.put(DisplayConstants.META_MUSIC_USE_CONTENT_TP, "Y");
				metaInfo = metaInfoService.getMusicMetaInfo(paramMap);
				if(metaInfo!=null)
					product = responseInfoGenerateFacade.generateMusicProduct(metaInfo);
	        } //앨범
			else if(svcGrpCd.equals("DP000208")) {
	        	albumMeta = metaInfoService.getAlbumMetaInfo(paramMap);
				if(albumMeta!=null)
					product = responseInfoGenerateFacade.generateAlbumProduct(albumMeta);
	        }
		}
		// 쇼핑
		else if (prodId.startsWith("CL")) {
			paramMap.put("imageCd", DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
			metaInfo = metaInfoService.getShoppingMetaInfo(paramMap);
			if(metaInfo!=null)
				product = responseInfoGenerateFacade.generateShoppingProduct(metaInfo);
		}

		if(product==null)
			return null;

		product.setRecommendedReason(listProd.getRecomReason());
		product.setEtcProp(listProd.getEtcProp());
		product.setExpoOrd(listProd.getExpoOrd());
		product.setExpoOrdSub(listProd.getExpoOrdSub());

		return product;
	}
}
