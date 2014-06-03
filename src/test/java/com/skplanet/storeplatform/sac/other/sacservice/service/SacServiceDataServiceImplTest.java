package com.skplanet.storeplatform.sac.other.sacservice.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.skplanet.storeplatform.sac.other.sacservice.vo.SacService;

public class SacServiceDataServiceImplTest {

	private SacServiceDataServiceImpl dataSvc;

	@Before
	public void setUp() {
		this.dataSvc = new SacServiceDataServiceImpl();
	}

	@Test
	public void testGetServiceActiveForActive() {
		SacService vo = new SacService();
		vo.setServiceCd("GAME_CASH_FLAT_RATE");
		this.dataSvc.getServiceActive(vo);
		assertTrue(vo.isActive());
	}

	@Test
	public void testGetServiceActiveForInactive() {
		SacService vo = new SacService();
		vo.setServiceCd("GAME_CASH_FLAT_RATE2");
		this.dataSvc.getServiceActive(vo);
		assertFalse(vo.isActive());
	}

}
