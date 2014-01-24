/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.common;

import java.util.ArrayList;
import java.util.List;

import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDevice;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDeviceDetail;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;

/**
 * Device Util
 * 
 * Updated on : 2014. 1. 22. Updated by : 반범진, 지티소프트
 */
public class DeviceUtil {

	/**
	 * 휴대기기 부가속성값을 구한다
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

				if (info.getExtraProfile().equals(extraProfile)) {
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
	 *            휴대기기 부가속성 리스트
	 * @return deviceExtraInfoList List<DeviceExtraInfo>
	 */
	public static List<DeviceExtraInfo> setDeviceExtraValue(String extraProfile, String extraProfileValue, List<DeviceExtraInfo> deviceExtraInfoList) {

		if (deviceExtraInfoList != null) {

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
		}

		return deviceExtraInfoList;
	}

	/**
	 * 
	 * SC회원 휴대기기 정보를 SAC회원 휴대기기 정보로 변환
	 * 
	 * @param userMbrDevice
	 *            SC회원 휴대기기부가정보 객체
	 * @return deviceInfo DeviceInfo SAC회원 휴대기기부가정보 객체
	 * @throws Exception
	 *             Exception
	 */
	public static DeviceInfo getConverterDeviceInfo(UserMbrDevice userMbrDevice) throws Exception {

		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setUserKey(userMbrDevice.getUserKey());
		deviceInfo.setDeviceKey(userMbrDevice.getDeviceKey());
		deviceInfo.setDeviceId(userMbrDevice.getDeviceID());
		deviceInfo.setTenantId(userMbrDevice.getTenantID());
		deviceInfo.setDeviceModelNo(userMbrDevice.getDeviceModelNo());
		deviceInfo.setDeviceTelecom(userMbrDevice.getDeviceTelecom());
		deviceInfo.setDeviceNickName(userMbrDevice.getDeviceNickName());
		deviceInfo.setIsPrimary(userMbrDevice.getIsPrimary());
		deviceInfo.setIsAuthenticated(userMbrDevice.getIsAuthenticated());
		deviceInfo.setAuthenticationDate(userMbrDevice.getAuthenticationDate());
		deviceInfo.setIsRecvSms(userMbrDevice.getIsRecvSMS());
		deviceInfo.setNativeId(userMbrDevice.getNativeID());
		deviceInfo.setDeviceAccount(userMbrDevice.getDeviceAccount());
		deviceInfo.setJoinId(userMbrDevice.getJoinId());
		deviceInfo.setIsUsed(userMbrDevice.getIsUsed());
		deviceInfo.setDeviceNickName(userMbrDevice.getDeviceNickName());
		deviceInfo.setDeviceAccount(userMbrDevice.getDeviceAccount());
		if (userMbrDevice.getUserMbrDeviceDetail() != null) {
			deviceInfo.setUserDeviceExtraInfo(getConverterDeviceInfoDetailList(userMbrDevice.getUserMbrDeviceDetail()));
		}

		return deviceInfo;
	}

	/**
	 * 
	 * SC회원 휴대기기 부가정보를 SAC회원 휴대기기 부가정보로 변환
	 * 
	 * @param list
	 *            SC회원 휴대기기 부가정보 리스트
	 * @return List<DeviceExtraInfo> SAC회원 휴대기기 부가정보 리스트
	 */
	public static List<DeviceExtraInfo> getConverterDeviceInfoDetailList(List<UserMbrDeviceDetail> list) throws Exception {

		List<DeviceExtraInfo> deviceExtraInfoList = null;
		DeviceExtraInfo deviceExtraInfo = null;

		if (list != null && list.size() > 0) {
			deviceExtraInfoList = new ArrayList<DeviceExtraInfo>();
			for (UserMbrDeviceDetail deviceDetail : list) {
				deviceExtraInfo = new DeviceExtraInfo();
				deviceExtraInfo.setExtraProfile(deviceDetail.getExtraProfile());
				deviceExtraInfo.setExtraProfileValue(deviceDetail.getExtraProfileValue());
				deviceExtraInfo.setDeviceKey(deviceDetail.getDeviceKey());
				deviceExtraInfo.setTenentId(deviceDetail.getTenantID());
				deviceExtraInfo.setUserKey(deviceDetail.getUserKey());
				deviceExtraInfoList.add(deviceExtraInfo);
			}
		}

		return deviceExtraInfoList;

	}

	/**
	 * 
	 * SAC회원 휴대기기 정보를 SC회원 휴대기기 정보로 변환
	 * 
	 * @param deviceInfo
	 *            DeviceInfo SAC회원 휴대기기부가정보 객체
	 * @return userMbrDevice SC회원 휴대기기부가정보 객체
	 * @throws Exception
	 *             Exception
	 */
	public static UserMbrDevice getConverterUserMbrDeviceInfo(DeviceInfo deviceInfo) throws Exception {

		UserMbrDevice userMbrDevice = new UserMbrDevice();
		userMbrDevice.setUserKey(deviceInfo.getUserKey());
		userMbrDevice.setDeviceKey(deviceInfo.getDeviceKey());
		userMbrDevice.setDeviceID(deviceInfo.getDeviceId());
		userMbrDevice.setTenantID(deviceInfo.getTenantId());
		userMbrDevice.setDeviceModelNo(deviceInfo.getDeviceModelNo());
		userMbrDevice.setDeviceTelecom(deviceInfo.getDeviceTelecom());
		userMbrDevice.setDeviceNickName(deviceInfo.getDeviceNickName());
		userMbrDevice.setIsPrimary(deviceInfo.getIsPrimary());
		userMbrDevice.setIsAuthenticated(deviceInfo.getIsAuthenticated());
		userMbrDevice.setAuthenticationDate(deviceInfo.getAuthenticationDate());
		userMbrDevice.setIsRecvSMS(deviceInfo.getIsRecvSms());
		userMbrDevice.setNativeID(deviceInfo.getNativeId());
		userMbrDevice.setDeviceAccount(deviceInfo.getDeviceAccount());
		userMbrDevice.setJoinId(deviceInfo.getJoinId());
		userMbrDevice.setIsUsed(deviceInfo.getIsUsed());
		userMbrDevice.setUserMbrDeviceDetail(getConverterUserMbrDeviceDetailList(deviceInfo));

		return userMbrDevice;

	}

	/**
	 * 
	 * SAC회원 휴대기기 부가정보를 SC회원 휴대기기 부가정보로 변환
	 * 
	 * @param deviceInfo
	 *            > SAC회원 휴대기기 정보 객체
	 * @return userMbrDeviceDetailList SC회원 휴대기기 부가정보 리스트
	 */
	public static List<UserMbrDeviceDetail> getConverterUserMbrDeviceDetailList(DeviceInfo deviceInfo) throws Exception {

		List<UserMbrDeviceDetail> userMbrDeviceDetailList = null;
		UserMbrDeviceDetail userMbrDeviceDetail = null;

		if (deviceInfo.getUserDeviceExtraInfo() != null && deviceInfo.getUserDeviceExtraInfo().size() > 0) {
			userMbrDeviceDetailList = new ArrayList<UserMbrDeviceDetail>();
			for (DeviceExtraInfo deviceExtraInfo : deviceInfo.getUserDeviceExtraInfo()) {
				userMbrDeviceDetail = new UserMbrDeviceDetail();
				userMbrDeviceDetail.setExtraProfile(deviceExtraInfo.getExtraProfile());
				userMbrDeviceDetail.setExtraProfileValue(deviceExtraInfo.getExtraProfileValue());
				userMbrDeviceDetail.setDeviceKey(deviceInfo.getDeviceKey());
				userMbrDeviceDetail.setTenantID(deviceInfo.getTenantId());
				userMbrDeviceDetail.setUserKey(deviceInfo.getUserKey());
				userMbrDeviceDetailList.add(userMbrDeviceDetail);
			}
		}

		return userMbrDeviceDetailList;

	}

}
