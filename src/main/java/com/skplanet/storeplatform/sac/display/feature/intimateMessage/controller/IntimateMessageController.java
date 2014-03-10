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
	 * @param header
	 *            header
	 * @param messageReq
	 *            messageReq
	 * @return
	 */
	@RequestMapping(value = "/intimateMessage/list/v1", method = RequestMethod.POST)
	@ResponseBody
	public IntimateMessageSacRes searchIntimateMessageList(SacRequestHeader header,
			@RequestBody @Validated IntimateMessageSacReq messageReq) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("[searchIntimateMessageList] SacRequestHeader\n{}", header.toString());
		this.logger.debug("[searchIntimateMessageList] IntimateMessageSacReq\n{}", messageReq.toString());
		this.logger.debug("----------------------------------------------------------------");

		return this.intimateMessageService.searchIntimateMessageList(header, messageReq);
	}
}
