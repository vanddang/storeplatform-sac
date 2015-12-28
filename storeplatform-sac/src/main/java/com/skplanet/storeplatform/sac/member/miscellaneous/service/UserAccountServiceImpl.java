package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import com.skplanet.storeplatform.external.client.inicis.sci.InicisSCI;
import com.skplanet.storeplatform.external.client.inicis.vo.InicisAuthAccountEcReq;
import com.skplanet.storeplatform.external.client.inicis.vo.InicisAuthAccountEcRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.AuthorizeAccountReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.AuthorizeAccountRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * 결제 계좌 기능 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 * Updated on : 2015. 12. 28. Updated by : 임근대, SKP. - Micellaneos 클래스에서 결제 계좌 관련 기능 클래스 분리
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserAccountServiceImpl.class);

	@Autowired
	private InicisSCI inicisSCI; // 이니시스 연동 Interface.


	/**
	 * <pre>
	 * 결제 계좌 정보 인증.
	 * </pre>
	 * 
	 * @param request
	 *            AuthorizeAccountReq
	 * @return AuthorizeAccountRes
	 */
	@Override
	public AuthorizeAccountRes authorizeAccount(AuthorizeAccountReq request) {

		// 1. EC (Inicis 연동) 파라미터 전달, 결제계좌 정보 인증 요청
		InicisAuthAccountEcReq inicisAuthAccountEcReq = new InicisAuthAccountEcReq();
		inicisAuthAccountEcReq.setAcctNm(request.getBankAcctName());
		inicisAuthAccountEcReq.setBankCd(request.getBankCode());
		inicisAuthAccountEcReq.setAcctNo(request.getBankAccount());

		InicisAuthAccountEcRes inicisAuthAccountEcRes = this.inicisSCI.authAccount(inicisAuthAccountEcReq);
		LOGGER.debug("## 계좌인증 성공. Inicis ResultCode : {}", inicisAuthAccountEcRes.getResultCode());

		return new AuthorizeAccountRes();

	}
}
