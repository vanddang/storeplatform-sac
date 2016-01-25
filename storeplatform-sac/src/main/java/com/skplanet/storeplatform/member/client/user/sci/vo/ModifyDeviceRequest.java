/*
 * Copyright (c) 2014 SK planet.
 * All right reserved.
 * 
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * 사용자 휴대기기기 수정 요청 Value Object
 *
 * Updated on : 2015. 12. 29 Updated by : 반범진
 */
public class ModifyDeviceRequest extends CommonInfo implements Serializable {

    /** The Constant serial Version UID. */
    private static final long serialVersionUID = 1L;

    /** 공통 요청 Value Object. */
    private CommonRequest commonRequest;

    /**
     * 공통 요청 Value Object를 리턴한다.
     *
     * @return commonRequest - 공통 요청 Value Object
     */
    public CommonRequest getCommonRequest() {
        return this.commonRequest;
    }

    /**
     * 공통 요청 Value Object를 설정한다.
     *
     * @param commonRequest
     *            공통 요청 Value Object
     */
    public void setCommonRequest(CommonRequest commonRequest) {
        this.commonRequest = commonRequest;
    }

    /** 사용자 Key. */
    private String userKey;

    /** deviceId, mdn 수정여부. */
    private boolean isUpdDeviceId = false;

    /** 사용자 휴대기기 Value Object. */
    private UserMbrDevice userMbrDevice;

    /**
     * 사용자 휴대기기 Value Object를 리턴한다.
     *
     * @return userMbrDevice - 사용자 휴대기기 Value Object
     */
    public UserMbrDevice getUserMbrDevice() {
        return this.userMbrDevice;
    }

    /**
     * 사용자 휴대기기 Value Object를 설정한다.
     *
     * @param userMbrDevice
     *            사용자 휴대기기 Value Object
     */
    public void setUserMbrDevice(UserMbrDevice userMbrDevice) {
        this.userMbrDevice = userMbrDevice;
    }

    /**
     * 사용자 Key를 리턴한다.
     *
     * @return userKey - 사용자 Key
     */
    public String getUserKey() {
        return this.userKey;
    }

    /**
     * 사용자 Key를 설정한다.
     *
     * @param userKey
     *            사용자 Key
     */
    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    /**
     * deviceId, mdn 수정여부를 리턴한다.
     *
     * @return isUpdDeviceId - deviceId, mdn 수정여부
     */
    public boolean isUpdDeviceId() {
        return isUpdDeviceId;
    }

    /**
     * deviceId, mdn 수정여부를 설정한다.
     *
     * @param isUpdDeviceId
     *            deviceId, mdn 수정여부
     */
    public void setIsUpdDeviceId(boolean isUpdDeviceId) {
        this.isUpdDeviceId = isUpdDeviceId;
    }
}
