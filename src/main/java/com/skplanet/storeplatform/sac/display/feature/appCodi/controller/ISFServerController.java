package com.skplanet.storeplatform.sac.display.feature.appCodi.controller;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.display.feature.appCodi.service.ISFServerService;
import com.skplanet.storeplatform.sac.display.feature.appCodi.vo.Request;
import com.skplanet.storeplatform.sac.display.feature.appCodi.vo.Response;

/**
 * 
 * Calss 설명(AppCodi 조회 컨트롤러)
 * 
 * Updated on : 2014. 01. 28. Updated by : 윤주영, SK 플래닛.
 */
@Controller
@RequestMapping("/isf-tstore")
public class ISFServerController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ISFServerService isfServerService;

	/**
	 * <pre>
	 * ISF TSTORE LOCAL SERVER METHOD.
	 * </pre>
	 * 
	 */
	@RequestMapping(value = "/appCodi", method = RequestMethod.POST)
	@ResponseBody
	public Response makeAppCodiList(@RequestBody Request requestVO) throws Exception {

		this.logger.debug("ISFServerController.makeAppCodiList start !!");
		this.logger.debug("request {}", requestVO);

		Response response = this.isfServerService.makeAppCodiList(requestVO);
		JAXBContext jc = JAXBContext.newInstance(Response.class);
		Marshaller m1 = jc.createMarshaller();
		m1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m1.marshal(response, System.out);

		return response;
	}

	/**
	 * <pre>
	 * ISF TSTORE LOCAL SERVER METHOD.
	 * </pre>
	 * 
	 */
	@RequestMapping(value = "/theme/recommend", method = RequestMethod.POST)
	@ResponseBody
	public Response makeThemeRecommendList(@RequestBody Request requestVO) throws Exception {

		this.logger.debug("ISFServerController.makeThemeRecommendList start !!");
		this.logger.debug("request {}", requestVO);

		Response response = this.isfServerService.makeThemeRecommendList(requestVO);
		JAXBContext jc = JAXBContext.newInstance(Response.class);
		Marshaller m1 = jc.createMarshaller();
		m1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m1.marshal(response, System.out);

		return response;
	}
}
