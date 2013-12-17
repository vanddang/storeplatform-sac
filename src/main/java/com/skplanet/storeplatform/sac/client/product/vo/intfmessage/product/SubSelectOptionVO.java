/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.framework.core.common.vo.CommonVO;
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.SelectOptionProto;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.PriceVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.TitleVO;

/**
 * Interface Message SelectOption.SubSelectOption Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(SelectOptionProto.SelectOption.SubSelectOption.class)
public class SubSelectOptionVO extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String subId; // 두번째 콤보에 들어갈 옵션 ID
	private TitleVO subTitle; // 두번째 콤보에 들어갈 제목
	private PriceVO subPrice; // 두번째 콤보에 들어갈 가격

	public String getSubId() {
		return this.subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public TitleVO getSubTitle() {
		return this.subTitle;
	}

	public void setSubTitle(TitleVO subTitle) {
		this.subTitle = subTitle;
	}

	public PriceVO getSubPrice() {
		return this.subPrice;
	}

	public void setSubPrice(PriceVO subPrice) {
		this.subPrice = subPrice;
	}
}
