/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.cache.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.display.vo.card.SegmentReq;

import java.util.List;

/**
 * <p>
 * SegmentInfo
 * </p>
 * Updated on : 2014. 10. 29 Updated by : 정희원, SK 플래닛.
 */
public class SegmentInfo extends CommonInfo {
    private final static long serialVersionUID = 1L;

    private String outsdMbrGrdCd;

    private String insdMbrGrdCd;

    private String sex;

    private String ageClsfCd;

    private String deviceChgYn;

    private String newEntryYn;

    private String mnoClsfCd;

    private List<String> categoryBest;

    public SegmentInfo() {}

    public SegmentInfo(SegmentReq req) {
        if(req == null)
            return;

        this.outsdMbrGrdCd = req.getOutsdMbrGrdCd();
        this.insdMbrGrdCd = req.getInsdMbrGrdCd();
        this.sex = req.getSex();
        this.ageClsfCd = req.getAgeClsfCd();
        this.deviceChgYn = req.getDeviceChgYn();
        this.newEntryYn = req.getNewEntryYn();
        this.mnoClsfCd = req.getMnoClsfCd();
        this.categoryBest = req.getCategoryBest();
    }

    public String getOutsdMbrGrdCd() {
        return outsdMbrGrdCd;
    }

    public void setOutsdMbrGrdCd(String outsdMbrGrdCd) {
        this.outsdMbrGrdCd = outsdMbrGrdCd;
    }

    public String getInsdMbrGrdCd() {
        return insdMbrGrdCd;
    }

    public void setInsdMbrGrdCd(String insdMbrGrdCd) {
        this.insdMbrGrdCd = insdMbrGrdCd;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAgeClsfCd() {
        return ageClsfCd;
    }

    public void setAgeClsfCd(String ageClsfCd) {
        this.ageClsfCd = ageClsfCd;
    }

    public String getDeviceChgYn() {
        return deviceChgYn;
    }

    public void setDeviceChgYn(String deviceChgYn) {
        this.deviceChgYn = deviceChgYn;
    }

    public String getNewEntryYn() {
        return newEntryYn;
    }

    public void setNewEntryYn(String newEntryYn) {
        this.newEntryYn = newEntryYn;
    }

    public String getMnoClsfCd() {
        return mnoClsfCd;
    }

    public void setMnoClsfCd(String mnoClsfCd) {
        this.mnoClsfCd = mnoClsfCd;
    }

    public List<String> getCategoryBest() {
        return categoryBest;
    }

    public void setCategoryBest(List<String> categoryBest) {
        this.categoryBest = categoryBest;
    }
}
