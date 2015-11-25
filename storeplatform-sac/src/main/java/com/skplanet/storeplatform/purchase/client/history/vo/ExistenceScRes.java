/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.history.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * 
 * 기구매체크 응답.
 * 
 * Updated on : 2013. 12. 28. Updated by : 조용진, 엔텔스.
 */
public class ExistenceScRes extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String prodId; // 상품ID
	private String prchsId; // 구매ID
	private String prchsDt; // 구매일시
	private String statusCd; // 구매상태
	private String useInsdDeviceId; // 사용자 디바이스키
	private String useInsdUsermbrNo; // 사용자 디바이스키
	private String tenantProdGrpCd; // 테넌트상품그룹코드
	private String prchsReqPathCd; // 구매채널
	private String prchsCaseCd; // 구매인입경로
	private int prodAmt; // 구매상태
	private String useExprDt; // 이용종료일시
	private String partChrgProdNm; // 부분유료화명,회차정보

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
	 * @return the prchsDt
	 */
	public String getPrchsDt() {
		return this.prchsDt;
	}

	/**
	 * @param prchsDt
	 *            the prchsDt to set
	 */
	public void setPrchsDt(String prchsDt) {
		this.prchsDt = prchsDt;
	}

	/**
	 * @return the statusCd
	 */
	public String getStatusCd() {
		return this.statusCd;
	}

	/**
	 * @param statusCd
	 *            the statusCd to set
	 */
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	/**
	 * @return the useInsdDeviceId
	 */
	public String getUseInsdDeviceId() {
		return this.useInsdDeviceId;
	}

	/**
	 * @param useInsdDeviceId
	 *            the useInsdDeviceId to set
	 */
	public void setUseInsdDeviceId(String useInsdDeviceId) {
		this.useInsdDeviceId = useInsdDeviceId;
	}

	/**
	 * @return the tenantProdGrpCd
	 */
	public String getTenantProdGrpCd() {
		return this.tenantProdGrpCd;
	}

	/**
	 * @param tenantProdGrpCd
	 *            the tenantProdGrpCd to set
	 */
	public void setTenantProdGrpCd(String tenantProdGrpCd) {
		this.tenantProdGrpCd = tenantProdGrpCd;
	}

	/**
	 * @return the prchsReqPathCd
	 */
	public String getPrchsReqPathCd() {
		return this.prchsReqPathCd;
	}

	/**
	 * @param prchsReqPathCd
	 *            the prchsReqPathCd to set
	 */
	public void setPrchsReqPathCd(String prchsReqPathCd) {
		this.prchsReqPathCd = prchsReqPathCd;
	}

	/**
	 * @return the prchsCaseCd
	 */
	public String getPrchsCaseCd() {
		return this.prchsCaseCd;
	}

	/**
	 * @param prchsCaseCd
	 *            the prchsCaseCd to set
	 */
	public void setPrchsCaseCd(String prchsCaseCd) {
		this.prchsCaseCd = prchsCaseCd;
	}

	/**
	 * @return the prodAmt
	 */
	public int getProdAmt() {
		return this.prodAmt;
	}

	/**
	 * @param prodAmt
	 *            the prodAmt to set
	 */
	public void setProdAmt(int prodAmt) {
		this.prodAmt = prodAmt;
	}

	/**
	 * @return the useExprDt
	 */
	public String getUseExprDt() {
		return this.useExprDt;
	}

	/**
	 * @param useExprDt
	 *            the useExprDt to set
	 */
	public void setUseExprDt(String useExprDt) {
		this.useExprDt = useExprDt;
	}

	/**
	 * Gets part chrg prod nm.
	 * 
	 * @return the part chrg prod nm
	 */
	public String getPartChrgProdNm() {
		return this.partChrgProdNm;
	}

	/**
	 * Sets part chrg prod nm.
	 * 
	 * @param partChrgProdNm
	 *            the part chrg prod nm
	 */
	public void setPartChrgProdNm(String partChrgProdNm) {
		this.partChrgProdNm = StringUtils.replace(partChrgProdNm, "\u0000", "");
	}

	/**
	 * @return the useInsdUsermbrNo
	 */
	public String getUseInsdUsermbrNo() {
		return this.useInsdUsermbrNo;
	}

	/**
	 * @param useInsdUsermbrNo
	 *            the useInsdUsermbrNo to set
	 */
	public void setUseInsdUsermbrNo(String useInsdUsermbrNo) {
		this.useInsdUsermbrNo = useInsdUsermbrNo;
	}

}
