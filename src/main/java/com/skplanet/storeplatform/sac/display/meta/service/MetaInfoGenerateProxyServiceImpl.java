package com.skplanet.storeplatform.sac.display.meta.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.api.conts.DisplayConstants;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
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
	public Product generateAppProductProxy(MetaInfo metaInfo) {
		Product product = new Product();

		Identifier identifier = this.generateIdentifier(metaInfo);
		// TODO osm1021 supprotList 추가 필요
		Title title = this.generateTitle(metaInfo);
		Price price = this.generatePrice(metaInfo);
		List<Menu> menuList = this.generateAppMenuList(metaInfo);
		List<Source> sourceList = this.generateSourceList(metaInfo);
		Accrual accrual = this.generateAccrual(metaInfo);
		Rights rights = this.generateRights(metaInfo);
		App app = this.generateApp(metaInfo);

		product.setIdentifier(identifier);
		product.setTitle(title);
		product.setApp(app);
		product.setPrice(price);
		product.setAccrual(accrual);
		product.setSourceList(sourceList);
		product.setProductExplain(metaInfo.getProdBaseDesc());
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
		// MenuList 생성
		List<Menu> menuList = this.generateMenuList(metaInfo);
		// SourceList 생성
		List<Source> sourceList = this.generateSourceList(metaInfo);
		// Music용 Accrual 설정
		Accrual accrual = this.generateMusicAccrual(metaInfo);
		// Rights 설정
		Rights rights = this.generateRights(metaInfo);
		// Music용 Contributor 설정
		Contributor contributor = this.generateMusicContributor(metaInfo);
		// Music 생성
		Music music = this.generateMusicProxy(metaInfo);

		product.setIdentifier(identifier);
		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setMusic(music);
		return product;
	}

	@Override
	public Product generateMovieProductProxy(MetaInfo metaInfo) {
		Product product = new Product();
		// Identifier 설정
		Identifier identifier = this.generateIdentifier(metaInfo);
		// TODO osm1021 supoortList 처리 필요
		// Title 생성
		Title title = this.generateTitle(metaInfo);
		// Price 생성
		Price price = this.generatePrice(metaInfo);
		// MenuList 생성
		List<Menu> menuList = this.generateMenuList(metaInfo);
		// SourceList 생성
		List<Source> sourceList = this.generateSourceList(metaInfo);
		// Accrual 생성
		Accrual accrual = this.generateAccrual(metaInfo);
		// Rights 설정
		Rights rights = this.generateRights(metaInfo);
		// 영화용 Contributor 설정
		Contributor contributor = this.generateMovieContributor(metaInfo);

		product.setIdentifier(identifier);
		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setProductExplain(metaInfo.getProdBaseDesc());

		return product;
	}

	@Override
	public Product generateTVProductProxy(MetaInfo metaInfo) {
		Product product = new Product();
		Identifier identifier = this.generateIdentifier(metaInfo);
		// TODO osm1021 supoortList 처리 필요
		// Title 생성
		Title title = this.generateTitle(metaInfo);
		// Price 생성
		Price price = this.generatePrice(metaInfo);
		// MenuList 생성
		List<Menu> menuList = this.generateMenuList(metaInfo);
		// SourceList 생성
		List<Source> sourceList = this.generateSourceList(metaInfo);
		// Accrual 생성
		Accrual accrual = this.generateAccrual(metaInfo);
		// Rights 설정
		Rights rights = this.generateRights(metaInfo);
		// 방송용 Contributor 설정
		Contributor contributor = this.generateTVContributor(metaInfo);

		product.setIdentifier(identifier);
		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setProductExplain(metaInfo.getProdBaseDesc());
		return product;
	}

	@Override
	public Product generateEbookProductProxy(MetaInfo metaInfo) {
		Product product = new Product();
		Identifier identifier = this.generateIdentifier(metaInfo);
		// TODO osm1021 supoortList 처리 필요
		// Title 생성
		Title title = this.generateTitle(metaInfo);
		// Price 생성
		Price price = this.generatePrice(metaInfo);
		// MenuList 생성
		List<Menu> menuList = this.generateMenuList(metaInfo);
		// SourceList 생성
		List<Source> sourceList = this.generateSourceList(metaInfo);
		// Accrual 생성
		Accrual accrual = this.generateAccrual(metaInfo);
		// Rights 설정
		Rights rights = this.generateRights(metaInfo);
		// Ebook용 Contributor 설정
		Contributor contributor = this.generateEbookContributor(metaInfo);

		product.setIdentifier(identifier);
		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setProductExplain(metaInfo.getProdBaseDesc());
		return product;
	}

	@Override
	public Product generateComicProductProxy(MetaInfo metaInfo) {
		Product product = new Product();
		Identifier identifier = this.generateIdentifier(metaInfo);
		// TODO osm1021 supoortList 처리 필요
		// Title 생성
		Title title = this.generateTitle(metaInfo);
		// Price 생성
		Price price = this.generatePrice(metaInfo);
		// MenuList 생성
		List<Menu> menuList = this.generateMenuList(metaInfo);
		// SourceList 생성
		List<Source> sourceList = this.generateSourceList(metaInfo);
		// Accrual 생성
		Accrual accrual = this.generateAccrual(metaInfo);
		// Rights 설정
		Rights rights = this.generateRights(metaInfo);
		// Comic용 Contributor 설정
		Contributor contributor = this.generateComicContributor(metaInfo);

		product.setIdentifier(identifier);
		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		// TODO osm1021 productExplain 설정 필요
		product.setProductExplain(metaInfo.getProdBaseDesc());
		return product;
	}

	@Override
	public Product generateShoppingProductProxy(MetaInfo metaInfo) {
		Product product = new Product();
		return product;
	}

	@Override
	public Identifier generateIdentifier(MetaInfo metaInfo) {
		String contentsTypeCd = metaInfo.getContentsTypeCd();
		Identifier identifier = new Identifier();
		if (DisplayConstants.DP_EPISODE_IDENTIFIER_CD.equals(contentsTypeCd)) {
			identifier.setType(DisplayConstants.DP_EPISODE_IDENTIFIER_CD);
		} else if (DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD.equals(contentsTypeCd)
				&& DisplayConstants.DP_SHOPPING_COUPON_TOP_MENU_ID.equals(metaInfo.getTopMenuId())) {
			identifier.setType(DisplayConstants.DP_CATALOG_IDENTIFIER_CD);
		} else if (DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD.equals(contentsTypeCd)) {
			identifier.setType(DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD);
		}
		identifier.setText(metaInfo.getProdId());
		return identifier;
	}

	@Override
	public List<Menu> generateMenuList(MetaInfo metaInfo) {
		List<Menu> menuList = this.generateAppMenuList(metaInfo);

		Menu menu = new Menu();
		menu.setType(DisplayConstants.DP_META_CLASS_MENU_TYPE);
		menu.setId(metaInfo.getMetaClsfCd());
		menuList.add(menu);
		return menuList;
	}

	@Override
	public List<Menu> generateAppMenuList(MetaInfo metaInfo) {
		Menu menu = new Menu();
		List<Menu> menuList = new ArrayList<Menu>();

		menu.setId(metaInfo.getMenuId());
		menu.setName(metaInfo.getMenuNm());
		menuList.add(menu);

		menu = new Menu();
		menu.setType(DisplayConstants.DP_MENU_TOPCLASS_TYPE);
		menu.setId(metaInfo.getTopMenuId());
		menu.setName(metaInfo.getTopMenuNm());
		menuList.add(menu);
		return menuList;
	}

	@Override
	public Source generateSource(MetaInfo metaInfo) {
		Source source = new Source();
		source.setType(DisplayConstants.DP_THUMNAIL_SOURCE);
		source.setMediaType(DisplayCommonUtil.getMimeType(metaInfo.getFilePath()));
		source.setUrl(metaInfo.getFilePath());
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
	public Map<String, Object> generateSupportList(MetaInfo metaInfo) {
		return null;
	}

	@Override
	public App generateApp(MetaInfo metaInfo) {
		App app = new App();
		app.setAid(metaInfo.getAid());
		app.setPackageName(metaInfo.getApkPkgNm());
		app.setVersionCode(metaInfo.getApkVer());
		app.setVersion(metaInfo.getProdVer());
		app.setSize(metaInfo.getFileSize());
		return app;
	}

	@Override
	public Price generatePrice(MetaInfo metaInfo) {
		Price price = new Price();
		price.setText(metaInfo.getProdAmt());
		return price;
	}

	@Override
	public Accrual generateAccrual(MetaInfo metaInfo) {
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
	// TODO osm1021 현재 사용하지 않음 추후 필요없으면 삭제 필요
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
	public Music generateMusicProxy(MetaInfo metaInfo) {
		Music music = new Music();
		List<Service> serviceList = new ArrayList<Service>();
		Service service = this.generateService(DisplayConstants.DP_MUSIC_SERVICE_MP3, metaInfo.getMp3Sprt());
		serviceList.add(service);
		service = this.generateService(DisplayConstants.DP_MUSIC_SERVICE_BELL, metaInfo.getBellSprt());
		serviceList.add(service);
		service = this.generateService(DisplayConstants.DP_MUSIC_SERVICE_RING, metaInfo.getRingSprt());
		serviceList.add(service);
		music.setServiceList(serviceList);
		return music;
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
	public Contributor generateMovieContributor(MetaInfo metaInfo) {
		Contributor contributor = new Contributor();
		contributor.setDirector(""); // 제작자
		contributor.setArtist(""); // 출연자
		Date date = new Date();
		date.setText("");
		contributor.setDate(date);
		return contributor;
	}

	@Override
	public Contributor generateTVContributor(MetaInfo metaInfo) {
		Contributor contributor = new Contributor();
		contributor.setArtist(""); // 출연자
		return contributor;
	}

	@Override
	public Contributor generateEbookContributor(MetaInfo metaInfo) {
		Contributor contributor = new Contributor();
		contributor.setName(""); // 제목
		contributor.setPublisher(""); // 출판사
		Date date = new Date();
		date.setText(""); // 출판년도
		contributor.setDate(date);
		return contributor;
	}

	@Override
	public Contributor generateComicContributor(MetaInfo metaInfo) {
		Contributor contributor = new Contributor();
		contributor.setName(""); // 제목
		contributor.setPainter(""); // 작가
		contributor.setPublisher(""); // 출판사
		return contributor;
	}
}
