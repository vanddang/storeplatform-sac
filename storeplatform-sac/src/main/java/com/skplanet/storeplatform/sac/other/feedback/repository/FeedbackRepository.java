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
import java.util.Map;

import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserExtraInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserExtraInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacRes;
import com.skplanet.storeplatform.sac.other.feedback.vo.MbrAvg;
import com.skplanet.storeplatform.sac.other.feedback.vo.MbrAvgScore;
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
	 * 판매자 회원정보 조회.
	 * </pre>
	 * 
	 * @param detailInformationSacReq
	 *            detailInformationSacReq
	 * @return DetailInformationSacRes
	 */
	public DetailInformationSacRes detailInformation(DetailInformationSacReq detailInformationSacReq);

	/**
	 * 
	 * <pre>
	 * 일반 회원정보 조회.
	 * </pre>
	 * 
	 * @param searchUserSacReq
	 *            searchUserSacReq
	 * @return SearchUserSacRes
	 */
	public SearchUserSacRes searchUserByUserKey(SearchUserSacReq searchUserSacReq);

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
	 * 사용후기 리스트 조회V2.
	 * </pre>
	 * 
	 * @param prodNoti
	 *            prodNoti
	 * @return List<ProdNoti>
	 */
	public List<ProdNoti> getProdNotiListV2(ProdNoti prodNoti);

	/**
	 * 
	 * <pre>
	 * 사용후기 추천후 상품정보 조회.
	 * </pre>
	 * 
	 * @param prodNoti
	 *            prodNoti
	 * @return ProdNoti
	 */
	public ProdNoti getProdNoti(ProdNoti prodNoti);

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

	/**
	 * 
	 * <pre>
	 * 사용후기 리스트 카운트 조회V2.
	 * </pre>
	 * 
	 * @param prodNoti
	 *            prodNoti
	 * @return prodNoti
	 */
	public Object getProdNotiCountV2(ProdNoti prodNoti);

	/**
	 * 
	 * <pre>
	 * 자기가 작성한 사용후기 리스트.
	 * </pre>
	 * 
	 * @param prodNoti
	 *            prodNoti
	 * @return List<ProdNoti>
	 */
	public List<ProdNoti> getMyProdNotiList(ProdNoti prodNoti);

	/**
	 * 
	 * <pre>
	 * 자기가 작성한 사용후기 리스트 카운트 조회.
	 * </pre>
	 * 
	 * @param prodNoti
	 *            prodNoti
	 * 
	 * @return Object
	 */
	public Object getMyProdNotiCount(ProdNoti prodNoti);

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
	 * 평점, 다운로드 조회.
	 * </pre>
	 * 
	 * @param tenantProdStats
	 *            tenantProdStats
	 * @return TenantProdStats
	 */
	public TenantProdStats getProdEvalInfo(TenantProdStats tenantProdStats);

	/**
	 * 
	 * <pre>
	 * 참여수 조회.
	 * </pre>
	 * 
	 * @param mbrAvgScore
	 *            mbrAvgScore
	 * @return List<MbrAvgScore>
	 */
	public List<MbrAvgScore> getScoreList(MbrAvgScore mbrAvgScore);

	/**
	 * 
	 * <pre>
	 * 채널/에피소드 관계 조회.
	 * </pre>
	 * 
	 * @param partProdId
	 *            partProdId
	 * @return Map<String, String>
	 */
	public Map<String, String> getChannelEpisodeRelation(String partProdId);

	/**
	 * 
	 * <pre>
	 * 채널/에피소드 관계시 채널상품 존재 여부 확인.
	 * </pre>
	 * 
	 * @param partProdId
	 *            partProdId
	 * @return Object
	 */
	public Object getChannelTenantProdStatsCount(String partProdId);

	/**
	 * 
	 * <pre>
	 * 채널/에피소드 관계시 상품통계 채널상품 존재시 전체 에피소드 통계 UPDATE.
	 * </pre>
	 * 
	 * @param tenantProdStats
	 *            tenantProdStats
	 * @return Object
	 */
	public Object updateChannelTenantProdStats(TenantProdStats tenantProdStats);

	/**
	 * 
	 * <pre>
	 * 채널/에피소드 관계시 상품통계 채널상품 미존재시 전체 에피소드 통계 INSERT.
	 * </pre>
	 * 
	 * @param tenantProdStats
	 *            tenantProdStats
	 * @return Object
	 */
	public Object insertChannelTenantProdStats(TenantProdStats tenantProdStats);

	/**
	 * 
	 * <pre>
	 * 게임, 앱상품에 대한 최근 업데이트 날짜 조회
	 * </pre>
	 * 
	 * @param prodNoti
	 *            prodNoti
	 * @return ProdNoti
	 */
	public ProdNoti getAppProdLastDeployDt(ProdNoti prodNoti);

	/**
	 * 
	 * <pre>
	 * 회원 부가속성 정보 조회.
	 * </pre>
	 * 
	 * @param searchUserSacReq
	 *            searchUserSacReq
	 * @return SearchUserSacRes
	 */
	public SearchUserExtraInfoSacRes searchUserExtraInfo(SearchUserExtraInfoSacReq searchUserExtraInfoSacReq);

	/**
	 * 
	 * <pre>
	 * 상품 서비스그룹 조회.
	 * </pre>
	 * 
	 * @param prodId
	 *            prodId
	 * @return String
	 */
	public String getProdSvcGrpCd(String prodId);
}
