package com.skplanet.storeplatform.sac.other.feedback.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.other.feedback.vo.MbrAvg;
import com.skplanet.storeplatform.sac.other.feedback.vo.ProdNoti;
import com.skplanet.storeplatform.sac.other.feedback.vo.ProdNotiGood;
import com.skplanet.storeplatform.sac.other.feedback.vo.TenantProdStats;

/**
 * 
 * Feedback Repository Test
 * 
 * Updated on : 2014. 2. 11. Updated by : 김현일, 인크로스.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@WebAppConfiguration
@TransactionConfiguration
@Transactional
public class FeedbackRepositoryTest {

	@Autowired
	private FeedbackRepository feedbackRepository;

	/**
	 * <pre>
	 * 기등록 상품후기 조회.
	 * </pre>
	 */
	@Test
	public void testGetRegProdNoti() {

		ProdNoti prodNoti = new ProdNoti();
		prodNoti.setTenantId("S01");
		prodNoti.setMbrNo("IW1425872523320130906100815");
		prodNoti.setProdId("H090123997");
		// prodNoti.setChnlId("CL00000642");

		ProdNoti ret = this.feedbackRepository.getRegProdNoti(prodNoti);
		assertNotNull(ret);
	}

	/**
	 * <pre>
	 * 상품후기 등록.
	 * </pre>
	 */
	@Test
	public void testInsertProdNoti() {
		ProdNoti prodNoti = new ProdNoti();
		prodNoti.setTenantId("S01");
		prodNoti.setNotiSeq("14280");
		prodNoti.setMbrNo("IW1023992275020110428164435");
		prodNoti.setProdId("0000045297");
		prodNoti.setTitle("aaa");
		prodNoti.setNotiDscr("bbb");
		prodNoti.setRegId("test");
		prodNoti.setMbrTelno("010");
		prodNoti.setFbPostYn("Y");
		prodNoti.setDeviceModelCd("SHA-400");
		prodNoti.setPkgVer("1234");

		int ret = (Integer) this.feedbackRepository.insertProdNoti(prodNoti);

		assertTrue(ret > 0);
	}

	/**
	 * <pre>
	 * 상품추천 삭제.
	 * </pre>
	 */
	@Test
	public void testDeleteProdNotiGood() {
		ProdNotiGood prodNotiGood = new ProdNotiGood();
		prodNotiGood.setTenantId("S01");
		prodNotiGood.setNotiSeq("14279");
		int ret = (Integer) this.feedbackRepository.deleteProdNotiGood(prodNotiGood);

		assertTrue(ret > 0);
	}

	/**
	 * <pre>
	 * 상품 후기 수정.
	 * </pre>
	 */
	@Test
	public void testUpdateProdNoti() {
		ProdNoti prodNoti = new ProdNoti();
		prodNoti.setTenantId("S01");
		prodNoti.setNotiSeq("14279");
		prodNoti.setMbrNo("IW1023992275020110428164435");
		prodNoti.setTitle("aaa");
		prodNoti.setNotiDscr("bbb");
		prodNoti.setDeviceModelCd("SHA-400");
		prodNoti.setPkgVer("1234");
		int ret = (Integer) this.feedbackRepository.updateProdNoti(prodNoti);

		assertTrue(ret > 0);
	}

	/**
	 * <pre>
	 * 상품 후기 삭제.
	 * </pre>
	 */
	@Test
	public void deleteProdNoti() {
		ProdNoti prodNoti = new ProdNoti();
		prodNoti.setTenantId("S01");
		prodNoti.setNotiSeq("14279");
		prodNoti.setNotiDscr("");
		prodNoti.setMbrNo("IW1023992275020110428164435");
		int ret = (Integer) this.feedbackRepository.deleteProdNoti(prodNoti);

		assertTrue(ret > 0);

	}

	/**
	 * <pre>
	 * 기등록 평점 조회.
	 * </pre>
	 */
	@Test
	public void testGetRegMbrAvg() {

		MbrAvg mbrAvg = new MbrAvg();
		mbrAvg.setTenantId("S01");
		mbrAvg.setMbrNo("IF1023064687020100310145002");
		mbrAvg.setProdId("0000017953");

		MbrAvg ret = this.feedbackRepository.getRegMbrAvg(mbrAvg);
		assertNotNull(ret);
	}

	/**
	 * <pre>
	 * 평점 등록/수정.
	 * </pre>
	 */
	@Test
	public void testMergeMbrAvg() {
		MbrAvg mbrAvg = new MbrAvg();
		mbrAvg.setTenantId("S01");
		mbrAvg.setMbrNo("IF1023064687020100310145002");
		mbrAvg.setProdId("0000017953");
		mbrAvg.setAvgScore("2");
		mbrAvg.setRegId("test1");

		int ret = (Integer) this.feedbackRepository.mergeMbrAvg(mbrAvg);

		assertTrue(ret > 0);
	}

	/**
	 * <pre>
	 * 상품평가정보 수정.
	 * </pre>
	 */
	@Test
	public void testUpdateTenantProdStats() {
		TenantProdStats tenantProdStats = new TenantProdStats();
		tenantProdStats.setTenantId("S01");
		tenantProdStats.setProdId("S000000439");
		tenantProdStats.setAvgEvluScore("0");
		tenantProdStats.setPreAvgScore("4");
		tenantProdStats.setUpdId("test1");
		int ret = (Integer) this.feedbackRepository.updateTenantProdStats(tenantProdStats);

		assertTrue(ret > 0);
	}

	/**
	 * <pre>
	 * 상품평가정보 수정.
	 * </pre>
	 */
	@Test
	public void testMergeTenantProdStats() {
		TenantProdStats tenantProdStats = new TenantProdStats();
		tenantProdStats.setTenantId("S01");
		tenantProdStats.setProdId("S000000439");
		tenantProdStats.setAvgEvluScore("0");
		tenantProdStats.setPreAvgScore("4");
		tenantProdStats.setRegId("test1");
		tenantProdStats.setUpdId("test1");
		int ret = (Integer) this.feedbackRepository.mergeTenantProdStats(tenantProdStats);

		assertTrue(ret > 0);
	}

	/**
	 * <pre>
	 * 탈퇴회원 사용후기 카운트 조회.
	 * </pre>
	 */
	@Test
	public void testGetProdNotiWDCount() {
		ProdNoti prodNoti = new ProdNoti();
		prodNoti.setTenantId("S01");
		prodNoti.setNotiSeq("14262");

		int ret = (Integer) this.feedbackRepository.getProdNotiWDCount(prodNoti);

		assertTrue(ret > 0);
	}

	/**
	 * <pre>
	 * 일반회원 사용후기 카운트 조회.
	 * </pre>
	 */
	@Test
	public void testGetProdNotiWDGoodCount() {
		ProdNoti prodNoti = new ProdNoti();
		prodNoti.setTenantId("S01");
		prodNoti.setNotiSeq("3068");
		prodNoti.setMbrNo("IF102815948420090820103525");
		prodNoti.setProdId("H900000017");

		int ret = (Integer) this.feedbackRepository.getProdNotiWDGoodCount(prodNoti);

		assertTrue(ret > 0);
	}

	/**
	 * <pre>
	 * 상품 추천 등록.
	 * </pre>
	 */
	@Test
	public void testInsertProdNotiGood() {
		ProdNotiGood prodNotiGood = new ProdNotiGood();
		prodNotiGood.setTenantId("S01");
		prodNotiGood.setNotiSeq("3068");
		prodNotiGood.setMbrNo("IF1028159484200908201035255");
		prodNotiGood.setRegId("test1");

		int ret = (Integer) this.feedbackRepository.insertProdNotiGood(prodNotiGood);

		assertTrue(ret > 0);
	}

	/**
	 * <pre>
	 * 탈퇴회원 상품추천  수정.
	 * </pre>
	 */
	@Test
	public void testUpdateProdNotiWDGood() {
		ProdNotiGood prodNotiGood = new ProdNotiGood();
		prodNotiGood.setTenantId("S01");
		prodNotiGood.setNotiSeq("14262");
		prodNotiGood.setAction("create");
		this.feedbackRepository.updateProdNotiWDGood(prodNotiGood);

		prodNotiGood.setAction("remove");
		int ret = (Integer) this.feedbackRepository.updateProdNotiWDGood(prodNotiGood);

		assertTrue(ret > 0);
	}

	/**
	 * <pre>
	 * 일반회원 상품 추천 카운트 조회.
	 * </pre>
	 */
	@Test
	public void testGetProdNotiGoodCount() {
		ProdNoti prodNoti = new ProdNoti();
		prodNoti.setTenantId("S01");
		prodNoti.setNotiSeq("3068");
		prodNoti.setMbrNo("IF102815948420090820103525");
		prodNoti.setProdId("H900000017");

		int ret = (Integer) this.feedbackRepository.getProdNotiGoodCount(prodNoti);

		assertTrue(ret > 0);
	}

	/**
	 * <pre>
	 * 일반회원 상품추천 등록.
	 * </pre>
	 */
	@Test
	public void testUpdateProdNotiGood() {
		ProdNotiGood prodNotiGood = new ProdNotiGood();
		prodNotiGood.setTenantId("S01");
		prodNotiGood.setNotiSeq("14262");
		prodNotiGood.setAction("create");
		this.feedbackRepository.updateProdNotiGood(prodNotiGood);

		prodNotiGood.setAction("remove");

		int ret = (Integer) this.feedbackRepository.updateProdNotiGood(prodNotiGood);

		assertTrue(ret > 0);
	}

	/**
	 * <pre>
	 * 사용후기 전체조회.
	 * </pre>
	 */
	@Test
	public void testGetProdNotiList() {

		// notiSeq
		// prodType
		// mbrNo
		// chnlId
		// startRow, endRow
		ProdNoti prodNoti = new ProdNoti();
		prodNoti.setStartRow("1");
		prodNoti.setEndRow("10");
		prodNoti.setProdId("0000000020");
		prodNoti.setMbrNo("IF110007722009061100144459");
		prodNoti.setOrderedBy("recent");
		this.feedbackRepository.getProdNotiList(prodNoti);
	}

	/**
	 * <pre>
	 * 사용후기 전체조회 카운트.
	 * </pre>
	 */
	@Test
	public void testGetProdNotiCount() {
		ProdNoti prodNoti = new ProdNoti();
		prodNoti.setStartRow("1");
		prodNoti.setEndRow("10");
		prodNoti.setProdId("0000000020");
		prodNoti.setMbrNo("IF110007722009061100144459");
		prodNoti.setOrderedBy("recent");
		this.feedbackRepository.getProdNotiCount(prodNoti);
	}

	/**
	 * <pre>
	 * 판매자 댓글 등록/수정/삭제.
	 * </pre>
	 */
	@Test
	public void testUpdateSellerResp() {
		ProdNoti prodNoti = new ProdNoti();
		prodNoti.setTenantId("S01");
		prodNoti.setNotiSeq("14262");
		prodNoti.setSellerRespOpin("저희 게임이 재미있다니 정말 다행이군요!.");
		prodNoti.setSellerRespTitle("고객님 감사합니다.");

		int ret = (Integer) this.feedbackRepository.updateSellerResp(prodNoti);

		assertTrue(ret > 0);
	}

	/**
	 * <pre>
	 * 판매자 댓글 등록/수정/삭제.
	 * </pre>
	 */
	@Test
	public void testUpdateSellerRespWD() {
		ProdNoti prodNoti = new ProdNoti();
		prodNoti.setTenantId("S01");
		prodNoti.setNotiSeq("14262");
		prodNoti.setSellerRespOpin("저희 게임이 재미있다니 정말 다행이군요!.");
		prodNoti.setSellerRespTitle("고객님 감사합니다.");

		int ret = (Integer) this.feedbackRepository.updateSellerRespWD(prodNoti);

		assertTrue(ret > 0);
	}

}
