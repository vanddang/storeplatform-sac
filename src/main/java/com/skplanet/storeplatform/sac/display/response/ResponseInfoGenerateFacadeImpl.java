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

import java.util.ArrayList;
import java.util.List;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.*;
import com.skplanet.storeplatform.sac.display.common.vo.MileageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
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

	@Autowired
	private FreepassInfoGenerator freepassGenerator;

	@Autowired
	private DisplayCommonService commonService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade#generateAppProduct(com.skplanet.
	 * storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Product generateAppProduct(MetaInfo metaInfo) {
		Product product = new Product();
		product.setIdentifierList(this.appGenerator.generateIdentifierList(metaInfo));

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

		// App 판매자 설정
		product.setDistributor(this.commonGenerator.generateDistributor(metaInfo));
		// App 상품설명
		product.setProductExplain(metaInfo.getProdBaseDesc());
		// App 상품상세설명
		product.setProductDetailExplain(metaInfo.getProdDtlDesc());

		product.setSupportList(supportList);
		product.setTitle(title);
		product.setApp(app);
		product.setPrice(price);
		product.setAccrual(accrual);
		product.setSourceList(sourceList);
		product.setRights(rights);
		product.setMenuList(menuList);
		// 판매상태 설정
		product.setSalesStatus(metaInfo.getProdStatusCd());
		// 상품 유/무료 구분
		product.setProdChrgYn(metaInfo.getProdChrg());

        // 마일리지
        appendMileageInfo(metaInfo, product);

		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade#generateAppProductShort(com.skplanet.
	 * storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Product generateAppProductShort(MetaInfo metaInfo) {
		Product product = new Product();
		product.setIdentifierList(this.appGenerator.generateIdentifierList(metaInfo));

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

		product.setSupportList(supportList);
		product.setTitle(title);
		product.setApp(app);
		product.setPrice(price);
		product.setAccrual(accrual);
		product.setSourceList(sourceList);
		product.setRights(rights);
		product.setMenuList(menuList);
		product.setProductExplain(metaInfo.getProdBaseDesc());

        // 마일리지
        appendMileageInfo(metaInfo, product);

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
		product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
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
		// Date 생성
		Date date = this.commonGenerator.generateDate(DisplayConstants.DP_DATE_REG, metaInfo.getRegDt());

		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setMusic(music);
		product.setDate(date);
		// Music 상품상세설명
		product.setProductDetailExplain(metaInfo.getProdDtlDesc());
		// 판매상태 설정
		product.setSalesStatus(metaInfo.getProdStatusCd());

        // 마일리지
        appendMileageInfo(metaInfo, product);

		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade#generateMusicProductShort(com.skplanet
	 * . storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Product generateMusicProductShort(MetaInfo metaInfo) {
		Product product = new Product();
		// Identifier 설정
		product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
		// Title 설정
		Title title = this.commonGenerator.generateTitle(metaInfo);
		// Price 설정
		Price price = this.commonGenerator.generatePrice(metaInfo);
		// MenuList 생성
		List<Menu> menuList = this.commonGenerator.generateMenuList(metaInfo);
		// SourceList 생성
		List<Source> sourceList = this.commonGenerator.generateSourceList(metaInfo);
		// Accrual 설정
		Accrual accrual = this.commonGenerator.generateAccrual(metaInfo);
		// Rights 설정
		Rights rights = this.commonGenerator.generateRights(metaInfo);
		// Music용 Contributor 설정
		Contributor contributor = this.musicGenerator.generateContributor(metaInfo);
		// Music 생성
		Music music = this.musicGenerator.generateMusic(metaInfo);

		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setMusic(music);
		product.setProductExplain(metaInfo.getProdBaseDesc());

        // 마일리지
        appendMileageInfo(metaInfo, product);

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
		product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
		// Title 생성
		Title title = this.commonGenerator.generateTitle(metaInfo);
		// Price 생성
		Price price = this.commonGenerator.generatePrice(metaInfo);
		// MenuList 생성
		List<Menu> menuList = this.commonGenerator.generateMenuList(metaInfo);
		// SourceList 생성
		List<Source> sourceList = this.commonGenerator.generateSourceList(metaInfo);
		// previewSourceList 생성
		List<Source> previewSourceList = this.commonGenerator.generateVodSourceList(metaInfo);
		// Accrual 생성
		Accrual accrual = this.commonGenerator.generateAccrual(metaInfo);
		// Rights 설정
		Rights rights = this.commonGenerator.generateRights(metaInfo);
		// 영화용 Contributor 설정
		Contributor contributor = this.vodGenerator.generateMovieContributor(metaInfo);

		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setPreviewSourceList(previewSourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setProductExplain(metaInfo.getProdBaseDesc());
		product.setSupportList(this.vodGenerator.generateSupportList(metaInfo));
		// Movie 상품상세설명
		product.setProductDetailExplain(metaInfo.getProdDtlDesc());
		// 판매상태 설정
		product.setSalesStatus(metaInfo.getProdStatusCd());
		// 상품 유/무료 구분
		product.setProdChrgYn(metaInfo.getProdChrg());

        // 마일리지
        appendMileageInfo(metaInfo, product);

		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade#generateMovieProductShort(com.skplanet
	 * . storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Product generateMovieProductShort(MetaInfo metaInfo) {
		Product product = new Product();
		// Identifier 설정
		product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
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

		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setSupportList(this.vodGenerator.generateSupportList(metaInfo));
		product.setProductExplain(metaInfo.getProdBaseDesc());

        // 마일리지
        appendMileageInfo(metaInfo, product);

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
		product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
		// Title 생성
		Title title = this.commonGenerator.generateTitle(metaInfo);
		// Price 생성
		Price price = this.commonGenerator.generatePrice(metaInfo);
		// MenuList 생성
		List<Menu> menuList = this.commonGenerator.generateMenuList(metaInfo);
		// SourceList 생성
		List<Source> sourceList = this.commonGenerator.generateSourceList(metaInfo);
		// previewSourceList 생성
		List<Source> previewSourceList = this.commonGenerator.generateVodSourceList(metaInfo);
		// Accrual 생성
		Accrual accrual = this.commonGenerator.generateAccrual(metaInfo);
		// Rights 설정
		Rights rights = this.commonGenerator.generateRights(metaInfo);
		// 방송용 Contributor 설정
		Contributor contributor = this.vodGenerator.generateBroadcastContributor(metaInfo);
		// 방송용 Vod 설정
		Vod vod = this.vodGenerator.generateVod(metaInfo);

		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setPreviewSourceList(previewSourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setVod(vod);
		product.setProductExplain(metaInfo.getProdBaseDesc());
		product.setSupportList(this.vodGenerator.generateSupportList(metaInfo));
		// Broadcast 상품상세설명
		product.setProductDetailExplain(metaInfo.getProdDtlDesc());
		// 판매상태 설정
		product.setSalesStatus(metaInfo.getProdStatusCd());
		// 상품 유/무료 구분
		product.setProdChrgYn(metaInfo.getProdChrg());
		// 방송사명
		product.setBrdcCompNm(metaInfo.getBrdcCompNm());

        // 마일리지
        appendMileageInfo(metaInfo, product);

		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade#generateBroadcastProductShort(com.
	 * skplanet .storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Product generateBroadcastProductShort(MetaInfo metaInfo) {
		Product product = new Product();
		product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
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

		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setSupportList(this.vodGenerator.generateSupportList(metaInfo));
		product.setProductExplain(metaInfo.getProdBaseDesc());

        // 마일리지
        appendMileageInfo(metaInfo, product);

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
		product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
		// Title 생성
		Title title = this.commonGenerator.generateTitle(metaInfo);
		// Price 생성
		Price price = this.commonGenerator.generateEpubPrice(metaInfo);
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

		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setProductExplain(metaInfo.getProdBaseDesc());
		product.setBook(book);
		// product.setSupportList(this.ebookComicGenerator.generateSupportList(metaInfo)); //book안에 포함
		// Ebook 상품상세설명
		product.setProductDetailExplain(metaInfo.getProdDtlDesc());
		// 판매상태 설정
		product.setSalesStatus(metaInfo.getProdStatusCd());
		// 상품 유/무료 구분
		product.setProdChrgYn(metaInfo.getProdChrg());

        // 마일리지
        appendMileageInfo(metaInfo, product);

		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade#generateEbookProductShort(com.skplanet
	 * . storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Product generateEbookProductShort(MetaInfo metaInfo) {
		Product product = new Product();
		product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
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

		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		// 이북 상품은 상세 설명 정보를 내려줌
		product.setProductDetailExplain(metaInfo.getProdDtlDesc());

        // 마일리지
        appendMileageInfo(metaInfo, product);

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
		product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
		// Title 생성
		Title title = this.commonGenerator.generateTitle(metaInfo);
		// Price 생성
		Price price = this.commonGenerator.generateEpubPrice(metaInfo);
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

		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setProductExplain(metaInfo.getProdBaseDesc());
		product.setBook(book);
		// product.setSupportList(this.ebookComicGenerator.generateSupportList(metaInfo)); //book안에 포함
		// Comic 상품상세설명
		product.setProductDetailExplain(metaInfo.getProdDtlDesc());
		// 판매상태 설정
		product.setSalesStatus(metaInfo.getProdStatusCd());
		// 상품 유/무료 구분
		product.setProdChrgYn(metaInfo.getProdChrg());

        // 마일리지
        appendMileageInfo(metaInfo, product);

		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade#generateComicProductShort(com.skplanet
	 * . storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Product generateComicProductShort(MetaInfo metaInfo) {
		Product product = new Product();
		product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
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

		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setProductExplain(metaInfo.getProdBaseDesc());

        // 마일리지
        appendMileageInfo(metaInfo, product);

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
	public Product generateWebtoonProduct(MetaInfo metaInfo) {
		Product product = new Product();
		product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
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
		if (StringUtils.isEmpty(metaInfo.getBookClsfCd())) {
			if (StringUtils.isNotEmpty(metaInfo.getChapter())) {
				Chapter chapter = new Chapter();
				chapter.setText(Integer.parseInt(metaInfo.getChapter()));
				book.setChapter(chapter);
			}
		}
		book.setStatus(metaInfo.getBookStatus());

		// Date 생성
		// Date date = this.commonGenerator.generateDate(metaInfo);
		Date date = new Date(DisplayConstants.DP_DATE_UPT_NM, metaInfo.getUpdDt());

		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setProductExplain(metaInfo.getProdBaseDesc());
		product.setBook(book);
		product.setDate(date);
		// product.setSupportList(this.ebookComicGenerator.generateSupportList(metaInfo)); //book안에 포함
		// 판매상태 설정
		product.setSalesStatus(metaInfo.getProdStatusCd());
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
		product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));

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

		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setSalesOption(salesOption);
		// 판매상태 설정
		product.setSalesStatus(metaInfo.getProdStatusCd());
		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade#generateShoppingProductShort(com.skplanet
	 * .storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Product generateShoppingProductShort(MetaInfo metaInfo) {
		Product product = new Product();
		product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));

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
		Product product = new Product();
		List<Identifier> identifierList = this.appGenerator.generateSpecificIdentifierList(metaInfo);
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
		List<Menu> menuList = this.appGenerator.generateSpecificMenuList(metaInfo);

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

		// 판매상태 설정
		product.setSalesStatus(metaInfo.getProdStatusCd());

        // 마일리지
        appendMileageInfo(metaInfo, product);

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
		product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
		product.setTitle(this.commonGenerator.generateTitle(metaInfo));
		product.setPrice(this.commonGenerator.generatePrice(metaInfo));
		product.setMenuList(this.commonGenerator.generateMenuList(metaInfo));
		product.setSourceList(this.commonGenerator.generateSourceList(metaInfo));
		product.setAccrual(this.commonGenerator.generateAccrual(metaInfo));
		product.setRights(this.commonGenerator.generateRights(metaInfo));
		product.setContributor(this.musicGenerator.generateContributor(metaInfo));
		product.setProductExplain(metaInfo.getProdBaseDesc());
		product.setDistributor(this.commonGenerator.generateDistributor(metaInfo));
		// product.setMusic(this.musicGenerator.generateMusic(metaInfo));
		// 판매상태 설정
		product.setSalesStatus(metaInfo.getProdStatusCd());

        // 마일리지
        appendMileageInfo(metaInfo, product);

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
		List<Identifier> identifierList = this.commonGenerator.generateIdentifierList(metaInfo);
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
		// 판매상태 설정
		product.setSalesStatus(metaInfo.getProdStatusCd());
		// Date 생성
		List<Date> dateList = new ArrayList<Date>();
		dateList.add(this.commonGenerator.generateDate(DisplayConstants.DP_DATE_REG, metaInfo.getRegDt()));
		product.setDateList(dateList);
		// previewSourceList 생성
		List<Source> previewSourceList = this.commonGenerator.generateVodSourceList(metaInfo);
		product.setPreviewSourceList(previewSourceList);
		// 상품 유/무료 구분
		product.setProdChrgYn(metaInfo.getProdChrg());

        // 마일리지
        appendMileageInfo(metaInfo, product);

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
		product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
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
		// Date 생성
		List<Date> dateList = new ArrayList<Date>();
		dateList.add(this.commonGenerator.generateDate(DisplayConstants.DP_DATE_REG, metaInfo.getRegDt()));
		product.setDateList(dateList);
		// previewSourceList 생성
		List<Source> previewSourceList = this.commonGenerator.generateVodSourceList(metaInfo);
		product.setPreviewSourceList(previewSourceList);

		// 방송용 Vod 설정
		Vod vod = new Vod();
		Chapter chapter = new Chapter();
		chapter.setUnit(this.commonService.getVodChapterUnit());
		if (StringUtils.isNotEmpty(metaInfo.getChapter())) {
			chapter.setText(Integer.parseInt(metaInfo.getChapter()));
		}
		if (StringUtils.isNotEmpty(metaInfo.getMetaClsfCd())
				&& !DisplayConstants.DP_VOD_SHORT_STORY_CLASS_CD.equals(metaInfo.getMetaClsfCd())) {
			vod.setChapter(chapter);
		}

		product.setVod(vod);
		// 판매상태 설정
		product.setSalesStatus(metaInfo.getProdStatusCd());
		// Broadcast 상품상세설명
		product.setProductDetailExplain(metaInfo.getProdDtlDesc());
		// 상품 유/무료 구분
		product.setProdChrgYn(metaInfo.getProdChrg());
		// 방송사명
		product.setBrdcCompNm(metaInfo.getBrdcCompNm());

        // 마일리지
        appendMileageInfo(metaInfo, product);

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
		product.setIdentifierList(this.ebookComicGenerator.generateSpecificIdentifierList(metaInfo));
		product.setTitle(this.commonGenerator.generateTitle(metaInfo));
		product.setPrice(this.commonGenerator.generatePrice(metaInfo));
		product.setMenuList(this.commonGenerator.generateMenuList(metaInfo));
		product.setSourceList(this.commonGenerator.generateSourceList(metaInfo));
		product.setAccrual(this.commonGenerator.generateAccrual(metaInfo));
		product.setRights(this.commonGenerator.generateRights(metaInfo));
		product.setContributor(this.ebookComicGenerator.generateEbookContributor(metaInfo));
		product.setProductExplain(productExplain);
		product.setDistributor(this.commonGenerator.generateDistributor(metaInfo));
		// BOOK 설정
		Book book = this.ebookComicGenerator.generateBook(metaInfo);
		book.setBookClsfCd(metaInfo.getBookClsfCd());
		product.setBook(book);
		// 판매상태 설정
		product.setSalesStatus(metaInfo.getProdStatusCd());

        // 마일리지
        appendMileageInfo(metaInfo, product);

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

		List<Identifier> identifierList = this.commonGenerator.generateIdentifierList(metaInfo);
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
		// product.setBook(this.ebookComicGenerator.generateBook(metaInfo));
		// BOOK 설정
		Book book = this.ebookComicGenerator.generateBook(metaInfo);
		book.setBookClsfCd(metaInfo.getBookClsfCd());
		product.setBook(book);
		product.setSupportList(this.ebookComicGenerator.generateSupportList(metaInfo));
		// 판매상태 설정
		product.setSalesStatus(metaInfo.getProdStatusCd());

        // 마일리지
        appendMileageInfo(metaInfo, product);

		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade#generateSpecificWebtoonProduct(com
	 * .skplanet .storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Product generateSpecificWebtoonProduct(MetaInfo metaInfo) {
		Product product = new Product();

		List<Identifier> identifierList = this.commonGenerator.generateIdentifierList(metaInfo);
		product.setIdentifierList(identifierList);
		product.setPrice(this.commonGenerator.generatePrice(metaInfo));
		product.setMenuList(this.commonGenerator.generateMenuList(metaInfo));
		product.setSourceList(this.commonGenerator.generateSourceList(metaInfo));
		product.setAccrual(this.commonGenerator.generateAccrual(metaInfo));
		product.setRights(this.commonGenerator.generateRights(metaInfo));
		product.setContributor(this.ebookComicGenerator.generateEbookContributor(metaInfo));
		product.setProductExplain(metaInfo.getProdBaseDesc());
		product.setDistributor(this.commonGenerator.generateDistributor(metaInfo));
		// product.setBook(this.ebookComicGenerator.generateBook(metaInfo));
		// BOOK 설정
		Book book = this.ebookComicGenerator.generateBook(metaInfo);
		if (StringUtils.isEmpty(metaInfo.getBookClsfCd())) {
			if (StringUtils.isNotEmpty(metaInfo.getChapter())) {
				Chapter chapter = new Chapter();
				chapter.setText(Integer.parseInt(metaInfo.getChapter()));
				book.setChapter(chapter);
			}
		}
		book.setStatus(metaInfo.getBookStatus());
		product.setBook(book);
		product.setSupportList(this.ebookComicGenerator.generateSupportList(metaInfo));
		// Title 정보 설정
		Title title = new Title();
		title.setText(metaInfo.getProdNm());
		title.setAlias(metaInfo.getChnlProdNm());
		product.setTitle(title);
		// 판매상태 설정
		product.setSalesStatus(metaInfo.getProdStatusCd());
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
		product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
		product.setTitle(this.commonGenerator.generateTitle(metaInfo));
		product.setPrice(this.shoppingGenerator.generatePrice(metaInfo));
		product.setMenuList(this.commonGenerator.generateMenuList(metaInfo));
		product.setSourceList(this.commonGenerator.generateSourceList(metaInfo));
		product.setAccrual(this.shoppingGenerator.generateAccrual(metaInfo));
		product.setRights(this.shoppingGenerator.generateRights(metaInfo));
		product.setContributor(this.shoppingGenerator.generateContributor(metaInfo));
		product.setSalesOption(this.shoppingGenerator.generateSalesOption(metaInfo));
		// product.setDistributor(this.commonGenerator.generateDistributor(metaInfo));
		// 판매상태 설정
		product.setSalesStatus(metaInfo.getProdStatusCd());
		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade#generateFreepassProduct(com.skplanet
	 * .storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Coupon generateFreepassProduct(MetaInfo metaInfo) {
		Coupon coupon = new Coupon();

		// Identifier 생성
		coupon.setIdentifierList(this.freepassGenerator.generateIdentifierList(metaInfo));
		// Title 생성
		Title title = this.freepassGenerator.generateTitle(metaInfo);
		// Price 생성
		Price price = this.commonGenerator.generatePrice(metaInfo);
		// MenuList 생성
		AutoPay autoPay = this.freepassGenerator.generateAutoPay(metaInfo);
		// SourceList 생성
		coupon.setSourceList(this.freepassGenerator.generateSourceList(metaInfo));
		// Date 생성
		// Date date = this.freepassGenerator.generateDate(metaInfo);
		coupon.setDateList(this.freepassGenerator.generateDateList(metaInfo));
		// Menu 생성
		coupon.setMenuList(this.freepassGenerator.generateMenuList(metaInfo));

		coupon.setKind(metaInfo.getCmpxProdClsfCd());
		coupon.setCouponExplain(metaInfo.getProdIntrDscr());

		coupon.setTitle(title);
		coupon.setPrice(price);
		coupon.setAutopay(autoPay);
		// coupon.setDate(date);
		coupon.setSaleStatus(metaInfo.getProdStatusCd());
		if (metaInfo.getCashAmt() != null) {
			coupon.setCashList(this.freepassGenerator.generateCashList(metaInfo));
		}
		return coupon;
	}

    private void appendMileageInfo(MetaInfo metaInfo, Product product) {
        MileageInfo mileageInfo = metaInfo.getMileageInfo();
        if(mileageInfo == null || product == null)
            return;

        List<Point> mileage = commonGenerator.generateMileage(mileageInfo);
        if (CollectionUtils.isNotEmpty(mileage)) {
            product.setPointList(mileage);
        }
    }

}
