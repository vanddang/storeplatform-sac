package com.skplanet.storeplatform.member.client.user.sci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

import java.io.Serializable;

/**
 * 사용자 인증 토큰 생성 요청 Value Object
 *
 * Updated on : 2016. 1. 6 Updated by : 최진호, 보고지티.
 */
public class CreateUserAuthTokenRequest extends CommonInfo implements Serializable {

    /** The Constant serial Version UID. */
    private static final long serialVersionUID = 1L;

    /** 공통 요청 Value Object. */
    private CommonRequest commonRequest;

    /** 사용자 Key. */
    private String userKey;

    /** 사용자 인증 토큰 */
    private String userAuthToken;

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
