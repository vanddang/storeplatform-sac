package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * [RESPONSE] 모바일 전용 회원 인증(MDN 인증) v3.
 * 
 * Updated on : 2016. 1. 13. Updated by : 반범진.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AuthorizeByMdnV3SacRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 고유 Key.
	 */
	private String userKey = "";

	/**
	 * 기기 Key.
	 */
	private String deviceKey = "";

    /**
     * @return userKey
     */
    public String getUserKey() {
        return userKey;
    }

    /**
     * @param userKey
     *            String
     */
    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    /**
     * @return deviceKey
     */
    public String getDeviceKey() {
        return deviceKey;
    }

    /**
     * @param deviceKey
     *            String
     */
    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

}
