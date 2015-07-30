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

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
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
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
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
	@Autowired
	private DisplayCommonService commonService;

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
		if (StringUtils.isNotEmpty(metaInfo.getIssueDay())) {
			contributor.setDate(this.commonGenerator.generateDateString(DisplayConstants.DP_DATE_RELEASE,
					metaInfo.getIssueDay()));
		}

		contributor.setDirector(metaInfo.getArtist2Nm()); // 감독
		contributor.setCompany(metaInfo.getChnlCompNm()); // 제공업체
		contributor.setAgency(metaInfo.getAgencyNm()); // 기획사
		contributor.setChannel(metaInfo.getAgencyNm()); // 방송사

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
		if (StringUtils.isNotEmpty(metaInfo.getIssueDay())) {
			contributor.setDate(this.commonGenerator.generateDateString(DisplayConstants.DP_DATE_RELEASE,
					metaInfo.getIssueDay()));
		}
		contributor.setCompany(metaInfo.getChnlCompNm()); // 제공업체
		contributor.setAgency(metaInfo.getAgencyNm()); // 기획사

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
		return this.generateVod(metaInfo, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.VodGenerator#generateVod(com.skplanet.storeplatform.sac.display
	 * .meta.vo.MetaInfo)
	 */
	@Override
	public Vod generateVod(MetaInfo metaInfo, boolean supportFhdVideo) {
		Vod vod = new Vod();
		Chapter chapter = new Chapter();
		Time time = new Time();
		time.setText(metaInfo.getEpsdPlayTm());
		chapter.setUnit(this.commonService.getVodChapterUnit());
		if (StringUtils.isNotEmpty(metaInfo.getChapter())) {
			chapter.setText(Integer.parseInt(metaInfo.getChapter()));
		}
		if (StringUtils.isNotEmpty(metaInfo.getMetaClsfCd())
				&& !DisplayConstants.DP_VOD_SHORT_STORY_CLASS_CD.equals(metaInfo.getMetaClsfCd())) {
			vod.setChapter(chapter);
		}
		vod.setRunningTime(time);
		vod.setVideoInfoList(this.generateVideoInfoList(metaInfo, supportFhdVideo));
		vod.setSupportList(this.generateSupportStorePlay(metaInfo));
		return vod;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param metaInfo
	 * @return
	 */
	private List<Support> generateSupportStorePlay(MetaInfo metaInfo) {
		List<Support> supportList = new ArrayList<Support>();
		Support support = null;
		if ("Y".equals(metaInfo.getSupportPlay())) {
			support = this.commonGenerator.generateSupport(DisplayConstants.DP_EBOOK_PLAY_SUPPORT_NM, "Y");
			supportList.add(support);
		} else {
			support = this.commonGenerator.generateSupport(DisplayConstants.DP_EBOOK_PLAY_SUPPORT_NM, "N");
			supportList.add(support);
		}

		if ("Y".equals(metaInfo.getSupportStore())) {
			support = this.commonGenerator.generateSupport(DisplayConstants.DP_EBOOK_STORE_SUPPORT_NM, "Y");
			supportList.add(support);
		} else {
			support = this.commonGenerator.generateSupport(DisplayConstants.DP_EBOOK_STORE_SUPPORT_NM, "N");
			supportList.add(support);
		}
		return supportList;
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
		supportList
				.add(this.commonGenerator.generateSupport(DisplayConstants.DP_VOD_HD_SUPPORT_NM, metaInfo.getHdvYn()));
		supportList.add(this.commonGenerator.generateSupport(DisplayConstants.DP_VOD_DOLBY_SUPPORT_NM,
				metaInfo.getDolbySprtYn()));
		if (DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD.equals(metaInfo.getContentsTypeCd()))
			supportList.add(new Support(DisplayConstants.DP_DRM_SUPPORT_NM, metaInfo.getDrmYn()));

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

		// 영화,TV방송에 대한 allow 설정
		if (StringUtils.equals(metaInfo.getDwldAreaLimtYn(), "Y")) {
			rights.setAllow(DisplayConstants.DP_RIGHTS_ALLOW_DOMESTIC);
		}

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
		rights.setPlus19Yn(metaInfo.getPlus19Yn());
		return rights;
	}

	@Override
	public List<VideoInfo> generateVideoInfoList(MetaInfo metaInfo) {
		return this.generateVideoInfoList(metaInfo, false);
	}

	private List<VideoInfo> generateVideoInfoList(MetaInfo metaInfo, boolean supportFhdVideo) {
		VideoInfo videoInfo = null;
		List<VideoInfo> videoInfoList = new ArrayList<VideoInfo>();

		/*
		 * 일반화질 정보
		 */
		if (StringUtils.isNotEmpty(metaInfo.getNmSubContsId())) {
			videoInfo = this.getNmVideoInfo(metaInfo);
			videoInfoList.add(videoInfo);
		}

		/*
		 * SD 고화질 정보
		 */
		if (StringUtils.isNotEmpty(metaInfo.getSdSubContsId())) {
			videoInfo = this.getSdVideoInfo(metaInfo);
			videoInfoList.add(videoInfo);
		}

		/*
		 * 4.x I/F 일때
		 */

		if (supportFhdVideo) {
			if (StringUtils.isNotEmpty(metaInfo.getHdSubContsId())) {
				videoInfo = this.getHdVideoInfoV2(metaInfo);
				videoInfoList.add(videoInfo);
			}

			if (StringUtils.isNotEmpty(metaInfo.getHihdSubContsId())) {
				videoInfo = this.getHiHdVideoInfo(metaInfo);
				videoInfoList.add(videoInfo);
			}

		} else {
			/*
			 * 고화질 정보(3.x I/F 일때) HD 화질 정보와 HIHD 화질정보 동시에 존재 할때에는 HIHD 화질이 우선적으로 내려가도록
			 */
			if (StringUtils.isNotEmpty(metaInfo.getHdSubContsId())
					|| StringUtils.isNotEmpty(metaInfo.getHihdSubContsId())) {
				videoInfo = this.getHdVideoInfo(metaInfo);
				videoInfoList.add(videoInfo);
			}
		}

		// FHD 정보는 조회하고 있으나 규격에 내려주지 않음 update by 2015.07.30 이석희
		// if (supportFhdVideo) {
		// if (StringUtils.isNotEmpty(metaInfo.getFhdSubContsId())) {
		// videoInfo = this.getFhdVideoInfo(metaInfo);
		// videoInfoList.add(videoInfo);
		// }
		// }

		return videoInfoList;
	}

	@Override
	public VideoInfo getNmVideoInfo(MetaInfo metaInfo) {
		VideoInfo videoInfo = new VideoInfo();
		videoInfo.setPictureSize(metaInfo.getNmDpPicRatio());
		videoInfo.setPixel(metaInfo.getNmDpPixel());
		videoInfo.setScid(metaInfo.getNmSubContsId());
		videoInfo.setSize(metaInfo.getNmFileSize());
		videoInfo.setType(DisplayConstants.DP_VOD_QUALITY_NORMAL);
		videoInfo.setVersion(metaInfo.getNmProdVer());
		videoInfo.setFilePath(metaInfo.getNmFilePath());
		return videoInfo;
	}

	@Override
	public VideoInfo getSdVideoInfo(MetaInfo metaInfo) {
		VideoInfo videoInfo = new VideoInfo();
		// videoInfo.setBtvcid(metaInfo.getSdBtvCid());
		videoInfo.setPictureSize(metaInfo.getSdDpPicRatio());
		videoInfo.setPixel(metaInfo.getSdDpPixel());
		videoInfo.setScid(metaInfo.getSdSubContsId());
		videoInfo.setSize(metaInfo.getSdFileSize());
		videoInfo.setType(DisplayConstants.DP_VOD_QUALITY_SD);
		videoInfo.setVersion(metaInfo.getSdProdVer());
		videoInfo.setFilePath(metaInfo.getSdFilePath());
		return videoInfo;
	}

	/**
	 * HD 고화질 정보 HD2 (D화질) 우선, 없으면 HD 정보 노출
	 * 
	 * @param metaInfo
	 */
	@Override
	public VideoInfo getHdVideoInfo(MetaInfo metaInfo) {
		VideoInfo videoInfo = new VideoInfo();

		if (StringUtils.isNotEmpty(metaInfo.getHihdSubContsId())) {
			videoInfo.setType(DisplayConstants.DP_VOD_QUALITY_HIHD);
			videoInfo.setPictureSize(metaInfo.getHihdDpPicRatio());
			videoInfo.setPixel(metaInfo.getHihdDpPixel());
			videoInfo.setScid(metaInfo.getHihdSubContsId());
			videoInfo.setSize(metaInfo.getHihdFileSize());
			videoInfo.setVersion(metaInfo.getHihdProdVer());
			videoInfo.setFilePath(metaInfo.getHihdFilePath());
		} else {
			videoInfo.setType(DisplayConstants.DP_VOD_QUALITY_HD);
			videoInfo.setPictureSize(metaInfo.getHdDpPicRatio());
			videoInfo.setPixel(metaInfo.getHdDpPixel());
			videoInfo.setScid(metaInfo.getHdSubContsId());
			videoInfo.setSize(metaInfo.getHdFileSize());
			videoInfo.setVersion(metaInfo.getHdProdVer());
			videoInfo.setFilePath(metaInfo.getHiFilePath());
		}

		return videoInfo;
	}

	@Override
	public VideoInfo getFhdVideoInfo(MetaInfo metaInfo) {
		VideoInfo videoInfo = new VideoInfo();
		videoInfo.setPictureSize(metaInfo.getFhdDpPicRatio());
		videoInfo.setPixel(metaInfo.getFhdDpPixel());
		videoInfo.setScid(metaInfo.getFhdSubContsId());
		videoInfo.setSize(metaInfo.getFhdFileSize());
		videoInfo.setType(DisplayConstants.DP_VOD_QUALITY_FHD);
		videoInfo.setVersion(metaInfo.getFhdProdVer());
		videoInfo.setFilePath(metaInfo.getFhdFilePath());
		return videoInfo;
	}

	@Override
	public Price generateVodPrice(MetaInfo metaInfo) {
		Price price = new Price();
		price.setText(metaInfo.getProdAmt());
		price.setFixedPrice(metaInfo.getProdNetAmt());
		price.setUnlmtAmt(metaInfo.getUnlmtAmt());
		price.setPeriodAmt(metaInfo.getPeriodAmt());
		return price;
	}

	/**
	 * HD 고화질 정보(4.x I/F)
	 * 
	 * @param metaInfo
	 */
	@Override
	public VideoInfo getHdVideoInfoV2(MetaInfo metaInfo) {
		VideoInfo videoInfo = new VideoInfo();

		videoInfo.setType(DisplayConstants.DP_VOD_QUALITY_HD);
		videoInfo.setPictureSize(metaInfo.getHdDpPicRatio());
		videoInfo.setPixel(metaInfo.getHdDpPixel());
		videoInfo.setScid(metaInfo.getHdSubContsId());
		videoInfo.setSize(metaInfo.getHdFileSize());
		videoInfo.setVersion(metaInfo.getHdProdVer());
		videoInfo.setFilePath(metaInfo.getHiFilePath());

		return videoInfo;
	}

	/**
	 * HIHD 고화질 정보(4.x I/F)
	 * 
	 * @param metaInfo
	 */
	@Override
	public VideoInfo getHiHdVideoInfo(MetaInfo metaInfo) {
		VideoInfo videoInfo = new VideoInfo();

		videoInfo.setType(DisplayConstants.DP_VOD_QUALITY_HIHD);
		videoInfo.setPictureSize(metaInfo.getHihdDpPicRatio());
		videoInfo.setPixel(metaInfo.getHihdDpPixel());
		videoInfo.setScid(metaInfo.getHihdSubContsId());
		videoInfo.setSize(metaInfo.getHihdFileSize());
		videoInfo.setVersion(metaInfo.getHihdProdVer());
		videoInfo.setFilePath(metaInfo.getHihdFilePath());
		return videoInfo;
	}
}
