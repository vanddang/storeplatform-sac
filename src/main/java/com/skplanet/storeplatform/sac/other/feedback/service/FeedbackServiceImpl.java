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
import java.util.List;

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
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacRes;
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

		// ?? 회원 정보 조회 회원SCI 조회.
		this.feedbackRepository.searchUserByUserKey(createFeedbackSacReq.getUserKey());

		// 평점 저장
		this.setMbrAvgTenantProdStats(createFeedbackSacReq, sacRequestHeader);

		String notiSeq = "";

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
			ProdNoti getRegProdNoti = this.feedbackRepository.getRegProdNoti(prodNoti);
			if (getRegProdNoti == null) {
				int affectedRow = (Integer) this.feedbackRepository.insertProdNoti(prodNoti);
				if (affectedRow <= 0)
					throw new StorePlatformException("SAC_OTH_1001");
			} else {
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

		// ?? 회원 정보 조회 회원SCI 조회.
		this.feedbackRepository.searchUserByUserKey(modifyFeedbackSacReq.getUserKey());

		// 평점 저장.
		this.setMbrAvgTenantProdStats(modifyFeedbackSacReq, sacRequestHeader);

		String notiSeq = "";

		if (StringUtils.isNotEmpty(modifyFeedbackSacReq.getNotiDscr())) {
			ProdNoti prodNoti = new ProdNoti();
			prodNoti.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
			prodNoti.setNotiSeq(modifyFeedbackSacReq.getNotiSeq());
			prodNoti.setMbrNo(modifyFeedbackSacReq.getUserKey());
			// prodNoti.setProdId(modifyFeedbackSacReq.getProdId());
			prodNoti.setTitle(modifyFeedbackSacReq.getNotiTitle());
			prodNoti.setNotiDscr(modifyFeedbackSacReq.getNotiDscr());
			// prodNoti.setRegId(createFeedbackSacReq.getUserId());
			// prodNoti.setMbrTelno(createFeedbackSacReq.getDeviceId());
			// prodNoti.setFbPostYn(createFeedbackSacReq.getFbSendYN());
			prodNoti.setDeviceModelCd(sacRequestHeader.getDeviceHeader().getModel());
			prodNoti.setPkgVer(modifyFeedbackSacReq.getPkgVer());
			// prodNoti.setChnlId(modifyFeedbackSacReq.getChnlId());

			int affectedRow = (Integer) this.feedbackRepository.updateProdNoti(prodNoti);

			if (affectedRow > 0) {
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

		// ?? 회원 정보 조회 회원SCI 조회.
		this.feedbackRepository.searchUserByUserKey(removeFeedbackSacReq.getUserKey());

		// 기 평가여부 조회
		MbrAvg mbrAvg = new MbrAvg();
		mbrAvg.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		mbrAvg.setMbrNo(removeFeedbackSacReq.getUserKey());
		mbrAvg.setProdId(removeFeedbackSacReq.getProdId());
		MbrAvg getRegMbrAvg = this.feedbackRepository.getRegMbrAvg(mbrAvg);
		if (getRegMbrAvg != null) {
			this.feedbackRepository.deleteMbrAvg(mbrAvg);
			TenantProdStats tenantProdStats = new TenantProdStats();
			tenantProdStats.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
			tenantProdStats.setProdId(removeFeedbackSacReq.getProdId());
			tenantProdStats.setUpdId(removeFeedbackSacReq.getUserId());
			TenantProdStats getTenantProdStats = this.feedbackRepository.getTenantProdStats(tenantProdStats);
			if (getTenantProdStats != null) {
				if (NumberUtils.toInt(getTenantProdStats.getPaticpersCnt(), 0) == 1) {
					this.feedbackRepository.deleteTenantProdStats(tenantProdStats);
				} else {
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

		ProdNoti prodNoti = new ProdNoti();
		prodNoti.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		prodNoti.setNotiSeq(removeFeedbackSacReq.getNotiSeq());
		// 삭제는 DEL_YN만 셋팅해준다.
		// prodNoti.setNotiDscr(StringUtils.EMPTY);
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

		// ?? 회원 정보 조회 회원SCI 조회.
		this.feedbackRepository.searchUserByUserKey(createRecommendFeedbackReq.getUserKey());

		ProdNoti prodNoti = new ProdNoti();
		prodNoti.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		prodNoti.setNotiSeq(createRecommendFeedbackReq.getNotiSeq());
		prodNoti.setProdId(createRecommendFeedbackReq.getProdId());
		prodNoti.setMbrNo(createRecommendFeedbackReq.getUserKey());
		// 탈퇴회원 사용후기 여부 조회
		int count = (Integer) this.feedbackRepository.getProdNotiWDCount(prodNoti);

		if (count > 0) {
			// 기 추천여부
			count = (Integer) this.feedbackRepository.getProdNotiWDGoodCount(prodNoti);

			if (count > 0) {
				throw new StorePlatformException("SAC_OTH_1001");
			}

			ProdNotiGood prodNotiGood = new ProdNotiGood();
			prodNotiGood.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
			prodNotiGood.setNotiSeq(createRecommendFeedbackReq.getNotiSeq());
			prodNotiGood.setMbrNo(createRecommendFeedbackReq.getUserKey());
			prodNotiGood.setRegId(createRecommendFeedbackReq.getUserId());
			int affectedRow = (Integer) this.feedbackRepository.insertProdNotiGood(prodNotiGood);

			if (affectedRow <= 0) {
				throw new StorePlatformException("SAC_OTH_1001");
			}

			prodNotiGood.setAction("create");
			affectedRow = (Integer) this.feedbackRepository.updateProdNotiWDGood(prodNotiGood);

			if (affectedRow <= 0) {
				throw new StorePlatformException("SAC_OTH_1002");
			}
		} else {

			count = (Integer) this.feedbackRepository.getProdNotiGoodCount(prodNoti);

			if (count > 0) {
				throw new StorePlatformException("SAC_OTH_1001");
			}

			ProdNotiGood prodNotiGood = new ProdNotiGood();
			prodNotiGood.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
			prodNotiGood.setNotiSeq(createRecommendFeedbackReq.getNotiSeq());
			prodNotiGood.setMbrNo(createRecommendFeedbackReq.getUserKey());
			prodNotiGood.setRegId(createRecommendFeedbackReq.getUserId());
			int affectedRow = (Integer) this.feedbackRepository.insertProdNotiGood(prodNotiGood);

			if (affectedRow <= 0) {
				throw new StorePlatformException("SAC_OTH_1001");
			}

			prodNotiGood.setAction("create");
			affectedRow = (Integer) this.feedbackRepository.updateProdNotiGood(prodNotiGood);

			if (affectedRow <= 0) {
				throw new StorePlatformException("SAC_OTH_1002");
			}
		}

		// 추천은 현재 수정 반영된 1건만 조회.
		prodNoti.setStartRow("1");
		prodNoti.setEndRow("1");
		ProdNoti res = this.feedbackRepository.getProdNoti(prodNoti);

		if (res == null) {
			throw new StorePlatformException("SAC_OTH_9001");
		}

		Feedback feedback = this.setFeedback(res, "");

		CreateRecommendFeedbackSacRes createRecommendFeedbackSacRes = new CreateRecommendFeedbackSacRes();
		BeanUtils.copyProperties(feedback, createRecommendFeedbackSacRes);

		return createRecommendFeedbackSacRes;
	}

	@Override
	public RemoveRecommendFeedbackSacRes removeRecommend(RemoveRecommendFeedbackSacReq removeRecommendFeedbackSacReq,
			SacRequestHeader sacRequestHeader) {

		// ?? 회원 정보 조회 회원SCI 조회.
		// this.feedbackRepository.searchUserByUserKey(removeRecommendFeedbackSacReq.getUserKey());

		ProdNoti prodNoti = new ProdNoti();
		prodNoti.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		prodNoti.setNotiSeq(removeRecommendFeedbackSacReq.getNotiSeq());
		prodNoti.setProdId(removeRecommendFeedbackSacReq.getProdId());
		prodNoti.setMbrNo(removeRecommendFeedbackSacReq.getUserKey());
		// 탈퇴회원 사용후기 여부 조회
		int count = (Integer) this.feedbackRepository.getProdNotiWDCount(prodNoti);

		if (count > 0) {
			ProdNotiGood prodNotiGood = new ProdNotiGood();
			prodNotiGood.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
			prodNotiGood.setNotiSeq(removeRecommendFeedbackSacReq.getNotiSeq());
			prodNotiGood.setMbrNo(removeRecommendFeedbackSacReq.getUserKey());
			int affectedRow = (Integer) this.feedbackRepository.deleteProdNotiGood(prodNotiGood);

			if (affectedRow <= 0) {
				throw new StorePlatformException("SAC_OTH_1003");
			}

			prodNotiGood.setAction("remove");
			affectedRow = (Integer) this.feedbackRepository.updateProdNotiWDGood(prodNotiGood);

			if (affectedRow <= 0) {
				throw new StorePlatformException("SAC_OTH_1002");
			}

		} else {
			ProdNotiGood prodNotiGood = new ProdNotiGood();
			prodNotiGood.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
			prodNotiGood.setNotiSeq(removeRecommendFeedbackSacReq.getNotiSeq());
			prodNotiGood.setMbrNo(removeRecommendFeedbackSacReq.getUserKey());
			int affectedRow = (Integer) this.feedbackRepository.deleteProdNotiGood(prodNotiGood);

			if (affectedRow <= 0) {
				throw new StorePlatformException("SAC_OTH_1003");
			}

			prodNotiGood.setAction("remove");
			affectedRow = (Integer) this.feedbackRepository.updateProdNotiGood(prodNotiGood);

			if (affectedRow <= 0) {
				throw new StorePlatformException("SAC_OTH_1002");
			}
		}

		// 추천 취소는 현재 수정된 1건만 조회.
		prodNoti.setStartRow("1");
		prodNoti.setEndRow("1");
		ProdNoti res = this.feedbackRepository.getProdNoti(prodNoti);

		if (res == null) {
			throw new StorePlatformException("SAC_OTH_9001");
		}

		Feedback feedback = this.setFeedback(res, "");

		RemoveRecommendFeedbackSacRes removeRecommendFeedbackSacRes = new RemoveRecommendFeedbackSacRes();
		BeanUtils.copyProperties(feedback, removeRecommendFeedbackSacRes);

		return removeRecommendFeedbackSacRes;
	}

	@Override
	public ListFeedbackSacRes list(ListFeedbackSacReq listFeedbackSacReq, SacRequestHeader sacRequestHeader) {

		TenantProdStats tenantProdStats = new TenantProdStats();
		tenantProdStats.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		tenantProdStats.setProdId(listFeedbackSacReq.getProdId());
		TenantProdStats getProdEvalInfo = this.feedbackRepository.getProdEvalInfo(tenantProdStats);
		if (getProdEvalInfo == null) {
			throw new StorePlatformException("SAC_OTH_9001");
		}
		ListFeedbackSacRes listFeedbackRes = new ListFeedbackSacRes();
		listFeedbackRes.setAvgEvluScorePcts(getProdEvalInfo.getAvgEvluScorePcts());
		listFeedbackRes.setAvgEvluScore(getProdEvalInfo.getAvgEvluScore());
		listFeedbackRes.setDwldCnt(getProdEvalInfo.getDwldCnt());
		listFeedbackRes.setPaticpersCnt(getProdEvalInfo.getPaticpersCnt());
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
		int totalCount = (Integer) this.feedbackRepository.getProdNotiCount(prodNoti);
		if (totalCount <= 0) {
			throw new StorePlatformException("SAC_OTH_9001");
		}
		listFeedbackRes.setNotiTot(String.valueOf(totalCount));
		List<ProdNoti> getProdnotiList = this.feedbackRepository.getProdNotiList(prodNoti);
		if (CollectionUtils.isEmpty(getProdnotiList)) {
			throw new StorePlatformException("SAC_OTH_9001");
		}
		List<Feedback> notiList = new ArrayList<Feedback>();
		for (ProdNoti res : getProdnotiList) {
			notiList.add(this.setFeedback(res, listFeedbackSacReq.getProdType()));
		}
		listFeedbackRes.setNotiList(notiList);
		return listFeedbackRes;
		// ListFeedbackSacRes listFeedbackRes = new ListFeedbackSacRes();
		// listFeedbackRes.setAvgEvluScorePcts("80,100,20,10,50");
		// listFeedbackRes.setNotiTot("2");
		// listFeedbackRes.setAvgEvluScore("2");
		// listFeedbackRes.setDwldCnt("11103");
		// listFeedbackRes.setPaticpersCnt("105");
		//
		// listFeedbackRes.setNotiList(this.getFeedbackList());
		//
		// return listFeedbackRes;

	}

	@Override
	public ListMyFeedbackSacRes listMyFeedback(ListMyFeedbackSacReq listMyFeedbackSacReq,
			SacRequestHeader sacRequestHeader) {

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

		int notiTot = (Integer) this.feedbackRepository.getMyProdNotiCount(prodNoti);

		if (notiTot <= 0) {
			throw new StorePlatformException("SAC_OTH_9001");
		}

		ListMyFeedbackSacRes listMyFeedbackSacRes = new ListMyFeedbackSacRes();
		listMyFeedbackSacRes.setNotiTot(String.valueOf(notiTot));

		List<ProdNoti> getMyProdNotiList = this.feedbackRepository.getMyProdNotiList(prodNoti);

		if (CollectionUtils.isEmpty(getMyProdNotiList)) {
			throw new StorePlatformException("SAC_OTH_9001");
		}

		List<FeedbackMy> notiMyList = new ArrayList<FeedbackMy>();

		for (ProdNoti res : getMyProdNotiList) {
			Feedback feedback = this.setFeedback(res, listMyFeedbackSacReq.getProdType());
			FeedbackMy feedbackMy = new FeedbackMy();
			BeanUtils.copyProperties(feedback, feedbackMy);
			notiMyList.add(feedbackMy);
		}

		listMyFeedbackSacRes.setNotiList(notiMyList);
		return listMyFeedbackSacRes;
		// List<Feedback> notiList = this.getFeedbackList();
		// List<FeedbackMy> notiMyList = new ArrayList<FeedbackMy>();
		// for (Feedback res : notiList) {
		// FeedbackMy feedbackMy = new FeedbackMy(); // feedbackMy.setTotalCount("10");
		// BeanUtils.copyProperties(res, feedbackMy);
		// notiMyList.add(feedbackMy);
		// }
		// ListMyFeedbackSacRes listMyFeedbackRes = new ListMyFeedbackSacRes();
		// listMyFeedbackRes.setNotiTot("2");
		// listMyFeedbackRes.setNotiList(notiMyList);
		// return listMyFeedbackRes;

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

		memberReq.setSellerKey(createSellerFeedbackSacReq.getSellerKey());
		this.feedbackRepository.detailInformation(memberReq);

		// 탈퇴회원 사용후기 등록 수 조회
		int count = (Integer) this.feedbackRepository.getProdNotiWDCount(prodNoti);

		LOGGER.info("### 탈퇴회원 사용후기 등록 수 : {}", count);

		if (count > 0) {
			int affectedRow = (Integer) this.feedbackRepository.updateSellerRespWD(prodNoti);
			if (affectedRow <= 0)
				throw new StorePlatformException("SAC_OTH_1001");
		} else {
			int affectedRow = (Integer) this.feedbackRepository.updateSellerResp(prodNoti);
			if (affectedRow <= 0)
				throw new StorePlatformException("SAC_OTH_1001");
		}

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

		memberReq.setSellerKey(modifySellerFeedbackSacReq.getSellerKey());
		this.feedbackRepository.detailInformation(memberReq);

		// 탈퇴회원 사용후기 등록 수 조회
		int count = (Integer) this.feedbackRepository.getProdNotiWDCount(prodNoti);

		LOGGER.info("### 탈퇴회원 사용후기 등록 수 : {}", count);

		if (count > 0) {
			int affectedRow = (Integer) this.feedbackRepository.updateSellerRespWD(prodNoti);
			if (affectedRow <= 0)
				throw new StorePlatformException("SAC_OTH_1001");
		} else {
			int affectedRow = (Integer) this.feedbackRepository.updateSellerResp(prodNoti);
			if (affectedRow <= 0)
				throw new StorePlatformException("SAC_OTH_1001");
		}

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

		memberReq.setSellerKey(removeSellerFeedbackSacReq.getSellerKey());
		this.feedbackRepository.detailInformation(memberReq);

		// 탈퇴회원 사용후기 등록 수 조회
		int count = (Integer) this.feedbackRepository.getProdNotiWDCount(prodNoti);

		LOGGER.info("### 탈퇴회원 사용후기 등록 수 : {}", count);

		if (count > 0) {
			int affectedRow = (Integer) this.feedbackRepository.updateSellerRespWD(prodNoti);
			if (affectedRow <= 0)
				throw new StorePlatformException("SAC_OTH_1001");
		} else {
			int affectedRow = (Integer) this.feedbackRepository.updateSellerResp(prodNoti);
			if (affectedRow <= 0)
				throw new StorePlatformException("SAC_OTH_1001");
		}

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
	 * Feedback Sample.
	 * </pre>
	 * 
	 * @return List<Feedback>
	 */
	private List<Feedback> getFeedbackList() {

		List<Feedback> notiList = new ArrayList<Feedback>();

		for (int i = 0; i < 2; i++) {
			Feedback feedback = new Feedback();
			feedback.setNotiSeq("" + i);
			feedback.setUserKey("IW11000162200905070014211" + i);
			feedback.setSellerKey("IF111111111111111111111" + i);
			feedback.setNotiTitle("좋아요!!(" + i + ")");
			feedback.setNotiDscr("프로야구 게임 재밌네요~ ㅋㅋ(" + i + ")");
			feedback.setNotiScore("0");
			feedback.setRegId("011****5052");
			feedback.setRegDt("20090606035251");
			feedback.setSellerRespTitle("");
			feedback.setSellerRespOpin("");
			feedback.setSellerRespRegDt("");
			feedback.setCid("0000000001");
			feedback.setSelfYn("N");
			feedback.setSaleYn("N");
			feedback.setWhose("mine");
			feedback.setSelfRecomYn("N");
			feedback.setAvgScore("5");
			feedback.setNickNm("");
			feedback.setFbPostYn("N");
			feedback.setProdId("0000000001");
			feedback.setCompNm("");
			notiList.add(feedback);
		}
		return notiList;
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
	private Feedback setFeedback(ProdNoti prodNoti, String prodType) {
		Feedback feedback = new Feedback();
		feedback.setNotiSeq(prodNoti.getNotiSeq());
		feedback.setUserKey(prodNoti.getMbrNo());
		feedback.setSellerKey(prodNoti.getSellerMbrNo());
		feedback.setNotiTitle(prodNoti.getTitle());
		feedback.setNotiDscr(prodNoti.getNotiDscr());
		feedback.setNotiScore(String.valueOf(prodNoti.getNotiScore()));

		// ?? 회원 SCI 연동후, 해당 ID가 기기사용자일경우, mdn을 별표처리한다. 그외는 regId를 셋팅한다.
		SearchUserSacRes searchUserSacRes = null;
		try {
			searchUserSacRes = this.feedbackRepository.searchUserByUserKey(prodNoti.getMbrNo());
		} catch (Exception e) {
			searchUserSacRes = null;
		}
		String regId = "";
		// String regId = "010***1234";
		if (searchUserSacRes != null
				&& StringUtils.equals(searchUserSacRes.getUserType(), MemberConstants.USER_TYPE_MOBILE)
				&& StringUtils.isNotBlank(prodNoti.getMbrTelno())) {
			String telNo = prodNoti.getMbrTelno();
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(StringUtils.substring(telNo, 0, 3));
			if (telNo.length() < 11) {
				stringBuffer.append("***");
			} else {
				stringBuffer.append("****");
			}
			regId = stringBuffer.append(StringUtils.substring(telNo, telNo.length() - 4, telNo.length())).toString();
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
			DetailInformationSacReq detailInformationSacReq = new DetailInformationSacReq();
			detailInformationSacReq.setSellerKey(prodNoti.getSellerMbrNo());
			DetailInformationSacRes detailInformationSacRes = null;
			try {
				detailInformationSacRes = this.feedbackRepository.detailInformation(detailInformationSacReq);
			} catch (Exception e) {
				// 개발자 정보조회는 Exception 발생할 경우는 Null로 전환
				detailInformationSacRes = null;
			}
			if (detailInformationSacRes != null && detailInformationSacRes.getSellerMbr() != null) {
				sellerNickName = detailInformationSacRes.getSellerMbr().getSellerNickName();
				sellerCompany = detailInformationSacRes.getSellerMbr().getSellerCompany();
				sellerClass = detailInformationSacRes.getSellerMbr().getSellerClass();
				charger = detailInformationSacRes.getSellerMbr().getCharger();
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
				} else {
					nickNm = sellerCompany;
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
