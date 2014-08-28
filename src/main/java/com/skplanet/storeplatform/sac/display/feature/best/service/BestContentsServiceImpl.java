package com.skplanet.storeplatform.sac.display.feature.best.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.best.BestContentsSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.best.BestContentsSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
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
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 23. Updated by : 이석희, SK 플래닛.
 */
@Service
public class BestContentsServiceImpl implements BestContentsService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService commonService;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	/**
	 * 
	 * <pre>
	 * BEST 컨텐츠 리스트 조회.
	 * </pre>
	 * 
	 * @param requestheader
	 *            공통헤더
	 * @param bestContentsReq
	 *            파라미터
	 * @return BEST 컨텐츠 리스트
	 */
	@Override
	public BestContentsSacRes searchBestContentsList(SacRequestHeader requestheader, BestContentsSacReq bestContentsReq) {
		TenantHeader tenantHeader = requestheader.getTenantHeader();
		DeviceHeader deviceHeader = requestheader.getDeviceHeader();

		this.log.debug("########################################################");
		this.log.debug("tenantHeader.getTenantId()	:	" + tenantHeader.getTenantId());
		this.log.debug("tenantHeader.getLangCd()	:	" + tenantHeader.getLangCd());
		this.log.debug("deviceHeader.getModel()		:	" + deviceHeader.getModel());
		this.log.debug("########################################################");

		bestContentsReq.setTenantId(tenantHeader.getTenantId());
		bestContentsReq.setLangCd(tenantHeader.getLangCd());
		bestContentsReq.setDeviceModelCd(deviceHeader.getModel());

		BestContentsSacRes response = new BestContentsSacRes();
		CommonResponse commonResponse = new CommonResponse();

		List<Product> productList = new ArrayList<Product>();
		List<Identifier> identifierList = null;
		List<Menu> menuList = null;
		List<Source> sourceList = null;
		List<Support> supportList = null;

		// 파라미터 유효값 체크
		if (StringUtils.isNotEmpty(bestContentsReq.getProdGradeCd())) {
			String[] arrayProdGradeCd = bestContentsReq.getProdGradeCd().split("\\+");
			for (int i = 0; i < arrayProdGradeCd.length; i++) {
				if (StringUtils.isNotEmpty(arrayProdGradeCd[i])) {
					if (!"PD004401".equals(arrayProdGradeCd[i]) && !"PD004402".equals(arrayProdGradeCd[i])
							&& !"PD004403".equals(arrayProdGradeCd[i])) {
						this.log.debug("----------------------------------------------------------------");
						this.log.debug("유효하지않은 상품 등급 코드 : " + arrayProdGradeCd[i]);
						this.log.debug("----------------------------------------------------------------");

						throw new StorePlatformException("SAC_DSP_0003", (i + 1) + " 번째 prodGradeCd",
								arrayProdGradeCd[i]);
					}
				}
			}
		}

		int offset = 1; // default
		int count = 20; // default

		if (bestContentsReq.getOffset() != null) {
			offset = bestContentsReq.getOffset();
		}
		bestContentsReq.setOffset(offset); // set offset

		if (bestContentsReq.getCount() != null) {
			count = bestContentsReq.getCount();
		}
		count = offset + count - 1;
		bestContentsReq.setCount(count); // set count

		String stdDt = this.commonService.getBatchStandardDateString(tenantHeader.getTenantId(),
				bestContentsReq.getListId());
		bestContentsReq.setStdDt(stdDt); // 2014.01.28 이석희 수정 S01 하드코딩에서 헤더에서 get 한 TenantId

		// '+'로 연결 된 상품등급코드를 배열로 전달
		if (StringUtils.isNotEmpty(bestContentsReq.getProdGradeCd())) {
			String[] arrayProdGradeCd = bestContentsReq.getProdGradeCd().split("\\+");
			bestContentsReq.setArrayProdGradeCd(arrayProdGradeCd);
		}

        // ID list 조회
        List<ProductBasicInfo> productBasicInfoList = null;
        if ("movie".equals(bestContentsReq.getFilteredBy()) || "broadcast".equals(bestContentsReq.getFilteredBy())
                || "movie+broadcast".equals(bestContentsReq.getFilteredBy())) {
            // 영화, 방송 상품List 조회 (Step 1)
            productBasicInfoList = this.commonDAO.queryForList("BestContents.selectBestContentsVodList",
                    bestContentsReq, ProductBasicInfo.class);
        } else {
            // 이북, 코믹 상품List 조회 (Step 1)
            productBasicInfoList = this.commonDAO.queryForList("BestContents.selectBestContentsBookList",
                    bestContentsReq, ProductBasicInfo.class);
        }

        if (!productBasicInfoList.isEmpty()) {
            Map<String, Object> reqMap = new HashMap<String, Object>();
            reqMap.put("tenantHeader", tenantHeader);
            reqMap.put("deviceHeader", deviceHeader);
            reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
            for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
                reqMap.put("productBasicInfo", productBasicInfo);
                MetaInfo retMetaInfo = null;
                if ("movie".equals(bestContentsReq.getFilteredBy())
                        || "broadcast".equals(bestContentsReq.getFilteredBy())
                        || "movie+broadcast".equals(bestContentsReq.getFilteredBy())) {
                    // 영화, 방송 Meta 정보 조회 (Step 2)
                    reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
                    retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap);
                } else {
                    // 이북, 코믹 Meta 정보 조회 (Step 2)
                    reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
                    retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap);
                }

                if (retMetaInfo != null) {
                    if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(retMetaInfo.getTopMenuId())) {
                        Product product = this.responseInfoGenerateFacade.generateEbookProduct(retMetaInfo);
                        productList.add(product);
                    } else if (DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(retMetaInfo.getTopMenuId())) {
                        Product product = this.responseInfoGenerateFacade.generateComicProduct(retMetaInfo);
                        productList.add(product);
                    } else if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(retMetaInfo.getTopMenuId())) {
                        Product product = this.responseInfoGenerateFacade.generateMovieProduct(retMetaInfo);
                        productList.add(product);
                    } else if (DisplayConstants.DP_TV_TOP_MENU_ID.equals(retMetaInfo.getTopMenuId())) {
                        Product product = this.responseInfoGenerateFacade.generateBroadcastProduct(retMetaInfo);
                        productList.add(product);
                    }
                }
            }
            commonResponse.setTotalCount(productBasicInfoList.get(0).getTotalCount());
            response.setProductList(productList);
            response.setCommonResponse(commonResponse);
        } else {
            // 조회 결과 없음
            commonResponse.setTotalCount(0);
            response.setProductList(productList);
            response.setCommonResponse(commonResponse);
        }

		response.setCommonResponse(commonResponse);
		response.setProductList(productList);

		return response;
	}
}
