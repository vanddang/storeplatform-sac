/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.sac.api.conts.DisplayConstants;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Chapter;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Play;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Store;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Vod;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * VOD 상품 전용 정보 Generator 구현체
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스
 */
@Component
public class VodGeneratorImpl implements VodGenerator {
	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.VodGenerator#generateBroadcastContributor(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Contributor generateBroadcastContributor(MetaInfo metaInfo) {
		Contributor contributor = new Contributor();
		contributor.setArtist(metaInfo.getArtist1Nm()); // 출연자
		return contributor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.VodGenerator#generateMovieContributor(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Contributor generateMovieContributor(MetaInfo metaInfo) {
		Contributor contributor = new Contributor();
		contributor.setDirector(metaInfo.getArtist2Nm()); // 제작자
		contributor.setArtist(metaInfo.getArtist1Nm()); // 출연자
		Date date = new Date();
		date.setText(metaInfo.getIssueDay());
		contributor.setDate(date);
		return contributor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.VodGenerator#generateVod(com.skplanet.storeplatform.sac.display
	 * .meta.vo.MetaInfo)
	 */
	@Override
	public Vod generateVod(MetaInfo metaInfo) {
		Vod vod = new Vod();
		Chapter chapter = new Chapter();
		chapter.setUnit(metaInfo.getChapter());
		vod.setChapter(chapter);
		return vod;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.VodGenerator#generateSupportList(com.skplanet.storeplatform.sac
	 * .display.meta.vo.MetaInfo)
	 */
	@Override
	public List<Support> generateSupportList(MetaInfo metaInfo) {
		List<Support> supportList = new ArrayList<Support>();
		Support support = this.commonGenerator.generateSupport(DisplayConstants.DP_VOD_HD_SUPPORT_NM,
				metaInfo.getHdvYn());
		supportList.add(support);
		return supportList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateMultimediaRights(com.skplanet
	 * .storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Rights generateRights(MetaInfo metaInfo) {
		Rights rights = new Rights();
		Play play = new Play();
		Store store = new Store();
		Support support = new Support();

		if ("Y".equals(metaInfo.getSupportPlay())) {
			support = this.commonGenerator.generateSupport(DisplayConstants.DP_EBOOK_PLAY_SUPPORT_NM, "Y");
		} else {
			support = this.commonGenerator.generateSupport(DisplayConstants.DP_EBOOK_PLAY_SUPPORT_NM, "N");
		}
		play.setSupport(support);

		support = new Support();
		if ("Y".equals(metaInfo.getSupportStore())) {
			support = this.commonGenerator.generateSupport(DisplayConstants.DP_EBOOK_STORE_SUPPORT_NM, "Y");
		} else {
			support = this.commonGenerator.generateSupport(DisplayConstants.DP_EBOOK_STORE_SUPPORT_NM, "N");
		}
		store.setSupport(support);

		rights.setPlay(play);
		rights.setStore(store);
		rights.setGrade(metaInfo.getProdGrdCd());
		return rights;
	}
}
