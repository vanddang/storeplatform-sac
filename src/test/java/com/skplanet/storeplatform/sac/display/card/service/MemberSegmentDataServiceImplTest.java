package com.skplanet.storeplatform.sac.display.card.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.card.vo.MemberSegment;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/spring-test/context-repository.xml"})
public class MemberSegmentDataServiceImplTest {

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	private MemberSegmentDataServiceImpl service;

	@Before
	public void setUp() {
		this.service = new MemberSegmentDataServiceImpl();
		this.service.setCommonDAO(this.commonDAO);
	}
	
	@Test
	public void testSelectList() {
		String tenantId = "S01";
		String userKey = "IM142100006038855201402131159";
		MemberSegment memberSegment = service.selectMemberSegment(tenantId, userKey);
		System.out.println(memberSegment);
	}
	
}
