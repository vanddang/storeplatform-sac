package com.skplanet.storeplatform.sac.member.user.service;

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
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
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceSetInfoRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceSetInfoResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDeviceSet;
import com.skplanet.storeplatform.sac.client.member.vo.user.CheckDevicePinSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CheckDevicePinSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDevicePinSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDevicePinSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyDevicePinSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyDevicePinSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyDeviceSetInfoSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyDeviceSetInfoSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchDevicePinSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchDevicePinSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchDeviceSetInfoSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchDeviceSetInfoSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.miscellaneous.vo.ServiceAuth;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 휴대기기 설정 정보 관련 ServiceImpl
 * 
 * Updated on : 2015. 10. 29. Updated by : 최진호, 보고지티.
 */
@Service
public class DeviceSetServiceImpl implements DeviceSetService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceSetServiceImpl.class);

	@Autowired
	private DeviceSetSCI deviceSetSCI;

	@Autowired
	private DeviceSCI deviceSCI;

	/**
	 * 회원 공통 Component.
	 */
	@Autowired
	private MemberCommonComponent component;

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDao;

	/**
	 * <pre>
	 * 2.1.44. 휴대기기 PIN 번호 등록.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            CreateDevicePinSacReq
	 * @return CreateDevicePinSacRes
	 */
	@Override
	public CreateDevicePinSacRes regDevicePin(SacRequestHeader header, CreateDevicePinSacReq req) {
		CommonRequest commonRequest = this.component.getSCCommonRequest(header);
		CreateDevicePinResponse createDevicePinResponse = null;
		SearchDeviceResponse schDeviceRes = null;
		final String tlogUserKey = req.getUserKey();
		final String tlogDeviceKey = req.getDeviceKey();

		try {
			List<KeySearch> keySearchList = new ArrayList<KeySearch>();
			KeySearch keySchUserKey = null;
			keySchUserKey = new KeySearch();
			keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_DEVICE_ID);
			keySchUserKey.setKeyString(req.getDeviceKey());
			keySearchList.add(keySchUserKey);

			SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();
			searchDeviceRequest.setCommonRequest(commonRequest);
			searchDeviceRequest.setUserKey(req.getUserKey());
			searchDeviceRequest.setKeySearchList(keySearchList);
			schDeviceRes = this.deviceSCI.searchDevice(searchDeviceRequest);

			final String tlogDeviceId = schDeviceRes.getUserMbrDevice().getDeviceID();
			new TLogUtil().log(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.log_id("TL_SAC_MEM_0007").result_code("Y").result_message("보안비밀번호 설정 요청")
							.insd_usermbr_no(tlogUserKey).insd_device_id(tlogDeviceKey).device_id(tlogDeviceId);
				}
			});

			// TL_SAC_MEM_0008 tlog set
			new TLogUtil().set(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.insd_usermbr_no(tlogUserKey).insd_device_id(tlogDeviceKey).device_id(tlogDeviceId);
				}
			});

			/**
			 * 검색 조건 setting
			 */
			keySearchList = new ArrayList<KeySearch>();
			keySchUserKey = new KeySearch();
			keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_DEVICE_ID);
			keySchUserKey.setKeyString(req.getDeviceKey());
			keySearchList.add(keySchUserKey);
			keySchUserKey = new KeySearch();
			keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
			keySchUserKey.setKeyString(req.getUserKey());
			keySearchList.add(keySchUserKey);

			CreateDevicePinRequest createDevicePinRequest = new CreateDevicePinRequest();
			createDevicePinRequest.setCommonRequest(commonRequest);
			createDevicePinRequest.setPinNo(req.getPinNo());
			createDevicePinRequest.setKeySearchList(keySearchList);
			createDevicePinResponse = this.deviceSetSCI.createDevicePin(createDevicePinRequest);

			// TL_SAC_MEM_0008 tlog set
			new TLogUtil().log(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.result_code("보안비밀번호 설정 여부").result_message("보안비밀번호 설정 성공");
				}
			});

		} catch (StorePlatformException e) {
			final String tlogDeviceId = schDeviceRes != null ? schDeviceRes.getUserMbrDevice().getDeviceID() : null;
			if (schDeviceRes == null) {
				new TLogUtil().log(new ShuttleSetter() {
					@Override
					public void customize(TLogSentinelShuttle shuttle) {
						shuttle.log_id("TL_SAC_MEM_0007").result_code("Y").result_message("보안비밀번호 설정 요청")
								.insd_usermbr_no(tlogUserKey).insd_device_id(tlogDeviceKey).device_id(tlogDeviceId);
					}
				});
			}

			// TL_SAC_MEM_0008 tlog set
			new TLogUtil().log(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.result_code("보안비밀번호 설정 여부").result_message("보안비밀번호 설정 실패");
				}
			});

			if (StringUtils.equals(MemberConstants.SC_ERROR_DUPLICATED_DEVICE_ID, e.getErrorInfo().getCode())) {
				throw new StorePlatformException("SAC_MEM_2012", "userKey : " + req.getUserKey() + ", deviceKey :"
						+ req.getDeviceKey());
			} else {
				throw e;
			}
		}

		CreateDevicePinSacRes res = new CreateDevicePinSacRes();
		res.setDeviceId(createDevicePinResponse.getDeviceId());
		res.setDeviceKey(createDevicePinResponse.getDeviceKey());
		res.setUserKey(createDevicePinResponse.getUserKey());

		return res;
	}

	/**
	 * <pre>
	 * 2.1.45. 휴대기기 PIN 번호 수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ModifyDevicePinSacReq
	 * @return ModifyDevicePinSacRes
	 */
	@Override
	public ModifyDevicePinSacRes modDevicePin(SacRequestHeader header, ModifyDevicePinSacReq req) {
		ModifyDevicePinRequest modifyDevicePinRequest = new ModifyDevicePinRequest();

		CommonRequest commonRequest = this.component.getSCCommonRequest(header);
		modifyDevicePinRequest.setCommonRequest(commonRequest);

		/**
		 * 검색 조건 setting
		 */
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySchUserKey = null;
		keySchUserKey = new KeySearch();
		keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_DEVICE_ID);
		keySchUserKey.setKeyString(req.getDeviceKey());
		keySearchList.add(keySchUserKey);
		keySchUserKey = new KeySearch();
		keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
		keySchUserKey.setKeyString(req.getUserKey());
		keySearchList.add(keySchUserKey);

		modifyDevicePinRequest.setPinNo(req.getPinNo());
		modifyDevicePinRequest.setNewPinNo(req.getNewPinNo());
		modifyDevicePinRequest.setKeySearchList(keySearchList);

		// SC Call
		ModifyDevicePinResponse modifyDevicePinResponse = null;
		try {
			modifyDevicePinResponse = this.deviceSetSCI.modifyDevicePin(modifyDevicePinRequest);
		} catch (StorePlatformException e) {
			if (StringUtils.equals(MemberConstants.SC_ERROR_EDIT_INPUT_ITEM_NOT_FOUND, e.getErrorInfo().getCode())) {
				throw new StorePlatformException("SAC_MEM_1513");
			} else {
				throw e;
			}
		} finally {
			keySearchList = new ArrayList<KeySearch>();
			keySchUserKey = new KeySearch();
			keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_DEVICE_ID);
			keySchUserKey.setKeyString(req.getDeviceKey());
			keySearchList.add(keySchUserKey);

			SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();
			searchDeviceRequest.setCommonRequest(commonRequest);
			searchDeviceRequest.setUserKey(req.getUserKey());
			searchDeviceRequest.setKeySearchList(keySearchList);
			SearchDeviceResponse schDeviceRes = null;

			try {
				schDeviceRes = this.deviceSCI.searchDevice(searchDeviceRequest);
			} catch (StorePlatformException ex) {
				schDeviceRes = null;
			}

			final String tlogUserKey = req.getUserKey();
			final String tlogDeviceKey = req.getDeviceKey();
			final String tlogDeviceId = schDeviceRes != null ? schDeviceRes.getUserMbrDevice().getDeviceID() : null;

			// TLog (보안비밀번호 재설정 요청)
			new TLogUtil().log(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.log_id("TL_SAC_MEM_0011").result_code("Y").result_message("보안비밀번호 재설정 요청")
							.insd_usermbr_no(tlogUserKey).insd_device_id(tlogDeviceKey).device_id(tlogDeviceId);
				}
			});

			// 성공 실패
			final String resultMessage = (modifyDevicePinResponse != null) ? "보안비밀번호 재설정 성공" : "보안비밀번호 재설정 실패";

			// TLog (보안비밀번호 재설정 완료 여부)
			new TLogUtil().log(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.log_id("TL_SAC_MEM_0012").result_code("보안비밀번호 설정 여부").result_message(resultMessage)
							.insd_usermbr_no(tlogUserKey).insd_device_id(tlogDeviceKey).device_id(tlogDeviceId);
				}
			});
		}

		ModifyDevicePinSacRes res = new ModifyDevicePinSacRes();
		if (modifyDevicePinResponse != null) {
			res.setDeviceId(modifyDevicePinResponse.getDeviceId());
			res.setDeviceKey(modifyDevicePinResponse.getDeviceKey());
			res.setUserKey(modifyDevicePinResponse.getUserKey());
		}

		return res;
	}

	/**
	 * <pre>
	 * 2.1.46. 휴대기기 PIN 번호 찾기.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            SearchDevicePinSacReq
	 * @return SearchDevicePinSacRes
	 */
	@Override
	public SearchDevicePinSacRes searchDevicePin(SacRequestHeader header, SearchDevicePinSacReq req) {
		SearchDevicePinRequest searchDevicePinRequest = new SearchDevicePinRequest();

		CommonRequest commonRequest = this.component.getSCCommonRequest(header);
		searchDevicePinRequest.setCommonRequest(commonRequest);

		/**
		 * 검색 조건 setting
		 */
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySchUserKey = null;
		keySchUserKey = new KeySearch();
		keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_DEVICE_ID);
		keySchUserKey.setKeyString(req.getDeviceKey());
		keySearchList.add(keySchUserKey);
		keySchUserKey = new KeySearch();
		keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
		keySchUserKey.setKeyString(req.getUserKey());
		keySearchList.add(keySchUserKey);

		searchDevicePinRequest.setKeySearchList(keySearchList);
		SearchDevicePinResponse searchDevicePinResponse = null;

		// SC Call
		try {
			searchDevicePinResponse = this.deviceSetSCI.searchDevicePin(searchDevicePinRequest);
		} catch (StorePlatformException ex) {
			throw ex;
		} finally {
			keySearchList = new ArrayList<KeySearch>();
			keySchUserKey = new KeySearch();
			keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_DEVICE_ID);
			keySchUserKey.setKeyString(req.getDeviceKey());
			keySearchList.add(keySchUserKey);

			SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();
			searchDeviceRequest.setCommonRequest(commonRequest);
			searchDeviceRequest.setUserKey(req.getUserKey());
			searchDeviceRequest.setKeySearchList(keySearchList);
			SearchDeviceResponse schDeviceRes = null;

			try {
				schDeviceRes = this.deviceSCI.searchDevice(searchDeviceRequest);
			} catch (StorePlatformException e) {
				schDeviceRes = null;
			}

			final String tlogUserKey = req.getUserKey();
			final String tlogDeviceKey = req.getDeviceKey();
			final String tlogDeviceId = schDeviceRes != null ? schDeviceRes.getUserMbrDevice().getDeviceID() : null;

			// TLog (보안비밀번호 초기화 요청)
			new TLogUtil().log(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.log_id("TL_SAC_MEM_0013").result_code("Y").result_message("보안비밀번호 초기화 요청")
							.insd_usermbr_no(tlogUserKey).insd_device_id(tlogDeviceKey).device_id(tlogDeviceId);
				}
			});

			// 성공 실패
			final String resultMessage = (searchDevicePinResponse != null) ? "보안비밀번호 초기화 성공" : "보안비밀번호 초기화 실패";

			// TLog (보안비밀번호 초기화 여부)
			new TLogUtil().log(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.log_id("TL_SAC_MEM_0014").result_code("보안비밀번호 초기화 여부").result_message(resultMessage)
							.insd_usermbr_no(tlogUserKey).insd_device_id(tlogDeviceKey).device_id(tlogDeviceId);
				}
			});
		}

		SearchDevicePinSacRes res = new SearchDevicePinSacRes();
		if (searchDevicePinResponse != null) {
			res.setDeviceId(searchDevicePinResponse.getDeviceId());
			res.setDeviceKey(searchDevicePinResponse.getDeviceKey());
			res.setUserKey(searchDevicePinResponse.getUserKey());
			res.setPinNo(searchDevicePinResponse.getPinNo());
		}

		return res;
	}

	/**
	 * <pre>
	 * 2.1.46. 휴대기기 PIN 번호 확인.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            CheckDevicePinSacReq
	 * @return CheckDevicePinSacRes
	 */
	@Override
	public CheckDevicePinSacRes checkDevicePin(SacRequestHeader header, CheckDevicePinSacReq req) {
		CommonRequest commonRequest = this.component.getSCCommonRequest(header);
		CheckDevicePinResponse checkDevicePinResponse = null;
		SearchDeviceResponse schDeviceRes = null;
		final String tlogUserKey = req.getUserKey();
		final String tlogDeviceKey = req.getDeviceKey();
		String phoneSign = null; // 휴대기기 PIN 번호 지위확인을 위한 signature
		boolean isAuthPin = false; // PIN 인증 성공여부

		try {
			List<KeySearch> keySearchList = new ArrayList<KeySearch>();
			KeySearch keySchUserKey = null;
			keySchUserKey = new KeySearch();
			keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_DEVICE_ID);
			keySchUserKey.setKeyString(req.getDeviceKey());
			keySearchList.add(keySchUserKey);

			SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();
			searchDeviceRequest.setCommonRequest(commonRequest);
			searchDeviceRequest.setUserKey(req.getUserKey());
			searchDeviceRequest.setKeySearchList(keySearchList);
			schDeviceRes = this.deviceSCI.searchDevice(searchDeviceRequest);

			final String tlogDeviceId = schDeviceRes.getUserMbrDevice().getDeviceID();
			new TLogUtil().log(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.log_id("TL_SAC_MEM_0009").result_code("Y").result_message("보안비밀번호 인증 요청")
							.insd_usermbr_no(tlogUserKey).insd_device_id(tlogDeviceKey).device_id(tlogDeviceId);
				}
			});

			// TL_SAC_MEM_0010 tlog set
			new TLogUtil().set(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.insd_usermbr_no(tlogUserKey).insd_device_id(tlogDeviceKey).device_id(tlogDeviceId);
				}
			});

			/**
			 * 검색 조건 setting
			 */
			keySearchList = new ArrayList<KeySearch>();
			keySchUserKey = new KeySearch();
			keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_DEVICE_ID);
			keySchUserKey.setKeyString(req.getDeviceKey());
			keySearchList.add(keySchUserKey);
			keySchUserKey = new KeySearch();
			keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
			keySchUserKey.setKeyString(req.getUserKey());
			keySearchList.add(keySchUserKey);

			CheckDevicePinRequest checkDevicePinRequest = new CheckDevicePinRequest();
			checkDevicePinRequest.setCommonRequest(commonRequest);
			checkDevicePinRequest.setKeySearchList(keySearchList);
			checkDevicePinRequest.setPinNo(req.getPinNo());
			checkDevicePinResponse = this.deviceSetSCI.checkDevicePin(checkDevicePinRequest);

		} catch (StorePlatformException e) {
			final String tlogDeviceId = schDeviceRes != null ? schDeviceRes.getUserMbrDevice().getDeviceID() : null;
			if (schDeviceRes == null) {
				new TLogUtil().log(new ShuttleSetter() {
					@Override
					public void customize(TLogSentinelShuttle shuttle) {
						shuttle.log_id("TL_SAC_MEM_0009").result_code("Y").result_message("보안비밀번호 인증 요청")
								.insd_usermbr_no(tlogUserKey).insd_device_id(tlogDeviceKey).device_id(tlogDeviceId);
					}
				});
			}

			// TL_SAC_MEM_0010 tlog set
			new TLogUtil().log(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.result_code("보안비밀번호 인증 여부").result_message("보안비밀번호 인증 실패");
				}
			});

			throw e;
		}

		// Pin 계정 잠김 상태 - 인증 5회 실패로 인한
		if (StringUtils.equals(MemberConstants.USE_Y, checkDevicePinResponse.getUserMbrDeviceSet().getAuthLockYn())
				&& StringUtils.equals("0", checkDevicePinResponse.getFailCnt())) {

			// TL_SAC_MEM_0010 tlog set
			new TLogUtil().log(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.result_code("보안비밀번호 인증 여부").result_message("보안비밀번호 인증 5회실패");
				}
			});
			throw new StorePlatformException("SAC_MEM_1512");

		} else if (!StringUtils.equals("0", checkDevicePinResponse.getFailCnt())) {

			// TL_SAC_MEM_0010 tlog set
			new TLogUtil().log(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.result_code("보안비밀번호 인증 여부").result_message("보안비밀번호 인증 실패");
				}
			});

		} else {

			// TL_SAC_MEM_0010 tlog set
			new TLogUtil().log(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.result_code("보안비밀번호 인증 여부").result_message("보안비밀번호 인증 성공");
				}
			});

			// tb_cm_svc_auth 데이터 생성 2015.06.25 추가
			/* 인증 Signautre 생성 - guid 형식 */
			phoneSign = UUID.randomUUID().toString().replace("-", "");
			LOGGER.debug("## [SAC] phoneSign : {}", phoneSign);

			/* DB에 저장할 파라미터 셋팅 */
			ServiceAuth serviceAuthInfo = new ServiceAuth();
			serviceAuthInfo.setAuthTypeCd(MemberConstants.AUTH_TYPE_CD_PIN);
			serviceAuthInfo.setAuthSign("PinNumberAuthorization");
			serviceAuthInfo.setAuthValue(phoneSign);
			serviceAuthInfo.setAuthComptYn(MemberConstants.USE_Y);
			serviceAuthInfo.setAuthMdn(checkDevicePinResponse.getDeviceId());
			Object resultObj = this.commonDao.insert("ServiceAuth.createServiceAuthCode", serviceAuthInfo);
			if (resultObj != null && Integer.parseInt(resultObj.toString()) > 0) {
				isAuthPin = true;
			}

		}

		CheckDevicePinSacRes res = new CheckDevicePinSacRes();
		res.setDeviceId(checkDevicePinResponse.getDeviceId());
		res.setDeviceKey(checkDevicePinResponse.getDeviceKey());
		res.setUserKey(checkDevicePinResponse.getUserKey());
		res.setFailCnt(checkDevicePinResponse.getUserMbrDeviceSet().getAuthFailCnt());
		// PIN 인증 성공 시 진위여부 확인을 위한 Signature, 2015.06.25 추가
		if (isAuthPin) {
			res.setPhoneSign(phoneSign);
		}

		return res;
	}

	/**
	 * <pre>
	 * 2.1.48. 휴대기기 설정 정보 조회.
	 * </pre>
	 * 
	 * @param header
	 *            SearchDeviceSetInfoSacRes
	 * @param req
	 *            SearchDeviceSetInfoSacReq
	 * @return SearchDeviceSetInfoSacRes
	 */
	@Override
	public SearchDeviceSetInfoSacRes searchDeviceSetInfo(SacRequestHeader header, SearchDeviceSetInfoSacReq req) {
		SearchDeviceSetInfoRequest searchDeviceSetInfoRequest = new SearchDeviceSetInfoRequest();

		CommonRequest commonRequest = this.component.getSCCommonRequest(header);
		searchDeviceSetInfoRequest.setCommonRequest(commonRequest);

		/**
		 * 검색 조건 setting
		 */
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySchUserKey = null;
		keySchUserKey = new KeySearch();
		keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_DEVICE_ID);
		keySchUserKey.setKeyString(req.getDeviceKey());
		keySearchList.add(keySchUserKey);
		keySchUserKey = new KeySearch();
		keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
		keySchUserKey.setKeyString(req.getUserKey());
		keySearchList.add(keySchUserKey);

		searchDeviceSetInfoRequest.setKeySearchList(keySearchList);

		// SC Call
		SearchDeviceSetInfoResponse searchDeviceSetInfoResponse = this.deviceSetSCI
				.searchDeviceSetInfo(searchDeviceSetInfoRequest);

		SearchDeviceSetInfoSacRes res = new SearchDeviceSetInfoSacRes();

		res.setAutoUpdateSet(StringUtils.defaultString(searchDeviceSetInfoResponse.getUserMbrDeviceSet()
				.getAutoUpdateSet(), null));
		res.setIsAdult(StringUtils.defaultString(searchDeviceSetInfoResponse.getUserMbrDeviceSet().getIsAdult(), null));
		res.setIsPin(searchDeviceSetInfoResponse.getUserMbrDeviceSet().getIsPin());
		res.setIsPinClosed(StringUtils.defaultString(searchDeviceSetInfoResponse.getUserMbrDeviceSet().getAuthLockYn(),
				null));
		res.setFailCnt(StringUtils.defaultString(searchDeviceSetInfoResponse.getUserMbrDeviceSet().getAuthFailCnt(), null));

		// Default=Y
		//res.setIsPinRetry(StringUtils.defaultString(searchDeviceSetInfoResponse.getUserMbrDeviceSet().getIsPinRetry(),
	//			null));
		// Default=Y
		res.setIsAutoUpdate(StringUtils.defaultString(searchDeviceSetInfoResponse.getUserMbrDeviceSet()
				.getIsAutoUpdate(), null));
		// Default=Y
		res.setIsAutoUpdateWifi(StringUtils.defaultString(searchDeviceSetInfoResponse.getUserMbrDeviceSet()
				.getIsAutoUpdateWifi(), null));
		// Default=N
		res.setIsLoginLock(StringUtils.defaultString(
				searchDeviceSetInfoResponse.getUserMbrDeviceSet().getIsLoginLock(), null));
		// Default=Y
		res.setIsAdultLock(StringUtils.defaultString(
				searchDeviceSetInfoResponse.getUserMbrDeviceSet().getIsAdultLock(), null));
		// Default=Y
		res.setIsDownloadWifiOnly(StringUtils.defaultString(searchDeviceSetInfoResponse.getUserMbrDeviceSet()
				.getIsDownloadWifiOnly(), null));
		// Default=N, ICAS_AUTH_YN 컬럼은 디폴트 값이 없고 기존 데이터는 NULL로 되어 있으므로 주의.
		res.setIsIcasAuth(StringUtils.defaultString(searchDeviceSetInfoResponse.getUserMbrDeviceSet().getIsIcasAuth(),
				"N"));
		res.setRealNameDate(searchDeviceSetInfoResponse.getUserMbrDeviceSet().getRnameAuthDate());
		res.setRealNameMdn(searchDeviceSetInfoResponse.getUserMbrDeviceSet().getRnameAuthMdn());

		return res;
	}

	/**
	 * <pre>
	 * 2.1.49. 휴대기기 설정 정보 등록/수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ModifyDeviceSetInfoSacReq
	 * @return ModifyDeviceSetInfoSacRes
	 */
	@Override
	public ModifyDeviceSetInfoSacRes modDeviceSetInfo(SacRequestHeader header, ModifyDeviceSetInfoSacReq req) {
		ModifyDeviceSetInfoRequest modifyDeviceSetInfoRequest = new ModifyDeviceSetInfoRequest();

		CommonRequest commonRequest = this.component.getSCCommonRequest(header);
		modifyDeviceSetInfoRequest.setCommonRequest(commonRequest);

		/**
		 * 검색 조건 setting
		 */
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch keySchUserKey = null;
		keySchUserKey = new KeySearch();
		keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_DEVICE_ID);
		keySchUserKey.setKeyString(req.getDeviceKey());
		keySearchList.add(keySchUserKey);
		keySchUserKey = new KeySearch();
		keySchUserKey.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
		keySchUserKey.setKeyString(req.getUserKey());
		keySearchList.add(keySchUserKey);

		modifyDeviceSetInfoRequest.setKeySearchList(keySearchList);

		UserMbrDeviceSet userMbrDeviceSet = new UserMbrDeviceSet();
		userMbrDeviceSet.setIsAutoUpdate(req.getIsAutoUpdate());
		userMbrDeviceSet.setAutoUpdateSet(req.getAutoUpdateSet());
		userMbrDeviceSet.setIsAutoUpdateWifi(req.getIsAutoUpdateWifi());
		userMbrDeviceSet.setIsLoginLock(req.getIsLoginLock());
		//userMbrDeviceSet.setIsPinRetry(req.getIsPinRetry());
		userMbrDeviceSet.setIsAdult(req.getIsAdult());
		userMbrDeviceSet.setIsAdultLock(req.getIsAdultLock());
		userMbrDeviceSet.setIsDownloadWifiOnly(req.getIsDownloadWifiOnly());
		userMbrDeviceSet.setRnameAuthDate(req.getRealNameDate());
		userMbrDeviceSet.setRnameAuthMdn(req.getRealNameMdn());

		modifyDeviceSetInfoRequest.setUserMbrDeviceSet(userMbrDeviceSet);

		// SC Call
		ModifyDeviceSetInfoResponse modifyDeviceSetInfoResponse = this.deviceSetSCI
				.modifyDeviceSetInfo(modifyDeviceSetInfoRequest);

		ModifyDeviceSetInfoSacRes res = new ModifyDeviceSetInfoSacRes();
		res.setDeviceId(modifyDeviceSetInfoResponse.getDeviceId());
		res.setDeviceKey(modifyDeviceSetInfoResponse.getDeviceKey());
		res.setUserKey(modifyDeviceSetInfoResponse.getUserKey());

		return res;
	}

}
