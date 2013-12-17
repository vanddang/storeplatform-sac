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
import com.skplanet.storeplatform.framework.core.common.vo.CommonVO;
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.ProductProto;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.DateVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.IdentifierVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.MenuVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.PriceVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.SourceVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.TitleVO;

/**
 * Interface Message Product Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(ProductProto.Product.class)
public class ProductVO extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 상품 ID
	 */
	private IdentifierVO identifier;
	/**
	 * identifier 추가 기술
	 */
	private String id;
	/**
	 * 지원 매체
	 */
	private String support;
	/**
	 * 지원 언어
	 */
	private String lang;
	/**
	 * 신규상품/마감상품 설정 > new : 신규상품 > closing : 마감임박
	 */
	private String status;
	/**
	 * title Message(상품명)
	 */
	private TitleVO title;
	/**
	 * 가격정보
	 */
	private PriceVO price;
	/**
	 * > Code : 대분류, 소분류 카테고리코드 > name : shoppingStore > type(option) : 대분류 카테고리시 topClass > categoryExplian : 카테고리 설명
	 */
	private List<MenuVO> menuList;
	/**
	 * * 패킷과금 타입 > paid : 일반 과금상품(PD000601) > library : 라이브러리(PD000602) > vm : VM(PD000603) > free : 일반 비과금상품(PD000604)
	 * > paid-rss : RSS 과금상품(PD000605) > free-rss : RSS 비과금상품(PD000606)
	 */
	private String packetFee;
	/**
	 * resource 정보
	 */
	private List<SourceVO> sourceList;
	/**
	 * > short : 한 줄 설명 > long : 상세 설명
	 */
	private String requestType;
	/**
	 * 상품설명
	 */
	private String productExplain;
	/**
	 * 목차정보(ebook일 경우)
	 */
	private String tableOfContents;
	/**
	 * 작가소개(ebook일 경우)
	 */
	private String aboutWriter;
	/**
	 * 앱 추천사유 (앱코디 리스트인 경우)
	 */
	private String recommendedReason;
	/**
	 * Accrual
	 */
	private AccrualVO accrual;
	/**
	 * 권한 정보
	 */
	private RightsVO rights;
	/**
	 * 앱 정보
	 */
	private AppVO app;
	/**
	 * 음악 부가 정보
	 */
	private MusicVO music;
	/**
	 * VOD 부가 정보
	 */
	private VodVO vod;
	/**
	 * VOD일경우 VOD 부가정보
	 */
	private VideoInfoVO videoInfo;
	/**
	 * ebook,Comic일 경우 부가정보
	 */
	private BookVO book; //
	/**
	 * 이벤트 상품 정보
	 */
	private PromotionVO promotion;
	/**
	 * 제작자 정보
	 */
	private ContributorVO contributorList;
	/**
	 * 제작사 또는 개발사 정보
	 */
	private DistributorVO distributor;
	/**
	 * 판매 option
	 */
	private SalesOptionVO salesOption;
	/**
	 * 구매 정보
	 */
	private PurchaseVO purchase;
	/**
	 * 쿠폰 정보
	 */
	private CouponVO coupon;
	/**
	 * 날짜 정보
	 */
	private DateVO date; //
	/**
	 * 이벤트 정보
	 */
	private EventVO event;
	/**
	 * 상품 선택 옵션 정보
	 */
	private SelectOptionVO selectOption;
	/**
	 * Restrict 유형 > ota : 네트워크 제한 > sales : 판매 중지
	 */
	private String restrictType;
	/**
	 * ebook의 최신호 정보
	 */
	private String latestIssue;

	public IdentifierVO getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(IdentifierVO identifier) {
		this.identifier = identifier;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSupport() {
		return this.support;
	}

	public void setSupport(String support) {
		this.support = support;
	}

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public TitleVO getTitle() {
		return this.title;
	}

	public void setTitle(TitleVO title) {
		this.title = title;
	}

	public PriceVO getPrice() {
		return this.price;
	}

	public void setPrice(PriceVO price) {
		this.price = price;
	}

	public List<MenuVO> getMenuList() {
		return this.menuList;
	}

	public void setMenuList(List<MenuVO> menuList) {
		this.menuList = menuList;
	}

	public String getPacketFee() {
		return this.packetFee;
	}

	public void setPacketFee(String packetFee) {
		this.packetFee = packetFee;
	}

	public List<SourceVO> getSourceList() {
		return this.sourceList;
	}

	public void setSourceList(List<SourceVO> sourceList) {
		this.sourceList = sourceList;
	}

	public String getRequestType() {
		return this.requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getProductExplain() {
		return this.productExplain;
	}

	public void setProductExplain(String productExplain) {
		this.productExplain = productExplain;
	}

	public String getTableOfContents() {
		return this.tableOfContents;
	}

	public void setTableOfContents(String tableOfContents) {
		this.tableOfContents = tableOfContents;
	}

	public String getAboutWriter() {
		return this.aboutWriter;
	}

	public void setAboutWriter(String aboutWriter) {
		this.aboutWriter = aboutWriter;
	}

	public String getRecommendedReason() {
		return this.recommendedReason;
	}

	public void setRecommendedReason(String recommendedReason) {
		this.recommendedReason = recommendedReason;
	}

	public AccrualVO getAccrual() {
		return this.accrual;
	}

	public void setAccrual(AccrualVO accrual) {
		this.accrual = accrual;
	}

	public RightsVO getRights() {
		return this.rights;
	}

	public void setRights(RightsVO rights) {
		this.rights = rights;
	}

	public AppVO getApp() {
		return this.app;
	}

	public void setApp(AppVO app) {
		this.app = app;
	}

	public MusicVO getMusic() {
		return this.music;
	}

	public void setMusic(MusicVO music) {
		this.music = music;
	}

	public VodVO getVod() {
		return this.vod;
	}

	public void setVod(VodVO vod) {
		this.vod = vod;
	}

	public VideoInfoVO getVideoInfo() {
		return this.videoInfo;
	}

	public void setVideoInfo(VideoInfoVO videoInfo) {
		this.videoInfo = videoInfo;
	}

	public BookVO getBook() {
		return this.book;
	}

	public void setBook(BookVO book) {
		this.book = book;
	}

	public PromotionVO getPromotion() {
		return this.promotion;
	}

	public void setPromotion(PromotionVO promotion) {
		this.promotion = promotion;
	}

	public ContributorVO getContributorList() {
		return this.contributorList;
	}

	public void setContributorList(ContributorVO contributorList) {
		this.contributorList = contributorList;
	}

	public DistributorVO getDistributor() {
		return this.distributor;
	}

	public void setDistributor(DistributorVO distributor) {
		this.distributor = distributor;
	}

	public SalesOptionVO getSalesOption() {
		return this.salesOption;
	}

	public void setSalesOption(SalesOptionVO salesOption) {
		this.salesOption = salesOption;
	}

	public PurchaseVO getPurchase() {
		return this.purchase;
	}

	public void setPurchase(PurchaseVO purchase) {
		this.purchase = purchase;
	}

	public CouponVO getCoupon() {
		return this.coupon;
	}

	public void setCoupon(CouponVO coupon) {
		this.coupon = coupon;
	}

	public DateVO getDate() {
		return this.date;
	}

	public void setDate(DateVO date) {
		this.date = date;
	}

	public EventVO getEvent() {
		return this.event;
	}

	public void setEvent(EventVO event) {
		this.event = event;
	}

	public SelectOptionVO getSelectOption() {
		return this.selectOption;
	}

	public void setSelectOption(SelectOptionVO selectOption) {
		this.selectOption = selectOption;
	}

	public String getRestrictType() {
		return this.restrictType;
	}

	public void setRestrictType(String restrictType) {
		this.restrictType = restrictType;
	}

	public String getLatestIssue() {
		return this.latestIssue;
	}

	public void setLatestIssue(String latestIssue) {
		this.latestIssue = latestIssue;
	}
}
