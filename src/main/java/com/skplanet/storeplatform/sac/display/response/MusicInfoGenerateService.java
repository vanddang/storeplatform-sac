package com.skplanet.storeplatform.sac.display.response;

import java.util.List;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Music;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

public interface MusicInfoGenerateService {
	public Accrual generateAccrual(MetaInfo metaInfo);

	public Service generateService(String name, String type);

	public Music generateMusic(MetaInfo metaInfo);

	public List<Service> generateServiceList(MetaInfo metaInfo);

	public Contributor generateContributor(MetaInfo metaInfo);

}
