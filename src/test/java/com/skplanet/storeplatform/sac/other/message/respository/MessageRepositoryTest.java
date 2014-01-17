/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.message.respository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.other.message.common.MessageCommon;
import com.skplanet.storeplatform.sac.other.message.repository.MessageRepository;

/**
 * 
 * Message Repository Test
 * 
 * Updated on : 2014. 1. 15. Updated by : 김현일, 인크로스.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@WebAppConfiguration
@TransactionConfiguration
@Transactional
public class MessageRepositoryTest {

	@Autowired
	private MessageRepository messageRepository;

	/**
	 * 
	 * <pre>
	 * Message Reposiory SMS 전송 기능 테스트.
	 * </pre>
	 */
	@Test
	public void testSmsSend() {
		Map<String, String> resultMap = this.messageRepository.smsSend(MessageCommon.getSmsSendReq());
		//
		assertNotNull(resultMap);
		assertThat(resultMap.get("resultStatus"), is("success"));
	}

	// @Test
	// public void testEmailSend() {
	//
	// int result = (Integer) this.messageRepository.emailSend(MessageCommon.getEmailSendReq());
	// //
	// assertThat(result, is(1));
	// }
}
