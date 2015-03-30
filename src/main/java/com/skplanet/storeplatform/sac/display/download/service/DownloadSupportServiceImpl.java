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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Encryption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.EncryptionContents;
import com.skplanet.storeplatform.sac.display.common.DisplayCryptUtils;
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

    private static final Logger logger = LoggerFactory.getLogger(DownloadSupportServiceImpl.class);

    @Autowired
    private EncryptionGenerator encryptionGenerator;

    @Autowired
    private DownloadAES128Helper downloadAES128Helper;

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

        logger.info("DownloadResult: prodId={}, userKey={}, deviceKey={}, elapTime={}ms, dlEnc={}"
                ,prodId, userKey, deviceKey, elapTime, dlEnc);
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

        logger.debug("Encryption={}", ReflectionToStringBuilder.reflectionToString(encryption));

        return encryption;
    }

    @Override
    public void createUserDownloadInfo(String mdn, String aid, String tenantId, String prodId) {
        Map<String, Object> req = new HashMap<String, Object>();

        String key = DisplayCryptUtils.hashMdnAidKey(mdn, aid);
        if (Strings.isNullOrEmpty(key)) {
            logger.error("사용자의 다운로드 이력을 저장하지 못했습니다. (mdn:{}, aid:{}, tenantId:{})", mdn, aid, tenantId);
            return;
        }

        logger.info("Regist_UD:{},{},{},{}", mdn,aid,key,tenantId);

        req.put("mdnaidKey", key);
        req.put("aid", aid);
        req.put("prodId", prodId);
        req.put("tenantId", tenantId);

        commonDAO.update("Download.updateUserDownloadInfo", req);
    }
}
