package com.skplanet.storeplatform.sac.display.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.sac.api.conts.DisplayConstants;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Chapter;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Vod;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

@Component
public class VodGeneratorImpl implements VodGenerator {
	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

	@Override
	public Contributor generateBroadcastContributor(MetaInfo metaInfo) {
		Contributor contributor = new Contributor();
		contributor.setArtist(metaInfo.getArtist1Nm()); // 출연자
		return contributor;
	}

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

	@Override
	public Vod generateVod(MetaInfo metaInfo) {
		Vod vod = new Vod();
		Chapter chapter = new Chapter();
		chapter.setUnit(metaInfo.getChapter());
		vod.setChapter(chapter);
		return vod;
	}

	@Override
	public List<Support> generateSupportList(MetaInfo metaInfo) {
		List<Support> supportList = new ArrayList<Support>();
		Support support = this.commonGenerator.generateSupport(DisplayConstants.DP_VOD_HD_SUPPORT_NM,
				metaInfo.getHdvYn());
		supportList.add(support);
		return supportList;
	}
}
