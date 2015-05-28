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

import java.util.ArrayList;
import java.util.List;

import com.skplanet.storeplatform.external.client.shopping.util.StringUtil;
import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.display.vo.card.SegmentReq;

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

    private String testMdnYn = "N";
    private String tingYn    = "N";

    public SegmentInfo() {}

    public SegmentInfo(SegmentReq req) {

    	if(req == null) return;

        this.outsdMbrGrdCd = req.getOutsdMbrGrdCd();
        this.insdMbrGrdCd  = req.getInsdMbrGrdCd();
        this.sex           = req.getSex();
        this.ageClsfCd     = req.getAgeClsfCd();
        this.deviceChgYn   = req.getDeviceChgYn();
        this.newEntryYn    = req.getNewEntryYn();
        this.mnoClsfCd     = req.getMnoClsfCd();
        this.categoryBest  = req.getCategoryBest();
        this.testMdnYn     = req.getTestMdnYn();
        this.tingYn        = req.getTingYn();

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
    	this.deviceChgYn = StringUtil.trim( deviceChgYn );
    }

    public String getNewEntryYn() {
        return newEntryYn;
    }

    public void setNewEntryYn(String newEntryYn) {
    	this.newEntryYn = StringUtil.trim( newEntryYn );
    }

    public String getMnoClsfCd() {
        return mnoClsfCd;
    }

    public void setMnoClsfCd(String mnoClsfCd) {
        this.mnoClsfCd = mnoClsfCd;
    }

    public List<String> getCategoryBest() {
        return categoryBest == null ? new ArrayList<String>() : categoryBest;
    }

    public void setCategoryBest(List<String> categoryBest) {
        this.categoryBest = categoryBest;
    }

	public String getTestMdnYn() {
	    return testMdnYn;
    }

	public String getTingYn() {
	    return tingYn;
    }

}
