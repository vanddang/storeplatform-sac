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
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateFeedbackSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateRecommendFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateSellerFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ModifyFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ModifyFeedbackSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ModifySellerFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.RemoveFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.RemoveSellerFeedbackSacReq;
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

	String userKey = "IW1425872523320130906108999";
	String prodId = "0000045297";

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
	public void testAll() {

		CreateFeedbackSacReq createFeedbackSacReq = new CreateFeedbackSacReq();
		createFeedbackSacReq.setUserKey(this.userKey);
		createFeedbackSacReq.setProdId(this.prodId);
		createFeedbackSacReq.setNotiTitle("SAC 사용후기 테스트");
		createFeedbackSacReq.setNotiDscr("SAC 사용후기 테스트 내용");
		createFeedbackSacReq.setUserId("test1");
		createFeedbackSacReq.setDeviceId("0101231234");
		createFeedbackSacReq.setFbPostYn("Y");
		createFeedbackSacReq.setPkgVer("");
		createFeedbackSacReq.setAvgScore("5");
		createFeedbackSacReq.setChnlId("");

		CreateFeedbackSacRes createFeedbackSacRes = this.feedbackService.create(createFeedbackSacReq,
				this.sacRequestHeader);

		ModifyFeedbackSacReq modifyFeedbackSacReq = new ModifyFeedbackSacReq();
		modifyFeedbackSacReq.setProdId(this.prodId);
		modifyFeedbackSacReq.setNotiSeq(createFeedbackSacRes.getNotiSeq());
		modifyFeedbackSacReq.setUserKey(this.userKey);
		modifyFeedbackSacReq.setUserId("test1");
		modifyFeedbackSacReq.setNotiTitle("SAC 수정 테스트.");
		modifyFeedbackSacReq.setNotiDscr("SAC 수정 테스트 내용.");
		modifyFeedbackSacReq.setPkgVer("1234");
		modifyFeedbackSacReq.setAvgScore("1");
		modifyFeedbackSacReq.setChnlId("");

		ModifyFeedbackSacRes modifyFeedbackSacRes = this.feedbackService.modify(modifyFeedbackSacReq,
				this.sacRequestHeader);

		RemoveFeedbackSacReq removeFeedbackSacReq = new RemoveFeedbackSacReq();
		removeFeedbackSacReq.setProdId(this.prodId);
		removeFeedbackSacReq.setUserKey(this.userKey);
		removeFeedbackSacReq.setUserId("test12");
		removeFeedbackSacReq.setNotiSeq(modifyFeedbackSacRes.getNotiSeq());
		this.feedbackService.remove(removeFeedbackSacReq, this.sacRequestHeader);

	}

	@Test
	public void testCreate() {

		CreateFeedbackSacReq createFeedbackSacReq = new CreateFeedbackSacReq();
		createFeedbackSacReq.setUserKey(this.userKey);
		createFeedbackSacReq.setProdId(this.prodId);
		createFeedbackSacReq.setNotiTitle("SAC 사용후기 테스트");
		createFeedbackSacReq.setNotiDscr("SAC 사용후기 테스트 내용");
		createFeedbackSacReq.setUserId("test1");
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
		modifyFeedbackSacReq.setProdId(this.prodId);
		modifyFeedbackSacReq.setNotiSeq("300000034");
		modifyFeedbackSacReq.setUserKey(this.userKey);
		modifyFeedbackSacReq.setUserId("test1");
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
		removeFeedbackSacReq.setProdId(this.prodId);
		removeFeedbackSacReq.setUserKey(this.userKey);
		removeFeedbackSacReq.setUserId("test12");
		removeFeedbackSacReq.setNotiSeq("300000034");
		this.feedbackService.remove(removeFeedbackSacReq, this.sacRequestHeader);
	}

	@Test
	public void testCreateRecommendWD() {
		CreateRecommendFeedbackSacReq createRecommendFeedbackSacReq = new CreateRecommendFeedbackSacReq();
		createRecommendFeedbackSacReq.setUserId("test123");
		createRecommendFeedbackSacReq.setUserKey("IW1425881009320130909120520");
		createRecommendFeedbackSacReq.setProdId("H090122031");
		createRecommendFeedbackSacReq.setNotiSeq("14262");
		this.feedbackService.createRecommend(createRecommendFeedbackSacReq, this.sacRequestHeader);
	}

	@Test
	public void testCreateRecommend() {
		CreateRecommendFeedbackSacReq createRecommendFeedbackSacReq = new CreateRecommendFeedbackSacReq();
		createRecommendFeedbackSacReq.setUserId("test123");
		createRecommendFeedbackSacReq.setUserKey("IW1425872523320130906100815");
		createRecommendFeedbackSacReq.setProdId("0000045297");
		createRecommendFeedbackSacReq.setNotiSeq("300000044");
		this.feedbackService.createRecommend(createRecommendFeedbackSacReq, this.sacRequestHeader);
	}

	@Test
	public void testCreateSellerFeedback() {
		CreateSellerFeedbackSacReq createSellerFeedbackSacReq = new CreateSellerFeedbackSacReq();

		createSellerFeedbackSacReq.setNotiSeq("300000010");
		createSellerFeedbackSacReq.setSellerRespTitle("고객님 감사합니다.");
		createSellerFeedbackSacReq.setSellerRespOpin("저희 게임이 재미있다니 정말 다행이군요!.");

		this.feedbackService.createSellerFeedback(createSellerFeedbackSacReq, this.sacRequestHeader);
	}

	@Test
	public void testModifySellerFeedback() {
		ModifySellerFeedbackSacReq modifySellerFeedbackSacReq = new ModifySellerFeedbackSacReq();

		modifySellerFeedbackSacReq.setNotiSeq("300000010");
		modifySellerFeedbackSacReq.setSellerRespTitle("고객님 감사합니다.");
		modifySellerFeedbackSacReq.setSellerRespOpin("저희 게임이 재미있다니 정말 다행이군요!.");

		this.feedbackService.modifySellerFeedback(modifySellerFeedbackSacReq, this.sacRequestHeader);
	}

	@Test
	public void testRemoveSellerFeedback() {
		RemoveSellerFeedbackSacReq removeSellerFeedbackSacReq = new RemoveSellerFeedbackSacReq();

		removeSellerFeedbackSacReq.setNotiSeq("300000010");

		this.feedbackService.removeSellerFeedback(removeSellerFeedbackSacReq, this.sacRequestHeader);
	}
}
