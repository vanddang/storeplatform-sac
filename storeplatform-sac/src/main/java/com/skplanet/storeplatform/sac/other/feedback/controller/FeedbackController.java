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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
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
import com.skplanet.storeplatform.sac.other.common.util.ObjectMapperUtils;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackController.class);

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
	public CreateFeedbackSacRes create(@RequestBody @Validated CreateFeedbackSacReq createFeedbackSacReq, SacRequestHeader sacRequestHeader) {
		LOGGER.info("Request : {}", ObjectMapperUtils.convertObjectToJson(createFeedbackSacReq));
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
	public ModifyFeedbackSacRes modify(@RequestBody @Validated ModifyFeedbackSacReq modifyFeedbackSacReq, SacRequestHeader sacRequestHeader) {
		LOGGER.info("Request : {}", ObjectMapperUtils.convertObjectToJson(modifyFeedbackSacReq));
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
	public RemoveFeedbackSacRes remove(@RequestBody @Validated RemoveFeedbackSacReq removeFeedbackSacReq, SacRequestHeader sacRequestHeader) {
		LOGGER.info("Request : {}", ObjectMapperUtils.convertObjectToJson(removeFeedbackSacReq));
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
	public CreateRecommendFeedbackSacRes createRecommend(@RequestBody @Validated CreateRecommendFeedbackSacReq createRecommendFeedbackSacReq, SacRequestHeader sacRequestHeader) {
		LOGGER.info("Request : {}", ObjectMapperUtils.convertObjectToJson(createRecommendFeedbackSacReq));
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
	public RemoveRecommendFeedbackSacRes removeRecommend(@RequestBody @Validated RemoveRecommendFeedbackSacReq removeRecommendFeedbackSacReq, SacRequestHeader sacRequestHeader) {
		LOGGER.info("Request : {}", ObjectMapperUtils.convertObjectToJson(removeRecommendFeedbackSacReq));
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
	public ListFeedbackSacRes list(@Validated ListFeedbackSacReq listFeedbackSacReq, SacRequestHeader sacRequestHeader) {
		LOGGER.info("Request : {}", ObjectMapperUtils.convertObjectToJson(listFeedbackSacReq));
		return this.feedbackService.list(listFeedbackSacReq, sacRequestHeader);
	}

	/**
	 * 
	 * <pre>
	 * [I04000029] 사용후기 전체 조회(V2).
	 * </pre>
	 * 
	 * @param listFeedbackSacReq
	 *            listFeedbackSacReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return ListFeedbackSacRes
	 */
	@RequestMapping(value = "/list/v2", method = RequestMethod.GET)
	@ResponseBody
	public ListFeedbackSacRes listV2(@Validated ListFeedbackSacReq listFeedbackSacReq, SacRequestHeader sacRequestHeader) {
		LOGGER.info("Request : {}", ObjectMapperUtils.convertObjectToJson(listFeedbackSacReq));
		return this.feedbackService.listV2(listFeedbackSacReq, sacRequestHeader);
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
	public ListMyFeedbackSacRes listMyFeedback(@Validated ListMyFeedbackSacReq listMyFeedbackSacReq, SacRequestHeader sacRequestHeader) {
		LOGGER.info("Request : {}", ObjectMapperUtils.convertObjectToJson(listMyFeedbackSacReq));
		return this.feedbackService.listMyFeedback(listMyFeedbackSacReq, sacRequestHeader);
	}

	/**
	 * 
	 * <pre>
	 * [I04000029] 내 사용후기 조회 V2.
	 * </pre>
	 * 
	 * @param listMyFeedbackSacReq
	 *            listMyFeedbacSackReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return ListMyFeedbackSacRes
	 */
	@RequestMapping(value = "/listMyFeedback/v2", method = RequestMethod.GET)
	@ResponseBody
	public ListMyFeedbackSacRes listMyFeedbackV2(@Validated ListMyFeedbackSacReq listMyFeedbackSacReq, SacRequestHeader sacRequestHeader) {
		LOGGER.info("Request : {}", ObjectMapperUtils.convertObjectToJson(listMyFeedbackSacReq));
		return this.feedbackService.listMyFeedbackV2(listMyFeedbackSacReq, sacRequestHeader);
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
	public CreateSellerFeedbackSacRes createSellerFeedback(@RequestBody @Validated CreateSellerFeedbackSacReq createSellerFeedbackSacReq, SacRequestHeader sacRequestHeader) {
		LOGGER.info("Request : {}", ObjectMapperUtils.convertObjectToJson(createSellerFeedbackSacReq));
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
	public ModifySellerFeedbackSacRes modifySellerFeedback(@RequestBody @Validated ModifySellerFeedbackSacReq modifySellerFeedbackSacReq, SacRequestHeader sacRequestHeader) {
		LOGGER.info("Request : {}", ObjectMapperUtils.convertObjectToJson(modifySellerFeedbackSacReq));
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
	public RemoveSellerFeedbackSacRes removeSellerFeedback(@RequestBody @Validated RemoveSellerFeedbackSacReq removeSellerFeedbackSacReq, SacRequestHeader sacRequestHeader) {
		LOGGER.info("Request : {}", ObjectMapperUtils.convertObjectToJson(removeSellerFeedbackSacReq));
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
	public GetScoreSacRes getScore(@Validated GetScoreSacReq getScoreSacReq, SacRequestHeader sacRequestHeader) {
		LOGGER.info("Request : {}", ObjectMapperUtils.convertObjectToJson(getScoreSacReq));
		return this.feedbackService.getScore(getScoreSacReq, sacRequestHeader);
	}

	/**
	 * 
	 * <pre>
	 * 평점별 참여수 조회.
	 * </pre>
	 * 
	 * @param listScorePaticpersSacReq
	 *            listScorePaticpersSacReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return ListScoreParticpersSacRes
	 */
	@RequestMapping(value = "/listScorePaticpers/v1", method = RequestMethod.GET)
	@ResponseBody
	public ListScorePaticpersSacRes listScoreParticpers(@Validated ListScorePaticpersSacReq listScorePaticpersSacReq, SacRequestHeader sacRequestHeader) {
		LOGGER.info("Request : {}", ObjectMapperUtils.convertObjectToJson(listScorePaticpersSacReq));
		return this.feedbackService.listScoreParticpers(listScorePaticpersSacReq, sacRequestHeader);
	}
}
