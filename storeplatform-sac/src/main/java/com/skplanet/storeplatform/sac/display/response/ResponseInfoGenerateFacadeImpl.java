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
import java.util.Arrays;
import java.util.List;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.*;
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
import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.display.cache.vo.AlbumMeta;
import com.skplanet.storeplatform.sac.display.common.DisplayCommonUtil;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.MileageInfo;
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
	private VoucherInfoGenerator voucherGenerator;

	@Autowired
	private DisplayCommonService commonService;

	@Autowired
	private AlbumInfoGenerator albumInfoGenerator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade#generateAppProduct(com.skplanet.
	 * storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Product generateAppProduct(MetaInfo metaInfo) {

		if( metaInfo == null ) return null;

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
		this.appendMileageInfo(metaInfo, product);

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

		if( metaInfo == null ) return null;

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
		this.appendMileageInfo(metaInfo, product);

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

		if( metaInfo == null ) return null;

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

		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setMusic(music);
		if (metaInfo.getRegDt() != null) {
			java.util.Date regDt = DateUtils.parseDate(metaInfo.getRegDt());
			if (regDt != null)
				product.setDateList(Arrays.asList(new Date(DisplayConstants.DP_DATE_REG, regDt)));
		}

        // Music 상품설명
        product.setProductExplain(metaInfo.getProdBaseDesc());
		// Music 상품상세설명
		product.setProductDetailExplain(metaInfo.getProdDtlDesc());
		// 판매상태 설정
		product.setSalesStatus(metaInfo.getProdStatusCd());

		// 마일리지
		this.appendMileageInfo(metaInfo, product);

		return product;
	}

	@Override
	public Product generateMusicProductShort(MetaInfo metaInfo) {

		if( metaInfo == null ) return null;

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
		this.appendMileageInfo(metaInfo, product);

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
	public Product generateAlbumProduct(AlbumMeta albumMeta) {

		if( albumMeta == null ) return null;

		Product product = new Product();
		product.setIdentifierList(this.albumInfoGenerator.generateIdentifierList(albumMeta));
		product.setTitle(this.albumInfoGenerator.generateTitle(albumMeta));
		product.setMenuList(this.albumInfoGenerator.generateMenuList(albumMeta));
		product.setSourceList(this.albumInfoGenerator.generateSourceList(albumMeta));
		product.setRights(this.albumInfoGenerator.generateRights(albumMeta));
		product.setContributor(this.albumInfoGenerator.generateContributor(albumMeta));
		product.setDateList(this.albumInfoGenerator.generateDateList(albumMeta));
		product.setProductDetailExplain(albumMeta.getProdDtlDesc());
		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade#generateAlbumDetailProduct(com.skplanet
	 * .storeplatform.sac.display.cache.vo.AlbumMeta)
	 */
	@Override
	public Product generateAlbumDetailProduct(AlbumMeta albumMeta) {

		if( albumMeta == null ) return null;

		Product product = this.generateAlbumProduct(albumMeta);
		product.setLikeYn(albumMeta.getLikeYn());
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

		if( metaInfo == null ) return null;

		Product product = new Product();
		// Identifier 설정
		product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
		// Title 생성
		Title title = this.commonGenerator.generateTitle(metaInfo);
		// Price 생성
		Price price = this.vodGenerator.generateVodPrice(metaInfo);
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
		// Vod 설정
		Vod vod = this.vodGenerator.generateVod(metaInfo);
        Badge badge = this.commonGenerator.generateBadge(metaInfo);

		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setPreviewSourceList(previewSourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
        product.setBadge(badge);
        product.setContributor(contributor);
		product.setProductExplain(metaInfo.getProdBaseDesc());
		product.setSupportList(this.vodGenerator.generateSupportList(metaInfo));
		// Movie 상품상세설명
		product.setProductDetailExplain(metaInfo.getProdDtlDesc());
		// 판매상태 설정
		product.setSalesStatus(metaInfo.getProdStatusCd());
		// 상품 유/무료 구분
		product.setProdChrgYn(metaInfo.getProdChrg());
		product.setVod(vod);

		// 마일리지
		this.appendMileageInfo(metaInfo, product);

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

		if( metaInfo == null ) return null;

		Product product = new Product();
		// Identifier 설정
		product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
		// Title 생성
		Title title = this.commonGenerator.generateTitle(metaInfo);
		// Price 생성
		Price price = this.vodGenerator.generateVodPrice(metaInfo);
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
		// 앱코디 전용 Vod 생성
		if ("APPCODI".equals(metaInfo.getSvcGrpNm())) {
			Vod vod = this.vodGenerator.generateVod(metaInfo);
			product.setVod(vod);
		}

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
		this.appendMileageInfo(metaInfo, product);

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

		if( metaInfo == null ) return null;

		Product product = new Product();
		product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
		// Title 생성
		Title title = this.commonGenerator.generateTitle(metaInfo);
		// Price 생성
		Price price = this.vodGenerator.generateVodPrice(metaInfo);
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
        Badge badge = this.commonGenerator.generateBadge(metaInfo);

		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setPreviewSourceList(previewSourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setVod(vod);
        product.setBadge(badge);
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
		this.appendMileageInfo(metaInfo, product);

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

		if( metaInfo == null ) return null;

		Product product = new Product();
		product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
		// Title 생성
		Title title = this.commonGenerator.generateTitle(metaInfo);
		// Price 생성
		Price price = this.vodGenerator.generateVodPrice(metaInfo);
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
		// 앱코디 전용 Vod 및 방송사명 생성
		if ("APPCODI".equals(metaInfo.getSvcGrpNm())) {
			Vod vod = this.vodGenerator.generateVod(metaInfo);
			product.setVod(vod);
			product.setBrdcCompNm(metaInfo.getBrdcCompNm());
		}

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
		this.appendMileageInfo(metaInfo, product);

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

		if( metaInfo == null ) return null;

		Product product = new Product();
		product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
		// Title 생성
		Title title = this.commonGenerator.generateTitle(metaInfo);
		// Price 생성
		Price price = this.ebookComicGenerator.generateEpubPrice(metaInfo);
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
        Badge badge = this.commonGenerator.generateBadge(metaInfo);

		this.generateEpubPreview(metaInfo, rights);

		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setProductExplain(metaInfo.getProdBaseDesc());
		product.setBook(book);
        product.setBadge(badge);
		// product.setSupportList(this.ebookComicGenerator.generateSupportList(metaInfo)); //book안에 포함
		// Ebook 상품상세설명
		product.setProductDetailExplain(metaInfo.getProdDtlDesc());
		// 판매상태 설정
		product.setSalesStatus(metaInfo.getProdStatusCd());
		// 상품 유/무료 구분
		product.setProdChrgYn(metaInfo.getProdChrg());
		product.setDistributor(this.commonGenerator.generateDistributor(metaInfo));
		// 웹툰 원고 보기
		product.setVerticalYn(metaInfo.getVerticalYn());
		// 마일리지
		this.appendMileageInfo(metaInfo, product);

		return product;
	}

	/**
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param metaInfo
	 * @param rights
	 */
	private void generateEpubPreview(MetaInfo metaInfo, Rights rights) {

		// 미리보기
		if (StringUtils.isNotBlank(metaInfo.getSamplUrl())) {
			Preview preview = new Preview();
			List<Source> epubPreviewSourceList = new ArrayList<Source>();

			Source source = new Source();
			source.setType(DisplayConstants.DP_EPUB_PREVIEW);
			source.setUrl(this.commonService.makeEpubPreviewUrl(metaInfo.getSamplUrl()));
			source.setMediaType(DisplayCommonUtil.getMimeType(metaInfo.getSamplUrl()));
			epubPreviewSourceList.add(source);

			preview.setSourceList(epubPreviewSourceList);
			rights.setPreview(preview);
		}
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

		if( metaInfo == null ) return null;

		Product product = new Product();
		product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
		// Title 생성
		Title title = this.commonGenerator.generateTitle(metaInfo);
		// Price 생성
		Price price = this.ebookComicGenerator.generateEpubPrice(metaInfo);
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

		this.generateEpubPreview(metaInfo, rights);

		// 앱코디 전용 Book 생성
		if ("APPCODI".equals(metaInfo.getSvcGrpNm())) {
			product.setBook(this.ebookComicGenerator.generateBook(metaInfo));
		}

		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		// 이북 상품은 상세 설명 정보를 내려줌
		product.setProductDetailExplain(metaInfo.getProdDtlDesc());
		product.setDistributor(this.commonGenerator.generateDistributor(metaInfo));

		// 마일리지
		this.appendMileageInfo(metaInfo, product);

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

		if( metaInfo == null ) return null;

		Product product = new Product();
		product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
		// Title 생성
		Title title = this.commonGenerator.generateTitle(metaInfo);
		// Price 생성
		Price price = this.ebookComicGenerator.generateEpubPrice(metaInfo);
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
		Badge badge = this.commonGenerator.generateBadge(metaInfo);

		this.generateEpubPreview(metaInfo, rights);

		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setProductExplain(metaInfo.getProdBaseDesc());
		product.setBook(book);
		product.setBadge(badge);
		// product.setSupportList(this.ebookComicGenerator.generateSupportList(metaInfo)); //book안에 포함
		// Comic 상품상세설명
		product.setProductDetailExplain(metaInfo.getProdDtlDesc());
		// 판매상태 설정
		product.setSalesStatus(metaInfo.getProdStatusCd());
		// 상품 유/무료 구분
		product.setProdChrgYn(metaInfo.getProdChrg());
		product.setDistributor(this.commonGenerator.generateDistributor(metaInfo));
		// 웹툰 원고 보기
		product.setVerticalYn(metaInfo.getVerticalYn());

		// 마일리지
		this.appendMileageInfo(metaInfo, product);

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

		if( metaInfo == null ) return null;

		Product product = new Product();
		product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
		// Title 생성
		Title title = this.commonGenerator.generateTitle(metaInfo);
		// Price 생성
		Price price = this.ebookComicGenerator.generateEpubPrice(metaInfo);
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

		this.generateEpubPreview(metaInfo, rights);

		// 앱코디 전용 Book 생성
		if ("APPCODI".equals(metaInfo.getSvcGrpNm())) {
			product.setBook(this.ebookComicGenerator.generateBook(metaInfo));
		}

		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setProductExplain(metaInfo.getProdBaseDesc());
		product.setDistributor(this.commonGenerator.generateDistributor(metaInfo));

		// 마일리지
		this.appendMileageInfo(metaInfo, product);

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

		if( metaInfo == null ) return null;

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

		product.setTitle(title);
		product.setPrice(price);
		product.setMenuList(menuList);
		product.setSourceList(sourceList);
		product.setAccrual(accrual);
		product.setRights(rights);
		product.setContributor(contributor);
		product.setProductExplain(metaInfo.getProdBaseDesc());
		product.setBook(book);
		product.setDateList(Arrays.asList(new Date(DisplayConstants.DP_DATE_REG, metaInfo.getUpdDt()))); // updDt이지만
																										 // 실제로는
																										 // lastDeployDt임
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

		if( metaInfo == null ) return null;

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
		// 특가상품여부
		product.setSpecialProdYn(metaInfo.getSpecialSaleYn());

		// Tstore멤버십 적립율 정보
		this.appendMileageInfo(metaInfo, product);

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

		if( metaInfo == null ) return null;

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

		// Tstore멤버십 적립율 정보
		this.appendMileageInfo(metaInfo, product);

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

		if( metaInfo == null ) return null;

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
		this.appendMileageInfo(metaInfo, product);

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

		if( metaInfo == null ) return null;

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
        product.setMusic(this.musicGenerator.generateMusic(metaInfo));
		// 판매상태 설정
		product.setSalesStatus(metaInfo.getProdStatusCd());

		// 마일리지
		this.appendMileageInfo(metaInfo, product);

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

		if( metaInfo == null ) return null;

		Product product = new Product();

		// Identifier 설정
		List<Identifier> identifierList = this.commonGenerator.generateIdentifierList(metaInfo);
		product.setIdentifierList(identifierList);
		product.setTitle(this.commonGenerator.generateTitle(metaInfo));
		product.setPrice(this.vodGenerator.generateVodPrice(metaInfo));
		product.setMenuList(this.commonGenerator.generateMenuList(metaInfo));
		product.setSourceList(this.commonGenerator.generateSourceList(metaInfo));
		product.setAccrual(this.commonGenerator.generateAccrual(metaInfo));
		product.setRights(this.vodGenerator.generateRights(metaInfo));
		product.setContributor(this.vodGenerator.generateMovieContributor(metaInfo));
		product.setBadge(this.commonGenerator.generateBadge(metaInfo));
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
		this.appendMileageInfo(metaInfo, product);

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

		if( metaInfo == null ) return null;

		Product product = new Product();

		// Identifier 설정
		product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
		product.setTitle(this.commonGenerator.generateTitle(metaInfo));
		product.setPrice(this.vodGenerator.generateVodPrice(metaInfo));
		product.setMenuList(this.commonGenerator.generateMenuList(metaInfo));
		product.setSourceList(this.commonGenerator.generateSourceList(metaInfo));
		product.setAccrual(this.commonGenerator.generateAccrual(metaInfo));
		product.setRights(this.vodGenerator.generateRights(metaInfo));
		product.setContributor(this.vodGenerator.generateMovieContributor(metaInfo));
		product.setBadge(this.commonGenerator.generateBadge(metaInfo));
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
		this.appendMileageInfo(metaInfo, product);

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

		if( metaInfo == null ) return null;

		Product product = new Product();
		String productExplain = metaInfo.getProdBaseDesc();
		String productDetailExplain = metaInfo.getProdDtlDesc();
		product.setIdentifierList(this.ebookComicGenerator.generateSpecificIdentifierList(metaInfo));
		product.setTitle(this.commonGenerator.generateTitle(metaInfo));
		product.setPrice(this.ebookComicGenerator.generateEpubPrice(metaInfo));
		product.setMenuList(this.commonGenerator.generateMenuList(metaInfo));
		product.setSourceList(this.commonGenerator.generateSourceList(metaInfo));
		product.setAccrual(this.commonGenerator.generateAccrual(metaInfo));
        product.setBadge(this.commonGenerator.generateBadge(metaInfo));

		Rights rights = this.commonGenerator.generateRights(metaInfo);
		// 이북/코믹 미리보기 URL 추가
		this.generateEpubPreview(metaInfo, rights);
		product.setRights(rights);
		product.setContributor(this.ebookComicGenerator.generateEbookContributor(metaInfo));
		product.setProductExplain(productExplain);
		product.setProductDetailExplain(productDetailExplain);
		product.setDistributor(this.commonGenerator.generateDistributor(metaInfo));

		List<Date> dateList = new ArrayList<Date>();
		dateList.add(this.commonGenerator.generateDate(DisplayConstants.DP_DATE_REG, metaInfo.getRegDt()));
		product.setDateList(dateList);

		product.setAboutWriter(productExplain); // 이북 상품은 prodBaseDesc 값을 작가 소개로 사용한다.

		// 목차
		product.setTableOfContents(metaInfo.getBookTbctns());

		// BOOK 설정
		Book book = this.ebookComicGenerator.generateBook(metaInfo);
		book.setBookClsfCd(metaInfo.getBookClsfCd());
		product.setBook(book);
		// 판매상태 설정
		product.setSalesStatus(metaInfo.getProdStatusCd());

		// 마일리지
		this.appendMileageInfo(metaInfo, product);

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

		if( metaInfo == null ) return null;

		Product product = new Product();

		List<Identifier> identifierList = this.commonGenerator.generateIdentifierList(metaInfo);
		product.setIdentifierList(identifierList);
		product.setTitle(this.commonGenerator.generateTitle(metaInfo));
		product.setPrice(this.ebookComicGenerator.generateEpubPrice(metaInfo));
		product.setMenuList(this.commonGenerator.generateMenuList(metaInfo));
		product.setSourceList(this.commonGenerator.generateSourceList(metaInfo));
		product.setAccrual(this.commonGenerator.generateAccrual(metaInfo));
		Rights rights = this.commonGenerator.generateRights(metaInfo);
		// 이북/코믹 미리보기 URL 추가
		this.generateEpubPreview(metaInfo, rights);
		product.setRights(rights);
		product.setContributor(this.ebookComicGenerator.generateComicContributor(metaInfo));
		product.setProductExplain(metaInfo.getProdBaseDesc());
		product.setDistributor(this.commonGenerator.generateDistributor(metaInfo));
        product.setBadge(this.commonGenerator.generateBadge(metaInfo));
		// product.setBook(this.ebookComicGenerator.generateBook(metaInfo));

		List<Date> dateList = new ArrayList<Date>();
		dateList.add(this.commonGenerator.generateDate(DisplayConstants.DP_DATE_REG, metaInfo.getRegDt()));
		product.setDateList(dateList);

		// BOOK 설정
		Book book = this.ebookComicGenerator.generateBook(metaInfo);
		book.setBookClsfCd(metaInfo.getBookClsfCd());
		product.setBook(book);
		product.setSupportList(this.ebookComicGenerator.generateSupportList(metaInfo));
		// 판매상태 설정
		product.setSalesStatus(metaInfo.getProdStatusCd());

		// 목차
		product.setAboutWriter(metaInfo.getBookTbctns());

		// 마일리지
		this.appendMileageInfo(metaInfo, product);

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

		if( metaInfo == null ) return null;

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
    // 로직은 generateShoppingProduct와 동일함
	@Override
	public Product generateSpecificShoppingProduct(MetaInfo metaInfo) {

		if( metaInfo == null ) return null;

		Product product = new Product();

		product.setIdentifierList(this.commonGenerator.generateIdentifierList(metaInfo));
		product.setTitle(this.commonGenerator.generateTitle(metaInfo));
		product.setPrice(this.shoppingGenerator.generatePrice(metaInfo));
		product.setMenuList(this.commonGenerator.generateMenuList(metaInfo));
		product.setSourceList(this.commonGenerator.generateSourceList(metaInfo));
		product.setAccrual(this.shoppingGenerator.generateAccrual(metaInfo));
		product.setRights(this.shoppingGenerator.generateRights(metaInfo));
		product.setContributor(this.shoppingGenerator.generateContributor(metaInfo));
		product.setSalesOption(this.shoppingGenerator.generateSalesOption(metaInfo));
        product.setDistributor(this.commonGenerator.generateDistributor(metaInfo));
		product.setSalesStatus(metaInfo.getProdStatusCd());

        appendMileageInfo(metaInfo, product);

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

		if( metaInfo == null ) return null;

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

		// 정액권 이용안내
		coupon.setFreepassGuide(metaInfo.getProdDtlDesc());

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade#generateVoucherProduct(com.skplanet
	 * .storeplatform.sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Coupon generateVoucherProduct(MetaInfo metaInfo) {

		if( metaInfo == null ) return null;

		Coupon coupon = new Coupon();

		// Identifier 생성
		coupon.setIdentifierList(this.voucherGenerator.generateIdentifierList(metaInfo));
		// Title 생성
		Title title = this.voucherGenerator.generateTitle(metaInfo);
		// Price 생성
		Price price = this.commonGenerator.generatePrice(metaInfo);
		// MenuList 생성
		AutoPay autoPay = this.voucherGenerator.generateAutoPay(metaInfo);
		// SourceList 생성
		coupon.setSourceList(this.voucherGenerator.generateSourceList(metaInfo));
		// Date 생성
		// Date date = this.voucherGenerator.generateDate(metaInfo);
		coupon.setDateList(this.voucherGenerator.generateDateList(metaInfo));
		// Menu 생성
		coupon.setMenuList(this.voucherGenerator.generateMenuList(metaInfo));
		// FreepassAttr 생성
		FreepassAttr freepassAttr = this.voucherGenerator.generateFreepassAttrList(metaInfo);
		// Rights 생성
		Rights rights = this.voucherGenerator.generateRights(metaInfo);

		coupon.setKind(metaInfo.getCmpxProdClsfCd());
		coupon.setCouponExplain(metaInfo.getProdIntrDscr());

		// 정액권 이용안내
		coupon.setFreepassGuide(metaInfo.getProdDtlDesc());

		// 요청 상품 여부
		coupon.setRequestProduct(metaInfo.getRequestProduct());

		coupon.setTitle(title);
		coupon.setPrice(price);
		coupon.setAutopay(autoPay);
		if (freepassAttr.getCouponGroup() != null || freepassAttr.getPossLend() != null
				|| freepassAttr.getSerialBook() != null) {
			coupon.setFreepassAttr(freepassAttr);
		}
		if (rights.getGrade() != null || rights.getPlus19Yn() != null) {
			coupon.setRights(rights);
		}
		// coupon.setDate(date);
		coupon.setSaleStatus(metaInfo.getProdStatusCd());
		if (metaInfo.getCashAmt() != null) {
			coupon.setCashList(this.voucherGenerator.generateCashList(metaInfo));
		}
		coupon.setSupportList(this.voucherGenerator.generateSupportList(metaInfo));
		return coupon;
	}

	private void appendMileageInfo(MetaInfo metaInfo, Product product) {

		MileageInfo mileageInfo = metaInfo.getMileageInfo();
		if (mileageInfo == null || product == null)
			return;

		// 예외 상품이면 적립율 그대로 노출
		// 예외 상품이 아닌 경우
		if (metaInfo.getProdAmt() == null || metaInfo.getProdAmt() == 0
				&& StringUtils.equals(mileageInfo.getPolicyTargetCd(), DisplayConstants.POLICY_TARGET_CD_CATEGORY)) {
			// 무료 상품 && 카테고리
			/*
			 * String topMenuId = metaInfo.getTopMenuId(); if (StringUtils.isNotEmpty(metaInfo.getPartParentClsfCd()) //
			 * 인앱 지원 여부 참조. AppInfoGeneratorImpl.generateSupportList &&
			 * (DisplayConstants.DP_GAME_TOP_MENU_ID.equals(topMenuId) ||
			 * DisplayConstants.DP_FUN_TOP_MENU_ID.equals(topMenuId) ||
			 * DisplayConstants.DP_LIFE_LIVING_TOP_MENU_ID.equals(topMenuId) ||
			 * DisplayConstants.DP_LANG_EDU_TOP_MENU_ID.equals(topMenuId)) ) { // 앱상품 && 인앱상품이 존재하면 노출 } else
			 * mileageInfo = null;
			 */

			// 앱상품 && 인앱상품이 존재하면 노출
			// 인앱 지원 여부 참조. AppInfoGeneratorImpl.generateSupportList
			if (StringUtils.isEmpty(metaInfo.getPartParentClsfCd())
					|| StringUtils.equalsIgnoreCase(metaInfo.getPartParentClsfCd(), "N")) {
				mileageInfo = new MileageInfo();
			}
		}

		List<Point> mileage = this.commonGenerator.generateMileage(mileageInfo);
		if (CollectionUtils.isNotEmpty(mileage)) {
			product.setPointList(mileage);
		}
	}
}
