/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.common;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.external.client.uaps.sci.UAPSSCI;
import com.skplanet.storeplatform.external.client.uaps.vo.UserRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetOpmdReq;
import com.skplanet.storeplatform.sac.common.vo.Device;
import com.skplanet.storeplatform.sac.member.common.repository.MemberCommonRepository;
import com.skplanet.storeplatform.sac.member.common.vo.ClauseDTO;
import com.skplanet.storeplatform.sac.member.miscellaneous.service.MiscellaneousService;

/**
 * 공통 기능을 임시로 정의해서 사용한다.
 * 
 * Updated on : 2014. 1. 7. Updated by : 심대진, 다모아 솔루션.
 */
@Component
public class MemberCommonComponent {

	private static final Logger LOGGER = LoggerFactory.getLogger(MemberCommonComponent.class);

	@Autowired
	private MemberCommonRepository repository;

	@Autowired
	private MiscellaneousService miscellaneousService;

	@Autowired
	private UAPSSCI uapsSCI;

	/**
	 * <pre>
	 * 모번호 조회
	 * 989로 시작하는 MSISDN이 들어오면 모번호를 조회해서 반환하고, 
	 * 989로 시작하지 않는경우 파라미터로 받은 MSISDN을 그대로 반환.
	 * </pre>
	 * 
	 * @param msisdn
	 *            msisdn
	 * @return String
	 * @throws Exception
	 *             Exception
	 */
	public String getOpmdMdnInfo(String msisdn) throws Exception { // 2014. 01. 09. 김다슬, 인크로스. 수정
		GetOpmdReq req = new GetOpmdReq();
		req.setMsisdn(msisdn);

		return this.miscellaneousService.getOpmd(req).getMsisdn();
	}

	/**
	 * <pre>
	 * 필수 약관 동의 목록 조회.
	 * </pre>
	 * 
	 * @param tenantId
	 *            tenantId
	 * @return List<ClauseDTO>
	 * @throws Exception
	 *             Exception
	 */
	public List<ClauseDTO> getMandAgreeList(String tenantId) throws Exception {
		return this.repository.getMandAgreeList(tenantId);
	}

	/**
	 * <pre>
	 * 폰 정보 조회.
	 * </pre>
	 * 
	 * @param deviceModelCd
	 *            deviceModelCd
	 * @return Device
	 * @throws Exception
	 *             Exception
	 */
	public Device getPhoneInfo(String deviceModelCd) throws Exception {
		return this.repository.getPhoneInfo(deviceModelCd);
	}

	/**
	 * <pre>
	 * 고객정보조회
	 * 모번호 조회및 단말 정보 조회(USPS 정보와 서비스 관리번호 UA_CD 값이 같이 들어와야함.)
	 * TODO 기타 파트에서 api 개발 완료되면 확인해봐야함.
	 * </pre>
	 * 
	 * @param pReqParam
	 *            pReqParam
	 * @param type
	 *            type
	 * @return UserRes
	 * @throws Exception
	 *             Exception
	 */
	public UserRes getMappingInfo(String pReqParam, String type) throws Exception {
		LOGGER.info("## 기타 파트 API 미구현...... (1월 27일 완료 예정이라함.)");
		return this.uapsSCI.getMappingInfo(pReqParam, type);
	}

}
