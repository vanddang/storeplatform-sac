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

import com.google.common.base.Objects;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * <p>
 * UserDevicePK
 * </p>
 * Updated on : 2016. 01. 26 Updated by : 정희원, SK 플래닛.
 */
@Embeddable
public class UserDevicePK implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INSD_USERMBR_NO")
    private UserMember member;

    /**
     * 내부 생성 디바이스 식별자
     */
    private String insdDeviceId;

    public UserDevicePK() {}

    public UserDevicePK(UserMember member, String insdDeviceId) {
        this.member = member;
        this.insdDeviceId = insdDeviceId;
    }

    public UserMember getMember() {
        return member;
    }

    public void setMember(UserMember member) {
        this.member = member;
    }

    public String getInsdDeviceId() {
        return insdDeviceId;
    }

    public void setInsdDeviceId(String insdDeviceId) {
        this.insdDeviceId = insdDeviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDevicePK that = (UserDevicePK) o;
        return Objects.equal(member, that.member) &&
                Objects.equal(insdDeviceId, that.insdDeviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(member, insdDeviceId);
    }
}