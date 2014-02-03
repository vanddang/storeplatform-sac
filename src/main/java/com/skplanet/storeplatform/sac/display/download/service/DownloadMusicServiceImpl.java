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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceItemSc;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScRequest;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScResponse;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadMusicSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadMusicSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Music;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Purchase;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.download.vo.DownloadMusic;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.purchase.history.service.ExistenceSacService;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 1. 22. Updated by : 이석희, 인크로스.
 */
@Service
@Transactional
public class DownloadMusicServiceImpl implements DownloadMusicService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	@Autowired
	private DisplayCommonService commonService;

	@Autowired
	ExistenceSacService existenceSacService;

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
		List<Support> supportList = null;
		List<Source> sourceList = null;

		// 다운로드 Music 상품 조회
		DownloadMusic downloadMusicInfo = null;

		Product product = null;
		Identifier identifier = null;
		Rights rights = null;
		Source source = null;
		Title title = null;
		Menu menu = null;
		Purchase purchase = null;
		Contributor contributor = null;
		Music music = null;

		if (downloadMusicSacReq.getDummy() == null) {
			// dummy 호출이 아닐때
			DownloadMusic downloadMusic = null;

			// 필수 파라미터 체크
			if (StringUtils.isEmpty(productId) || StringUtils.isEmpty(deviceKey) || StringUtils.isEmpty(userKey)) {

				// throw new StorePlatformException("필수 파라미터가 부족합니다.");
				throw new StorePlatformException("필수 파라미터가 부족합니다.", "1", "2", "3");
			}

			// 다운로드 Music 상품 조회
			MetaInfo metaInfo = (MetaInfo) this.commonDAO.queryForObject("Download.getDownloadMusicInfo",
					downloadMusicSacReq);

			this.log.debug("###################################################");
			this.log.debug("metaInfo	:	" + metaInfo);
			this.log.debug("###################################################");
			String prchsId = null;

			try {
				// 기구매 체크를 위한 생성자
				ExistenceScRequest existenceScRequest = new ExistenceScRequest();
				existenceScRequest.setTenantId(downloadMusicSacReq.getTenantId());
				existenceScRequest.setInsdUsermbrNo(downloadMusicSacReq.getUserKey());
				existenceScRequest.setInsdDeviceId(downloadMusicSacReq.getDeviceKey());

				ExistenceItemSc existenceItemSc = new ExistenceItemSc();
				existenceItemSc.setProdId(downloadMusicSacReq.getProductId());

				List<ExistenceItemSc> list = new ArrayList<ExistenceItemSc>();
				list.add(existenceItemSc);
				existenceScRequest.setExistenceItemSc(list);

				// 기구매 체크 실행
				List<ExistenceScResponse> existenceResponseList = this.existenceSacService
						.searchExistenceList(existenceScRequest);

				if (!existenceResponseList.isEmpty()) {
					this.log.debug("----------------------------------------------------------------");
					this.log.debug("구매 상품 ({}", existenceResponseList.toString(), ")");
					this.log.debug("----------------------------------------------------------------");

					prchsId = existenceResponseList.get(0).getPrchsId();
				} else {
					this.log.debug("----------------------------------------------------------------");
					this.log.debug("미구매 상품");
					this.log.debug("----------------------------------------------------------------");
				}
			} catch (Exception e) {
				throw new StorePlatformException("ERROR_0001", "1", "2", "3");
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

			// 상품ID
			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
			identifier.setText(metaInfo.getProdId());
			identifierList.add(identifier);
			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD);
			identifier.setText(metaInfo.getChnlProdId());
			identifierList.add(identifier);

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
			menu = new Menu();
			menu.setId(metaInfo.getMetaClsfCd());
			menu.setType(DisplayConstants.DP_META_CLASS_MENU_TYPE);
			menuList.add(menu);

			contributor.setName(metaInfo.getArtist1Nm());
			contributor.setAlbum(metaInfo.getArtist3Nm());

			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_DOWNLOAD_IDENTIFIER_CD);
			identifier.setText(metaInfo.getOutsdContentsId());
			sourceList = new ArrayList<Source>();
			source = new Source();
			source.setSize(metaInfo.getFileSize());
			source.setType(DisplayConstants.DP_SOURCE_TYPE_AUDIO_MP3_128);
			sourceList.add(source);
			source = new Source();
			source.setSize(metaInfo.getFileSizeH());
			source.setType(DisplayConstants.DP_SOURCE_TYPE_AUDIO_MP3_192);
			sourceList.add(source);
			music.setIdentifier(identifier);
			music.setSourceList(sourceList);

			/*
			 * Rights grade
			 */
			rights.setGrade(metaInfo.getProdGrdCd());

			identifier = new Identifier();
			identifier.setType(DisplayConstants.DP_PURCHASE_IDENTIFIER_CD);
			identifier.setText(prchsId);
			purchase.setIdentifier(identifier);
			purchase.setPurchaseFlag(StringUtils.isNotEmpty(prchsId) ? "payment" : "nonPayment");
			product.setPurchase(purchase);

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
			identifier = new Identifier();
			identifier.setType("isPartOf");
			identifier.setText("H001601608");
			identifierList.add(identifier);

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

			identifier = new Identifier();
			identifier.setType("purchase");
			identifier.setText("GI100000000265812187");
			purchase.setIdentifier(identifier);
			purchase.setPurchaseFlag("payment");

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
