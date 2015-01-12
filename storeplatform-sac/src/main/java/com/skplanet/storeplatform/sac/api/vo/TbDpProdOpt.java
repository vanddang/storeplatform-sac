/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.api.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <pre>
 * 전처리 상품 옵션 Value Object.
 * </pre>
 * 
 * Created on : 2014-01-02 Created by : 김형식, SK플래닛 Last Updated on : 2014-01-02 Last Updated by : 김형식, SK플래닛
 */
public class TbDpProdOpt extends CommonInfo {
	private static final long serialVersionUID = 1L;
	private String chnlProdId; // 채널_상품_ID
	private String epsdProdId; // 에피소드_상품_ID
	private String opt1Nm; // 옵션1_명
	private String opt2Nm; // 옵션2_명
	private long expoOrd; // 노출_순서
	private String expoYn; // 노출_여부
	private String regId; // 등록_ID
	private String regDt; // 등록_일시
	private String updId; // 수정_ID
	private String updDt; // 수정_일시

	/**
	 * @return the chnlProdId
	 */
	public String getChnlProdId() {
		return this.chnlProdId;
	}

	/**
	 * @param chnlProdId
	 *            the chnlProdId to set
	 */
	public void setChnlProdId(String chnlProdId) {
		this.chnlProdId = chnlProdId;
	}

	/**
	 * @return the epsdProdId
	 */
	public String getEpsdProdId() {
		return this.epsdProdId;
	}

	/**
	 * @param epsdProdId
	 *            the epsdProdId to set
	 */
	public void setEpsdProdId(String epsdProdId) {
		this.epsdProdId = epsdProdId;
	}

	/**
	 * @return the opt1Nm
	 */
	public String getOpt1Nm() {
		return this.opt1Nm;
	}

	/**
	 * @param opt1Nm
	 *            the opt1Nm to set
	 */
	public void setOpt1Nm(String opt1Nm) {
		this.opt1Nm = opt1Nm;
	}

	/**
	 * @return the opt2Nm
	 */
	public String getOpt2Nm() {
		return this.opt2Nm;
	}

	/**
	 * @param opt2Nm
	 *            the opt2Nm to set
	 */
	public void setOpt2Nm(String opt2Nm) {
		this.opt2Nm = opt2Nm;
	}

	/**
	 * @return the expoOrd
	 */
	public long getExpoOrd() {
		return this.expoOrd;
	}

	/**
	 * @param expoOrd
	 *            the expoOrd to set
	 */
	public void setExpoOrd(long expoOrd) {
		this.expoOrd = expoOrd;
	}

	/**
	 * @return the expoYn
	 */
	public String getExpoYn() {
		return this.expoYn;
	}

	/**
	 * @param expoYn
	 *            the expoYn to set
	 */
	public void setExpoYn(String expoYn) {
		this.expoYn = expoYn;
	}

	/**
	 * @return the regId
	 */
	public String getRegId() {
		return this.regId;
	}

	/**
	 * @param regId
	 *            the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
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

	/**
	 * @return the updId
	 */
	public String getUpdId() {
		return this.updId;
	}

	/**
	 * @param updId
	 *            the updId to set
	 */
	public void setUpdId(String updId) {
		this.updId = updId;
	}

	/**
	 * @return the updDt
	 */
	public String getUpdDt() {
		return this.updDt;
	}

	/**
	 * @param updDt
	 *            the updDt to set
	 */
	public void setUpdDt(String updDt) {
		this.updDt = updDt;
	}

}
