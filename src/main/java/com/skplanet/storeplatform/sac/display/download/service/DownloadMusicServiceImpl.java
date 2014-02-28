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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadMusicSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadMusicSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.DeviceSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Music;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.EncryptionGenerator;
import com.skplanet.storeplatform.sac.display.response.MusicInfoGenerator;

//import org.apache.commons.lang3.StringUtils;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 1. 22. Updated by : 이석희, 인크로스.
 */
@Service
public class DownloadMusicServiceImpl implements DownloadMusicService {

	// private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	@Autowired
	private DisplayCommonService commonService;
	@Autowired
	private CommonMetaInfoGenerator commonGenerator;
	@Autowired
	private MusicInfoGenerator musicInfoGenerator;
	@Autowired
	HistoryInternalSCI historyInternalSCI;
	@Autowired
	private EncryptionGenerator encryptionGenerator;
	@Autowired
	private DownloadAES128Helper downloadAES128Helper;
	@Autowired
	private DeviceSCI deviceSCI;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.DownloadAppService#DownloadAppService(com.skplanet
	 * .storeplatform.sac.client.product.vo.DownloadAppReqVO)
	 */
	@Override
	public DownloadMusicSacRes searchDownloadMusic(SacRequestHeader requestheader,
			DownloadMusicSacReq downloadMusicSacReq) {
		TenantHeader tanantHeader = requestheader.getTenantHeader();
		DeviceHeader deviceHeader = requestheader.getDeviceHeader();

		// MetaInfo downloadSystemDate = this.commonDAO.queryForObject("Download.selectDownloadSystemDate", "",
		// MetaInfo.class);
		//
		// String reqExpireDate = downloadSystemDate.getExpiredDate();
		// String sysDate = downloadSystemDate.getSysDate();

		downloadMusicSacReq.setTenantId(tanantHeader.getTenantId());
		downloadMusicSacReq.setDeviceModelCd(deviceHeader.getModel());
		downloadMusicSacReq.setLangCd(tanantHeader.getLangCd());
		downloadMusicSacReq.setImageCd(DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);

		DownloadMusicSacRes response = new DownloadMusicSacRes();
		CommonResponse commonResponse = new CommonResponse();

		String productId = downloadMusicSacReq.getProductId();
		String deviceKey = downloadMusicSacReq.getDeviceKey();
		String userKey = downloadMusicSacReq.getUserKey();

		List<Identifier> identifierList = null;
		Product product = new Product();
		Music music = null;

		if (downloadMusicSacReq.getDummy() == null) {
			// dummy 호출이 아닐때

			// 필수 파라미터 체크
			if (StringUtils.isEmpty(productId)) {
				throw new StorePlatformException("SAC_DSP_0002", "productId", productId);
			}
			if (StringUtils.isEmpty(deviceKey)) {
				throw new StorePlatformException("SAC_DSP_0002", "deviceKey", deviceKey);
			}
			if (StringUtils.isEmpty(userKey)) {
				throw new StorePlatformException("SAC_DSP_0002", "userKey", userKey);
			}

			// 다운로드 Music 상품 조회
			MetaInfo metaInfo = (MetaInfo) this.commonDAO.queryForObject("Download.getDownloadMusicInfo",
					downloadMusicSacReq);

			// // 구매내역 조회를 위한 생성자
			// ProductListSacIn productListSacIn = null;
			// List<ProductListSacIn> productList = null;
			// HistoryListSacInReq historyReq = null;
			// HistoryListSacInRes historyRes = null;
			//
			// try {
			// productListSacIn = new ProductListSacIn();
			// productList = new ArrayList<ProductListSacIn>();
			//
			// productListSacIn.setProdId(metaInfo.getProdId());
			// productList.add(productListSacIn);
			//
			// historyReq = new HistoryListSacInReq();
			// historyReq.setTenantId(downloadMusicSacReq.getTenantId());
			// historyReq.setUserKey(downloadMusicSacReq.getUserKey());
			// historyReq.setDeviceKey(downloadMusicSacReq.getDeviceKey());
			// historyReq.setPrchsProdHaveYn(DisplayConstants.PRCHS_PROD_HAVE_YES);
			// historyReq.setPrchsProdType(DisplayConstants.PRCHS_PROD_TYPE_UNIT);
			// historyReq.setStartDt(DisplayConstants.PRCHS_START_DATE);
			// historyReq.setEndDt(sysDate);
			// historyReq.setOffset(1);
			// historyReq.setCount(1000);
			// historyReq.setProductList(productList);
			//
			// // 구매내역 조회 실행
			// historyRes = this.historyInternalSCI.searchHistoryList(historyReq);
			//
			// } catch (Exception ex) {
			// throw new StorePlatformException("SAC_DSP_2001", ex);
			// }
			//
			// String prchsId = null; // 구매ID
			// String prchsDt = null; // 구매일시
			// String useExprDt = null; // 이용 만료일시
			// String dwldExprDt = null; // 다운로드 만료일시
			// String prchsCaseCd = null; // 선물 여부
			// String prchsState = null; // 구매상태
			// String prchsProdId = null; // 구매 상품ID
			// String puchsPrice = null; // 구매 상품금액
			// String drmYn = null; // 구매상품 Drm여부
			//
			// if (historyRes != null && historyRes.getTotalCnt() > 0) {
			// List<Purchase> purchaseList = new ArrayList<Purchase>();
			// List<Encryption> encryptionList = new ArrayList<Encryption>();
			//
			// for (int i = 0; i < historyRes.getTotalCnt(); i++) {
			// prchsId = historyRes.getHistoryList().get(i).getPrchsId();
			// prchsDt = historyRes.getHistoryList().get(i).getPrchsDt();
			// useExprDt = historyRes.getHistoryList().get(i).getUseExprDt();
			// dwldExprDt = historyRes.getHistoryList().get(i).getDwldExprDt();
			// prchsCaseCd = historyRes.getHistoryList().get(i).getPrchsCaseCd();
			// prchsProdId = historyRes.getHistoryList().get(i).getProdId();
			// puchsPrice = historyRes.getHistoryList().get(i).getProdAmt();
			// drmYn = historyRes.getHistoryList().get(i).getDrmYn();
			//
			// // 구매상태 확인
			// downloadMusicSacReq.setPrchsDt(prchsDt);
			// downloadMusicSacReq.setDwldExprDt(dwldExprDt);
			// prchsState = (String) this.commonDAO.queryForObject("Download.getDownloadPurchaseState",
			// downloadMusicSacReq);
			//
			// // 구매상태 만료여부 확인
			// if (!DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsState)) {
			// // 구매 및 선물 여부 확인
			// if (DisplayConstants.PRCHS_CASE_PURCHASE_CD.equals(prchsCaseCd)) {
			// prchsState = "payment";
			// } else if (DisplayConstants.PRCHS_CASE_GIFT_CD.equals(prchsCaseCd)) {
			// prchsState = "gift";
			// }
			// }
			//
			// this.log.debug("----------------------------------------------------------------");
			// this.log.debug("[getDownloadMusickInfo] prchsId : {}", prchsId);
			// this.log.debug("[getDownloadMusickInfo] prchsDt : {}", prchsDt);
			// this.log.debug("[getDownloadMusickInfo] useExprDt : {}", useExprDt);
			// this.log.debug("[getDownloadMusickInfo] dwldExprDt : {}", dwldExprDt);
			// this.log.debug("[getDownloadMusickInfo] prchsCaseCd : {}", prchsCaseCd);
			// this.log.debug("[getDownloadMusickInfo] prchsState : {}", prchsState);
			// this.log.debug("[getDownloadMusickInfo] prchsProdId : {}", prchsProdId);
			// this.log.debug("----------------------------------------------------------------");
			//
			// metaInfo.setPurchaseId(prchsId);
			// metaInfo.setPurchaseProdId(prchsProdId);
			// metaInfo.setPurchaseDt(prchsDt);
			// metaInfo.setPurchaseState(prchsState);
			// metaInfo.setPurchaseDwldExprDt(dwldExprDt);
			// metaInfo.setPurchasePrice(Integer.parseInt(puchsPrice));
			// metaInfo.setDrmYn(drmYn);
			//
			// // 구매 정보
			// purchaseList.add(this.commonGenerator.generatePurchase(metaInfo));
			//
			// /************************************************************************************************
			// * 구매 정보에 따른 암호화 시작
			// ************************************************************************************************/
			// // 구매상태 만료 여부 확인
			// if (!DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsState)) {
			// String deviceId = null; // Device Id
			// String deviceIdType = null; // Device Id 유형
			// SearchDeviceIdSacReq deviceReq = null;
			// SearchDeviceIdSacRes deviceRes = null;
			//
			// try {
			// deviceReq = new SearchDeviceIdSacReq();
			// deviceReq.setUserKey(downloadMusicSacReq.getUserKey());
			// deviceReq.setDeviceKey(downloadMusicSacReq.getDeviceKey());
			//
			// // 기기정보 조회
			// deviceRes = this.deviceSCI.searchDeviceId(deviceReq);
			// } catch (Exception ex) {
			// throw new StorePlatformException("SAC_DSP_1001", ex);
			// }
			//
			// if (deviceRes != null) {
			// deviceId = deviceRes.getDeviceId();
			// deviceIdType = this.commonService.getDeviceIdType(deviceId);
			//
			// metaInfo.setExpiredDate(reqExpireDate);
			// metaInfo.setUseExprDt(useExprDt);
			// metaInfo.setUserKey(userKey);
			// metaInfo.setDeviceKey(deviceKey);
			// metaInfo.setDeviceType(deviceIdType);
			// metaInfo.setDeviceSubKey(deviceId);
			//
			// // 암호화 정보 (JSON)
			// EncryptionContents contents = this.encryptionGenerator.generateEncryptionContents(metaInfo);
			//
			// // JSON 파싱
			// MarshallingHelper marshaller = new JacksonMarshallingHelper();
			// byte[] jsonData = marshaller.marshal(contents);
			//
			// // JSON 암호화
			// byte[] encryptByte = this.downloadAES128Helper.encryption(jsonData);
			// String encryptString = this.downloadAES128Helper.toHexString(encryptByte);
			//
			// // 암호화 정보 (AES-128)
			// Encryption encryption = new Encryption();
			// encryption.setProductId(prchsProdId);
			// encryption.setDigest(DisplayConstants.DP_FORDOWNLOAD_ENCRYPT_DIGEST);
			// encryption.setKeyIndex(String.valueOf(this.downloadAES128Helper.getSacRandomNo()));
			// encryption.setToken(encryptString);
			// encryptionList.add(encryption);
			//
			// // JSON 복호화
			// byte[] decryptString = this.downloadAES128Helper.convertBytes(encryptString);
			// byte[] decrypt = this.downloadAES128Helper.decryption(decryptString);
			//
			// try {
			// String decData = new String(decrypt, "UTF-8");
			// this.log.debug("----------------------------------------------------------------");
			// this.log.debug("[getDownloadVodInfo] decData : {}", decData);
			// this.log.debug("----------------------------------------------------------------");
			// } catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
			// }
			// }
			// }
			// }
			// // 구매 정보
			// product.setPurchaseList(purchaseList);
			//
			// // 암호화 정보
			// if (!encryptionList.isEmpty()) {
			// product.setDl(encryptionList);
			// }
			// }

			identifierList = new ArrayList<Identifier>();

			music = new Music();

			// 상품ID 정보
			metaInfo.setPartProdId(metaInfo.getProdId());
			metaInfo.setContentsTypeCd(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
			product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));

			// 상품명 정보
			product.setTitle(this.commonGenerator.generateTitle(metaInfo));

			// 이미지 정보
			product.setSourceList(this.commonGenerator.generateSourceList(metaInfo));

			// 메뉴 정보
			product.setMenuList(this.commonGenerator.generateMenuList(metaInfo));

			// 제공자 정보
			product.setContributor(this.musicInfoGenerator.generateContributor(metaInfo));

			// 뮤직 ID 정보
			identifierList.add(this.commonGenerator.generateIdentifier(DisplayConstants.DP_SONG_IDENTIFIER_CD,
					metaInfo.getMusicId()));
			music.setIdentifierList(identifierList);

			// 뮤직의 이미지 정보
			List<Source> musicSourceList = new ArrayList<Source>();
			musicSourceList.add(this.commonGenerator
					.generateSource(DisplayConstants.DP_SOURCE_TYPE_AUDIO_MP3_128, null));
			musicSourceList.add(this.commonGenerator
					.generateSource(DisplayConstants.DP_SOURCE_TYPE_AUDIO_MP3_192, null));
			music.setSourceList(musicSourceList);
			product.setMusic(music);

			// 이용등급 정보
			product.setRights(this.commonGenerator.generateRights(metaInfo));

			// // 구매 정보
			// if (StringUtils.isNotEmpty(prchsId)) {
			// purchase.setState(prchsState);
			// List<Identifier> purchaseIdentifierList = new ArrayList<Identifier>();
			//
			// identifier = new Identifier();
			// identifier.setType(DisplayConstants.DP_PURCHASE_IDENTIFIER_CD);
			// identifier.setText(prchsId);
			// purchaseIdentifierList.add(identifier);
			//
			// identifier = new Identifier();
			// identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
			// identifier.setText(prchsProdId);
			// purchaseIdentifierList.add(identifier);
			//
			// purchase.setIdentifierList(purchaseIdentifierList);
			//
			// date = new Date();
			// date.setType("date/purchase");
			// date.setText(DateUtils.parseDate(prchsDt));
			// List<Date> dateList = new ArrayList<Date>();
			// dateList.add(date);
			// purchase.setDateList(dateList);
			// product.setPurchase(purchase);
			//
			// }

			commonResponse.setTotalCount(1);

		}
		response.setCommonResponse(commonResponse);
		response.setProduct(product);
		return response;
	}
}
