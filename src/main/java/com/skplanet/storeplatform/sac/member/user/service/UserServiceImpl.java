package com.skplanet.storeplatform.sac.member.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.external.client.idp.vo.ImIDPReceiverM;
import com.skplanet.storeplatform.framework.core.util.StringUtil;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbr;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.idp.constants.IDPConstants;
import com.skplanet.storeplatform.sac.member.common.idp.constants.ImIDPConstants;
import com.skplanet.storeplatform.sac.member.common.idp.repository.IDPRepository;
import com.skplanet.storeplatform.sac.member.common.idp.service.IDPService;
import com.skplanet.storeplatform.sac.member.common.idp.service.ImIDPService;

/**
 * 사용자 공통 서비스 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private MemberCommonComponent commService;

	@Autowired
	private DeviceService deviceService;

	private IDPService idpService;

	@Autowired
	private ImIDPService imIdpService;

	@Autowired
	private IDPRepository idpRepository;

	@Autowired
	private UserSCI userSCI;

	@Value("#{propertiesForSac['idp.im.request.operation']}")
	public String IDP_OPERATION_MODE;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.UserService#
	 * modifyProfileIdp
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void modifyProfileIdp(String systemId, String tenantId, String userKey, String userAuthKey) throws Exception {

		/* 회원정보 조회 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(systemId);
		commonRequest.setTenantID(tenantId);

		SearchUserRequest schUserReq = new SearchUserRequest();
		schUserReq.setCommonRequest(commonRequest);
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		key.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
		key.setKeyString(userKey);
		keySearchList.add(key);
		schUserReq.setKeySearchList(keySearchList);
		SearchUserResponse schUserRes = this.userSCI.searchUser(schUserReq);

		UserMbr userMbr = schUserRes.getUserMbr();

		HashMap<String, Object> param = new HashMap<String, Object>();
		if (userMbr.getUserSex() != null) {
			param.put("user_sex", userMbr.getUserSex());
		}
		if (userMbr.getUserBirthDay() != null) {
			param.put("user_birthday", userMbr.getUserBirthDay());
		}
		if (userMbr.getUserZip() != null) {
			param.put("user_zipcode", userMbr.getUserZip());
		}
		if (userMbr.getUserAddress() != null) {
			param.put("user_address", userMbr.getUserAddress());
		}
		if (userMbr.getUserDetailAddress() != null) {
			param.put("user_address2", userMbr.getUserDetailAddress());
		}
		if (userMbr.getUserPhone() != null) {
			param.put("user_tel", userMbr.getUserPhone());
		}
		if (userMbr.getUserPhoneCountry() != null) {
			param.put("is_foreign", (userMbr.getUserPhoneCountry().equals("82") ? "N" : "Y"));
		}

		/* 휴대기기 목록 조회 */
		ListDeviceReq listDeviceReq = new ListDeviceReq();
		listDeviceReq.setIsMainDevice("N");// 대표기기만 조회(Y), 모든기기 조회(N)
		listDeviceReq.setUserKey(userKey);

		SacRequestHeader requestHeader = new SacRequestHeader();
		TenantHeader tenant = new TenantHeader();
		tenant.setSystemId(systemId);
		tenant.setTenantId(tenantId);
		ListDeviceRes listDeviceRes = this.deviceService.listDevice(requestHeader, listDeviceReq);

		List<DeviceInfo> deviceInfoList = listDeviceRes.getDeviceInfoList();
		String userPhoneStr = null;
		if (deviceInfoList != null) {
			StringBuffer sbUserPhone = new StringBuffer();
			for (DeviceInfo deviceInfo : deviceInfoList) {
				sbUserPhone.append(deviceInfo.getDeviceId());
				sbUserPhone.append(",");
				sbUserPhone.append(deviceInfo.getImMngNum() == null ? "" : deviceInfo.getImMngNum());
				sbUserPhone.append(",");
				sbUserPhone.append(deviceInfo.getUacd() == null ? "" : deviceInfo.getUacd());
				sbUserPhone.append(",");
				sbUserPhone.append(this.commService.convertDeviceTelecom(deviceInfo.getDeviceTelecom()));
				sbUserPhone.append("|");
			}
			userPhoneStr = sbUserPhone.toString();
			userPhoneStr = userPhoneStr.substring(0, userPhoneStr.lastIndexOf("|"));
		}

		param.put("user_auth_key", userAuthKey);

		if (schUserRes.getUserMbr().getUserType().equals(MemberConstants.USER_TYPE_ONEID)) { // 통합회원

			param.put("key", schUserRes.getUserMbr().getImSvcNo());
			param.put("operation_mode", this.IDP_OPERATION_MODE);
			if (userPhoneStr != null) {
				param.put("user_mdn", userPhoneStr);
				param.put("user_mdn_auth_key", this.idpRepository.makePhoneAuthKey(userPhoneStr));
			}

			param.put("modify_req_date", DateUtil.getDateString(new Date(), "yyyyMMddHH"));
			param.put("modify_req_time", DateUtil.getDateString(new Date(), "HHmmss"));

			ImIDPReceiverM imIdpReceiver = this.imIdpService.updateAdditionalInfo(param);
			if (!StringUtil.equals(imIdpReceiver.getResponseHeader().getResult(), ImIDPConstants.IDP_RES_CODE_OK)) {
				throw new RuntimeException("[" + imIdpReceiver.getResponseHeader().getResult() + "] "
						+ imIdpReceiver.getResponseHeader().getResult_text());
			}

		} else {

			param.put("key_type", "2");
			param.put("key", schUserRes.getUserMbr().getImMbrNo());
			if (userPhoneStr != null) {
				param.put("user_phone", userPhoneStr);
				param.put("phone_auth_key", this.idpRepository.makePhoneAuthKey(userPhoneStr));
			}
			IDPReceiverM idpReceiver = this.idpService.modifyProfile(param);
			if (!StringUtil.equals(idpReceiver.getResponseHeader().getResult(), IDPConstants.IDP_RES_CODE_OK)) {
				throw new RuntimeException("[" + idpReceiver.getResponseHeader().getResult() + "] "
						+ idpReceiver.getResponseHeader().getResult_text());
			}

		}
	}

}
