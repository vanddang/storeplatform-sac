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
	 * @param createFeedbackReq
	 *            createFeedbackReq
	 * @param sacRequestHeader
	 *            sacRequestHeader
	 * @return CreateFeedbackRes
	 */
	public CreateFeedbackRes create(CreateFeedbackReq createFeedbackReq, SacRequestHeader sacRequestHeader);

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
	public ModifyFeedbackRes modify(ModifyFeedbackReq modifyFeedbackReq, SacRequestHeader sacRequestHeader);

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
	public RemoveFeedbackRes remove(RemoveFeedbackReq removeFeedbackReq, SacRequestHeader sacRequestHeader);

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
	public CreateRecommendFeedbackRes createRecommend(CreateRecommendFeedbackReq createRecommendFeedbackReq,
			SacRequestHeader sacRequestHeader);

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
	public RemoveRecommendFeedbackRes removeRecommend(RemoveRecommendFeedbackReq removeRecommendFeedbackReq,
			SacRequestHeader sacRequestHeader);

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
	public ListFeedbackRes list(ListFeedbackReq listFeedbackReq, SacRequestHeader sacRequestHeader);

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
	public ListMyFeedbackRes listMyFeedback(ListMyFeedbackReq listMyFeedbackReq, SacRequestHeader sacRequestHeader);

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
	public CreateSellerFeedbackRes createSellerFeedback(CreateSellerFeedbackReq createSellerFeedbackReq,
			SacRequestHeader sacRequestHeader);

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
	public ModifySellerFeedbackRes modifySellerFeedback(ModifySellerFeedbackReq modifySellerFeedbackReq,
			SacRequestHeader sacRequestHeader);

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
	public RemoveSellerFeedbackRes removeSellerFeedback(RemoveSellerFeedbackReq removeSellerFeedbackReq,
			SacRequestHeader sacRequestHeader);

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
	public GetScoreRes getScore(GetScoreReq getScoreReq, SacRequestHeader sacRequestHeader);

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
	public ListScoreParticpersRes listScoreParticpers(ListScoreParticpersReq listScoreParticpersReq,
			SacRequestHeader sacRequestHeader);

	/**
	 * 
	 * <pre>
	 * 사용자 ID 변경.
	 * </pre>
	 * 
	 * @param changeFeedbackUserIdReq
	 *            changeFeedbackUserIdReq
	 * @return ChangeFeedbackUserIdRes
	 */
	public ChangeFeedbackUserIdRes changeFeedbackUserId(ChangeFeedbackUserIdReq changeFeedbackUserIdReq);

	/**
	 * 
	 * <pre>
	 * 사용자 Key 변경.
	 * </pre>
	 * 
	 * @param changeFeedbackUserKeyReq
	 *            changeFeedbackUserKeyReq
	 * @return ChangeFeedbackUserKeyRes
	 */
	public ChangeFeedbackUserKeyRes changeFeedbackUserKey(ChangeFeedbackUserKeyReq changeFeedbackUserKeyReq);

}
