/**
 *
 */
package com.skplanet.storeplatform.sac.display.download.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * App Delta Update Parameter VO.
 *
 * Updated on : 2014. 9. 15.
 * Updated by : 양해엽, SK Planet
 */
public class AppDeltaUpdateParam extends CommonInfo {

	private static final long serialVersionUID = -1096758648786674490L;

	private String prodId;
	private String subContentsId;
	private Integer apkVer;
	private Integer preApkVer;

	public String getProdId() {
		return this.prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getSubContentsId() {
		return this.subContentsId;
	}

	public void setSubContentsId(String subContentsId) {
		this.subContentsId = subContentsId;
	}

	public Integer getApkVer() {
		return this.apkVer;
	}

	public void setApkVer(Integer apkVer) {
		this.apkVer = apkVer;
	}

	public Integer getPreApkVer() {
		return this.preApkVer;
	}

	public void setPreApkVer(Integer preApkVer) {
		this.preApkVer = preApkVer;
	}




}
