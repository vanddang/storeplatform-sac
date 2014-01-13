package com.skplanet.storeplatform.sac.runtime.acl.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.security.SignatureException;

import org.junit.Test;

public class HmacSha1UtilTest {

	@Test
	public void getSignature_and_isValidSignatureAndTimestampAndNonce() {
		String baseUrl = "http://store.skplanet.com/sac";
		String tenantKey = "9Coum1NDRACh2v7eoYxfaA";
		String timestamp = AuthUtil.getTimestamp();
		String nonce = AuthUtil.getNonce();

		String secretKey = "9Coum1NDRACh2v7eoYxfaA"; //Test

		String urlForAuth = AuthUtil.getUrlForAuth(baseUrl, tenantKey, timestamp, nonce);

		String signature = null;

		try {
			//Signature 생성
			signature = HmacSha1Util.getSignature(urlForAuth, secretKey);
			System.out.println("<getSignature_and_isValidSignatureAndTimestampAndNonce> signature="+signature);
			assertNotNull(signature);

			//sv1/Cv2YaMx4MLmuCD6Sp6BEI50=
			//sv1/Cv2YaMx4MLmuCD6Sp6BEI50=

			boolean isValid = HmacSha1Util.isValidSignature(signature, urlForAuth, secretKey);
			System.out.println("<getSignature_and_isValidSignatureAndTimestampAndNonce> isValidSignature="+isValid);
			assertTrue(isValid);


			isValid = HmacSha1Util.isValidTimestampAndNonce(timestamp, nonce);
			System.out.println("<getSignature_and_isValidSignatureAndTimestampAndNonce> isValidTimestampAndNonce="+isValid);
			assertTrue(isValid);

		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void isValidTimestampAndNonce_valid() {
		String timestamp = AuthUtil.getTimestamp();
		String nonce = AuthUtil.getNonce();

		try {
			//timestamp : 5분 동안 Valid
			//nonce : 무조건 성공
			boolean isValid = HmacSha1Util.isValidTimestampAndNonce(timestamp, nonce);
			System.out.println("<isValidTimestampAndNonce_valid>="+isValid);
			assertTrue(isValid);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void isValidTimestampAndNonce_invalid() {
		String nonce = AuthUtil.getNonce();

		try {
			//timestamp : 6분 이전. invalid
			//nonce : 무조건 성공
			long nTimestamp = System.currentTimeMillis() - 6 * 60 * 1000L;
			boolean isValid = HmacSha1Util.isValidTimestampAndNonce(nTimestamp+"", nonce);
			System.out.println("<isValidTimestampAndNonce_invalid>="+isValid);
			assertFalse(isValid);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
