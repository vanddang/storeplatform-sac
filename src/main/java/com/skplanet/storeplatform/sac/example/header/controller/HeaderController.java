package com.skplanet.storeplatform.sac.example.header.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.example.header.DeviceRes;
import com.skplanet.storeplatform.sac.client.example.header.NetworkRes;
import com.skplanet.storeplatform.sac.client.example.header.TenantRes;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.NetworkHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;

@Controller
@RequestMapping(value = "/example/header")
public class HeaderController {

	@RequestMapping(value = "/tenant", method = RequestMethod.GET)
	@ResponseBody
	public TenantRes tenant(SacRequestHeader requestHeader) {
		TenantHeader header = requestHeader.getTenantHeader();
		TenantRes res = new TenantRes();
		res.setTenantId(header.getTenantId());
		res.setSystemId(header.getSystemId());
		return res;
	}

	@RequestMapping(value = "/device", method = RequestMethod.GET)
	@ResponseBody
	public DeviceRes device(SacRequestHeader requestHeader) {
		DeviceHeader header = requestHeader.getDeviceHeader();
		DeviceRes res = new DeviceRes();
		res.setModel(header.getModel());
		res.setDpi(header.getDpi());
		res.setResolution(header.getResolution());
		res.setOsVersion(header.getOsVersion());
		return res;
	}

	@RequestMapping(value = "/network", method = RequestMethod.GET)
	@ResponseBody
	public NetworkRes network(SacRequestHeader requestHeader) {
		NetworkHeader header = requestHeader.getNetworkHeader();
		NetworkRes res = new NetworkRes();
		res.setType(header.getType());
		res.setOperator(header.getOperator());
		res.setSimOperator(header.getSimOperator());
		return res;
	}

}
