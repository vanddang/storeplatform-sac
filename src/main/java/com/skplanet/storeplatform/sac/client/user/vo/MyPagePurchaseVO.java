/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.user.vo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;

/**
 * 
 * 마이페이지 구매
 * 
 * Updated on : 2013. 11. 28. Updated by : 김현일, 인크로스.
 */
@ProtobufMapping(MyPagePurchaseProto.MyPagePurchase.class)
public class MyPagePurchaseVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private String pid;

	private String payId;

	private String userId;

	private String purchaseId;

	private double num;

	/**
	 * @return the num
	 */
	public double getNum() {
		return this.num;
	}

	/**
	 * @param num
	 *            the num to set
	 */
	public void setNum(double num) {
		this.num = num;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return int
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param id
	 *            id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getPid() {
		return this.pid;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param pid
	 *            pid
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getPayId() {
		return this.payId;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param payId
	 *            payId
	 */
	public void setPayId(String payId) {
		this.payId = payId;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param userId
	 *            userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getPurchaseId() {
		return this.purchaseId;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param purchaseId
	 *            purchaseId
	 */
	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
