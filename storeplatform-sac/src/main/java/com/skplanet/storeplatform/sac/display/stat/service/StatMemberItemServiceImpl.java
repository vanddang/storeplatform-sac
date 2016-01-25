/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.stat.service;

import com.skplanet.storeplatform.sac.common.header.extractor.HeaderExtractor;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.sac.client.display.vo.other.OtherArtistReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherArtistRes;
import com.skplanet.storeplatform.sac.client.product.vo.Card;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.cache.service.PanelCardInfoManager;
import com.skplanet.storeplatform.sac.display.cache.vo.CardInfo;
import com.skplanet.storeplatform.sac.display.card.service.CardDetailService;
import com.skplanet.storeplatform.sac.display.card.util.CardDynamicInfoProcessor;
import com.skplanet.storeplatform.sac.display.card.vo.CardDetail;
import com.skplanet.storeplatform.sac.display.card.vo.PreferredCategoryInfo;
import com.skplanet.storeplatform.sac.display.feature.product.service.ProductListService;
import com.skplanet.storeplatform.sac.display.feature.product.vo.ListProduct;
import com.skplanet.storeplatform.sac.display.other.service.OtherArtistService;
import com.skplanet.storeplatform.sac.display.stat.vo.StatLike;

/**
 * <p>
 * MemberSegmentServiceImpl
 * </p>
 * Created on 2014.11.05 by 서대영, SK플래닛
 * Updated on 2015.05.12 by 서대영, SK플래닛 : 앨범/아티스트 통계타입에 대한 통계조회 기능 추가 
 */
@Service
public class StatMemberItemServiceImpl implements StatMemberItemService {

	private static final String CLSF_CARD   = "DP01210001";
	private static final String CLSF_PROD   = "DP01210002";
	private static final String CLSF_ARTIST = "DP01210003";
	private static final String CLSF_ALBUM  = "DP01210004";

	@Autowired
	private CardDetailService cardDetailService;
	
    @Autowired
    private PanelCardInfoManager panelCardInfoManager;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	HeaderExtractor header;

	@Autowired
	private OtherArtistService otherArtistService;

	@Override
	public Object findItem(StatLike like, PreferredCategoryInfo preferredCategoryInfo) {
		String statsClsf = like.getStatsClsf();
		String statsKey = like.getStatsKey();
		String userKey = like.getUserKey();

		Object item;
		if (CLSF_CARD.equals(statsClsf)) {
			item = findCard( statsKey, userKey, preferredCategoryInfo );
		} else if (CLSF_PROD.equals(statsClsf) || CLSF_ALBUM.equals(statsClsf)) {
			item = findProd( statsKey );
		} else if (CLSF_ARTIST.equals(statsClsf)) {
			item = findArtist( statsKey );
		} else {
			item = new Object();
		}

		return item;
	}

	@Override
	public Card findCard( String cardId, String userKey, PreferredCategoryInfo preferredCategoryInfo) {

		// 정적 처리 (Cache)
        CardInfo cardInfo = panelCardInfoManager.getCardInfo(header.getTenantId(), cardId);
        
        if (cardInfo == null)  {
        	return null;
        }
        
        CardDetail cardDetail = new CardDetail();
        BeanUtils.copyProperties(cardInfo, cardDetail);
        Card card = cardDetailService.makeCard(cardDetail, preferredCategoryInfo, header.getLangCd());
        
        if (card == null) {
        	return null;
        }
        
        // 동적 처리
        CardDynamicInfoProcessor processor = new CardDynamicInfoProcessor(header.getTenantId(), cardDetailService);
        processor.addCard(card);
        processor.execute(userKey);
        return card;
	}

	@Override
	public Product findProd( String prodId ) {
		return metaInfoService.getProductMeta( prodId );
	}
	
	@Override
	public Contributor findArtist( String artistId ) {
		OtherArtistReq req = new OtherArtistReq();
		req.setArtistId(artistId);
		OtherArtistRes otherArtistRes = otherArtistService.searchArtistDetail( req, header.getHeader() );
		return otherArtistRes.getContributor();
	}

}
