package com.skplanet.storeplatform.sac.common.header.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.skplanet.storeplatform.sac.common.constant.CommonConstants;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.NetworkHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SacRequestHeaderIntercepter extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		SacRequestHeader sacHeader = new SacRequestHeader();

		TenantHeader tenant = this.extractTenant(new ServletWebRequest(request, response));
		DeviceHeader device = this.extractDevice(new ServletWebRequest(request, response));
		NetworkHeader network = this.extractNetwork(new ServletWebRequest(request, response));

		sacHeader.setTenantHeader(tenant);
		sacHeader.setDeviceHeader(device);
		sacHeader.setNetworkHeader(network);

		RequestContextHolder.getRequestAttributes().setAttribute(SacRequestHeader.class.getName(), sacHeader,
				RequestAttributes.SCOPE_REQUEST);

		return true;
	}

	/**
	 * <pre>
	 * Tenant 정보 추출. (더미 구현)
	 * </pre>
	 * 
	 * @param webRequest
	 * @return
	 */
	private TenantHeader extractTenant(NativeWebRequest webRequest) {
		TenantHeader tenant = new TenantHeader();

		tenant.setTenantId("S01");

		String systemId = webRequest.getHeader(CommonConstants.HEADER_SYSTEM_ID);
		if (StringUtils.isEmpty(systemId)) {
			tenant.setSystemId("S01-01002");
		} else {
			tenant.setSystemId(systemId);
		}

		String langCd = "ko";
		// String acceptLanguage = webRequest.getHeader(CommonConstants.HEADER_ACCEPT_LANGUATE);
		// String langCd = SomeUtils.acceptLanguageToLandCd(acceptLanguage);
		tenant.setLangCd(langCd);

		return tenant;
	}

	/**
	 * <pre>
	 * Device 정보 추출. (더미 구현)
	 * </pre>
	 * 
	 * @param webRequest
	 * @return
	 */
	private DeviceHeader extractDevice(NativeWebRequest webRequest) {
        DeviceHeader device = new DeviceHeader();
        device.setDpi("320");
        device.setModel("SHW-M110S");
        device.setResolution("480*720");
        device.setOs("Android/4.0.4");
        device.setPkg("store.skplanet.com/0.1");
        device.setSvc("SAC_Client/4.3");

//        String header = webRequest.getHeader("x-sac-device-info");
//        Pattern p = Pattern.compile("(\\w+)=\"([0-9a-zA-Z/\\.\\-\\*]+)\"");
//        for (String kv : header.split(",")) {
//            Matcher matcher = p.matcher(kv.trim());
//            if (matcher.find()) {
//                String key = matcher.group(1);
//                String value = matcher.group(2);
//
//                if(key.equals("model"))
//                    device.setModel(value);
//            }
//        }

		return device;
	}

	/**
	 * <pre>
	 * Network 정보 추출. (더미 구현)
	 * </pre>
	 * 
	 * @param webRequest
	 * @return
	 */
	private NetworkHeader extractNetwork(NativeWebRequest webRequest) {
		NetworkHeader network = new NetworkHeader();
		network.setOperator("unknown/unknown");
		network.setSimOperator("450/05");
		network.setType("wifi");
		return network;
	}
}
