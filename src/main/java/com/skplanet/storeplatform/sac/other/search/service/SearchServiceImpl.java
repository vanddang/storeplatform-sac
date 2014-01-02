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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.search.sci.SearchSCI;
import com.skplanet.storeplatform.external.client.search.vo.ExSearchReq;
import com.skplanet.storeplatform.framework.core.util.DateUtil;
import com.skplanet.storeplatform.sac.client.other.vo.search.SearchReq;
import com.skplanet.storeplatform.sac.client.other.vo.search.SearchRes;

@Profile(value = { "stag", "real" })
@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchSCI searchSCI;

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
		ExSearchReq exSearchReq = new ExSearchReq();
		// 카테고리 셋팅
		this.setCategory(searchReq, exSearchReq);
		// adultYN이 비어있으면 실명인증 안함.
		this.setAudult(searchReq, exSearchReq);
		// order 지원 범위 확인
		this.setOrder(searchReq, exSearchReq);
		// 검색기간
		this.setDateFromTo(searchReq, exSearchReq);
		// meta 17 / meta26 / meta47 가공
		this.setMeta(searchReq, exSearchReq);
		// default 처리 (N)
		exSearchReq.setRel(searchReq.getRelSearchYN());
		// 암호화
		exSearchReq.setUvKey(searchReq.getDeviceId());
		// 구글링크여부
		exSearchReq.setGgOutlink(searchReq.getGoogleLinkYN());
		// 스펠링체크여부
		exSearchReq.setSc(searchReq.getSpellCorrectYN());
		// E/C 연동 후 String 형태의 JSON을 전달 받음.
		// String resultData = this.searchSCI.search(exSearchReq);
		// 결과값 가공
		// SearchRes searchRes = this.getResultData(resultData);
		return null;
	}

	private void setCategory(SearchReq searchReq, ExSearchReq exSearchReq) {
		if (StringUtils.isEmpty(searchReq.getCategory()))
			return;
		exSearchReq.setCategory(searchReq.getCategory());
		exSearchReq.setOffset(searchReq.getOffset());
		exSearchReq.setCount(searchReq.getCount());
	}

	private void setDateFromTo(SearchReq searchReq, ExSearchReq exSearchReq) {
		String dtType = searchReq.getDtType();
		if (StringUtils.isEmpty(dtType))
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

	private void setAudult(SearchReq searchReq, ExSearchReq exSearchReq) {
		if (StringUtils.isEmpty(searchReq.getAdultYN()))
			exSearchReq.setAdult("A");
		else
			exSearchReq.setAdult(searchReq.getAdultYN());
	}

	public void setOrder(SearchReq searchReq, ExSearchReq exSearchReq) {
		if (!this.orderMap.containsKey(searchReq.getOrderedBy())) {
			throw new RuntimeException("");
		} else {
			exSearchReq.setOrder(this.orderMap.get(searchReq.getOrderedBy()));
		}
	}

	private void setMeta(SearchReq searchReq, ExSearchReq exSearchReq) {

		// DB 조회.
		// UACD는 DB에서 조회해온다.
		// exSearchReq.setMeta13("");

		// ArrayList<String> listMeta17 = new ArrayList<String>();
		// if (StringUtils.equals(sType, "app") || StringUtils.equals(sType, "all")) {
		// listMeta17.add("I"); // 일반 App
		// }
		// if (!StringUtils.equals(sType, "app")) {
		//
		// listMeta17.add("B"); // 일반화질 동영상
		//
		// String sHdvSprtYn = ObjectUtils.toString(mapUrl.get("HDV_SPRT_YN"));
		// String sHdvSprtMmYn = ObjectUtils.toString(mapUrl.get("HDV_SPRT_MM_YN"));
		// String sEbookSprtYn = ObjectUtils.toString(mapUrl.get("EBOOK_SPRT_YN"));
		// String sMagazineSprtYn = ObjectUtils.toString(mapUrl.get("MAGAZINE_SPRT_YN"));
		// String sComicSprtYn = ObjectUtils.toString(mapUrl.get("COMIC_SPRT_YN"));
		// String sMusicSprtYn = ObjectUtils.toString(mapUrl.get("MUSIC_SPRT_YN"));
		// String sSsSprtYn = ObjectUtils.toString(mapUrl.get("SS_SPRT_YN"));
		// String sWetoonSprtYn = ObjectUtils.toString(mapUrl.get("WEBTOON_SPRT_YN"));
		//
		// if (StringUtils.equals(sHdvSprtYn, Consts.VAR_YES)) { // 고화질 지원여부
		// listMeta17.add("V");
		// }
		// if (StringUtils.equals(sHdvSprtMmYn, Consts.VAR_YES)) { // 고화질 HD 지원여부
		// listMeta17.add("H");
		// }
		// if (StringUtils.equals(sEbookSprtYn, Consts.VAR_YES)) { // e-Book 지원여부
		// listMeta17.add("E");
		// }
		// if (StringUtils.equals(sMagazineSprtYn, Consts.VAR_YES)) { // e-Book 잡지지원여부
		// listMeta17.add("J");
		// }
		// if (StringUtils.equals(sComicSprtYn, Consts.VAR_YES)) { // Comic 지원여부
		// listMeta17.add("C");
		// }
		// if (StringUtils.equals(sMusicSprtYn, Consts.VAR_YES)) { // Music 지원여부
		// listMeta17.add("A");
		// }
		// if (StringUtils.equals(sWetoonSprtYn, Consts.VAR_YES)) { // Webtoon 지원여부
		// listMeta17.add("W");
		// }
		// if (StringUtils.equals(sSsSprtYn, Consts.VAR_YES) || StringUtils.equals(sSsSprtYn, "S")) { // Shopping 지원여부
		// // Header 의 뉴쇼핑 바이너리 여부
		// String sNewShopping = ObjectUtils.toString(map.get(Consts.HTTP_NEW_SHOPPING));
		// if (StringUtils.equals(sNewShopping, Consts.VAR_YES)) { // New 쇼핑 바이너리
		// listMeta17.add("D");
		// } else { // 구쇼핑 바이너리
		// listMeta17.add("S");
		// }
		// }
		//
		// }
		// mapUrl.put("meta17", StringUtils.join(listMeta17, ","));

		// DRM 적용 여부 ( Y:DRM 적용/N:DRM 미적용/A:전체 )
		// String sDrmSprtYn = ObjectUtils.toString(mapUrl.get("MOV_DRM_SPRT_YN"), "PD005200");
		// if (StringUtils.equals(sDrmSprtYn, "PD005200")) {
		// mapUrl.put("meta26", Consts.VAR_NO);
		// }

		// String sHdmiYn = ObjectUtils.toString(mapUrl.get("HDMI_YN"));
		// String sHdcpYn = ObjectUtils.toString(mapUrl.get("HDCP_YN"));
		// if (StringUtils.equals(sHdmiYn, Consts.VAR_YES) && StringUtils.equals(sHdcpYn, Consts.VAR_NO)) {
		// mapUrl.put("meta47", Consts.VAR_YES);
		// }

	}

	private SearchRes getResultData(String resultData) {
		// GSON 연동
		//

		return new SearchRes();
	}
}
