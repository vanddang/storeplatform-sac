package com.skplanet.storeplatform.sac.member.user.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.sci.IdpSCI;
import com.skplanet.storeplatform.external.client.idp.sci.ImIdpSCI;
import com.skplanet.storeplatform.external.client.idp.vo.ModifyProfileEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.imidp.UpdateAdditionalInfoEcReq;
import com.skplanet.storeplatform.external.client.shopping.util.StringUtil;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.MoveUserInfoRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.MoveUserInfoResponse;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailV2Res;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.MoveUserInfoSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.MoveUserInfoSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchExtentReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.util.DeviceUtil;

/**
 * 사용자 공통 서비스 인터페이스 구현체.
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private MemberCommonComponent commService;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private ImIdpSCI imIdpSCI;

	@Autowired
	private IdpSCI idpSCI;

	@Autowired
	private UserSCI userSCI;

	@Autowired
	private UserSearchService userSearchService;

	private static CommonRequest commonRequest;

	static {
		commonRequest = new CommonRequest();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.UserService# modifyProfileIdp
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader, java.lang.String, java.lang.String)
	 */
	@Override
	public void modProfileIdp(SacRequestHeader requestHeader, String userKey, String userAuthKey) {

		/* 회원정보 조회 */
		DetailReq detailReq = new DetailReq();
		detailReq.setUserKey(userKey);
		SearchExtentReq searchExtent = new SearchExtentReq();
		searchExtent.setUserInfoYn(MemberConstants.USE_Y);
		searchExtent.setDeviceInfoYn(MemberConstants.USE_Y);
		detailReq.setSearchExtent(searchExtent);
		DetailV2Res detailRes = this.userSearchService.detailV2(requestHeader, detailReq);

		if (!StringUtil.equals(detailRes.getUserInfo().getUserType(), MemberConstants.USER_TYPE_MOBILE)) {

			String userPhoneStr = "";

			if (detailRes.getDeviceInfoList() != null && detailRes.getDeviceInfoList().size() > 0) {
				StringBuffer sbUserPhone = new StringBuffer();
				for (DeviceInfo deviceInfo : detailRes.getDeviceInfoList()) {

					String imMngNum = deviceInfo.getSvcMangNum();
					String uacd = DeviceUtil.getDeviceExtraValue(MemberConstants.DEVICE_EXTRA_UACD,
							deviceInfo.getDeviceExtraInfoList());

					sbUserPhone.append(deviceInfo.getDeviceId());
					sbUserPhone.append(",");
					if (StringUtils.equals(deviceInfo.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKT)) {
						sbUserPhone.append(imMngNum == null ? "" : imMngNum);
					}
					sbUserPhone.append(",");
					sbUserPhone.append(uacd == null ? "" : uacd);
					sbUserPhone.append(",");
					sbUserPhone.append(this.commService.convertDeviceTelecom(deviceInfo.getDeviceTelecom()));
					sbUserPhone.append("|");
				}
				userPhoneStr = sbUserPhone.toString();
				if (userPhoneStr.indexOf("|") > -1) {
					userPhoneStr = userPhoneStr.substring(0, userPhoneStr.lastIndexOf("|"));
				}
			}

			if (StringUtils.isNotBlank(detailRes.getUserInfo().getImSvcNo())) { // 통합회원
				UpdateAdditionalInfoEcReq req = new UpdateAdditionalInfoEcReq();
				req.setUserAuthKey(userAuthKey);
				req.setKey(detailRes.getUserInfo().getImSvcNo());
				req.setUserMdn(userPhoneStr);
				LOGGER.info("{} updateAdditionalInfo userMdn : {}", userKey, userPhoneStr);
				this.imIdpSCI.updateAdditionalInfo(req);
			} else {
				ModifyProfileEcReq req = new ModifyProfileEcReq();
				req.setKeyType("2"); // idp키로 조회
				req.setUserAuthKey(userAuthKey);
				req.setKey(detailRes.getUserInfo().getImMbrNo());
				req.setUserPhone(userPhoneStr);
				LOGGER.info("{} modifyProfile userMdn : {}", userKey, userPhoneStr);
				this.idpSCI.modifyProfile(req);
			}

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.UserService# updateAdditionalInfoForNonLogin
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader, java.lang.String, java.lang.String)
	 */
	@Override
	public void modAdditionalInfoForNonLogin(SacRequestHeader requestHeader, String userKey, String imSvcNo) {

		/* 휴대기기 목록 조회 */
		ListDeviceReq listDeviceReq = new ListDeviceReq();
		listDeviceReq.setIsMainDevice("N");
		listDeviceReq.setUserKey(userKey);

		String userPhoneStr = "";

		ListDeviceRes listDeviceRes = this.deviceService.listDevice(requestHeader, listDeviceReq);
		if (listDeviceRes.getDeviceInfoList() != null && listDeviceRes.getDeviceInfoList().size() > 0) {
			StringBuffer sbUserPhone = new StringBuffer();
			for (DeviceInfo deviceInfo : listDeviceRes.getDeviceInfoList()) {

				String imMngNum = deviceInfo.getSvcMangNum();
				String uacd = DeviceUtil.getDeviceExtraValue(MemberConstants.DEVICE_EXTRA_UACD,
						deviceInfo.getDeviceExtraInfoList());

				sbUserPhone.append(deviceInfo.getDeviceId());
				sbUserPhone.append(",");
				if (StringUtils.equals(deviceInfo.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKT)) {
					sbUserPhone.append(imMngNum == null ? "" : imMngNum);
				}
				sbUserPhone.append(",");
				sbUserPhone.append(uacd == null ? "" : uacd);
				sbUserPhone.append(",");
				sbUserPhone.append(this.commService.convertDeviceTelecom(deviceInfo.getDeviceTelecom()));
				sbUserPhone.append("|");
			}
			userPhoneStr = sbUserPhone.toString();
			if (userPhoneStr.indexOf("|") > -1) {
				userPhoneStr = userPhoneStr.substring(0, userPhoneStr.lastIndexOf("|"));
			}
		}

		UpdateAdditionalInfoEcReq req = new UpdateAdditionalInfoEcReq();
		req.setExecuteMode("A");
		req.setKey(imSvcNo);
		req.setUserMdn(userPhoneStr);
		LOGGER.info("{} updateAdditionalInfo userMdn : {}", userKey, userPhoneStr);
		this.imIdpSCI.updateAdditionalInfo(req);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.UserService# moveUserInfo
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader, MoveUserInfoSacReq)
	 */
	@Override
	public MoveUserInfoSacRes moveUserInfo(SacRequestHeader sacHeader, MoveUserInfoSacReq moveUserInfoSacReq) {
		/* 헤더 정보 셋팅 */
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		MoveUserInfoRequest moveUserInfoRequest = new MoveUserInfoRequest();
		moveUserInfoRequest.setCommonRequest(commonRequest);
		moveUserInfoRequest.setUserKey(moveUserInfoSacReq.getUserKey());
		// 실제 moveType 정보는 정상/휴면 회원 여부를 체크 해서 아래 둘중 하나로 넣도록 한다.
		// Constant.USERMBR_MOVE_TYPE_ACTIVATE(정상 처리), Constant.USERMBR_MOVE_TYPE_DORMANT(휴면 처리)
		moveUserInfoRequest.setMoveType(moveUserInfoSacReq.getMoveType());
		MoveUserInfoResponse moveUserInfoResponse = this.userSCI.moveUserMbr(moveUserInfoRequest);

		MoveUserInfoSacRes moveUserInfoSacRes = new MoveUserInfoSacRes();
		moveUserInfoSacRes.setUserKey(moveUserInfoResponse.getUserKey());

		return moveUserInfoSacRes;
	}
}
