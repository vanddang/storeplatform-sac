package com.skplanet.storeplatform.sac.display.feature.product.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.feature.product.ProductListSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.product.ProductListSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.extractor.HeaderExtractor;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.cache.service.ProductListCacheManager;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.feature.list.vo.DisplayListCriteria;
import com.skplanet.storeplatform.sac.display.feature.list.vo.DisplayListFromDB;
import com.skplanet.storeplatform.sac.display.feature.product.vo.ListProduct;
import com.skplanet.storeplatform.sac.display.feature.product.vo.ListProductCriteria;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ProductListServiceImpl
 *
 * Updated on : 2014. 10. 6.
 * Updated by : 정화수
 */
@Service
public class ProductListServiceImpl implements ProductListService{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	HeaderExtractor header;

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	ProductListCacheManager cacheManager;

	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

	@Autowired
	private DisplayCommonService displayCommonService;

	@Autowired
	private MetaInfoService metaInfoService;

	/**
	 * <pre>
	 * TB_DP_LISTPROD 테이블에서 listId를 기준으로 상품목록을 만들어주는 함수
	 * </pre>
	 * @param request 상품 목록 조회 조건
	 * @param header
	 * @return 상품 목록
	 */
	@Override
	public ProductListSacRes searchProductList( ProductListSacReq request, SacRequestHeader header ) {

		ProductListSacRes response = new ProductListSacRes();

		String stdDt = getBatchStdDateStringFromDB(request, header);

		// 1건을 더 조회하도록 파라미터가 세팅된다.
		ListProductCriteria param = new ListProductCriteria( request, header.getTenantHeader().getTenantId(), stdDt );

		while( true ) {

			List<ListProduct> prodListFromDB = cacheManager.getListProducts( param );

			addListProductIntoResponse( header, response, prodListFromDB, param.getCount() );

			if( hasResponseEnoughProdList(response, param.getCount()) || noMoreProdToGet(prodListFromDB, param.getCount()) ) break;

			setNextExpoOrdIntoCriteria( param, prodListFromDB );

		}

		// 1건을 더 조회하여, 다음페이지 존재여부를 체크하고
		setHasNext( response, request );

		// 최종결과에서는 1건을 제외시킨다.
		removeRedundantLastItem( response, request );

		setStartKey( response );
		setCount( response );
		setStdDt( response, stdDt );
		setListIdAndEtcProp( response, request.getListId() );

		return response;

	}

	private void setListIdAndEtcProp( ProductListSacRes response, String listId ) {

		String tenantId = header.getTenantHeader().getTenantId();

		DisplayListCriteria listCriteria = new DisplayListCriteria( tenantId, listId, "N", 1 );

		List<DisplayListFromDB> listsFromDB = commonDAO.queryForList( "DisplayList.selectDisplayList", listCriteria, DisplayListFromDB.class );

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

	private String getBatchStdDateStringFromDB( ProductListSacReq requestVO, SacRequestHeader header ) {
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

			Product prodMeta = getProduct( prod );

			if( prodMeta != null ) productList.add( prodMeta );

		}

	}

	private void setStartKey(ProductListSacRes response) {
		List<Product> prodList = response.getProductList();
		if( prodList.size() == 0) return;
		String startKey = prodList.get(prodList.size()-1).getExpoOrd() + "/";
		startKey       += prodList.get(prodList.size()-1).getExpoOrdSub();
		response.setStartKey(startKey);
	}

	private void setCount( ProductListSacRes response ) {
		response.setCount( response.getProductList().size() );
	}

	private Product getProduct( ListProduct productInList ) {

        if ( productInList == null ) return null;

		Product product = metaInfoService.getProductMeta( productInList.getProdId() );

		if( product == null ) return null;

		product.setRecommendedReason( productInList.getRecomReason() );
		product.setEtcProp( productInList.getEtcProp() );
		product.setExpoOrd( productInList.getExpoOrd() );
		product.setExpoOrdSub( productInList.getExpoOrdSub() );

		return product;

	}

}