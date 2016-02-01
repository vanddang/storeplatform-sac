/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.user.service;

import com.google.common.base.Strings;
import com.skplanet.storeplatform.sac.member.domain.mbr.UserMarketPin;
import com.skplanet.storeplatform.sac.member.domain.shared.UserDevice;
import com.skplanet.storeplatform.sac.member.domain.shared.UserDeviceSetting;
import com.skplanet.storeplatform.sac.member.repository.UserDeviceRepository;
import com.skplanet.storeplatform.sac.member.repository.UserDeviceSettingRepository;
import com.skplanet.storeplatform.sac.member.repository.UserMarketPinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * DeviceSettingServiceImpl
 * </p>
 * Updated on : 2016. 01. 26 Updated by : 정희원, SK 플래닛.
 */
@Service
@Transactional(value = "transactionManagerForScMember", readOnly = true)
public class DeviceSettingService {

    @Autowired
    private UserDeviceSettingRepository deviceSettingRepository;

    @Autowired
    private UserDeviceRepository deviceRepository;

    @Autowired
    private UserMarketPinRepository marketPinRepository;

    public UserDeviceSetting find(String userKey, String deviceKey, boolean checkMarketPin) {
        UserDevice device = deviceRepository.findByUserKeyAndDeviceKey(userKey, deviceKey);
        UserDeviceSetting setting = device.getSetting();

        // 데이터가 없으면 기본값을 세팅하여 응답한다.
        if( setting == null ) {
            return UserDeviceSetting.createDefault();
        }

        if (checkMarketPin) {
            UserMarketPin marketPin = marketPinRepository.findOne(userKey);
            setting.setUseMarketPin(marketPin != null);
            setting.setMarketPinFailCnt(marketPin != null ? marketPin.getAuthFailCnt() : null);
        }

        return setting;
    }

    public UserDeviceSetting find(String userKey, String deviceKey) {
        return find(userKey, deviceKey, false);
    }

    @Transactional
    public String merge(String userKey, String deviceKey, final UserDeviceSetting v) {

        UserDevice device = deviceRepository.findByUserKeyAndDeviceKey(userKey, deviceKey);
        UserDeviceSetting setting = device.getSetting();

        if (setting == null) {
            setting = UserDeviceSetting.createDefault();
            setting.setId(device.getId());
        }

        if(!Strings.isNullOrEmpty(v.getAutoUpdtYn()))
            setting.setAutoUpdtYn(v.getAutoUpdtYn());
        if(!Strings.isNullOrEmpty(v.getAutoUpdtSetClsf()))
            setting.setAutoUpdtSetClsf(v.getAutoUpdtSetClsf());
        if(!Strings.isNullOrEmpty(v.getWiFiAutoUpdtYn()))
            setting.setWiFiAutoUpdtYn(v.getWiFiAutoUpdtYn());
        if(!Strings.isNullOrEmpty(v.getLoginLockYn()))
            setting.setLoginLockYn(v.getLoginLockYn());
        if(!Strings.isNullOrEmpty(v.getAdultContentsLockYn()))
            setting.setAdultContentsLockYn(v.getAdultContentsLockYn());

        if(!Strings.isNullOrEmpty(v.getWiFiAutoDwldYn()))
            setting.setWiFiAutoDwldYn(v.getWiFiAutoDwldYn());
        if(!Strings.isNullOrEmpty(v.getIcasAuthYn()))
            setting.setIcasAuthYn(v.getIcasAuthYn());
        if(v.getRnameAuthDate() != null)
            setting.setRnameAuthDate(v.getRnameAuthDate());
        if(!Strings.isNullOrEmpty(v.getRnameAuthMdn()))
            setting.setRnameAuthMdn(v.getRnameAuthMdn());

        deviceSettingRepository.save(setting);
        return device.getDeviceId();
    }
}
