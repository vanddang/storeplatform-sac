package com.skplanet.storeplatform.sac.display.feature.category.service;

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
import com.skplanet.storeplatform.sac.client.display.vo.feature.category.FeatureCategoryEpubSacReq;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/spring-test/context-repository.xml"})
public class FeatureCategoryEpubDataServiceImplTest {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	private FeatureCategoryEpubDataServiceImpl service;
	
	@Before
	public void setUp() {
		service = new FeatureCategoryEpubDataServiceImpl();
		service.setCommonDAO(commonDAO);
	}
	
	/**
	 * /display/feature/category/epub/list/v1?listId=ADM000000013&topMenuId=DP14&prodCharge=N&count=5
	 */
	@Test
	public void test() {
		FeatureCategoryEpubSacReq req = new FeatureCategoryEpubSacReq();
		req.setListId("ADM000000013");
		req.setProdCharge("N");
		req.setTopMenuId("DP13");
		req.setFilteredBy("ebook+normal");
		req.setOffset(1);
		req.setCount(5);
		req.setTenantId("S01");
		req.setLangCd("ko");
		req.setDeviceModelCd("SHW-M110S");
		req.setAnyDeviceModelCd("ANY-PHONE-4MM");
		req.setStdDt("20140101000000");
		List<ProductBasicInfo> list = service.selectCategoryEpubRecomList(req);
		System.out.println(list);
	}

}
