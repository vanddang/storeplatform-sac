/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.purchase.vo.order;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacReq.GroupCreateBizPurchase;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacReq.GroupCreateFreePurchase;
import com.skplanet.storeplatform.sac.client.purchase.vo.order.CreatePurchaseSacReq.GroupCreatePurchase;

/**
 * 
 * 구매요청 상품구매정보 VO
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
public class CreatePurchaseSacReqProduct extends CommonInfo {
	private static final long serialVersionUID = 201401031L;

	@NotBlank(groups = { GroupCreatePurchase.class, GroupCreateFreePurchase.class })
	private String prodId; // 상품 ID
	@NotNull(groups = { GroupCreatePurchase.class, GroupCreateFreePurchase.class })
	private Double prodAmt; // 상품 가격
	@NotNull(groups = { GroupCreatePurchase.class, GroupCreateFreePurchase.class })
	private int prodQty; // 상품 수량
	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class })
	private String tid; // 부분유료화 개발사 구매Key
	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class })
	private String txId; // 부분유료화 전자영수증 번호
	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class })
	private String partChrgVer; // 부분유료화 라이브러리 버전
	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class })
	private String partChrgProdNm; // 부분유료화 상세 상품명
	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class })
	private String rnPid; // RN 관리 상품ID
	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class })
	private String infoUseFee; // 정보 이용 요금
	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class })
	private String cid; // 컨텐츠 ID (SONG ID)
	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class })
	private String contentsClsf; // 컨텐츠 구분: 컬러링 / 벨소리
	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class })
	private String timbreClsf; // 음질 구분
	@Null(groups = { GroupCreateFreePurchase.class, GroupCreateBizPurchase.class })
	private String timbreSctn; // 음질 구간
	@Null(groups = { GroupCreatePurchase.class, GroupCreateBizPurchase.class })
	private String useExprDt; // 이용종료일
	private String resvCol01; // 예비컬럼01
	private String resvCol02; // 예비컬럼02
	private String resvCol03; // 예비컬럼03
	private String resvCol04; // 예비컬럼04
	private String resvCol05; // 예비컬럼05

	/**
	 */
	public CreatePurchaseSacReqProduct() {
	}

	/**
	 */
	public CreatePurchaseSacReqProduct(String prodId, double prodAmt, int prodQty) {
		this.prodId = prodId;
		this.prodAmt = prodAmt;
		this.prodQty = prodQty;
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
	 * @return the prodAmt
	 */
	public Double getProdAmt() {
		return this.prodAmt;
	}

	/**
	 * @param prodAmt
	 *            the prodAmt to set
	 */
	public void setProdAmt(Double prodAmt) {
		this.prodAmt = prodAmt;
	}

	/**
	 * @return the prodQty
	 */
	public int getProdQty() {
		return this.prodQty;
	}

	/**
	 * @param prodQty
	 *            the prodQty to set
	 */
	public void setProdQty(int prodQty) {
		this.prodQty = prodQty;
	}

	/**
	 * @return the tid
	 */
	public String getTid() {
		return this.tid;
	}

	/**
	 * @param tid
	 *            the tid to set
	 */
	public void setTid(String tid) {
		this.tid = tid;
	}

	/**
	 * @return the txId
	 */
	public String getTxId() {
		return this.txId;
	}

	/**
	 * @param txId
	 *            the txId to set
	 */
	public void setTxId(String txId) {
		this.txId = txId;
	}

	/**
	 * @return the partChrgVer
	 */
	public String getPartChrgVer() {
		return this.partChrgVer;
	}

	/**
	 * @param partChrgVer
	 *            the partChrgVer to set
	 */
	public void setPartChrgVer(String partChrgVer) {
		this.partChrgVer = partChrgVer;
	}

	/**
	 * @return the partChrgProdNm
	 */
	public String getPartChrgProdNm() {
		return this.partChrgProdNm;
	}

	/**
	 * @param partChrgProdNm
	 *            the partChrgProdNm to set
	 */
	public void setPartChrgProdNm(String partChrgProdNm) {
		this.partChrgProdNm = partChrgProdNm;
	}

	/**
	 * @return the rnPid
	 */
	public String getRnPid() {
		return this.rnPid;
	}

	/**
	 * @param rnPid
	 *            the rnPid to set
	 */
	public void setRnPid(String rnPid) {
		this.rnPid = rnPid;
	}

	/**
	 * @return the infoUseFee
	 */
	public String getInfoUseFee() {
		return this.infoUseFee;
	}

	/**
	 * @param infoUseFee
	 *            the infoUseFee to set
	 */
	public void setInfoUseFee(String infoUseFee) {
		this.infoUseFee = infoUseFee;
	}

	/**
	 * @return the cid
	 */
	public String getCid() {
		return this.cid;
	}

	/**
	 * @param cid
	 *            the cid to set
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}

	/**
	 * @return the contentsClsf
	 */
	public String getContentsClsf() {
		return this.contentsClsf;
	}

	/**
	 * @param contentsClsf
	 *            the contentsClsf to set
	 */
	public void setContentsClsf(String contentsClsf) {
		this.contentsClsf = contentsClsf;
	}

	/**
	 * @return the timbreClsf
	 */
	public String getTimbreClsf() {
		return this.timbreClsf;
	}

	/**
	 * @param timbreClsf
	 *            the timbreClsf to set
	 */
	public void setTimbreClsf(String timbreClsf) {
		this.timbreClsf = timbreClsf;
	}

	/**
	 * @return the timbreSctn
	 */
	public String getTimbreSctn() {
		return this.timbreSctn;
	}

	/**
	 * @param timbreSctn
	 *            the timbreSctn to set
	 */
	public void setTimbreSctn(String timbreSctn) {
		this.timbreSctn = timbreSctn;
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
	 * @return the resvCol01
	 */
	public String getResvCol01() {
		return this.resvCol01;
	}

	/**
	 * @param resvCol01
	 *            the resvCol01 to set
	 */
	public void setResvCol01(String resvCol01) {
		this.resvCol01 = resvCol01;
	}

	/**
	 * @return the resvCol02
	 */
	public String getResvCol02() {
		return this.resvCol02;
	}

	/**
	 * @param resvCol02
	 *            the resvCol02 to set
	 */
	public void setResvCol02(String resvCol02) {
		this.resvCol02 = resvCol02;
	}

	/**
	 * @return the resvCol03
	 */
	public String getResvCol03() {
		return this.resvCol03;
	}

	/**
	 * @param resvCol03
	 *            the resvCol03 to set
	 */
	public void setResvCol03(String resvCol03) {
		this.resvCol03 = resvCol03;
	}

	/**
	 * @return the resvCol04
	 */
	public String getResvCol04() {
		return this.resvCol04;
	}

	/**
	 * @param resvCol04
	 *            the resvCol04 to set
	 */
	public void setResvCol04(String resvCol04) {
		this.resvCol04 = resvCol04;
	}

	/**
	 * @return the resvCol05
	 */
	public String getResvCol05() {
		return this.resvCol05;
	}

	/**
	 * @param resvCol05
	 *            the resvCol05 to set
	 */
	public void setResvCol05(String resvCol05) {
		this.resvCol05 = resvCol05;
	}

}
