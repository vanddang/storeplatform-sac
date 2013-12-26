/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product;

import java.io.Serializable;
import java.util.List;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.BannerProto;
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.PreviewProto.Preview;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Url;

/**
 * Interface Message Banner Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@ProtobufMapping(BannerProto.Banner.class)
public class Banner extends CommonInfo implements Serializable {
	/*
	 * Banner를 클릭했을때 이동하는 base URL (Web URL or WebView을 연동할 경우는 정의되지 않는다.) > http://<<BASE>>/product : 상품타입 >
	 * http://<<BASE>>/product/category/themeZone : 추천리스트 > http://<<BASE>>/product/category/brandShop 브랜드샵 >
	 * http://<<BASE>>/miscellaneous/category/brandShop : 특정브랜드샵
	 */
	private String base;
	/*
	 * 베너 크기 유형 > A > B > C > D
	 */
	private String sizeType;
	/*
	 * 베너 등록 방식 > product : 상품 > externalUrl : 외부 URL > category : 카테고리 연결 > themeZone : 운영자 임의 추천 리스트 연결 >
	 * brandShopCategory : 브랜드샵 목록 연결 > brandShop : 특정 브랜드샵 연결 > url : 내부 URL > themeRecommend : 상황 별 추천
	 */
	private String type;

	private List<Identifier> identifier;
	// required com.skplanet.storeplatform.sac.client.intfmessage.common.vo.Identifier identifier = 4; //베너�ID
	/*
	 * 베너 명(type에 따라서 달라짐) > product : 상품명 > category : 카테고리 명 > themeZone : 운영자 임의추천 명 > brandShopCategory : 카테고리 명 >
	 * brandShop : 브랜드 샵 명 > themeRecommend : 상황 별 추천 명
	 * 
	 * > text
	 */
	private Title title;

	/*
	 * 카테고리 > all: 전체(main) > game: 게임 > fun: fun > living: 생활/위치 > languageEducation: 어학/교육 > movie: 영화 > broadcast:
	 * TV방송 > music: 음악 > cartoon: 만화 > ebook: 이북 > shoppingCoupon: 쇼핑/쿠폰 > shoppingStore: T store shopping (기존
	 * shoppingCoupon과 구분을 위해 새로 정의된 category) > meta_cls_cd 를 표현하기 위해 정의된다
	 */
	private String category;

	private BannerExplain bannerExplain; // bannerExplain Message

	/*
	 * type > browser : Browser을 통해서 연동하는 경우에 사용 > webView : WebView를 이용할 경우에 사용
	 */
	private Url url;

	private List<Source> sourceList;

	private Music music; // music 상품일 경우 미리듣기 정보

	/*
	 * 미리 보기 정보를 정의한다. source의 type="video/x-freeview-lq" (미리보기 저화질) source의 type="video/x-freeview-hq" (미리보기 고화질)
	 */
	private Preview preview; // VOD 상품일 경우 미리보기 정보

	public String getBase() {
		return this.base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getSizeType() {
		return this.sizeType;
	}

	public void setSizeType(String sizeType) {
		this.sizeType = sizeType;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Identifier> getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(List<Identifier> identifier) {
		this.identifier = identifier;
	}

	public Title getTitle() {
		return this.title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public BannerExplain getBannerExplain() {
		return this.bannerExplain;
	}

	public void setBannerExplain(BannerExplain bannerExplain) {
		this.bannerExplain = bannerExplain;
	}

	public Url getUrl() {
		return this.url;
	}

	public void setUrl(Url url) {
		this.url = url;
	}

	public List<Source> getSourceList() {
		return this.sourceList;
	}

	public void setSourceList(List<Source> sourceList) {
		this.sourceList = sourceList;
	}

	public Music getMusic() {
		return this.music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}

	public Preview getPreview() {
		return this.preview;
	}

	public void setPreview(Preview preview) {
		this.preview = preview;
	}
}
