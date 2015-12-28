package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import com.skplanet.storeplatform.external.client.inicis.sci.InicisSCI;
import com.skplanet.storeplatform.external.client.inicis.vo.InicisAuthAccountEcReq;
import com.skplanet.storeplatform.external.client.inicis.vo.InicisAuthAccountEcRes;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.AuthorizeAccountReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.AuthorizeAccountRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.PinAuthorizationCheckReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.PinAuthorizationCheckRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.miscellaneous.vo.ServiceAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 
 * 기타 기능 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 */
@Service
public class MiscellaneousServiceImpl implements MiscellaneousService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MiscellaneousServiceImpl.class);

	@Autowired
	private MemberCommonComponent commonComponent; // 회원 공통기능 컴포넌트
	@Autowired
	private InicisSCI inicisSCI; // 이니시스 연동 Interface.
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDao;


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

	/**
	 * <pre>
	 * 2.1.60.	휴대기기 PIN 번호 인증 여부 확인.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            PinAuthorizationCheckReq
	 * @return PinAuthorizationCheckRes
	 */
	@Override
	public PinAuthorizationCheckRes pinAuthorizationCheck(SacRequestHeader header, PinAuthorizationCheckReq req) {
		String tenantId = header.getTenantHeader().getTenantId();
		String systemId = header.getTenantHeader().getSystemId();
		String deviceId = req.getDeviceId();

		ServiceAuth serviceAuthInfo = new ServiceAuth();
		serviceAuthInfo.setTenantId(tenantId);
		serviceAuthInfo.setSystemId(systemId);
		serviceAuthInfo.setAuthTypeCd(MemberConstants.AUTH_TYPE_CD_PIN);
		serviceAuthInfo.setAuthMdn(deviceId);
		serviceAuthInfo.setAuthValue(req.getPhoneSign());
		serviceAuthInfo.setTimeToLive(req.getTimeToLive());

		/** pin 인증 signature 확인 */
		Object authCntObj = this.commonDao.queryForObject("Miscellaneous.searchPinAuthorizationCheckCnt",
				serviceAuthInfo);

		PinAuthorizationCheckRes res = new PinAuthorizationCheckRes();
		res.setDeviceId(deviceId);
		if (authCntObj != null && Integer.parseInt(authCntObj.toString()) > 0) {
			res.setComptYn(MemberConstants.USE_Y); // 인증번호 확인 성공
		} else {
			res.setComptYn(MemberConstants.USE_N); // 인증번호 확인 실패
		}

		return res;
	}
}
