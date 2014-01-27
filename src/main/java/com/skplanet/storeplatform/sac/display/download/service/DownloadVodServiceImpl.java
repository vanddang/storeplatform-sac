/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.download.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadVodReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadVodRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Time;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Chapter;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Distributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Play;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Purchase;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Store;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.VideoInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Vod;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.download.vo.DownloadVod;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 1. 22. Updated by : 이석희, 인크로스.
 */
@Service
@Transactional
public class DownloadVodServiceImpl implements DownloadVodService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	@Autowired
	private DisplayCommonService commonService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.DownloadAppService#DownloadAppService(com.skplanet
	 * .storeplatform.sac.client.product.vo.DownloadAppReqVO)
	 */
	@Override
	public DownloadVodRes searchDownloadVod(SacRequestHeader requestheader, DownloadVodReq downloadVodReq) {
		TenantHeader tanantHeader = requestheader.getTenantHeader();
		DeviceHeader deviceHeader = requestheader.getDeviceHeader();

		downloadVodReq.setTenantId(tanantHeader.getTenantId());
		downloadVodReq.setDeviceModelCd(deviceHeader.getModel());

		DownloadVodRes response = new DownloadVodRes();
		CommonResponse commonResponse = new CommonResponse();
		int totalCount = 0;

		List<Identifier> identifierList = null;
		List<Menu> menuList = null;
		List<Support> supportList = null;
		List<Source> sourceList = null;
		List<VideoInfo> videoInfoList = null;

		Product product = null;
		Identifier identifier = null;
		Support support = null;
		Rights rights = null;
		Source source = null;
		Title title = null;
		Menu menu = null;
		Purchase purchase = null;
		Vod vod = null;
		Time runningTime = null;
		VideoInfo videoInfo = null;
		Store store = null;
		Play play = null;
		Price price = null;
		Chapter chapter = null;
		Date date = null;
		Distributor distributor = new Distributor();

		if (downloadVodReq.getDummy() == null) {

			DownloadVod downloadVodInfo = null;

			// 다운로드 Vod 상품 조회
			downloadVodInfo = this.commonDAO.queryForObject("Download.getDownloadVodInfo", downloadVodReq,
					DownloadVod.class);

			menuList = new ArrayList<Menu>();
			sourceList = new ArrayList<Source>();
			identifierList = new ArrayList<Identifier>();
			videoInfoList = new ArrayList<VideoInfo>();

			product = new Product();
			identifier = new Identifier();
			support = new Support();
			rights = new Rights();
			source = new Source();
			title = new Title();
			purchase = new Purchase();
			vod = new Vod();
			runningTime = new Time();
			store = new Store();
			price = new Price();
			chapter = new Chapter();
			play = new Play();

			// 상품ID
			identifier = new Identifier();
			identifier.setType("episode");
			identifier.setText(downloadVodInfo.getProdId());
			identifierList.add(identifier);
			identifier = new Identifier();
			identifier.setType("isPartOf");
			identifier.setText(downloadVodInfo.getProdId());
			identifierList.add(identifier);

			supportList = new ArrayList<Support>();
			support = new Support();
			support.setType("hdcp");
			support.setText(downloadVodInfo.getHdcpYn());
			supportList.add(support);
			support = new Support();
			support.setType("hd");
			support.setText(downloadVodInfo.getHdvYn());
			supportList.add(support);
			support = new Support();
			support.setType("btv");
			support.setText(downloadVodInfo.getBtvYn());
			supportList.add(support);
			support = new Support();
			support.setType("dolby");
			support.setText(downloadVodInfo.getDolbySprtYn());
			supportList.add(support);

			title.setText(downloadVodInfo.getProdNm());

			/*
			 * Menu(메뉴정보) Id, Name, Type
			 */
			menu = new Menu();
			menu.setId(downloadVodInfo.getTopMenuId());
			menu.setName(downloadVodInfo.getTopMenuNm());
			menu.setType("topClass");
			menuList.add(menu);
			menu = new Menu();
			menu.setId(downloadVodInfo.getMenuId());
			menu.setName(downloadVodInfo.getMenuNm());
			menuList.add(menu);
			menu = new Menu();
			menu.setId(downloadVodInfo.getMetaClsfCd());
			menu.setType("metaClass");
			menuList.add(menu);

			/*
			 * source mediaType, size, type, url
			 */
			source.setMediaType("image/png");
			source.setType("thumbnail");
			source.setUrl(downloadVodInfo.getImgPath() + downloadVodInfo.getImgNm());
			source.setSize(downloadVodInfo.getImgSize());
			sourceList.add(source);

			runningTime.setText(downloadVodInfo.getEpsdPlayTm());
			vod.setRunningTime(runningTime);
			// chapter.setUnit(1);
			// chapter.setText("회");
			videoInfo = new VideoInfo();
			videoInfo.setBtvcid(downloadVodInfo.getNmBtvCid());
			videoInfo.setPictureSize(downloadVodInfo.getNmDpPicRatio());
			videoInfo.setPixel(downloadVodInfo.getNmDpPixel());
			videoInfo.setScid(downloadVodInfo.getNmSubContsId());
			videoInfo.setSize(downloadVodInfo.getNmFileSize().toString());
			videoInfo.setType("normal");
			videoInfo.setVersion(downloadVodInfo.getNmProdVer());
			videoInfoList.add(videoInfo);
			videoInfo = new VideoInfo();
			videoInfo.setBtvcid(downloadVodInfo.getSdBtvCid());
			videoInfo.setPictureSize(downloadVodInfo.getSdDpPicRatio());
			videoInfo.setPixel(downloadVodInfo.getSdDpPixel());
			videoInfo.setScid(downloadVodInfo.getSdSubContsid());
			videoInfo.setSize(downloadVodInfo.getSdFileSize().toString());
			videoInfo.setType("sd");
			videoInfo.setVersion(downloadVodInfo.getSdProdVer());
			videoInfoList.add(videoInfo);
			videoInfo = new VideoInfo();
			videoInfo.setBtvcid(downloadVodInfo.getHdBtvCid());
			videoInfo.setPictureSize(downloadVodInfo.getHdDpPicRatio());
			videoInfo.setPixel(downloadVodInfo.getHdDpPixel());
			videoInfo.setScid(downloadVodInfo.getHdSubContsid());
			videoInfo.setSize(downloadVodInfo.getHdFileSize().toString());
			videoInfo.setType("hd");
			videoInfo.setVersion(downloadVodInfo.getHdProdVer());
			videoInfoList.add(videoInfo);
			vod.setChapter(chapter);
			vod.setVideoInfoList(videoInfoList);

			/*
			 * Rights grade
			 */
			rights.setAllow(downloadVodInfo.getDwldAreaLimtYn());
			rights.setGrade(downloadVodInfo.getProdGrdCd());

			// Support playSupport = new Support();
			// playSupport.setType("drm");
			// if(downloadVodInfo.getPlayDrmYn() == null){
			// playSupport.setText(" ");
			// }else{
			// playSupport.setText(downloadVodInfo.getPlayDrmYn());
			// }

			// play.setSupport(playSupport);

			date = new Date();
			date.setType("duration/usagePeriod");
			date.setText(downloadVodInfo.getUsagePeriod());
			price.setText(downloadVodInfo.getPlayProdAmt());

			Source playSource = new Source();
			playSource.setUrl(downloadVodInfo.getPlayProdId());

			play.setDate(date); // 이용기간
			play.setPrice(price); // 바로보기 상품 금액
			play.setSource(playSource); // 바로보기 상품 url
			if (downloadVodInfo.getStrmNetworkCd() != null) {
				play.setNetworkRestrict("ota");
			}

			rights.setPlay(play);
			// Support storeSupport = new Support();
			// storeSupport.setType("drm");
			// if(downloadVodInfo.getStoreDrmYn() == null){
			// storeSupport.setText(" ");
			// }else{
			// storeSupport.setText(downloadVodInfo.getStoreDrmYn());
			// }

			// play.setSupport(storeSupport);

			price = new Price();
			price.setText(downloadVodInfo.getStoreProdAmt());
			Source storeSource = new Source();
			storeSource.setUrl(downloadVodInfo.getStoreProdId());

			store.setPrice(price);
			store.setSource(storeSource);

			// 네트워크 제한이 있을경우
			if (downloadVodInfo.getDwldNetworkCd() != null) {
				store.setNetworkRestrict("ota");
			}
			rights.setStore(store);

			distributor.setName(downloadVodInfo.getExpoSellerNm());
			distributor.setTel(downloadVodInfo.getExpoSellerTelno());
			distributor.setEmail(downloadVodInfo.getExpoSellerEmail());
			distributor.setRegNo(downloadVodInfo.getSellerMbrNo());

			identifier = new Identifier();
			identifier.setType("purchase");
			identifier.setText("GI100000000265812187");
			purchase.setIdentifier(identifier);
			purchase.setState("gift");
			purchase.setToken("863353467024782ffce534613bac6450048a87fb0dcdbb4e30184f2a88418a037b90c3a16eb87d6eef34b18f320114e9d9cc965fb055db");

			product = new Product();
			product.setIdentifierList(identifierList);
			product.setTitle(title);
			product.setMenuList(menuList);
			product.setSourceList(sourceList);
			product.setVod(vod);
			product.setRights(rights);
			product.setDistributor(distributor);
			product.setPurchase(purchase);

		} else {
			// dummy data를 호출할때
			menuList = new ArrayList<Menu>();
			sourceList = new ArrayList<Source>();
			identifierList = new ArrayList<Identifier>();
			videoInfoList = new ArrayList<VideoInfo>();

			product = new Product();
			identifier = new Identifier();
			support = new Support();
			rights = new Rights();
			source = new Source();
			title = new Title();
			purchase = new Purchase();
			vod = new Vod();
			runningTime = new Time();
			store = new Store();
			price = new Price();
			chapter = new Chapter();

			// 상품ID
			identifier = new Identifier();
			identifier.setType("episode");
			identifier.setText("H001601609");
			identifierList.add(identifier);
			identifier = new Identifier();
			identifier.setType("isPartOf");
			identifier.setText("H001601608");
			identifierList.add(identifier);

			supportList = new ArrayList<Support>();
			support = new Support();
			support.setType("hdcp");
			support.setText("N");
			supportList.add(support);
			support = new Support();
			support.setType("hd");
			support.setText("Y");
			supportList.add(support);
			support = new Support();
			support.setType("btv");
			support.setText("Y");
			supportList.add(support);

			title.setText("대지진");

			/*
			 * Menu(메뉴정보) Id, Name, Type
			 */
			menu = new Menu();
			menu.setId("DP000517");
			menu.setName("영화");
			menu.setType("topClass");
			menuList.add(menu);
			menu = new Menu();
			menu.setId("DP17004");
			menu.setName("드라마");
			menu = new Menu();
			menu.setId("CT13");
			menu.setType("metaClass");
			menuList.add(menu);

			/*
			 * source mediaType, size, type, url
			 */
			source.setMediaType("image/png");
			source.setType("thumbnail");
			source.setUrl("http://wap.tstore.co.kr/SMILE_DATA7/PVOD/201310/31/0001747842/5/0003526438/5/RT1_00000191803_1_0346_182x261_130x186.PNG");
			sourceList.add(source);

			runningTime.setText("135");
			vod.setRunningTime(runningTime);
			// chapter.setUnit(1);
			// chapter.setText("회");
			videoInfo = new VideoInfo();
			videoInfo.setBtvcid("2222222222");
			videoInfo.setPictureSize("16:9");
			videoInfo.setPixel("576x324");
			videoInfo.setScid("0003526439");
			videoInfo.setSize("575311754");
			videoInfo.setType("normal");
			videoInfo.setVersion("1");
			videoInfoList.add(videoInfo);
			videoInfo = new VideoInfo();
			videoInfo.setBtvcid("2222222222");
			videoInfo.setPictureSize("16:9");
			videoInfo.setPixel("720x400");
			videoInfo.setScid("0003526440");
			videoInfo.setSize("1146264845");
			videoInfo.setType("sd");
			videoInfo.setVersion("1");
			videoInfoList.add(videoInfo);
			videoInfo = new VideoInfo();
			videoInfo.setBtvcid("2222222222");
			videoInfo.setPictureSize("16:9");
			videoInfo.setPixel("1280x720");
			videoInfo.setScid("0003526441");
			videoInfo.setSize("2028754302");
			videoInfo.setType("hd");
			videoInfo.setVersion("1");
			videoInfoList.add(videoInfo);
			vod.setChapter(chapter);
			vod.setVideoInfoList(videoInfoList);

			/*
			 * Rights grade
			 */
			rights.setAllow("domestic");
			rights.setGrade("PD004401");

			price.setText(1200);
			source = new Source();
			source.setUrl("/movie/drama/H001373322");
			store.setPrice(price);
			store.setSource(source);
			rights.setStore(store);

			distributor.setName("서진우");
			distributor.setTel("0215990011");
			distributor.setEmail("skplanet_vod@tstore.co.kr");
			distributor.setRegNo("중구-02923호");

			identifier = new Identifier();
			identifier.setType("purchase");
			identifier.setText("GI100000000265812187");
			purchase.setIdentifier(identifier);
			purchase.setState("gift");
			purchase.setToken("863353467024782ffce534613bac6450048a87fb0dcdbb4e30184f2a88418a037b90c3a16eb87d6eef34b18f320114e9d9cc965fb055db");

			product = new Product();
			product.setIdentifierList(identifierList);
			product.setTitle(title);
			product.setMenuList(menuList);
			product.setSourceList(sourceList);
			product.setVod(vod);
			product.setRights(rights);
			product.setDistributor(distributor);
			product.setPurchase(purchase);

			commonResponse.setTotalCount(1);
		}
		response.setCommonResponse(commonResponse);
		response.setProduct(product);
		return response;
	}
}
