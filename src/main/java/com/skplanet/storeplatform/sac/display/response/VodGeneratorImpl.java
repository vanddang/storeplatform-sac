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

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Time;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.*;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
		return generateVod(metaInfo, false);
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
		Support support = new Support();
		support = this.commonGenerator.generateSupport(DisplayConstants.DP_VOD_HD_SUPPORT_NM, metaInfo.getHdvYn());
		supportList.add(support);
		support = new Support();
		support = this.commonGenerator.generateSupport(DisplayConstants.DP_VOD_DOLBY_SUPPORT_NM,
				metaInfo.getDolbySprtYn());
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
        return generateVideoInfoList(metaInfo, false);
    }
	private List<VideoInfo> generateVideoInfoList(MetaInfo metaInfo, boolean supportFhdVideo) {
		VideoInfo videoInfo = null;
		List<VideoInfo> videoInfoList = new ArrayList<VideoInfo>();

		/*
		 * 일반화질 정보
		 */
		if (StringUtils.isNotEmpty(metaInfo.getNmSubContsId())) {
            videoInfo = getNmVideoInfo(metaInfo);
			videoInfoList.add(videoInfo);
		}

		/*
		 * SD 고화질 정보
		 */
		if (StringUtils.isNotEmpty(metaInfo.getSdSubContsId())) {
            videoInfo = getSdVideoInfo(metaInfo);
			videoInfoList.add(videoInfo);
		}

		/*
		 * HD 고화질 정보
		 */
		if (StringUtils.isNotEmpty(metaInfo.getHdSubContsId()) || StringUtils.isNotEmpty(metaInfo.getHd2SubContsId())) {
            videoInfo = getHdVideoInfo(metaInfo);
            videoInfoList.add(videoInfo);
        }
		
        if(supportFhdVideo) {
            if (StringUtils.isNotEmpty(metaInfo.getFhdSubContsId())) {
            	videoInfo = getFhdVideoInfo(metaInfo);
            	videoInfoList.add(videoInfo);
            }
        }

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
	 * HD 고화질 정보
	 * HD2 (D화질) 우선, 없으면 HD 정보 노출
	 * @param metaInfo
	 */
    @Override
    public VideoInfo getHdVideoInfo(MetaInfo metaInfo) {
    	VideoInfo videoInfo = new VideoInfo();
    	videoInfo.setType(DisplayConstants.DP_VOD_QUALITY_HD);
    	
    	if (StringUtils.isNotEmpty(metaInfo.getHd2SubContsId())) {
    		videoInfo.setPictureSize(metaInfo.getHd2DpPicRatio());
            videoInfo.setPixel(metaInfo.getHd2DpPixel());
            videoInfo.setScid(metaInfo.getHd2SubContsId());
            videoInfo.setSize(metaInfo.getHd2FileSize());
            videoInfo.setVersion(metaInfo.getHd2ProdVer());
            videoInfo.setFilePath(metaInfo.getHd2FilePath());
    	} else {
            videoInfo.setPictureSize(metaInfo.getHdDpPicRatio());
            videoInfo.setPixel(metaInfo.getHdDpPixel());
            videoInfo.setScid(metaInfo.getHdSubContsId());
            videoInfo.setSize(metaInfo.getHdFileSize());
            videoInfo.setVersion(metaInfo.getHdProdVer());
            videoInfo.setFilePath(metaInfo.getHdFilePath());
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
}
