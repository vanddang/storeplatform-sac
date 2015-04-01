package com.skplanet.storeplatform.sac.display.related.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.related.SimilarMovieSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.SimilarMovieSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.feature.product.service.ProductListService;
import com.skplanet.storeplatform.sac.display.feature.product.vo.ListProduct;
import com.skplanet.storeplatform.sac.display.related.vo.SimilarMovieCriteria;
import com.skplanet.storeplatform.sac.display.related.vo.SimilarMovieDbResultMap;

/**
 * 기존에 유사 상품 API가 있었지만 2015년 3월 신규 추가되었습니다.
 * - 기존: 모든 상품, DA자료 제공
 * - 신규: 영화 상품, Text Analysis팀 제공
 * Text file이 배치로 DB테이블에 업데이드 됩니다
 *
 * Updated on : 2015. 3. 16.
 * Updated by : 문동선M
 */
@Service
public class SimilarMovieServiceImpl implements SimilarMovieService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final String BATCH_ID = "SIM_VOD_UPDATE";

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService displayCommonService;

	@Autowired
	private ProductListService productListService;

	@Override
	public SimilarMovieSacRes searchSimilarMovieList(SimilarMovieSacReq requestVO, SacRequestHeader header){
		String tenantId = header.getTenantHeader().getTenantId();
		String prodId = requestVO.getProdId();
		String stdDt = getBatchStdDateStringFromDB(tenantId);

		List<SimilarMovieDbResultMap> moviesFromDB = getSimilarMovieListFromDb(tenantId, prodId, stdDt);
		SimilarMovieSacRes res = makeResponseFrom(moviesFromDB, requestVO, header);
		return res;
	}

	private String[] toProdGradeCdArr(String prodGradeCd) {
		if(StringUtils.isEmpty(prodGradeCd))
			return null;
		return StringUtils.split(prodGradeCd, "+");
	}

	/**
	 * <pre>
	 * 배치 기준 일자 조회
	 * </pre>
	 * @param tenantId
	 * @return
	 */
	private String getBatchStdDateStringFromDB(String tenantId) {
		String stdDt = displayCommonService.getBatchStandardDateString(tenantId, BATCH_ID);
		if (StringUtils.isEmpty(stdDt))
			throw new StorePlatformException("SAC_DSP_0003", "stdDt", stdDt);
		return stdDt;
	}

	/**
	 * <pre>
	 * DB에서 유사 영화 목록 조회
	 * </pre>
	 * @param tenantId
	 * @param prodId
	 * @param stdDt
	 * @return
	 */
	private List<SimilarMovieDbResultMap> getSimilarMovieListFromDb(String tenantId, String prodId, String stdDt) {
		SimilarMovieCriteria criteria = new SimilarMovieCriteria(tenantId, prodId, stdDt);
		List<SimilarMovieDbResultMap> prodListsFromDB = commonDAO.queryForList( "SimilarMovie.selectSimilarMovieList", criteria, SimilarMovieDbResultMap.class);
		return prodListsFromDB;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * @param moviesFromDB
	 * @param requestVO
	 * @param header
	 * @return
	 */
	private SimilarMovieSacRes makeResponseFrom(List<SimilarMovieDbResultMap> moviesFromDB, SimilarMovieSacReq requestVO, SacRequestHeader header) {
		String[] prodGradeCdArr = toProdGradeCdArr(requestVO.getProdGradeCd());
		Integer reqCount = requestVO.getCount();
		String hasNext = "N";
		SimilarMovieSacRes res = new SimilarMovieSacRes();
		List<Product> list = new ArrayList<Product>();
		if(moviesFromDB==null || moviesFromDB.isEmpty())
			return res;
		SimilarMovieDbResultMap rm =  moviesFromDB.get(0);
		String[] prodIdWithPointArr = rm.getSimProdIdList().split(",");
		for(String prodIdWithPoint : prodIdWithPointArr){
			String prodId = prodIdWithPoint.split("\\(")[0].trim();
			logger.debug("add prodId={}",prodId);
			Product p = makeProductFrom(prodId, header);
			if(p!=null && onSale(p) && requestedGrade(p,prodGradeCdArr)) {
				if(list.size()<reqCount)
					list.add(p);
				else {
					hasNext = "Y";
					break;
				}
			}
		}
		res.setProductList(list);
		res.setCount(list.size());
		res.setHasNext(hasNext);
		return res;
	}

	private boolean requestedGrade(Product p, String[] prodGradeCds) {
		if(prodGradeCds==null)
			return true;
		for(String gradeCd:prodGradeCds)
			if(p.getRights()!=null && p.getRights().getGrade().equals(gradeCd))
				return true;
		return false;
	}

	private boolean onSale(Product product) {
		return product.getSalesStatus().equals(DisplayConstants.DP_SALE_STAT_ING);
	}

	/**
	 * <pre>
	 * DB resultmap으로 부터 response용 Product 객체 생성
	 * </pre>
	 * @param rm
	 * @return
	 */
	private Product makeProductFrom(String prodId, SacRequestHeader header) {
		ListProduct lp = new ListProduct();
		lp.setProdId(prodId);
		lp.setTopMenuId(DisplayConstants.DP_MOVIE_TOP_MENU_ID);
		lp.setSvcGrpCd(DisplayConstants.DP_MULTIMEDIA_PROD_SVC_GRP_CD);
		lp.setContentsTypeCd(DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);

		return productListService.getProduct(header, lp);
	}
}
