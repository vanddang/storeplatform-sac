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
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.category.vo.CategoryApp;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;

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

		TenantHeader tenantHeader = header.getTenantHeader();
		DeviceHeader deviceHeader = header.getDeviceHeader();

		this.logger.debug("########################################################");
		this.logger.debug("tenantHeader.getTenantId()	:	" + tenantHeader.getTenantId());
		this.logger.debug("tenantHeader.getLangCd()		:	" + tenantHeader.getLangCd());
		this.logger.debug("deviceHeader.getModel()		:	" + deviceHeader.getModel());
		this.logger.debug("########################################################");

		req.setTenantId(tenantHeader.getTenantId());
		req.setLangCd(tenantHeader.getLangCd());
		req.setDeviceModelCd(deviceHeader.getModel());

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

		int offset = 1; // default
		int count = 20; // default

		if (req.getOffset() != null) {
			offset = req.getOffset();
		}
		req.setOffset(offset);

		if (req.getCount() != null) {
			count = req.getCount();
		}
		count = offset + count - 1;
		req.setCount(count);

		// 이미지 사이즈
		req.setImageCd("DP000101");

		// 일반 카테고리 앱 상품 조회
		List<CategoryApp> appList = this.commonDAO.queryForList("Category.selectCategoryAppList", req,
				CategoryApp.class);

		if (!appList.isEmpty()) {
			CategoryApp categoryApp = null;

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

			List<Identifier> identifierList = null;
			List<Menu> menuList = null;
			List<Source> sourceList = null;
			List<Support> supportList = null;
			List<Product> productList = new ArrayList<Product>();

			for (int i = 0; i < appList.size(); i++) {
				product = new Product();
				categoryApp = appList.get(i);

				// 상품 정보 (상품ID)
				identifierList = new ArrayList<Identifier>();
				identifier = new Identifier();
				identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
				identifier.setText(categoryApp.getProdId());
				identifierList.add(identifier);
				product.setIdentifierList(identifierList);

				// 상품 지원 정보
				support = new Support();
				supportList = new ArrayList<Support>();
				support.setType(DisplayConstants.DP_DRM_SUPPORT_NM);
				support.setText(categoryApp.getDrmYn());
				supportList.add(support);

				support = new Support();
				support.setType(DisplayConstants.DP_IN_APP_SUPPORT_NM);
				support.setText(categoryApp.getPartParentClsfCd());
				supportList.add(support);
				product.setSupportList(supportList);

				// 메뉴 정보
				menu = new Menu();
				menuList = new ArrayList<Menu>();
				menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
				menu.setId(categoryApp.getTopMenuId());
				menu.setName(categoryApp.getTopMenuNm());
				menuList.add(menu);

				menu = new Menu();
				menu.setId(categoryApp.getMenuId());
				menu.setName(categoryApp.getMenuNm());
				menuList.add(menu);
				product.setMenuList(menuList);

				// 어플리케이션 정보
				app = new App();
				app.setAid(categoryApp.getAid());
				app.setPackageName(categoryApp.getApkPkgNm());
				app.setSize(categoryApp.getApkFileSize());
				app.setVersion(categoryApp.getProdVer());
				app.setVersionCode(categoryApp.getApkVer());
				product.setApp(app);

				// 평점 정보
				accrual = new Accrual();
				accrual.setDownloadCount(categoryApp.getPrchsCnt());
				accrual.setScore(categoryApp.getAvgEvluScore());
				accrual.setVoterCount(categoryApp.getPaticpersCnt());
				product.setAccrual(accrual);

				// 이용권한 정보
				rights = new Rights();
				rights.setGrade(categoryApp.getProdGrdCd());
				product.setRights(rights);

				// 상품 정보 (상품명)
				title = new Title();
				title.setText(categoryApp.getProdNm());
				product.setTitle(title);

				// 이미지 정보
				source = new Source();
				sourceList = new ArrayList<Source>();
				source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
				source.setSize(categoryApp.getImgSize());
				source.setMediaType(DisplayCommonUtil.getMimeType(categoryApp.getImgPath()));
				source.setUrl(categoryApp.getImgPath());
				sourceList.add(source);
				product.setSourceList(sourceList);

				// 상품 정보 (상품설명)
				product.setProductExplain(categoryApp.getProdBaseDesc());

				// 상품 정보 (상품가격)
				price = new Price();
				price.setText(Integer.parseInt(categoryApp.getProdAmt()));
				product.setPrice(price);

				// 데이터 매핑
				productList.add(i, product);
			}

			commonRes.setTotalCount(categoryApp.getTotalCount());
			appRes.setProductList(productList);
			appRes.setCommonResponse(commonRes);
		} else {
			// 조회 결과 없음
			appRes.setCommonResponse(commonRes);
		}

		return appRes;
	}
}
