/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.related;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Contributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

/**
 * 특정 아티스트별 상품 조회 Value Object.
 * 
 * Updated on : 2014. 03. 03 Updated by : 유시혁.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ArtistProductSacRes extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 11123123142L;

	private CommonResponse commonResponse;

	private Contributor contributor;

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
	 *            CommonResponse
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
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

	/**
	 * 
	 * <pre>
	 * contributor.
	 * </pre>
	 * 
	 * @return Contributor
	 */
	public Contributor getContributor() {
		return this.contributor;
	}

	/**
	 * 
	 * <pre>
	 * contributor.
	 * </pre>
	 * 
	 * @param contributor
	 *            Contributor
	 */
	public void setContributor(Contributor contributor) {
		this.contributor = contributor;
	}

}
