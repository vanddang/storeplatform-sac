/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.card;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * <p>
 * SegmentReq
 * </p>
 * Updated on : 2014. 10. 08 Updated by : 정희원, SK 플래닛.
 */
public class SegmentReq extends CommonInfo {

    private static final long serialVersionUID = 1L;

    @NotBlank
    private String outsdMbrGrdCd;

    @NotBlank
    private String insdMbrGrdCd;

    @Pattern(regexp = "M|F|X", message = "")
    private String sex;

    @NotBlank
    private String ageClsfCd;

    @Pattern(regexp = "Y|N")
    private String deviceChgYn;

    @Pattern(regexp = "Y|N")
    private String newEntryYn;

    @NotBlank
    private String mnoClsfCd;

    private List<String> categoryBest;

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
