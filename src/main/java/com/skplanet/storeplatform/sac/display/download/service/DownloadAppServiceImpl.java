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
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceList;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceResponse;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadAppSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadAppSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.download.vo.DownloadApp;
import com.skplanet.storeplatform.sac.purchase.history.service.ExistenceService;

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
	ExistenceService existenceService;

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

		String[] temp = deviceHeader.getOsVersion().trim().split("/");
		String osVersion = temp[1];
		downloadAppSacReq.setTenantId(tanantHeader.getTenantId());
		downloadAppSacReq.setDeviceModelCd(deviceHeader.getModel());
		downloadAppSacReq.setLangCd(tanantHeader.getLangCd());
		downloadAppSacReq.setOsVersion(osVersion);
		downloadAppSacReq.setImgCd(DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);

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
		List<Support> supportList = null;
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

		if (downloadAppSacReq.getDummy() == null) {

			// dummy 호출이 아닐때
			DownloadApp downloadAppInfo = null;
			// 필수 파라미터 체크
			if (StringUtils.isEmpty(filteredBy) || StringUtils.isEmpty(deviceKey) || StringUtils.isEmpty(userKey)) {
				// throw new StorePlatformException("ERROR_0001", "1", "2", "3");
				throw new StorePlatformException("필수 파라미터가 부족합니다.");
			}

			// 조회 유효값 체크
			if (!"package".equals(filteredBy) && !"id".equals(filteredBy)) {
				throw new StorePlatformException("유효하지않은 조회유형 입니다.", "1", "2", "3");
			}

			if ("package".equals(filteredBy)) {
				if (StringUtil.isEmpty(packageName)) {
					throw new StorePlatformException("필수 파라미터가 부족합니다.");
				}

				productId = (String) this.commonDAO.queryForObject("Download.getProductIdForPackageName",
						downloadAppSacReq);
				downloadAppSacReq.setProductId(productId);

				if (StringUtil.isEmpty(productId)) {
					throw new StorePlatformException("해당 패키지 명의 상품이 존재하지 않습니다.");
				}
			} else {
				if (StringUtil.isEmpty(productId)) {
					throw new StorePlatformException("상품 ID는 필수 파라미터 입니다.");
				}
			}

			try {

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

					String prchsId = null;

					try {
						// 기구매 체크를 위한 생성자
						ExistenceRequest existenceRequest = new ExistenceRequest();
						existenceRequest.setTenantId(downloadAppSacReq.getTenantId());
						existenceRequest.setInsdUsermbrNo(downloadAppSacReq.getUserKey());
						existenceRequest.setInsdDeviceId(downloadAppSacReq.getDeviceKey());
						existenceRequest.setPrchsId("M1040449015718184793");

						ExistenceList existenceList = new ExistenceList();
						// existenceList.setProdId(downloadAppSacReq.getProductId());
						existenceList.setProdId("H900000037"); // 기구매 테스트용 상품 ID

						List<ExistenceList> list = new ArrayList<ExistenceList>();
						list.add(existenceList);
						existenceRequest.setExistenceList(list);

						// 기구매 체크 실행
						List<ExistenceResponse> existenceResponseList = this.existenceService
								.listExist(existenceRequest);

						if (!existenceResponseList.isEmpty()) {
							this.log.debug("----------------------------------------------------------------");
							this.log.debug("구매 상품");
							this.log.debug("----------------------------------------------------------------");

							prchsId = existenceResponseList.get(0).getPrchsId();
						} else {
							this.log.debug("----------------------------------------------------------------");
							this.log.debug("미구매 상품");
							this.log.debug("----------------------------------------------------------------");
						}
					} catch (Exception e) {
						throw new StorePlatformException("ERROR_0001", "1", "2", "3");
					}

					identifier = new Identifier();
					identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
					if (StringUtil.isEmpty(downloadAppInfo.getSeedProductId())) {
						identifier.setText("");
					} else {
						identifier.setText(downloadAppInfo.getSeedProductId());
					}

					component.setIdentifier(identifier);

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
					// identifier = new Identifier();
					// identifier.setType("isPartOf");
					// identifier.setText(downloadAppInfo.getProdId());
					// identifierList.add(identifier);

					title.setText(downloadAppInfo.getProdNm());

					/*
					 * source mediaType, size, type, url
					 */
					source.setMediaType(DisplayCommonUtil.getMimeType(downloadAppInfo.getImgPath()));
					source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL);
					source.setUrl(downloadAppInfo.getImgPath());
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
					distributor.setRegNo(downloadAppInfo.getSellerMbrNo());

					identifier = new Identifier();
					identifier.setType(DisplayConstants.DP_PURCHASE_IDENTIFIER_CD);
					identifier.setText(prchsId);
					purchase.setIdentifier(identifier);
					purchase.setPurchaseFlag(StringUtils.isNotEmpty(prchsId) ? "payment" : "nonPayment");
					product.setPurchase(purchase);
				}
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

				commonResponse.setTotalCount(1);
			} catch (Exception ex) {
				throw new StorePlatformException("ERROR_0001", ex);
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
			identifier.setType("episode");
			identifier.setText("0000395599");
			component.setIdentifier(identifier);
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

			identifier = new Identifier();
			identifier.setType("purchase");
			identifier.setText("GI100000000265812187");
			purchase.setIdentifier(identifier);
			purchase.setPurchaseFlag("payment");

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

			commonResponse.setTotalCount(1);
		}
		response.setCommonResponse(commonResponse);
		response.setComponent(component);
		response.setProduct(product);
		return response;
	}
}
