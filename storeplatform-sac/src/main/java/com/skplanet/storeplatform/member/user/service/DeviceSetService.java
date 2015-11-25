package com.skplanet.storeplatform.member.user.service;

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

/**
 * 휴대기기 설정 기능을 제공하는 Service interface.
 * 
 * Updated on : 2014. 10. 30. Updated by : Rejoice, Burkhan
 */
public interface DeviceSetService {

	/**
	 * <pre>
	 * PIN 등록기능을 제공한다.
	 * </pre>
	 * 
	 * @param createDevicePinRequest
	 *            - PIN 등록 요청 Value Object
	 * @return CreateDevicePinResponse - PIN 등록 응답 Value Object
	 */
	public CreateDevicePinResponse createDevicePin(CreateDevicePinRequest createDevicePinRequest);

	/**
	 * <pre>
	 * PIN 수정기능을 제공한다.
	 * </pre>
	 * 
	 * @param modifyDevicePinRequest
	 *            - PIN 수정 요청 Value Object
	 * @return ModifyDevicePinResponse - PIN 수정 응답 Value Object
	 */
	public ModifyDevicePinResponse modifyDevicePin(ModifyDevicePinRequest modifyDevicePinRequest);

	/**
	 * <pre>
	 * PIN 번호 초기화 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchDevicePinRequest
	 *            - PIN 초기화 요청 Value Object
	 * @return SearchDevicePinResponse - PIN 초기화 응답 Value Object
	 */
	public SearchDevicePinResponse searchDevicePin(SearchDevicePinRequest searchDevicePinRequest);

	/**
	 * <pre>
	 * PIN 확인 기능을 제공한다.
	 * </pre>
	 * 
	 * @param checkDevicePinRequest
	 *            - PIN 확인 요청 Value Object
	 * @return CheckDevicePinResponse - PIN 확인 응답 Value Object
	 */
	public CheckDevicePinResponse checkDevicePin(CheckDevicePinRequest checkDevicePinRequest);

	/**
	 * <pre>
	 * 휴대기기 설정 정보 조회 기능을 제공한다.
	 * </pre>
	 * 
	 * @param searchDeviceSetInfoRequest
	 *            - 휴대기기 설정 정보 조회 요청 Value Object
	 * @return SearchDeviceSetInfoResponse- 휴대기기 설정 정보 조회 응답 Value Object
	 */
	public SearchDeviceSetInfoResponse searchDeviceSetInfo(SearchDeviceSetInfoRequest searchDeviceSetInfoRequest);

	/**
	 * <pre>
	 * 휴대기기 설정 정보 등록/수정 기능을 제공한다.
	 * </pre>
	 * 
	 * @param modifyDeviceSetInfoRequest
	 *            - 휴대기기 설정 등록/수정 요청 Value Object
	 * @return ModifyDeviceSetInfoResponse - 휴대기기 설정 등록/수정 응답 Value Object
	 */
	public ModifyDeviceSetInfoResponse modifyDeviceSetInfo(ModifyDeviceSetInfoRequest modifyDeviceSetInfoRequest);

	/**
	 * <pre>
	 * 휴대기기 설정 정보 이관 기능을 제공한다.
	 * </pre>
	 * 
	 * @param transferDeviceSetInfoRequest
	 *            - 휴대기기 설정 정보 이관 요청 Value Object
	 * @return transferDeviceSetInfoResponse - 휴대기기 설정 정보 이관 응답 Value Object
	 */
	public TransferDeviceSetInfoResponse transferDeviceSetInfo(TransferDeviceSetInfoRequest transferDeviceSetInfoRequest);
}
