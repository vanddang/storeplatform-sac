/**
 * 
 */
package com.skplanet.storeplatform.member.user.sci;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.integration.bean.LocalSCI;
import com.skplanet.storeplatform.member.client.common.vo.CommonResponse;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSetSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckDevicePinRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckDevicePinResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDevicePinRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDevicePinResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.ModifyDevicePinRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.ModifyDevicePinResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.ModifyDeviceSetInfoRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.ModifyDeviceSetInfoResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDevicePinRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDevicePinResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceSetInfoRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceSetInfoResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.TransferDeviceSetInfoRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.TransferDeviceSetInfoResponse;
import com.skplanet.storeplatform.member.user.service.DeviceSetService;

/**
 * 휴대기기 설정 기능을 제공하는 Controller
 * 
 * Updated on : 2014. 10. 30. Updated by : Rejoice, Burkhan
 */
@LocalSCI
public class DeviceSetSCIController implements DeviceSetSCI {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceSetSCIController.class);

	@Autowired
	private DeviceSetService deviceSet;

	@Autowired
	@Qualifier("scMember")
	private MessageSourceAccessor messageSourceAccessor;

	/**
	 * <pre>
	 * PIN 등록기능을 제공한다.
	 * </pre>
	 * 
	 * @param createDevicePinRequest
	 *            - PIN 등록 요청 Value Object
	 * @return CreateDevicePinResponse - PIN 등록 응답 Value Object
	 */
	@Override
	public CreateDevicePinResponse createDevicePin(CreateDevicePinRequest createDevicePinRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("컨트롤러 - PIN 정보 등록");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CreateDevicePinResponse createDevicePinResponse = null;

		// 입력 파라미터가 없음
		if (createDevicePinRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (createDevicePinRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (createDevicePinRequest.getCommonRequest().getTenantID() == null
				|| createDevicePinRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터
		if (createDevicePinRequest.getKeySearchList() == null || createDevicePinRequest.getKeySearchList().size() <= 0
				|| createDevicePinRequest.getPinNo() == null || createDevicePinRequest.getPinNo().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			// PIN 등록
			createDevicePinResponse = this.deviceSet.createDevicePin(createDevicePinRequest);

			if (createDevicePinResponse != null) {
				createDevicePinResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
				return createDevicePinResponse;
			}
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));

		} catch (StorePlatformException ex) {
			throw ex;
		}

	}

	/**
	 * <pre>
	 * PIN 수정기능을 제공한다.
	 * </pre>
	 * 
	 * @param modifyDevicePinRequest
	 *            - PIN 수정 요청 Value Object
	 * @return ModifyDevicePinResponse - PIN 수정 응답 Value Object
	 */
	@Override
	public ModifyDevicePinResponse modifyDevicePin(ModifyDevicePinRequest modifyDevicePinRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("컨트롤러 - PIN 정보 수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		ModifyDevicePinResponse modifyDevicePinResponse = null;

		// 입력 파라미터가 없음
		if (modifyDevicePinRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (modifyDevicePinRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (modifyDevicePinRequest.getCommonRequest().getTenantID() == null
				|| modifyDevicePinRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터, deviceKey, pinNo, newPinNo
		if (modifyDevicePinRequest.getKeySearchList() == null || modifyDevicePinRequest.getKeySearchList().size() <= 0
				|| modifyDevicePinRequest.getPinNo() == null || modifyDevicePinRequest.getPinNo().length() <= 0
				|| modifyDevicePinRequest.getNewPinNo() == null || modifyDevicePinRequest.getNewPinNo().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			//
			modifyDevicePinResponse = this.deviceSet.modifyDevicePin(modifyDevicePinRequest);

			if (modifyDevicePinResponse != null) {
				modifyDevicePinResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
				return modifyDevicePinResponse;
			}
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));

		} catch (StorePlatformException ex) {
			throw ex;
		}
	}

	/**
	 * <pre>
	 * PIN 번호 초기화 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchDevicePinRequest
	 *            - PIN 초기화 요청 Value Object
	 * @return SearchDevicePinResponse - PIN 초기화 응답 Value Object
	 */
	@Override
	public SearchDevicePinResponse searchDevicePin(SearchDevicePinRequest searchDevicePinRequest) {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("컨트롤러 - PIN 초기화");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchDevicePinResponse searchDevicePinResponse = null;

		// 입력 파라미터가 없음
		if (searchDevicePinRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchDevicePinRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (searchDevicePinRequest.getCommonRequest().getTenantID() == null
				|| searchDevicePinRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터, KeySearchList
		if (searchDevicePinRequest.getKeySearchList() == null || searchDevicePinRequest.getKeySearchList().size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			//
			searchDevicePinResponse = this.deviceSet.searchDevicePin(searchDevicePinRequest);

			if (searchDevicePinResponse != null) {
				searchDevicePinResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
				return searchDevicePinResponse;
			}
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));

		} catch (StorePlatformException ex) {
			throw ex;
		}
	}

	/**
	 * <pre>
	 * PIN 확인 기능을 제공한다.
	 * </pre>
	 * 
	 * @param checkDevicePinRequest
	 *            - PIN 확인 요청 Value Object
	 * @return CheckDevicePinResponse - PIN 확인 응답 Value Object
	 */
	@Override
	public CheckDevicePinResponse checkDevicePin(CheckDevicePinRequest checkDevicePinRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("컨트롤러 - PIN 확인");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CheckDevicePinResponse checkDevicePinResponse = null;

		// 입력 파라미터가 없음
		if (checkDevicePinRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (checkDevicePinRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (checkDevicePinRequest.getCommonRequest().getTenantID() == null
				|| checkDevicePinRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		if (checkDevicePinRequest.getKeySearchList() == null || checkDevicePinRequest.getKeySearchList().size() <= 0
				|| checkDevicePinRequest.getPinNo() == null || checkDevicePinRequest.getPinNo().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			//
			checkDevicePinResponse = this.deviceSet.checkDevicePin(checkDevicePinRequest);

			if (checkDevicePinResponse != null) {
				checkDevicePinResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
				return checkDevicePinResponse;
			}
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));

		} catch (StorePlatformException ex) {
			throw ex;
		}
	}

	/**
	 * <pre>
	 * 휴대기기 설정 정보 조회 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchDeviceSetInfoRequest
	 *            - 휴대기기 설정 정보 조회 요청 Value Object
	 * @return SearchDeviceSetInfoResponse- 휴대기기 설정 정보 조회 응답 Value Object
	 */
	@Override
	public SearchDeviceSetInfoResponse searchDeviceSetInfo(SearchDeviceSetInfoRequest searchDeviceSetInfoRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("컨트롤러 - 설정정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchDeviceSetInfoResponse searchDeviceSetInfoResponse = null;

		// 입력 파라미터가 없음
		if (searchDeviceSetInfoRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (searchDeviceSetInfoRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (searchDeviceSetInfoRequest.getCommonRequest().getTenantID() == null
				|| searchDeviceSetInfoRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터, KeySearchList
		if (searchDeviceSetInfoRequest.getKeySearchList() == null
				|| searchDeviceSetInfoRequest.getKeySearchList().size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			//
			searchDeviceSetInfoResponse = this.deviceSet.searchDeviceSetInfo(searchDeviceSetInfoRequest);

			if (searchDeviceSetInfoResponse != null) {
				searchDeviceSetInfoResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
				return searchDeviceSetInfoResponse;
			}
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));

		} catch (StorePlatformException ex) {
			throw ex;
		}
	}

	/**
	 * <pre>
	 * 휴대기기 설정 정보 등록/수정 기능을 제공한다.
	 * </pre>
	 * 
	 * @param modifyDeviceSetInfoRequest
	 *            - 휴대기기 설정 등록/수정 요청 Value Object
	 * @return ModifyDeviceSetInfoResponse - 휴대기기 설정 등록/수정 응답 Value Object
	 */
	@Override
	public ModifyDeviceSetInfoResponse modifyDeviceSetInfo(ModifyDeviceSetInfoRequest modifyDeviceSetInfoRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("컨트롤러 - 설정 정보 등록/수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		ModifyDeviceSetInfoResponse modifyDeviceSetInfoResponse = null;

		// 입력 파라미터가 없음
		if (modifyDeviceSetInfoRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (modifyDeviceSetInfoRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (modifyDeviceSetInfoRequest.getCommonRequest().getTenantID() == null
				|| modifyDeviceSetInfoRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터, deviceKey, userKey
		if (modifyDeviceSetInfoRequest.getKeySearchList() == null
				|| modifyDeviceSetInfoRequest.getKeySearchList().size() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			//
			modifyDeviceSetInfoResponse = this.deviceSet.modifyDeviceSetInfo(modifyDeviceSetInfoRequest);

			if (modifyDeviceSetInfoResponse != null) {
				modifyDeviceSetInfoResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
				return modifyDeviceSetInfoResponse;
			}
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));

		} catch (StorePlatformException ex) {
			throw ex;
		}
	}

	/**
	 * <pre>
	 * 휴대기기 설정 정보 이관 기능을 제공한다.
	 * </pre>
	 * 
	 * @param transferDeviceSetInfoRequest
	 *            - 휴대기기 설정 정보 이관 요청 Value Object
	 * @return transferDeviceSetInfoResponse - 휴대기기 설정 정보 이관 응답 Value Object
	 */
	@Override
	public TransferDeviceSetInfoResponse transferDeviceSetInfo(TransferDeviceSetInfoRequest transferDeviceSetInfoRequest) {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("컨트롤러 - 설정 정보 이관");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		TransferDeviceSetInfoResponse transferDeviceSetInfoResponse = null;

		// 입력 파라미터가 없음
		if (transferDeviceSetInfoRequest == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.inputNotFound", ""));
		}

		// 공통 파라미터 없음
		if (transferDeviceSetInfoRequest.getCommonRequest() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.commonNotFound", ""));
		}

		// 테넌트 아이디 없음
		if (transferDeviceSetInfoRequest.getCommonRequest().getTenantID() == null
				|| transferDeviceSetInfoRequest.getCommonRequest().getTenantID().length() <= 0) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.tanentIDNotFound", ""));
		}

		// 필수 파라미터, deviceKey, userKey
		if (transferDeviceSetInfoRequest.getUserKey() == null || transferDeviceSetInfoRequest.getDeviceKey() == null
				|| transferDeviceSetInfoRequest.getPreUserKey() == null
				|| transferDeviceSetInfoRequest.getPreDeviceKey() == null) {
			throw new StorePlatformException(this.getMessage("response.ResultCode.mandatoryNotFound", ""));
		}

		try {

			//
			transferDeviceSetInfoResponse = this.deviceSet.transferDeviceSetInfo(transferDeviceSetInfoRequest);

			if (transferDeviceSetInfoResponse != null) {
				transferDeviceSetInfoResponse.setCommonResponse(this.getErrorResponse("response.ResultCode.success",
						"response.ResultMessage.success"));
				return transferDeviceSetInfoResponse;
			}
			throw new StorePlatformException(this.getMessage("response.ResultCode.resultNotFound", ""));

		} catch (StorePlatformException ex) {
			throw ex;
		}
	}

	/**
	 * <pre>
	 * 에러 메시지.
	 * </pre>
	 * 
	 * @param resultCode
	 *            에러코드
	 * @param resultMessage
	 *            에러메세지
	 * @return CommonResponse 결과 메세지
	 */
	private CommonResponse getErrorResponse(String resultCode, String resultMessage) {
		CommonResponse commonResponse;
		commonResponse = new CommonResponse();
		commonResponse.setResultCode(this.getMessage(resultCode, ""));
		commonResponse.setResultMessage(this.getMessage(resultMessage, ""));
		return commonResponse;
	}

	/**
	 * <pre>
	 *  메시지 프로퍼티에서 메시지 참조.
	 * </pre>
	 * 
	 * @param code
	 *            fail 코드
	 * @param fail
	 *            에러메세지
	 * @return String 결과 메세지
	 */
	private String getMessage(String code, String fail) {
		String msg = this.messageSourceAccessor.getMessage(code, null, fail, LocaleContextHolder.getLocale());
		LOGGER.debug(msg);
		return msg;
	}
}
