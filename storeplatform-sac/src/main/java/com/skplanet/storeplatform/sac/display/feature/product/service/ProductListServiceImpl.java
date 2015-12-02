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

		// 1건을 더 조회하도록 파라미터가 세팅된다.
		ListProductCriteria param = new ListProductCriteria( requestVO, header.getTenantHeader().getTenantId(), stdDt );

		while( true ) {

			List<ListProduct> prodListFromDB = commonDAO.queryForList( "ProductList.selectListProdList", param, ListProduct.class );

			addListProductIntoResponse( header, response, prodListFromDB, param.getCount() );

			if( hasResponseEnoughProdList(response, param.getCount()) || noMoreProdToGet(prodListFromDB, param.getCount()) ) break;

			setNextExpoOrdIntoCriteria( param, prodListFromDB );

		}

		// 1건을 더 조회하여, 다음페이지 존재여부를 체크하고
		setHasNext( response, requestVO );

		// 최종결과에서는 1건을 제외시킨다.
		removeRedundantLastItem( response, requestVO );

		setStartKey( response );
		setCount( response );
		setStdDt( response, stdDt );
		setListIdAndEtcProp( response, requestVO, header );

		return response;
	}

	private void setListIdAndEtcProp(ProductListSacRes response, ProductListSacReq requestVO, SacRequestHeader header) {

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

	private boolean hasResponseEnoughProdList( ProductListSacRes response, int limitCount ) {
		return response.getProductList().size() >= limitCount;
	}

	private void setHasNext( ProductListSacRes response, ProductListSacReq requestVO ) {

		response.setHasNext( response.getProductList().size() > requestVO.getCount() ? "Y" : "N" );

	}

	private boolean noMoreProdToGet(List<ListProduct> prodListFromDB, int countExpected) {
		return prodListFromDB.size() == 0 || prodListFromDB.size() < countExpected;
	}

	private void setNextExpoOrdIntoCriteria(ListProductCriteria lpCriteria, List<ListProduct> prodListFromDB) {

		ListProduct lastProd = prodListFromDB.get( prodListFromDB.size() - 1 );

		lpCriteria.setLastExpoOrd(    Integer.valueOf(lastProd.getExpoOrd()) );
		lpCriteria.setLastExpoOrdSub( Integer.valueOf(lastProd.getExpoOrdSub()) );
	}

	private void removeRedundantLastItem(ProductListSacRes response, ProductListSacReq requestedCount) {

		List<Product> list = response.getProductList();

		while( list.size() > requestedCount.getCount() )
			list.remove(list.size()-1);

	}

	private void setStdDt(ProductListSacRes response, String stdDt) {
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

	private void addListProductIntoResponse( SacRequestHeader header, ProductListSacRes response, List<ListProduct> listProds, int limitCount ) {

		List<Product> productList = response.getProductList();

		for( ListProduct prod : listProds ) {

			if( productList.size() >= limitCount ) break;

			Product prodMeta = getProduct( header, prod );

			if( prodMeta != null ) productList.add( prodMeta );

		}

	}

	private void setStartKey(ProductListSacRes response) {
		List<Product> prodList = response.getProductList();
		if(prodList.size()==0)
			return;
		String startKey = prodList.get(prodList.size()-1).getExpoOrd() + "/";
		startKey       += prodList.get(prodList.size()-1).getExpoOrdSub();
		response.setStartKey(startKey);
	}

	private void setCount( ProductListSacRes response ) {
		response.setCount( response.getProductList().size() );
	}

	@Override
	public Product getProduct( SacRequestHeader header, ListProduct listProd ) {
        if (listProd == null) {
            return null;
        }

		Product product = null;
		String prodId=listProd.getProdId();
		String topMenuId=listProd.getTopMenuId();
		String svcGrpCd=listProd.getSvcGrpCd();

		MetaInfo metaInfo; // 메타정보 VO
		AlbumMeta albumMeta; // 메타정보 VO
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
			if(metaInfo!=null) {
				product = responseInfoGenerateFacade.generateShoppingProduct(metaInfo);
				// 쇼핑 4.0 일 경우 특가 상품 여부 추가
				product.setSpecialProdYn(metaInfo.getSpecialSaleYn());
			}
		}

		if( product==null ) return null;

		product.setRecommendedReason(listProd.getRecomReason());
		product.setEtcProp(listProd.getEtcProp());
		product.setExpoOrd(listProd.getExpoOrd());
		product.setExpoOrdSub(listProd.getExpoOrdSub());

		return product;

	}

}
