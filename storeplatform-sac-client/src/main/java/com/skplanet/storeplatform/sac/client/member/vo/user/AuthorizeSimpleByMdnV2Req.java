package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * [REQUEST] 심플 인증(간편 인증) v2.
 *
 * Updated on : 2016. 1. 18. Updated by : 최진호, 보고지티.
 */
public class AuthorizeSimpleByMdnV2Req extends CommonInfo implements Serializable {

    /** */
    private static final long serialVersionUID = 1L;

    /** 기기 ID. */
    @NotEmpty(message = "파라미터가 존재하지 않습니다.")
    private String deviceId;

    /** 사용자 타입. */
    @NotEmpty(message = "파라미터가 존재하지 않습니다.")
    private String userType;

    /** 사용자 ID. */
    private String userId;

    /**
     * @return deviceId
     */
    public String getDeviceId() {
        return this.deviceId;
    }

    /**
     * @param deviceId
     *            String
     */
    public void setDeviceId(String deviceId) {  this.deviceId = deviceId; }

    /**
     * @return userType
     */
    public String getUserType() {
        return this.userType;
    }

    /**
     * @param userType
     *            String userType
     */
    public void setUserType(String userType) {  this.userType = userType; }

    /**
     * @return userId
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * @param userId
     *            String userId
     */
    public void setUserId(String userId) {  this.userId = userId; }

}
