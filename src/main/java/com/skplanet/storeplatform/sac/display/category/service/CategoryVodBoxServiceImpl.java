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
import com.skplanet.storeplatform.sac.client.display.vo.vod.VodBoxListRes;
import com.skplanet.storeplatform.sac.client.display.vo.vod.VodBoxReq;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Time;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Preview;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Store;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.VideoInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Vod;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.VodExplain;

/**
 * Category Vod Box Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2013. 12. 19. Updated by : 윤주영, SK 플래닛.
 */
@Service
public class CategoryVodBoxServiceImpl implements CategoryVodBoxService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.category.service.CategoryVodBoxService#searchVodBoxList(VodBoxRequest
	 * requestVO)
	 */
	@Override
	public VodBoxListRes searchVodBoxList(VodBoxReq requestVO) throws JsonGenerationException, JsonMappingException,
			IOException, Exception {

		int totalCount = 1;

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

		VodBoxListRes responseVO = null;
		CommonResponse commonResponse = null;

		if (null == filteredBy || "".equals(filteredBy)) {
			throw new Exception("filteredBy 는 필수 파라메터 입니다.");
		}
		// if (null == imageSizeCd || "".equals(imageSizeCd)) {
		// throw new Exception("imageSizeCd 는 필수 파라메터 입니다.");
		// }

		if (true) {

			// Response VO를 만들기위한 생성자
			List<Product> productList = new ArrayList<Product>();
			List<Menu> menuList = new ArrayList<Menu>();

			// for (int i = 1; i <= 5; i++) {
			Product product = new Product();
			Identifier identifier = new Identifier();
			Title title = new Title();
			Menu menu = new Menu();
			Rights rights = new Rights();
			Preview preview = new Preview();
			List<Source> sourceList = new ArrayList<Source>();
			Source source = new Source();
			Store store = new Store();
			Price price = new Price();

			Vod vod = new Vod();
			Time runningTime = new Time();
			VideoInfo videoInfo = new VideoInfo();
			VodExplain vodExplain = new VodExplain();

			Contributor contributor = new Contributor();
			Date date = new Date();

			// 상품ID
			identifier = new Identifier();
			identifier.setType("btv");
			identifier.setText("0001143511");

			/*
			 * TITLE
			 */
			title.setText("구가의 서");

			/*
			 * Menu(메뉴정보) Id, Name, Type
			 */
			menu.setId("MN000518");
			menu.setName("방송");
			menu.setType("topCategory");
			menuList.add(menu);
			menu = new Menu();
			menu.setId("MN18001");
			menu.setName("드라마");
			menuList.add(menu);

			rights.setGrade("1");
			/*
			 * source mediaType, size, type, url
			 */
			source.setMediaType("video/mp4");
			source.setType("video/x-freeview-lq");
			source.setUrl("http://../preview.mp4");
			sourceList.add(source);
			preview.setSourceList(sourceList);

			rights.setPreview(preview);

			price.setText(700);
			store.setPrice(price);

			/*
			 * Contributor name : 제작자 또는 저자 이름, album : 앨범명
			 */
			contributor.setName("이승기,수지,이성재,조성하,유연석");
			contributor.setChannel("MBC");
			contributor.setArtist("이승기,수지,이성재,조성하,유연석");
			contributor.setAlbum("");

			/*
			 * vod runningTime, videoInfo, vodExplain
			 */
			runningTime.setUnit("16");
			runningTime.setText("63");

			videoInfo.setScid("0002663073");
			videoInfo.setType("normal");
			videoInfo.setPixel("576x324");
			videoInfo.setPictureSize("16:9");
			videoInfo.setVersion("1");
			videoInfo.setBtvcid("2222222222");
			videoInfo.setSize("307990233");

			vod.setVideoInfo(videoInfo);
			vod.setRunningTime(runningTime);

			date.setText("20130123152110");

			product = new Product();
			product.setProductExplain("무형도관에…");
			product.setIdentifier(identifier);
			product.setMenuList(menuList);
			product.setTitle(title);
			product.setContributor(contributor);

			productList.add(product);

			// }

			responseVO = new VodBoxListRes();
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
