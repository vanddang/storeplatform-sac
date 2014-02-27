/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.interworking.vo;

import java.io.Serializable;
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 구매후처리 연동(인터파크,씨네21).
 * 
 * Updated on : 2014. 2. 6. Updated by : 조용진, NTELS.
 */
public class InterworkingSacReq extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String systemId;
	private String userKey;
	private String deviceKey;
	private String prchsId;
	private String prchsDt;
	private String prchsCancelDt;
	private String fileMakeYn;
	private List<InterworkingSac> interworkingListSac;

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return this.systemId;
	}

	/**
	 * @param systemId
	 *            the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            the deviceKey to set
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * @return the prchsId
	 */
	public String getPrchsId() {
		return this.prchsId;
	}

	/**
	 * @param prchsId
	 *            the prchsId to set
	 */
	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}

	/**
	 * @return the prchsDt
	 */
	public String getPrchsDt() {
		return this.prchsDt;
	}

	/**
	 * @param prchsDt
	 *            the prchsDt to set
	 */
	public void setPrchsDt(String prchsDt) {
		this.prchsDt = prchsDt;
	}

	/**
	 * @return the prchsCancelDt
	 */
	public String getPrchsCancelDt() {
		return this.prchsCancelDt;
	}

	/**
	 * @param prchsCancelDt
	 *            the prchsCancelDt to set
	 */
	public void setPrchsCancelDt(String prchsCancelDt) {
		this.prchsCancelDt = prchsCancelDt;
	}

	/**
	 * @return the fileMakeYn
	 */
	public String getFileMakeYn() {
		return this.fileMakeYn;
	}

	/**
	 * @param fileMakeYn
	 *            the fileMakeYn to set
	 */
	public void setFileMakeYn(String fileMakeYn) {
		this.fileMakeYn = fileMakeYn;
	}

	/**
	 * @return the interworkingListSac
	 */
	public List<InterworkingSac> getInterworkingListSac() {
		return this.interworkingListSac;
	}

	/**
	 * @param interworkingListSac
	 *            the interworkingListSac to set
	 */
	public void setInterworkingListSac(List<InterworkingSac> interworkingListSac) {
		this.interworkingListSac = interworkingListSac;
	}

}
