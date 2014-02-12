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

import com.skplanet.storeplatform.sac.client.member.vo.user.CreateOcbInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateOcbInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetOcbInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.GetOcbInformationRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveOcbInformationReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.RemoveOcbInformationRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 회원 OCB 정보 서비스 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2014. 2. 12. Updated by : 심대진, 다모아 솔루션.
 */
public interface UserOcbService {

	/**
	 * <pre>
	 * 회원 OCB 정보 등록/수정.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	public CreateOcbInformationRes createOcbInformation(SacRequestHeader sacHeader, CreateOcbInformationReq req);

	/**
	 * <pre>
	 * 회원 OCB 정보 삭제.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	public RemoveOcbInformationRes removeOcbInformation(SacRequestHeader sacHeader, RemoveOcbInformationReq req);

	/**
	 * <pre>
	 * 회원 OCB 정보 조회.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            공통 헤더
	 * @param req
	 *            Request Value Object
	 * @return Response Value Object
	 */
	public GetOcbInformationRes getOcbInformation(SacRequestHeader sacHeader, GetOcbInformationReq req);

}
