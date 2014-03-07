/*
 * Copyright (c) 2014 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.appguide;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * App guide 테마 추천 Request Value Object.
 * 
 * Updated on : 2014. 03. 06. Updated by : 윤주영, SK 플래닛.
 */
public class AppguideThemeSacReq extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6632749297805159025L;

	private int offset = 1; // 시작점 ROW

	private int count = 100; // 페이지당 노출 ROW 수

	/**
	 * @return the offset
	 */
	public int getOffset() {
		return this.offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return this.count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

}
