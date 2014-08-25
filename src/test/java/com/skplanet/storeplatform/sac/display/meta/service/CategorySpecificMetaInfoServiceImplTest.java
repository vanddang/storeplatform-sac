package com.skplanet.storeplatform.sac.display.meta.service;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath*:/spring-test/context-repository.xml"})
public class CategorySpecificMetaInfoServiceImplTest {
	
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	
	private CategorySpecificMetaInfoServiceImpl svc;
	
	@Before
	public void setUp() {
		svc = new CategorySpecificMetaInfoServiceImpl();
		svc.setCommonDAO(commonDAO);
	}

	@Test
	public void testGetSpecificEbookList() {
		RequestContextHolder.currentRequestAttributes().setAttribute("useCache", false, RequestAttributes.SCOPE_REQUEST);
		
		TenantHeader tenantHeader = new TenantHeader();
		tenantHeader.setTenantId("S01");
		tenantHeader.setLangCd("ko");
		
		ProductBasicInfo productBasicInfo = new ProductBasicInfo();
		productBasicInfo.setContentsTypeCd("PD002502");
		productBasicInfo.setPartProdId("H900179741");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
		paramMap.put("tenantHeader", tenantHeader);
		paramMap.put("productBasicInfo", productBasicInfo);
		
		MetaInfo meta = svc.getSpecificEbookList(paramMap);
		System.out.println("### testGetSpecificEbookList() ###\n" + meta);
		assertEquals("H900108521", meta.getProdId());
	}

}
