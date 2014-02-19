package com.skplanet.storeplatform.sac.other.feedback.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateFeedbackSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateRecommendFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateSellerFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.GetScoreSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListScorePaticpersSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ModifyFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ModifyFeedbackSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ModifySellerFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.RemoveFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.RemoveSellerFeedbackSacReq;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;

/**
 * 
 * Feedback Service Test
 * 
 * Updated on : 2014. 2. 11. Updated by : 홍길동, SK 플래닛.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class FeedbackServiceTest {

	@Autowired
	private FeedbackService feedbackService;

	private SacRequestHeader sacRequestHeader;

	String userKey = "IW1425872523320130906108999";
	String sellerKey = "IF1023501634320130913165229";
	String prodId = "0000045297";

	/**
	 * <pre>
	 * 초기화.
	 * </pre>
	 */
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

	/**
	 * <pre>
	 * 사용후기 등록/수정/삭제 테스트.
	 * </pre>
	 */
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

	/**
	 * <pre>
	 * 사용후기 등록 테스트.
	 * </pre>
	 */
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

	/**
	 * <pre>
	 * 사용후기 수정 테스트.
	 * </pre>
	 */
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

	/**
	 * <pre>
	 * 사용후기 삭제 테스트.
	 * </pre>
	 */
	@Test
	public void testRemove() {
		RemoveFeedbackSacReq removeFeedbackSacReq = new RemoveFeedbackSacReq();
		removeFeedbackSacReq.setProdId(this.prodId);
		removeFeedbackSacReq.setUserKey(this.userKey);
		removeFeedbackSacReq.setUserId("test12");
		removeFeedbackSacReq.setNotiSeq("300000034");
		this.feedbackService.remove(removeFeedbackSacReq, this.sacRequestHeader);
	}

	/**
	 * <pre>
	 * 탈퇴회원 추천.
	 * </pre>
	 */
	@Test
	public void testCreateRecommendWD() {
		CreateRecommendFeedbackSacReq createRecommendFeedbackSacReq = new CreateRecommendFeedbackSacReq();
		createRecommendFeedbackSacReq.setUserId("test123");
		createRecommendFeedbackSacReq.setUserKey("IW1425881009320130909120520");
		createRecommendFeedbackSacReq.setProdId("H090122031");
		createRecommendFeedbackSacReq.setNotiSeq("14262");
		this.feedbackService.createRecommend(createRecommendFeedbackSacReq, this.sacRequestHeader);
	}

	/**
	 * <pre>
	 * 일반회원 추천.
	 * </pre>
	 */
	@Test
	public void testCreateRecommend() {
		CreateRecommendFeedbackSacReq createRecommendFeedbackSacReq = new CreateRecommendFeedbackSacReq();
		createRecommendFeedbackSacReq.setUserId("test123");
		createRecommendFeedbackSacReq.setUserKey("IW1425872523320130906100815");
		createRecommendFeedbackSacReq.setProdId("0000045297");
		createRecommendFeedbackSacReq.setNotiSeq("300000044");
		this.feedbackService.createRecommend(createRecommendFeedbackSacReq, this.sacRequestHeader);
	}

	/**
	 * <pre>
	 * 판매자 댓글 등록 테스트.
	 * </pre>
	 */
	@Test
	public void testCreateSellerFeedback() {
		CreateSellerFeedbackSacReq createSellerFeedbackSacReq = new CreateSellerFeedbackSacReq();

		createSellerFeedbackSacReq.setProdId(this.prodId);
		createSellerFeedbackSacReq.setSellerKey(this.sellerKey);
		createSellerFeedbackSacReq.setNotiSeq("300000010");
		createSellerFeedbackSacReq.setSellerRespTitle("고객님 감사합니다.");
		createSellerFeedbackSacReq.setSellerRespOpin("저희 게임이 재미있다니 정말 다행이군요!.");

		this.feedbackService.createSellerFeedback(createSellerFeedbackSacReq, this.sacRequestHeader);
	}

	/**
	 * <pre>
	 * 판매자 댓글 수정 테스트.
	 * </pre>
	 */
	@Test
	public void testModifySellerFeedback() {
		ModifySellerFeedbackSacReq modifySellerFeedbackSacReq = new ModifySellerFeedbackSacReq();

		modifySellerFeedbackSacReq.setProdId(this.prodId);
		modifySellerFeedbackSacReq.setSellerKey(this.sellerKey);
		modifySellerFeedbackSacReq.setNotiSeq("300000010");
		modifySellerFeedbackSacReq.setSellerRespTitle("고객님 감사합니다.");
		modifySellerFeedbackSacReq.setSellerRespOpin("저희 게임이 재미있다니 정말 다행이군요!.");

		this.feedbackService.modifySellerFeedback(modifySellerFeedbackSacReq, this.sacRequestHeader);
	}

	/**
	 * <pre>
	 * 판매자 댓글 삭제 테스트.
	 * </pre>
	 */
	@Test
	public void testRemoveSellerFeedback() {
		RemoveSellerFeedbackSacReq removeSellerFeedbackSacReq = new RemoveSellerFeedbackSacReq();

		removeSellerFeedbackSacReq.setProdId(this.prodId);
		removeSellerFeedbackSacReq.setSellerKey(this.sellerKey);
		removeSellerFeedbackSacReq.setNotiSeq("300000010");

		this.feedbackService.removeSellerFeedback(removeSellerFeedbackSacReq, this.sacRequestHeader);
	}

	/**
	 * <pre>
	 * 평점, 다운로드 조회 테스트.
	 * </pre>
	 */
	@Test
	public void testGetScore() {
		GetScoreSacReq getScoreSacReq = new GetScoreSacReq();

		getScoreSacReq.setProdId(this.prodId);

		this.feedbackService.getScore(getScoreSacReq, this.sacRequestHeader);
	}

	/**
	 * <pre>
	 * 참여수 조회 테스트.
	 * </pre>
	 */
	@Test
	public void testListScoreParticpers() {
		ListScorePaticpersSacReq listScorePaticpersSacReq = new ListScorePaticpersSacReq();

		listScorePaticpersSacReq.setProdId(this.prodId);

		this.feedbackService.listScoreParticpers(listScorePaticpersSacReq, this.sacRequestHeader);
	}
}
