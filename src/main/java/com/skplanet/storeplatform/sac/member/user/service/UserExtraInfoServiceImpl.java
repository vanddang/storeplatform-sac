package com.skplanet.storeplatform.sac.member.user.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.MbrMangItemPtcr;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
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
@Transactional
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
	public UserExtraInfoRes modifyAdditionalInformation(UserExtraInfoReq req, SacRequestHeader sacHeader)
			throws Exception {

		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		UserExtraInfoRes res = new UserExtraInfoRes();

		/* Req : userKey 정상적인 key인지 회원정보 호출하여 확인 */
		UserInfo searchUser = this.searchUser(req, sacHeader);

		/* 정상회원이면 SC 회원 부가 정보 등록/수정 호출 */
		if (searchUser != null) {
			res = this.modifyUserExtra(req);
		}

		return res;
	}

	/**
	 * 사용자 부가정보 리스트
	 */
	@Override
	public UserExtraInfoRes listAdditionalInformation(UserExtraInfoReq req, SacRequestHeader sacHeader)
			throws Exception {

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

	/* 회원부가정보 삭제 */
	@Override
	public UserExtraInfoRes removeAdditionalInformation(UserExtraInfoReq req, SacRequestHeader sacHeader)
			throws Exception {

		UserExtraInfoRes res = new UserExtraInfoRes();

		return res;
	}

	/* SC API 회원정보 조회 */
	@Override
	public UserInfo searchUser(UserExtraInfoReq req, SacRequestHeader sacHeader) throws Exception {

		UserInfo userInfo = this.mcc.getUserBaseInfo("userKey", req.getUserKey(), sacHeader);

		return userInfo;
	}

	/* SC API 회원부가정보 조회 */
	@Override
	public UserExtraInfoRes listUserExtra(UserExtraInfoReq req, SacRequestHeader sacHeader) throws Exception {
		UserExtraInfoRes extraRes = this.mcc.getUserExtraInfo(req.getUserKey(), sacHeader);

		return extraRes;
	}

	/* SC API 회원부가정보 등록/수정 */
	@Override
	public UserExtraInfoRes modifyUserExtra(UserExtraInfoReq req) throws Exception {
		UserExtraInfoRes res = new UserExtraInfoRes();
		UpdateManagementRequest updateReq = new UpdateManagementRequest();
		UpdateManagementResponse updateRes = new UpdateManagementResponse();

		List<MbrMangItemPtcr> ptcrList = new ArrayList<MbrMangItemPtcr>();

		LOGGER.debug("###### 회원부가정보 등록/수정 Req : {}", req.getUserKey());
		LOGGER.debug("###### 회원부가정보 등록/수정 Req : {}", req.getAddInfoList().toString());

		for (UserExtraInfo info : req.getAddInfoList()) {
			MbrMangItemPtcr ptcr = new MbrMangItemPtcr();
			ptcr.setExtraProfile(info.getExtraProfileCode());
			ptcr.setExtraProfileValue(info.getExtraProfileValue());
			ptcr.setUserKey(req.getUserKey());
			ptcr.setTenantID(commonRequest.getTenantID());

			ptcrList.add(ptcr);
		}

		updateReq.setUserKey(req.getUserKey());
		updateReq.setMbrMangItemPtcr(ptcrList);
		updateReq.setCommonRequest(commonRequest);

		LOGGER.debug("###### 회원부가정보 등록/수정 SC API ptcrList Req : {}", ptcrList.toString());
		LOGGER.debug("###### 회원부가정보 등록/수정 SC API update Req : {}", updateReq.getUserKey());
		LOGGER.debug("###### 회원부가정보 등록/수정 SC API update Req : {}", updateReq.getMbrMangItemPtcr().toString());

		updateRes = this.userSCI.updateManagement(updateReq);

		if (StringUtils.equals(updateRes.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)
				&& updateRes.getUserKey() != null) {

			LOGGER.debug("###### 회원부가정보 등록/수정 SC API Success Res : {}", updateRes.getCommonResponse().getResultCode());
			LOGGER.debug("###### 회원부가정보 등록/수정 SC API Success Res : {}", updateRes.getCommonResponse()
					.getResultMessage());
			LOGGER.debug("###### 회원부가정보 등록/수정 SC API Success Res : {}", updateRes.getUserKey());

			res.setUserKey(updateRes.getUserKey());
		} else {
			LOGGER.debug("###### 회원부가정보 등록/수정 SC API Fail Res : {}", updateRes.getCommonResponse().getResultCode());
			LOGGER.debug("###### 회원부가정보 등록/수정 SC API Fail Res : {}", updateRes.getCommonResponse().getResultMessage());

			throw new RuntimeException("회원부가정보 등록/수정 SC API Fail : [" + updateRes.getCommonResponse().getResultCode()
					+ "]" + "[" + updateRes.getCommonResponse().getResultMessage() + "]");
		}

		return res;
	}
}
