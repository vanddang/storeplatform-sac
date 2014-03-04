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

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Book;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;

/**
 * Ebook 상품 전용 정보 Generator.
 * 
 * Updated on : 2014. 1. 27. Updated by : 오승민, 인크로스
 */
public interface EbookComicGenerator {
	/**
	 * <pre>
	 * Ebook 상품 전용 Contributor 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Contributor
	 */
	public Contributor generateEbookContributor(MetaInfo metaInfo);

	/**
	 * <pre>
	 * Comic 상품 전용 Contributor 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Contributor
	 */
	public Contributor generateComicContributor(MetaInfo metaInfo);

	/**
	 * <pre>
	 * Ebook 상품 전용 Book 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Book
	 */
	public Book generateBook(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 특정 상품 Ebook 전용 IdentifierList 객체 생성.
	 * </pre>
	 * 
	 * @param type
	 *            type
	 * @param text
	 *            text
	 * @return Identifier
	 */
	public Identifier generateIdentifier(String type, String text);

	/**
	 * <pre>
	 * 특정 상품 Ebook 전용 IdentifierList 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Identifier
	 */
	public Identifier generateIdentifier(MetaInfo metaInfo);

	/**
	 * <pre>
	 * 특정 상품 Ebook 전용 IdentifierList 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Book
	 */
	public List<Identifier> generateSpecificIdentifierList(MetaInfo metaInfo);

	/**
	 * <pre>
	 * Ebook 상품 for download 전용 Book 객체 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return Book
	 */
	public Book generateForDownloadBook(MetaInfo metaInfo);

	/**
	 * <pre>
	 * Ebook 상품 전용 Support List 생성.
	 * </pre>
	 * 
	 * @param metaInfo
	 *            metaInfo
	 * @return List<Support>
	 */
	public List<Support> generateSupportList(MetaInfo metaInfo);
}
