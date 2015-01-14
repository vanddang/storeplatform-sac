/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.sacservice.service;

import java.util.List;

import com.skplanet.storeplatform.sac.client.other.vo.sacservice.GetActiveReq;
import com.skplanet.storeplatform.sac.client.other.vo.sacservice.GetActiveRes;
import com.skplanet.storeplatform.sac.client.other.vo.sacservice.SetActiveReq;
import com.skplanet.storeplatform.sac.client.other.vo.sacservice.SetActiveRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.other.sacservice.vo.SacService;

/**
 * SacServiceTypeService 인터페이스
 *
 * Created on 2014. 06. 02. by 서대영, SK플래닛
 */
public interface SacServiceTypeService {

	SacService fromGetReq(GetActiveReq req, SacRequestHeader header);

	GetActiveRes toGetRes(SacService vo);

	SacService fromSetReq(SetActiveReq req, SacRequestHeader header);

	SetActiveRes toSetRes(SacService vo);

	List<SacService> fromGetReqList(List<GetActiveReq> reqList, SacRequestHeader header);

	List<GetActiveRes> toGetResList(List<SacService> voList);

	List<SacService> fromSetReqList(List<SetActiveReq> reqList, SacRequestHeader header);

	List<SetActiveRes> toSetResList(List<SacService> voList);
}
