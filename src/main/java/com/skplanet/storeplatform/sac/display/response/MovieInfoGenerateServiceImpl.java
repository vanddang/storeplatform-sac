package com.skplanet.storeplatform.sac.display.response;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

@Service
@Transactional
public class MovieInfoGenerateServiceImpl implements MovieInfoGenerateService {
	@Override
	public Contributor generateContributor(MetaInfo metaInfo) {
		Contributor contributor = new Contributor();
		contributor.setDirector(metaInfo.getArtist2Nm()); // 제작자
		contributor.setArtist(metaInfo.getArtist1Nm()); // 출연자
		Date date = new Date();
		date.setText(metaInfo.getIssueDay());
		contributor.setDate(date);
		return contributor;
	}
}
