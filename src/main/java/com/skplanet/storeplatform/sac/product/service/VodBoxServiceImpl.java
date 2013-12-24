/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.product.service;

import java.io.IOException;
import java.util.ArrayList;
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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Preview;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Store;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.VideoInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Vod;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.VodExplain;
import com.skplanet.storeplatform.sac.client.product.vo.vod.VodBoxListResponse;
import com.skplanet.storeplatform.sac.client.product.vo.vod.VodBoxRequest;

/**
 * MenuList Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 19. Updated by : 윤주영, SK 플래닛.
 */
@Service
public class VodBoxServiceImpl implements VodBoxService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.MenuListService#searchMenuList(String tenantId, String
	 * systemId, String menuId)
	 */
	@Override
	public VodBoxListResponse searchVodBoxList(VodBoxRequest requestVO) throws JsonGenerationException,
			JsonMappingException, IOException, Exception {

		int totalCount = 5;

		String filteredBy; // 차트 구분 코드
		String imageSizeCd; // 이미지 사이즈 코드
		String offset; // 시작점 ROW
		String count; // 페이지당 노출 ROW 수
		String duration; // 기간
		String chapter; // 회차
		String regdate; // 등록일자

		filteredBy = requestVO.getFilteredBy(); // 차트 구분 코드
		imageSizeCd = requestVO.getImageSizeCd(); // 이미지 사이즈 코드
		offset = requestVO.getOffset(); // 시작점 ROW
		count = requestVO.getCount(); // 페이지당 노출 ROW 수
		duration = requestVO.getDuration();
		chapter = requestVO.getChapter();
		regdate = requestVO.getRegdate();

		VodBoxListResponse responseVO = null;
		CommonResponse commonResponse = null;

		if (null == filteredBy || "".equals(filteredBy)) {
			throw new Exception("filteredBy 는 필수 파라메터 입니다.");
		}
		if (null == imageSizeCd || "".equals(imageSizeCd)) {
			throw new Exception("imageSizeCd 는 필수 파라메터 입니다.");
		}

		if (true) {

			// Response VO를 만들기위한 생성자
			List<Product> productList = new ArrayList<Product>();
			List<Menu> menuList = new ArrayList<Menu>();

			for (int i = 1; i <= 5; i++) {
				Product product = new Product();
				Identifier identifier = new Identifier();
				Title title = new Title();
				Menu menu = new Menu();
				Rights rights = new Rights();
				Preview preview = new Preview();
				Source source = new Source();
				Store store = new Store();
				Price price = new Price();

				Vod vod = new Vod();
				VideoInfo videoInfo = new VideoInfo();
				VodExplain vodExplain = new VodExplain();

				Contributor contributor = new Contributor();
				Date date = new Date();

				// 상품ID
				identifier = new Identifier();
				identifier.setType("product" + i);
				identifier.setText("H900067185_" + i);

				/*
				 * TITLE
				 */
				title.setText("VOD 보관함");

				/*
				 * Menu(메뉴정보) Id, Name, Type
				 */
				menu.setId("MenuId11" + i);
				menu.setName("dummyMenuName11" + i);
				menu.setType("dummyMenuType11" + i);
				menuList.add(menu);
				menu = new Menu();
				menu.setId("dummyMenuId111" + i);
				menu.setName("dummyMenuName111" + i);
				menu.setType("dummyMenuType111" + i);
				menuList.add(menu);

				rights.setGrade("1");
				/*
				 * source mediaType, size, type, url
				 */
				source.setMediaType("media_" + i);
				source.setSize("1024_" + i);
				source.setType("thumbNail");
				source.setUrl("http://./4_182_261_130x186.PNG");

				preview.setSource(source);

				rights.setPreview(preview);

				price.setText(0);
				store.setPrice(price);

				/*
				 * Contributor name : 제작자 또는 저자 이름, album : 앨범명
				 */
				contributor.setName("소녀시대");
				contributor.setAlbum("The Great Escape" + i);

				/*
				 * vod runningTime, videoInfo, vodExplain
				 */
				videoInfo.setType("PD002502");
				videoInfo.setPixel("1090");
				videoInfo.setPictureSize("2098");
				videoInfo.setVersion(i + ".0");
				videoInfo.setBtvcid("");
				videoInfo.setSize("1024");
				vod.setVideoInfo(videoInfo);

				date.setText("20131124");
				date.setType("YYYYMMDD");

				vodExplain.setSaleDateInfo("20110331");
				vodExplain.setText("이 vod로 말할 거 같으면... 어쩌구 저쩌구...");
				vod.setVodExplain(vodExplain);

				product = new Product();
				product.setProductExplain("VOD 보관함 설명");
				product.setIdentifier(identifier);
				product.setMenuList(menuList);
				product.setTitle(title);
				product.setContributor(contributor);

				productList.add(product);

			}

			responseVO = new VodBoxListResponse();
			commonResponse = new CommonResponse();
			responseVO.setProductList(productList);
			commonResponse.setTotalCount(totalCount);
			responseVO.setCommonRes(commonResponse);

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
			String json = objectMapper.writeValueAsString(responseVO);

			this.log.debug("test json : {}", json);
			// System.out.println(json);

		}
		return responseVO;

	}

}
