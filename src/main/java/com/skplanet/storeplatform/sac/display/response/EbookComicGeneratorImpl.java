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

import com.skplanet.storeplatform.sac.api.conts.DisplayConstants;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Book;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Chapter;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
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

	@Override
	public Contributor generateEbookContributor(MetaInfo metaInfo) {
		Contributor contributor = new Contributor();
		contributor.setName(metaInfo.getArtist1Nm()); // 제목
		contributor.setPainter(metaInfo.getArtist2Nm()); //
		contributor.setPublisher(metaInfo.getChnlCompNm()); // 출판사
		Date date = new Date();
		date.setText(metaInfo.getIssueDay()); // 출판년도
		contributor.setDate(date);
		return contributor;
	}

	@Override
	public Contributor generateComicContributor(MetaInfo metaInfo) {
		Contributor contributor = new Contributor();
		contributor.setName(metaInfo.getArtist1Nm()); // 제목
		contributor.setPainter(metaInfo.getArtist2Nm()); // 작가
		contributor.setPublisher(metaInfo.getChnlCompNm()); // 출판사
		return contributor;
	}

	@Override
	public Book generateBook(MetaInfo metaInfo) {
		Book book = new Book();
		Chapter chapter = new Chapter();
		chapter.setUnit(metaInfo.getChapter());
		chapter.setText(metaInfo.getBookCount());
		book.setChapter(chapter);

		if (DisplayConstants.DP_SERIAL_META_CLASS_CD.equals(metaInfo)) {
			// 연재물인 경우에만 Type과 status를 적용한다.
			book.setType(DisplayConstants.DP_EBOOK_SERIAL_NM);
			if ("Y".equals(metaInfo.getComptYn())) {
				book.setStatus(DisplayConstants.DP_EBOOK_COMPLETED_NM);
			} else {
				book.setStatus(DisplayConstants.DP_EBOOK_CONTINUE_NM);
			}
		}
		book.setSupportList(this.generateSupportList(metaInfo));

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
}
