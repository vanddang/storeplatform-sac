/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.stat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.client.display.vo.stat.LikeRes;
import com.skplanet.storeplatform.sac.client.display.vo.stat.ListByMemberReq;
import com.skplanet.storeplatform.sac.client.display.vo.stat.ListByMemberRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.card.vo.PreferredCategoryInfo;
import com.skplanet.storeplatform.sac.display.stat.util.StatMemberUtils;
import com.skplanet.storeplatform.sac.display.stat.vo.StatLike;

/**
 * <p>
 * StatMemberTypeServiceImpl
 * </p>
 * Updated on : 2014. 11. 04 Updated by : 서대영, SK 플래닛.
 */
@Service
public class StatMemberTypeServiceImpl implements StatMemberTypeService {
	
	@Autowired
	private StatMemberItemService itemService;
	
	@Override
	public StatLike fromReqToVo(ListByMemberReq req, SacRequestHeader header) {
		String tenantId = header.getTenantHeader().getTenantId();
		String userKey = req.getUserKey();
		int startKey = req.getStartKey();
		int count = req.getCount();
		
		StatLike statLike = new StatLike(tenantId, userKey); 
		statLike.setStartKey(startKey);
		statLike.setCount(count);
		return statLike;
	}
	
	@Override
	public ListByMemberRes fromVotoRes(List<StatLike> voList, ListByMemberReq req, SacRequestHeader header) {
		ListByMemberRes res = new ListByMemberRes();		
		mapLikeList(res, voList, req, header);
		mapTotalCount(res, voList);
		mapCount(res, voList, req);
		mapHasNext(res, voList, req);
		mapStartKey(res, voList, req);
		return res;
	}

	private void mapLikeList(ListByMemberRes res, List<StatLike> voList, ListByMemberReq req, SacRequestHeader header) {
		List<LikeRes> resList = new ArrayList<LikeRes>();
		int count = StatMemberUtils.getResCount(voList.size(), req.getCount()); // for hasNext
		PreferredCategoryInfo preferredCategoryInfo = new PreferredCategoryInfo(req.getPreferredCategoryList());
		req.getPreferredCategoryList();
		
		for (int i = 0; i < count; i++) {
			StatLike each = voList.get(i);
			String statsClsf = each.getStatsClsf();
			String statsKey = each.getStatsKey();
			
			int cntLike = each.getCntLike();
			if (cntLike < 0) {
				cntLike = 0;
			}
			int cntShar = each.getCntShar();
			if (cntShar < 0) {
				cntShar = 0;
			}
			
			Object item = itemService.findItem(each, header, preferredCategoryInfo);
			
			LikeRes likeRes = new LikeRes();
			likeRes.setStatsClsf(statsClsf);
			likeRes.setStatsKey(statsKey);
			likeRes.setCntLike(cntLike);
			likeRes.setCntShar(cntShar);
			likeRes.setItem(item);
			
			resList.add(likeRes);
		}
		
		res.setLikeList(resList);
	}
		
	private void mapTotalCount(ListByMemberRes res, List<StatLike> voList) {
		if (voList.size() == 0) {
			res.setTotalCount(0);
			return;
		}
		
		int totalCount = voList.get(0).getTotalCount();
		res.setTotalCount(totalCount);
	}
	
	private void mapCount(ListByMemberRes res, List<StatLike> voList, ListByMemberReq req) {
		int count = StatMemberUtils.getResCount(voList.size(), req.getCount());
		res.setCount(count); 
	}
	
	private void mapHasNext(ListByMemberRes res, List<StatLike> voList, ListByMemberReq req) {
		if (StatMemberUtils.hasResNext(voList.size(), req.getCount())){
			res.setHasNext("Y");
		} else {
			res.setHasNext("N");
		}
	}
	
	private void mapStartKey(ListByMemberRes res, List<StatLike> voList, ListByMemberReq req) {
		if (!StatMemberUtils.hasResNext(voList.size(), req.getCount())) {
			return;
		}
		
		StatLike startVo = voList.get(voList.size() - 1);
		Integer startSeq = startVo.getSeq();
		res.setStartKey(startSeq);
	}

}
