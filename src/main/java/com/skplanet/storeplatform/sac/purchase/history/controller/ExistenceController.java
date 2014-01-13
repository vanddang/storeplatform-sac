///*
// * Copyright (c) 2013 SK planet.
// * All right reserved.
// *
// * This software is the confidential and proprietary information of SK planet.
// * You shall not disclose such Confidential Information and
// * shall use it only in accordance with the terms of the license agreement
// * you entered into with SK planet.
// */
//package com.skplanet.storeplatform.sac.purchase.history.controller;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceList;
//import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceRequest;
//import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceResponse;
//import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceListRes;
//import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceReq;
//import com.skplanet.storeplatform.sac.client.purchase.vo.history.ExistenceRes;
//import com.skplanet.storeplatform.sac.purchase.history.service.ExistenceService;
//
//@Controller
//@RequestMapping(value = "/purchase")
//public class ExistenceController {
//
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	@Autowired
//	private ExistenceService existenceService;
//
//	@RequestMapping(value = "/history/Existence/list/v1", method = RequestMethod.POST)
//	@ResponseBody
//	public ExistenceListRes getExist(@RequestBody ExistenceReq existenceReq) {
//
//		ExistenceRequest req = this.reqConvert(existenceReq);
//		List<ExistenceResponse> existenceResponse = new ArrayList<ExistenceResponse>();
//		existenceResponse = this.existenceService.getExist(req);
//
//		List<ExistenceRes> res = new ArrayList<ExistenceRes>();
//		res = this.resConvert(existenceResponse);
//		this.logger.debug("@@@@@@@@@@@@@" + res.size());
//		this.logger.debug("@@@@@@@@@@@@@" + res.size());
//		this.logger.debug("@@@@@@@@@@@@@" + res.size());
//		this.logger.debug("@@@@@@@@@@@@@" + res.size());
//		ExistenceListRes ExistenceListRes = new ExistenceListRes();
//
//		ExistenceListRes.setExistenceRes(res);
//		return ExistenceListRes;
//	}
//
//	private ExistenceRequest reqConvert(ExistenceReq existenceReq) {
//		ExistenceRequest req = new ExistenceRequest();
//		List<ExistenceList> list = new ArrayList<ExistenceList>();
//
//		req.setTenantId(existenceReq.getTenantId());
//		req.setMbrNo(existenceReq.getMbrNo());
//		req.setDeviceNo(existenceReq.getDeviceNo());
//		int size = existenceReq.getExistenceList().size();
//		this.logger.debug("@@@@@@reqConvert@@@@@@@" + size);
//		for (int i = 0; i < size; i++) {
//			ExistenceList existenceList = new ExistenceList();
//			existenceList.setProdId(existenceReq.getExistenceList().get(i).getProdId());
//			existenceList.setTenantProdGrpCd(existenceReq.getExistenceList().get(i).getTenantProdGrpCd());
//			list.add(existenceList);
//		}
//		req.setExistenceList(list);
//
//		return req;
//	}
//
//	private List<ExistenceRes> resConvert(List<ExistenceResponse> existenceResponseList) {
//		List<ExistenceRes> res = new ArrayList<ExistenceRes>();
//		int size = existenceResponseList.size();
//		this.logger.debug("@@@@@@resConvert@@@@@@@" + size);
//		for (int i = 0; i < size; i++) {
//			ExistenceRes ExistenceRes = new ExistenceRes();
//			ExistenceRes.setPrchsId(existenceResponseList.get(i).getPrchsId());
//			ExistenceRes.setProdId(existenceResponseList.get(i).getProdId());
//			res.add(ExistenceRes);
//		}
//
//		return res;
//	}
// }
