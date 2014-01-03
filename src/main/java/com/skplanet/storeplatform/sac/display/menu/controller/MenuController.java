package com.skplanet.storeplatform.sac.display.menu.controller;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuListResponse;
import com.skplanet.storeplatform.sac.client.display.vo.menu.MenuRequest;
import com.skplanet.storeplatform.sac.display.menu.service.MenuListService;

@Controller
@RequestMapping("/display/menu")
public class MenuController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MenuListService menuListService;

	@RequestMapping(value = "/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public MenuListResponse searchMenuList(@RequestParam String tenantId, @RequestParam String systemId)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		this.logger.debug("MenuController.searchMenuList start !!");

		MenuRequest requestVO = new MenuRequest();
		requestVO.setTenantId(tenantId);
		requestVO.setSystemId(systemId);

		return this.menuListService.searchMenuList(requestVO);
	}

	@RequestMapping(value = "/specific/list/v1", method = RequestMethod.GET)
	@ResponseBody
	public MenuListResponse searchMenu(@RequestParam String tenantId, @RequestParam String systemId,
			@RequestParam String menuId) throws JsonGenerationException, JsonMappingException, IOException, Exception {

		this.logger.debug("MenuController.searchMenuList start !!");

		MenuRequest requestVO = new MenuRequest();
		requestVO.setTenantId(tenantId);
		requestVO.setSystemId(systemId);
		requestVO.setSystemId(menuId);

		return this.menuListService.searchMenuList(requestVO);
	}

}
