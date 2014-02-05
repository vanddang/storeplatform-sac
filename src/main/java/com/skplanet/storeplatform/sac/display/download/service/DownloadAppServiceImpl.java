/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.download.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.external.client.shopping.util.StringUtil;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadAppSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadAppSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Component;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Distributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Purchase;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListSacReq;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.HistoryListSacRes;
import com.skplanet.storeplatform.sac.client.purchase.history.vo.ProductListSac;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.download.vo.DownloadApp;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import com.skplanet.storeplatform.sac.purchase.history.service.ExistenceSacService;
import com.skplanet.storeplatform.sac.purchase.history.service.HistoryListService;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 1. 21. Updated by : 이석희, 인크로스.
 */
@Service
@Transactional
public class DownloadAppServiceImpl implements DownloadAppService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	@Autowired
	private DisplayCommonService commonService;

	@Autowired
	ExistenceSacService existenceSacService;

	@Autowired
	HistoryListService historyListService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.DownloadAppService#DownloadAppService(com.skplanet
	 * .storeplatform.sac.client.product.vo.downloadAppSacReqVO)
	 */
	@Override
	public DownloadAppSacRes searchDownloadApp(SacRequestHeader requestheader, DownloadAppSacReq downloadAppSacReq) {
		TenantHeader tanantHeader = requestheader.getTenantHeader();
		DeviceHeader deviceHeader = requestheader.getDeviceHeader();

		this.log.debug("######################################################################");
		this.log.debug("deviceHeader.getResolution()	:	" + deviceHeader.getResolution());
		this.log.debug("deviceHeader.getDpi()	:	" + deviceHeader.getDpi());

		this.log.debug("######################################################################");

		// OS VERSION 가공
		String[] temp = deviceHeader.getOsVersion().trim().split("/");
		String osVersion = temp[1];

		String[] resolutionTemp = deviceHeader.getResolution().trim().split("/");

		// int iDpiEnd = NumberUtils.toInt(map.get("ADD_FIELD1"), 0);
		// int iDpiBegin = NumberUtils.toInt(map.get("ADD_FIELD2"), 0);

		String osVersionOrginal = osVersion;
		String[] osVersionTemp = StringUtils.split(osVersionOrginal, ".");
		if (osVersionTemp.length == 3) {
			osVersion = osVersionTemp[0] + "." + osVersionTemp[1];
		}

		downloadAppSacReq.setTenantId(tanantHeader.getTenantId());
		downloadAppSacReq.setDeviceModelCd(deviceHeader.getModel());
		downloadAppSacReq.setLangCd(tanantHeader.getLangCd());
		downloadAppSacReq.setOsVersion(osVersion);
		downloadAppSacReq.setImageCd(DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);

		DownloadAppSacRes response = new DownloadAppSacRes();
		CommonResponse commonResponse = new CommonResponse();
		int totalCount = 0;
		String filteredBy = downloadAppSacReq.getFilteredBy();
		String productId = downloadAppSacReq.getProductId();
		String deviceKey = downloadAppSacReq.getDeviceKey();
		String userKey = downloadAppSacReq.getUserKey();
		String packageName = downloadAppSacReq.getPackageName();

		List<Identifier> identifierList = null;
		List<Menu> menuList = null;
		List<Source> sourceList = null;

		Product product = null;
		Identifier identifier = null;
		App app = null;
		Rights rights = null;
		Source source = null;
		Title title = null;
		Menu menu = null;
		Purchase purchase = null;
		Distributor distributor = null;
		Component component = null;
		Date date = null;

		if (downloadAppSacReq.getDummy() == null) {

			// dummy 호출이 아닐때
			DownloadApp downloadAppInfo = null;
			// 필수 파라미터 체크
			if (StringUtils.isEmpty(filteredBy)) {
				throw new StorePlatformException("SAC_DSP_0002", "filteredBy", filteredBy);
			}

			if (StringUtils.isEmpty(deviceKey)) {
				throw new StorePlatformException("SAC_DSP_0002", "deviceKey", deviceKey);
			}

			if (StringUtils.isEmpty(userKey)) {
				throw new StorePlatformException("SAC_DSP_0002", "userKey", productId);
			}

			// 조회 유효값 체크
			if (!"package".equals(filteredBy) && !"id".equals(filteredBy)) {
				throw new StorePlatformException("SAC_DSP_0002", "filteredBy", filteredBy);
			}

			// 파라미터 체크
			if ("package".equals(filteredBy)) {
				if (StringUtil.isEmpty(packageName)) {
					throw new StorePlatformException("SAC_DSP_0002", "packageName", packageName);
				}

				productId = (String) this.commonDAO.queryForObject("Download.getProductIdForPackageName",
						downloadAppSacReq);
				downloadAppSacReq.setProductId(productId);

				if (StringUtil.isEmpty(productId)) {
					throw new StorePlatformException("SAC_DSP_0005", packageName);
				}
			} else {
				if (StringUtil.isEmpty(productId)) {
					throw new StorePlatformException("SAC_DSP_0002", "productId", productId);
				}
			}

			// 다운로드 앱 상품 조회
			downloadAppInfo = this.commonDAO.queryForObject("Download.getDownloadAppInfo", downloadAppSacReq,
					DownloadApp.class);

			if (downloadAppInfo != null) {

				menuList = new ArrayList<Menu>();
				sourceList = new ArrayList<Source>();
				identifierList = new ArrayList<Identifier>();

				product = new Product();
				identifier = new Identifier();
				app = new App();
				rights = new Rights();
				source = new Source();
				title = new Title();
				purchase = new Purchase();
				distributor = new Distributor();
				component = new Component(); // Seed App 정보
				date = new Date();

				String prchsId = null;
				String prchsDt = null;
				String prchsState = null;
				String prchsProdId = null;

				try {
					// 구매내역 조회를 위한 생성자
					ProductListSac productListSac = new ProductListSac();
					productListSac.setProdId(downloadAppInfo.getProdId());

					List<ProductListSac> productList = new ArrayList<ProductListSac>();
					productList.add(productListSac);

					HistoryListSacReq historyListSacReq = new HistoryListSacReq();
					historyListSacReq.setTenantId(downloadAppSacReq.getTenantId());
					historyListSacReq.setUserKey(downloadAppSacReq.getUserKey());
					historyListSacReq.setDeviceKey(downloadAppSacReq.getDeviceKey());
					historyListSacReq.setPrchsProdType(PurchaseConstants.PRCHS_PROD_TYPE_OWN);
					historyListSacReq.setStartDt("19000101000000");
					historyListSacReq.setEndDt(downloadAppInfo.getSysDate());
					historyListSacReq.setOffset(1);
					historyListSacReq.setCount(1);
					historyListSacReq.setProductList(productList);

					// 구매내역 조회 실행
					HistoryListSacRes historyListSacRes = this.historyListService.searchHistoryList(historyListSacReq);

					this.log.debug("##################################################################################");
					this.log.debug("[getDownloadAppInfo] purchase count : {}", historyListSacRes.getTotalCnt());
					this.log.debug("##################################################################################");

					if (historyListSacRes.getTotalCnt() > 0) {
						prchsId = historyListSacRes.getHistoryList().get(0).getPrchsId();
						prchsDt = historyListSacRes.getHistoryList().get(0).getPrchsDt();
						prchsState = historyListSacRes.getHistoryList().get(0).getPrchsCaseCd();
						prchsProdId = historyListSacRes.getHistoryList().get(0).getProdId();

						if (PurchaseConstants.PRCHS_CASE_PURCHASE_CD.equals(prchsState)) {
							prchsState = "payment";
						} else if (PurchaseConstants.PRCHS_CASE_GIFT_CD.equals(prchsState)) {
							prchsState = "gift";
						}
					}
				} catch (Exception ex) {
					throw new StorePlatformException("SAC_DSP_0001", "구매내역 조회 ", ex);
				}

				List<Identifier> seedIdentifierList = new ArrayList<Identifier>();
				identifier = new Identifier();
				identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
				if (StringUtil.isEmpty(downloadAppInfo.getSeedProductId())) {
					identifier.setText("");
				} else {
					identifier.setText(downloadAppInfo.getSeedProductId());
				}
				seedIdentifierList.add(identifier);

				identifier = new Identifier();
				identifier.setType(DisplayConstants.DP_GAMECENTER_IDENTIFIER_CD);
				if (StringUtil.isEmpty(downloadAppInfo.getGameCentrId())) {
					identifier.setText("");
				} else {
					identifier.setText(downloadAppInfo.getGameCentrId());
				}

				seedIdentifierList.add(identifier);

				component.setIdentifierList(seedIdentifierList);
				component
						.setGameCenterVerCd(StringUtils.isNotEmpty(downloadAppInfo.getGameCentrVerCd()) ? downloadAppInfo
								.getGameCentrVerCd() : "");

				if (StringUtil.isEmpty(downloadAppInfo.getSeedProductId())) {
					component.setUseYn("");
				} else {
					component.setUseYn(downloadAppInfo.getSeedUseYn());
				}

				if (StringUtil.isEmpty(downloadAppInfo.getSeedCaseRefCd())) {
					component.setCaseRefCd("");
				} else {
					component.setCaseRefCd(downloadAppInfo.getSeedCaseRefCd());
				}

				// 상품ID
				identifier = new Identifier();
				identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
				identifier.setText(downloadAppInfo.getProdId());
				identifierList.add(identifier);
				identifier = new Identifier();
				// identifier = new Identifier();
				// identifier.setType("isPartOf");
				// identifier.setText(downloadAppInfo.getProdId());
				// identifierList.add(identifier);

				title.setText(downloadAppInfo.getProdNm());

				/*
				 * source mediaType, size, type, url
				 */
				source.setMediaType(DisplayCommonUtil.getMimeType(downloadAppInfo.getImagePath()));
				source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL);
				source.setUrl(downloadAppInfo.getImagePath());
				sourceList.add(source);

				/*
				 * Menu(메뉴정보) Id, Name, Type
				 */
				menu = new Menu();
				menu.setId(downloadAppInfo.getTopMenuId());
				menu.setName(downloadAppInfo.getTopMenuNm());
				menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
				menuList.add(menu);
				menu = new Menu();
				menu.setId(downloadAppInfo.getMenuId());
				menu.setName(downloadAppInfo.getMenuNm());
				menuList.add(menu);

				/*
				 * App aid, packagename, versioncode, version
				 */
				app.setSupportedOs(downloadAppInfo.getSupportedOs());
				app.setPackageName(downloadAppInfo.getApkPkgNm());
				app.setVersionCode(downloadAppInfo.getApkVerCd());
				app.setSize(downloadAppInfo.getApkFileSize());
				app.setFilePath(downloadAppInfo.getFilePath());

				/*
				 * Rights grade
				 */
				rights.setGrade(downloadAppInfo.getProdGrdCd());

				distributor.setName(downloadAppInfo.getExpoSellerNm());
				distributor.setTel(downloadAppInfo.getExpoSellerTelNo());
				distributor.setEmail(downloadAppInfo.getExpoSellerEmail());
				distributor.setSellerKey(downloadAppInfo.getSellerMbrNo());

				// 구매 정보
				if (StringUtils.isNotEmpty(prchsId)) {
					purchase.setState(prchsState);
					List<Identifier> purchaseIdentifierList = new ArrayList<Identifier>();

					identifier = new Identifier();
					identifier.setType(DisplayConstants.DP_PURCHASE_IDENTIFIER_CD);
					identifier.setText(prchsId);
					purchaseIdentifierList.add(identifier);

					identifier = new Identifier();
					identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
					identifier.setText(prchsProdId);
					purchaseIdentifierList.add(identifier);

					purchase.setIdentifierList(purchaseIdentifierList);

					date = new Date();
					date.setType("date/purchase");
					date.setText(prchsDt);
					purchase.setDate(date);
					product.setPurchase(purchase);
				}

				// identifier = new Identifier();
				// identifier.setType(DisplayConstants.DP_PURCHASE_IDENTIFIER_CD);
				// identifier.setText(prchsId);
				// purchase.setIdentifier(identifier);
				// purchase.setPurchaseFlag(StringUtils.isNotEmpty(prchsId) ? "payment" : "nonPayment");
				// product.setPurchase(purchase);

				product = new Product();
				product.setIdentifierList(identifierList);
				product.setPacketFee(downloadAppInfo.getProdClsfCd());
				product.setTitle(title);
				product.setSourceList(sourceList);
				product.setMenuList(menuList);
				product.setApp(app);
				product.setRights(rights);
				product.setDistributor(distributor);
				product.setPurchase(purchase);
				product.setPlatClsfCd(downloadAppInfo.getPlatClsfCd());

				commonResponse.setTotalCount(1);
			} else {
				commonResponse.setTotalCount(0);
			}

		} else {
			// dummy data를 호출할때
			menuList = new ArrayList<Menu>();
			sourceList = new ArrayList<Source>();
			identifierList = new ArrayList<Identifier>();

			product = new Product();
			identifier = new Identifier();
			app = new App();
			rights = new Rights();
			source = new Source();
			title = new Title();
			purchase = new Purchase();
			distributor = new Distributor();
			component = new Component(); // Seed App 정보

			identifier = new Identifier();
			List<Identifier> seedIdentifierList = new ArrayList<Identifier>();
			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
			identifier.setText("0000395599");
			seedIdentifierList.add(identifier);

			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_GAMECENTER_IDENTIFIER_CD);
			identifier.setText("0000395599");

			seedIdentifierList.add(identifier);

			component.setIdentifierList(seedIdentifierList);
			component.setIdentifierList(seedIdentifierList);
			component.setGameCenterVerCd("3.1");
			component.setUseYn("Y");
			component.setCaseRefCd("PD013018");

			// 상품ID
			identifier = new Identifier();
			identifier.setType("episode");
			identifier.setText("0000395599");
			identifierList.add(identifier);

			title.setText("아스팔트 8: 에어본");

			/*
			 * source mediaType, size, type, url
			 */
			source.setMediaType("image/png");
			source.setType("thumbnail");
			source.setUrl("http://wap.tstore.co.kr/android6/201401/08/IF142815953620090820155126/0000395599/img/thumbnail/0000395599_130_130_0_91_20140108180415.PNG");
			sourceList.add(source);

			/*
			 * Menu(메뉴정보) Id, Name, Type
			 */
			menu = new Menu();
			menu.setId("DP000501");
			menu.setName("게임");
			menu.setType("topClass");
			menuList.add(menu);
			menu = new Menu();
			menu.setId("DP01006");
			menu.setName("스포츠");
			menuList.add(menu);

			/*
			 * App aid, packagename, versioncode, version
			 */
			app.setSupportedOs("Android 2.3~4.4");
			app.setPackageName("com.gameloft.android.SKTS.GloftA8SK");
			app.setVersionCode("12006");
			app.setSize(14910670);
			app.setFilePath("/data4/android/201207/03/IF102158942020090723111912/0000065195/0000033618/sbinary/0000033618_20120703171604.apk");

			/*
			 * Rights grade
			 */
			rights.setGrade("PD004401");

			distributor.setName("오재환");
			distributor.setTel("07000000000");
			distributor.setEmail("SEO-OperatorInfo@gameloft.com");
			distributor.setRegNo("2009-서울강남-03038");

			purchase.setState("payment");
			List<Identifier> purchaseIdentifierList = new ArrayList<Identifier>();

			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_PURCHASE_IDENTIFIER_CD);
			identifier.setText("MI100000000000044286");
			purchaseIdentifierList.add(identifier);

			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
			identifier.setText("0000395599");
			purchaseIdentifierList.add(identifier);

			purchase.setIdentifierList(purchaseIdentifierList);

			date = new Date();
			date.setType("date/purchase");
			date.setText("20130701165632");
			purchase.setDate(date);

			product.setPurchase(purchase);

			product = new Product();
			product.setIdentifierList(identifierList);
			product.setPacketFee("paid");
			product.setTitle(title);
			product.setSourceList(sourceList);
			product.setMenuList(menuList);
			product.setApp(app);
			product.setRights(rights);
			product.setDistributor(distributor);
			product.setPurchase(purchase);
			product.setPlatClsfCd("PD005606");

			commonResponse.setTotalCount(1);
		}
		response.setCommonResponse(commonResponse);
		response.setComponent(component);
		response.setProduct(product);
		return response;
	}
}
