package com.skplanet.storeplatform.sac.member.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.sci.IdpSCI;
import com.skplanet.storeplatform.external.client.idp.sci.ImIdpSCI;
import com.skplanet.storeplatform.external.client.idp.vo.ModifyProfileEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UpdateAdditionalInfoEcReq;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.idp.constants.IdpConstants;
import com.skplanet.storeplatform.sac.member.common.idp.repository.IdpRepository;
import com.skplanet.storeplatform.sac.member.common.util.DeviceUtil;

/**
 * 사용자 공통 서비스 인터페이스 구현체.
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private MemberCommonComponent commService;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private ImIdpSCI imIdpSCI;

	@Autowired
	private IdpSCI idpSCI;

	@Autowired
	private IdpRepository idpRepository;

	@Autowired
	private UserSCI userSCI;

	@Value("#{propertiesForSac['idp.im.request.operation']}")
	public String idpOperationMode;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.UserService#
	 * modifyProfileIdp
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void updateProfileIdp(SacRequestHeader requestHeader, String userKey, String userAuthKey) {

		/* 휴대기기 목록 조회 */
		ListDeviceReq listDeviceReq = new ListDeviceReq();
		listDeviceReq.setIsMainDevice("N");
		listDeviceReq.setUserKey(userKey);

		String userPhoneStr = "";

		ListDeviceRes listDeviceRes = this.deviceService.listDevice(requestHeader, listDeviceReq);
		if (listDeviceRes.getDeviceInfoList() != null) {
			StringBuffer sbUserPhone = new StringBuffer();
			for (DeviceInfo deviceInfo : listDeviceRes.getDeviceInfoList()) {

				String imMngNum = deviceInfo.getSvcMangNum();
				String uacd = DeviceUtil.getDeviceExtraValue(MemberConstants.DEVICE_EXTRA_UACD, deviceInfo.getDeviceExtraInfoList());

				sbUserPhone.append(deviceInfo.getDeviceId());
				sbUserPhone.append(",");
				sbUserPhone.append(imMngNum == null ? "" : imMngNum);
				sbUserPhone.append(",");
				sbUserPhone.append(uacd == null ? "" : uacd);
				sbUserPhone.append(",");
				sbUserPhone.append(this.commService.convertDeviceTelecom(deviceInfo.getDeviceTelecom()));
				sbUserPhone.append("|");
			}
			userPhoneStr = sbUserPhone.toString();
			userPhoneStr = userPhoneStr.substring(0, userPhoneStr.lastIndexOf("|"));
		}

		/* 회원정보 조회 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(requestHeader.getTenantHeader().getTenantId());

		SearchUserRequest schUserReq = new SearchUserRequest();
		schUserReq.setCommonRequest(commonRequest);
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		key.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
		key.setKeyString(userKey);
		keySearchList.add(key);
		schUserReq.setKeySearchList(keySearchList);
		SearchUserResponse schUserRes = this.userSCI.searchUser(schUserReq);

		if (schUserRes.getUserMbr().getUserType().equals(MemberConstants.USER_TYPE_ONEID)) { // 통합회원
			UpdateAdditionalInfoEcReq req = new UpdateAdditionalInfoEcReq();
			req.setUserAuthKey(userAuthKey);
			req.setKey(schUserRes.getUserMbr().getImSvcNo());
			req.setUserMdn(userPhoneStr);
			this.imIdpSCI.updateAdditionalInfo(req);
		} else {
			ModifyProfileEcReq req = new ModifyProfileEcReq();
			req.setUserAuthKey(userAuthKey);
			req.setKeyType(IdpConstants.IDP_PARAM_KEY_SECEDE_KEY_TYPE_USERKEY);
			req.setKey(schUserRes.getUserMbr().getImMbrNo());
			req.setUserPhone(userPhoneStr);
			this.idpSCI.modifyProfile(req);
		}

	}

}
