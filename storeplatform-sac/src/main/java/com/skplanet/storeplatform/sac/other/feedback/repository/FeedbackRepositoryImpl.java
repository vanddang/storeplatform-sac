/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.feedback.repository;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.internal.member.seller.sci.SellerSearchSCI;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchSapUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchSapUserSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserExtraInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserExtraInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacRes;
import com.skplanet.storeplatform.sac.other.feedback.vo.MbrAvg;
import com.skplanet.storeplatform.sac.other.feedback.vo.MbrAvgScore;
import com.skplanet.storeplatform.sac.other.feedback.vo.SendMqData;
import com.skplanet.storeplatform.sac.other.feedback.vo.ProdNoti;
import com.skplanet.storeplatform.sac.other.feedback.vo.ProdNotiGood;
import com.skplanet.storeplatform.sac.other.feedback.vo.TenantProdStats;

/**
 * 
 * Feedback Repository 구현체.
 * 
 * Updated on : 2014. 1. 24. Updated by : 김현일, 인크로스
 */
@Component
public class FeedbackRepositoryImpl implements FeedbackRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackRepositoryImpl.class);

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private SearchUserSCI searchUserSCI;

	@Autowired
	private SellerSearchSCI sellerSearchSCI;

	@Override
	public DetailInformationSacRes detailInformation(DetailInformationSacReq detailInformationSacReq) {
		LOGGER.info("################ [SAC OTH LocalSCI] SAC Member Start : sellerSearchSCI.detailInformation");
		DetailInformationSacRes detailInformationSacRes = this.sellerSearchSCI
				.detailInformation(detailInformationSacReq);
		LOGGER.info("################ [SAC OTH LocalSCI] SAC Member Start : sellerSearchSCI.detailInformation");
		return detailInformationSacRes;
	}

	@Override
	public SearchUserSacRes searchUserByUserKey(SearchUserSacReq searchUserSacReq) {
		LOGGER.info("################ [SAC OTH LocalSCI] SAC Member Start : searchUserSCI.searchUserByUserKey");
		SearchUserSacRes searchUserSacRes = this.searchUserSCI.searchUserByUserKey(searchUserSacReq);
		LOGGER.info("################ [SAC OTH LocalSCI] SAC Member Start : searchUserSCI.searchUserByUserKey");
		return searchUserSacRes;
	}

	@Override
	public ProdNoti getRegProdNoti(ProdNoti prodNoti) {
		return this.commonDAO.queryForObject("Feedback.getRegProdNoti", prodNoti, ProdNoti.class);
	}

	@Override
	public Object insertProdNoti(ProdNoti prodNoti) {
		return this.commonDAO.insert("Feedback.insertProdNoti", prodNoti);
	}

	@Override
	public Object updateProdNoti(ProdNoti prodNoti) {
		return this.commonDAO.update("Feedback.updateProdNoti", prodNoti);
	}

	@Override
	public Object deleteProdNoti(ProdNoti prodNoti) {
		return this.commonDAO.update("Feedback.deleteProdNoti", prodNoti);
	}

	@Override
	public Object deleteProdNotiGood(ProdNotiGood prodNotiGood) {
		return this.commonDAO.delete("Feedback.deleteProdNotiGood", prodNotiGood);
	}

	@Override
	public MbrAvg getRegMbrAvg(MbrAvg mbrAvg) {
		return this.commonDAO.queryForObject("Feedback.getRegMbrAvg", mbrAvg, MbrAvg.class);
	}

	@Override
	public Object mergeMbrAvg(MbrAvg mbrAvg) {
		return this.commonDAO.insert("Feedback.mergeMbrAvg", mbrAvg);
	}

	@Override
	public Object deleteMbrAvg(MbrAvg mbrAvg) {
		return this.commonDAO.delete("Feedback.deleteMbrAvg", mbrAvg);
	}

	@Override
	public TenantProdStats getTenantProdStats(TenantProdStats tenantProdStats) {
		return this.commonDAO.queryForObject("Feedback.getTenantProdStats", tenantProdStats, TenantProdStats.class);
	}

	@Override
	public Object mergeTenantProdStats(TenantProdStats tenantProdStats) {
		return this.commonDAO.insert("Feedback.mergeTenantProdStats", tenantProdStats);
	}

	@Override
	public Object updateTenantProdStats(TenantProdStats tenantProdStats) {
		return this.commonDAO.update("Feedback.updateTenantProdStats", tenantProdStats);
	}

	@Override
	public Object deleteTenantProdStats(TenantProdStats tenantProdStats) {
		return this.commonDAO.update("Feedback.deleteTenantProdStats", tenantProdStats);
	}

	@Override
	public Object insertProdNotiGood(ProdNotiGood prodNotiGood) {
		return this.commonDAO.insert("Feedback.insertProdNotiGood", prodNotiGood);
	}

	@Override
	public Object getProdNotiGoodCount(ProdNoti prodNoti) {
		return this.commonDAO.queryForInt("Feedback.getProdNotiGoodCount", prodNoti);
	}

	@Override
	public Object updateProdNotiGood(ProdNotiGood prodNotiGood) {
		return this.commonDAO.update("Feedback.updateProdNotiGood", prodNotiGood);
	}

	@Override
	public List<ProdNoti> getProdNotiList(ProdNoti prodNoti) {
		return this.commonDAO.queryForList("Feedback.getProdNotiList", prodNoti, ProdNoti.class);
	}

	@Override
	public List<ProdNoti> getProdNotiListV2(ProdNoti prodNoti) {
		return this.commonDAO.queryForList("Feedback.getProdNotiListV2", prodNoti, ProdNoti.class);
	}

	@Override
	public ProdNoti getProdNoti(ProdNoti prodNoti) {
		return this.commonDAO.queryForObject("Feedback.getProdNotiList", prodNoti, ProdNoti.class);
	}

	@Override
	public Object getProdNotiCount(ProdNoti prodNoti) {
		return this.commonDAO.queryForInt("Feedback.getProdNotiCount", prodNoti);
	}

	@Override
	public Object getProdNotiCountV2(ProdNoti prodNoti) {
		return this.commonDAO.queryForInt("Feedback.getProdNotiCountV2", prodNoti);
	}

	@Override
	public List<ProdNoti> getMyProdNotiList(ProdNoti prodNoti) {
		return this.commonDAO.queryForList("Feedback.getMyProdNotiList", prodNoti, ProdNoti.class);
	}

	@Override
	public Object getMyProdNotiCount(ProdNoti prodNoti) {
		return this.commonDAO.queryForInt("Feedback.getMyProdNotiCount", prodNoti);
	}

	@Override
	public Object updateSellerResp(ProdNoti prodNoti) {
		return this.commonDAO.update("Feedback.updateSellerResp", prodNoti);
	}

	@Override
	public TenantProdStats getProdEvalInfo(TenantProdStats tenantProdStats) {
		return this.commonDAO.queryForObject("Feedback.getProdEvalInfo", tenantProdStats, TenantProdStats.class);
	}

	@Override
	public List<MbrAvgScore> getScoreList(MbrAvgScore mbrAvgScore) {
		return this.commonDAO.queryForList("Feedback.getScoreList", mbrAvgScore, MbrAvgScore.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> getChannelEpisodeRelation(String partProdId) {
		return this.commonDAO.queryForObject("Feedback.getChannelEpisodeRelation", partProdId, Map.class);
	}

	@Override
	public Object getChannelTenantProdStatsCount(String partProdId) {
		return this.commonDAO.queryForInt("Feedback.getChannelTenantProdStatsCount", partProdId);
	}

	@Override
	public Object updateChannelTenantProdStats(TenantProdStats tenantProdStats) {
		return this.commonDAO.update("Feedback.updateChannelTenantProdStats", tenantProdStats);
	}

	@Override
	public Object insertChannelTenantProdStats(TenantProdStats tenantProdStats) {
		return this.commonDAO.update("Feedback.insertChannelTenantProdStats", tenantProdStats);
	}

	@Override
	public ProdNoti getAppProdLastDeployDt(ProdNoti prodNoti) {
		return this.commonDAO.queryForObject("Feedback.getAppProdLastDeployDt", prodNoti, ProdNoti.class);
	}

	@Override
	public SearchUserExtraInfoSacRes searchUserExtraInfo(SearchUserExtraInfoSacReq searchUserExtraInfoSacReq) {
		return this.searchUserSCI.searchUserExtraInfo(searchUserExtraInfoSacReq);
	}

	@Override
	public String getProdSvcGrpCd(String prodId) {
		return this.commonDAO.queryForObject("Feedback.getProdSvcGrpCd", prodId, String.class);
	}

	@Override
	public String getSendMqData(SendMqData data) {
		return this.commonDAO.queryForObject("Feedback.getFcSendMqData", data, String.class);
	}

	@Override
	public int getProdCheck(String prodId) {
		TenantProdStats tenantProdStats = new TenantProdStats();
		tenantProdStats.setProdId(prodId);
		return this.commonDAO.queryForObject("Feedback.getProdCheck", tenantProdStats, Integer.class);
	}
}
