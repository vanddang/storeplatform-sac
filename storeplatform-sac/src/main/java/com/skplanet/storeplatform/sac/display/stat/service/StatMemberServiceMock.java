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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skplanet.storeplatform.sac.client.display.vo.stat.LikeRes;
import com.skplanet.storeplatform.sac.client.display.vo.stat.ListByMemberReq;
import com.skplanet.storeplatform.sac.client.display.vo.stat.ListByMemberRes;
import com.skplanet.storeplatform.sac.client.product.vo.Card;
import com.skplanet.storeplatform.sac.client.product.vo.DatasetProp;
import com.skplanet.storeplatform.sac.client.product.vo.EtcProp;
import com.skplanet.storeplatform.sac.client.product.vo.Stats;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * <p>
 * MemberSegmentServiceMcok (더미 데이터)
 * </p>
 * Updated on : 2014. 10. 30 Updated by : 서대영, SK 플래닛.
 */
// @Service
public class StatMemberServiceMock implements StatMemberService {

	@Override
	public ListByMemberRes listByMember(ListByMemberReq req, SacRequestHeader header) {
		LikeRes like = new LikeRes();
		like.setStatsClsf("DP01210001");
		like.setStatsKey("CRD0000002");
		like.setCntLike(0);
		like.setCntShar(1);
		
		Card item = new Card();
		item.setId("CRD00001");
		item.setTypeCd("DP1180101");
		item.setTitle("게임 유료다운로드BEST");
		item.setDesc("사용자들이 많이 이용하는 유료 상품을 확인해 보세요.");
		item.setLayout("3x1+2");
		
		Source source1 = new Source();
		source1.setMediaType("image/png");
		source1.setType("cardimg");
		source1.setRatio("4:3");
		source1.setUrl("/android6/201311/22/IF142306712239/0000643818/img/thumbnail/000064182120310.PNG");
		
		Source source2 = new Source();
		source2.setMediaType("image/png");
		source2.setType("landimg");
		source2.setRatio("16:9");
		source2.setUrl("/android6/201311/22/IF142306712239/0000643818/img/thumbnail/000064182120310.PNG");
		
		List<Source> sourceList = Arrays.asList(source1, source2);
		
		item.setSourceList(sourceList);
		
		item.setLndTitle("게임 유료다운로드 BEST");
		item.setLndDesc("사용자들이 많이 이용하는 유료 상품입니다.");
		item.setLndLayout("1x100");
		item.setMenuId("DP01");
		
		EtcProp etcProp = new EtcProp();
		etcProp.setExpoYnCardTitle("Y");
		etcProp.setExpoYnCardDesc("Y");
		etcProp.setExpoYnCardLike("Y");
		etcProp.setExpoYnCardShar("Y");
		etcProp.setExpoYnCardDcPrvPrice("N");
		etcProp.setExpoYnCardDcRate("N");
		etcProp.setExpoYnCardImg("N");
		etcProp.setExpoYnCardItemNo("N");
		etcProp.setExpoYnCardRecomReason("N");
		etcProp.setExpoYnCardAbstrTm("N");
		etcProp.setExpoYnLndTitle("Y");
		etcProp.setExpoYnLndDesc("Y");
		etcProp.setExpoYnLndItemNo("N");
		etcProp.setExpoYnLndDcPrvPrice("Y");
		etcProp.setExpoYnLndDcRate("Y");
		etcProp.setExpoYnLndImg("N");
		etcProp.setExpoYnLndImgOverlay("N");
		
		item.setEtcProp(etcProp);
		
		DatasetProp datasetProp = new DatasetProp();
		datasetProp.setId("DT00000002");
		datasetProp.setTitle("상품리스트");
		datasetProp.setUrl("/display/featured/list/listId/#{listId}/menuId/${menuId}/startKey/${nextKey}/count/${rownum}");
		datasetProp.setSmartOfrListId("OFR0000001");
		Map<String, String> urlParam = new HashMap<String, String>();
		urlParam.put("listId", "RNK00000006");
		urlParam.put("menuId", "DP01");
		datasetProp.setUrlParam(urlParam);
		
		item.setDatasetProp(datasetProp);
		
		Stats stats = new Stats();
		stats.setCntLike(220);
		stats.setCntShar(120);
		item.setStats(stats);
		
		like.setItem(item);
		
		ListByMemberRes res = new ListByMemberRes();
		res.setLikeList(Arrays.asList(like));
		res.setStartKey(11);
		res.setHasNext("Y");
		res.setCount(20);
		res.setTotalCount(200);
		
		return res;
	}

}
