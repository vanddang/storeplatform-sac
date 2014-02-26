package com.skplanet.storeplatform.sac.display.feature.intimateMessage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.feature.intimateMessage.IntimateMessageSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.intimateMessage.IntimateMessageSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.feature.intimateMessage.service.IntimateMessageService;

/**
 * IntimateMessage 조회
 * 
 * Updated on : 2014. 1. 28. Updated by : 이태희.
 */
@Controller
@RequestMapping("/display/feature")
public class IntimateMessageController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IntimateMessageService intimateMessageService;

	/**
	 * <pre>
	 * IntimateMessage 조회.
	 * </pre>
	 * 
	 * @param requestHeader
	 *            requestHeader
	 * @param intimateMessageReq
	 *            intimateMessageReq
	 * @return IntimateMessageSacRes
	 */
	@RequestMapping(value = "/intimateMessage/list/v1", method = RequestMethod.POST)
	@ResponseBody
	public IntimateMessageSacRes searchIntimateMessageList(SacRequestHeader requestHeader,
			@RequestBody @Validated IntimateMessageSacReq intimateMessageReq) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("searchIntimateMessageList started.");
		this.logger.debug("intimateMessageReq : {}", intimateMessageReq.toString());
		this.logger.debug("----------------------------------------------------------------");

		return this.intimateMessageService.searchIntimateMessageList(requestHeader, intimateMessageReq);
	}
}
