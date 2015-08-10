package com.skplanet.storeplatform.sac.example.sample.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.skplanet.storeplatform.external.client.example.sample.vo.Sample;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class SampleServiceImplTest {

	@Autowired
	private SampleServiceImpl service;


	@Test
	public void test() {
		Sample actualOutput = this.service.findOneFromRemoteSci(17);
		assertEquals(17, actualOutput.getNo().intValue());
	}

}
