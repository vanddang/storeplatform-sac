/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.DateUtils;
import com.skplanet.storeplatform.sac.purchase.constant.PurchaseConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 구매 보조 서비스
 * 
 * Updated on : 2014. 6. 19. Updated by : 이승택, nTels.
 */
@Service
public class PurchaseOrderAssistServiceImpl implements PurchaseOrderAssistService {
	@Value("#{systemProperties['env.servername']}")
	private String instanceName;
	private String hostNum;
	private String instanceNum;

	@PostConstruct
	public void initHostInstanceNum() {
		String hostName = null;
		try {
			hostName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			hostName = "01";
		}
		Pattern pattern = Pattern.compile("\\d");

		this.hostNum = "";
		Matcher matcher = pattern.matcher(hostName);
		while (matcher.find()) {
			this.hostNum += matcher.group(0);
		}
		if (StringUtils.isBlank(this.hostNum)) {
			this.hostNum = "01";
		}
		this.hostNum = StringUtils.leftPad(this.hostNum, 2, "0");
		this.hostNum = StringUtils.substring(this.hostNum, this.hostNum.length() - 2); // max 2자리로

		this.instanceNum = "";
		if (StringUtils.isNotBlank(this.instanceName)) {
			matcher = pattern.matcher(this.instanceName);
			while (matcher.find()) {
				this.instanceNum += matcher.group(0);
			}
		}
		if (StringUtils.isBlank(this.instanceNum)) {
			this.instanceNum = "01";
		}
		this.instanceNum = StringUtils.leftPad(this.instanceNum, 2, "0");
	}

	/**
	 * <pre>
	 * 새로운 구매ID 생성.
	 * </pre>
	 * 
	 * @return 새로 생성된 구매ID
	 */
	@Override
	public String makePrchsId(String sequence, String date) {
		// yyMMddhhmmss(12) + 서버ID(2) + 인스턴스ID(2) + 구매시퀀스(4)
		StringBuffer sbPrchsId = new StringBuffer(20);
		sbPrchsId.append(date.substring(2)).append(this.hostNum).append(this.instanceNum)
				.append(StringUtils.leftPad(sequence, 4, "0"));
		return sbPrchsId.toString();
	}

	/**
	 * 
	 * <pre>
	 * 기준일로부터 이용 일자 계산.
	 * </pre>
	 * 
	 * @param startDt
	 *            기준일(시작일)
	 * @param periodUnitCd
	 *            이용기간 단위 코드
	 * @param periodVal
	 *            이용기간 값
	 * 
	 * @return 계산된 이용 일자
	 */
	@Override
	public String calculateUseDate(String startDt, String periodUnitCd, String periodVal) {
		return this.calculateUseDate(startDt, periodUnitCd, periodVal, false);
	}

	/**
	 *
	 * <pre>
	 * 기준일로부터 이용 일자 계산.
	 * </pre>
	 *
	 * @param startDt
	 *            기준일(시작일)
	 * @param periodUnitCd
	 *            이용기간 단위 코드
	 * @param periodVal
	 *            이용기간 값
	 * @param bAutoPrchs
	 *            자동결제상품 여부
	 *
	 * @return 계산된 이용 일자
	 */
	@Override
	public String calculateUseDate(String startDt, String periodUnitCd, String periodVal, boolean bAutoPrchs) {
		return this.calculateUseDate(startDt, periodUnitCd, periodVal, bAutoPrchs, "yyyyMMddHHmmss");
	}

	/**
	 * 
	 * <pre>
	 * 기준일로부터 이용 일자 계산.
	 * </pre>
	 * 
	 * @param startDt
	 *            기준일(시작일)
	 * @param periodUnitCd
	 *            이용기간 단위 코드
	 * @param periodVal
	 *            이용기간 값
	 * @param bAutoPrchs
	 *            자동결제상품 여부
	 * 
	 * @return 계산된 이용 일자
	 */
	@Override
	public String calculateUseDate(String startDt, String periodUnitCd, String periodVal, boolean bAutoPrchs,
			String dateFormant) {

		if (StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_UNLIMITED)) { // 무제한
			return PurchaseConstants.UNLIMITED_DATE;
		} else if (StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_SELECT)) { // 기간선택
			return periodVal;
		} else if (StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_CURR_DATE)) { // 당일
			return startDt.substring(0, 8) + "235959";
		} else if (StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_CURR_YEAR)) { // 당년
			return startDt.substring(0, 4) + "1231235959";
		} else {
			Date checkDate = null;
			try {
				checkDate = DateUtils.parseDate(startDt, "yyyyMMddHHmmss");
			} catch (ParseException e) {
				throw new StorePlatformException("SAC_PUR_7216", startDt);
			}

			if (StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_DATE)) { // 일
				checkDate = DateUtils.addDays(checkDate, Integer.parseInt(periodVal));
			} else if (StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_HOUR)) { // 시간
				checkDate = DateUtils.addHours(checkDate, Integer.parseInt(periodVal));
			} else if (StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_MONTH)) { // 월
				checkDate = DateUtils.addMonths(checkDate, Integer.parseInt(periodVal));
			} else if (StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_YEAR)) { // 년
				checkDate = DateUtils.addYears(checkDate, Integer.parseInt(periodVal));
			} else if (StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_CURR_MONTH)) { // 당월
				checkDate = DateUtils.ceiling(checkDate, Calendar.MONTH);
			} else if (StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_MINUTE)) { // 분
				checkDate = DateUtils.addMinutes(checkDate, Integer.parseInt(periodVal));
			} else {
				throw new StorePlatformException("SAC_PUR_7215", periodUnitCd);
			}

			if (bAutoPrchs) {
				checkDate = DateUtils.addHours(DateUtils.truncate(checkDate, Calendar.DATE), 10);
			}

			return DateFormatUtils.format(checkDate, dateFormant);
		}
	}

	@Override
	public String calculateDayCnt(String today, String periodUnitCd, String periodVal) {
		return calculateDayCnt(today, periodUnitCd, Integer.parseInt(periodVal));
	}

	/**
	 * Calculate day cnt string.
	 *
	 * @param today
	 * 		the today
	 * @param periodUnitCd
	 * 		the period unit cd
	 * @param periodVal
	 * 		the period val
	 *
	 * @return the string
	 */
	@Override
	public String calculateDayCnt(String today, String periodUnitCd, int periodVal) {
		if (StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_UNLIMITED)) { // 무제한
			return "0";
		} else if (StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_CURR_DATE)) { // 당일
			return "1";
		} else if (StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_CURR_YEAR)
				|| StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_CURR_MONTH)) { // 당년
			try {
				Date dToday = DateUtils.parseDate(today, "yyyyMMddHHmmss");
				if (StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_CURR_YEAR)) {
					Date dEndOfYear = DateUtils.parseDate(today.substring(0, 4) + "1231235959", "yyyyMMddHHmmss");
					return String.valueOf(DateUtils.getFragmentInDays(dEndOfYear, Calendar.YEAR)
							- DateUtils.getFragmentInDays(dToday, Calendar.YEAR) + 1);
				} else {
					Calendar cal = Calendar.getInstance();
					cal.setTime(dToday);
					return String.valueOf(cal.getActualMaximum(Calendar.DATE) - Integer.parseInt(today.substring(6, 8))
							+ 1);
				}
			} catch (ParseException e) {
				throw new StorePlatformException("SAC_PUR_7216", today);
			}
		} else {
			if (StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_DATE)
					|| StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_SELECT)) { // 일
				return String.valueOf(periodVal);
			} else if (StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_MONTH)) { // 월
				return String.valueOf(30 * periodVal);
			} else if (StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_YEAR)) { // 년
				return String.valueOf(365 * periodVal);
			} else if (StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_HOUR) || // 시간
					StringUtils.equals(periodUnitCd, PurchaseConstants.PRODUCT_USE_PERIOD_UNIT_MINUTE)) { // 분 (시간과 분은
																										  // 1일 이내의 범위에서
																										  // 설정)
				return "1";
			} else {
				throw new StorePlatformException("SAC_PUR_7215", periodUnitCd);
			}
		}
	}

	/**
	 * 
	 * <pre>
	 * DB PK 오류 여부 체크.
	 * </pre>
	 * 
	 * @param e
	 *            발생한 Exception 개체
	 * 
	 * @return DB PK 오류 여부
	 */
	@Override
	public boolean isDuplicateKeyException(Exception e) {
		Throwable exception = e;
		while (exception != null) {
			if (exception instanceof DuplicateKeyException) {
				return true;
			}
			exception = exception.getCause();
		}

		return false;
	}
}
