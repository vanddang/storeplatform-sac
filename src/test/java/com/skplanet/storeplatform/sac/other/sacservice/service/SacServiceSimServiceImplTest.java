package com.skplanet.storeplatform.sac.other.sacservice.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class SacServiceSimServiceImplTest {

	private SacServiceSimServiceImpl svc;

	@Before
	public void setUp() {
		this.svc = new SacServiceSimServiceImpl();
	}

	@Test
	public void test() {
		assertFalse(this.svc.belongsToSkt(null));
		assertFalse(this.svc.belongsToSkt(""));
		assertFalse(this.svc.belongsToSkt(" "));
		assertFalse(this.svc.belongsToSkt("123/01"));

		assertFalse(this.svc.belongsToSkt("450/02"));
		assertTrue(this.svc.belongsToSkt("450/03"));
		assertFalse(this.svc.belongsToSkt("450/04"));
		assertTrue(this.svc.belongsToSkt("450/05"));
		assertFalse(this.svc.belongsToSkt("450/06"));
		assertFalse(this.svc.belongsToSkt("450/08"));
		assertTrue(this.svc.belongsToSkt("450/11"));
	}

}

