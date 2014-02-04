/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.appCodi.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.skplanet.storeplatform.external.client.isf.vo.ISFReq;
import com.skplanet.storeplatform.external.client.isf.vo.ISFRes;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.feature.appCodi.AppCodiListRes;
import com.skplanet.storeplatform.sac.client.display.vo.feature.appCodi.AppCodiReq;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;

/**
 * App Codi Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 01. 28. Updated by : 윤주영, SK 플래닛.
 */
@Service
public class AppCodiServiceImpl implements AppCodiService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Value("#{propertiesForSac['component.external.baseUrl']}")
	private String domainName;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.CategoryServiceImpl#searchTopCategoryList(MenuReq
	 * requestVO)
	 */
	@Override
	public AppCodiListRes searchAppCodiList(AppCodiReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		TenantHeader tenantHeader = requestHeader.getTenantHeader();
		DeviceHeader deviceHeader = requestHeader.getDeviceHeader();

		AppCodiListRes responseVO = new AppCodiListRes();

		CommonResponse commonResponse = new CommonResponse();

		ISFRes response = this.getISF_AppCodiData(requestVO);

		int totalCount = 0;
		int offset = 1;
		int count = 20;

		List<Product> productList = new ArrayList<Product>();
		List<Menu> menuList = null;
		List<Support> supportList = null;
		List<Source> sourceList = null;

		Product product = null;
		Identifier identifier = null;
		App app = null;
		Accrual accrual = null;
		Rights rights = null;
		Source source = null;
		Price price = null;
		Title title = null;
		Support support = null;
		Menu menu = null;

		return responseVO;
	}

	private ISFRes getISF_AppCodiData(AppCodiReq requestVO) throws IOException, Exception {

		this.log.debug("getISF_AppCodiData START...... !!");
		this.log.debug(requestVO.toString());

		ISFRes response = new ISFRes();

		HttpHeaders headers = new HttpHeaders();
		// headers.setContentType(MediaType.ALL);
		HttpEntity<ISFReq> requestEntity = new HttpEntity<ISFReq>(this.makeRequestParam(requestVO), headers);

		ResponseEntity<ISFRes> res = this.restTemplate.exchange(this.domainName + "/isf/appCodi/v1", HttpMethod.POST,
				requestEntity, ISFRes.class);

		if (res.getStatusCode().equals(HttpStatus.OK)) {
			response = res.getBody();
		} else {
			this.log.info("ISF 연동 오류~~!!");
		}

		JAXBContext jc = JAXBContext.newInstance(ISFRes.class);
		Marshaller m1 = jc.createMarshaller();
		m1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m1.marshal(response, System.out);

		return response;

	}

	private ISFReq makeRequestParam(AppCodiReq requestVO) {

		ISFReq request = new ISFReq();

		// request.setChCode("M");
		if ("longList".equals(requestVO.getType()))
			request.setId("SVC_CODI_0001");
		else
			request.setId("SVC_MAIN_0003");
		// request.setMbn("test1");
		request.setMbn(requestVO.getMemberNo());
		request.setMdn(requestVO.getMsisdn());
		// request.setMdn("01012345678");

		return request;
	}

}
