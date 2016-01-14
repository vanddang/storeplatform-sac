package com.skplanet.storeplatform.member.client.user.sci.vo;

import com.skplanet.storeplatform.member.client.common.util.Utils;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

/**
 * 비밀번호 수정 요청 Value Object
 *
 * Updated on : 2016. 1. 06. Updated by : 최진호, 보고지티.
 */
public class ModifyUserPwdRequest {

    /** The Constant serial Version UID. */
    private static final long serialVersionUID = 1L;

    /** 공통 요청 Value Object. */
    private CommonRequest commonRequest;

    /** 사용자 Key. */
    private String userKey;

    /** 사용자 pw (이전 패스워드). */
    private String oldPassword;

    /** 사용자 pw (이후 패스워드). */
    private String newPassword;

    /** 사용자 salt값 (One Id 유저인경우) */
    private String userSalt;

    /** 비밀번호 타입 (IDP:US011502, OneId:US011503) */
    private String userPwType;

    /** 휴면계정 유무 */
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
     * 사용자 pw (이전 패스워드) 를 리턴한다.
     *
     * @return userSalt - 사용자 pw (이전 패스워드)
     */
    public String getOldPassword() { return oldPassword; }

    /**
     * 사용자 pw (이전 패스워드)를 설정한다.
     *
     * @param oldPassword
     *            사용자 pw (이전 패스워드)
     */
    public void setOldPassword(String oldPassword) { this.oldPassword = oldPassword; }

    /**
     * 사용자 pw (이후 패스워드) 를 리턴한다.
     *
     * @return userSalt - 사용자 pw (이후 패스워드)
     */
    public String getNewPassword() { return newPassword; }

    /**
     * 사용자 pw (이후 패스워드)를 설정한다.
     *
     * @param newPassword
     *            사용자 pw (이후 패스워드)
     */
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }

    /**
     * 사용자 salt값 (One Id 유저인경우) 를 리턴한다.
     *
     * @return userSalt - 사용자 salt값 (One Id 유저인경우)
     */
    public String getUserSalt() { return userSalt; }

    /**
     * 사용자 salt값 (One Id 유저인경우)를 설정한다.
     *
     * @param userSalt
     *            사용자 salt값 (One Id 유저인경우)
     */
    public void setUserSalt(String userSalt) { this.userSalt = userSalt; }

    /**
     * 비밀번호 타입 (IDP:US011502, OneId:US011503) 를 리턴한다.
     *
     * @return userPwType - 사용자 salt값 (One Id 유저인경우)
     */
    public String getUserPwType() {
        return userPwType;
    }

    /**
     * 비밀번호 타입 (IDP:US011502, OneId:US011503)를 설정한다.
     *
     * @param userPwType
     *            사용자 salt값 (One Id 유저인경우)
     */
    public void setUserPwType(String userPwType) { this.userPwType = userPwType; }

    /**
     * @return isDormant
     */
    public String getIsDormant() {
        return this.isDormant;
    }

    /**
     * @param isDormant
     *            String
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
