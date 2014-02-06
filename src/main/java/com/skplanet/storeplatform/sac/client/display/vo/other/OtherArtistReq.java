package com.skplanet.storeplatform.sac.client.display.vo.other;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 아티스트별 정보 조회 Request Value Object.
 * 
 * Updated on : 2014. 2. 3. Updated by : 오승민, 인크로스.
 */
public class OtherArtistReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/**
	 * 아티스트 Id.
	 */
	private String artistId;

	/**
	 * @return the artistId
	 */
	public String getArtistId() {
		return this.artistId;
	}

	/**
	 * @param artistId
	 *            the artistId to set
	 */
	public void setArtistId(String artistId) {
		this.artistId = artistId;
	}

}
