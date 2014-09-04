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
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadMusicSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadMusicSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.DeviceSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.ProductListSacIn;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Encryption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Music;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Purchase;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.ProductType;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.ProductInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.EncryptionGenerator;
import com.skplanet.storeplatform.sac.display.response.MusicInfoGenerator;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 *
 * Updated on : 2014. 1. 22. Updated by : 이석희, 인크로스.
 */
@Service
public class DownloadMusicServiceImpl implements DownloadMusicService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

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
	private HistoryInternalSCI historyInternalSCI;

    @Autowired
	private EncryptionGenerator encryptionGenerator;

    @Autowired
	private DownloadAES128Helper downloadAES128Helper;

    @Autowired
	private DeviceSCI deviceSCI;

    @Autowired
    private DownloadSupportService supportService;

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
        List<Encryption> encryptionList = new ArrayList<Encryption>();
        StopWatch sw = new StopWatch();
        sw.start();

        MetaInfo downloadSystemDate = this.commonDAO.queryForObject("Download.selectDownloadSystemDate", "",
				MetaInfo.class);

		String reqExpireDate = downloadSystemDate.getExpiredDate();
		String sysDate = downloadSystemDate.getSysDate();

		downloadMusicSacReq.setTenantId(tanantHeader.getTenantId());
		downloadMusicSacReq.setDeviceModelCd(deviceHeader.getModel());
		downloadMusicSacReq.setAnyDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);
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

		this.log.info("----------------------------------------------------------------");
		this.log.info("[DownloadMusicServiceImpl] productId : {}", productId);
		this.log.info("[DownloadMusicServiceImpl] deviceKey : {}", deviceKey);
		this.log.info("[DownloadMusicServiceImpl] userKey : {}", userKey);
		this.log.info("----------------------------------------------------------------");

        MetaInfo metaInfo;

		// 다운로드 Music 상품 조회
        ProductInfo info = this.commonService.getProductInfo(downloadMusicSacReq.getProductId());

        if (info.getProductType() == ProductType.Music) {
            metaInfo = this.commonDAO.queryForObject("Download.getDownloadMusicInfo", downloadMusicSacReq, MetaInfo.class);
        }
        else if(info.getProductType() == ProductType.RingBell) {
            // 벨소리 타입인 경우
            metaInfo = this.commonDAO.queryForObject("Download.getDownloadRingBellInfo", downloadMusicSacReq, MetaInfo.class);
        }
        else
            throw new StorePlatformException("");

		if (metaInfo != null) {
			if (StringUtils.isNotEmpty(deviceKey) && StringUtils.isNotEmpty(userKey)) {
				// 구매내역 조회를 위한 생성자
				ProductListSacIn productListSacIn = null;
				List<ProductListSacIn> productList = null;
				HistoryListSacInReq historyReq = null;
				HistoryListSacInRes historyRes = null;
				boolean purchaseFlag = true;

				try {
					productListSacIn = new ProductListSacIn();
					productList = new ArrayList<ProductListSacIn>();

					productListSacIn.setProdId(metaInfo.getProdId());
					productList.add(productListSacIn);

					historyReq = new HistoryListSacInReq();
					historyReq.setTenantId(downloadMusicSacReq.getTenantId());
					historyReq.setUserKey(downloadMusicSacReq.getUserKey());
					historyReq.setDeviceKey(downloadMusicSacReq.getDeviceKey());
					historyReq.setPrchsProdHaveYn(DisplayConstants.PRCHS_PROD_HAVE_YES);
					historyReq.setPrchsProdType(DisplayConstants.PRCHS_PROD_TYPE_UNIT);
					historyReq.setStartDt(DisplayConstants.PRCHS_START_DATE);
					historyReq.setPrchsStatusCd(DisplayConstants.PRCHS_STSTUS_COMPLETE_CD);
					historyReq.setEndDt(sysDate);
					historyReq.setOffset(1);
					historyReq.setCount(1000);
					historyReq.setProductList(productList);

					this.log.info("----------------------------------------------------------------");
					this.log.info("********************	구매 요청 파라미터	***************************");
					this.log.info("[DownloadMusicServiceImpl] tenantId : {}", historyReq.getTenantId());
					this.log.info("[DownloadMusicServiceImpl] userKey : {}", historyReq.getUserKey());
					this.log.info("[DownloadMusicServiceImpl] deviceKey : {}", historyReq.getDeviceKey());
					this.log.info("[DownloadMusicServiceImpl] prchsProdHaveYn : {}", historyReq.getPrchsProdHaveYn());
					this.log.info("[DownloadMusicServiceImpl] prchsProdtype : {}", historyReq.getPrchsProdType());
					this.log.info("[DownloadMusicServiceImpl] startDt : {}", historyReq.getStartDt());
					this.log.info("[DownloadMusicServiceImpl] endDt : {}", historyReq.getEndDt());
					this.log.info("[DownloadMusicServiceImpl] offset : {}", historyReq.getOffset());
					this.log.info("[DownloadMusicServiceImpl] count : {}", historyReq.getCount());
					this.log.info("[DownloadMusicServiceImpl] prodId : {}", productList.get(0).getProdId());
					this.log.info("----------------------------------------------------------------");

					// 구매내역 조회 실행
					this.log.info("##### [SAC DSP LocalSCI] SAC Purchase Start : historyInternalSCI.searchHistoryList");
					long start = System.currentTimeMillis();
					historyRes = this.historyInternalSCI.searchHistoryList(historyReq);
					this.log.info("##### [SAC DSP LocalSCI] SAC Purchase End : historyInternalSCI.searchHistoryList");
					long end = System.currentTimeMillis();
					this.log.info(
							"##### [SAC DSP LocalSCI] SAC Purchase historyInternalSCI.searchHistoryList takes {} ms",
							(end - start));

				} catch (Exception ex) {
					purchaseFlag = false;
					this.log.info("[DownloadMusicServiceImpl] Purchase History Search Exception : {}");
					this.log.error("구매내역 조회 연동 중 오류가 발생하였습니다. \n{}", ex);
					// throw new StorePlatformException("SAC_DSP_2001", ex);
				}

				this.log.info("---------------------------------------------------------------------");
				this.log.info("[DownloadMusicServiceImpl] purchaseFlag :{}", purchaseFlag);
				this.log.info("[DownloadMusicServiceImpl] historyRes :{}", historyRes);
				if (purchaseFlag && historyRes != null) {

					this.log.info("[DownloadMusicServiceImpl] 구매건수 :{}", historyRes.getTotalCnt());
					this.log.info("---------------------------------------------------------------------");

					String prchsId = null; // 구매ID
					String prchsDt = null; // 구매일시
					String useExprDt = null; // 이용 만료일시
					String dwldStartDt = null; // 다운로드 시작일시
					String dwldExprDt = null; // 다운로드 만료일시
					String prchsCaseCd = null; // 선물 여부
					String prchsState = null; // 구매상태
					String prchsProdId = null; // 구매 상품ID
					String puchsPrice = null; // 구매 상품금액
					String permitDeviceYn = null; // 단말 지원여부
					String purchaseHide = null; // 구매내역 숨김 여부
					String updateAlarm = null; // 업데이트 알람 수신 여부

					if (historyRes.getTotalCnt() > 0) {
						List<Purchase> purchaseList = new ArrayList<Purchase>();

						for (int i = 0; i < historyRes.getTotalCnt(); i++) {
							prchsId = historyRes.getHistoryList().get(i).getPrchsId();
							prchsDt = historyRes.getHistoryList().get(i).getPrchsDt();
							useExprDt = historyRes.getHistoryList().get(i).getUseExprDt();
							dwldStartDt = historyRes.getHistoryList().get(i).getDwldStartDt();
							dwldExprDt = historyRes.getHistoryList().get(i).getDwldExprDt();
							prchsCaseCd = historyRes.getHistoryList().get(i).getPrchsCaseCd();
							prchsProdId = historyRes.getHistoryList().get(i).getProdId();
							puchsPrice = historyRes.getHistoryList().get(i).getProdAmt();
							permitDeviceYn = historyRes.getHistoryList().get(i).getPermitDeviceYn();
							purchaseHide = historyRes.getHistoryList().get(i).getHidingYn();
							updateAlarm = historyRes.getHistoryList().get(i).getAlarmYn();

							// 구매상태 확인
							downloadMusicSacReq.setPrchsDt(prchsDt);
							downloadMusicSacReq.setDwldStartDt(dwldStartDt);
							downloadMusicSacReq.setDwldExprDt(dwldExprDt);
							// prchsState = (String) this.commonDAO.queryForObject("Download.getDownloadPurchaseState",
							// downloadMusicSacReq);

							prchsState = (String) ((HashMap) this.commonDAO.queryForObject(
									"Download.getDownloadPurchaseState", downloadMusicSacReq)).get("PURCHASE_STATE");

							// 구매상태 만료여부 확인
							if (!DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsState)) {
								// 구매 및 선물 여부 확인
								if (DisplayConstants.PRCHS_CASE_PURCHASE_CD.equals(prchsCaseCd)) {
									prchsState = "payment";
								} else if (DisplayConstants.PRCHS_CASE_GIFT_CD.equals(prchsCaseCd)) {
									prchsState = "gift";
								}
							}

							this.log.info("----------------------------------------------------------------");
							this.log.info("[DownloadMusicServiceImpl] prchsId : {}", prchsId);
							this.log.info("[DownloadMusicServiceImpl] prchsDt : {}", prchsDt);
							this.log.info("[DownloadMusicServiceImpl] useExprDt : {}", useExprDt);
							this.log.info("[DownloadMusicServiceImpl] dwldExprDt : {}", dwldExprDt);
							this.log.info("[DownloadMusicServiceImpl] prchsCaseCd : {}", prchsCaseCd);
							this.log.info("[DownloadMusicServiceImpl] prchsState : {}", prchsState);
							this.log.info("[DownloadMusicServiceImpl] prchsProdId : {}", prchsProdId);
							this.log.info("[DownloadMusicServiceImpl] prchsPrice : {}", puchsPrice);
							this.log.info("----------------------------------------------------------------");

							metaInfo.setPurchaseId(prchsId);
							metaInfo.setPurchaseProdId(prchsProdId);
							metaInfo.setPurchaseDt(prchsDt);
							metaInfo.setPurchaseState(prchsState);
							metaInfo.setPurchaseDwldExprDt(dwldExprDt);
							metaInfo.setPurchasePrice(Integer.parseInt(puchsPrice));

							// 구매 정보
							purchaseList.add(this.commonGenerator.generatePurchase(metaInfo));

							/************************************************************************************************
							 * 구매 정보에 따른 암호화 시작
							 ************************************************************************************************/
							// 구매상태 만료 여부 확인
							if (!DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsState)
									&& permitDeviceYn.equals("Y")) {
								String deviceId = null; // Device Id
								String deviceIdType = null; // Device Id 유형
								SearchDeviceIdSacReq deviceReq = null;
								SearchDeviceIdSacRes deviceRes = null;
								boolean memberFlag = true;

								try {
									deviceReq = new SearchDeviceIdSacReq();
									deviceReq.setUserKey(downloadMusicSacReq.getUserKey());
									deviceReq.setDeviceKey(downloadMusicSacReq.getDeviceKey());
									this.log.info("----------------------------------------------------------------");
									this.log.info("*******************회원 단말 정보 조회 파라미터*********************");
									this.log.info("[DownloadMusicServiceImpl] userKey : {}", deviceReq.getUserKey());
									this.log.info("[DownloadMusicServiceImpl] deviceKey : {}", deviceReq.getDeviceKey());
									this.log.info("----------------------------------------------------------------");

									// 기기정보 조회
									this.log.info("##### [SAC DSP LocalSCI] SAC Member Start : deviceSCI.searchDeviceId");
									long start = System.currentTimeMillis();
									deviceRes = this.deviceSCI.searchDeviceId(deviceReq);
									this.log.info("##### [SAC DSP LocalSCI] SAC Member End : deviceSCI.searchDeviceId");
									long end = System.currentTimeMillis();
									this.log.info(
											"##### [SAC DSP LocalSCI] SAC Member deviceSCI.searchDeviceId takes {} ms",
											(end - start));
								} catch (Exception ex) {
									memberFlag = false;
									this.log.info("[DownloadMusicServiceImpl] Device Search Exception : {}");
									this.log.error("단말정보 조회 연동 중 오류가 발생하였습니다. \n{}", ex);
									// throw new StorePlatformException("SAC_DSP_1001", ex);
								}

								this.log.info("----------------------------------------------------------------");
								this.log.info("[DownloadMusicServiceImpl] memberFlag	:	{}", memberFlag);
								this.log.info("[DownloadMusicServiceImpl] deviceRes	:	{}", deviceRes);
								this.log.info("----------------------------------------------------------------");
								if (memberFlag && deviceRes != null) {
									// MDN 인증여부 확인 (2014.05.22 회원 API 변경에 따른 추가)
									if ("Y".equals(deviceRes.getAuthYn())) {
										deviceId = deviceRes.getDeviceId();
										deviceIdType = this.commonService.getDeviceIdType(deviceId);

										metaInfo.setExpiredDate(reqExpireDate);
										metaInfo.setUseExprDt(useExprDt);
										metaInfo.setUserKey(userKey);
										metaInfo.setDeviceKey(deviceKey);
										metaInfo.setDeviceType(deviceIdType);
										metaInfo.setDeviceSubKey(deviceId);
										metaInfo.setPurchaseHide(purchaseHide);
										metaInfo.setUpdateAlarm(updateAlarm);


										// 암호화 정보 (JSON)
										metaInfo.setSystemId(tanantHeader.getSystemId());
                                        Encryption encryption = this.supportService.generateEncryption(metaInfo, prchsProdId);
										encryptionList.add(encryption);

										this.log.debug("-----------------------------------------------------------");
										this.log.debug("[DownloadEbookLog] token : {}", encryption.getToken());
										this.log.debug("[DownloadEbookLog] keyIdx : {}", encryption.getKeyIndex());
										this.log.debug("-----------------------------------------------------------");
									} else {
										this.log.info("##### [SAC DSP LocalSCI] userKey : {}", deviceReq.getUserKey());
										this.log.info("##### [SAC DSP LocalSCI] deviceKey : {}",
												deviceReq.getDeviceKey());
										this.log.info("##### [SAC DSP LocalSCI] NOT VALID DEVICE_ID : "
												+ deviceRes.getDeviceId());
									}
								}
								// 구매 정보
								product.setPurchaseList(purchaseList);
								// 암호화 정보
								// if (!encryptionList.isEmpty()) {
								// product.setDl(encryptionList);
								// }
								break;
							}
						}
					}
				}
			}

			identifierList = new ArrayList<Identifier>();

			music = new Music();

			// 상품ID 정보
			// metaInfo.setPartProdId(metaInfo.getProdId());
			// metaInfo.setContentsTypeCd(DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD);
			// product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));

			Identifier identifier = new Identifier();
			identifier = this.commonGenerator.generateIdentifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD,
					metaInfo.getChnlProdId());
			identifierList.add(identifier);

			identifier = this.commonGenerator.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD,
					metaInfo.getProdId());
			identifierList.add(identifier);

			product.setIdentifierList(identifierList); // 상품 ID

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
            if(metaInfo.getFileSize() != null) {
                musicSourceList.add(this.commonGenerator.generateSource(DisplayConstants.DP_SOURCE_TYPE_AUDIO_MP3_128,
                        null, metaInfo.getFileSize()));
            }
            if(metaInfo.getFileSizeH() != null) {
                musicSourceList.add(this.commonGenerator.generateSource(DisplayConstants.DP_SOURCE_TYPE_AUDIO_MP3_192,
                        null, metaInfo.getFileSizeH()));
            }

			music.setSourceList(musicSourceList);
			product.setMusic(music);

			// 이용등급 정보
			product.setRights(this.commonGenerator.generateRights(metaInfo));

			commonResponse.setTotalCount(1);
		} else {
			throw new StorePlatformException("SAC_DSP_0009");
		}
		response.setCommonResponse(commonResponse);
		response.setProduct(product);

        sw.stop();
        this.supportService.logDownloadResult(userKey, deviceKey, productId, encryptionList, sw.getTime());

		return response;
	}
}
