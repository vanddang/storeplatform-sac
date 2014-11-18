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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.feature.product.ProductListSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.product.ProductListSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.cache.service.ProductInfoManager;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.feature.product.vo.ListProduct;
import com.skplanet.storeplatform.sac.display.feature.product.vo.ListProductCriteria;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.cache.vo.AlbumMeta;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * Class 설명
 * 
 * Updated on : 2014. 10. 6.
 * Updated by : 문동선
 */
@org.springframework.stereotype.Service
public class ProductListServiceImpl implements ProductListService{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	
	@Autowired
	private CommonMetaInfoGenerator commonGenerator;
	
	@Autowired
    private ProductInfoManager productInfoManager;

	@Autowired
	private DisplayCommonService displayCommonService;
	

	@Autowired
	MetaInfoService metaInfoService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	/**
	 * <pre>
	 * TB_DP_LISTPROD 테이블에서 listId를 기준으로 상품목록을 만들어주는 함수
	 * </pre>
	 * @param 상품 목록 조회 조건
	 * @param header
	 * @return 상품 목록
	 */
	@Override
	public ProductListSacRes searchProductList(ProductListSacReq requestVO, SacRequestHeader header) {
		ProductListSacRes response = new ProductListSacRes();
		
		// [1] 기준 일시 조회
		String stdDt = getBatchStdDateStringFromDB(requestVO, header);
		
		// [2] 상품 조회 조건 생성
		ListProductCriteria lpCriteria = new ListProductCriteria(requestVO, stdDt);
		
		// [3] 상품ID목록을 DB에서 가져온다.
		List<ListProduct> prodList = this.commonDAO.queryForList( "ProductList.selectListProdList", lpCriteria, ListProduct.class);
		
		// [4] 다음 목록 검색을 위한 startKey를 response에 저장한다.
		setStartKeyIntoResponse(response, prodList);

		// [5] 상품ID로 상품메타정보를 채워넣는다
		setListProductIntoResponse(header, response, lpCriteria, prodList);
		
		if(prodList.size()>requestVO.getCount()){
			response.setHasNext("Y");
			removeRedundantLastItem(response);
		} else
			response.setHasNext("N");
		
		response.setCount(response.getProductList().size());
		setStdDtIntoResponse(response, stdDt);
		
		return response;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * @param response
	 */
	private void removeRedundantLastItem(ProductListSacRes response) {
		List<Product> list = response.getProductList();
		list.remove(list.size()-1);
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * @param response
	 * @param stdDt
	 */
	private void setStdDtIntoResponse(ProductListSacRes response, String stdDt) {
		Date date = this.commonGenerator.generateDate(DisplayConstants.DP_DATE_REG, stdDt);
		response.setDate(date);
	}

	private String getBatchStdDateStringFromDB(ProductListSacReq requestVO,
			SacRequestHeader header) {
		TenantHeader tenantHeader = header.getTenantHeader();
		
		// 배치완료 기준일시 조회
		String stdDt = this.displayCommonService.getBatchStandardDateString(tenantHeader.getTenantId(), requestVO.getListId());
	
		// 기준일시 체크
		if (StringUtils.isEmpty(stdDt))
			throw new StorePlatformException("SAC_DSP_0003", "stdDt", stdDt);
		return stdDt;
	}

	private void setListProductIntoResponse(SacRequestHeader header, ProductListSacRes response, ListProductCriteria lpCriteria, List<ListProduct> listProds) {
		List<Product> productList = new ArrayList<Product>();
		for(ListProduct listProd: listProds){
			Product p = getProduct(header, listProd);
			if(p!=null)
				productList.add(p);
		}
		
		response.setProductList(productList);
	}

	private void setStartKeyIntoResponse(ProductListSacRes response, List<ListProduct> prodList) {
		if(prodList.size()==0)
			return;
		String startKey = prodList.get(prodList.size()-1).getExpoOrd() + "/";
		startKey       += prodList.get(prodList.size()-1).getExpoOrdSub();
		response.setStartKey(startKey);
	}
	
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
			metaInfo = this.metaInfoService.getAppMetaInfo(paramMap);
			if(metaInfo!=null)
				product = this.responseInfoGenerateFacade.generateAppProduct(metaInfo);
		}// 이북
		else if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)){
			paramMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
			metaInfo = this.metaInfoService.getEbookComicMetaInfo(paramMap);
			if(metaInfo!=null)
				product = this.responseInfoGenerateFacade.generateEbookProduct(metaInfo);
		}// 코믹
		else if (DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(topMenuId)) {
			paramMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
			metaInfo = this.metaInfoService.getEbookComicMetaInfo(paramMap);
			if(metaInfo!=null)
				product = this.responseInfoGenerateFacade.generateComicProduct(metaInfo);
		}// 웹툰
		else if (DisplayConstants.DP_WEBTOON_TOP_MENU_ID.equals(topMenuId)) {
			paramMap.put("imageCd", DisplayConstants.DP_WEBTOON_TOP_MENU_ID);
			metaInfo = this.metaInfoService.getWebtoonMetaInfo(paramMap);
			if(metaInfo!=null)
				product = this.responseInfoGenerateFacade.generateWebtoonProduct(metaInfo);
		}
		// 영화
		else if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)){
			paramMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
			metaInfo = this.metaInfoService.getVODMetaInfo(paramMap);
			if(metaInfo!=null)
				product = this.responseInfoGenerateFacade.generateMovieProduct(metaInfo);
		}
		// 방송
		else if (DisplayConstants.DP_TV_TOP_MENU_ID.equals(topMenuId)) {
			paramMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
			metaInfo = this.metaInfoService.getVODMetaInfo(paramMap);
			if(metaInfo!=null)
				product = this.responseInfoGenerateFacade.generateBroadcastProduct(metaInfo);
		}
		// 통합뮤직
		else if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(topMenuId)) {
			paramMap.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
			//뮤직
			if(svcGrpCd.equals("DP000203")) {
				metaInfo = this.metaInfoService.getMusicMetaInfo(paramMap);
				if(metaInfo!=null)
					product = this.responseInfoGenerateFacade.generateMusicProduct(metaInfo);
	        }
			//앨범
			if(svcGrpCd.equals("DP000208")) {
	        	albumMeta = this.metaInfoService.getAlbumMetaInfo(paramMap);
				if(albumMeta!=null)
					product = this.responseInfoGenerateFacade.generateAlbumProduct(albumMeta);
	        }
		}
		// 쇼핑
		else if (prodId.startsWith("CL")) {
			paramMap.put("imageCd", DisplayConstants.DP_SHOPPING_REPRESENT_IMAGE_CD);
			metaInfo = this.metaInfoService.getShoppingMetaInfo(paramMap);
			if(metaInfo!=null)
				product = this.responseInfoGenerateFacade.generateShoppingProduct(metaInfo);
		}
		
		// 디버깅(오픈전 삭제 요망)
		if(product==null){
			product = new Product();
			product.setId(prodId);
			product.setProductExplain("---------더미 데이터-----------------");
			product.setProductDetailExplain(topMenuId);
		}
		
		product.setRecommendedReason(listProd.getRecomReason());
		product.setEtcProp(listProd.getEtcProp());

		return product;
	}
}
