package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * [RESPONSE] ID기반(Tstore ID / Social ID)회원의 인증 [OneStore 단말을 위한 신규규격].
 *
 * Updated on : 2016. 1. 5. Updated by : 최진호, 보고지티.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AuthorizeByPwdSacRes extends CommonInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 사용자 고유 Key.
     */
    private String userKey = "";

    /**
     * 사용자 인증 토큰.
     */
    private String userAuthToken = "";

    /**
     * 사용자 구분코드.
     */
    private String userType = "";

    /**
     * 로그인 상태코드.
     */
    private String loginStatusCode = "";

    /**
     * 로그인 실패 카운트.
     */
    private String loginFailCount = "";

    /**
     * 로그인 성공유무(Y/N).
     */
    private String isLoginSuccess = "";

    /**
     * @return userKey
     */
    public String getUserKey() {
        return this.userKey;
    }

    /**
     * @param userKey String
     */
    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    /**
     * @return userAuthToken
     */
    public String getUserAuthToken() {
        return this.userAuthToken;
    }

    /**
     * @param userAuthToken String
     */
    public void setUserAuthToken(String userAuthToken) {
        this.userAuthToken = userAuthToken;
    }

    /**
     * @return userType
     */
    public String getUserType() {
        return this.userType;
    }

    /**
     * @param userType String
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * @return loginStatusCode
     */
    public String getLoginStatusCode() {
        return this.loginStatusCode;
    }

    /**
     * @param loginStatusCode String
     */
    public void setLoginStatusCode(String loginStatusCode) {
        this.loginStatusCode = loginStatusCode;
    }

    /**
     * @return loginFailCount
     */
    public String getLoginFailCount() {
        return this.loginFailCount;
    }

    /**
     * @param loginFailCount String
     */
    public void setLoginFailCount(String loginFailCount) {
        this.loginFailCount = loginFailCount;
    }

    /**
     * @return isLoginSuccess
     */
    public String getIsLoginSuccess() {
        return this.isLoginSuccess;
    }

    /**
     * @param isLoginSuccess String
     */
    public void setIsLoginSuccess(String isLoginSuccess) {
        this.isLoginSuccess = isLoginSuccess;
    }

}