/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.intimateMessage.vo;

/**
 * Feature VOD 카테고리 상품 조회 DTO Value Object.
 * 
 * Updated on : 2014. 01. 06. Updated by : 이태희, SK 플래닛.
 */
public class IntimateMessage {
	private int totalCount;
	private String msgId;
	private String msgTypeCd;
	private String mainMsg;
	private String infrMsg;
	private String ofrDesc;
	private String ofrTypeCd;
	private String mainColor;
	private String infrColor;
	private String biImgPath;
	private String gnbImgPath;
	private String regDt;

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return this.totalCount;
	}

	/**
	 * @param totalCount
	 *            the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the msgId
	 */
	public String getMsgId() {
		return this.msgId;
	}

	/**
	 * @param msgId
	 *            the msgId to set
	 */
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	/**
	 * @return the msgTypeCd
	 */
	public String getMsgTypeCd() {
		return this.msgTypeCd;
	}

	/**
	 * @param msgTypeCd
	 *            the msgTypeCd to set
	 */
	public void setMsgTypeCd(String msgTypeCd) {
		this.msgTypeCd = msgTypeCd;
	}

	/**
	 * @return the mainMsg
	 */
	public String getMainMsg() {
		return this.mainMsg;
	}

	/**
	 * @param mainMsg
	 *            the mainMsg to set
	 */
	public void setMainMsg(String mainMsg) {
		this.mainMsg = mainMsg;
	}

	/**
	 * @return the infrMsg
	 */
	public String getInfrMsg() {
		return this.infrMsg;
	}

	/**
	 * @param infrMsg
	 *            the infrMsg to set
	 */
	public void setInfrMsg(String infrMsg) {
		this.infrMsg = infrMsg;
	}

	/**
	 * @return the ofrDesc
	 */
	public String getOfrDesc() {
		return this.ofrDesc;
	}

	/**
	 * @param ofrDesc
	 *            the ofrDesc to set
	 */
	public void setOfrDesc(String ofrDesc) {
		this.ofrDesc = ofrDesc;
	}

	/**
	 * @return the ofrTypeCd
	 */
	public String getOfrTypeCd() {
		return this.ofrTypeCd;
	}

	/**
	 * @param ofrTypeCd
	 *            the ofrTypeCd to set
	 */
	public void setOfrTypeCd(String ofrTypeCd) {
		this.ofrTypeCd = ofrTypeCd;
	}

	/**
	 * @return the mainColor
	 */
	public String getMainColor() {
		return this.mainColor;
	}

	/**
	 * @param mainColor
	 *            the mainColor to set
	 */
	public void setMainColor(String mainColor) {
		this.mainColor = mainColor;
	}

	/**
	 * @return the infrColor
	 */
	public String getInfrColor() {
		return this.infrColor;
	}

	/**
	 * @param infrColor
	 *            the infrColor to set
	 */
	public void setInfrColor(String infrColor) {
		this.infrColor = infrColor;
	}

	/**
	 * @return the biImgPath
	 */
	public String getBiImgPath() {
		return this.biImgPath;
	}

	/**
	 * @param biImgPath
	 *            the biImgPath to set
	 */
	public void setBiImgPath(String biImgPath) {
		this.biImgPath = biImgPath;
	}

	/**
	 * @return the gnbImgPath
	 */
	public String getGnbImgPath() {
		return this.gnbImgPath;
	}

	/**
	 * @param gnbImgPath
	 *            the gnbImgPath to set
	 */
	public void setGnbImgPath(String gnbImgPath) {
		this.gnbImgPath = gnbImgPath;
	}

	/**
	 * @return the regDt
	 */
	public String getRegDt() {
		return this.regDt;
	}

	/**
	 * @param regDt
	 *            the regDt to set
	 */
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
}
