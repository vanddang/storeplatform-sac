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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.client.other.vo.sacservice.GetActiveReq;
import com.skplanet.storeplatform.sac.client.other.vo.sacservice.GetActiveRes;
import com.skplanet.storeplatform.sac.client.other.vo.sacservice.SetActiveReq;
import com.skplanet.storeplatform.sac.client.other.vo.sacservice.SetActiveRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.other.sacservice.vo.SacService;

/**
 * SacServiceTypeService 클래스
 *
 * Created on 2014. 06. 02. by 서대영, SK플래닛
 */
@Service
public class SacServiceTypeServiceImpl implements SacServiceTypeService {

	@Override
	public SacService fromGetReq(GetActiveReq req, SacRequestHeader header) {
		String serviceCd = req.getServiceCd();
		String tenantId = chooseValueFromBodyOrHeader(req.getServiceCd(), header.getTenantHeader().getTenantId());
		String simOperator = chooseValueFromBodyOrHeader(req.getSimOperator(), header.getNetworkHeader().getSimOperator());
		String model = chooseValueFromBodyOrHeader(req.getModel(), header.getDeviceHeader().getModel());
		
		SacService vo = new SacService();
		vo.setServiceCd(serviceCd);
		vo.setTenantId(tenantId);
		vo.setSimOperator(simOperator);
		vo.setModel(model);
		
		return vo;
	}
	
	public static String chooseValueFromBodyOrHeader(String valueFromBody, String valueFromHeader) {
		if (StringUtils.isNotBlank(valueFromBody)) {
			return valueFromBody;
		} else {
			return valueFromHeader;
		}
	}

	@Override
	public GetActiveRes toGetRes(SacService vo) {
		GetActiveRes res = new GetActiveRes();
		res.setServiceCd(vo.getServiceCd());
		res.setSimOperator(vo.getSimOperator());
		res.setModel(vo.getModel());
		res.setActive(vo.isActive());
		return res;
	}

	@Override
	public SacService fromSetReq(SetActiveReq req, SacRequestHeader header) {
		String serviceCd = req.getServiceCd();
		String tenantId = chooseValueFromBodyOrHeader(req.getServiceCd(), header.getTenantHeader().getTenantId());
		String simOperator = chooseValueFromBodyOrHeader(req.getSimOperator(), header.getNetworkHeader().getSimOperator());
		String model = chooseValueFromBodyOrHeader(req.getModel(), header.getDeviceHeader().getModel());
		
		SacService vo = new SacService();
		vo.setServiceCd(serviceCd);
		vo.setTenantId(tenantId);
		vo.setSimOperator(simOperator);
		vo.setModel(model);

		return vo;
	}

	@Override
	public SetActiveRes toSetRes(SacService vo) {
		SetActiveRes res = new SetActiveRes();
		res.setServiceCd(vo.getServiceCd());
		res.setSimOperator(vo.getSimOperator());
		res.setModel(vo.getModel());
		res.setApplied(vo.isApplied());
		return res;
	}

	@Override
	public List<SacService> fromGetReqList(List<GetActiveReq> reqList, SacRequestHeader header) {
		List<SacService> voList = new ArrayList<SacService>();

		if (CollectionUtils.isNotEmpty(reqList)) {
			for (GetActiveReq req : reqList) {
				SacService vo = this.fromGetReq(req, header);
				voList.add(vo);
			}
		}

		return voList;
	}

	@Override
	public List<GetActiveRes> toGetResList(List<SacService> voList) {
		List<GetActiveRes> resList = new ArrayList<GetActiveRes>();

		if (CollectionUtils.isNotEmpty(voList)) {
			for (SacService vo : voList) {
				GetActiveRes res = this.toGetRes(vo);
				resList.add(res);
			}
		}

		return resList;
	}

	@Override
	public List<SacService> fromSetReqList(List<SetActiveReq> reqList, SacRequestHeader header) {
		List<SacService> voList = new ArrayList<SacService>();

		if (CollectionUtils.isNotEmpty(reqList)) {
			for (SetActiveReq req : reqList) {
				SacService vo = this.fromSetReq(req, header);
				voList.add(vo);
			}
		}

		return voList;
	}

	@Override
	public List<SetActiveRes> toSetResList(List<SacService> voList) {
		List<SetActiveRes> resList = new ArrayList<SetActiveRes>();

		if (CollectionUtils.isNotEmpty(voList)) {
			for (SacService vo : voList) {
				SetActiveRes res = this.toSetRes(vo);
				resList.add(res);
			}
		}

		return resList;
	}




}
