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

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;

/**
 * Interface Message Product Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Product extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 상품 ID.
	 */
	private Identifier identifier;

	private List<Identifier> identifierList;
	/**
	 * identifier 추가 기술.
	 */
	private String id;
	/**
	 * 지원 매체.
	 */
	private List<Support> supportList;
	/**
	 * 지원 언어.
	 */
	private String lang;
	/**
	 * 신규상품/마감상품 설정 > new : 신규상품 > closing : 마감임박.
	 */
	private String status;
	/**
	 * title Message(상품명).
	 */
	private Title title;

	/**
	 * 채널 상품명.
	 */
	private String chnlProdNm;

	/**
	 * 가격정보.
	 */
	private Price price;
	/**
	 * > Code : 대분류, 소분류 카테고리코드 > name : shoppingStore > type(option) : 대분류 카테고리시 topClass > categoryExplian : 카테고리 설명.
	 */
	private List<Menu> menuList;
	/**
	 * * 패킷과금 타입 > paid : 일반 과금상품(PD000601) > library : 라이브러리(PD000602) > vm : VM(PD000603) > free : 일반 비과금상품(PD000604).
	 * > paid-rss : RSS 과금상품(PD000605) > free-rss : RSS 비과금상품(PD000606).
	 */
	private String packetFee;

	/**
	 * resource 정보.
	 */
	private List<Source> sourceList;
	/**
	 * > short : 한 줄 설명 > long : 상세 설명.
	 */
	private String requestType;
	/**
	 * 상품설명.
	 */
	private String productExplain;
	/**
	 * 상품상세설명.
	 */
	private String productDetailExplain;
	/**
	 * 상품소개내용.
	 */
	private String productIntroduction;
	/**
	 * 목차정보(ebook일 경우).
	 */
	private String tableOfContents;
	/**
	 * 작가소개(ebook일 경우).
	 */
	private String aboutWriter;
	/**
	 * 앱 추천사유 (앱코디 리스트인 경우).
	 */
	private String recommendedReason;
	/**
	 * Accrual.
	 */
	private Accrual accrual;
	/**
	 * 권한 정보.
	 */
	private Rights rights;
	/**
	 * 앱 정보.
	 */
	private App app;
	/**
	 * 음악 부가 정보.
	 */
	private Music music;
	/**
	 * VOD 부가 정보.
	 */
	private Vod vod;
	/**
	 * VOD일경우 VOD 부가정보.
	 */
	private VideoInfo videoInfo;
	/**
	 * ebook,Comic일 경우 부가정보.
	 */
	private Book book; //
	/**
	 * 이벤트 상품 정보.
	 */
	private Promotion promotion;
	/**
	 * 제작자 정보.
	 */
	private Contributor contributor;
	/**
	 * 제작사 또는 개발사 정보.
	 */
	private Distributor distributor;
	/**
	 * 판매 option.
	 */
	private SalesOption salesOption;
	/**
	 * 구매 정보.
	 */
	private Purchase purchase;
	/**
	 * 구매 정보 리스트.
	 */
	private List<Purchase> purchaseList;
	/**
	 * 쿠폰 정보.
	 */
	private Coupon coupon;
	/**
	 * 날짜 정보.
	 */
	private Date date;
	/**
	 * 이벤트 정보.
	 */
	private Event event;
	/**
	 * 상품 선택 옵션 정보.
	 */
	private SelectOption selectOption;

	/**
	 * 상품 선택 옵션 정보 List.
	 */
	private List<SelectOption> selectOptionList;

	/**
	 * Restrict 유형 > ota : 네트워크 제한 > sales : 판매 중지.
	 */
	private String restrictType;
	/**
	 * ebook의 최신호 정보.
	 */
	private String latestIssue;
	/**
	 * 물리 파일 경로.
	 */
	private String physicalPath;

	/**
	 * 하위 상품 총 건수.
	 */
	private Integer subProductTotalCount;

	/**
	 * 하위 상품 목록.
	 */
	private List<Product> subProductList;

	/**
	 * 날짜 목록.
	 */
	private List<Date> dateList;

	/**
	 * 상품 유/무료 여부.
	 */
	private String prodChrgYn;

	/**
	 * 플랫폼 구분 코드.
	 */
	private String platClsfCd;

	/**
	 * 암호화 정보.
	 */
	private List<Encryption> dl;

	/**
	 * 판매상태.
	 */
	private String salesStatus;

	/**
	 * 미리보기 Source List.
	 */
	private List<Source> previewSourceList;

	public String getSalesStatus() {
		return this.salesStatus;
	}

	public void setSalesStatus(String salesStatus) {
		this.salesStatus = salesStatus;
	}

	/**
	 * @return Identifier
	 */
	public Identifier getIdentifier() {
		return this.identifier;
	}

	/**
	 * @param identifier
	 *            identifier
	 */
	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return List<Identifier>
	 */
	public List<Identifier> getIdentifierList() {
		return this.identifierList;
	}

	/**
	 * @param identifierList
	 *            identifierList
	 */
	public void setIdentifierList(List<Identifier> identifierList) {
		this.identifierList = identifierList;
	}

	/**
	 * @return String
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * @param id
	 *            id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return List<Support>
	 */
	public List<Support> getSupportList() {
		return this.supportList;
	}

	/**
	 * @param supportList
	 *            supportList
	 */
	public void setSupportList(List<Support> supportList) {
		this.supportList = supportList;
	}

	/**
	 * @return String
	 */
	public String getLang() {
		return this.lang;
	}

	/**
	 * @param lang
	 *            lang
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * @return String
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * @param status
	 *            status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Title
	 */
	public Title getTitle() {
		return this.title;
	}

	/**
	 * @param title
	 *            title
	 */
	public void setTitle(Title title) {
		this.title = title;
	}

	/**
	 * @return the chnlProdNm
	 */
	public String getChnlProdNm() {
		return this.chnlProdNm;
	}

	/**
	 * @param chnlProdNm
	 *            the chnlProdNm to set
	 */
	public void setChnlProdNm(String chnlProdNm) {
		this.chnlProdNm = chnlProdNm;
	}

	/**
	 * @return Price
	 */
	public Price getPrice() {
		return this.price;
	}

	/**
	 * @param price
	 *            price
	 */
	public void setPrice(Price price) {
		this.price = price;
	}

	/**
	 * @return List<Menu>
	 */
	public List<Menu> getMenuList() {
		return this.menuList;
	}

	/**
	 * @param menuList
	 *            menuList
	 */
	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	/**
	 * @return String
	 */
	public String getPacketFee() {
		return this.packetFee;
	}

	/**
	 * @param packetFee
	 *            packetFee
	 */
	public void setPacketFee(String packetFee) {
		this.packetFee = packetFee;
	}

	/**
	 * @return List<Source>
	 */
	public List<Source> getSourceList() {
		return this.sourceList;
	}

	/**
	 * @param sourceList
	 *            sourceList
	 */
	public void setSourceList(List<Source> sourceList) {
		this.sourceList = sourceList;
	}

	/**
	 * @return String
	 */
	public String getRequestType() {
		return this.requestType;
	}

	/**
	 * @param requestType
	 *            requestType
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	/**
	 * @return String
	 */
	public String getProductExplain() {
		return this.productExplain;
	}

	/**
	 * @param productExplain
	 *            productExplain
	 */
	public void setProductExplain(String productExplain) {
		this.productExplain = productExplain;
	}

	/**
	 * @return String
	 */
	public String getProductDetailExplain() {
		return this.productDetailExplain;
	}

	/**
	 * @param productDetailExplain
	 *            productDetailExplain
	 */
	public void setProductDetailExplain(String productDetailExplain) {
		this.productDetailExplain = productDetailExplain;
	}

	/**
	 * 상품 소개 내용.
	 * 
	 * @return String
	 */
	public String getProductIntroduction() {
		return this.productIntroduction;
	}

	/**
	 * 상품 소개 내용.
	 * 
	 * @param productIntroduction
	 *            productIntroduction
	 */
	public void setProductIntroduction(String productIntroduction) {
		this.productIntroduction = productIntroduction;
	}

	/**
	 * @return String
	 */
	public String getTableOfContents() {
		return this.tableOfContents;
	}

	/**
	 * @param tableOfContents
	 *            tableOfContents
	 */
	public void setTableOfContents(String tableOfContents) {
		this.tableOfContents = tableOfContents;
	}

	/**
	 * @return String
	 */
	public String getAboutWriter() {
		return this.aboutWriter;
	}

	/**
	 * @param aboutWriter
	 *            aboutWriter
	 */
	public void setAboutWriter(String aboutWriter) {
		this.aboutWriter = aboutWriter;
	}

	/**
	 * @return String
	 */
	public String getRecommendedReason() {
		return this.recommendedReason;
	}

	/**
	 * @param recommendedReason
	 *            recommendedReason
	 */
	public void setRecommendedReason(String recommendedReason) {
		this.recommendedReason = recommendedReason;
	}

	/**
	 * @return Accrual
	 */
	public Accrual getAccrual() {
		return this.accrual;
	}

	/**
	 * @param accrual
	 *            accrual
	 */
	public void setAccrual(Accrual accrual) {
		this.accrual = accrual;
	}

	/**
	 * @return Rights
	 */
	public Rights getRights() {
		return this.rights;
	}

	/**
	 * @param rights
	 *            rights
	 */
	public void setRights(Rights rights) {
		this.rights = rights;
	}

	/**
	 * @return App
	 */
	public App getApp() {
		return this.app;
	}

	/**
	 * @param app
	 *            app
	 */
	public void setApp(App app) {
		this.app = app;
	}

	/**
	 * @return Music
	 */
	public Music getMusic() {
		return this.music;
	}

	/**
	 * @param music
	 *            music
	 */
	public void setMusic(Music music) {
		this.music = music;
	}

	/**
	 * @return Vod
	 */
	public Vod getVod() {
		return this.vod;
	}

	/**
	 * @param vod
	 *            vod
	 */
	public void setVod(Vod vod) {
		this.vod = vod;
	}

	/**
	 * @return VideoInfo
	 */
	public VideoInfo getVideoInfo() {
		return this.videoInfo;
	}

	/**
	 * @param videoInfo
	 *            videoInfo
	 */
	public void setVideoInfo(VideoInfo videoInfo) {
		this.videoInfo = videoInfo;
	}

	/**
	 * @return Book
	 */
	public Book getBook() {
		return this.book;
	}

	/**
	 * @param book
	 *            book
	 */
	public void setBook(Book book) {
		this.book = book;
	}

	/**
	 * @return Promotion
	 */
	public Promotion getPromotion() {
		return this.promotion;
	}

	/**
	 * @param promotion
	 *            promotion
	 */
	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	/**
	 * @return Contributor
	 */
	public Contributor getContributor() {
		return this.contributor;
	}

	/**
	 * @param contributor
	 *            contributor
	 */
	public void setContributor(Contributor contributor) {
		this.contributor = contributor;
	}

	/**
	 * @return Distributor
	 */
	public Distributor getDistributor() {
		return this.distributor;
	}

	/**
	 * @param distributor
	 *            distributor
	 */
	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	/**
	 * @return SalesOption
	 */
	public SalesOption getSalesOption() {
		return this.salesOption;
	}

	/**
	 * @param salesOption
	 *            salesOption
	 */
	public void setSalesOption(SalesOption salesOption) {
		this.salesOption = salesOption;
	}

	/**
	 * @return Purchase
	 */
	public Purchase getPurchase() {
		return this.purchase;
	}

	/**
	 * @param purchase
	 *            purchase
	 */
	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	/**
	 * @return the purchaseList
	 */
	public List<Purchase> getPurchaseList() {
		return this.purchaseList;
	}

	/**
	 * @param purchaseList
	 *            the purchaseList to set
	 */
	public void setPurchaseList(List<Purchase> purchaseList) {
		this.purchaseList = purchaseList;
	}

	/**
	 * @return Coupon
	 */
	public Coupon getCoupon() {
		return this.coupon;
	}

	/**
	 * @param coupon
	 *            coupon
	 */
	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	/**
	 * @return Date
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * @param date
	 *            date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return Event
	 */
	public Event getEvent() {
		return this.event;
	}

	/**
	 * @param event
	 *            event
	 */
	public void setEvent(Event event) {
		this.event = event;
	}

	/**
	 * @return SelectOption
	 */
	public SelectOption getSelectOption() {
		return this.selectOption;
	}

	/**
	 * @return the selectOptionList
	 */
	public List<SelectOption> getSelectOptionList() {
		return this.selectOptionList;
	}

	/**
	 * @param selectOptionList
	 *            the selectOptionList to set
	 */
	public void setSelectOptionList(List<SelectOption> selectOptionList) {
		this.selectOptionList = selectOptionList;
	}

	/**
	 * @param selectOption
	 *            selectOption
	 */
	public void setSelectOption(SelectOption selectOption) {
		this.selectOption = selectOption;
	}

	/**
	 * @return String
	 */
	public String getRestrictType() {
		return this.restrictType;
	}

	/**
	 * @param restrictType
	 *            restrictType
	 */
	public void setRestrictType(String restrictType) {
		this.restrictType = restrictType;
	}

	/**
	 * @return String
	 */
	public String getLatestIssue() {
		return this.latestIssue;
	}

	/**
	 * @param latestIssue
	 *            latestIssue
	 */
	public void setLatestIssue(String latestIssue) {
		this.latestIssue = latestIssue;
	}

	/**
	 * @return String
	 */
	public String getPhysicalPath() {
		return this.physicalPath;
	}

	/**
	 * @param physicalPath
	 *            physicalPath
	 */
	public void setPhysicalPath(String physicalPath) {
		this.physicalPath = physicalPath;
	}

	/**
	 * @return Integer
	 */
	public Integer getSubProductTotalCount() {
		return this.subProductTotalCount;
	}

	/**
	 * 
	 * @param subProductTotalCount
	 *            subProductTotalCount
	 */
	public void setSubProductTotalCount(Integer subProductTotalCount) {
		this.subProductTotalCount = subProductTotalCount;
	}

	/**
	 * @return List<Product>
	 */
	public List<Product> getSubProductList() {
		return this.subProductList;
	}

	/**
	 * @param subProductList
	 *            subProductList
	 */
	public void setSubProductList(List<Product> subProductList) {
		this.subProductList = subProductList;
	}

	/**
	 * @return List<Date>
	 */
	public List<Date> getDateList() {
		return this.dateList;
	}

	/**
	 * @param dateList
	 *            dateList
	 */
	public void setDateList(List<Date> dateList) {
		this.dateList = dateList;
	}

	/**
	 * @return the prodChrgYn
	 */
	public String getProdChrgYn() {
		return this.prodChrgYn;
	}

	/**
	 * @param prodChrgYn
	 *            the prodChrgYn to set
	 */
	public void setProdChrgYn(String prodChrgYn) {
		this.prodChrgYn = prodChrgYn;
	}

	/**
	 * @return the platClsfCd
	 */
	public String getPlatClsfCd() {
		return this.platClsfCd;
	}

	/**
	 * @param platClsfCd
	 *            the platClsfCd to set
	 */
	public void setPlatClsfCd(String platClsfCd) {
		this.platClsfCd = platClsfCd;
	}

	/**
	 * @return the dl
	 */
	public List<Encryption> getDl() {
		return this.dl;
	}

	/**
	 * @param dl
	 *            the dl to set
	 */
	public void setDl(List<Encryption> dl) {
		this.dl = dl;
	}

	/**
	 * @return the previewSourceList
	 */
	public List<Source> getPreviewSourceList() {
		return this.previewSourceList;
	}

	/**
	 * @param previewSourceList
	 *            the previewSourceList to set
	 */
	public void setPreviewSourceList(List<Source> previewSourceList) {
		this.previewSourceList = previewSourceList;
	}

}
