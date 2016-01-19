package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * [REQUEST] ID기반(Tstore ID / Social ID)회원의 인증 [OneStore 단말을 위한 신규규격].
 *
 * Updated on : 2016. 1. 5. Updated by : 최진호, 보고지티.
 */
public class AuthorizeByPwdSacReq  extends CommonInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 사용자 아이디.
     */
    @NotEmpty(message = "파라미터가 존재하지 않습니다.")
    private String userId;

    /**
     * 사용자 패스워드.
     */
    @NotEmpty(message = "파라미터가 존재하지 않습니다.")
    private String userPw;

    /**
     * @return userId
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * @param userId
     *            String
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return userPw
     */
    public String getUserPw() {
        return this.userPw;
    }

    /**
     * @param userPw
     *            String
     */
    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

}