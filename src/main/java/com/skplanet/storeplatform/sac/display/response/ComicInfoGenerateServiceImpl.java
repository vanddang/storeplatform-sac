package com.skplanet.storeplatform.sac.display.response;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

@Service
@Transactional
public class ComicInfoGenerateServiceImpl implements ComicInfoGenerateService {
	@Override
	public Contributor generateContributor(MetaInfo metaInfo) {
		Contributor contributor = new Contributor();
		contributor.setName(""); // 제목
		contributor.setPainter(""); // 작가
		contributor.setPublisher(""); // 출판사
		return contributor;
	}
}
