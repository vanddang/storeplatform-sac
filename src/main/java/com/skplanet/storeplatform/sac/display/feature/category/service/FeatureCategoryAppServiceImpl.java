/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.category.service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.display.vo.feature.category.FeatureCategoryAppReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.category.FeatureCategoryAppRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.feature.category.vo.CategoryAppDTO;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * 
 * 
 * Updated on : 2013. 12. 24. Updated by : 서영배, GTSOFT.
 */
@org.springframework.stereotype.Service
@Transactional
public class FeatureCategoryAppServiceImpl implements FeatureCategoryAppService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

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
	public FeatureCategoryAppRes searchAppList(FeatureCategoryAppReq requestVO) {
		// TODO Auto-generated method stub
		// 공통 응답 변수 선언
		int totalCount = 0;
		FeatureCategoryAppRes responseVO = null;
		CommonResponse commonResponse = null;

		List<CategoryAppDTO> resultList = this.commonDAO.queryForList("FeatureCategory.selectCategoryAppListDummy",
				requestVO, CategoryAppDTO.class);
		List<Product> listVO = new ArrayList<Product>();

		CategoryAppDTO categoryAppDTO;
		Product product;
		Identifier identifier;
		Title title;
		App app;
		Accrual accrual;
		Rights rights;
		Source source;
		Price price;
		Support support;
		Menu menu;

		// Response VO를 만들기위한 생성자
		// List<Product> productList = new ArrayList<Product>();
		List<Menu> menuList;
		List<Source> sourceList;
		List<Support> supportList;
		List<Identifier> identifierList;

		for (int i = 0; resultList != null && i < resultList.size(); i++) {

			categoryAppDTO = resultList.get(i);
			product = new Product();
			identifier = new Identifier();
			title = new Title();
			app = new App();
			accrual = new Accrual();
			rights = new Rights();
			source = new Source();
			price = new Price();
			support = new Support();

			// 상품ID
			identifier = new Identifier();

			// Response VO를 만들기위한 생성자
			menuList = new ArrayList<Menu>();
			sourceList = new ArrayList<Source>();
			supportList = new ArrayList<Support>();
			identifierList = new ArrayList<Identifier>();

			totalCount = categoryAppDTO.getTotalCount();

			identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
			identifier.setText(categoryAppDTO.getProdId());
			title.setText(categoryAppDTO.getProdNm());

			menu = new Menu();
			menu.setId(categoryAppDTO.getTopMenuId());
			menu.setName(categoryAppDTO.getTopMenuNm());
			menu.setType("topClass");
			menuList.add(menu);
			menu = new Menu();
			menu.setId(categoryAppDTO.getMenuId());
			menu.setName(categoryAppDTO.getMenuNm());
			// menu.setType("");
			menuList.add(menu);

			app.setAid(categoryAppDTO.getAid());
			app.setPackageName(categoryAppDTO.getApkPkgNm());
			app.setVersionCode(categoryAppDTO.getApkVer());
			app.setVersion(categoryAppDTO.getProdVer());
			product.setApp(app);

			accrual.setVoterCount(categoryAppDTO.getPrchsCnt());
			accrual.setDownloadCount(categoryAppDTO.getDwldCnt());
			accrual.setScore(3.3);

			/*
			 * Rights grade
			 */
			rights.setGrade(categoryAppDTO.getProdGrdCd());

			// source.setMediaType("");
			source.setSize(categoryAppDTO.getFileSize());
			source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
			source.setUrl(categoryAppDTO.getFilePath());
			sourceList.add(source);

			/*
			 * Price text
			 */
			price.setText(categoryAppDTO.getProdAmt());

			// identifier Lisst형으로 수정
			// product.setIdentifier(identifier);
			identifierList.add(identifier);
			product.setIdentifierList(identifierList);

			product.setTitle(title);

			if ("PD012301".equals(categoryAppDTO.getPartParentClsfCd())) {
				support = new Support();
				support.setType("iab");
				support.setText("Y");
				supportList.add(support);
			} else {
				support = new Support();
				support.setType("iab");
				support.setText("N");
				supportList.add(support);
			}
			if ("Y".equals(categoryAppDTO.getDrmYn())) {
				support = new Support();
				support.setType("drm");
				support.setText("Y");
				supportList.add(support);
			} else {
				support = new Support();
				support.setType("drm");
				support.setText("N");
				supportList.add(support);
			}

			product.setSupportList(supportList);
			product.setMenuList(menuList);

			product.setAccrual(accrual);
			product.setRights(rights);
			product.setProductExplain(categoryAppDTO.getProdBaseDesc());
			product.setSourceList(sourceList);
			product.setPrice(price);

			listVO.add(product);
		}

		responseVO = new FeatureCategoryAppRes();
		commonResponse = new CommonResponse();
		commonResponse.setTotalCount(totalCount);

		responseVO.setCommonResponse(commonResponse);
		responseVO.setProductList(listVO);
		return responseVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.product.service.TotalRecommendService#searchTotalRecommendList(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public FeatureCategoryAppRes searchMenuAppList(FeatureCategoryAppReq requestVO, SacRequestHeader header) {
		// TODO Auto-generated method stub
		// 공통 응답 변수 선언
		FeatureCategoryAppRes responseVO = new FeatureCategoryAppRes();
		CommonResponse commonResponse = new CommonResponse();

		// 헤더값 세팅
		requestVO.setTenantId(header.getTenantHeader().getTenantId());
		requestVO.setDeviceModelCd(header.getDeviceHeader().getModel());
		requestVO.setLangCd(header.getTenantHeader().getLangCd());

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(requestVO.getTenantId()) || StringUtils.isEmpty(requestVO.getListId())) {
			this.log.debug("----------------------------------------------------------------");
			this.log.debug("필수 파라미터 부족");
			this.log.debug("----------------------------------------------------------------");

			responseVO = new FeatureCategoryAppRes();
			responseVO.setCommonResponse(new CommonResponse());
			return responseVO;
		}

		// 시작점 ROW Default 세팅
		if (requestVO.getOffset() == 0) {
			requestVO.setOffset(1);
		}
		// 페이지당 노출될 ROW 개수 Default 세팅
		if (requestVO.getCount() == 0) {
			requestVO.setCount(20);
		}

		// 소셜게임 menuId 세팅
		if ("ADM000000005".equals(requestVO.getListId())) {
			requestVO.setMenuId("DP01");
		}

		// 배치완료 기준일시 조회
		String stdDt = this.displayCommonService.getBatchStandardDateString(requestVO.getTenantId(),
				requestVO.getListId());

		// 기준일시 체크
		if (StringUtils.isEmpty(stdDt)) {
			this.log.debug("----------------------------------------------------------------");
			this.log.debug("배치완료 기준일시 정보 누락");
			this.log.debug("----------------------------------------------------------------");

			responseVO = new FeatureCategoryAppRes();
			responseVO.setCommonResponse(new CommonResponse());
			return responseVO;
		}
		requestVO.setStdDt(stdDt);

		// prodGradeCd encode 처리(테넌트에서 인코딩하여 넘길 시 제거 필요)
		if (!StringUtils.isEmpty(requestVO.getProdGradeCd())) {
			try {
				requestVO.setProdGradeCd(URLEncoder.encode(requestVO.getProdGradeCd(), "UTF-8"));
			} catch (Exception ex) {
				throw new StorePlatformException("EX_ERR_CD_9999", ex); // 코드 확인 후 변경 필요
			}

			// prodGradeCd 배열로 변경
			String[] prodGradeCdArr = requestVO.getProdGradeCd().split("\\+");
			requestVO.setProdGradeCdArr(prodGradeCdArr);
		}

		List<ProductBasicInfo> productBasicInfoList;

		// 메인의 최신일 경우 BP별로 2개씩만 노출되는 정책 적용
		if ("ADM000000001".equals(requestVO.getListId()) && StringUtil.nvl(requestVO.getMenuId(), "").length() == 4) {
			productBasicInfoList = this.commonDAO.queryForList("FeatureCategory.selectTopMenuAppListByRecent",
					requestVO, ProductBasicInfo.class);
		} else {
			if (StringUtil.nvl(requestVO.getMenuId(), "").length() == 4) {
				productBasicInfoList = this.commonDAO.queryForList("FeatureCategory.selectTopMenuAppList", requestVO,
						ProductBasicInfo.class);
			} else {
				productBasicInfoList = this.commonDAO.queryForList("FeatureCategory.selectSubMenuAppList", requestVO,
						ProductBasicInfo.class);
			}
		}

		List<Product> productList = new ArrayList<Product>();

		// Meta DB 조회 파라미터 생성
		Map<String, Object> reqMap = new HashMap<String, Object>();
		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		reqMap.put("req", requestVO);
		reqMap.put("tenantHeader", tenantHeader);
		reqMap.put("deviceHeader", deviceHeader);
		reqMap.put("stdDt", stdDt);
		reqMap.put("lang", tenantHeader.getLangCd());

		reqMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
		reqMap.put("svcGrpCd", DisplayConstants.DP_APP_PROD_SVC_GRP_CD);
		reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);

		if (productBasicInfoList != null && productBasicInfoList.size() > 0) {
			for (ProductBasicInfo productBasicInfo : productBasicInfoList) {
				reqMap.put("productBasicInfo", productBasicInfo);

				// App Meta 정보 조회
				MetaInfo retMetaInfo = this.metaInfoService.getAppMetaInfo(reqMap);

				if (retMetaInfo != null) {
					// App Response Generate
					Product product = this.responseInfoGenerateFacade.generateAppProduct(retMetaInfo);
					productList.add(product);
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
