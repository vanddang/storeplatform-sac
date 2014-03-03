package com.skplanet.storeplatform.sac.common.header.intercepter;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.common.constant.CommonConstants;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.NetworkHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
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

        assignValues(webRequest.getHeader(CommonConstants.HEADER_DEVICE), device);

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

        assignValues(webRequest.getHeader(CommonConstants.HEADER_NETWORK), network);

		return network;
	}

    private static final Pattern PATTERN_KV = Pattern.compile("^\\s*(\\w+)\\s*=\\s*\"([\\d\\w\\-\\.\\*/_,]*)\"\\s*(,|$)");

    /**
     * <p>
     * 헤더 문자열에서 추출한 값들을 Bean에 할당한다.
     * </p>
     * @param str 헤더문자열
     * @param obj 대상 객체
     */
    private <T> void assignValues(final String str, T obj) {

        if (StringUtils.isEmpty(str) || obj == null) {
            return;
        }

        String strb = str;
        Matcher matcher = null;
        try
        {
            while((matcher = PATTERN_KV.matcher(strb)).find()) {
                String mtdNm = "set" + Character.toUpperCase(matcher.group(1).charAt(0)) + matcher.group(1).substring(1);
                Method method = obj.getClass().getMethod(mtdNm, String.class);

                method.invoke(obj, matcher.group(2));
                strb = strb.substring(matcher.group(0).length());
            }
        }
        catch (Exception e)
        {
            throw new StorePlatformException("SAC_002",
                    obj.getClass().getSimpleName(),
                    matcher != null ? matcher.group(1) : null,
                    matcher != null ? matcher.group(2) : null);
        }
    }
}
