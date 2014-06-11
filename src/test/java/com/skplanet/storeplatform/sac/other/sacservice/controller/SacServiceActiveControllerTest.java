package com.skplanet.storeplatform.sac.other.sacservice.controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.skplanet.storeplatform.sac.client.other.vo.sacservice.GetActiveReq;
import com.skplanet.storeplatform.sac.client.other.vo.sacservice.GetActiveRes;
import com.skplanet.storeplatform.sac.other.sacservice.service.SacServiceDataService;
import com.skplanet.storeplatform.sac.other.sacservice.service.SacServiceTypeServiceImpl;
import com.skplanet.storeplatform.sac.other.sacservice.vo.SacService;

@RunWith(MockitoJUnitRunner.class)
public class SacServiceActiveControllerTest {

	@InjectMocks
	private SacServiceActiveController controller;

	@Mock
	private SacServiceDataService dataSvc;

	@Before
	public void setUp() {
		this.controller.setTypeSvc(new SacServiceTypeServiceImpl());
	}

	@Test
	public void test() {
		String serviceCd = "tstore.gamecash.flatrate";
		String simOperator = "450/05";

		GetActiveReq req = new GetActiveReq();
		req.setServiceCd(serviceCd);
		req.setSimOperator(simOperator);

		SacService vo1 = new SacService();
		vo1.setServiceCd(serviceCd);
		vo1.setSimOperator(simOperator);

		SacService vo2 = new SacService();
		vo2.setServiceCd(serviceCd);
		vo2.setSimOperator(simOperator);
		vo2.setActive(true);

		when(this.dataSvc.getServiceActive(vo1)).thenReturn(vo2);

		GetActiveRes res = this.controller.getActive(req, null);
		assertTrue(res.isActive());
	}

}
