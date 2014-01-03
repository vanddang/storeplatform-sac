package com.skplanet.storeplatform.sac.sample.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.ResourceAccessException;

import com.skplanet.storeplatform.external.client.sample.vo.Sample;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class SampleRepositoryImplTest {

	@Autowired
	private SampleRepositoryImpl repository;

	@Test(expected=ResourceAccessException.class)
	public void test() {
		Sample sample = new Sample(17);
		sample = this.repository.getDetailFromEC(sample);
		System.out.println(sample);
		assertEquals("#17", sample.getId());
	}

}
