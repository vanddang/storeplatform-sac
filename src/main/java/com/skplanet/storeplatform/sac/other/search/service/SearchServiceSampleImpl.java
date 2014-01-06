/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.other.search.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.skplanet.storeplatform.external.client.search.vo.Doc;
import com.skplanet.storeplatform.external.client.search.vo.EcSearchReq;
import com.skplanet.storeplatform.external.client.search.vo.EcSearchRes;
import com.skplanet.storeplatform.external.client.search.vo.Group;
import com.skplanet.storeplatform.external.client.search.vo.Header;
import com.skplanet.storeplatform.framework.core.util.DateUtil;
import com.skplanet.storeplatform.sac.client.common.vo.CmDevice;
import com.skplanet.storeplatform.sac.client.other.vo.search.Category;
import com.skplanet.storeplatform.sac.client.other.vo.search.Search;
import com.skplanet.storeplatform.sac.client.other.vo.search.SearchReq;
import com.skplanet.storeplatform.sac.client.other.vo.search.SearchRes;
import com.skplanet.storeplatform.sac.common.service.CategoryService;
import com.skplanet.storeplatform.sac.common.service.CommonService;
import com.skplanet.storeplatform.sac.other.search.repository.SearchRepository;

/**
 * 
 * 검색 서비스 Class 구현체
 * 
 * Updated on : 2014. 1. 6. Updated by : 김현일, 인크로스
 */
@Profile(value = { "dev", "local" })
@Service
public class SearchServiceSampleImpl implements SearchService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchServiceImpl.class);

	@Autowired
	private SearchRepository searchRepository;

	@Autowired
	private CommonService commonService;

	@Autowired
	private CategoryService categoryService;

	@SuppressWarnings("serial")
	private final Map<String, String> orderMap = new HashMap<String, String>() {
		{
			this.put("recent", "L");
			this.put("score", "G");
			this.put("accuracy", "R");
			this.put("popular", "D");
		}
	};

	@Override
	public SearchRes search(SearchReq searchReq) {

		LOGGER.debug("input : {}", searchReq);

		// TB_CM_DEVICE 정보조회
		// UA_CD 및 파라미터 조작시 필요
		CmDevice device = this.commonService.getCmDevice(searchReq.getDeviceModelNo());

		if (device == null) {
			LOGGER.warn("device is null");
			throw new RuntimeException("device is null");
		}
		EcSearchReq ecSearchReq = new EcSearchReq();

		// 카테고리 셋팅
		this.setCategory(searchReq, ecSearchReq);
		// adultYN이 비어있으면 실명인증 안함.
		this.setAdult(searchReq, ecSearchReq);
		// order 지원 범위 확인
		this.setOrder(searchReq, ecSearchReq);
		// 검색기간
		this.setDateFromTo(searchReq, ecSearchReq);
		// meta 17 / meta26 / meta47 가공
		this.setMeta(device, searchReq, ecSearchReq);
		// 키워드
		ecSearchReq.setQ(searchReq.getKeywd());
		// default 처리 (N)
		ecSearchReq.setRel(searchReq.getRelSearchYN());
		// 암호화하기
		ecSearchReq.setUvKey(searchReq.getDeviceId());
		// 구글링크여부
		ecSearchReq.setGgOutlink(searchReq.getGoogleLinkYN());
		// 스펠링체크여부
		ecSearchReq.setSc(searchReq.getSpellCorrectYN());
		// E/C 연동 후 String 형태의 JSON을 전달 받음.

		LOGGER.debug("exSearchReq : {}", ecSearchReq);

		EcSearchRes ecSearchRes = this.searchRepository.search(ecSearchReq);

		LOGGER.debug("exSearchRes : {}", ecSearchRes);
		// 데이터 가공
		return this.getResultData(searchReq, ecSearchReq, ecSearchRes);
	}

	private void setCategory(SearchReq searchReq, EcSearchReq exSearchReq) {
		if (StringUtils.isBlank(searchReq.getCategory()) || StringUtils.equals(searchReq.getCategory(), "ALL"))
			return;
		exSearchReq.setCategory(StringUtils.trimToEmpty(searchReq.getCategory()));
		exSearchReq.setOffset(searchReq.getOffset());
		exSearchReq.setCount(searchReq.getCount());
	}

	private void setDateFromTo(SearchReq searchReq, EcSearchReq exSearchReq) {
		String dtType = searchReq.getDtType();
		if (StringUtils.isBlank(dtType))
			return;
		Date to = new Date();
		Date from = null;
		if (dtType.equals("W")) {
			from = DateUtil.addWeeks(to, -1);
		} else if (dtType.equals("M")) {
			from = DateUtil.addMonths(to, -1);
		} else if (dtType.equals("Y")) {
			from = DateUtil.addYears(to, -1);
		}
		exSearchReq.setDateFrom(DateFormatUtils.format(from, "yyyyMMdd"));
		exSearchReq.setDateTo(DateFormatUtils.format(to, "yyyyMMdd"));
	}

	private void setAdult(SearchReq searchReq, EcSearchReq exSearchReq) {
		if (StringUtils.isBlank(searchReq.getAdultYN()))
			exSearchReq.setAdult("A");
		else
			exSearchReq.setAdult(searchReq.getAdultYN());
	}

	public void setOrder(SearchReq searchReq, EcSearchReq exSearchReq) {
		if (!this.orderMap.containsKey(searchReq.getOrderedBy())) {
			// LOGGER.warn("not support order");
			// throw new RuntimeException("not support order");
			// default 최신순
			exSearchReq.setOrder(this.orderMap.get("recent"));
		} else {
			exSearchReq.setOrder(this.orderMap.get(searchReq.getOrderedBy()));
		}
	}

	private void setMeta(CmDevice device, SearchReq searchReq, EcSearchReq exSearchReq) {
		// TB_CM_DEVICE의 UA_CD를 조회한다.
		exSearchReq.setMeta13(device.getUaCd());
		List<String> listMeta17 = new ArrayList<String>();
		String category = StringUtils.trimToEmpty(searchReq.getCategory());
		if (StringUtils.equals(category, "DP000503,DP000504,DP000508") || StringUtils.equals(category, "")) {
			listMeta17.add("I");
		}
		if (!StringUtils.equals(category, "DP000503,DP000504,DP000508")) {
			listMeta17.add("B");
			// HDV_SPRT_YN
			if (StringUtils.equals(device.getSdVideoSprtYn(), "Y")) {
				listMeta17.add("V");
			}
			// HDV_SPRT_MM_YN
			if (StringUtils.equals(device.getHdvSprtYn(), "Y")) {
				listMeta17.add("H");
			}
			// EBOOK_SPRT_YN
			if (StringUtils.equals(device.getEbookSprtYn(), "Y")) {
				listMeta17.add("E");
			}
			// MAGAZINE_SPRT_YN
			if (StringUtils.equals(device.getMagazineSprtYn(), "Y")) {
				listMeta17.add("J");
			}
			// COMIC_SPRT_YN
			if (StringUtils.equals(device.getComicSprtYn(), "Y")) {
				listMeta17.add("C");
			}
			// MUSIC_SPRT_YN
			if (StringUtils.equals(device.getMusicSprtYn(), "Y")) {
				listMeta17.add("A");
			}
			// WEBTOON_SPRT_YN
			if (StringUtils.equals(device.getWebtoonSprtYn(), "Y")) {
				listMeta17.add("W");
			}
			// SS_SPRT_YN
			if (StringUtils.equals(device.getSclShpgSprtYn(), "Y")
					|| StringUtils.equals(device.getSclShpgSprtYn(), "S")) {
				if (StringUtils.equals(searchReq.getNewShoppingYN(), "Y")) {
					listMeta17.add("D");
				} else {
					listMeta17.add("S");
				}
			}
			exSearchReq.setMeta17(StringUtils.join(listMeta17, ","));
		}

		// DRM 적용 여부 ( Y:DRM 적용/N:DRM 미적용/A:전체 )
		// AS-IS는 무조건 셋팅하고 있음(중요)
		// if (StringUtils.equals(device.getVideoDrmSprtYn(), "Y")) {
		exSearchReq.setMeta26("N");
		// }

		if (StringUtils.equals(searchReq.getRelSearchYN(), "Y")) {
			exSearchReq.setRel("Y");
		}
		if (StringUtils.equals(device.getHdmiSprtYn(), "Y") && StringUtils.equals(device.getHdcpSprtYn(), "Y")) {
			exSearchReq.setMeta47("Y");
		}
	}

	private SearchRes getResultData(SearchReq searchReq, EcSearchReq ecSearchReq, EcSearchRes exSearchRes) {

		if (exSearchRes == null || exSearchRes.getResponse() == null || exSearchRes.getResponse().getHeader() == null
				|| exSearchRes.getResponse().getHeader().getTotalCount() <= 0) {
			// 추천상품리턴..
			return this.getResultRecomData(searchReq);
		}
		// 무조건 데이터가 있는 경우임.
		Header header = exSearchRes.getResponse().getHeader();
		List<Group> groups = exSearchRes.getResponse().getGroups();
		// List<Doc> docs = exSearchRes.getResponse().getDocs();
		// List<String> categories = exSearchRes.getResponse().getCategories();
		SearchRes searchRes = new SearchRes();
		List<Category> categoryList = new ArrayList<Category>();
		if (StringUtils.isEmpty(ecSearchReq.getCategory())) {
			if (header.getGroupCount() > 0) {
				List<Doc> docList = new ArrayList<Doc>();
				for (Group group : groups) {
					if (group.getDoclist().getNumFound() > 0) {
						Category category = new Category();
						category.setCategoryCd(group.getGroupValue());
						category.setCategoryCnt(group.getDoclist().getNumFound());
						categoryList.add(category);
						docList.addAll(group.getDoclist().getDocs());
					}
				}
				searchRes.setCategoryList(categoryList);
				searchRes.setProductList(this.getProductList(searchReq, docList));
			}
		} else {
			if (header.getTotalCount() > 0) {
				Category category = new Category();
				category.setCategoryCd(ecSearchReq.getCategory());
				category.setCategoryCnt(header.getTotalCount());
				categoryList.add(category);
				searchRes.setCategoryList(categoryList);
				searchRes.setProductList(this.getProductList(searchReq, exSearchRes.getResponse().getDocs()));
			}
		}
		// 연관검색어
		searchRes.setRelKeywd(header.getRelationKeywords());
		// 자동변환검색어
		searchRes.setAutoTransKeywd(header.getQuery());
		// 원래입력한검색어
		searchRes.setOrgKeywd(header.getOriginalQuery());

		return searchRes;
	}

	private SearchRes getResultRecomData(SearchReq searchReq) {
		return null;
	}

	private List<Search> getProductList(SearchReq searchReq, List<Doc> docList) {
		List<Search> productList = new ArrayList<Search>();
		String topCatCd = "";
		String[] sellerTags = null;
		String[] keywdTags = null;
		StringBuffer bTag = new StringBuffer();
		for (Doc doc : docList) {
			Search search = new Search();
			// 상품ID
			search.setProdId(doc.getMeta3());
			// 상품명
			search.setProdNm(doc.getTitle());
			// 상품설명
			search.setProdDesc(doc.getDescription());
			// Artist 명
			if (!StringUtils.isEmpty(doc.getMeta2()))
				search.setArtistNm(doc.getMeta2());

			topCatCd = doc.getMeta9();

			// new 쇼핑쿠폰
			if (StringUtils.equals(topCatCd, "DP000528")) {
				// 할인율
				search.setDcRate(doc.getMeta78());
				// 할인전 가격
				search.setProdNetAmt(doc.getMeta48());
				// 상품권 DP006301
				// 교환권 DP006302
				// 배송상품 DP006303
				if (StringUtils.equals(doc.getMeta48(), "DP006301")) {
					search.setProdType("giftcard");
				} else if (StringUtils.equals(doc.getMeta48(), "DP006302")) {
					search.setProdType("exchange");
				} else if (StringUtils.equals(doc.getMeta48(), "DP006303")) {
					search.setProdType("delivery");
				}
			}

			// 대여가격기존노출
			if (StringUtils.equals(topCatCd, "DP000513") || StringUtils.equals(topCatCd, "DP000514")
					|| StringUtils.equals(topCatCd, "DP000517") || StringUtils.equals(topCatCd, "DP000518")) {
				// 소장상품개수
				String meta23 = doc.getMeta23();
				// 대여상품개수
				String meta16 = doc.getMeta16();
				if (StringUtils.isEmpty(meta23) && StringUtils.isEmpty(meta16)) {
					search.setProdAmt(doc.getPrice());
				} else {
					if (!StringUtils.equals(StringUtils.defaultIfEmpty(meta23, "0"), "0")
							&& StringUtils.equals(StringUtils.defaultIfEmpty(meta16, "0"), "0")) {
						// 소장상품만존재할경우
						search.setProdAmt(doc.getPrice());
					} else {
						search.setProdAmt(doc.getMeta15());
					}
				}
			} else {
				search.setProdAmt(doc.getPrice());
			}
			// contributor 정보가공
			// 방송
			if (StringUtils.equals(topCatCd, "DP000518")) {
				search.setVodChnlCompNm(StringUtils.defaultIfEmpty(doc.getMeta52(), doc.getMeta53()));
				search.setVodSaleDt(doc.getMeta63());
				// 이북, 만화
			} else if (StringUtils.equals(topCatCd, "DP000513") || StringUtils.equals(topCatCd, "DP000514")) {
				search.setBookArtistNm(doc.getMeta2());
				search.setBookChnlCompNm(doc.getMeta54());
				search.setBookSaleDt(doc.getMeta63());
				// 음악
			} else if (StringUtils.equals(topCatCd, "DP000516")) {
				search.setMusicArtistNm(doc.getMeta2());
				search.setMusicAlbumNm(doc.getMeta83());
				// 웹툰, 소설
			} else if (StringUtils.equals(topCatCd, "DP000526")) {
				search.setBookArtistNm(doc.getMeta2());
				// 쇼핑, 브랜드
			} else if (StringUtils.equals(topCatCd, "DP000528")) {
				search.setBrandNm(doc.getAuthor());
			}
			// DRM 여부
			if (StringUtils.equals(doc.getMeta26(), "Y")) {
				search.setDrm("drm");
			}
			// 웹툰일경우 - Mobile WEB Redirect URL 가공
			if (StringUtils.equals(topCatCd, "DP000526")) {
				search.setWebtoonUrl(this.getWebtoonUrl(searchReq, doc));
			}
			// 0 구글플레이 상품, 1 : 티스토어 상품
			String googleYn = doc.getIsGoogleContents();
			if (StringUtils.equals(googleYn, "0")) {
				search.setFileLoc(doc.getMeta4());
				// 판매자정보
				search.setSellerNm(doc.getMeta22());
				// 구글아웃링크
				search.setOutLinkUrl(doc.getGoogleOutlink());
			} else {
				// 다운로드수
				search.setDwldCnt(doc.getMeta12());
				// 평점
				search.setScore(doc.getMeta11());
				// 참여자수
				search.setParticCnt(doc.getMeta62());
				// 패키지명
				search.setApkPkg(doc.getMeta65());
				// DPI별 ICON Mapping
				search.setFileLoc(this.getDpiMapping(doc));
			}

			// 구글YN
			search.setGoogleYN(StringUtils.equals(googleYn, "0") ? "Y" : "N");
			String prodGrdCd = doc.getMeta7();
			// 상품등급코드가공
			if (StringUtils.equals(prodGrdCd, "PD004401")) {
				search.setProdGrdCd("0");
			} else if (StringUtils.equals(prodGrdCd, "PD004402")) {
				search.setProdGrdCd("1");
			} else if (StringUtils.equals(prodGrdCd, "PD004403")) {
				search.setProdGrdCd("2");
			} else if (StringUtils.equals(prodGrdCd, "PD004404")) {
				search.setProdGrdCd("4");
			}
			// TOP_CAT_CD
			search.setTopCategory(new Category(topCatCd));
			// 카테고리 정보
			if (StringUtils.isNotEmpty(doc.getMeta39())) {
				search.setDpCategory(new Category(doc.getMeta39()));
			}
			// META_CLS_CD
			if (StringUtils.isNotEmpty(doc.getMeta27())) {
				search.setMetaClsf(new Category(doc.getMeta27()));
			}

			// Tag 가공(Seller Tag 3개 + kewyd Tag 4개)
			bTag.setLength(0);
			String sellerTag = doc.getMeta34();
			String keywdTag = doc.getMeta33();

			// Seller Tag 3개
			if (StringUtils.isNotBlank(sellerTag)) {
				sellerTags = StringUtils.splitByWholeSeparator(sellerTag, ",");
				if (!ArrayUtils.isEmpty(sellerTags)) {
					bTag.append(StringUtils.join(sellerTags, ",", 0, ((sellerTags.length > 3) ? 3 : sellerTags.length)));
				}
			}
			// Keywd Tag 4개
			if (StringUtils.isNotBlank(keywdTag)) {
				keywdTags = StringUtils.splitByWholeSeparator(keywdTag, ",");
				if (!ArrayUtils.isEmpty(keywdTags)) {
					bTag.append(StringUtils.join(keywdTags, ",", 0, ((keywdTags.length > 4) ? 4 : keywdTags.length)));
				}
			}

			search.setTag(bTag.toString());
		}
		return productList;
	}

	private String getWebtoonUrl(SearchReq searchReq, Doc doc) {

		// webtoonURL 설정파일로
		UriComponents uriComponents = UriComponentsBuilder
				.fromHttpUrl("http://m.tstore.co.kr/mobilepoc/sc/webtoon/webtoonList.omp")
				.queryParam("prodId", doc.getMeta3()).queryParam("strSearchword", searchReq.getKeywd())
				.queryParam("token", this.getToken(searchReq))
				.queryParam("phone_model_cd=", searchReq.getDeviceModelNo()).build();

		return uriComponents.toUriString();
	}

	private String getDpiMapping(Doc doc) {
		String dpi = "";
		String topCatCd = doc.getMeta9();
		String fileLoc = "";
		// DIP별 ICON Mapping
		// xhdpi
		if (StringUtils.equals(dpi, "PI000101") || StringUtils.equals(dpi, "PI000105")) {
			// vod
			if (StringUtils.equals(topCatCd, "DP000517") || StringUtils.equals(topCatCd, "DP000518")) {
				fileLoc = doc.getMeta68();
				// e-book
			} else if (StringUtils.equals(topCatCd, "DP000513")) {
				fileLoc = doc.getMeta71();
				// comic
			} else if (StringUtils.equals(topCatCd, "DP000514")) {
				fileLoc = doc.getMeta68();
				// 쇼핑/쿠폰
			} else if (StringUtils.equals(topCatCd, "DP000515")) {
				fileLoc = doc.getMeta46();
				// 웹툰
			} else if (StringUtils.equals(topCatCd, "DP000526")) {
				fileLoc = doc.getMeta75();
				// game/app, new 쇼핑/쿠폰
			} else {
				fileLoc = doc.getMeta66();
			}
			// hdpi
		} else if (StringUtils.equals(dpi, "PI000102")) {
			// vod
			if (StringUtils.equals(topCatCd, "DP000517") || StringUtils.equals(topCatCd, "DP000518")) {
				fileLoc = doc.getMeta56();
				// e-book
			} else if (StringUtils.equals(topCatCd, "DP000513")) {
				fileLoc = doc.getMeta57();
				// comic
			} else if (StringUtils.equals(topCatCd, "DP000514")) {
				fileLoc = doc.getMeta70();
				// 쇼핑/쿠폰
			} else if (StringUtils.equals(topCatCd, "DP000515")) {
				fileLoc = doc.getMeta46();
				// 웹툰
			} else if (StringUtils.equals(topCatCd, "DP000526")) {
				fileLoc = doc.getMeta75();
				// game/app, new 쇼핑/쿠폰
			} else {
				fileLoc = doc.getMeta55();
			}
			// mdpi
		} else {
			// vod
			if (StringUtils.equals(topCatCd, "DP000517") || StringUtils.equals(topCatCd, "DP000518")) {
				fileLoc = doc.getMeta69();
				// e-book
			} else if (StringUtils.equals(topCatCd, "DP000513")) {
				fileLoc = doc.getMeta60();
				// comic
			} else if (StringUtils.equals(topCatCd, "DP000514")) {
				fileLoc = doc.getMeta60();
				// 쇼핑/쿠폰
			} else if (StringUtils.equals(topCatCd, "DP000515")) {
				fileLoc = doc.getMeta46();
				// 웹툰
			} else if (StringUtils.equals(topCatCd, "DP000526")) {
				fileLoc = doc.getMeta75();
				// game/app, new 쇼핑/쿠폰
			} else {
				fileLoc = doc.getMeta67();
			}
		}
		return fileLoc;
	}

	private String getToken(SearchReq searchReq) {
		StringBuffer token = new StringBuffer();

		if (StringUtils.isNotEmpty(searchReq.getDeviceId()) && StringUtils.isNotEmpty(searchReq.getUserKey())) {
			token.append("msisdn=").append(searchReq.getDeviceId()).append(";").append("mbrno=")
					.append(searchReq.getUserKey()).append(";");
			// 암호화 모듈 필요.
		}
		return token.toString();
	}
}
