package com.skplanet.storeplatform.sac.member.common.idp.sci;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.skplanet.storeplatform.external.client.idp.sci.IdpSCI;
import com.skplanet.storeplatform.external.client.idp.vo.AuthForWapEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.AuthForWapEcRes;
import com.skplanet.storeplatform.external.client.idp.vo.CheckDupIdEcReq;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;

/**
 * IDP SCI
 * 
 * Updated on : 2014. 2. 11. Updated by : 김경복, 부르칸
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class IdpSCITest {

	private static final Logger LOGGER = LoggerFactory.getLogger(IdpSCITest.class);

	@Autowired
	private IdpSCI idpSCI;

	@Test
	public void test() {
		CheckDupIdEcReq req = new CheckDupIdEcReq();
		 req.setUserId("asdawdad11");
		try {
			this.idpSCI.checkDupId(req);
		} catch (StorePlatformException e) {
			LOGGER.debug("ErroeInfo : \n{}", e.getErrorInfo().toString());
			throw e;
		}
	}

	@Test
	public void authForWapTest() {
		AuthForWapEcReq req = new AuthForWapEcReq();
		 req.setUserMdn("01066786263");
		AuthForWapEcRes res = this.idpSCI.authForWap(req);
		 res.getUserMdn();
	}

}
