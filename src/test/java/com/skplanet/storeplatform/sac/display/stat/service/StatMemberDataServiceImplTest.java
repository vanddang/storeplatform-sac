package com.skplanet.storeplatform.sac.display.stat.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.feature.product.vo.ListProduct;
import com.skplanet.storeplatform.sac.display.stat.vo.StatLike;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/spring-test/context-repository.xml"})
public class StatMemberDataServiceImplTest {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	private StatMemberDataServiceImpl service;

	@Before
	public void setUp() {
		this.service = new StatMemberDataServiceImpl();
		this.service.setCommonDAO(this.commonDAO);
	}

	
	@Test
	public void testSelectList() {
		StatLike like = new StatLike();
		like.setTenantId("S01");
		like.setUserKey("IM120000055187820140428114155");
		like.setStartKey(99);
		like.setCount(5);
		List<StatLike> list = service.selectStatLikeList(like);
		System.out.println("testSelectList()\n" +list);
	}
	
	@Test
	public void testSelectProdcut() {
		String prodId = "0000414028";
		ListProduct product = service.selectProdcut(prodId);
		System.out.println("# testSelectProdcut()#\n" + product);
	}

}
