/**
 * 
 */
package com.skplanet.storeplatform.sac.member.common;

import java.lang.reflect.Method;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;

/**
 * Internal SCI 기능 확인을 위한 Controller.
 * 
 * Updated on : 2014. 3. 11. Updated by : 김다슬, 인크로스.
 */
@Controller
@RequestMapping(value = "/member/internalSci/test")
public class InternalSCITestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(InternalSCITestController.class);

	@Autowired
	private ApplicationContext appContext;

	@RequestMapping(value = "/{className}/{methodName}/{voName}/v1", method = RequestMethod.POST)
	@ResponseBody
	public Object internalSCITest(@RequestBody Object request, @PathVariable("className") String className,
			@PathVariable("methodName") String methodName, @PathVariable("voName") String voName)
			throws StorePlatformException, Exception {

		LOGGER.debug("\n[InternalSCITestController.internalSCITest] Request : {}", request);
		LOGGER.debug("\n[InternalSCITestController.internalSCITest] className:{}, methodName:{}, voName:{}", className,
				methodName, voName);

		String classPath = "com.skplanet.storeplatform.sac.member.user.sci."; // SCIController PackageName
		String voPath = "com.skplanet.storeplatform.sac.client.internal.member.user.vo."; // VO PackageName
		String reqVoName = voPath + voName + "Req";
		String resVoName = voPath + voName + "Res";

		Object sciClassObj = this.appContext.getBean(classPath + className);

		Class reqVoCls = Class.forName(reqVoName); // Request VO
		Object reqVo = reqVoCls.newInstance(); // 객체 생성

		Method method = sciClassObj.getClass().getMethod(methodName, reqVoCls); // 메소드 접근

		// Request 객체 JSON Mapping
		ObjectMapper objMapper = new ObjectMapper();
		reqVo = objMapper.convertValue(request, reqVo.getClass());

		Class resVoCls = Class.forName(resVoName); // Response VO
		Object resVo = resVoCls.newInstance();
		// 메소드 호출
		resVo = method.invoke(sciClassObj, reqVo);
		LOGGER.debug("\n[InternalSCITestController.internalSCITest] Method call Response: {}", resVo);

		return resVo;
	}
}
