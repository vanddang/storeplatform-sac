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
/**
 * <pre>
 * 전처리 상품 옵션 Value Object
 * </pre>
 *
 * Created on : 2014-01-02
 * Created by : 김형식, SK플래닛
 * Last Updated on : 2014-01-02
 * Last Updated by : 김형식, SK플래닛
 */
public class TbDpProdOpt {
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

	public String getChnlProdId() {
		return this.chnlProdId;
	}

	public void setChnlProdId(String chnlProdId) {
		this.chnlProdId = chnlProdId;
	}

	public String getEpsdProdId() {
		return this.epsdProdId;
	}

	public void setEpsdProdId(String epsdProdId) {
		this.epsdProdId = epsdProdId;
	}

	public String getOpt1Nm() {
		return this.opt1Nm;
	}

	public void setOpt1Nm(String opt1Nm) {
		this.opt1Nm = opt1Nm;
	}

	public String getOpt2Nm() {
		return this.opt2Nm;
	}

	public void setOpt2Nm(String opt2Nm) {
		this.opt2Nm = opt2Nm;
	}

	public long getExpoOrd() {
		return this.expoOrd;
	}

	public void setExpoOrd(long expoOrd) {
		this.expoOrd = expoOrd;
	}

	public String getExpoYn() {
		return this.expoYn;
	}

	public void setExpoYn(String expoYn) {
		this.expoYn = expoYn;
	}

	public String getRegId() {
		return this.regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getRegDt() {
		return this.regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getUpdId() {
		return this.updId;
	}

	public void setUpdId(String updId) {
		this.updId = updId;
	}

	public String getUpdDt() {
		return this.updDt;
	}

	public void setUpdDt(String updDt) {
		this.updDt = updDt;
	}

}
