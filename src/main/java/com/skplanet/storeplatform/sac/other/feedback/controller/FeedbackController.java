/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.feedback.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.other.vo.feedback.FeedbackReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.GetScoreRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListFeedbackRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListMyFeedbackRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListParticipationRes;
import com.skplanet.storeplatform.sac.other.feedback.service.FeedbackService;

/**
 * 
 * 사용후기 Controller
 * 
 * Updated on : 2014. 1. 7. Updated by : 김현일, 인크로스
 */
@Controller
@RequestMapping("/other/feedback")
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;

	@RequestMapping(value = "/create/v1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> create(FeedbackReq feedbackReq) {
		return this.feedbackService.create(feedbackReq);
	}

	@RequestMapping(value = "/modify/v1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> modify(FeedbackReq feedbackReq) {
		return this.feedbackService.modify(feedbackReq);
	}

	@RequestMapping(value = "/remove/v1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> remove(FeedbackReq feedbackReq) {
		return this.feedbackService.remove(feedbackReq);
	}

	@RequestMapping(value = "/createRecommend/v1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createRecommend(FeedbackReq feedbackReq) {
		return null;
	}

	@RequestMapping(value = "/removeRecommend/v1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> removeRecommend(FeedbackReq feedbackReq) {
		return null;
	}

	@RequestMapping(value = "/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public List<ListFeedbackRes> list(FeedbackReq feedbackReq) {
		return null;
	}

	@RequestMapping(value = "/listMyFeedback/v1", method = RequestMethod.GET)
	@ResponseBody
	public List<ListMyFeedbackRes> listMyFeedback(FeedbackReq feedbackReq) {
		return null;

	}

	@RequestMapping(value = "/createSellerFeedback/v1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> createSellerFeedback(FeedbackReq feedbackReq) {
		return null;
	}

	@RequestMapping(value = "/modifySellerFeedback/v1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> modifySellerFeedback(FeedbackReq feedbackReq) {
		return null;
	}

	@RequestMapping(value = "/removeSellerFeedback/v1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> removeSellerFeedback(FeedbackReq feedbackReq) {
		return null;
	}

	@RequestMapping(value = "/getScore/v1", method = RequestMethod.GET)
	@ResponseBody
	public GetScoreRes getScore(FeedbackReq feedbackReq) {
		return null;
	}

	@RequestMapping(value = "/listParticipation/v1", method = RequestMethod.GET)
	@ResponseBody
	public List<ListParticipationRes> listParticipation(FeedbackReq feedbackReq) {
		return null;
	}
}
