package com.skplanet.storeplatform.sac.common.header.intercepter;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.common.constant.CommonConstants;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.NetworkHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SacRequestHeaderIntercepter extends HandlerInterceptorAdapter {

    @Value("#{propertiesForSac['skp.common.default.language']}")
    private String DEFAULT_LANG;
    @Value("#{propertiesForSac['skp.common.service.language']}")
    private String SERVICE_LANG;

    private static final Pattern PATTERN_KV = Pattern.compile("^\\s*(\\w+)\\s*=\\s*\"([\\d\\w\\-\\.\\*/_,]*)\"\\s*(,|$)");
    private static Pattern PATTERN_ACCLANG_ELEM = Pattern.compile("([a-zA-Z]{1,8}(\\-[a-zA-Z]{1,8})?)(;q=[0-9\\.]+)?");     // HTTP/1.1 Accept-Language Spec.

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		SacRequestHeader sacHeader = new SacRequestHeader();
        ServletWebRequest servletWebRequest = new ServletWebRequest(request, response);

        TenantHeader tenant = this.extractTenant(servletWebRequest);
		DeviceHeader device = this.extractDevice(servletWebRequest);
		NetworkHeader network = this.extractNetwork(servletWebRequest);

		sacHeader.setTenantHeader(tenant);
		sacHeader.setDeviceHeader(device);
		sacHeader.setNetworkHeader(network);

		RequestContextHolder.currentRequestAttributes().setAttribute(SacRequestHeader.class.getName(), sacHeader,
				RequestAttributes.SCOPE_REQUEST);

        // for Debug - 캐쉬 이용 여부 처리
        String headerUseCache = request.getHeader("x-sac-use-cache");
        RequestContextHolder.currentRequestAttributes()
                .setAttribute("useCache", StringUtils.isEmpty(headerUseCache) || headerUseCache.toLowerCase().equals("true"), RequestAttributes.SCOPE_REQUEST);

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

		String acceptLanguage = webRequest.getHeader(CommonConstants.HEADER_ACCEPT_LANGUAGE);
		tenant.setLangCd(parseAcceptLanguage(acceptLanguage));

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
            throw new StorePlatformException("SAC_CMN_0101",
                    obj.getClass().getSimpleName(),
                    matcher != null ? matcher.group(1) : null,
                    matcher != null ? matcher.group(2) : null);
        }
    }

    /**
     * <p>
     * Accept-Language 값을 파싱한다.
     * language-range부분은 무시하며, 헤더값이 없거나 지원하는 언어가 없는 경우 기본 언어를 리턴한다.
     * </p>
     * @param hdr 헤더값
     * @return 언어코드
     */
    private String parseAcceptLanguage(String hdr) {

        if(StringUtils.isEmpty(hdr))
            return DEFAULT_LANG;

        HashSet<String> supportLanges = new HashSet<String>(Arrays.asList(SERVICE_LANG.split(",")));
        for(String str : hdr.split(",")) {
            Matcher m = PATTERN_ACCLANG_ELEM.matcher(str.trim());
            if(m.matches()) {
                if(supportLanges.contains(m.group(1)))
                    return m.group(1);
            }
        }
        return DEFAULT_LANG;
    }
}
