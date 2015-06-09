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
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistorySacIn;
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
	public DownloadMusicSacRes searchDownloadMusic(SacRequestHeader requestheader, DownloadMusicSacReq downloadMusicSacReq) {
		TenantHeader tenantHeader = requestheader.getTenantHeader();
		DeviceHeader deviceHeader = requestheader.getDeviceHeader();
        List<Encryption> encryptionList = new ArrayList<Encryption>();
        StopWatch sw = new StopWatch();
        sw.start();

        MetaInfo downloadSystemDate = this.commonDAO.queryForObject("Download.selectDownloadSystemDate", "", MetaInfo.class);

		String reqExpireDate = downloadSystemDate.getExpiredDate();
		String sysDate = downloadSystemDate.getSysDate();

		downloadMusicSacReq.setTenantId(tenantHeader.getTenantId());
		downloadMusicSacReq.setDeviceModelCd(deviceHeader.getModel());
		downloadMusicSacReq.setAnyDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);
		downloadMusicSacReq.setLangCd(tenantHeader.getLangCd());
		downloadMusicSacReq.setImageCd(DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);

		String productId = downloadMusicSacReq.getProductId();
		String deviceKey = downloadMusicSacReq.getDeviceKey();
		String userKey = downloadMusicSacReq.getUserKey();

		List<Identifier> identifierList = null;
		Product product = new Product();
		Music music = null;

		log.debug("----------------------------------------------------------------");
		log.debug("[DownloadMusicServiceImpl] productId : {}", productId);
		log.debug("[DownloadMusicServiceImpl] deviceKey : {}", deviceKey);
		log.debug("[DownloadMusicServiceImpl] userKey : {}", userKey);
		log.debug("----------------------------------------------------------------");

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

		if (metaInfo == null) {
			throw new StorePlatformException("SAC_DSP_0009");
		}

		if (StringUtils.isNotEmpty(deviceKey) && StringUtils.isNotEmpty(userKey)) {
			HistoryListSacInRes historyRes = null;
			boolean purchaseFlag = true;

			try {
				List<ProductListSacIn> prodIdList = makeProdIdList(metaInfo);
				HistoryListSacInReq historyReq = makeHistoryListSacInReq(downloadMusicSacReq, sysDate, prodIdList);
				loggingParamsForPurchaseHistoryLocalSCI(prodIdList, historyReq);
				historyRes = this.historyInternalSCI.searchHistoryList(historyReq);
			} catch (Exception ex) {
				purchaseFlag = false;
				log.debug("[DownloadMusicServiceImpl] Purchase History Search Exception : {}");
				log.error("구매내역 조회 연동 중 오류가 발생하였습니다. \n{}", ex);
				// throw new StorePlatformException("SAC_DSP_2001", ex);
			}

			log.debug("---------------------------------------------------------------------");
			log.debug("[DownloadMusicServiceImpl] purchaseFlag :{}", purchaseFlag);
			log.debug("[DownloadMusicServiceImpl] historyRes :{}", historyRes);
			if (purchaseFlag && historyRes != null) {

				log.debug("[DownloadMusicServiceImpl] 구매건수 :{}", historyRes.getTotalCnt());
				log.debug("---------------------------------------------------------------------");

				String useExprDt = null; // 이용 만료일시
				String dwldStartDt = null; // 다운로드 시작일시
				String dwldExprDt = null; // 다운로드 만료일시
				String prchsCaseCd = null; // 선물 여부
				String prchsProdId = null; // 구매 상품ID
				String permitDeviceYn = null; // 단말 지원여부
				String purchaseHide = null; // 구매내역 숨김 여부
				String updateAlarm = null; // 업데이트 알람 수신 여부

				if (historyRes.getTotalCnt() > 0) {
					List<Purchase> purchaseList = new ArrayList<Purchase>();

					for(HistorySacIn historySacIn : historyRes.getHistoryList()) {
						useExprDt = historySacIn.getUseExprDt();
						dwldStartDt = historySacIn.getDwldStartDt();
						dwldExprDt = historySacIn.getDwldExprDt();
						prchsCaseCd = historySacIn.getPrchsCaseCd();
						prchsProdId = historySacIn.getProdId();
						permitDeviceYn = historySacIn.getPermitDeviceYn();
						purchaseHide = historySacIn.getHidingYn();
						updateAlarm = historySacIn.getAlarmYn();

						String prchsStateCheckedByDbTime = getDownloadPurchaseStateByDbTime(dwldStartDt, dwldExprDt);
						String prchsState = null;

						// 구매상태 만료여부 확인
						if (!DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsStateCheckedByDbTime)) {
							// 구매 및 선물 여부 확인
							if (DisplayConstants.PRCHS_CASE_PURCHASE_CD.equals(prchsCaseCd)) {
								prchsState = "payment";
							} else if (DisplayConstants.PRCHS_CASE_GIFT_CD.equals(prchsCaseCd)) {
								prchsState = "gift";
							}
						}

						loggingResponseOfPurchaseHistoryLocalSCI(historySacIn, prchsState);

						metaInfo.setPurchaseId(historySacIn.getPrchsId());
						metaInfo.setPurchaseProdId(historySacIn.getProdId());
						metaInfo.setPurchaseDt(historySacIn.getPrchsDt());
						metaInfo.setPurchaseState(prchsState);
						metaInfo.setPurchaseDwldExprDt(historySacIn.getDwldExprDt());
						metaInfo.setPurchasePrice(Integer.parseInt(historySacIn.getProdAmt()));

						// 구매 정보
						purchaseList.add(this.commonGenerator.generatePurchase(metaInfo));

						/************************************************************************************************
						 * 구매 정보에 따른 암호화 시작
						 ************************************************************************************************/
						// 구매상태 만료 여부 확인
						if (!DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsStateCheckedByDbTime) && permitDeviceYn.equals("Y")) {
							SearchDeviceIdSacReq deviceReq = null;
							SearchDeviceIdSacRes deviceRes = null;
							boolean memberFlag = true;

							try {
								deviceReq = makeSearchDeviceIdSacReq(downloadMusicSacReq, tenantHeader);
								deviceRes = this.deviceSCI.searchDeviceId(deviceReq);
							} catch (Exception ex) {
								memberFlag = false;
								log.debug("[DownloadMusicServiceImpl] Device Search Exception : {}");
								log.error("단말정보 조회 연동 중 오류가 발생하였습니다. \n{}", ex);
								// throw new StorePlatformException("SAC_DSP_1001", ex);
							}

							log.debug("----------------------------------------------------------------");
							log.debug("[DownloadMusicServiceImpl] memberFlag	:	{}", memberFlag);
							log.debug("[DownloadMusicServiceImpl] deviceRes	:	{}", deviceRes);
							log.debug("----------------------------------------------------------------");
							if (memberFlag && deviceRes != null) {
								// MDN 인증여부 확인 (2014.05.22 회원 API 변경에 따른 추가)
								if ("Y".equals(deviceRes.getAuthYn())) {
									String deviceId = deviceRes.getDeviceId();
									String deviceIdType = this.commonService.getDeviceIdType(deviceId);

									metaInfo.setExpiredDate(reqExpireDate);
									metaInfo.setUseExprDt(useExprDt);
									metaInfo.setUserKey(userKey);
									metaInfo.setDeviceKey(deviceKey);
									metaInfo.setDeviceType(deviceIdType);
									metaInfo.setDeviceSubKey(deviceId);
									metaInfo.setPurchaseHide(purchaseHide);
									metaInfo.setUpdateAlarm(updateAlarm);

									// 암호화 정보 (JSON)
									metaInfo.setSystemId(tenantHeader.getSystemId());
                                    Encryption encryption = this.supportService.generateEncryption(metaInfo, prchsProdId);
									encryptionList.add(encryption);

									log.debug("-----------------------------------------------------------");
									log.debug("[DownloadEbookLog] token : {}", encryption.getToken());
									log.debug("[DownloadEbookLog] keyIdx : {}", encryption.getKeyIndex());
									log.debug("-----------------------------------------------------------");
								} else {
									log.debug("##### [SAC DSP LocalSCI] NOT VALID DEVICE_ID : {}", deviceRes.getDeviceId());
								}
							}
							// 구매 정보
							product.setPurchaseList(purchaseList);
							break;
						}
					}
				}
			}
		}

		identifierList = new ArrayList<Identifier>();

		music = new Music();

		Identifier identifier = new Identifier();
		identifier = this.commonGenerator.generateIdentifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, metaInfo.getChnlProdId());
		identifierList.add(identifier);

		identifier = this.commonGenerator.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, metaInfo.getProdId());
		identifierList.add(identifier);

		product.setIdentifierList(identifierList); // 상품 ID
		product.setTitle(this.commonGenerator.generateTitle(metaInfo));
		product.setSourceList(this.commonGenerator.generateSourceList(metaInfo));
		product.setMenuList(this.commonGenerator.generateMenuList(metaInfo));
		product.setContributor(this.musicInfoGenerator.generateContributor(metaInfo));
		identifierList.add(this.commonGenerator.generateIdentifier(DisplayConstants.DP_SONG_IDENTIFIER_CD,metaInfo.getMusicId()));
		music.setIdentifierList(identifierList);
		List<Source> musicSourceList = new ArrayList<Source>();
        if(metaInfo.getFileSize() != null) {
            musicSourceList.add(this.commonGenerator.generateSource(DisplayConstants.DP_SOURCE_TYPE_AUDIO_MP3_128, null, metaInfo.getFileSize()));
        }
        if(metaInfo.getFileSizeH() != null) {
            musicSourceList.add(this.commonGenerator.generateSource(DisplayConstants.DP_SOURCE_TYPE_AUDIO_MP3_192, null, metaInfo.getFileSizeH()));
        }

		music.setSourceList(musicSourceList);
		product.setMusic(music);
		product.setRights(this.commonGenerator.generateRights(metaInfo));

		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setTotalCount(1);

		DownloadMusicSacRes response = new DownloadMusicSacRes();
		response.setCommonResponse(commonResponse);
		response.setProduct(product);

        sw.stop();
        this.supportService.logDownloadResult(userKey, deviceKey, productId, encryptionList, sw.getTime());

		return response;
	}

	private SearchDeviceIdSacReq makeSearchDeviceIdSacReq(DownloadMusicSacReq downloadMusicSacReq, TenantHeader tenantHeader) {
		SearchDeviceIdSacReq deviceReq = new SearchDeviceIdSacReq();
		deviceReq.setUserKey(downloadMusicSacReq.getUserKey());
		deviceReq.setDeviceKey(downloadMusicSacReq.getDeviceKey());
		deviceReq.setTenantId(tenantHeader.getTenantId());
		log.debug("----------------------------------------------------------------");
		log.debug("*******************회원 단말 정보 조회 파라미터*********************");
		log.debug("[DownloadMusicServiceImpl] userKey : {}", deviceReq.getUserKey());
		log.debug("[DownloadMusicServiceImpl] deviceKey : {}", deviceReq.getDeviceKey());
		log.debug("----------------------------------------------------------------");
		return deviceReq;
	}

	private void loggingResponseOfPurchaseHistoryLocalSCI(HistorySacIn historySacIn, String prchsState) {
		log.debug("----------------------------------------------------------------");
		log.debug("[DownloadMusicServiceImpl] prchsId : {}", historySacIn.getPrchsId());
		log.debug("[DownloadMusicServiceImpl] prchsDt : {}", historySacIn.getPrchsDt());
		log.debug("[DownloadMusicServiceImpl] useExprDt : {}", historySacIn.getUseExprDt());
		log.debug("[DownloadMusicServiceImpl] dwldExprDt : {}", historySacIn.getDwldExprDt());
		log.debug("[DownloadMusicServiceImpl] prchsCaseCd : {}", historySacIn.getPrchsCaseCd());
		log.debug("[DownloadMusicServiceImpl] prchsState : {}", prchsState);
		log.debug("[DownloadMusicServiceImpl] prchsProdId : {}", historySacIn.getProdId());
		log.debug("[DownloadMusicServiceImpl] prchsPrice : {}", historySacIn.getProdAmt());
		log.debug("----------------------------------------------------------------");
	}

	@SuppressWarnings("rawtypes")
	private String getDownloadPurchaseStateByDbTime(String dwldStartDt, String dwldExprDt) {
		DownloadMusicSacReq req = new DownloadMusicSacReq();
		req.setDwldStartDt(dwldStartDt);
		req.setDwldExprDt(dwldExprDt);

		HashMap map = (HashMap) commonDAO.queryForObject("Download.getDownloadPurchaseState", req);
		return (String) map.get("PURCHASE_STATE");
	}

	private List<ProductListSacIn> makeProdIdList(MetaInfo metaInfo) {
		List<ProductListSacIn>  productList = new ArrayList<ProductListSacIn>();
		ProductListSacIn productListSacIn = new ProductListSacIn();
		productListSacIn.setProdId(metaInfo.getProdId());
		productList.add(productListSacIn);
		return productList;
	}

	private void loggingParamsForPurchaseHistoryLocalSCI(List<ProductListSacIn> productList, HistoryListSacInReq historyReq) {
		log.debug("----------------------------------------------------------------");
		log.debug("********************	구매 요청 파라미터	***************************");
		log.debug("[DownloadMusicServiceImpl] tenantId : {}", historyReq.getTenantId());
		log.debug("[DownloadMusicServiceImpl] userKey : {}", historyReq.getUserKey());
		log.debug("[DownloadMusicServiceImpl] deviceKey : {}", historyReq.getDeviceKey());
		log.debug("[DownloadMusicServiceImpl] prchsProdHaveYn : {}", historyReq.getPrchsProdHaveYn());
		log.debug("[DownloadMusicServiceImpl] prchsProdtype : {}", historyReq.getPrchsProdType());
		log.debug("[DownloadMusicServiceImpl] startDt : {}", historyReq.getStartDt());
		log.debug("[DownloadMusicServiceImpl] endDt : {}", historyReq.getEndDt());
		log.debug("[DownloadMusicServiceImpl] offset : {}", historyReq.getOffset());
		log.debug("[DownloadMusicServiceImpl] count : {}", historyReq.getCount());
		log.debug("[DownloadMusicServiceImpl] prodId : {}", productList.get(0).getProdId());
		log.debug("----------------------------------------------------------------");
	}

	private HistoryListSacInReq makeHistoryListSacInReq(DownloadMusicSacReq downloadMusicSacReq, String sysDate, List<ProductListSacIn> productList) {
		HistoryListSacInReq historyReq = new HistoryListSacInReq();
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
		return historyReq;
	}
}
