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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.client.other.vo.feedback.FeedbackReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ParticipationRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ScoreRes;

@Profile("local")
@Service
public class FeedbackServiceSampleImpl implements FeedbackService {

	private final String notiSeq = "1234";

	@Override
	public Map<String, String> create(FeedbackReq feedbackReq) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("notiSeq", this.notiSeq);
		return result;
	}

	@Override
	public Map<String, String> modify(FeedbackReq feedbackReq) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("notiSeq", this.notiSeq);
		return result;
	}

	@Override
	public Map<String, String> remove(FeedbackReq feedbackReq) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("notiSeq", this.notiSeq);
		return result;
	}

	@Override
	public Map<String, String> createRecommend(FeedbackReq feedbackReq) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("notiSeq", this.notiSeq);
		return result;
	}

	@Override
	public Map<String, String> removeRecommend(FeedbackReq feedbackReq) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("notiSeq", this.notiSeq);
		return result;
	}

	@Override
	public Map<String, Object> list(FeedbackReq feedbackReq) {
		return null;
	}

	@Override
	public Map<String, Object> listMyFeedback(FeedbackReq feedbackReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> createSellerFeedback(FeedbackReq feedbackReq) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("notiSeq", this.notiSeq);
		return result;
	}

	@Override
	public Map<String, String> modifySellerFeedback(FeedbackReq feedbackReq) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("notiSeq", this.notiSeq);
		return result;
	}

	@Override
	public Map<String, String> removeSellerFeedback(FeedbackReq feedbackReq) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("notiSeq", this.notiSeq);
		return result;
	}

	@Override
	public ScoreRes getScore(FeedbackReq feedbackReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ParticipationRes> listParticipation(FeedbackReq feedbackReq) {
		// TODO Auto-generated method stub
		return null;
	}

}
