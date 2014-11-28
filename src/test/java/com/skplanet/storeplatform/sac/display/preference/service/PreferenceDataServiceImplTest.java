package com.skplanet.storeplatform.sac.display.preference.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/spring-test/context-test.xml"})
public class PreferenceDataServiceImplTest {

	@Autowired
	private PreferenceDataServiceImpl svc;
	
	@Test
	public void test() {
	
	}

}
