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
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.client.other.vo.feedback.AvgScore;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ChangeFeedbackUserIdReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ChangeFeedbackUserIdRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ChangeFeedbackUserKeyReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ChangeFeedbackUserKeyRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateFeedbackReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateFeedbackRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateRecommendFeedbackReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateRecommendFeedbackRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateSellerFeedbackReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateSellerFeedbackRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.Feedback;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.GetScoreReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.GetScoreRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListFeedbackReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListFeedbackRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListMyFeedbackReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListMyFeedbackRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListScoreParticpersReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListScoreParticpersRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ModifyFeedbackReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ModifyFeedbackRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ModifySellerFeedbackReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ModifySellerFeedbackRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.RemoveFeedbackReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.RemoveFeedbackRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.RemoveRecommendFeedbackReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.RemoveRecommendFeedbackRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.RemoveSellerFeedbackReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.RemoveSellerFeedbackRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 
 * FeedbackService Sample
 * 
 * Updated on : 2014. 1. 28. Updated by : 김현일, 인크로스.
 */
@Profile(value = { "dev", "local" })
@Service
@Transactional
public class FeedbackServiceSampleImpl implements FeedbackService {

	@Override
	public CreateFeedbackRes create(CreateFeedbackReq createFeedbackReq, SacRequestHeader sacRequestHeader) {
		CreateFeedbackRes createFeedbackRes = new CreateFeedbackRes();
		createFeedbackRes.setNotiSeq(14275);
		return createFeedbackRes;
	}

	@Override
	public ModifyFeedbackRes modify(ModifyFeedbackReq modifyFeedbackReq, SacRequestHeader sacRequestHeader) {
		ModifyFeedbackRes modifyFeedbackRes = new ModifyFeedbackRes();
		modifyFeedbackRes.setNotiSeq(14275);
		return modifyFeedbackRes;
	}

	@Override
	public RemoveFeedbackRes remove(RemoveFeedbackReq removeFeedbackReq, SacRequestHeader sacRequestHeader) {
		RemoveFeedbackRes removeFeedbackRes = new RemoveFeedbackRes();
		removeFeedbackRes.setNotiSeq(14275);
		return removeFeedbackRes;
	}

	@Override
	public CreateRecommendFeedbackRes createRecommend(CreateRecommendFeedbackReq createRecommendFeedbackReq,
			SacRequestHeader sacRequestHeader) {
		CreateRecommendFeedbackRes createRecommendFeedbackRes = new CreateRecommendFeedbackRes();
		createRecommendFeedbackRes.setNotiList(this.getFeedbackList());
		return createRecommendFeedbackRes;
	}

	@Override
	public RemoveRecommendFeedbackRes removeRecommend(RemoveRecommendFeedbackReq feedbackRecommendReq,
			SacRequestHeader sacRequestHeader) {
		RemoveRecommendFeedbackRes removeRecommendFeedbackRes = new RemoveRecommendFeedbackRes();
		removeRecommendFeedbackRes.setNotiList(this.getFeedbackList());
		return removeRecommendFeedbackRes;
	}

	@Override
	public ListFeedbackRes list(ListFeedbackReq listFeedbackReq, SacRequestHeader sacRequestHeader) {
		ListFeedbackRes listFeedbackRes = new ListFeedbackRes();
		listFeedbackRes.setAvgScorePct("80.00");
		listFeedbackRes.setNotiTot(10);
		listFeedbackRes.setAvgScore(2);
		listFeedbackRes.setDwldCnt(11103);
		listFeedbackRes.setParticpersCnt(105);
		listFeedbackRes.setNotiList(this.getFeedbackList());
		return listFeedbackRes;
	}

	@Override
	public ListMyFeedbackRes listMyFeedback(ListMyFeedbackReq listMyFeedbackReq, SacRequestHeader sacRequestHeader) {
		ListMyFeedbackRes listMyFeedbackRes = new ListMyFeedbackRes();
		listMyFeedbackRes.setNotiTot(10);
		listMyFeedbackRes.setNotiList(this.getFeedbackList());
		return listMyFeedbackRes;
	}

	@Override
	public CreateSellerFeedbackRes createSellerFeedback(CreateSellerFeedbackReq createSellerFeedbackReq,
			SacRequestHeader sacRequestHeader) {
		CreateSellerFeedbackRes createSellerFeedbackRes = new CreateSellerFeedbackRes();
		createSellerFeedbackRes.setNotiSeq(14275);
		return createSellerFeedbackRes;
	}

	@Override
	public ModifySellerFeedbackRes modifySellerFeedback(ModifySellerFeedbackReq modifySellerFeedbackReq,
			SacRequestHeader sacRequestHeader) {
		ModifySellerFeedbackRes modifySellerFeedbackRes = new ModifySellerFeedbackRes();
		modifySellerFeedbackRes.setNotiSeq(14275);
		return modifySellerFeedbackRes;
	}

	@Override
	public RemoveSellerFeedbackRes removeSellerFeedback(RemoveSellerFeedbackReq removeSellerFeedbackReq,
			SacRequestHeader sacRequestHeader) {
		RemoveSellerFeedbackRes removeSellerFeedbackRes = new RemoveSellerFeedbackRes();
		removeSellerFeedbackRes.setNotiSeq(14275);
		return removeSellerFeedbackRes;
	}

	@Override
	public GetScoreRes getScore(GetScoreReq getScoreReq, SacRequestHeader sacRequestHeader) {
		GetScoreRes getScoreRes = new GetScoreRes();
		getScoreRes.setProdId(getScoreReq.getProdId());
		getScoreRes.setTotEvluScore(20);
		getScoreRes.setAvgEvluScore("4.0");
		getScoreRes.setAvgEvluScorePct("80.00");
		getScoreRes.setParticpersCnt(5);
		return getScoreRes;
	}

	@Override
	public ListScoreParticpersRes listScoreParticpers(ListScoreParticpersReq listScoreParticpersReq,
			SacRequestHeader sacRequestHeader) {
		ListScoreParticpersRes listScoreRes = new ListScoreParticpersRes();
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
			feedback.setNotiDesc("프로야구 게임 재밌네요~ ㅋㅋ(" + i + ")");
			feedback.setNotiScore(0);
			feedback.setRegId("011****5052");
			feedback.setRegDt("20090606035251");
			feedback.setSellerRespTitle("");
			feedback.setSellerRespOpin("");
			feedback.setSellerRespRegDt("");
			feedback.setCid("0000000001");
			feedback.setSelfYN("N");
			feedback.setSaleYN("N");
			feedback.setWhose("mine");
			feedback.setSelfRecomYN("N");
			feedback.setScore(5);
			feedback.setNickNm("");
			feedback.setFbSendYN("N");
			feedback.setProdId("0000000001");
			feedback.setCompNm("");
			notiList.add(feedback);
		}
		return notiList;
	}

	@Override
	public ChangeFeedbackUserIdRes changeFeedbackUserId(ChangeFeedbackUserIdReq changeFeedbackUserIdReq) {
		ChangeFeedbackUserIdRes changeFeedbackUserIdRes = new ChangeFeedbackUserIdRes();
		changeFeedbackUserIdRes.setResultStatus("success");
		return changeFeedbackUserIdRes;
	}

	@Override
	public ChangeFeedbackUserKeyRes changeFeedbackUserKey(ChangeFeedbackUserKeyReq changeFeedbackUserKeyReq) {
		ChangeFeedbackUserKeyRes changeFeedbackUserKeyRes = new ChangeFeedbackUserKeyRes();
		changeFeedbackUserKeyRes.setResultStatus("success");
		return changeFeedbackUserKeyRes;
	}

}
