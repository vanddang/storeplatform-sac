package com.skplanet.storeplatform.sac.member.user.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.MbrMangItemPtcr;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveManagementRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.RemoveManagementResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateManagementRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.UpdateManagementResponse;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;

/**
 * 회원 부가 정보 등록/수정/삭제/조회 인터페이스
 * 
 * Updated on : 2014. 1. 20. Updated by : 강신완, 부르칸.
 */

@Service
public class UserExtraInfoServiceImpl implements UserExtraInfoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserExtraInfoServiceImpl.class);

	@Autowired
	private UserSCI userSCI;

	private static CommonRequest commonRequest;

	static {
		commonRequest = new CommonRequest();
	}

	@Autowired
	private MemberCommonComponent mcc;

	/**
	 * 사용자 부가정보 등록/수정
	 */
	@Override
	public UserExtraInfoRes modifyAdditionalInformation(UserExtraInfoReq req, SacRequestHeader sacHeader) {

		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		UserExtraInfoRes res = new UserExtraInfoRes();

		/* Req : userKey 정상적인 key인지 회원정보 호출하여 확인 */
		UserInfo searchUser = this.searchUser(req, sacHeader);

		/* 입력받은 profileCode 정상인지 확인 */
		String validProfileCode = this.validProfileCode(req);

		/* 정상회원이면 SC 회원 부가 정보 등록/수정 호출 */
		if (searchUser != null && "Y".equals(validProfileCode)) {
			res = this.modifyUserExtra(req, sacHeader);
		}

		return res;
	}

	/**
	 * 사용자 부가정보 리스트
	 */
	@Override
	public UserExtraInfoRes listAdditionalInformation(UserExtraInfoReq req, SacRequestHeader sacHeader) {

		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		/* Req : userKey 정상적인 key인지 회원정보 호출하여 확인 */
		UserInfo searchUser = this.searchUser(req, sacHeader);

		/* 정상회원이면 SC 회원 부가 정보 조회 호출 */
		UserExtraInfoRes res = new UserExtraInfoRes();
		if (searchUser != null) {
			res = this.listUserExtra(req, sacHeader);
		}

		return res;
	}

	/**
	 * 사용자 부가정보 삭제
	 */
	@Override
	public UserExtraInfoRes removeAdditionalInformation(UserExtraInfoReq req, SacRequestHeader sacHeader) {

		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		UserExtraInfoRes res = new UserExtraInfoRes();

		/* Req : userKey 정상적인 key인지 회원정보 호출하여 확인 */
		UserInfo searchUser = this.searchUser(req, sacHeader);

		/* 입력받은 profileCode 정상인지 확인 */
		String validProfileCode = this.validProfileCode(req);

		/* 입력받은 profileCode 가 등록이 되어 있는지 확인 : 등록이 되어 있어야 삭제가 가능. */
		String registerdProfileCode = this.registeredProfileCode(searchUser, req);

		/* 정상회원이면 SC 회원 부가 정보 삭제 호출 */
		if (searchUser != null && "Y".equals(validProfileCode) && "Y".equals(registerdProfileCode)) {
			res = this.removeUserExtra(req, sacHeader);
		}

		return res;
	}

	/* SC API 회원정보 조회 */
	@Override
	public UserInfo searchUser(UserExtraInfoReq req, SacRequestHeader sacHeader) {

		UserInfo userInfo = this.mcc.getUserBaseInfo("userKey", req.getUserKey(), sacHeader);

		return userInfo;
	}

	/* SC API 회원부가정보 조회 */
	@Override
	public UserExtraInfoRes listUserExtra(UserExtraInfoReq req, SacRequestHeader sacHeader) {
		UserExtraInfoRes extraRes = this.mcc.getUserExtraInfo(req.getUserKey(), sacHeader);

		return extraRes;
	}

	/* SC API 회원부가정보 삭제 */
	@Override
	public UserExtraInfoRes removeUserExtra(UserExtraInfoReq req, SacRequestHeader sacHeader) {
		UserExtraInfoRes res = new UserExtraInfoRes();
		RemoveManagementRequest removeReq = new RemoveManagementRequest();

		List<MbrMangItemPtcr> ptcrList = new ArrayList<MbrMangItemPtcr>();

		LOGGER.debug("###### removeUserExtra Req : {}", req.getUserKey());
		LOGGER.debug("###### removeUserExtra Req : {}", req.getAddInfoList().toString());

		for (UserExtraInfo info : req.getAddInfoList()) {
			MbrMangItemPtcr ptcr = new MbrMangItemPtcr();
			ptcr.setExtraProfile(info.getExtraProfileCode());
			ptcr.setExtraProfileValue(info.getExtraProfileValue());
			ptcr.setUserKey(req.getUserKey());
			ptcr.setTenantID(sacHeader.getTenantHeader().getTenantId());

			ptcrList.add(ptcr);
		}

		removeReq.setUserKey(req.getUserKey());
		removeReq.setMbrMangItemPtcr(ptcrList);
		removeReq.setCommonRequest(commonRequest);

		LOGGER.debug("###### removeUserExtra SC API ptcrList Req : {}", ptcrList.toString());
		LOGGER.debug("###### removeUserExtra SC API update Req : {}", removeReq.getUserKey());
		LOGGER.debug("###### removeUserExtra SC API update Req : {}", removeReq.getMbrMangItemPtcr().toString());

		RemoveManagementResponse removeRes = this.userSCI.removeManagement(removeReq);

		LOGGER.debug("###### removeUserExtra SC API Success Res : {}", removeRes.getCommonResponse().getResultCode());
		LOGGER.debug("###### removeUserExtra SC API Success Res : {}", removeRes.getCommonResponse().getResultMessage());
		LOGGER.debug("###### removeUserExtra SC API Success Res : {}", removeRes.getUserKey());

		res.setUserKey(removeRes.getUserKey());

		return res;
	}

	/* SC API 회원부가정보 등록/수정 */
	@Override
	public UserExtraInfoRes modifyUserExtra(UserExtraInfoReq req, SacRequestHeader sacHeader) {
		UserExtraInfoRes res = new UserExtraInfoRes();
		UpdateManagementRequest updateReq = new UpdateManagementRequest();

		List<MbrMangItemPtcr> ptcrList = new ArrayList<MbrMangItemPtcr>();

		for (UserExtraInfo info : req.getAddInfoList()) {
			MbrMangItemPtcr ptcr = new MbrMangItemPtcr();
			ptcr.setExtraProfile(info.getExtraProfileCode());
			ptcr.setExtraProfileValue(info.getExtraProfileValue());
			ptcr.setUserKey(req.getUserKey());
			ptcr.setTenantID(sacHeader.getTenantHeader().getTenantId());

			ptcrList.add(ptcr);
		}

		updateReq.setUserKey(req.getUserKey());
		updateReq.setMbrMangItemPtcr(ptcrList);
		updateReq.setCommonRequest(commonRequest);

		LOGGER.debug("###### SC API ptcrList Req : {}", ptcrList.toString());
		LOGGER.debug("###### SC API update Req : {}", updateReq.getUserKey());
		LOGGER.debug("###### SC API update Req : {}", updateReq.getMbrMangItemPtcr().toString());

		UpdateManagementResponse updateRes = this.userSCI.updateManagement(updateReq);

		LOGGER.debug("###### SC API Success Code : {}", updateRes.getCommonResponse().getResultCode());
		LOGGER.debug("###### SC API Success Msg : {}", updateRes.getCommonResponse().getResultMessage());
		LOGGER.debug("###### SC API Success Res : {}", updateRes.getUserKey());

		res.setUserKey(updateRes.getUserKey());

		return res;
	}

	/* 입력받은 profileCode 정상인지 체크 */
	@Override
	public String validProfileCode(UserExtraInfoReq req) {
		String validProfileCode = "";

		for (UserExtraInfo info : req.getAddInfoList()) {
			if (info.getExtraProfileCode().equals(MemberConstants.USER_EXTRA_CERTIFICATION)
					|| info.getExtraProfileCode().equals(MemberConstants.USER_EXTRA_SKTBILLSEPARATION)
					|| info.getExtraProfileCode().equals(MemberConstants.USER_EXTRA_FACEBOOKACCESSTOKEN)
					|| info.getExtraProfileCode().equals(MemberConstants.USER_EXTRA_FACEBOOKPURCHASE)
					|| info.getExtraProfileCode().equals(MemberConstants.USER_EXTRA_FACEBOOKRATING)
					|| info.getExtraProfileCode().equals(MemberConstants.USER_EXTRA_FACEBOOKREVIEW)
					|| info.getExtraProfileCode().equals(MemberConstants.USER_EXTRA_MEMBERPOINTJOIN)) {

				validProfileCode = "Y";

			} else {
				validProfileCode = "N";
				LOGGER.debug("###### inValid ProfileCode : {}", info.getExtraProfileCode());
				throw new StorePlatformException("SAC_MEM_0002", info.getExtraProfileCode());
			}
		}

		return validProfileCode;
	}

	/* 입력받은 profileCode 가 등록이 되어 있는지 확인 : 등록이 되어 있어야 삭제가 가능. */
	@Override
	public String registeredProfileCode(UserInfo searchUser, UserExtraInfoReq req) {
		List<UserExtraInfo> extraInfo = searchUser.getUserExtraInfo();
		List<UserExtraInfo> reqInfo = req.getAddInfoList();
		String registeredProfileCode = "";
		int checkCount = 0;

		for (UserExtraInfo infoSearchUser : extraInfo) {
			for (UserExtraInfo infoReqUser : reqInfo) {

				if (infoSearchUser.getExtraProfileCode().equals(infoReqUser.getExtraProfileCode())) {
					checkCount += 1;
				}
			}
		}

		if (checkCount == 0) {
			throw new StorePlatformException("SAC_MEM_0002", "getExtraProfileCode()");
		} else {
			registeredProfileCode = "Y";
		}
		return registeredProfileCode;
	}
}
