/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.category.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.api.conts.DisplayConstants;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryAppReq;
import com.skplanet.storeplatform.sac.client.display.vo.category.CategoryAppRes;
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
import com.skplanet.storeplatform.sac.display.category.vo.CategoryAppDTO;

/**
 * CategoryApp Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 11. 28. Updated by : 이태희, SK 플래닛.
 */
@Service
@Transactional
public class CategoryAppServiceImpl implements CategoryAppService {
	private transient Logger logger = LoggerFactory.getLogger(CategoryAppServiceImpl.class);

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.category.service.CategoryAppService#searchCategoryAppList(com.skplanet
	 * .storeplatform.sac.client.display.vo.category.CategoryAppReq)
	 */
	@Override
	public CategoryAppRes searchAppList(CategoryAppReq req, SacRequestHeader header) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchAppList Service started!!");
		this.logger.debug("----------------------------------------------------------------");

		CategoryAppRes appRes = new CategoryAppRes();
		CommonResponse commonRes = new CommonResponse();

		String prodCharge = req.getProdCharge();
		String prodGradeCd = req.getProdGradeCd();
		String menuId = req.getMenuId();
		String orderedBy = req.getOrderedBy();

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(prodCharge) || StringUtils.isEmpty(menuId) || StringUtils.isEmpty(orderedBy)) {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("필수 파라미터 부족");
			this.logger.debug("----------------------------------------------------------------");

			appRes.setCommonResponse(commonRes);
			return appRes;
		}
		// 상품등급코드 유효값 체크
		if (StringUtils.isNotEmpty(prodGradeCd)) {
			if (!"PD004401".equals(prodGradeCd) && !"PD004402".equals(prodGradeCd) && !"PD004403".equals(prodGradeCd)) {
				this.logger.debug("----------------------------------------------------------------");
				this.logger.debug("유효하지않은 상품 등급 코드");
				this.logger.debug("----------------------------------------------------------------");

				appRes.setCommonResponse(commonRes);
				return appRes;
			}
		}
		// 상품정렬순서 유효값 체크
		if (!"download".equals(orderedBy)) {
			this.logger.debug("----------------------------------------------------------------");
			this.logger.debug("유효하지않은 상품 정렬 순서");
			this.logger.debug("----------------------------------------------------------------");

			appRes.setCommonResponse(commonRes);
			return appRes;
		}
		// 상품 유무료 구분 Default 세팅
		if (!"A".equals(prodCharge) && (!"Y".equals(prodCharge) && !"N".equals(prodCharge))) {
			prodCharge = "A";
		}
		// 시작점 ROW Default 세팅
		if (req.getOffset() == null) {
			req.setOffset(1);
		}
		// 페이지당 노출될 ROW 개수 Default 세팅
		if (req.getCount() == null) {
			req.setCount(20);
		}

		// 헤더값 세팅
		req.setDeviceModelCd(header.getDeviceHeader().getModel());
		req.setTenantId(header.getTenantHeader().getTenantId());
		req.setImageCd("DP000101");

		// 일반 카테고리 앱 상품 조회
		List<CategoryAppDTO> appList = this.commonDAO.queryForList("Category.selectCategoryAppList", req,
				CategoryAppDTO.class);

		if (!appList.isEmpty()) {
			CategoryAppDTO categoryAppDTO = null;

			Identifier identifier = null;
			Support support = null;
			Menu menu = null;
			App app = null;
			Accrual accrual = null;
			Rights rights = null;
			Title title = null;
			Source source = null;
			Price price = null;
			Product product = null;

			List<Menu> menuList = null;
			List<Source> sourceList = null;
			List<Support> supportList = null;
			List<Product> productList = new ArrayList<Product>();

			for (int i = 0; i < appList.size(); i++) {
				product = new Product();
				categoryAppDTO = appList.get(i);

				// 상품 정보 (상품ID)
				identifier = new Identifier();
				identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
				identifier.setText(categoryAppDTO.getProdId());
				product.setIdentifier(identifier);

				// 상품 지원 정보
				support = new Support();
				supportList = new ArrayList<Support>();
				support.setType("drm");
				support.setText(categoryAppDTO.getDrmYn());
				supportList.add(support);

				support = new Support();
				support.setType("inApp");
				support.setText(categoryAppDTO.getPartParentClsfCd());
				supportList.add(support);
				product.setSupportList(supportList);

				// 메뉴 정보
				menu = new Menu();
				menuList = new ArrayList<Menu>();
				menu.setType("topClass");
				menu.setId(categoryAppDTO.getTopMenuId());
				menu.setName(categoryAppDTO.getTopMenuNm());
				menuList.add(menu);

				menu = new Menu();
				menu.setId(categoryAppDTO.getMenuId());
				menu.setName(categoryAppDTO.getMenuNm());
				menuList.add(menu);
				product.setMenuList(menuList);

				// 어플리케이션 정보
				app = new App();
				app.setAid(categoryAppDTO.getAid());
				app.setPackageName(categoryAppDTO.getApkPkgNm());
				app.setSize(categoryAppDTO.getApkFileSize());
				app.setVersion(categoryAppDTO.getProdVer());
				app.setVersionCode(categoryAppDTO.getApkVer());
				product.setApp(app);

				// 평점 정보
				accrual = new Accrual();
				accrual.setDownloadCount(categoryAppDTO.getPrchsCnt());
				accrual.setScore(categoryAppDTO.getAvgEvluScore());
				accrual.setVoterCount(categoryAppDTO.getPaticpersCnt());
				product.setAccrual(accrual);

				// 이용권한 정보
				rights = new Rights();
				rights.setGrade(categoryAppDTO.getProdGrdCd());
				product.setRights(rights);

				// 상품 정보 (상품명)
				title = new Title();
				title.setText(categoryAppDTO.getProdNm());
				product.setTitle(title);

				// 이미지 정보
				source = new Source();
				sourceList = new ArrayList<Source>();
				source.setType("thumbnail");
				source.setMediaType("image/png");
				source.setUrl(categoryAppDTO.getImgPath());
				sourceList.add(source);
				product.setSourceList(sourceList);

				// 상품 정보 (상품설명)
				product.setProductExplain(categoryAppDTO.getProdBaseDesc());

				// 상품 정보 (상품가격)
				price = new Price();
				price.setText(Integer.parseInt(categoryAppDTO.getProdAmt()));
				product.setPrice(price);

				// 데이터 매핑
				productList.add(i, product);
			}

			commonRes.setTotalCount(categoryAppDTO.getTotalCount());
			appRes.setProductList(productList);
			appRes.setCommonResponse(commonRes);
		} else {
			// 조회 결과 없음
			appRes.setCommonResponse(commonRes);
		}

		return appRes;
	}
}
