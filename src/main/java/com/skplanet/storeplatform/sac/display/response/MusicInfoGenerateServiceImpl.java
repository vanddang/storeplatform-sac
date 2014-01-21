package com.skplanet.storeplatform.sac.display.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.api.conts.DisplayConstants;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Music;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

@org.springframework.stereotype.Service
@Transactional
public class MusicInfoGenerateServiceImpl implements MusicInfoGenerateService {
	@Override
	public Accrual generateAccrual(MetaInfo metaInfo) {
		Accrual accrual = new Accrual();
		accrual.setChangeRank(metaInfo.getRankChgCnt());
		return accrual;
	}

	@Override
	public Service generateService(String name, String type) {
		Service service = new Service();
		service.setName(name);
		service.setType(type);
		return service;
	}

	@Override
	public Music generateMusic(MetaInfo metaInfo) {
		Music music = new Music();
		List<Service> serviceList = new ArrayList<Service>();
		Service service = this.generateService(DisplayConstants.DP_MUSIC_SERVICE_MP3, metaInfo.getMp3Sprt());
		serviceList.add(service);
		service = this.generateService(DisplayConstants.DP_MUSIC_SERVICE_BELL, metaInfo.getBellSprt());
		serviceList.add(service);
		service = this.generateService(DisplayConstants.DP_MUSIC_SERVICE_RING, metaInfo.getRingSprt());
		serviceList.add(service);
		music.setServiceList(serviceList);
		return music;
	}

	@Override
	// TODO osm1021 현재 사용하지 않음 추후 필요없으면 삭제 필요
	public List<Service> generateServiceList(MetaInfo metaInfo) {
		List<Service> serviceList = new ArrayList<Service>();
		Service service = this.generateService(DisplayConstants.DP_MUSIC_SERVICE_MP3, metaInfo.getMp3Sprt());
		serviceList.add(service);
		service = this.generateService(DisplayConstants.DP_MUSIC_SERVICE_BELL, metaInfo.getBellSprt());
		serviceList.add(service);
		service = this.generateService(DisplayConstants.DP_MUSIC_SERVICE_RING, metaInfo.getRingSprt());
		serviceList.add(service);
		return serviceList;
	}

	@Override
	public Contributor generateContributor(MetaInfo metaInfo) {
		Contributor contributor = new Contributor();
		contributor.setName(metaInfo.getArtist1Nm()); // 가수
		contributor.setAlbum(metaInfo.getArtist3Nm()); // 앨범명
		contributor.setPublisher(metaInfo.getChnlCompNm()); // 발행인
		contributor.setAgency(metaInfo.getAgencyNm()); // 에이전시
		return contributor;
	}
}
