/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 import java.util.List;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
lose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.brandshop;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Layout;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

/**
 * 브랜드샵 조회 List Value Object.
 * 
 * Updated on : 2014. 2. 7. Updated by : 이승훈, 엔텔스.
 */
public class BrandshopSacRes extends CommonInfo {
	private static final long serialVersionUID = 1L;
	private CommonResponse commonResponse;
	private Layout layout;

	private List<Product> productList;

	/**
	 * 
	 * <pre>
	 * 공통 Response.
	 * </pre>
	 * 
	 * @return CommonResponse
	 */
	public CommonResponse getCommonResponse() {
		return this.commonResponse;
	}

	/**
	 * 
	 * <pre>
	 * 공통 Response.
	 * </pre>
	 * 
	 * @param commonResponse
	 *            commonResponse
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	/**
	 * 
	 * <pre>
	 * Layout.
	 * </pre>
	 * 
	 * @return Layout
	 */
	public Layout getLayout() {
		return this.layout;
	}

	/**
	 * 
	 * <pre>
	 * Layout.
	 * </pre>
	 * 
	 * @param layout
	 *            layout
	 */
	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	/**
	 * 
	 * <pre>
	 * 상품 List.
	 * </pre>
	 * 
	 * @return productList
	 */
	public List<Product> getProductList() {
		return this.productList;
	}

	/**
	 * 
	 * <pre>
	 * 상품 List.
	 * </pre>
	 * 
	 * @param productList
	 *            productList
	 */
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
