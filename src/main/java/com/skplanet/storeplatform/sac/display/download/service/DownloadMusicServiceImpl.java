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

import com.skplanet.storeplatform.sac.display.cache.service.CachedExtraInfoManager;
import com.skplanet.storeplatform.sac.display.cache.vo.GetProductBaseInfoParam;
import com.skplanet.storeplatform.sac.display.cache.vo.ProductBaseInfo;
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

	private final Logger log = LoggerFactory.getLogger(getClass());

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

    @Autowired
    private CachedExtraInfoManager cachedExtraInfoManager;

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

        MetaInfo downloadSystemDate = commonDAO.queryForObject("Download.selectDownloadSystemDate", "", MetaInfo.class);

		String reqExpireDate = downloadSystemDate.getExpiredDate();
//		String sysDate = downloadSystemDate.getSysDate();

		setRequest(downloadMusicSacReq, tenantHeader, deviceHeader);

		String productId = downloadMusicSacReq.getProductId();
		Product product = new Product();

		log.debug("----------------------------------------------------------------");
		log.debug("[DownloadMusicServiceImpl] productId : {}", productId);
		log.debug("[DownloadMusicServiceImpl] deviceKey : {}", downloadMusicSacReq.getDeviceKey());
		log.debug("[DownloadMusicServiceImpl] userKey : {}", downloadMusicSacReq.getUserKey());
		log.debug("----------------------------------------------------------------");

        MetaInfo metaInfo = getMusicMetaInfo(downloadMusicSacReq);

		if (StringUtils.isNotEmpty(downloadMusicSacReq.getDeviceKey()) && StringUtils.isNotEmpty(downloadMusicSacReq.getUserKey())) {
			HistoryListSacInRes historyRes = null;
			boolean purchaseFlag = true;

			try {
				List<ProductListSacIn> prodIdList = makeProdIdList(metaInfo);
				HistoryListSacInReq historyReq = makeHistoryListSacInReq(downloadMusicSacReq, prodIdList);
				loggingParamsForPurchaseHistoryLocalSCI(prodIdList, historyReq);
				historyRes = historyInternalSCI.searchHistoryList(historyReq);
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
				String prchsProdId; // 구매 상품ID
				String permitDeviceYn; // 단말 지원여부
				List<Purchase> purchaseList = new ArrayList<Purchase>();

				for(HistorySacIn historySacIn : historyRes.getHistoryList()) {
					prchsProdId = historySacIn.getProdId();
					permitDeviceYn = historySacIn.getPermitDeviceYn();

					String prchsState = setPrchsState(historySacIn);
					loggingResponseOfPurchaseHistoryLocalSCI(historySacIn, prchsState);
					addPurchaseIntoList(purchaseList, historySacIn, prchsState);
					// 구매상태 만료 여부 확인
					if (DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsState) || !permitDeviceYn.equals("Y")) {
						continue;
					}

					SearchDeviceIdSacRes deviceRes = null;
					try {
						SearchDeviceIdSacReq deviceReq = makeSearchDeviceIdSacReq(downloadMusicSacReq, tenantHeader);
						deviceRes = deviceSCI.searchDeviceId(deviceReq);
					} catch (Exception ex) {
						log.error("단말정보 조회 연동 중 오류가 발생하였습니다. \n{}", ex);
					}
					log.debug("----------------------------------------------------------------");
					log.debug("[DownloadMusicServiceImpl] deviceRes	:	{}", deviceRes);
					log.debug("----------------------------------------------------------------");
					// MDN 인증여부 확인 (2014.05.22 회원 API 변경에 따른 추가)
					if (deviceRes == null || !"Y".equals(deviceRes.getAuthYn()))
						break;

					setMetaInfo(metaInfo, historySacIn, downloadMusicSacReq, tenantHeader, reqExpireDate, prchsState, deviceRes);
					Encryption encryption = supportService.generateEncryption(metaInfo, prchsProdId);
					encryptionList.add(encryption);
					loggingEncResult(encryption);

					product.setPurchaseList(purchaseList);
					break;
				}
			}
		}
		setProduct(product, metaInfo);
		DownloadMusicSacRes response = makeResponse(product);
        sw.stop();
        supportService.logDownloadResult(downloadMusicSacReq.getUserKey(), downloadMusicSacReq.getDeviceKey(), productId, encryptionList, sw.getTime());

		return response;
	}

	private String setPrchsState(HistorySacIn historySacIn) {
		String prchsState = getDownloadPurchaseStateByDbTime(historySacIn);

		// 구매상태 만료여부 확인
		if (!DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsState)) {
			// 구매 및 선물 여부 확인
			if (DisplayConstants.PRCHS_CASE_PURCHASE_CD.equals(historySacIn.getPrchsCaseCd())) {
				prchsState = "payment";
			} else if (DisplayConstants.PRCHS_CASE_GIFT_CD.equals(historySacIn.getPrchsCaseCd())) {
				prchsState = "gift";
			}
		}
		return prchsState;
	}

	private void setRequest(DownloadMusicSacReq downloadMusicSacReq, TenantHeader tenantHeader, DeviceHeader deviceHeader) {
		downloadMusicSacReq.setTenantId(tenantHeader.getTenantId());
		downloadMusicSacReq.setDeviceModelCd(deviceHeader.getModel());
		downloadMusicSacReq.setAnyDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);
		downloadMusicSacReq.setLangCd(tenantHeader.getLangCd());
		downloadMusicSacReq.setImageCd(DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
	}

	private void setProduct(Product product, MetaInfo metaInfo) {
		Music music = new Music();
		List<Identifier> identifierList = new ArrayList<Identifier>();
		Identifier identifier = commonGenerator.generateIdentifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, metaInfo.getChnlProdId());
		identifierList.add(identifier);

		identifier = commonGenerator.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, metaInfo.getProdId());
		identifierList.add(identifier);

		product.setIdentifierList(identifierList); // 상품 ID
		product.setTitle(commonGenerator.generateTitle(metaInfo));
		product.setSourceList(commonGenerator.generateSourceList(metaInfo));
		product.setMenuList(commonGenerator.generateMenuList(metaInfo));
		product.setContributor(musicInfoGenerator.generateContributor(metaInfo));
		identifierList.add(commonGenerator.generateIdentifier(DisplayConstants.DP_SONG_IDENTIFIER_CD,metaInfo.getOutsdContentsId()));
		music.setIdentifierList(identifierList);
		List<Source> musicSourceList = new ArrayList<Source>();
        if(metaInfo.getFileSize() != null) {
            musicSourceList.add(commonGenerator.generateSource(DisplayConstants.DP_SOURCE_TYPE_AUDIO_MP3_128, null, metaInfo.getFileSize()));
        }
        if(metaInfo.getFileSizeH() != null) {
            musicSourceList.add(commonGenerator.generateSource(DisplayConstants.DP_SOURCE_TYPE_AUDIO_MP3_192, null, metaInfo.getFileSizeH()));
        }
        if(metaInfo.getFileSizeHH() != null) {
            musicSourceList.add(commonGenerator.generateSource(DisplayConstants.DP_SOURCE_TYPE_AUDIO_MP3_320, null, metaInfo.getFileSizeHH()));
        }

		music.setSourceList(musicSourceList);
		product.setMusic(music);
		product.setRights(commonGenerator.generateRights(metaInfo));
	}

	private void loggingEncResult(Encryption encryption) {
		log.debug("-----------------------------------------------------------");
		log.debug("[DownloadEbookLog] token : {}", encryption.getToken());
		log.debug("[DownloadEbookLog] keyIdx : {}", encryption.getKeyIndex());
		log.debug("-----------------------------------------------------------");
	}

	private DownloadMusicSacRes makeResponse(Product product) {
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setTotalCount(1);

		DownloadMusicSacRes response = new DownloadMusicSacRes();
		response.setCommonResponse(commonResponse);
		response.setProduct(product);
		return response;
	}

	private void setMetaInfo(MetaInfo metaInfo, HistorySacIn historySacIn, DownloadMusicSacReq downloadMusicSacReq, TenantHeader tenantHeader,
			String reqExpireDate, String prchsState, SearchDeviceIdSacRes deviceRes) {
		String deviceId = deviceRes.getDeviceId();
		String deviceIdType = commonService.getDeviceIdType(deviceId);

		metaInfo.setPurchaseId(historySacIn.getPrchsId());
		metaInfo.setPurchaseProdId(historySacIn.getProdId());
		metaInfo.setPurchaseDt(historySacIn.getPrchsDt());
		metaInfo.setPurchaseState(prchsState);
		metaInfo.setPurchaseDwldExprDt(historySacIn.getDwldExprDt());
		metaInfo.setPurchasePrice(Integer.parseInt(historySacIn.getProdAmt()));
		metaInfo.setExpiredDate(reqExpireDate);
		metaInfo.setUseExprDt(historySacIn.getUseExprDt()); // 이용 만료일시
		metaInfo.setUserKey(downloadMusicSacReq.getUserKey());
		metaInfo.setDeviceKey(downloadMusicSacReq.getDeviceKey());
		metaInfo.setDeviceType(deviceIdType);
		metaInfo.setDeviceSubKey(deviceId);
		metaInfo.setPurchaseHide(historySacIn.getHidingYn()); // 구매내역 숨김 여부
		metaInfo.setUpdateAlarm(historySacIn.getAlarmYn()); // 업데이트 알람 수신 여부
		metaInfo.setSystemId(tenantHeader.getSystemId());
	}

	private void addPurchaseIntoList(List<Purchase> purchaseList, HistorySacIn historySacIn, String prchsState) {
		Purchase p = commonGenerator.generatePurchase(prchsState, historySacIn);
		purchaseList.add(p);
	}

	private MetaInfo getMusicMetaInfo(DownloadMusicSacReq req) {
		MetaInfo metaInfo;

        ProductBaseInfo baseInfo = cachedExtraInfoManager.getProductBaseInfo(new GetProductBaseInfoParam(req.getProductId()));
        ProductType prodTp = baseInfo.getProductType();

        if (prodTp == ProductType.Music)
            metaInfo = commonDAO.queryForObject("Download.getDownloadMusicInfo", req, MetaInfo.class);
        else if(prodTp == ProductType.RingBell)
            metaInfo = commonDAO.queryForObject("Download.getDownloadRingBellInfo", req, MetaInfo.class);
        else
            throw new StorePlatformException("SAC_DSP_0032", req.getProductId());

		if (metaInfo == null)
			throw new StorePlatformException("SAC_DSP_0009");

		return metaInfo;
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
	private String getDownloadPurchaseStateByDbTime(HistorySacIn historySacIn) {
		DownloadMusicSacReq req = new DownloadMusicSacReq();
		req.setDwldStartDt(historySacIn.getDwldStartDt());
		req.setDwldExprDt(historySacIn.getDwldExprDt());

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

	private HistoryListSacInReq makeHistoryListSacInReq(DownloadMusicSacReq downloadMusicSacReq, List<ProductListSacIn> productList) {
		HistoryListSacInReq historyReq = new HistoryListSacInReq();
		historyReq.setTenantId(downloadMusicSacReq.getTenantId());
		historyReq.setUserKey(downloadMusicSacReq.getUserKey());
		historyReq.setDeviceKey(downloadMusicSacReq.getDeviceKey());
		historyReq.setPrchsProdHaveYn(DisplayConstants.PRCHS_PROD_HAVE_YES);
		historyReq.setPrchsProdType(DisplayConstants.PRCHS_PROD_TYPE_UNIT);
		historyReq.setStartDt(DisplayConstants.PRCHS_START_DATE);
		historyReq.setPrchsStatusCd(DisplayConstants.PRCHS_STSTUS_COMPLETE_CD);
		historyReq.setEndDt("20991231235959");
		historyReq.setOffset(1);
		historyReq.setCount(1000);
		historyReq.setProductList(productList);
		return historyReq;
	}
}
