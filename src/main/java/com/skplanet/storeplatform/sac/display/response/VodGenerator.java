package com.skplanet.storeplatform.sac.display.response;

import java.util.List;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Vod;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

public interface VodGenerator {
	public Contributor generateBroadcastContributor(MetaInfo metaInfo);

	public Contributor generateMovieContributor(MetaInfo metaInfo);

	public Vod generateVod(MetaInfo metaInfo);

	public List<Support> generateSupportList(MetaInfo metaInfo);

}
