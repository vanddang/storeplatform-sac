/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.api.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.Character.UnicodeBlock;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 공통적으로 필요한 데이터 변환기능 및 문자열 조작기능등이 구현되어 있음.
 */
public class StringUtil extends StringUtils {
	public static final int RIGHT = 1;
	public static final int LEFT = 2;

	/**
	 * 주어진 문자열을 이용하여 지정한 위치로부터 원하는 길이만큼의 문자열을 구함.
	 * 
	 * @param str
	 *            원하는 문자열 가지고 있는 문자열
	 * @param offset
	 *            원하는 문자열 시작위치 (1부터 시작)
	 * @param leng
	 *            원하는 문자열 길이
	 * @return 원하는 문자열 객체
	 */
	public static String subString(String str, int offset, int leng) {
		return new String(str.getBytes(), (offset - 1), leng);
	}

	/**
	 * 주어진 문자열을 이용하여 지정한 위치로부터 끝까지의 문자열을 구함.
	 * 
	 * @param str
	 *            원하는 문자열 가지고 있는 문자열
	 * @param offset
	 *            원하는 문자열 시작위치 (1부터 시작)
	 * @return 원하는 문자열 객체
	 */
	public static String subString(String str, int offset) {
		byte[] bytes = str.getBytes();
		int size = bytes.length - (offset - 1);
		return new String(bytes, (offset - 1), size);
	}

	/**
	 * 주어진 문자열을 대상으로하여 주어진 길이만큼의 문자열을 생성하여 리턴함.
	 * <p>
	 * 
	 * <pre>
	 * (예)
	 *  String str = "abcd";
	 *  log.info(StringUtil.fitString(str,6));
	 *  출력 = "abcd  "
	 * 
	 *  String str = "abcd";
	 *  log.info(StringUtil.fitString(str,3));
	 *  출력 = "abc"
	 * 
	 *  String str = "가나다라";
	 *  log.info(StringUtil.fitString(str,6));
	 *  출력 = "가나다"
	 * 
	 *  String str = "가나다라";
	 *  log.info(StringUtil.fitString(str,3));
	 *  출력 = "???"
	 * </pre>
	 * 
	 * @param str
	 *            대상 문자열
	 * @param size
	 *            만들고자 하는 문자열의 길이
	 * @return 주어진 길이만큼의 문자
	 */
	public static String fitString(String str, int size) {
		return fitString(str, size, StringUtil.LEFT);
	}

	/**
	 * 주어진 문자열을 대상으로하여 주어진 길이만큼의 문자열을 생성하여 리턴함.
	 * <p>
	 * 
	 * <pre>
	 * (예)
	 *  String str = "abcd";
	 *  log.info(StringUtil.fitString(str,6,StringUtil.RIGHT));
	 *  출력 = "  abcd"
	 * </pre>
	 * 
	 * @param str
	 *            대상 문자열
	 * @param size
	 *            만들고자 하는 문자열의 길이
	 * @param align
	 *            정열기준의 방향(RIGHT, LEFT)
	 * @return 주어진 길이만큼의 문자
	 */
	public static String fitString(String str, int size, int align) {
		byte[] bts = str.getBytes();
		int len = bts.length;
		if (len == size) {
			return str;
		}
		if (len > size) {
			String s = new String(bts, 0, size);
			if (s.length() == 0) {
				StringBuffer sb = new StringBuffer(size);
				for (int idx = 0; idx < size; idx++) {
					sb.append("?");
				}
				s = sb.toString();
			}
			return s;
		}
		if (len < size) {
			int cnt = size - len;
			char[] values = new char[cnt];
			for (int idx = 0; idx < cnt; idx++) {
				values[idx] = ' ';
			}
			if (align == StringUtil.RIGHT) {
				return String.copyValueOf(values) + str;
			} else {
				return str + String.copyValueOf(values);
			}
		}
		return str;
	}

	/**
	 * 문자열을 기본분리자(white space)로 분리하여 문자열배열을 생성함.
	 * 
	 * @param str
	 *            str
	 * @return 문자열 배열
	 */
	public static String[] toStringArray(String str) {
		Vector vt = new Vector();
		StringTokenizer st = new StringTokenizer(str);
		while (st.hasMoreTokens()) {
			vt.add(st.nextToken());
		}
		return toStringArray(vt);
	}

	/**
	 * Vector에 저장된 객체들을 이용하여 문자열 배열을 생성함.
	 * 
	 * @param vt
	 *            vt
	 * @return 문자열 배열
	 */
	public static String[] toStringArray(Vector vt) {
		String[] strings = new String[vt.size()];
		for (int idx = 0; idx < vt.size(); idx++) {
			strings[idx] = vt.elementAt(idx).toString();
		}
		return strings;
	}

	/**
	 * 주어진 문자열에서 지정한 문자열값을 지정한 문자열로 치환후 그결과 문자열을 리턴함.
	 * 
	 * @param src
	 *            src
	 * @param from
	 *            from
	 * @param to
	 *            to
	 * @return 문자열
	 */
	public static String replace(String src, String from, String to) {
		if (src == null)
			return null;
		if (from == null)
			return src;
		if (to == null)
			to = "";
		StringBuffer buf = new StringBuffer();
		for (int pos; (pos = src.indexOf(from)) >= 0;) {
			buf.append(src.substring(0, pos));
			buf.append(to);
			src = src.substring(pos + from.length());
		}
		buf.append(src);
		return buf.toString();
	}

	/**
	 * 주어진문자열이 지정한 길이를 초과하는 경우 짤라내고 '...'을 붙여 리턴함.
	 * 
	 * @param str
	 *            str
	 * @param limit
	 *            limit
	 * @return 문자열
	 */
	public static String cutString(String str, int limit) {

		if (str == null || limit < 4)
			return str;

		int len = str.length();
		int cnt = 0, index = 0;

		while (index < len && cnt < limit) {
			if (str.charAt(index++) < 256) // 1바이트 문자라면...
				cnt++; // 길이 1 증가
			else
				// 2바이트 문자라면...
				cnt += 2; // 길이 2 증가
		}

		if (index < len)
			str = str.substring(0, index - 1) + "...";

		return str;
	}

	/**
	 * 주어진 문자로 원하는 갯수만큼의 char[] 를 생성함.
	 * 
	 * @param c
	 *            생성할 문자
	 * @param cnt
	 *            생성할 갯수
	 * @return char array
	 */
	public static char[] makeCharArray(char c, int cnt) {
		char a[] = new char[cnt];
		Arrays.fill(a, c);
		return a;
	}

	/**
	 * 주어진 문자로 원하는 갯수만큼의 String 을 생성함.
	 * 
	 * @param c
	 *            생성할 문자
	 * @param cnt
	 *            생성할 갯수
	 * @return 원하는 갯수 많큼 생성된 문자열
	 */
	public static String getString(char c, int cnt) {
		return new String(makeCharArray(c, cnt));
	}

	// 2002-02-07 추가

	/**
	 * String의 좌측 공백을 없앤다.
	 * 
	 * @param lstr
	 *            대상 String
	 * @return String 결과 String
	 */
	public static String getLeftTrim(String lstr) {

		if (!lstr.equals("")) {
			int strlen = 0;
			int cptr = 0;
			boolean lpflag = true;
			char chk;

			strlen = lstr.length();
			cptr = 0;
			lpflag = true;

			do {
				chk = lstr.charAt(cptr);
				if (chk != ' ') {
					lpflag = false;
				} else {
					if (cptr == strlen) {
						lpflag = false;
					} else {
						cptr++;
					}
				}
			} while (lpflag);

			if (cptr > 0) {
				lstr = lstr.substring(cptr, strlen);
			}
		}
		return lstr;
	}

	/**
	 * String의 우측 공백을 없앤다.
	 * 
	 * @param lstr
	 *            대상 String
	 * @return String 결과 String
	 */
	public static String getRightTrim(String lstr) {
		if (!lstr.equals("")) {
			int strlen = 0;
			int cptr = 0;
			boolean lpflag = true;
			char chk;

			strlen = lstr.length();
			cptr = strlen;
			lpflag = true;

			do {
				chk = lstr.charAt(cptr - 1);
				if (chk != ' ') {
					lpflag = false;
				} else {
					if (cptr == 0) {
						lpflag = false;
					} else {
						cptr--;
					}
				}
			} while (lpflag);

			if (cptr < strlen) {
				lstr = lstr.substring(0, cptr);
			}
		}
		return lstr;
	}

	/**
	 * 좌측에서 특정 길이 만큼 취한다.
	 * 
	 * @param str
	 *            대상 String
	 * @param len
	 *            길이
	 * @return 결과 String
	 */
	public static String getLeft(String str, int len) {
		if (str.equals(null))
			return "";

		return str.substring(0, len);
	}

	/**
	 * 우측에서 특정 길이 만큼 취한다.
	 * 
	 * @param str
	 *            대상 String
	 * @param len
	 *            길이
	 * @return 결과 String
	 */
	public static String getRight(String str, int len) {
		if (str.equals(null))
			return "";

		String dest = "";
		for (int i = (str.length() - 1); i >= 0; i--)
			dest = dest + str.charAt(i);

		str = dest;
		str = str.substring(0, len);
		dest = "";

		for (int i = (str.length() - 1); i >= 0; i--)
			dest = dest + str.charAt(i);

		return dest;
	}

	/**
	 * 입력된 값이 널이면, replace 값으로 대체한다.
	 * 
	 * @param str
	 *            입력
	 * @param replace
	 *            대체 값
	 * @return 문자
	 */
	public static String nvl(String str, String replace) {
		if (str == null) {
			return replace;
		} else {
			return str;
		}
	}

	/**
	 * Null 또는 공백이면 다른 값으로 대체한다.
	 * 
	 * @param str
	 *            입력
	 * @param replace
	 *            대체 값
	 * @return 문
	 */
	public static String checkEmpty(String str, String replace) {
		if (str == null || str.equals("")) {
			return replace;
		} else {
			return str;
		}
	}

	/**
	 * 컬럼내의 프로퍼티 값을 변경한다.
	 * 
	 * @param source
	 *            프로퍼티 값
	 * @return 변경된 프로퍼티 값
	 */
	public static String convertPropIntoColumn(String source) {
		if (source == null || source.length() == 0) {
			return source;
		}

		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < source.length(); i++) {
			if (Character.isUpperCase(source.charAt(i))) {
				buffer.append("_");
			}

			buffer.append(Character.toUpperCase(source.charAt(i)));
		}

		return buffer.toString();
	}

	/**
	 * 문자를 합친다.
	 * 
	 * @param str
	 *            문자
	 * @return 합쳐진 문자
	 */
	public static String capitalize(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		return new StringBuffer(strLen).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1))
				.toString();
	}

	/**
	 * Exception 정보를 String으로 변환한다.
	 * 
	 * @param e
	 *            Exception
	 * @return String 변환된 Exception
	 */
	public static String getErrorTrace(Exception e) {
		if (e == null) {
			return "";
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);

		String errTrace = sw.toString();

		return errTrace;
	}

	/**
	 * escapeXml 변환한다.
	 * 
	 * @param s
	 *            s
	 * @return String
	 */
	public static String escapeXml(String s) {
		if (s == null)
			return "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '&') {
				sb.append("&amp;");
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * Html Tag정보를 Text형태로 변환한다.
	 * 
	 * @param s
	 *            s
	 * @return Text정보 반환
	 */
	public static String escapeHtmlTag(String s) {
		if (s == null)
			return "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '<') {
				sb.append("&lt;");
			} else if (c == '>') {
				sb.append("&gt;");
			} else if (c == '&') {
				sb.append("&amp;");
			} else if (c == '\"') {
				sb.append("&quot;");
			} else if (c == '\'' || c == '%') {
				sb.append("");
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * sqlInjectionStringCheck.
	 * 
	 * @param checkStr
	 *            checkStr
	 * @return boolean
	 */
	public static boolean sqlInjectionStringCheck(String checkStr) {

		if (checkStr == null || checkStr.equals(""))
			return false;

		int idx = StringUtils.indexOfAny(checkStr.toLowerCase(), new String[] { "\'", "\"", "\\", "/", ";", ":", "<",
				">", "=", "%", "union ", "select ", "insert ", "delete ", "update ", "drop ", " or ", " and ", "--",
				"create " });

		if (idx > -1)
			return true; // 검색문자열 존재
		else
			return false; // 검색문자열 미존재

	}

	/**
	 * isInteger.
	 * 
	 * @param value
	 *            value
	 * @return boolean
	 */
	public static boolean isInteger(String value) {
		try {
			Integer num = new Integer(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 보안 점검을 위해 <,>,",',&,%,!,-- 값 막기".
	 * 
	 * @param param
	 *            param
	 * @return String
	 */
	public static String removeSpecial(String param) {
		if (param == null)
			param = "";
		else {
			// param = param.replaceAll("<", "");
			// param = param.replaceAll(">", "");
			// param = param.replaceAll("\"", "");
			// param = param.replaceAll("'", "");
			// param = param.replaceAll("&", "");
			// param = param.replaceAll("%", "");
			// param = param.replaceAll("!", "");
			// param = param.replaceAll("--", "");
			// param = param.replaceAll("#", "");
			// param = param.replaceAll("[/*]", "");
			// param = param.replaceAll("[*/]", "");
			param = param.replace(Character.toString(Character.toChars(49824)[0]), ""); // 완성형한글 공백막기
		}

		return param;
	}

	/**
	 * 모든 특수문자 제거.
	 * 
	 * @param param
	 *            param
	 * @return String
	 */
	public static String removeSpecialAll(String param) {

		String[] filterWord = { "", "\\.", "\\?", "\\/", "\\~", "\\!", "\\@", "\\#", "\\$", "\\%", "\\^", "\\&", "\\*",
				"\\(", "\\)", "\\_", "\\+", "\\=", "\\|", "\\\\", "\\}", "\\]", "\\{", "\\[", "\\\"", "\\'", "\\:",
				"\\;", "\\<", "\\,", "\\>", "\\.", "\\?", "\\/", "\\-", "\\`" };

		String temp = "";

		if (param == null)
			param = "";
		else {
			for (int i = 0; i < filterWord.length; i++) {
				temp = param.replaceAll(filterWord[i], "");
				param = temp;
			}
		}

		return param;
	}

	/**
	 * 콤마(,)를 제외한 모든 특수문자 제거.
	 * 
	 * @param param
	 *            param
	 * @return String
	 */
	public static String removeSpecialAllExceptComma(String param) {

		String[] filterWord = { " ", "\\.", "\\?", "\\/", "\\~", "\\!", "\\@", "\\#", "\\$", "\\%", "\\^", "\\&",
				"\\*", "\\(", "\\)", "\\_", "\\+", "\\=", "\\|", "\\\\", "\\}", "\\]", "\\{", "\\[", "\\\"", "\\'",
				"\\:", "\\;", "\\<", "\\>", "\\.", "\\?", "\\/", "\\-", "\\`" };

		String temp = "";

		if (param == null)
			param = "";
		else {
			for (int i = 0; i < filterWord.length; i++) {
				temp = param.replaceAll(filterWord[i], "");
				param = temp;
			}
		}

		return param;
	}

	/**
	 * byte 단위로 substring.
	 * 
	 * @param str
	 *            str
	 * @param i
	 *            i
	 * @param trail
	 *            trail
	 * @return String
	 */
	public static String substrb(String str, int i, String trail) {

		if (str == null)
			return "";

		String tmp = str;
		int slen = 0, blen = 0;
		char c;

		if (tmp.getBytes().length > i) {

			while (blen + 1 < i) {

				c = tmp.charAt(slen);
				blen++;
				slen++;

				if (c > 127)
					blen++; // 2-byte character..
			}

			tmp = tmp.substring(0, slen) + trail;
		}

		return tmp;
	}

	/**
	 * byte 단위로 substring.
	 * 
	 * @param str
	 *            str
	 * @param beginIndex
	 *            0부터 시작
	 * @param endIndex
	 *            substr할 바이트 수. 2-byte문자의 경우 바이트가 부족하면 그 앞 글자까지만 자름.
	 * @param bytesForDB
	 *            2-byte문자(한글 등)의 DB에서의 바이트 수. 예를들어 오라클/UTF-8이면 3바이트임
	 * @return String
	 */
	public static String substrb(String str, int beginIndex, int endIndex, int bytesForDB) {

		if (str == null)
			return "";

		String tmp = str;
		int slen = 0, blen = 0;
		char c;

		try {
			// 20111110 서영배 - getBytes 에서 UTF-8 코드로 변환해서 길이 읽도록 수정
			if (tmp.getBytes("UTF-8").length > endIndex) { // 0부터 카운트 되므로 endIndex - 1

				while (blen < endIndex) {

					c = tmp.charAt(slen);
					// blen++; // 한 글자의 바이트 수에 따라 밑에서 증가시킴
					slen++;

					if (c > 127)
						blen = blen + bytesForDB;

					// 20111110 서영배 - UTF-8 에서 탭과 개행문자는 1바이트로 인식
					// else if(c == '\t') { // tab은 2바이트로 처리. WEB상에서 2byte로 처리되는 듯.(textarea)
					// blen = blen + 2;
					// }
					else if (c == '\n') { // 서버(unix)에서 개행문자를 2바이트로 처리. - 유닉스(LF \n)/윈도우간(CR,LF \r\n) 개행문자 차이 때문에.
						if (slen > 1 && tmp.charAt(slen - 2) != '\r') {
							blen = blen + 2;
						} else
							blen++;
					} else {
						blen++;
					}

				}
				if (blen > endIndex) // 2-byte 문자의 중간에서 잘릴 경우, 마지막 문자 하나는 제거
					slen = slen - 1;

				tmp = tmp.substring(beginIndex, slen);
			}
		} catch (UnsupportedEncodingException ue) {
			ue.getMessage();
		}

		return tmp;
	}

	/**
	 * 입력받은 스트링이 null 이거나 길이가 "0" 일때 공백문자 "" 로 반환하고 아닐 경우에는 trim 을 해서 반환한다.
	 * 
	 * @param tmp
	 *            string that trim
	 * @return string
	 */
	public static String setTrim(String tmp) {
		if (tmp != null && tmp.length() > 0) {
			tmp = tmp.trim();
		} else {
			tmp = "";
		}
		return tmp;
	}

	/**
	 * 입력받은 스트링이 null 이거나 길이가 "0" 일때 "N" 로 반환하고 아닐 경우에는 trim 을 해서 반환한다.
	 * 
	 * @param tmp
	 *            string that trim
	 * @return string
	 */
	public static String setTrimYn(String tmp) {
		if (tmp != null && tmp.length() > 0) {
			tmp = tmp.trim();
		} else {
			tmp = "N";
		}
		return tmp;
	}

	/**
	 * 입력한 값이 "" 공백문자이거나 null 인 경우 두번째 파라메타 값으로 초기화한다.
	 * 
	 * @param orgStr
	 *            original string
	 * @param initStr
	 *            initial string
	 * @return init string
	 */
	public static String nvlStr(String orgStr, String initStr) {
		if (orgStr == null || orgStr.equals(""))
			return initStr;
		else
			return orgStr;
	}

	/**
	 * 입력받은 XML 에서 특정 태그로 둘러쌓은 값을 반환한다.
	 * 
	 * ex) String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + log.info("TMP=" + tmp); ----
	 * ---------------------------------------------------------------------- ------ ===> TMP=TestValue
	 * 
	 * 
	 * @param xmlStr
	 *            xmlStr
	 * @param tagName
	 *            tagName
	 * @return String
	 */
	public static String extractTagValue(String xmlStr, String tagName) {
		String preTag = "<" + tagName + ">";
		String postTag = "</" + tagName + ">";
		int preStr = xmlStr.indexOf(preTag);
		int postStr = xmlStr.indexOf(postTag);

		if (xmlStr.indexOf(preTag, preStr + 1) != -1) {
			StringBuffer tagValueBuf = new StringBuffer();
			preStr = 0;
			postStr = 0;

			while (true) {
				preStr = xmlStr.indexOf(preTag, preStr);
				postStr = xmlStr.indexOf(postTag, postStr);

				if (preStr == -1 || postStr == -1) {
					break;
				}

				String tagValue = xmlStr.substring(preStr + preTag.length(), postStr).trim();

				preStr = postStr + postTag.length();
				postStr = preStr;

				tagValueBuf.append(tagValue);
				tagValueBuf.append(";");
			}
			String resultStr = tagValueBuf.toString();
			if (resultStr.length() > 0) {
				resultStr = resultStr.substring(0, (resultStr.length() - 1));
			}
			return resultStr.trim();
		} else {
			if (preStr == -1 || postStr == -1) {
				return "";
			} else {
				String tagValue = xmlStr.substring(preStr + preTag.length(), postStr).trim();

				return tagValue.trim();
			}
		}
	}

	/**
	 * 주어진 문자열에서 태그를 모두 제거한다.
	 * 
	 * @param str
	 *            원본 문자열
	 * @param replacement
	 *            대체할 문자열
	 * @return String
	 */
	public static String replaceAllTags(String str, String replacement) {

		// <로 시작. /가 0번 또는 1번 나옴. (a-zA-Z문자가 0번 이상)이 한 묶음.
		// (\s 공백문자. a-Z문자가 0번 이상. = 나옴. > 제외한 문자 0번 이상.)이 한 묶음으로 0번 또는 1번 나옴. <- 태그 다음 공백부터 > 전까지. ==> 속성
		// 공백 0번 이상. /가 0번 또는 1번. >로 끝.
		// ex) <a href="www.naver.com" title="title" >NAVER</a>
		return str.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", replacement);
	}

	/**
	 * 주어진 문자열에서 a 태그를 제외한 태그를 모두 제거한다.
	 * 
	 * @param str
	 *            원본 문자열
	 * @param replacement
	 *            대체할 문자열
	 * @return String
	 */
	public static String replaceAllTagsExceptA(String str, String replacement) {

		// /aA 또는 aA가 아닌 것으로 시작하는 모든 태그
		// (?!(/[aA])|([aA])). 에서와 같이 /a /A 또는 a A 와 매치하지 않는 것을 찾아내는 정규표현식
		return str.replaceAll("<(?!(/[aA])|([aA])).([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", replacement);
	}

	/**
	 * 주어진 문자열에서 태그를 대체 문자로 변환한다.
	 * 
	 * @param str
	 *            원본 문자열
	 * @param tag
	 *            변환할 태그. &lt;br&gt;의 형태로 사용
	 * @param replacement
	 *            대체 문자
	 * @return String
	 */
	public static String changeTag(String str, String tag, String replacement) {

		String s = str.replaceAll(tag.toLowerCase(), replacement);
		s = s.replaceAll(tag.toUpperCase(), replacement);

		return s;
	}

	/**
	 * 지정한 charset으로 문자열을 변환하여 반환한다.
	 * 
	 * @param str
	 *            str
	 * @param charset
	 *            charset
	 * @return 변환 실패하면 null
	 */
	public static String changeCharset(String str, String charset) {

		String s = null;

		try {
			if (StringUtils.isEmpty(charset))
				charset = "MS949";

			s = new String(str.getBytes(charset), charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return s;
	}

	/**
	 * 지정된 길이만큼 특정 문자열을 가지고 확장.
	 * 
	 * @param src
	 *            원본 문자열
	 * @param length
	 *            길이
	 * @param with
	 *            특정 문자열
	 * @return String
	 */
	public static String extendStrWith(String src, int length, String with) {

		if (src.length() >= length) // 원본이 만들고자 하는 길이보다 길면 그대로 반환
			return src;
		else {
			for (int i = src.length(); i < length; i++) {
				src = src + with;
			}
		}

		return src;
	}

	/**
	 * extendStrWith.
	 * 
	 * @param src
	 *            원본 문자열
	 * @param length
	 *            길이
	 * @param with
	 *            특정 문자열
	 * @return String
	 */
	public static String extendStrWith(int src, int length, String with) {

		return extendStrWith(String.valueOf(src), length, with);
	}

	/**
	 * 지정된 문자열로 시작하는지 여부를 확인한다.
	 * 
	 * @param str
	 *            원본
	 * @param prefix
	 *            특정
	 * @return 원본이나 특정 문자열이 null 또는 공백이면 false, 특정 문자열로 시작하지 않아도 false, 특정 문자열로 시작하면 true
	 */
	public static boolean startsWith(String str, String prefix) {

		if (StringUtils.isBlank(str) || StringUtils.isBlank(prefix))
			return false;

		return str.startsWith(prefix);
	}

	/**
	 * 태그의 갯수를 제한.
	 * 
	 * @param src
	 *            구분자로 연결된 태그 모음. null/공백이면 공백 반환
	 * @param limit
	 *            최대 갯수. 0이하면 원본 반환
	 * @param regex
	 *            구분자. null/공백이면 원본 반환
	 * @return String
	 */
	public static String cutTagCount(String src, int limit, String regex) {

		if (StringUtils.isBlank(src))
			return "";

		if (limit <= 0 || StringUtils.isBlank(regex))
			return src;

		String s = "";
		// null 문자 공백 처리
		src = src.replaceAll("null", "").replaceAll("NULL", "");

		String[] tags = src.split(regex);
		for (int i = 0; i < tags.length; i++) {

			if (i >= limit)
				break;

			if (StringUtils.isNotBlank(s))
				s += regex;

			s += tags[i];
		}

		return s;
	}

	/**
	 * 태그의 갯수를 제한.
	 * 
	 * @param src
	 *            구분자로 연결된 태그 모음. null/공백이면 공백 반환
	 * @param limit
	 *            최대 갯수. 0이하면 원본 반환
	 * @param regex
	 *            구분자. null/공백이면 원본 반환
	 * @param checkBanWord
	 *            금칙어 제한 여부. true면 태그 목록 생성 시 금칙어는 제외하고 카운트한다.
	 * @return
	 */
	// public static String cutTagCount(String src, int limit, String regex, boolean checkBanWord) {
	//
	// if (StringUtils.isBlank(src))
	// return "";
	//
	// if (limit <= 0 || StringUtils.isBlank(regex))
	// return src;
	//
	// TagService ts = new TagService();
	// String s = "";
	// int cnt = 0;
	//
	// String[] tags = src.split(regex);
	// for (int i = 0; i < tags.length; i++) {
	//
	// if (cnt >= limit)
	// break;
	//
	// if (!ts.isInBanWordList(tags[i])) {
	// if (StringUtils.isNotBlank(s))
	// s += regex;
	//
	// s += tags[i];
	// cnt++;
	// }
	// }
	//
	// return s;
	// }

	/**
	 * 전화번호(일반전화,Mobile) 마스크 처리 가운데 자리를 * 처리한다.
	 * 
	 * @param sProdNo
	 *            : 전화번호
	 * @return String
	 */
	public static String cvtProdNoMask(String sProdNo) {
		if (sProdNo == null)
			return "";

		sProdNo = replace(sProdNo, "-", "");
		if (sProdNo.length() <= 7)
			return sProdNo;

		if (sProdNo.startsWith("02")) {
			if (sProdNo.length() == 9) {
				sProdNo = sProdNo.substring(0, 2) + "-" + "***" + "-" + sProdNo.substring(5);
			} else if (sProdNo.length() == 10) {
				sProdNo = sProdNo.substring(0, 2) + "-" + "****" + "-" + sProdNo.substring(6);
			}

		} else {
			if (sProdNo.length() == 10) {
				sProdNo = sProdNo.substring(0, 3) + "-" + "***" + "-" + sProdNo.substring(6);
			} else if (sProdNo.length() == 11) {
				sProdNo = sProdNo.substring(0, 3) + "-" + "****" + "-" + sProdNo.substring(7);
			}

		}

		return sProdNo;
	}

	/**
	 * ID 마스크 처리 끝 2자리를 * 처리한다.
	 * 
	 * @param id
	 *            : 아이디
	 * @return String
	 */
	public static String cvtIdMask(String id) {
		if (id == null)
			return "";

		if (id.length() <= 2)
			return id;

		id = id.substring(0, id.length() - 2) + "**";

		return id;
	}

	/**
	 * Coupon Id 마스크 처리 처음3자리 마지막 2자리만 남기고 * 처리한다.
	 * 
	 * @param couponId
	 *            : 쿠폰 아이디
	 * @return String
	 */
	public static String cvtCouponIdMask(String couponId) {
		if (couponId == null)
			return "";

		if (couponId.length() <= 5)
			return couponId;

		String maskCouponId = couponId.substring(0, 2) + convStar(couponId.length() - 5)
				+ couponId.substring(couponId.length() - 3, couponId.length());
		return maskCouponId;
	}

	/**
	 * 한글이름 마스크 처리 (해외이름/국내이름 모두 사용가능) 첫자와 마지막자만 보이게.
	 * 
	 * @param kName
	 *            : 한글 이름
	 * @return String
	 */
	public static String cvtKreanNameMask(String kName) {
		if (kName == null)
			return "";

		if (kName.length() <= 1)
			return kName;

		// 한글이름이면
		if (containsHangul(kName)) {
			if (kName.length() == 2) {
				kName = kName.substring(0, 1) + "*";
			} else {
				kName = kName.substring(0, 1) + convStar(kName.length() - 2)
						+ kName.substring(kName.length() - 1, kName.length());
				;
			}
		} else {
			kName = cvtForeignNameMask(kName);
		}

		return kName;
	}

	/**
	 * convStar.
	 * 
	 * @param lengthSize
	 *            : 한글 이름
	 * @return String
	 */
	public static String convStar(int lengthSize) {

		if (lengthSize < 1) {
			return "";
		}

		StringBuffer tochars = new StringBuffer(lengthSize);
		for (int i = 0; i < lengthSize; i++) {
			tochars.append("*");
		}

		return tochars.toString();
	}

	/**
	 * 해외이름 마스크 처리 (해외이름만 사용가능) 4번째부터 3글자.
	 * 
	 * @param fName
	 *            : 해외 이름
	 * @return String
	 */
	public static String cvtForeignNameMask(String fName) {
		if (fName == null)
			return "";

		if (fName.length() <= 3)
			return fName;

		String maskName = fName.substring(0, 3) + convFstar(fName.length());
		if (fName.length() > 6)
			maskName = maskName + fName.substring(6, fName.length());
		return maskName;
	}

	/**
	 * convFstar.
	 * 
	 * @param lengthSize
	 *            lengthSize
	 * @return String
	 */
	public static String convFstar(int lengthSize) {

		if (lengthSize < 3) {
			return "";
		}

		String toStar = "";
		if (lengthSize == 4) {
			toStar = "*";
		} else if (lengthSize == 5) {
			toStar = "**";
		} else if (lengthSize >= 6) {
			toStar = "***";
		}
		return toStar;
	}

	/**
	 * 이메일 마스크 처리 아이디부분 끝 2자리.
	 * 
	 * @param email
	 *            : 이메일
	 * @return String
	 */
	public static String cvtEmailMask(String email) {
		if (email == null)
			return "";

		if (email.length() <= 4)
			return email;

		if (isEmail(email) == false)
			return cvtIdMask(email);

		String[] tempArr = email.split("@");

		if (tempArr.length == 2) {
			email = cvtIdMask(tempArr[0]) + "@" + tempArr[1];
		} else {
			email = cvtIdMask(email);
		}

		return email;
	}

	/**
	 * 이메일 정합성 확인.
	 * 
	 * @param email
	 *            : 이메일
	 * @return String
	 */
	public static boolean isEmail(String email) {
		if (email == null)
			return false;
		boolean b = Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+", email.trim());
		return b;
	}

	/**
	 * 한글이 한자라도 있으면 한글(이름)으로 인식.
	 * 
	 * @param str
	 *            : 이름.
	 * @return String
	 */
	public static boolean containsHangul(String str) {
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(ch);
			if (UnicodeBlock.HANGUL_SYLLABLES.equals(unicodeBlock)
					|| UnicodeBlock.HANGUL_COMPATIBILITY_JAMO.equals(unicodeBlock)
					|| UnicodeBlock.HANGUL_JAMO.equals(unicodeBlock)) {
				return true;
			}
		}
		return false;
	}

}
