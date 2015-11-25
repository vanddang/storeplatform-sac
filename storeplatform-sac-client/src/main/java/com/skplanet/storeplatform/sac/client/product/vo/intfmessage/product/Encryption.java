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

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Encryption Value Object.
 * 
 * Updated on : 2014. 02. 11. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Encryption extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String productId;
	private String digest;
	private String keyIndex;
	private String token;

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return this.productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the digest
	 */
	public String getDigest() {
		return this.digest;
	}

	/**
	 * @param digest
	 *            the digest to set
	 */
	public void setDigest(String digest) {
		this.digest = digest;
	}

	/**
	 * @return the keyIndex
	 */
	public String getKeyIndex() {
		return this.keyIndex;
	}

	/**
	 * @param keyIndex
	 *            the keyIndex to set
	 */
	public void setKeyIndex(String keyIndex) {
		this.keyIndex = keyIndex;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return this.token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
}
