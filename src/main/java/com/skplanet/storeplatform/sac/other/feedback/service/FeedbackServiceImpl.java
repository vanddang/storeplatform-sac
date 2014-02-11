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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.NumberUtils;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.AvgScore;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ChangeFeedbackUserIdSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ChangeFeedbackUserIdSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ChangeFeedbackUserKeySacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ChangeFeedbackUserKeySacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateFeedbackSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateRecommendFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateRecommendFeedbackSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateSellerFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateSellerFeedbackSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.Feedback;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.GetScoreSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.GetScoreSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListFeedbackSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListMyFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListMyFeedbackSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListScoreParticpersSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListScoreParticpersSacRes;
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
import com.skplanet.storeplatform.sac.other.feedback.controller.FeedbackController;
import com.skplanet.storeplatform.sac.other.feedback.repository.FeedbackRepository;
import com.skplanet.storeplatform.sac.other.feedback.vo.MbrAvg;
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
@Transactional
public class FeedbackServiceImpl implements FeedbackService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackController.class);

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Override
	public CreateFeedbackSacRes create(CreateFeedbackSacReq createFeedbackSacReq, SacRequestHeader sacRequestHeader) {

		// 평점 저장
		this.setMbrAvgTenantProdStats(createFeedbackSacReq, sacRequestHeader);

		// 사용후기 내용이 있을 경우에만 등록한다.
		// 필수 파라미터이기 때문에 if 로직은 빠져되 된다.
		// if (StringUtils.isNotEmpty(createFeedbackSacReq.getNotiDesc())) {
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
		CreateFeedbackSacRes createFeedbackSacRes = new CreateFeedbackSacRes();
		createFeedbackSacRes.setNotiSeq(prodNoti.getNotiSeq());
		return createFeedbackSacRes;
	}

	@Override
	public ModifyFeedbackSacRes modify(ModifyFeedbackSacReq modifyFeedbackSacReq, SacRequestHeader sacRequestHeader) {

		// 평점 저장.
		this.setMbrAvgTenantProdStats(modifyFeedbackSacReq, sacRequestHeader);

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

		ModifyFeedbackSacRes modifyFeedbackSacRes = new ModifyFeedbackSacRes();
		modifyFeedbackSacRes.setNotiSeq(prodNoti.getNotiSeq());
		// ModifyFeedbackSacRes modifyFeedbackSacRes = new ModifyFeedbackSacRes();
		// modifyFeedbackSacRes.setNotiSeq("14275");
		return modifyFeedbackSacRes;
	}

	@Override
	public RemoveFeedbackSacRes remove(RemoveFeedbackSacReq removeFeedbackSacReq, SacRequestHeader sacRequestHeader) {
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
		prodNoti.setNotiDscr(StringUtils.EMPTY);
		prodNoti.setMbrNo(removeFeedbackSacReq.getUserKey());
		int affectedRow = (Integer) this.feedbackRepository.deleteProdNoti(prodNoti);
		if (affectedRow <= 0) {
			throw new StorePlatformException("SAC_OTH_1002");
		}

		RemoveFeedbackSacRes removeFeedbackSacRes = new RemoveFeedbackSacRes();
		removeFeedbackSacRes.setNotiSeq(removeFeedbackSacReq.getNotiSeq());

		return removeFeedbackSacRes;
	}

	@Override
	public CreateRecommendFeedbackSacRes createRecommend(CreateRecommendFeedbackSacReq createRecommendFeedbackReq,
			SacRequestHeader sacRequestHeader) {

		// ProdNoti prodNoti = new ProdNoti();
		// prodNoti.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		// prodNoti.setNotiSeq(createRecommendFeedbackReq.getNotiSeq());
		// prodNoti.setProdId(createRecommendFeedbackReq.getProdId());
		// prodNoti.setMbrNo(createRecommendFeedbackReq.getUserKey());
		// // 탈퇴회원 사용후기 여부 조회
		// int count = (Integer) this.feedbackRepository.getProdNotiWDCount(prodNoti);
		//
		// if (count > 0) {
		// // 기 추천여부
		// count = (Integer) this.feedbackRepository.getProdNotiWDGoodCount(prodNoti);
		//
		// if (count > 0) {
		// throw new StorePlatformException("SAC_OTH_1001");
		// }
		//
		// ProdNotiGood prodNotiGood = new ProdNotiGood();
		// prodNotiGood.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		// prodNotiGood.setNotiSeq(createRecommendFeedbackReq.getNotiSeq());
		// prodNotiGood.setMbrNo(createRecommendFeedbackReq.getUserKey());
		// prodNotiGood.setRegId(createRecommendFeedbackReq.getUserId());
		// int affectedRow = (Integer) this.feedbackRepository.insertProdNotiGood(prodNotiGood);
		//
		// if (affectedRow <= 0) {
		// throw new StorePlatformException("SAC_OTH_1001");
		// }
		//
		// prodNotiGood.setAction("create");
		// affectedRow = (Integer) this.feedbackRepository.updateProdNotiWDGood(prodNotiGood);
		//
		// if (affectedRow <= 0) {
		// throw new StorePlatformException("SAC_OTH_1002");
		// }
		// } else {
		//
		// count = (Integer) this.feedbackRepository.getProdNotiGoodCount(prodNoti);
		//
		// if (count > 0) {
		// throw new StorePlatformException("SAC_OTH_1001");
		// }
		//
		// ProdNotiGood prodNotiGood = new ProdNotiGood();
		// prodNotiGood.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		// prodNotiGood.setNotiSeq(createRecommendFeedbackReq.getNotiSeq());
		// prodNotiGood.setMbrNo(createRecommendFeedbackReq.getUserKey());
		// prodNotiGood.setRegId(createRecommendFeedbackReq.getUserId());
		// int affectedRow = (Integer) this.feedbackRepository.insertProdNotiGood(prodNotiGood);
		//
		// if (affectedRow <= 0) {
		// throw new StorePlatformException("SAC_OTH_1001");
		// }
		//
		// prodNotiGood.setAction("create");
		// affectedRow = (Integer) this.feedbackRepository.updateProdNotiGood(prodNotiGood);
		//
		// if (affectedRow <= 0) {
		// throw new StorePlatformException("SAC_OTH_1002");
		// }
		// }

		CreateRecommendFeedbackSacRes createRecommendFeedbackSacRes = new CreateRecommendFeedbackSacRes();
		createRecommendFeedbackSacRes.setNotiSeq(createRecommendFeedbackReq.getNotiSeq());
		return createRecommendFeedbackSacRes;
	}

	@Override
	public RemoveRecommendFeedbackSacRes removeRecommend(RemoveRecommendFeedbackSacReq removeRecommendFeedbackSacReq,
			SacRequestHeader sacRequestHeader) {

		// ProdNoti prodNoti = new ProdNoti();
		// prodNoti.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		// prodNoti.setNotiSeq(removeRecommendFeedbackSacReq.getNotiSeq());
		// prodNoti.setProdId(removeRecommendFeedbackSacReq.getProdId());
		// prodNoti.setMbrNo(removeRecommendFeedbackSacReq.getUserKey());
		// // 탈퇴회원 사용후기 여부 조회
		// int count = (Integer) this.feedbackRepository.getProdNotiWDCount(prodNoti);
		//
		// if (count > 0) {
		// ProdNotiGood prodNotiGood = new ProdNotiGood();
		// prodNotiGood.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		// prodNotiGood.setNotiSeq(removeRecommendFeedbackSacReq.getNotiSeq());
		// int affectedRow = (Integer) this.feedbackRepository.deleteProdNotiGood(prodNotiGood);
		//
		// if (affectedRow <= 0) {
		// throw new StorePlatformException("SAC_OTH_1003");
		// }
		//
		// prodNotiGood.setAction("remove");
		// affectedRow = (Integer) this.feedbackRepository.updateProdNotiWDGood(prodNotiGood);
		//
		// if (affectedRow <= 0) {
		// throw new StorePlatformException("SAC_OTH_1002");
		// }
		//
		// } else {
		// ProdNotiGood prodNotiGood = new ProdNotiGood();
		// prodNotiGood.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		// prodNotiGood.setNotiSeq(removeRecommendFeedbackSacReq.getNotiSeq());
		// int affectedRow = (Integer) this.feedbackRepository.deleteProdNotiGood(prodNotiGood);
		//
		// if (affectedRow <= 0) {
		// throw new StorePlatformException("SAC_OTH_1003");
		// }
		//
		// prodNotiGood.setAction("remove");
		// affectedRow = (Integer) this.feedbackRepository.updateProdNotiGood(prodNotiGood);
		//
		// if (affectedRow <= 0) {
		// throw new StorePlatformException("SAC_OTH_1002");
		// }
		// }

		RemoveRecommendFeedbackSacRes removeRecommendFeedbackSacRes = new RemoveRecommendFeedbackSacRes();
		removeRecommendFeedbackSacRes.setNotiSeq(removeRecommendFeedbackSacReq.getNotiSeq());
		return removeRecommendFeedbackSacRes;
	}

	@Override
	public ListFeedbackSacRes list(ListFeedbackSacReq listFeedbackSacReq, SacRequestHeader sacRequestHeader) {

		// totcal count
		// offset
		// count

		// 페이징 계산식?

		ListFeedbackSacRes listFeedbackRes = new ListFeedbackSacRes();
		listFeedbackRes.setAvgEvluScorePct("80.00");
		listFeedbackRes.setNotiTot("10");
		listFeedbackRes.setAvgEvluScore("2");
		listFeedbackRes.setDwldCnt("11103");
		listFeedbackRes.setParticpersCnt("105");
		listFeedbackRes.setNotiList(this.getFeedbackList());
		return listFeedbackRes;
	}

	@Override
	public ListMyFeedbackSacRes listMyFeedback(ListMyFeedbackSacReq listMyFeedbackSacReq,
			SacRequestHeader sacRequestHeader) {
		ListMyFeedbackSacRes listMyFeedbackRes = new ListMyFeedbackSacRes();
		listMyFeedbackRes.setNotiTot("10");
		listMyFeedbackRes.setNotiList(this.getFeedbackList());
		return listMyFeedbackRes;
	}

	@Override
	public CreateSellerFeedbackSacRes createSellerFeedback(CreateSellerFeedbackSacReq createSellerFeedbackSacReq,
			SacRequestHeader sacRequestHeader) {
		ProdNoti prodNoti = new ProdNoti();

		prodNoti.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		prodNoti.setNotiSeq(createSellerFeedbackSacReq.getNotiSeq());
		prodNoti.setSellerRespTitle(createSellerFeedbackSacReq.getSellerRespTitle());
		prodNoti.setSellerRespOpin(createSellerFeedbackSacReq.getSellerRespOpin());

		// 회원정보 조회 => LocalSCI call 할 것
		boolean sellerExist = true;

		if (sellerExist) {
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
		} else {
			// 회원에서 받은 exception throw
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

		// 회원정보 조회 => LocalSCI call 할 것
		boolean sellerExist = true;

		if (sellerExist) {
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
		} else {
			// 회원에서 받은 exception throw
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

		// 회원정보 조회 => LocalSCI call 할 것
		boolean sellerExist = true;

		if (sellerExist) {
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
		} else {
			// 회원에서 받은 exception throw
		}

		RemoveSellerFeedbackSacRes removeSellerFeedbackRes = new RemoveSellerFeedbackSacRes();
		removeSellerFeedbackRes.setNotiSeq(prodNoti.getNotiSeq());
		return removeSellerFeedbackRes;
	}

	@Override
	public GetScoreSacRes getScore(GetScoreSacReq getScoreSacReq, SacRequestHeader sacRequestHeader) {
		GetScoreSacRes getScoreRes = new GetScoreSacRes();
		getScoreRes.setProdId(getScoreSacReq.getProdId());
		getScoreRes.setTotEvluScore("20");
		getScoreRes.setAvgEvluScore("4.0");
		getScoreRes.setAvgEvluScorePct("80.00");
		getScoreRes.setParticpersCnt("5");
		return getScoreRes;
	}

	@Override
	public ListScoreParticpersSacRes listScoreParticpers(ListScoreParticpersSacReq listScoreParticpersSacReq,
			SacRequestHeader sacRequestHeader) {
		ListScoreParticpersSacRes listScoreRes = new ListScoreParticpersSacRes();
		List<AvgScore> avgScoreList = new ArrayList<AvgScore>();
		AvgScore avgScore1 = new AvgScore();
		avgScore1.setAvgScore("5");
		avgScore1.setAvgScorePct("0");
		avgScore1.setParticpersCnt("0");

		AvgScore avgScore2 = new AvgScore();
		avgScore2.setAvgScore("4");
		avgScore2.setAvgScorePct("100");
		avgScore2.setParticpersCnt("1");

		AvgScore avgScore3 = new AvgScore();
		avgScore3.setAvgScore("3");
		avgScore3.setAvgScorePct("0");
		avgScore3.setParticpersCnt("0");

		AvgScore avgScore4 = new AvgScore();
		avgScore4.setAvgScore("2");
		avgScore4.setAvgScorePct("100");
		avgScore4.setParticpersCnt("1");

		AvgScore avgScore5 = new AvgScore();
		avgScore5.setAvgScore("1");
		avgScore5.setAvgScorePct("0");
		avgScore5.setParticpersCnt("0");

		avgScoreList.add(avgScore1);
		avgScoreList.add(avgScore2);
		avgScoreList.add(avgScore3);
		avgScoreList.add(avgScore4);
		avgScoreList.add(avgScore5);

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

	@Override
	public ChangeFeedbackUserIdSacRes changeFeedbackUserId(ChangeFeedbackUserIdSacReq changeFeedbackUserIdSacReq) {
		ChangeFeedbackUserIdSacRes changeFeedbackUserIdRes = new ChangeFeedbackUserIdSacRes();
		changeFeedbackUserIdRes.setResultStatus("success");
		return changeFeedbackUserIdRes;
	}

	@Override
	public ChangeFeedbackUserKeySacRes changeFeedbackUserKey(ChangeFeedbackUserKeySacReq changeFeedbackUserKeySacReq) {
		ChangeFeedbackUserKeySacRes changeFeedbackUserKeyRes = new ChangeFeedbackUserKeySacRes();
		changeFeedbackUserKeyRes.setResultStatus("success");
		return changeFeedbackUserKeyRes;
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
}
