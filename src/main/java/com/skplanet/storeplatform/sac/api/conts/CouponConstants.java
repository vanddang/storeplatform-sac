/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.api.conts;

import com.skplanet.storeplatform.external.client.shopping.inf.ItxTypeCode.TX_TYPE_CODE;

/**
 * 쇼핑 쿠폰용 상수 Class
 * 
 * Updated on : 2014. 01. 02. Updated by : 김형식, SK 플래닛
 */
public class CouponConstants {

	// 쇼핑쿠폰 전문
	public static final String IF_SYSTEM_COUPON_XML_CP = "1003"; // 쇼핑쿠폰 상품 추가/수정 전문코드

	// 쇼핑쿠폰 PARAMETER_ID
	public static final String IF_SYSTEM_COUPON_PARAM_BD = "1101"; // 브랜드 parameter
	public static final String IF_SYSTEM_COUPON_PARAM_CT = "1102"; // 카달로그 parameter
	public static final String IF_SYSTEM_COUPON_PARAM_CP = "1103"; // 상품 추가/수정 parameter
	public static final String IF_SYSTEM_COUPON_PARAM_ST = "1104"; // 상품 갱신 parameter
	public static final String IF_SYSTEM_COUPON_PARAM_LS = "1105"; // 특가상품 목록 조회 parameter
	public static final String IF_SYSTEM_COUPON_PARAM_DT = "1106"; // 특가상품 상세 조회 parameter
	public static final String IF_SYSTEM_COUPON_PARAM_AT = "1107"; // 쿠폰상태목록 변경 미전시-> 전시

	// 쇼핑쿠폰 RESPONSE_ID
	public static final String IF_SYSTEM_COUPON_RESPONSE_BD = "1201"; // 브랜드 response
	public static final String IF_SYSTEM_COUPON_RESPONSE_CT = "1202"; // 카달로그 response
	public static final String IF_SYSTEM_COUPON_RESPONSE_CP = "1203"; // 상품 추가/수정 response
	public static final String IF_SYSTEM_COUPON_RESPONSE_ST = "1204"; // 상품 갱신 response
	public static final String IF_SYSTEM_COUPON_RESPONSE_LS = "1205"; // 특가상품 목록 조회 response
	public static final String IF_SYSTEM_COUPON_RESPONSE_DT = "1206"; // 특가상품 상세 조회 response
	public static final String IF_SYSTEM_COUPON_RESPONSE_AT = "1207"; // 쿠폰상태목록 변경 미전시-> 전시

	// COUPON ERROR_CODE
	public static final String COUPON_IF_ERROR_CODE_OK = "0000"; // 정상
	public static final String COUPON_IF_ERROR_CODE_MISS = "1001"; // 필수정보 누락 [xxxx]
	public static final String COUPON_IF_ERROR_CODE_TYPE = "1002"; // Parameter 타입 오류
	public static final String COUPON_IF_ERROR_CODE_SERVICE_STOP = "9000"; // Tstore 전시처리 오류(관리자 문의요망)
	public static final String COUPON_IF_ERROR_CODE_QUESTION = "9999"; // 알수없는 오류

	public static final String COUPON_IF_ERROR_CODE_COUPONCODE = "3001"; // 잘못된 쿠폰ID
	public static final String COUPON_IF_ERROR_CODE_POIID = "3002"; // 잘못된 POI ID

	public static final String COUPON_IF_ERROR_CODE_DUP_TXID = "4000"; // 중복 TX_ID [xxxx]
	public static final String COUPON_IF_ERROR_CODE_NOT_CATID = "4001"; // 미존재 카테고리 ID [xxxx]
	public static final String COUPON_IF_ERROR_CODE_NOT_BRID = "4002"; // 미존재 브랜드 ID [xxxx]
	public static final String COUPON_IF_ERROR_CODE_NOT_COUPONID = "4003"; // 미존재 쿠폰 ID [xxxx]
	public static final String COUPON_IF_ERROR_CODE_NOT_ITEMID = "4004"; // 미존재 Item ID [xxxx]
	public static final String COUPON_IF_ERROR_CODE_NOT_BPID = "4005"; // 미등록 BP ID [xxxx]
	public static final String COUPON_IF_ERROR_CODE_DUP_BRID = "4006"; // 중복 브랜드 ID [xxxx]
	public static final String COUPON_IF_ERROR_CODE_DUP_COUPONID = "4007"; // 중복 쿠폰 ID [xxxx]
	public static final String COUPON_IF_ERROR_CODE_DUP_ITEMID = "4008"; // 중복 Item ID [xxxx]
	public static final String COUPON_IF_ERROR_CODE_NOT_CATALOGID = "4009"; // 미등록 카탈로그 ID [xxxx]
	public static final String COUPON_IF_ERROR_CODE_DUP_CATALOGID = "4010"; // 중복 카탈로그 ID [xxxx]
	public static final String COUPON_IF_ERROR_CODE_FILEACESS_ERR = "4011"; // 파일 Access 오류
	public static final String COUPON_IF_ERROR_CODE_IMGTYPE_ERR = "4012"; // 이미지 타입 오류
	public static final String COUPON_IF_ERROR_CODE_IMGCRE_ERR = "4013"; // 이미지 생성 오류
	public static final String COUPON_IF_ERROR_CODE_IMGSIZE_ERR = "4014"; // 이미지 사이즈 오류
	public static final String COUPON_IF_ERROR_CODE_NOT_SPECIAL = "4015"; // 특가상품 없음

	public static final String COUPON_IF_ERROR_CODE_DATA_ERR = "4021"; // Data 오류 [xxxx]
	public static final String COUPON_IF_ERROR_CODE_DB_ERR = "4031"; // DB 장애 오류 [xxxx]

	public static final String COUPON_IF_ERROR_CODE_DB_ETC = "4091"; // 기타 error MSG 직접입력 (쇼핑쿠폰 ERROR 메세지 전송과
																	 // TBL_BP_COUPON_HIST ERROR_MSG 저장내역이 동일)

	// COUPON IF TX_STATUS
	public static final String COUPON_IF_TX_STATUS_SUCCESS = "01"; // 정상
	public static final String COUPON_IF_TX_STATUS_ERROR = "02"; // ERROR

	// 공통코드
	public static final int COUPON_CONTENT_DEFAULT_REVISION = 1; // START REVISION 값
	public static final String COUPON_CONTENT_TP_CHA = "PD002501"; // 채널타입
	public static final String COUPON_CONTENT_TP_EPI = "PD002502"; // 에피소드타입

	// 전시코드
	public static final String COUPON_TOP_CAT_CD = "DP000528"; // 쇼핑 TOP
	public static final String COUPON_SALE_CHNL_CD = "DP006101"; // 판매채널 : T store

	// 상품 유형
	public static final String COUPON_SALE_TYPE_VOUCHER_TICKET = "1"; // 상품권
	public static final String COUPON_SALE_TYPE_COUPON_TICKET = "2"; // 교환권
	public static final String COUPON_SALE_TYPE_SHIPPING_PROD = "3"; // 배송상품

	// 브랜드,카탈로그 Path
	public static final String BRAND_CATALOG_PATH = "shopping"; // 브랜드, 카탈로그 path

	// 브랜드, 카탈로그 REPOSITORY_TYPE
	public static final String TYPE_FOR_REPOSITORY_PATH = "PD011801"; // M/M

	// 브랜드, 카탈로그 REPOSITORY_PCD
	public static final String PCD_FOR_REPOSITORY_PATH = "PD011906"; // 쇼핑쿠폰

	// 브랜드 IMG_CLS
	public static final String BRAND_IMG_260_170 = "DP0001A4";
	public static final String BRAND_IMG_177_177 = "DP0001B9";
	public static final String BRAND_IMG_114_114 = "DP0001B8";
	// public static final String BRAND_IMG_82x82 = "DP000199";
	public static final String BRAND_IMG_29_29 = "DP0001B5";
	public static final String BRAND_IMG_56_56 = "DP0001B6";
	// 2013.06.24 이미지 추가
	public static final String BRAND_IMG_27_27 = "DP0001B7";
	public static final String BRAND_IMG_25_25 = "DP0001B2";

	// 카탈로그 대표이미지 IMG_CLS
	public static final String CATALOG_TOP_IMG_60_60 = "DP000165";
	public static final String CATALOG_TOP_IMG_120_120 = "DP000167";
	public static final String CATALOG_TOP_IMG_130_130 = "DP000191";
	public static final String CATALOG_TOP_IMG_40_40 = "DP000164";
	public static final String CATALOG_TOP_IMG_80_80 = "DP000166";
	public static final String CATALOG_TOP_IMG_110_110 = "DP0001A0";
	public static final String CATALOG_TOP_IMG_180_180 = "DP000168";
	public static final String CATALOG_TOP_IMG_182_182 = "DP0001A3";
	public static final String CATALOG_TOP_IMG_31_30 = "DP0001B3";

	// 카탈로그 상세이미지 IMG_CLS
	public static final String CATALOG_DTL_IMG_684_X = "DP0001B0"; // 가변길이 684x 세로 최대 1170
	public static final String CATALOG_DTL_IMG_684_XY = "DP0001B4"; // 카칼로그 상세이미지 684xY

	// 카탈로그 셀러TAG
	public static final String TAG_TYPE_FOR_COUPON_TAG = "DP004507"; // 셀러TAG 태그타입(쇼핑용)

	/**
	 * 상품 CUD 타입 정의.
	 */
	public static final String CONTENT_CUD_TYPE_CREATE = "C";
	public static final String CONTENT_CUD_TYPE_UPDATE = "U";
	public static final String CONTENT_CUD_TYPE_DELETE = "D";

	/**
	 * 상품 정보 타입 정의.
	 */
	public final static String PAY_CODE_TIME = "PD00292"; // 과금유형(기간정액제)
	public final static String PAY_CODE_GIFTCARD = "DP006301"; // 상품권
	public final static String PAY_CODE_CUPON = "DP006302"; // 교환권
	public final static String PAY_CODE_DELIVER = "DP006303"; // 배송상품
	public final static int OPT_NUMBER_FOR_CHANNEL = 1; // 옵션 전시순서(채널용)
	public final static int OPT_NUMBER_FOR_EPISODE = 2; // 옵션 전시순서(에피소드용)

	public final static String CUPON_SVC_GRP_CD = "DP000206"; // MALL 구분 코드(쇼핑쿠폰)
	public final static String CHNL_TP_CD_CUPON = "DP001117"; // 채널 타입 코드(쇼핑쿠폰)

	/** 연령제한 기본값 - 전체이용가. */
	public static final String AGE_RESTRICTIONS_DEFAULT_COMMON_CODE = "PD004401";
	public static final String AGE_RESTRICTIONS_19RESTRICT = "PD004404";

	/** 상품 전시 상태. **/
	public static final String DP_STATUS_PREPARE_SERVICE = "PD000402"; // 판매대기
	public static final String DP_STATUS_IN_SERVICE = "PD000403"; // 판매중
	public static final String DP_STATUS_STOP_SERVICE = "PD000404"; // 판매중지
	public static final String DP_STATUS_FIN_SERVICE = "PD000407"; // 일시정지
	public static final String DP_STATUS_FORBID_SERVICE = "PD000405"; // 판매금지(다운로드 불가)
	public static final String DP_STATUS_FORBID_SERVICE_DL_AVIL = "PD000409"; // 판매금지(다운로드 허용)

	/** 상품 사용기간 단위. **/
	public static final String USE_PERIOD_UNIT_NONE = "PD00310"; // 무제한 -> 다운로드
	public static final String USE_PERIOD_UNIT_TIME = "PD00311";
	public static final String USE_PERIOD_UNIT_DAY = "PD00312"; // 기간제(일) -> 바로보기
	public static final String USE_PERIOD_UNIT_MONTH = "PD00313";
	public static final String USE_PERIOD_UNIT_YEAR = "PD00314";
	public static final String USE_PERIOD_UNIT_CURRENT_DAY = "PD00315";
	public static final String USE_PERIOD_UNIT_CURRENT_MONTH = "PD00316";
	public static final String USE_PERIOD_UNIT_CURRENT_YEAR = "PD00317";
	public static final String USE_PERIOD_UNIT_MINUTE = "PD00318"; // 분
	public static final String USE_PERIOD_UNIT_SELECT = "PD00319"; // 기간선택

	public static final String CATEGORY_ID_CUPON_SERIES_CONTENT = "CT28"; // 쇼핑쿠폰 부모 카테고리ID
	public static final String TOP_MENU_ID_CUPON_CONTENT = "DP28"; // 상위 메뉴 ID
	public static final String UPDATE_TYPE_CD_DESC = "D";

	public static final String LANG_CD_KO = "ko"; // ko 한글 코드값
	public static final String TENANT_ID = "S01"; // S01 값
	public static final String PROD_RSHP_CD = "DP010802"; // 채널-에피소드 상품 관계

	public static final String ANDROID_STANDARD2 = "android_standard2"; // 디바이스 정보셋팅

	/**
	 * 4091 error code 가 아닌 경우에는 아리와 같은 메세지로 쇼핑쿠폰에 전송함.
	 * 
	 * @param code
	 *            code
	 * @param errorMsg
	 *            errorMsg
	 * @return @String
	 */
	public static String getCouponErrorMsg(String code, String errorMsg) {
		return (code == null) ? "null" : (code.equals(COUPON_IF_ERROR_CODE_OK)) ? "정상" : (code
				.equals(COUPON_IF_ERROR_CODE_DUP_TXID)) ? "중복 TX_ID" : (code.equals(COUPON_IF_ERROR_CODE_MISS)) ? "필수정보 누락" : (code
				.equals(COUPON_IF_ERROR_CODE_TYPE)) ? "Parameter 타입 오류" : (code
				.equals(COUPON_IF_ERROR_CODE_SERVICE_STOP)) ? "Tstore 전시처리 오류(관리자 문의요망)" : (code
				.equals(COUPON_IF_ERROR_CODE_QUESTION)) ? "알수없는 오류" : (code.equals(COUPON_IF_ERROR_CODE_COUPONCODE)) ? "잘못된 쿠폰ID" : (code
				.equals(COUPON_IF_ERROR_CODE_POIID)) ? "잘못된 POI ID" : (code.equals(COUPON_IF_ERROR_CODE_NOT_CATID)) ? "미존재 카테고리 ID" : (code
				.equals(COUPON_IF_ERROR_CODE_NOT_BRID)) ? "미존재 브랜드 ID" : (code
				.equals(COUPON_IF_ERROR_CODE_NOT_COUPONID)) ? "미존재 쿠폰 ID" : (code
				.equals(COUPON_IF_ERROR_CODE_NOT_ITEMID)) ? "미존재 Item ID" : (code.equals(COUPON_IF_ERROR_CODE_NOT_BPID)) ? "미등록 BP ID" : (code
				.equals(COUPON_IF_ERROR_CODE_DUP_BRID)) ? "중복 브랜드 ID" : (code.equals(COUPON_IF_ERROR_CODE_DUP_COUPONID)) ? "중복 쿠폰 ID" : (code
				.equals(COUPON_IF_ERROR_CODE_DUP_ITEMID)) ? "중복 Item ID" : (code
				.equals(COUPON_IF_ERROR_CODE_NOT_CATALOGID)) ? "미등록 카탈로그 ID" : (code
				.equals(COUPON_IF_ERROR_CODE_DUP_CATALOGID)) ? "중복 카탈로그 ID" : (code
				.equals(COUPON_IF_ERROR_CODE_FILEACESS_ERR)) ? "파일 Access 오류" : (code
				.equals(COUPON_IF_ERROR_CODE_IMGTYPE_ERR)) ? "이미지 타입 오류" : (code
				.equals(COUPON_IF_ERROR_CODE_IMGCRE_ERR)) ? "이미지 생성 오류" : (code
				.equals(COUPON_IF_ERROR_CODE_IMGSIZE_ERR)) ? "이미지 사이즈 오류" : (code.equals(COUPON_IF_ERROR_CODE_DATA_ERR)) ? "Data 오류" : (code
				.equals(COUPON_IF_ERROR_CODE_DB_ERR)) ? "DB 장애 오류" : (code.equals(COUPON_IF_ERROR_CODE_NOT_SPECIAL)) ? "특가상품 없음" : (code
				.equals(COUPON_IF_ERROR_CODE_DB_ETC)) ? errorMsg : code;
	}

	/**
	 * getCouponParamId 체크.
	 * 
	 * @param txType
	 *            txType
	 * @return String
	 */
	public static String getCouponParamId(String txType) {
		if (txType == null)
			return null;

		switch (TX_TYPE_CODE.get(txType)) {

		case BD:
			return CouponConstants.IF_SYSTEM_COUPON_PARAM_BD;
		case CT:
			return CouponConstants.IF_SYSTEM_COUPON_PARAM_CT;
		case CP:
			return CouponConstants.IF_SYSTEM_COUPON_PARAM_CP;
		case ST:
			return CouponConstants.IF_SYSTEM_COUPON_PARAM_ST;
		case LS:
			return CouponConstants.IF_SYSTEM_COUPON_PARAM_LS;
		case DT:
			return CouponConstants.IF_SYSTEM_COUPON_PARAM_DT;
		case AT:
			return CouponConstants.IF_SYSTEM_COUPON_PARAM_AT;
		default:
			return null;

		}

	}

}
