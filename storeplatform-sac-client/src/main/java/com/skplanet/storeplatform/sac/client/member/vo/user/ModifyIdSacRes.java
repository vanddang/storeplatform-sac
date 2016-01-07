package com.skplanet.storeplatform.sac.client.member.vo.user;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * [RESPONSE] ID 변경
 *
 * Updated on : 2016. 1. 6. Updated by : 최진호, 보고지티.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ModifyIdSacRes extends CommonInfo {

    private static final long serialVersionUID = 1L;

    /** 사용자 고유키. */
    private String userKey;

    /**
     * @return String : userKey
     */
    public String getUserKey() {
        return this.userKey;
    }

    /**
     * @param userKey
     *            String : the userKey to set
     */
    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

}
