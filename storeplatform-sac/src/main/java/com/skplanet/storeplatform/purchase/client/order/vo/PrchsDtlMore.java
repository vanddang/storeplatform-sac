/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.purchase.client.order.vo;

import java.util.List;

import com.skplanet.storeplatform.purchase.client.common.vo.PrchsDtl;

/**
 * 
 * 구매 생성 시 필요한 - Prchs 정보를 포함하는 PrchsDtl 기반의 추가 정보 VO
 * 
 * Updated on : 2014. 2. 19. Updated by : 이승택, nTels.
 */
public class PrchsDtlMore extends PrchsDtl {
	private static final long serialVersionUID = 1L;

	/**
	 * 시스템 ID
	 */
	private String systemId;
	/**
	 * 내부 회원 번호
	 */
	private String insdUsermbrNo;
	/**
	 * 내부 디바이스 ID
	 */
	private String insdDeviceId;
	/**
	 * 네트워크 타입 코드
	 */
	private String networkTypeCd;
	/**
	 * 이전 구매상태 코드
	 */
	private String oldStatusCd;
	/**
	 * 선물 수신자 목록
	 */
	private List<PurchaseUserInfo> receiverList;
	/**
	 * 특가 상품 여부
	 */
	private String sprcProdYn;
	/**
	 * 이용한 정액권 상품 타입
	 */
	private String useFixrateProdClsfCd;
	/**
	 * 임시 정보 여부: 전권 소장/대여 상품 에피소드 처리 시 사용
	 */
	private boolean temporary;
	/**
	 * SKT 서비스 관리 번호
	 */
	private String svcMangNo;
	/**
	 * CPS CPID
	 */
	private String mediId;

	/**
	 * 자동결제 상품 여부
	 */
	private String autoPrchsYn;

	/**
	 * 오퍼링 지급 상태
	 */
	private String offeringState;

	/**
	 * 오퍼링 지급 금액
	 */
	private Integer offeringAmt;

	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return this.systemId;
	}

	/**
	 * @param systemId
	 *            the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * @return the insdUsermbrNo
	 */
	public String getInsdUsermbrNo() {
		return this.insdUsermbrNo;
	}

	/**
	 * @param insdUsermbrNo
	 *            the insdUsermbrNo to set
	 */
	public void setInsdUsermbrNo(String insdUsermbrNo) {
		this.insdUsermbrNo = insdUsermbrNo;
	}

	/**
	 * @return the insdDeviceId
	 */
	public String getInsdDeviceId() {
		return this.insdDeviceId;
	}

	/**
	 * @param insdDeviceId
	 *            the insdDeviceId to set
	 */
	public void setInsdDeviceId(String insdDeviceId) {
		this.insdDeviceId = insdDeviceId;
	}

	/**
	 * @return the networkTypeCd
	 */
	public String getNetworkTypeCd() {
		return this.networkTypeCd;
	}

	/**
	 * @param networkTypeCd
	 *            the networkTypeCd to set
	 */
	public void setNetworkTypeCd(String networkTypeCd) {
		this.networkTypeCd = networkTypeCd;
	}

	/**
	 * @return the oldStatusCd
	 */
	public String getOldStatusCd() {
		return this.oldStatusCd;
	}

	/**
	 * @param oldStatusCd
	 *            the oldStatusCd to set
	 */
	public void setOldStatusCd(String oldStatusCd) {
		this.oldStatusCd = oldStatusCd;
	}

	/**
	 * @return the receiverList
	 */
	public List<PurchaseUserInfo> getReceiverList() {
		return this.receiverList;
	}

	/**
	 * @param receiverList
	 *            the receiverList to set
	 */
	public void setReceiverList(List<PurchaseUserInfo> receiverList) {
		this.receiverList = receiverList;
	}

	/**
	 * @return the sprcProdYn
	 */
	public String getSprcProdYn() {
		return this.sprcProdYn;
	}

	/**
	 * @param sprcProdYn
	 *            the sprcProdYn to set
	 */
	public void setSprcProdYn(String sprcProdYn) {
		this.sprcProdYn = sprcProdYn;
	}

	/**
	 * @return the useFixrateProdClsfCd
	 */
	public String getUseFixrateProdClsfCd() {
		return this.useFixrateProdClsfCd;
	}

	/**
	 * @param useFixrateProdClsfCd
	 *            the useFixrateProdClsfCd to set
	 */
	public void setUseFixrateProdClsfCd(String useFixrateProdClsfCd) {
		this.useFixrateProdClsfCd = useFixrateProdClsfCd;
	}

	/**
	 * @return the temporary
	 */
	public boolean isTemporary() {
		return this.temporary;
	}

	/**
	 * @param temporary
	 *            the temporary to set
	 */
	public void setTemporary(boolean temporary) {
		this.temporary = temporary;
	}

	/**
	 * @return the svcMangNo
	 */
	public String getSvcMangNo() {
		return this.svcMangNo;
	}

	/**
	 * @param svcMangNo
	 *            the svcMangNo to set
	 */
	public void setSvcMangNo(String svcMangNo) {
		this.svcMangNo = svcMangNo;
	}

	/**
	 * @return the mediId
	 */
	public String getMediId() {
		return this.mediId;
	}

	/**
	 * @param mediId
	 *            the mediId to set
	 */
	public void setMediId(String mediId) {
		this.mediId = mediId;
	}

	/**
	 * @return the autoPrchsYn
	 */
	public String getAutoPrchsYn() {
		return this.autoPrchsYn;
	}

	/**
	 * @param autoPrchsYn
	 *            the autoPrchsYn to set
	 */
	public void setAutoPrchsYn(String autoPrchsYn) {
		this.autoPrchsYn = autoPrchsYn;
	}

	/**
	 * Gets offering state.
	 * 
	 * @return the offering state
	 */
	public String getOfferingState() {
		return this.offeringState;
	}

	/**
	 * Sets offering state.
	 * 
	 * @param offeringState
	 *            the offering state
	 */
	public void setOfferingState(String offeringState) {
		this.offeringState = offeringState;
	}

	/**
	 * Gets offering amt.
	 * 
	 * @return the offering amt
	 */
	public Integer getOfferingAmt() {
		return this.offeringAmt;
	}

	/**
	 * Sets offering amt.
	 * 
	 * @param offeringAmt
	 *            the offering amt
	 */
	public void setOfferingAmt(Integer offeringAmt) {
		this.offeringAmt = offeringAmt;
	}
}
