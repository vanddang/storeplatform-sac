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
import com.skplanet.storeplatform.sac.runtime.acl.vo.AuthKey;
import com.skplanet.storeplatform.sac.runtime.acl.vo.Interface;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-repository.xml" })
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
	public void testInterfaceById() {
		String interfaceId = "I03000001";
		Interface intf = this.service.selectInterfaceById(interfaceId);
		System.out.println("# intf : " + intf);
		assertEquals("I03000001", intf.getInterfaceId());
		assertTrue(StringUtils.isNotBlank(intf.getUrl()));
	}

	@Test
	public void testSelectAuthKey() {
        String pAuthKey = "S01c61c0cad59adc6e03069e71614a5a0a3";
        AuthKey authKey = this.service.selectAuthKey(pAuthKey);
        System.out.println("authKey : " + authKey);
        assertEquals("S01", authKey.getTenantId());
	}

    /*
	@Test(expected = StorePlatformException.class)
	public void testValidateInterfaceForInvalidInterfaceID() {
		String authKey = "25f9aabf90acf38aa2e6d0da49e9eee75";
		Tenant tenant = this.service.selectTenantByAuthKey(authKey);
		System.out.println("tenant=" + tenant);
	}
	*/

}
