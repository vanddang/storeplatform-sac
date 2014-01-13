package com.skplanet.storeplatform.sac.runtime.extend.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 커스템 헤더 정보를 컨트롤러에서 제공하기 위한 Value Object
 *
 * Updated on : 2014. 1. 13.
 * Updated by : 서대영, SK 플래닛.
 */
public class SACHeader extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private Tenant tenant;
	private Device device;
	private Network network;

	public SACHeader() {
		super();
	}

	public Tenant getTenant() {
		return this.tenant;
	}
	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}
	public Device getDevice() {
		return this.device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public Network getNetwork() {
		return this.network;
	}
	public void setNetwork(Network network) {
		this.network = network;
	}

}
