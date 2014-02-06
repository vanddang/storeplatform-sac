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

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

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

/**
 * 
 * FeedbackService Sample
 * 
 * Updated on : 2014. 1. 28. Updated by : 김현일, 인크로스.
 */
@Profile(value = { "dev", "local" })
@Service
public class FeedbackServiceSampleImpl implements FeedbackService {

	@Override
	public CreateFeedbackSacRes create(CreateFeedbackSacReq createFeedbackReq, SacRequestHeader sacRequestHeader) {
		CreateFeedbackSacRes createFeedbackRes = new CreateFeedbackSacRes();
		createFeedbackRes.setNotiSeq(14275);
		return createFeedbackRes;
	}

	@Override
	public ModifyFeedbackSacRes modify(ModifyFeedbackSacReq modifyFeedbackReq, SacRequestHeader sacRequestHeader) {
		ModifyFeedbackSacRes modifyFeedbackRes = new ModifyFeedbackSacRes();
		modifyFeedbackRes.setNotiSeq(14275);
		return modifyFeedbackRes;
	}

	@Override
	public RemoveFeedbackSacRes remove(RemoveFeedbackSacReq removeFeedbackReq, SacRequestHeader sacRequestHeader) {
		RemoveFeedbackSacRes removeFeedbackRes = new RemoveFeedbackSacRes();
		removeFeedbackRes.setNotiSeq(14275);
		return removeFeedbackRes;
	}

	@Override
	public CreateRecommendFeedbackSacRes createRecommend(CreateRecommendFeedbackSacReq createRecommendFeedbackReq,
			SacRequestHeader sacRequestHeader) {
		CreateRecommendFeedbackSacRes createRecommendFeedbackRes = new CreateRecommendFeedbackSacRes();
		createRecommendFeedbackRes.setNotiList(this.getFeedbackList());
		return createRecommendFeedbackRes;
	}

	@Override
	public RemoveRecommendFeedbackSacRes removeRecommend(RemoveRecommendFeedbackSacReq feedbackRecommendReq,
			SacRequestHeader sacRequestHeader) {
		RemoveRecommendFeedbackSacRes removeRecommendFeedbackRes = new RemoveRecommendFeedbackSacRes();
		removeRecommendFeedbackRes.setNotiList(this.getFeedbackList());
		return removeRecommendFeedbackRes;
	}

	@Override
	public ListFeedbackSacRes list(ListFeedbackSacReq listFeedbackReq, SacRequestHeader sacRequestHeader) {
		ListFeedbackSacRes listFeedbackRes = new ListFeedbackSacRes();
		listFeedbackRes.setAvgEvluScorePct("80.00");
		listFeedbackRes.setNotiTot(10);
		listFeedbackRes.setAvgEvluScore(2);
		listFeedbackRes.setDwldCnt(11103);
		listFeedbackRes.setParticpersCnt(105);
		listFeedbackRes.setNotiList(this.getFeedbackList());
		return listFeedbackRes;
	}

	@Override
	public ListMyFeedbackSacRes listMyFeedback(ListMyFeedbackSacReq listMyFeedbackReq, SacRequestHeader sacRequestHeader) {
		ListMyFeedbackSacRes listMyFeedbackRes = new ListMyFeedbackSacRes();
		listMyFeedbackRes.setNotiTot(10);
		listMyFeedbackRes.setNotiList(this.getFeedbackList());
		return listMyFeedbackRes;
	}

	@Override
	public CreateSellerFeedbackSacRes createSellerFeedback(CreateSellerFeedbackSacReq createSellerFeedbackReq,
			SacRequestHeader sacRequestHeader) {
		CreateSellerFeedbackSacRes createSellerFeedbackRes = new CreateSellerFeedbackSacRes();
		createSellerFeedbackRes.setNotiSeq(14275);
		return createSellerFeedbackRes;
	}

	@Override
	public ModifySellerFeedbackSacRes modifySellerFeedback(ModifySellerFeedbackSacReq modifySellerFeedbackReq,
			SacRequestHeader sacRequestHeader) {
		ModifySellerFeedbackSacRes modifySellerFeedbackRes = new ModifySellerFeedbackSacRes();
		modifySellerFeedbackRes.setNotiSeq(14275);
		return modifySellerFeedbackRes;
	}

	@Override
	public RemoveSellerFeedbackSacRes removeSellerFeedback(RemoveSellerFeedbackSacReq removeSellerFeedbackReq,
			SacRequestHeader sacRequestHeader) {
		RemoveSellerFeedbackSacRes removeSellerFeedbackRes = new RemoveSellerFeedbackSacRes();
		removeSellerFeedbackRes.setNotiSeq(14275);
		return removeSellerFeedbackRes;
	}

	@Override
	public GetScoreSacRes getScore(GetScoreSacReq getScoreReq, SacRequestHeader sacRequestHeader) {
		GetScoreSacRes getScoreRes = new GetScoreSacRes();
		getScoreRes.setProdId(getScoreReq.getProdId());
		getScoreRes.setTotEvluScore(20);
		getScoreRes.setAvgEvluScore("4.0");
		getScoreRes.setAvgEvluScorePct("80.00");
		getScoreRes.setParticpersCnt(5);
		return getScoreRes;
	}

	@Override
	public ListScoreParticpersSacRes listScoreParticpers(ListScoreParticpersSacReq listScoreParticpersReq,
			SacRequestHeader sacRequestHeader) {
		ListScoreParticpersSacRes listScoreRes = new ListScoreParticpersSacRes();
		List<AvgScore> avgScoreList = new ArrayList<AvgScore>();
		AvgScore avgScore1 = new AvgScore();
		avgScore1.setAvgScore(5);
		avgScore1.setAvgScorePct(0);
		avgScore1.setParticpersCnt(0);

		AvgScore avgScore2 = new AvgScore();
		avgScore2.setAvgScore(4);
		avgScore2.setAvgScorePct(100);
		avgScore2.setParticpersCnt(1);

		AvgScore avgScore3 = new AvgScore();
		avgScore3.setAvgScore(3);
		avgScore3.setAvgScorePct(0);
		avgScore3.setParticpersCnt(0);

		AvgScore avgScore4 = new AvgScore();
		avgScore4.setAvgScore(2);
		avgScore4.setAvgScorePct(100);
		avgScore4.setParticpersCnt(1);

		AvgScore avgScore5 = new AvgScore();
		avgScore5.setAvgScore(1);
		avgScore5.setAvgScorePct(0);
		avgScore5.setParticpersCnt(0);

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

		for (int i = 0; i < 10; i++) {
			Feedback feedback = new Feedback();
			feedback.setUserKey("IW11000162200905070014211" + i);
			feedback.setSellerKey("IF111111111111111111111" + i);
			feedback.setNotiTitle("좋아요!!(" + i + ")");
			feedback.setNotiDscr("프로야구 게임 재밌네요~ ㅋㅋ(" + i + ")");
			feedback.setNotiScore(0);
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
			feedback.setAvgScore(5);
			feedback.setNickNm("");
			feedback.setFbPostYn("N");
			feedback.setProdId("0000000001");
			feedback.setCompNm("");
			notiList.add(feedback);
		}
		return notiList;
	}

	@Override
	public ChangeFeedbackUserIdSacRes changeFeedbackUserId(ChangeFeedbackUserIdSacReq changeFeedbackUserIdReq) {
		ChangeFeedbackUserIdSacRes changeFeedbackUserIdRes = new ChangeFeedbackUserIdSacRes();
		changeFeedbackUserIdRes.setResultStatus("success");
		return changeFeedbackUserIdRes;
	}

	@Override
	public ChangeFeedbackUserKeySacRes changeFeedbackUserKey(ChangeFeedbackUserKeySacReq changeFeedbackUserKeyReq) {
		ChangeFeedbackUserKeySacRes changeFeedbackUserKeyRes = new ChangeFeedbackUserKeySacRes();
		changeFeedbackUserKeyRes.setResultStatus("success");
		return changeFeedbackUserKeyRes;
	}

}
