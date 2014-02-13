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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.StringUtils;
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
			multi.setId("0000256625");
			multi.setScore(10);
			multi.setReasonCode("01");
			multi.setRelId("0000266015");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000256708");
			multi.setScore(50);
			multi.setReasonCode("02");
			multi.setRelId("0000266071");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000025013");
			multi.setScore(90);
			multi.setReasonCode("03");
			multi.setRelId("0000256771");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000028070");
			multi.setScore(90);
			multi.setReasonCode("04");
			multi.setRelId("0000257158");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000029096");
			multi.setScore(90);
			multi.setReasonCode("1191");
			multi.setRelId("0000257655");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000029241");
			multi.setScore(90);
			multi.setReasonCode("1191");
			multi.setRelId("0000258003");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000029677");
			multi.setScore(90);
			multi.setReasonCode("1191");
			multi.setRelId("0000258031");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000029727");
			multi.setScore(90);
			multi.setReasonCode("1191");
			multi.setRelId("0000258193");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000033523");
			multi.setScore(90);
			multi.setReasonCode("1191");
			multi.setRelId("0000258263");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000033534");
			multi.setScore(90);
			multi.setReasonCode("2191");
			multi.setRelId("8856");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000033972");
			multi.setScore(90);
			multi.setReasonCode("2191");
			multi.setRelId("0000258335");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000035676");
			multi.setScore(90);
			multi.setReasonCode("2191");
			multi.setRelId("0000258420");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000035937");
			multi.setScore(90);
			multi.setReasonCode("2191");
			multi.setRelId("0000258524");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000039768");
			multi.setScore(90);
			multi.setReasonCode("2191");
			multi.setRelId("0000259193");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000039810");
			multi.setScore(90);
			multi.setReasonCode("2191");
			multi.setRelId("0000259418");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000043782");
			multi.setScore(90);
			multi.setReasonCode("2191");
			multi.setRelId("0000259756");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000044486");
			multi.setScore(90);
			multi.setReasonCode("2191");
			multi.setRelId("0000260475");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000044488");
			multi.setScore(90);
			multi.setReasonCode("9299");
			multi.setRelId("0000260476");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000044622");
			multi.setScore(90);
			multi.setReasonCode("9299");
			multi.setRelId("0000261102");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000044629");
			multi.setScore(90);
			multi.setReasonCode("9299");
			multi.setRelId("0000261877");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000044636");
			multi.setScore(90);
			multi.setReasonCode("9299");
			multi.setRelId("0000262173");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000044665");
			multi.setScore(90);
			multi.setReasonCode("9299");
			multi.setRelId("0000262572");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000044668");
			multi.setScore(90);
			multi.setReasonCode("9299");
			multi.setRelId("0000262790");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000044674");
			multi.setScore(90);
			multi.setReasonCode("9299");
			multi.setRelId("0000263957");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000044717");
			multi.setScore(90);
			multi.setReasonCode("9299");
			multi.setRelId("0000264085");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000044733");
			multi.setScore(90);
			multi.setReasonCode("9299");
			multi.setRelId("0000264220");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000044745");
			multi.setScore(90);
			multi.setReasonCode("9299");
			multi.setRelId("0000264264");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000044756");
			multi.setScore(90);
			multi.setReasonCode("9299");
			multi.setRelId("0000264699");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000044786");
			multi.setScore(90);
			multi.setReasonCode("9299");
			multi.setRelId("0000264849");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000044788");
			multi.setScore(90);
			multi.setReasonCode("9299");
			multi.setRelId("0000265203");
			multis.addMultiValue(multi);

			multi = new MultiValueType();
			multi.setId("0000044793");
			multi.setScore(90);
			multi.setReasonCode("9299");
			multi.setRelId("0000265223");
			multis.addMultiValue(multi);

			prop.setMultiValues(multis);
		}
		response.setProps(prop);

		if (this.log.isDebugEnabled()) {
			JAXBContext jc = JAXBContext.newInstance(Response.class);
			Marshaller m1 = jc.createMarshaller();
			m1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m1.marshal(response, System.out);
		}

		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.CategoryServiceImpl#searchTopCategoryList(MenuReq
	 * requestVO)
	 */
	@Override
	public Response makeThemeRecommendList(Request request) throws IOException, Exception {

		this.log.debug("ISFServerServiceImpl.makeAppCodiList start !!");
		this.log.debug("request {}", request);

		String PKG_ID = "227,243,302,54,26,81,85,72,282,27";
		String PKG_NM = "패키지60,패키지100,강성근패키지,버스 지하철 어디쯤 오고있나?,정말 심심할때? 킬링타임 App,내일 날씨를 말씀 드리겠습니다,우리 오늘 어디서 만날까?,바쁜 나의 일정관리,해외여행 시 꼭 필요한 앱,부작용 없는 수면 도우미";
		String PKG_ORD = "2,3,3,4,2,1,5,7,9,8";

		List<String> pkgIdList = Arrays.asList(StringUtils.split(PKG_ID, ","));
		List<String> pkgNmList = Arrays.asList(StringUtils.split(PKG_NM, ","));
		List<String> pkgordList = Arrays.asList(StringUtils.split(PKG_ORD, ","));

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

		if ("SVC_MAIN_0002".equals(request.getService().getId())) {
			prop.setCount(2);
			singles.setCount(2);
			single.setName("recId");
			single.setValue("000000000002");
			singles.addSingleValue(single);
			single.setName("reason");
			single.setValue("20대 여성 / 생활/위치 위주 구매 /가입 후 6개월이 경과 되셨습니다.");
			singles.addSingleValue(single);
			prop.setSingleValues(singles);

			multis.setCount(pkgIdList.size());

			Iterator<String> pit = pkgIdList.iterator();
			Iterator<String> pmit = pkgNmList.iterator();
			while (pit.hasNext()) {
				multi.setId(pit.next());
				multi.setName(pmit.next());
				multis.addMultiValue(multi);
				multi = new MultiValueType();
			}

			prop.setMultiValues(multis);
		} else { // SVC_MAIN_0004
			prop.setCount(2);
			singles.setCount(2);
			single.setName("recId");
			single.setValue("IW142335067932014011611260920140116134738791");
			single.setName("reason");
			single.setValue("요즘 인기있는 테마 추천");
			singles.addSingleValue(single);
			prop.setSingleValues(singles);

			multis.setCount(4);

			int i = 1;

			Iterator<String> pit = pkgIdList.iterator();
			Iterator<String> pmit = pkgNmList.iterator();
			Iterator<String> oit = pkgordList.iterator();
			while (pit.hasNext()) {

				if (i >= 5)
					break;

				multi.setId(pit.next());
				multi.setName(pmit.next());
				multi.setOrder(Integer.valueOf(oit.next()));
				multis.addMultiValue(multi);
				multi = new MultiValueType();

				i++;

			}

			prop.setMultiValues(multis);
		}
		response.setProps(prop);

		if (this.log.isDebugEnabled()) {
			JAXBContext jc = JAXBContext.newInstance(Response.class);
			Marshaller m1 = jc.createMarshaller();
			m1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m1.marshal(response, System.out);
		}

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
