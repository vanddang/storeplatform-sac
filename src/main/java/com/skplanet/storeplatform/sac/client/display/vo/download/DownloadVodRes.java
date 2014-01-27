/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 import java.util.List;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
lose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.download;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

/**
 * BEST 앱 상품 조회 List Value Object.
 * 
 * Updated on : 2013. 12. 23. Updated by : 이석희, SK 플래닛.
 */
// @ProtobufMapping(BestAppProto.resBestApp.class)
public class DownloadVodRes extends CommonInfo {

	private static final long serialVersionUID = 1L;
	private CommonResponse commonResponse;
	private Product product;

	// private Component component;

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
	 * 상품 정보.
	 * </pre>
	 * 
	 * @return product
	 */
	public Product getProduct() {
		return this.product;
	}

	/**
	 * 
	 * <pre>
	 * 상품 정보.
	 * </pre>
	 * 
	 * @param product
	 *            product
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	// /**
	// *
	// * <pre>
	// * Seed 앱 정보.
	// * </pre>
	// *
	// * @return Component
	// */
	// public Component getComponent() {
	// return this.component;
	// }
	//
	// /**
	// *
	// * <pre>
	// * Seed 앱 정보.
	// * </pre>
	// *
	// * @param component
	// * component
	// */
	// public void setComponent(Component component) {
	// this.component = component;
	// }

	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
