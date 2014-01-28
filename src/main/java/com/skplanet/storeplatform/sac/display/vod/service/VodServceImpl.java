/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.vod.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.vod.VodDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.vod.VodDetailRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Time;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Distributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Play;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Preview;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Store;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.VideoInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Vod;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.ProductImage;
import com.skplanet.storeplatform.sac.display.vod.controller.VodController;
import com.skplanet.storeplatform.sac.display.vod.vo.VodDetail;

/**
 * VOD Service
 *
 * Updated on : 2014-01-09 Updated by : 임근대, SK플래닛.
 */
@Service
@Transactional
public class VodServceImpl implements VodService {

	private static final Logger logger = LoggerFactory.getLogger(VodController.class);

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService commonService;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.skplanet.storeplatform.sac.display.vod.service.VodService#searchVod(com.skplanet.storeplatform.sac.client
	 * .display.vo.vod.VodDetailReq)
	 */
	@Override
	public VodDetailRes searchVod(VodDetailReq req) {

		// Dummy
		VodDetailRes res = new VodDetailRes();
		Product product = new Product();

		// Channel 정보 조회
		VodDetail appDetail = null;// this.commonDAO.queryForObject("VodDetail.selectVodDetail", req, VodDetail.class);
		logger.debug("appDetail={}", appDetail);

		product.setIdentifier(new Identifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, "H900194984"));
		// title
		product.setTitle(new Title("무한도전"));
		// sourceList
		List<Source> sourceList = new ArrayList<Source>();
		Source source = new Source();
		source.setMediaType("image/png");
		source.setSize(0);
		source.setType("thumbnail");
		source.setUrl("/SMILE_DATA7/PVODS/201401/11/0000194954/20/0000203872/19/ST1_00000208638_1_0859_694x465.PNG");
		sourceList.add(source);

		source = new Source();
		source.setMediaType("image/png");
		source.setSize(0);
		source.setType(DisplayConstants.DP_SOURCE_TYPE_SCREENSHOT);
		source.setUrl("/SMILE_DATA7/PVODS/201401/11/0000194954/20/0000203872/19/ST1_00000208638_1_0859_694x465.PNG");
		sourceList.add(source);

		source = new Source();
		source.setMediaType("image/png");
		source.setSize(0);
		source.setType(DisplayConstants.DP_SOURCE_TYPE_SCREENSHOT);
		source.setUrl("/SMILE_DATA7/PVODS/201401/11/0000194954/20/0000203872/19/ST1_00000208638_1_0859_694x465.PNG");
		sourceList.add(source);

		source = new Source();
		source.setMediaType("image/png");
		source.setSize(0);
		source.setType(DisplayConstants.DP_SOURCE_TYPE_SCREENSHOT);
		source.setUrl("/SMILE_DATA7/PVODS/201401/11/0000194954/20/0000203872/19/ST1_00000208638_1_0859_694x465.PNG");
		sourceList.add(source);

		source = new Source();
		source.setMediaType("image/png");
		source.setSize(0);
		source.setType(DisplayConstants.DP_SOURCE_TYPE_SCREENSHOT);
		source.setUrl("/SMILE_DATA7/PVODS/201401/11/0000194954/20/0000203872/19/ST1_00000208638_1_0859_694x465.PNG");
		sourceList.add(source);

		product.setSourceList(sourceList);

		product.setProductExplain("국내 최고 리얼 버라이어티 쇼, 무한도전!!");
		product.setProductDetailExplain("국내 최고 리얼 버라이어티 쇼, 무한도전!! 대한민국 평균 이하임을 자처하는 일곱 남자지만, 이들이 모이면 천하무적!");
		product.setProductIntroduction("");

		Support support = new Support();
		support.setType("drm");
		support.setText("Y");
		Support support1 = new Support();
		support1.setType("hd");
		support1.setText("Y");
		Support support2 = new Support();
		support2.setType("dolby");
		support2.setText("Y");
		product.setSupportList(new ArrayList<Support>(Arrays.asList(support, support1, support2)));

		Menu menu = new Menu();
		menu.setId("DP000518");
		menu.setName("broadcast");
		menu.setType("topClass");

		Menu menu1 = new Menu();
		menu1.setId("DP18004");
		menu1.setName("broadcast/entertainmentFun");

		Menu menu2 = new Menu();
		menu2.setId("CT14");
		menu2.setName("movie/series");
		menu2.setType("metaClass");

		product.setMenuList(new ArrayList<Menu>(Arrays.asList(menu, menu1, menu2)));

		Date date = new Date();
		date.setType("date/reg");
		date.setText("20130324T220917+0900");
		Date date1 = new Date();
		date1.setType("date/saleReg");
		date1.setText("20130324T220917+0900");
		product.setDateList(new ArrayList<Date>(Arrays.asList(date, date1)));

		Contributor contributor = new Contributor();
		contributor.setArtist("유재석,박명수,정준하,정형돈,노홍철,하하");
		contributor.setChannel("mbc");
		contributor.setDirector("감독");
		product.setContributor(contributor);

		Distributor distributor = new Distributor();
		distributor.setIdentifier("mbc_tv");
		distributor.setName("서진우");
		distributor.setTel("0215990011");
		distributor.setEmail("skplanet_vod@tstore.co.kr");
		product.setDistributor(distributor);

		Rights rights = new Rights();
		rights.setGrade("0");
		rights.setAllow("domestic");

		Preview preview = new Preview();

		Source previewSource = new Source();
		previewSource.setMediaType("video/mp4");
		previewSource.setType("video/x-freeview-lq");
		previewSource.setUrl("http://../preview.mp4");
		// preview.setSource(previewSource);
		// Source Object에서 List로 변경됨. (이태희)
		preview.setSourceList(new ArrayList<Source>(Arrays.asList(source)));

		rights.setPreview(preview);

		Support emplySupport = new Support();
		rights.setPlay(new Play(emplySupport, new Price(700), new Date("date/publish", "2013")));
		rights.setStore(new Store(emplySupport, new Price(700), new Date("date/publish", "2013")));
		rights.setDate(new Date("date/publish", "2013"));
		product.setRights(rights);

		Accrual accrual = new Accrual();
		accrual.setVoterCount(48);
		accrual.setDownloadCount(1776);
		accrual.setScore(3.0);
		product.setAccrual(accrual);

		product.setSubProductTotalCount(100);

		// subProjectList
		List<Product> subProjectList = new ArrayList<Product>();
		Product product1 = new Product();
		product1.setIdentifier(new Identifier(DisplayConstants.DP_CONTENT_IDENTIFIER_CD, "0002119673"));

		Support support3 = new Support();
		support3.setType("drm");
		support3.setText("Y");
		Support support4 = new Support();
		support4.setType("hd");
		support4.setText("Y");
		product1.setSupportList(new ArrayList<Support>(Arrays.asList(support3, support4)));

		product1.setTitle(new Title("무한도전"));

		Date date2 = new Date();
		date2.setType("date/reg");
		date2.setText("20130324T220917+0900");
		Date date3 = new Date();
		date3.setType("date/saleReg");
		date3.setText("20130324T220917+0900");
		product1.setDateList(new ArrayList<Date>(Arrays.asList(date2, date3)));

		Rights rights2 = new Rights();
		rights2.setPlay(new Play(emplySupport, new Price(700), new Date("date/publish", "20130324T220917+0900")));
		rights2.setStore(new Store(emplySupport, new Price(700), new Date("date/publish", "20130324T220917+0900")));
		rights2.setDate(new Date("date/publish", "20130324T220917+0900"));
		product1.setRights(rights2);

		/*
<videoInfo btvcid="2222222222" pictureSize="16:9" pixel="576x324" scid="0003948737" size="390643757" type="normal" version="1" />
<videoInfo btvcid="2222222222" pictureSize="16:9" pixel="800x450" scid="0003948738" size="801568401" type="sd" version="1" />
<videoInfo btvcid="2222222222" pictureSize="16:9" pixel="1280x720" scid="0003948739" size="1186069455" type="hd" version="1" />
		 */
		VideoInfo videoInfo = new VideoInfo();
		videoInfo.setScid("0002663073");
		videoInfo.setType("normal");
		videoInfo.setPixel("576x324");
		videoInfo.setPictureSize("16:9");
		videoInfo.setVersion("1");
		videoInfo.setSize("307990233");

		product1.setVod(new Vod(new Time("16", "63"), new ArrayList<VideoInfo>(Arrays.asList(videoInfo))));
		subProjectList.add(product1);
		product.setSubProductList(subProjectList);

		res.setProduct(product);

		return res;

	}

	public VodDetailRes searchVod_dev(VodDetailReq req) {
		// Dummy
		VodDetailRes res = new VodDetailRes();
		Product product = new Product();

		//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ");

		// --------------------------------------------------------
		// 1. Channel 정보 조회
		// --------------------------------------------------------
		VodDetail vodDetail = this.commonDAO.queryForObject("VodDetail.selectVodChannel", req, VodDetail.class);
		logger.debug("vodDetail={}", vodDetail);

		//Screenshots
		List<ProductImage> screenshotList = this.commonDAO.queryForList("VodDetail.selectSourceList", req, ProductImage.class);

		this.mapProduct(product, vodDetail, screenshotList);

		// --------------------------------------------------------
		// 2. subProjectList
		// --------------------------------------------------------
		List<Product> productList = null;
		this.mapSubProductList(product, productList);

		res.setProduct(product);

		return res;
	}



	/**
	 *
	 * @param product
	 * @param vodDetail
	 * @param req
	 * @return
	 */
	private void mapProduct(Product product, VodDetail vodDetail, List<ProductImage> screenshotList) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ");
		product.setIdentifier(new Identifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, vodDetail.getProdId()));
		product.setTitle(new Title(vodDetail.getProdNm()));

		// 대표 이미지 (thumbnail)
		List<Source> sourceList = new ArrayList<Source>();
		Source source = new Source();
		source.setMediaType(DisplayCommonUtil.getMimeType(vodDetail.getImgPath()));
		source.setSize(vodDetail.getImgSize());
		source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL);
		source.setUrl(vodDetail.getImgPath()+vodDetail.getImgNm());
		sourceList.add(source);

		// screenshot
		for (ProductImage screenshotImage : screenshotList) {
			Source screenshotSource = new Source();
			screenshotSource.setType(DisplayConstants.DP_SOURCE_TYPE_SCREENSHOT);
			screenshotSource.setSize(screenshotImage.getFileSize());
			screenshotSource.setUrl(screenshotImage.getFilePath() + screenshotImage.getFileNm());
			sourceList.add(screenshotSource);
		}
		product.setSourceList(sourceList);

		// 상품 설명
		product.setProductExplain(vodDetail.getProdBaseDesc());
		product.setProductDetailExplain(vodDetail.getProdDtlDesc());
		product.setProductIntroduction(vodDetail.getProdIntrDscr());

		/** DRM */
		Support supportDrm = new Support();
		supportDrm.setType(DisplayConstants.DP_DRM_SUPPORT_NM);
		supportDrm.setText(vodDetail.getDrmYn());
		/** HD */
		Support supportHd = new Support();
		supportHd.setType(DisplayConstants.DP_VOD_HD_SUPPORT_NM);
		supportHd.setText(vodDetail.getHdvYn());
		/** DOLBY_SPRT_YN */
		Support supportDolby = new Support();
		supportDolby.setType(DisplayConstants.DP_VOD_HD_SUPPORT_NM);
		supportDolby.setText(vodDetail.getDolbySprtYn());

		product.setSupportList(new ArrayList<Support>(Arrays.asList(supportDrm, supportHd, supportDolby)));

		//MenuList
		List<Menu> menuList = new ArrayList<Menu>();
		Menu menu = new Menu();
		menu.setId(vodDetail.getTopMenuId());
		menu.setName(vodDetail.getTopMenuNm());
		menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
		menuList.add(menu);

		menu = new Menu();
		menu.setId(vodDetail.getMenuId());
		menu.setName(vodDetail.getMenuNm());
		menuList.add(menu);

		//Meta Class
		menu = new Menu();
		menu.setType(DisplayConstants.DP_META_CLASS_MENU_TYPE);
		menu.setId(vodDetail.getMetaClsfCd());
		menuList.add(menu);

		//Genre
		menu = new Menu();
		menu.setType(DisplayConstants.DP_MENU_TYPE_GENRE);
		menu.setId(vodDetail.getGenreCd());
		menuList.add(menu);

		product.setMenuList(menuList);


		Date dateReg = new Date();
		dateReg.setType(DisplayConstants.DP_DATE_REG);
		dateReg.setText(sdf.format(vodDetail.getRegDt()));

		Date dateSaleReg = new Date();
		dateSaleReg.setType(DisplayConstants.DP_DATE_SALE_REG);
		dateSaleReg.setText(sdf.format(vodDetail.getSvcStartDt()));
		product.setDateList(new ArrayList<Date>(Arrays.asList(dateReg, dateSaleReg)));

		Contributor contributor = new Contributor();
		contributor.setDirector(vodDetail.getArtist2Nm()); 	//감독
		contributor.setArtist(vodDetail.getArtist1Nm()); 	//출연


		//TODO: 코드값 상수처리?
		if(vodDetail.getTopMenuId().equals("DP000518")) { //공통코드 : DP000518 (TV 방송)
			contributor.setChannel(vodDetail.getBrdcCompCdNm()); //방송사
		}
		Date date = new Date();
		date.setText(sdf.format(vodDetail.getIssueDay()));
		contributor.setDate(date);
		product.setContributor(contributor);


		//FIXME: Distributor
		Distributor distributor = new Distributor();
		distributor.setIdentifier("mbc_tv");
		distributor.setType("corporation");
		distributor.setName("서진우");
		distributor.setTel("0215990011");
		distributor.setEmail("skplanet_vod@tstore.co.kr");
		product.setDistributor(distributor);


		//FIXME: rights
		Rights rights = new Rights();
		rights.setGrade("0");
		rights.setAllow("domestic");

		//FIXME: Preview
		Preview preview = new Preview();
		Source previewSource = new Source();
		previewSource.setMediaType("video/mp4");
		previewSource.setType("video/x-freeview-lq");
		previewSource.setUrl("http://../preview.mp4");
		// preview.setSource(previewSource);
		// Source Object에서 List로 변경됨. (이태희)
		preview.setSourceList(new ArrayList<Source>(Arrays.asList(source)));
		rights.setPreview(preview);

		//FIXME: rights
		Support emplySupport = new Support();
		rights.setPlay(new Play(emplySupport, new Price(700), new Date("date/publish", "2013")));
		rights.setStore(new Store(emplySupport, new Price(700), new Date("date/publish", "2013")));
		rights.setDate(new Date("date/publish", "2013"));
		product.setRights(rights);

		//Accrual
		Accrual accrual = new Accrual();
		accrual.setVoterCount(vodDetail.getPaticpersCnt());
		accrual.setDownloadCount(vodDetail.getPrchsCnt());
		accrual.setScore(vodDetail.getAvgEvluScore());
		product.setAccrual(accrual);

	}

	/**
	 *
	 * @param res
	 * @param product
	 */
	private void mapSubProductList(Product product, List<Product> productList) {
		product.setSubProductTotalCount(100);
		List<Product> subProjectList = new ArrayList<Product>();
		Product product1 = new Product();
		product1.setIdentifier(new Identifier(DisplayConstants.DP_CONTENT_IDENTIFIER_CD, "0002119673"));

		Support support2 = new Support();
		support2.setType("drm");
		support2.setText("Y");
		Support support3 = new Support();
		support3.setType("hd");
		support3.setText("Y");
		product1.setSupportList(new ArrayList<Support>(Arrays.asList(support2, support3)));

		product1.setTitle(new Title("무한도전"));

		Date date2 = new Date();
		date2.setType("date/reg");
		date2.setText("20130324T220917+0900");
		Date date3 = new Date();
		date3.setType("date/saleReg");
		date3.setText("20130324T220917+0900");
		product1.setDateList(new ArrayList<Date>(Arrays.asList(date2, date3)));


		Support emplySupport = new Support();
		Rights rights2 = new Rights();
		rights2.setPlay(new Play(emplySupport, new Price(700), new Date("date/publish", "2013")));
		rights2.setStore(new Store(emplySupport, new Price(700), new Date("date/publish", "2013")));
		rights2.setDate(new Date("date/publish", "2013"));
		product1.setRights(rights2);

		VideoInfo videoInfo = new VideoInfo();
		videoInfo.setScid("0002663073");
		videoInfo.setType("normal");
		videoInfo.setPixel("576x324");
		videoInfo.setPictureSize("16:9");
		videoInfo.setVersion("1");
		videoInfo.setBtvcid("2222222222");
		videoInfo.setSize("307990233");

		product1.setVod(new Vod(new Time("16", "63"), videoInfo));
		subProjectList.add(product1);
		product.setSubProductList(subProjectList);

	}

}
