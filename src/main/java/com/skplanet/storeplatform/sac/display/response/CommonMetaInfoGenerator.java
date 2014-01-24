package com.skplanet.storeplatform.sac.display.response;

import java.util.List;
import java.util.Map;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Distributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

public interface CommonMetaInfoGenerator {

	public Identifier generateIdentifier(String type, String text);

	public Identifier generateIdentifier(MetaInfo metaInfo);

	public List<Menu> generateMenuList(MetaInfo metaInfo);

	public Source generateSource(MetaInfo metaInfo);

	public List<Source> generateSourceList(MetaInfo metaInfo);

	public Rights generateRights(MetaInfo metaInfo);

	public Support generateSupport(String type, String text);

	public Map<String, Object> generateSupportList(MetaInfo metaInfo);

	public Price generatePrice(MetaInfo metaInfo);

	public Accrual generateAccrual(MetaInfo metaInfo);

	public Title generateTitle(MetaInfo metaInfo);

	public List<Identifier> generateSpecificProductIdentifierList(MetaInfo metaInfo);

	public Distributor generateDistributor(MetaInfo metaInfo);
}
