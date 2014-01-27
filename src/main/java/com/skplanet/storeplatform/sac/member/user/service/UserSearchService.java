/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.user.service;

import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.DetailRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ExistRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchAgreementReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchAgreementRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.UserExtraInfoRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 회원 조회 서비스 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 1. 7. Updated by : 강신완, 부르칸.
 */
public interface UserSearchService {

	/**
	 * <pre>
	 * 회원 가입 여부 조회
	 * </pre>
	 * 
	 * @param headerVo
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ExistRes exist(SacRequestHeader sacHeader, ExistReq req) throws Exception;

	/**
	 * 
	 * <pre>
	 * 회원 정보 조회
	 * </pre>
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public DetailRes detail(SacRequestHeader sacHeader, DetailReq req) throws Exception;

	/**
	 * 
	 * <pre>
	 * 회원 기본정보 조회 SC API
	 * </pre>
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public UserInfo searchUser(DetailReq req, SacRequestHeader sacHeader) throws Exception;

	/**
	 * 
	 * <pre>
	 * 회원 부가정보 조회 SC API
	 * </pre>
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public UserExtraInfoRes listUserExtra(DetailReq req, SacRequestHeader sacHeader) throws Exception;

	/**
	 * 
	 * <pre>
	 * 디바이스 리스트 조회 SC API
	 * </pre>
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public ListDeviceRes listDevice(DetailReq req, SacRequestHeader sacHeader) throws Exception;

	/**
	 * 
	 * <pre>
	 * 약관동의목록 조회 SC API
	 * </pre>
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public SearchAgreementRes searchAgreement(SearchAgreementReq req, SacRequestHeader sacHeader) throws Exception;
}
