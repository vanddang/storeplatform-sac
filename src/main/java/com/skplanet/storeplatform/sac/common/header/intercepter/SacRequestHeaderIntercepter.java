package com.skplanet.storeplatform.sac.common.header.intercepter;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.common.constant.CommonConstants;
import com.skplanet.storeplatform.sac.common.header.extractor.TenantExtractor;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.NetworkHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;

public class SacRequestHeaderIntercepter extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SacRequestHeaderIntercepter.class);

	@Autowired
	private TenantExtractor tenantExtractor;

    @Value("#{config['header.device.resolution']}")
    private String defaultDevResolution = "";
    @Value("#{config['header.device.svc']}")
    private String defaultDevSvc = "";
    @Value("#{config['header.device.model']}")
    private String defaultDevModel = "";
    @Value("#{config['header.device.os']}")
    private String defaultDevOs = "";
    @Value("#{config['header.network.type']}")
    private String defaultNetType = "";

    private static final Pattern PATTERN_KV = Pattern.compile("^\\s*(\\w+)\\s*=\\s*\"([\\d\\w\\-\\.\\*/_,]*)\"\\s*(,|$)");

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
		return this.tenantExtractor.extract(webRequest);
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
        device.setDpi("");  // 참조 없음
        device.setPkg(""); // 참조 없음
        device.setResolution(defaultDevResolution);
        device.setSvc(defaultDevSvc);
        device.setModel(defaultDevModel);
        device.setOs(defaultDevOs);

        String headerStr = webRequest.getHeader(CommonConstants.HEADER_DEVICE);
        this.assignValues(headerStr, device);

        logger.info("DeviceInfo: mdl={}, res={}, dpi={}, os={}, pkg={}, svc={} << {}",
                device.getModel(), device.getResolution(), device.getDpi(), device.getOs(), device.getPkg(), device.getSvc(), headerStr);

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
		network.setOperator("");    // 참조 없음
		network.setSimOperator(""); // 참조 없음
		network.setType(defaultNetType);

        String headerStr = webRequest.getHeader(CommonConstants.HEADER_NETWORK);
        this.assignValues(headerStr, network);

        logger.info("NetworkInfo: oper={}, soper={}, type={} << {}",
                network.getOperator(), network.getSimOperator(), network.getType(), headerStr);

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

}
