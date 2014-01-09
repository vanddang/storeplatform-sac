package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.uaps.sci.UAPSSCI;
import com.skplanet.storeplatform.external.client.uaps.vo.OpmdRes;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetUaCodeRes;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;

/**
 * 
 * 기타 기느능 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 */
@Service
public class MiscellaneousServiceImpl implements MiscellaneousService {
	private static final Logger logger = LoggerFactory.getLogger(MiscellaneousServiceImpl.class);

	@Autowired
	private UAPSSCI uapsSCI;

	@Autowired
	private UserSCI userSCI;

	@Autowired
	private DeviceSCI deviceSCI;

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDao;

	@Autowired
	private MemberCommonComponent commonComp;

	@Override
	public GetOpmdRes getOpmd(GetOpmdReq req) throws Exception {
		String msisdn = req.getMsisdn();

		GetOpmdRes res = new GetOpmdRes();
		res.setMsisdn(msisdn);

		// 자번호(989)가 아닌 경우, request로 전달된 msisdn 그대로 반환
		if (StringUtils.substring(msisdn, 0, 3).equals("989")) {
			logger.info("## 모번호 조회 START");
			OpmdRes opmdRes = this.uapsSCI.getOpmdInfo(msisdn);
			if (opmdRes.getResultCode() == 0) {
				res.setMsisdn(opmdRes.getMobileMdn());
			} else {
				throw new RuntimeException("UAPS 연동 오류");
			}

		}
		return res;
	}

	@Override
	public GetUaCodeRes getUaCode(GetUaCodeReq request) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
