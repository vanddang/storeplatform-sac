package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * PayPlanet 인증용 Pin정보.
 * 
 * Updated on : 2014. 11. 6. Updated by : 반범진, SK 플래닛.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class MarketPinInfo extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 결제 PIN 설정 여부.
	 */
	private String isPinSet;

	/**
	 * 결제 PIN 재입력 여부.
	 */
	private String isPinRetry;

	/**
	 * 결제 PIN 잠김 여부.
	 */
	private String isPinClosed;

	/**
	 * 결제 Pin 입력/설정/재설정 화면 URL.
	 */
	private String setPinUrl;

	/**
	 * @return isPinSet
	 */
	public String getIsPinSet() {
		return this.isPinSet;
	}

	/**
	 * @param isPinSet
	 *            String
	 */
	public void setIsPinSet(String isPinSet) {
		this.isPinSet = isPinSet;
	}

	/**
	 * @return isPinRetry
	 */
	public String getIsPinRetry() {
		return this.isPinRetry;
	}

	/**
	 * @param isPinRetry
	 *            String
	 */
	public void setIsPinRetry(String isPinRetry) {
		this.isPinRetry = isPinRetry;
	}

	/**
	 * @return isPinClosed
	 */
	public String getIsPinClosed() {
		return this.isPinClosed;
	}

	/**
	 * @param isPinClosed
	 *            String
	 */
	public void setIsPinClosed(String isPinClosed) {
		this.isPinClosed = isPinClosed;
	}

	/**
	 * @return setPinUrl
	 */
	public String getSetPinUrl() {
		return this.setPinUrl;
	}

	/**
	 * @param setPinUrl
	 *            String
	 */
	public void setSetPinUrl(String setPinUrl) {
		this.setPinUrl = setPinUrl;
	}

}
