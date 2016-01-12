package com.skplanet.storeplatform.member.client.user.sci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;

import java.io.Serializable;

/**
 * 패스워드 일치 여부 응답 Value Object
 *
 * Updated on : 2015. 12. 21 Updated by : 최진호, 보고지티.
 */
public class CheckUserPwdResponse extends CommonInfo implements Serializable {

    /** The Constant serial Version UID. */
    private static final long serialVersionUID = 1L;

    /** 공통 응답 Value Object. */
    private CommonResponse commonResponse;

    /** 사용자 Key. */
    private String userKey;

    /** 사용자 인증 토큰 */
    private String userAuthToken;

    /**
     * 공통 응답 Value Object를 리턴한다.
     *
     * @return CommonResponse - 공통 응답 Value Object
     */
    public CommonResponse getCommonResponse() {
        return this.commonResponse;
    }

    /**
     * 공통 응답 Value Object를 설정한다.
     *
     * @param commonResponse
     *            공통 응답 Value Object
     */
    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    /**
     * 사용자 Key를 리턴한다.
     *
     * @return userKey - 사용자 Key
     */
    public String getUserKey() {
        return userKey;
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
     * 사용자 인증 토큰을 리턴한다.
     *
     * @return userAuthToken - 사용자 인증 토큰
     */
    public String getUserAuthToken() {
        return userAuthToken;
    }

    /**
     * 사용자 인증 토큰을 설정한다.
     *
     * @param userAuthToken
     *            사용자 인증 토큰
     */
    public void setUserAuthToken(String userAuthToken) {
        this.userAuthToken = userAuthToken;
    }

    /**
     * Returns the serial version UID.
     *
     * @return serialVersionUID - the serial version UID
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.skplanet.storeplatform.framework.core.common.vo.CommonInfo#toString()
     */
    @Override
    public String toString() {
        return Utils.printKeyValues(this);
    }

}
