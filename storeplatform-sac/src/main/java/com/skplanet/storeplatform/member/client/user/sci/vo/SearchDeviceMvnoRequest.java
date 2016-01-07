package com.skplanet.storeplatform.member.client.user.sci.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;

import java.io.Serializable;

/**
 * 휴대기기(MVNO) 단건 조회 요청 Value Object
 *
 * Updated on : 2016. 1. 6 Updated by : 윤보영, 카레즈
 */
public class SearchDeviceMvnoRequest extends CommonInfo implements Serializable {

    /** The Constant serial Version UID. */
    private static final long serialVersionUID = 1L;

    /** 공통 요청 Value Object. */
    private CommonRequest commonRequest;

    /** MDN */
    private String mdn;

    /** deviceNatvId */
    private String deviceNatvId;

    public CommonRequest getCommonRequest() {
        return commonRequest;
    }

    public void setCommonRequest(CommonRequest commonRequest) {
        this.commonRequest = commonRequest;
    }

    public String getMdn() {
        return mdn;
    }

    public void setMdn(String mdn) {
        this.mdn = mdn;
    }

    public String getDeviceNatvId() {
        return deviceNatvId;
    }

    public void setDeviceNatvId(String deviceNatvId) {
        this.deviceNatvId = deviceNatvId;
    }
}
