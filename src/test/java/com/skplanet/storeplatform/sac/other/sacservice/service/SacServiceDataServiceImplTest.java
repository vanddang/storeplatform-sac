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
		this.dataSvc.setSimSvc(new SacServiceSimServiceImpl());
	}

	@Test
	public void testGetServiceActiveWrongForInactive() {
		SacService vo = new SacService();
		vo.setServiceCd("tstore.gamecash.flatrate.v2");
		vo.setSimOperator("450/05");
		this.dataSvc.getServiceActive(vo);
		assertFalse(vo.isActive());
	}

	@Test
	public void testGetServiceActiveCashForActive() {
		SacService vo = new SacService();
		vo.setServiceCd("tstore.gamecash.flatrate");
		vo.setSimOperator("450/05");
		this.dataSvc.getServiceActive(vo);
		assertTrue(vo.isActive());
	}

	@Test
	public void testGetServiceActiveCashForInactive1() {
		SacService vo = new SacService();
		vo.setServiceCd("tstore.gamecash.flatrate");
		vo.setSimOperator("450/08");
		this.dataSvc.getServiceActive(vo);
		assertFalse(vo.isActive());
	}

	@Test
	public void testGetServiceActiveMileageForActive1() {
		SacService vo = new SacService();
		vo.setServiceCd("tstore.mileage");
		vo.setSimOperator("450/05");
		this.dataSvc.getServiceActive(vo);
		assertTrue(vo.isActive());
	}

	@Test
	public void testGetServiceActiveForMileageActive2() {
		SacService vo = new SacService();
		vo.setServiceCd("tstore.mileage");
		vo.setSimOperator("450/08");
		this.dataSvc.getServiceActive(vo);
		assertTrue(vo.isActive());
	}

}
