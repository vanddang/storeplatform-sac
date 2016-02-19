package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.external.client.idp.sci.IdpSCI;
import com.skplanet.storeplatform.external.client.idp.vo.JoinSupServiceRequestEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.JoinSupServiceRequestEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.ServiceSubscriptionCheckEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.ServiceSubscriptionCheckEcRes;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateAdditionalServiceReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.CreateAdditionalServiceRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetAdditionalServiceReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetAdditionalServiceRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * 부가 서비스 기능 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 * Updated on : 2015. 12. 28. Updated by : 임근대, SKP. - Miscellaneous 클래스에서 부가 서비스 관련 기능 클래스 분리
 * @deprecated Onestore 부가서비스 기능 미 제공
 */
@Service
@Deprecated
public class AdditionalServiceServiceImpl implements AdditionalServiceService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AdditionalServiceServiceImpl.class);

	@Autowired
	private IdpSCI idpSCI; // IDP 연동 Interface.

	/**
	 * <pre>
	 * 부가서비스 가입.
	 * </pre>
	 * 
	 * @param request
	 *            CreateAdditionalServiceReq
	 * @return CreateAdditionalServiceRes
	 * @deprecated Onestore 부가서비스 기능 미 제공
	 */
	@Override
	@Deprecated
	public CreateAdditionalServiceRes regAdditionalService(CreateAdditionalServiceReq request) {

		CreateAdditionalServiceRes response = new CreateAdditionalServiceRes();

		JoinSupServiceRequestEcReq joinSupServiceEcReq = new JoinSupServiceRequestEcReq();
		joinSupServiceEcReq.setSvcCode(request.getSvcCode());
		joinSupServiceEcReq.setUserMdn(request.getMsisdn());
		joinSupServiceEcReq.setUserSvcMngNum(request.getSvcMngNum());

		/* FDS LOG START */
		final String deviceId = joinSupServiceEcReq.getUserMdn();
		final String serviceCode = joinSupServiceEcReq.getSvcCode();

		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.log_id("TL_SAC_MEM_0003").device_id(deviceId).service_code(serviceCode);
				;
			}
		});
		/* FDS LOG END */

		JoinSupServiceRequestEcRes joinSupServiceEcRes = this.idpSCI.joinSupServiceRequest(joinSupServiceEcReq);

		response.setSvcCode(serviceCode); // 부가서비스 코드
		response.setMsisdn(joinSupServiceEcRes.getUserMdn()); // 사용자 휴대폰번호

		return response;
	}

	/**
	 * <pre>
	 * 부가서비스 가입 조회.
	 * </pre>
	 * 
	 * @param request
	 *            GetAdditionalServiceReq
	 * @return GetAdditionalServiceRes
	 * @deprecated Onestore 부가서비스 기능 미 제공
	 */
	@Override
	@Deprecated
	public GetAdditionalServiceRes getAdditionalService(GetAdditionalServiceReq request) {

		ServiceSubscriptionCheckEcReq serviceSubscriptionCheckEcReq = new ServiceSubscriptionCheckEcReq();
		serviceSubscriptionCheckEcReq.setUserMdn(request.getMsisdn());
		serviceSubscriptionCheckEcReq.setSvcCode(request.getSvcCode());
		ServiceSubscriptionCheckEcRes serviceSubscriptionCheckEcRes = this.idpSCI
				.serviceSubscriptionCheck(serviceSubscriptionCheckEcReq);

		LOGGER.debug("[getAdditionalService] SAC<-IDP Response {}", serviceSubscriptionCheckEcRes);

		GetAdditionalServiceRes response = new GetAdditionalServiceRes();
		response.setMsisdn(serviceSubscriptionCheckEcRes.getUserMdn());
		response.setSvcCode(serviceSubscriptionCheckEcRes.getSvcCode());
		response.setSvcJoinResult(serviceSubscriptionCheckEcRes.getSvcResult());
		LOGGER.debug("## Response {}", response);

		return response;
	}

}
