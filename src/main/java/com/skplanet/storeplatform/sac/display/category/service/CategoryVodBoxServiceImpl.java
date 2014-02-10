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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Chapter;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Play;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Preview;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Store;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
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
		String chapterVo; // 회차
		String regdate; // 등록일자

		filteredBy = requestVO.getFilteredBy(); // 차트 구분 코드
		imageSizeCd = requestVO.getImageSizeCd(); // 이미지 사이즈 코드
		offset = requestVO.getOffset(); // 시작점 ROW
		count = requestVO.getCount(); // 페이지당 노출 ROW 수
		duration = requestVO.getDuration();
		chapterVo = requestVO.getChapter();
		regdate = requestVO.getRegdate();

		VodBoxListRes vodBoxListRes = null;
		CommonResponse commonResponse = null;

		// if (null == filteredBy || "".equals(filteredBy)) {
		// throw new Exception("filteredBy 는 필수 파라메터 입니다.");
		// }
		// if (null == imageSizeCd || "".equals(imageSizeCd)) {
		// throw new Exception("imageSizeCd 는 필수 파라메터 입니다.");
		// }

		if (true) {

			// Response VO를 만들기위한 생성자
			List<Product> productList = new ArrayList<Product>();
			List<Menu> menuList = new ArrayList<Menu>();

			Product product = new Product();
			Identifier identifier;
			List<Identifier> identifierList;
			Title title;
			Menu menu;
			Rights rights;
			Preview preview;
			Support support;
			List<Support> supportList;
			Source source;
			List<Source> sourceList;
			Store store;
			Price price;
			Vod vod;
			Time runningTime;
			VideoInfo videoInfo;
			List<VideoInfo> videoInfoList;
			VodExplain vodExplain;
			Contributor contributor;
			Date date;
			Play play;
			Chapter chapter;

			/*
			 * IdentifierList
			 */
			identifierList = new ArrayList<Identifier>();
			identifier = new Identifier();
			identifier.setType("Content");
			identifier.setText("0000029452");
			identifierList.add(identifier);
			identifier = new Identifier();
			identifier.setType("channel");
			identifier.setText("H900000372");
			identifierList.add(identifier);
			product.setIdentifierList(identifierList);

			/*
			 * SupportList
			 */
			supportList = new ArrayList<Support>();
			support = new Support();
			support.setType("btv");
			support.setText("Y");
			supportList.add(support);
			product.setSupportList(supportList);

			/*
			 * TITLE
			 */
			title = new Title();
			title.setText("프리즌브레이크 시즌 1");
			product.setTitle(title);

			/*
			 * ProductExplain
			 */
			product.setProductExplain("사형 집행 직전, 전기의자에 앉은 링컨의 눈에 아버지의 모습이 보이고, 잠시 후 사형을 연기한다는 판사의 전화가 걸려온다. 한편, 마이클은 새 통로를 찾다가 화상을 입어 문신의 일부를 잃는다.");

			/*
			 * Contributor
			 */
			contributor = new Contributor();
			contributor.setDirector("");
			contributor.setChannel("");
			contributor.setArtist("웬트워스 밀러, 도미닉 퍼셀");
			date = new Date();
			date.setType("date/broadcast");
			date.setText("");
			contributor.setDate(date);
			product.setContributor(contributor);

			/*
			 * MenuList
			 */
			menuList = new ArrayList<Menu>();
			menu = new Menu();
			menu.setId("DP18");
			menu.setName("TV 방송");
			menu.setType("topCategory");
			menuList.add(menu);
			menu = new Menu();
			menu.setId("DP18002");
			menu.setName("미드/외화");
			menuList.add(menu);
			menu = new Menu();
			menu.setId("CT14");
			menu.setType("metaClass");
			menuList.add(menu);
			product.setMenuList(menuList);

			/*
			 * Date
			 */
			date = new Date();
			date.setType("date/reg");
			date.setText("20101223212513");
			product.setDate(date);

			/*
			 * Rights
			 */
			rights = new Rights();
			rights.setGrade("2");
			preview = new Preview();
			sourceList = new ArrayList<Source>();
			source = new Source();
			source.setType("video/x-freeview-lq");
			source.setUrl("/SMILE_DATA/201004/27/0000023733/2/06_DanielPowter-BadDay_short(1).mp4");
			sourceList.add(source);
			source = new Source();
			source.setType("video/x-freeview-hq");
			source.setUrl("/SMILE_DATA/201004/27/0000023733/2/06_DanielPowter-BadDay_short.mp4");
			sourceList.add(source);
			preview.setSourceList(sourceList);
			rights.setPreview(preview);
			play = new Play();
			supportList = new ArrayList<Support>();
			support = new Support();
			support.setType("drm");
			support.setText("Y");
			supportList.add(support);
			play.setSupportList(supportList);
			date = new Date();
			date.setType("uint/usagePeriod");
			date.setText("2일");
			play.setDate(date);
			price = new Price();
			price.setText(600);
			play.setPrice(price);
			identifierList = new ArrayList<Identifier>();
			identifier = new Identifier();
			identifier.setType("episode");
			identifier.setText("H900000394");
			identifierList.add(identifier);
			play.setIdentifierList(identifierList);
			play.setPlayProductStatusCode("");
			rights.setPlay(play);
			store = new Store();
			supportList = new ArrayList<Support>();
			support = new Support();
			support.setType("drm");
			support.setText("N");
			supportList.add(support);
			store.setSupportList(supportList);
			price = new Price();
			price.setText(0);
			store.setPrice(price);
			identifierList = new ArrayList<Identifier>();
			identifier = new Identifier();
			identifier.setType("episode");
			identifier.setText("");
			identifierList.add(identifier);
			store.setIdentifierList(identifierList);
			store.setStoreProductStatusCode("");
			rights.setStore(store);
			product.setRights(rights);

			/*
			 * VOD
			 */
			vod = new Vod();
			runningTime = new Time();
			runningTime.setUnit("");
			runningTime.setText("45");
			vod.setRunningTime(runningTime);
			chapter = new Chapter();
			chapter.setUnit("회");
			chapter.setText(15);
			vod.setChapter(chapter);
			vodExplain = new VodExplain();
			vodExplain.setSaleDateInfo("");
			vodExplain.setText("");
			vod.setVodExplain(vodExplain);
			videoInfoList = new ArrayList<VideoInfo>();
			videoInfo = new VideoInfo();
			videoInfo.setType("normal");
			videoInfo.setScid("0000030215");
			videoInfo.setPixel("640x480");
			videoInfo.setPictureSize("4:3");
			videoInfo.setVersion("1");
			videoInfo.setBtvcid("{3252188A-789A-4C5D-9417-16909CDAB444}");
			videoInfo.setSize("0");
			videoInfoList.add(videoInfo);
			videoInfo.setType("sd");
			videoInfo.setScid("0000030216");
			videoInfo.setPixel("640x480");
			videoInfo.setPictureSize("4:3");
			videoInfo.setVersion("1");
			videoInfo.setBtvcid("{3252188A-789A-4C5D-9417-16909CDAB444}");
			videoInfo.setSize("0");
			videoInfoList.add(videoInfo);
			videoInfo.setType("hd");
			videoInfo.setScid("0000030214");
			videoInfo.setPixel("640x480");
			videoInfo.setPictureSize("4:3");
			videoInfo.setVersion("1");
			videoInfo.setBtvcid("{3252188A-789A-4C5D-9417-16909CDAB444}");
			videoInfo.setSize("0");
			videoInfoList.add(videoInfo);
			vod.setVideoInfoList(videoInfoList);
			product.setVod(vod);

			productList.add(product);

			vodBoxListRes = new VodBoxListRes();
			commonResponse = new CommonResponse();
			vodBoxListRes.setProductList(productList);
			commonResponse.setTotalCount(totalCount);
			vodBoxListRes.setCommonRes(commonResponse);
		}
		return vodBoxListRes;

	}

}
