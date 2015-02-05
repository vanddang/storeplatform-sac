/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.common.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDevice;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDeviceDetail;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;

/**
 * Device Util.
 * 
 * Updated on : 2014. 1. 22. Updated by : 반범진, 지티소프트
 */
public class DeviceUtil {

	/**
	 * SAC 휴대기기 부가속성값을 구한다.
	 * 
	 * @param extraProfile
	 *            기타 기기 부가속성 key
	 * @param deviceExtraInfoList
	 *            휴대기기 부가속성 리스트
	 * @return extraProfileValue String
	 */
	public static String getDeviceExtraValue(String extraProfile, List<DeviceExtraInfo> deviceExtraInfoList) {

		String extraProfileValue = null;

		if (deviceExtraInfoList != null) {
			for (DeviceExtraInfo info : deviceExtraInfoList) {

				if (StringUtils.equals(info.getExtraProfile(), extraProfile)) {
					extraProfileValue = info.getExtraProfileValue();
					break;
				}
			}
		}

		return extraProfileValue;
	}

	/**
	 * SC 휴대기기 부가속성값을 구한다.
	 * 
	 * @param extraProfile
	 *            기타 기기 부가속성 key
	 * @param userMbrDeviceDetailList
	 *            휴대기기 부가속성 리스트
	 * @return extraProfileValue String
	 */
	public static String getUserMbrDeviceDetailValue(String extraProfile,
			List<UserMbrDeviceDetail> userMbrDeviceDetailList) {

		String extraProfileValue = null;

		if (userMbrDeviceDetailList != null) {
			for (UserMbrDeviceDetail info : userMbrDeviceDetailList) {

				if (StringUtils.equals(info.getExtraProfile(), extraProfile)) {
					extraProfileValue = info.getExtraProfileValue();
					break;
				}
			}
		}

		return extraProfileValue;
	}

	/**
	 * 
	 * 휴대기기 부가속성값을 추가한후 휴대기기 부가속성 리스트를 리턴한다.
	 * 
	 * @param extraProfile
	 *            기타 기기 부가속성 key
	 * @param extraProfileValue
	 *            기타 기기 부가속성 값
	 * @param deviceExtraInfoList
	 *            부가 휴대기기 정보
	 * @return deviceExtraInfoList List<DeviceExtraInfo>
	 */
	public static List<DeviceExtraInfo> setDeviceExtraValue(String extraProfile, String extraProfileValue,
			List<DeviceExtraInfo> deviceExtraInfoList) {

		if (deviceExtraInfoList == null) {
			deviceExtraInfoList = new ArrayList<DeviceExtraInfo>();
		}

		for (DeviceExtraInfo info : deviceExtraInfoList) {
			if (info.getExtraProfile().equals(extraProfile)) {
				deviceExtraInfoList.remove(info);
				break;
			}
		}

		DeviceExtraInfo deviceExtraInfo = new DeviceExtraInfo();
		deviceExtraInfo.setExtraProfile(extraProfile);
		deviceExtraInfo.setExtraProfileValue(extraProfileValue);
		deviceExtraInfoList.add(deviceExtraInfo);

		return deviceExtraInfoList;
	}

	/**
	 * 
	 * SC회원 휴대기기 정보를 SAC회원 휴대기기 정보로 변환.
	 * 
	 * @param userMbrDevice
	 *            SC회원 휴대기기부가정보 객체
	 * @return deviceInfo DeviceInfo SAC회원 휴대기기부가정보 객체
	 */
	public static DeviceInfo getConverterDeviceInfo(UserMbrDevice userMbrDevice) {

		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setUserKey(userMbrDevice.getUserKey());
		deviceInfo.setDeviceKey(userMbrDevice.getDeviceKey());
		deviceInfo.setDeviceId(userMbrDevice.getDeviceID());
		deviceInfo.setTenantId(userMbrDevice.getTenantID());
		deviceInfo.setDeviceModelNo(userMbrDevice.getDeviceModelNo());
		deviceInfo.setDeviceTelecom(userMbrDevice.getDeviceTelecom());
		deviceInfo.setDeviceNickName(userMbrDevice.getDeviceNickName());
		deviceInfo.setIsPrimary(userMbrDevice.getIsPrimary());
		deviceInfo.setIsRecvSms(userMbrDevice.getIsRecvSMS());
		deviceInfo.setNativeId(userMbrDevice.getNativeID());
		deviceInfo.setDeviceAccount(userMbrDevice.getDeviceAccount());
		deviceInfo.setJoinId(userMbrDevice.getJoinId());
		deviceInfo.setDeviceNickName(userMbrDevice.getDeviceNickName());
		deviceInfo.setSvcMangNum(userMbrDevice.getSvcMangNum());
		deviceInfo.setAuthenticationDate(userMbrDevice.getAuthenticationDate());
		// deviceInfo.setIsAuthenticated(userMbrDevice.getIsAuthenticated());
		deviceInfo.setIsAuthenticated("Y"); // TODO. Y로 강제 셋팅
		deviceInfo.setDeviceExtraInfoList(getConverterDeviceInfoDetailList(userMbrDevice.getUserMbrDeviceDetail()));

		return deviceInfo;
	}

	/**
	 * 
	 * SC회원 휴대기기 부가정보를 SAC회원 휴대기기 부가정보로 변환.
	 * 
	 * @param list
	 *            SC회원 휴대기기 부가정보 리스트
	 * @return List<DeviceExtraInfo> SAC회원 휴대기기 부가정보 리스트
	 */
	public static List<DeviceExtraInfo> getConverterDeviceInfoDetailList(List<UserMbrDeviceDetail> list) {

		List<DeviceExtraInfo> deviceExtraInfoList = new ArrayList<DeviceExtraInfo>();
		DeviceExtraInfo deviceExtraInfo = null;

		if (list != null && list.size() > 0) {
			for (UserMbrDeviceDetail userMbrDeviceDetail : list) {
				if (StringUtils.isNotBlank(userMbrDeviceDetail.getExtraProfile())
						&& StringUtils.isNotBlank(userMbrDeviceDetail.getExtraProfileValue())) {
					deviceExtraInfo = new DeviceExtraInfo();
					deviceExtraInfo.setExtraProfile(userMbrDeviceDetail.getExtraProfile());
					deviceExtraInfo.setExtraProfileValue(userMbrDeviceDetail.getExtraProfileValue());
					deviceExtraInfo.setDeviceKey(userMbrDeviceDetail.getDeviceKey());
					deviceExtraInfo.setTenentId(userMbrDeviceDetail.getTenantID());
					deviceExtraInfo.setUserKey(userMbrDeviceDetail.getUserKey());
					deviceExtraInfoList.add(deviceExtraInfo);
				}
			}
		}

		return deviceExtraInfoList;

	}

	/**
	 * 
	 * SAC회원 휴대기기 정보를 SC회원 휴대기기 정보로 변환.
	 * 
	 * @param deviceInfo
	 *            DeviceInfo SAC회원 휴대기기부가정보 객체
	 * @return userMbrDevice SC회원 휴대기기부가정보 객체
	 */
	public static UserMbrDevice getConverterUserMbrDeviceInfo(DeviceInfo deviceInfo) {

		UserMbrDevice userMbrDevice = new UserMbrDevice();
		userMbrDevice.setUserKey(deviceInfo.getUserKey());
		userMbrDevice.setDeviceKey(deviceInfo.getDeviceKey());
		userMbrDevice.setDeviceID(deviceInfo.getDeviceId());
		userMbrDevice.setTenantID(deviceInfo.getTenantId());
		if (StringUtils.isNotBlank(deviceInfo.getDeviceModelNo()))
			userMbrDevice.setDeviceModelNo(deviceInfo.getDeviceModelNo());
		userMbrDevice.setDeviceTelecom(deviceInfo.getDeviceTelecom());
		userMbrDevice.setDeviceNickName(deviceInfo.getDeviceNickName());
		userMbrDevice.setIsPrimary(deviceInfo.getIsPrimary());
		userMbrDevice.setIsRecvSMS(deviceInfo.getIsRecvSms());
		userMbrDevice.setNativeID(deviceInfo.getNativeId());
		userMbrDevice.setDeviceAccount(deviceInfo.getDeviceAccount());
		userMbrDevice.setJoinId(deviceInfo.getJoinId());
		userMbrDevice.setSvcMangNum(deviceInfo.getSvcMangNum());
		userMbrDevice.setUserMbrDeviceDetail(getConverterUserMbrDeviceDetailList(deviceInfo));

		return userMbrDevice;

	}

	/**
	 * 
	 * SAC회원 휴대기기 부가정보를 SC회원 휴대기기 부가정보로 변환.
	 * 
	 * @param deviceInfo
	 *            > SAC회원 휴대기기 정보 객체
	 * @return userMbrDeviceDetailList SC회원 휴대기기 부가정보 리스트
	 */
	public static List<UserMbrDeviceDetail> getConverterUserMbrDeviceDetailList(DeviceInfo deviceInfo) {

		List<UserMbrDeviceDetail> userMbrDeviceDetailList = null;
		UserMbrDeviceDetail userMbrDeviceDetail = null;

		if (deviceInfo.getDeviceExtraInfoList() != null && deviceInfo.getDeviceExtraInfoList().size() > 0) {
			userMbrDeviceDetailList = new ArrayList<UserMbrDeviceDetail>();
			for (DeviceExtraInfo deviceExtraInfo : deviceInfo.getDeviceExtraInfoList()) {
				if (StringUtils.isNotBlank(deviceExtraInfo.getExtraProfile())
						&& StringUtils.isNotBlank(deviceExtraInfo.getExtraProfileValue())) {
					userMbrDeviceDetail = new UserMbrDeviceDetail();
					userMbrDeviceDetail.setExtraProfile(deviceExtraInfo.getExtraProfile());
					userMbrDeviceDetail.setExtraProfileValue(deviceExtraInfo.getExtraProfileValue());
					userMbrDeviceDetail.setDeviceKey(deviceInfo.getDeviceKey());
					userMbrDeviceDetail.setTenantID(deviceInfo.getTenantId());
					userMbrDeviceDetail.setUserKey(deviceInfo.getUserKey());
					userMbrDeviceDetailList.add(userMbrDeviceDetail);
				}
			}
		}

		return userMbrDeviceDetailList;

	}

	/**
	 * <pre>
	 * 지메일 정보에서 gmail.com 계정만 순서대로 3건 추출하여 LIST로 리턴.
	 * </pre>
	 * 
	 * @param gmail
	 *            String
	 * @return ArrayList<String>
	 */
	public static ArrayList<String> getGmailList(String gmail) {
		Integer gmailCnt = 3;
		String delim = "\\,";
		ArrayList<String> gmailList = new ArrayList<String>();

		if (gmail == null || StringUtils.equals(gmail, "")) {
			return gmailList;
		}

		String tempGmailArr[] = gmail.split(delim);

		for (int i = 0; i < tempGmailArr.length; i++) {
			if (tempGmailArr[i].indexOf("@gmail.com") > -1) {
				if (gmailList.size() == gmailCnt) {
					break;
				}
				gmailList.add(tempGmailArr[i]);
			}
		}

		return gmailList;
	}

	/**
	 * <pre>
	 * 지메일 정보에서 gmail.com 계정만 순서대로 3건 추출하여 String 리턴.
	 * </pre>
	 * 
	 * @param gmail
	 *            String
	 * @return String
	 */
	public static String getGmailStr(String gmail) {

		Integer cnt = 0;
		Integer gmailCnt = 3;
		String delim = "\\,";
		String gmailStr = "";

		if (gmail == null || StringUtils.equals(gmail, "")) {
			return "";
		}

		String tempGmailArr[] = gmail.split(delim);

		/*
		 * if (tempGmailArr.length == 1) { // 한 건인 경우 return gmail; }
		 */

		for (int i = 0; i < tempGmailArr.length; i++) {

			if (tempGmailArr[i].indexOf("@gmail.com") > -1) {

				if (cnt == gmailCnt) {
					break;
				}

				gmailStr = gmailStr + tempGmailArr[i] + ",";
				cnt++;
			}
		}

		if (gmailStr.indexOf(",") > -1) {
			gmailStr = gmailStr.substring(0, gmailStr.lastIndexOf(","));
		}

		return gmailStr;

	}

}
