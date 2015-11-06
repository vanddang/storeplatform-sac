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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Book;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Chapter;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * Ebook 상품 전용 정보 Generator 구현체
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스.
 */
@Component
public class EbookComicGeneratorImpl implements EbookComicGenerator {
	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

	@Autowired
	private DisplayCommonService commonService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateIdentifier(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Identifier generateIdentifier(String type, String text) {
		Identifier identifier = new Identifier();
		identifier.setType(type);
		identifier.setText(text);

		return identifier;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator#generateIdentifier(com.skplanet.storeplatform
	 * .sac.display.meta.vo.MetaInfo)
	 */
	@Override
	public Identifier generateIdentifier(MetaInfo metaInfo) {
		String contentsTypeCd = metaInfo.getContentsTypeCd();
		Identifier identifier = null;
		if (DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD.equals(contentsTypeCd)) {
			identifier = this.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, metaInfo.getProdId());
		} else if (DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD.equals(contentsTypeCd)
				&& DisplayConstants.DP_SHOPPING_TOP_MENU_ID.equals(metaInfo.getTopMenuId())) {
			identifier = this.generateIdentifier(DisplayConstants.DP_CATALOG_IDENTIFIER_CD, metaInfo.getCatalogId());
		} else if (DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD.equals(contentsTypeCd)) {
			identifier = this.generateIdentifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, metaInfo.getProdId());
		}
		return identifier;
	}

	@Override
	public Contributor generateEbookContributor(MetaInfo metaInfo) {
		Contributor contributor = new Contributor();
		contributor.setName(metaInfo.getArtist1Nm()); // 글작가
		contributor.setPainter(metaInfo.getArtist2Nm()); // 그림작가
		contributor.setTranslator(metaInfo.getArtist3Nm()); // 번역자
		contributor.setPublisher(metaInfo.getChnlCompNm()); // 출판사

		if (StringUtils.isNotEmpty(metaInfo.getIssueDay())) {
			contributor.setDate(this.commonGenerator.generateDateString(DisplayConstants.DP_DATE_PUBLISH,
					metaInfo.getIssueDay()));
		}
		return contributor;
	}

	@Override
	public Contributor generateComicContributor(MetaInfo metaInfo) {
		Contributor contributor = new Contributor();
		contributor.setName(metaInfo.getArtist1Nm()); // 글작가
		contributor.setPainter(metaInfo.getArtist2Nm()); // 그림작가
		contributor.setTranslator(metaInfo.getArtist3Nm()); // 번역자
		contributor.setPublisher(metaInfo.getChnlCompNm()); // 출판사

		if (StringUtils.isNotEmpty(metaInfo.getIssueDay())) {
			contributor.setDate(this.commonGenerator.generateDateString(DisplayConstants.DP_DATE_PUBLISH,
					metaInfo.getIssueDay()));
		}
		return contributor;
	}

	@Override
	public Book generateBook(MetaInfo metaInfo) {
		Book book = new Book();
		if (StringUtils.isNotEmpty(metaInfo.getBookClsfCd()) && StringUtils.isNotEmpty(metaInfo.getChapter())) {
			Chapter chapter = new Chapter();
			chapter.setUnit(this.commonService.getEpubChapterUnit(metaInfo.getBookClsfCd()));
			chapter.setText(Integer.parseInt(metaInfo.getChapter()));
			book.setChapter(chapter);
		}

		//이북 type
        if(StringUtils.equals(metaInfo.getBookClsfCd(), DisplayConstants.DP_BOOK_BOOK)) {
            book.setTotalCount(metaInfo.getBookCount());
        	book.setBookType(DisplayConstants.DP_BOOK_TYPE_BOOK);
        } else if(StringUtils.equals(metaInfo.getBookClsfCd(), DisplayConstants.DP_BOOK_SERIAL)) {
            book.setTotalCount(metaInfo.getSerialCount());
        	book.setBookType(DisplayConstants.DP_BOOK_TYPE_SERIAL);
        } else if(StringUtils.equals(metaInfo.getBookClsfCd(), DisplayConstants.DP_BOOK_MAGAZINE)) {
            book.setTotalCount(metaInfo.getMagazineCount());
        	book.setBookType(DisplayConstants.DP_BOOK_TYPE_MAGAZINE);
        }

		if (DisplayConstants.DP_SERIAL_META_CLASS_CD.equals(metaInfo.getMetaClsfCd())
				|| DisplayConstants.DP_INTERACTIVE_WEBTOON_META_CLASS_CD.equals(metaInfo.getMetaClsfCd())
				|| DisplayConstants.DP_SERIAL_COMIC_META_CLASS_CD.equals(metaInfo.getMetaClsfCd())
				|| DisplayConstants.DP_MAGAZINE_COMIC_META_CLASS_CD.equals(metaInfo.getMetaClsfCd())
				|| DisplayConstants.DP_WEBTOON_COMIC_META_CLASS_CD.equals(metaInfo.getMetaClsfCd())) {
			// eBook 연재물인 경우에만 Type과 status를 적용한다.
			book.setType(DisplayConstants.DP_EBOOK_SERIAL_NM);
			if ("Y".equals(metaInfo.getComptYn())) {
				book.setStatus(DisplayConstants.DP_EBOOK_COMPLETED_NM);
			} else {
				book.setStatus(DisplayConstants.DP_EBOOK_CONTINUE_NM);
			}
		}
		
		//이북/코믹 에피소드 건수/마지막 챕터 (단행/연재/잡지)
		book.setBookCount(metaInfo.getBookCount());
		book.setSerialCount(metaInfo.getSerialCount());
		book.setMagazineCount(metaInfo.getMagazineCount());
		book.setBookLastChapter(metaInfo.getBookLastChapter());
		book.setSerialLastChapter(metaInfo.getSerialLastChapter());
		book.setMagazineLastChapter(metaInfo.getMagazineLastChapter());
		
		book.setSupportList(this.generateSupportList(metaInfo));
		book.setIsbn(metaInfo.getIsbn());
        book.setVerticalYn(StringUtils.defaultString(metaInfo.getVerticalYn(), "N"));
		return book;
	}

	@Override
	public Book generateForDownloadBook(MetaInfo metaInfo) {
		Book book = new Book();
		book.setBookVersion(metaInfo.getProdVer());
		book.setScid(metaInfo.getSubContentsId());
		book.setBookClsfCd(metaInfo.getBookClsfCd());
		book.setSize(metaInfo.getFileSize());
		book.setBpJoinFileNo(metaInfo.getBpJoinFileNo());
		book.setTotalCount(metaInfo.getBookPageCnt());
		book.setIsbn(metaInfo.getIsbn());
		book.setMgzinCoverYn(metaInfo.getMgzinCoverYn());
		book.setMallCd(metaInfo.getMallCd());

		// 완결 여부
		if ("Y".equals(metaInfo.getComptYn())) {
			book.setStatus(DisplayConstants.DP_EBOOK_COMPLETED_NM);
		} else {
			book.setStatus(DisplayConstants.DP_EBOOK_CONTINUE_NM);
		}

		// 회차 정보
		if (StringUtils.isNotEmpty(metaInfo.getChapter())) {
			Chapter chapter = new Chapter();
			chapter.setText(Integer.parseInt(metaInfo.getChapter()));
			chapter.setUnit(this.commonService.getEpubChapterUnit(metaInfo.getBookClsfCd()));
			book.setChapter(chapter);
		}

		// 도서 연재 구분
		if (DisplayConstants.DP_BOOK_SERIAL.equals(metaInfo.getBookClsfCd())) {
			book.setType(DisplayConstants.DP_EBOOK_SERIAL_NM);
		}

		// 컬러 지원 구분
		if (StringUtils.isNotEmpty(metaInfo.getColorSprtYn())) {
			List<Support> supportList = new ArrayList<Support>();
			supportList.add(this.commonGenerator.generateSupport(DisplayConstants.DP_COLOR_SUPPORT_NM,
					metaInfo.getColorSprtYn()));
			book.setSupportList(supportList);
		}

		return book;
	}

	@Override
	public List<Support> generateSupportList(MetaInfo metaInfo) {
		List<Support> supportList = new ArrayList<Support>();
		Support support = null;
		if ("Y".equals(metaInfo.getSupportPlay())) {
			support = this.commonGenerator.generateSupport(DisplayConstants.DP_EBOOK_PLAY_SUPPORT_NM, "Y");
			supportList.add(support);
		} else {
			support = this.commonGenerator.generateSupport(DisplayConstants.DP_EBOOK_PLAY_SUPPORT_NM, "N");
			supportList.add(support);
		}

		if ("Y".equals(metaInfo.getSupportStore())) {
			support = this.commonGenerator.generateSupport(DisplayConstants.DP_EBOOK_STORE_SUPPORT_NM, "Y");
			supportList.add(support);
		} else {
			support = this.commonGenerator.generateSupport(DisplayConstants.DP_EBOOK_STORE_SUPPORT_NM, "N");
			supportList.add(support);
		}
		return supportList;
	}

	@Override
	public List<Identifier> generateSpecificIdentifierList(MetaInfo metaInfo) {
		String contentsTypeCd = metaInfo.getContentsTypeCd();
		Identifier identifier = null;
		List<Identifier> identifierList = new ArrayList<Identifier>();

		if (DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD.equals(contentsTypeCd)) { // Episode ID 기준검색일 경우
			identifier = this.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, metaInfo.getPartProdId());
			identifierList.add(identifier);

			if (DisplayConstants.DP_SHOPPING_TOP_MENU_ID.equals(metaInfo.getTopMenuId())) {
				if (metaInfo.getCatalogId() != null) {
					identifier = this.generateIdentifier(DisplayConstants.DP_CATALOG_IDENTIFIER_CD,
							metaInfo.getCatalogId());
					identifierList.add(identifier);
				}
			} else {
				if (metaInfo.getProdId() != null) {
					identifier = this.generateIdentifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD,
							metaInfo.getProdId());
					identifierList.add(identifier);
				}
			}

			// music 의 경우 songId
			if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(metaInfo.getTopMenuId())) {
				identifier = this.generateIdentifier(DisplayConstants.DP_SONG_IDENTIFIER_CD,
						metaInfo.getOutsdContentsId());
				identifierList.add(identifier);
			}

		} else if (DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD.equals(contentsTypeCd) // Catalog ID 기준 검색일 경우
				&& DisplayConstants.DP_SHOPPING_TOP_MENU_ID.equals(metaInfo.getTopMenuId())) {
			identifier = this.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, metaInfo.getPartProdId());
			identifierList.add(identifier);
			identifier = this.generateIdentifier(DisplayConstants.DP_CATALOG_IDENTIFIER_CD, metaInfo.getCatalogId());
			identifierList.add(identifier);
		} else if (DisplayConstants.DP_CHANNEL_CONTENT_TYPE_CD.equals(contentsTypeCd)) { // Channel ID 기준 검색일 경우
			identifier = this.generateIdentifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, metaInfo.getProdId());
			identifierList.add(identifier);
		}
		// Cid 설정
		if (StringUtils.isNotEmpty(metaInfo.getCid())) {
			identifier = this.generateIdentifier(DisplayConstants.DP_CONTENT_IDENTIFIER_CD, metaInfo.getCid());
			identifierList.add(identifier);
		}
		// OutsdContentsId 설정
		if (StringUtils.isNotEmpty(metaInfo.getOutsdContentsId())) {
			identifier = this.generateIdentifier(DisplayConstants.DP_OUTSDCONTENTS_IDENTIFIER_CD,
					metaInfo.getOutsdContentsId());
			identifierList.add(identifier);
		}
		return identifierList;
	}
	

	@Override
	public Price generateEpubPrice(MetaInfo metaInfo) {
		Price price = new Price();
		price.setText(metaInfo.getProdAmt());
		price.setFixedPrice(metaInfo.getProdNetAmt());
		price.setUnlmtAmt(metaInfo.getUnlmtAmt());
		price.setPeriodAmt(metaInfo.getPeriodAmt());
		return price;
	}

}
