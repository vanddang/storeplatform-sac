package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 사용자 부가정보
 *
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */

@JsonSerialize(include = Inclusion.NON_NULL)
public class UserExtraInfo extends CommonInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 관리항목 코드
     */
    private String extraProfile;

    /*
     * 관리항목 값
     */
    private String extraProfileValue;

    public UserExtraInfo() {}

    public UserExtraInfo(String extraProfile, String extraProfileValue) {
        this.extraProfile = extraProfile;
        this.extraProfileValue = extraProfileValue;
    }

    public String getExtraProfile() {
        return this.extraProfile;
    }

    public void setExtraProfile(String extraProfile) {
        this.extraProfile = extraProfile;
    }

    public String getExtraProfileValue() {
        return this.extraProfileValue;
    }

    public void setExtraProfileValue(String extraProfileValue) {
        this.extraProfileValue = extraProfileValue;
    }

}
