package com.skplanet.storeplatform.member.client.user.sci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * ID 변경 요청 Value Object
 *
 * Updated on : 2016. 1. 06. Updated by : 최진호, 보고지티.
 */
public class ModifyIdRequest  extends CommonInfo {

    /** The Constant serial Version UID. */
    private static final long serialVersionUID = 1L;

    /** 공통 요청 Value Object. */
    private CommonRequest commonRequest;

    /** 사용자 Key. */
    private String userKey = "";

    /** 현 사용자 ID. */
    private String userId = "";

    /** 현 사용자 타입. */
    private String userType = "";

    /** 현 사용자 인증 토큰. */
    private String userAuthToken = "";

    /** 변경할 사용자 ID. */
    private String newUserId = "";

    /** 변경할 사용자 타입. */
    private String newUserType = "";

    /** 변경할 사용자 이메일 */
    private String newUserEmail = "";

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
     * @return String : userKey
     */
    public String getUserKey() { return userKey; }

    /**
     * @param userKey
     *            String : the userKey to set
     */
    public void setUserKey(String userKey) { this.userKey = userKey; }

    /**
     * @return String : userId
     */
    public String getUserId() { return userId; }

    /**
     * @param userId
     *            String : the userId to set
     */
    public void setUserId(String userId) { this.userId = userId; }

    /**
     * @return String : userType
     */
    public String getUserType() { return userType; }

    /**
     * @param userType
     *            String : the userType to set
     */
    public void setUserType(String userType) { this.userType = userType; }

    /**
     * @return String : userAuthToken
     */
    public String getUserAuthToken() { return userAuthToken; }

    /**
     * @param userAuthToken
     *            String : the userAuthToken to set
     */
    public void setUserAuthToken(String userAuthToken) { this.userAuthToken = userAuthToken; }

    /**
     * @return String : newUserId
     */
    public String getNewUserId() { return newUserId; }

    /**
     * @param newUserId
     *            String : the newUserId to set
     */
    public void setNewUserId(String newUserId) { this.newUserId = newUserId; }

    /**
     * @return String : newUserType
     */
    public String getNewUserType() { return newUserType; }

    /**
     * @param newUserType
     *            String : the newUserType to set
     */
    public void setNewUserType(String newUserType) { this.newUserType = newUserType; }

    /**
     * @return String : newUserEmail
     */
    public String getNewUserEmail() { return newUserEmail; }

    /**
     * @param newUserEmail
     *            String : the newUserEmail to set
     */
    public void setNewUserEmail(String newUserEmail) { this.newUserEmail = newUserEmail; }

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
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return Utils.printKeyValues(this);
    }

}
