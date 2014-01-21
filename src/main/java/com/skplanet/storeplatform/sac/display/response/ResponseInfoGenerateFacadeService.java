package com.skplanet.storeplatform.sac.display.response;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

public interface ResponseInfoGenerateFacadeService {

	public Product generateAppProduct(MetaInfo metaInfo);

	public Product generateMusicProduct(MetaInfo metaInfo);

	public Product generateMovieProduct(MetaInfo metaInfo);

	public Product generateBroadcastProduct(MetaInfo metaInfo);

	public Product generateEbookProduct(MetaInfo metaInfo);

	public Product generateComicProduct(MetaInfo metaInfo);

	public Product generateShoppingProduct(MetaInfo metaInfo);
}
