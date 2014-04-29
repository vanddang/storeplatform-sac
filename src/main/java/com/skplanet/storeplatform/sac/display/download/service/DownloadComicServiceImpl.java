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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.test.JacksonMarshallingHelper;
import com.skplanet.storeplatform.framework.test.MarshallingHelper;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadComicSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadComicSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.DeviceSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.ProductListSacIn;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Encryption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.EncryptionContents;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Purchase;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.EbookComicGenerator;
import com.skplanet.storeplatform.sac.display.response.EncryptionGenerator;

/**
 * DownloadComic Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 1. 28. Updated by : 이태희.
 */
@Service
public class DownloadComicServiceImpl implements DownloadComicService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	HistoryInternalSCI historyInternalSCI;

	@Autowired
	CommonMetaInfoGenerator commonMetaInfoGenerator;

	@Autowired
	private EbookComicGenerator ebookComicGenerator;

	@Autowired
	private EncryptionGenerator encryptionGenerator;

	@Autowired
	private DownloadAES128Helper downloadAES128Helper;

	@Autowired
	private DeviceSCI deviceSCI;

	@Autowired
	private DisplayCommonService commonService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.download.service.DownloadComicService#getDownloadComicInfo(com.skplanet
	 * .storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.display.vo.download.DownloadComicSacReq)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public DownloadComicSacRes getDownloadComicInfo(SacRequestHeader header, DownloadComicSacReq comicReq) {
		// 현재일시 및 만료일시 조회
		MetaInfo metaInfo = (MetaInfo) this.commonDAO.queryForObject("Download.selectDownloadSystemDate", null);

		String sysDate = metaInfo.getSysDate();
		String reqExpireDate = metaInfo.getExpiredDate();
		metaInfo = null;

		DownloadComicSacRes comicRes = new DownloadComicSacRes();
		CommonResponse commonResponse = new CommonResponse();

		String productId = comicReq.getProductId();
		String deviceKey = comicReq.getDeviceKey();
		String userKey = comicReq.getUserKey();

		this.logger.info("----------------------------------------------------------------");
		this.logger.info("[DownloadComicLog] productId : {}", productId);
		this.logger.info("[DownloadComicLog] deviceKey : {}", deviceKey);
		this.logger.info("[DownloadComicLog] userKey : {}", userKey);
		this.logger.info("----------------------------------------------------------------");

		// 헤더정보 세팅
		comicReq.setTenantId(header.getTenantHeader().getTenantId());
		comicReq.setLangCd(header.getTenantHeader().getLangCd());
		comicReq.setDeviceModelCd(header.getDeviceHeader().getModel());
		comicReq.setAnyDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);
		comicReq.setImageCd(DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);

		// comic 상품 정보 조회(for download)
		metaInfo = (MetaInfo) this.commonDAO.queryForObject("Download.selectDownloadComicInfo", comicReq);

		if (metaInfo != null) {
			this.logger.info("----------------------------------------------------------------");
			this.logger.info("[DownloadComicLog] scid : {}", metaInfo.getSubContentsId());
			this.logger.info("----------------------------------------------------------------");

			Product product = new Product();

			// 상품 ID 정보
			List<Identifier> identifierList = new ArrayList<Identifier>();
			identifierList.add(this.commonMetaInfoGenerator.generateIdentifier(
					DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, metaInfo.getProdId()));
			identifierList.add(this.commonMetaInfoGenerator.generateIdentifier(
					DisplayConstants.DP_EPISODE_IDENTIFIER_CD, metaInfo.getPartProdId()));
			product.setIdentifierList(identifierList);

			// 상품명 정보
			product.setTitle(this.commonMetaInfoGenerator.generateTitle(metaInfo));
			product.setChnlProdNm(metaInfo.getChnlProdNm());

			// 상품 설명 정보
			product.setProductExplain(metaInfo.getProdBaseDesc());
			product.setProductDetailExplain(metaInfo.getProdDtlDesc());

			// 이미지 정보
			product.setSourceList(this.commonMetaInfoGenerator.generateDownloadSourceList(metaInfo));

			// 메뉴 정보
			product.setMenuList(this.commonMetaInfoGenerator.generateMenuList(metaInfo));

			// 도서 정보
			product.setBook(this.ebookComicGenerator.generateForDownloadBook(metaInfo));

			// 상품금액 정보
			product.setPrice(this.commonMetaInfoGenerator.generatePrice(metaInfo));

			// 이용권한 정보
			product.setRights(this.commonMetaInfoGenerator.generateRights(metaInfo));

			// 배포자 정보
			product.setDistributor(this.commonMetaInfoGenerator.generateDistributor(metaInfo));

			// 저작자 정보
			product.setContributor(this.ebookComicGenerator.generateComicContributor(metaInfo));

			if (StringUtils.isNotEmpty(deviceKey) && StringUtils.isNotEmpty(userKey)) {
				// 구매내역 조회를 위한 생성자
				ProductListSacIn productListSacIn = null;
				List<ProductListSacIn> productList = null;
				HistoryListSacInReq historyReq = null;
				HistoryListSacInRes historyRes = null;
				boolean purchasePassFlag = true;

				try {
					// 구매내역 조회를 위한 생성자
					productListSacIn = new ProductListSacIn();
					productList = new ArrayList<ProductListSacIn>();

					productListSacIn.setProdId(metaInfo.getPartProdId());
					productList.add(productListSacIn);

					historyReq = new HistoryListSacInReq();
					historyReq.setTenantId(comicReq.getTenantId());
					historyReq.setUserKey(comicReq.getUserKey());
					historyReq.setDeviceKey(comicReq.getDeviceKey());
					historyReq.setPrchsProdHaveYn(DisplayConstants.PRCHS_PROD_HAVE_YES);
					historyReq.setPrchsProdType(DisplayConstants.PRCHS_PROD_TYPE_UNIT);
					historyReq.setStartDt(DisplayConstants.PRCHS_START_DATE);
					historyReq.setEndDt(sysDate);
					historyReq.setOffset(1);
					historyReq.setCount(1000);
					historyReq.setProductList(productList);

					this.logger.info("----------------------------------------------------------------");
					this.logger.info("[DownloadComicLog] 구매내역 조회 요청 파라미터");
					this.logger.info("----------------------------------------------------------------");
					this.logger.info("[DownloadComicLog] tenantId : {}", historyReq.getTenantId());
					this.logger.info("[DownloadComicLog] userKey : {}", historyReq.getUserKey());
					this.logger.info("[DownloadComicLog] deviceKey : {}", historyReq.getDeviceKey());
					this.logger.info("[DownloadComicLog] prchsProdHaveYn : {}", historyReq.getPrchsProdHaveYn());
					this.logger.info("[DownloadComicLog] prchsProdType : {}", historyReq.getPrchsProdType());
					this.logger.info("[DownloadComicLog] startDt : {}", historyReq.getStartDt());
					this.logger.info("[DownloadComicLog] endDt : {}", historyReq.getEndDt());
					this.logger.info("[DownloadComicLog] prodId : {}", productList.get(0).getProdId());
					this.logger.info("----------------------------------------------------------------");

					// 구매내역 조회 실행
					this.logger
							.info("##### [SAC DSP LocalSCI] SAC Purchase Start : historyInternalSCI.searchHistoryList");
					long start = System.currentTimeMillis();
					historyRes = this.historyInternalSCI.searchHistoryList(historyReq);
					this.logger
							.info("##### [SAC DSP LocalSCI] SAC Purchase End : historyInternalSCI.searchHistoryList");
					long end = System.currentTimeMillis();
					this.logger.info(
							"##### [SAC DSP LocalSCI] SAC Purchase historyInternalSCI.searchHistoryList takes {} ms",
							(end - start));
				} catch (Exception ex) {
					purchasePassFlag = false;
					this.logger.error("구매내역 조회 연동 중 오류가 발생하였습니다.\n", ex);
				}

				this.logger.info("----------------------------------------------------------------");
				this.logger.info("[DownloadComicLog] purchasePassFlag : {}", purchasePassFlag);
				this.logger.info("[DownloadComicLog] historyRes : {}", historyRes.toString());
				this.logger.info("----------------------------------------------------------------");

				if (purchasePassFlag && historyRes != null) {
					this.logger.info("----------------------------------------------------------------");
					this.logger.info("[DownloadComicLog] 구매건수 : {}", historyRes.getTotalCnt());
					this.logger.info("----------------------------------------------------------------");

					String prchsId = null; // 구매ID
					String prchsDt = null; // 구매일시
					String useExprDt = null; // 이용 만료일시
					String dwldStartDt = null; // 다운로드 시작일시
					String dwldExprDt = null; // 다운로드 만료일시
					String prchsCaseCd = null; // 선물 여부
					String prchsState = null; // 구매상태
					String prchsProdId = null; // 구매 상품ID
					String prchsPrice = null; // 구매금액
					String drmYn = null; // DRM 지원여부

					if (historyRes.getTotalCnt() > 0) {
						List<Purchase> purchaseList = new ArrayList<Purchase>();
						List<Encryption> encryptionList = new ArrayList<Encryption>();

						for (int i = 0; i < historyRes.getTotalCnt(); i++) {
							prchsId = historyRes.getHistoryList().get(i).getPrchsId();
							prchsDt = historyRes.getHistoryList().get(i).getPrchsDt();
							useExprDt = historyRes.getHistoryList().get(i).getUseExprDt();
							dwldStartDt = historyRes.getHistoryList().get(i).getDwldStartDt();
							dwldExprDt = historyRes.getHistoryList().get(i).getDwldExprDt();
							prchsCaseCd = historyRes.getHistoryList().get(i).getPrchsCaseCd();
							prchsProdId = historyRes.getHistoryList().get(i).getProdId();
							prchsPrice = historyRes.getHistoryList().get(i).getProdAmt();
							drmYn = historyRes.getHistoryList().get(i).getDrmYn();

							// 구매상태 확인
							comicReq.setDwldStartDt(dwldStartDt);
							comicReq.setDwldExprDt(dwldExprDt);
							prchsState = (String) ((HashMap) this.commonDAO.queryForObject(
									"Download.getDownloadPurchaseState", comicReq)).get("PURCHASE_STATE");

							// 구매상태 만료여부 확인
							if (!DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsState)) {
								// 구매 및 선물 여부 확인
								if (DisplayConstants.PRCHS_CASE_PURCHASE_CD.equals(prchsCaseCd)) {
									prchsState = "payment";
								} else if (DisplayConstants.PRCHS_CASE_GIFT_CD.equals(prchsCaseCd)) {
									prchsState = "gift";
								}
							}

							this.logger.info("----------------------------------------------------------------");
							this.logger.info("[DownloadComicLog] prchsId : {}", prchsId);
							this.logger.info("[DownloadComicLog] prchsDt : {}", prchsDt);
							this.logger.info("[DownloadComicLog] useExprDt : {}", useExprDt);
							this.logger.info("[DownloadComicLog] dwldExprDt : {}", dwldExprDt);
							this.logger.info("[DownloadComicLog] prchsCaseCd : {}", prchsCaseCd);
							this.logger.info("[DownloadComicLog] prchsState : {}", prchsState);
							this.logger.info("[DownloadComicLog] prchsProdId : {}", prchsProdId);
							this.logger.info("[DownloadComicLog] prchsPrice : {}", prchsPrice);
							this.logger.info("----------------------------------------------------------------");

							metaInfo.setPurchaseId(prchsId);
							metaInfo.setPurchaseProdId(prchsProdId);
							metaInfo.setPurchaseDt(prchsDt);
							metaInfo.setPurchaseState(prchsState);
							metaInfo.setPurchaseDwldExprDt(dwldExprDt);
							metaInfo.setPurchasePrice(Integer.parseInt(prchsPrice));

							// 구매 정보
							purchaseList.add(this.commonMetaInfoGenerator.generatePurchase(metaInfo));

							// 구매상태 만료 여부 확인
							if (!DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsState)) {
								String deviceId = null; // Device Id
								String deviceIdType = null; // Device Id 유형
								SearchDeviceIdSacReq deviceReq = null;
								SearchDeviceIdSacRes deviceRes = null;
								boolean memberPassFlag = true;

								try {
									deviceReq = new SearchDeviceIdSacReq();
									deviceReq.setUserKey(comicReq.getUserKey());
									deviceReq.setDeviceKey(comicReq.getDeviceKey());

									this.logger.info("--------------------------------------------------------------");
									this.logger.info("[DownloadComicLog] 단말정보 조회 요청 파라미터");
									this.logger.info("--------------------------------------------------------------");
									this.logger.info("[DownloadComicLog] userKey : {}", deviceReq.getUserKey());
									this.logger.info("[DownloadComicLog] deviceKey : {}", deviceReq.getDeviceKey());
									this.logger.info("--------------------------------------------------------------");

									// 기기정보 조회
									this.logger
											.info("##### [SAC DSP LocalSCI] SAC Member Start : deviceSCI.searchDeviceId");
									long start = System.currentTimeMillis();
									deviceRes = this.deviceSCI.searchDeviceId(deviceReq);
									this.logger
											.info("##### [SAC DSP LocalSCI] SAC Member End : deviceSCI.searchDeviceId");
									long end = System.currentTimeMillis();
									this.logger.info(
											"##### [SAC DSP LocalSCI] SAC Member deviceSCI.searchDeviceId takes {} ms",
											(end - start));

								} catch (Exception ex) {
									memberPassFlag = false;
									this.logger.error("단말정보 조회 연동 중 오류가 발생하였습니다.\n", ex);
								}

								this.logger.info("----------------------------------------------------------------");
								this.logger.info("[DownloadComicLog] memberPassFlag : {}", memberPassFlag);
								this.logger.info("[DownloadComicLog] deviceRes : {}", deviceRes.toString());
								this.logger.info("----------------------------------------------------------------");

								if (memberPassFlag && deviceRes != null) {
									deviceId = deviceRes.getDeviceId();
									deviceIdType = this.commonService.getDeviceIdType(deviceId);

									metaInfo.setExpiredDate(reqExpireDate);
									metaInfo.setUseExprDt(useExprDt);
									metaInfo.setUserKey(userKey);
									metaInfo.setDeviceKey(deviceKey);
									metaInfo.setDeviceType(deviceIdType);
									metaInfo.setDeviceSubKey(deviceId);

									// 구매시점 DRM 여부값으로 세팅
									if (StringUtils.isNotEmpty(drmYn)) {
										metaInfo.setDrmYn(drmYn);
									}

									// 암호화 정보 (JSON)
									EncryptionContents contents = this.encryptionGenerator
											.generateEncryptionContents(metaInfo);

									// JSON 파싱
									MarshallingHelper marshaller = new JacksonMarshallingHelper();
									byte[] jsonData = marshaller.marshal(contents);

									// JSON 암호화
									byte[] encryptByte = this.downloadAES128Helper.encryption(jsonData);
									String encryptString = this.downloadAES128Helper.toHexString(encryptByte);

									// 암호화 정보 (AES-128)
									Encryption encryption = new Encryption();
									encryption.setProductId(prchsProdId);
									byte[] digest = this.downloadAES128Helper.getDigest(jsonData);
									encryption.setDigest(this.downloadAES128Helper.toHexString(digest));
									encryption.setKeyIndex(String.valueOf(this.downloadAES128Helper.getSacRandomNo()));
									encryption.setToken(encryptString);
									encryptionList.add(encryption);

									this.logger.info("-------------------------------------------------------------");
									this.logger.info("[DownloadComicLog] token : {}", encryption.getToken());
									this.logger.info("[DownloadComicLog] keyIdx : {}", encryption.getKeyIndex());
									this.logger.info("-------------------------------------------------------------");
								}
							}
						}
						// 구매 정보
						product.setPurchaseList(purchaseList);

						// 암호화 정보
						if (!encryptionList.isEmpty()) {
							product.setDl(encryptionList);
						}
					}
				}
			}

			comicRes.setProduct(product);
			commonResponse.setTotalCount(1);
		} else {
			throw new StorePlatformException("SAC_DSP_0009");
		}

		comicRes.setCommonResponse(commonResponse);
		return comicRes;
	}
}
