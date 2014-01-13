/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.uaps.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.other.uaps.repository.UAPSRepository;

/**
 * 
 * UAPS SCI 연동 테스트
 * 
 * Updated on : 2014. 1. 6. Updated by : 김현일, 인크로스.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@TransactionConfiguration
@Transactional
public class UapsRepositoryTest {

	@Autowired
	private UAPSRepository uapsRepository;

	private final String pCustId = "";
	private final String pReqParam = "";
	private final String pType = "";

	/**
	 * 
	 * <pre>
	 * UAPS 연동규격서 3.2) 고객정보 조회
	 * 고객의 MDN, MIN, CLIENTID를 이용하여 명의자인증 조회.
	 * </pre>
	 */
	@Test
	public void shouldAuthorize() {
		this.uapsRepository.getAuthorize(this.pCustId, this.pReqParam, this.pType);
	}

	/**
	 * 
	 * <pre>
	 * UAPS 연동규격서 3.10.1.2) opmd child 정보조회.
	 * 고객의 모번호(MDN, MIN, CLIENTID, IMSI, 서비스관리번호)를 이용하여 자번호 리스트를 조회.
	 * </pre>
	 */
	@Test
	public void shouldOpmd() {
		this.uapsRepository.getOpmd(this.pReqParam);
	}

	/**
	 * 
	 * <pre>
	 * UAPS 연동규격서 3.10.1.2) opmd child 정보조회.
	 * 고객의 모번호(MDN, MIN, CLIENTID, IMSI, 서비스관리번호)를 이용하여 자번호 리스트를 조회.
	 * </pre>
	 */
	@Test
	public void shouldOpmdChild() {
		this.uapsRepository.getOpmdChild(this.pReqParam, this.pType);
	}

	/**
	 * 
	 * <pre>
	 * UAPS 연동규격서 3.2) 고객정보 조회
	 * 고객의 MDN, MIN, CLIENTID를 이용하여 MDN-MIN Mapping 정보조회.
	 * </pre>
	 */
	@Test
	public void shouldOpmdMapping() {
		this.uapsRepository.getMapping(this.pReqParam, this.pType);
	}

	/**
	 * 
	 * <pre>
	 * UAPS 연동규격서 (3.6.1) device 정보조회
	 * 고객의 MDN, MIN, ClientID를 이용하여 UAFMAP에 있는 단말제조+단말모델 코드를 조회할 때 사용한다.
	 * 고객의 MDN, MIN, ClientID, IMSI를 이용하여 SKT User Agent를 조회할 때 사용한다.
	 * </pre>
	 */
	@Test
	public void shouldDevice() {
		this.uapsRepository.getDevice(this.pReqParam, this.pType);
	}

	/**
	 * 
	 * <pre>
	 * UAPS 연동규격서 3.7) 한도소진 정보조회.
	 * 고객의 MDN, MIN, CLIENTID를 이용하여 한도 소진 정보를 한번에 조회할때 사용한다.
	 * 
	 * TingWirelessLimitYN : 팅무선인터넷차단서비스(NA00001147)
	 * TingParentsEaseYN : 팅부모안심(NA00002125)
	 * TingLimitYN : 팅한도소진(NA00002507)
	 * DataPerfectLimitYN : 데이터퍼팩트한도소진(NA00002452)
	 * TingInfoLimitYN : 팅 정보료 한도정지(NA00003098) (2011.02 규격추가)
	 * 의 값을 ‘Y’ 또는 ‘N’ 으로 return 한다.
	 * 
	 * 단, 일반CP는 사용할 수 없으며, 사업팀 요청에 의해 특정권한이 부여된 CP만 사용 가능함.
	 * 
	 * </pre>
	 */
	@Test
	public void shouldLimitSvc() {
		this.uapsRepository.getLimitSvc(this.pReqParam, this.pType);
	}
}
