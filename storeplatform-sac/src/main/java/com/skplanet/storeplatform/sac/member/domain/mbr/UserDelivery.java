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

import com.google.common.base.Strings;
import com.google.common.primitives.Longs;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeliveryInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeliveryInfoSacReq;
import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.member.domain.shared.UserMember;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>
 * UserDelivery
 * </p>
 * Updated on : 2016. 01. 19 Updated by : 정희원, SK 플래닛.
 */
@Entity
@Table(name = "TB_US_OUSERMBR_DELIVERY")
@SequenceGenerator(name = "DELIVERY", sequenceName = "SQ_US_OUSERMBR_DELIVERY", allocationSize = 1)
public class UserDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELIVERY")
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INSD_USERMBR_NO", updatable = false)
    private UserMember member;

    /**
     * what code?
     */
    private String deliveryTypeCd;

    private String deliveryNm;

    private Date useDt;

    @Column(name = "RECEVER_NM")
    private String receiverNm;

    private String senderNm;

    private String zip;

    private String addr;

    private String dtlAddr;

    private String connTelNo;

    private String deliveryMsg;

    private Date regDt;

    ///// Business
    public DeliveryInfo convertToDeliveryInfo() {
        DeliveryInfo v = new DeliveryInfo();
        v.setDeliveryTypeCd(deliveryTypeCd);
        v.setDeliveryNm(deliveryNm);
        v.setReceiverNm(receiverNm);
        v.setSenderNm(senderNm);
        v.setZip(zip);
        v.setAddr(addr);
        v.setDtlAddr(dtlAddr);
        v.setConnTelNo(connTelNo);
        v.setDeliveryMsg(deliveryMsg);
        v.setDeliverySeq(seq.toString());
        v.setRegDate(DateUtils.format(regDt));
        v.setUseDate(DateUtils.format(useDt));

        return v;
    }

    public static UserDelivery convertFromRequest(CreateDeliveryInfoSacReq req) {
        Assert.notNull(req);

        if(Strings.isNullOrEmpty(req.getDeliverySeq()) && Strings.isNullOrEmpty(req.getDeliveryTypeCd()))
            throw new StorePlatformException("SC_MEM_9993");

        UserDelivery ud = new UserDelivery();
        ud.setSeq(Longs.tryParse(Strings.nullToEmpty(req.getDeliverySeq())));
        ud.setDeliveryTypeCd(req.getDeliveryTypeCd());
        ud.setDeliveryNm(req.getDeliveryNm());
        ud.setReceiverNm(req.getReceiverNm());
        ud.setSenderNm(req.getSenderNm());
        ud.setZip(req.getZip());
        ud.setAddr(req.getAddr());
        ud.setDtlAddr(req.getDtlAddr());
        ud.setConnTelNo(req.getConnTelNo());
        ud.setDeliveryMsg(req.getDeliveryMsg());

        return ud;
    }

    @PrePersist
    public void prePersist() {
        regDt = new Date();
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public UserMember getMember() {
        return member;
    }

    public void setMember(UserMember member) {
        this.member = member;
    }

    public String getDeliveryTypeCd() {
        return deliveryTypeCd;
    }

    public void setDeliveryTypeCd(String deliveryTypeCd) {
        this.deliveryTypeCd = deliveryTypeCd;
    }

    public String getDeliveryNm() {
        return deliveryNm;
    }

    public void setDeliveryNm(String deliveryNm) {
        this.deliveryNm = deliveryNm;
    }

    public Date getUseDt() {
        return useDt;
    }

    public void setUseDt(Date useDt) {
        this.useDt = useDt;
    }

    public String getReceiverNm() {
        return receiverNm;
    }

    public void setReceiverNm(String receiverNm) {
        this.receiverNm = receiverNm;
    }

    public String getSenderNm() {
        return senderNm;
    }

    public void setSenderNm(String senderNm) {
        this.senderNm = senderNm;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getDtlAddr() {
        return dtlAddr;
    }

    public void setDtlAddr(String dtlAddr) {
        this.dtlAddr = dtlAddr;
    }

    public String getConnTelNo() {
        return connTelNo;
    }

    public void setConnTelNo(String connTelNo) {
        this.connTelNo = connTelNo;
    }

    public String getDeliveryMsg() {
        return deliveryMsg;
    }

    public void setDeliveryMsg(String deliveryMsg) {
        this.deliveryMsg = deliveryMsg;
    }

    public Date getRegDt() {
        return regDt;
    }

    public void setRegDt(Date regDt) {
        this.regDt = regDt;
    }
}
