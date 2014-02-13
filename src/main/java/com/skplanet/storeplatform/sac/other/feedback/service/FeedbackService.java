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

/**
 * 
 * Feedback Service
 * 
 * Updated on : 2014. 1. 7. Updated by : 인크로스, 김현일
 */
public interface FeedbackService {
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
	public CreateFeedbackSacRes create(CreateFeedbackSacReq createFeedbackSacReq, SacRequestHeader sacRequestHeader);

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
	public ModifyFeedbackSacRes modify(ModifyFeedbackSacReq modifyFeedbackSacReq, SacRequestHeader sacRequestHeader);

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
	public RemoveFeedbackSacRes remove(RemoveFeedbackSacReq removeFeedbackSacReq, SacRequestHeader sacRequestHeader);

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
	public CreateRecommendFeedbackSacRes createRecommend(CreateRecommendFeedbackSacReq createRecommendFeedbackSacReq,
			SacRequestHeader sacRequestHeader);

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
	public RemoveRecommendFeedbackSacRes removeRecommend(RemoveRecommendFeedbackSacReq removeRecommendFeedbackSacReq,
			SacRequestHeader sacRequestHeader);

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
	public ListFeedbackSacRes list(ListFeedbackSacReq listFeedbackSacReq, SacRequestHeader sacRequestHeader);

	/**
	 * 
	 * <pre>
	 * 내 사용후기 조회.
	 * </pre>
	 * 
	 * @param listMyFeedbackSacReq
	 *            listMyFeedbackSacReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return ListMyFeedbackSacRes
	 */
	public ListMyFeedbackSacRes listMyFeedback(ListMyFeedbackSacReq listMyFeedbackSacReq,
			SacRequestHeader sacRequestHeader);

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
	public CreateSellerFeedbackSacRes createSellerFeedback(CreateSellerFeedbackSacReq createSellerFeedbackSacReq,
			SacRequestHeader sacRequestHeader);

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
	public ModifySellerFeedbackSacRes modifySellerFeedback(ModifySellerFeedbackSacReq modifySellerFeedbackSacReq,
			SacRequestHeader sacRequestHeader);

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
	public RemoveSellerFeedbackSacRes removeSellerFeedback(RemoveSellerFeedbackSacReq removeSellerFeedbackSacReq,
			SacRequestHeader sacRequestHeader);

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
	public GetScoreSacRes getScore(GetScoreSacReq getScoreSacReq, SacRequestHeader sacRequestHeader);

	/**
	 * 
	 * <pre>
	 * 평점별 참여수 조회.
	 * </pre>
	 * 
	 * @param ListScorePaticpersSacReq
	 *            ListScorePaticpersSacReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return ListScoreParticpersSacRes
	 */
	public ListScorePaticpersSacRes listScoreParticpers(ListScorePaticpersSacReq ListScorePaticpersSacReq,
			SacRequestHeader sacRequestHeader);

	/**
	 * 
	 * <pre>
	 * 사용자 ID 변경.
	 * </pre>
	 * 
	 * @param changeFeedbackUserIdSacReq
	 *            changeFeedbackUserIdSacReq
	 * @return ChangeFeedbackUserIdSacRes
	 */
	public ChangeFeedbackUserIdSacRes changeFeedbackUserId(ChangeFeedbackUserIdSacReq changeFeedbackUserIdSacReq);

	/**
	 * 
	 * <pre>
	 * 사용자 Key 변경.
	 * </pre>
	 * 
	 * @param changeFeedbackUserKeySacReq
	 *            changeFeedbackUserKeySacReq
	 * @return ChangeFeedbackUserKeySacRes
	 */
	public ChangeFeedbackUserKeySacRes changeFeedbackUserKey(ChangeFeedbackUserKeySacReq changeFeedbackUserKeySacReq);

}
