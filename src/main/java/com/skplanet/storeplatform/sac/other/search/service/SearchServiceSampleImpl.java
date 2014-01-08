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

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.skplanet.storeplatform.external.client.search.vo.Doc;
import com.skplanet.storeplatform.external.client.search.vo.Group;
import com.skplanet.storeplatform.external.client.search.vo.Header;
import com.skplanet.storeplatform.external.client.search.vo.TstoreSearchReq;
import com.skplanet.storeplatform.external.client.search.vo.TstoreSearchRes;
import com.skplanet.storeplatform.framework.core.util.DateUtil;
import com.skplanet.storeplatform.sac.client.other.vo.search.Search;
import com.skplanet.storeplatform.sac.client.other.vo.search.SearchCategory;
import com.skplanet.storeplatform.sac.client.other.vo.search.SearchReq;
import com.skplanet.storeplatform.sac.client.other.vo.search.SearchRes;
import com.skplanet.storeplatform.sac.common.service.CategoryService;
import com.skplanet.storeplatform.sac.common.service.CommonService;
import com.skplanet.storeplatform.sac.common.util.CryptUtils;
import com.skplanet.storeplatform.sac.common.vo.Device;
import com.skplanet.storeplatform.sac.other.search.repository.SearchRepository;

/**
 * 
 * 검색 서비스 Class 구현체
 * 
 * Updated on : 2014. 1. 6. Updated by : 김현일, 인크로스
 */
@Profile(value = { "dev", "local" })
@Service
@Transactional
public class SearchServiceSampleImpl implements SearchService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SearchServiceImpl.class);

	@Autowired
	private SearchRepository searchRepository;

	@Autowired
	private CommonService commonService;

	@Autowired
	private CategoryService categoryService;

	/**
	 * 요청 정렬관련 Map
	 */
	private final Map<String, String> orderMap;

	public SearchServiceSampleImpl() {
		this.orderMap = new HashMap<String, String>();
		// 최신
		this.orderMap.put("recent", "L");
		// 평점
		this.orderMap.put("score", "G");
		// 정확도
		this.orderMap.put("accuracy", "R");
		// 인기순(다운로드)
		this.orderMap.put("popular", "D");
	}

	/**
	 * webtoonUrl 셋팅
	 */
	@Value("#{propertiesForSac['nate.tisp.json.webtoon.url']}")
	private String webtoonUrl;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.other.search.service.SearchService#search(com.skplanet.storeplatform.sac.client
	 * .other.vo.search.SearchReq)
	 */
	@Override
	public SearchRes search(SearchReq searchReq) {

		LOGGER.debug("Search Service");

		// TB_CM_DEVICE 정보조회
		// UA_CD 및 파라미터 조작시 필요
		Device device = this.commonService.getDevice(searchReq.getDeviceModelNo());

		if (device == null) {
			LOGGER.warn("device is null");
			throw new RuntimeException("device is null");
		}
		TstoreSearchReq ecSearchReq = new TstoreSearchReq();

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
		ecSearchReq.setUvKey(CryptUtils.encrypt(CryptUtils.AES, "F82799142FA202C1", searchReq.getDeviceId()));
		// 구글링크여부
		ecSearchReq.setGgOutlink("Y");

		// 스펠링체크여부
		String sc = StringUtils.equals(searchReq.getSpellCorrectYN(), "Y") ? "on" : "";
		if (!StringUtils.isEmpty(sc))
			ecSearchReq.setSc(sc);
		// E/C 연동 후 String 형태의 JSON을 전달 받음.

		LOGGER.debug("exSearchReq : {}", ecSearchReq);

		TstoreSearchRes tstoreSearchRes = this.searchRepository.search(ecSearchReq);

		LOGGER.debug("exSearchRes : {}", tstoreSearchRes);
		// 데이터 가공
		return this.getResultData(searchReq, ecSearchReq, tstoreSearchRes);
	}

	/**
	 * 
	 * <pre>
	 * 카테고리 셋팅.
	 * </pre>
	 * 
	 * @param searchReq
	 *            searchReq
	 * @param exSearchReq
	 *            exSearchReq
	 */
	private void setCategory(SearchReq searchReq, TstoreSearchReq tstoreSearchReq) {
		// 카테고리가 비어있거나, ALL이면 skip(통합검색)
		if (StringUtils.isBlank(searchReq.getCategory()) || StringUtils.equals(searchReq.getCategory(), "all"))
			return;
		// 그외 카테고리가 있는경우는 카테고리 검색임으로 아래와 같이 셋팅을한다.
		// 카테고리, offset, count를 설정한다.
		// String topCatCd = "";
		if (StringUtils.equals(searchReq.getCategory(), "app")) {
			tstoreSearchReq.setCategory("DP000503,DP000504,DP000508");
		} else {
			tstoreSearchReq.setCategory(this.categoryService.getCategoryCd(searchReq.getCategory()));
		}
		tstoreSearchReq.setOffset(searchReq.getOffset());
		tstoreSearchReq.setCount(searchReq.getCount());
	}

	/**
	 * 
	 * <pre>
	 * 검색 기간 셋팅.
	 * </pre>
	 * 
	 * @param searchReq
	 *            searchReq
	 * @param exSearchReq
	 *            exSearchReq
	 */
	private void setDateFromTo(SearchReq searchReq, TstoreSearchReq tstoreSearchReq) {

		// DateType이 존재하지 않으면 skip.
		String dtType = searchReq.getDtType();
		if (StringUtils.isBlank(dtType))
			return;
		Date to = new Date();
		// 검색일 기준으로
		// 주단위, 월단위, 년단위에 -1 씩한다.
		Date from = null;
		if (dtType.equals("W")) {
			from = DateUtil.addWeeks(to, -1);
		} else if (dtType.equals("M")) {
			from = DateUtil.addMonths(to, -1);
		} else if (dtType.equals("Y")) {
			from = DateUtil.addYears(to, -1);
		}
		tstoreSearchReq.setDateFrom(DateFormatUtils.format(from, "yyyyMMdd"));
		tstoreSearchReq.setDateTo(DateFormatUtils.format(to, "yyyyMMdd"));
	}

	/**
	 * 
	 * <pre>
	 * 성인인증여부 셋팅.
	 * </pre>
	 * 
	 * @param searchReq
	 * @param exSearchReq
	 */
	private void setAdult(SearchReq searchReq, TstoreSearchReq tstoreSearchReq) {
		// 미실명인증 - A(전체노출)
		// 실명인증(성인) - Y(성인컨텐츠포함)
		// 실명인증(미성인) - N(성인컨텐츠미포함)
		if (StringUtils.isBlank(searchReq.getAdultYN()))
			tstoreSearchReq.setAdult("A");
		else
			tstoreSearchReq.setAdult(searchReq.getAdultYN());
	}

	/**
	 * 
	 * <pre>
	 * 검색 정렬 셋팅.
	 * </pre>
	 * 
	 * @param searchReq
	 *            searchReq
	 * @param exSearchReq
	 *            exSearchReq
	 */
	public void setOrder(SearchReq searchReq, TstoreSearchReq tstoreSearchReq) {
		// 정렬기준이 없으면 무조건 최신
		if (!this.orderMap.containsKey(searchReq.getOrderedBy())) {
			tstoreSearchReq.setOrder(this.orderMap.get("recent"));
		} else {
			// 그외는 OrderMap에서 조회를 한다.
			tstoreSearchReq.setOrder(this.orderMap.get(searchReq.getOrderedBy()));
		}
	}

	/**
	 * 
	 * <pre>
	 * 그외 MetaData 셋팅.
	 * </pre>
	 * 
	 * @param device
	 *            device
	 * @param searchReq
	 *            searchReq
	 * @param exSearchReq
	 *            exSearchReq
	 */
	private void setMeta(Device device, SearchReq searchReq, TstoreSearchReq tstoreSearchReq) {
		// TB_CM_DEVICE의 UA_CD를 조회한다.
		tstoreSearchReq.setMeta13(device.getUaCd());
		List<String> listMeta17 = new ArrayList<String>();
		String category = StringUtils.trimToEmpty(searchReq.getCategory());

		// 카테고리가 app(DP000503,DP000504,DP000508)
		if (StringUtils.equals(searchReq.getCategory(), "app") || StringUtils.equals(searchReq.getCategory(), "all")) {
			listMeta17.add("I");
		}
		// 그외 카테고리는 Device 정보에서 필요한 메타값을 셋팅
		if (!StringUtils.equals(searchReq.getCategory(), "app")) {
			listMeta17.add("B");
			// 고화질 지원여부
			if (StringUtils.equals(device.getSdVideoSprtYn(), "Y")) {
				listMeta17.add("V");
			}
			// 고화질 HD 지원여부
			if (StringUtils.equals(device.getHdvSprtYn(), "Y")) {
				listMeta17.add("H");
			}
			// e-Book 지원여부
			if (StringUtils.equals(device.getEbookSprtYn(), "Y")) {
				listMeta17.add("E");
			}
			// e-Book 잡지 지원여부
			if (StringUtils.equals(device.getMagazineSprtYn(), "Y")) {
				listMeta17.add("J");
			}
			// Comic 지원여부
			if (StringUtils.equals(device.getComicSprtYn(), "Y")) {
				listMeta17.add("C");
			}
			// Music 지원여부
			if (StringUtils.equals(device.getMusicSprtYn(), "Y")) {
				listMeta17.add("A");
			}
			// Webtoon 지원여부
			if (StringUtils.equals(device.getWebtoonSprtYn(), "Y")) {
				listMeta17.add("W");
			}
			// Shopping 지원여부
			if (StringUtils.equals(device.getSclShpgSprtYn(), "Y")
					|| StringUtils.equals(device.getSclShpgSprtYn(), "S")) {
				// 쇼핑 바이너피 지원여부
				if (StringUtils.equals(searchReq.getNewShoppingYN(), "Y")) {
					listMeta17.add("D");
				} else {
					// 구쇼핑 바이너리
					listMeta17.add("S");
				}
			}
			tstoreSearchReq.setMeta17(StringUtils.join(listMeta17, ","));
		}

		// DRM 적용 여부 ( Y:DRM 적용/N:DRM 미적용/A:전체 )
		String drmSprtYn = ObjectUtils.toString(device.getVideoDrmSprtYn(), "PD005200");
		if (StringUtils.equals(drmSprtYn, "PD005200")) {
			tstoreSearchReq.setMeta26("N");
		}
		// 연관검색어 - 자체처리용이므로 TISP 연동시 제거
		if (StringUtils.equals(searchReq.getRelSearchYN(), "Y")) {
			tstoreSearchReq.setRel("Y");
		}
		// HDCP, HDMI 조건 추가
		if (StringUtils.equals(device.getHdmiSprtYn(), "Y") && StringUtils.equals(device.getHdcpSprtYn(), "Y")) {
			tstoreSearchReq.setMeta47("Y");
		}
	}

	/**
	 * 
	 * <pre>
	 * 검색 결과 가공.
	 * </pre>
	 * 
	 * @param searchReq
	 *            searchReq
	 * @param ecSearchReq
	 *            ecSearchReq
	 * @param exSearchRes
	 *            exSearchRes
	 * @return
	 */
	private SearchRes getResultData(SearchReq searchReq, TstoreSearchReq tstoreSearchReq,
			TstoreSearchRes tstoreSearchRes) {

		if (tstoreSearchRes == null || tstoreSearchRes.getResponse() == null
				|| tstoreSearchRes.getResponse().getHeader() == null
				|| tstoreSearchRes.getResponse().getHeader().getTotalCount() <= 0) {
			// 추천상품리턴..
			return this.getResultRecomData(searchReq);
		}
		// 무조건 데이터가 있는 경우임.
		Header header = tstoreSearchRes.getResponse().getHeader();
		List<Group> groups = tstoreSearchRes.getResponse().getGroups();
		// List<Doc> docs = exSearchRes.getResponse().getDocs();
		// List<String> categories = exSearchRes.getResponse().getCategories();
		SearchRes searchRes = new SearchRes();
		List<SearchCategory> categoryList = new ArrayList<SearchCategory>();
		if (StringUtils.isEmpty(tstoreSearchReq.getCategory())) {
			if (header.getGroupCount() > 0) {
				List<Doc> docList = new ArrayList<Doc>();
				for (Group group : groups) {
					if (group.getDoclist().getNumFound() > 0) {
						SearchCategory category = new SearchCategory();
						if (StringUtils.equals(group.getGroupValue(), "DP000503,DP000504,DP000508")) {
							category.setCategoryNm("app");
						} else {
							category.setCategoryNm(this.categoryService.getCategoryEngNm(group.getGroupValue()));
						}
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
				SearchCategory category = new SearchCategory();
				if (StringUtils.equals(tstoreSearchReq.getCategory(), "DP000503,DP000504,DP000508")) {
					category.setCategoryNm("app");
				} else {
					category.setCategoryNm(this.categoryService.getCategoryEngNm(searchReq.getCategory()));
				}
				category.setCategoryCnt(header.getTotalCount());
				categoryList.add(category);
				searchRes.setCategoryList(categoryList);
				searchRes.setProductList(this.getProductList(searchReq, tstoreSearchRes.getResponse().getDocs()));
				searchRes.setTotalCount(header.getTotalCount());
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

	/**
	 * 
	 * <pre>
	 * 검색결과가 없을경우 T Store 운영자 추천상품 조회.
	 * </pre>
	 * 
	 * @param searchReq
	 *            searchReq
	 * @return SearchRes
	 */
	private SearchRes getResultRecomData(SearchReq searchReq) {
		return null;
	}

	/**
	 * 
	 * <pre>
	 * 검색 상품 리스트 가공.
	 * </pre>
	 * 
	 * @param searchReq
	 * @param docList
	 * @return
	 */
	private List<Search> getProductList(SearchReq searchReq, List<Doc> docList) {
		List<Search> productList = new ArrayList<Search>();
		String topCatCd = "";
		// Seller Tag
		String[] sellerTags = null;
		// Keyword Tag
		String[] keywdTags = null;
		// Tag 가공
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
			search.setTopCategory(new SearchCategory(topCatCd, this.categoryService.getCategoryEngNm(topCatCd),
					this.categoryService.getCategoryDesc(topCatCd)));
			// 카테고리 정보
			if (StringUtils.isNotEmpty(doc.getMeta39())) {
				search.setDpCategory(new SearchCategory(doc.getMeta39(), this.categoryService.getCategoryEngNm(doc
						.getMeta39()), this.categoryService.getCategoryDesc(doc.getMeta39())));
			}
			// META_CLS_CD
			if (StringUtils.isNotEmpty(doc.getMeta27())) {
				search.setMetaClsf(new SearchCategory(doc.getMeta27(), this.categoryService.getCategoryEngNm(doc
						.getMeta27()), this.categoryService.getCategoryDesc(doc.getMeta27())));
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
			productList.add(search);
		}
		return productList;
	}

	/**
	 * 
	 * <pre>
	 * WebtoonURL 가공.
	 * </pre>
	 * 
	 * @param searchReq
	 *            searchReq
	 * @param doc
	 *            doc
	 * @return String
	 */
	private String getWebtoonUrl(SearchReq searchReq, Doc doc) {

		// webtoonURL 설정파일로
		UriComponents uriComponents = UriComponentsBuilder
				.fromHttpUrl("http://m.tstore.co.kr/mobilepoc/sc/webtoon/webtoonList.omp")
				.queryParam("prodId", doc.getMeta3()).queryParam("strSearchword", searchReq.getKeywd())
				.queryParam("token", this.getToken(searchReq))
				.queryParam("phone_model_cd=", searchReq.getDeviceModelNo()).build();

		return uriComponents.toUriString();
	}

	/**
	 * 
	 * <pre>
	 * DPI Mapping.
	 * </pre>
	 * 
	 * @param doc
	 *            doc
	 * @return String
	 */
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

	/**
	 * 
	 * <pre>
	 * WebtoonURL 가공시 Token값 생성.
	 * </pre>
	 * 
	 * @param searchReq
	 *            searchReq
	 * @return String
	 */
	private String getToken(SearchReq searchReq) {
		StringBuffer token = new StringBuffer();

		if (StringUtils.isNotEmpty(searchReq.getDeviceId()) && StringUtils.isNotEmpty(searchReq.getUserKey())) {
			token.append("msisdn=").append(searchReq.getDeviceId()).append(";").append("mbrno=")
					.append(searchReq.getUserKey()).append(";");
		}
		return CryptUtils.encrypt(CryptUtils.AES128_CTR, "9", token.toString());
	}
}
