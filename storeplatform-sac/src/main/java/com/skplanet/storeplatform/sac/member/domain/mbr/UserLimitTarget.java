/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.domain.mbr;

import com.skplanet.storeplatform.member.client.common.vo.LimitTarget;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 서비스 제한 정책 Entity
 * Client Vo 클래스명
 * </p>
 * Updated on : 2016. 1. 6 Updated by : 임근대, SK 플래닛.
 */
@Entity
@Table(name = "TB_US_OLIMT_TARGET")
@SequenceGenerator(name = "LIMIT_TARGET_SEQ",
        sequenceName="SQ_US_LIMT_TARGET",
        allocationSize=1)
public class UserLimitTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LIMIT_TARGET_SEQ")
    private Integer seq;

    private String limtPolicyCd;
    private Date startDt;
    private Date endDt;
    private String policyApplyValue;
    private String limtPolicyKey;
    private String preLimtAmt;
    @Column(length = 1, columnDefinition = "char(1)")
    private String pmtType;
    @Column(length = 1, columnDefinition = "char(1)")
    private String useYn;
    private String limtAmt;
    private String insDt;
    private String lineMangStatus;

    private Date regDt;
    private String regId;
    private Date updDt;
    private String updId;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
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

    public String getPolicyApplyValue() {
        return policyApplyValue;
    }

    public void setPolicyApplyValue(String policyApplyValue) {
        this.policyApplyValue = policyApplyValue;
    }

    public Date getRegDt() {
        return regDt;
    }

    public void setRegDt(Date regDt) {
        this.regDt = regDt;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public Date getUpdDt() {
        return updDt;
    }

    public void setUpdDt(Date updDt) {
        this.updDt = updDt;
    }

    public String getUpdId() {
        return updId;
    }

    public void setUpdId(String updId) {
        this.updId = updId;
    }

    public String getLimtPolicyKey() {
        return limtPolicyKey;
    }

    public void setLimtPolicyKey(String limtPolicyKey) {
        this.limtPolicyKey = limtPolicyKey;
    }

    public String getPreLimtAmt() {
        return preLimtAmt;
    }

    public void setPreLimtAmt(String preLimtAmt) {
        this.preLimtAmt = preLimtAmt;
    }

    public String getPmtType() {
        return pmtType;
    }

    public void setPmtType(String pmtType) {
        this.pmtType = pmtType;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getLimtAmt() {
        return limtAmt;
    }

    public void setLimtAmt(String limtAmt) {
        this.limtAmt = limtAmt;
    }

    public String getInsDt() {
        return insDt;
    }

    public void setInsDt(String insDt) {
        this.insDt = insDt;
    }

    public String getLineMangStatus() {
        return lineMangStatus;
    }

    public void setLineMangStatus(String lineMangStatus) {
        this.lineMangStatus = lineMangStatus;
    }

    public String getLimtPolicyCd() {
        return limtPolicyCd;
    }

    public void setLimtPolicyCd(String limtPolicyCd) {
        this.limtPolicyCd = limtPolicyCd;
    }

    @PrePersist
    public void prePersist() {
        this.regDt = new Date();
        this.updDt = new Date();

    }

    @PreUpdate
    public void preUpdate() {
        this.updDt = new Date();
    }

    /**
     *
     * @param simpleDateFormat
     * @param startDt
     */
    public void setStartDtString(SimpleDateFormat simpleDateFormat, String startDt) {
        if(StringUtils.isNotEmpty(startDt)) try { this.setStartDt(simpleDateFormat.parse(startDt)); } catch (ParseException ignore) {}
    }

    public void setEndDtString(SimpleDateFormat simpleDateFormat, String endDt) {
        if(StringUtils.isNotEmpty(endDt)) try { this.setEndDt(simpleDateFormat.parse(endDt)); } catch (ParseException ignore) {}
    }

    public void setRegDtString(SimpleDateFormat simpleDateFormat, String regDt) {
        if(StringUtils.isNotEmpty(regDt)) try { this.setRegDt(simpleDateFormat.parse(regDt)); } catch (ParseException ignore) {}
    }

    public void setUpdDtString(SimpleDateFormat simpleDateFormat, String updDt) {
        if(StringUtils.isNotEmpty(updDt)) try { this.setUpdDt(simpleDateFormat.parse(updDt)); } catch (ParseException ignore) {}
    }


    public String getUpdDtString(SimpleDateFormat simpleDateFormat) {
        return updDt != null ? simpleDateFormat.format(updDt) : null;
    }

    public String getRegDtString(SimpleDateFormat simpleDateFormat) {
        return regDt != null ? simpleDateFormat.format(regDt) : null;
    }

    public String getEndDtString(SimpleDateFormat simpleDateFormat) {
        return endDt != null ? simpleDateFormat.format(endDt) : null;
    }

    public String getStartDtString(SimpleDateFormat simpleDateFormat) {
        return startDt != null ? simpleDateFormat.format(startDt) : null;
    }


    /**
     * LimitTarget Domain 객체를 Client 객체로 변환
     * @return 서비스 제한 정책 Client Vo
     */
    public LimitTarget convertToVo() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        LimitTarget limitTargetVo = new LimitTarget();

        limitTargetVo.setLimitTargetNo(String.valueOf(this.getSeq()));
        limitTargetVo.setLimitPolicyKey(this.getLimtPolicyKey());
        limitTargetVo.setLimitPolicyCode(this.getLimtPolicyCd());
        limitTargetVo.setPolicyApplyValue(this.getPolicyApplyValue());
        limitTargetVo.setInsDate(this.getInsDt());
        limitTargetVo.setRegID(this.getRegId());
        limitTargetVo.setUpdateID(this.getUpdId());
        limitTargetVo.setLimitAmount(this.getLimtAmt());
        limitTargetVo.setPreLimitAmount(this.getPreLimtAmt());
        limitTargetVo.setPermissionType(this.getPmtType());
        limitTargetVo.setIsUsed(this.getUseYn());
        limitTargetVo.setLineMangStatus(this.getLineMangStatus());

        limitTargetVo.setStartDate(this.getStartDtString(simpleDateFormat));
        limitTargetVo.setEndDate(this.getEndDtString(simpleDateFormat));
        limitTargetVo.setRegDate(this.getRegDtString(simpleDateFormat));
        limitTargetVo.setUpdateDate(this.getUpdDtString(simpleDateFormat));
        return limitTargetVo;
    }

    /**
     * ClientVo To Entity
     * @param limitTarget ClientVo
     * @return Entity
     */
    public static UserLimitTarget createUserLimitTargetFromVo(LimitTarget limitTarget) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        UserLimitTarget userLimitTargetDomain = new UserLimitTarget();

        if(StringUtils.isNumeric(limitTarget.getLimitTargetNo())) userLimitTargetDomain.setSeq(Integer.parseInt(limitTarget.getLimitTargetNo()));
        userLimitTargetDomain.setLimtPolicyKey(limitTarget.getLimitPolicyKey());
        userLimitTargetDomain.setLimtPolicyCd(limitTarget.getLimitPolicyCode());
        userLimitTargetDomain.setPolicyApplyValue(limitTarget.getPolicyApplyValue());
        userLimitTargetDomain.setUpdId(userLimitTargetDomain.getRegId());
        userLimitTargetDomain.setUpdId(userLimitTargetDomain.getUpdId());
        userLimitTargetDomain.setLimtAmt(limitTarget.getLimitAmount());
        userLimitTargetDomain.setPreLimtAmt(limitTarget.getPreLimitAmount());
        userLimitTargetDomain.setUseYn(limitTarget.getIsUsed());
        userLimitTargetDomain.setLineMangStatus(limitTarget.getLineMangStatus());

        if(StringUtils.isNotEmpty(limitTarget.getPermissionType())) userLimitTargetDomain.setPmtType(limitTarget.getPermissionType());
        if(StringUtils.isNotEmpty(userLimitTargetDomain.getInsDt())) userLimitTargetDomain.setInsDt(limitTarget.getInsDate());
        if(limitTarget.getStartDate() != null) userLimitTargetDomain.setStartDtString(simpleDateFormat, limitTarget.getStartDate());
        if(limitTarget.getEndDate() != null) userLimitTargetDomain.setEndDtString(simpleDateFormat, limitTarget.getEndDate());
        if(userLimitTargetDomain.getRegDt() != null) userLimitTargetDomain.setRegDtString(simpleDateFormat, limitTarget.getRegDate());
        if(userLimitTargetDomain.getUpdDt() != null) userLimitTargetDomain.setUpdDtString(simpleDateFormat, limitTarget.getUpdateDate());

        return userLimitTargetDomain;
    }

}
