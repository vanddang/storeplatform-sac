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

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.client.other.vo.feedback.FeedbackReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.GetScoreRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListParticipationRes;

@Profile(value = { "stag", "real" })
@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Override
	public Map<String, String> create(FeedbackReq feedbackReq) {
		return null;
	}

	@Override
	public Map<String, String> modify(FeedbackReq feedbackReq) {
		return null;
	}

	@Override
	public Map<String, String> remove(FeedbackReq feedbackReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> createRecommend(FeedbackReq feedbackReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> removeRecommend(FeedbackReq feedbackReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> list(FeedbackReq feedbackReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> listMyFeedback(FeedbackReq feedbackReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> createSellerFeedback(FeedbackReq feedbackReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> modifySellerFeedback(FeedbackReq feedbackReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> removeSellerFeedback(FeedbackReq feedbackReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetScoreRes getScore(FeedbackReq feedbackReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ListParticipationRes> listParticipation(FeedbackReq feedbackReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int changeUserId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int changeUserKey() {
		// TODO Auto-generated method stub
		return 0;
	}

}
