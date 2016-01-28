/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.client.purchase.vo.migration;

import com.skplanet.storeplatform.sac.client.purchase.common.vo.PurchaseHeaderSacReq;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;

/**
 * 구매이관정보 조회 요청 VO.
 * 
 * Updated on : 2016. 1. 13. Updated by : 황민규, SK플래닛.
 */
public class PurchaseMigInformationSacReq extends PurchaseHeaderSacReq {

	private static final long serialVersionUID = 1L;

	private String marketCd;
	@NotBlank
	private String marketDeviceKey; // 이관 식별 기준 Key : Naver ID
	private String migStatusCd; // 이관 처리 상태
	private int offset; // 오프셋
	@Max(100)
	private int count; // 데이터갯수
	
	private int startrow; // 페이징 처리 시작 row
	private int endrow; // 페이징 처리 종료 row

	/**
	 * Gets market device key.
	 *
	 * @return the market device key
	 */
	public String getMarketDeviceKey() {
		return marketDeviceKey;
	}

	/**
	 * Sets market device key.
	 *
	 * @param marketDeviceKey
	 *            the market device key
	 */
	public void setMarketDeviceKey(String marketDeviceKey) {
		this.marketDeviceKey = marketDeviceKey;
	}

	/**
	 * Gets mig status cd.
	 *
	 * @return the mig status cd
	 */
	public String getMigStatusCd() {
		return migStatusCd;
	}

	/**
	 * Sets mig status cd.
	 *
	 * @param migStatusCd    
	 *            the mig status cd
	 */
	public void setMigStatusCd(String migStatusCd) {
		this.migStatusCd = migStatusCd;
	}

	/**
	 * Gets offset.
	 *
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * Sets offset.
	 *
	 * @param offset
	 *            the offset
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * Gets count.
	 *
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Sets count.
	 *
	 * @param count
	 *            the count
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * Gets startrow.
	 *
	 * @return the startrow
	 */
	public int getStartrow() {
		return startrow;
	}

	/**
	 * Sets startrow.
	 *
	 * @param startrow
	 *            the startrow
	 */
	public void setStartrow(int startrow) {
		this.startrow = startrow;
	}

	/**
	 * Gets endrow.
	 *
	 * @return the endrow
	 */
	public int getEndrow() {
		return endrow;
	}

	/**
	 * Sets endrow.
	 *
	 * @param endrow
	 *            the endrow
	 */
	public void setEndrow(int endrow) {
		this.endrow = endrow;
	}

	/**
	 * Gets market cd.
	 *
	 * @return the market cd
	 */
	public String getMarketCd() {
		return marketCd;
	}

	/**
	 * Sets market cd.
	 *
	 * @param marketCd
	 *            the market cd
	 */
	public void setMarketCd(String marketCd) {
		this.marketCd = marketCd;
	}
}
