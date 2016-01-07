package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * [REQUEST] ID 변경
 *
 * Updated on : 2016. 1. 6. Updated by : 최진호, 보고지티.
 */
public class ModifyIdSacReq extends CommonInfo {

    private static final long serialVersionUID = 1L;

    /** 사용자 Key. */
    @NotEmpty
    private String userKey = "";

    /** 현 사용자 ID. */
    @NotEmpty
    private String userId = "";

    /** 현 사용자 타입. */
    @NotEmpty
    private String userType = "";

    /** 현 사용자 인증 토큰. */
    @NotEmpty
    private String userAuthToken = "";

    /** 변경할 사용자 ID. */
    @NotEmpty
    private String newUserId = "";

    /** 변경할 사용자 타입. */
    @NotEmpty
    private String newUserType = "";

    /** 변경할 사용자 인증 토큰. */
    @NotEmpty
    private String newUserAuthToken = "";

    public static long getSerialVersionUID() { return serialVersionUID; }

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
     * @return String : newUserAuthToken
     */
    public String getNewUserAuthToken() { return newUserAuthToken; }

    /**
     * @param newUserAuthToken
     *            String : the newUserAuthToken to set
     */
    public void setNewUserAuthToken(String newUserAuthToken) { this.newUserAuthToken = newUserAuthToken; }

}
