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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadMusicSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadMusicSacRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.ProductListSacIn;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Music;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Purchase;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

//import org.apache.commons.lang3.StringUtils;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 1. 22. Updated by : 이석희, 인크로스.
 */
@Service
public class DownloadMusicServiceImpl implements DownloadMusicService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	@Autowired
	private DisplayCommonService commonService;
	@Autowired
	HistoryInternalSCI historyInternalSCI;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.DownloadAppService#DownloadAppService(com.skplanet
	 * .storeplatform.sac.client.product.vo.DownloadAppReqVO)
	 */
	@Override
	public DownloadMusicSacRes searchDownloadMusic(SacRequestHeader requestheader,
			DownloadMusicSacReq downloadMusicSacReq) {
		TenantHeader tanantHeader = requestheader.getTenantHeader();
		DeviceHeader deviceHeader = requestheader.getDeviceHeader();

		downloadMusicSacReq.setTenantId(tanantHeader.getTenantId());
		downloadMusicSacReq.setDeviceModelCd(deviceHeader.getModel());
		downloadMusicSacReq.setLangCd(tanantHeader.getLangCd());
		downloadMusicSacReq.setImageCd(DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);

		DownloadMusicSacRes response = new DownloadMusicSacRes();
		CommonResponse commonResponse = new CommonResponse();

		int totalCount = 0;
		String productId = downloadMusicSacReq.getProductId();
		String deviceKey = downloadMusicSacReq.getDeviceKey();
		String userKey = downloadMusicSacReq.getUserKey();

		List<Identifier> identifierList = null;
		List<Menu> menuList = null;
		List<Source> sourceList = null;

		Product product = null;
		Identifier identifier = null;
		Rights rights = null;
		Source source = null;
		Title title = null;
		Menu menu = null;
		Purchase purchase = null;
		Contributor contributor = null;
		Music music = null;
		Date date = null;

		if (downloadMusicSacReq.getDummy() == null) {
			// dummy 호출이 아닐때

			// 필수 파라미터 체크
			if (StringUtils.isEmpty(productId)) {
				throw new StorePlatformException("SAC_DSP_0002", "productId", productId);
			}
			if (StringUtils.isEmpty(deviceKey)) {
				throw new StorePlatformException("SAC_DSP_0002", "deviceKey", deviceKey);
			}
			if (StringUtils.isEmpty(userKey)) {
				throw new StorePlatformException("SAC_DSP_0002", "userKey", userKey);
			}

			// 다운로드 Music 상품 조회
			MetaInfo metaInfo = (MetaInfo) this.commonDAO.queryForObject("Download.getDownloadMusicInfo",
					downloadMusicSacReq);

			String prchsId = null;
			String prchsDt = null;
			String prchsState = null;
			String prchsProdId = null;

			try {
				// 구매내역 조회를 위한 생성자
				ProductListSacIn productListSacIn = new ProductListSacIn();
				List<ProductListSacIn> productList = new ArrayList<ProductListSacIn>();

				productListSacIn.setProdId(metaInfo.getProdId());
				productList.add(productListSacIn);

				HistoryListSacInReq historyListSacReq = new HistoryListSacInReq();
				historyListSacReq.setTenantId(downloadMusicSacReq.getTenantId());
				historyListSacReq.setUserKey(downloadMusicSacReq.getUserKey());
				historyListSacReq.setDeviceKey(downloadMusicSacReq.getDeviceKey());
				historyListSacReq.setPrchsProdType(DisplayConstants.PRCHS_PROD_TYPE_OWN);
				historyListSacReq.setStartDt("19000101000000");
				historyListSacReq.setEndDt(metaInfo.getSysDate());
				historyListSacReq.setOffset(1);
				historyListSacReq.setCount(1);
				historyListSacReq.setProductList(productList);

				// 구매내역 조회 실행
				HistoryListSacInRes historyListSacRes = this.historyInternalSCI.searchHistoryList(historyListSacReq);

				this.log.debug("----------------------------------------------------------------");
				this.log.debug("[getDownloadMusicInfo] purchase count : {}", historyListSacRes.getTotalCnt());
				this.log.debug("----------------------------------------------------------------");

				if (historyListSacRes.getTotalCnt() > 0) {
					prchsId = historyListSacRes.getHistoryList().get(0).getPrchsId();
					prchsDt = historyListSacRes.getHistoryList().get(0).getPrchsDt();
					prchsState = historyListSacRes.getHistoryList().get(0).getPrchsCaseCd();
					prchsProdId = historyListSacRes.getHistoryList().get(0).getProdId();

					if (DisplayConstants.PRCHS_CASE_PURCHASE_CD.equals(prchsState)) {
						prchsState = "payment";
					} else if (DisplayConstants.PRCHS_CASE_GIFT_CD.equals(prchsState)) {
						prchsState = "gift";
					}
				}
			} catch (Exception ex) {
				throw new StorePlatformException("SAC_DSP_0001", "구매내역 조회 ", ex);
			}

			menuList = new ArrayList<Menu>();
			sourceList = new ArrayList<Source>();
			identifierList = new ArrayList<Identifier>();

			product = new Product();
			identifier = new Identifier();
			rights = new Rights();
			source = new Source();
			title = new Title();
			purchase = new Purchase();
			contributor = new Contributor();
			music = new Music();
			date = new Date();

			// 상품ID
			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
			identifier.setText(metaInfo.getProdId());
			identifierList.add(identifier);
			// identifier = new Identifier();
			// identifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
			// identifier.setText(metaInfo.getChnlProdId());
			// identifierList.add(identifier);

			title.setText(metaInfo.getProdNm());

			/*
			 * source mediaType, size, type, url
			 */
			source.setMediaType(DisplayCommonUtil.getMimeType(metaInfo.getImagePath() + metaInfo.getImageNm()));
			source.setType(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL);
			source.setUrl(metaInfo.getImagePath() + metaInfo.getImageNm());
			source.setSize(metaInfo.getImageSize());
			sourceList.add(source);

			/*
			 * Menu(메뉴정보) Id, Name, Type
			 */
			menu = new Menu();
			menu.setId(metaInfo.getTopMenuId());
			menu.setName(metaInfo.getTopMenuNm());
			menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
			menuList.add(menu);
			menu = new Menu();
			menu.setId(metaInfo.getMenuId());
			menu.setName(metaInfo.getMenuNm());
			menuList.add(menu);
			menu = new Menu();
			menu.setId(metaInfo.getMetaClsfCd());
			menu.setType(DisplayConstants.DP_META_CLASS_MENU_TYPE);
			menuList.add(menu);

			contributor.setName(metaInfo.getArtist1Nm());
			contributor.setAlbum(metaInfo.getArtist3Nm());

			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_SONG_IDENTIFIER_CD);
			identifier.setText(metaInfo.getMusicId());
			List<Source> mussicSourceList = new ArrayList<Source>();
			source = new Source();
			source.setSize(metaInfo.getFileSize());
			source.setType(DisplayConstants.DP_SOURCE_TYPE_AUDIO_MP3_128);
			mussicSourceList.add(source);
			source = new Source();
			source.setSize(metaInfo.getFileSizeH());
			source.setType(DisplayConstants.DP_SOURCE_TYPE_AUDIO_MP3_192);
			mussicSourceList.add(source);
			music.setIdentifier(identifier);
			music.setSourceList(sourceList);

			/*
			 * Rights grade
			 */
			rights.setGrade(metaInfo.getProdGrdCd());

			// 구매 정보
			if (StringUtils.isNotEmpty(prchsId)) {
				purchase.setState(prchsState);
				List<Identifier> purchaseIdentifierList = new ArrayList<Identifier>();

				identifier = new Identifier();
				identifier.setType(DisplayConstants.DP_PURCHASE_IDENTIFIER_CD);
				identifier.setText(prchsId);
				purchaseIdentifierList.add(identifier);

				identifier = new Identifier();
				identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
				identifier.setText(prchsProdId);
				purchaseIdentifierList.add(identifier);

				purchase.setIdentifierList(purchaseIdentifierList);

				date = new Date();
				date.setType("date/purchase");
				date.setText(DateUtils.parseDate(prchsDt));
				purchase.setDate(date);
				product.setPurchase(purchase);

			}

			product = new Product();
			product.setIdentifierList(identifierList);
			product.setTitle(title);
			product.setSourceList(sourceList);
			product.setMenuList(menuList);
			product.setContributor(contributor);
			product.setMusic(music);
			product.setRights(rights);
			product.setPurchase(purchase);

			commonResponse.setTotalCount(1);

		} else {
			// dummy data를 호출할때
			menuList = new ArrayList<Menu>();
			sourceList = new ArrayList<Source>();
			identifierList = new ArrayList<Identifier>();

			product = new Product();
			identifier = new Identifier();
			rights = new Rights();
			source = new Source();
			title = new Title();
			purchase = new Purchase();
			contributor = new Contributor();
			music = new Music();

			// 상품ID
			identifier = new Identifier();
			identifier.setType("episode");
			identifier.setText("H001601609");
			identifierList.add(identifier);
			// identifier = new Identifier();
			// identifier.setType("isPartOf");
			// identifier.setText("H001601608");
			// identifierList.add(identifier);

			title.setText("조금 이따 샤워해 (Feat. Crush)");

			/*
			 * source mediaType, size, type, url
			 */
			source.setMediaType("image/png");
			source.setType("thumbnail");
			source.setUrl("http://wap.tstore.co.kr/SMILE_DATA7/PMUSIC/201401/20/0002112826/6/0003940962/6/10_0002112826_200_200_1709_200x200_R130x130.PNG");
			sourceList.add(source);

			/*
			 * Menu(메뉴정보) Id, Name, Type
			 */
			menu = new Menu();
			menu.setId("DP000516");
			menu.setName("뮤직");
			menu.setType("topClass");
			menuList.add(menu);
			menu = new Menu();
			menu.setId("DP16002002");
			menu.setName("가요");
			menu = new Menu();
			menu.setId("CT25");
			menu.setType("metaClass");
			menuList.add(menu);

			contributor.setName("Gary");
			contributor.setAlbum("MR.GAE");

			identifier = new Identifier();
			identifier.setType("download");
			identifier.setText("4397969");
			List<Source> musicSourceList = new ArrayList<Source>();
			source = new Source();
			source.setSize(3842715);
			source.setType("audio/mp3-128");
			musicSourceList.add(source);
			source = new Source();
			source.setSize(5764074);
			source.setType("audio/mp3-192");
			musicSourceList.add(source);
			music.setIdentifier(identifier);
			music.setSourceList(musicSourceList);

			/*
			 * Rights grade
			 */
			rights.setGrade("PD004401");

			purchase.setState("payment");
			List<Identifier> purchaseIdentifierList = new ArrayList<Identifier>();

			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_PURCHASE_IDENTIFIER_CD);
			identifier.setText("MI100000000000044286");
			purchaseIdentifierList.add(identifier);

			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
			identifier.setText("0000395599");
			purchaseIdentifierList.add(identifier);

			purchase.setIdentifierList(purchaseIdentifierList);

			date = new Date();
			date.setType("date/purchase");
			date.setText("20130722143732");
			purchase.setDate(date);

			product = new Product();
			product.setIdentifierList(identifierList);
			product.setTitle(title);
			product.setSourceList(sourceList);
			product.setMenuList(menuList);
			product.setContributor(contributor);
			product.setMusic(music);
			product.setRights(rights);
			product.setPurchase(purchase);

			commonResponse.setTotalCount(1);
		}
		response.setCommonResponse(commonResponse);
		response.setProduct(product);
		return response;
	}
}
