package com.skplanet.storeplatform.sac.display.related.service;

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
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.related.vo.BoughtTogetherProduct;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath*:/spring-test/context-repository.xml"})
public class BoughtTogetherProductDataServiceImplTest {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	private BoughtTogetherProductDataServiceImpl svc;

	@Before
	public void setUp() {
		this.svc = new BoughtTogetherProductDataServiceImpl();
		this.svc.setCommonDAO(this.commonDAO);
	}

	@Test
	public void test() {
		BoughtTogetherProduct prd = new BoughtTogetherProduct();
		prd.setTenantId("S01");
		prd.setProductId("0000309883");
		prd.setExceptId("0000349719");
		prd.setOffset(1);
		prd.setCount(20);
		prd.setDeviceModelCd("ANY-PHONE-4APP");
		prd.setMmDeviceModelCd("ANY-PHONE-4APP");
		prd.setEbookSprtYn("Y");
		prd.setComicSprtYn("Y");
		prd.setMusicSprtYn("Y");
		prd.setSdVideoSprtYn("Y");
		prd.setVideoDrmSprtYn("Y");

		List<ProductBasicInfo> list = this.svc.selectList(prd);
		System.out.println("# size : " + list.size());
		System.out.println(list);
	}

}
