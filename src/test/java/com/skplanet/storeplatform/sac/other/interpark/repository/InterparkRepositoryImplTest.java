package com.skplanet.storeplatform.sac.other.interpark.repository;

import org.junit.Test;

import com.skplanet.storeplatform.external.client.interpark.vo.AuthKeyReq;

public class InterparkRepositoryImplTest {

	private InterparkRepositoryImpl repository;

	@Test
	public void test() {
		AuthKeyReq req = new AuthKeyReq();
		req.setType("order");
		req.setEbFileNo("100656");
		req.setRevOrdNo("00000000000000000001");
		this.repository.getAuthKeyFromEC(req);
	}

}
