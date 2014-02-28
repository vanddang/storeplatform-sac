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
		this.feedbackRepository.searchUserByUserKey(searchUserSacReq);

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
					throw new StorePlatformException("SAC_OTH_1001");
			} else {
				// 에러.
				throw new StorePlatformException("SAC_OTH_1001");
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
		this.feedbackRepository.searchUserByUserKey(searchUserSacReq);

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
				throw new StorePlatformException("SAC_OTH_1002");
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
		this.feedbackRepository.searchUserByUserKey(searchUserSacReq);

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
			}
		}

		// 사용후기 삭제, DEL_YN = 'Y'.
		ProdNoti prodNoti = new ProdNoti();
		prodNoti.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		prodNoti.setNotiSeq(removeFeedbackSacReq.getNotiSeq());
		prodNoti.setMbrNo(removeFeedbackSacReq.getUserKey());
		int affectedRow = (Integer) this.feedbackRepository.deleteProdNoti(prodNoti);
		if (affectedRow <= 0) {
			throw new StorePlatformException("SAC_OTH_1002");
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
		this.feedbackRepository.searchUserByUserKey(searchUserSacReq);

		// 기 추천여부 조회.
		ProdNoti prodNoti = new ProdNoti();
		prodNoti.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		prodNoti.setNotiSeq(createRecommendFeedbackReq.getNotiSeq());
		prodNoti.setProdId(createRecommendFeedbackReq.getProdId());
		prodNoti.setMbrNo(createRecommendFeedbackReq.getUserKey());

		int count = (Integer) this.feedbackRepository.getProdNotiGoodCount(prodNoti);

		if (count > 0) {
			throw new StorePlatformException("SAC_OTH_1001");
		}

		// 사용후기 추천 등록.
		ProdNotiGood prodNotiGood = new ProdNotiGood();
		prodNotiGood.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		prodNotiGood.setNotiSeq(createRecommendFeedbackReq.getNotiSeq());
		prodNotiGood.setMbrNo(createRecommendFeedbackReq.getUserKey());
		prodNotiGood.setRegId(createRecommendFeedbackReq.getUserId());
		int affectedRow = (Integer) this.feedbackRepository.insertProdNotiGood(prodNotiGood);

		if (affectedRow <= 0) {
			throw new StorePlatformException("SAC_OTH_1001");
		}

		// 사용후기 추천 업데이트.
		prodNotiGood.setAction("create");
		affectedRow = (Integer) this.feedbackRepository.updateProdNotiGood(prodNotiGood);

		if (affectedRow <= 0) {
			throw new StorePlatformException("SAC_OTH_1002");
		}

		prodNoti.setStartRow("1");
		prodNoti.setEndRow("1");
		// 등록된 사용후기 조회.
		ProdNoti res = this.feedbackRepository.getProdNoti(prodNoti);

		if (res == null) {
			throw new StorePlatformException("SAC_OTH_9001");
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
		this.feedbackRepository.searchUserByUserKey(searchUserSacReq);

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
		int affectedRow = (Integer) this.feedbackRepository.deleteProdNotiGood(prodNotiGood);

		if (affectedRow <= 0) {
			throw new StorePlatformException("SAC_OTH_1003");
		}

		// 사용후기 추천 업데이트.
		prodNotiGood.setAction("remove");
		affectedRow = (Integer) this.feedbackRepository.updateProdNotiGood(prodNotiGood);

		if (affectedRow <= 0) {
			throw new StorePlatformException("SAC_OTH_1002");
		}

		prodNoti.setStartRow("1");
		prodNoti.setEndRow("1");

		// 삭제된 사용후기 조회.
		ProdNoti res = this.feedbackRepository.getProdNoti(prodNoti);

		if (res == null) {
			throw new StorePlatformException("SAC_OTH_9001");
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
			listFeedbackRes.setAvgEvluScorePcts("0");
			listFeedbackRes.setAvgEvluScore("0");
			listFeedbackRes.setDwldCnt("0");
			listFeedbackRes.setPaticpersCnt("0");
		} else {
			listFeedbackRes.setAvgEvluScorePcts(ObjectUtils.defaultIfNull(getProdEvalInfo.getAvgEvluScorePcts(), "0"));
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
		if (totalCount <= 0) {
			throw new StorePlatformException("SAC_OTH_9001");
		}
		listFeedbackRes.setNotiTot(String.valueOf(totalCount));
		// 사용후기 목록.
		List<ProdNoti> getProdnotiList = this.feedbackRepository.getProdNotiList(prodNoti);
		if (CollectionUtils.isEmpty(getProdnotiList)) {
			throw new StorePlatformException("SAC_OTH_9001");
		}

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

		this.feedbackRepository.detailInformation(memberReq);

		int affectedRow = (Integer) this.feedbackRepository.updateSellerResp(prodNoti);
		if (affectedRow <= 0)
			throw new StorePlatformException("SAC_OTH_1001");

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

		int affectedRow = (Integer) this.feedbackRepository.updateSellerResp(prodNoti);
		if (affectedRow <= 0)
			throw new StorePlatformException("SAC_OTH_1001");

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

		this.feedbackRepository.detailInformation(memberReq);

		int affectedRow = (Integer) this.feedbackRepository.updateSellerResp(prodNoti);
		if (affectedRow <= 0)
			throw new StorePlatformException("SAC_OTH_1001");

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
		if (StringUtils.isNotEmpty(score) && StringUtils.isEmpty(chnlId)) {
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
		}
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param prodNoti
	 * @return
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

		String regId = "";
		if (searchUserSacRes != null) {
			if (!CollectionUtils.isEmpty(searchUserSacRes.getUserInfo())) {
				UserInfoSac userInfoSac = searchUserSacRes.getUserInfo().get(prodNoti.getMbrNo());
				if (userInfoSac != null) {
					if (StringUtils.equals(userInfoSac.getUserType(), MemberConstants.USER_TYPE_MOBILE)
							&& StringUtils.isNotBlank(prodNoti.getMbrTelno())) {
						String telNo = "";
						if (prodNoti.getNotiSeq() != null) {
							telNo = prodNoti.getMbrTelno();
						} else {
							if (!CollectionUtils.isEmpty(userInfoSac.getDeviceIdList())) {
								telNo = userInfoSac.getDeviceIdList().get(0);
							} else {
								telNo = userInfoSac.getUserId();
							}
						}
						StringBuffer stringBuffer = new StringBuffer();
						stringBuffer.append(StringUtils.substring(telNo, 0, 3));
						if (telNo.length() < 11) {
							stringBuffer.append("***");
						} else {
							stringBuffer.append("****");
						}
						regId = stringBuffer.append(StringUtils.substring(telNo, telNo.length() - 4, telNo.length()))
								.toString();
					} else {
						regId = userInfoSac.getUserId();
					}
				} else {
					regId = prodNoti.getRegId();
				}
			} else {
				regId = prodNoti.getRegId();
			}
		} else {
			regId = prodNoti.getRegId();
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
		String nickNm = "";
		String compNm = "";
		String sellerNickName = "";
		String sellerCompany = "";
		String sellerClass = "";
		String charger = "";
		if (StringUtils.isNotBlank(prodNoti.getSellerMbrNo())) {
			if (detailInformationSacRes != null
					&& !CollectionUtils.isEmpty(detailInformationSacRes.getSellerMbrListMap())) {

				List<SellerMbrSac> sellerMbarSacList = detailInformationSacRes.getSellerMbrListMap().get(
						prodNoti.getSellerMbrNo());

				if (!CollectionUtils.isEmpty(sellerMbarSacList)) {
					SellerMbrSac sellerMbrSac = sellerMbarSacList.get(0);
					sellerNickName = sellerMbrSac.getSellerNickName();
					sellerCompany = sellerMbrSac.getSellerCompany();
					sellerClass = sellerMbrSac.getSellerClass();
					charger = sellerMbrSac.getCharger();
				}

			}
		}
		// 테스트 해보기.

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
				} else if (StringUtils.equals(sellerClass, SellerConstants.SELLER_TYPE_LEGAL_BUSINESS)) {
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
			} else if (StringUtils.equals(sellerClass, SellerConstants.SELLER_TYPE_LEGAL_BUSINESS)) {
				compNm = sellerCompany;
			} else {

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
}
