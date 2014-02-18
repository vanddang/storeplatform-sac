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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.framework.test.JacksonMarshallingHelper;
import com.skplanet.storeplatform.framework.test.MarshallingHelper;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadEbookSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadEbookSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.DeviceSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.ProductListSacIn;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
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
 * DownloadEbook Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 1. 28. Updated by : 이태희.
 */
@Service
public class DownloadEbookServiceImpl implements DownloadEbookService {
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
	 * com.skplanet.storeplatform.sac.display.download.service.DownloadEbookService#getDownloadEbookInfo(com.skplanet
	 * .storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.display.vo.download.DownloadEbookSacReq)
	 */
	@Override
	public DownloadEbookSacRes getDownloadEbookInfo(SacRequestHeader requestHeader, DownloadEbookSacReq ebookReq) {
		// 현재일시 및 요청만료일시 조회
		MetaInfo metaInfo = (MetaInfo) this.commonDAO.queryForObject("Download.selectDownloadSystemDate", null);

		String sysDate = metaInfo.getSysDate();
		String reqExpireDate = metaInfo.getExpiredDate();
		metaInfo = null;

		DownloadEbookSacRes ebookRes = new DownloadEbookSacRes();
		CommonResponse commonResponse = new CommonResponse();

		String idType = ebookReq.getIdType();
		String productId = ebookReq.getProductId();
		String deviceKey = ebookReq.getDeviceKey();
		String userKey = ebookReq.getUserKey();

		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("[getDownloadEbookInfo] idType : {}", idType);
		this.logger.debug("[getDownloadEbookInfo] productId : {}", productId);
		this.logger.debug("[getDownloadEbookInfo] deviceKey : {}", deviceKey);
		this.logger.debug("[getDownloadEbookInfo] userKey : {}", userKey);
		this.logger.debug("----------------------------------------------------------------");

		// 필수 파라미터 체크
		if (StringUtils.isEmpty(idType)) {
			throw new StorePlatformException("SAC_DSP_0002", "idType", idType);
		}
		if (StringUtils.isEmpty(productId)) {
			throw new StorePlatformException("SAC_DSP_0002", "productId", productId);
		}
		if (StringUtils.isEmpty(deviceKey)) {
			throw new StorePlatformException("SAC_DSP_0002", "deviceKey", deviceKey);
		}
		if (StringUtils.isEmpty(userKey)) {
			throw new StorePlatformException("SAC_DSP_0002", "userKey", userKey);
		}
		// ID유형 유효값 체크
		if (!"channel".equals(idType) && !"episode".equals(idType)) {
			throw new StorePlatformException("SAC_DSP_0003", "idType", idType);
		}

		// 헤더정보 세팅
		ebookReq.setTenantId(requestHeader.getTenantHeader().getTenantId());
		ebookReq.setLangCd(requestHeader.getTenantHeader().getLangCd());
		ebookReq.setDeviceModelCd(requestHeader.getDeviceHeader().getModel());
		ebookReq.setImageCd(DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);

		// ebook 상품 정보 조회(for download)
		metaInfo = (MetaInfo) this.commonDAO.queryForObject("Download.selectDownloadEbookInfo", ebookReq);

		if (metaInfo != null) {
			Product product = new Product();

			// 상품 ID 정보
			metaInfo.setContentsTypeCd(DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
			product.setIdentifierList(this.commonMetaInfoGenerator.generateIdentifierList(metaInfo));

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

			// 이용등급 및 소장/대여 정보
			product.setRights(this.commonMetaInfoGenerator.generateRights(metaInfo));

			// 배포자 정보
			product.setDistributor(this.commonMetaInfoGenerator.generateDistributor(metaInfo));

			// 저작자 정보
			product.setContributor(this.ebookComicGenerator.generateEbookContributor(metaInfo));

			// 구매내역 조회를 위한 생성자
			ProductListSacIn productListSacIn = null;
			List<ProductListSacIn> productList = null;
			HistoryListSacInReq historyReq = null;
			HistoryListSacInRes historyRes = null;

			try {
				productListSacIn = new ProductListSacIn();
				productList = new ArrayList<ProductListSacIn>();

				// 소장 상품ID
				productListSacIn.setProdId(metaInfo.getStoreProdId());
				productList.add(productListSacIn);

				// 대여 상품ID
				productListSacIn = new ProductListSacIn();
				productListSacIn.setProdId(metaInfo.getPlayProdId());
				productList.add(productListSacIn);

				historyReq = new HistoryListSacInReq();
				historyReq.setTenantId(ebookReq.getTenantId());
				historyReq.setUserKey(ebookReq.getUserKey());
				historyReq.setDeviceKey(ebookReq.getDeviceKey());
				historyReq.setPrchsProdHaveYn(DisplayConstants.PRCHS_PROD_HAVE_YES);
				historyReq.setPrchsProdType(DisplayConstants.PRCHS_PROD_TYPE_UNIT);
				historyReq.setStartDt(DisplayConstants.PRCHS_START_DATE);
				historyReq.setEndDt(sysDate);
				historyReq.setOffset(1);
				historyReq.setCount(1);
				historyReq.setProductList(productList);

				// 구매내역 조회 실행
				historyRes = this.historyInternalSCI.searchHistoryList(historyReq);
			} catch (Exception ex) {
				throw new StorePlatformException("SAC_DSP_0001", "구매내역 조회 ", ex);
			}

			String prchsId = null; // 구매ID
			String prchsDt = null; // 구매일시
			String useExprDt = null; // 이용 만료일시
			String dwldExprDt = null; // 다운로드 만료일시
			String prchsCaseCd = null; // 선물 여부
			String prchsState = null; // 구매상태
			String prchsProdId = null; // 구매 상품ID

			if (historyRes != null && historyRes.getTotalCnt() > 0) {
				List<Purchase> purchaseList = new ArrayList<Purchase>();
				List<Encryption> encryptionList = new ArrayList<Encryption>();

				for (int i = 0; i < historyRes.getTotalCnt(); i++) {
					prchsId = historyRes.getHistoryList().get(i).getPrchsId();
					prchsDt = historyRes.getHistoryList().get(i).getPrchsDt();
					useExprDt = historyRes.getHistoryList().get(i).getUseExprDt();
					dwldExprDt = historyRes.getHistoryList().get(i).getDwldExprDt();
					prchsCaseCd = historyRes.getHistoryList().get(i).getPrchsCaseCd();
					prchsProdId = historyRes.getHistoryList().get(i).getProdId();

					// 구매상태 확인
					ebookReq.setPrchsDt(prchsDt);
					ebookReq.setDwldExprDt(dwldExprDt);
					prchsState = (String) this.commonDAO.queryForObject("Download.getDownloadPurchaseState", ebookReq);

					// 구매상태 만료여부 확인
					if (!DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsState)) {
						// 구매 및 선물 여부 확인
						if (DisplayConstants.PRCHS_CASE_PURCHASE_CD.equals(prchsCaseCd)) {
							prchsState = "payment";
						} else if (DisplayConstants.PRCHS_CASE_GIFT_CD.equals(prchsCaseCd)) {
							prchsState = "gift";
						}
					}

					this.logger.debug("----------------------------------------------------------------");
					this.logger.debug("[getDownloadEbookInfo] prchsId : {}", prchsId);
					this.logger.debug("[getDownloadEbookInfo] prchsDt : {}", prchsDt);
					this.logger.debug("[getDownloadEbookInfo] useExprDt : {}", useExprDt);
					this.logger.debug("[getDownloadEbookInfo] dwldExprDt : {}", dwldExprDt);
					this.logger.debug("[getDownloadEbookInfo] prchsCaseCd : {}", prchsCaseCd);
					this.logger.debug("[getDownloadEbookInfo] prchsState : {}", prchsState);
					this.logger.debug("[getDownloadEbookInfo] prchsProdId : {}", prchsProdId);
					this.logger.debug("----------------------------------------------------------------");

					metaInfo.setPurchaseId(prchsId);
					metaInfo.setPurchaseProdId(prchsProdId);
					metaInfo.setPurchaseDt(prchsDt);
					metaInfo.setPurchaseState(prchsState);
					metaInfo.setPurchaseDwldExprDt(dwldExprDt);

					// 구매 정보
					purchaseList.add(this.commonMetaInfoGenerator.generatePurchase(metaInfo));

					// 구매상태 만료 여부 확인
					if (!DisplayConstants.PRCHS_STATE_TYPE_EXPIRED.equals(prchsState)) {
						String deviceId = null; // Device Id
						String deviceIdType = null; // Device Id 유형
						SearchDeviceIdSacReq deviceReq = null;
						SearchDeviceIdSacRes deviceRes = null;

						try {
							deviceReq = new SearchDeviceIdSacReq();
							deviceReq.setUserKey(ebookReq.getUserKey());
							deviceReq.setDeviceKey(ebookReq.getDeviceKey());

							// 기기정보 조회
							deviceRes = this.deviceSCI.searchDeviceId(deviceReq);
						} catch (Exception ex) {
							throw new StorePlatformException("SAC_DSP_0001", "기기정보 조회 ", ex);
						}

						if (deviceRes != null) {
							deviceId = deviceRes.getDeviceId();
							deviceIdType = this.commonService.getDeviceIdType(deviceId);

							metaInfo.setExpiredDate(reqExpireDate);
							metaInfo.setUseExprDt(useExprDt);
							metaInfo.setUserKey(userKey);
							metaInfo.setDeviceKey(deviceKey);
							metaInfo.setDeviceType(deviceIdType);
							metaInfo.setDeviceSubKey(deviceId);

							// 소장, 대여 구분(Store : 소장, Play : 대여)
							if (prchsProdId.equals(metaInfo.getStoreProdId())) {
								metaInfo.setDrmYn(metaInfo.getStoreDrmYn());
								metaInfo.setProdChrg(metaInfo.getStoreProdChrg());
							} else {
								metaInfo.setDrmYn(metaInfo.getPlayDrmYn());
								metaInfo.setProdChrg(metaInfo.getPlayProdChrg());
							}

							// 인터파크 DRM 번호
							if (StringUtils.isNotEmpty(metaInfo.getBpJoinFileNo())) {
								metaInfo.setBpJoinFileType(DisplayConstants.DP_FORDOWNLOAD_BP_EBOOK_TYPE);
							} else {
								metaInfo.setBpJoinFileType(DisplayConstants.DP_FORDOWNLOAD_BP_DEFAULT_TYPE);
							}

							// 암호화 정보 (JSON)
							EncryptionContents contents = this.encryptionGenerator.generateEncryptionContents(metaInfo);

							// JSON 파싱
							MarshallingHelper marshaller = new JacksonMarshallingHelper();
							byte[] jsonData = marshaller.marshal(contents);

							// JSON 암호화
							byte[] encryptByte = this.downloadAES128Helper.encryption(jsonData);
							String encryptString = this.downloadAES128Helper.toHexString(encryptByte);

							// 암호화 정보 (AES-128)
							Encryption encryption = new Encryption();
							encryption.setProductId(prchsProdId);
							encryption.setDigest(DisplayConstants.DP_FORDOWNLOAD_ENCRYPT_DIGEST);
							encryption.setKeyIndex(String.valueOf(this.downloadAES128Helper.getSAC_RANDOM_NUMBER()));
							encryption.setToken(encryptString);
							encryptionList.add(encryption);

							// JSON 복호화
							byte[] decryptString = this.downloadAES128Helper.convertBytes(encryptString);
							byte[] decrypt = this.downloadAES128Helper.decryption(decryptString);

							try {
								String decData = new String(decrypt, "UTF-8");
								this.logger.debug("----------------------------------------------------------------");
								this.logger.debug("[getDownloadEbookInfo] decData : {}", decData);
								this.logger.debug("----------------------------------------------------------------");
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}
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

			ebookRes.setProduct(product);
			commonResponse.setTotalCount(1);
		} else {
			commonResponse.setTotalCount(0);
		}

		ebookRes.setCommonResponse(commonResponse);
		return ebookRes;
	}
}
