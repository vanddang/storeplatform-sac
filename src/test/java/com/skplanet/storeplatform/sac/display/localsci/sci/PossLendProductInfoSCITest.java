package com.skplanet.storeplatform.sac.display.localsci.sci;

import java.util.ArrayList;
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

import com.skplanet.storeplatform.sac.client.internal.display.localsci.sci.PossLendProductInfoSCI;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PossLendProductInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PossLendProductInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PossLendProductInfoSacRes;

/**
 * 
 * PossLendProductInfo SCI Test
 * 
 * 소장/대여 상품 정보 조회
 * 
 * Updated on : 2014. 4. 15. Updated by : 홍지호, 엔텔스
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
@Transactional
public class PossLendProductInfoSCITest {

	@Autowired
	private PossLendProductInfoSCI possLendProductInfoSCI;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * <pre>
	 * 소장/대여 상품 정보 조회.
	 * </pre>
	 * 
	 */
	@Test
	public void searchInAppPossLendProductInfo() {
		try {
			PossLendProductInfoSacReq req = new PossLendProductInfoSacReq();
			List<String> prodIdList = new ArrayList<String>();
			prodIdList.add("H001568883"); // 소장
			prodIdList.add("H001573660"); // 대여
			req.setProdIdList(prodIdList);

			List<String> possLendClsfCdList = new ArrayList<String>();
			possLendClsfCdList.add("DP010601"); // 소장
			possLendClsfCdList.add("DP010602"); // 대여
			req.setPossLendClsfCdList(possLendClsfCdList);

			req.setTenantId("S01");
			req.setLangCd("ko");

			PossLendProductInfoSacRes res = this.possLendProductInfoSCI.searchPossLendProductInfo(req);
			List<PossLendProductInfo> possLendProductInfoList = res.getPossLendProductInfoList();
			for (int i = 0; i < possLendProductInfoList.size(); i++) {
				PossLendProductInfo possLendProductInfo = possLendProductInfoList.get(i);
				this.log.debug("##### searchPossLendProductInfo possLendProductInfo[{}] : {}", i,
						possLendProductInfo.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
