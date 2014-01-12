/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.vod;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Vod 상세 조회 Input Value Object.
 * 
 * Updated on : 2014. 01. 07. Updated by : 임근대, SK 플래닛.
 */
public class VodDetailReq extends CommonInfo {

	private static final long serialVersionUID = 3567228512653706925L;

	private String channelld; // 체널 ID

	private String orderedBy; // 상품 정렬 순서

	private Integer offset = 1; // 시작점 ROW, default : 1

	private Integer count = 20; // 페이지당 노출될 ROW 개수, default :20

	public String getChannelld() {
		return channelld;
	}

	public void setChannelld(String channelld) {
		this.channelld = channelld;
	}

	public String getOrderedBy() {
		return orderedBy;
	}

	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "VodDetailReq [channelld=" + channelld + ", orderedBy="
				+ orderedBy + ", offset=" + offset + ", count=" + count + "]";
	}

}
