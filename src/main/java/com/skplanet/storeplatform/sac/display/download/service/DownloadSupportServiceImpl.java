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

import com.skplanet.storeplatform.framework.test.JacksonMarshallingHelper;
import com.skplanet.storeplatform.framework.test.MarshallingHelper;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Encryption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.EncryptionContents;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.EncryptionGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void logDownloadResult(String prodId, String dlEnc, long elapTime) {
        logger.info("DownloadResult: prodId={}, elapTime={}ms, dlEnc={}", prodId, elapTime, dlEnc);
    }

    @Override
    public Encryption generateEncryption(MetaInfo metaInfo, String prchProdId) {
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
        encryption.setProductId(prchProdId);
        byte[] digest = this.downloadAES128Helper.getDigest(jsonData);
        encryption.setDigest(this.downloadAES128Helper.toHexString(digest));
        encryption.setKeyIndex(String.valueOf(this.downloadAES128Helper
                .getSacRandomNo()));
        encryption.setToken(encryptString);
        return encryption;
    }
}
