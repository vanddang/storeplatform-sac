package com.skplanet.storeplatform.member.client.user.sci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

import java.io.Serializable;

/**
 * 사용자 인증 체크 요청 Value Object.
 *
 * Updated on : 2015. 1. 4. Updated by : 최진호, 보고지티
 */
public class CheckUserAuthTokenRequest extends CommonInfo implements Serializable {

    /** The Constant serial Version UID. */
    private static final long serialVersionUID = 1L;

    /** 공통 요청 Value Object. */
    private CommonRequest commonRequest;

    /** 사용자 Key. */
    private String userKey;

    /** 사용자 인증 토큰 */
    private String userAuthToken;

    /** 휴면계정 여부. */
    private String isDormant;

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
     * 사용자 인증 토큰를 리턴한다.
     *
     * @return userAuthToken - 사용자 인증 토큰
     */
    public String getUserAuthToken() {
        return this.userAuthToken;
    }

    /**
     * 사용자 인증 토큰를 설정한다.
     *
     * @param userAuthToken
     *            사용자 인증 토큰
     */
    public void setUserAuthToken(String userAuthToken) {
        this.userAuthToken = userAuthToken;
    }

    /**
     * 휴면계정 여부를 리턴한다.
     *
     * @return isDormant - 휴면계정 여부
     */
    public String getIsDormant() {
        return isDormant;
    }

    /**
     * 휴면계정 여부를 설정한다.
     *
     * @param isDormant
     *            휴면계정 여부
     */
    public void setIsDormant(String isDormant) {
        this.isDormant = isDormant;
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
