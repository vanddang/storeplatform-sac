package com.skplanet.storeplatform.sac.display.localsci.sci;

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
	@Test
	public void searchFreePassDrmInfo() {
		try {
			FreePassInfoSacReq req = new FreePassInfoSacReq();

			req.setProdId("H900067755");
			req.setEpisodeProdId("H900565545");
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
}
