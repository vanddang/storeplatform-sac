/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.common;

import static org.junit.Assert.assertNotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.skplanet.storeplatform.sac.purchase.common.service.PayPlanetShopService;
import com.skplanet.storeplatform.sac.purchase.common.vo.PayPlanetShop;

/**
 * 
 * 구매Part 테넌트 정책 조회 테스트
 * 
 * Updated on : 2014. 1. 16. Updated by : 이승택, nTels.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/spring-test/context-test.xml" })
public class PayPlanetShopServiceImplTest {

	@Autowired
	private PayPlanetShopService payPlanetShopService;

	@Value("#{systemProperties['spring.profiles.active']}")
	private String envServerLevel;

	private final String loggingLevel = "local";

	private boolean bSystemOut = false;

	@Before
	public void init() {
		this.bSystemOut = StringUtils.equals(this.envServerLevel, this.loggingLevel);
	}

	@Test
	public void getShopInfoOld() {
		String tenantId = "S01";

		PayPlanetShop payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfo(tenantId);
		assertNotNull(payPlanetShop);
	}

	@Test
	public void getShopInfo() {
		if (this.bSystemOut) {
			System.out.println("############# getShopInfo #############");
		}

		String tenantId = "S01";
		String apiTypeCd = "OR021001";
		String prchsReqPathCd = "OR000407";

		// S01 / 구매요청 / IAP
		PayPlanetShop payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfo(tenantId, apiTypeCd,
				prchsReqPathCd);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + prchsReqPathCd + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}

		// S01 / 구매요청 / 이북보관함
		prchsReqPathCd = "OR000444";
		payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfo(tenantId, apiTypeCd, prchsReqPathCd);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + prchsReqPathCd + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}

		// S01 / 구매요청 / 일반(S/C)
		prchsReqPathCd = "OR000405";
		payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfo(tenantId, apiTypeCd, prchsReqPathCd);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + prchsReqPathCd + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}

		// S01 / 결제취소 / IAP
		apiTypeCd = "OR021002";
		prchsReqPathCd = "OR000407";
		payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfo(tenantId, apiTypeCd, prchsReqPathCd);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + prchsReqPathCd + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}

		// S01 / 결제취소 / 이북보관함
		prchsReqPathCd = "OR000444";
		payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfo(tenantId, apiTypeCd, prchsReqPathCd);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + prchsReqPathCd + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}

		// S01 / 결제취소 / 일반(S/C)
		prchsReqPathCd = "OR000405";
		payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfo(tenantId, apiTypeCd, prchsReqPathCd);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + prchsReqPathCd + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}

		// S02 / 구매요청 / IAP
		tenantId = "S02";
		apiTypeCd = "OR021001";
		prchsReqPathCd = "OR000407";
		payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfo(tenantId, apiTypeCd, prchsReqPathCd);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + prchsReqPathCd + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}

		// S02 / 구매요청 / 일반(S/C)
		prchsReqPathCd = "OR000405";
		payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfo(tenantId, apiTypeCd, prchsReqPathCd);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + prchsReqPathCd + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}

		// S02 / 결제취소 / IAP
		apiTypeCd = "OR021002";
		prchsReqPathCd = "OR000407";
		payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfo(tenantId, apiTypeCd, prchsReqPathCd);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + prchsReqPathCd + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}

		// S02 / 결제취소 / 일반(S/C)
		prchsReqPathCd = "OR000405";
		payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfo(tenantId, apiTypeCd, prchsReqPathCd);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + prchsReqPathCd + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}

		// S03 / 구매요청 / IAP
		tenantId = "S03";
		apiTypeCd = "OR021001";
		prchsReqPathCd = "OR000407";
		payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfo(tenantId, apiTypeCd, prchsReqPathCd);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + prchsReqPathCd + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}

		// S03 / 구매요청 / 일반(S/C)
		prchsReqPathCd = "OR000405";
		payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfo(tenantId, apiTypeCd, prchsReqPathCd);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + prchsReqPathCd + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}

		// S03 / 결제취소 / IAP
		apiTypeCd = "OR021002";
		prchsReqPathCd = "OR000407";
		payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfo(tenantId, apiTypeCd, prchsReqPathCd);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + prchsReqPathCd + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}

		// S03 / 결제취소 / 일반(S/C)
		prchsReqPathCd = "OR000405";
		payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfo(tenantId, apiTypeCd, prchsReqPathCd);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + prchsReqPathCd + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}

	}

	@Test
	public void getShopInfoByMid() {
		if (this.bSystemOut) {
			System.out.println("############# getShopInfoByMid #############");
		}

		String tenantId = "S01";
		String apiTypeCd = "OR021001";
		String mid = "TSTORE0003";

		// S01 / 구매요청 / TSTORE0003
		PayPlanetShop payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfoByMid(tenantId, apiTypeCd, mid);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + mid + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}

		// S01 / 구매요청 / TSTORE0004
		mid = "TSTORE0004";
		payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfoByMid(tenantId, apiTypeCd, mid);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + mid + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}

		// S01 / 구매요청 / SKTStore01
		mid = "SKTStore01";
		payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfoByMid(tenantId, apiTypeCd, mid);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + mid + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}

		// S01 / 결제취소 / TSTORE0003
		apiTypeCd = "OR021002";
		mid = "TSTORE0003";
		payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfoByMid(tenantId, apiTypeCd, mid);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + mid + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}

		// S01 / 결제취소 / TSTORE0004
		mid = "TSTORE0004";
		payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfoByMid(tenantId, apiTypeCd, mid);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + mid + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}

		// S01 / 결제취소 / SKTStore01
		mid = "SKTStore01";
		payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfoByMid(tenantId, apiTypeCd, mid);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + mid + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}

		// S02 / 구매요청 / KTXXXX0002
		tenantId = "S02";
		apiTypeCd = "OR021001";
		mid = "KTXXXX0002";
		payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfoByMid(tenantId, apiTypeCd, mid);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + mid + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}

		// S02 / 구매요청 / KTXXXX0003
		mid = "KTXXXX0003";
		payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfoByMid(tenantId, apiTypeCd, mid);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + mid + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}

		// S02 / 결제취소 / KTXXXX0002
		apiTypeCd = "OR021002";
		mid = "KTXXXX0002";
		payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfoByMid(tenantId, apiTypeCd, mid);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + mid + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}

		// S02 / 결제취소 / KTXXXX0003
		mid = "KTXXXX0003";
		payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfoByMid(tenantId, apiTypeCd, mid);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + mid + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}

		// S03 / 구매요청 / LGXXXX0002
		tenantId = "S03";
		apiTypeCd = "OR021001";
		mid = "LGXXXX0002";
		payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfoByMid(tenantId, apiTypeCd, mid);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + mid + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}

		// S03 / 구매요청 / LGXXXX0003
		mid = "LGXXXX0003";
		payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfoByMid(tenantId, apiTypeCd, mid);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + mid + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}

		// S03 / 결제취소 / LGXXXX0002
		apiTypeCd = "OR021002";
		mid = "LGXXXX0002";
		payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfoByMid(tenantId, apiTypeCd, mid);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + mid + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}

		// S03 / 결제취소 / LGXXXX0003
		mid = "LGXXXX0003";
		payPlanetShop = this.payPlanetShopService.getPayPlanetShopInfoByMid(tenantId, apiTypeCd, mid);
		assertNotNull(payPlanetShop);
		if (this.bSystemOut) {
			System.out.println("GET:[" + tenantId + "/" + apiTypeCd + "/" + mid + "]:"
					+ ReflectionToStringBuilder.toString(payPlanetShop, ToStringStyle.SHORT_PREFIX_STYLE));
		}
	}

}
