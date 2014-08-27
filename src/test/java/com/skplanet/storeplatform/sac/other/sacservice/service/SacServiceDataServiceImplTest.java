package com.skplanet.storeplatform.sac.other.sacservice.service;

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
import com.skplanet.storeplatform.sac.other.sacservice.vo.SacService;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath*:/spring-test/context-repository.xml"})
public class SacServiceDataServiceImplTest {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	
	private SacServiceDataServiceImpl svc;
	
	@Before
	public void setUp() {
		svc = new SacServiceDataServiceImpl();
		svc.setCommonDAO(commonDAO);
	}
	
	@Test
	public void testSelectOne() {
		SacService vo = svc.selectService("tstore.test");
		System.out.println("<<< testDetail() >>>\n" + vo);
	}
	
	@Test
	public void testSelectSimOperatorList() {
		List<String> list = svc.selectSimOperatorList("tstore.gamecash.flatrate");
		System.out.println("<<< testSelectSimOperatorList >>>\n" + list);
	}
	
	@Test
	public void testSelectModel() {
		List<String> list = svc.selectModelList("tstore.test");
		System.out.println("<<< testSelectModel >>>\n" + list);
	}

}
