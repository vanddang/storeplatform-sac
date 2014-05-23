package com.skplanet.storeplatform.sac.display.appguide.vo;

import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;

/**
 * 테마추천 Appguide Main info
 * 
 * Updated on : 2014. 05. 22. Updated by : 윤주영, SK 플래닛.
 */
public class AppguideMain extends ProductBasicInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 테마추천 메인
	private String themeId;
	private String themeNm;
	private String themeType;
	private String themeImg;

	/**
	 * @return the themeId
	 */
	public String getThemeId() {
		return this.themeId;
	}

	/**
	 * @param themeId
	 *            the themeId to set
	 */
	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}

	/**
	 * @return the themeNm
	 */
	public String getThemeNm() {
		return this.themeNm;
	}

	/**
	 * @param themeNm
	 *            the themeNm to set
	 */
	public void setThemeNm(String themeNm) {
		this.themeNm = themeNm;
	}

	/**
	 * @return the themeType
	 */
	public String getThemeType() {
		return this.themeType;
	}

	/**
	 * @param themeType
	 *            the themeType to set
	 */
	public void setThemeType(String themeType) {
		this.themeType = themeType;
	}

	/**
	 * @return the themeImg
	 */
	public String getThemeImg() {
		return this.themeImg;
	}

	/**
	 * @param themeImg
	 *            the themeImg to set
	 */
	public void setThemeImg(String themeImg) {
		this.themeImg = themeImg;
	}

}
