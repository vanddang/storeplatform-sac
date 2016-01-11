package com.skplanet.storeplatform.sac.display.feature.recommend.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendTodaySacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendTodaySacRes;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * Updated on : 2014. 2. 6. Updated by : 조준일, nTels.
 */
@Service
public class RecommendTodayServiceImpl implements RecommendTodayService {

	// private transient Logger log = LoggerFactory.getLogger(CategoryAppServiceImpl.class);

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService displayCommonService;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.product.service.TotalRecommendService#searchTotalRecommendList(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public RecommendTodaySacRes searchTodayList(RecommendTodaySacReq requestVO, SacRequestHeader header) {
		// TODO Auto-generated method stub
		// 공통 응답 변수 선언
		RecommendTodaySacRes responseVO = new RecommendTodaySacRes();
		CommonResponse commonResponse = new CommonResponse();

		// 헤더값 세팅
		requestVO.setTenantId(header.getTenantHeader().getTenantId());
		requestVO.setDeviceModelCd(header.getDeviceHeader().getModel());
		requestVO.setAnyDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);
		requestVO.setLangCd(header.getTenantHeader().getLangCd());

		// 시작점 ROW Default 세팅
		if (requestVO.getOffset() == null) {
			requestVO.setOffset(1);
		}
		// 페이지당 노출될 ROW 개수 Default 세팅
		if (requestVO.getCount() == null) {
			requestVO.setCount(20);
		}

		// 배치완료 기준일시 조회
		String stdDt = this.displayCommonService.getBatchStandardDateString(requestVO.getTenantId(),
				requestVO.getListId());

		// 기준일시 체크
		if (StringUtils.isEmpty(stdDt)) {
			throw new StorePlatformException("SAC_DSP_0003", "stdDt", stdDt);
		} else {
			requestVO.setStdDt(stdDt);
		}

		// prodGradeCd 배열로 변경
		if (!StringUtils.isEmpty(requestVO.getProdGradeCd())) {
			String[] prodGradeCdArr = StringUtils.split(requestVO.getProdGradeCd(), "+");
			requestVO.setProdGradeCdArr(prodGradeCdArr);
		}

		List<ProductBasicInfo> productBasicInfoList;

		productBasicInfoList = this.commonDAO.queryForList("FeatureRecommend.selectRecommendTodayList", requestVO,
				ProductBasicInfo.class);

		List<Product> productList = new ArrayList<Product>();

		if (productBasicInfoList != null && productBasicInfoList.size() > 0) {
			for (ProductBasicInfo productBasicInfo : productBasicInfoList) {

				if ("DP16".equals(requestVO.getTopMenuId())) {
					// Meta 정보 조회
					MetaInfo retMetaInfo = this.metaInfoService.getMusicMetaInfo(productBasicInfo);

					if (retMetaInfo != null) {
						// Response Generate
						Product product = this.responseInfoGenerateFacade.generateMusicProduct(retMetaInfo);
						productList.add(product);
					}
				} else {
					// Meta 정보 조회
					MetaInfo retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(productBasicInfo);

					if (retMetaInfo != null) {
						if ("DP13".equals(requestVO.getTopMenuId())) {
							// Response Generate
							Product product = this.responseInfoGenerateFacade.generateEbookProduct(retMetaInfo);
							// 스티커
							product.setSticker(productBasicInfo.getEtcCd());
							productList.add(product);
						} else {
							// Response Generate
							Product product = this.responseInfoGenerateFacade.generateComicProduct(retMetaInfo);
							// 스티커
							product.setSticker(productBasicInfo.getEtcCd());
							productList.add(product);
						}
					}
				}
			}
			commonResponse.setTotalCount(productBasicInfoList.get(0).getTotalCount());
			responseVO.setProductList(productList);
			responseVO.setCommonResponse(commonResponse);
		} else {
			// 조회 결과 없음
			commonResponse.setTotalCount(0);
			responseVO.setProductList(productList);
			responseVO.setCommonResponse(commonResponse);
		}

		return responseVO;
	}
}
