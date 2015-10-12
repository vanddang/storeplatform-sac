package com.skplanet.storeplatform.sac.client.member.vo.user;

import org.hibernate.validator.constraints.NotEmpty;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 배송지 정보 등록/수정
 * 
 * Updated on : 2015. 10. 02. Updated by : 최진호, Bogogt.
 */
public class CreateDeliveryInfoSacReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 사용자 Key */
	@NotEmpty
	private String userKey;

	/** 배송지 타입 */
	private String deliveryTypeCd;

	/** 배송지 이름 */
	private String deliveryNm;

	/** 받는 사람 */
	private String receiverNm;

	/** 보내는 사람 */
	private String senderNm;

	/** 우편 번호 */
	private String zip;

	/** 주소 */
	private String Addr;

	/** 상세 주소 */
	private String dtlAddr;

	/** 연락처 */
	private String connTelNo;

	/** 남기는 말씀 */
	private String deliveryMsg;

	/** 배송지 시퀀스 (등록 X, 수정 필수) */
	private String deliverySeq;

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the deliveryTypeCd
	 */
	public String getDeliveryTypeCd() {
		return this.deliveryTypeCd;
	}

	/**
	 * @param deliveryTypeCd
	 *            the deliveryTypeCd to set
	 */
	public void setDeliveryTypeCd(String deliveryTypeCd) {
		this.deliveryTypeCd = deliveryTypeCd;
	}

	/**
	 * @return the deliveryNm
	 */
	public String getDeliveryNm() {
		return this.deliveryNm;
	}

	/**
	 * @param deliveryNm
	 *            the deliveryNm to set
	 */
	public void setDeliveryNm(String deliveryNm) {
		this.deliveryNm = deliveryNm;
	}

	/**
	 * @return the receiverNm
	 */
	public String getReceiverNm() {
		return this.receiverNm;
	}

	/**
	 * @param receiverNm
	 *            the receiverNm to set
	 */
	public void setReceiverNm(String receiverNm) {
		this.receiverNm = receiverNm;
	}

	/**
	 * @return the senderNm
	 */
	public String getSenderNm() {
		return this.senderNm;
	}

	/**
	 * @param senderNm
	 *            the senderNm to set
	 */
	public void setSenderNm(String senderNm) {
		this.senderNm = senderNm;
	}

	/**
	 * @return the zip
	 */
	public String getZip() {
		return this.zip;
	}

	/**
	 * @param zip
	 *            the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @return the addr
	 */
	public String getAddr() {
		return this.Addr;
	}

	/**
	 * @param addr
	 *            the addr to set
	 */
	public void setAddr(String addr) {
		this.Addr = addr;
	}

	/**
	 * @return the dtlAddr
	 */
	public String getDtlAddr() {
		return this.dtlAddr;
	}

	/**
	 * @param dtlAddr
	 *            the dtlAddr to set
	 */
	public void setDtlAddr(String dtlAddr) {
		this.dtlAddr = dtlAddr;
	}

	/**
	 * @return the connTelNo
	 */
	public String getConnTelNo() {
		return this.connTelNo;
	}

	/**
	 * @param connTelNo
	 *            the connTelNo to set
	 */
	public void setConnTelNo(String connTelNo) {
		this.connTelNo = connTelNo;
	}

	/**
	 * @return the deliveryMsg
	 */
	public String getDeliveryMsg() {
		return this.deliveryMsg;
	}

	/**
	 * @param deliveryMsg
	 *            the deliveryMsg to set
	 */
	public void setDeliveryMsg(String deliveryMsg) {
		this.deliveryMsg = deliveryMsg;
	}

	/**
	 * @return the deliverySeq
	 */
	public String getDeliverySeq() {
		return this.deliverySeq;
	}

	/**
	 * @param deliverySeq
	 *            the deliverySeq to set
	 */
	public void setDeliverySeq(String deliverySeq) {
		this.deliverySeq = deliverySeq;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
