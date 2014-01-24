package com.skplanet.storeplatform.sac.member.user.service;

import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 회원 부가 정보 등록/수정/삭제/조회 인터페이스
 * 
 * Updated on : 2014. 1. 20. Updated by : 강신완, 부르칸.
 */
public interface UserExtraInfoService {

	/**
	 * 등록/수정
	 * 
	 * @param headerVo
	 * @param UserExtraInfoReq
	 * @return
	 * @throws Exception
	 */
	public UserExtraInfoRes modifyAdditionalInformation(UserExtraInfoReq req, SacRequestHeader sacHeader)
			throws Exception;

	/**
	 * 조회
	 * 
	 * @param headerVo
	 * @param UserExtraInfoReq
	 * @return
	 * @throws Exception
	 */
	public UserExtraInfoRes listAdditionalInformation(UserExtraInfoReq req, SacRequestHeader sacHeader)
			throws Exception;

	/**
	 * 삭제
	 * 
	 * @param headerVo
	 * @param UserExtraInfoReq
	 * @return
	 * @throws Exception
	 */
	public UserExtraInfoRes removeAdditionalInformation(UserExtraInfoReq req, SacRequestHeader sacHeader)
			throws Exception;

	/**
	 * 회원조회 SC API
	 * 
	 * @param headerVo
	 * @param UserExtraInfoReq
	 * @return
	 * @throws Exception
	 */
	public UserInfo searchUser(UserExtraInfoReq req, SacRequestHeader sacHeader) throws Exception;

	/**
	 * 부가정보리스트 SC API
	 * 
	 * @param headerVo
	 * @param UserExtraInfoReq
	 * @return
	 * @throws Exception
	 */
	public UserExtraInfoRes listUserExtra(UserExtraInfoReq req, SacRequestHeader sacHeader) throws Exception;

	/**
	 * 부가정보등록/수정 SC API
	 * 
	 * @param headerVo
	 * @param UserExtraInfoReq
	 * @return
	 * @throws Exception
	 */
	public UserExtraInfoRes modifyUserExtra(UserExtraInfoReq req, SacRequestHeader sacHeader) throws Exception;

	/**
	 * 부가정보 삭제 SC API
	 * 
	 * @param headerVo
	 * @param UserExtraInfoReq
	 * @return
	 * @throws Exception
	 */
	public UserExtraInfoRes removeUserExtra(UserExtraInfoReq req, SacRequestHeader sacHeader) throws Exception;

	/**
	 * profileCode 정상 데이터인지 체크
	 * 
	 * @param headerVo
	 * @param UserExtraInfoReq
	 * @return
	 * @throws Exception
	 */
	public String validProfileCode(UserExtraInfoReq req) throws Exception;
}
