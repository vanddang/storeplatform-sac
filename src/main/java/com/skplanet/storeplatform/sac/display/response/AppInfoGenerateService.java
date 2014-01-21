package com.skplanet.storeplatform.sac.display.response;

import java.util.List;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

public interface AppInfoGenerateService {
	public List<Menu> generateMenuList(MetaInfo metaInfo);

	public List<Support> generateSupportList(MetaInfo metaInfo);

	public App generateApp(MetaInfo metaInfo);

}
