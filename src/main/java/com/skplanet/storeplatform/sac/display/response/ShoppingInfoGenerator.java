package com.skplanet.storeplatform.sac.display.response;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SalesOption;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

public interface ShoppingInfoGenerator {
	public Contributor generateContributor(MetaInfo metaInfo);

	public SalesOption generateSalesOption(MetaInfo metaInfo);

	public Rights generateRights(MetaInfo metaInfo);

	public Accrual generateAccrual(MetaInfo metaInfo);

	public Price generatePrice(MetaInfo metaInfo);
}
