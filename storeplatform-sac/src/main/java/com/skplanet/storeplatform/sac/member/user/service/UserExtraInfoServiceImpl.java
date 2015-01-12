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
import com.skplanet.storeplatform.sac.member.common.vo.CommonCode;

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
	public UserExtraInfoRes modAdditionalInformation(UserExtraInfoReq req, SacRequestHeader sacHeader) {

		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		UserInfo searchUser = this.srhUser(req, sacHeader);

		/* 입력받은 profileCode 정상인지 확인 */
		String validProfileCode = this.validProfileCode(req);

		/* 정상회원이면 SC 회원 부가 정보 등록/수정 호출 */
		UserExtraInfoRes res = new UserExtraInfoRes();
		if (searchUser != null && "Y".equals(validProfileCode)) {
			res = this.modUserExtra(req, sacHeader);
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
		UserInfo searchUser = this.srhUser(req, sacHeader);

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
	public UserExtraInfoRes remAdditionalInformation(UserExtraInfoReq req, SacRequestHeader sacHeader) {

		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		/* Req : userKey 정상적인 key인지 회원정보 호출하여 확인 */
		UserInfo searchUser = this.srhUser(req, sacHeader);

		/* 입력받은 profileCode 정상인지 확인 */
		String validProfileCode = this.validProfileCode(req);

		/* 입력받은 profileCode 가 등록이 되어 있는지 확인 : 등록이 되어 있어야 삭제가 가능. */
		String registerdProfileCode = this.registeredProfileCode(searchUser, req);

		UserExtraInfoRes res = new UserExtraInfoRes();
		/* 정상회원이면 SC 회원 부가 정보 삭제 호출 */
		if (searchUser != null && "Y".equals(validProfileCode) && "Y".equals(registerdProfileCode)) {
			res = this.remUserExtra(req, sacHeader);
		}

		return res;
	}

	/* SC API 회원정보 조회 */
	@Override
	public UserInfo srhUser(UserExtraInfoReq req, SacRequestHeader sacHeader) {

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
	public UserExtraInfoRes remUserExtra(UserExtraInfoReq req, SacRequestHeader sacHeader) {

		RemoveManagementRequest removeReq = new RemoveManagementRequest();

		List<MbrMangItemPtcr> ptcrList = new ArrayList<MbrMangItemPtcr>();

		for (UserExtraInfo info : req.getUserExtraInfoList()) {
			MbrMangItemPtcr ptcr = new MbrMangItemPtcr();
			ptcr.setExtraProfile(info.getExtraProfile());
			ptcr.setExtraProfileValue(info.getExtraProfileValue());
			ptcr.setUserKey(req.getUserKey());
			ptcr.setTenantID(sacHeader.getTenantHeader().getTenantId());

			ptcrList.add(ptcr);
		}

		removeReq.setUserKey(req.getUserKey());
		removeReq.setMbrMangItemPtcr(ptcrList);
		removeReq.setCommonRequest(commonRequest);

		RemoveManagementResponse removeRes = this.userSCI.removeManagement(removeReq);

		UserExtraInfoRes res = new UserExtraInfoRes();
		res.setUserKey(removeRes.getUserKey());

		return res;
	}

	/* SC API 회원부가정보 등록/수정 */
	@Override
	public UserExtraInfoRes modUserExtra(UserExtraInfoReq req, SacRequestHeader sacHeader) {

		UpdateManagementRequest updateReq = new UpdateManagementRequest();

		List<MbrMangItemPtcr> ptcrList = new ArrayList<MbrMangItemPtcr>();

		for (UserExtraInfo info : req.getUserExtraInfoList()) {
			MbrMangItemPtcr ptcr = new MbrMangItemPtcr();
			ptcr.setExtraProfile(info.getExtraProfile());
			ptcr.setExtraProfileValue(info.getExtraProfileValue());
			ptcr.setUserKey(req.getUserKey());
			ptcr.setTenantID(sacHeader.getTenantHeader().getTenantId());

			ptcrList.add(ptcr);
		}

		updateReq.setUserKey(req.getUserKey());
		updateReq.setMbrMangItemPtcr(ptcrList);
		updateReq.setCommonRequest(commonRequest);

		UpdateManagementResponse updateRes = this.userSCI.updateManagement(updateReq);

		UserExtraInfoRes res = new UserExtraInfoRes();
		res.setUserKey(updateRes.getUserKey());

		return res;
	}

	/* 입력받은 profileCode 정상인지 체크 */
	@Override
	public String validProfileCode(UserExtraInfoReq req) {
		String validProfileCode = "N";
		List<CommonCode> codes = this.mcc.getCommonCode(MemberConstants.USER_EXTRA_GROP_CD);
		int checkCount = 0;

		for (CommonCode commonCode : codes) {
			for (UserExtraInfo info : req.getUserExtraInfoList()) {
				if (StringUtils.equals(info.getExtraProfile(), commonCode.getCdId())) {
					checkCount += 1;
				}
			}
		}

		if (checkCount == req.getUserExtraInfoList().size()) {
			validProfileCode = "Y";
		} else {
			throw new StorePlatformException("SAC_MEM_0002", "getExtraProfileCode()");
		}
		return validProfileCode;
	}

	/* 입력받은 profileCode 가 등록이 되어 있는지 확인 : 등록이 되어 있어야 삭제가 가능. */
	@Override
	public String registeredProfileCode(UserInfo searchUser, UserExtraInfoReq req) {
		List<UserExtraInfo> extraInfo = searchUser.getUserExtraInfoList();
		List<UserExtraInfo> reqInfo = req.getUserExtraInfoList();
		String registeredProfileCode = "";
		int checkCount = 0;

		for (UserExtraInfo infoSearchUser : extraInfo) {
			for (UserExtraInfo infoReqUser : reqInfo) {

				if (infoSearchUser.getExtraProfile().equals(infoReqUser.getExtraProfile())) {
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
