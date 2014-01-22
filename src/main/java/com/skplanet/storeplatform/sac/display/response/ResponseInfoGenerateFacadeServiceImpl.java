package com.skplanet.storeplatform.sac.display.response;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SalesOption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

@org.springframework.stereotype.Service
@Transactional
public class ResponseInfoGenerateFacadeServiceImpl implements ResponseInfoGenerateFacadeService {

	@Autowired
	private CommonMetaInfoGenerateService commonService;

	@Autowired
	private AppInfoGenerateService appService;

	@Autowired
	private MusicInfoGenerateService musicService;

	@Autowired
	private MovieInfoGenerateService moviceService;

	@Autowired
	private BroadcastInfoGenerateService boradcastService;

	@Autowired
	private EbookInfoGenerateService ebookService;

	@Autowired
	private ComicInfoGenerateService comicService;

	@Autowired
	private ShoppingInfoGenerateService shoppingService;

	@Override
	public Product generateAppProduct(MetaInfo metaInfo) {
		Product product = new Product();
		Identifier identifier = this.commonService.generateIdentifier(metaInfo);
		// App용 SupportList 설정
		List<Support> supportList = this.appService.generateSupportList(metaInfo);
		// Title 설정
		Title title = this.commonService.generateTitle(metaInfo);
		// App 설정
		App app = this.appService.generateApp(metaInfo);
		// Price 설정
		Price price = this.commonService.generatePrice(metaInfo);
		// Accrual 설정
		Accrual accrual = this.commonService.generateAccrual(metaInfo);
		// SourceList 생성
		List<Source> sourceList = this.commonService.generateSourceList(metaInfo);
		// Rights 설정
		Rights rights = this.commonService.generateRights(metaInfo);
		// App용 MenuList 설정
		List<Menu> menuList = this.appService.generateMenuList(metaInfo);

		product.setIdentifier(identifier);
		product.setSupportList(supportList);
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
	public Product generateMusicProduct(MetaInfo metaInfo) {
		Product product = new Product();
		// Identifier 설정
		Identifier identifier = this.commonService.generateIdentifier(metaInfo);
		// Title 설정
		Title title = this.commonService.generateTitle(metaInfo);
		// Price 설정
		Price price = this.commonService.generatePrice(metaInfo);
		// MenuList 생성
		List<Menu> menuList = this.commonService.generateMenuList(metaInfo);
		// SourceList 생성
		List<Source> sourceList = this.commonService.generateSourceList(metaInfo);
		// Music용 Accrual 설정
		Accrual accrual = this.musicService.generateAccrual(metaInfo);
		// Rights 설정
		Rights rights = this.commonService.generateRights(metaInfo);
		// Music용 Contributor 설정
		Contributor contributor = this.musicService.generateContributor(metaInfo);
		// Music 생성
		Music music = this.musicService.generateMusic(metaInfo);

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
	public Product generateMovieProduct(MetaInfo metaInfo) {
		Product product = new Product();
		// Identifier 설정
		Identifier identifier = this.commonService.generateIdentifier(metaInfo);
		// TODO osm1021 supoortList 처리 필요
		// Title 생성
		Title title = this.commonService.generateTitle(metaInfo);
		// Price 생성
		Price price = this.commonService.generatePrice(metaInfo);
		// MenuList 생성
		List<Menu> menuList = this.commonService.generateMenuList(metaInfo);
		// SourceList 생성
		List<Source> sourceList = this.commonService.generateSourceList(metaInfo);
		// Accrual 생성
		Accrual accrual = this.commonService.generateAccrual(metaInfo);
		// Rights 설정
		Rights rights = this.commonService.generateRights(metaInfo);
		// 영화용 Contributor 설정
		Contributor contributor = this.moviceService.generateContributor(metaInfo);

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
	public Product generateBroadcastProduct(MetaInfo metaInfo) {
		Product product = new Product();
		Identifier identifier = this.commonService.generateIdentifier(metaInfo);
		// TODO osm1021 supoortList 처리 필요
		// Title 생성
		Title title = this.commonService.generateTitle(metaInfo);
		// Price 생성
		Price price = this.commonService.generatePrice(metaInfo);
		// MenuList 생성
		List<Menu> menuList = this.commonService.generateMenuList(metaInfo);
		// SourceList 생성
		List<Source> sourceList = this.commonService.generateSourceList(metaInfo);
		// Accrual 생성
		Accrual accrual = this.commonService.generateAccrual(metaInfo);
		// Rights 설정
		Rights rights = this.commonService.generateRights(metaInfo);
		// 방송용 Contributor 설정
		Contributor contributor = this.boradcastService.generateContributor(metaInfo);

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
	public Product generateEbookProduct(MetaInfo metaInfo) {
		Product product = new Product();
		Identifier identifier = this.commonService.generateIdentifier(metaInfo);
		// TODO osm1021 supoortList 처리 필요
		// Title 생성
		Title title = this.commonService.generateTitle(metaInfo);
		// Price 생성
		Price price = this.commonService.generatePrice(metaInfo);
		// MenuList 생성
		List<Menu> menuList = this.commonService.generateMenuList(metaInfo);
		// SourceList 생성
		List<Source> sourceList = this.commonService.generateSourceList(metaInfo);
		// Accrual 생성
		Accrual accrual = this.commonService.generateAccrual(metaInfo);
		// Rights 설정
		Rights rights = this.commonService.generateRights(metaInfo);
		// Ebook용 Contributor 설정
		Contributor contributor = this.ebookService.generateContributor(metaInfo);

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
	public Product generateComicProduct(MetaInfo metaInfo) {
		Product product = new Product();
		Identifier identifier = this.commonService.generateIdentifier(metaInfo);
		// TODO osm1021 supoortList 처리 필요
		// Title 생성
		Title title = this.commonService.generateTitle(metaInfo);
		// Price 생성
		Price price = this.commonService.generatePrice(metaInfo);
		// MenuList 생성
		List<Menu> menuList = this.commonService.generateMenuList(metaInfo);
		// SourceList 생성
		List<Source> sourceList = this.commonService.generateSourceList(metaInfo);
		// Accrual 생성
		Accrual accrual = this.commonService.generateAccrual(metaInfo);
		// Rights 설정
		Rights rights = this.commonService.generateRights(metaInfo);
		// Comic용 Contributor 설정
		Contributor contributor = this.comicService.generateContributor(metaInfo);

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
	public Product generateShoppingProduct(MetaInfo metaInfo) {
		Product product = new Product();
		Identifier identifier = this.commonService.generateIdentifier(metaInfo);
		// Title 생성
		Title title = this.commonService.generateTitle(metaInfo);
		// Price 생성
		Price price = this.shoppingService.generatePrice(metaInfo);
		// MenuList 생성
		List<Menu> menuList = this.commonService.generateMenuList(metaInfo);
		// SourceList 생성
		List<Source> sourceList = this.commonService.generateSourceList(metaInfo);
		// Accrual 생성
		Accrual accrual = this.shoppingService.generateAccrual(metaInfo);
		// Rights 생성
		Rights rights = this.shoppingService.generateRights(metaInfo);
		// Shopping용 Contributor 생성
		Contributor contributor = this.shoppingService.generateContributor(metaInfo);
		// SalesOption 생성
		SalesOption salesOption = this.shoppingService.generateSalesOption(metaInfo);

		product.setIdentifier(identifier);
		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setSalesOption(salesOption);
		return product;
	}

	@Override
	public Product generateSpecificAppProduct(MetaInfo metaInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product generateSpecificMusicProduct(MetaInfo metaInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product generateSpecificMovieProduct(MetaInfo metaInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product generateSpecificBroadcastProduct(MetaInfo metaInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product generateSpecificEbookProduct(MetaInfo metaInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product generateSpecificComicProduct(MetaInfo metaInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product generateSpecificShoppingProduct(MetaInfo metaInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
