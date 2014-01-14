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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.external.client.search.vo.TstoreSearchRes;
import com.skplanet.storeplatform.external.client.search.vo.TstoreSearchV2Req;

@Service
@Transactional
public class SearchServiceV2Impl implements SearchServiceV2 {

	@Override
	public TstoreSearchRes search(TstoreSearchV2Req tstoreSearchV2Req) {
		String dummy = "{\"response\":{\"groups\":[{\"groupValue\":\"DP000503,DP000504,DP000508\",\"doclist\":{\"start\":0,\"docs\":[{\"title\":\"다나와PC견적\",\"author\":\"danawamobile\",\"description\":\"오직다나와에서만가능한간편한PC조립견적\",\"price\":\"0\",\"date\":\"20130819154817\",\"adult\":\"0\",\"isGoogleContents\":\"1\",\"googleOutlink\":\"\",\"meta1\":\"P\",\"meta2\":\"\",\"meta3\":\"0000282697\",\"meta4\":\"/images/IF1423162847220100914175009/0000282697/thumbnail/0000282697_72_73_0_12.JPG\",\"meta5\":\"DP000201\",\"meta6\":\"DP04015\",\"meta7\":\"PD004401\",\"meta8\":\"PD005606\",\"meta9\":\"DP000504\",\"meta10\":\"생활/위치>쇼핑\",\"meta11\":\"5.0\",\"meta12\":\"4231\",\"meta13\":\",0002,0003,0004,1111,1112,9999,CA01,HT11,HT14,HT17,HT18,HT21,HT22,KKWJ,KKWK,KKWL,KPWB,KPWN,KSWS,KSXN,KTW1,KTW2,KTW4,KTW5,LGDE,LGEF,LGEI,LGEP,LGER,LGES,LGEV,LGEW,LGEX,LGEY,LGEZ,LGFB,LGFD,LGFE,LGFF,LGFG,LGFI,LGFJ,LGFL,LGFN,LGFP,LGFQ,LGFR,LGFS,LGFT,LGFU,LGFV,LGFW,LGFX,LGFY,LGFZ,LGG0,LGG1,LGG2,LGG4,MT49,MT50,MT51,MT55,MT57,MT58,MT59,MT60,MT62,MT63,MT65,SE02,SE06,SE07,SK97,SKB1,SKB2,SKB3,SKB7,SKB8,SKB9,SKBA,SKBB,SKBC,SKBD,SKBE,SKBF,SKC0,SKC1,SKC2,SKX0,SKX1,SKX3,SKY0,SKY1,SKY2,SKY3,SKY4,SKY6,SKY7,SKY8,SKY9,SKYB,SKYC,SP01,SP02,SP05,SP06,SP07,SP09,SP0A,SP0B,SP0D,SP0E,SP0F,SP0G,SP0H,SP0I,SP0J,SP0K,SP0L,SP0M,SSMD,SSMF,SSMO,SSNC,SSNL,SSNT,SSNU,SSNV,SSNW,SSNY,SSNZ,SSO0,SSOA,SSOC,SSOD,SSOG,SSOH,SSOI,SSOJ,SSOK,SSOL,SSOM,SSON,SSOO,SSOP,SSOQ,SSOR,SSOS,SSOT,SSOU,SSOV,SSOW,SSOY,SSOZ,SSPS,SSR6,SSR7,SSU1,T001,T002,T003,T004,T005,T006,T007,T008,T009,T010,T011,T012,T013,T014,T015,T016,T017,T018,T019,T020,T021,T022,T023,T024,T025,T026,T027,T028,T029,T030,T031,T032,T033,T034,T035,XXC3,XXC7,ZZAJ,ZZAL,ZZAW,ZZIB\",\"meta14\":\"\",\"meta15\":\"0\",\"meta16\":\"\",\"meta17\":\"I\",\"meta18\":\"/data/img/IF1423162847220100914175009/0000282697/thumbnail/0000282697_104_104_0_9.PNG\",\"meta19\":\"/data/img/IF1423162847220100914175009/0000282697/thumbnail/0000282697_52_52_0_13.PNG\",\"meta20\":\"/data/img/IF1423162847220100914175009/0000282697/thumbnail/0000282697_42_42_0_15.PNG\",\"meta21\":\"\",\"meta22\":\"TSTORE\",\"meta23\":\"\",\"meta24\":\"0000282697\",\"meta25\":\"C\",\"meta26\":\"0\",\"meta27\":\"\",\"meta28\":\"다나와PC견적서비스는110만개의풍부한상품정보와1100여개협력사의최신가격정보가합쳐진가상온라인견적서비스를모바일로제공하는어플입니다.PC조립을계획하시는고객님을위한최고의어플!실시간으로제공되는정보를통해언제어디서나손쉽게내게맞는조립PC를구성하고검색할수있습니다.다나와PC견적애플리케이션의주요기능-PC부품별인기상품리스트제공-제품상세이미지와사양정보-필요한부품을빠르게검색할수있는부품검색-빠르게추가하고삭제하는견적카트-견적서저장기능과공유하기-업체별가격리스트-업체정보(연락처,이메일)-모바일결제서비스(무통장입금방식)저희다나와를아끼고사랑해주셔서대단히감사드립니다.앞으로도더욱알차고유용한애플리케이션을만들도록최선을다해노력하겠습니다.감사합니다.\",\"meta31\":\"Android\",\"meta32\":\",쇼핑\",\"meta33\":\",가격비교,쇼핑습관,물품구입,물품정보,장바구니\",\"meta34\":\",견적,PC조립,PC부품,컴퓨터,다나와\",\"meta35\":\"\",\"meta36\":\",남성용\",\"meta37\":\",대중교통\",\"meta38\":\",정보찾기\",\"meta39\":\"DP04015\",\"meta40\":\"\",\"meta42\":\"\",\"meta43\":\"\",\"meta44\":\"\",\"meta45\":\"\",\"meta46\":\"/data/img/IF1423162847220100914175009/0000282697/thumbnail/0000282697_DP000102.JPG\",\"meta47\":\"0\",\"meta48\":\"\",\"meta49\":\"\",\"meta50\":\"\",\"meta51\":\"\",\"meta52\":\"\",\"meta53\":\"\",\"meta54\":\"\",\"meta55\":\"/images/IF1423162847220100914175009/0000282697/thumbnail/0000282697_120_120_0_67.PNG\",\"meta56\":\"\",\"meta57\":\"\",\"meta58\":\"\",\"meta59\":\"/images/IF1423162847220100914175009/0000282697/thumbnail/0000282697_80_80_0_66.PNG\",\"meta60\":\"\",\"meta61\":\"\",\"meta62\":\"6\",\"meta63\":\"20130819\",\"meta64\":\"\",\"meta65\":\"com.danawa.estimate\",\"meta66\":\"/images/IF1423162847220100914175009/0000282697/thumbnail/0000282697_130_130_0_91.PNG\",\"meta67\":\"/images/IF1423162847220100914175009/0000282697/thumbnail/0000282697_60_60_0_65.PNG\",\"meta68\":\"\",\"meta69\":\"\",\"meta70\":\"\",\"meta71\":\"\",\"meta72\":\"0\",\"meta73\":\"\",\"meta74\":\"0\",\"meta75\":\"\",\"meta76\":\"0\",\"meta77\":\"0\",\"meta78\":\"0\",\"meta79\":\"\",\"meta80\":\"생활/위치\",\"meta81\":\"쇼핑\",\"meta82\":\"\",\"meta83\":\"\",\"meta84\":\"(주)다나와\"}],\"numFound\":1468}}],\"header\":{\"groupCount\":8,\"totalCount\":2399,\"relationKeywords\":\"\",\"originalQuery\":\"pc\",\"query\":\"pc\"}}}";
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			return objectMapper.readValue(dummy, TstoreSearchRes.class);
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
		return new TstoreSearchRes();
	}
}
