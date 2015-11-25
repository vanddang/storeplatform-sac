/**
 * 
 */
package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 판매자 BP정보 Value Object./
 * 
 * Updated on : 2014. 3. 28. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SellerBpSac extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** BP 파일 경로. (BP_FILE_PATH) */
	private String bpFilePath;

	/** BP 파일명. (BP_FILE_NM) */
	private String bpFileName;

	/**
	 * @return the bpFilePath
	 */
	public String getBpFilePath() {
		return this.bpFilePath;
	}

	/**
	 * @param bpFilePath
	 *            the bpFilePath to set
	 */
	public void setBpFilePath(String bpFilePath) {
		this.bpFilePath = bpFilePath;
	}

	/**
	 * @return the bpFileName
	 */
	public String getBpFileName() {
		return this.bpFileName;
	}

	/**
	 * @param bpFileName
	 *            the bpFileName to set
	 */
	public void setBpFileName(String bpFileName) {
		this.bpFileName = bpFileName;
	}

}
