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

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Music;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 음원 상품 전용 정보 Generator 구현체
 *
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스
 */
@Component
public class MusicInfoGeneratorImpl implements MusicInfoGenerator {

	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.MusicInfoGenerator#generateAccrual(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Accrual generateAccrual(MetaInfo metaInfo) {
		Accrual accrual = new Accrual();
		if (StringUtils.isNotEmpty(metaInfo.getRankChgClsfCd())) {
			if (metaInfo.getRankChgClsfCd().equals("DP005303")) {
				accrual.setChangeRank("-" + metaInfo.getRankChgCnt());
			} else {
				accrual.setChangeRank(metaInfo.getRankChgCnt());
			}
		} else {
			accrual.setChangeRank(metaInfo.getRankChgCnt());
		}
		accrual.setVoterCount(metaInfo.getPaticpersCnt());
		accrual.setDownloadCount(metaInfo.getPrchsCnt());
		accrual.setScore(metaInfo.getAvgEvluScore());
		return accrual;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.skplanet.storeplatform.sac.display.response.MusicInfoGenerator#generateService(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Service generateService(String name, String type) {
		Service service = new Service();
		service.setName(name);
		service.setType(type);
		return service;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.MusicInfoGenerator#generateMusic(com.skplanet.storeplatform.sac
	 * .display.meta.vo.MetaInfo)
	 */
	@Override
	public Music generateMusic(MetaInfo metaInfo) {
		Music music = new Music();
		music.setServiceList(this.generateServiceList(metaInfo));
        music.setDiscNumber(metaInfo.getDiscNo());
        music.setTrackNumber(metaInfo.getTrackNo());
		return music;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.MusicInfoGenerator#generateServiceList(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public List<Service> generateServiceList(MetaInfo metaInfo) {
		List<Service> serviceList = new ArrayList<Service>();
		Service service = this.generateService(DisplayConstants.DP_MUSIC_SERVICE_MP3, metaInfo.getMp3SprtYn());
		serviceList.add(service);
		service = this.generateService(DisplayConstants.DP_MUSIC_SERVICE_BELL, metaInfo.getBellSprtYn());
		serviceList.add(service);
		service = this.generateService(DisplayConstants.DP_MUSIC_SERVICE_RING, metaInfo.getColorringSprtYn());
		serviceList.add(service);
		return serviceList;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.MusicInfoGenerator#generateContributor(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Contributor generateContributor(MetaInfo metaInfo) {
		Contributor contributor = new Contributor();
		if (StringUtils.isNotEmpty(metaInfo.getArtistId())) {
			List<Identifier> identifierList = new ArrayList<Identifier>();
			identifierList.add(this.commonGenerator.generateIdentifier(DisplayConstants.DP_ARTIST_IDENTIFIER_CD,
					metaInfo.getArtistId()));
			contributor.setIdentifierList(identifierList); // 아티스트ID
		}
		contributor.setName(metaInfo.getArtist1Nm()); // 가수
		contributor.setAlbum(metaInfo.getArtist3Nm()); // 앨범명
		contributor.setPublisher(metaInfo.getChnlCompNm()); // 발행인
		contributor.setAgency(metaInfo.getAgencyNm()); // 에이전시
		return contributor;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.MusicInfoGenerator#generateArtistDetailContributor(com.skplanet
	 * .storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Contributor generateArtistDetailContributor(MetaInfo metaInfo) {
		Contributor contributor = new Contributor();
		List<Identifier> identifierList = new ArrayList<Identifier>();
		identifierList.add(this.commonGenerator.generateIdentifier(DisplayConstants.DP_ARTIST_IDENTIFIER_CD,
				metaInfo.getArtistId()));
		contributor.setIdentifierList(identifierList);
		contributor.setName(metaInfo.getArtistNm());
		contributor.setDebutDay(metaInfo.getDebutDay());
		contributor.setDebutMusicNm(metaInfo.getDebutMusicNm());
		contributor.setCountry(metaInfo.getCountry());

		List<Source> sourceList = this.commonGenerator.generateSourceList(metaInfo);
		contributor.setSourceList(sourceList);

		return contributor;
	}
}
