/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.recommend.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendAdminReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.recommend.RecommendAdminRes;
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
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.feature.FeatureConstant;
import com.skplanet.storeplatform.sac.display.feature.recommend.vo.RecommendAdminDTO;

/**
 * 
 * 
 * Updated on : 2013. 12. 19. Updated by : 서영배, GTSOFT.
 */
@org.springframework.stereotype.Service
@Transactional
public class RecommendAdminServiceImpl implements RecommendAdminService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService displayCommonService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.product.service.TotalRecommendService#searchTotalRecommendList(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public RecommendAdminRes searchAdminList(RecommendAdminReq requestVO, SacRequestHeader header) {
		// TODO Auto-generated method stub

		// 공통 응답 변수 선언
		int totalCount = 0;
		RecommendAdminRes responseVO = null;
		CommonResponse commonResponse = null;
		List<RecommendAdminDTO> resultList = null;
		List<Product> listVO = new ArrayList<Product>();

		// 헤더값 세팅
		requestVO.setTenantId(header.getTenantHeader().getTenantId());
		requestVO.setDeviceModelCd(header.getDeviceHeader().getModel());
		requestVO.setLangCd(header.getTenantHeader().getLangCd());

		if (StringUtils.isEmpty(requestVO.getTenantId()))
			requestVO.setTenantId("S01");
		if (StringUtils.isEmpty(requestVO.getDeviceModelCd()))
			requestVO.setDeviceModelCd("SHW-M180L");
		if (StringUtils.isEmpty(requestVO.getListId()))
			requestVO.setListId("ADM000000013");
		if (StringUtils.isEmpty(requestVO.getTopMenuId()))
			requestVO.setTopMenuId(StringUtil.nvl(requestVO.getTopMenuId(), "DP01"));

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(requestVO.getTenantId()) || StringUtils.isEmpty(requestVO.getListId())
				|| StringUtils.isEmpty(requestVO.getTopMenuId())) {
			this.log.debug("----------------------------------------------------------------");
			this.log.debug("필수 파라미터 부족");
			this.log.debug("----------------------------------------------------------------");

			responseVO = new RecommendAdminRes();
			responseVO.setCommonResponse(new CommonResponse());
			return responseVO;
		}

		// 리스트ID 유효값 체크
		if (!"ADM000000013".equals(requestVO.getListId())) {
			this.log.debug("----------------------------------------------------------------");
			this.log.debug("유효하지않은 리스트ID");
			this.log.debug("----------------------------------------------------------------");

			responseVO = new RecommendAdminRes();
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

		// 배치완료 기준일시 조회
		String stdDt = this.displayCommonService.getBatchStandardDateString(requestVO.getTenantId(),
				requestVO.getListId());

		// 기준일시 체크
		if (StringUtils.isEmpty(stdDt)) {
			this.log.debug("----------------------------------------------------------------");
			this.log.debug("배치완료 기준일시 정보 누락");
			this.log.debug("----------------------------------------------------------------");

			responseVO = new RecommendAdminRes();
			responseVO.setCommonResponse(new CommonResponse());
			return responseVO;
		}
		requestVO.setStdDt(stdDt);

		// topMenuId 배열로 변경
		String[] topMenuIdArr = requestVO.getTopMenuId().split(","); // 구분자 확인 필요 + 에러
		requestVO.setTopMenuIdArr(topMenuIdArr);

		resultList = this.commonDAO.queryForList("FeatureRecommend.selectRecommendAdminList", requestVO,
				RecommendAdminDTO.class);

		RecommendAdminDTO recommendAdminDTO;
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
		List<Product> productList = new ArrayList<Product>();
		List<Menu> menuList;
		List<Source> sourceList;
		List<Support> supportList;
		List<Identifier> identifierList;

		for (int i = 0; resultList != null && i < resultList.size(); i++) {

			recommendAdminDTO = resultList.get(i);
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

			totalCount = recommendAdminDTO.getTotalCount();

			identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
			identifier.setText(recommendAdminDTO.getProdId());
			title.setText(recommendAdminDTO.getProdNm());

			menu = new Menu();
			menu.setId(recommendAdminDTO.getTopMenuId());
			menu.setName(recommendAdminDTO.getTopMenuNm());
			menu.setType("topClass");
			menuList.add(menu);
			menu = new Menu();
			menu.setId(recommendAdminDTO.getMenuId());
			menu.setName(recommendAdminDTO.getMenuNm());
			// menu.setType("");
			menuList.add(menu);

			app.setAid(recommendAdminDTO.getAid());
			app.setPackageName(recommendAdminDTO.getApkPkgNm());
			app.setVersionCode(recommendAdminDTO.getApkVer());
			// app.setVersion(recommendAdminDTO.getProdVer());
			app.setVersion(FeatureConstant.convertProdVer(recommendAdminDTO.getVerMajor(),
					recommendAdminDTO.getVerMinor()));
			product.setApp(app);

			accrual.setVoterCount(recommendAdminDTO.getPrchsCnt());
			accrual.setDownloadCount(recommendAdminDTO.getDwldCnt());
			accrual.setScore(recommendAdminDTO.getAvgEvluScore());
			// accrual.setScore(3.3);

			/*
			 * Rights grade
			 */
			// rights.setGrade(recommendAdminDTO.getProdGrdCd());
			rights.setGrade(FeatureConstant.convertProdGrdCd(recommendAdminDTO.getProdGrdCd()));

			// source.setMediaType("");
			source.setSize(recommendAdminDTO.getFileSize());
			source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
			source.setUrl(recommendAdminDTO.getFilePath());
			sourceList.add(source);

			/*
			 * Price text
			 */
			price.setText(recommendAdminDTO.getProdAmt());

			//identifier Lisst형으로 수정
			//product.setIdentifier(identifier);
			identifierList.add(identifier);
			product.setIdentifierList(identifierList);
			
			product.setTitle(title);
			// support.setText(StringUtil.nvl(recommendAdminDTO.getDrmYn(), "") + "|" +
			// StringUtil.nvl(recommendAdminDTO.getPartParentClsfCd(), ""));

			if ("PD012301".equals(recommendAdminDTO.getPartParentClsfCd())) {
				support = new Support();
				support.setType("iab");
				support.setText("Y");
				supportList.add(support);
			}
			if ("Y".equals(recommendAdminDTO.getDrmYn())) {
				support = new Support();
				support.setType("drm");
				support.setText("Y");
				supportList.add(support);
			}

			product.setSupportList(supportList);
			product.setMenuList(menuList);

			product.setAccrual(accrual);
			product.setRights(rights);
			product.setProductExplain(recommendAdminDTO.getProdBaseDesc());
			product.setSourceList(sourceList);
			product.setPrice(price);

			listVO.add(product);
		}

		responseVO = new RecommendAdminRes();
		commonResponse = new CommonResponse();
		commonResponse.setTotalCount(totalCount);

		responseVO.setCommonResponse(commonResponse);
		responseVO.setProductList(listVO);
		return responseVO;
	}

}
