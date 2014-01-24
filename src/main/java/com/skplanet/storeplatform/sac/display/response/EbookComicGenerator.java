package com.skplanet.storeplatform.sac.display.response;

import java.util.List;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Book;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

public interface EbookComicGenerator {
	public Contributor generateEbookContributor(MetaInfo metaInfo);

	public Contributor generateComicContributor(MetaInfo metaInfo);

	public Book generateBook(MetaInfo metaInfo);

	public List<Support> generateSupportList(MetaInfo metaInfo);
}
