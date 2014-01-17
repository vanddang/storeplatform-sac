package com.skplanet.storeplatform.sac.display.meta.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.api.conts.DisplayConstants;
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
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

@org.springframework.stereotype.Service
@Transactional
public class MetaInfoGenerateProxyServiceImpl implements MetaInfoGenerateProxyService {

	@Override
	public Identifier generateIdentifier(MetaInfo metaInfo) {
		String contentsTypeCd = metaInfo.getContentsTypeCd();
		Identifier identifier = new Identifier();
		if (DisplayConstants.DP_EPISODE_IDENTIFIER_CD.equals(contentsTypeCd)) {
			identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
		} else if (DisplayConstants.DP_CATALOG_IDENTIFIER_CD.equals(contentsTypeCd)) {
			identifier.setType(DisplayConstants.DP_CATALOG_IDENTIFIER_CD);
		} else if (DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD.equals(contentsTypeCd)) {
			identifier.setType(DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
		}
		identifier.setText(metaInfo.getProdId());
		return identifier;
	}

	@Override
	public List<Menu> generateMenuList(MetaInfo metaInfo) {
		Menu menu = new Menu();
		List<Menu> menuList = new ArrayList<Menu>();

		menu.setId(metaInfo.getMenuId());
		menu.setName(metaInfo.getMenuNm());
		menuList.add(menu);

		menu = new Menu();
		menu.setType(DisplayConstants.DP_META_CLASS_MENU_TYPE);
		menu.setId(metaInfo.getMetaClsfCd());
		menuList.add(menu);
		return menuList;
	}

	@Override
	public Source generateSource(MetaInfo metaInfo) {
		Source source = new Source();
		source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
		source.setMediaType(DisplayCommonUtil.getMimeType(metaInfo.getFileNm()));
		source.setUrl(metaInfo.getFilePath() + metaInfo.getFileNm());
		return source;
	}

	@Override
	public List<Source> generateSourceList(MetaInfo metaInfo) {
		List<Source> sourceList = new ArrayList<Source>();
		Source source = this.generateSource(metaInfo);
		sourceList.add(source);
		return sourceList;
	}

	@Override
	public Rights generateRights(MetaInfo metaInfo) {
		Rights rights = new Rights();
		rights.setGrade(metaInfo.getProdGrdCd());
		return rights;
	}

	@Override
	public Support generateSupport(String type, String text) {
		Support support = new Support();
		support.setType(type);
		support.setText(text);
		return support;
	}

	@Override
	public List<Support> generateAppSupportList(MetaInfo metaInfo) {
		List<Support> supportList = new ArrayList<Support>();
		Support support = this.generateSupport(DisplayConstants.DP_DRM_SUPPORT_NM, metaInfo.getDrmYn());
		supportList.add(support);
		support = this.generateSupport(DisplayConstants.DP_IN_APP_SUPPORT_NM, metaInfo.getPartParentClsfCd());
		supportList.add(support);
		return supportList;
	}

	@Override
	public App generateApp(MetaInfo metaInfo) {
		App app = new App();
		app.setAid(metaInfo.getAid());
		app.setPackageName(metaInfo.getApkPkgNm());
		app.setVersionCode(metaInfo.getApkVer());
		app.setVersion(metaInfo.getProdVer());
		return app;
	}

	@Override
	public Price generatePrice(MetaInfo metaInfo) {
		Price price = new Price();
		price.setText(metaInfo.getProdAmt());
		return price;
	}

	@Override
	public Accrual generateAppAccrual(MetaInfo metaInfo) {
		Accrual accrual = new Accrual();
		accrual.setVoterCount(metaInfo.getPaticpersCnt());
		accrual.setDownloadCount(metaInfo.getPrchsCnt());
		accrual.setScore(metaInfo.getAvgEvluScore());

		return accrual;
	}

	@Override
	public Accrual generateMusicAccrual(MetaInfo metaInfo) {
		Accrual accrual = new Accrual();
		accrual.setChangeRank(metaInfo.getRankChgCnt());
		return accrual;
	}

	@Override
	public Title generateTitle(MetaInfo metaInfo) {
		Title title = new Title();
		title.setText(metaInfo.getProdNm());
		return title;
	}

	@Override
	public Service generateService(String name, String type) {
		Service service = new Service();
		service.setName(name);
		service.setType(type);
		return service;
	}

	@Override
	public List<Service> generateMusicServiceList(MetaInfo metaInfo) {
		List<Service> serviceList = new ArrayList<Service>();
		Service service = this.generateService(DisplayConstants.DP_MUSIC_SERVICE_MP3, metaInfo.getMp3Sprt());
		serviceList.add(service);
		service = this.generateService(DisplayConstants.DP_MUSIC_SERVICE_BELL, metaInfo.getBellSprt());
		serviceList.add(service);
		service = this.generateService(DisplayConstants.DP_MUSIC_SERVICE_RING, metaInfo.getRingSprt());
		serviceList.add(service);
		return serviceList;
	}

	@Override
	public Music generateMusicProxy(List<Service> serviceList) {
		Music music = new Music();
		music.setServiceList(serviceList);
		return music;
	}

	@Override
	public Product generateAppProductProxy(MetaInfo metaInfo) {
		Product product = new Product();
		Identifier identifier = this.generateIdentifier(metaInfo);
		Title title = this.generateTitle(metaInfo);
		App app = this.generateApp(metaInfo);
		Price price = this.generatePrice(metaInfo);
		Accrual accrual = this.generateAppAccrual(metaInfo);
		List<Source> sourceList = this.generateSourceList(metaInfo);
		Rights rights = this.generateRights(metaInfo);
		List<Menu> menuList = this.generateMenuList(metaInfo);

		product.setIdentifier(identifier);
		product.setTitle(title);
		product.setApp(app);
		product.setPrice(price);
		product.setAccrual(accrual);
		product.setSourceList(sourceList);
		product.setRights(rights);
		product.setMenuList(menuList);
		return product;
	}

	@Override
	public Product generateMusicProductProxy(MetaInfo metaInfo) {
		Product product = new Product();
		// Identifier 설정
		Identifier identifier = this.generateIdentifier(metaInfo);
		// Title 설정
		Title title = this.generateTitle(metaInfo);
		// Price 설정
		Price price = this.generatePrice(metaInfo);
		// Music용 Accrual 설정
		Accrual accrual = this.generateMusicAccrual(metaInfo);
		// Rights 설정
		Rights rights = this.generateRights(metaInfo);
		// Music용 Contributor 설정
		Contributor contributor = this.generateMusicContributor(metaInfo);
		// SourceList 생성
		List<Source> sourceList = this.generateSourceList(metaInfo);
		// MenuList 생성
		List<Menu> menuList = this.generateMenuList(metaInfo);
		// ServiceList 생성
		List<Service> serviceList = this.generateMusicServiceList(metaInfo);
		// Music 생성
		Music music = this.generateMusicProxy(serviceList);

		product.setIdentifier(identifier);
		product.setTitle(title);
		product.setPrice(price);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setSourceList(sourceList);
		product.setMenuList(menuList);
		product.setMusic(music);
		return product;
	}

	@Override
	public Product generateMovieProductProxy(MetaInfo metaInfo) {
		Product product = new Product();
		return product;
	}

	@Override
	public Product generateBroadCastingProductProxy(MetaInfo metaInfo) {
		Product product = new Product();
		return product;
	}

	@Override
	public Product generateEbookProductProxy(MetaInfo metaInfo) {
		Product product = new Product();
		return product;
	}

	@Override
	public Product generateComicProductProxy(MetaInfo metaInfo) {
		Product product = new Product();
		return product;
	}

	@Override
	public Product generateShoppingProductProxy(MetaInfo metaInfo) {
		Product product = new Product();
		return product;
	}

	@Override
	public Contributor generateMusicContributor(MetaInfo metaInfo) {
		Contributor contributor = new Contributor();
		contributor.setName(metaInfo.getArtist1Nm()); // 가수
		contributor.setAlbum(metaInfo.getArtist3Nm()); // 앨범명
		contributor.setPublisher(metaInfo.getChnlCompNm()); // 발행인
		contributor.setAgency(metaInfo.getAgencyNm()); // 에이전시
		return contributor;
	}

	@Override
	public Service generateService(MetaInfo metaInfo) {
		// TODO Auto-generated method stub
		return null;
	}
}
