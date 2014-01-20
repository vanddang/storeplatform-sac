package com.skplanet.storeplatform.sac.other.interpark.repository;

import org.junit.Test;

import com.skplanet.storeplatform.external.client.interpark.vo.AuthKeyReq;

/**
 * 
 * Interpark Repository Test
 * 
 * Updated on : 2014. 1. 20. Updated by : 김현일, 인크로스.
 */
public class InterparkRepositoryImplTest {

	private InterparkRepositoryImpl repository;

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 */
	@Test
	public void test() {
		AuthKeyReq req = new AuthKeyReq();
		req.setType("order");
		req.setEbFileNo("100656");
		req.setRevOrdNo("00000000000000000001");
		this.repository.getAuthKeyFromEC(req);
	}

}
