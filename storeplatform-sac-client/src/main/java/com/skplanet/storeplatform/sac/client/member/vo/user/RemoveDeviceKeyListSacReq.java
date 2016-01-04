package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 휴대기기 삭제v2 기능을 제공한다
 *
 * Updated on : 2015. 12. 30. Updated by : 윤보영, 카레즈.
 */
public class RemoveDeviceKeyListSacReq extends CommonInfo {

    private static final long serialVersionUID = 1L;

    private String deviceKey;

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }
}
