package com.skplanet.storeplatform.sac.runtime.acl.service.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Interface;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/spring-test/context-repository.xml"})
@WebAppConfiguration
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class AclDataAccessServiceBatisTest {

	private AclDataAccessServiceBatis service;

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDao;


	@Before
	public void setUp() {
		this.service = new AclDataAccessServiceBatis(this.commonDao);
	}


	@Test
	public void test() {
		String interfaceId = "I03000001";
		Interface intf = this.service.selectInterfaceById(interfaceId);
		System.out.println("# intf : " + intf);
		assertEquals("I03000001", intf.getInterfaceId());
		assertTrue(StringUtils.isNotBlank(intf.getUrl()));
	}

}
