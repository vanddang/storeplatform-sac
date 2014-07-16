package com.skplanet.storeplatform.sac.display.menu.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.menu.vo.Menu;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath*:/spring-test/context-repository.xml"})
public class MenuDataServiceImplTest {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	private MenuDataServiceImpl svc;

	@Before
	public void setUp() {
		this.svc = new MenuDataServiceImpl();
		this.svc.setCommonDAO(this.commonDAO);
	}

	@Test
	public void testSelectBestMenuList() {
		TenantHeader tenant = new TenantHeader();
		tenant.setTenantId("S01");
		tenant.setSystemId("S01-01002");

		String menuCategoryCd = "DP01150101";

		List<Menu> list = this.svc.selectBestMenuList(tenant, menuCategoryCd);
		System.out.println("# testSelectBestMenuList()\n" + list);
	}

}
