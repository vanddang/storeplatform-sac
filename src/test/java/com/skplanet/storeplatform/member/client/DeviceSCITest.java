package com.skplanet.storeplatform.member.client;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.member.client.common.constant.Constant;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckSaveNSyncRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CheckSaveNSyncResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.ReviveUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.ReviveUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAllDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAllDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceOwnerRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceOwnerResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchOrderDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchOrderDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SetMainDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SetMainDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateDeviceManagementRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateDeviceManagementResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDevice;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDeviceDetail;

/**
 * 휴대기기 API 테스트.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/member/spring-test/context-test.xml" })
@Transactional
@TransactionConfiguration
public class DeviceSCITest {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SellerSCITest.class);

	@Autowired
	private DeviceSCI deviceSCI;

	@Autowired
	@Qualifier("scMember")
	private MessageSourceAccessor messageSourceAccessor;

	/**
	 * <pre>
	 * 등록된 휴대기기 목록 조회.
	 * isMainDevice(대표기기 여부) : Y
	 * 5대를 보유한 사용자 
	 * userKey : IF1023002708420090928145937
	 * </pre>
	 */
	@Test
	public void searchDeviceListInsdUsermbrNO() {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 등록된 휴대기기 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchDeviceListRequest searchDeviceListRequest;
		SearchDeviceListResponse searchDeviceListResponse;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01"); // 태넌트 id

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_USER_KEY);
		keySearch.setKeyString("US201402071242230500001968");
		// keySearch.setKeyString("IW1424093256120120914010537"); // 상용 사용자키

		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		searchDeviceListRequest = new SearchDeviceListRequest();
		searchDeviceListRequest.setCommonRequest(commonRequest);
		searchDeviceListRequest.setKeySearchList(keySearchList);
		searchDeviceListRequest.setIsMainDevice("N");

		// 요청
		searchDeviceListResponse = this.deviceSCI.searchDeviceList(searchDeviceListRequest);

		// 응답
		assertThat(searchDeviceListResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchDeviceListResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchDeviceListResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchDeviceListResponse.getCommonResponse().getResultMessage(), notNullValue());

		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchDeviceListResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > userID
		assertThat(searchDeviceListResponse.getUserID(), notNullValue());
		// 응답필수 > userKey
		assertThat(searchDeviceListResponse.getUserKey(), notNullValue());

		LOGGER.info("### searchDeviceListResponse : {}", searchDeviceListResponse);

		LOGGER.info("### searchDeviceListResponse : {}", searchDeviceListResponse);
		List<UserMbrDevice> userMbrDeviceList = searchDeviceListResponse.getUserMbrDevice();
		LOGGER.debug("### size : {}", userMbrDeviceList.size());
		for (UserMbrDevice userMbrDevice : userMbrDeviceList) {
			LOGGER.debug("### getDeviceID : {}", userMbrDevice.getDeviceID());
			LOGGER.debug("### getIsPrimary : {}", userMbrDevice.getIsPrimary());
		}

	}

	/**
	 * <pre>
	 * AUTH_DT authenticationDate 인증 날짜.
	 * ENTRY_CHNL_CD joinId 가입 채널 코드.
	 * 추가로 내려줌.
	 * 로그출력
	 * 등록된 휴대기기 목록 조회.
	 * </pre>
	 */
	@Test
	public void searchDeviceList() {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 등록된 휴대기기 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchDeviceListRequest searchDeviceListRequest;
		SearchDeviceListResponse searchDeviceListResponse;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01"); // 태넌트 id

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_USER_KEY);
		// keySearch.setKeyString("IW1424093256120120914010537"); // 상용 사용자키
		keySearch.setKeyString("IF1023002708420090928145937"); // US201401280706367180001249
		// keySearch.setKeyString("US201401280706367180001249"); // sac test

		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		searchDeviceListRequest = new SearchDeviceListRequest();
		searchDeviceListRequest.setCommonRequest(commonRequest);
		searchDeviceListRequest.setKeySearchList(keySearchList);
		searchDeviceListRequest.setIsMainDevice("N");

		// 요청
		searchDeviceListResponse = this.deviceSCI.searchDeviceList(searchDeviceListRequest);

		// 응답
		assertThat(searchDeviceListResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchDeviceListResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchDeviceListResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchDeviceListResponse.getCommonResponse().getResultMessage(), notNullValue());

		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchDeviceListResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > userID
		assertThat(searchDeviceListResponse.getUserID(), notNullValue());
		// 응답필수 > userKey
		assertThat(searchDeviceListResponse.getUserKey(), notNullValue());

		LOGGER.info("### searchDeviceListResponse : {}", searchDeviceListResponse);

		LOGGER.info("### searchDeviceListResponse : {}", searchDeviceListResponse);
		List<UserMbrDevice> userMbrDeviceList = searchDeviceListResponse.getUserMbrDevice();
		LOGGER.debug("### size : {}", userMbrDeviceList.size());
		for (UserMbrDevice userMbrDevice : userMbrDeviceList) {
			LOGGER.debug("### getDeviceID : {}", userMbrDevice.getDeviceID());
			LOGGER.debug("### getIsPrimary : {}", userMbrDevice.getIsPrimary());

			LOGGER.debug("### authenticationDate : {}", userMbrDevice.getAuthenticationDate());
			LOGGER.debug("### joinId : {}", userMbrDevice.getJoinId());
		}

	}

	/**
	 * <pre>
	 * 등록된 휴대기기 목록 조회.
	 * isMainDevice(대표기기 여부) : N
	 * 5대를 보유한 사용자 
	 * userKey : IF1023002708420090928145937
	 * </pre>
	 */
	@Test
	public void searchDeviceListInsdUsermbrNONotMain() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 등록된 휴대기기 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchDeviceListRequest searchDeviceListRequest;
		SearchDeviceListResponse searchDeviceListResponse;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01"); // 태넌트 id

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_USER_KEY);
		// keySearch.setKeyString("IW1424093256120120914010537"); // 상용 사용자키
		keySearch.setKeyString("IF1023002708420090928145937");

		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		searchDeviceListRequest = new SearchDeviceListRequest();
		searchDeviceListRequest.setCommonRequest(commonRequest);
		searchDeviceListRequest.setKeySearchList(keySearchList);
		searchDeviceListRequest.setIsMainDevice("N");

		// 요청
		searchDeviceListResponse = this.deviceSCI.searchDeviceList(searchDeviceListRequest);

		// 응답
		assertThat(searchDeviceListResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchDeviceListResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchDeviceListResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchDeviceListResponse.getCommonResponse().getResultMessage(), notNullValue());

		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchDeviceListResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > userID
		assertThat(searchDeviceListResponse.getUserID(), notNullValue());
		// 응답필수 > userKey
		assertThat(searchDeviceListResponse.getUserKey(), notNullValue());

		LOGGER.info("### searchDeviceListResponse : {}", searchDeviceListResponse);

	}

	/**
	 * 등록된 휴대기기 목록 조회.
	 */
	@Test
	public void searchDeviceListMbrID() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 등록된 휴대기기 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchDeviceListRequest searchDeviceListRequest;
		SearchDeviceListResponse searchDeviceListResponse;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01"); // 태넌트 id

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_USER_ID);
		keySearch.setKeyString("shop_20017421"); // 상용, 정상상태인 사용자
		keySearch.setKeyString("IDS01-01002000000001099");
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		searchDeviceListRequest = new SearchDeviceListRequest();
		searchDeviceListRequest.setCommonRequest(commonRequest);
		searchDeviceListRequest.setKeySearchList(keySearchList);
		searchDeviceListRequest.setIsMainDevice("Y");

		// 요청
		searchDeviceListResponse = this.deviceSCI.searchDeviceList(searchDeviceListRequest);

		// 응답
		assertThat(searchDeviceListResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchDeviceListResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchDeviceListResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchDeviceListResponse.getCommonResponse().getResultMessage(), notNullValue());

		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchDeviceListResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > userID
		assertThat(searchDeviceListResponse.getUserID(), notNullValue());
		// 응답필수 > userKey
		assertThat(searchDeviceListResponse.getUserKey(), notNullValue());

		LOGGER.info("### searchDeviceListResponse : {}", searchDeviceListResponse);

	}

	/**
	 * 등록된 휴대기기 목록 조회.
	 */
	@Test
	public void searchDeviceListIntgSvcNo() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 등록된 휴대기기 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchDeviceListRequest searchDeviceListRequest;
		SearchDeviceListResponse searchDeviceListResponse;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01"); // 태넌트 id

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_ONEID_KEY);
		// keySearch.setKeyString("100012569772"); // 상용 서비스 관리번호
		keySearch.setKeyString("100000003281");
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		searchDeviceListRequest = new SearchDeviceListRequest();
		searchDeviceListRequest.setCommonRequest(commonRequest);
		searchDeviceListRequest.setKeySearchList(keySearchList);
		searchDeviceListRequest.setIsMainDevice("Y");

		// 요청
		searchDeviceListResponse = this.deviceSCI.searchDeviceList(searchDeviceListRequest);

		// 응답
		assertThat(searchDeviceListResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchDeviceListResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchDeviceListResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchDeviceListResponse.getCommonResponse().getResultMessage(), notNullValue());

		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchDeviceListResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > userID
		assertThat(searchDeviceListResponse.getUserID(), notNullValue());
		// 응답필수 > userKey
		assertThat(searchDeviceListResponse.getUserKey(), notNullValue());

		LOGGER.info("### searchDeviceListResponse : {}", searchDeviceListResponse);

	}

	/**
	 * 등록된 휴대기기 목록 조회.
	 */
	@Test
	public void searchDeviceListUsermbrNo() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 등록된 휴대기기 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchDeviceListRequest searchDeviceListRequest;
		SearchDeviceListResponse searchDeviceListResponse;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01"); // 태넌트 id

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_IDP_KEY);
		keySearch.setKeyString("IW1023857942220110414141217");
		// keySearch.setKeyString("IW1425939715620120221212652"); // 상용 usermbr_no

		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		searchDeviceListRequest = new SearchDeviceListRequest();
		searchDeviceListRequest.setCommonRequest(commonRequest);
		searchDeviceListRequest.setKeySearchList(keySearchList);
		searchDeviceListRequest.setIsMainDevice("N");

		// 요청
		searchDeviceListResponse = this.deviceSCI.searchDeviceList(searchDeviceListRequest);

		// 응답
		assertThat(searchDeviceListResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchDeviceListResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchDeviceListResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchDeviceListResponse.getCommonResponse().getResultMessage(), notNullValue());

		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchDeviceListResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > userID
		assertThat(searchDeviceListResponse.getUserID(), notNullValue());
		// 응답필수 > userKey
		assertThat(searchDeviceListResponse.getUserKey(), notNullValue());

		LOGGER.info("### searchDeviceListResponse : {}", searchDeviceListResponse);
		List<UserMbrDevice> userMbrDeviceList = searchDeviceListResponse.getUserMbrDevice();
		LOGGER.debug("### size : {}", userMbrDeviceList.size());
		for (UserMbrDevice userMbrDevice : userMbrDeviceList) {
			LOGGER.debug("### data : {}", userMbrDevice.getDeviceID());
		}

	}

	/**
	 * 등록된 휴대기기 목록 조회.
	 */
	@Test
	public void searchDeviceListInsdDeviceID() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 등록된 휴대기기 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchDeviceListRequest searchDeviceListRequest;
		SearchDeviceListResponse searchDeviceListResponse;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01"); // 태넌트 id

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_KEY);
		keySearch.setKeyString("DE201401272118334120000552");
		// keySearch.setKeyString("**0914768799"); // 상용 deviceKey

		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		searchDeviceListRequest = new SearchDeviceListRequest();
		searchDeviceListRequest.setCommonRequest(commonRequest);
		searchDeviceListRequest.setKeySearchList(keySearchList);
		searchDeviceListRequest.setIsMainDevice("N");

		// 요청
		searchDeviceListResponse = this.deviceSCI.searchDeviceList(searchDeviceListRequest);

		// 응답
		assertThat(searchDeviceListResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchDeviceListResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchDeviceListResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchDeviceListResponse.getCommonResponse().getResultMessage(), notNullValue());

		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchDeviceListResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > userID
		assertThat(searchDeviceListResponse.getUserID(), notNullValue());
		// 응답필수 > userKey
		assertThat(searchDeviceListResponse.getUserKey(), notNullValue());

		LOGGER.info("### searchDeviceListResponse : {}", searchDeviceListResponse);

	}

	/**
	 * 등록된 휴대기기 목록 조회.
	 */
	@Test
	public void searchDeviceListDeviceID() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 등록된 휴대기기 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchDeviceListRequest searchDeviceListRequest;
		SearchDeviceListResponse searchDeviceListResponse;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01"); // 태넌트 id

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_ID);
		keySearch.setKeyString("01077281935"); // 상용 MDN
		keySearch.setKeyString("01020977773"); // 0501234562 // 01093808294
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// keySearch = new KeySearch();
		// keySearch.setKeyType(Constant.SEARCH_TYPE_USER_KEY);
		// keySearch.setKeyString("US201402071242230500001968");
		// keySearchList.add(keySearch);

		// 요청 필수
		searchDeviceListRequest = new SearchDeviceListRequest();
		searchDeviceListRequest.setCommonRequest(commonRequest);
		searchDeviceListRequest.setKeySearchList(keySearchList);
		searchDeviceListRequest.setIsMainDevice("N");

		// 요청
		searchDeviceListResponse = this.deviceSCI.searchDeviceList(searchDeviceListRequest);

		// 응답
		assertThat(searchDeviceListResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchDeviceListResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchDeviceListResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchDeviceListResponse.getCommonResponse().getResultMessage(), notNullValue());

		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchDeviceListResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > userID
		assertThat(searchDeviceListResponse.getUserID(), notNullValue());
		// 응답필수 > userKey
		assertThat(searchDeviceListResponse.getUserKey(), notNullValue());

		LOGGER.info("### searchDeviceListResponse : {}", searchDeviceListResponse);

	}

	/**
	 * <pre>
	 * 조건이 여러개인 휴대기기 목록 조회.
	 * DEVICE_ID : 01020284280
	 * INSD_DEVICE_ID : DE201401161113425370000050
	 * MBR_ID : 01020284280
	 * isMainDevice : N
	 * </pre>
	 */
	@Test
	public void searchDeviceListMultiSearchType() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 등록된 휴대기기 목록 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchDeviceListRequest searchDeviceListRequest;
		SearchDeviceListResponse searchDeviceListResponse;
		CommonRequest commonRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01"); // 태넌트 id

		// 조건 설정
		keySearchList = new ArrayList<KeySearch>();

		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_ID);
		keySearch.setKeyString("0501234565");
		// keySearch.setKeyString("01077281935"); // 상용 deviceID

		keySearchList.add(keySearch);

		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_KEY);
		keySearch.setKeyString("DE201402101945298110001383");
		// keySearch.setKeyString("01077281935"); // 상용 deviceKey
		keySearchList.add(keySearch);

		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_USER_ID);
		keySearch.setKeyString("wisestone2");
		// keySearch.setKeyString("shop_20017421"); // 상용 userID

		keySearchList.add(keySearch);

		// 요청 필수
		searchDeviceListRequest = new SearchDeviceListRequest();
		searchDeviceListRequest.setCommonRequest(commonRequest);
		searchDeviceListRequest.setKeySearchList(keySearchList);
		searchDeviceListRequest.setIsMainDevice("N");

		// 요청
		searchDeviceListResponse = this.deviceSCI.searchDeviceList(searchDeviceListRequest);

		// 응답
		assertThat(searchDeviceListResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchDeviceListResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchDeviceListResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchDeviceListResponse.getCommonResponse().getResultMessage(), notNullValue());

		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchDeviceListResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답필수 > userID
		assertThat(searchDeviceListResponse.getUserID(), notNullValue());
		// 응답필수 > userKey
		assertThat(searchDeviceListResponse.getUserKey(), notNullValue());

		LOGGER.info("### searchDeviceListResponse : {}", searchDeviceListResponse);
		LOGGER.info("### getUserMbrDevice size : {}", searchDeviceListResponse.getUserMbrDevice().size());

	}

	/**
	 * 휴대기기 개별 조회 : 전체, USER_KEY.
	 */
	@Test
	public void searchDeviceInsdDeviceID() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 휴대기기 개별 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchDeviceRequest searchDeviceRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_KEY);

		keySearch.setKeyString("DE201401272118334120000552"); // 기기 있는 회원의 정상 기기
		// keySearch.setKeyString("DE201402101841152570001375"); // 기기 있는 회원의 정상 기기
		// keySearch.setKeyString("DE201402051557350450000939"); // 기기 있는 회원의 삭제 기기
		// keySearch.setKeyString("01077281935"); // 상용 deviceKey
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		searchDeviceRequest = new SearchDeviceRequest();
		searchDeviceRequest.setCommonRequest(commonRequest);
		// searchDeviceRequest.setUserKey("US201401241840125650000649"); // 기기 없는 회원
		// searchDeviceRequest.setUserKey("US201402051557349950001648"); // 기기 있는 회원
		// searchDeviceRequest.setUserKey("US201402051557349950001649"); // 유령 회원
		// searchDeviceRequest.setUserKey("US201403180943390260004549"); // 기기 있는 회원
		searchDeviceRequest.setUserKey("US201401272118331600001099"); // 기기 있는 회원
		// searchDeviceRequest.setUserKey("IW1424093256120120914010537"); // 상용 userKey
		searchDeviceRequest.setKeySearchList(keySearchList);

		SearchDeviceResponse searchDeviceResponse = this.deviceSCI.searchDevice(searchDeviceRequest);

		// 응답
		assertThat(searchDeviceResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchDeviceResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchDeviceResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchDeviceResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 이 테스트는 메시지가 성공이어야함.
		assertSame(
				searchDeviceResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.info("### 서버에서 내려받은 값 : {}", searchDeviceResponse.toString());

	}

	/**
	 * 휴대기기 개별 조회 : 전체, USER_KEY.
	 */
	@Test
	public void searchDeviceDeviceID() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 휴대기기 개별 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchDeviceRequest searchDeviceRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_ID);

		keySearch.setKeyString("01001231122");
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		searchDeviceRequest = new SearchDeviceRequest();
		searchDeviceRequest.setCommonRequest(commonRequest);
		// searchDeviceRequest.setUserKey("US201402070435490820001979");
		searchDeviceRequest.setUserKey("US201401272118331600001099"); // 기기 있는 회원
		// searchDeviceRequest.setUserKey("IM142100006600755201304050800"); // 상용 userKey

		searchDeviceRequest.setKeySearchList(keySearchList);

		SearchDeviceResponse searchDeviceResponse = this.deviceSCI.searchDevice(searchDeviceRequest);

		// 응답
		assertThat(searchDeviceResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchDeviceResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchDeviceResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchDeviceResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 이 테스트는 메시지가 성공이어야함.
		assertSame(
				searchDeviceResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.info("### 서버에서 내려받은 값 : {}", searchDeviceResponse.toString());

	}

	/**
	 * 휴대기기 개별 조회 : 전체, no USER_KEY.
	 */
	@Test
	public void searchDeviceDeviceIDNoUserkey() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 휴대기기 개별 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchDeviceRequest searchDeviceRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_DEVICE_ID);
		keySearch.setKeyString("01020977773");
		// keySearch.setKeyString("01065951623"); // 상용 존재하는 deviceKey
		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		searchDeviceRequest = new SearchDeviceRequest();
		searchDeviceRequest.setCommonRequest(commonRequest);
		searchDeviceRequest.setKeySearchList(keySearchList);

		SearchDeviceResponse searchDeviceResponse = this.deviceSCI.searchDevice(searchDeviceRequest);

		// 응답
		assertThat(searchDeviceResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchDeviceResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchDeviceResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchDeviceResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 이 테스트는 메시지가 성공이어야함.
		assertSame(
				searchDeviceResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.info("### 서버에서 내려받은 값 : {}", searchDeviceResponse.toString());

	}

	/**
	 * 휴대기기 개별 조회 : 휴대기기 서비스 관리번호.
	 */
	@Test
	public void searchDevicerSvnMangNo() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 휴대기기 개별 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchDeviceRequest searchDeviceRequest;
		List<KeySearch> keySearchList;
		KeySearch keySearch;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 조건 설정
		keySearch = new KeySearch();
		keySearch.setKeyType(Constant.SEARCH_TYPE_SVC_MANG_NO);
		keySearch.setKeyString("7012s55953411"); // 서비스 관리번호가 없는 경우
		keySearch.setKeyString("7223989080"); // 서비스 관리번호가 존재하는 경우
		// keySearch.setKeyString("1605775712"); // 상용 서비스 관리번호

		keySearchList = new ArrayList<KeySearch>();
		keySearchList.add(keySearch);

		// 요청 필수
		searchDeviceRequest = new SearchDeviceRequest();
		searchDeviceRequest.setCommonRequest(commonRequest);
		searchDeviceRequest.setKeySearchList(keySearchList);

		SearchDeviceResponse searchDeviceResponse = this.deviceSCI.searchDevice(searchDeviceRequest);

		// 응답
		assertThat(searchDeviceResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchDeviceResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchDeviceResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchDeviceResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 이 테스트는 메시지가 성공이어야함.
		assertSame(
				searchDeviceResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.info("### 서버에서 내려받은 값 : {}", searchDeviceResponse.toString());

	}

	/**
	 * <pre>
	 * 휴대기기 대표단말 설정.
	 * 등록된 단말이 1개인 사용자의 IW1023857942220110414141217
	 * 휴대기기 01031241569를 대표단말로 설정
	 * </pre>
	 */
	@Test
	public void setMainDevice() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 휴대기기 대표단말 설정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SetMainDeviceRequest setMainDeviceRequest;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		setMainDeviceRequest = new SetMainDeviceRequest();
		setMainDeviceRequest.setCommonRequest(commonRequest);
		setMainDeviceRequest.setUserKey("IF1023002708420090928145937");
		setMainDeviceRequest.setDeviceKey("DE201402031629492570000818");

		SetMainDeviceResponse setMainDeviceResponse = this.deviceSCI.setMainDevice(setMainDeviceRequest);

		LOGGER.info("### 서버에서 내려받은 값 : {}", setMainDeviceResponse.toString());

		// 응답
		assertThat(setMainDeviceResponse, notNullValue());
		// 응답 > 공통
		assertThat(setMainDeviceResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(setMainDeviceResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(setMainDeviceResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 이 테스트는 메시지가 성공이어야함.
		assertSame(
				setMainDeviceResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답 필수 > userKey
		assertNotNull(setMainDeviceResponse.getUserKey());
		// 응답 필수 > deviceKey
		assertNotNull(setMainDeviceResponse.getDeviceKey());

	}

	/**
	 * <pre>
	 * 휴대기기 대표단말 설정.
	 * 등록된 단말이 4개인 사용자의 IF1023009623220091028112626
	 * 휴대기기 01031139010를 대표단말로 설정
	 * </pre>
	 */
	@Test
	public void setMainDeviceFour() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 휴대기기 대표단말 설정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SetMainDeviceRequest setMainDeviceRequest;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		setMainDeviceRequest = new SetMainDeviceRequest();
		setMainDeviceRequest.setCommonRequest(commonRequest);
		setMainDeviceRequest.setUserKey("IF1023002708420090928145937");
		setMainDeviceRequest.setDeviceKey("DE201402031629492570000818");

		SetMainDeviceResponse setMainDeviceResponse = this.deviceSCI.setMainDevice(setMainDeviceRequest);

		LOGGER.info("### 서버에서 내려받은 값 : {}", setMainDeviceResponse.toString());

		// 응답
		assertThat(setMainDeviceResponse, notNullValue());
		// 응답 > 공통
		assertThat(setMainDeviceResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(setMainDeviceResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(setMainDeviceResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 이 테스트는 메시지가 성공이어야함.
		assertSame(
				setMainDeviceResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 응답 필수 > userKey
		assertNotNull(setMainDeviceResponse.getUserKey());
		// 응답 필수 > deviceKey
		assertNotNull(setMainDeviceResponse.getDeviceKey());

	}

	/**
	 * 휴대기기 등록 - 신규.
	 */
	@Test
	public void testCreateDeviceYCASEI() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 휴대기기 등록");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CreateDeviceRequest createDeviceRequest = new CreateDeviceRequest();
		UserMbrDevice userMbrDevice = new UserMbrDevice();

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		createDeviceRequest.setUserKey("US201401272118331600001099"); // US201401290924483520001291 : skp_test1
		createDeviceRequest.setIsNew("Y");

		userMbrDevice.setDeviceID("04029634785-");
		userMbrDevice.setDeviceModelNo("LG-SH810");
		userMbrDevice.setDeviceTelecom("US001201");
		userMbrDevice.setDeviceNickName("w1");
		userMbrDevice.setIsPrimary("Y");
		userMbrDevice.setIsUsed("Y");
		userMbrDevice.setNativeID("IMEI152157");
		userMbrDevice.setDeviceAccount("w1@gmail.com");
		userMbrDevice.setAuthenticationDate("20140102120000");

		UserMbrDeviceDetail userMbrDeviceDetail;
		List<UserMbrDeviceDetail> userMbrDeviceDetailList;

		userMbrDeviceDetailList = new ArrayList<UserMbrDeviceDetail>();

		userMbrDeviceDetail = new UserMbrDeviceDetail();
		userMbrDeviceDetail.setExtraProfile("US011401");
		userMbrDeviceDetail.setExtraProfileValue("Y");
		userMbrDeviceDetailList.add(userMbrDeviceDetail);

		userMbrDeviceDetail = new UserMbrDeviceDetail();
		userMbrDeviceDetail.setExtraProfile("US011402");
		userMbrDeviceDetail.setExtraProfileValue("Y");
		userMbrDeviceDetailList.add(userMbrDeviceDetail);

		userMbrDeviceDetail = new UserMbrDeviceDetail();
		userMbrDeviceDetail.setExtraProfile("US011403");
		userMbrDeviceDetail.setExtraProfileValue("Y");
		userMbrDeviceDetailList.add(userMbrDeviceDetail);

		userMbrDeviceDetail = new UserMbrDeviceDetail();
		userMbrDeviceDetail.setExtraProfile("US011404");
		userMbrDeviceDetail.setExtraProfileValue("Y");
		userMbrDeviceDetailList.add(userMbrDeviceDetail);

		userMbrDeviceDetail = new UserMbrDeviceDetail();
		userMbrDeviceDetail.setExtraProfile("US011405");
		userMbrDeviceDetail.setExtraProfileValue("Y");
		userMbrDeviceDetailList.add(userMbrDeviceDetail);

		userMbrDeviceDetail = new UserMbrDeviceDetail();
		userMbrDeviceDetail.setExtraProfile("US011406");
		userMbrDeviceDetail.setExtraProfileValue("Y");
		userMbrDeviceDetailList.add(userMbrDeviceDetail);

		userMbrDeviceDetail = new UserMbrDeviceDetail();
		userMbrDeviceDetail.setExtraProfile("US011407");
		userMbrDeviceDetail.setExtraProfileValue("Y");
		userMbrDeviceDetailList.add(userMbrDeviceDetail);

		userMbrDeviceDetail = new UserMbrDeviceDetail();
		userMbrDeviceDetail.setExtraProfile("US011408");
		userMbrDeviceDetail.setExtraProfileValue("Y");
		userMbrDeviceDetailList.add(userMbrDeviceDetail);

		userMbrDeviceDetail = new UserMbrDeviceDetail();
		userMbrDeviceDetail.setExtraProfile("US011409");
		userMbrDeviceDetail.setExtraProfileValue("Y");
		userMbrDeviceDetailList.add(userMbrDeviceDetail);

		userMbrDeviceDetail = new UserMbrDeviceDetail();
		userMbrDeviceDetail.setExtraProfile("US011410");
		userMbrDeviceDetail.setExtraProfileValue("Y");
		userMbrDeviceDetailList.add(userMbrDeviceDetail);

		userMbrDeviceDetail = new UserMbrDeviceDetail();
		userMbrDeviceDetail.setExtraProfile("US011411");
		userMbrDeviceDetail.setExtraProfileValue("Y");
		userMbrDeviceDetailList.add(userMbrDeviceDetail);

		userMbrDeviceDetail = new UserMbrDeviceDetail();
		userMbrDeviceDetail.setExtraProfile("US011412");
		userMbrDeviceDetail.setExtraProfileValue("Y");
		userMbrDeviceDetailList.add(userMbrDeviceDetail);

		userMbrDeviceDetail = new UserMbrDeviceDetail();
		userMbrDeviceDetail.setExtraProfile("US011413");
		userMbrDeviceDetail.setExtraProfileValue("Y");
		userMbrDeviceDetailList.add(userMbrDeviceDetail);

		// 요청 필수
		// userMbrDevice.setUserMbrDeviceDetail(userMbrDeviceDetailList);
		createDeviceRequest.setCommonRequest(commonRequest);
		createDeviceRequest.setUserMbrDevice(userMbrDevice);

		CreateDeviceResponse createDeviceResponse = this.deviceSCI.createDevice(createDeviceRequest);

		LOGGER.info("### testCreateDevice 서버에서 내려받은 값 : {}", createDeviceResponse.toString());

		// 응답
		assertThat(createDeviceResponse, notNullValue());
		// 응답 > 공통
		assertThat(createDeviceResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(createDeviceResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(createDeviceResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 이 테스트는 메시지가 성공이어야함.
		assertSame(
				createDeviceResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));
	}

	/**
	 * 휴대기기 등록 - 신규.
	 */
	@Test
	public void testCreateDeviceYCASEII() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 휴대기기 등록");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CreateDeviceRequest createDeviceRequest = new CreateDeviceRequest();
		UserMbrDevice userMbrDevice = new UserMbrDevice();

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		createDeviceRequest.setUserKey("US201401272118331600001099"); // US201401270222082320000833
		createDeviceRequest.setIsNew("Y");

		userMbrDevice.setDeviceID("0501234569");
		userMbrDevice.setDeviceModelNo("SHW-M110S");
		userMbrDevice.setDeviceTelecom("US001201");
		userMbrDevice.setDeviceNickName("갤럭시S");
		userMbrDevice.setIsPrimary("Y");
		userMbrDevice.setIsUsed("Y");
		userMbrDevice.setAuthenticationDate("20140127");

		UserMbrDeviceDetail userMbrDeviceDetail;
		List<UserMbrDeviceDetail> userMbrDeviceDetailList;

		userMbrDeviceDetailList = new ArrayList<UserMbrDeviceDetail>();

		userMbrDeviceDetail = new UserMbrDeviceDetail();
		userMbrDeviceDetail.setExtraProfile("US123");
		userMbrDeviceDetail.setExtraProfileValue("Y");
		userMbrDeviceDetailList.add(userMbrDeviceDetail);

		// 요청 필수
		userMbrDevice.setUserMbrDeviceDetail(userMbrDeviceDetailList);
		createDeviceRequest.setCommonRequest(commonRequest);
		createDeviceRequest.setUserMbrDevice(userMbrDevice);

		CreateDeviceResponse createDeviceResponse = this.deviceSCI.createDevice(createDeviceRequest);

		LOGGER.info("### testCreateDevice 서버에서 내려받은 값 : {}", createDeviceResponse.toString());

		// 응답
		assertThat(createDeviceResponse, notNullValue());
		// 응답 > 공통
		assertThat(createDeviceResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(createDeviceResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(createDeviceResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 이 테스트는 메시지가 성공이어야함.
		assertSame(
				createDeviceResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));
	}

	/**
	 * 휴대기기 등록 - 신규.
	 */
	@Test
	public void testCreateDeviceYCASEIII() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 휴대기기 등록");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CreateDeviceRequest createDeviceRequest = new CreateDeviceRequest();
		UserMbrDevice userMbrDevice = new UserMbrDevice();

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");
		// 'IF1023030650220091223142444'; -- 01062496961
		createDeviceRequest.setUserKey("IF1023030650220091223142444");
		createDeviceRequest.setIsNew("Y");

		userMbrDevice.setDeviceID("01049896725");
		userMbrDevice.setDeviceModelNo("LG-SH810");
		userMbrDevice.setDeviceTelecom("US001201");
		userMbrDevice.setDeviceNickName("testese");
		userMbrDevice.setIsPrimary("Y");
		userMbrDevice.setIsUsed("Y");
		userMbrDevice.setAuthenticationDate("20130112120000");

		UserMbrDeviceDetail userMbrDeviceDetail;
		List<UserMbrDeviceDetail> userMbrDeviceDetailList;

		userMbrDeviceDetailList = new ArrayList<UserMbrDeviceDetail>();

		userMbrDeviceDetail = new UserMbrDeviceDetail();
		userMbrDeviceDetail.setExtraProfile("US123");
		userMbrDeviceDetail.setExtraProfileValue("Y");
		userMbrDeviceDetailList.add(userMbrDeviceDetail);

		// 요청 필수
		userMbrDevice.setUserMbrDeviceDetail(userMbrDeviceDetailList);
		createDeviceRequest.setCommonRequest(commonRequest);
		createDeviceRequest.setUserMbrDevice(userMbrDevice);

		CreateDeviceResponse createDeviceResponse = this.deviceSCI.createDevice(createDeviceRequest);

		LOGGER.info("### testCreateDevice 서버에서 내려받은 값 : {}", createDeviceResponse.toString());

		// 응답
		assertThat(createDeviceResponse, notNullValue());
		// 응답 > 공통
		assertThat(createDeviceResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(createDeviceResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(createDeviceResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 이 테스트는 메시지가 성공이어야함.
		assertSame(
				createDeviceResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));
	}

	/**
	 * 휴대기기 등록 - 신규.
	 */
	@Test
	public void testCreateDeviceYCASEIV() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 휴대기기 등록");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CreateDeviceRequest createDeviceRequest = new CreateDeviceRequest();
		UserMbrDevice userMbrDevice = new UserMbrDevice();

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		createDeviceRequest.setUserKey("IW1023791056020101204201045"); // US201401270222082320000833
		createDeviceRequest.setIsNew("Y");

		userMbrDevice.setDeviceID("01020029019"); // 01090882431 01020951368 01089050946 01092810230 01031257491
		userMbrDevice.setDeviceModelNo("LG-SH810");
		userMbrDevice.setDeviceTelecom("US001201");
		userMbrDevice.setDeviceNickName("testeses");
		userMbrDevice.setIsPrimary("Y");
		userMbrDevice.setIsUsed("Y");
		userMbrDevice.setAuthenticationDate("20130102120000");

		UserMbrDeviceDetail userMbrDeviceDetail;
		List<UserMbrDeviceDetail> userMbrDeviceDetailList;

		userMbrDeviceDetailList = new ArrayList<UserMbrDeviceDetail>();

		userMbrDeviceDetail = new UserMbrDeviceDetail();
		userMbrDeviceDetail.setExtraProfile("US123");
		userMbrDeviceDetail.setExtraProfileValue("Y");
		userMbrDeviceDetailList.add(userMbrDeviceDetail);

		// 요청 필수
		userMbrDevice.setUserMbrDeviceDetail(userMbrDeviceDetailList);
		createDeviceRequest.setCommonRequest(commonRequest);
		createDeviceRequest.setUserMbrDevice(userMbrDevice);

		CreateDeviceResponse createDeviceResponse = this.deviceSCI.createDevice(createDeviceRequest);

		LOGGER.info("### testCreateDevice 서버에서 내려받은 값 : {}", createDeviceResponse.toString());

		// 응답
		assertThat(createDeviceResponse, notNullValue());
		// 응답 > 공통
		assertThat(createDeviceResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(createDeviceResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(createDeviceResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 이 테스트는 메시지가 성공이어야함.
		assertSame(
				createDeviceResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));
	}

	/**
	 * 휴대기기 등록 - 신규.
	 */
	@Test
	public void testCreateDeviceYCASEV() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 휴대기기 등록 5. 원래 ID회원 기기다. ---> USE_YN 바꾸고  신규 등록한다");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CreateDeviceRequest createDeviceRequest = new CreateDeviceRequest();
		UserMbrDevice userMbrDevice = new UserMbrDevice();

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		createDeviceRequest.setUserKey("IF1527627020140611202715"); // US201401270222082320000833
		createDeviceRequest.setIsNew("Y");

		userMbrDevice.setDeviceID("01066786220");
		userMbrDevice.setDeviceModelNo("LG-SH810");
		userMbrDevice.setDeviceTelecom("US001201");
		userMbrDevice.setDeviceNickName("testese");
		userMbrDevice.setIsPrimary("Y");
		userMbrDevice.setIsUsed("Y");
		userMbrDevice.setAuthenticationDate("20130102120000");

		UserMbrDeviceDetail userMbrDeviceDetail;
		List<UserMbrDeviceDetail> userMbrDeviceDetailList;

		userMbrDeviceDetailList = new ArrayList<UserMbrDeviceDetail>();

		userMbrDeviceDetail = new UserMbrDeviceDetail();
		userMbrDeviceDetail.setExtraProfile("US011409");
		userMbrDeviceDetail.setExtraProfileValue("Y");
		userMbrDeviceDetailList.add(userMbrDeviceDetail);

		// 요청 필수
		userMbrDevice.setUserMbrDeviceDetail(userMbrDeviceDetailList);
		createDeviceRequest.setCommonRequest(commonRequest);
		createDeviceRequest.setUserMbrDevice(userMbrDevice);

		CreateDeviceResponse createDeviceResponse = this.deviceSCI.createDevice(createDeviceRequest);

		LOGGER.info("### testCreateDevice 서버에서 내려받은 값 : {}", createDeviceResponse.toString());

		// 응답
		assertThat(createDeviceResponse, notNullValue());
		// 응답 > 공통
		assertThat(createDeviceResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(createDeviceResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(createDeviceResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 이 테스트는 메시지가 성공이어야함.
		assertSame(
				createDeviceResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));
	}

	/**
	 * 휴대기기 등록 - 신규.
	 */
	@Test
	public void testCreateDeviceYCASEV2() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 휴대기기 등록");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CreateDeviceRequest createDeviceRequest = new CreateDeviceRequest();
		UserMbrDevice userMbrDevice = new UserMbrDevice();

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// IF1023002708420090928145937
		// IF1023228643620110104192713
		createDeviceRequest.setUserKey("US201401272118331600001099"); // US201401270222082320000833
		createDeviceRequest.setIsNew("Y");

		userMbrDevice.setDeviceID("0501234569");
		userMbrDevice.setDeviceModelNo("LG-SH810");
		userMbrDevice.setDeviceTelecom("US001201");
		userMbrDevice.setDeviceNickName("testese");
		userMbrDevice.setIsPrimary("Y");
		userMbrDevice.setIsUsed("Y");
		userMbrDevice.setAuthenticationDate("20130102120000");

		UserMbrDeviceDetail userMbrDeviceDetail;
		List<UserMbrDeviceDetail> userMbrDeviceDetailList;

		userMbrDeviceDetailList = new ArrayList<UserMbrDeviceDetail>();

		userMbrDeviceDetail = new UserMbrDeviceDetail();
		userMbrDeviceDetail.setExtraProfile("US123");
		userMbrDeviceDetail.setExtraProfileValue("Y");
		userMbrDeviceDetailList.add(userMbrDeviceDetail);

		// 요청 필수
		// userMbrDevice.setUserMbrDeviceDetail(userMbrDeviceDetailList);
		createDeviceRequest.setCommonRequest(commonRequest);
		createDeviceRequest.setUserMbrDevice(userMbrDevice);

		CreateDeviceResponse createDeviceResponse = this.deviceSCI.createDevice(createDeviceRequest);

		LOGGER.info("### testCreateDeviceYCASEV2 서버에서 내려받은 값 : {}", createDeviceResponse.toString());

		// 응답
		assertThat(createDeviceResponse, notNullValue());
		// 응답 > 공통
		assertThat(createDeviceResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(createDeviceResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(createDeviceResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 이 테스트는 메시지가 성공이어야함.
		assertSame(
				createDeviceResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));
	}

	/**
	 * 휴대기기 등록 - 수정.
	 */
	@Test
	public void testCreateDeviceN() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 휴대기기 수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CreateDeviceRequest createDeviceRequest = new CreateDeviceRequest();
		UserMbrDevice userMbrDevice = new UserMbrDevice();

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");
		commonRequest.setSystemID("S01-01002");

		createDeviceRequest.setUserKey("US201401272118331600001099");
		// createDeviceRequest.setUserKey("US201404020343556300005202");

		userMbrDevice.setDeviceKey("DE201401272118334120000552");
		// userMbrDevice.setDeviceKey("DE201404020343559390003690");
		userMbrDevice.setDeviceID("01001231122"); // 05029634784
		// userMbrDevice.setDeviceID("01066786221"); // 05029634784
		userMbrDevice.setDeviceModelNo("SHW-M110S");
		// userMbrDevice.setDeviceTelecom("US001201");
		// userMbrDevice.setDeviceNickName("갤럭시S");
		// userMbrDevice.setSvcMangNum("7012s59534");
		// userMbrDevice.setIsRecvSMS("Y");
		// userMbrDevice.setNativeID("A000s0031648sE9");
		// userMbrDevice.setDeviceAccount("skps1@gmail.com");
		// userMbrDevice.setAuthenticationDate("20140116010203");
		userMbrDevice.setChangeCaseCode("US012001");

		UserMbrDeviceDetail userMbrDeviceDetail;
		List<UserMbrDeviceDetail> userMbrDeviceDetailList;

		userMbrDeviceDetailList = new ArrayList<UserMbrDeviceDetail>();

		userMbrDeviceDetail = new UserMbrDeviceDetail();
		userMbrDeviceDetail.setExtraProfile("US011401");
		userMbrDeviceDetail.setExtraProfileValue("Y");
		userMbrDeviceDetailList.add(userMbrDeviceDetail);

		userMbrDeviceDetail = new UserMbrDeviceDetail();
		userMbrDeviceDetail.setExtraProfile("US011402");
		userMbrDeviceDetail.setExtraProfileValue("Y");
		userMbrDeviceDetailList.add(userMbrDeviceDetail);

		userMbrDeviceDetail = new UserMbrDeviceDetail();
		userMbrDeviceDetail.setExtraProfile("US011403");
		userMbrDeviceDetail.setExtraProfileValue("Y");
		userMbrDeviceDetailList.add(userMbrDeviceDetail);

		userMbrDeviceDetail = new UserMbrDeviceDetail();
		userMbrDeviceDetail.setExtraProfile("US011404");
		userMbrDeviceDetail.setExtraProfileValue("Y");
		userMbrDeviceDetailList.add(userMbrDeviceDetail);

		userMbrDeviceDetail = new UserMbrDeviceDetail();
		userMbrDeviceDetail.setExtraProfile("US011405");
		userMbrDeviceDetail.setExtraProfileValue("Y");
		userMbrDeviceDetailList.add(userMbrDeviceDetail);

		// userMbrDeviceDetail = new UserMbrDeviceDetail();
		// userMbrDeviceDetail.setExtraProfile("US011406");
		// userMbrDeviceDetail.setExtraProfileValue("Y");
		// userMbrDeviceDetailList.add(userMbrDeviceDetail);
		//
		// userMbrDeviceDetail = new UserMbrDeviceDetail();
		// userMbrDeviceDetail.setExtraProfile("US011407");
		// userMbrDeviceDetail.setExtraProfileValue("Y");
		// userMbrDeviceDetailList.add(userMbrDeviceDetail);
		//
		// userMbrDeviceDetail = new UserMbrDeviceDetail();
		// userMbrDeviceDetail.setExtraProfile("US011408");
		// userMbrDeviceDetail.setExtraProfileValue("Y");
		// userMbrDeviceDetailList.add(userMbrDeviceDetail);
		//
		// userMbrDeviceDetail = new UserMbrDeviceDetail();
		// userMbrDeviceDetail.setExtraProfile("US011409");
		// userMbrDeviceDetail.setExtraProfileValue("Y");
		// userMbrDeviceDetailList.add(userMbrDeviceDetail);

		// userMbrDeviceDetail = new UserMbrDeviceDetail();
		// userMbrDeviceDetail.setExtraProfile("US011410");
		// userMbrDeviceDetail.setExtraProfileValue("Y");
		// userMbrDeviceDetailList.add(userMbrDeviceDetail);
		//
		// userMbrDeviceDetail = new UserMbrDeviceDetail();
		// userMbrDeviceDetail.setExtraProfile("US011411");
		// userMbrDeviceDetail.setExtraProfileValue("Y");
		// userMbrDeviceDetailList.add(userMbrDeviceDetail);
		//
		// userMbrDeviceDetail = new UserMbrDeviceDetail();
		// userMbrDeviceDetail.setExtraProfile("US011412");
		// userMbrDeviceDetail.setExtraProfileValue("Y");
		// userMbrDeviceDetailList.add(userMbrDeviceDetail);
		//
		// userMbrDeviceDetail = new UserMbrDeviceDetail();
		// userMbrDeviceDetail.setExtraProfile("US011413");
		// userMbrDeviceDetail.setExtraProfileValue("Y");
		// userMbrDeviceDetailList.add(userMbrDeviceDetail);

		userMbrDevice.setUserMbrDeviceDetail(userMbrDeviceDetailList);

		// 요청 필수
		createDeviceRequest.setCommonRequest(commonRequest);

		createDeviceRequest.setIsNew("N");
		createDeviceRequest.setUserMbrDevice(userMbrDevice);

		CreateDeviceResponse createDeviceResponse = this.deviceSCI.createDevice(createDeviceRequest);

		LOGGER.info("### testCreateDevice 서버에서 내려받은 값 : {}", createDeviceResponse.toString());

		// 응답
		assertThat(createDeviceResponse, notNullValue());
		// 응답 > 공통
		assertThat(createDeviceResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(createDeviceResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(createDeviceResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 이 테스트는 메시지가 성공이어야함.
		assertSame(
				createDeviceResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));
	}

	/**
	 * 휴대기기 등록 - 수정. my deviceID
	 */
	@Test
	public void testCreateDeviceN2() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 휴대기기 수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CreateDeviceRequest createDeviceRequest = new CreateDeviceRequest();
		UserMbrDevice userMbrDevice = new UserMbrDevice();

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");
		commonRequest.setSystemID("S01-01002");

		createDeviceRequest.setUserKey("US201401272118331600001099");

		userMbrDevice.setDeviceKey("DE201401272118334120000552");

		userMbrDevice.setDeviceID("05029634792");

		userMbrDevice.setDeviceModelNo("SHW-M110S");
		userMbrDevice.setDeviceTelecom("US001201");
		userMbrDevice.setDeviceNickName("갤럭시S");
		userMbrDevice.setSvcMangNum("7012s59534");
		userMbrDevice.setIsRecvSMS("Y");
		userMbrDevice.setNativeID("A000s0031648sE9");
		userMbrDevice.setDeviceAccount("skps1@gmail.com");
		userMbrDevice.setAuthenticationDate("20140116010203");
		userMbrDevice.setChangeCaseCode("US012002");

		// 요청 필수
		createDeviceRequest.setCommonRequest(commonRequest);

		createDeviceRequest.setIsNew("N");
		createDeviceRequest.setUserMbrDevice(userMbrDevice);

		CreateDeviceResponse createDeviceResponse = this.deviceSCI.createDevice(createDeviceRequest);

		LOGGER.info("### testCreateDevice 서버에서 내려받은 값 : {}", createDeviceResponse.toString());

		// 응답
		assertThat(createDeviceResponse, notNullValue());
		// 응답 > 공통
		assertThat(createDeviceResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(createDeviceResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(createDeviceResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 이 테스트는 메시지가 성공이어야함.
		assertSame(
				createDeviceResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));
	}

	/**
	 * <pre>
	 * 휴대기기 삭제. 
	 * 성공하는 케이스
	 * 1대 삭제하기 
	 * userKey : IF1023002708420090928145937
	 * deviceKey 
	 * 01020135954
	 * </pre>
	 */
	@Test
	public void removeDeviceDeviceCountOne() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 휴대기기 등록");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		RemoveDeviceRequest removeDeviceRequest = new RemoveDeviceRequest();

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		List<String> deviceKeyList;

		deviceKeyList = new ArrayList<String>();
		deviceKeyList.add("DE201401272118334120000552");

		// 요청 필수
		removeDeviceRequest.setCommonRequest(commonRequest);
		removeDeviceRequest.setUserKey("US201401272118331600001099");
		removeDeviceRequest.setDeviceKey(deviceKeyList);

		RemoveDeviceResponse removeDeviceResponse = this.deviceSCI.removeDevice(removeDeviceRequest);

		LOGGER.info("### 서버에서 내려받은 값 : {}", removeDeviceResponse.toString());

		// 응답
		assertThat(removeDeviceResponse, notNullValue());
		// 응답 > 공통
		assertThat(removeDeviceResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(removeDeviceResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(removeDeviceResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 이 테스트는 메시지가 성공이어야함.
		assertSame(
				removeDeviceResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 결과확인
		assertTrue(removeDeviceResponse.getDelDeviceCount() == 1);

		LOGGER.info("### getDelDeviceCount : {}", removeDeviceResponse.getDelDeviceCount());

	}

	/**
	 * <pre>
	 * 휴대기기 삭제. 
	 * 성공하는 케이스
	 * 5대 삭제하기 
	 * userKey : IF1023002708420090928145937
	 * deviceKey
	 * 01020135954
	 * 01020396461
	 * 01020950985
	 * 01020950989
	 * 01020955475
	 * </pre>
	 */
	@Test
	public void removeDeviceDeviceCountFive() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 휴대기기 등록");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		RemoveDeviceRequest removeDeviceRequest = new RemoveDeviceRequest();

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		List<String> deviceKeyList;

		deviceKeyList = new ArrayList<String>();
		deviceKeyList.add("DE201403191633282610003234");
		deviceKeyList.add("DE201403191633290090003236");

		// 요청 필수
		removeDeviceRequest.setCommonRequest(commonRequest);
		removeDeviceRequest.setUserKey("US201402071242230500001968");
		removeDeviceRequest.setDeviceKey(deviceKeyList);

		RemoveDeviceResponse removeDeviceResponse = this.deviceSCI.removeDevice(removeDeviceRequest);

		LOGGER.info("### 서버에서 내려받은 값 : {}", removeDeviceResponse.toString());

		// 응답
		assertThat(removeDeviceResponse, notNullValue());
		// 응답 > 공통
		assertThat(removeDeviceResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(removeDeviceResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(removeDeviceResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 이 테스트는 메시지가 성공이어야함.
		assertSame(
				removeDeviceResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		// 결과확인
		assertTrue(removeDeviceResponse.getDelDeviceCount() == 2);

		LOGGER.info("### getDelDeviceCount : {}", removeDeviceResponse.getDelDeviceCount());

	}

	/**
	 * <pre>
	 * 변동성 대상 체크.
	 * </pre>
	 */
	@Test
	public void checkSaveNSync() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 변동성 대상 체크");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CheckSaveNSyncRequest checkSaveNSyncRequest = new CheckSaveNSyncRequest();

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		checkSaveNSyncRequest.setCommonRequest(commonRequest);
		checkSaveNSyncRequest.setDeviceID("01066786220");
		// 01048088874 - Y
		// 01066786220 - N
		// qa :

		CheckSaveNSyncResponse checkSaveNSyncResponse = this.deviceSCI.checkSaveNSync(checkSaveNSyncRequest);

		LOGGER.info("### 서버에서 내려받은 값 : {}", checkSaveNSyncResponse.toString());

		// 응답
		assertThat(checkSaveNSyncResponse, notNullValue());
		// 응답 > 공통
		assertThat(checkSaveNSyncResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(checkSaveNSyncResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(checkSaveNSyncResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 이 테스트는 메시지가 성공이어야함.
		assertSame(
				checkSaveNSyncResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

	}

	/**
	 * <pre>
	 * 회원 복구.
	 * </pre>
	 */
	@Test
	public void reviveUser() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 회원 복구");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		ReviveUserRequest reviveUserRequest = new ReviveUserRequest();

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		reviveUserRequest.setCommonRequest(commonRequest);
		reviveUserRequest.setImMbrNo("IW1023350238820110701120456");
		reviveUserRequest.setUserKey("IW1023350238820110701120455");

		ReviveUserResponse reviveUserResponse = this.deviceSCI.reviveUser(reviveUserRequest);

		// 응답
		assertThat(reviveUserResponse, notNullValue());
		// 응답 > 공통
		assertThat(reviveUserResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(reviveUserResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(reviveUserResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 이 테스트는 메시지가 성공이어야함.
		assertSame(
				reviveUserResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.info("### reviveUserResponse : {}", reviveUserResponse);

	}

	/**
	 * 휴대기기 등록 - 번호변경.
	 */
	@Test
	public void testCreateDeviceNewDeviceID() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 휴대기기 수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		CreateDeviceRequest createDeviceRequest = new CreateDeviceRequest();
		UserMbrDevice userMbrDevice = new UserMbrDevice();

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");
		commonRequest.setSystemID("S001");

		userMbrDevice.setDeviceKey("DE201402101945301220001384");
		userMbrDevice.setDeviceID("10312355631");
		userMbrDevice.setDeviceModelNo("SHW-M110S");
		userMbrDevice.setDeviceTelecom("US001202");
		userMbrDevice.setDeviceNickName("갤2럭시sS");
		userMbrDevice.setSvcMangNum("7012s55534");
		userMbrDevice.setIsRecvSMS("Y");
		userMbrDevice.setNativeID("A023s0031648sE9");
		userMbrDevice.setDeviceAccount("skps1@gmail.com");
		userMbrDevice.setAuthenticationDate("20140116010203");
		// US012012 : 번호이동
		// US012003 : 번호변경
		userMbrDevice.setChangeCaseCode("US012003");

		// 요청 필수
		createDeviceRequest.setCommonRequest(commonRequest);
		createDeviceRequest.setUserKey("US201402071236505060001967");
		createDeviceRequest.setIsNew("N");
		createDeviceRequest.setUserMbrDevice(userMbrDevice);

		CreateDeviceResponse createDeviceResponse = this.deviceSCI.createDevice(createDeviceRequest);

		LOGGER.info("### testCreateDevice 서버에서 내려받은 값 : {}", createDeviceResponse.toString());

		// 응답
		assertThat(createDeviceResponse, notNullValue());
		// 응답 > 공통
		assertThat(createDeviceResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(createDeviceResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(createDeviceResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 이 테스트는 메시지가 성공이어야함.
		assertSame(
				createDeviceResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));
	}

	/**
	 * 이전 휴대기기 개별 조회 : 전체, USER_KEY.
	 */
	@Test
	public void searchAllDevice() {

		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 이전 휴대기기 개별 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchAllDeviceRequest searchAllDeviceRequest;

		// 공통 요청
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01");

		// 요청 필수
		searchAllDeviceRequest = new SearchAllDeviceRequest();
		searchAllDeviceRequest.setCommonRequest(commonRequest);
		// 없는거 IW102158844420091030165015 01094993599
		// 있는거 US201401272118331600001099 DE201401272118334120000552
		searchAllDeviceRequest.setUserKey("IW102158844420091030165015"); // 기기 있는 회원
		searchAllDeviceRequest.setDeviceKey("01094993599"); //

		SearchAllDeviceResponse searchAllDeviceResponse = this.deviceSCI.searchAllDevice(searchAllDeviceRequest);

		// 응답
		assertThat(searchAllDeviceResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchAllDeviceResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchAllDeviceResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchAllDeviceResponse.getCommonResponse().getResultMessage(), notNullValue());
		// 응답 > 공통 > 메시지 > 이 테스트는 메시지가 성공이어야함.
		assertSame(
				searchAllDeviceResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.info("### 서버에서 내려받은 값 : {}", searchAllDeviceResponse.toString());

	}

	/**
	 * <pre>
	 * 휴대기기 소유자이력 조회.
	 * </pre>
	 */
	@Test
	public void searchDeviceOwner() {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 휴대기기 소유자이력 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchDeviceOwnerRequest searchDeviceOwnerRequest;
		SearchDeviceOwnerResponse searchDeviceOwnerResponse;
		CommonRequest commonRequest;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01"); // 태넌트 id

		// 요청 필수
		searchDeviceOwnerRequest = new SearchDeviceOwnerRequest();
		searchDeviceOwnerRequest.setCommonRequest(commonRequest);
		// searchDeviceOwnerRequest.setDeviceID("01066786222"); // main table
		searchDeviceOwnerRequest.setDeviceID("01011117773"); // his table
		searchDeviceOwnerRequest.setRegDate("20140205000000");

		// 요청
		searchDeviceOwnerResponse = this.deviceSCI.searchDeviceOwner(searchDeviceOwnerRequest);

		// 응답
		assertThat(searchDeviceOwnerResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchDeviceOwnerResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchDeviceOwnerResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchDeviceOwnerResponse.getCommonResponse().getResultMessage(), notNullValue());

		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchDeviceOwnerResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.info("### searchDeviceOwnerListResponse : {}", searchDeviceOwnerResponse.toString());
	}

	/**
	 * <pre>
	 * 등록 단말 정보 조회.
	 * </pre>
	 */
	@Test
	public void searchOrderDevice() {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 등록 단말 정보 조회");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		SearchOrderDeviceRequest searchOrderDeviceRequest;
		SearchOrderDeviceResponse searchOrderDeviceResponse;
		CommonRequest commonRequest;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01"); // 태넌트 id

		// 요청 필수
		searchOrderDeviceRequest = new SearchOrderDeviceRequest();
		searchOrderDeviceRequest.setCommonRequest(commonRequest);
		// DEVICE - IW102158844420091030165015 01094993599
		searchOrderDeviceRequest.setUserKey("IW102158844420091030165015");
		searchOrderDeviceRequest.setDeviceKey("01094993599");
		// DEVICE_WD - IW1024445190420110704164040 01040910189
		// searchOrderDeviceRequest.setUserKey("IW1024445190420110704164040");
		// searchOrderDeviceRequest.setDeviceKey("01040910189");

		// 요청
		searchOrderDeviceResponse = this.deviceSCI.searchOrderDevice(searchOrderDeviceRequest);

		// 응답
		assertThat(searchOrderDeviceResponse, notNullValue());
		// 응답 > 공통
		assertThat(searchOrderDeviceResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(searchOrderDeviceResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(searchOrderDeviceResponse.getCommonResponse().getResultMessage(), notNullValue());

		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				searchOrderDeviceResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.info("### searchDeviceOwnerListResponse : {}", searchOrderDeviceResponse.toString());
	}

	/**
	 * <pre>
	 * 단말 부가속성 등록/수정.
	 * </pre>
	 */
	@Test
	public void updateDeviceManagement() {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 단말 부가속성 등록/수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateDeviceManagementRequest updateDeviceManagementRequest;
		UpdateDeviceManagementResponse updateDeviceManagementResponse;
		CommonRequest commonRequest;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01"); // 태넌트 id

		// 요청 필수
		updateDeviceManagementRequest = new UpdateDeviceManagementRequest();
		updateDeviceManagementRequest.setCommonRequest(commonRequest);
		// DEVICE - IW102158844420091030165015 01094993599
		updateDeviceManagementRequest.setUserKey("IW102158844420091030165015");
		updateDeviceManagementRequest.setDeviceKey("01094993599");
		// DEVICE_WD - IW1024445190420110704164040 01040910189
		// updateDeviceManagementRequest.setUserKey("IW1024445190420110704164040");
		// updateDeviceManagementRequest.setDeviceKey("01040910189");

		// 요청
		updateDeviceManagementResponse = this.deviceSCI.updateDeviceManagement(updateDeviceManagementRequest);

		// 응답
		assertThat(updateDeviceManagementResponse, notNullValue());
		// 응답 > 공통
		assertThat(updateDeviceManagementResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updateDeviceManagementResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updateDeviceManagementResponse.getCommonResponse().getResultMessage(), notNullValue());

		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				updateDeviceManagementResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.info("### updateDeviceManagementResponse : {}", updateDeviceManagementResponse.toString());
	}

	/**
	 * <pre>
	 * 단말 부가속성 등록/수정.
	 * </pre>
	 */
	@Test
	public void searchDeviceManagement() {
		LOGGER.debug("\n\n\n\n\n");
		LOGGER.debug("==================================================================================");
		LOGGER.debug("테스트 시작 - 단말 부가속성 등록/수정");
		LOGGER.debug("==================================================================================\n\n\n\n\n");

		UpdateDeviceManagementRequest updateDeviceManagementRequest;
		UpdateDeviceManagementResponse updateDeviceManagementResponse;
		CommonRequest commonRequest;

		// 공통 요청
		commonRequest = new CommonRequest();
		commonRequest.setTenantID("S01"); // 태넌트 id
		commonRequest.setSystemID("S01-01002");
		// 요청 필수
		updateDeviceManagementRequest = new UpdateDeviceManagementRequest();
		updateDeviceManagementRequest.setCommonRequest(commonRequest);
		// US201504231556532370023413 DE2015042315565328215048554
		updateDeviceManagementRequest.setUserKey("US201504231556532370023413");
		updateDeviceManagementRequest.setDeviceKey("DE2015042315565328215048554");
		UserMbrDeviceDetail userMbrDeviceDetail = new UserMbrDeviceDetail();
		userMbrDeviceDetail.setExtraProfile("US011415");
		userMbrDeviceDetail.setExtraProfileValue("Y");
		userMbrDeviceDetail.setUserKey("US201504231556532370023413");
		userMbrDeviceDetail.setDeviceKey("DE2015042315565328215048554");
		userMbrDeviceDetail.setUpdateDate("20150513155653");

		List<UserMbrDeviceDetail> userMbrDeviceDetails = new ArrayList<UserMbrDeviceDetail>();
		userMbrDeviceDetails.add(userMbrDeviceDetail);

		updateDeviceManagementRequest.setUserMbrDeviceDetail(userMbrDeviceDetails);
		// 요청
		updateDeviceManagementResponse = this.deviceSCI.updateDeviceManagement(updateDeviceManagementRequest);

		// 응답
		assertThat(updateDeviceManagementResponse, notNullValue());
		// 응답 > 공통
		assertThat(updateDeviceManagementResponse.getCommonResponse(), notNullValue());
		// 응답 > 공통 > 코드
		assertThat(updateDeviceManagementResponse.getCommonResponse().getResultCode(), notNullValue());
		// 응답 > 공통 > 메시지
		assertThat(updateDeviceManagementResponse.getCommonResponse().getResultMessage(), notNullValue());

		// 응답 > 공통 > 메시지 > 성공
		assertSame(
				updateDeviceManagementResponse.getCommonResponse().getResultCode(),
				this.messageSourceAccessor.getMessage("response.ResultCode.success", null, "0000",
						LocaleContextHolder.getLocale()));

		LOGGER.info("### updateDeviceManagementResponse : {}", updateDeviceManagementResponse.toString());
	}
}
