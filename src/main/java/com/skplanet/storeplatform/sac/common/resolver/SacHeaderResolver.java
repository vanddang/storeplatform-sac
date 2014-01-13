package com.skplanet.storeplatform.sac.common.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import com.skplanet.storeplatform.sac.runtime.extend.vo.Device;
import com.skplanet.storeplatform.sac.runtime.extend.vo.Network;
import com.skplanet.storeplatform.sac.runtime.extend.vo.SacHeader;
import com.skplanet.storeplatform.sac.runtime.extend.vo.Tenant;

public class SacHeaderResolver implements WebArgumentResolver {

	@Override
	public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
		if (methodParameter.getParameterType().equals(SacHeader.class)) {

			Device device = new Device();

			device.setDpi("dpi");
			device.setModel("model");
			device.setOsVersion("osVersion");
			device.setPkgVersion("pkgVersion");
			device.setResolution("resolution");

			Network network = new Network();

			network.setOperator("operator");
			network.setSimOperator("simOperator");
			network.setType("type");

			Tenant tenant = new Tenant();

			tenant.setSystemId("systemId");
			tenant.setTenantId("tenantId");

			SacHeader sacHeader = new SacHeader();

			sacHeader.setDevice(device);

			sacHeader.setNetwork(network);

			sacHeader.setTenant(tenant);

			return sacHeader;
		}
		return UNRESOLVED;

	}
}
