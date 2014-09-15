/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.common.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * SAC 전시 상수 클래스 전시 파트 내에서 사용되는 상수는 여기에 정의한다.
 *
 * Updated on : 2014. 1. 30. Updated by : 서대영, SK 플래닛.
 */
public class DisplayConstants {

    static {
        SET_SERIES_META = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("CT14,CT15,CT16,CT20,CT21,CT22".split(","))));
    }

    public static final Set<String> SET_SERIES_META; // 시리즈물인 metaClsfCd Set


	// 상품 서비스 그룹 코드

	/**
	 * 멀티미디어.
	 */
	public static final String DP_MULTIMEDIA_PROD_SVC_GRP_CD = "DP000203";
	/**
	 * Tstore 쇼핑.
	 */
	public static final String DP_TSTORE_SHOPPING_PROD_SVC_GRP_CD = "DP000206";

	/**
	 * 정액권.
	 */
	public static final String DP_TSTORE_FREEPASS_PROD_SVC_GRP_CD = "DP000207";
	/**
	 * 소셜쇼핑.
	 */
	public static final String DP_SOCIAL_SHOPPING_PROD_SVC_GRP_CD = "DP000205";
	/**
	 * 폰꾸미기.
	 */
	public static final String DP_PHONE_DECO_PROD_SVC_GRP_CD = "DP000204";
	/**
	 * 애플리캐이션.
	 */
	public static final String DP_APP_PROD_SVC_GRP_CD = "DP000201";

	// 상품 서비스 타입 코드
	/**
	 * 제너럴 Type.
	 */
	public static final String DP_GENERAL_TYPE_SVC_TYPE_CD = "DP001113";
	/**
	 * 뮤직 Type.
	 */
	public static final String DP_MUSIC_TYPE_SVC_TYPE_CD = "DP001111";
	/**
	 * APP Type.
	 */
	public static final String DP_APP_TYPE_SVC_TYPE_CD = "DP001114";
	/**
	 * 고화질 동영상 Type.
	 */
	public static final String DP_HVOD_TYPE_SVC_TYPE_CD = "DP001115";
	/**
	 * 동영상 Type.
	 */
	public static final String DP_VOD_TYPE_SVC_TYPE_CD = "DP001112";
	/**
	 * 이북(eBook)/툰도시(Comic) Type.
	 */
	public static final String DP_EBOOK_COMIC_TYPE_SVC_TYPE_CD = "DP001116";
	/**
	 * Tstore 쇼핑 Type.
	 */
	public static final String DP_TSOTRE_SHOPPING_TYPE_SVC_TYPE_CD = "DP001117";

	public static final String DP_CATALOG_IDENTIFIER_CD = "catalog";
	public static final String DP_CHANNEL_IDENTIFIER_CD = "channel";
	public static final String DP_CONTENT_IDENTIFIER_CD = "content";
	public static final String DP_EPISODE_IDENTIFIER_CD = "episode";
	public static final String DP_ARTIST_IDENTIFIER_CD = "artist";
	public static final String DP_INDIVIDUAL_IDENTIFIER_CD = "individual";
	public static final String DP_PRIVATEOPERATOR_IDENTIFIER_CD = "privateOperator";
	public static final String DP_CORPORATION_IDENTIFIER_CD = "corporation";
	public static final String DP_FOREIGNER_IDENTIFIER_CD = "foreigner";
	public static final String DP_BRAND_IDENTIFIER_CD = "brand";
	public static final String DP_THEME_IDENTIFIER_CD = "theme";
	public static final String DP_SONG_IDENTIFIER_CD = "song";
	public static final String DP_OUTSDCONTENTS_ONE_CD = "oneday";
	public static final String DP_PURCHASE_IDENTIFIER_CD = "purchase";
	public static final String DP_DOWNLOAD_IDENTIFIER_CD = "download";
	public static final String DP_GAMECENTER_IDENTIFIER_CD = "gamecenter";
	public static final String DP_FREEPASS_IDENTIFIER_CD = "freepass";
	public static final String DP_PROMOTION_IDENTIFIER_CD = "promotion";
	public static final String DP_BANNER_IDENTIFIER_CD = "banner";
	public static final String DP_OUTSDCONTENTS_IDENTIFIER_CD = "outsdContents";
	public static final String DP_PRODUCT_IDENTIFIER_CD = "product";

	// //////////////////////////////////// TOP MENU ID //////////////////////////////////////////
	/**
	 * 앱 전체
	 */
	public static final String DP_APPALL_TOP_MENU_ID = "DPAPP";
	/**
	 * 게임.
	 */
	public static final String DP_GAME_TOP_MENU_ID = "DP01";
	/**
	 * 폰꾸미기.
	 */
	public static final String DP_DISPLAY_PHONE_TOP_MENU_ID = "DP02";

	/**
	 * FUN.
	 */
	public static final String DP_FUN_TOP_MENU_ID = "DP03";
	/**
	 * 생활/위치.
	 */
	public static final String DP_LIFE_LIVING_TOP_MENU_ID = "DP04";
	/**
	 * 어학/교육.
	 */
	public static final String DP_LANG_EDU_TOP_MENU_ID = "DP08";
	/**
	 * VOD 다운로드.
	 */
	public static final String DP_VOD_TOP_MENU_ID = "DP09";
	/**
	 * Android.
	 */
	public static final String DP_ANDROID_TOP_MENU_ID = "DP12";
	/**
	 * eBook.
	 */
	public static final String DP_EBOOK_TOP_MENU_ID = "DP13";
	/**
	 * 만화.
	 */
	public static final String DP_COMIC_TOP_MENU_ID = "DP14";
	/**
	 * 쇼핑/쿠폰.
	 */
	public static final String DP_SHOPPING_COUPON_TOP_MENU_ID = "DP15";
	/**
	 * 음악.
	 */
	public static final String DP_MUSIC_TOP_MENU_ID = "DP16";
	/**
	 * 영화.
	 */
	public static final String DP_MOVIE_TOP_MENU_ID = "DP17";

	/**
	 * TV 방송.
	 */
	public static final String DP_TV_TOP_MENU_ID = "DP18";
	/**
	 * 웹툰.
	 */
	public static final String DP_WEBTOON_TOP_MENU_ID = "DP26";

	/**
	 * 쇼핑.
	 */
	public static final String DP_SHOPPING_TOP_MENU_ID = "DP28";

	// ////////////////////////////////////// ETC Code //////////////////////////////////////////
	/**
	 * 영화.
	 */
	public static final String DP_MOVIE_ETC_CD = "DP004801";

	// ////////////////////////////////////// Content Type Code //////////////////////////////////////////
	/**
	 * 채널 타입.
	 */
	public static final String DP_CHANNEL_CONTENT_TYPE_CD = "PD002501";
	/**
	 * 에피소드 타입.
	 */
	public static final String DP_EPISODE_CONTENT_TYPE_CD = "PD002502";

	/**
	 * 구독상품
	 */
	public static final String DP_SUBSCRIPTION_CD = "DP001401";

	/**
	 * 비구독상품
	 */
	public static final String DP_NOT_SUBSCRIPTION_CD = "DP001402";

	// //////////////////////////////////////Content Type Code //////////////////////////////////////////
	/**
	 * 판매상태.
	 */
	public static final String DP_SALE_STAT_ING = "PD000403"; // 판매중
	public static final String DP_SALE_STAT_WAIT = "PD000402"; // 판매대기
	public static final String DP_SALE_STAT_STOP = "PD000404"; // 판매중지
	public static final String DP_SALE_STAT_RESTRIC = "PD000405"; // 판매불가
	public static final String DP_SALE_STAT_DROP_REQ = "PD000406"; // 해지요청
	public static final String DP_SALE_STAT_PAUSED = "PD000407"; // 일시정지
	public static final String DP_SALE_STAT_DELETED = "PD000408"; // 판매삭제
	public static final String DP_SALE_STAT_RESTRIC_DN = "PD000409"; // 판매불가
	public static final String DP_SALE_STAT_DROP_REQ_DN = "PD000410"; // 해지요청(다운허용)

	/**
	 * 정액제 상품 판매상태.
	 */
	public static final String DP_PASS_SALE_STAT_WAIT = "PD013201"; // 판매대기
	public static final String DP_PASS_SALE_STAT_ING = "PD013202"; // 판매중
	public static final String DP_PASS_SALE_STAT_STOP = "PD013203"; // 판매중지
	public static final String DP_PASS_SALE_STAT_RESTRIC = "PD013204"; // 판매금지
	public static final String DP_PASS_SALE_STAT_FINISH = "PD013205"; // 판매종료

	// //////////////////////////////////////Source Code //////////////////////////////////////////
	public static final String DP_THUMNAIL_SOURCE = "thumbnail";
	public static final String DP_DLM_SOURCE = "dlm";

	public static final String DP_MENU_TOPCLASS_TYPE = "topClass";
	public static final String DP_META_CLASS_MENU_TYPE = "metaClass";
	public static final String DP_SVC_GRP_CD_TYPE = "svcGrpCd";
	/** 장르. */
	public static final String DP_MENU_TYPE_GENRE = "genre";
	public static final String DP_MENU_TYPE_SUB_GENRE = "subGenre";

	public static final String DP_MUSIC_SERVICE_MP3 = "mp3";
	public static final String DP_MUSIC_SERVICE_BELL = "bell";
	public static final String DP_MUSIC_SERVICE_RING = "ring";

	// ////////////////////////////////////// supported hardware 정보 //////////////////////////////////////////
	public static final String DP_DRM_SUPPORT_NM = "drm";
	public static final String DP_IN_APP_SUPPORT_NM = "iab";
	public static final String DP_COLOR_SUPPORT_NM = "color";

	/**
	 * 부모-자식 상품 관계 코드
	 */
	public static final String DP_PARENT_CHILD_RELATIONSHIP_CD = "DP010801";

	/**
	 * 채널-에피소드 상품 관계 코드
	 */
	public static final String DP_CHANNEL_EPISHODE_RELATIONSHIP_CD = "DP010802";

	/**
	 * 폰 타입 코드.
	 */
	public static final String DP_PHONE_DEVICE_TYPE_CD = "PD011502";

	public static final String DP_PHONE_DEVICE_TYPE_NM = "phone";

	/**
	 * 태블릿 타입 코드.
	 */
	public static final String DP_TABLET_DEVICE_TYPE_CD = "PD011503";

	public static final String DP_TABLET_DEVICE_TYPE_NM = "tablet";

	/**
	 * OMD 단말 타입.
	 */
	public static final String DP_OMD_TYPE_CD = "US001204";

	public static final String DP_OMD_TYPE_NM = "omd";

	public static final String DP_OMD_NORMAL_NM = "normal";

	public static final String DP_EBOOK_SERIAL_NM = "serial";

	public static final String DP_EBOOK_COMPLETED_NM = "completed";

	public static final String DP_EBOOK_CONTINUE_NM = "continue";

	/** eBook 단편 메타 클래스 코드 */
	public static final String DP_BOOK_META_CLASS_CD = "CT19";
	/**
	 * eBook 시리즈 메타 클래스 코드.
	 */
	public static final String DP_SERIAL_META_CLASS_CD = "CT20";

	/** 잡지 */
	public static final String DP_MAGAZINE_META_CLASS_CD = "CT24";
	/** 인터렉티브 잡지 */
	public static final String DP_INTERACTIVE_MAGAZINE_META_CLASS_CD = "CT26";
	/** webtoon */
	public static final String DP_INTERACTIVE_WEBTOON_META_CLASS_CD = "CT27";

	public static final String DP_EBOOK_STORE_SUPPORT_NM = "store";

	public static final String DP_EBOOK_PLAY_SUPPORT_NM = "play";

	/** 이북/코믹 book.type */
	public static final String DP_BOOK_TYPE_BOOK = "book";
	public static final String DP_BOOK_TYPE_SERIAL = "serial";

	// //////////////////////////////////// Source Type //////////////////////////////////////////
	/** 상품. */
	public static final String DP_SOURCE_TYPE_PRODUCT = "product";
	/** 썸네일. */
	public static final String DP_SOURCE_TYPE_THUMBNAIL = "thumbnail";
	/** 원본. */
	public static final String DP_SOURCE_TYPE_ORIGINAL = "original";
	/** 동영상. */
	public static final String DP_SOURCE_TYPE_MOVIE = "movie";
	/** 스크린샷. */
	public static final String DP_SOURCE_TYPE_SCREENSHOT = "screenshot";
	/** 음악. */
	public static final String DP_SOURCE_TYPE_MUSIC = "music";
	/** 바코드이미지. */
	public static final String DP_SOURCE_TYPE_BARCODE = "barcode";
	/** 배너이미지. */
	public static final String DP_SOURCE_TYPE_BANNER = "banner";
	/** 프로모션이미지. */
	public static final String DP_SOURCE_TYPE_PROMOTION = "promotion";
	/** 미리보기. */
	public static final String DP_SOURCE_TYPE_PREVIEW = "preview";

	/** 음악 mp3-192. */
	public static final String DP_SOURCE_TYPE_AUDIO_MP3_192 = "audio/mp3-192";
	/** 음악 mp3-128. */
	public static final String DP_SOURCE_TYPE_AUDIO_MP3_128 = "audio/mp3-128";
	/** Intimate Message 배경이미지. */
	public static final String DP_SOURCE_TYPE_GNB_BG = "gnb/bg";
	/** Intimate Message 아이콘 이미지. */
	public static final String DP_SOURCE_TYPE_GNB_ICON = "gnb/icon";

	// //////////////////////////////////// 이미지 코드 //////////////////////////////////////////
	public static final String DP_APP_REPRESENT_IMAGE_CD = "DP000101";

	public static final String DP_VOD_REPRESENT_IMAGE_CD = "DP000127";

	public static final String DP_EBOOK_COMIC_REPRESENT_IMAGE_CD = "DP006207"; /* 이북(채널,시리즈에피소드) + 코믹(채널) 이미지 코드 */
	public static final String DP_COMIC_EPISODE_REPRESENT_IMAGE_CD = "DP000194"; /* 코믹 에피소드 이미지 코드 */

	public static final String DP_MUSIC_REPRESENT_IMAGE_CD = "DP000162";

	public static final String DP_WEBTOON_REPRESENT_IMAGE_CD = "DP000196";

	public static final String DP_SHOPPING_REPRESENT_IMAGE_CD = "DP0001A3";

	public static final String DP_SHOPPING_SPECIAL_BANNER_IMAGE_CD = "DP0001A8";

	public static final String DP_SHOPPING_SPECIAL_PROMOTION_IMAGE_CD = "DP0001A6";

	public static final String DP_SHOPPING_BRAND_REPRESENT_IMAGE_CD = "DP0001B9";

	public static final String DP_SHOPPING_THEME_REPRESENT_IMAGE_CD = "DP0001A7";

	public static final String DP_SHOPPING_REPRESENT_CUT_DETAIL_IMAGE_CD = "DP0001B0";

	public static final String DP_SHOPPING_REPRESENT_DETAIL_IMAGE_CD = "DP0001B4";

	public static final String DP_OPENAPI_APP_PREVIEW_IMAGE_CD = "DP000116";

	// 정액제 배너이미지
	public static final String DP_FREEPASS_BANNER_IMAGE_CD = "DP000197";
	// 정액제 썸네일이미지
	public static final String DP_FREEPASS_THUMBNAIL_IMAGE_CD = "DP000199";
	// 정액제(이북/코믹전권) 썸네일이미지
	public static final String DP_FREEPASS_EBOOK_THUMBNAIL_IMAGE_CD = "DP000153";
	// //////////////////////////////////// VOD META CODE //////////////////////////////////////////

	public static final String DP_SERIAL_VOD_META_CLASS_CD = "CT14";
	public static final String DP_SERIAL_VOD_LANGUAGE_META_CLASS_CD = "CT15";
	public static final String DP_SERIAL_VOD_SKT_META_CLASS_CD = "CT16";

	// //////////////////////////////////// VOD 화질 유형 //////////////////////////////////////////
	public static final String DP_VOD_QUALITY_NORMAL = "normal";

	public static final String DP_VOD_QUALITY_SD = "sd";

	public static final String DP_VOD_QUALITY_HD = "hd";

	// //////////////////////////////////// VOD 지원명 //////////////////////////////////////////

	public static final String DP_VOD_HDCP_SUPPORT_NM = "hdcp";

	public static final String DP_VOD_HD_SUPPORT_NM = "hd";

	public static final String DP_VOD_BTV_SUPPORT_NM = "btv";

	public static final String DP_VOD_DOLBY_SUPPORT_NM = "dolbySprt";

	public static final String DP_VOD_DOLBY_NM = "dolby";

	// ////////////////////////////////// 도서 유형 //////////////////////////////////////////
	/** 단행본. */
	public static final String DP_BOOK_BOOK = "DP004301";
	/** 연재. */
	public static final String DP_BOOK_SERIAL = "DP004302";
	/** 잡지. */
	public static final String DP_BOOK_MAGAZINE = "DP004303";
	/** 웹툰. */
	public static final String DP_BOOK_WEBTOON = "DP004304";

	// ////////////////////////////////// ETC //////////////////////////////////////////

	public static final String DP_NETWORK_RESTRICT = "ota";

	public static final String DP_SHOPPING_RECENT_DEFAULT_ORDERED_OPTION = "recent";

	public static final String DP_SHOPPING_POPULAR_DEFAULT_ORDERED_OPTION = "popular";

	public static final String DP_SHOPPING_CLOSINGTIME_DEFAULT_ORDERED_OPTION = "closingTime";

	public static final String DP_USE_PERIOD_UNIT_CD_NONE = "PD00310"; // 사용기간 단위 코드 (무제한)
	public static final String DP_USE_PERIOD_UNIT_CD_HOUR = "PD00311"; // 사용기간 단위 코드(시간 )
	public static final String DP_USE_PERIOD_UNIT_CD_DAY = "PD00312"; // 사용기간 단위 코드(일 )
	public static final String DP_USE_PERIOD_UNIT_CD_MONTH = "PD00313"; // 사용기간 단위 코드(월 )
	public static final String DP_USE_PERIOD_UNIT_CD_YEAR = "PD00314"; // 사용기간 단위 코드(년 )
	public static final String DP_USE_PERIOD_UNIT_CD_LIMIT_DAY = "PD00315"; // 사용기간 단위 코드(당일 )
	public static final String DP_USE_PERIOD_UNIT_CD_LIMIT_MONTH = "PD00316"; // 사용기간 단위 코드(당월 )
	public static final String DP_USE_PERIOD_UNIT_CD_LIMIT_YEAR = "PD00317"; // 사용기간 단위 코드(당년 )
	public static final String DP_USE_PERIOD_UNIT_CD_MINUTE = "PD00318"; // 사용기간 단위 코드(분 )
	public static final String DP_USE_PERIOD_UNIT_CD_CALENDAR = "PD00319"; // 사용기간 단위 코드(기간선택)

	// ////////////////////////////////// 날짜 유형 //////////////////////////////////////////
	/** 사용기간-FROM/TO. */
	public static final String DP_DATE_USAGE_PERIOD = "duration/usagePeriod";
	/** 사용기간-단위. */
	public static final String DP_DATE_TYPE_USE_PERIOD = "unit/usagePeriod";
	/** 등록 날짜. */
	public static final String DP_DATE_REG = "date/reg";
	/** 판매등록 날짜. */
	public static final String DP_DATE_SALE_REG = "date/saleReg";
	/** 개봉일. */
	public static final String DP_DATE_RELEASE = "date/release";
	/** 출판일. */
	public static final String DP_DATE_PUBLISH = "date/publish";
	/** 방송 날짜. */
	public static final String DP_DATE_BROADCAST = "date/broadcast";
	/** 발행일. */
	public static final String DP_DATE_ISSUE = "date/issue";
	/** 판매기간-FROM/TO. */
	public static final String DP_DATE_SALE_PERIOD_DURATION = "duration/salePeriod";
	/** 구매 날짜. */
	public static final String DP_DATE_PURCHASE = "date/purchase";

	// ////////////////////////////////// Shopping Rights Type 정보 //////////////////////////////////////////

	public static final String DP_DATE_DOWNLOAD_EXPIRED_NM = "date/downloadExpired";

	public static final String DP_DATE_UPT_NM = "date/upt";

	public static final String DP_SOLDOUT = "soldout";

	public static final String DP_CONTINUE = "continue";

	public static final String DP_SHOPPING_BRAND_HOT = "hot";

	/**
	 * For Download.
	 */
	public static final String DP_FORDOWNLOAD_ENCRYPT_DIGEST = "HexString"; // for download 암호화 digest

	public static final String DP_FORDOWNLOAD_ENCRYPT_TYPE = "AES128"; // for download 암호화 방식

	public static final String DP_FORDOWNLOAD_BP_DEFAULT_TYPE = "00"; // for download 기본 DRM 타입

	public static final String DP_FORDOWNLOAD_BP_EBOOK_TYPE = "01"; // for download 인터파크 DRM 타입

	public static final String DP_DEVICE_ID_TYPE_MSISDN = "msisdn"; // device type msisdn

	public static final String DP_DEVICE_ID_TYPE_MAC = "mac"; // device type mac

	public static final String DP_DEVICE_ID_TYPE_UUID = "uuid"; // device type uuid

	public static final String DP_DEVICE_SERVICE_TYPE_TING = "NA00002125"; // 팅 요금제(한도요금 상품)

	public static final String DP_PACKETFEE_TYPE_PAID = "paid"; // 유료 과금

	public static final String DP_PACKETFEE_TYPE_HALFPAID = "half"; // 50% 할인 과금

	public static final String DP_OPENAPI_PRODUCT_SEARCHTYPE_PRODNM = "prodNm"; // openapi 상품 검색 유형 (by 상품명)

	public static final String DP_OPENAPI_PRODUCT_SEARCHTYPE_SELLERNM = "sellerNm"; // openapi 상품 검색 유형 (by 판매자명)

	public static final String DP_TELECOM_TYPE_CD_SKT = "US001201"; // 통신사 코드 타입 (US001201 : SKT)

	/*
	 * 배너 타입 코드
	 */
	public static final String DP_BANNER_URL_NEW_CD = "DP010301"; // URL 직접 입력, 새 페이지(New) 연결
	public static final String DP_BANNER_URL_POPUP_CD = "DP010302"; // URL 직접 입력, 매뉴 내 삽입(POPUP) 연결
	public static final String DP_BANNER_PRODUCT_CD = "DP010303"; // 상품 지정 입력
	public static final String DP_BANNER_PRODUCT_NEW_CD = "DP010304"; // 상품 지정 입력, 새 페이지(New) 연결
	public static final String DP_BANNER_PRODUCT_POPUP_CD = "DP010305"; // 상품 지정 입력, 매뉴 내 삽입(POPUP) 연결
	public static final String DP_BANNER_CATEGORY_CD = "DP010306"; // 카테고리모바일배너
	public static final String DP_BANNER_ADMIN_RECOMM_CD = "DP010307"; // 운영자 임의 추천
	public static final String DP_BANNER_BRANDSHOP_LIST_CD = "DP010308"; // 브랜드샵 리스트
	public static final String DP_BANNER_SPECIFIC_BRANDSHOP_CD = "DP010309"; // 특정 브랜드샵
	public static final String DP_BANNER_INTERNAL_URL_CD = "DP010310"; // 내부URL연결
	public static final String DP_BANNER_SITUATIONAL_RECOMM_CD = "DP010311"; // 상황별 추천리스트 연결
	public static final String DP_BANNER_THEME_RECOMM_CD = "DP010312"; // 테마추천 리스트 연결

	public static final String DP_BANNER_TYPE_PRODUCT = "product"; // 배너타입 : 상품
	public static final String DP_BANNER_TYPE_EXTERNAL_URL = "externalUrl"; // 배너타입 : 외부 URL 새 페이지(New)
	public static final String DP_BANNER_TYPE_POPUP_URL = "popupUrl"; // 배너타입 : 외부 URL 매뉴 내 삽입(POPUP)
	public static final String DP_BANNER_TYPE_URL = "url"; // 배너타입 : 내부 URL
	public static final String DP_BANNER_TYPE_CATEGORY = "category"; // 배너타입 : 카테고리
	public static final String DP_BANNER_TYPE_THEME_ZONE = "themeZone"; // 배너타입 : 운영자 임의 추천
	public static final String DP_BANNER_TYPE_BRAND_SHOP_CATEGORY = "brandShopCategory"; // 배너타입 : 브랜드샵 리스트
	public static final String DP_BANNER_TYPE_BRAND_SHOP = "brandShop"; // 배너타입 : 특정 브랜드샵
	public static final String DP_BANNER_TYPE_THEME_RECOMM = "themeRecomm"; // 배너타입 : 상황별 추천
	public static final String DP_BANNER_TYPE_APP_GUIDE = "appGuide"; // 배너타입 : 앱가이드 테마
	public static final String DP_ORDEREDBY_TYPE_RECENT = "recent";
	public static final String DP_ORDEREDBY_TYPE_NONPAYMENT = "nonPayment";
	public static final String DP_ORDEREDBY_TYPE_POPULAR = "popular";
	public static final String DP_ORDEREDBY_TYPE_RECOMMEND = "recommend";
	/*
	 * 배너 탑 코드
	 */
	public static final String DP_EBOOK_THEME_BANNER_CD = "DP010913"; // Ebook 테마 배너
	public static final String DP_COMIC_THEME_BANNER_CD = "DP010914"; // Comic 테마 배너

	/*
	 * 배너 그룹 코드
	 */
	public static final String DP_BANNER_MOBILE_CLIENT_CD = "MBI000000003"; // 배너관리 > 모바일 클라이언트

	// ////////////////////////////////////// rights //////////////////////////////////////////
	public static final String DP_RIGHTS_ALLOW_FREEPASS = "freepass";
	public static final String DP_RIGHTS_ALLOW_DOMESTIC = "domestic"; // dwldAreaLimitYn 다운로드 지역제한 == 'Y' 일 경우 domestic
	public static final String DP_RIGHTS_ALLOW_SUBSCRIPTION = "subscription";
	public static final String DP_RIGHTS_ALLOW_FEEDBACK = "feedback";
	// 리턴
	/** 바로보기/대여. */
	public static final String DP_RIGHTS_PLAY = "play";
	/** 다운로드/소장. */
	public static final String DP_RIGHTS_STORE = "store";

	// ////////////////////////////////////// preview //////////////////////////////////////////
	public static final String DP_PREVIEW_HQ = "video/x-freeview-hq";
	public static final String DP_PREVIEW_LQ = "video/x-freeview-lq";
	public static final String DP_EPUB_PREVIEW = "epub/x-freeview";

	/**
	 * 특정 상품 조회 API 파라미터 제한 개수.
	 */
	public static final Integer DP_CATEGORY_SPECIFIC_PRODUCT_PARAMETER_LIMIT = Integer.valueOf(50);
	
	/**
	 * 특정 상품 eBook 조회 API 파라미터 제한 개수.
	 */
	public static final Integer DP_CATEGORY_SPECIFIC_PRODUCT_EBOOK_PARAMETER_LIMIT = Integer.valueOf(100);

	/**
	 * 특정 상품 MUSIC 조회 API 파라미터 100개로 제한 .
	 */
	public static final Integer DP_CATEGORY_SPECIFIC_PRODUCT_MUSIC_PARAMETER_LIMIT = Integer.valueOf(100);

	/**
	 * 구매 내역 조회 시 필요한 상품 메타 정보 조회 API 파라미터 제한 개수.
	 */
	public static final Integer DP_PRODUCT_INFO_PARAMETER_LIMIT = Integer.valueOf(100);

	/**
	 * 결제 시 필요한 상품 메타 정보 조회 API 파라미터 제한 개수.
	 */
	public static final Integer DP_PAYMENT_INFO_PARAMETER_LIMIT = Integer.valueOf(100);

	/**
	 * 특정 상품 조회 API 파라미터 제한 개수.
	 */
	public static final Integer DP_UPDATE_PARAM_LIMIT = Integer.valueOf(1000);

	/**
	 * 업데이트 목록 조회 API 파라미터 제한 개수.
	 */
	public static final Integer DP_PERSONAL_UPDATE_PARAM_LIMIT = Integer.valueOf(1000);

	/**
	 * 상품 ID에 대한 단말 Provisioning 조회 API 파라미터 제한 개수.
	 */
	public static final Integer DP_DEVICE_PROVISIONG_PARAMETER_LIMIT = Integer.valueOf(100);

	/**
	 * 미지원 단말.
	 */
	public static final String DP_ANDROID_STANDARD_NM = "android_standard";

	/**
	 * 가상 모델명.
	 */
	public static final String DP_ANDROID_STANDARD2_NM = "android_standard2";

	/**
	 * 가상 프로비저닝 모델명 (앱).
	 */
	public static final String DP_ANY_PHONE_4APP = "ANY-PHONE-4APP";

	/**
	 * 가상 프로비저닝 모델명 (멀티미디어).
	 */
	public static final String DP_ANY_PHONE_4MM = "ANY-PHONE-4MM";

	/**
	 * 카테고리 태그.
	 */
	public static final String DP_CATEGORY_TAG_CD = "DP004501";
	/**
	 * 키워드 태그.
	 */
	public static final String DP_KEYWORD_TAG_CD = "DP004502";
	/**
	 * 셀러 태그.
	 */
	public static final String DP_SELLER_TAG_CD = "DP004503";
	/**
	 * 테이스트 대상 태그.
	 */
	public static final String DP_TASTE_TARGET_CD = "DP004504";
	/**
	 * 테이스트 장소 태그.
	 */
	public static final String DP_TASTE_PLACE_CD = "DP004505";
	/**
	 * 테이스트 목적 태그.
	 */
	public static final String DP_TASTE_PURPOSE_CD = "DP004506";
	/**
	 * 쇼핑/쿠폰 태그.
	 */
	public static final String DP_SHOPPING_COUPON_CD = "DP004507";

	/**
	 * 구매.
	 */
	public static final String PRCHS_PROD_HAVE_YES = "Y"; // 상품보유
	public static final String PRCHS_PROD_HAVE_NO = "N"; // 상품 미보유
	public static final String PRCHS_PROD_TYPE_UNIT = "OR020201"; // 단위상품
	public static final String PRCHS_PROD_TYPE_FREEPASS = "OR020202"; // 권한상품
	public static final String PRCHS_CASE_PURCHASE_CD = "OR020301"; // 구매
	public static final String PRCHS_CASE_GIFT_CD = "OR020302"; // 선물
	public static final String PRCHS_START_DATE = "19000101000000"; // 구매조회 시작일시
	public static final String PRCHS_STATE_TYPE_EXPIRED = "expired"; // 구매상태(만료)
	public static final String PRCHS_STSTUS_COMPLETE_CD = "OR000301"; // 구매상태코드 - 완료

	// 구매 경로 - T freemium
	public static final String PRCHS_REQ_PATH_TFREEMIUM1_CD = "OR000413";
	public static final String PRCHS_REQ_PATH_TFREEMIUM2_CD = "OR000420";

	// 정액제 상품 자동결제 구분
	public static final String DP_AUTOPAY_AUTO = "auto"; // 자동결제
	public static final String DP_AUTOPAY_NORMAL = "normal"; // 일반결제
	public static final String DP_FREEPASS_CASH = "cash"; // 게임 캐쉬
	public static final String DP_FREEPASS_BONUS = "bonus"; // 게임 보너스

	/**
	 * OpenApi Release Type.
	 */
	public static final String DP_OPENAPI_RELEASETYPE_DAY = "1"; // 1일 이내
	public static final String DP_OPENAPI_RELEASETYPE_WEEK = "7"; // 7일 이내

	/** 사용자구분코드. */
	public static final String MEMBER_MAIN_STATUS_NORMAL = "US010201"; // 정상
	public static final String MEMBER_MAIN_STATUS_PAUSE = "US010204"; // 7일이용정지/30일이용정지/영구이용정지

	/**
	 * 일반 컬러링 메타 클래스 코드.
	 */
	public static final String DP_MUSIC_NORMAL_COLORRING_META_CLASS_CD = "CT30";
	/**
	 * 롱 컬러링 메타 클래스 코드.
	 */
	public static final String DP_MUSIC_LONG_COLORRING_META_CLASS_CD = "CT31";
	/**
	 * 일반 라이브벨 메타 클래스 코드.
	 */
	public static final String DP_MUSIC_NORMAL_BELL_META_CLASS_CD = "CT32";
	/**
	 * 고음질 라이브벨 메타 클래스 코드.
	 */
	public static final String DP_MUSIC_HIGH_QUALITY_BELL_META_CLASS_CD = "CT33";

	/**
	 * VOD 단편 메타 클래스 코드.
	 */
	public static final String DP_VOD_SHORT_STORY_CLASS_CD = "CT13";

	public static final String DP_MUSIC_COLORING_TYPE = "colorRing";

	public static final String DP_MUSIC_BELL_TYPE = "bell";

	public static final String DP_MUSIC_NORMAL_QUALITY = "normal";

	public static final String DP_MUSIC_HIGH_QUALITY = "long";

	/**
	 * 부분유료화_부모상품.
	 */
	public static final String DP_PART_PARENT_CLSF_CD = "PD012301";

	/**
	 * 부분유료화_자식상품
	 */
	public static final String DP_PART_CHILD_CLSF_CD = "PD012302";

	/**
	 * OPENAPI APP URL
	 */
	public static final String DP_OPENAPI_APP_URL = "http://www.tstore.co.kr/userpoc/game/view?pid=";

	public static final String DP_OPENAPI_SC_URL = "http://tsto.re/";

	public static final String DP_EXTERNAL_CLIENT = "externalClient";

	public static final String DP_EXTERNAL_PORTAL = "externalPotal";

	public static final String DP_EXTERNAL = "external";

	public static final String DP_PURSTAT_AVAILABLE = "available";

	public static final String DP_PURSTAT_RESTRICTED = "restricted";

	public static final String DP_LIST_NEWFREE = "ADM000000001";

	public static final String DP_LIST_SHOPPING_POPULAR = "RNK000000006";

	public static final String DP_LIST_CATEGORY_SHOPPING_POPULAR = "RNK000000101";

	/** 할인율 product.point */
	public static final String DC_RATE_TMEMBERSHIP = "tmembership";
	/** 할인율 유형 - 일반 상품 */
	public static final String DC_RATE_TYPE_NORMAL = "normal";
	/** 할인율 유형 - 정액권 상품 */
	public static final String DC_RATE_TYPE_FREEPASS = "freepass";

	/*
	 * SYSTEM ID
	 */
	public static final String DP_SHOP_CLIENT_3_0_SYSTEM_ID = "S01-01002"; // Shop Client 3.0 시스템 ID

	/*
	 * COMIC 단행/연재물
	 */
	public static final String DP_SERIAL_COMIC_META_CLASS_CD = "CT21";
	/*
	 * COMIC 잡지
	 */
	public static final String DP_MAGAZINE_COMIC_META_CLASS_CD = "CT22";
	/*
	 * COMIC 웹툰
	 */
	public static final String DP_WEBTOON_COMIC_META_CLASS_CD = "CT23";
	/*
	 * 트리거_신규상품_영화/방송
	 */
	public static final String DP_NEW_VOD_TRIGGER = "TGR000000002";

    /**
     * Poing.name.마일리지
     */
    public static final String POINT_NM_MILEAGE = "mileage";

    public static final String POINT_TP_MILEAGE_LV1 = "platinum";
    public static final String POINT_TP_MILEAGE_LV2 = "gold";
    public static final String POINT_TP_MILEAGE_LV3 = "silver";
    
	/** 정액제 상품 타입 코드 */
	public static final String FIXRATE_PROD_TYPE_VOD_FIXRATE = "OR004301"; // VOD 정액권
	public static final String FIXRATE_PROD_TYPE_VOD_SERIESPASS = "OR004302"; // VOD 시리즈패스
	public static final String FIXRATE_PROD_TYPE_EBOOKCOMIC_OWN = "OR004303"; // 이북/코믹 전권소장
	public static final String FIXRATE_PROD_TYPE_EBOOKCOMIC_LOAN = "OR004304"; // 이북/코믹 전권대여
	
	/** 할인율/적립율 정책 대상 코드 */
	public static final String POLICY_TARGET_CD_PRODUCT = "DP01160101"; 
	public static final String POLICY_TARGET_CD_CATEGORY = "DP01160102";

    /**
     * DisplayCommonService.getTmembershipDcRateForMenu() 요청시 모든 메뉴
     */
    public static final String REQUEST_TMEMBERSHIP_ALL_MENU = "*";
}
