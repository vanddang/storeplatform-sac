package com.skplanet.storeplatform.sac.runtime.cache.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * Service Value Object
 * 
 * Updated on : 2014. 1. 16. Updated by : 김현일, 인크로스.
 */
public class ServiceInfo extends CommonInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pkgNm;
	private String classNm;
	private String methodNm;

	/**
	 * @return String
	 */
	public String getPkgNm() {
		return this.pkgNm;
	}

	/**
	 * @return String
	 */
	public String getClassNm() {
		return this.classNm;
	}

	/**
	 * @return String
	 */
	public String getMethodNm() {
		return this.methodNm;
	}

	/**
	 * @param pkgNm
	 *            pkgNm
	 */
	public void setPkgNm(String pkgNm) {
		this.pkgNm = pkgNm;
	}

	/**
	 * @param classNm
	 *            classNm
	 */
	public void setClassNm(String classNm) {
		this.classNm = classNm;
	}

	/**
	 * @param methodNm
	 *            methodNm
	 */
	public void setMethodNm(String methodNm) {
		this.methodNm = methodNm;
	}
}
