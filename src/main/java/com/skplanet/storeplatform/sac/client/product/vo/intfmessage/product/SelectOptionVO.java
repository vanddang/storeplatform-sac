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
import java.util.List;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.framework.core.common.vo.CommonVO;
import com.skplanet.storeplatform.sac.client.intfmessage.product.vo.SelectOptionProto;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.PriceVO;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.TitleVO;

/**
 * Interface Message SelectOption Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(SelectOptionProto.SelectOption.class)
public class SelectOptionVO extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id; // 선택 옵션 ID
	private String index; // 선택옵션 번호
	private TitleVO title; // 선택옵션 제목
	private PriceVO price; // 선택옵션 가격
	private List<SubSelectOptionVO> subSelectOptionList; // 두번째 옵션 리스트

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIndex() {
		return this.index;
	}

	public void setIndex(String index) {
		this.index = index;
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

	public List<SubSelectOptionVO> getSubSelectOptionList() {
		return this.subSelectOptionList;
	}

	public void setSubSelectOptionList(List<SubSelectOptionVO> subSelectOptionList) {
		this.subSelectOptionList = subSelectOptionList;
	}
}
