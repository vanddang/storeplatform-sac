/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.vo.history;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * 선물수신확인 요청.
 * 
 * Updated on : 2013. 12. 28. Updated by : 조용진, 엔텔스.
 */
public class GiftReceiveReq extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tenantId; // Tenant ID
	private String sendMbrNo; // 회원번호
	private String sendDeviceNo; // 디바이스 번호
	private String recvMbrNo; // 회원번호
	private String recvDeviceNo; // 디바이스 번호
	private String prodId; // 상품 아이디
	private String prchsId; // 구매ID

	/**
	 * @return the prchsId
	 */
	public String getPrchsId() {
		return this.prchsId;
	}

	/**
	 * @param prchsId
	 *            the prchsId to set
	 */
	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the sendMbrNo
	 */
	public String getSendMbrNo() {
		return this.sendMbrNo;
	}

	/**
	 * @param sendMbrNo
	 *            the sendMbrNo to set
	 */
	public void setSendMbrNo(String sendMbrNo) {
		this.sendMbrNo = sendMbrNo;
	}

	/**
	 * @return the sendDeviceNo
	 */
	public String getSendDeviceNo() {
		return this.sendDeviceNo;
	}

	/**
	 * @param sendDeviceNo
	 *            the sendDeviceNo to set
	 */
	public void setSendDeviceNo(String sendDeviceNo) {
		this.sendDeviceNo = sendDeviceNo;
	}

	/**
	 * @return the recvMbrNo
	 */
	public String getRecvMbrNo() {
		return this.recvMbrNo;
	}

	/**
	 * @param recvMbrNo
	 *            the recvMbrNo to set
	 */
	public void setRecvMbrNo(String recvMbrNo) {
		this.recvMbrNo = recvMbrNo;
	}

	/**
	 * @return the recvDeviceNo
	 */
	public String getRecvDeviceNo() {
		return this.recvDeviceNo;
	}

	/**
	 * @param recvDeviceNo
	 *            the recvDeviceNo to set
	 */
	public void setRecvDeviceNo(String recvDeviceNo) {
		this.recvDeviceNo = recvDeviceNo;
	}

	/**
	 * @return the prodId
	 */
	public String getProdId() {
		return this.prodId;
	}

	/**
	 * @param prodId
	 *            the prodId to set
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
