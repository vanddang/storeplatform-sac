package com.skplanet.storeplatform.sac.common.header.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 커스템 헤더 정보를 컨트롤러에서 제공하기 위한 Value Object
 *
 * Updated on : 2014. 1. 13.
 * Updated by : 서대영, SK 플래닛.
 */
public class SacRequestHeader extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private TenantHeader tenant;
	private DeviceHeader device;
	private NetworkHeader network;

	public SacRequestHeader() {
		super();
	}

	public TenantHeader getTenantHeader() {
		return this.tenant;
	}
	public void setTenantHeader(TenantHeader tenant) {
		this.tenant = tenant;
	}
	public DeviceHeader getDeviceHeader() {
		return this.device;
	}
	public void setDeviceHeader(DeviceHeader device) {
		this.device = device;
	}
	public NetworkHeader getNetworkHeader() {
		return this.network;
	}
	public void setNetworkHeader(NetworkHeader network) {
		this.network = network;
	}

}
