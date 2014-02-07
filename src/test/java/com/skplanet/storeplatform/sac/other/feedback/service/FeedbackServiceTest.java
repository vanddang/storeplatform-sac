package com.skplanet.storeplatform.sac.other.feedback.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ModifyFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.RemoveFeedbackSacReq;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@WebAppConfiguration
@TransactionConfiguration
@Transactional
public class FeedbackServiceTest {

	@Autowired
	private FeedbackService feedbackService;

	private SacRequestHeader sacRequestHeader;

	@Before
	public void before() {
		this.sacRequestHeader = new SacRequestHeader();
		TenantHeader tenantHeader = new TenantHeader();
		tenantHeader.setTenantId("S01");
		this.sacRequestHeader.setTenantHeader(tenantHeader);
		DeviceHeader deviceHeader = new DeviceHeader();
		deviceHeader.setModel("SH-440");
		this.sacRequestHeader.setDeviceHeader(deviceHeader);
	}

	@Test
	public void testCreate() {
		CreateFeedbackSacReq createFeedbackSacReq = new CreateFeedbackSacReq();
		createFeedbackSacReq.setUserKey("IW1425872523320130906100815");
		createFeedbackSacReq.setProdId("0000045297");
		createFeedbackSacReq.setNotiTitle("SAC 사용후기 테스트");
		createFeedbackSacReq.setNotiDscr("SAC 사용후기 테스트 내용");
		createFeedbackSacReq.setUserId("");
		createFeedbackSacReq.setDeviceId("0101231234");
		createFeedbackSacReq.setFbPostYn("Y");
		createFeedbackSacReq.setPkgVer("");
		createFeedbackSacReq.setAvgScore("5");
		createFeedbackSacReq.setChnlId("");
		this.feedbackService.create(createFeedbackSacReq, this.sacRequestHeader);
	}

	@Test
	public void testModify() {
		ModifyFeedbackSacReq modifyFeedbackSacReq = new ModifyFeedbackSacReq();
		modifyFeedbackSacReq.setProdId("0000045297");
		modifyFeedbackSacReq.setNotiSeq("14279");
		modifyFeedbackSacReq.setUserKey("IW1023992275020110428164435");
		modifyFeedbackSacReq.setNotiTitle("SAC 수정 테스트.");
		modifyFeedbackSacReq.setNotiDscr("SAC 수정 테스트 내용.");
		modifyFeedbackSacReq.setPkgVer("1234");
		modifyFeedbackSacReq.setAvgScore("1");
		modifyFeedbackSacReq.setChnlId("");
		this.feedbackService.modify(modifyFeedbackSacReq, this.sacRequestHeader);
	}

	@Test
	public void testRemove() {
		RemoveFeedbackSacReq removeFeedbackSacReq = new RemoveFeedbackSacReq();
		removeFeedbackSacReq.setProdId("H090123997");
		removeFeedbackSacReq.setUserKey("IW1425872523320130906100815");
		removeFeedbackSacReq.setNotiSeq("14255");
		this.feedbackService.remove(removeFeedbackSacReq, this.sacRequestHeader);
	}
}
