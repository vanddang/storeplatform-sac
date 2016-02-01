/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.common.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 구매 Extra Info VO
 * 
 * Updated on : 2016. 01. 15. Updated by : eastaim
 */
public class ExtraInfo extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String prchsId;
	private String infoTypeCd;
    private Integer infoSeq;
	private String col01;
	private String col02;
    private String col03;
    private String col04;
    private String col05;
    private String text01;
    private String text02;
	private String regId;
	private String regDt;
	private String updId;
	private String updDt;

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
     *
     * @return the prchsId
     */
    public String getPrchsId() {
        return prchsId;
    }

    /**
     * set prchsId
     * @param prchsId
     */
    public void setPrchsId(String prchsId) {
        this.prchsId = prchsId;
    }

    /**
     * get InfoTypeCd
     * @return
     */
    public String getInfoTypeCd() {
        return infoTypeCd;
    }

    /**
     * set InfoTypeCd
     * @param infoTypeCd
     */
    public void setInfoTypeCd(String infoTypeCd) {
        this.infoTypeCd = infoTypeCd;
    }

    /**
     * get InfoSeq
     * @return
     */
    public Integer getInfoSeq() {
        return infoSeq;
    }

    /**
     * set InfoSeq
     * @param infoSeq
     */
    public void setInfoSeq(Integer infoSeq) {
        this.infoSeq = infoSeq;
    }

    /**
     * get Col01 Value
     * @return
     */
    public String getCol01() {
        return col01;
    }

    /**
     * set Col01 Value
     * @param col01
     */
    public void setCol01(String col01) {
        this.col01 = col01;
    }

    /**
     * get col_02 Value
     * @return
     */
    public String getCol02() {
        return col02;
    }

    /**
     * set col_02 value
     * @param col02
     */
    public void setCol02(String col02) {
        this.col02 = col02;
    }

    /**
     * get col_03 Value
     * @return
     */
    public String getCol03() {
        return col03;
    }

    /**
     * set col_03 value
     * @param col03
     */
    public void setCol03(String col03) {
        this.col03 = col03;
    }

    /**
     * get col_04 value
     * @return
     */
    public String getCol04() {
        return col04;
    }

    /**
     * set col_04 value
     * @param col04
     */
    public void setCol04(String col04) {
        this.col04 = col04;
    }

    /**
     * get col_05 value
     * @return
     */
    public String getCol05() {
        return col05;
    }

    /**
     * set col_05 value
     * @param col05
     */
    public void setCol05(String col05) {
        this.col05 = col05;
    }

    /**
     * get text_01 value
     * @return
     */
    public String getText01() {
        return text01;
    }

    /**
     * set text_01 value
     * @param text01
     */
    public void setText01(String text01) {
        this.text01 = text01;
    }

    /**
     * get text_02 value
     * @return
     */
    public String getText02() {
        return text02;
    }

    /**
     * set text_02 value
     * @param text02
     */
    public void setText02(String text02) {
        this.text02 = text02;
    }

    /**
     * 등록자/시스템ID 조회
     * @return
     */
    public String getRegId() {
        return regId;
    }

    /**
     * 등록자/시스템ID 저장
     * @param regId
     */
    public void setRegId(String regId) {
        this.regId = regId;
    }

    /**
     * 등록일시
     * @return
     */
    public String getRegDt() {
        return regDt;
    }

    /**
     * 등록일시 설정
     * @param regDt
     */
    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    /**
     * 수정ID 조회
     * @return
     */
    public String getUpdId() {
        return updId;
    }

    /**
     * 수정ID 설정
     * @param updId
     */
    public void setUpdId(String updId) {
        this.updId = updId;
    }

    /**
     * 수정일시
     * @return
     */
    public String getUpdDt() {
        return updDt;
    }

    /**
     * 수정일시 조회
     * @param updDt
     */
    public void setUpdDt(String updDt) {
        this.updDt = updDt;
    }
}
