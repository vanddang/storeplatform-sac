package com.skplanet.storeplatform.sac.display.response;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

public interface ComicInfoGenerateService {
	public Contributor generateContributor(MetaInfo metaInfo);
}
