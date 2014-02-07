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

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.NumberUtils;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
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
import com.skplanet.storeplatform.sac.other.feedback.repository.FeedbackRepository;
import com.skplanet.storeplatform.sac.other.feedback.vo.MbrAvg;
import com.skplanet.storeplatform.sac.other.feedback.vo.ProdNoti;
import com.skplanet.storeplatform.sac.other.feedback.vo.ProdNotiGood;
import com.skplanet.storeplatform.sac.other.feedback.vo.TenantProdStats;

/**
 * 
 * Feedback Service 구현체
 * 
 * Updated on : 2014. 1. 16. Updated by : 김현일, 인크로스.
 */
@Profile(value = { "stag", "real" })
@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Override
	public CreateFeedbackSacRes create(CreateFeedbackSacReq createFeedbackSacReq, SacRequestHeader sacRequestHeader) {

		// 평점 저장
		this.setMbrAvgTenantProdStats(createFeedbackSacReq, sacRequestHeader);

		// 사용후기 내용이 있을 경우에만 등록한다.
		// 필수 파라미터이기 때문에 if 로직은 빠져되 된다.
		// if (StringUtils.isNotEmpty(createFeedbackSacReq.getNotiDesc())) {
		ProdNoti prodNoti = new ProdNoti();
		prodNoti.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		prodNoti.setMbrNo(createFeedbackSacReq.getUserKey());
		prodNoti.setProdId(createFeedbackSacReq.getProdId());
		prodNoti.setTitle(createFeedbackSacReq.getNotiTitle());
		prodNoti.setNotiDscr(createFeedbackSacReq.getNotiDscr());
		prodNoti.setRegId(createFeedbackSacReq.getUserId());
		prodNoti.setMbrTelno(createFeedbackSacReq.getDeviceId());
		prodNoti.setFbPostYn(createFeedbackSacReq.getFbPostYn());
		prodNoti.setDeviceModelCd(sacRequestHeader.getDeviceHeader().getModel());
		prodNoti.setPkgVer(createFeedbackSacReq.getPkgVer());
		prodNoti.setChnlId(createFeedbackSacReq.getChnlId());
		ProdNoti getRegProdNoti = this.feedbackRepository.getRegProdNoti(prodNoti);
		if (getRegProdNoti == null) {
			int affectedRow = (Integer) this.feedbackRepository.insertProdNoti(prodNoti);
			if (affectedRow <= 0)
				throw new StorePlatformException("SAC_OTH_1001");
		} else {
			throw new StorePlatformException("SAC_OTH_1001");
		}
		CreateFeedbackSacRes createFeedbackSacRes = new CreateFeedbackSacRes();
		createFeedbackSacRes.setNotiSeq(prodNoti.getNotiSeq());
		return createFeedbackSacRes;
	}

	@Override
	public ModifyFeedbackSacRes modify(ModifyFeedbackSacReq modifyFeedbackSacReq, SacRequestHeader sacRequestHeader) {

		// 평점 저장.
		this.setMbrAvgTenantProdStats(modifyFeedbackSacReq, sacRequestHeader);

		ProdNoti prodNoti = new ProdNoti();
		prodNoti.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		prodNoti.setNotiSeq(modifyFeedbackSacReq.getNotiSeq());
		prodNoti.setMbrNo(modifyFeedbackSacReq.getUserKey());
		// prodNoti.setProdId(modifyFeedbackSacReq.getProdId());
		prodNoti.setTitle(modifyFeedbackSacReq.getNotiTitle());
		prodNoti.setNotiDscr(modifyFeedbackSacReq.getNotiDscr());
		// prodNoti.setRegId(createFeedbackSacReq.getUserId());
		// prodNoti.setMbrTelno(createFeedbackSacReq.getDeviceId());
		// prodNoti.setFbPostYn(createFeedbackSacReq.getFbSendYN());
		prodNoti.setDeviceModelCd(sacRequestHeader.getDeviceHeader().getModel());
		prodNoti.setPkgVer(modifyFeedbackSacReq.getPkgVer());
		// prodNoti.setChnlId(modifyFeedbackSacReq.getChnlId());

		int affectedRow = (Integer) this.feedbackRepository.updateProdNoti(prodNoti);

		if (affectedRow > 0) {
			ProdNotiGood prodNotiGood = new ProdNotiGood();
			prodNotiGood.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
			prodNotiGood.setNotiSeq(modifyFeedbackSacReq.getNotiSeq());
			this.feedbackRepository.deleteProdNotiGood(prodNotiGood);
		} else {
			throw new StorePlatformException("SAC_OTH_1002");
		}

		ModifyFeedbackSacRes modifyFeedbackSacRes = new ModifyFeedbackSacRes();
		modifyFeedbackSacRes.setNotiSeq(prodNoti.getNotiSeq());
		return modifyFeedbackSacRes;
	}

	@Override
	public RemoveFeedbackSacRes remove(RemoveFeedbackSacReq removeFeedbackSacReq, SacRequestHeader sacRequestHeader) {
		// 기 평가여부 조회
		MbrAvg mbrAvg = new MbrAvg();
		mbrAvg.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		mbrAvg.setMbrNo(removeFeedbackSacReq.getUserKey());
		mbrAvg.setProdId(removeFeedbackSacReq.getProdId());
		MbrAvg getRegMbrAvg = this.feedbackRepository.getRegMbrAvg(mbrAvg);
		if (getRegMbrAvg != null) {
			this.feedbackRepository.deleteMbrAvg(mbrAvg);
			TenantProdStats tenantProdStats = new TenantProdStats();
			tenantProdStats.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
			tenantProdStats.setProdId(removeFeedbackSacReq.getProdId());
			TenantProdStats getTenantProdStats = this.feedbackRepository.getTenantProdStats(tenantProdStats);
			if (getTenantProdStats != null) {
				if (NumberUtils.toInt(getTenantProdStats.getPaticpersCnt(), 0) == 1) {
					this.feedbackRepository.deleteTenantProdStats(tenantProdStats);
				} else {
					TenantProdStats updateTenantProdStats = new TenantProdStats();
					updateTenantProdStats.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
					updateTenantProdStats.setProdId(removeFeedbackSacReq.getProdId());
					updateTenantProdStats.setAvgEvluScore("0");
					updateTenantProdStats.setPreAvgScore(getRegMbrAvg.getAvgScore());
					this.feedbackRepository.updateTenantProdStats(updateTenantProdStats);
				}
			}
		}

		ProdNoti prodNoti = new ProdNoti();
		prodNoti.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
		prodNoti.setNotiSeq(removeFeedbackSacReq.getNotiSeq());
		prodNoti.setNotiDscr(StringUtils.EMPTY);
		prodNoti.setMbrNo(removeFeedbackSacReq.getUserKey());
		int affectedRow = (Integer) this.feedbackRepository.deleteProdNoti(prodNoti);
		if (affectedRow <= 0) {
			throw new StorePlatformException("SAC_OTH_1002");
		}

		RemoveFeedbackSacRes removeFeedbackSacRes = new RemoveFeedbackSacRes();
		removeFeedbackSacRes.setNotiSeq(removeFeedbackSacReq.getNotiSeq());

		return removeFeedbackSacRes;
	}

	@Override
	public CreateRecommendFeedbackSacRes createRecommend(CreateRecommendFeedbackSacReq createRecommendFeedbackSacReq,
			SacRequestHeader sacRequestHeader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RemoveRecommendFeedbackSacRes removeRecommend(RemoveRecommendFeedbackSacReq removeRecommendFeedbackSacReq,
			SacRequestHeader sacRequestHeader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListFeedbackSacRes list(ListFeedbackSacReq listFeedbackSacReq, SacRequestHeader sacRequestHeader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListMyFeedbackSacRes listMyFeedback(ListMyFeedbackSacReq listMyFeedbackSacReq,
			SacRequestHeader sacRequestHeader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CreateSellerFeedbackSacRes createSellerFeedback(CreateSellerFeedbackSacReq createSellerFeedbackSacReq,
			SacRequestHeader sacRequestHeader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModifySellerFeedbackSacRes modifySellerFeedback(ModifySellerFeedbackSacReq modifySellerFeedbackSacReq,
			SacRequestHeader sacRequestHeader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RemoveSellerFeedbackSacRes removeSellerFeedback(RemoveSellerFeedbackSacReq removeSellerFeedbackSacReq,
			SacRequestHeader sacRequestHeader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetScoreSacRes getScore(GetScoreSacReq getScoreSacReq, SacRequestHeader sacRequestHeader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListScoreParticpersSacRes listScoreParticpers(ListScoreParticpersSacReq listScoreParticpersSacReq,
			SacRequestHeader sacRequestHeader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChangeFeedbackUserIdSacRes changeFeedbackUserId(ChangeFeedbackUserIdSacReq changeFeedbackUserIdSacReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChangeFeedbackUserKeySacRes changeFeedbackUserKey(ChangeFeedbackUserKeySacReq changeFeedbackUserKeySacReq) {
		// TODO Auto-generated method stub
		return null;
	}

	private void setMbrAvgTenantProdStats(Object object, SacRequestHeader sacRequestHeader) {
		BeanWrapperImpl beanWrapperImpl = new BeanWrapperImpl();
		beanWrapperImpl.setWrappedInstance(object);
		String score = (String) beanWrapperImpl.getPropertyValue("avgScore");
		// 채널ID는 평점 테이블 저장시에만 사용된다.
		String chnlId = (String) beanWrapperImpl.getPropertyValue("chnlId");
		String userKey = (String) beanWrapperImpl.getPropertyValue("userKey");
		String prodId = (String) beanWrapperImpl.getPropertyValue("prodId");
		if (StringUtils.isEmpty(chnlId)) {
			String avgScore = "1";
			if (NumberUtils.toInt(score, 0) > 5) {
				avgScore = "5";
			} else if (NumberUtils.toInt(score, 0) <= 0) {
				avgScore = "1";
			}
			MbrAvg mbrAvg = new MbrAvg();
			mbrAvg.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
			mbrAvg.setMbrNo(userKey);
			mbrAvg.setProdId(prodId);
			mbrAvg.setAvgScore(avgScore);

			MbrAvg getRegMbrAvg = this.feedbackRepository.getRegMbrAvg(mbrAvg);
			this.feedbackRepository.mergeMbrAvg(mbrAvg);

			TenantProdStats tenantProdStats = new TenantProdStats();
			tenantProdStats.setTenantId(sacRequestHeader.getTenantHeader().getTenantId());
			tenantProdStats.setProdId(prodId);
			if (getRegMbrAvg != null) {
				tenantProdStats.setAvgEvluScore(avgScore);
				tenantProdStats.setPreAvgScore(getRegMbrAvg.getAvgScore());
				this.feedbackRepository.updateTenantProdStats(tenantProdStats);
			} else {
				tenantProdStats.setAvgEvluScore(avgScore);
				this.feedbackRepository.mergeTenantProdStats(tenantProdStats);
			}
		}
	}
}
