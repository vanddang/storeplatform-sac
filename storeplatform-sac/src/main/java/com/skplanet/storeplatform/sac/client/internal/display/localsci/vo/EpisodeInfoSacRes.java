package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import java.util.List;

/**
 * 정액권 상품을 이용하여 개별 상품 조회 FreePassInfoRes VO.
 * 
 * Updated on : 2014. 4. 22. Updated by : 김형식 , 지티소프트
 */
public class EpisodeInfoSacRes {
	private static final long serialVersionUID = 1L;

	List<EpisodeInfoRes> freePassInfoRes;

	/**
	 * @return the freePassInfoRes
	 */
	public List<EpisodeInfoRes> getFreePassInfoRes() {
		return this.freePassInfoRes;
	}

	/**
	 * @param freePassInfoRes
	 *            the freePassInfoRes to set
	 */
	public void setFreePassInfoRes(List<EpisodeInfoRes> freePassInfoRes) {
		this.freePassInfoRes = freePassInfoRes;
	}

}
