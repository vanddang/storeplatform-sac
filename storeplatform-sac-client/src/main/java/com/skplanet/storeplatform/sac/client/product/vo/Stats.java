/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * <p>
 * 카드 통계
 * </p>
 * Updated on : 2014. 10. 08 Updated by : 정희원, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Stats extends CommonInfo {
    private static final long serialVersionUID = 1L;

    /**
     * 통계구분
     */
    private String clsf;

    /**
     * 상품ID or 카드ID
     */
    private String key;

    /**
     * 카드 - [좋아요] 건수
     */
    private Integer cntLike;

    /**
     * 카드 - [공유] 건수
     */
    private Integer cntShar;

    public String getClsf() {
        return clsf;
    }

    public void setClsf(String clsf) {
        this.clsf = clsf;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getCntLike() {
        return cntLike;
    }

    public void setCntLike(Integer cntLike) {
        this.cntLike = cntLike;
    }

    public Integer getCntShar() {
        return cntShar;
    }

    public void setCntShar(Integer cntShar) {
        this.cntShar = cntShar;
    }
}
