package com.skplanet.storeplatform.sac.display.feature.intimateMessage.controller;

import com.skplanet.storeplatform.sac.client.display.vo.feature.intimateMessage.IntimateMessageAppCodiSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.intimateMessage.IntimateMessageAppCodiSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.feature.intimateMessage.IntimateMessageSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.feature.intimateMessage.IntimateMessageSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.feature.intimateMessage.service.IntimateMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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

	@InitBinder("intimateMessageSacReq")
	public void initIntimateMessageSacReqBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new IntimateMessageSacReqValidator());
	}

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
			@RequestBody @Validated IntimateMessageSacReq intimateMessageSacReq) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("[searchIntimateMessageList] SacRequestHeader\n{}", header.toString());
		this.logger.debug("[searchIntimateMessageList] IntimateMessageSacReq\n{}", intimateMessageSacReq.toString());
		this.logger.debug("----------------------------------------------------------------");

		return this.intimateMessageService.searchIntimateMessageList(header, intimateMessageSacReq);
	}

	/**
	 * <pre>
	 * Intimate Message 앱코디 상품 리스트 조회.
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param messageReq
	 *            messageReq
	 * @return
	 */
	@RequestMapping(value = "/intimateMessage/appCodi/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public IntimateMessageAppCodiSacRes searchIntimateMessageAppCodiList(SacRequestHeader header,
			@Validated IntimateMessageAppCodiSacReq appCodiReq) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("[searchIntimateMessageAppCodiList] SacRequestHeader\n{}", header.toString());
		this.logger.debug("[searchIntimateMessageAppCodiList] IntimateMessageAppCodiSacReq\n{}", appCodiReq.toString());
		this.logger.debug("----------------------------------------------------------------");

		return this.intimateMessageService.searchIntimateMessageAppCodiList(header, appCodiReq);
	}
}
