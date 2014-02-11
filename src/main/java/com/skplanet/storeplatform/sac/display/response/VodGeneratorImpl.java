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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Time;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Chapter;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Play;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Store;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.VideoInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Vod;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
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
		Time time = new Time();
		time.setText(metaInfo.getEpsdPlayTm());
		chapter.setUnit(metaInfo.getChapter());
		vod.setChapter(chapter);
		vod.setRunningTime(time);
		vod.setVideoInfoList(this.generateVideoInfoList(metaInfo));
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

	@Override
	public List<VideoInfo> generateVideoInfoList(MetaInfo metaInfo) {
		VideoInfo videoInfo = new VideoInfo();
		List<VideoInfo> videoInfoList = new ArrayList<VideoInfo>();

		/*
		 * 일반화질 정보
		 */
		if (StringUtils.isNotEmpty(metaInfo.getNmBtvCid())) {
			videoInfo.setBtvcid(metaInfo.getNmBtvCid());
			videoInfo.setPictureSize(metaInfo.getNmDpPicRatio());
			videoInfo.setPixel(metaInfo.getNmDpPixel());
			videoInfo.setScid(metaInfo.getNmSubContsId());
			videoInfo.setSize(metaInfo.getNmFileSize().toString());
			videoInfo.setType(DisplayConstants.DP_VOD_QUALITY_NORMAL);
			videoInfo.setVersion(metaInfo.getNmProdVer());
			videoInfoList.add(videoInfo);
		}

		/*
		 * SD 고화질 정보
		 */
		if (StringUtils.isNotEmpty(metaInfo.getSdBtvCid())) {
			videoInfo = new VideoInfo();
			videoInfo.setBtvcid(metaInfo.getSdBtvCid());
			videoInfo.setPictureSize(metaInfo.getSdDpPicRatio());
			videoInfo.setPixel(metaInfo.getSdDpPixel());
			videoInfo.setScid(metaInfo.getSdSubContsId());
			videoInfo.setSize(metaInfo.getSdFileSize().toString());
			videoInfo.setType(DisplayConstants.DP_VOD_QUALITY_SD);
			videoInfo.setVersion(metaInfo.getSdProdVer());
			videoInfoList.add(videoInfo);
		}

		/*
		 * HD 고화질 정보
		 */
		if (StringUtils.isNotEmpty(metaInfo.getHdBtvCid())) {
			videoInfo = new VideoInfo();
			videoInfo.setBtvcid(metaInfo.getHdBtvCid());
			videoInfo.setPictureSize(metaInfo.getHdDpPicRatio());
			videoInfo.setPixel(metaInfo.getHdDpPixel());
			videoInfo.setScid(metaInfo.getHdSubContsId());
			videoInfo.setSize(metaInfo.getHdFileSize().toString());
			videoInfo.setType(DisplayConstants.DP_VOD_QUALITY_HD);
			videoInfo.setVersion(metaInfo.getHdProdVer());
			videoInfoList.add(videoInfo);
		}

		return videoInfoList;
	}
}
