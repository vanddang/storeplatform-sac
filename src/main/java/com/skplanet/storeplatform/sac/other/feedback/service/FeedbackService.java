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

import com.skplanet.storeplatform.sac.client.other.vo.feedback.FeedbackReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.GetScoreRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListParticipationRes;

/**
 * 
 * Calss 설명
 * 
 * Updated on : 2014. 1. 7. Updated by : 인크로스, 김현일
 */
public interface FeedbackService {

	public Map<String, String> create(FeedbackReq feedbackReq);

	public Map<String, String> modify(FeedbackReq feedbackReq);

	public Map<String, String> remove(FeedbackReq feedbackReq);

	public Map<String, String> createRecommend(FeedbackReq feedbackReq);

	public Map<String, String> removeRecommend(FeedbackReq feedbackReq);

	public Map<String, Object> list(FeedbackReq feedbackReq);

	public Map<String, Object> listMyFeedback(FeedbackReq feedbackReq);

	public Map<String, String> createSellerFeedback(FeedbackReq feedbackReq);

	public Map<String, String> modifySellerFeedback(FeedbackReq feedbackReq);

	public Map<String, String> removeSellerFeedback(FeedbackReq feedbackReq);

	public GetScoreRes getScore(FeedbackReq feedbackReq);

	public List<ListParticipationRes> listParticipation(FeedbackReq feedbackReq);

	public int changeUserId();

	public int changeUserKey();
}
