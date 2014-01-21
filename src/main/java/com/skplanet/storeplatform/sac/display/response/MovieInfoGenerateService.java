package com.skplanet.storeplatform.sac.display.response;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

public interface MovieInfoGenerateService {
	public Contributor generateContributor(MetaInfo metaInfo);
}
