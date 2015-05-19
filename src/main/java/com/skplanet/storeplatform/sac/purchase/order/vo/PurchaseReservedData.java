/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.purchase.order.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 구매 예약 시 저장해 둔 정보 VO
 * 
 * Updated on : 2015. 1. 20. Updated by : 이승택, nTels.
 */
public class PurchaseReservedData extends CommonInfo {
	private static final long serialVersionUID = 1L;

	private String apiVer;
	private String systemId;
	private String userId;
	private String deviceId;
	private String marketDeviceKey;
	private String deviceModelCd;
	private String telecom;
	private String imei;
	private String oneId;
	private String networkTypeCd;
	private String mediaId;
	private String dupleYn;
	private String tMileageRateInfo;
	private String useDeviceId;
	private String useDeviceModelCd;
	private String aid;
	private String couponCode;
	private String itemCode;
	private String bonusPoint;
	private String bonusPointUsePeriodUnitCd;
	private String bonusPointUsePeriod;
	private String bonusPointUsableDayCnt;
	private String autoPrchsPeriodUnitCd;
	private String autoPrchsPeriodValue;
	private String afterAutoPayDt;
	private String sellerMbrNo;
	private String mallCd;
	private String outsdContentsId;
	private String autoPrchsYn;
	private String autoLastPeriod;
	private String specialCouponId;
	private String specialCouponAmt;
	private String possLendClsfCd;
	private String cmpxProdClsfCd;
	private String cmpxProdBookClsfCd;
	private String prodCaseCd;
	private String s2sAutoYn;
	private String s2sYn;
	private String dwldAvailableDayCnt;
	private String usePeriodCnt;
	private String iapYn;
	private String iapPostbackUrl;
	private String iapProdKind;
	private String iapProdCase;
	private String iapUsePeriod;
	private String packagePrchsYn;

	/**
	 * @return the apiVer
	 */
	public String getApiVer() {
		return this.apiVer;
	}

	/**
	 * @param apiVer
	 *            the apiVer to set
	 */
	public void setApiVer(String apiVer) {
		this.apiVer = apiVer;
	}

	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return this.systemId;
	}

	/**
	 * @param systemId
	 *            the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return this.deviceId;
	}

	/**
	 * @param deviceId
	 *            the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the marketDeviceKey
	 */
	public String getMarketDeviceKey() {
		return this.marketDeviceKey;
	}

	/**
	 * @param marketDeviceKey
	 *            the marketDeviceKey to set
	 */
	public void setMarketDeviceKey(String marketDeviceKey) {
		this.marketDeviceKey = marketDeviceKey;
	}

	/**
	 * @return the deviceModelCd
	 */
	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}

	/**
	 * @param deviceModelCd
	 *            the deviceModelCd to set
	 */
	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

	/**
	 * @return the telecom
	 */
	public String getTelecom() {
		return this.telecom;
	}

	/**
	 * @param telecom
	 *            the telecom to set
	 */
	public void setTelecom(String telecom) {
		this.telecom = telecom;
	}

	/**
	 * @return the imei
	 */
	public String getImei() {
		return this.imei;
	}

	/**
	 * @param imei
	 *            the imei to set
	 */
	public void setImei(String imei) {
		this.imei = imei;
	}

	/**
	 * @return the oneId
	 */
	public String getOneId() {
		return this.oneId;
	}

	/**
	 * @param oneId
	 *            the oneId to set
	 */
	public void setOneId(String oneId) {
		this.oneId = oneId;
	}

	/**
	 * @return the networkTypeCd
	 */
	public String getNetworkTypeCd() {
		return this.networkTypeCd;
	}

	/**
	 * @param networkTypeCd
	 *            the networkTypeCd to set
	 */
	public void setNetworkTypeCd(String networkTypeCd) {
		this.networkTypeCd = networkTypeCd;
	}

	/**
	 * @return the mediaId
	 */
	public String getMediaId() {
		return this.mediaId;
	}

	/**
	 * @param mediaId
	 *            the mediaId to set
	 */
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	/**
	 * @return the dupleYn
	 */
	public String getDupleYn() {
		return this.dupleYn;
	}

	/**
	 * @param dupleYn
	 *            the dupleYn to set
	 */
	public void setDupleYn(String dupleYn) {
		this.dupleYn = dupleYn;
	}

	/**
	 * @return the tMileageRateInfo
	 */
	public String gettMileageRateInfo() {
		return this.tMileageRateInfo;
	}

	/**
	 * @param tMileageRateInfo
	 *            the tMileageRateInfo to set
	 */
	public void settMileageRateInfo(String tMileageRateInfo) {
		this.tMileageRateInfo = tMileageRateInfo;
	}

	/**
	 * @return the useDeviceId
	 */
	public String getUseDeviceId() {
		return this.useDeviceId;
	}

	/**
	 * @param useDeviceId
	 *            the useDeviceId to set
	 */
	public void setUseDeviceId(String useDeviceId) {
		this.useDeviceId = useDeviceId;
	}

	/**
	 * @return the useDeviceModelCd
	 */
	public String getUseDeviceModelCd() {
		return this.useDeviceModelCd;
	}

	/**
	 * @param useDeviceModelCd
	 *            the useDeviceModelCd to set
	 */
	public void setUseDeviceModelCd(String useDeviceModelCd) {
		this.useDeviceModelCd = useDeviceModelCd;
	}

	/**
	 * @return the aid
	 */
	public String getAid() {
		return this.aid;
	}

	/**
	 * @param aid
	 *            the aid to set
	 */
	public void setAid(String aid) {
		this.aid = aid;
	}

	/**
	 * @return the couponCode
	 */
	public String getCouponCode() {
		return this.couponCode;
	}

	/**
	 * @param couponCode
	 *            the couponCode to set
	 */
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * @param itemCode
	 *            the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * @return the bonusPoint
	 */
	public String getBonusPoint() {
		return this.bonusPoint;
	}

	/**
	 * @param bonusPoint
	 *            the bonusPoint to set
	 */
	public void setBonusPoint(String bonusPoint) {
		this.bonusPoint = bonusPoint;
	}

	/**
	 * @return the bonusPointUsePeriodUnitCd
	 */
	public String getBonusPointUsePeriodUnitCd() {
		return this.bonusPointUsePeriodUnitCd;
	}

	/**
	 * @param bonusPointUsePeriodUnitCd
	 *            the bonusPointUsePeriodUnitCd to set
	 */
	public void setBonusPointUsePeriodUnitCd(String bonusPointUsePeriodUnitCd) {
		this.bonusPointUsePeriodUnitCd = bonusPointUsePeriodUnitCd;
	}

	/**
	 * @return the bonusPointUsePeriod
	 */
	public String getBonusPointUsePeriod() {
		return this.bonusPointUsePeriod;
	}

	/**
	 * @param bonusPointUsePeriod
	 *            the bonusPointUsePeriod to set
	 */
	public void setBonusPointUsePeriod(String bonusPointUsePeriod) {
		this.bonusPointUsePeriod = bonusPointUsePeriod;
	}

	/**
	 * @return the bonusPointUsableDayCnt
	 */
	public String getBonusPointUsableDayCnt() {
		return this.bonusPointUsableDayCnt;
	}

	/**
	 * @param bonusPointUsableDayCnt
	 *            the bonusPointUsableDayCnt to set
	 */
	public void setBonusPointUsableDayCnt(String bonusPointUsableDayCnt) {
		this.bonusPointUsableDayCnt = bonusPointUsableDayCnt;
	}

	/**
	 * @return the autoPrchsPeriodUnitCd
	 */
	public String getAutoPrchsPeriodUnitCd() {
		return this.autoPrchsPeriodUnitCd;
	}

	/**
	 * @param autoPrchsPeriodUnitCd
	 *            the autoPrchsPeriodUnitCd to set
	 */
	public void setAutoPrchsPeriodUnitCd(String autoPrchsPeriodUnitCd) {
		this.autoPrchsPeriodUnitCd = autoPrchsPeriodUnitCd;
	}

	/**
	 * @return the autoPrchsPeriodValue
	 */
	public String getAutoPrchsPeriodValue() {
		return this.autoPrchsPeriodValue;
	}

	/**
	 * @param autoPrchsPeriodValue
	 *            the autoPrchsPeriodValue to set
	 */
	public void setAutoPrchsPeriodValue(String autoPrchsPeriodValue) {
		this.autoPrchsPeriodValue = autoPrchsPeriodValue;
	}

	/**
	 * @return the afterAutoPayDt
	 */
	public String getAfterAutoPayDt() {
		return this.afterAutoPayDt;
	}

	/**
	 * @param afterAutoPayDt
	 *            the afterAutoPayDt to set
	 */
	public void setAfterAutoPayDt(String afterAutoPayDt) {
		this.afterAutoPayDt = afterAutoPayDt;
	}

	/**
	 * @return the sellerMbrNo
	 */
	public String getSellerMbrNo() {
		return this.sellerMbrNo;
	}

	/**
	 * @param sellerMbrNo
	 *            the sellerMbrNo to set
	 */
	public void setSellerMbrNo(String sellerMbrNo) {
		this.sellerMbrNo = sellerMbrNo;
	}

	/**
	 * @return the mallCd
	 */
	public String getMallCd() {
		return this.mallCd;
	}

	/**
	 * @param mallCd
	 *            the mallCd to set
	 */
	public void setMallCd(String mallCd) {
		this.mallCd = mallCd;
	}

	/**
	 * @return the outsdContentsId
	 */
	public String getOutsdContentsId() {
		return this.outsdContentsId;
	}

	/**
	 * @param outsdContentsId
	 *            the outsdContentsId to set
	 */
	public void setOutsdContentsId(String outsdContentsId) {
		this.outsdContentsId = outsdContentsId;
	}

	/**
	 * @return the autoPrchsYn
	 */
	public String getAutoPrchsYn() {
		return this.autoPrchsYn;
	}

	/**
	 * @param autoPrchsYn
	 *            the autoPrchsYn to set
	 */
	public void setAutoPrchsYn(String autoPrchsYn) {
		this.autoPrchsYn = autoPrchsYn;
	}

	/**
	 * @return the autoLastPeriod
	 */
	public String getAutoLastPeriod() {
		return this.autoLastPeriod;
	}

	/**
	 * @param autoLastPeriod
	 *            the autoLastPeriod to set
	 */
	public void setAutoLastPeriod(String autoLastPeriod) {
		this.autoLastPeriod = autoLastPeriod;
	}

	/**
	 * @return the specialCouponId
	 */
	public String getSpecialCouponId() {
		return this.specialCouponId;
	}

	/**
	 * @param specialCouponId
	 *            the specialCouponId to set
	 */
	public void setSpecialCouponId(String specialCouponId) {
		this.specialCouponId = specialCouponId;
	}

	/**
	 * @return the specialCouponAmt
	 */
	public String getSpecialCouponAmt() {
		return this.specialCouponAmt;
	}

	/**
	 * @param specialCouponAmt
	 *            the specialCouponAmt to set
	 */
	public void setSpecialCouponAmt(String specialCouponAmt) {
		this.specialCouponAmt = specialCouponAmt;
	}

	/**
	 * @return the possLendClsfCd
	 */
	public String getPossLendClsfCd() {
		return this.possLendClsfCd;
	}

	/**
	 * @param possLendClsfCd
	 *            the possLendClsfCd to set
	 */
	public void setPossLendClsfCd(String possLendClsfCd) {
		this.possLendClsfCd = possLendClsfCd;
	}

	/**
	 * @return the cmpxProdClsfCd
	 */
	public String getCmpxProdClsfCd() {
		return this.cmpxProdClsfCd;
	}

	/**
	 * @param cmpxProdClsfCd
	 *            the cmpxProdClsfCd to set
	 */
	public void setCmpxProdClsfCd(String cmpxProdClsfCd) {
		this.cmpxProdClsfCd = cmpxProdClsfCd;
	}

	/**
	 * @return the cmpxProdBookClsfCd
	 */
	public String getCmpxProdBookClsfCd() {
		return this.cmpxProdBookClsfCd;
	}

	/**
	 * @param cmpxProdBookClsfCd
	 *            the cmpxProdBookClsfCd to set
	 */
	public void setCmpxProdBookClsfCd(String cmpxProdBookClsfCd) {
		this.cmpxProdBookClsfCd = cmpxProdBookClsfCd;
	}

	/**
	 * @return the prodCaseCd
	 */
	public String getProdCaseCd() {
		return this.prodCaseCd;
	}

	/**
	 * @param prodCaseCd
	 *            the prodCaseCd to set
	 */
	public void setProdCaseCd(String prodCaseCd) {
		this.prodCaseCd = prodCaseCd;
	}

	/**
	 * @return the s2sAutoYn
	 */
	public String getS2sAutoYn() {
		return this.s2sAutoYn;
	}

	/**
	 * @param s2sAutoYn
	 *            the s2sAutoYn to set
	 */
	public void setS2sAutoYn(String s2sAutoYn) {
		this.s2sAutoYn = s2sAutoYn;
	}

	/**
	 * @return the s2sYn
	 */
	public String getS2sYn() {
		return this.s2sYn;
	}

	/**
	 * @param s2sYn
	 *            the s2sYn to set
	 */
	public void setS2sYn(String s2sYn) {
		this.s2sYn = s2sYn;
	}

	/**
	 * @return the dwldAvailableDayCnt
	 */
	public String getDwldAvailableDayCnt() {
		return this.dwldAvailableDayCnt;
	}

	/**
	 * @param dwldAvailableDayCnt
	 *            the dwldAvailableDayCnt to set
	 */
	public void setDwldAvailableDayCnt(String dwldAvailableDayCnt) {
		this.dwldAvailableDayCnt = dwldAvailableDayCnt;
	}

	/**
	 * @return the usePeriodCnt
	 */
	public String getUsePeriodCnt() {
		return this.usePeriodCnt;
	}

	/**
	 * @param usePeriodCnt
	 *            the usePeriodCnt to set
	 */
	public void setUsePeriodCnt(String usePeriodCnt) {
		this.usePeriodCnt = usePeriodCnt;
	}

	/**
	 * @return the iapYn
	 */
	public String getIapYn() {
		return this.iapYn;
	}

	/**
	 * @param iapYn
	 *            the iapYn to set
	 */
	public void setIapYn(String iapYn) {
		this.iapYn = iapYn;
	}

	/**
	 * @return the iapPostbackUrl
	 */
	public String getIapPostbackUrl() {
		return this.iapPostbackUrl;
	}

	/**
	 * @param iapPostbackUrl
	 *            the iapPostbackUrl to set
	 */
	public void setIapPostbackUrl(String iapPostbackUrl) {
		this.iapPostbackUrl = iapPostbackUrl;
	}

	/**
	 * @return the iapProdKind
	 */
	public String getIapProdKind() {
		return this.iapProdKind;
	}

	/**
	 * @param iapProdKind
	 *            the iapProdKind to set
	 */
	public void setIapProdKind(String iapProdKind) {
		this.iapProdKind = iapProdKind;
	}

	/**
	 * @return the iapProdCase
	 */
	public String getIapProdCase() {
		return this.iapProdCase;
	}

	/**
	 * @param iapProdCase
	 *            the iapProdCase to set
	 */
	public void setIapProdCase(String iapProdCase) {
		this.iapProdCase = iapProdCase;
	}

	/**
	 * @return the iapUsePeriod
	 */
	public String getIapUsePeriod() {
		return this.iapUsePeriod;
	}

	/**
	 * @param iapUsePeriod
	 *            the iapUsePeriod to set
	 */
	public void setIapUsePeriod(String iapUsePeriod) {
		this.iapUsePeriod = iapUsePeriod;
	}

	/**
	 * @return the packagePrchsYn
	 */
	public String getPackagePrchsYn() {
		return this.packagePrchsYn;
	}

	/**
	 * @param packagePrchsYn
	 *            the packagePrchsYn to set
	 */
	public void setPackagePrchsYn(String packagePrchsYn) {
		this.packagePrchsYn = packagePrchsYn;
	}

}
