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
 * PIN 기능 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 * Updated on : 2015. 12. 28. Updated by : 임근대, SKP. - Micellaneos 클래스에서 PIN 관련 기능 클래스 분리
 */
@Service
public class PinServiceImpl implements PinService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PinServiceImpl.class);

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDao;


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
