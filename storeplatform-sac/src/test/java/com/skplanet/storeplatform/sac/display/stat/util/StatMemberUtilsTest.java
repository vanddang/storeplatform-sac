package com.skplanet.storeplatform.sac.display.stat.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class StatMemberUtilsTest {

	@Test
	public void test1() {
		int reqCount = 10;
		int dbListSize = 11;
		
		assertEquals(10, StatMemberUtils.getResCount(dbListSize, reqCount));
		assertTrue(StatMemberUtils.hasResNext(dbListSize, reqCount));
	}
	
	@Test
	public void test2() {
		int reqCount = 10;
		int dbListSize = 3;
		
		assertEquals(3, StatMemberUtils.getResCount(dbListSize, reqCount));
		assertFalse(StatMemberUtils.hasResNext(dbListSize, reqCount));
	}
	
	@Test
	public void test3() {
		int reqCount = 0;
		int dbListSize = 1;
		
		assertEquals(0, StatMemberUtils.getResCount(dbListSize, reqCount));
		assertTrue(StatMemberUtils.hasResNext(dbListSize, reqCount));
	}
	
	@Test
	public void test4() {
		int reqCount = 10;
		int dbListSize = 0;
		
		assertEquals(0, StatMemberUtils.getResCount(dbListSize, reqCount));
		assertFalse(StatMemberUtils.hasResNext(dbListSize, reqCount));
	}

}
