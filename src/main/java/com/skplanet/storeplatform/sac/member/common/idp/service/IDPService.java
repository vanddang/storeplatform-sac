package com.skplanet.storeplatform.sac.member.common.idp.service;

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;

public interface IDPService {

	/** 아이디 중복 체크 */
	public IDPReceiverM checkDupID(String id) throws Exception;

	/** 모바일 API */
	/** 모바일 회원 인증 */
	public IDPReceiverM authForWap(String mdn) throws Exception;

	/** 모바일 회원가입 */
	public IDPReceiverM join4Wap(String mdn) throws Exception;

	/** 사용자 해지 */
	public IDPReceiverM secedeUser4Wap(String mdn) throws Exception;
}
