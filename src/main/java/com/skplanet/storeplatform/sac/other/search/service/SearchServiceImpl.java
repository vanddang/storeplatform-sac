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

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.client.other.vo.search.SearchReq;
import com.skplanet.storeplatform.sac.client.other.vo.search.SearchRes;

@Profile(value = { "dev", "local" })
@Service
public class SearchServiceImpl implements SearchService {

	@Override
	public SearchRes search(SearchReq searchReq) {
		// TODO Auto-generated method stub

		String dummy = "{\"categoryList\":[{\"categoryCd\":\"DP000503,DP000504,DP000508\",\"categoryCnt\":3  }],\"relKeywd\":\"\",\"autoTransKeywd\":\"cacao\",\"orgKeywd\":\"cacao\",\"totalCount\":0,\"productList\":[{\"topCategory\":{\"categoryCd\":\"DP000503\"},\"dpCategory\":{\"categoryCd\":\"DP03004\"},\"metaClsf\":null,\"prodId\":\"0000393058\",\"prodNm\":\"CACAO광고하기\",\"prodDesc\":\"카카오톡으로 광고하자! 더이상의 막대한 광고 비용은 노노!!\",\"prodVer\":null,\"prodNetAmt\":null,\"prodType\":null,\"prodAmt\":\"900\",\"prodGrdCd\":\"0\",\"dcRate\":null,\"aid\":null,\"appVerCd\":null,\"artistNm\":null,\"vodChnlCompNm\":null,\"vodSaleDt\":null,\"bookArtistNm\":null,\"bookChnlCompNm\":null,\"bookSaleDt\":null,\"musicArtistNm\":null,\"musicAlbumNm\":null,\"brandId\":null,\"brandNm\":null,\"drm\":null,\"webtoonUrl\":null,\"fileLoc\":\"/android4/201306/08/IF1423462173620110830033319/0000393058/img/thumbnail/0000393058_60_60_0_65_20130608143724.PNG\",\"sellerNm\":null,\"outLinkUrl\":null,\"dwldCnt\":\"37\",\"score\":\"5.0\",\"particCnt\":\"2\",\"apkPkg\":\"kr.co.jkh.kakaoadvertise\",\"googleYN\":\"N\",\"tag\":\"카카오톡,광고,마케팅트위터,포토톡,채팅,메신저\",\"inAppBilling\":null  },{\"topCategory\":{\"categoryCd\":\"DP000503\"},\"dpCategory\":{\"categoryCd\":\"DP03004\"},\"metaClsf\":null,\"prodId\":\"0000396938\",\"prodNm\":\"CACAO 광고 (공짜버전)\",\"prodDesc\":\"카톡 광고 (공짜버전입니다. 마케팅 광고 낚시 홍보)\",\"prodVer\":null,\"prodNetAmt\":null,\"prodType\":null,\"prodAmt\":\"0\",\"prodGrdCd\":\"0\",\"dcRate\":null,\"aid\":null,\"appVerCd\":null,\"artistNm\":null,\"vodChnlCompNm\":null,\"vodSaleDt\":null,\"bookArtistNm\":null,\"bookChnlCompNm\":null,\"bookSaleDt\":null,\"musicArtistNm\":null,\"musicAlbumNm\":null,\"brandId\":null,\"brandNm\":null,\"drm\":null,\"webtoonUrl\":null,\"fileLoc\":\"/android4/201306/17/IF1423462173620110830033319/0000396938/img/thumbnail/0000396938_60_60_0_65_20130617232720.PNG\",\"sellerNm\":null,\"outLinkUrl\":null,\"dwldCnt\":\"76\",\"score\":\"5.0\",\"particCnt\":\"1\",\"apkPkg\":\"kr.co.jkh.kakaoadvertise.ads\",\"googleYN\":\"N\",\"tag\":\"카카오톡,카톡,광고트위터,포토톡,채팅,메신저\",\"inAppBilling\":null  },{\"topCategory\":{\"categoryCd\":\"DP000504\"},\"dpCategory\":{\"categoryCd\":\"DP04003\"},\"metaClsf\":null,\"prodId\":\"0000327630\",\"prodNm\":\"CACAO 프로필 앨범 (카톡+카스 사진뽑기\",\"prodDesc\":\"카카오톡과 카카오 스토리 사진들 저장을 하세요!!\",\"prodVer\":null,\"prodNetAmt\":null,\"prodType\":null,\"prodAmt\":\"990\",\"prodGrdCd\":\"0\",\"dcRate\":null,\"aid\":null,\"appVerCd\":null,\"artistNm\":null,\"vodChnlCompNm\":null,\"vodSaleDt\":null,\"bookArtistNm\":null,\"bookChnlCompNm\":null,\"bookSaleDt\":null,\"musicArtistNm\":null,\"musicAlbumNm\":null,\"brandId\":null,\"brandNm\":null,\"drm\":null,\"webtoonUrl\":null,\"fileLoc\":\"/android4/201302/04/IF1423462173620110830033319/0000327630/img/thumbnail/0000327630_60_60_0_65.PNG\",\"sellerNm\":null,\"outLinkUrl\":null,\"dwldCnt\":\"742\",\"score\":\"3.0\",\"particCnt\":\"4\",\"apkPkg\":\"kr.co.jkh.kakaoalbumpro\",\"googleYN\":\"N\",\"tag\":\"카톡,카카오,카톡앨범배터리,탐색,저장,메모리관리\",\"inAppBilling\":null  }]}";

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			return objectMapper.readValue(dummy, SearchRes.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new SearchRes();
	}
}
