package com.skplanet.storeplatform.sac.external.idp.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@TransactionConfiguration
@Transactional
public class IdpSacServiceImplTest {

	// @Mock
	// private IdpSacRepository repository;

	@Autowired
	private IdpSacService service;

	@Test
	public void testAlredyJoinCheckByEmail() {

		try {

			this.service.alredyJoinCheckByEmail("aaaaa@gmail.com");
			this.service.alredyJoinCheckBySn("1111111111111");
			this.service.alredyJoinCheck("1", "1");
			this.service.checkDupID("ididididid");
			this.service.realNameAuthForNative("name", "1111111111111");
			this.service.join4Wap("0101112222");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
