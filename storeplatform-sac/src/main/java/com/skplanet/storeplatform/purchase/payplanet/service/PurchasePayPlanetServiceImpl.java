package com.skplanet.storeplatform.purchase.payplanet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.purchase.client.common.vo.PpProperty;

@Service
public class PurchasePayPlanetServiceImpl implements PurchasePayPlanetService {
	@Autowired
	@Qualifier("scPurchase")
	private CommonDAO commonDao;

	/**
	 * 
	 * <pre>
	 * Pay Planet 가맹점 정보 조회.
	 * </pre>
	 * 
	 * @param ppProperty
	 *            PayPlanet 프로퍼티 조회 조건
	 * @return Pay Planet 가맹점 정보
	 */
	@Override
	public PpProperty searchPayPlanetShopInfo(PpProperty ppProperty) {
		return this.commonDao.queryForObject("PurchasePayPlanet.searchPayPlanetShopInfoDetail", ppProperty,
				PpProperty.class);
	}

	/**
	 * 
	 * <pre>
	 * Pay Planet 가맹점 ID를 통한 가맹점 정보 조회.
	 * </pre>
	 * 
	 * @param ppProperty
	 *            PayPlanet 프로퍼티 조회 조건
	 * @return Pay Planet 가맹점 정보
	 */
	@Override
	public PpProperty searchPayPlanetShopInfoByMid(PpProperty ppProperty) {
		return this.commonDao.queryForObject("PurchasePayPlanet.searchPayPlanetShopInfoDetail", ppProperty,
				PpProperty.class);
	}

	/**
	 * 
	 * <pre>
	 * SKT 결제/결제취소 용 SYSTEM DIVISION 조회.
	 * </pre>
	 * 
	 * @param ppProperty
	 *            PayPlanet 프로퍼티 조회 조건
	 * @return Pay Planet 가맹점 정보
	 */
	@Override
	public PpProperty searchDcbSystemDivision(PpProperty ppProperty) {
		return this.commonDao.queryForObject("PurchasePayPlanet.searchDcbSystemDivisionDetail", ppProperty,
				PpProperty.class);
	}
}
