package com.skplanet.storeplatform.member.client.user.sci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

import java.io.Serializable;

/**
 * 휴대기기(MVNO) 단건 조회 응답 Value Object
 *
 * Updated on : 2016. 1. 6 Updated by : 윤보영, 카레즈
 */
public class SearchDeviceMvnoResponse extends CommonInfo implements Serializable {

    /** The Constant serial Version UID. */
    private static final long serialVersionUID = 1L;

    /** 공통 응답 Value Object. */
    private CommonResponse commonResponse;

    /** 사용자 Key. */
    private String userKey;

    /** 사용자 ID. */
    private String userID;

    /** 사용자 휴대기기 Value Object. */
    private UserMbrDevice userMbrDevice;

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public UserMbrDevice getUserMbrDevice() {
        return userMbrDevice;
    }

    public void setUserMbrDevice(UserMbrDevice userMbrDevice) {
        this.userMbrDevice = userMbrDevice;
    }
}
