/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.feedback.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.NumberUtils;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserInfoSac;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.AvgScore;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateFeedbackSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateRecommendFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateRecommendFeedbackSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateSellerFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateSellerFeedbackSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.Feedback;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.FeedbackMy;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.GetScoreSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.GetScoreSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListFeedbackSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListMyFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListMyFeedbackSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListScorePaticpersSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListScorePaticpersSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ModifyFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ModifyFeedbackSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ModifySellerFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ModifySellerFeedbackSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.RemoveFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.RemoveFeedbackSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.RemoveRecommendFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.RemoveRecommendFeedbackSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.RemoveSellerFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.RemoveSellerFeedbackSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants.SellerConstants;
import com.skplanet.storeplatform.sac.other.feedback.repository.FeedbackRepository;
import com.skplanet.storeplatform.sac.other.feedback.vo.MbrAvg;
import com.skplanet.storeplatform.sac.other.feedback.vo.MbrAvgScore;
import com.skplanet.storeplatform.sac.other.feedback.vo.ProdNoti;
import com.skplanet.storeplatform.sac.other.feedback.vo.ProdNotiGood;
import com.skplanet.storeplatform.sac.other.feedback.vo.TenantProdStats;

/**
 * 
 * Feedback Service 구현체
 * 
 * Updated on : 2014. 1. 16. Updated by : 김현일, 인크로스.
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackServiceImpl.class);

	private static final String DEFAULT_MSG1 = "별로에요";
	private static final String DEFAULT_MSG2 = "그저 그래요";
	private static final String DEFAULT_MSG3 = "좋아요";
	private static final String DEFAULT_MSG4 = "만족해요";
	private static final String DEFAULT_MSG5 = "아주 좋아요";

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Override
	public CreateFeedbackSacRes create(CreateFeedbackSacReq createFeedbackSacReq, SacRequestHeader sacRequestHeader) {

		// 회원 SCI 연동, 회원조회, 없으면 Exception.
		SearchUserSacReq searchUserSacReq = new SearchUserSacReq();
		List<String> userKeyList = new ArrayList<String>();
		userKeyList.add(createFeedbackSacReq.getUserKey());
		searchUserSacReq.setUserKeyList(userKeyList);

		try {
			this.feedbackRepository.searchUserByUserKey(searchUserSacReq);
		} catch (StorePlatformException e) {
			throw new StorePlatformException("SAC_OTH_9002", e);
		}

		// 평점 저장.
		this.setMbrAvgTenantProdStats(createFeedbackSacReq, sacRequestHeader);

		String notiSeq = "";

		// 사용후기 내용이 있으면.
		if (StringUtils.isNotEmpty(createFeedbackSacReq.getNotiDscr())) {

			ProdNoti prodNoti = new ProdNoti();
			prodNoti.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
			prodNoti.setMbrNo(createFeedbackSacReq.getUserKey());
			prodNoti.setProdId(createFeedbackSacReq.getProdId());
			prodNoti.setTitle(createFeedbackSacReq.getNotiTitle());
			prodNoti.setNotiDscr(createFeedbackSacReq.getNotiDscr());
			prodNoti.setRegId(createFeedbackSacReq.getUserId());
			prodNoti.setMbrTelno(createFeedbackSacReq.getDeviceId());
			prodNoti.setFbPostYn(createFeedbackSacReq.getFbPostYn());
			prodNoti.setDeviceModelCd(sacRequestHeader.getDeviceHeader().getModel());
			prodNoti.setPkgVer(createFeedbackSacReq.getPkgVer());
			prodNoti.setChnlId(createFeedbackSacReq.getChnlId());
			prodNoti.setRegId(createFeedbackSacReq.getUserId());
			// 기등록여부 확인.
			ProdNoti getRegProdNoti = this.feedbackRepository.getRegProdNoti(prodNoti);
			if (getRegProdNoti == null) {
				// 등록.
				int affectedRow = (Integer) this.feedbackRepository.insertProdNoti(prodNoti);
				if (affectedRow <= 0)
					throw new StorePlatformException("SAC_OTH_9102");
			} else {
				// 에러.
				throw new StorePlatformException("SAC_OTH_9101");
			}
			notiSeq = prodNoti.getNotiSeq();
		}
		CreateFeedbackSacRes createFeedbackSacRes = new CreateFeedbackSacRes();
		createFeedbackSacRes.setProdId(createFeedbackSacReq.getProdId());
		createFeedbackSacRes.setNotiSeq(notiSeq);
		return createFeedbackSacRes;
	}

	@Override
	public ModifyFeedbackSacRes modify(ModifyFeedbackSacReq modifyFeedbackSacReq, SacRequestHeader sacRequestHeader) {

		// 회원 SCI 연동, 회원조회, 없으면 Exception.
		SearchUserSacReq searchUserSacReq = new SearchUserSacReq();
		List<String> userKeyList = new ArrayList<String>();
		userKeyList.add(modifyFeedbackSacReq.getUserKey());
		searchUserSacReq.setUserKeyList(userKeyList);
		try {
			this.feedbackRepository.searchUserByUserKey(searchUserSacReq);
		} catch (StorePlatformException e) {
			throw new StorePlatformException("SAC_OTH_9002", e);
		}

		// 평점 저장.
		this.setMbrAvgTenantProdStats(modifyFeedbackSacReq, sacRequestHeader);

		String notiSeq = "";

		// 사용후기 내용이 있으면.
		if (StringUtils.isNotEmpty(modifyFeedbackSacReq.getNotiDscr())) {
			ProdNoti prodNoti = new ProdNoti();
			prodNoti.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
			prodNoti.setNotiSeq(modifyFeedbackSacReq.getNotiSeq());
			prodNoti.setMbrNo(modifyFeedbackSacReq.getUserKey());
			prodNoti.setTitle(modifyFeedbackSacReq.getNotiTitle());
			prodNoti.setNotiDscr(modifyFeedbackSacReq.getNotiDscr());
			prodNoti.setDeviceModelCd(sacRequestHeader.getDeviceHeader().getModel());
			prodNoti.setPkgVer(modifyFeedbackSacReq.getPkgVer());

			// 사용후기 수정.
			int affectedRow = (Integer) this.feedbackRepository.updateProdNoti(prodNoti);

			if (affectedRow > 0) {
				// 사용후기가 수정되면 사용후기 추천은 삭제한다.
				ProdNotiGood prodNotiGood = new ProdNotiGood();
				prodNotiGood.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
				prodNotiGood.setNotiSeq(modifyFeedbackSacReq.getNotiSeq());
				this.feedbackRepository.deleteProdNotiGood(prodNotiGood);
			} else {
				throw new StorePlatformException("SAC_OTH_9103");
			}
			notiSeq = modifyFeedbackSacReq.getNotiSeq();
		}

		ModifyFeedbackSacRes modifyFeedbackSacRes = new ModifyFeedbackSacRes();
		modifyFeedbackSacRes.setProdId(modifyFeedbackSacReq.getProdId());
		modifyFeedbackSacRes.setNotiSeq(notiSeq);

		return modifyFeedbackSacRes;
	}

	@Override
	public RemoveFeedbackSacRes remove(RemoveFeedbackSacReq removeFeedbackSacReq, SacRequestHeader sacRequestHeader) {

		// 회원 SCI 연동, 회원조회, 없으면 Exception.
		SearchUserSacReq searchUserSacReq = new SearchUserSacReq();
		List<String> userKeyList = new ArrayList<String>();
		userKeyList.add(removeFeedbackSacReq.getUserKey());
		searchUserSacReq.setUserKeyList(userKeyList);
		try {
			this.feedbackRepository.searchUserByUserKey(searchUserSacReq);
		} catch (StorePlatformException e) {
			throw new StorePlatformException("SAC_OTH_9002", e);
		}

		// 기 평가여부 조회
		MbrAvg mbrAvg = new MbrAvg();
		mbrAvg.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		mbrAvg.setMbrNo(removeFeedbackSacReq.getUserKey());
		mbrAvg.setProdId(removeFeedbackSacReq.getProdId());
		MbrAvg getRegMbrAvg = this.feedbackRepository.getRegMbrAvg(mbrAvg);
		if (getRegMbrAvg != null) {
			// 기 평가가 존재하면 삭제.
			this.feedbackRepository.deleteMbrAvg(mbrAvg);
			TenantProdStats tenantProdStats = new TenantProdStats();
			tenantProdStats.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
			tenantProdStats.setProdId(removeFeedbackSacReq.getProdId());
			tenantProdStats.setUpdId(removeFeedbackSacReq.getUserId());
			// 상품 통계가 존재하면.
			TenantProdStats getTenantProdStats = this.feedbackRepository.getTenantProdStats(tenantProdStats);
			if (getTenantProdStats != null) {
				// 참여수가 1일경우 삭제.(모두 0으로 업데이트).
				if (NumberUtils.toInt(getTenantProdStats.getPaticpersCnt(), 0) == 1) {
					this.feedbackRepository.deleteTenantProdStats(tenantProdStats);
				} else {

					// 그외는 업데이트한다.
					TenantProdStats updateTenantProdStats = new TenantProdStats();
					updateTenantProdStats.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
					updateTenantProdStats.setProdId(removeFeedbackSacReq.getProdId());
					updateTenantProdStats.setAvgEvluScore("0");
					updateTenantProdStats.setPreAvgScore(getRegMbrAvg.getAvgScore());
					updateTenantProdStats.setUpdId(removeFeedbackSacReq.getUserId());
					updateTenantProdStats.setAction("remove");
					this.feedbackRepository.updateTenantProdStats(updateTenantProdStats);
				}
				// 웹툰일경우 채널ID에 에피소드들의 상품통계 평점을 계산한다.
				this.setWebtoonChannelMbrAvgTenantStats(removeFeedbackSacReq.getProdId(), sacRequestHeader
						.getTenantHeader().getTenantId(), removeFeedbackSacReq.getUserId());
			}
		}

		// 사용후기 삭제, DEL_YN = 'Y'.
		ProdNoti prodNoti = new ProdNoti();
		prodNoti.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		prodNoti.setNotiSeq(removeFeedbackSacReq.getNotiSeq());
		prodNoti.setMbrNo(removeFeedbackSacReq.getUserKey());
		int affectedRow = (Integer) this.feedbackRepository.deleteProdNoti(prodNoti);
		if (affectedRow <= 0) {
			throw new StorePlatformException("SAC_OTH_9104");
		}

		RemoveFeedbackSacRes removeFeedbackSacRes = new RemoveFeedbackSacRes();
		removeFeedbackSacRes.setProdId(removeFeedbackSacReq.getProdId());
		removeFeedbackSacRes.setNotiSeq(removeFeedbackSacReq.getNotiSeq());

		return removeFeedbackSacRes;
	}

	@Override
	public CreateRecommendFeedbackSacRes createRecommend(CreateRecommendFeedbackSacReq createRecommendFeedbackReq,
			SacRequestHeader sacRequestHeader) {

		// 회원 SCI 연동, 회원조회, 없으면 Exception.
		SearchUserSacReq searchUserSacReq = new SearchUserSacReq();
		List<String> userKeyList = new ArrayList<String>();
		userKeyList.add(createRecommendFeedbackReq.getUserKey());
		searchUserSacReq.setUserKeyList(userKeyList);
		try {
			this.feedbackRepository.searchUserByUserKey(searchUserSacReq);
		} catch (StorePlatformException e) {
			throw new StorePlatformException("SAC_OTH_9002", e);
		}

		// 기 추천여부 조회.
		ProdNoti prodNoti = new ProdNoti();
		prodNoti.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		prodNoti.setNotiSeq(createRecommendFeedbackReq.getNotiSeq());
		prodNoti.setProdId(createRecommendFeedbackReq.getProdId());
		prodNoti.setMbrNo(createRecommendFeedbackReq.getUserKey());

		int count = (Integer) this.feedbackRepository.getProdNotiGoodCount(prodNoti);

		if (count > 0) {
			throw new StorePlatformException("SAC_OTH_9201");
		}

		// 사용후기 추천 등록.
		ProdNotiGood prodNotiGood = new ProdNotiGood();
		prodNotiGood.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		prodNotiGood.setNotiSeq(createRecommendFeedbackReq.getNotiSeq());
		prodNotiGood.setMbrNo(createRecommendFeedbackReq.getUserKey());
		prodNotiGood.setRegId(createRecommendFeedbackReq.getUserId());
		int affectedRow = (Integer) this.feedbackRepository.insertProdNotiGood(prodNotiGood);

		if (affectedRow <= 0) {
			throw new StorePlatformException("SAC_OTH_9202");
		}

		// 사용후기 추천 업데이트.
		prodNotiGood.setAction("create");
		affectedRow = (Integer) this.feedbackRepository.updateProdNotiGood(prodNotiGood);

		if (affectedRow <= 0) {
			throw new StorePlatformException("SAC_OTH_9202");
		}

		prodNoti.setStartRow("1");
		prodNoti.setEndRow("1");
		// 등록된 사용후기 조회.
		ProdNoti res = this.feedbackRepository.getProdNoti(prodNoti);

		if (res == null) {
			throw new StorePlatformException("SAC_OTH_9205");
		}

		// 판매자 요청 리스트.
		List<SellerMbrSac> sellerMbrSacList = new ArrayList<SellerMbrSac>();
		SellerMbrSac sellerMbrSac = new SellerMbrSac();
		sellerMbrSac.setSellerKey(res.getSellerMbrNo());
		sellerMbrSacList.add(sellerMbrSac);

		DetailInformationSacReq detailInformationSacReq = new DetailInformationSacReq();
		detailInformationSacReq.setSellerMbrSacList(sellerMbrSacList);
		DetailInformationSacRes detailInformationSacRes = null;
		// 판매자 조회.
		try {
			detailInformationSacRes = this.feedbackRepository.detailInformation(detailInformationSacReq);
		} catch (Exception e) {
			detailInformationSacRes = null;
		}

		// 회원 조회 요청.
		userKeyList.clear();
		userKeyList.add(res.getMbrNo());
		searchUserSacReq.setUserKeyList(userKeyList);
		SearchUserSacRes searchUserSacRes = null;

		// 회원 조회.
		try {
			searchUserSacRes = this.feedbackRepository.searchUserByUserKey(searchUserSacReq);
		} catch (Exception e) {
			searchUserSacRes = null;
		}

		// 응답 셋팅.
		Feedback feedback = this.setFeedback(res, "", detailInformationSacRes, searchUserSacRes);

		// 응답.
		CreateRecommendFeedbackSacRes createRecommendFeedbackSacRes = new CreateRecommendFeedbackSacRes();
		BeanUtils.copyProperties(feedback, createRecommendFeedbackSacRes);

		return createRecommendFeedbackSacRes;
	}

	@Override
	public RemoveRecommendFeedbackSacRes removeRecommend(RemoveRecommendFeedbackSacReq removeRecommendFeedbackSacReq,
			SacRequestHeader sacRequestHeader) {

		// 회원 SCI 연동, 회원조회, 없으면 Exception.
		SearchUserSacReq searchUserSacReq = new SearchUserSacReq();
		List<String> userKeyList = new ArrayList<String>();
		userKeyList.add(removeRecommendFeedbackSacReq.getUserKey());
		searchUserSacReq.setUserKeyList(userKeyList);
		try {
			this.feedbackRepository.searchUserByUserKey(searchUserSacReq);
		} catch (StorePlatformException e) {
			throw new StorePlatformException("SAC_OTH_9002", e);
		}

		ProdNoti prodNoti = new ProdNoti();
		prodNoti.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		prodNoti.setNotiSeq(removeRecommendFeedbackSacReq.getNotiSeq());
		prodNoti.setProdId(removeRecommendFeedbackSacReq.getProdId());
		prodNoti.setMbrNo(removeRecommendFeedbackSacReq.getUserKey());

		// 사용후기 추천 삭제.
		ProdNotiGood prodNotiGood = new ProdNotiGood();
		prodNotiGood.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		prodNotiGood.setNotiSeq(removeRecommendFeedbackSacReq.getNotiSeq());
		prodNotiGood.setMbrNo(removeRecommendFeedbackSacReq.getUserKey());

		int count = (Integer) this.feedbackRepository.getProdNotiGoodCount(prodNoti);

		if (count <= 0) {
			throw new StorePlatformException("SAC_OTH_9203");
		}

		int affectedRow = (Integer) this.feedbackRepository.deleteProdNotiGood(prodNotiGood);

		if (affectedRow <= 0) {
			throw new StorePlatformException("SAC_OTH_9204");
		}

		// 사용후기 추천 업데이트.
		prodNotiGood.setAction("remove");
		affectedRow = (Integer) this.feedbackRepository.updateProdNotiGood(prodNotiGood);

		if (affectedRow <= 0) {
			throw new StorePlatformException("SAC_OTH_9204");
		}

		prodNoti.setStartRow("1");
		prodNoti.setEndRow("1");

		// 삭제된 사용후기 조회.
		ProdNoti res = this.feedbackRepository.getProdNoti(prodNoti);

		if (res == null) {
			throw new StorePlatformException("SAC_OTH_9205");
		}

		// 판매자 요청 리스트.
		List<SellerMbrSac> sellerMbrSacList = new ArrayList<SellerMbrSac>();
		SellerMbrSac sellerMbrSac = new SellerMbrSac();
		sellerMbrSac.setSellerKey(res.getSellerMbrNo());
		sellerMbrSacList.add(sellerMbrSac);

		DetailInformationSacReq detailInformationSacReq = new DetailInformationSacReq();
		detailInformationSacReq.setSellerMbrSacList(sellerMbrSacList);
		DetailInformationSacRes detailInformationSacRes = null;
		// 판매자 조회.
		try {
			detailInformationSacRes = this.feedbackRepository.detailInformation(detailInformationSacReq);
		} catch (Exception e) {
			detailInformationSacRes = null;
		}

		// 회원 조회 요청.
		userKeyList.clear();
		userKeyList.add(res.getMbrNo());
		searchUserSacReq.setUserKeyList(userKeyList);
		SearchUserSacRes searchUserSacRes = null;

		// 회원 조회.
		try {
			searchUserSacRes = this.feedbackRepository.searchUserByUserKey(searchUserSacReq);
		} catch (Exception e) {
			searchUserSacRes = null;
		}

		// 응답셋팅.
		Feedback feedback = this.setFeedback(res, "", detailInformationSacRes, searchUserSacRes);

		// 응답
		RemoveRecommendFeedbackSacRes removeRecommendFeedbackSacRes = new RemoveRecommendFeedbackSacRes();
		BeanUtils.copyProperties(feedback, removeRecommendFeedbackSacRes);

		return removeRecommendFeedbackSacRes;
	}

	@Override
	public ListFeedbackSacRes list(ListFeedbackSacReq listFeedbackSacReq, SacRequestHeader sacRequestHeader) {

		// 사용후기 평균평점.
		TenantProdStats tenantProdStats = new TenantProdStats();
		tenantProdStats.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		tenantProdStats.setProdId(listFeedbackSacReq.getProdId());
		TenantProdStats getProdEvalInfo = this.feedbackRepository.getProdEvalInfo(tenantProdStats);

		ListFeedbackSacRes listFeedbackRes = new ListFeedbackSacRes();
		if (getProdEvalInfo == null) {
			listFeedbackRes.setAvgEvluScorePcts("0,0,0,0,0");
			listFeedbackRes.setAvgEvluScore("0");
			listFeedbackRes.setDwldCnt("0");
			listFeedbackRes.setPaticpersCnt("0");
		} else {
			listFeedbackRes.setAvgEvluScorePcts(ObjectUtils.defaultIfNull(getProdEvalInfo.getAvgEvluScorePcts(),
					"0,0,0,0,0"));
			listFeedbackRes.setAvgEvluScore(ObjectUtils.defaultIfNull(getProdEvalInfo.getAvgEvluScore(), "0"));
			listFeedbackRes.setDwldCnt(ObjectUtils.defaultIfNull(getProdEvalInfo.getDwldCnt(), "0"));
			listFeedbackRes.setPaticpersCnt(ObjectUtils.defaultIfNull(getProdEvalInfo.getPaticpersCnt(), "0"));
		}

		// 페이징 처리.
		int offset = listFeedbackSacReq.getOffset() == 0 ? 1 : listFeedbackSacReq.getOffset();
		int count = listFeedbackSacReq.getCount() == 0 ? 10 : (offset + listFeedbackSacReq.getCount()) - 1;
		ProdNoti prodNoti = new ProdNoti();
		prodNoti.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		prodNoti.setProdId(listFeedbackSacReq.getProdId());
		prodNoti.setMbrNo(ObjectUtils.defaultIfNull(listFeedbackSacReq.getUserKey(), ""));
		prodNoti.setOrderedBy(listFeedbackSacReq.getOrderedBy());
		prodNoti.setChnlId(listFeedbackSacReq.getChnlId());
		prodNoti.setProdType(listFeedbackSacReq.getProdType());
		prodNoti.setStartRow(String.valueOf(offset));
		prodNoti.setEndRow(String.valueOf(count));
		// 사용후기 카운트.
		int totalCount = (Integer) this.feedbackRepository.getProdNotiCount(prodNoti);
		// if (totalCount <= 0) {
		// throw new StorePlatformException("SAC_OTH_9001");
		// }
		listFeedbackRes.setNotiTot(String.valueOf(totalCount));
		// 사용후기 목록.
		List<ProdNoti> getProdnotiList = this.feedbackRepository.getProdNotiList(prodNoti);
		if (!CollectionUtils.isEmpty(getProdnotiList)) {
			// if (CollectionUtils.isEmpty(getProdnotiList)) {
			// throw new StorePlatformException("SAC_OTH_9001");
			// }
			List<Feedback> notiList = new ArrayList<Feedback>();
			List<SellerMbrSac> sellerMbrSacList = new ArrayList<SellerMbrSac>();
			Set<SellerMbrSac> sellerMbrSacSet = new HashSet<SellerMbrSac>();
			List<String> userKeyList = new ArrayList<String>();
			Set<String> userKeySet = new HashSet<String>();
			// 회원/ 판매자 요청 리스트.
			for (ProdNoti res : getProdnotiList) {
				SellerMbrSac sellerMbrSac = new SellerMbrSac();
				sellerMbrSac.setSellerKey(res.getSellerMbrNo());
				sellerMbrSacSet.add(sellerMbrSac);
				userKeySet.add(res.getMbrNo());
			}
			// 판매자 조회 요청.
			sellerMbrSacList.addAll(sellerMbrSacSet);
			DetailInformationSacReq detailInformationSacReq = new DetailInformationSacReq();
			detailInformationSacReq.setSellerMbrSacList(sellerMbrSacList);
			DetailInformationSacRes detailInformationSacRes = null;

			// 판매자 조회.
			try {
				detailInformationSacRes = this.feedbackRepository.detailInformation(detailInformationSacReq);
			} catch (Exception e) {
				detailInformationSacRes = null;
			}

			// 회원 조회 요청.
			SearchUserSacReq searchUserSacReq = new SearchUserSacReq();
			userKeyList.addAll(userKeySet);
			searchUserSacReq.setUserKeyList(userKeyList);
			SearchUserSacRes searchUserSacRes = null;

			// 회원 조회.
			try {
				searchUserSacRes = this.feedbackRepository.searchUserByUserKey(searchUserSacReq);
			} catch (Exception e) {
				searchUserSacRes = null;
			}

			// 응답셋팅.
			for (ProdNoti res : getProdnotiList) {
				notiList.add(this.setFeedback(res, listFeedbackSacReq.getProdType(), detailInformationSacRes,
						searchUserSacRes));
			}
			// 응답.
			listFeedbackRes.setNotiList(notiList);
		}
		return listFeedbackRes;
	}

	@Override
	public ListMyFeedbackSacRes listMyFeedback(ListMyFeedbackSacReq listMyFeedbackSacReq,
			SacRequestHeader sacRequestHeader) {

		// 페이징 처리.
		int offset = listMyFeedbackSacReq.getOffset() == 0 ? 1 : listMyFeedbackSacReq.getOffset();
		int count = listMyFeedbackSacReq.getCount() == 0 ? 100 : (offset + listMyFeedbackSacReq.getCount()) - 1;

		ProdNoti prodNoti = new ProdNoti();
		prodNoti.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		prodNoti.setProdIds(Arrays.asList((StringUtils.split(listMyFeedbackSacReq.getProdIds(), ","))));
		prodNoti.setMbrNo(listMyFeedbackSacReq.getUserKey());
		prodNoti.setChnlId(listMyFeedbackSacReq.getChnlId());
		prodNoti.setProdType(listMyFeedbackSacReq.getProdType());
		prodNoti.setDefaultMsg1(DEFAULT_MSG1);
		prodNoti.setDefaultMsg2(DEFAULT_MSG2);
		prodNoti.setDefaultMsg3(DEFAULT_MSG3);
		prodNoti.setDefaultMsg4(DEFAULT_MSG4);
		prodNoti.setDefaultMsg5(DEFAULT_MSG5);
		prodNoti.setStartRow(String.valueOf(offset));
		prodNoti.setEndRow(String.valueOf(count));

		// 사용후기 카운트.
		int notiTot = (Integer) this.feedbackRepository.getMyProdNotiCount(prodNoti);

		if (notiTot <= 0) {
			throw new StorePlatformException("SAC_OTH_9001");
		}

		// 사용후기 목록.
		ListMyFeedbackSacRes listMyFeedbackSacRes = new ListMyFeedbackSacRes();
		listMyFeedbackSacRes.setNotiTot(String.valueOf(notiTot));

		List<ProdNoti> getMyProdNotiList = this.feedbackRepository.getMyProdNotiList(prodNoti);

		if (CollectionUtils.isEmpty(getMyProdNotiList)) {
			throw new StorePlatformException("SAC_OTH_9001");
		}

		List<FeedbackMy> notiMyList = new ArrayList<FeedbackMy>();
		List<SellerMbrSac> sellerMbrSacList = new ArrayList<SellerMbrSac>();
		Set<SellerMbrSac> sellerMbrSacSet = new HashSet<SellerMbrSac>();
		List<String> userKeyList = new ArrayList<String>();
		Set<String> userKeySet = new HashSet<String>();

		// 판매자 요청 리스트.
		for (ProdNoti res : getMyProdNotiList) {
			SellerMbrSac sellerMbrSac = new SellerMbrSac();
			sellerMbrSac.setSellerKey(res.getSellerMbrNo());
			sellerMbrSacSet.add(sellerMbrSac);
			userKeySet.add(res.getMbrNo());
		}

		// 판매자 요청.
		sellerMbrSacList.addAll(sellerMbrSacSet);
		DetailInformationSacReq detailInformationSacReq = new DetailInformationSacReq();
		detailInformationSacReq.setSellerMbrSacList(sellerMbrSacList);
		DetailInformationSacRes detailInformationSacRes = null;

		// 판매자 조회.
		try {
			detailInformationSacRes = this.feedbackRepository.detailInformation(detailInformationSacReq);
		} catch (Exception e) {
			detailInformationSacRes = null;
		}

		// 회원 조회 요청.
		SearchUserSacReq searchUserSacReq = new SearchUserSacReq();
		userKeyList.addAll(userKeySet);
		searchUserSacReq.setUserKeyList(userKeyList);
		SearchUserSacRes searchUserSacRes = null;

		// 회원 조회.
		try {
			searchUserSacRes = this.feedbackRepository.searchUserByUserKey(searchUserSacReq);
		} catch (Exception e) {
			searchUserSacRes = null;
		}

		// 응답셋팅.
		for (ProdNoti res : getMyProdNotiList) {
			Feedback feedback = this.setFeedback(res, listMyFeedbackSacReq.getProdType(), detailInformationSacRes,
					searchUserSacRes);
			FeedbackMy feedbackMy = new FeedbackMy();
			BeanUtils.copyProperties(feedback, feedbackMy);
			notiMyList.add(feedbackMy);
		}
		// 응답
		listMyFeedbackSacRes.setNotiList(notiMyList);
		return listMyFeedbackSacRes;
	}

	@Override
	public CreateSellerFeedbackSacRes createSellerFeedback(CreateSellerFeedbackSacReq createSellerFeedbackSacReq,
			SacRequestHeader sacRequestHeader) {

		ProdNoti prodNoti = new ProdNoti();
		prodNoti.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		prodNoti.setNotiSeq(createSellerFeedbackSacReq.getNotiSeq());
		prodNoti.setSellerRespTitle(createSellerFeedbackSacReq.getSellerRespTitle());
		prodNoti.setSellerRespOpin(createSellerFeedbackSacReq.getSellerRespOpin());

		// 회원정보 조회
		DetailInformationSacReq memberReq = new DetailInformationSacReq();

		List<SellerMbrSac> sellerMbrSacList = new ArrayList<SellerMbrSac>();
		SellerMbrSac sellerMbrSac = new SellerMbrSac();
		sellerMbrSac.setSellerKey(createSellerFeedbackSacReq.getSellerKey());
		sellerMbrSacList.add(sellerMbrSac);
		memberReq.setSellerMbrSacList(sellerMbrSacList);

		try {
			this.feedbackRepository.detailInformation(memberReq);
		} catch (StorePlatformException e) {
			throw new StorePlatformException("SAC_OTH_9003", e);
		}

		int affectedRow = (Integer) this.feedbackRepository.updateSellerResp(prodNoti);
		if (affectedRow <= 0)
			throw new StorePlatformException("SAC_OTH_9301");

		CreateSellerFeedbackSacRes createSellerFeedbackRes = new CreateSellerFeedbackSacRes();
		createSellerFeedbackRes.setNotiSeq(prodNoti.getNotiSeq());
		return createSellerFeedbackRes;
	}

	@Override
	public ModifySellerFeedbackSacRes modifySellerFeedback(ModifySellerFeedbackSacReq modifySellerFeedbackSacReq,
			SacRequestHeader sacRequestHeader) {

		ProdNoti prodNoti = new ProdNoti();
		prodNoti.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		prodNoti.setNotiSeq(modifySellerFeedbackSacReq.getNotiSeq());
		prodNoti.setSellerRespTitle(modifySellerFeedbackSacReq.getSellerRespTitle());
		prodNoti.setSellerRespOpin(modifySellerFeedbackSacReq.getSellerRespOpin());

		// 회원정보 조회
		DetailInformationSacReq memberReq = new DetailInformationSacReq();

		List<SellerMbrSac> sellerMbrSacList = new ArrayList<SellerMbrSac>();
		SellerMbrSac sellerMbrSac = new SellerMbrSac();
		sellerMbrSac.setSellerKey(modifySellerFeedbackSacReq.getSellerKey());
		sellerMbrSacList.add(sellerMbrSac);
		memberReq.setSellerMbrSacList(sellerMbrSacList);

		try {
			this.feedbackRepository.detailInformation(memberReq);
		} catch (StorePlatformException e) {
			throw new StorePlatformException("SAC_OTH_9003", e);
		}

		int affectedRow = (Integer) this.feedbackRepository.updateSellerResp(prodNoti);
		if (affectedRow <= 0)
			throw new StorePlatformException("SAC_OTH_9302");

		ModifySellerFeedbackSacRes modifySellerFeedbackRes = new ModifySellerFeedbackSacRes();
		modifySellerFeedbackRes.setNotiSeq(prodNoti.getNotiSeq());
		return modifySellerFeedbackRes;
	}

	@Override
	public RemoveSellerFeedbackSacRes removeSellerFeedback(RemoveSellerFeedbackSacReq removeSellerFeedbackSacReq,
			SacRequestHeader sacRequestHeader) {
		ProdNoti prodNoti = new ProdNoti();

		prodNoti.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		prodNoti.setNotiSeq(removeSellerFeedbackSacReq.getNotiSeq());
		prodNoti.setSellerRespTitle("");
		prodNoti.setSellerRespOpin("");

		// 회원정보 조회
		DetailInformationSacReq memberReq = new DetailInformationSacReq();

		List<SellerMbrSac> sellerMbrSacList = new ArrayList<SellerMbrSac>();
		SellerMbrSac sellerMbrSac = new SellerMbrSac();
		sellerMbrSac.setSellerKey(removeSellerFeedbackSacReq.getSellerKey());
		sellerMbrSacList.add(sellerMbrSac);
		memberReq.setSellerMbrSacList(sellerMbrSacList);

		try {
			this.feedbackRepository.detailInformation(memberReq);
		} catch (StorePlatformException e) {
			throw new StorePlatformException("SAC_OTH_9003", e);
		}

		int affectedRow = (Integer) this.feedbackRepository.updateSellerResp(prodNoti);
		if (affectedRow <= 0)
			throw new StorePlatformException("SAC_OTH_9303");

		RemoveSellerFeedbackSacRes removeSellerFeedbackRes = new RemoveSellerFeedbackSacRes();
		removeSellerFeedbackRes.setNotiSeq(prodNoti.getNotiSeq());
		return removeSellerFeedbackRes;
	}

	@Override
	public GetScoreSacRes getScore(GetScoreSacReq getScoreSacReq, SacRequestHeader sacRequestHeader) {
		TenantProdStats tenantProdStats = new TenantProdStats();

		tenantProdStats.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		tenantProdStats.setProdId(getScoreSacReq.getProdId());

		TenantProdStats res = this.feedbackRepository.getProdEvalInfo(tenantProdStats);

		GetScoreSacRes getScoreRes;

		if (res != null) {
			getScoreRes = new GetScoreSacRes();

			getScoreRes.setProdId(getScoreSacReq.getProdId());
			getScoreRes.setTotEvluScore(res.getTotEvluScore());
			getScoreRes.setAvgEvluScore(res.getAvgEvluScore());
			getScoreRes.setAvgEvluScorePct(res.getAvgEvluScorePct());
			getScoreRes.setPaticpersCnt(res.getPaticpersCnt());
		} else {
			throw new StorePlatformException("SAC_OTH_9001");
		}

		return getScoreRes;
	}

	@Override
	public ListScorePaticpersSacRes listScoreParticpers(ListScorePaticpersSacReq listScorePaticpersSacReq,
			SacRequestHeader sacRequestHeader) {
		MbrAvgScore mbrAvgScore = new MbrAvgScore();

		mbrAvgScore.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		mbrAvgScore.setProdId(listScorePaticpersSacReq.getProdId());

		List<MbrAvgScore> mbrAvgScoreList = this.feedbackRepository.getScoreList(mbrAvgScore);

		List<AvgScore> avgScoreList;

		if (mbrAvgScoreList.size() != 0) {
			avgScoreList = new ArrayList<AvgScore>();
			for (int i = 0; i < mbrAvgScoreList.size(); i++) {
				AvgScore avgScore = new AvgScore();

				avgScore.setAvgScore(mbrAvgScoreList.get(i).getAvgScore());
				avgScore.setAvgScorePct(mbrAvgScoreList.get(i).getAvgScorePct());
				avgScore.setPaticpersCnt(mbrAvgScoreList.get(i).getPaticpersCnt());

				avgScoreList.add(avgScore);
			}
		} else {
			throw new StorePlatformException("SAC_OTH_9001");
		}

		ListScorePaticpersSacRes listScoreRes = new ListScorePaticpersSacRes();
		listScoreRes.setAvgScoreList(avgScoreList);

		return listScoreRes;
	}

	/**
	 * 
	 * <pre>
	 * 평점 등록.
	 * </pre>
	 * 
	 * @param object
	 *            object
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 */
	private void setMbrAvgTenantProdStats(Object object, SacRequestHeader sacRequestHeader) {
		BeanWrapperImpl beanWrapperImpl = new BeanWrapperImpl();
		beanWrapperImpl.setWrappedInstance(object);
		String score = (String) beanWrapperImpl.getPropertyValue("avgScore");
		// 채널ID는 평점 테이블 저장시에만 사용된다.
		String chnlId = (String) beanWrapperImpl.getPropertyValue("chnlId");
		String userKey = (String) beanWrapperImpl.getPropertyValue("userKey");
		String prodId = (String) beanWrapperImpl.getPropertyValue("prodId");
		String userId = (String) beanWrapperImpl.getPropertyValue("userId");

		if (StringUtils.isNotBlank(score) && StringUtils.isBlank(chnlId)) {
			String avgScore = score;
			if (NumberUtils.toInt(score, 0) > 5) {
				avgScore = "5";
			} else if (NumberUtils.toInt(score, 0) <= 0) {
				avgScore = "1";
			}
			MbrAvg mbrAvg = new MbrAvg();
			mbrAvg.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
			mbrAvg.setMbrNo(userKey);
			mbrAvg.setProdId(prodId);
			mbrAvg.setAvgScore(avgScore);
			mbrAvg.setRegId(userId);

			MbrAvg getRegMbrAvg = this.feedbackRepository.getRegMbrAvg(mbrAvg);
			this.feedbackRepository.mergeMbrAvg(mbrAvg);

			TenantProdStats updateTenantProdStats = new TenantProdStats();
			updateTenantProdStats.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
			updateTenantProdStats.setProdId(prodId);
			updateTenantProdStats.setRegId(userId);
			updateTenantProdStats.setUpdId(userId);
			if (getRegMbrAvg != null) {
				updateTenantProdStats.setAvgEvluScore(avgScore);
				updateTenantProdStats.setPreAvgScore(getRegMbrAvg.getAvgScore());
				updateTenantProdStats.setAction("create");
				this.feedbackRepository.updateTenantProdStats(updateTenantProdStats);
			} else {
				updateTenantProdStats.setAvgEvluScore(avgScore);
				this.feedbackRepository.mergeTenantProdStats(updateTenantProdStats);
			}
			// 웹툰일경우 채널ID에 에피소드들의 상품통계 평점을 계산한다.
			this.setWebtoonChannelMbrAvgTenantStats(prodId, sacRequestHeader.getTenantHeader().getTenantId(), userId);
		}
	}

	/**
	 * 
	 * <pre>
	 * 사용후기 데이터 가공.
	 * </pre>
	 * 
	 * @param prodNoti
	 *            prodNoti
	 * @param prodType
	 *            prodType
	 * @param detailInformationSacRes
	 *            detailInformationSacRes
	 * @param searchUserSacRes
	 *            searchUserSacRes
	 * @return Feedback
	 */
	private Feedback setFeedback(ProdNoti prodNoti, String prodType, DetailInformationSacRes detailInformationSacRes,
			SearchUserSacRes searchUserSacRes) {
		Feedback feedback = new Feedback();
		feedback.setNotiSeq(prodNoti.getNotiSeq());
		feedback.setUserKey(prodNoti.getMbrNo());
		feedback.setSellerKey(prodNoti.getSellerMbrNo());
		feedback.setNotiTitle(prodNoti.getTitle());
		feedback.setNotiDscr(prodNoti.getNotiDscr());
		feedback.setNotiScore(String.valueOf(prodNoti.getNotiScore()));

		// 기본 등록ID.
		String regId = this.getMaskRegId(prodNoti.getRegId());
		// 사용자 조회후.
		if (searchUserSacRes != null) {
			if (!CollectionUtils.isEmpty(searchUserSacRes.getUserInfo())) {
				UserInfoSac userInfoSac = searchUserSacRes.getUserInfo().get(prodNoti.getMbrNo());
				if (userInfoSac != null) {
					// 사용자가 기기사용자이면.
					if (StringUtils.equals(userInfoSac.getUserType(), MemberConstants.USER_TYPE_MOBILE)
							&& StringUtils.isNotBlank(prodNoti.getMbrTelno())) {

						// 사용후기 이면
						if (prodNoti.getNotiSeq() != null) {
							// 사용후기 테이블의 정보 셋팅 후 마스킹 처리.
							regId = this.getMaskTelNoOrDefaultRegId(prodNoti.getMbrTelno(), prodNoti.getRegId());
						} else {
							// 평점 이면.
							if (!CollectionUtils.isEmpty(userInfoSac.getDeviceIdList())) {
								// 회원정보 셋팅 후 마스킹 처리.
								regId = this.getMaskTelNoOrDefaultRegId(userInfoSac.getDeviceIdList().get(0),
										userInfoSac.getUserId());
							}
						}
					}
				}
			}
		}
		feedback.setRegId(regId);
		feedback.setRegDt(prodNoti.getRegDt());
		feedback.setSellerRespTitle(prodNoti.getSellerRespTitle());
		feedback.setSellerRespOpin(prodNoti.getSellerRespOpin());
		feedback.setSellerRespRegDt(prodNoti.getSellerRespRegDt());
		feedback.setCid(prodNoti.getCid());
		feedback.setSelfYn(prodNoti.getSelfYn());
		feedback.setSaleYn(prodNoti.getSaleYn());
		feedback.setWhose(prodNoti.getWhose());
		feedback.setSelfRecomYn(prodNoti.getNotiYn());
		feedback.setAvgScore(prodNoti.getAvgScore());
		// 판매자 정보
		String nickNm = "";
		String compNm = "";
		String sellerNickName = "";
		String sellerCompany = "";
		String sellerClass = "";
		String charger = "";
		if (StringUtils.isNotBlank(prodNoti.getSellerMbrNo())) {
			// DB분리로 인해 회원 DB 조회대상은 SCI 호출로 대체하여 기존 쿼리상 로직을 Biz 로직으로 대체.
			if (detailInformationSacRes != null
					&& !CollectionUtils.isEmpty(detailInformationSacRes.getSellerMbrListMap())) {

				List<SellerMbrSac> sellerMbarSacList = detailInformationSacRes.getSellerMbrListMap().get(
						prodNoti.getSellerMbrNo());

				if (!CollectionUtils.isEmpty(sellerMbarSacList)) {
					// 회원 정보
					SellerMbrSac sellerMbrSac = sellerMbarSacList.get(0);
					// 회원 닉네임명
					sellerNickName = sellerMbrSac.getSellerNickName();
					// 회원 회사명
					sellerCompany = sellerMbrSac.getSellerCompany();
					// 회원 타입
					sellerClass = sellerMbrSac.getSellerClass();
					// 회원 담당자명
					charger = sellerMbrSac.getCharger();
				}
			}
		}
		// , NVL2( N.SELLER_NOTI_DSCR
		// , NVL2( N.EXPOSURE_DEV_NM
		// , N.EXPOSURE_DEV_NM
		// , DECODE( M.DEV_TP_CD, 'US000401', M.OP_NM, 'US000404', M.FR_COMPANY, M.COMP_NM ) )
		// , NULL) AS NICK_NAME
		// 사용후기 내용이 있으면
		if (StringUtils.isNotBlank(prodNoti.getNotiDscr())) {
			// 상품테이블 판매자명이 있으면
			if (StringUtils.isNotBlank(prodNoti.getExpoSellerNm())) {
				nickNm = prodNoti.getExpoSellerNm();
			} else {
				// 상품테이블에 판매자 노출명이 없고 개인판매자이면 담당자명을 노출
				if (StringUtils.equals(sellerClass, SellerConstants.SELLER_TYPE_PRIVATE_PERSON)) {
					nickNm = charger;
					// 상품테이블에 판매자 노출명이 없고 법인사업자이면 회사명을 노출
				} else {
					// else if (StringUtils.equals(sellerClass, SellerConstants.SELLER_TYPE_LEGAL_BUSINESS)) {
					nickNm = sellerCompany;
					// 기타 추가 될 사항있음으로 기본으로 회사명을 노출
				}
			}
		}
		// 사용후기가 내용이 없으면 NULL
		feedback.setNickNm(nickNm);
		// <!-- Shopping 인 경우만 -->
		// <isEqual property="type" compareValue="shopping">
		// , DECODE( M.DEV_TP_CD
		// , 'US000401', M.OP_NM
		// , 'US000404', M.FR_COMPANY
		// , DECODE( N.SVC_GRP_CD, 'DP000206', M.REP_COMP_NM, M.COMP_NM ) ) AS COMP_NM
		// </isEqual>
		// 쇼핑 상품인 경우.
		if (StringUtils.equals(prodType, "shopping")) {
			// 쇼핑 상품이면서 개인 판매자이면,
			if (StringUtils.equals(sellerClass, SellerConstants.SELLER_TYPE_PRIVATE_PERSON)) {
				compNm = charger;
				// 법인사업자이면.
			} else if (StringUtils.equals(sellerClass, SellerConstants.SELLER_TYPE_LEGAL_BUSINESS)) {
				compNm = sellerCompany;
			} else {
				// TStore 쇼핑인경우.
				if (StringUtils.equals(prodNoti.getSvcGrpCd(), DisplayConstants.DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD)) {
					compNm = sellerNickName;
				} else {
					compNm = sellerCompany;
				}
			}
		}
		feedback.setCompNm(compNm);
		feedback.setFbPostYn(prodNoti.getFbPostYn());
		feedback.setProdId(prodNoti.getProdId());

		return feedback;
	}

	/**
	 * 
	 * <pre>
	 * Webtoon 상품의 경우 채널-에피소드의 관계에서 에피소드의 평점이 등록/수정이 되면 채널도 에피소드 전체에 대한 통계를 업데이트 한다.
	 * </pre>
	 * 
	 * @param partProdId
	 *            partProdId
	 * @param tenantId
	 *            tenantId
	 * @param userId
	 *            userId
	 */
	private void setWebtoonChannelMbrAvgTenantStats(String partProdId, String tenantId, String userId) {
		// 상품ID에 대해서 해당 상품에 채널/에피소드 관계이면서 웹툰일경우에만
		// 채널ID에 해당되는 상품통계에 대해서 전체 통계를 진행하는 로직.(모바일POC 요청)
		Map<String, String> chnlEpisodeMap = this.feedbackRepository.getChannelEpisodeRelation(partProdId);
		if (!CollectionUtils.isEmpty(chnlEpisodeMap)) {
			String channelId = chnlEpisodeMap.get("PROD_ID");
			String topMenuId = chnlEpisodeMap.get("TOP_MENU_ID");
			if (StringUtils.equals(topMenuId, DisplayConstants.DP_WEBTOON_TOP_MENU_ID)) {
				// 채널 상품 통계 조회.
				int count = (Integer) this.feedbackRepository.getChannelTenantProdStatsCount(partProdId);
				TenantProdStats tenantProdStats = new TenantProdStats();
				tenantProdStats.setTenantId(tenantId);
				tenantProdStats.setProdId(channelId);
				tenantProdStats.setRegId(userId);
				tenantProdStats.setUpdId(userId);
				if (count > 0) {
					// 조회결과 있을 경우 전체 에피소드 통계 Update 처리
					this.feedbackRepository.updateChannelTenantProdStats(tenantProdStats);
				} else {
					// 조회결과 없을 경우 전체 에피소드 통계 Insert 처리
					this.feedbackRepository.insertChannelTenantProdStats(tenantProdStats);
				}
			}
		}
	}

	/**
	 * 
	 * <pre>
	 * 회원 TelNo Masking.
	 * </pre>
	 * 
	 * @param telNo
	 *            telNo
	 * @param defaultRegId
	 *            defaultRegId
	 * @return String
	 */
	private String getMaskTelNoOrDefaultRegId(String telNo, String defaultRegId) {
		// telNo 방어로직 추가.
		if (StringUtils.isNotBlank(telNo)) {
			try {
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append(StringUtils.substring(telNo, 0, 3));
				// mdn 마스킹 처리.
				if (telNo.length() < 11) {
					stringBuffer.append("***");
				} else {
					stringBuffer.append("****");
				}
				return stringBuffer.append(StringUtils.substring(telNo, telNo.length() - 4, telNo.length())).toString();
			} catch (Exception e) {
				// 휴대폰 번호가 아닌경우.
				// 회원 ID 마스킹 처리.
				return this.getMaskRegId(defaultRegId);
			}
		}
		// 휴대폰 번호가 없는경우.
		// 회원 ID 마스킹 처리.
		return this.getMaskRegId(defaultRegId);
	}

	/**
	 * 
	 * <pre>
	 * 회원 ID의 Masking.
	 * </pre>
	 * 
	 * @param regId
	 *            regId
	 * @return String
	 */
	private String getMaskRegId(String regId) {
		if (StringUtils.isNotBlank(regId)) {
			if (regId.length() > 3) {
				return new StringBuffer().append(regId.substring(0, 3)).append("*****").toString();
			} else {
				return StringUtils.rightPad(regId, 8, "*");
			}
		}
		return StringUtils.EMPTY;
	}
}
