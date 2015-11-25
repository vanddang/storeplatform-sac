/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * <p>
 * Preference
 * 구매자 개인의 선호도 정보를 표현하는 객체
 * </p>
 * Updated on : 2014. 11. 07 Updated by : 정희원, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Preference extends CommonInfo {

    private static final long serialVersionUID = 1L;

    public Preference() {}

    public Preference(String likeYn) {
        this.likeYn = likeYn;
    }

    private String likeYn;

    public String getLikeYn() {
        return likeYn;
    }

    public void setLikeYn(String likeYn) {
        this.likeYn = likeYn;
    }
}
