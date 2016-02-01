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

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import org.springframework.data.annotation.Transient;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * PromotionEvent
 * Redis에 저장되는 형태
 * [98dc] 초기
 * [cf17] add userTargetTp
 * </p>
 * Updated on : 2015. 07. 15 Updated by : 정희원, SK 플래닛.
 */
public class PromotionEvent {

    private Integer promId;
    private String acmlMethodCd;
    private String acmlDt;
    private Integer rateGrd1;
    private Integer rateGrd2;
    private Integer rateGrd3;
    private Integer prvAcmlLimt;

    private Date startDt;
    private Date endDt;

    private String targetId;    // chnlId, menuId, topMenuId
    private String userTargetTp;

    private Date updDt;

    public Integer getPromId() {
        return promId;
    }

    public void setPromId(Integer promId) {
        this.promId = promId;
    }

    public String getAcmlMethodCd() {
        return acmlMethodCd;
    }

    public void setAcmlMethodCd(String acmlMethodCd) {
        this.acmlMethodCd = acmlMethodCd;
    }

    public String getAcmlDt() {
        return acmlDt;
    }

    public void setAcmlDt(String acmlDt) {
        this.acmlDt = acmlDt;
    }

    public Integer getRateGrd1() {
        return rateGrd1;
    }

    public void setRateGrd1(Integer rateGrd1) {
        this.rateGrd1 = rateGrd1;
    }

    public Integer getRateGrd2() {
        return rateGrd2;
    }

    public void setRateGrd2(Integer rateGrd2) {
        this.rateGrd2 = rateGrd2;
    }

    public Integer getRateGrd3() {
        return rateGrd3;
    }

    public void setRateGrd3(Integer rateGrd3) {
        this.rateGrd3 = rateGrd3;
    }

    public Date getStartDt() {
        return startDt;
    }

    public void setStartDt(Date startDt) {
        this.startDt = startDt;
    }

    public Date getEndDt() {
        return endDt;
    }

    public void setEndDt(Date endDt) {
        this.endDt = endDt;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public Integer getPrvAcmlLimt() {
        return prvAcmlLimt;
    }

    public void setPrvAcmlLimt(Integer prvAcmlLimt) {
        this.prvAcmlLimt = prvAcmlLimt;
    }

    public String getUserTargetTp() {
        return userTargetTp;
    }

    public void setUserTargetTp(String userTargetTp) {
        this.userTargetTp = userTargetTp;
    }

    public Date getUpdDt() {
        return updDt;
    }

    public void setUpdDt(Date updDt) {
        this.updDt = updDt;
    }

    @Transient
    public boolean isNeedsUserTargetSync() {
        return !StringUtils.equals(this.userTargetTp, DisplayConstants.PROM_TARGET_USER_ALL);
    }

    @Transient
    public String getTargetTp() {
        return StringUtils.defaultString(targetId).startsWith("DP") ? "DP01160102" : "DP01160101";
    }

    @Transient
    public boolean isLiveFor(Date now) {
        return !(this.startDt == null || this.endDt == null || now == null) &&
                !now.before(startDt) && !now.after(endDt);
    }

    @Transient
    public String makeDatetimeKey() {
        return DateUtils.format(startDt) + "_" + DateUtils.format(endDt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PromotionEvent event = (PromotionEvent) o;

        if (!promId.equals(event.promId)) return false;
        if (!acmlMethodCd.equals(event.acmlMethodCd)) return false;
        if (!acmlDt.equals(event.acmlDt)) return false;
        if (!rateGrd1.equals(event.rateGrd1)) return false;
        if (!rateGrd2.equals(event.rateGrd2)) return false;
        if (!rateGrd3.equals(event.rateGrd3)) return false;
        if (prvAcmlLimt != null ? !prvAcmlLimt.equals(event.prvAcmlLimt) : event.prvAcmlLimt != null) return false;
        if (!startDt.equals(event.startDt)) return false;
        if (!endDt.equals(event.endDt)) return false;
        if (!userTargetTp.equals(event.userTargetTp)) return false;
        if (!updDt.equals(event.updDt)) return false;
        return targetId.equals(event.targetId);

    }

    @Override
    public int hashCode() {
        int result = promId.hashCode();
        result = 31 * result + acmlMethodCd.hashCode();
        result = 31 * result + acmlDt.hashCode();
        result = 31 * result + rateGrd1.hashCode();
        result = 31 * result + rateGrd2.hashCode();
        result = 31 * result + rateGrd3.hashCode();
        result = 31 * result + (prvAcmlLimt != null ? prvAcmlLimt.hashCode() : 0);
        result = 31 * result + startDt.hashCode();
        result = 31 * result + endDt.hashCode();
        result = 31 * result + targetId.hashCode();
        result = 31 * result + userTargetTp.hashCode();
        result = 31 * result + updDt.hashCode();
        return result;
    }
}
