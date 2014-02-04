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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.display.feature.appCodi.vo.MultiValueType;
import com.skplanet.storeplatform.sac.display.feature.appCodi.vo.MultiValuesType;
import com.skplanet.storeplatform.sac.display.feature.appCodi.vo.PropsType;
import com.skplanet.storeplatform.sac.display.feature.appCodi.vo.Request;
import com.skplanet.storeplatform.sac.display.feature.appCodi.vo.Response;
import com.skplanet.storeplatform.sac.display.feature.appCodi.vo.SingleValueType;
import com.skplanet.storeplatform.sac.display.feature.appCodi.vo.SingleValuesType;

/**
 * ISF SERVER Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 01. 28. Updated by : 윤주영, SK 플래닛.
 */
@Service
public class ISFServerServiceImpl implements ISFServerService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.CategoryServiceImpl#searchTopCategoryList(MenuReq
	 * requestVO)
	 */
	@Override
	public Response makeAppCodiList(Request request) throws IOException, Exception {

		this.log.debug("ISFServerServiceImpl.makeAppCodiList start !!");
		this.log.debug("request {}", request);

		Response response = new Response();
		Response.Service service = new Response.Service();
		PropsType prop = new PropsType();
		SingleValuesType singles = new SingleValuesType();
		SingleValueType single = new SingleValueType();
		MultiValuesType multis = new MultiValuesType();
		MultiValueType multi = new MultiValueType();

		response.setMbn(request.getMbn());
		response.setMdn(request.getMdn());
		service.setChCode(request.getService().getChCode());
		service.setId(request.getService().getId());
		response.setService(service);

		if ("SVC_MAIN_0003".equals(request.getService().getId())) {
			prop.setCount(2);
			singles.setCount(1);
			single.setName("recId");
			single.setValue("000000000001");
			singles.addSingleValue(single);
			prop.setSingleValues(singles);

			multis.setCount(4);
			multi.setId("1234");
			multi.setScore(10);
			multi.setReasonCode("01");
			multi.setRelId("3576");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("5678");
			multi.setScore(50);
			multi.setReasonCode("08");
			multi.setRelId("8856");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("9625");
			multi.setScore(90);
			multi.setReasonCode("06");
			multi.setRelId("7474");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("9628");
			multi.setScore(90);
			multi.setReasonCode("06");
			multi.setRelId("7474");
			multis.addMultiValue(multi);
			prop.setMultiValues(multis);
		} else { // SVC_CODI_0001
			prop.setCount(2);
			singles.setCount(1);
			single.setName("recId");
			single.setValue("000000000001");
			singles.addSingleValue(single);
			prop.setSingleValues(singles);

			multis.setCount(3);
			multi.setId("1234");
			multi.setScore(10);
			multi.setReasonCode("01");
			multi.setRelId("3576");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("5678");
			multi.setScore(50);
			multi.setReasonCode("08");
			multi.setRelId("8856");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("9625");
			multi.setScore(90);
			multi.setReasonCode("06");
			multi.setRelId("7474");
			multis.addMultiValue(multi);
			prop.setMultiValues(multis);
		}
		response.setProps(prop);

		JAXBContext jc = JAXBContext.newInstance(Response.class);
		Marshaller m1 = jc.createMarshaller();
		m1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m1.marshal(response, System.out);
		// this.log.debug("response {}", response);

		return response;
	}

	@Override
	public String makeAppCodiXML() {
		StringBuffer xml = new StringBuffer();

		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
				.append("<ns2:response xmlns=\"http://isf.sktelecom.com/schema/tstore-response\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://isf.sktelecom.com/schema/tstore-request.xsd\">")
				.append("    <ns2:service id=\"SVC_MAIN_0003\" chCode=\"M\"/>").append("    <ns2:mbn>test1</ns2:mbn>")
				.append("    <ns2:mdn>01012345678</mdn>").append("    <ns2:props count=\"2\">")
				.append("        <ns2:singleValues count=\"0\">")
				.append("            <ns2:singleValue name=\"recId\">000000000001</ns2:singleValue>")
				.append("        </ns2:singleValues>").append("        <ns2:multiValues count=\"4\">")
				.append("            <ns2:multiValue id=\"1234\" score=\"10\" reasonCode=\"01\" relId=\"3576\"/>")
				.append("            <ns2:multiValue id=\"5678\" score=\"50\" reasonCode=\"08\" relId=\"8856\"/>")
				.append("            <ns2:multiValue id=\"9625\" score=\"90\" reasonCode=\"06\" relId=\"7474\"/>")
				.append("            <ns2:multiValue id=\"9628\" score=\"90\" reasonCode=\"06\" relId=\"7474\"/>")
				.append("        </ns2:multiValues>").append("    </ns2:props>").append("</ns2:response>");
		return xml.toString();
	}
}
