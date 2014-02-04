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

import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateFeedbackSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateRecommendFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateRecommendFeedbackSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateSellerFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.CreateSellerFeedbackSacRes;
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
	 * @param createFeedbackSacReq
	 *            createFeedbackSacReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return CreateFeedbackSacRes
	 */
	@RequestMapping(value = "/create/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateFeedbackSacRes create(@RequestBody CreateFeedbackSacReq createFeedbackSacReq,
			SacRequestHeader sacRequestHeader) {
		return this.feedbackService.create(createFeedbackSacReq, sacRequestHeader);
	}

	/**
	 * 
	 * <pre>
	 * 사용후기/평점 수정.
	 * </pre>
	 * 
	 * @param modifyFeedbackSacReq
	 *            modifyFeedbackSacReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return ModifyFeedbackSacRes
	 */
	@RequestMapping(value = "/modify/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifyFeedbackSacRes modify(@RequestBody ModifyFeedbackSacReq modifyFeedbackSacReq,
			SacRequestHeader sacRequestHeader) {
		return this.feedbackService.modify(modifyFeedbackSacReq, sacRequestHeader);
	}

	/**
	 * 
	 * <pre>
	 * 사용후기/평점 삭제.
	 * </pre>
	 * 
	 * @param removeFeedbackSacReq
	 *            removeFeedbackSacReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return RemoveFeedbackSacRes
	 */
	@RequestMapping(value = "/remove/v1", method = RequestMethod.POST)
	@ResponseBody
	public RemoveFeedbackSacRes remove(@RequestBody RemoveFeedbackSacReq removeFeedbackSacReq,
			SacRequestHeader sacRequestHeader) {
		return this.feedbackService.remove(removeFeedbackSacReq, sacRequestHeader);
	}

	/**
	 * 
	 * <pre>
	 * 사용후기 추천.
	 * </pre>
	 * 
	 * @param createRecommendFeedbackSacReq
	 *            createRecommendFeedbackSacReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return CreateRecommendFeedbackSacRes
	 */
	@RequestMapping(value = "/createRecommend/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateRecommendFeedbackSacRes createRecommend(
			@RequestBody CreateRecommendFeedbackSacReq createRecommendFeedbackSacReq, SacRequestHeader sacRequestHeader) {
		return this.feedbackService.createRecommend(createRecommendFeedbackSacReq, sacRequestHeader);
	}

	/**
	 * 
	 * <pre>
	 * 사용후기 추천 취소.
	 * </pre>
	 * 
	 * @param removeRecommendFeedbackSacReq
	 *            removeRecommendFeedbackSacReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return RemoveRecommendFeedbackSacRes
	 */
	@RequestMapping(value = "/removeRecommend/v1", method = RequestMethod.POST)
	@ResponseBody
	public RemoveRecommendFeedbackSacRes removeRecommend(
			@RequestBody RemoveRecommendFeedbackSacReq removeRecommendFeedbackSacReq, SacRequestHeader sacRequestHeader) {
		return this.feedbackService.removeRecommend(removeRecommendFeedbackSacReq, sacRequestHeader);
	}

	/**
	 * 
	 * <pre>
	 * 사용후기 전체 조회.
	 * </pre>
	 * 
	 * @param listFeedbackSacReq
	 *            listFeedbackSacReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return ListFeedbackSacRes
	 */
	@RequestMapping(value = "/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListFeedbackSacRes list(ListFeedbackSacReq listFeedbackSacReq, SacRequestHeader sacRequestHeader) {
		return this.feedbackService.list(listFeedbackSacReq, sacRequestHeader);
	}

	/**
	 * 
	 * <pre>
	 * 내 사용후기 조회.
	 * </pre>
	 * 
	 * @param listMyFeedbackSacReq
	 *            listMyFeedbacSackReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return ListMyFeedbackSacRes
	 */
	@RequestMapping(value = "/listMyFeedback/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListMyFeedbackSacRes listMyFeedback(ListMyFeedbackSacReq listMyFeedbackSacReq,
			SacRequestHeader sacRequestHeader) {
		return this.feedbackService.listMyFeedback(listMyFeedbackSacReq, sacRequestHeader);
	}

	/**
	 * 
	 * <pre>
	 * 판매자 댓글 등록.
	 * </pre>
	 * 
	 * @param createSellerFeedbackSacReq
	 *            createSellerFeedbackSacReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return CreateSellerFeedbackSacRes
	 */
	@RequestMapping(value = "/createSellerFeedback/v1", method = RequestMethod.POST)
	@ResponseBody
	public CreateSellerFeedbackSacRes createSellerFeedback(
			@RequestBody CreateSellerFeedbackSacReq createSellerFeedbackSacReq, SacRequestHeader sacRequestHeader) {
		return this.feedbackService.createSellerFeedback(createSellerFeedbackSacReq, sacRequestHeader);
	}

	/**
	 * 
	 * <pre>
	 * 판매자 댓글 수정.
	 * </pre>
	 * 
	 * @param modifySellerFeedbackSacReq
	 *            modifySellerFeedbackSacReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return ModifySellerFeedbackSacRes
	 */
	@RequestMapping(value = "/modifySellerFeedback/v1", method = RequestMethod.POST)
	@ResponseBody
	public ModifySellerFeedbackSacRes modifySellerFeedback(
			@RequestBody ModifySellerFeedbackSacReq modifySellerFeedbackSacReq, SacRequestHeader sacRequestHeader) {
		return this.feedbackService.modifySellerFeedback(modifySellerFeedbackSacReq, sacRequestHeader);
	}

	/**
	 * 
	 * <pre>
	 * 판매자 댓글 삭제.
	 * </pre>
	 * 
	 * @param removeSellerFeedbackSacReq
	 *            removeSellerFeedbackSacReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return RemoveSellerFeedbackSacRes
	 */
	@RequestMapping(value = "/removeSellerFeedback/v1", method = RequestMethod.POST)
	@ResponseBody
	public RemoveSellerFeedbackSacRes removeSellerFeedback(
			@RequestBody RemoveSellerFeedbackSacReq removeSellerFeedbackSacReq, SacRequestHeader sacRequestHeader) {
		return this.feedbackService.removeSellerFeedback(removeSellerFeedbackSacReq, sacRequestHeader);
	}

	/**
	 * 
	 * <pre>
	 * 평점 조회.
	 * </pre>
	 * 
	 * @param getScoreSacReq
	 *            getScoreSacReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return GetScoreSacRes
	 */
	@RequestMapping(value = "/getScore/v1", method = RequestMethod.GET)
	@ResponseBody
	public GetScoreSacRes getScore(GetScoreSacReq getScoreSacReq, SacRequestHeader sacRequestHeader) {
		return this.feedbackService.getScore(getScoreSacReq, sacRequestHeader);
	}

	/**
	 * 
	 * <pre>
	 * 평점별 참여수 조회.
	 * </pre>
	 * 
	 * @param listScoreParticpersSacReq
	 *            listScoreParticpersSacReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return ListScoreParticpersSacRes
	 */
	@RequestMapping(value = "/listScoreParticpers/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListScoreParticpersSacRes listScoreParticpers(ListScoreParticpersSacReq listScoreParticpersSacReq,
			SacRequestHeader sacRequestHeader) {
		return this.feedbackService.listScoreParticpers(listScoreParticpersSacReq, sacRequestHeader);
	}
}
