/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <p>
 * EtcProp
 * </p>
 * Updated on : 2014. 10. 08 Updated by : 정희원, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class EtcProp extends CommonInfo {
    private static final long serialVersionUID = 1L;

    /**
     * 카드 타이틀 노출 여부
     */
    private String expoYnCardTitle;

    /**
     * 카드 설명 노출 여부
     */
    private String expoYnCardDesc;

    /**
     * 카드 좋아요 노출 여부
     */
    private String expoYnCardLike;

    /**
     * 카드 공유 노출 여부
     */
    private String expoYnCardShar;

    /**
     * 카드 할인전 가격 노출여부
     */
    private String expoYnCardDcPrvPrice;

    /**
     * 카드 할인율 노출 여부
     */
    private String expoYnCardDcRate;

    /**
     * 카드 배경이미지 노출 여부
     */
    private String expoYnCardImg;

    /**
     * 카드 아이템번호 노출 여부
     */
    private String expoYnCardItemNo;

    /**
     * 카드 추천사유 노출 여부
     */
    private String expoYnCardRecomReason;

    /**
     * 카드 추출시간 노출 여부
     */
    private String expoYnCardAbstrTm;

    /**
     * 랜딩 타이틀 노출 여부
     */
    private String expoYnLndTitle;

    /**
     * 랜딩 설명 노출 여부
     */
    private String expoYnLndDesc;

    /**
     * 랜딩 레이아웃 노출 여부
     */
    private String expoYnLndLayout;

    /**
     * 랜딩 아이템번호 노출여부
     */
    private String expoYnLndItemNo;

    /**
     * 랜딩 할인전 가격 노출여부
     */
    private String expoYnLndDcPrvPrice;

    /**
     * 랜딩 할인율 노출여부
     */
    private String expoYnLndDcRate;

    /**
     * 랜딩 배경이미지 노출여부
     */
    private String expoYnLndImg;

    /**
     * 랜딩 배경이미지 오버레이 여부
     */
    private String expoYnLndImgOverlay;

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

    public String getExpoYnLndLayout() {
        return expoYnLndLayout;
    }

    public void setExpoYnLndLayout(String expoYnLndLayout) {
        this.expoYnLndLayout = expoYnLndLayout;
    }
}
