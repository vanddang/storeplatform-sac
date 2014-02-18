package com.skplanet.storeplatform.sac.display.localsci.sci;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.ChangeDisplayUserSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ChangeDisplayUserSacReq;

/**
 * 
 * ChangeDisplayUser SCI Test
 * 
 * 전시 테이블 사용자 ID, KEY 변경 테스트 작성
 * 
 * Updated on : 2014. 2. 14. Updated by : 김현일, 인크로스
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@Transactional
public class ChangeDisplayUserSCITest {

	@Autowired
	private ChangeDisplayUserSCI changeDisplayUserSCI;

	/**
	 * 
	 * <pre>
	 * 사용자 ID 변경.
	 * </pre>
	 */
	@Test
	public void testChangeUserId() {
		ChangeDisplayUserSacReq changeDisplayUserSacReq = new ChangeDisplayUserSacReq();
		changeDisplayUserSacReq.setTenantId("S01");
		changeDisplayUserSacReq.setOldUserId("test1");
		changeDisplayUserSacReq.setNewUserId("test2");
		this.changeDisplayUserSCI.changeUserId(changeDisplayUserSacReq);
	}

	/**
	 * 
	 * <pre>
	 * 사용자 KEY 변경.
	 * </pre>
	 */
	@Test
	public void testChangeUserKey() {
		ChangeDisplayUserSacReq changeDisplayUserSacReq = new ChangeDisplayUserSacReq();
		changeDisplayUserSacReq.setTenantId("S01");
		changeDisplayUserSacReq.setOldUserKey("test3");
		changeDisplayUserSacReq.setNewUseKey("test4");
		this.changeDisplayUserSCI.changeUserKey(changeDisplayUserSacReq);
	}
}
