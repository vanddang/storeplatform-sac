package com.skplanet.storeplatform.sac.display.feature.list.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.feature.list.DisplayListSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.feature.list.DisplayListSacReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.feature.list.service.DisplayListService;

@Controller
@RequestMapping("/display/feature")
public class DisplayListController {
	@Autowired
	private DisplayListService listService;
	
	@RequestMapping(value = "/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public DisplayListSacRes searchList( DisplayListSacReq requestVO, SacRequestHeader header) {

		DisplayListSacRes responseVO;
		responseVO = listService.searchList(requestVO, header);
		return responseVO;
	}
}
