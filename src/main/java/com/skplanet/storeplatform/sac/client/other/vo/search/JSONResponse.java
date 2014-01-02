/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.other.vo.search;

import java.util.List;

public class JSONResponse {

	private Response response; // 통합검색 응답

	public Response getResponse() {
		return this.response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	/**
	 * JSON response 정의 Inner 클래스
	 */
	public class Response {

		private List<Group> groups; // 통합검색 Group
		private Header header; // 통합검색 Header
		private List<Doc> docs; // 카테고리 검색
		private List<String> categories; // 카테고리별 건수

		public List<String> getCategories() {
			return this.categories;
		}

		public void setCategories(List<String> categories) {
			this.categories = categories;
		}

		public List<Group> getGroups() {
			return this.groups;
		}

		public void setGroups(List<Group> groups) {
			this.groups = groups;
		}

		public Header getHeader() {
			return this.header;
		}

		public void setHeader(Header header) {
			this.header = header;
		}

		public List<Doc> getDocs() {
			return this.docs;
		}

		public void setDocs(List<Doc> docs) {
			this.docs = docs;
		}

	}

	/**
	 * header 정의 Inner 클래스
	 */
	public class Header {

		private int groupCount; // 검색된 카테고리 수
		private int totalCount; // 검색된 총 결과 수
		private String relationKeywords; // 연관 검색어
		private String revisedQuery; // 교정된 검색어
		private String originalQuery; // 사용자가 입력한 검색어
		private String query; // 실제 검색에 사용한 검색어

		public int getGroupCount() {
			return this.groupCount;
		}

		public void setGroupCount(int groupCount) {
			this.groupCount = groupCount;
		}

		public int getTotalCount() {
			return this.totalCount;
		}

		public void setTotalCount(int totalCount) {
			this.totalCount = totalCount;
		}

		public String getRelationKeywords() {
			return this.relationKeywords;
		}

		public void setRelationKeywords(String relationKeywords) {
			this.relationKeywords = relationKeywords;
		}

		public String getRevisedQuery() {
			return this.revisedQuery;
		}

		public void setRevisedQuery(String revisedQuery) {
			this.revisedQuery = revisedQuery;
		}

		public String getOriginalQuery() {
			return this.originalQuery;
		}

		public void setOriginalQuery(String originalQuery) {
			this.originalQuery = originalQuery;
		}

		public String getQuery() {
			return this.query;
		}

		public void setQuery(String query) {
			this.query = query;
		}

	}

	/**
	 * JSON group 정의 Inner 클래스
	 */
	public class Group {

		private String groupValue; // 카테고리 코드값
		private DocList doclist; // 검색 목록

		public String getGroupValue() {
			return this.groupValue;
		}

		public void setGroupValue(String groupValue) {
			this.groupValue = groupValue;
		}

		public DocList getDoclist() {
			return this.doclist;
		}

		public void setDoclist(DocList doclist) {
			this.doclist = doclist;
		}

	}

	/**
	 * JSON group doclist 정의 Inner 클래스
	 */
	public class DocList {

		private int start; // 문서 시작 위치
		private List<Doc> docs; // 검색목록
		private int numFound; // 검색 수

		public int getStart() {
			return this.start;
		}

		public void setStart(int start) {
			this.start = start;
		}

		public List<Doc> getDocs() {
			return this.docs;
		}

		public void setDocs(List<Doc> docs) {
			this.docs = docs;
		}

		public int getNumFound() {
			return this.numFound;
		}

		public void setNumFound(int numFound) {
			this.numFound = numFound;
		}
	}

	/**
	 * JSON Doc 정의 Inner 클래스
	 */
	public class Doc {

		private String title; // 제목
		private String author; // 작성자
		private String description; // 설명
		private String price; // 가격
		private String date; // 등록일시
		private String adult; // 성인여부
		private String isGoogleContents; // 구글컨텐츠구분
		private String googleOutlink; // 구글컨텐츠아웃링크
		private String meta1; // 상품구분
		private String meta2; // 아티스트(ARTIST_NM)
		private String meta3; // 상품아이디
		private String meta4; // 이미지경로
		private String meta5; // 서비스 그룹코드
		private String meta6; // 전시 카테고리 코드
		private String meta7; // 상품 등급 코드
		private String meta8; // 플랫폼 구분 코드
		private String meta9; // 분류 카테고리
		private String meta10; // 카테고리 full name
		private String meta11; // 평점/조회수
		private String meta12; // 다운로드수
		private String meta13; // 상품가능 휴대폰 정보
		private String meta14; // 공지/FAQ 구분
		private String meta15; // 이벤트 정보(경품,기간)
		private String meta16; // 대여상품갯수
		private String meta17; // 상품분류
		private String meta18; // WAP 이미지1
		private String meta19; // WAP 이미지2
		private String meta20; // WAP 이미지3
		private String meta21; // WAP 타이틀
		private String meta22; // 개발사명
		private String meta23; // 소장상품갯수
		private String meta24; // 컨텐츠 아이디
		private String meta25; // 채널/시리즈 여부
		private String meta26; // DRM 여부
		private String meta27; // META 구분코드(Comic)
		private String meta28; // Full text
		private String meta31; // Attribute tag
		private String meta32; // Category tag
		private String meta33; // Keyword tag
		private String meta34; // Seller tag
		private String meta35; // Description tag
		private String meta36; // 누가
		private String meta37; // 어디에서
		private String meta38; // 무엇을
		private String meta39; // 전시 카테고리 코드
		private String meta40; // TV App 구분코드
		private String meta42; // TV 방송사 구분코드
		private String meta43; // 포스터 이미지
		private String meta44; // WAP 이미지5
		private String meta45; // WAP 이미지6
		private String meta46; // WAP 이미지7
		private String meta47; // HDMI 지원여부
		private String meta48; // 할인 전 가격
		private String meta49; // 멀티미디어 직사각형 이미지
		private String meta50; // 코믹 그림작가
		private String meta51; // 이북 잡지호수
		private String meta52; // VOD 기획사
		private String meta53; // VOD 장르
		private String meta54; // eBook 출판사(CHNL_COMP_NM)
		private String meta55; // WEB이미지(120*120)
		private String meta56; // WEB이미지(120*171)
		private String meta57; // WEB이미지(90*128)
		private String meta58; // WEB이미지(120*90)
		private String meta59; // WEB이미지(80*80)
		private String meta60; // WEB이미지(80*114)
		private String meta61; // WEB이미지(80*60)
		private String meta62; // 평점 참여자수
		private String meta63; // 업데이트일자(SALE_DT)
		private String meta64; // 영화-배우
		private String meta65; // 어플패키지명
		private String meta66; // WAP30 추가 이미지 타입
		private String meta67; // WAP30 추가 이미지 타입
		private String meta68; // WAP30 추가 이미지 타입
		private String meta69; // WAP30 추가 이미지 타입
		private String meta70; // WAP30 추가 이미지 타입
		private String meta71; // WAP30 추가 이미지 타입
		private String meta72; // VOD/뮤직 바로듣기 가능여부
		private String meta73; // 미리보기 URL
		private String meta74; // 미리듣기 음원ID
		private String meta75; // 웹툰 이미지URL
		private String meta76; // BTV 여부
		private String meta77; // 다운로드/바로보기 여부
		private String meta78; // 쇼핑-할인율
		private String meta79; // 쇼핑-상품타입
		private String meta80; // 1depth 카테고리명
		private String meta81; // 2depth 카테고리명
		private String meta82; // 3depth 카테고리명
		private String meta83; // 앨범명(ARTIST_NM3)
		private String meta84; // 판매업체명

		public String getTitle() {
			return this.title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getAuthor() {
			return this.author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public String getDescription() {
			return this.description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getPrice() {
			return this.price;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public String getDate() {
			return this.date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getAdult() {
			return this.adult;
		}

		public void setAdult(String adult) {
			this.adult = adult;
		}

		public String getIsGoogleContents() {
			return this.isGoogleContents;
		}

		public void setIsGoogleContents(String isGoogleContents) {
			this.isGoogleContents = isGoogleContents;
		}

		public String getGoogleOutlink() {
			return this.googleOutlink;
		}

		public void setGoogleOutlink(String googleOutlink) {
			this.googleOutlink = googleOutlink;
		}

		public String getMeta1() {
			return this.meta1;
		}

		public void setMeta1(String meta1) {
			this.meta1 = meta1;
		}

		public String getMeta2() {
			return this.meta2;
		}

		public void setMeta2(String meta2) {
			this.meta2 = meta2;
		}

		public String getMeta3() {
			return this.meta3;
		}

		public void setMeta3(String meta3) {
			this.meta3 = meta3;
		}

		public String getMeta4() {
			return this.meta4;
		}

		public void setMeta4(String meta4) {
			this.meta4 = meta4;
		}

		public String getMeta5() {
			return this.meta5;
		}

		public void setMeta5(String meta5) {
			this.meta5 = meta5;
		}

		public String getMeta6() {
			return this.meta6;
		}

		public void setMeta6(String meta6) {
			this.meta6 = meta6;
		}

		public String getMeta7() {
			return this.meta7;
		}

		public void setMeta7(String meta7) {
			this.meta7 = meta7;
		}

		public String getMeta8() {
			return this.meta8;
		}

		public void setMeta8(String meta8) {
			this.meta8 = meta8;
		}

		public String getMeta9() {
			return this.meta9;
		}

		public void setMeta9(String meta9) {
			this.meta9 = meta9;
		}

		public String getMeta10() {
			return this.meta10;
		}

		public void setMeta10(String meta10) {
			this.meta10 = meta10;
		}

		public String getMeta11() {
			return this.meta11;
		}

		public void setMeta11(String meta11) {
			this.meta11 = meta11;
		}

		public String getMeta12() {
			return this.meta12;
		}

		public void setMeta12(String meta12) {
			this.meta12 = meta12;
		}

		public String getMeta13() {
			return this.meta13;
		}

		public void setMeta13(String meta13) {
			this.meta13 = meta13;
		}

		public String getMeta14() {
			return this.meta14;
		}

		public void setMeta14(String meta14) {
			this.meta14 = meta14;
		}

		public String getMeta15() {
			return this.meta15;
		}

		public void setMeta15(String meta15) {
			this.meta15 = meta15;
		}

		public String getMeta16() {
			return this.meta16;
		}

		public void setMeta16(String meta16) {
			this.meta16 = meta16;
		}

		public String getMeta17() {
			return this.meta17;
		}

		public void setMeta17(String meta17) {
			this.meta17 = meta17;
		}

		public String getMeta18() {
			return this.meta18;
		}

		public void setMeta18(String meta18) {
			this.meta18 = meta18;
		}

		public String getMeta19() {
			return this.meta19;
		}

		public void setMeta19(String meta19) {
			this.meta19 = meta19;
		}

		public String getMeta20() {
			return this.meta20;
		}

		public void setMeta20(String meta20) {
			this.meta20 = meta20;
		}

		public String getMeta21() {
			return this.meta21;
		}

		public void setMeta21(String meta21) {
			this.meta21 = meta21;
		}

		public String getMeta22() {
			return this.meta22;
		}

		public void setMeta22(String meta22) {
			this.meta22 = meta22;
		}

		public String getMeta23() {
			return this.meta23;
		}

		public void setMeta23(String meta23) {
			this.meta23 = meta23;
		}

		public String getMeta24() {
			return this.meta24;
		}

		public void setMeta24(String meta24) {
			this.meta24 = meta24;
		}

		public String getMeta25() {
			return this.meta25;
		}

		public void setMeta25(String meta25) {
			this.meta25 = meta25;
		}

		public String getMeta26() {
			return this.meta26;
		}

		public void setMeta26(String meta26) {
			this.meta26 = meta26;
		}

		public String getMeta27() {
			return this.meta27;
		}

		public void setMeta27(String meta27) {
			this.meta27 = meta27;
		}

		public String getMeta28() {
			return this.meta28;
		}

		public void setMeta28(String meta28) {
			this.meta28 = meta28;
		}

		public String getMeta31() {
			return this.meta31;
		}

		public void setMeta31(String meta31) {
			this.meta31 = meta31;
		}

		public String getMeta32() {
			return this.meta32;
		}

		public void setMeta32(String meta32) {
			this.meta32 = meta32;
		}

		public String getMeta33() {
			return this.meta33;
		}

		public void setMeta33(String meta33) {
			this.meta33 = meta33;
		}

		public String getMeta34() {
			return this.meta34;
		}

		public void setMeta34(String meta34) {
			this.meta34 = meta34;
		}

		public String getMeta35() {
			return this.meta35;
		}

		public void setMeta35(String meta35) {
			this.meta35 = meta35;
		}

		public String getMeta36() {
			return this.meta36;
		}

		public void setMeta36(String meta36) {
			this.meta36 = meta36;
		}

		public String getMeta37() {
			return this.meta37;
		}

		public void setMeta37(String meta37) {
			this.meta37 = meta37;
		}

		public String getMeta38() {
			return this.meta38;
		}

		public void setMeta38(String meta38) {
			this.meta38 = meta38;
		}

		public String getMeta39() {
			return this.meta39;
		}

		public void setMeta39(String meta39) {
			this.meta39 = meta39;
		}

		public String getMeta40() {
			return this.meta40;
		}

		public void setMeta40(String meta40) {
			this.meta40 = meta40;
		}

		public String getMeta42() {
			return this.meta42;
		}

		public void setMeta42(String meta42) {
			this.meta42 = meta42;
		}

		public String getMeta43() {
			return this.meta43;
		}

		public void setMeta43(String meta43) {
			this.meta43 = meta43;
		}

		public String getMeta44() {
			return this.meta44;
		}

		public void setMeta44(String meta44) {
			this.meta44 = meta44;
		}

		public String getMeta45() {
			return this.meta45;
		}

		public void setMeta45(String meta45) {
			this.meta45 = meta45;
		}

		public String getMeta46() {
			return this.meta46;
		}

		public void setMeta46(String meta46) {
			this.meta46 = meta46;
		}

		public String getMeta47() {
			return this.meta47;
		}

		public void setMeta47(String meta47) {
			this.meta47 = meta47;
		}

		public String getMeta48() {
			return this.meta48;
		}

		public void setMeta48(String meta48) {
			this.meta48 = meta48;
		}

		public String getMeta49() {
			return this.meta49;
		}

		public void setMeta49(String meta49) {
			this.meta49 = meta49;
		}

		public String getMeta50() {
			return this.meta50;
		}

		public void setMeta50(String meta50) {
			this.meta50 = meta50;
		}

		public String getMeta51() {
			return this.meta51;
		}

		public void setMeta51(String meta51) {
			this.meta51 = meta51;
		}

		public String getMeta52() {
			return this.meta52;
		}

		public void setMeta52(String meta52) {
			this.meta52 = meta52;
		}

		public String getMeta53() {
			return this.meta53;
		}

		public void setMeta53(String meta53) {
			this.meta53 = meta53;
		}

		public String getMeta54() {
			return this.meta54;
		}

		public void setMeta54(String meta54) {
			this.meta54 = meta54;
		}

		public String getMeta55() {
			return this.meta55;
		}

		public void setMeta55(String meta55) {
			this.meta55 = meta55;
		}

		public String getMeta56() {
			return this.meta56;
		}

		public void setMeta56(String meta56) {
			this.meta56 = meta56;
		}

		public String getMeta57() {
			return this.meta57;
		}

		public void setMeta57(String meta57) {
			this.meta57 = meta57;
		}

		public String getMeta58() {
			return this.meta58;
		}

		public void setMeta58(String meta58) {
			this.meta58 = meta58;
		}

		public String getMeta59() {
			return this.meta59;
		}

		public void setMeta59(String meta59) {
			this.meta59 = meta59;
		}

		public String getMeta60() {
			return this.meta60;
		}

		public void setMeta60(String meta60) {
			this.meta60 = meta60;
		}

		public String getMeta61() {
			return this.meta61;
		}

		public void setMeta61(String meta61) {
			this.meta61 = meta61;
		}

		public String getMeta62() {
			return this.meta62;
		}

		public void setMeta62(String meta62) {
			this.meta62 = meta62;
		}

		public String getMeta63() {
			return this.meta63;
		}

		public void setMeta63(String meta63) {
			this.meta63 = meta63;
		}

		public String getMeta64() {
			return this.meta64;
		}

		public void setMeta64(String meta64) {
			this.meta64 = meta64;
		}

		public String getMeta65() {
			return this.meta65;
		}

		public void setMeta65(String meta65) {
			this.meta65 = meta65;
		}

		public String getMeta66() {
			return this.meta66;
		}

		public void setMeta66(String meta66) {
			this.meta66 = meta66;
		}

		public String getMeta67() {
			return this.meta67;
		}

		public void setMeta67(String meta67) {
			this.meta67 = meta67;
		}

		public String getMeta68() {
			return this.meta68;
		}

		public void setMeta68(String meta68) {
			this.meta68 = meta68;
		}

		public String getMeta69() {
			return this.meta69;
		}

		public void setMeta69(String meta69) {
			this.meta69 = meta69;
		}

		public String getMeta70() {
			return this.meta70;
		}

		public void setMeta70(String meta70) {
			this.meta70 = meta70;
		}

		public String getMeta71() {
			return this.meta71;
		}

		public void setMeta71(String meta71) {
			this.meta71 = meta71;
		}

		public String getMeta72() {
			return this.meta72;
		}

		public void setMeta72(String meta72) {
			this.meta72 = meta72;
		}

		public String getMeta73() {
			return this.meta73;
		}

		public void setMeta73(String meta73) {
			this.meta73 = meta73;
		}

		public String getMeta74() {
			return this.meta74;
		}

		public void setMeta74(String meta74) {
			this.meta74 = meta74;
		}

		public String getMeta75() {
			return this.meta75;
		}

		public void setMeta75(String meta75) {
			this.meta75 = meta75;
		}

		public String getMeta76() {
			return this.meta76;
		}

		public void setMeta76(String meta76) {
			this.meta76 = meta76;
		}

		public String getMeta77() {
			return this.meta77;
		}

		public void setMeta77(String meta77) {
			this.meta77 = meta77;
		}

		public String getMeta78() {
			return this.meta78;
		}

		public void setMeta78(String meta78) {
			this.meta78 = meta78;
		}

		public String getMeta79() {
			return this.meta79;
		}

		public void setMeta79(String meta79) {
			this.meta79 = meta79;
		}

		public String getMeta80() {
			return this.meta80;
		}

		public void setMeta80(String meta80) {
			this.meta80 = meta80;
		}

		public String getMeta81() {
			return this.meta81;
		}

		public void setMeta81(String meta81) {
			this.meta81 = meta81;
		}

		public String getMeta82() {
			return this.meta82;
		}

		public void setMeta82(String meta82) {
			this.meta82 = meta82;
		}

		public String getMeta83() {
			return this.meta83;
		}

		public void setMeta83(String meta83) {
			this.meta83 = meta83;
		}

		public String getMeta84() {
			return this.meta84;
		}

		public void setMeta84(String meta84) {
			this.meta84 = meta84;
		}

	}

}
