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

import com.google.common.base.Strings;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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

    private static SecretKeySpec signingKey = null;

    static {
        try {
            signingKey = new SecretKeySpec(secretKey.getBytes(ENCODING), HMAC_SHA1_ALGORITHM);
        }
        catch (UnsupportedEncodingException e) {
            logger.error("DisplayCryptUtils 초기화시 에러 발생", e);
        }
    }

    public static SacSha1Mac createSha1Mac() {
        Mac mac;

        // Mac Instance
        try {
            mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);
        }
        catch (NoSuchAlgorithmException e) {
            logger.error("Mac생성시 에러", e);
            throw new IllegalStateException("Mac 생성중 오류가 발생했습니다.", e);
        }
        catch (InvalidKeyException e) {
            logger.error("Mac생성시 에러", e);
            throw new IllegalStateException("Mac 생성중 오류가 발생했습니다.", e);
        }

        return new SacSha1Mac(mac);
    }

    public static class SacSha1Mac {
        private Mac mac;
        private SacSha1Mac(Mac mac) {
            if(mac == null)
                throw new IllegalArgumentException();

            this.mac = mac;
        }

        public String hashPkgNm(String nm) {

            String result;

            try {
                // compute the hmac on input data bytes
                byte[] rawHmac = mac.doFinal(nm.getBytes(ENCODING));

                // base64-encode the hmac
                result = new String(Base64.encodeBase64(rawHmac));
            }
            catch (Exception e) {
                logger.error("패키지명 해쉬 적용중 에러: {}", nm, e);
                return nm;
            }

            return result;
        }

        /**
         * 28자 문자열이 응답됨
         * @param mdn
         * @param aid
         * @return
         */
        public String hashMdnAidKey(String mdn, String aid) {

            String result;
            if(Strings.isNullOrEmpty(mdn) || Strings.isNullOrEmpty(aid))
                return "";

            String rawKey = mdn + "|" + aid;
            try {

                // compute the hmac on input data bytes
                byte[] rawHmac = mac.doFinal(rawKey.getBytes(ENCODING));

                // base64-encode the hmac
                result = new String(Base64.encodeBase64(rawHmac));
            }
            catch (Exception e) {
                logger.error("Mdn+Aid 해쉬 적용중 에러: {}", rawKey, e);
                return "";
            }

            return result;
        }
    }
}
