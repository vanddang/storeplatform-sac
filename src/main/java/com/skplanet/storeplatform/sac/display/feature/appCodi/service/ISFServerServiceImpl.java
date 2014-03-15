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

		String PKG_ID = "2772,2771,2736,54,2424,307,2443,2758,2694,2759";
		String PKG_NM = "패키지60,패키지100,강성근패키지,버스 지하철 어디쯤 오고있나?,정말 심심할때? 킬링타임 App,내일 날씨를 말씀 드리겠습니다,우리 오늘 어디서 만날까?,바쁜 나의 일정관리,해외여행 시 꼭 필요한 앱,부작용 없는 수면 도우미";
		String PKG_ORD = "1,2,3,4,5,6,7,8,9,10";

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
			/*
			 * single.setName("recId"); single.setValue("IW142335067932014011611260920140116134738791");
			 * single.setName("reason"); single.setValue("요즘 인기있는 테마 추천");
			 */
			singles.addSingleValue(single);
			prop.setSingleValues(singles);

			multis.setCount(10);

			// int i = 1;

			Iterator<String> pit = pkgIdList.iterator();
			Iterator<String> pmit = pkgNmList.iterator();
			Iterator<String> oit = pkgordList.iterator();
			while (pit.hasNext()) {

				/*
				 * if (i >= 5) break;
				 */
				multi.setId(pit.next());
				multi.setName(pmit.next());
				multi.setOrder(Integer.valueOf(oit.next()));
				multis.addMultiValue(multi);
				multi = new MultiValueType();

				// i++;

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

		/*
		 * xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>") .append(
		 * "<ns2:response xmlns=\"http://isf.sktelecom.com/schema/tstore-response\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://isf.sktelecom.com/schema/tstore-request.xsd\">"
		 * ) .append("    <ns2:service id=\"SVC_MAIN_0003\" chCode=\"M\"/>").append("    <ns2:mbn>test1</ns2:mbn>")
		 * .append("    <ns2:mdn>01012345678</mdn>").append("    <ns2:props count=\"2\">")
		 * .append("        <ns2:singleValues count=\"0\">")
		 * .append("            <ns2:singleValue name=\"recId\">000000000001</ns2:singleValue>")
		 * .append("        </ns2:singleValues>").append("        <ns2:multiValues count=\"4\">")
		 * .append("            <ns2:multiValue id=\"1234\" score=\"10\" reasonCode=\"01\" relId=\"3576\"/>")
		 * .append("            <ns2:multiValue id=\"5678\" score=\"50\" reasonCode=\"08\" relId=\"8856\"/>")
		 * .append("            <ns2:multiValue id=\"9625\" score=\"90\" reasonCode=\"06\" relId=\"7474\"/>")
		 * .append("            <ns2:multiValue id=\"9628\" score=\"90\" reasonCode=\"06\" relId=\"7474\"/>")
		 * .append("        </ns2:multiValues>").append("    </ns2:props>").append("</ns2:response>");
		 */
		xml.append("%3C%3Fxml+version%3D%221.0%22+encoding%3D%22UTF-8%22+standalone%3D%22yes%22%3F%3E%0A%3Cns2%3Aresponse+xmlns%3Ans2%3D%22http%3A%2F%2Fisf.sktelecom.com%2Fschema%2Ftstore-response%22%3E%0A++++%3Cns2%3Aservice+chCode%3D%22M%22+id%3D%22SVC_CODI_0001%22%2F%3E%0A++++%3Cns2%3Ambn%3EIF10210000422009061700203625%3C%2Fns2%3Ambn%3E%0A++++%3Cns2%3Amdn%3E01028340366%3C%2Fns2%3Amdn%3E%0A++++%3Cns2%3Aprops+count%3D%222%22%3E%0A++++++++%3Cns2%3AsingleValues+count%3D%221%22%3E%0A++++++++++++%3CsingleValue+name%3D%22recId%22%3EIF1021000042200906170020362520140220194842309%3C%2FsingleValue%3E%0A++++++++%3C%2Fns2%3AsingleValues%3E%0A++++++++%3Cns2%3AmultiValues+count%3D%22100%22%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%220000368399%22+reasonCode%3D%222091%22+score%3D%2243%22+id%3D%220000412736%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22H001247194%22+reasonCode%3D%222017%22+score%3D%2240%22+id%3D%22H001378966%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%220000368399%22+reasonCode%3D%222091%22+score%3D%2237%22+id%3D%220000050680%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%220000412402%22+reasonCode%3D%222091%22+score%3D%2236%22+id%3D%220000648350%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%220000140580%22+reasonCode%3D%222091%22+score%3D%2241%22+id%3D%220000315771%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%220000412402%22+reasonCode%3D%222091%22+score%3D%2230%22+id%3D%220000311121%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%220000024129%22+reasonCode%3D%222091%22+score%3D%2230%22+id%3D%220000342087%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%220000412070%22+reasonCode%3D%222091%22+score%3D%2230%22+id%3D%220000405600%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%220000035323%22+reasonCode%3D%222091%22+score%3D%2238%22+id%3D%220000298454%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%222091%22+score%3D%2237%22+id%3D%220000645732%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22H001247194%22+reasonCode%3D%222017%22+score%3D%2234%22+id%3D%22H001163507%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%220000313664%22+reasonCode%3D%222091%22+score%3D%2230%22+id%3D%220000367986%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%220000412070%22+reasonCode%3D%222091%22+score%3D%2230%22+id%3D%220000375519%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%220000140580%22+reasonCode%3D%222091%22+score%3D%2230%22+id%3D%220000057600%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%220000412402%22+reasonCode%3D%222091%22+score%3D%2229%22+id%3D%220000646667%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22H001247194%22+reasonCode%3D%222017%22+score%3D%2230%22+id%3D%22H001169567%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%220000024129%22+reasonCode%3D%222091%22+score%3D%2235%22+id%3D%220000117501%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%220000412070%22+reasonCode%3D%222091%22+score%3D%2235%22+id%3D%220000319945%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%220000412070%22+reasonCode%3D%222091%22+score%3D%2230%22+id%3D%220000268985%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%220000368399%22+reasonCode%3D%222091%22+score%3D%2232%22+id%3D%220000361598%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%222091%22+score%3D%2225%22+id%3D%220000406798%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%220000410321%22+reasonCode%3D%222091%22+score%3D%2230%22+id%3D%220000269978%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%222091%22+score%3D%2230%22+id%3D%220000412161%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229214%22+score%3D%2224%22+id%3D%22H900035844%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%220000140580%22+reasonCode%3D%222091%22+score%3D%2230%22+id%3D%220000365378%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%220000412402%22+reasonCode%3D%222091%22+score%3D%2230%22+id%3D%220000414026%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%220000265213%22+reasonCode%3D%222091%22+score%3D%2230%22+id%3D%220000025711%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229214%22+score%3D%2231%22+id%3D%22H001395440%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%220000412402%22+reasonCode%3D%222091%22+score%3D%2225%22+id%3D%220000302629%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%220000035323%22+reasonCode%3D%222091%22+score%3D%2230%22+id%3D%220000290353%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22H001247194%22+reasonCode%3D%222017%22+score%3D%2230%22+id%3D%22H001424656%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229218%22+score%3D%2222%22+id%3D%22H000455303%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%222091%22+score%3D%2222%22+id%3D%220000643246%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22H001247194%22+reasonCode%3D%222017%22+score%3D%2228%22+id%3D%22H001287923%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229214%22+score%3D%2223%22+id%3D%22H001429151%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229214%22+score%3D%2225%22+id%3D%22H900189242%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%222091%22+score%3D%2230%22+id%3D%220000384020%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%222091%22+score%3D%2230%22+id%3D%220000277336%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229216%22+score%3D%2220%22+id%3D%22H001528964%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229217%22+score%3D%2222%22+id%3D%22H000500285%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229218%22+score%3D%2225%22+id%3D%22H000451924%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229213%22+score%3D%2222%22+id%3D%22H900218195%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%222091%22+score%3D%2226%22+id%3D%220000311077%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%222091%22+score%3D%2221%22+id%3D%220000364701%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%222091%22+score%3D%2224%22+id%3D%220000406878%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%222091%22+score%3D%2224%22+id%3D%220000299844%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229218%22+score%3D%2224%22+id%3D%22H001496030%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229217%22+score%3D%2223%22+id%3D%22H000404813%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2219%22+id%3D%220000311999%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%220000412402%22+reasonCode%3D%222091%22+score%3D%2220%22+id%3D%220000412980%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2220%22+id%3D%220000329718%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2217%22+id%3D%220000315875%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229217%22+score%3D%2221%22+id%3D%22H001282855%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2217%22+id%3D%220000308985%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229214%22+score%3D%2223%22+id%3D%22H001411196%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2218%22+id%3D%220000413175%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229213%22+score%3D%2224%22+id%3D%22H900395409%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2217%22+id%3D%220000228841%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2216%22+id%3D%220000298090%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229218%22+score%3D%2223%22+id%3D%22H001539415%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2218%22+id%3D%220000098302%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229213%22+score%3D%2223%22+id%3D%22H900400358%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2216%22+id%3D%220000367878%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2216%22+id%3D%220000414858%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229214%22+score%3D%2221%22+id%3D%22H900322285%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2215%22+id%3D%220000644245%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229217%22+score%3D%2221%22+id%3D%22H900772293%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229213%22+score%3D%2222%22+id%3D%22H900316919%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%222091%22+score%3D%2222%22+id%3D%220000414517%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229218%22+score%3D%2222%22+id%3D%22H000445970%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229214%22+score%3D%2222%22+id%3D%22H001412266%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2216%22+id%3D%220000025288%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2216%22+id%3D%220000302442%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2216%22+id%3D%220000090861%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2217%22+id%3D%220000647942%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2219%22+id%3D%220000035500%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2218%22+id%3D%220000644476%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%222091%22+score%3D%2223%22+id%3D%220000082220%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2217%22+id%3D%220000256609%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2217%22+id%3D%220000281454%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2216%22+id%3D%220000029079%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2215%22+id%3D%220000649078%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2216%22+id%3D%220000413197%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2216%22+id%3D%220000270098%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2216%22+id%3D%220000319294%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2216%22+id%3D%220000297453%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2217%22+id%3D%220000025013%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229217%22+score%3D%2221%22+id%3D%22H001128533%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2216%22+id%3D%220000411777%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2215%22+id%3D%220000649676%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2215%22+id%3D%220000352518%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2219%22+id%3D%220000275511%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2218%22+id%3D%220000647786%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2217%22+id%3D%220000296439%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2218%22+id%3D%220000307384%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2218%22+id%3D%220000364378%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2215%22+id%3D%220000368258%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2216%22+id%3D%220000646718%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2216%22+id%3D%220000068921%22%2F%3E%0A++++++++++++%3Cns2%3AmultiValue+relId%3D%22%22+reasonCode%3D%229004%22+score%3D%2215%22+id%3D%220000107400%22%2F%3E%0A++++++++%3C%2Fns2%3AmultiValues%3E%0A++++%3C%2Fns2%3Aprops%3E%0A%3C%2Fns2%3Aresponse%3E%0A");

		return xml.toString();
	}
}
