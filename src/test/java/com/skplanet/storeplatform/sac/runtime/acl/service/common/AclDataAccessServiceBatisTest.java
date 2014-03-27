package com.skplanet.storeplatform.sac.runtime.acl.service.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import com.skplanet.storeplatform.sac.runtime.acl.vo.Tenant;

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


	@Test
	public void selectTenant() {
		String pAuthKey = "S01";
		Tenant tenant = this.service.selectTenant(pAuthKey);
		System.out.println("tenant : " + tenant);
		assertEquals("S01", tenant.getTenantId());
		System.out.println("status="+tenant.getStatus());
		assertNotNull(tenant.getStatus());
	}

	@Test
	public void selectSystem() {
		String param = "S01-01002";
		com.skplanet.storeplatform.sac.runtime.acl.vo.System system = this.service.selectSystem(param);
		System.out.println("system : " + system);
		assertEquals("S01-01002", system.getSystemId());
		System.out.println("status="+system.getStatus());
		assertNotNull(system.getStatus());
	}
	@Test
	public void selectSystemByIp() {
		com.skplanet.storeplatform.sac.runtime.acl.vo.System param = new com.skplanet.storeplatform.sac.runtime.acl.vo.System();
		param.setSystemId("S01-01002");
		param.setIp("1.1.1.1");
		com.skplanet.storeplatform.sac.runtime.acl.vo.System system = this.service.selectSystemByIp(param);
		System.out.println("system : " + system);
		assertEquals("S01-01002", system.getSystemId());
		System.out.println("status="+system.getStatus());
		assertNotNull(system.getStatus());
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
