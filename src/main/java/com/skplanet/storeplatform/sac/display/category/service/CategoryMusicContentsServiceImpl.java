/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.category.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.music.MusicContentsListRes;
import com.skplanet.storeplatform.sac.client.display.vo.music.MusicContentsReq;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Music;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.category.vo.MusicContentsDTO;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;

/**
 * Music Contents Service 인터페이스(CoreStoreBusiness)
 * 
 * Updated on : 2013. 12. 24. Updated by : 윤주영, SK 플래닛.
 */
@Service
public class CategoryMusicContentsServiceImpl implements CategoryMusicContentsService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.MusicContentsListService#searchMusicContentsList(
	 * MusicContentsRequest requestVO)
	 */
	@Override
	public MusicContentsListRes searchMusicContentsList(MusicContentsReq requestVO, SacRequestHeader requestHeader)
			throws JsonGenerationException, JsonMappingException, IOException, Exception {

		this.log.debug("searchMusicContentsList start !!");

		int totalCount = 0;

		String filteredBy; // 차트 구분 코드
		String purchase;
		String orderedBy;
		String menuId;
		String imageCd; // 이미지 사이즈 코드
		String langCd;
		String deviceModelCd;
		String tenantId;
		int offset; // 시작점 ROW
		int count; // 페이지당 노출 ROW 수

		filteredBy = requestVO.getFilteredBy(); // 차트 구분 코드
		purchase = requestVO.getPurchase();
		orderedBy = requestVO.getOrderedBy();
		langCd = requestVO.getLangCd();
		menuId = requestVO.getMenuId();
		imageCd = requestVO.getImageCd(); // dpi 코드를 받아서 이미지 사이즈 코드를 알아내는 로직 추가 되어야 함.
		deviceModelCd = requestHeader.getDeviceHeader().getModel();
		tenantId = requestHeader.getTenantHeader().getTenantId();
		offset = requestVO.getOffset(); // 시작점 ROW
		count = requestVO.getCount(); // 페이지당 노출 ROW 수

		requestVO.setDeviceModelCd(deviceModelCd);
		requestVO.setTenantId(tenantId);

		this.log.debug("offset : " + offset);
		this.log.debug("count : " + count);

		MusicContentsListRes responseVO = null;
		CommonResponse commonResponse = null;

		if (null == filteredBy || "".equals(filteredBy)) {
			throw new Exception("filteredBy 는 필수 파라메터 입니다.");
		}
		if (null == purchase || "".equals(purchase)) {
			// throw new Exception("purchase 는 필수 파라메터 입니다.");
		}
		if (null == tenantId || "".equals(tenantId)) {
			requestVO.setTenantId("S01");
			// throw new Exception("tenantId 는 필수 파라메터 입니다.");
		}
		if (null == deviceModelCd || "".equals(deviceModelCd)) {
			requestVO.setDeviceModelCd("SHW-M250S");
		}
		if (null == langCd || "".equals(langCd)) {
			requestVO.setLangCd("ko");
		}
		if (null == menuId || "".equals(menuId)) {
			menuId = "DP16";
			requestVO.setMenuId(menuId);
		}
		if (null == imageCd || "".equals(imageCd)) {
			imageCd = "DP000191";
			requestVO.setImageCd(imageCd);
		}
		// rownum 디폴트값 세팅
		if (offset <= 0) {
			offset = 1;
			requestVO.setOffset(offset);
		}
		if (count <= 0) {
			count = 100;
			requestVO.setCount(count);
		}
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);

		List<Product> productList = new ArrayList<Product>();

		List<MusicContentsDTO> resultList = this.commonDAO.queryForList(this.getStatementId(requestVO), requestVO,
				MusicContentsDTO.class);
		if (resultList != null) {

			Iterator<MusicContentsDTO> iterator = resultList.iterator();
			while (iterator.hasNext()) {
				MusicContentsDTO mapperVO = iterator.next();

				totalCount = mapperVO.getTotalCount();

				// Response VO를 만들기위한 생성자
				List<Menu> menuList = new ArrayList<Menu>();
				List<Source> sourceList = new ArrayList<Source>();
				List<com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service> serviceList = new ArrayList<com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service>();
				Music music = new Music();

				Product product = new Product();
				Identifier identifier = new Identifier();
				// App app = new App();
				Accrual accrual = new Accrual();
				// Rights rights = new Rights();
				Source source = new Source();
				Price price = new Price();
				Title title = new Title();
				Contributor contributor = new Contributor();
				com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service service = new com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service();

				product.setId(mapperVO.getEpsdProdId()); // 에피소드 상품 ID

				// 상품ID
				identifier = new Identifier();
				identifier.setType("channel"); // 채널 상품 ID
				identifier.setText(mapperVO.getChnlProdId());

				/*
				 * Menu(메뉴정보) Id, Name, Type
				 */
				Menu menu = new Menu();
				menu.setId(mapperVO.getTopMenuId());
				menu.setName(mapperVO.getTopMenuNm());
				menu.setType("topClass");
				menuList.add(menu);
				menu = new Menu();
				menu.setId(mapperVO.getMenuId());
				menu.setName(mapperVO.getMenuNm());
				menu.setType("");
				menuList.add(menu);

				/*
				 * TITLE
				 */
				title.setText(mapperVO.getProdNm());

				/*
				 * source mediaType, size, type, url
				 */
				source.setMediaType(DisplayCommonUtil.getMimeType(mapperVO.getFilePos()));
				source.setSize(Integer.toString(mapperVO.getFileSize()));
				source.setType("thumbNail");
				source.setUrl(mapperVO.getFilePos());
				sourceList.add(source);

				/*
				 * Accrual changeRank 변동 순위, 하락은 음수로 표현한다.
				 */
				accrual.setChangeRank(mapperVO.getRankChgCnt());

				/*
				 * Contributor name : 제작자 또는 저자 이름 album : 앨범명
				 */
				contributor.setName(mapperVO.getArtist1Nm()); // 가수
				contributor.setAlbum(mapperVO.getArtist3Nm()); // 앨범명
				contributor.setPublisher(mapperVO.getChnlCompNm()); // 발행인
				contributor.setAgency(mapperVO.getAgencyNm()); // 에이전시

				/*
				 * music
				 */
				service.setName("mp3");
				service.setType(mapperVO.getMp3Sprt());
				serviceList.add(service);

				service = new com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service();
				service.setName("bell");
				service.setType(mapperVO.getBellSprt());
				serviceList.add(service);

				service = new com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service();
				service.setName("ring");
				service.setType(mapperVO.getRingSprt());
				serviceList.add(service);
				music.setServiceList(serviceList);

				/*
				 * price
				 */
				price.setText(Integer.valueOf(mapperVO.getProdAmt()));

				product.setIdentifier(identifier);
				product.setMenuList(menuList);
				product.setAccrual(accrual);
				product.setTitle(title);
				product.setSourceList(sourceList);
				product.setContributor(contributor);
				product.setMusic(music);

				productList.add(product);

			}

			responseVO = new MusicContentsListRes();
			commonResponse = new CommonResponse();
			responseVO.setProductList(productList);
			commonResponse.setTotalCount(totalCount);
			responseVO.setCommonRes(commonResponse);

			String json = objectMapper.writeValueAsString(responseVO);

			this.log.debug("test json : {}", json);
			// System.out.println(json);

		}
		return responseVO;
	}

	private String getStatementId(MusicContentsReq requestVO) {

		String statementId = "";
		String filteredBy = requestVO.getFilteredBy();

		if ("top".equals(filteredBy) || "recent".equals(filteredBy)) { // TOP 뮤직/ 최신순
			statementId = "MusicMain.getMusicMainList";
		} else { // 그외 APP
			statementId = "MusicMain.getMusicMainDummyList";
		}
		return statementId;
	}
}
