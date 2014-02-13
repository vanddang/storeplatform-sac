/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
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
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.best.BestDownloadSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.best.BestDownloadSacRes;
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
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 20. Updated by : 이석희, SK 플래닛.
 */

@Service
@Transactional
public class BestDownloadServiceImpl implements BestDownloadService {

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.BestDownloadService#BestDownloadService(com.skplanet
	 * .storeplatform.sac.client.product.vo.BestDownloadRequestVO)
	 */
	@Override
	public BestDownloadSacRes searchBestDownloadList(SacRequestHeader requestheader, BestDownloadSacReq bestDownloadReq) {
		TenantHeader tenantHeader = requestheader.getTenantHeader();
		DeviceHeader deviceHeader = requestheader.getDeviceHeader();

		this.log.debug("########################################################");
		this.log.debug("tenantHeader.getTenantId()	:	" + tenantHeader.getTenantId());
		this.log.debug("tenantHeader.getLangCd()	:	" + tenantHeader.getLangCd());
		this.log.debug("deviceHeader.getModel()		:	" + deviceHeader.getModel());
		this.log.debug("########################################################");

		bestDownloadReq.setTenantId(tenantHeader.getTenantId());
		bestDownloadReq.setLangCd(tenantHeader.getLangCd());
		bestDownloadReq.setDeviceModelCd(deviceHeader.getModel());

		BestDownloadSacRes response = new BestDownloadSacRes();

		CommonResponse commonResponse = new CommonResponse();
		List<Product> productList = new ArrayList<Product>();

		String listId = bestDownloadReq.getListId();
		String filteredBy = bestDownloadReq.getFilteredBy();

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(listId)) {
			throw new StorePlatformException("SAC_DSP_0002", "listId", listId);
		}

		// 파라미터 유효값 체크
		if (StringUtils.isNotEmpty(bestDownloadReq.getProdGradeCd())) {
			String[] arrayProdGradeCd = bestDownloadReq.getProdGradeCd().split("\\+");
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
		if (StringUtils.isNotEmpty(filteredBy)) {
			if (!"ebook+normal".equals(filteredBy) && !"ebook+genre".equals(filteredBy)) {
				throw new StorePlatformException("SAC_DSP_0003", "filteredBy", filteredBy);
			}
		}

		int offset = 1; // default
		int count = 20; // default

		if (bestDownloadReq.getOffset() != null) {
			offset = bestDownloadReq.getOffset();
		}
		bestDownloadReq.setOffset(offset); // set offset

		if (bestDownloadReq.getCount() != null) {
			count = bestDownloadReq.getCount();
		}
		count = offset + count - 1;
		bestDownloadReq.setCount(count); // set count

		String stdDt = this.commonService.getBatchStandardDateString(bestDownloadReq.getTenantId(),
				bestDownloadReq.getListId());
		bestDownloadReq.setStdDt(stdDt);

		// '+'로 연결 된 상품등급코드를 배열로 전달
		if (StringUtils.isNotEmpty(bestDownloadReq.getProdGradeCd())) {
			String[] arrayProdGradeCd = bestDownloadReq.getProdGradeCd().split("\\+");
			bestDownloadReq.setArrayProdGradeCd(arrayProdGradeCd);
		}

		// BEST 다운로드 상품 조회
		List<ProductBasicInfo> bestList = null;

		if (bestDownloadReq.getDummy() == null) { // dummy 호출이 아닐때
			if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(bestDownloadReq.getTopMenuId())
					|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(bestDownloadReq.getTopMenuId())
					|| DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(bestDownloadReq.getTopMenuId())
					|| DisplayConstants.DP_TV_TOP_MENU_ID.equals(bestDownloadReq.getTopMenuId())) { // 멀티미디어_상품
				bestList = this.commonDAO.queryForList("BestDownload.selectBestDownloadMMList", bestDownloadReq,
						ProductBasicInfo.class);

				if (!bestList.isEmpty()) {
					Map<String, Object> reqMap = new HashMap<String, Object>();
					reqMap.put("tenantHeader", tenantHeader);
					reqMap.put("deviceHeader", deviceHeader);
					reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
					for (ProductBasicInfo productBasicInfo : bestList) {
						reqMap.put("productBasicInfo", productBasicInfo);
						MetaInfo retMetaInfo = null;
						if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(bestDownloadReq.getTopMenuId())
								|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(bestDownloadReq.getTopMenuId())) {
							// 이북, 코믹
							reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
							retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap);
						} else {
							// 영화, 방송
							reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
							retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap);
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
					commonResponse.setTotalCount(bestList.get(0).getTotalCount());
					response.setProductList(productList);
					response.setCommonResponse(commonResponse);
				} else {
					// 조회 결과 없음
					commonResponse.setTotalCount(0);
					response.setProductList(productList);
					response.setCommonResponse(commonResponse);
				}

			} else { // App 상품
				bestList = this.commonDAO.queryForList("BestDownload.selectBestDownloadAppList", bestDownloadReq,
						ProductBasicInfo.class);

				if (!bestList.isEmpty()) {
					Map<String, Object> reqMap = new HashMap<String, Object>();
					reqMap.put("tenantHeader", tenantHeader);
					reqMap.put("deviceHeader", deviceHeader);
					reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
					for (ProductBasicInfo productBasicInfo : bestList) {
						reqMap.put("productBasicInfo", productBasicInfo);
						reqMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
						MetaInfo retMetaInfo = this.metaInfoService.getAppMetaInfo(reqMap);

						if (retMetaInfo != null) {
							Product product = this.responseInfoGenerateFacade.generateAppProduct(retMetaInfo);
							productList.add(product);
						}
					}
					commonResponse.setTotalCount(bestList.get(0).getTotalCount());
					response.setProductList(productList);
					response.setCommonResponse(commonResponse);
				} else {
					// 조회 결과 없음
					commonResponse.setTotalCount(0);
					response.setProductList(productList);
					response.setCommonResponse(commonResponse);
				}
			}

		} else {
			// dummy data를 호출할때
			for (int i = 1; i <= 1; i++) {
				Product product = new Product();
				Identifier identifier = new Identifier();
				App app = new App();
				Accrual accrual = new Accrual();
				Rights rights = new Rights();
				Source source = new Source();
				Price price = new Price();
				Title title = new Title();
				Support support = new Support();
				Menu menu = new Menu();

				// 상품ID
				List<Identifier> identifierList = new ArrayList<Identifier>();
				identifier = new Identifier();
				identifier.setType("episode");
				identifier.setText("0000643818");
				identifierList.add(identifier);

				List<Support> supportList = new ArrayList<Support>();
				support.setType("drm");
				support.setText("N");
				supportList.add(support);
				support = new Support();
				support.setType("iab");
				support.setText("N");
				supportList.add(support);

				/*
				 * Menu(메뉴정보) Id, Name, Type
				 */
				List<Menu> menuList = new ArrayList<Menu>();
				menu.setId("DP01");
				menu.setName("게임");
				menu.setType("topClass");
				menuList.add(menu);
				menu = new Menu();
				menu.setId("DP01004");
				menu.setName("RPG");
				menuList.add(menu);

				/*
				 * App aid, packagename, versioncode, version 상품이 앱일 경우 데이터 존재 앱이 아닐 경우 없음
				 */
				app.setAid("OA00643818");
				app.setPackageName("proj.syjt.tstore");
				app.setVersionCode("11000");
				app.setVersion("1.1");

				/*
				 * Accrual voterCount (참여자수) DownloadCount (다운로드 수) score(평점)
				 */
				accrual.setVoterCount(14305);
				accrual.setDownloadCount(513434);
				accrual.setScore(4.8);

				/*
				 * Rights grade
				 */
				rights.setGrade("0");

				title.setText("워밸리 온라인");

				/*
				 * source mediaType, size, type, url
				 */
				List<Source> sourceList = new ArrayList<Source>();
				source.setMediaType("image/png");
				source.setSize(1234);
				source.setType("thumbnail");
				source.setUrl("/android6/201311/22/IF1423067129420100319114239/0000643818/img/thumbnail/0000643818_130_130_0_91_20131122120310.PNG");
				sourceList.add(source);

				/*
				 * Price text
				 */
				price.setText(0);

				product = new Product();
				product.setIdentifierList(identifierList);
				product.setSupportList(supportList);
				product.setMenuList(menuList);
				product.setApp(app);
				product.setAccrual(accrual);
				product.setRights(rights);
				product.setTitle(title);
				product.setSourceList(sourceList);
				product.setProductExplain("★이벤트★세상에 없던 모바일 MMORPG!");
				product.setPrice(price);

				productList.add(product);

				commonResponse.setTotalCount(10);
			}
		}

		response.setCommonResponse(commonResponse);
		response.setProductList(productList);

		return response;
	}
}
