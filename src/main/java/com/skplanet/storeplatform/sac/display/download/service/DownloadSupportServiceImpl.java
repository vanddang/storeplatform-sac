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

import com.google.common.base.Strings;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.purchase.client.history.sci.PurchaseDrmInfoSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.PurchaseDrmInfoScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.PurchaseDrmInfoScRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.GiftConfirmInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.GiftConfirmSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.GiftConfirmSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistorySacIn;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Encryption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.EncryptionContents;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.DisplayCryptUtils;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.EncryptionGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * DownloadSupportServiceImpl
 * </p>
 * Updated on : 2014. 07. 10 Updated by : 정희원, SK 플래닛.
 */
@Service
public class DownloadSupportServiceImpl implements DownloadSupportService {

    private static final Logger log = LoggerFactory.getLogger(DownloadSupportServiceImpl.class);

    @Autowired
    private EncryptionGenerator encryptionGenerator;

    @Autowired
    private DownloadAES128Helper downloadAES128Helper;

    @Autowired
    private PurchaseDrmInfoSCI purchaseDrmInfoSCI;

    @Autowired
    private GiftConfirmInternalSCI giftConfirmInternalSCI;

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Override
    public void logDownloadResult(String userKey, String deviceKey, String prodId, List<Encryption> encryptionList, long elapTime) {
        String dlEnc = "";

        if(CollectionUtils.isNotEmpty(encryptionList)) {
            List<String> encs = new ArrayList<String>(encryptionList.size());
            for (Encryption enc : encryptionList) {
                encs.add(ReflectionToStringBuilder.toString(enc, ToStringStyle.SHORT_PREFIX_STYLE));
            }
            dlEnc = StringUtils.join(encs, ",");
        }

        log.info("DownloadResult: prodId={}, userKey={}, deviceKey={}, elapTime={}ms, dlEnc={}"
                , prodId, userKey, deviceKey, elapTime, dlEnc);
    }

    @Override
    public Encryption generateEncryption(MetaInfo metaInfo, String prchProdId) {
        return generateEncryption(metaInfo, prchProdId, false);
    }

    @Override
    public Encryption generateEncryption(MetaInfo metaInfo, String prchProdId, boolean supportFhdVideo) {
        EncryptionContents contents = this.encryptionGenerator.generateEncryptionContents(metaInfo, supportFhdVideo);
        JSONObject jsonObject = new JSONObject(contents);
        byte[] jsonData = jsonObject.toString().getBytes(Charset.forName("UTF-8"));

        // JSON 암호화
        int keyIdx = this.downloadAES128Helper.getRandomKeyIndex();

        byte[] encryptByte = this.downloadAES128Helper.encryption(keyIdx, jsonData);

        String encryptString = this.downloadAES128Helper.toHexString(encryptByte);

        // 암호화 정보 (AES-128)
        Encryption encryption = new Encryption();
        encryption.setProductId(prchProdId);
        byte[] digest = this.downloadAES128Helper.getDigest(jsonData);
        encryption.setDigest(this.downloadAES128Helper.toHexString(digest));
        encryption.setKeyIndex(String.valueOf(keyIdx));
        encryption.setToken(encryptString);
        
        log.debug("Encryption={}", ReflectionToStringBuilder.reflectionToString(encryption));

        return encryption;
    }
    
	@Override
	public Encryption generateEncryptionV2(MetaInfo metaInfo, String prchProdId, boolean supportFhdVideo,
			boolean unlimitedDrmExpireDt) {
		EncryptionContents contents = this.encryptionGenerator.generateEncryptionContentsV2(metaInfo, supportFhdVideo,
				unlimitedDrmExpireDt);
		JSONObject jsonObject = new JSONObject(contents);
		byte[] jsonData = jsonObject.toString().getBytes(Charset.forName("UTF-8"));

		// JSON 암호화
		int keyIdx = this.downloadAES128Helper.getRandomKeyIndex();

		byte[] encryptByte = this.downloadAES128Helper.encryption(keyIdx, jsonData);

		String encryptString = this.downloadAES128Helper.toHexString(encryptByte);

		// 암호화 정보 (AES-128)
		Encryption encryption = new Encryption();
		encryption.setProductId(prchProdId);
		byte[] digest = this.downloadAES128Helper.getDigest(jsonData);
		encryption.setDigest(this.downloadAES128Helper.toHexString(digest));
		encryption.setKeyIndex(String.valueOf(keyIdx));
		encryption.setToken(encryptString);

		log.debug("Encryption={}", ReflectionToStringBuilder.reflectionToString(encryption));

		return encryption;
	}    

    @Override
    public void createUserDownloadInfo(String mdn, String aid, String tenantId, String prodId) {
        Map<String, Object> req = new HashMap<String, Object>();

        String key = DisplayCryptUtils.hashMdnAidKey(mdn, aid);
        if (Strings.isNullOrEmpty(key)) {
            log.error("사용자의 다운로드 이력을 저장하지 못했습니다. (mdn:{}, aid:{}, tenantId:{})", mdn, aid, tenantId);
            return;
        }

        log.info("Regist_UD:{},{},{},{}", mdn, aid, key, tenantId);

        req.put("mdnaidKey", key);
        req.put("aid", aid);
        req.put("prodId", prodId);
        req.put("tenantId", tenantId);

        try {
            commonDAO.update("Download.mergeUserDownloadInfo", req);
        }
        catch (DuplicateKeyException e) {
            // NOOP
        }
    }

    @Override
    public void mapPurchaseDrmInfo(MetaInfo metaInfo) {
        PurchaseDrmInfoScReq req = new PurchaseDrmInfoScReq();
        req.setUserKey(metaInfo.getUserKey());
        req.setTenantId(metaInfo.getTenantId());
        req.setSystemId(metaInfo.getSystemId());
        req.setPrchsId(metaInfo.getPurchaseId());
        req.setProdId(metaInfo.getPurchaseProdId());

        /**
         * 다운로드 여부에 따른 DRM 정보 수정시 DL Token의 만료일(요청만료일)에 대한 수정이 필요한 것이 아니라 사용만료일에 대한 기간 재산정이 필요
         * Update By 2015.09.16 이석희 I-S PLUS
         */
        PurchaseDrmInfoScRes drmInfoScRes = purchaseDrmInfoSCI.updatePrchaseDrm(req);
        if(drmInfoScRes != null && "Y".equals(drmInfoScRes.getResultYn()))
//            metaInfo.setExpiredDate(drmInfoScRes.getUseExprDt());
        	 metaInfo.setUseExprDt(drmInfoScRes.getUseExprDt());
    }

    @Override
    public boolean isTfreemiumPurchase(String prchsReqPathCd) {
        //-	"prchsReqPathCd": "OR0004xx",
        //-	OR000413, OR000420 2개 코드가 T Freemium을 통한 구매건임.
        return StringUtils.equals(DisplayConstants.PRCHS_REQ_PATH_TFREEMIUM1_CD, prchsReqPathCd)
                || StringUtils.equals(DisplayConstants.PRCHS_REQ_PATH_TFREEMIUM2_CD, prchsReqPathCd);
    }

    // 선물인경우 다운로드 시점에 만료기간을 reset한다.
    // 이는 선물 받은 상품이 다운로드 하는 시점에 만료가 되어 사용할 수 없게 되는 것을 방지하기 위함이다.
    @Override
    public boolean resetExprDtOfGift(HistorySacIn historySacIn,
                                  SacRequestHeader header,
                                  String userKey,
                                  String deviceKey,
                                  String prodId,
                                  String sysDate,
                                  String prchsState) {

        if (prchsState.equals("gift") && StringUtils.isEmpty(historySacIn.getRecvDt()) ){

            GiftConfirmSacInReq req = new GiftConfirmSacInReq();

            req.setTenantId(header.getTenantHeader().getTenantId());
            req.setSystemId(header.getTenantHeader().getSystemId());
            req.setUserKey(userKey);
            req.setDeviceKey(deviceKey);
            req.setProdId(prodId);
            req.setPrchsId(historySacIn.getPrchsId());
            req.setRecvDt(sysDate);
            req.setRecvConfPathCd("OR003904");  // TODO 매핑룰 추가 필요.

            GiftConfirmSacInRes res = giftConfirmInternalSCI.modifyGiftConfirm(req);

            historySacIn.setUseStartDt(res.getUseStartDt());
            historySacIn.setUseExprDt(res.getUseExprDt());
            historySacIn.setDwldStartDt(res.getDwldStartDt());
            historySacIn.setDwldExprDt(res.getDwldExprDt());

            return true;
        }
        return false;
    }
}
