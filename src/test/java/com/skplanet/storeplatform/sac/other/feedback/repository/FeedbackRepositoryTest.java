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

@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@WebAppConfiguration
@TransactionConfiguration
@Transactional
public class FeedbackRepositoryTest {

	@Autowired
	private FeedbackRepository feedbackRepository;

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

	@Test
	public void testDeleteProdNotiGood() {
		ProdNotiGood prodNotiGood = new ProdNotiGood();
		prodNotiGood.setTenantId("S01");
		prodNotiGood.setNotiSeq("14279");
		int ret = (Integer) this.feedbackRepository.deleteProdNotiGood(prodNotiGood);

		assertTrue(ret > 0);
	}

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

	@Test
	public void testGetRegMbrAvg() {

		MbrAvg mbrAvg = new MbrAvg();
		mbrAvg.setTenantId("S01");
		mbrAvg.setMbrNo("IF1023064687020100310145002");
		mbrAvg.setProdId("0000017953");

		MbrAvg ret = this.feedbackRepository.getRegMbrAvg(mbrAvg);
		assertNotNull(ret);
	}

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

	@Test
	public void testGetProdNotiWDCount() {
		ProdNoti prodNoti = new ProdNoti();
		prodNoti.setTenantId("S01");
		prodNoti.setNotiSeq("14262");

		int ret = (Integer) this.feedbackRepository.getProdNotiWDCount(prodNoti);

		assertTrue(ret > 0);
	}

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
