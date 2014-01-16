package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [REQUEST] 대표단말설정
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SetMainDeviceReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/* 사용자 키 */
	private String userKey;
	/* 기기 키 */
	private String deviceKey;
	private CommonRequest commonRequest;

	public String getUserKey() {
		return this.userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getDeviceKey() {
		return this.deviceKey;
	}

	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CommonRequest getCommonRequest() {
		return this.commonRequest;
	}

	public void setCommonRequest(CommonRequest commonRequest) {
		this.commonRequest = commonRequest;
	}

	public class CommonRequest implements Serializable {

		/** The tenant id. */
		private String tenantID;

		/** The system id. */
		private String systemID;

		/**
		 * Returns the tenant id.
		 * 
		 * @return tenantID - the tenant id
		 */
		public String getTenantID() {
			return this.tenantID;
		}

		/**
		 * Sets the tenant id.
		 * 
		 * @param tenantID
		 *            the new tenant id
		 */
		public void setTenantID(String tenantID) {
			this.tenantID = tenantID;
		}

		/**
		 * Returns the system id.
		 * 
		 * @return systemID - the system id
		 */
		public String getSystemID() {
			return this.systemID;
		}

		/**
		 * Sets the system id.
		 * 
		 * @param systemID
		 *            the new system id
		 */
		public void setSystemID(String systemID) {
			this.systemID = systemID;
		}

	}
}
