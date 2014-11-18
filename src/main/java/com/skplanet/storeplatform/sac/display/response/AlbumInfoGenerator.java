package com.skplanet.storeplatform.sac.display.response;

import java.util.List;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.display.cache.vo.AlbumMeta;

/**
 * 앨범 메타 생성 클래스
 * 
 * Updated on : 2014. 10. 23.
 * Updated by : 김희민
 */
public interface AlbumInfoGenerator {
	public List<Identifier> generateIdentifierList(AlbumMeta albumMeta);
	public Title generateTitle(AlbumMeta albumMeta);
	public List<Menu> generateMenuList(AlbumMeta albumMeta);
	public List<Source> generateSourceList(AlbumMeta albumMeta);
	public Rights generateRights(AlbumMeta albumMeta);
	public Contributor generateContributor(AlbumMeta albumMeta);
	public List<Date> generateDateList(AlbumMeta albumMeta);
}
