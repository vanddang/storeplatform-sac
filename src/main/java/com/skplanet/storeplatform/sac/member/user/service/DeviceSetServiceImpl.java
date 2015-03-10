package com.skplanet.storeplatform.sac.member.user.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
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

/**
 * 휴대기기 설정 정보 관련 ServiceImpl
 * 
 * Updated on : 2014. 10. 28. Updated by : Rejoice, Burkhan
 */
@Service
public class DeviceSetServiceImpl implements DeviceSetService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceSetServiceImpl.class);

	@Autowired
	private DeviceSetSCI deviceSetSCI;

	/**
	 * 회원 공통 Component.
	 */
	@Autowired
	private MemberCommonComponent component;

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
		CreateDevicePinRequest createDevicePinRequest = new CreateDevicePinRequest();

		CommonRequest commonRequest = this.component.getSCCommonRequest(header);
		createDevicePinRequest.setCommonRequest(commonRequest);

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

		createDevicePinRequest.setPinNo(req.getPinNo());
		createDevicePinRequest.setKeySearchList(keySearchList);
		CreateDevicePinResponse createDevicePinResponse = null;
		try {

			createDevicePinResponse = this.deviceSetSCI.createDevicePin(createDevicePinRequest);
		} catch (StorePlatformException e) {
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
		}

		ModifyDevicePinSacRes res = new ModifyDevicePinSacRes();
		res.setDeviceId(modifyDevicePinResponse.getDeviceId());
		res.setDeviceKey(modifyDevicePinResponse.getDeviceKey());
		res.setUserKey(modifyDevicePinResponse.getUserKey());

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

		// SC Call
		SearchDevicePinResponse searchDevicePinResponse = this.deviceSetSCI.searchDevicePin(searchDevicePinRequest);

		SearchDevicePinSacRes res = new SearchDevicePinSacRes();
		res.setDeviceId(searchDevicePinResponse.getDeviceId());
		res.setDeviceKey(searchDevicePinResponse.getDeviceKey());
		res.setUserKey(searchDevicePinResponse.getUserKey());
		res.setPinNo(searchDevicePinResponse.getPinNo());

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
		CheckDevicePinRequest checkDevicePinRequest = new CheckDevicePinRequest();

		CommonRequest commonRequest = this.component.getSCCommonRequest(header);
		checkDevicePinRequest.setCommonRequest(commonRequest);

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

		checkDevicePinRequest.setKeySearchList(keySearchList);
		checkDevicePinRequest.setPinNo(req.getPinNo());

		// SC Call
		CheckDevicePinResponse checkDevicePinResponse = this.deviceSetSCI.checkDevicePin(checkDevicePinRequest);
		// Pin 계정 잠김 상태 - 인증 5회 실패로 인한
		if (StringUtils.equals(MemberConstants.USE_Y, checkDevicePinResponse.getUserMbrDeviceSet().getAuthLockYn())
				&& StringUtils.equals("0", checkDevicePinResponse.getFailCnt())) {
			throw new StorePlatformException("SAC_MEM_1512");
		}

		CheckDevicePinSacRes res = new CheckDevicePinSacRes();
		res.setDeviceId(checkDevicePinResponse.getDeviceId());
		res.setDeviceKey(checkDevicePinResponse.getDeviceKey());
		res.setUserKey(checkDevicePinResponse.getUserKey());
		res.setFailCnt(checkDevicePinResponse.getUserMbrDeviceSet().getAuthCnt());

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
		res.setIsAutoUpdate(StringUtils.defaultString(searchDeviceSetInfoResponse.getUserMbrDeviceSet()
				.getIsAutoUpdate(), null));
		res.setIsAutoUpdateWifi(StringUtils.defaultString(searchDeviceSetInfoResponse.getUserMbrDeviceSet()
				.getIsAutoUpdateWifi(), null));
		res.setIsLoginLock(StringUtils.defaultString(
				searchDeviceSetInfoResponse.getUserMbrDeviceSet().getIsLoginLock(), null));
		res.setIsPin(searchDeviceSetInfoResponse.getUserMbrDeviceSet().getIsPin());
		res.setIsPinRetry(StringUtils.defaultString(searchDeviceSetInfoResponse.getUserMbrDeviceSet().getIsPinRetry(),
				null));
		res.setIsPinClosed(StringUtils.defaultString(searchDeviceSetInfoResponse.getUserMbrDeviceSet().getAuthLockYn(),
				null));
		res.setFailCnt(StringUtils.defaultString(searchDeviceSetInfoResponse.getUserMbrDeviceSet().getAuthCnt(), null));

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
		userMbrDeviceSet.setIsPinRetry(req.getIsPinRetry());
		userMbrDeviceSet.setIsAdult(req.getIsAdult());
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
