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

import java.util.List;

/**
 * <p>
 * Panel
 * </p>
 * Updated on : 2014. 10. 08 Updated by : 정희원, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Panel extends CommonInfo {
    private static final long serialVersionUID = 1L;

    /**
     * 패널 ID
     */
    private String id;

    /**
     * 패널명
     */
    private String name;

    /**
     * 패널 Level
     */
    private Integer level;

    /**
     * 최대 카드 전시 개수
     */
    private Integer maxDpCardCnt;

    /**
     * 그룹 정보
     */
    private List<Panel> subGroup;

    /**
     * 카드 목록
     */
    private List<Card> cardList;

    /**
     * 서브패널 더 존재여부
     */
    private String moreYn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getMaxDpCardCnt() {
        return maxDpCardCnt;
    }

    public void setMaxDpCardCnt(Integer maxDpCardCnt) {
        this.maxDpCardCnt = maxDpCardCnt;
    }

    public List<Panel> getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(List<Panel> subGroup) {
        this.subGroup = subGroup;
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }

    public String getMoreYn() {
        return moreYn;
    }

    public void setMoreYn(String moreYn) {
        this.moreYn = moreYn;
    }
}
