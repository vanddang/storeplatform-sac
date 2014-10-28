package com.skplanet.storeplatform.sac.common.util;

import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.api.util.StringUtil;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 10. 28.
 */
public class CommonUtils {
	/**
	 * 대한민국 주민번호 체계를 이용해 현재 연도에 해당하는 만 나이를 추출해 냅니다.
	 * 
	 * @author 김영균
	 * @param socialNum1
	 *            주민번호 앞자리(생년월일)
	 * @param socialNum2
	 *            주민번호 뒤 1자리 (생년월일과 성별로 체크한 값)
	 * @return 만 나이
	 */
	public static int getAgeBySocalNumber(String socialNum1, String socialNum2) {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		int targetYear = cal.get(cal.YEAR);
		cal = null;
		return getAgeBySocalNumber(socialNum1, socialNum2, targetYear);
	}

	/**
	 * 대한민국 주민번호 체계를 이용해 지정한 연도에 해당하는 만 나이를 추출해 냅니다.
	 * 
	 * @author 김영균
	 * @param socialNum1
	 *            주민번호 앞자리(생년월일)
	 * @param socialNum2
	 *            주민번호 뒤 1자리 (생년월일과 성별로 체크한 값)
	 * @param targetYear
	 *            지정 연도
	 * @return 만 나이
	 */
	public static int getAgeBySocalNumber(String socialNum1, String socialNum2, int targetYear) {
		if (StringUtil.isBlank(socialNum1))
			return 0;
		if (StringUtil.isBlank(socialNum2))
			return 0;

		int age = 0;
		int birth = Integer.parseInt(socialNum1.substring(0, 2));
		int sexPerfix = Integer.parseInt(socialNum2.substring(0, 1));
		int yearMultiplication = 0;

		/**
		 * 주민번호 뒷자리 첫번째 숫자 부여 체계 짝수 여성, 홀수 남성 구분 1800년도생 1900년도생 2000년도생 내국인 9, 0 1, 2 3, 4 외국인 5, 6 7, 8
		 */
		switch (sexPerfix) {
		case 1:
		case 2:
		case 5:
		case 6:
			yearMultiplication = 0;
			break;
		case 3:
		case 4:
		case 7:
		case 8:
			yearMultiplication = 1;
			break;
		case 9:
		case 0:
			yearMultiplication = -1;
			break;
		}
		System.out.println("yearMultiplication : " + yearMultiplication);
		age = targetYear - (1900 + birth + (yearMultiplication * 100));

		// 월/일까지 계산하여 나이 계산하도록 수정함.
		String curMMDD = DateUtil.getCurrentDate().substring(4);
		String userMMDD = socialNum1.substring(2);

		// 만나이로 할려면 생일이 지나지 않은 경우는 한살 뺌
		if (Integer.parseInt(userMMDD) < Integer.parseInt(curMMDD)) {
			age--;
		}

		return age;
	}
}
