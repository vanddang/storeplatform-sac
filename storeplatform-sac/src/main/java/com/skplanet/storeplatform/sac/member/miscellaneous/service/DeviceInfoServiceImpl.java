package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import com.skplanet.storeplatform.external.client.uaps.sci.UapsSCI;
import com.skplanet.storeplatform.external.client.uaps.vo.UafmapEcRes;
import com.skplanet.storeplatform.external.client.uaps.vo.UapsEcReq;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.*;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.vo.Device;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Device info 기능 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 * Updated on : 2015. 12. 28. Updated by : 임근대, SKP. - Micellaneos 클래스에서 Device info 관련 기능 클래스 분리
 */
@Service
public class DeviceInfoServiceImpl implements DeviceInfoService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceInfoServiceImpl.class);

	@Autowired
	private MemberCommonComponent commonComponent; // 회원 공통기능 컴포넌트
	@Autowired
	private UserSCI userSCI; // 회원 Component 사용자 기능 Interface.
	@Autowired
	private DeviceSCI deviceSCI; // 회원 Component 휴대기기 기능 Interface.
	@Autowired
	private UapsSCI uapsSCI; // UAPS 연동 Interface.
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDao;

	/**
	 * <pre>
	 * OPMD 모회선 번호 조회.
	 * </pre>
	 * 
	 * @param req
	 *            GetOpmdReq
	 * @return GetOpmdRes
	 */
	@Override
	public GetOpmdRes getOpmd(GetOpmdReq req) {
		GetOpmdRes res = new GetOpmdRes();
		res.setMsisdn(this.commonComponent.getOpmdMdnInfo(req.getMsisdn()));
		return res;
	}

	/**
	 * <pre>
	 * UA 코드 정보 조회.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param req
	 *            GetUaCodeReq
	 * @return GetUaCodeRes
	 */
	@Override
	public GetUaCodeRes getUaCode(SacRequestHeader requestHeader, GetUaCodeReq req) {
		String deviceModelNo = req.getDeviceModelNo();
		String msisdn = req.getMsisdn();
		String userKey = null;

		/* 헤더 정보 셋팅 */
		CommonRequest commonRequest = this.commonComponent.getSCCommonRequest(requestHeader);

		GetUaCodeRes response = new GetUaCodeRes();
		/* 파라미터로 MSISDN만 넘어온 경우 */
		if (StringUtils.isNotBlank(msisdn) && StringUtils.isBlank(deviceModelNo)) {
			SearchUserRequest searchUserRequest = new SearchUserRequest();
			SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();

			/* SC 공통헤더 Request 파라미터 셋팅 */
			searchUserRequest.setCommonRequest(commonRequest);
			searchDeviceRequest.setCommonRequest(commonRequest);

			List<KeySearch> keySearchList = new ArrayList<KeySearch>();
			KeySearch keySearch = new KeySearch();
			keySearch.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
			keySearch.setKeyString(msisdn);
			keySearchList.add(keySearch);
			searchUserRequest.setKeySearchList(keySearchList);

			/* deviceId(msisdn)로 userKey 조회 - SC 회원 "회원 기본 정보 조회" */
			SearchUserResponse searchUserResponse = this.userSCI.searchUser(searchUserRequest);

			if (searchUserResponse == null || searchUserResponse.getUserMbr() == null) {
				throw new StorePlatformException("SAC_MEM_0003", "deviceId", msisdn);
			} else {
				userKey = searchUserResponse.getUserMbr().getUserKey();
				LOGGER.debug("[DeviceInfoService.getUaCode] SC Response : {}", searchUserResponse.getUserMbr());
			}

			searchDeviceRequest.setKeySearchList(keySearchList);
			searchDeviceRequest.setUserKey(userKey);

			/* deviceId와 userKey로 deviceModelNo 조회 */
			SearchDeviceResponse searchDeviceResult = this.deviceSCI.searchDevice(searchDeviceRequest);

			/* deviceModelNo 조회 결과 확인 */
			if (searchDeviceResult != null
					&& StringUtils.isNotBlank(searchDeviceResult.getUserMbrDevice().getDeviceModelNo())) {
				deviceModelNo = searchDeviceResult.getUserMbrDevice().getDeviceModelNo();
			} else {
				throw new StorePlatformException("SAC_MEM_3402", "msisdn", msisdn);
			}
		}

		if (StringUtils.isNotBlank(deviceModelNo)) { // deviceModelNo 가 파라미터로 들어온 경우
			// DB 접속(TB_CM_DEVICE) - UaCode 조회
			String uaCode = this.commonDao.queryForObject("Miscellaneous.getUaCode", deviceModelNo, String.class);
			if (StringUtils.isNotBlank(uaCode)) {
				response.setUaCd(uaCode);
			} else {
				throw new StorePlatformException("SAC_MEM_3401", "deviceModelNo", deviceModelNo);
			}
		}
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
	@Override
	public GetModelCodeRes getModelCode(GetModelCodeReq request) {
		GetModelCodeRes response = new GetModelCodeRes();
		String uaCd = request.getUaCd();
		String msisdn = request.getMsisdn();
		String errorKey = "uaCd";
		String errorValue = uaCd;

		if (StringUtils.isNotBlank(msisdn) && StringUtils.isBlank(uaCd)) {
			errorKey = "msisdn";
			errorValue = msisdn;
			UapsEcReq uapsReq = new UapsEcReq();
			uapsReq.setDeviceId(request.getMsisdn());
			uapsReq.setType("mdn");

			UafmapEcRes uapsRes = this.uapsSCI.getDeviceInfo(uapsReq);
			if (uapsRes != null) {
				if (StringUtils.isNotBlank(uapsRes.getDeviceModel())) {
					uaCd = uapsRes.getDeviceModel();
				} else {
					// else uaCd가 존재하지 않을 경우 9999로 셋팅. - 비정상 데이터.
					LOGGER.info("## uaCd is not exists, Set uaCd=\"9999\"");
					uaCd = "9999";
				}
			}
		}

		// uaCd로 PhoneInfo 테이블 조회.
		Device device = this.commonComponent.getPhoneInfoByUacd(uaCd);

		if (device != null && StringUtils.isNotBlank(device.getDeviceModelCd())) {
			response.setDeviceModelNo(device.getDeviceModelCd());
		} else {
			throw new StorePlatformException("SAC_MEM_3402", errorKey, errorValue);
		}
		LOGGER.debug("## Response : {}", response);

		return response;
	}

}
