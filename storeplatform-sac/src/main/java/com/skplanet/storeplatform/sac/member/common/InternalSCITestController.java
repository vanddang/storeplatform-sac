/**
 * 
 */
package com.skplanet.storeplatform.sac.member.common;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
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

	@RequestMapping(value = "/{type}/{className}/{methodName}/{voName}/v1", method = RequestMethod.POST)
	@ResponseBody
	public Object internalSCITest(@RequestBody Object request, @PathVariable("className") String className,
			@PathVariable("methodName") String methodName, @PathVariable("voName") String voName,
			@PathVariable("type") String type) throws StorePlatformException, Exception {

		LOGGER.debug("\n[InternalSCITestController.internalSCITest] Request : {}", request);
		LOGGER.debug("\n[InternalSCITestController.internalSCITest] className:{}, methodName:{}, voName:{}", className,
				methodName, voName);

		String classPath = "com.skplanet.storeplatform.sac.member." + type + ".sci."; // SCIController PackageName
		String voPath = "com.skplanet.storeplatform.sac.client.internal.member." + type + ".vo."; // VO PackageName
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

	public static void main(String[] args) {
		StringBuffer testJson = new StringBuffer();
		testJson.append("{");
		testJson.append("	    \"deviceId\": \"01088870008\",");
		testJson.append("	    \"deviceIdType\": \"msisdn\",");
		testJson.append("	    \"deviceTelecom\": \"US001203\",");
		testJson.append("	    \"ownBirth\": \"20140225\",");
		testJson.append("	    \"parentBirthDay\": \"19830327\",");
		testJson.append("	    \"nativeId\": \"A0000031648EE9\",");
		testJson.append("	    \"agreementList\": [");
		testJson.append("	        {");
		testJson.append("	            \"extraAgreementId\": \"US010601\",");
		testJson.append("	            \"isExtraAgreement\": \"Y\"");
		testJson.append("	        },");
		testJson.append("	        {");
		testJson.append("	            \"extraAgreementId\": \"US010602\",");
		testJson.append("	            \"isExtraAgreement\": \"Y\"");
		testJson.append("	        },");
		testJson.append("	        {");
		testJson.append("	            \"extraAgreementId\": \"US010603\",");
		testJson.append("	            \"isExtraAgreement\": \"Y\"");
		testJson.append("	        }");
		testJson.append("	    ],");
		testJson.append("	    \"deviceExtraInfoList\": [");
		testJson.append("	        {");
		testJson.append("	            \"extraProfile\": \"US011414\",");
		testJson.append("	            \"extraProfileValue\": \"Y\"");
		testJson.append("	        }");
		testJson.append("	    ],");
		testJson.append("	    \"isParent\": \"Y\",");
		testJson.append("	    \"parentRealNameMethod\": \"US011101\",");
		testJson.append("	    \"parentName\": \"고아영\",");
		testJson.append("	    \"parentType\": \"F\",");
		testJson.append("	    \"parentDate\": \"20140123\",");
		testJson.append("	    \"parentEmail\": \"tlaeo00@naver.com\",");
		testJson.append("	    \"parentTelecom\": \"US001201\",");
		testJson.append("	    \"parentPhone\": \"01044072331\",");
		testJson.append("	    \"parentCi\": \"skpone0000132653GWyh3WsEm0FutitO5oSgC2/SgSrLKv5XohA8mxTNLitpB1 B9A3z5zrVHettHzKa5dpJA==\",");
		testJson.append("	    \"parentRealNameDate\": \"20140123\",");
		testJson.append("	    \"parentRealNameSite\": \"US011203\"");
		testJson.append("	}");

		try {
			Map classMap = new ObjectMapper().readValue(testJson.toString(), HashMap.class);
			System.out.println("==hashmap 출력==");
			System.out.println(classMap);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
