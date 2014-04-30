package com.skplanet.storeplatform.sac.display.localsci.sci;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.FreePassInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.EpisodeInfoReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.EpisodeInfoRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.EpisodeInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.FreePassInfoSacReq;

/**
 * 
 * FreePassInfo SCI Test
 * 
 * 정액제 상품 DRM 메타 정보 조회
 * 
 * Updated on : 2014. 2. 28. Updated by : 김형식 , 지티소프트
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@Transactional
public class FreePassInfoSCITest {

	@Autowired
	private FreePassInfoSCI freePassInfoSCI;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * <pre>
	 * 정액제 상품 DRM 메타 정보 조회.
	 * </pre>
	 * 
	 */
	// @Test
	public void searchFreePassDrmInfo() {
		try {
			FreePassInfoSacReq req = new FreePassInfoSacReq();

			req.setProdId("FR00000103");
			req.setEpisodeProdId("H000043061");
			req.setTenantId("S01");
			req.setLangCd("ko");

			FreePassInfo res = this.freePassInfoSCI.searchFreePassDrmInfo(req);
			this.log.debug("##### FreePassInfo getProdId[{}] : {}", res.getProdId());
			this.log.debug("##### FreePassInfo getEpisodeProdId[{}] : {}", res.getEpisodeProdId());
			this.log.debug("##### FreePassInfo getDrmYn[{}] : {}", res.getDrmYn());
			this.log.debug("##### FreePassInfo getProdStatusCd[{}] : {}", res.getProdStatusCd());
			this.log.debug("##### FreePassInfo getTopMenuId[{}] : {}", res.getTopMenuId());
			this.log.debug("##### FreePassInfo getUsePeriod[{}] : {}", res.getUsePeriod());
			this.log.debug("##### FreePassInfo getUsePeriodUnitCd[{}] : {}", res.getUsePeriodUnitCd());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * <pre>
	 * 정액권의 에피소드 상품 목록 조회.
	 * </pre>
	 * 
	 */
	@Test
	public void getFreepassList() {
		try {
			EpisodeInfoReq req = new EpisodeInfoReq();
			// Q/A 값은 H001610176
			// 개발은 FR00000103
			req.setProdId("FR00000103");
			req.setTenantId("S01");
			req.setLangCd("ko");
			req.setDeviceModelCd("SHW-M100S");
			req.setCmpxProdClsfCd("OR004303");

			EpisodeInfoSacRes res = this.freePassInfoSCI.searchEpisodeList(req);
			List<EpisodeInfoRes> freePassInfoResList = res.getFreePassInfoRes();
			for (EpisodeInfoRes vo : freePassInfoResList) {
				this.log.debug("##### FreePassInfo getProdId[{}] : {}", vo.getProdId());
				this.log.debug("##### FreePassInfo getDrmYn[{}] : {}", vo.getDrmYn());
				this.log.debug("##### FreePassInfo getProdSprtYn[{}] : {}", vo.getProdSprtYn());
				this.log.debug("##### FreePassInfo getProdStatusCd[{}] : {}", vo.getProdStatusCd());
				this.log.debug("##### FreePassInfo getUsePeriod[{}] : {}", vo.getUsePeriod());
				this.log.debug("##### FreePassInfo getUsePeriodUnitCd[{}] : {}", vo.getUsePeriodUnitCd());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
