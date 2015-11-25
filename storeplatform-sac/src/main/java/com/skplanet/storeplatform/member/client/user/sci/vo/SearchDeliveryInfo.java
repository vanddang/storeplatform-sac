package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.member.client.common.util.Utils;

/**
 * 배송지 정보 Value Object
 * 
 * Updated on : 2015. 09. 30. Updated by : 최진호, Bogogt
 */
public class SearchDeliveryInfo extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** 테넌트 id. */
	private String tenantId;

	/** 사용자 key. */
	private String userKey;

	/** 배송지 타입 코드. */
	private String deliveryTypeCd;

	/** 배송지 이름. */
	private String deliveryNm;

	/** 받는 사람. */
	private String receiverNm;

	/** 보내는 사람. */
	private String senderNm;

	/** 우편번호. */
	private String zip;

	/** 주소. */
	private String addr;

	/** 상세 주소. */
	private String dtlAddr;

	/** 연락처. */
	private String connTelNo;

	/** 남기는 말씀. */
	private String deliveryMsg;

	/** 배송지 시퀀스. */
	private String deliverySeq;

	/** 등록 일자. */
	private String regDate;

	/** 사용 일자. */
	private String useDate;

	/**
	 * 배송지 타입 코드를 리턴한다.
	 * 
	 * @return deliveryTypeCd 배송지 타입 코드
	 */
	public String getDeliveryTypeCd() {
		return this.deliveryTypeCd;
	}

	/**
	 * 배송지 타입 코드를 셋팅한다.
	 * 
	 * @param deliveryTypeCd
	 *            배송지 타입 코드
	 */
	public void setDeliveryTypeCd(String deliveryTypeCd) {
		this.deliveryTypeCd = deliveryTypeCd;
	}

	/**
	 * 배송지 이름을 리턴한다.
	 * 
	 * @return deliveryNm - 배송지 이름
	 */
	public String getDeliveryNm() {
		return this.deliveryNm;
	}

	/**
	 * 배송지 이름을 셋팅한다.
	 * 
	 * @param deliveryNm
	 *            배송지 이름
	 */
	public void setDeliveryNm(String deliveryNm) {
		this.deliveryNm = deliveryNm;
	}

	/**
	 * 받는 사람을 리턴한다.
	 * 
	 * @return receiverNm - 받는 사람
	 */
	public String getReceiverNm() {
		return this.receiverNm;
	}

	/**
	 * 받는 사람을 셋팅한다.
	 * 
	 * @param receiverNm
	 *            받는 사람
	 */
	public void setReceiverNm(String receiverNm) {
		this.receiverNm = receiverNm;
	}

	/**
	 * 보내는 사람을 리턴한다.
	 * 
	 * @return senderNm - 보내는 사람
	 */
	public String getSenderNm() {
		return this.senderNm;
	}

	/**
	 * 보내는 사람을 셋팅한다.
	 * 
	 * @param senderNm
	 *            보내는 사람
	 */
	public void setSenderNm(String senderNm) {
		this.senderNm = senderNm;
	}

	/**
	 * 우편번호를 리턴한다.
	 * 
	 * @return zip - 우편번호
	 */
	public String getZip() {
		return this.zip;
	}

	/**
	 * 우편번호를 셋팅한다.
	 * 
	 * @param zip
	 *            우편번호
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * 주소를 리턴한다.
	 * 
	 * @return addr - 주소
	 */
	public String getAddr() {
		return this.addr;
	}

	/**
	 * 주소를 셋팅한다.
	 * 
	 * @param addr
	 *            주소
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}

	/**
	 * 상세 주소를 리턴한다.
	 * 
	 * @return dtlAddr - 상세 주소
	 */
	public String getDtlAddr() {
		return this.dtlAddr;
	}

	/**
	 * 상세 주소를 셋팅한다.
	 * 
	 * @param dtlAddr
	 *            상세 주소
	 */
	public void setDtlAddr(String dtlAddr) {
		this.dtlAddr = dtlAddr;
	}

	/**
	 * 연락처를 리턴한다.
	 * 
	 * @return connTelNo - 연락처
	 */
	public String getConnTelNo() {
		return this.connTelNo;
	}

	/**
	 * 연락처를 셋팅한다.
	 * 
	 * @param connTelNo
	 *            연락처
	 */
	public void setConnTelNo(String connTelNo) {
		this.connTelNo = connTelNo;
	}

	/**
	 * 남기는 말씀을 리턴한다.
	 * 
	 * @return deliveryMsg - 남기는 말씀
	 */
	public String getDeliveryMsg() {
		return this.deliveryMsg;
	}

	/**
	 * 남기는 말씀을 셋팅한다.
	 * 
	 * @param deliveryMsg
	 *            남기는 말씀
	 */
	public void setDeliveryMsg(String deliveryMsg) {
		this.deliveryMsg = deliveryMsg;
	}

	/**
	 * 배송지 시퀀스을 리턴한다.
	 * 
	 * @return deliverySeq - 배송지 시퀀스
	 */
	public String getDeliverySeq() {
		return this.deliverySeq;
	}

	/**
	 * 배송지 시퀀스을 셋팅한다.
	 * 
	 * @param deliverySeq
	 *            배송지 시퀀스
	 */
	public void setDeliverySeq(String deliverySeq) {
		this.deliverySeq = deliverySeq;
	}

	/**
	 * 등록 일자를 리턴한다.
	 * 
	 * @return regDate - 등록 일자
	 */
	public String getRegDate() {
		return this.regDate;
	}

	/**
	 * 등록 일자를 셋팅한다.
	 * 
	 * @param regDate
	 *            등록 일자
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * 사용 일자를 리턴한다.
	 * 
	 * @return useDate - 사용 일자
	 */
	public String getUseDate() {
		return this.useDate;
	}

	/**
	 * 사용 일자를 셋팅한다.
	 * 
	 * @param useDate
	 *            사용 일자
	 */
	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}

	/**
	 * Returns the serial version UID.
	 * 
	 * @return serialVersionUID - the serial version UID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Utils.printKeyValues(this);
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

}
