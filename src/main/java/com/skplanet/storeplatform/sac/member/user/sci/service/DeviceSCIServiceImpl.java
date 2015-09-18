/**
 * 
 */
package com.skplanet.storeplatform.sac.member.user.sci.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAllDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchAllDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchChangedDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchChangedDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchOrderDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchOrderDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateDeviceManagementRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateDeviceManagementResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDeviceDetail;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchOrderDeviceIdSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchOrderDeviceIdSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UpdateLimitChargeYnSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UpdateLimitChargeYnSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.util.DeviceUtil;
import com.skplanet.storeplatform.sac.member.common.vo.Device;

/**
 * 단말 정보 조회 내부메소드 호출 ServiceImpl.
 * 
 * Updated on : 2014. 5. 20. Updated by : 김다슬, 인크로스.
 */
@Service
public class DeviceSCIServiceImpl implements DeviceSCIService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceSCIServiceImpl.class);

	@Autowired
	private MemberCommonComponent commService; // 공통 컴포넌트

	@Autowired
	private UserSCI userSCI; // 사용자 정보 SC

	@Autowired
	private DeviceSCI deviceSCI; // 휴대기기 정보 SC

	/**
	 * <pre>
	 * 단말 정보 조회.
	 * </pre>
	 * 
	 * @param requestHeader
	 * 
	 * @param keyType
	 *            (KEY_TYPE_INSD_DEVICE_ID)
	 * @param keyString
	 * @param userKey
	 * 
	 * @return DeviceInfo
	 */
	@Override
	public DeviceInfo srhDevice(SacRequestHeader requestHeader, String keyType, String keyString, String userKey) {

		/* 헤더 정보 셋팅 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		SearchAllDeviceRequest searchAllDeviceRequest = new SearchAllDeviceRequest();
		searchAllDeviceRequest.setCommonRequest(commonRequest);

		searchAllDeviceRequest.setUserKey(userKey);
		searchAllDeviceRequest.setDeviceKey(keyString); // deviceKey

		DeviceInfo deviceInfo = null;

		try {

			SearchAllDeviceResponse schAllDeviceRes = this.deviceSCI.searchAllDevice(searchAllDeviceRequest);

			deviceInfo = new DeviceInfo();
			deviceInfo = DeviceUtil.getConverterDeviceInfo(schAllDeviceRes.getUserMbrDevice());
			deviceInfo.setUserId(schAllDeviceRes.getUserID());
			deviceInfo.setUserKey(schAllDeviceRes.getUserKey());
			deviceInfo.setIsAuthenticated(schAllDeviceRes.getUserMbrDevice().getIsUsed());

			/* 폰정보 DB 조회하여 추가 정보 반영 */
			Device device = this.commService.getPhoneInfo(deviceInfo.getDeviceModelNo());
			if (device != null) {
				deviceInfo.setMakeComp(device.getMnftCompCd());
				deviceInfo.setModelNm(device.getModelNm());
				deviceInfo.setVmType(device.getVmTypeCd());
			}

		} catch (StorePlatformException ex) {
			if (!StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)) {
				throw ex;
			}
		}

		return deviceInfo;
	}

	/**
	 * <pre>
	 * 기기변경 이력 조회.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            SacRequestHeader
	 * @param historyRequest
	 *            ChangedDeviceHistoryReq
	 * @return ChangedDeviceHistoryRes
	 */
	@Override
	public ChangedDeviceHistorySacRes srhChangedDeviceHistory(SacRequestHeader sacHeader,
			ChangedDeviceHistorySacReq request) {
		// 공통 파라미터 셋팅
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		String errorValue = "userKey 또는 ";

		// 필수파라미터 미입력.
		if (request.getDeviceId() == null || "".equals(request.getDeviceId())) {
			errorValue = StringUtil.capitalize(errorValue + "deviceKey");
		} else {
			errorValue = StringUtil.capitalize(errorValue + "deviceId");
		}

		// SC 회원 기기변경이력 조회 기능 호출.
		SearchChangedDeviceRequest searchChangedDeviceRequest = new SearchChangedDeviceRequest();
		searchChangedDeviceRequest.setUserKey(request.getUserKey());
		searchChangedDeviceRequest.setDeviceKey(request.getDeviceKey());
		searchChangedDeviceRequest.setDeviceID(request.getDeviceId());
		searchChangedDeviceRequest.setCommonRequest(commonRequest);

		LOGGER.debug("[DeviceSCIController.searchChangedDeviceHistory] SC Request userSCI.searchChangedDevice : {}",
				searchChangedDeviceRequest);
		SearchChangedDeviceResponse searchChangedDeviceResponse = this.userSCI
				.searchChangedDevice(searchChangedDeviceRequest);

		ChangedDeviceHistorySacRes changedDeviceHistorySacRes = new ChangedDeviceHistorySacRes();

		if (searchChangedDeviceResponse != null && searchChangedDeviceResponse.getChangedDeviceLog() != null
				&& StringUtils.isNotBlank(searchChangedDeviceResponse.getChangedDeviceLog().getDeviceKey())) {
			LOGGER.debug(
					"[DeviceSCIController.searchChangedDeviceHistory] SC Response userSCI.searchChangedDevice : {}",
					searchChangedDeviceResponse.getChangedDeviceLog());
			changedDeviceHistorySacRes.setDeviceKey(searchChangedDeviceResponse.getChangedDeviceLog().getDeviceKey());
			changedDeviceHistorySacRes.setIsChanged(searchChangedDeviceResponse.getChangedDeviceLog().getIsChanged());
		} else {
			// Response DeviceKey값이 null일 경우, UserKey or DeviceId or DeviceKey 불량
			throw new StorePlatformException("SAC_MEM_0003", errorValue, "오류");
		}
		return changedDeviceHistorySacRes;

	}

	/**
	 * <pre>
	 * 2.1.10.	등록된 단말 정보 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            SearchOrderDeviceIdSacReq
	 * @return SearchOrderDeviceIdSacRes
	 */
	@Override
	public SearchOrderDeviceIdSacRes searchOrderDeviceId(SacRequestHeader header, SearchOrderDeviceIdSacReq req) {
		// 공통 파라미터 셋팅
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(header.getTenantHeader().getSystemId());
		commonRequest.setTenantID(header.getTenantHeader().getTenantId());

		// SC 회원 기기변경이력 조회 기능 호출.
		SearchOrderDeviceRequest searchOrderDeviceRequest = new SearchOrderDeviceRequest();
		searchOrderDeviceRequest.setUserKey(req.getUserKey());
		searchOrderDeviceRequest.setDeviceKey(req.getDeviceKey());
		searchOrderDeviceRequest.setCommonRequest(commonRequest);

		LOGGER.debug("[DeviceSCIController.searchOrderDeviceId] SC Request deviceSCI.searchOrderDevice : {}",
				searchOrderDeviceRequest);
		SearchOrderDeviceResponse searchOrderDeviceResponse = this.deviceSCI
				.searchOrderDevice(searchOrderDeviceRequest);

		SearchOrderDeviceIdSacRes res = new SearchOrderDeviceIdSacRes();

		res.setAuthYn(searchOrderDeviceResponse.getAuthYn());
		res.setDeviceId(searchOrderDeviceResponse.getDeviceId());
		res.setDeviceTelecom(searchOrderDeviceResponse.getDeviceTelecom());
		res.setTableName(searchOrderDeviceResponse.getTableName());
		res.setTenantId(searchOrderDeviceResponse.getTenantID());

		return res;
	}

	/**
	 * <pre>
	 * 2.1.13. 회원 한도 요금제 사용여부 업데이트.
	 * </pre>
	 * 
	 * @param request
	 *            UpdateLimitChargeYnSacReq
	 * @return UpdateLimitChargeYnSacRes
	 */
	@Override
	public UpdateLimitChargeYnSacRes updateLimitChargeYn(SacRequestHeader header, UpdateLimitChargeYnSacReq req) {
		// 공통 파라미터 셋팅
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(header.getTenantHeader().getSystemId());
		commonRequest.setTenantID(header.getTenantHeader().getTenantId());
		LOGGER.debug("[DeviceSCIController.updateLimitChargeYn] SC Request deviceSCI.searchOrderDevice : {}", req);

		SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();
		searchDeviceRequest.setCommonRequest(commonRequest);
		searchDeviceRequest.setUserKey(req.getUserKey());

		/**
		 * 검색 조건 setting
		 */
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySchUserKey = new KeySearch();
		keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_DEVICE_ID);
		keySchUserKey.setKeyString(req.getDeviceKey());
		keySearchList.add(keySchUserKey);
		searchDeviceRequest.setKeySearchList(keySearchList);

		SearchDeviceResponse searchDeviceResponse = null;

		try {
			searchDeviceResponse = this.deviceSCI.searchDevice(searchDeviceRequest);

			UpdateDeviceManagementRequest updateDeviceManagementRequest = new UpdateDeviceManagementRequest();
			updateDeviceManagementRequest.setCommonRequest(commonRequest);
			updateDeviceManagementRequest.setUserKey(req.getUserKey());
			updateDeviceManagementRequest.setDeviceKey(req.getDeviceKey());
			updateDeviceManagementRequest.setIsDormant(searchDeviceResponse.getUserMbrDevice().getIsDormant());

			// 단말 부가속성 (한도요금제 저장값) 설정
			UserMbrDeviceDetail userMbrDeviceDetail = new UserMbrDeviceDetail();
			userMbrDeviceDetail.setDeviceKey(req.getDeviceKey());
			userMbrDeviceDetail.setExtraProfile(MemberConstants.DEVICE_EXTRA_LIMIT_CHARGE_YN);
			userMbrDeviceDetail.setExtraProfileValue(req.getLimitChargeYn());
			userMbrDeviceDetail.setRegDate(req.getSearchDt());

			List<UserMbrDeviceDetail> userMbrDeviceDetails = searchDeviceResponse.getUserMbrDevice()
					.getUserMbrDeviceDetail();
			if (userMbrDeviceDetails != null) {
				for (UserMbrDeviceDetail userMbrDeviceDetail2 : userMbrDeviceDetails) {

					// 단말 부가속성중 한도요금제 속성 여부
					if (StringUtils.equals(MemberConstants.DEVICE_EXTRA_LIMIT_CHARGE_YN,
							userMbrDeviceDetail2.getExtraProfile())) {
						userMbrDeviceDetail.setUpdateDate(req.getSearchDt());
						userMbrDeviceDetail.setRegDate(null);
						break;
					}
				}
			}

			List<UserMbrDeviceDetail> deviceDetails = new ArrayList<UserMbrDeviceDetail>();
			deviceDetails.add(userMbrDeviceDetail);
			updateDeviceManagementRequest.setUserMbrDeviceDetail(deviceDetails);
			updateDeviceManagementRequest.setIsDormant(searchDeviceResponse.getUserMbrDevice().getIsDormant());
			UpdateDeviceManagementResponse updateDeviceManagementResponse = this.deviceSCI
					.updateDeviceManagement(updateDeviceManagementRequest);

			// TLOG 회원 한도 요금제 사용여부 업데이트 추가 (성공)
			final String tlogUserKey = req.getUserKey();
			final String tlogDeviceKey = req.getDeviceKey();
			final String tlogDeviceId = searchDeviceResponse != null ? searchDeviceResponse.getUserMbrDevice()
					.getDeviceID() : null;
			final String tlogTingYn = req.getLimitChargeYn();

			new TLogUtil().log(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.log_id("TL_SAC_MEM_0015").insd_usermbr_no(tlogUserKey).insd_device_id(tlogDeviceKey)
							.device_id(tlogDeviceId).result_code("Y").result_message("팅요금제 사용여부 업데이트 성공(LocalSCI)")
							.ting_yn(tlogTingYn);
				}
			});

			UpdateLimitChargeYnSacRes res = new UpdateLimitChargeYnSacRes();
			res.setDeviceKey(updateDeviceManagementResponse.getDeviceKey());
			res.setUserKey(updateDeviceManagementResponse.getUserKey());
			return res;
		} catch (StorePlatformException ex) {
			// TLOG 회원 한도 요금제 사용여부 업데이트 추가 (실패)
			final String tlogUserKey = req.getUserKey();
			final String tlogDeviceKey = req.getDeviceKey();
			final String tlogDeviceId = searchDeviceResponse != null ? searchDeviceResponse.getUserMbrDevice()
					.getDeviceID() : null;
			final String tlogTingYn = req.getLimitChargeYn();

			new TLogUtil().log(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.log_id("TL_SAC_MEM_0015").insd_usermbr_no(tlogUserKey).insd_device_id(tlogDeviceKey)
							.device_id(tlogDeviceId).result_code("N").result_message("팅요금제 사용여부 업데이트 실패(LocalSCI)")
							.ting_yn(tlogTingYn);
					;
				}
			});

			throw ex;
		}

	}

}
