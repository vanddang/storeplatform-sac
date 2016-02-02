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

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.*;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadVodSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadVodSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadVodV3SacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.DeviceSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.GiftConfirmInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistorySacIn;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.ProductListSacIn;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.VodGenerator;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 *
 * Updated on : 2014. 1. 22. Updated by : 이석희, 인크로스.
 */
@Service
public class DownloadVodServiceImpl implements DownloadVodService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

    @Autowired
	private DisplayCommonService commonService;

    @Autowired
	private HistoryInternalSCI historyInternalSCI;

    @Autowired
    private GiftConfirmInternalSCI giftConfirmInternalSCI;

    @Autowired
	private CommonMetaInfoGenerator commonGenerator;

    @Autowired
	private VodGenerator vodGenerator;

    @Autowired
	private DeviceSCI deviceSCI;

    @Autowired
    private DownloadSupportService supportService;
    
	/*
	 * (non-Javadoc)
	 *
	 * @see com.skplanet.storeplatform.sac.biz.product.service.DownloadVodService#DownloadVodService(com.skplanet
	 * .storeplatform.sac.client.product.vo.DownloadVodReqVO)
	 */
	@Override
	public DownloadVodSacRes searchDownloadVod(SacRequestHeader requestheader, DownloadVodSacReq downloadVodSacReq, boolean supportFhdVideo) {

        List<Encryption> encryptionList = new ArrayList<Encryption>();
        StopWatch sw = new StopWatch();
        sw.start();

        TenantHeader tenantHeader = requestheader.getTenantHeader();
		DeviceHeader deviceHeader = requestheader.getDeviceHeader();

		MetaInfo downloadSystemDate = commonDAO.queryForObject("Download.selectDownloadSystemDate", "", MetaInfo.class); // 현재일시 및 요청만료일시 조회

		String sysDate = downloadSystemDate.getSysDate(); // 현재 일시
		String reqExpireDate = downloadSystemDate.getExpiredDate(); // 요청 만료 일시
		setRequest(downloadVodSacReq, tenantHeader, deviceHeader);

		String idType = downloadVodSacReq.getIdType(); // 조회 상품 ID 유형
		String productId = downloadVodSacReq.getProductId(); // 조회 상품 ID
		
		// ID유형 유효값 체크 
		// Episode만 조회하도록 변경 2015.10.28 update by 이석희, I-S PLUS
		if (!DisplayConstants.DP_EPISODE_IDENTIFIER_CD.equals(idType)) {
			throw new StorePlatformException("SAC_DSP_0003", "idType", idType);
		}

		log.debug("----------------------------------------------------------------");
		log.debug("[DownloadVodServiceImpl] productId : {}", productId);
		log.debug("[DownloadVodServiceImpl] deviceKey : {}", downloadVodSacReq.getDeviceKey());
		log.debug("[DownloadVodServiceImpl] userKey : {}", downloadVodSacReq.getUserKey());
		log.debug("----------------------------------------------------------------");

		MetaInfo metaInfo = getVodMetaInfo(downloadVodSacReq); // VOD 상품 조회
		Product product = new Product();
		
		log.debug("----------------------------------------------------------------");
		log.debug("[DownloadVodServiceImpl] NORMAL scid : {}", metaInfo.getNmSubContsId());
		log.debug("[DownloadVodServiceImpl] SD scid : {}", metaInfo.getSdSubContsId());
		log.debug("[DownloadVodServiceImpl] HD scid : {}", metaInfo.getHdSubContsId());
		log.debug("[DownloadVodServiceImpl] CID : {}", metaInfo.getCid());
		log.debug("----------------------------------------------------------------");

		if (StringUtils.isNotEmpty(downloadVodSacReq.getDeviceKey()) && StringUtils.isNotEmpty(downloadVodSacReq.getUserKey())) {
			HistoryListSacInRes historyRes = null;
			boolean purchaseFlag = true;

			try {
				List<ProductListSacIn> prodIdList = makeProdIdList(metaInfo); // 소장 , 대여 상품 ID List 
				HistoryListSacInReq historyReq = makeHistoryListSacInReq(downloadVodSacReq, prodIdList); // 구매내역 조회 요청 Parameter Set
				loggingParamsForPurchaseHistoryLocalSCI(prodIdList, historyReq); // 구매내역 조회 요청 Parameter Log
				historyRes = historyInternalSCI.searchHistoryList(historyReq); // 구매내역 조회
			} catch (Exception ex) {
				purchaseFlag = false;
				log.debug("[DownloadVodServiceImpl] Purchase History Search Exception : {}");
				log.error("구매내역 조회 연동 중 오류가 발생하였습니다. \n{}", ex);
			}

			log.debug("---------------------------------------------------------------------");
			log.debug("[DownloadVodServiceImpl] purchaseFlag :{}", purchaseFlag);
			log.debug("[DownloadVodServiceImpl] historyRes :{}", historyRes);
			if (purchaseFlag && historyRes != null) { // 정상적으로 구매내역이 조회되고, 구매내역이 존재 할때
				log.debug("[DownloadVodServiceImpl] 구매건수 :{}", historyRes.getTotalCnt());
				log.debug("---------------------------------------------------------------------");
				List<Purchase> purchaseList = new ArrayList<Purchase>(); // 구매내역 List

				for(HistorySacIn historySacIn : historyRes.getHistoryList()) {
                    String permitDeviceYn = historySacIn.getPermitDeviceYn(); // 허용단말여부
					String prchsState = setPrchsState(historySacIn); // 구매 상태

                    loggingResponseOfPurchaseHistoryLocalSCI(historySacIn, prchsState);
					if (supportService.resetExprDtOfGift(historySacIn, requestheader, downloadVodSacReq.getUserKey(), downloadVodSacReq.getDeviceKey(),
							historySacIn.getProdId(), sysDate, prchsState)) {
						prchsState = setPrchsState(historySacIn); // 선물인경우 만료기한이 update 되었을 수 있어 만료여부 다시 체크
					}
					addPurchaseIntoList(purchaseList, historySacIn, prchsState); // 구매내역 List 생성 및 추가
					/************************************************************************************************
					 * 구매 정보에 따른 암호화 시작
					 ************************************************************************************************/
					log.debug("----------------------------------------------------------------");
					log.debug("[DownloadVodServiceImpl] prchsState	:	{}", prchsState);
					log.debug("----------------------------------------------------------------");

					// 구매상태 만료 여부 확인
					if (DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsState) || !permitDeviceYn.equals("Y")) {
						continue;
					}

					SearchDeviceIdSacRes deviceRes = null;
					try {
						SearchDeviceIdSacReq deviceReq = makeSearchDeviceIdSacReq(downloadVodSacReq); // 회원 단말 정보 조회 Parameter Set
						deviceRes = deviceSCI.searchDeviceId(deviceReq); // 회원 단말 정보 조회
					} catch (Exception ex) {
						log.error("단말정보 조회 연동 중 오류가 발생하였습니다. \n{}", ex);
					}

					log.debug("----------------------------------------------------------------");
					log.debug("[DownloadVodServiceImpl] deviceRes	:	{}", deviceRes);
					log.debug("----------------------------------------------------------------");

					if (deviceRes == null || !"Y".equals(deviceRes.getAuthYn()))
						break;

					/**
					 * CJ E&M 케이블 자유이용권 구매상품 DRM 만료 여부 - 2015.8월 CJ E&M 요청 사항
					 * CJ E&M 케이블 자유이용권 구매한 상품이고, DRM이 Y이고, 소장상품일 경우 true 아닐 경우 false
					 */
//					boolean unlimitedDrmExpireDt = (StringUtils.isNotEmpty(historySacIn.getUseFixrateProdId()) && "Y".equals(historySacIn.getDrmYn()) && StringUtils.isNotEmpty(metaInfo.getStoreProdId()));
					setMetaInfo(metaInfo, historySacIn, downloadVodSacReq, tenantHeader, reqExpireDate, prchsState, deviceRes); // Meta정보 Set

					/**
					 * unlimitedDrmExpireDt 값이 true 이면 DL 규격의 ExpirationDate 값이 99991231T125959Z 값으로 내려감  - 2015.8월 CJ E&M 요청 사항
					 */ 
//					Encryption encryption = supportService.generateEncryptionV2(metaInfo, historySacIn.getProdId(), supportFhdVideo, unlimitedDrmExpireDt);
					Encryption encryption = supportService.generateEncryption(metaInfo, historySacIn.getProdId(), supportFhdVideo);
					encryptionList.add(encryption); // 암호화 규격 add
					loggingEncResult(encryption); // 암화화 결과 log

					// 구매 정보
					product.setPurchaseList(purchaseList);

					// 암호화 정보
					if (!encryptionList.isEmpty()) {
						log.debug("[DownloadVodServiceImpl]	setDl : {}");
						product.setDl(encryptionList);
					}
					break;
				}
			}
		}

		setProduct(product, metaInfo, supportFhdVideo, downloadVodSacReq.getBaseYn());
		DownloadVodSacRes response = makeResponse(product); // 응답결과 
 
        sw.stop();
        supportService.logDownloadResult(downloadVodSacReq.getUserKey(), downloadVodSacReq.getDeviceKey(), productId, encryptionList, sw.getTime()); // 다운로드 결과 log

		return response;
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see com.skplanet.storeplatform.sac.biz.product.service.DownloadVodService#DownloadVodService(com.skplanet
	 * .storeplatform.sac.client.product.vo.DownloadVodReqVO)
	 */
	@Override
	public DownloadVodSacRes searchDownloadVodV3(SacRequestHeader requestheader, DownloadVodV3SacReq downloadVodV3SacReq, boolean supportFhdVideo) {

        List<Encryption> encryptionList = new ArrayList<Encryption>();
        StopWatch sw = new StopWatch();
        sw.start();

        TenantHeader tenantHeader = requestheader.getTenantHeader();
		DeviceHeader deviceHeader = requestheader.getDeviceHeader();

		MetaInfo downloadSystemDate = commonDAO.queryForObject("Download.selectDownloadSystemDate", "", MetaInfo.class); // 현재일시 및 요청만료일시 조회

		String sysDate = downloadSystemDate.getSysDate(); // 현재 일시
		String reqExpireDate = downloadSystemDate.getExpiredDate(); // 요청 만료 일시
		setRequestV3(downloadVodV3SacReq, tenantHeader, deviceHeader);

		String productId = downloadVodV3SacReq.getEpisodeId(); // 조회 상품 ID

		log.debug("----------------------------------------------------------------");
		log.debug("[DownloadVodServiceImpl] productId : {}", productId);
		log.debug("[DownloadVodServiceImpl] deviceKey : {}", downloadVodV3SacReq.getDeviceKey());
		log.debug("[DownloadVodServiceImpl] userKey : {}", downloadVodV3SacReq.getUserKey());
		log.debug("----------------------------------------------------------------");

		MetaInfo metaInfo = getVodMetaInfoV3(downloadVodV3SacReq); // VOD 상품 조회
		Product product = new Product();

		log.debug("----------------------------------------------------------------");
		log.debug("[DownloadVodServiceImpl] NORMAL scid : {}", metaInfo.getNmSubContsId());
		log.debug("[DownloadVodServiceImpl] SD scid : {}", metaInfo.getSdSubContsId());
		log.debug("[DownloadVodServiceImpl] HD scid : {}", metaInfo.getHdSubContsId());
		log.debug("[DownloadVodServiceImpl] CID : {}", metaInfo.getCid());
		log.debug("----------------------------------------------------------------");

		// Chrome Cast 재생 허용 Player
		if (StringUtils.isNotEmpty(downloadVodV3SacReq.getPlayer())) {
			String player = StringUtils.lowerCase(downloadVodV3SacReq.getPlayer());
			try {
				if (!"mobile".equals(player) && !"chrome".equals(player) && !"pc".equals(player) && !"tv".equals(player)) {
					this.log.debug("----------------------------------------------------------------");
					this.log.debug("유효하지않은 크롬캐스트 요청 파라메터 : " + player);
					this.log.debug("----------------------------------------------------------------");
					throw new StorePlatformException("SAC_DSP_0003", " player", player);
				}
				metaInfo.setAvailablePlayerReq(player);
			} catch (Exception e) {
				throw new StorePlatformException("SAC_DSP_0003", "player", player);
			}
		}else{
			// default : mobile
			metaInfo.setAvailablePlayerReq("mobile");
		}


		if (StringUtils.isNotEmpty(downloadVodV3SacReq.getDeviceKey()) && StringUtils.isNotEmpty(downloadVodV3SacReq.getUserKey())) {
			HistoryListSacInRes historyRes = null;
			boolean purchaseFlag = true;

			try {
				List<ProductListSacIn> prodIdList = makeProdIdList(metaInfo); // 소장 , 대여 상품 ID List 
				HistoryListSacInReq historyReq = makeHistoryListV3SacInReq(downloadVodV3SacReq, prodIdList); // 구매내역 조회 요청 Parameter Set
				loggingParamsForPurchaseHistoryLocalSCI(prodIdList, historyReq); // 구매내역 조회 요청 Parameter Log
				historyRes = historyInternalSCI.searchHistoryList(historyReq); // 구매내역 조회
			} catch (Exception ex) {
				purchaseFlag = false;
				log.debug("[DownloadVodServiceImpl] Purchase History Search Exception : {}");
				log.error("구매내역 조회 연동 중 오류가 발생하였습니다. \n{}", ex);
			}

			log.debug("---------------------------------------------------------------------");
			log.debug("[DownloadVodServiceImpl] purchaseFlag :{}", purchaseFlag);
			log.debug("[DownloadVodServiceImpl] historyRes :{}", historyRes);
			if (purchaseFlag && historyRes != null) { // 정상적으로 구매내역이 조회되고, 구매내역이 존재 할때
				log.debug("[DownloadVodServiceImpl] 구매건수 :{}", historyRes.getTotalCnt());
				log.debug("---------------------------------------------------------------------");
				List<Purchase> purchaseList = new ArrayList<Purchase>(); // 구매내역 List

				for(HistorySacIn historySacIn : historyRes.getHistoryList()) {
					/**
					 * 구매ID가 들어올경우 구매ID를 찾을때까지 continue 한다.
					 */
					if (StringUtils.isNotBlank(downloadVodV3SacReq.getPrchsId()) && !historySacIn.getPrchsId().equals(downloadVodV3SacReq.getPrchsId())) {
						continue;
					}

                    String permitDeviceYn = historySacIn.getPermitDeviceYn(); // 허용단말여부
					String prchsState = setPrchsState(historySacIn); // 구매 상태

                    loggingResponseOfPurchaseHistoryLocalSCI(historySacIn, prchsState);
					if (supportService.resetExprDtOfGift(historySacIn, requestheader, downloadVodV3SacReq.getUserKey(), downloadVodV3SacReq.getDeviceKey(),
							historySacIn.getProdId(), sysDate, prchsState)) {
						prchsState = setPrchsState(historySacIn); // 선물인경우 만료기한이 update 되었을 수 있어 만료여부 다시 체크
					}
					addPurchaseIntoList(purchaseList, historySacIn, prchsState); // 구매내역 List 생성 및 추가
					/************************************************************************************************
					 * 구매 정보에 따른 암호화 시작
					 ************************************************************************************************/
					log.debug("----------------------------------------------------------------");
					log.debug("[DownloadVodServiceImpl] prchsState	:	{}", prchsState);
					log.debug("----------------------------------------------------------------");

					// 구매상태 만료 여부 확인
					if (DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsState) || !permitDeviceYn.equals("Y")) {
						continue;
					}

					SearchDeviceIdSacRes deviceRes = null;
					try {
						SearchDeviceIdSacReq deviceReq = makeSearchDeviceIdV3SacReq(downloadVodV3SacReq); // 회원 단말 정보 조회 Parameter Set
						deviceRes = deviceSCI.searchDeviceId(deviceReq); // 회원 단말 정보 조회
					} catch (Exception ex) {
						log.error("단말정보 조회 연동 중 오류가 발생하였습니다. \n{}", ex);
					}

					log.debug("----------------------------------------------------------------");
					log.debug("[DownloadVodServiceImpl] deviceRes	:	{}", deviceRes);
					log.debug("----------------------------------------------------------------");

					if (deviceRes == null || !"Y".equals(deviceRes.getAuthYn()))
						break;

					setMetaInfoV3(metaInfo, historySacIn, downloadVodV3SacReq, tenantHeader, reqExpireDate, prchsState, deviceRes); // Meta정보 Set

					Encryption encryption = supportService.generateEncryption(metaInfo, historySacIn.getProdId(), supportFhdVideo);
					encryptionList.add(encryption); // 암호화 규격 add
					loggingEncResult(encryption); // 암화화 결과 log

					// 구매 정보
					product.setPurchaseList(purchaseList);

					// DlStrmCd 값 가져오기
					String dlStrmCd = "";
					if(StringUtils.isNotEmpty(metaInfo.getPlayDlStrmCd())){
						dlStrmCd = metaInfo.getPlayDlStrmCd();
					}else if(StringUtils.isNotEmpty(metaInfo.getStoreDlStrmCd())){
						dlStrmCd = metaInfo.getStoreDlStrmCd();
					}


					// Chrome Cast 재생 허용 Player
					boolean dlTokenFlag = false;
					if(StringUtils.isNotEmpty(metaInfo.getAvailablePlayer())){
						String availablePlayer = StringUtils.lowerCase(metaInfo.getAvailablePlayer());			// 실제 DB값
						String availablePlayerReq = StringUtils.lowerCase(metaInfo.getAvailablePlayerReq()); 	// Request 값
						if (availablePlayer.contains(availablePlayerReq)){
							//  요청이 player=chrome 이지만 스트리밍 지원 상품이면 규격을 내려준다. (dl : 다운로드, strm : 스트리밍, both : 스트리밍&다운로드)
							if (StringUtils.equals(availablePlayerReq, "chrome")){
								if(availablePlayer.contains(availablePlayerReq) && !StringUtils.equals(dlStrmCd, "dl")) {
									dlTokenFlag = true;
								}
							}else{
								// player=tv,mobile,pc로 요청하면 규격을 내려준다.
								if (availablePlayer.contains(availablePlayerReq)){
									dlTokenFlag = true;
								}
							}
						}
					}
					// 암호화 정보
					if (!encryptionList.isEmpty() && dlTokenFlag) {
						log.debug("[DownloadVodServiceImpl]	setDl : {}");
						product.setDl(encryptionList);
					}

					break;
				}
			}
		}

		setProduct(product, metaInfo, supportFhdVideo, downloadVodV3SacReq.getBaseYn());
		DownloadVodSacRes response = makeResponse(product); // 응답결과 
 
        sw.stop();
        supportService.logDownloadResult(downloadVodV3SacReq.getUserKey(), downloadVodV3SacReq.getDeviceKey(), productId, encryptionList, sw.getTime()); // 다운로드 결과 log

		return response;
	}	
	
	private String setPrchsState(HistorySacIn historySacIn) {
		String prchsState = getDownloadPurchaseStateByDbTime(historySacIn); // 구매 상태

		// 구매상태 만료여부 확인
		if (!DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsState)) { // 만료 상태가 아닐때
			// 구매 및 선물 여부 확인
			if (DisplayConstants.PRCHS_CASE_PURCHASE_CD.equals(historySacIn.getPrchsCaseCd())) {
				prchsState = "payment";
			} else if (DisplayConstants.PRCHS_CASE_GIFT_CD.equals(historySacIn.getPrchsCaseCd())) {
				prchsState = "gift";
			}
		}
		return prchsState;
	}

	private void setRequest(DownloadVodSacReq downloadVodSacReq, TenantHeader tenantHeader, DeviceHeader deviceHeader) {
		downloadVodSacReq.setTenantId(tenantHeader.getTenantId());
		downloadVodSacReq.setSystemId(tenantHeader.getSystemId());
		downloadVodSacReq.setDeviceModelCd(deviceHeader.getModel());
		downloadVodSacReq.setLangCd(tenantHeader.getLangCd());
		downloadVodSacReq.setImageCd(DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
		downloadVodSacReq.setAnyDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);
	}
	
	private void setRequestV3(DownloadVodV3SacReq downloadVodV3SacReq, TenantHeader tenantHeader, DeviceHeader deviceHeader) {
		downloadVodV3SacReq.setTenantId(tenantHeader.getTenantId());
		downloadVodV3SacReq.setSystemId(tenantHeader.getSystemId());
		downloadVodV3SacReq.setDeviceModelCd(deviceHeader.getModel());
		downloadVodV3SacReq.setLangCd(tenantHeader.getLangCd());
		downloadVodV3SacReq.setImageCd(DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
		downloadVodV3SacReq.setAnyDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);
	}

	private void setProduct(Product product, MetaInfo metaInfo, boolean supportFhdVideo, String baseYn) {
		List<Identifier>  identifierList = new ArrayList<Identifier>();
		Identifier identifier;

		identifier = commonGenerator.generateIdentifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, metaInfo.getProdId());
		identifierList.add(identifier);

		metaInfo.setContentsTypeCd(DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD);

		identifier = commonGenerator.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, metaInfo.getEspdProdId());
		identifierList.add(identifier);

		// CID
		identifier = commonGenerator.generateIdentifier(DisplayConstants.DP_CONTENT_IDENTIFIER_CD, metaInfo.getCid());
		identifierList.add(identifier);

		product.setIdentifierList(identifierList); // 상품 ID
		List<Support>  supportList = new ArrayList<Support>();
		supportList.add(commonGenerator.generateSupport(DisplayConstants.DP_VOD_HDCP_SUPPORT_NM, metaInfo.getHdcpYn()));
		supportList.add(commonGenerator.generateSupport(DisplayConstants.DP_VOD_HD_SUPPORT_NM, metaInfo.getHdvYn()));
		supportList.add(commonGenerator.generateSupport(DisplayConstants.DP_VOD_BTV_SUPPORT_NM, "Y"));
		supportList.add(commonGenerator.generateSupport(DisplayConstants.DP_VOD_DOLBY_NM, metaInfo.getDolbySprtYn()));
		product.setSupportList(supportList);
		
		product.setTitle(commonGenerator.generateTitle(metaInfo)); // 상품명
		product.setMenuList(commonGenerator.generateMenuList(metaInfo)); // 상품 메뉴정보
		product.setSourceList(commonGenerator.generateSourceList(metaInfo)); // 상품 이미지정보
		
		if("Y".equals(baseYn)){
			product.setVod(vodGenerator.generateVod(metaInfo, supportFhdVideo)); // VOD 정보
			product.setRights(commonGenerator.generateRights(metaInfo)); // 이용등급 및 소장/대여 정보
		}else{
			product.setVod(vodGenerator.generateVodV3(metaInfo, supportFhdVideo)); // VOD 정보
			product.setAuthority(commonGenerator.generateAuthority(metaInfo)); // 이용등급 및 소장/대여 정보(V3)
		}
		
		product.setDistributor(commonGenerator.generateDistributor(metaInfo)); // 판매자 정보

        // 보안을 위해 물리파일 경로는 API응답에서 삭제
        if (product.getVod() != null) {
            List<VideoInfo> videoInfoList = product.getVod().getVideoInfoList();
            for (VideoInfo info : videoInfoList) {
                info.setFilePath(null);
            }
        }
        
	}

	private void loggingEncResult(Encryption encryption) {
		log.debug("-------------------------------------------------------------");
		log.debug("[DownloadVodServiceImpl] token : {}", encryption.getToken());
		log.debug("[DownloadVodServiceImpl] keyIdx : {}", encryption.getKeyIndex());
		log.debug("--------------------------------------------------------------");
	}

	private DownloadVodSacRes makeResponse(Product product) {
		CommonResponse commonResponse = new CommonResponse();
        commonResponse.setTotalCount(1);

        DownloadVodSacRes response = new DownloadVodSacRes();
		response.setCommonResponse(commonResponse);
		response.setProduct(product);
		return response;
	}

	private void setMetaInfo(MetaInfo metaInfo, HistorySacIn historySacIn, DownloadVodSacReq downloadVodSacReq, TenantHeader tenantHeader,
			String reqExpireDate, String prchsState, SearchDeviceIdSacRes deviceRes) {
		String deviceId = deviceRes.getDeviceId();
		String deviceIdType = commonService.getDeviceIdType(deviceId);

		metaInfo.setPurchaseId(historySacIn.getPrchsId()); //구매 ID
		metaInfo.setPurchaseProdId(historySacIn.getProdId()); // 구매 상품ID
		metaInfo.setPurchaseDt(historySacIn.getPrchsDt()); // 구매일시
		metaInfo.setPurchaseState(prchsState); // 구매상태
		metaInfo.setPurchaseDwldExprDt(historySacIn.getDwldExprDt()); // 다운로드 만료일시
		metaInfo.setPurchasePrice(Integer.parseInt(historySacIn.getProdAmt())); // 구매상품 가격
		metaInfo.setExpiredDate(reqExpireDate); // 요청 만료일시
		metaInfo.setUseExprDt(historySacIn.getUseExprDt()); // 이용 만료일시
		metaInfo.setUserKey(downloadVodSacReq.getUserKey()); 
		metaInfo.setDeviceKey(downloadVodSacReq.getDeviceKey());
		metaInfo.setDeviceType(deviceIdType); // 단말 유형
		metaInfo.setDeviceSubKey(deviceId); 
		metaInfo.setPurchaseHide(historySacIn.getHidingYn()); // 구매내역 숨김 여부
		metaInfo.setUpdateAlarm(historySacIn.getAlarmYn()); // 업데이트 알람 수신 여부

		mapProdChrg(metaInfo, historySacIn.getProdId()); // 구매 상품ID
		mapDrmYn(metaInfo, historySacIn);

		metaInfo.setSystemId(tenantHeader.getSystemId());
		metaInfo.setTenantId(tenantHeader.getTenantId());

        if ("Y".equals(historySacIn.getDrmYn()) &&	!supportService.isTfreemiumPurchase(historySacIn.getPrchsReqPathCd())) {
			// 구매 경로가 Tfreemium 제외하고 호출되도록 수정한다.
			supportService.mapPurchaseDrmInfo(metaInfo);
			
		}
	}
	
	private void setMetaInfoV3(MetaInfo metaInfo, HistorySacIn historySacIn, DownloadVodV3SacReq downloadVodV3SacReq, TenantHeader tenantHeader,
			String reqExpireDate, String prchsState, SearchDeviceIdSacRes deviceRes) {
		String deviceId = deviceRes.getDeviceId();
		String deviceIdType = commonService.getDeviceIdType(deviceId);

		metaInfo.setPurchaseId(historySacIn.getPrchsId()); //구매 ID
		metaInfo.setPurchaseProdId(historySacIn.getProdId()); // 구매 상품ID
		metaInfo.setPurchaseDt(historySacIn.getPrchsDt()); // 구매일시
		metaInfo.setPurchaseState(prchsState); // 구매상태
		metaInfo.setPurchaseDwldExprDt(historySacIn.getDwldExprDt()); // 다운로드 만료일시
		metaInfo.setPurchasePrice(Integer.parseInt(historySacIn.getProdAmt())); // 구매상품 가격
		metaInfo.setExpiredDate(reqExpireDate); // 요청 만료일시
		metaInfo.setUseExprDt(historySacIn.getUseExprDt()); // 이용 만료일시
		metaInfo.setUserKey(downloadVodV3SacReq.getUserKey()); 
		metaInfo.setDeviceKey(downloadVodV3SacReq.getDeviceKey());
		metaInfo.setDeviceType(deviceIdType); // 단말 유형
		metaInfo.setDeviceSubKey(deviceId); 
		metaInfo.setPurchaseHide(historySacIn.getHidingYn()); // 구매내역 숨김 여부
		metaInfo.setUpdateAlarm(historySacIn.getAlarmYn()); // 업데이트 알람 수신 여부

		mapProdChrg(metaInfo, historySacIn.getProdId()); // 구매 상품ID
		mapDrmYn(metaInfo, historySacIn);

		metaInfo.setSystemId(tenantHeader.getSystemId());
		metaInfo.setTenantId(tenantHeader.getTenantId());

        if ("Y".equals(historySacIn.getDrmYn()) &&	!supportService.isTfreemiumPurchase(historySacIn.getPrchsReqPathCd())) {
			// 구매 경로가 Tfreemium 제외하고 호출되도록 수정한다.
			supportService.mapPurchaseDrmInfo(metaInfo);
			
		}
	}	

	private void addPurchaseIntoList(List<Purchase> purchaseList, HistorySacIn historySacIn, String prchsState) {
		Purchase p = commonGenerator.generatePurchase(prchsState, historySacIn);
		purchaseList.add(p);
	}

	private MetaInfo getVodMetaInfo(DownloadVodSacReq req) {
		MetaInfo metaInfo = commonDAO.queryForObject("Download.getDownloadVodInfo", req, MetaInfo.class); // VOD 상품 조회
		if (metaInfo == null)
			throw new StorePlatformException("SAC_DSP_0009"); // 요청하신 자료가 존재하지 않습니다. 
		// 요청 상품 ID유형이 채널이고, 메타 구분 코드가 CT14(영화 시리즈) 일때
		if (DisplayConstants.DP_CHANNEL_IDENTIFIER_CD.equals(req.getIdType()) && DisplayConstants.DP_SERIAL_VOD_META_CLASS_CD.equals(metaInfo.getMetaClsfCd()))
				throw new StorePlatformException("SAC_DSP_0013"); //단품인 상품만 조회가능 합니다.
		return metaInfo;
	}
	
	private MetaInfo getVodMetaInfoV3(DownloadVodV3SacReq req) {
		MetaInfo metaInfo = commonDAO.queryForObject("Download.getDownloadVodInfoV3", req, MetaInfo.class); // VOD 상품 조회
		if (metaInfo == null)
			throw new StorePlatformException("SAC_DSP_0009"); // 요청하신 자료가 존재하지 않습니다. 
		return metaInfo;
	}

	private SearchDeviceIdSacReq makeSearchDeviceIdSacReq(DownloadVodSacReq downloadVodSacReq) {
		SearchDeviceIdSacReq deviceReq = new SearchDeviceIdSacReq();
		deviceReq.setUserKey(downloadVodSacReq.getUserKey());
		deviceReq.setDeviceKey(downloadVodSacReq.getDeviceKey());
		log.debug("----------------------------------------------------------------");
		log.debug("*******************회원 단말 정보 조회 파라미터*********************");
		log.debug("[DownloadVodServiceImpl] userKey : {}", deviceReq.getUserKey());
		log.debug("[DownloadVodServiceImpl] deviceKey : {}", deviceReq.getDeviceKey());
		log.debug("----------------------------------------------------------------");
		
		return deviceReq;
	}
	
	private SearchDeviceIdSacReq makeSearchDeviceIdV3SacReq(DownloadVodV3SacReq downloadVodV3SacReq) {
		SearchDeviceIdSacReq deviceReq = new SearchDeviceIdSacReq();
		deviceReq.setUserKey(downloadVodV3SacReq.getUserKey());
		deviceReq.setDeviceKey(downloadVodV3SacReq.getDeviceKey());
		log.debug("----------------------------------------------------------------");
		log.debug("*******************회원 단말 정보 조회 파라미터*********************");
		log.debug("[DownloadVodServiceImpl] userKey : {}", deviceReq.getUserKey());
		log.debug("[DownloadVodServiceImpl] deviceKey : {}", deviceReq.getDeviceKey());
		log.debug("----------------------------------------------------------------");
		
		return deviceReq;
	}

	private void loggingResponseOfPurchaseHistoryLocalSCI(HistorySacIn historySacIn, String prchsState) {
		log.debug("----------------------------------------------------------------");
		log.debug("[DownloadVodServiceImpl] prchsId : {}", historySacIn.getPrchsId());
		log.debug("[DownloadVodServiceImpl] prchsDt : {}", historySacIn.getPrchsDt());
		log.debug("[DownloadVodServiceImpl] useExprDt : {}", historySacIn.getUseExprDt());
		log.debug("[DownloadVodServiceImpl] dwldStartDt : {}", historySacIn.getDwldStartDt());
		log.debug("[DownloadVodServiceImpl] dwldExprDt : {}", historySacIn.getDwldExprDt());
		log.debug("[DownloadVodServiceImpl] prchsCaseCd : {}", historySacIn.getPrchsCaseCd());
		log.debug("[DownloadVodServiceImpl] prchsState : {}", prchsState);
		log.debug("[DownloadVodServiceImpl] prchsProdId : {}", historySacIn.getProdId());
		log.debug("[DownloadVodServiceImpl] prchsPrice : {}", historySacIn.getProdAmt());
		log.debug("----------------------------------------------------------------");
		
	}

	@SuppressWarnings("rawtypes")
	private String getDownloadPurchaseStateByDbTime(HistorySacIn historySacIn) {
		DownloadVodSacReq req = new DownloadVodSacReq();
		req.setDwldStartDt(historySacIn.getDwldStartDt()); // 다운로드 시작일시
		req.setDwldExprDt(historySacIn.getDwldExprDt()); // 다운로드 만료일시

		HashMap map = (HashMap) commonDAO.queryForObject("Download.getDownloadPurchaseState", req); // 구매 상태 조회
		return (String) map.get("PURCHASE_STATE");
	}

	private List<ProductListSacIn> makeProdIdList(MetaInfo metaInfo) {
		List<ProductListSacIn> productList = new ArrayList<ProductListSacIn>();

		// 소장 상품ID
		ProductListSacIn storeProduct = new ProductListSacIn();
		storeProduct.setProdId(metaInfo.getStoreProdId());
		productList.add(storeProduct);

		// 대여 상품ID
		ProductListSacIn playProduct = new ProductListSacIn();
		playProduct.setProdId(metaInfo.getPlayProdId());
		productList.add(playProduct);

		return productList;
	}

	private HistoryListSacInReq makeHistoryListSacInReq(DownloadVodSacReq downloadVodSacReq, List<ProductListSacIn> productList) {
		HistoryListSacInReq historyReq = new HistoryListSacInReq();
		historyReq.setTenantId(downloadVodSacReq.getTenantId());
		historyReq.setUserKey(downloadVodSacReq.getUserKey());
		historyReq.setDeviceKey(downloadVodSacReq.getDeviceKey());
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
	
	private HistoryListSacInReq makeHistoryListV3SacInReq(DownloadVodV3SacReq downloadVodV3SacReq, List<ProductListSacIn> productList) {
		HistoryListSacInReq historyReq = new HistoryListSacInReq();
		historyReq.setTenantId(downloadVodV3SacReq.getTenantId());
		historyReq.setUserKey(downloadVodV3SacReq.getUserKey());
		historyReq.setDeviceKey(downloadVodV3SacReq.getDeviceKey());
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

	private void loggingParamsForPurchaseHistoryLocalSCI(List<ProductListSacIn> productList, HistoryListSacInReq historyReq) {
		log.debug("----------------------------------------------------------------");
		log.debug("********************	구매 요청 파라미터	***************************");
		log.debug("[DownloadVodServiceImpl] tenantId : {}", historyReq.getTenantId());
		log.debug("[DownloadVodServiceImpl] userKey : {}", historyReq.getUserKey());
		log.debug("[DownloadVodServiceImpl] deviceKey : {}", historyReq.getDeviceKey());
		log.debug("[DownloadVodServiceImpl] prchsProdHaveYn : {}", historyReq.getPrchsProdHaveYn());
		log.debug("[DownloadVodServiceImpl] prchsProdtype : {}", historyReq.getPrchsProdType());
		log.debug("[DownloadVodServiceImpl] startDt : {}", historyReq.getStartDt());
		log.debug("[DownloadVodServiceImpl] endDt : {}", historyReq.getEndDt());
		log.debug("[DownloadVodServiceImpl] offset : {}", historyReq.getOffset());
		log.debug("[DownloadVodServiceImpl] count : {}", historyReq.getCount());
		log.debug("[DownloadVodServiceImpl] store prodId : {}", productList.get(0).getProdId());
		log.debug("[DownloadVodServiceImpl] play prodId : {}", productList.get(1).getProdId());
		log.debug("----------------------------------------------------------------");
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * @param metaInfo
	 * @param prchsProdId
	 */
	private void mapProdChrg(MetaInfo metaInfo, String prchsProdId) {
		if (StringUtils.equals(prchsProdId, metaInfo.getStoreProdId())) {
			metaInfo.setProdChrg(metaInfo.getStoreProdChrg());
		} else {
			metaInfo.setProdChrg(metaInfo.getPlayProdChrg());
		}
	}

	/**
	 * DRM 여부 설정
	 * @param metaInfo
	 * @param historySacIn
	 */
	private void mapDrmYn(MetaInfo metaInfo, HistorySacIn historySacIn) {

		String prchsReqPathCd = "";
		String drmYn = null;

		if (historySacIn != null) {
			prchsReqPathCd = historySacIn.getPrchsReqPathCd();
            drmYn = historySacIn.getDrmYn();
        }

		// 2014.07.01. kdlim. 구매 내역 drmYn 값이 정확하지 않아 상품정보 drmYn으로 변경
		// 단, T Freemium을 통한 구매건의 경우는 무조건 DRM적용이므로 아래의 조건을 예외처리 해야함.
		//-	"prchsReqPathCd": "OR0004xx",
		//-	OR000413, OR000420 2개 코드가 T Freemium을 통한 구매건임.
		if (supportService.isTfreemiumPurchase(prchsReqPathCd)) {
			metaInfo.setDrmYn("Y");
			metaInfo.setStoreDrmYn("Y");
			metaInfo.setPlayDrmYn("Y");
			return;
		}

		// 2015.06.17
		// 별다른 조건이 없다면 구매당시의 DRM 정보를 사용하도록 수정 (이전에는 전시 정보 사용)
		metaInfo.setDrmYn(drmYn);
	}
}
