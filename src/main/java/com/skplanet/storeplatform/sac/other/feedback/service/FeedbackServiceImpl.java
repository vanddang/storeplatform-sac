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

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
 * Feedback Service 구현체
 * 
 * Updated on : 2014. 1. 16. Updated by : 김현일, 인크로스.
 */
@Profile(value = { "stag", "real" })
@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

	@Override
	public CreateFeedbackRes create(CreateFeedbackReq createFeedbackReq, SacRequestHeader sacRequestHeader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModifyFeedbackRes modify(ModifyFeedbackReq modifyFeedbackReq, SacRequestHeader sacRequestHeader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RemoveFeedbackRes remove(RemoveFeedbackReq removeFeedbackReq, SacRequestHeader sacRequestHeader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CreateRecommendFeedbackRes createRecommend(CreateRecommendFeedbackReq createRecommendFeedbackReq,
			SacRequestHeader sacRequestHeader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RemoveRecommendFeedbackRes removeRecommend(RemoveRecommendFeedbackReq removeRecommendFeedbackReq,
			SacRequestHeader sacRequestHeader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListFeedbackRes list(ListFeedbackReq listFeedbackReq, SacRequestHeader sacRequestHeader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListMyFeedbackRes listMyFeedback(ListMyFeedbackReq listMyFeedbackReq, SacRequestHeader sacRequestHeader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CreateSellerFeedbackRes createSellerFeedback(CreateSellerFeedbackReq createSellerFeedbackReq,
			SacRequestHeader sacRequestHeader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModifySellerFeedbackRes modifySellerFeedback(ModifySellerFeedbackReq modifySellerFeedbackReq,
			SacRequestHeader sacRequestHeader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RemoveSellerFeedbackRes removeSellerFeedback(RemoveSellerFeedbackReq removeSellerFeedbackReq,
			SacRequestHeader sacRequestHeader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetScoreRes getScore(GetScoreReq getScoreReq, SacRequestHeader sacRequestHeader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListScoreParticpersRes listScoreParticpers(ListScoreParticpersReq listScoreParticpersReq,
			SacRequestHeader sacRequestHeader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChangeFeedbackUserIdRes changeFeedbackUserId(ChangeFeedbackUserIdReq changeFeedbackUserIdReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChangeFeedbackUserKeyRes changeFeedbackUserKey(ChangeFeedbackUserKeyReq changeFeedbackUserKeyReq) {
		// TODO Auto-generated method stub
		return null;
	}

}
