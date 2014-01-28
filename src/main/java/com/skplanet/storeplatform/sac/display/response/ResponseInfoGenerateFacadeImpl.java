/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.response;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Book;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Music;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.SalesOption;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * Response 객체 Generate Facade 구현체
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스
 */
@Component
public class ResponseInfoGenerateFacadeImpl implements ResponseInfoGenerateFacade {

	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

	@Autowired
	private AppInfoGenerator appGenerator;

	@Autowired
	private MusicInfoGenerator musicGenerator;

	@Autowired
	private VodGenerator vodGenerator;

	@Autowired
	private EbookComicGenerator ebookComicGenerator;

	@Autowired
	private ShoppingInfoGenerator shoppingGenerator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade#generateAppProduct(com.skplanet.
	 * storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Product generateAppProduct(MetaInfo metaInfo) {
		Product product = new Product();
		Identifier identifier = this.commonGenerator.generateIdentifier(metaInfo);
		// App용 SupportList 설정
		List<Support> supportList = this.appGenerator.generateSupportList(metaInfo);
		// Title 설정
		Title title = this.commonGenerator.generateTitle(metaInfo);
		// App 설정
		App app = this.appGenerator.generateApp(metaInfo);
		// Price 설정
		Price price = this.commonGenerator.generatePrice(metaInfo);
		// Accrual 설정
		Accrual accrual = this.commonGenerator.generateAccrual(metaInfo);
		// SourceList 생성
		List<Source> sourceList = this.commonGenerator.generateSourceList(metaInfo);
		// Rights 설정
		Rights rights = this.commonGenerator.generateRights(metaInfo);
		// App용 MenuList 설정
		List<Menu> menuList = this.appGenerator.generateMenuList(metaInfo);

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade#generateMusicProduct(com.skplanet.
	 * storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Product generateMusicProduct(MetaInfo metaInfo) {
		Product product = new Product();
		// Identifier 설정
		Identifier identifier = this.commonGenerator.generateIdentifier(metaInfo);
		// Title 설정
		Title title = this.commonGenerator.generateTitle(metaInfo);
		// Price 설정
		Price price = this.commonGenerator.generatePrice(metaInfo);
		// MenuList 생성
		List<Menu> menuList = this.commonGenerator.generateMenuList(metaInfo);
		// SourceList 생성
		List<Source> sourceList = this.commonGenerator.generateSourceList(metaInfo);
		// Music용 Accrual 설정
		Accrual accrual = this.musicGenerator.generateAccrual(metaInfo);
		// Rights 설정
		Rights rights = this.commonGenerator.generateRights(metaInfo);
		// Music용 Contributor 설정
		Contributor contributor = this.musicGenerator.generateContributor(metaInfo);
		// Music 생성
		Music music = this.musicGenerator.generateMusic(metaInfo);

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade#generateMovieProduct(com.skplanet.
	 * storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Product generateMovieProduct(MetaInfo metaInfo) {
		Product product = new Product();
		// Identifier 설정
		Identifier identifier = this.commonGenerator.generateIdentifier(metaInfo);
		// Title 생성
		Title title = this.commonGenerator.generateTitle(metaInfo);
		// Price 생성
		Price price = this.commonGenerator.generatePrice(metaInfo);
		// MenuList 생성
		List<Menu> menuList = this.commonGenerator.generateMenuList(metaInfo);
		// SourceList 생성
		List<Source> sourceList = this.commonGenerator.generateSourceList(metaInfo);
		// Accrual 생성
		Accrual accrual = this.commonGenerator.generateAccrual(metaInfo);
		// Rights 설정
		Rights rights = this.commonGenerator.generateRights(metaInfo);
		// 영화용 Contributor 설정
		Contributor contributor = this.vodGenerator.generateMovieContributor(metaInfo);

		product.setIdentifier(identifier);
		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setProductExplain(metaInfo.getProdBaseDesc());
		product.setSupportList(this.vodGenerator.generateSupportList(metaInfo));
		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade#generateBroadcastProduct(com.skplanet
	 * .storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Product generateBroadcastProduct(MetaInfo metaInfo) {
		Product product = new Product();
		Identifier identifier = this.commonGenerator.generateIdentifier(metaInfo);
		// Title 생성
		Title title = this.commonGenerator.generateTitle(metaInfo);
		// Price 생성
		Price price = this.commonGenerator.generatePrice(metaInfo);
		// MenuList 생성
		List<Menu> menuList = this.commonGenerator.generateMenuList(metaInfo);
		// SourceList 생성
		List<Source> sourceList = this.commonGenerator.generateSourceList(metaInfo);
		// Accrual 생성
		Accrual accrual = this.commonGenerator.generateAccrual(metaInfo);
		// Rights 설정
		Rights rights = this.commonGenerator.generateRights(metaInfo);
		// 방송용 Contributor 설정
		Contributor contributor = this.vodGenerator.generateBroadcastContributor(metaInfo);

		product.setIdentifier(identifier);
		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setProductExplain(metaInfo.getProdBaseDesc());
		product.setSupportList(this.vodGenerator.generateSupportList(metaInfo));
		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade#generateEbookProduct(com.skplanet.
	 * storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Product generateEbookProduct(MetaInfo metaInfo) {
		Product product = new Product();
		Identifier identifier = this.commonGenerator.generateIdentifier(metaInfo);
		// Title 생성
		Title title = this.commonGenerator.generateTitle(metaInfo);
		// Price 생성
		Price price = this.commonGenerator.generatePrice(metaInfo);
		// MenuList 생성
		List<Menu> menuList = this.commonGenerator.generateMenuList(metaInfo);
		// SourceList 생성
		List<Source> sourceList = this.commonGenerator.generateSourceList(metaInfo);
		// Accrual 생성
		Accrual accrual = this.commonGenerator.generateAccrual(metaInfo);
		// Rights 설정
		Rights rights = this.commonGenerator.generateRights(metaInfo);
		// Ebook용 Contributor 설정
		Contributor contributor = this.ebookComicGenerator.generateEbookContributor(metaInfo);
		// Book 설정
		Book book = this.ebookComicGenerator.generateBook(metaInfo);

		product.setIdentifier(identifier);
		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setProductExplain(metaInfo.getProdBaseDesc());
		product.setBook(book);
		product.setSupportList(this.ebookComicGenerator.generateSupportList(metaInfo));
		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade#generateComicProduct(com.skplanet.
	 * storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Product generateComicProduct(MetaInfo metaInfo) {
		Product product = new Product();
		Identifier identifier = this.commonGenerator.generateIdentifier(metaInfo);
		// Title 생성
		Title title = this.commonGenerator.generateTitle(metaInfo);
		// Price 생성
		Price price = this.commonGenerator.generatePrice(metaInfo);
		// MenuList 생성
		List<Menu> menuList = this.commonGenerator.generateMenuList(metaInfo);
		// SourceList 생성
		List<Source> sourceList = this.commonGenerator.generateSourceList(metaInfo);
		// Accrual 생성
		Accrual accrual = this.commonGenerator.generateAccrual(metaInfo);
		// Rights 설정
		Rights rights = this.commonGenerator.generateRights(metaInfo);
		// Comic용 Contributor 설정
		Contributor contributor = this.ebookComicGenerator.generateComicContributor(metaInfo);
		// BOOK 설정
		Book book = this.ebookComicGenerator.generateBook(metaInfo);

		product.setIdentifier(identifier);
		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setProductExplain(metaInfo.getProdBaseDesc());
		product.setBook(book);
		product.setSupportList(this.ebookComicGenerator.generateSupportList(metaInfo));
		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade#generateShoppingProduct(com.skplanet
	 * .storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Product generateShoppingProduct(MetaInfo metaInfo) {
		Product product = new Product();
		Identifier identifier = this.commonGenerator.generateIdentifier(metaInfo);
		// Title 생성
		Title title = this.commonGenerator.generateTitle(metaInfo);
		// Price 생성
		Price price = this.shoppingGenerator.generatePrice(metaInfo);
		// MenuList 생성
		List<Menu> menuList = this.commonGenerator.generateMenuList(metaInfo);
		// SourceList 생성
		List<Source> sourceList = this.commonGenerator.generateSourceList(metaInfo);
		// Accrual 생성
		Accrual accrual = this.shoppingGenerator.generateAccrual(metaInfo);
		// Rights 생성
		Rights rights = this.shoppingGenerator.generateRights(metaInfo);
		// Shopping용 Contributor 생성
		Contributor contributor = this.shoppingGenerator.generateContributor(metaInfo);
		// SalesOption 생성
		SalesOption salesOption = this.shoppingGenerator.generateSalesOption(metaInfo);

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade#generateSpecificAppProduct(com.skplanet
	 * .storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Product generateSpecificAppProduct(MetaInfo metaInfo) {
		// TODO osm1021 일단은 기존 App meta 조회와 동일
		Product product = new Product();
		List<Identifier> identifierList = this.appGenerator.generateAppIdentifierList(metaInfo);
		// App용 SupportList 설정
		List<Support> supportList = this.appGenerator.generateSupportList(metaInfo);
		// Title 설정
		Title title = this.commonGenerator.generateTitle(metaInfo);
		// App 설정
		App app = this.appGenerator.generateApp(metaInfo);
		// Price 설정
		Price price = this.commonGenerator.generatePrice(metaInfo);
		// Accrual 설정
		Accrual accrual = this.commonGenerator.generateAccrual(metaInfo);
		// SourceList 생성
		List<Source> sourceList = this.commonGenerator.generateSourceList(metaInfo);
		// Rights 설정
		Rights rights = this.commonGenerator.generateRights(metaInfo);
		// App용 MenuList 설정
		List<Menu> menuList = this.appGenerator.generateMenuList(metaInfo);

		product.setIdentifierList(identifierList);
		product.setSupportList(supportList);
		product.setTitle(title);
		product.setApp(app);
		product.setPrice(price);
		product.setAccrual(accrual);
		product.setSourceList(sourceList);
		product.setRights(rights);
		product.setMenuList(menuList);
		product.setDistributor(this.commonGenerator.generateDistributor(metaInfo));
		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade#generateSpecificMusicProduct(com.skplanet
	 * .storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Product generateSpecificMusicProduct(MetaInfo metaInfo) {
		Product product = new Product();
		List<Identifier> identifierList = this.commonGenerator.generateSpecificProductIdentifierList(metaInfo);
		product.setIdentifierList(identifierList);
		product.setTitle(this.commonGenerator.generateTitle(metaInfo));
		product.setPrice(this.commonGenerator.generatePrice(metaInfo));
		product.setMenuList(this.commonGenerator.generateMenuList(metaInfo));
		product.setSourceList(this.commonGenerator.generateSourceList(metaInfo));
		product.setAccrual(this.commonGenerator.generateAccrual(metaInfo));
		product.setRights(this.commonGenerator.generateRights(metaInfo));
		product.setContributor(this.musicGenerator.generateContributor(metaInfo));
		product.setProductExplain(metaInfo.getProdBaseDesc());
		product.setDistributor(this.commonGenerator.generateDistributor(metaInfo));
		product.setMusic(this.musicGenerator.generateMusic(metaInfo));
		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade#generateSpecificMovieProduct(com.skplanet
	 * .storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Product generateSpecificMovieProduct(MetaInfo metaInfo) {
		Product product = new Product();

		// Identifier 설정
		String contentsTypeCd = metaInfo.getContentsTypeCd();
		List<Identifier> identifierList = this.commonGenerator.generateSpecificProductIdentifierList(metaInfo);
		product.setIdentifierList(identifierList);
		product.setTitle(this.commonGenerator.generateTitle(metaInfo));
		product.setPrice(this.commonGenerator.generatePrice(metaInfo));
		product.setMenuList(this.commonGenerator.generateMenuList(metaInfo));
		product.setSourceList(this.commonGenerator.generateSourceList(metaInfo));
		product.setAccrual(this.commonGenerator.generateAccrual(metaInfo));
		product.setRights(this.vodGenerator.generateRights(metaInfo));
		product.setContributor(this.vodGenerator.generateMovieContributor(metaInfo));
		product.setProductExplain(metaInfo.getProdBaseDesc());
		product.setDistributor(this.commonGenerator.generateDistributor(metaInfo));
		product.setSupportList(this.vodGenerator.generateSupportList(metaInfo));
		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade#generateSpecificBroadcastProduct(com
	 * .skplanet.storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Product generateSpecificBroadcastProduct(MetaInfo metaInfo) {
		Product product = new Product();

		// Identifier 설정
		product.setIdentifierList(this.commonGenerator.generateSpecificProductIdentifierList(metaInfo));
		product.setTitle(this.commonGenerator.generateTitle(metaInfo));
		product.setPrice(this.commonGenerator.generatePrice(metaInfo));
		product.setMenuList(this.commonGenerator.generateMenuList(metaInfo));
		product.setSourceList(this.commonGenerator.generateSourceList(metaInfo));
		product.setAccrual(this.commonGenerator.generateAccrual(metaInfo));
		product.setRights(this.vodGenerator.generateRights(metaInfo));
		product.setContributor(this.vodGenerator.generateBroadcastContributor(metaInfo));
		product.setProductExplain(metaInfo.getProdBaseDesc());
		product.setDistributor(this.commonGenerator.generateDistributor(metaInfo));
		product.setSupportList(this.vodGenerator.generateSupportList(metaInfo));
		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade#generateSpecificEbookProduct(com.skplanet
	 * .storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Product generateSpecificEbookProduct(MetaInfo metaInfo) {
		Product product = new Product();
		String productExplain = metaInfo.getProdBaseDesc();
		List<Identifier> identifierList = this.commonGenerator.generateSpecificProductIdentifierList(metaInfo);
		product.setIdentifierList(identifierList);
		product.setTitle(this.commonGenerator.generateTitle(metaInfo));
		product.setPrice(this.commonGenerator.generatePrice(metaInfo));
		product.setMenuList(this.commonGenerator.generateMenuList(metaInfo));
		product.setSourceList(this.commonGenerator.generateSourceList(metaInfo));
		product.setAccrual(this.commonGenerator.generateAccrual(metaInfo));
		product.setRights(this.commonGenerator.generateRights(metaInfo));
		product.setContributor(this.ebookComicGenerator.generateEbookContributor(metaInfo));
		product.setProductExplain(productExplain);
		product.setDistributor(this.commonGenerator.generateDistributor(metaInfo));
		product.setBook(this.ebookComicGenerator.generateBook(metaInfo));

		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade#generateSpecificComicProduct(com.skplanet
	 * .storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Product generateSpecificComicProduct(MetaInfo metaInfo) {
		Product product = new Product();

		List<Identifier> identifierList = this.commonGenerator.generateSpecificProductIdentifierList(metaInfo);
		product.setIdentifierList(identifierList);
		product.setTitle(this.commonGenerator.generateTitle(metaInfo));
		product.setPrice(this.commonGenerator.generatePrice(metaInfo));
		product.setMenuList(this.commonGenerator.generateMenuList(metaInfo));
		product.setSourceList(this.commonGenerator.generateSourceList(metaInfo));
		product.setAccrual(this.commonGenerator.generateAccrual(metaInfo));
		product.setRights(this.commonGenerator.generateRights(metaInfo));
		product.setContributor(this.ebookComicGenerator.generateComicContributor(metaInfo));
		product.setProductExplain(metaInfo.getProdBaseDesc());
		product.setDistributor(this.commonGenerator.generateDistributor(metaInfo));
		product.setBook(this.ebookComicGenerator.generateBook(metaInfo));
		product.setSupportList(this.ebookComicGenerator.generateSupportList(metaInfo));
		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade#generateSpecificShoppingProduct(com
	 * .skplanet.storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Product generateSpecificShoppingProduct(MetaInfo metaInfo) {
		Product product = new Product();

		// Identifier 설정
		String contentsTypeCd = metaInfo.getContentsTypeCd();
		if (DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD.equals(contentsTypeCd)) {
			List<Identifier> identifierList = this.commonGenerator.generateSpecificProductIdentifierList(metaInfo);
			product.setIdentifierList(identifierList);

		} else if (DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD.equals(contentsTypeCd)) {
			Identifier identifier = this.commonGenerator.generateIdentifier(metaInfo);
			product.setIdentifier(identifier);
		}

		product.setTitle(this.commonGenerator.generateTitle(metaInfo));
		product.setPrice(this.shoppingGenerator.generatePrice(metaInfo));
		product.setMenuList(this.commonGenerator.generateMenuList(metaInfo));
		product.setSourceList(this.commonGenerator.generateSourceList(metaInfo));
		product.setAccrual(this.shoppingGenerator.generateAccrual(metaInfo));
		product.setRights(this.shoppingGenerator.generateRights(metaInfo));
		product.setContributor(this.shoppingGenerator.generateContributor(metaInfo));
		product.setSalesOption(this.shoppingGenerator.generateSalesOption(metaInfo));
		product.setDistributor(this.commonGenerator.generateDistributor(metaInfo));
		return product;
	}

}
