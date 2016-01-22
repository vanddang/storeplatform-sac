/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.domain.shared;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * UserMemberTransform
 * 업무상 Join하여 사용할 일이 없어서 UserMember에 해당하는 키를 문자열로 받게 하였다.
 * </p>
 * Updated on : 2016. 01. 22 Updated by : 정희원, SK 플래닛.
 */
@Entity
@Table(name = "TB_US_OUSERMBR_IDMBR_TRFM_CHAS")
public class UserMemberTransform {

    @Id
    private Long seq;

    private Date regDt;

    private String regId;

    @Column(name = "PRE_INSD_USERMBR_NO")
    private String beforeUserKey;

    @Column(name = "AFTER_INSD_USERMBR_NO")
    private String afterUserKey;

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
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

    public String getBeforeUserKey() {
        return beforeUserKey;
    }

    public void setBeforeUserKey(String beforeUserKey) {
        this.beforeUserKey = beforeUserKey;
    }

    public String getAfterUserKey() {
        return afterUserKey;
    }

    public void setAfterUserKey(String afterUserKey) {
        this.afterUserKey = afterUserKey;
    }
}
