package com.skplanet.storeplatform.sac.client.internal.member.user.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * SAC Internal [REQUEST] Naver 구매 이관 정보 등록.
 *
 * Updated on : 2016. 02. 02. Updated by : 윤보영.
 */
public class CreatePurchaseNaverIdSacReq extends CommonInfo {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 회원 키.
     */
    @NotEmpty
    private String userKey;

    /**
     * 구매이관 처리된 Naver 아이디.
     */
    @NotEmpty
    private String naverId;

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getNaverId() {
        return naverId;
    }

    public void setNaverId(String naverId) {
        this.naverId = naverId;
    }

}
