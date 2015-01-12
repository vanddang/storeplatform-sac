/**
 * 
 */
package com.skplanet.storeplatform.sac.member.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 유효성 검사 Utils.
 * 
 * Updated on : 2014. 2. 7. Updated by : 김다슬, 인크로스.
 */
public class ValidationCheckUtils {

	/**
	 * <pre>
	 * 휴대폰번호 유효성 검사 : 숫자 체크, 10자리/11자리 체크.
	 * </pre>
	 * 
	 * @param mdn
	 *            String
	 * @return boolean
	 */
	public static boolean isMdn(String mdn) {
		Pattern pattern = Pattern.compile("[0-9]{10,11}");
		Matcher matcher = pattern.matcher(mdn);

		return matcher.matches();
	}
}
