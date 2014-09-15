/**
 *
 */
package com.skplanet.storeplatform.sac.display.download.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * App Delta Update Result VO.
 *
 * Updated on : 2014. 9. 15.
 * Updated by : 양해엽, SK Planet
 */
public class AppDeltaUpdate extends CommonInfo {

	private static final long serialVersionUID = 4450677543529397576L;

	private String prodId;
	private String subContentsId;
	private Integer apkVer;
	private Integer preApkVer;
	private Long deltaFileSize;
	private String deltaFilePath;

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

	public Long getDeltaFileSize() {
		return this.deltaFileSize;
	}

	public void setDeltaFileSize(Long deltaFileSize) {
		this.deltaFileSize = deltaFileSize;
	}

	public String getDeltaFilePath() {
		return this.deltaFilePath;
	}

	public void setDeltaFilePath(String deltaFilePath) {
		this.deltaFilePath = deltaFilePath;
	}

}
