package com.skplanet.storeplatform.sac.display.response;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

@Service
@Transactional
public class EbookInfoGenerateServiceImpl implements EbookInfoGenerateService {
	@Override
	public Contributor generateContributor(MetaInfo metaInfo) {
		Contributor contributor = new Contributor();
		contributor.setName(metaInfo.getArtist1Nm()); // 제목
		contributor.setPainter(metaInfo.getArtist2Nm()); //
		contributor.setPublisher(metaInfo.getChnlCompNm()); // 출판사
		Date date = new Date();
		date.setText(metaInfo.getIssueDay()); // 출판년도
		contributor.setDate(date);
		return contributor;
	}
}
