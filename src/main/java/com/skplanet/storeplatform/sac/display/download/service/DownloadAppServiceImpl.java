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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.external.client.shopping.util.StringUtil;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.framework.test.JacksonMarshallingHelper;
import com.skplanet.storeplatform.framework.test.MarshallingHelper;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadAppSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadAppSacRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.ProductListSacIn;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Component;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Encryption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.EncryptionContents;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.AppInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.EncryptionGenerator;

//import org.apache.commons.lang3.StringUtils;

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
	HistoryInternalSCI historyInternalSCI;
	@Autowired
	private CommonMetaInfoGenerator commonGenerator;
	@Autowired
	private AppInfoGenerator appInfoGenerator;
	@Autowired
	private EncryptionGenerator encryptionGenerator;
	@Autowired
	private DownloadAES128Helper downloadAES128Helper;

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

		MetaInfo downloadSystemDate = this.commonDAO.queryForObject("Download.selectDownloadSystemDate", "",
				MetaInfo.class);

		// Calendar cal = Calendar.getInstance();
		// cal.add(cal.HOUR, 1);
		//
		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		//
		// this.log.debug("##################################################################");
		// this.log.debug("now Time	:	" + dateFormat.format(cal.getTime()));
		// this.log.debug("##################################################################");

		// OS VERSION 가공
		String[] temp = deviceHeader.getOsVersion().trim().split("/");
		String osVersion = temp[1];

		String osVersionOrginal = osVersion;
		String[] osVersionTemp = StringUtils.split(osVersionOrginal, ".");
		if (osVersionTemp.length == 3) {
			osVersion = osVersionTemp[0] + "." + osVersionTemp[1];
		}

		downloadAppSacReq.setTenantId(tanantHeader.getTenantId());
		downloadAppSacReq.setDeviceModelCd(deviceHeader.getModel());
		downloadAppSacReq.setLangCd(tanantHeader.getLangCd());
		downloadAppSacReq.setOsVersion(osVersion); // OS Version
		downloadAppSacReq.setLcdSize(deviceHeader.getResolution()); // LCD SIZE
		downloadAppSacReq.setImageCd(DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);

		DownloadAppSacRes response = new DownloadAppSacRes();
		CommonResponse commonResponse = new CommonResponse();
		String filteredBy = downloadAppSacReq.getFilteredBy();
		String productId = downloadAppSacReq.getProductId();
		String deviceKey = downloadAppSacReq.getDeviceKey();
		String userKey = downloadAppSacReq.getUserKey();
		String packageName = downloadAppSacReq.getPackageName();

		List<Identifier> identifierList = null;

		Product product = null;
		Identifier identifier = null;
		Component component = null;

		if (downloadAppSacReq.getDummy() == null) {

			// dummy 호출이 아닐때
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
			MetaInfo metaInfo = this.commonDAO.queryForObject("Download.getDownloadAppInfo", downloadAppSacReq,
					MetaInfo.class);

			if (metaInfo != null) {
				identifierList = new ArrayList<Identifier>();

				product = new Product();
				identifier = new Identifier();

				String prchsId = null;
				String prchsDt = null;
				String prchsState = null;
				String prchsProdId = null;

				try {
					// 구매내역 조회를 위한 생성자
					ProductListSacIn productListSacIn = new ProductListSacIn();
					List<ProductListSacIn> productList = new ArrayList<ProductListSacIn>();

					productListSacIn.setProdId(metaInfo.getProdId());
					productList.add(productListSacIn);

					HistoryListSacInReq historyListSacReq = new HistoryListSacInReq();
					historyListSacReq.setTenantId(downloadAppSacReq.getTenantId());
					historyListSacReq.setUserKey(downloadAppSacReq.getUserKey());
					historyListSacReq.setDeviceKey(downloadAppSacReq.getDeviceKey());
					historyListSacReq.setPrchsProdType(DisplayConstants.PRCHS_PROD_TYPE_OWN);
					historyListSacReq.setStartDt("19000101000000");
					historyListSacReq.setEndDt(metaInfo.getSysDate());
					historyListSacReq.setOffset(1);
					historyListSacReq.setCount(1);
					historyListSacReq.setProductList(productList);

					// 구매내역 조회 실행
					HistoryListSacInRes historyListSacRes = this.historyInternalSCI
							.searchHistoryList(historyListSacReq);

					if (historyListSacRes.getTotalCnt() > 0) {
						prchsId = historyListSacRes.getHistoryList().get(0).getPrchsId();
						prchsDt = historyListSacRes.getHistoryList().get(0).getPrchsDt();
						prchsState = historyListSacRes.getHistoryList().get(0).getPrchsCaseCd();
						prchsProdId = historyListSacRes.getHistoryList().get(0).getProdId();

						if (DisplayConstants.PRCHS_CASE_PURCHASE_CD.equals(prchsState)) {
							prchsState = "payment";
						} else if (DisplayConstants.PRCHS_CASE_GIFT_CD.equals(prchsState)) {
							prchsState = "gift";
						}
					}
				} catch (Exception ex) {
					throw new StorePlatformException("SAC_DSP_0001", "구매내역 조회 ", ex);
				}

				/************************************************************************************************
				 * Seed App 정보
				 ************************************************************************************************/
				component = new Component();

				component.setIdentifierList(this.appInfoGenerator.generateComponentIdentifierList(metaInfo));
				component.setGameCenterVerCd(StringUtils.isNotEmpty(metaInfo.getGameCentrVerCd()) ? metaInfo
						.getGameCentrVerCd() : "");
				component.setUseYn(metaInfo.getSeedUseYn());
				component.setCaseRefCd(metaInfo.getSeedCaseRefCd());

				/************************************************************************************************
				 * 상품 정보
				 ************************************************************************************************/
				product = new Product();

				identifier = this.commonGenerator.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD,
						metaInfo.getProdId());
				identifierList.add(identifier);
				product.setIdentifierList(identifierList); // 상품 Id
				product.setTitle(this.commonGenerator.generateTitle(metaInfo)); // 상품명
				product.setSourceList(this.commonGenerator.generateSourceList(metaInfo)); // 상품 이미지정보
				product.setMenuList(this.commonGenerator.generateMenuList(metaInfo));// 상품 메뉴정보
				product.setApp(this.appInfoGenerator.generateApp(metaInfo)); // App 상세정보
				product.setRights(this.commonGenerator.generateRights(metaInfo)); // 권한
				product.setDistributor(this.commonGenerator.generateDistributor(metaInfo)); // 판매자 정보

				// 구매 정보
				if (StringUtils.isNotEmpty(prchsId)) {
					metaInfo.setPurchaseId(prchsId);
					metaInfo.setPurchaseDt(prchsDt);
					metaInfo.setPurchaseState(prchsState);
					metaInfo.setPurchaseProdId(prchsProdId);
					product.setPurchase(this.commonGenerator.generatePurchase(metaInfo));

					metaInfo.setExpiredDate(downloadSystemDate.getExpiredDate());
					// metaInfo.setDwldExprDt(dwldExprDt);
					metaInfo.setUserKey(downloadAppSacReq.getUserKey());
					metaInfo.setDeviceKey(downloadAppSacReq.getDeviceKey());
					metaInfo.setDeviceType("");
					metaInfo.setDeviceSubKey("");

					// 암호화 정보
					EncryptionContents contents = this.encryptionGenerator.generateEncryptionContents(metaInfo);

					// JSON 파싱
					MarshallingHelper marshaller = new JacksonMarshallingHelper();
					byte[] jsonData = marshaller.marshal(contents);

					// JSON 암호화
					byte[] encryptByte = this.downloadAES128Helper.encryption(jsonData);
					String encryptString = this.downloadAES128Helper.toHexString(encryptByte);

					Encryption encryption = new Encryption();
					encryption.setDigest(DisplayConstants.DP_FORDOWNLOAD_ENCRYPT_DIGEST);
					encryption.setKeyIndex(String.valueOf(this.downloadAES128Helper.getSAC_RANDOM_NUMBER()));
					encryption.setToken(encryptString);
					product.setEncryption(encryption);

					// // JSON 복호화
					// byte[] decryptString = this.downloadAES128Helper.convertBytes(encryptString);
					// byte[] decrypt = this.downloadAES128Helper.decryption(decryptString);
					//
					// try {
					// String decData = new String(decrypt, "UTF-8");
					// this.log.debug("----------------------------------------------------------------");
					// this.log.debug("[getDownloadEbookInfo] decData : {}", decData);
					// this.log.debug("----------------------------------------------------------------");
					// } catch (UnsupportedEncodingException e) {
					// e.printStackTrace();
					// }

					product.setEncryption(encryption);

				}

				product.setPacketFee(metaInfo.getProdClsfCd());
				product.setPlatClsfCd(metaInfo.getPlatClsfCd());

				commonResponse.setTotalCount(1);
			} else {
				commonResponse.setTotalCount(0);
			}

		}
		response.setCommonResponse(commonResponse);
		response.setComponent(component);
		response.setProduct(product);
		return response;
	}
}
