package com.skplanet.storeplatform.sac.member.miscellaneous.controller;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.*;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.util.ConvertMapperUtils;
import com.skplanet.storeplatform.sac.member.common.util.ValidationCheckUtils;
import com.skplanet.storeplatform.sac.member.miscellaneous.service.MiscellaneousService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * 기타 기능 관련 Controller
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 */
@Controller
@RequestMapping(value = "/member/miscellaneous")
public class MiscellaneousController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MiscellaneousController.class);

	@Autowired
	private MiscellaneousService service;

	/**
	 * <pre>
	 * UA 코드 정보 조회.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param request
	 *            GetUaCodeReq
	 * @return GetUaCodeRes
	 */
	@RequestMapping(value = "/getUaCode/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetUaCodeRes getUaCode(SacRequestHeader requestHeader, @Validated @RequestBody GetUaCodeReq request) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));

		// 필수 파라미터 확인. 둘 중 하나는 필수로 입력해야함.
		if (StringUtils.isBlank(request.getDeviceModelNo()) && StringUtils.isBlank(request.getMsisdn())) {
			throw new StorePlatformException("SAC_MEM_0001", "deviceModel 또는 msisdn");
		}
		if (StringUtils.isNotBlank(request.getMsisdn())) {
			if (!ValidationCheckUtils.isMdn(request.getMsisdn()))
				throw new StorePlatformException("SAC_MEM_3004");
		}

		GetUaCodeRes response = this.service.getUaCode(requestHeader, request);

		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(response));
		return response;
	}

	/**
	 * <pre>
	 * OPMD 모회선 번호 조회.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param request
	 *            GetOpmdReq
	 * @return GetOpmdRes
	 */
	@RequestMapping(value = "/getOpmd/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetOpmdRes getOpmd(SacRequestHeader requestHeader, @Validated @RequestBody GetOpmdReq request) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));

		// CommonComponent 호출 시에는 mdn Check 불필요하므로, Controller에서 유효성 검사.
		if (!ValidationCheckUtils.isMdn(request.getMsisdn())) {
			throw new StorePlatformException("SAC_MEM_3004");
		}

		GetOpmdRes response = this.service.getOpmd(request);
		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(response));
		return response;
	}

	/**
	 * <pre>
	 * 부가서비스 가입.
	 * </pre>
	 * 
	 * @param request
	 *            CreateAdditionalServiceReq
	 * @return CreateAdditionalServiceRes
	 */
	@RequestMapping(value = "/createAdditionalService/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateAdditionalServiceRes createAdditionalService(@Validated @RequestBody CreateAdditionalServiceReq request) {
		LOGGER.info("Request : {}", ConvertMapperUtils.defaultConvertObjectToJson(request));

		if (!ValidationCheckUtils.isMdn(request.getMsisdn())) {
			throw new StorePlatformException("SAC_MEM_3004");
		}
		CreateAdditionalServiceRes response = this.service.regAdditionalService(request);
		LOGGER.info("Response : msisdn : {}", response.getMsisdn());
		return response;
	}

	/**
	 * <pre>
	 * 부가서비스 가입 조회.
	 * </pre>
	 * 
	 * @param request
	 *            GetAdditionalServiceReq
	 * @return GetAdditionalServiceRes
	 */
	@RequestMapping(value = "/getAdditionalService/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetAdditionalServiceRes getAdditionalService(@Validated @RequestBody GetAdditionalServiceReq request) {

		LOGGER.info("Request : {}", ConvertMapperUtils.defaultConvertObjectToJson(request));

		if (!ValidationCheckUtils.isMdn(request.getMsisdn())) {
			throw new StorePlatformException("SAC_MEM_3004");
		}
		GetAdditionalServiceRes response = this.service.getAdditionalService(request);
		LOGGER.info("Response : join result : {}", response.getSvcJoinResult());
		return response;
	}

	/**
	 * <pre>
	 * 단말 모델코드 조회.
	 * </pre>
	 * 
	 * @param request
	 *            GetModelCodeReq
	 * @return GetModelCodeRes
	 */
	@RequestMapping(value = "/getModelCode/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetModelCodeRes getModelCode(@RequestBody @Validated GetModelCodeReq request) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));

		// 필수 파라미터 확인. 둘 중 하나는 필수로 입력해야함.
		if (StringUtils.isBlank(request.getUaCd()) && StringUtils.isBlank(request.getMsisdn())) {
			throw new StorePlatformException("SAC_MEM_0001", "uaCd 또는 msisdn");
		}
		if (StringUtils.isNotBlank(request.getMsisdn())) {
			if (!ValidationCheckUtils.isMdn(request.getMsisdn()))
				throw new StorePlatformException("SAC_MEM_3004");
		}
		GetModelCodeRes response = this.service.getModelCode(request);
		LOGGER.info("Response : {}", ConvertMapperUtils.convertObjectToJson(response));
		return response;
	}

	/**
	 * <pre>
	 * 결제 계좌 정보 인증.
	 * </pre>
	 * 
	 * @param request
	 *            AuthorizeAccountReq
	 * @return AuthorizeAccountRes
	 */
	@RequestMapping(value = "/authorizeAccount/v1", method = RequestMethod.POST)
	@ResponseBody
	public AuthorizeAccountRes authorizeAccount(@RequestBody @Validated AuthorizeAccountReq request) {

		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));
		AuthorizeAccountRes response = this.service.authorizeAccount(request);

		LOGGER.info("Response : SUCC.");
		return response;
	}

	/**
	 * <pre>
	 * 2.3.8. 사용자별 정책 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param request
	 *            GetIndividualPolicyReq
	 * @return GetIndividualPolicyRes
	 */
	@RequestMapping(value = "/getIndividualPolicy/v1", method = RequestMethod.POST)
	@ResponseBody
	public GetIndividualPolicyRes getIndividualPolicy(SacRequestHeader header,
			@RequestBody @Validated GetIndividualPolicyReq request) {
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));
		GetIndividualPolicyRes response = this.service.getIndividualPolicy(header, request);
		LOGGER.info("Response : policy count : {}", response.getPolicyList().size());
		return response;
	}

	/**
	 * <pre>
	 * 2.3.9. 사용자별 정책 등록/수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param request
	 *            CreateIndividualPolicyReq
	 * @return CreateIndividualPolicyRes
	 */
	@RequestMapping(value = "/createIndividualPolicy/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateIndividualPolicyRes createIndividualPolicy(SacRequestHeader header,
			@RequestBody @Validated CreateIndividualPolicyReq request) {
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));
		CreateIndividualPolicyRes response = this.service.regIndividualPolicy(header, request);
		LOGGER.info("Response : policy key {}", response.getKey());
		return response;
	}

	/**
	 * <pre>
	 * 2.3.10. 사용자별 정책 삭제.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param request
	 *            RemoveIndividualPolicyReq
	 * @return RemoveIndividualPolicyRes
	 */
	@RequestMapping(value = "/removeIndividualPolicy/v1", method = RequestMethod.POST)
	@ResponseBody
	public RemoveIndividualPolicyRes removeIndividualPolicy(SacRequestHeader header,
			@RequestBody @Validated RemoveIndividualPolicyReq request) {
		LOGGER.info("Request : {}", ConvertMapperUtils.convertObjectToJson(request));
		RemoveIndividualPolicyRes response = this.service.remIndividualPolicy(header, request);
		LOGGER.info("Response : policy key : {}", response.getKey());
		return response;
	}

}
