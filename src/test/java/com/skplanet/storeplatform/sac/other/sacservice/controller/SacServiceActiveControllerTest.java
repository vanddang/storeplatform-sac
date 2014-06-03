package com.skplanet.storeplatform.sac.other.sacservice.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.skplanet.storeplatform.sac.client.other.vo.sacservice.GetActiveReq;
import com.skplanet.storeplatform.sac.client.other.vo.sacservice.GetActiveRes;
import com.skplanet.storeplatform.sac.other.sacservice.service.SacServiceDataServiceImpl;
import com.skplanet.storeplatform.sac.other.sacservice.service.SacServiceTypeServiceImpl;

public class SacServiceActiveControllerTest {

	private SacServiceActiveController controller;

	@Before
	public void setUp() {
		this.controller = new SacServiceActiveController();
		this.controller.setDataSvc(new SacServiceDataServiceImpl());
		this.controller.setTypeSvc(new SacServiceTypeServiceImpl());
	}

	@Test
	public void test() {
		GetActiveReq req = new GetActiveReq();
		req.setServiceCd("GAME_CASH_FLAT_RATE");
		GetActiveRes res = this.controller.getActive(req, null);
		assertTrue(res.isActive());
	}

}
