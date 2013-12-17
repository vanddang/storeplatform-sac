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
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.PopupProto;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.PriceVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.TitleVO;

/**
 * Interface Message Popup Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(PopupProto.Popup.class)
public class PopupVO extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id; // 이벤트 ID
	private String layout; // 레이아웃 (dimmed : 반투명, fillscreen : 전체화면)
	private TitleVO title; // 선택옵션 제목
	private PriceVO price; // 선택옵션 가격
	private SelectOptionVO selectOption; // child 옵션

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLayout() {
		return this.layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public TitleVO getTitle() {
		return this.title;
	}

	public void setTitle(TitleVO title) {
		this.title = title;
	}

	public PriceVO getPrice() {
		return this.price;
	}

	public void setPrice(PriceVO price) {
		this.price = price;
	}

	public SelectOptionVO getSelectOption() {
		return this.selectOption;
	}

	public void setSelectOption(SelectOptionVO selectOption) {
		this.selectOption = selectOption;
	}
}
