/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.vo;

import org.apache.commons.lang3.StringUtils;

import com.skplanet.storeplatform.external.client.shopping.util.StringUtil;
import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * P/P 와의 연동에 전달되는 가맹점 임의 파라미터 VO
 * 
 * Updated on : 2015. 5. 20. Updated by : 이승택, nTels.
 */
public class MctSpareParam extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private static final String SEPARATOR = ":";

	private int version;
	private String useTenantId;
	private String useUserKey;

	/**
	 */
	public MctSpareParam() {
	}

	/**
	 * @param mctSpareParam
	 *            P/P로부터 전달받은 가맹점 파라미터
	 */
	public MctSpareParam(String mctSpareParam) {
		if (StringUtils.contains(mctSpareParam, SEPARATOR)) {
			// String[] arrVal = mctSpareParam.split(SEPARATOR, -1);
			String[] arrVal = StringUtil.splitPreserveAllTokens(mctSpareParam, SEPARATOR);

			this.version = Integer.parseInt(arrVal[0]);

			// 1:useTenantId:useInsdUsermbrNo
			switch (this.version) {
			case 1:
				this.useTenantId = arrVal[1];
				this.useUserKey = arrVal[2];
				break;
			}
		}
	}

	// ===================================================================================================

	/**
	 * 
	 * <pre>
	 * P/P로 전달할 가맹점 임의 파라미터 구성.
	 * </pre>
	 * 
	 * @return P/P로 전달할 가맹점 임의 파라미터
	 */
	public String makeMctSpareParam() {
		// 1:useTenantId:useInsdUsermbrNo

		StringBuffer sbMctParam = new StringBuffer(this.useTenantId.length() + this.useUserKey.length() + 3);
		sbMctParam.append(this.version);
		switch (this.version) {
		case 1:
			sbMctParam.append(SEPARATOR).append(this.useTenantId).append(SEPARATOR).append(this.useUserKey);
			break;
		}

		return sbMctParam.toString();
	}

	// ===================================================================================================

	/**
	 * @return the version
	 */
	public int getVersion() {
		return this.version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * @return the useTenantId
	 */
	public String getUseTenantId() {
		return this.useTenantId;
	}

	/**
	 * @param useTenantId
	 *            the useTenantId to set
	 */
	public void setUseTenantId(String useTenantId) {
		this.useTenantId = useTenantId;
	}

	/**
	 * @return the useUserKey
	 */
	public String getUseUserKey() {
		return this.useUserKey;
	}

	/**
	 * @param useUserKey
	 *            the useUserKey to set
	 */
	public void setUseUserKey(String useUserKey) {
		this.useUserKey = useUserKey;
	}

}
