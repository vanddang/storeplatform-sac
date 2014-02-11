/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.feedback.repository;

import java.util.List;

import com.skplanet.storeplatform.sac.other.feedback.vo.MbrAvg;
import com.skplanet.storeplatform.sac.other.feedback.vo.ProdNoti;
import com.skplanet.storeplatform.sac.other.feedback.vo.ProdNotiGood;
import com.skplanet.storeplatform.sac.other.feedback.vo.TenantProdStats;

/**
 * 
 * Feedback Repository
 * 
 * Updated on : 2014. 1. 16. Updated by : 김현일, 인크로스.
 */
public interface FeedbackRepository {

	/**
	 * 
	 * <pre>
	 * 사용후기 기등록 여부 조회.
	 * </pre>
	 * 
	 * @param prodNoti
	 *            prodNoti
	 * @return ProdNoti
	 */
	public ProdNoti getRegProdNoti(ProdNoti prodNoti);

	/**
	 * 
	 * <pre>
	 * 사용후기 등록.
	 * </pre>
	 * 
	 * @param prodNoti
	 *            prodNoti
	 * @return Object
	 */
	public Object insertProdNoti(ProdNoti prodNoti);

	/**
	 * 
	 * <pre>
	 * 사용후기 수정.
	 * </pre>
	 * 
	 * @param prodNoti
	 *            prodNoti
	 * @return Object
	 */
	public Object updateProdNoti(ProdNoti prodNoti);

	/**
	 * 
	 * <pre>
	 * 사용후기 삭제.
	 * </pre>
	 * 
	 * @param prodNoti
	 *            prodNoti
	 * @return Object
	 */
	public Object deleteProdNoti(ProdNoti prodNoti);

	/**
	 * 
	 * <pre>
	 * 사용후기 추천 삭제.
	 * </pre>
	 * 
	 * @param prodNotiGood
	 *            prodNotiGood
	 * @return Object
	 */
	public Object deleteProdNotiGood(ProdNotiGood prodNotiGood);

	/**
	 * 
	 * <pre>
	 * 평점 기등록 여부 조회.
	 * </pre>
	 * 
	 * @param mbrAvg
	 *            mbrAvg
	 * @return MbrAvg
	 */
	public MbrAvg getRegMbrAvg(MbrAvg mbrAvg);

	/**
	 * 
	 * <pre>
	 * 평점 등록/수정.
	 * </pre>
	 * 
	 * @param mbrAvg
	 *            mbrAvg
	 * @return Object
	 */
	public Object mergeMbrAvg(MbrAvg mbrAvg);

	/**
	 * 
	 * <pre>
	 * 평점 삭제.
	 * </pre>
	 * 
	 * @param mbrAvg
	 *            mbrAvg
	 * @return Object
	 */
	public Object deleteMbrAvg(MbrAvg mbrAvg);

	/**
	 * 
	 * <pre>
	 * 테넌트 상품 통계 조회.
	 * </pre>
	 * 
	 * @param tenantProdStats
	 *            tenantProdStats
	 * @return TenantProdStats
	 */
	public TenantProdStats getTenantProdStats(TenantProdStats tenantProdStats);

	/**
	 * 
	 * <pre>
	 * 테넌트 상품통계 수정 / 등록.
	 * </pre>
	 * 
	 * @param tenantProdStats
	 *            tenantProdStats
	 * @return Object
	 */
	public Object mergeTenantProdStats(TenantProdStats tenantProdStats);

	/**
	 * 
	 * <pre>
	 * 테넌트 상품통계 기등록 수정.
	 * </pre>
	 * 
	 * @param tenantProdStats
	 *            tenantProdStats
	 * @return Object
	 */
	public Object updateTenantProdStats(TenantProdStats tenantProdStats);

	/**
	 * 
	 * <pre>
	 * 테넌트 상품통계 삭제.
	 * </pre>
	 * 
	 * @param tenantProdStats
	 *            tenantProdStats
	 * @return Object
	 */
	public Object deleteTenantProdStats(TenantProdStats tenantProdStats);

	/**
	 * 
	 * <pre>
	 * 사용후기 사용자 ID 변경.
	 * </pre>
	 * 
	 * @param prodNoti
	 *            prodNoti
	 * @return Object
	 */
	public Object changeProdNotiUserId(ProdNoti prodNoti);

	/**
	 * 
	 * <pre>
	 * 사용후기 추천 사용자 ID 변경.
	 * </pre>
	 * 
	 * @param prodNotiGood
	 *            prodNotiGood
	 * @return Object
	 */
	public Object changeProdNotiGoodUserId(ProdNotiGood prodNotiGood);

	/**
	 * 
	 * <pre>
	 * 평점 사용자 ID 변경.
	 * </pre>
	 * 
	 * @param mbrAvg
	 *            mbrAvg
	 * @return Object
	 */
	public Object changeMbrAvgUserId(MbrAvg mbrAvg);

	/**
	 * 
	 * <pre>
	 * 사용후기 사용자 Key 변경.
	 * </pre>
	 * 
	 * @param prodNoti
	 *            prodNoti
	 * @return Object
	 */
	public Object changeProdNotiUserKey(ProdNoti prodNoti);

	/**
	 * 
	 * <pre>
	 * 사용후기 추천 사용자 Key 변경.
	 * </pre>
	 * 
	 * @param prodNotiGood
	 *            prodNotiGood
	 * @return Object
	 */
	public Object changeProdNotiGoodUserKey(ProdNotiGood prodNotiGood);

	/**
	 * 
	 * <pre>
	 * 평점 사용자 Key 변경.
	 * </pre>
	 * 
	 * @param mbrAvg
	 *            mbrAvg
	 * @return Object
	 */
	public Object changeMbrAvgUserKey(MbrAvg mbrAvg);

	/**
	 * 
	 * <pre>
	 * (탈퇴회원 사용후기) 등록 여부.
	 * </pre>
	 * 
	 * @param prodNoti
	 *            prodNoti
	 * @return Object
	 */
	public Object getProdNotiWDCount(ProdNoti prodNoti);

	/**
	 * 
	 * <pre>
	 * (탈퇴회원 사용후기)기 추천 여부.
	 * </pre>
	 * 
	 * @param prodNoti
	 *            prodNoti
	 * @return Object
	 */
	public Object getProdNotiWDGoodCount(ProdNoti prodNoti);

	/**
	 * 
	 * <pre>
	 * 사용후기 추천 등록.
	 * </pre>
	 * 
	 * @param prodNotiGood
	 *            prodNotiGood
	 * @return Object
	 */
	public Object insertProdNotiGood(ProdNotiGood prodNotiGood);

	/**
	 * 
	 * <pre>
	 * 탈퇴 회원 후기 추천 수 증감처리.
	 * </pre>
	 * 
	 * @param prodNotiGood
	 *            prodNotiGood
	 * @return Object
	 */
	public Object updateProdNotiWDGood(ProdNotiGood prodNotiGood);

	/**
	 * 
	 * <pre>
	 * 판매자 댓글 등록/수정/삭제.
	 * </pre>
	 * 
	 * @param prodNoti
	 *            prodNoti
	 * @return Object
	 */
	public Object updateSellerResp(ProdNoti prodNoti);

	/**
	 * 
	 * <pre>
	 * 탈퇴회원 판매자 댓글 등록/수정/삭제.
	 * </pre>
	 * 
	 * @param prodNoti
	 *            prodNoti
	 * @return Object
	 */
	public Object updateSellerRespWD(ProdNoti prodNoti);

	/**
	 * 
	 * <pre>
	 * (일반회원 사용후기) 기 추천 여부.
	 * </pre>
	 * 
	 * @param prodNoti
	 *            prodNoti
	 * @return Object
	 */
	public Object getProdNotiGoodCount(ProdNoti prodNoti);

	/**
	 * 
	 * <pre>
	 * 일반 회원 후기 추천 수 증감처리.
	 * </pre>
	 * 
	 * @param prodNotiGood
	 *            prodNotiGood
	 * @return Object
	 */
	public Object updateProdNotiGood(ProdNotiGood prodNotiGood);

	/**
	 * 
	 * <pre>
	 * 사용후기 리스트 조회.
	 * </pre>
	 * 
	 * @param prodNoti
	 *            prodNoti
	 * @return List<ProdNoti>
	 */
	public List<ProdNoti> getProdNotiList(ProdNoti prodNoti);

	/**
	 * 
	 * <pre>
	 * 사용후기 리스트 카운트 조회.
	 * </pre>
	 * 
	 * @param prodNoti
	 *            prodNoti
	 * @return prodNoti
	 */
	public Object getProdNotiCount(ProdNoti prodNoti);

}
