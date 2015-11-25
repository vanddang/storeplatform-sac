package com.skplanet.storeplatform.member.client;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.skplanet.storeplatform.member.client.common.constant.Constant;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
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
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDeviceSet;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 10. 31. Updated by : Rejoice, Burkhan
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/member/spring-test/context-test.xml" })
// @Transactional
@TransactionConfiguration
public class DeviceSetSCITest {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceSetSCITest.class);

	@Autowired
	private DeviceSetSCI deviceSetSCI;

	@Autowired
	@Qualifier("scMember")
	private MessageSourceAccessor messageSourceAccessor;

	/**
	 * <pre>
	 * PIN 등록기능을 제공한다.
	 * </pre>
	 */
	@Test
	public void createDevicePinTest() {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - PIN 등록");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CreateDevicePinRequest createDevicePinRequest;
		CreateDevicePinResponse createDevicePinResponse;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01"); // 태넌트 id

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_ID);
		// keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_KEY);
		keySearch.setKeyString("01088870008");
		// keySearch.setKeyString("DE201410011416278930005332");

		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		createDevicePinRequest = new CreateDevicePinRequest();
		createDevicePinRequest.setCommonRequest(commonRequest);
		createDevicePinRequest.setKeySearchList(keySearchList);
		createDevicePinRequest.setPinNo("1234");

		// 요청
		createDevicePinResponse = this.deviceSetSCI.createDevicePin(createDevicePinRequest);

		// 응답
		assertThat(createDevicePinResponse, notNullValue());
		assertThat(createDevicePinResponse, notNullValue());
		// 응답 > 공통
		assertThat(createDevicePinResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(createDevicePinResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(createDevicePinResponse.getCommonResponse().getResultMessage(), notNullValue());

		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				createDevicePinResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > deviceKey
		assertThat(createDevicePinResponse.getDeviceKey(), notNullValue());
		// 응답필수 > userKey
		assertThat(createDevicePinResponse.getUserKey(), notNullValue());
		// 응답필수 > deviceId
		assertThat(createDevicePinResponse.getDeviceId(), notNullValue());

		LOGGER.info("### searchDeviceListResponse : {}", createDevicePinResponse);

	}

	/**
	 * <pre>
	 * PIN 수정기능.
	 * </pre>
	 */
	@Test
	public void modifyDevicePinTest() {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - PIN 등록");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		ModifyDevicePinRequest modifyDevicePinRequest;
		ModifyDevicePinResponse modifyDevicePinResponse;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01"); // 태넌트 id

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_ID);
		// keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_KEY);
		keySearch.setKeyString("01088870008");
		// keySearch.setKeyString("DE201410011416278930005332");

		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		modifyDevicePinRequest = new ModifyDevicePinRequest();
		modifyDevicePinRequest.setCommonRequest(commonRequest);
		modifyDevicePinRequest.setKeySearchList(keySearchList);
		modifyDevicePinRequest.setPinNo("4321");
		modifyDevicePinRequest.setNewPinNo("4320");

		// 요청
		modifyDevicePinResponse = this.deviceSetSCI.modifyDevicePin(modifyDevicePinRequest);

		// 응답
		assertThat(modifyDevicePinResponse, notNullValue());
		assertThat(modifyDevicePinResponse, notNullValue());
		// 응답 > 공통
		assertThat(modifyDevicePinResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(modifyDevicePinResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(modifyDevicePinResponse.getCommonResponse().getResultMessage(), notNullValue());

		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				modifyDevicePinResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > deviceKey
		assertThat(modifyDevicePinResponse.getDeviceKey(), notNullValue());
		// 응답필수 > userKey
		assertThat(modifyDevicePinResponse.getUserKey(), notNullValue());
		// 응답필수 > deviceId
		assertThat(modifyDevicePinResponse.getDeviceId(), notNullValue());

		LOGGER.info("### searchDeviceListResponse : {}", modifyDevicePinResponse);
	}

	/**
	 * <pre>
	 * PIN 확인 기능.
	 * </pre>
	 */
	@Test
	public void checkDevicePinTest() {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - PIN 등록");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CheckDevicePinRequest checkDevicePinRequest;
		CheckDevicePinResponse checkDevicePinResponse;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01"); // 태넌트 id

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_ID);
		// keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_KEY);
		keySearch.setKeyString("01088870008");
		// keySearch.setKeyString("DE201410011416278930005332");

		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		checkDevicePinRequest = new CheckDevicePinRequest();
		checkDevicePinRequest.setCommonRequest(commonRequest);
		checkDevicePinRequest.setKeySearchList(keySearchList);
		checkDevicePinRequest.setPinNo("1235");

		// 요청
		checkDevicePinResponse = this.deviceSetSCI.checkDevicePin(checkDevicePinRequest);

		// 응답
		assertThat(checkDevicePinResponse, notNullValue());
		assertThat(checkDevicePinResponse, notNullValue());
		// 응답 > 공통
		assertThat(checkDevicePinResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(checkDevicePinResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(checkDevicePinResponse.getCommonResponse().getResultMessage(), notNullValue());

		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				checkDevicePinResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > deviceKey
		assertThat(checkDevicePinResponse.getDeviceKey(), notNullValue());
		// 응답필수 > userKey
		assertThat(checkDevicePinResponse.getUserKey(), notNullValue());
		// 응답필수 > deviceId
		assertThat(checkDevicePinResponse.getDeviceId(), notNullValue());

		LOGGER.info("### searchDeviceListResponse : {}", checkDevicePinResponse);
	}

	/**
	 * <pre>
	 * PIN 번호 초기화 기능.
	 * </pre>
	 */
	@Test
	public void searchDevicePinTest() {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - PIN 초기화");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchDevicePinRequest searchDevicePinRequest;
		SearchDevicePinResponse searchDevicePinResponse;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01"); // 태넌트 id

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_ID);
		// keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_KEY);
		keySearch.setKeyString("01088870008");
		// keySearch.setKeyString("DE201410011416278930005332");

		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		searchDevicePinRequest = new SearchDevicePinRequest();
		searchDevicePinRequest.setCommonRequest(commonRequest);
		searchDevicePinRequest.setKeySearchList(keySearchList);

		// 요청
		searchDevicePinResponse = this.deviceSetSCI.searchDevicePin(searchDevicePinRequest);

		// 응답
		assertThat(searchDevicePinResponse, notNullValue());
		assertThat(searchDevicePinResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchDevicePinResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchDevicePinResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchDevicePinResponse.getCommonResponse().getResultMessage(), notNullValue());

		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchDevicePinResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > deviceKey
		assertThat(searchDevicePinResponse.getDeviceKey(), notNullValue());
		// 응답필수 > userKey
		assertThat(searchDevicePinResponse.getUserKey(), notNullValue());
		// 응답필수 > deviceId
		assertThat(searchDevicePinResponse.getDeviceId(), notNullValue());
		// 응답필수 > pinNo
		assertThat(searchDevicePinResponse.getPinNo(), notNullValue());

		LOGGER.info("### searchDeviceListResponse : {}", searchDevicePinResponse);
	}

	/**
	 * <pre>
	 * 휴대기기 설정 정보 조회 기능을 제공한다.
	 * </pre>
	 */
	@Test
	public void searchDeviceSetInfoTest() {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 휴대기기 설정 정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchDeviceSetInfoRequest searchDeviceSetInfoRequest;
		SearchDeviceSetInfoResponse searchDeviceSetInfoResponse;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01"); // 태넌트 id

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_KEY);
		keySearch.setKeyString("DE201410011416278930005332");

		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		searchDeviceSetInfoRequest = new SearchDeviceSetInfoRequest();
		searchDeviceSetInfoRequest.setCommonRequest(commonRequest);
		searchDeviceSetInfoRequest.setKeySearchList(keySearchList);

		// 요청
		searchDeviceSetInfoResponse = this.deviceSetSCI.searchDeviceSetInfo(searchDeviceSetInfoRequest);

		// 응답
		assertThat(searchDeviceSetInfoResponse, notNullValue());
		assertThat(searchDeviceSetInfoResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchDeviceSetInfoResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchDeviceSetInfoResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchDeviceSetInfoResponse.getCommonResponse().getResultMessage(), notNullValue());

		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchDeviceSetInfoResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > deviceKey
		assertThat(searchDeviceSetInfoResponse.getUserMbrDeviceSet().getDeviceKey(), notNullValue());
		// 응답필수 > userKey
		assertThat(searchDeviceSetInfoResponse.getUserMbrDeviceSet().getUserKey(), notNullValue());

		LOGGER.info("### searchDeviceListResponse : {}", searchDeviceSetInfoResponse);
	}

	/**
	 * <pre>
	 * 휴대기기 설정 정보 등록/수정 기능을 제공한다.
	 * </pre>
	 */
	@Test
	public void modifyDeviceSetInfoTest() {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 휴대기기 설정 정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		ModifyDeviceSetInfoRequest modifyDeviceSetInfoRequest;
		ModifyDeviceSetInfoResponse modifyDeviceSetInfoResponse;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01"); // 태넌트 id

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_ID);
		// keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_KEY);
		keySearch.setKeyString("01088870008");
		// keySearch.setKeyString("DE201410011416278930005332");

		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		UserMbrDeviceSet userMbrDeviceSet = new UserMbrDeviceSet();

		userMbrDeviceSet.setAutoUpdateSet("A");
		userMbrDeviceSet.setIsAdult("Y");
		userMbrDeviceSet.setIsAutoUpdate("Y");
		userMbrDeviceSet.setIsAutoUpdateWifi("Y");
		userMbrDeviceSet.setIsLoginLock("Y");
		userMbrDeviceSet.setIsPinRetry("Y");

		// 요청 필수
		modifyDeviceSetInfoRequest = new ModifyDeviceSetInfoRequest();
		modifyDeviceSetInfoRequest.setCommonRequest(commonRequest);
		modifyDeviceSetInfoRequest.setKeySearchList(keySearchList);
		modifyDeviceSetInfoRequest.setUserMbrDeviceSet(userMbrDeviceSet);
		// 요청
		modifyDeviceSetInfoResponse = this.deviceSetSCI.modifyDeviceSetInfo(modifyDeviceSetInfoRequest);

		// 응답
		assertThat(modifyDeviceSetInfoResponse, notNullValue());
		assertThat(modifyDeviceSetInfoResponse, notNullValue());
		// 응답 > 공통
		assertThat(modifyDeviceSetInfoResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(modifyDeviceSetInfoResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(modifyDeviceSetInfoResponse.getCommonResponse().getResultMessage(), notNullValue());

		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				modifyDeviceSetInfoResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > deviceKey
		assertThat(modifyDeviceSetInfoResponse.getDeviceKey(), notNullValue());
		// 응답필수 > userKey
		assertThat(modifyDeviceSetInfoResponse.getUserKey(), notNullValue());

		LOGGER.info("### searchDeviceListResponse : {}", modifyDeviceSetInfoResponse);
	}

	/**
	 * <pre>
	 * 휴대기기 설정 정보 등록/수정 기능을 제공한다.
	 * </pre>
	 */
	@Test
	public void transferDeviceSetInfoTest() {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 휴대기기 설정 정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		TransferDeviceSetInfoRequest transferDeviceSetInfoRequest;
		TransferDeviceSetInfoResponse transferDeviceSetInfoResponse;
		CommonRequest commonRequest;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01"); // 태넌트 id

		// 요청 필수
		transferDeviceSetInfoRequest = new TransferDeviceSetInfoRequest();
		transferDeviceSetInfoRequest.setCommonRequest(commonRequest);

		transferDeviceSetInfoRequest.setDeviceKey("DE2014112010592179315045193");
		transferDeviceSetInfoRequest.setUserKey("US201411201059183060008531");

		transferDeviceSetInfoRequest.setPreDeviceKey("DE2014112010592179315045192");
		transferDeviceSetInfoRequest.setPreUserKey("US201411201059183060008530");

		// 요청
		transferDeviceSetInfoResponse = this.deviceSetSCI.transferDeviceSetInfo(transferDeviceSetInfoRequest);

		// 응답
		assertThat(transferDeviceSetInfoResponse, notNullValue());
		assertThat(transferDeviceSetInfoResponse, notNullValue());
		// 응답 > 공통
		assertThat(transferDeviceSetInfoResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(transferDeviceSetInfoResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(transferDeviceSetInfoResponse.getCommonResponse().getResultMessage(), notNullValue());

		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				transferDeviceSetInfoResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > deviceKey
		assertThat(transferDeviceSetInfoResponse.getDeviceKey(), notNullValue());
		// 응답필수 > userKey
		assertThat(transferDeviceSetInfoResponse.getUserKey(), notNullValue());

		LOGGER.info("### searchDeviceListResponse : {}", transferDeviceSetInfoResponse);
	}
}
