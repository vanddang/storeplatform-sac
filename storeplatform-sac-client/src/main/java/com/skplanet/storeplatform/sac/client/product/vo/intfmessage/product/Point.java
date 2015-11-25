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

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 할인율 정의
 * 
 * Updated on : 2014. 03. 31. Updated by : 임근대, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Point extends CommonInfo {
	private static final long serialVersionUID = 1L;

	/** 할인율 명 (ex. tmembership) */
	private String name;
	/** 할인율 유형 (normal/freepass) */
	private String type;
	
	/** 할인율 (% 생략) */
	private Integer discountRate;
	/** 할인가 */
	private Integer discountPrice;

    /**
     * 적용 금액
     */
    private Integer amount;

    /**
     * 적용 비율
     */
    private Integer rate;

    public Point() {}

    public Point(String name, String type, Integer rate, Integer amount) {
        this.name = name;
        this.type = type;
        this.rate = rate;
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    /**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the discountRate
	 */
	public Integer getDiscountRate() {
		return discountRate;
	}

	/**
	 * @param discountRate
	 *            the discountRate to set
	 */
	public void setDiscountRate(Integer discountRate) {
		this.discountRate = discountRate;
	}

	/**
	 * @return the discountPrice
	 */
	public Integer getDiscountPrice() {
		return discountPrice;
	}

	/**
	 * @param discountPrice
	 *            the discountPrice to set
	 */
	public void setDiscountPrice(Integer discountPrice) {
		this.discountPrice = discountPrice;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	

}

