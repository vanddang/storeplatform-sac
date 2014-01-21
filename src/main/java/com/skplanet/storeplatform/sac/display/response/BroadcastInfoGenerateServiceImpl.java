package com.skplanet.storeplatform.sac.display.response;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

@Service
@Transactional
public class BroadcastInfoGenerateServiceImpl implements BroadcastInfoGenerateService {
	@Override
	public Contributor generateContributor(MetaInfo metaInfo) {
		Contributor contributor = new Contributor();
		contributor.setArtist(metaInfo.getArtist1Nm()); // 출연자
		return contributor;
	}
}
