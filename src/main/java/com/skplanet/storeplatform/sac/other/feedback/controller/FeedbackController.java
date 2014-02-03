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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateFeedbackReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateFeedbackRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateRecommendFeedbackReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateRecommendFeedbackRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateSellerFeedbackReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateSellerFeedbackRes;
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
import com.skplanet.storeplatform.sac.other.feedback.service.FeedbackService;

/**
 * 
 * Feedback Controller
 * 
 * Updated on : 2014. 1. 7. Updated by : 김현일, 인크로스
 */
@Controller
@RequestMapping(value = "/other/feedback")
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;

	/**
	 * 
	 * <pre>
	 * 사용후기/평점 등록.
	 * </pre>
	 * 
	 * @param createFeedbackReq
	 *            createFeedbackReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return CreateFeedbackRes
	 */
	@RequestMapping(value = "/create/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateFeedbackRes create(@RequestBody CreateFeedbackReq createFeedbackReq, SacRequestHeader sacRequestHeader) {
		return this.feedbackService.create(createFeedbackReq, sacRequestHeader);
	}

	/**
	 * 
	 * <pre>
	 * 사용후기/평점 수정.
	 * </pre>
	 * 
	 * @param modifyFeedbackReq
	 *            modifyFeedbackReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return ModifyFeedbackRes
	 */
	@RequestMapping(value = "/modify/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyFeedbackRes modify(@RequestBody ModifyFeedbackReq modifyFeedbackReq, SacRequestHeader sacRequestHeader) {
		return this.feedbackService.modify(modifyFeedbackReq, sacRequestHeader);
	}

	/**
	 * 
	 * <pre>
	 * 사용후기/평점 삭제.
	 * </pre>
	 * 
	 * @param removeFeedbackReq
	 *            removeFeedbackReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return RemoveFeedbackRes
	 */
	@RequestMapping(value = "/remove/v1", method = RequestMethod.POST)
	@ResponseBody
	public RemoveFeedbackRes remove(@RequestBody RemoveFeedbackReq removeFeedbackReq, SacRequestHeader sacRequestHeader) {
		return this.feedbackService.remove(removeFeedbackReq, sacRequestHeader);
	}

	/**
	 * 
	 * <pre>
	 * 사용후기 추천.
	 * </pre>
	 * 
	 * @param createRecommendFeedbackReq
	 *            createRecommendFeedbackReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return CreateRecommendFeedbackRes
	 */
	@RequestMapping(value = "/createRecommend/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateRecommendFeedbackRes createRecommend(
			@RequestBody CreateRecommendFeedbackReq createRecommendFeedbackReq, SacRequestHeader sacRequestHeader) {
		return this.feedbackService.createRecommend(createRecommendFeedbackReq, sacRequestHeader);
	}

	/**
	 * 
	 * <pre>
	 * 사용후기 추천 취소.
	 * </pre>
	 * 
	 * @param removeRecommendFeedbackReq
	 *            removeRecommendFeedbackReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return RemoveRecommendFeedbackRes
	 */
	@RequestMapping(value = "/removeRecommend/v1", method = RequestMethod.POST)
	@ResponseBody
	public RemoveRecommendFeedbackRes removeRecommend(
			@RequestBody RemoveRecommendFeedbackReq removeRecommendFeedbackReq, SacRequestHeader sacRequestHeader) {
		return this.feedbackService.removeRecommend(removeRecommendFeedbackReq, sacRequestHeader);
	}

	/**
	 * 
	 * <pre>
	 * 사용후기 전체 조회.
	 * </pre>
	 * 
	 * @param listFeedbackReq
	 *            listFeedbackReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return ListFeedbackRes
	 */
	@RequestMapping(value = "/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListFeedbackRes list(ListFeedbackReq listFeedbackReq, SacRequestHeader sacRequestHeader) {
		return this.feedbackService.list(listFeedbackReq, sacRequestHeader);
	}

	/**
	 * 
	 * <pre>
	 * 내 사용후기 조회.
	 * </pre>
	 * 
	 * @param listMyFeedbackReq
	 *            listMyFeedbackReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return ListMyFeedbackRes
	 */
	@RequestMapping(value = "/listMyFeedback/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListMyFeedbackRes listMyFeedback(ListMyFeedbackReq listMyFeedbackReq, SacRequestHeader sacRequestHeader) {
		return this.feedbackService.listMyFeedback(listMyFeedbackReq, sacRequestHeader);
	}

	/**
	 * 
	 * <pre>
	 * 판매자 댓글 등록.
	 * </pre>
	 * 
	 * @param createSellerFeedbackReq
	 *            createSellerFeedbackReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return CreateSellerFeedbackRes
	 */
	@RequestMapping(value = "/createSellerFeedback/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateSellerFeedbackRes createSellerFeedback(@RequestBody CreateSellerFeedbackReq createSellerFeedbackReq,
			SacRequestHeader sacRequestHeader) {
		return this.feedbackService.createSellerFeedback(createSellerFeedbackReq, sacRequestHeader);
	}

	/**
	 * 
	 * <pre>
	 * 판매자 댓글 수정.
	 * </pre>
	 * 
	 * @param modifySellerFeedbackReq
	 *            modifySellerFeedbackReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return ModifySellerFeedbackRes
	 */
	@RequestMapping(value = "/modifySellerFeedback/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifySellerFeedbackRes modifySellerFeedback(@RequestBody ModifySellerFeedbackReq modifySellerFeedbackReq,
			SacRequestHeader sacRequestHeader) {
		return this.feedbackService.modifySellerFeedback(modifySellerFeedbackReq, sacRequestHeader);
	}

	/**
	 * 
	 * <pre>
	 * 판매자 댓글 삭제.
	 * </pre>
	 * 
	 * @param removeSellerFeedbackReq
	 *            removeSellerFeedbackReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return RemoveSellerFeedbackRes
	 */
	@RequestMapping(value = "/removeSellerFeedback/v1", method = RequestMethod.POST)
	@ResponseBody
	public RemoveSellerFeedbackRes removeSellerFeedback(@RequestBody RemoveSellerFeedbackReq removeSellerFeedbackReq,
			SacRequestHeader sacRequestHeader) {
		return this.feedbackService.removeSellerFeedback(removeSellerFeedbackReq, sacRequestHeader);
	}

	/**
	 * 
	 * <pre>
	 * 평점 조회.
	 * </pre>
	 * 
	 * @param getScoreReq
	 *            getScoreReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return GetScoreRes
	 */
	@RequestMapping(value = "/getScore/v1", method = RequestMethod.GET)
	@ResponseBody
	public GetScoreRes getScore(GetScoreReq getScoreReq, SacRequestHeader sacRequestHeader) {
		return this.feedbackService.getScore(getScoreReq, sacRequestHeader);
	}

	/**
	 * 
	 * <pre>
	 * 평점별 참여수 조회.
	 * </pre>
	 * 
	 * @param listScoreParticpersReq
	 *            listScoreParticpersReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return ListScoreParticpersRes
	 */
	@RequestMapping(value = "/listScoreParticpers/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListScoreParticpersRes listScoreParticpers(ListScoreParticpersReq listScoreParticpersReq,
			SacRequestHeader sacRequestHeader) {
		return this.feedbackService.listScoreParticpers(listScoreParticpersReq, sacRequestHeader);
	}
}
