package com.skplanet.storeplatform.member.common.crypto;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * IDP crpto.
 */
public class CryptoCode {

    public String getCryptoEncode(String inputValue, String alg) throws Exception {

        if (inputValue == null) {
            throw new Exception("Can't conver to " + alg + " String value!!");
        }
        byte[] ret = digest(alg, inputValue.getBytes());
        String result = encode(ret);
        return result;
    }

    public String getCryptoEncodeHex(String inputValue, String alg) throws Exception {

        if (inputValue == null) {
            throw new Exception("Can't conver to " + alg + " String value!!");
        }
        byte[] ret = digest(alg, inputValue.getBytes());
        String result = toHex(ret);
        return result;
    }

    public String getCryptoMD5Encode(String inputValue) throws Exception {

        if (inputValue == null) {
            throw new Exception("Can't conver to Message Digest 5 String value!!");
        }
        byte[] ret = digest("MD5", inputValue.getBytes());
        String result = encode(ret);
        return result;
    }

    public String getCryptoMD5EncodeHex(String inputValue) throws Exception {

        if (inputValue == null) {
            throw new Exception("Can't conver to Message Digest 5 String value!!");
        }
        byte[] ret = digest("MD5", inputValue.getBytes());
        String result = toHex(ret);
        return result;
    }

    public String getCryptoSHA1Encode(String inputValue) throws Exception {

        if (inputValue == null) {
            throw new Exception("Can't conver to Secure Hash Standard String value!!");
        }
        byte[] ret = digest("SHA1", inputValue.getBytes());
        String result = encode(ret);
        return result;
    }

    public String getCryptoSHA1EncodeHex(String inputValue) throws Exception {

        if (inputValue == null) {
            throw new Exception("Can't conver to Secure Hash Standard String value!!");
        }
        byte[] ret = digest("SHA1", inputValue.getBytes());
        String result = toHex(ret);
        return result;
    }

    /*************** SHA-256 start*******************/
    public String getCryptoSHA256Encode(String inputValue) throws Exception {

        if (inputValue == null) {
            throw new Exception("Can't conver to Message Digest 5 String value!!");
        }
        byte[] ret = digest("SHA-256", inputValue.getBytes());
        String result = encode(ret);
        return result;
    }

    public String getCryptoSHA256EncodeHex(String inputValue) throws Exception {

        if (inputValue == null) {
            throw new Exception("Can't conver to Message Digest 5 String value!!");
        }
        byte[] ret = digest("SHA-256", inputValue.getBytes());
        String result = toHex(ret);
        return result;
    }

    public String getCryptoSHA256EncodeHexLoop(String inputValue) throws Exception {

        if (inputValue == null) {
            throw new Exception("Can't conver to Message Digest 5 String value!!");
        }
        byte[] ret = digest("SHA-256", inputValue.getBytes());
        String result = toHex(ret);
        int count = 99;
        for (int i=0; i< count; i++){
            ret =digest("SHA-256", result.getBytes());
            result = toHex(ret);
        }
        return result;
    }
    /*************** SHA-256 end*******************/
    public byte[] digest(String alg, byte[] input) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance(alg);
        return md.digest(input);
    }

    public String encode(byte[] hash) {

        BASE64Encoder base64Encoder = new BASE64Encoder();
        ByteArrayInputStream bin = new ByteArrayInputStream(hash);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        String ret = null;
        try {
            base64Encoder.encodeBuffer(bin, bout);
            byte[] buf = bout.toByteArray();
            ret = new String(buf).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public byte[] decode(String strDecode) {

        BASE64Decoder base64Decoder = new BASE64Decoder();
        ByteArrayInputStream bin = new ByteArrayInputStream(strDecode.getBytes());
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buf = null;
        try {
            base64Decoder.decodeBuffer(bin, bout);
            buf = bout.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buf;
    }

    private String toHex(byte hash[]) {

        StringBuffer buf = new StringBuffer(hash.length * 2);
        for (int i = 0; i < hash.length; i++) {
            int intVal = hash[i] & 0xff;
            if (intVal < 0x10) {
                // append a zero before a one digit hex
                // number to make it two digits.
                buf.append("0");
            }
            buf.append(Integer.toHexString(intVal));
        }
        return buf.toString();
    }

    public String generateSecretKey() throws Exception {

        KeyGenerator keygen = KeyGenerator.getInstance("HmacSHA1", "SunJCE");
        keygen.init(128);
        SecretKey skey = keygen.generateKey();
        return toHex(skey.getEncoded());
    }

    public String generateMacSignature(String key, String message) throws Exception {

        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(skeySpec);
        byte[] result = mac.doFinal(message.getBytes("utf-8"));
        return toHex(result);
    }

    public String generateSalt(String key) throws Exception {

        String message = "gerotorl18nom";
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(skeySpec);
        byte[] result = mac.doFinal(message.getBytes("euc-kr"));
        return toHex(result);
    }

    public String generateIdpUserPW(String userPw) throws Exception {

        CryptoCode cryptoCode = new CryptoCode();

        return cryptoCode.getCryptoSHA256Encode(
                cryptoCode.generateSalt(cryptoCode.getCryptoMD5Encode(userPw)));

    }

}
