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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.skplanet.storeplatform.external.client.isf.vo.MultiValueType;
import com.skplanet.storeplatform.external.client.isf.vo.MultiValuesType;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
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
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;

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

		Map<String, Object> mapReq = new HashMap<String, Object>();

		// 상품 아이디
		String sPid = "";
		// 추천 사유 코드
		String sReasonCode = "";
		// 연관 상품 아이디
		String sRelId = "";

		// 앱코디 상품 리스트 : DB 조회 후 결과를 저장
		List<String> listProdParam = new ArrayList<String>();
		// 연관상품 리스트 : DB 조회 후 결과를 저장
		List<String> listRelProdParam = new ArrayList<String>();

		// 추천 사유 저장 : 상품 아이디 + 추천 사유 코드
		Map<String, String> mapReason = new HashMap<String, String>();
		// 연관 상품 저장 : 상품 아이디 + 연관 상품 아이디
		Map<String, String> mapRelProd = new HashMap<String, String>();
		// ISF에서 내려준 리스트 순서 저장 : 상품 아이디 + 순서
		Map<String, Integer> mapRank = new HashMap<String, Integer>();
		// 순서 체크용 임시 변수 - 나중에 최종 개수로 사용함
		int idx = 0;

		TenantHeader tenantHeader = requestHeader.getTenantHeader();
		DeviceHeader deviceHeader = requestHeader.getDeviceHeader();

		AppCodiListRes responseVO = new AppCodiListRes();

		CommonResponse commonResponse = new CommonResponse();

		ISFRes response = this.getISF_AppCodiData(requestVO);

		int multiCount = response.getProps().getMultiValues().getCount();

		if (multiCount > 0) {
			mapReq.put("MDN", response.getMdn()); // 모번호 세팅

			MultiValuesType multis = new MultiValuesType();
			MultiValueType multi = new MultiValueType();

			multis = response.getProps().getMultiValues();
			Iterator<MultiValueType> siterator = multis.getMultiValue().iterator();
			while (siterator.hasNext()) {
				multi = siterator.next();

				sPid = multi.getId();
				sReasonCode = multi.getReasonCode();
				sRelId = multi.getRelId();

				// PID 리스트 저장
				listProdParam.add(sPid);
				// PID 리스트 순서 저장
				mapRank.put(sPid, idx);
				idx++;
				// PID 별 추천사유 저장
				mapReason.put(sPid, sReasonCode);

				// 연관 상품 정보가 있을 경우에 연관 상품 정보 저장
				if (StringUtils.isNotEmpty(sRelId)) {
					// 연관 상품 PID 저장 : DB 조회용
					listRelProdParam.add(sRelId);
					// PID - 연관상품 ID 매핑 관리
					mapRelProd.put(sPid, sRelId);
				}
			}

			// 앱코디 상품 리스트 조회
			mapReq.put("PID_LIST", listProdParam);
			// List<Object> resultList = this.commonDAO.queryForList("isf.getAppCodi", mapReq, List.class);
		}

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
		if ("long".equals(requestVO.getFilteredBy()))
			request.setId("SVC_CODI_0001");
		else
			request.setId("SVC_MAIN_0003");
		// request.setMbn("test1");
		request.setMbn(requestVO.getMemberNo());
		request.setMdn(requestVO.getMsisdn());
		request.setType(requestVO.getFilteredBy());
		// request.setMdn("01012345678");

		this.log.debug(request.toString());

		return request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.CategoryServiceImpl#searchTopCategoryList(MenuReq
	 * requestVO)
	 */
	@Override
	public AppCodiListRes searchDummyAppCodiList(AppCodiReq requestVO, SacRequestHeader requestHeader) {

		AppCodiListRes response = new AppCodiListRes();
		CommonResponse commonResponse = null;
		List<Product> listVO = new ArrayList<Product>();

		Product product;
		Identifier identifier;
		Title title;
		App app;
		Accrual accrual;
		Rights rights;
		Source source;
		Price price;
		Menu menu;

		// Response VO를 만들기위한 생성자
		List<Menu> menuList;
		List<Source> sourceList;
		List<Support> supportList;
		List<Identifier> identifierList;

		product = new Product();
		identifier = new Identifier();
		title = new Title();
		app = new App();
		accrual = new Accrual();
		rights = new Rights();
		source = new Source();
		price = new Price();

		// 상품ID
		identifier = new Identifier();

		// Response VO를 만들기위한 생성자
		menuList = new ArrayList<Menu>();
		sourceList = new ArrayList<Source>();
		supportList = new ArrayList<Support>();
		identifierList = new ArrayList<Identifier>();

		identifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
		identifier.setText("0000648339");
		title.setText("뮤 더 제네시스 for Kakao");

		menu = new Menu();
		menu.setId("DP01");
		menu.setName("GAME");
		menu.setType("topClass");
		menuList.add(menu);
		menu = new Menu();
		menu.setId("DP01004");
		menu.setName("RPG");
		// menu.setType("");
		menuList.add(menu);

		app.setAid("OA00648339");
		app.setPackageName("com.webzenm.mtg4kakao");
		app.setVersionCode("11");
		app.setVersion("1.1");
		product.setApp(app);

		accrual.setVoterCount(1519);
		accrual.setDownloadCount(31410);
		accrual.setScore(4.5);

		/*
		 * Rights grade
		 */
		rights.setGrade("0");

		source.setMediaType("image/png");
		source.setSize(0);
		source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
		source.setUrl("http://wap.tstore.co.kr/android6/201312/04/IF1423502835320131125163752/0000648339/img/thumbnail/0000648339_130_130_0_91_20131204195212.PNG");
		sourceList.add(source);

		/*
		 * Price text
		 */
		price.setText(0);

		identifierList.add(identifier);
		product.setIdentifierList(identifierList);

		product.setTitle(title);
		product.setSupportList(supportList);
		product.setMenuList(menuList);

		product.setAccrual(accrual);
		product.setRights(rights);
		product.setProductExplain("뮤 10년의 역사가 모바일로 펼쳐진다!");
		product.setRecommendedReason("우리 또래의 인기 FUN 앱");
		product.setSourceList(sourceList);
		product.setPrice(price);

		listVO.add(product);

		commonResponse = new CommonResponse();
		commonResponse.setTotalCount(1);

		response.setCommonRes(commonResponse);
		response.setProductList(listVO);

		return response;
	}
}
