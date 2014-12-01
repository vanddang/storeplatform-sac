/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.display.cache.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Class 설명
 *
 * Updated on : 2014. 10. 8.
 * Updated by : 양해엽, SK 플래닛.
 */
public class CardInfo extends CommonInfo {

	private static final long serialVersionUID = -4099047932090965173L;

	/**
	 * CARD
	 */
	private String tenantId;
	private String cardId;
	private String cardTypeCd;
	private String cardTitle;
	private String cardDesc;
	private String cardLayOut;
	private String lndTitle;
	private String lndDesc;
	private String lndLayOut;
	private String datasetId;
	private String cardInjtVar;
	private String segmTypeCd;
	private String cardImgPath;
	private String cardImgRatio;
	private String cardImgType;
	private String lndImgPath;
	private String lndImgRatio;
	private String menuId;
	private String smartOfrListId;
	private String shareUrl;

	/**
	 * CARD_ETC_ATTR
	 */
	private String expoYnCardTitle;
	private String expoYnCardDesc;
	private String expoYnCardLike;
	private String expoYnCardShar;
	private String expoYnCardLnd;
	private String expoYnCardDcPrvPrice;
	private String expoYnCardDcRate;
	private String expoYnCardImg;
	private String expoYnCardItemNo;
	private String expoYnCardRecomReason;
	private String expoYnCardAbstrTm;
	private String expoYnLndTitle;
	private String expoYnLndDesc;
	private String expoYnLndLayout;
	private String expoYnLndItemNo;
	private String expoYnLndDcPrvPrice;
	private String expoYnLndDcRate;
	private String expoYnLndImg;
	private String expoYnLndImgOverlay;

	/**
	 * DATASET_FRAME
	 */
	private String datasetGrpCd;
	private String title;
	private String datasetDesc;
	private String tenantUrl;
	private String datasetInjtVar;
	private String injtVarKeyAdmin;
	private String itemLndUrl;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCardTypeCd() {
		return cardTypeCd;
	}

	public void setCardTypeCd(String cardTypeCd) {
		this.cardTypeCd = cardTypeCd;
	}

	public String getCardTitle() {
		return cardTitle;
	}

	public void setCardTitle(String cardTitle) {
		this.cardTitle = cardTitle;
	}

	public String getCardDesc() {
		return cardDesc;
	}

	public void setCardDesc(String cardDesc) {
		this.cardDesc = cardDesc;
	}

	public String getCardLayOut() {
		return cardLayOut;
	}

	public void setCardLayOut(String cardLayOut) {
		this.cardLayOut = cardLayOut;
	}

	public String getLndTitle() {
		return lndTitle;
	}

	public void setLndTitle(String lndTitle) {
		this.lndTitle = lndTitle;
	}

	public String getLndDesc() {
		return lndDesc;
	}

	public void setLndDesc(String lndDesc) {
		this.lndDesc = lndDesc;
	}

	public String getLndLayOut() {
		return lndLayOut;
	}

	public void setLndLayOut(String lndLayOut) {
		this.lndLayOut = lndLayOut;
	}

	public String getDatasetId() {
		return datasetId;
	}

	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}

	public String getCardInjtVar() {
		return cardInjtVar;
	}

	public void setCardInjtVar(String cardInjtVar) {
		this.cardInjtVar = cardInjtVar;
	}

	public String getSegmTypeCd() {
		return segmTypeCd;
	}

	public void setSegmTypeCd(String segmTypeCd) {
		this.segmTypeCd = segmTypeCd;
	}

	public String getCardImgPath() {
		return cardImgPath;
	}

	public void setCardImgPath(String cardImgPath) {
		this.cardImgPath = cardImgPath;
	}

	public String getCardImgRatio() {
		return cardImgRatio;
	}

	public void setCardImgRatio(String cardImgRatio) {
		this.cardImgRatio = cardImgRatio;
	}

	public String getCardImgType() {
		return cardImgType;
	}

	public void setCardImgType(String cardImgType) {
		this.cardImgType = cardImgType;
	}

	public String getLndImgPath() {
		return lndImgPath;
	}

	public void setLndImgPath(String lndImgPath) {
		this.lndImgPath = lndImgPath;
	}

	public String getLndImgRatio() {
		return lndImgRatio;
	}

	public void setLndImgRatio(String lndImgRatio) {
		this.lndImgRatio = lndImgRatio;
	}


	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getSmartOfrListId() {
		return smartOfrListId;
	}

	public void setSmartOfrListId(String smartOfrListId) {
		this.smartOfrListId = smartOfrListId;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public String getExpoYnCardTitle() {
		return expoYnCardTitle;
	}

	public void setExpoYnCardTitle(String expoYnCardTitle) {
		this.expoYnCardTitle = expoYnCardTitle;
	}

	public String getExpoYnCardDesc() {
		return expoYnCardDesc;
	}

	public void setExpoYnCardDesc(String expoYnCardDesc) {
		this.expoYnCardDesc = expoYnCardDesc;
	}

	public String getExpoYnCardLike() {
		return expoYnCardLike;
	}

	public void setExpoYnCardLike(String expoYnCardLike) {
		this.expoYnCardLike = expoYnCardLike;
	}

	public String getExpoYnCardShar() {
		return expoYnCardShar;
	}

	public void setExpoYnCardShar(String expoYnCardShar) {
		this.expoYnCardShar = expoYnCardShar;
	}

	public String getExpoYnCardLnd() {
		return expoYnCardLnd;
	}

	public void setExpoYnCardLnd(String expoYnCardLnd) {
		this.expoYnCardLnd = expoYnCardLnd;
	}

	public String getExpoYnCardDcPrvPrice() {
		return expoYnCardDcPrvPrice;
	}

	public void setExpoYnCardDcPrvPrice(String expoYnCardDcPrvPrice) {
		this.expoYnCardDcPrvPrice = expoYnCardDcPrvPrice;
	}

	public String getExpoYnCardDcRate() {
		return expoYnCardDcRate;
	}

	public void setExpoYnCardDcRate(String expoYnCardDcRate) {
		this.expoYnCardDcRate = expoYnCardDcRate;
	}

	public String getExpoYnCardImg() {
		return expoYnCardImg;
	}

	public void setExpoYnCardImg(String expoYnCardImg) {
		this.expoYnCardImg = expoYnCardImg;
	}

	public String getExpoYnCardItemNo() {
		return expoYnCardItemNo;
	}

	public void setExpoYnCardItemNo(String expoYnCardItemNo) {
		this.expoYnCardItemNo = expoYnCardItemNo;
	}

	public String getExpoYnCardRecomReason() {
		return expoYnCardRecomReason;
	}

	public void setExpoYnCardRecomReason(String expoYnCardRecomReason) {
		this.expoYnCardRecomReason = expoYnCardRecomReason;
	}

	public String getExpoYnCardAbstrTm() {
		return expoYnCardAbstrTm;
	}

	public void setExpoYnCardAbstrTm(String expoYnCardAbstrTm) {
		this.expoYnCardAbstrTm = expoYnCardAbstrTm;
	}

	public String getExpoYnLndTitle() {
		return expoYnLndTitle;
	}

	public void setExpoYnLndTitle(String expoYnLndTitle) {
		this.expoYnLndTitle = expoYnLndTitle;
	}

	public String getExpoYnLndDesc() {
		return expoYnLndDesc;
	}

	public void setExpoYnLndDesc(String expoYnLndDesc) {
		this.expoYnLndDesc = expoYnLndDesc;
	}

	public String getExpoYnLndLayout() {
		return expoYnLndLayout;
	}

	public void setExpoYnLndLayout(String expoYnLndLayout) {
		this.expoYnLndLayout = expoYnLndLayout;
	}

	public String getExpoYnLndItemNo() {
		return expoYnLndItemNo;
	}

	public void setExpoYnLndItemNo(String expoYnLndItemNo) {
		this.expoYnLndItemNo = expoYnLndItemNo;
	}

	public String getExpoYnLndDcPrvPrice() {
		return expoYnLndDcPrvPrice;
	}

	public void setExpoYnLndDcPrvPrice(String expoYnLndDcPrvPrice) {
		this.expoYnLndDcPrvPrice = expoYnLndDcPrvPrice;
	}

	public String getExpoYnLndDcRate() {
		return expoYnLndDcRate;
	}

	public void setExpoYnLndDcRate(String expoYnLndDcRate) {
		this.expoYnLndDcRate = expoYnLndDcRate;
	}

	public String getExpoYnLndImg() {
		return expoYnLndImg;
	}

	public void setExpoYnLndImg(String expoYnLndImg) {
		this.expoYnLndImg = expoYnLndImg;
	}

	public String getExpoYnLndImgOverlay() {
		return expoYnLndImgOverlay;
	}

	public void setExpoYnLndImgOverlay(String expoYnLndImgOverlay) {
		this.expoYnLndImgOverlay = expoYnLndImgOverlay;
	}

	public String getDatasetGrpCd() {
		return datasetGrpCd;
	}

	public void setDatasetGrpCd(String datasetGrpCd) {
		this.datasetGrpCd = datasetGrpCd;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDatasetDesc() {
		return datasetDesc;
	}

	public void setDatasetDesc(String datasetDesc) {
		this.datasetDesc = datasetDesc;
	}

	public String getTenantUrl() {
		return tenantUrl;
	}

	public void setTenantUrl(String tenantUrl) {
		this.tenantUrl = tenantUrl;
	}

	public String getDatasetInjtVar() {
		return datasetInjtVar;
	}

	public void setDatasetInjtVar(String datasetInjtVar) {
		this.datasetInjtVar = datasetInjtVar;
	}

	public String getInjtVarKeyAdmin() {
		return injtVarKeyAdmin;
	}

	public void setInjtVarKeyAdmin(String injtVarKeyAdmin) {
		this.injtVarKeyAdmin = injtVarKeyAdmin;
	}

	public String getItemLndUrl() {
		return itemLndUrl;
	}

	public void setItemLndUrl(String itemLndUrl) {
		this.itemLndUrl = itemLndUrl;
	}

}
