/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.common;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * <p>
 * DisplayCryptUtils
 * </p>
 * Updated on : 2014. 07. 02 Updated by : 정희원, SK 플래닛.
 */
public class DisplayCryptUtils {

    private static final Logger logger = LoggerFactory.getLogger(DisplayCryptUtils.class);

    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    private static final String ENCODING = "UTF-8";
    private static final String secretKey = "SKPTSTORESAC";

    private static Mac mac = null;

    private static Mac getSha1Mac() throws Exception {
        if(mac == null) {
            try {
                SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(ENCODING), HMAC_SHA1_ALGORITHM);

                // Mac Instance
                mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
                mac.init(signingKey);
            }
            catch (Exception e) {
                logger.error("초기화 에러", e);
                throw e;
            }
        }
        return mac;
    }

    public static String hashPkgNm(String nm) {

        String result;

        try {
            // compute the hmac on input data bytes
            byte[] rawHmac = getSha1Mac().doFinal(nm.getBytes(ENCODING));

            // base64-encode the hmac
            result = new String(Base64.encodeBase64(rawHmac));
        }
        catch (Exception e) {
            logger.error("패키지명 해쉬 적용중 에러: {}", nm, e);
            return nm;
        }

        return result;
    }
}
