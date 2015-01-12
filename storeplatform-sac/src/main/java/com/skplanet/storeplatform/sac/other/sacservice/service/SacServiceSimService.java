package com.skplanet.storeplatform.sac.other.sacservice.service;

/**
 * SacServiceSimeService 인터페이스
 *
 * Created on 2014. 6. 11. by 서대영. SK 플래닛
 */
public interface SacServiceSimService {

	/**
	 * SKT에서 발급한 Sim Card 이냐?
	 */
	boolean belongsToSkt(String simOperator);

}
