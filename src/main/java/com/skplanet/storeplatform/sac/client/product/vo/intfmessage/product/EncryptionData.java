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

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * EncryptionData Value Object.
 * 
 * Updated on : 2014. 02. 11. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class EncryptionData extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String title;
	private String topCatCd;
	private String catCd;
	private String packetFee;
	private String productFee;
	private String productId;
	private String purchaseId;
	private Integer purchasePrice;
	private String purchaseDate;
	private List<EncryptionSubContents> subContents;
	private EncryptionUsagePolicy usagePolicy;
	private String userKey;
	private EncryptionDeviceKey deviceKey;
	private String type;
	private String deltaPath;

	/**
	 * @return the title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the topCatCd
	 */
	public String getTopCatCd() {
		return this.topCatCd;
	}

	/**
	 * @param topCatCd
	 *            the topCatCd to set
	 */
	public void setTopCatCd(String topCatCd) {
		this.topCatCd = topCatCd;
	}

	/**
	 * @return the catCd
	 */
	public String getCatCd() {
		return this.catCd;
	}

	/**
	 * @param catCd
	 *            the catCd to set
	 */
	public void setCatCd(String catCd) {
		this.catCd = catCd;
	}

	/**
	 * @return the packetFee
	 */
	public String getPacketFee() {
		return this.packetFee;
	}

	/**
	 * @param packetFee
	 *            the packetFee to set
	 */
	public void setPacketFee(String packetFee) {
		this.packetFee = packetFee;
	}

	/**
	 * @return the productFee
	 */
	public String getProductFee() {
		return this.productFee;
	}

	/**
	 * @param productFee
	 *            the productFee to set
	 */
	public void setProductFee(String productFee) {
		this.productFee = productFee;
	}

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
	 * @return the purchaseId
	 */
	public String getPurchaseId() {
		return this.purchaseId;
	}

	/**
	 * @param purchaseId
	 *            the purchaseId to set
	 */
	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}

	/**
	 * @return the purchasePrice
	 */
	public Integer getPurchasePrice() {
		return this.purchasePrice;
	}

	/**
	 * @param purchasePrice
	 *            the purchasePrice to set
	 */
	public void setPurchasePrice(Integer purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	/**
	 * @return the purchaseDate
	 */
	public String getPurchaseDate() {
		return this.purchaseDate;
	}

	/**
	 * @param purchaseDate
	 *            the purchaseDate to set
	 */
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	/**
	 * @return the subContents
	 */
	public List<EncryptionSubContents> getSubContents() {
		return this.subContents;
	}

	/**
	 * @param subContents
	 *            the subContents to set
	 */
	public void setSubContents(List<EncryptionSubContents> subContents) {
		this.subContents = subContents;
	}

	/**
	 * @return the usagePolicy
	 */
	public EncryptionUsagePolicy getUsagePolicy() {
		return this.usagePolicy;
	}

	/**
	 * @param usagePolicy
	 *            the usagePolicy to set
	 */
	public void setUsagePolicy(EncryptionUsagePolicy usagePolicy) {
		this.usagePolicy = usagePolicy;
	}

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the deviceKey
	 */
	public EncryptionDeviceKey getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            the deviceKey to set
	 */
	public void setDeviceKey(EncryptionDeviceKey deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the deltaPath
	 */
	public String getDeltaPath() {
		return this.deltaPath;
	}

	/**
	 * @param deltaPath
	 *            the deltaPath to set
	 */
	public void setDeltaPath(String deltaPath) {
		this.deltaPath = deltaPath;
	}

}
