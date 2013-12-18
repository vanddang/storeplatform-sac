/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.framework.core.common.vo.CommonVO;
import com.skplanet.storeplatform.sac.client.intfmessage.common.vo.DateProto;

/**
 * Interface Message Date Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(DateProto.Date.class)
public class Date extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * 날짜타입 (date/reg : 등록일, date/saleReg : 판매등록 날짜, date/purchase : 구매날짜, date/update : 변경날짜, duration/usagePeriod :
	 * 이용기간, uint/usagePeriod : 이용기간, date/auth : 인증 날짜, duration/salePeriod : 판매기간, date/access : 접속일자, date/cancel :
	 * 해지일자 (자동결제 상품에서 사용), date/update : 인기검색어 업데이트 시간, duration/eventPeriod : 이벤트 기간, date/broadcast : 방송날짜)
	 * date/duration : 특정기간, chart/duration : 음원순위기간)
	 */
	private String type;
	/*
	 * 날짜 ISO 8601 Basic 형식으로 표기 20120913T195630+0900/20121013T195630+0900 ; 시작/끝 날짜를 지정하는 경우, /20121013T195630+0900 ;
	 * 시작 날짜 없이 끝 날짜만 지정하는 경우, 20120913T195630+0900/ ; 끝 날짜 없이 시작 날짜만 지정하는 경우
	 */
	private String text;

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
