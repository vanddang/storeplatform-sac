package com.skplanet.storeplatform.sac.runtime.common.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Interface;
import com.skplanet.storeplatform.sac.runtime.common.vo.Bypass;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring-test/context-repository.xml"})
public class RoutingDataServiceImplTest {

	private RoutingDataServiceImpl svc;

	@Autowired
	private CommonDAO dao;

	@Before
	public void setUp() {
		this.svc = new RoutingDataServiceImpl();
		this.svc.setDao(this.dao);
	}

	@Test
	public void testSelectBypassByInterface() {
		Interface interfac = new Interface("I04000001");
		Bypass bypass = this.svc.selectBypassByInterface(interfac);
		System.out.println("# testSelectBypassByInterface :\n" + bypass);
		assertEquals("0050001", bypass.getBypassId());
	}

}
