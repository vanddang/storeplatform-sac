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
import com.skplanet.storeplatform.sac.client.intfmessage.common.vo.IdentifierProto;

/**
 * Interface Message Identifier Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(IdentifierProto.Identifier.class)
public class Identifier extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * ID 구분 정보 (catalog : 상품 카달로그, channel : 상품 채널 ("CSI 시즌"의 시즌별 Channel ID), content : 상품 Content (VOD에서 기간제상품,
	 * 다운로드상품을 묶는 단위), episode : 상품 에피소드 ("CSI 시즌 1"의 1화 Episode ID), artist : 가수 또는 저자 ID, individual : 판매회원의 개발자ID,
	 * privateOperator : 개인사업자의 개발자ID, corporation : 법인사업자의 개발자ID, foreigner : 외국인 개발자ID, brand : 브랜드ID, feedback :
	 * 사용후기ID, banner : 배너ID)
	 */
	private String type;
	private String text; // 상품ID

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
