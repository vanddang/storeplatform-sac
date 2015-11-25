/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.category;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

/**
 * Interface Message VodBox List Value Object.
 * 
 * Updated on : 2014. 02. 12. Updated by : 유시혁.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CategoryVodBoxSacRes extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 11123123142L;

	private CommonResponse commonRes;

	private List<Product> productList;

	/**
	 * 
	 * <pre>
	 * 공통 Response.
	 * </pre>
	 * 
	 * @return CommonResponse
	 */
	public CommonResponse getCommonRes() {
		return this.commonRes;
	}

	/**
	 * 
	 * <pre>
	 * 공통 Response.
	 * </pre>
	 * 
	 * @param commonRes
	 *            CommonResponse
	 */
	public void setCommonRes(CommonResponse commonRes) {
		this.commonRes = commonRes;
	}

	/**
	 * 
	 * <pre>
	 * 상품 리스트.
	 * </pre>
	 * 
	 * @return List<Product>
	 */
	public List<Product> getProductList() {
		return this.productList;
	}

	/**
	 * 
	 * <pre>
	 * 상품 리스트.
	 * </pre>
	 * 
	 * @param productList
	 *            List<Product>
	 */
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

}
