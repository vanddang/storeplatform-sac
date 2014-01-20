package com.skplanet.storeplatform.sac.display.meta.service;

import java.util.List;
import java.util.Map;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Music;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Service;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

public interface MetaInfoGenerateProxyService {
	public Identifier generateIdentifier(MetaInfo metaInfo);

	public List<Menu> generateMenuList(MetaInfo metaInfo);

	public List<Menu> generateAppMenuList(MetaInfo metaInfo);

	public Source generateSource(MetaInfo metaInfo);

	public List<Source> generateSourceList(MetaInfo metaInfo);

	public Rights generateRights(MetaInfo metaInfo);

	public Support generateSupport(String type, String text);

	public List<Support> generateAppSupportList(MetaInfo metaInfo);

	public Map<String, Object> generateSupportList(MetaInfo metaInfo);

	public App generateApp(MetaInfo metaInfo);

	public Price generatePrice(MetaInfo metaInfo);

	public Accrual generateAccrual(MetaInfo metaInfo);

	public Accrual generateMusicAccrual(MetaInfo metaInfo);

	public Title generateTitle(MetaInfo metaInfo);

	public Contributor generateMovieContributor(MetaInfo metaInfo);

	public Contributor generateTVContributor(MetaInfo metaInfo);

	public Contributor generateEbookContributor(MetaInfo metaInfo);

	public Contributor generateComicContributor(MetaInfo metaInfo);

	public Contributor generateMusicContributor(MetaInfo metaInfo);

	public Product generateAppProductProxy(MetaInfo metaInfo);

	public Product generateMusicProductProxy(MetaInfo metaInfo);

	public Product generateMovieProductProxy(MetaInfo metaInfo);

	public Product generateTVProductProxy(MetaInfo metaInfo);

	public Product generateEbookProductProxy(MetaInfo metaInfo);

	public Product generateComicProductProxy(MetaInfo metaInfo);

	public Product generateShoppingProductProxy(MetaInfo metaInfo);

	public Service generateService(String name, String type);

	public List<Service> generateMusicServiceList(MetaInfo metaInfo);

	public Music generateMusicProxy(MetaInfo metaInfo);

}
