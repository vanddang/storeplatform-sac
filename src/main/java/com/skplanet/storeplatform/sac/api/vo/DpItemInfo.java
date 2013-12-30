package com.skplanet.storeplatform.sac.api.vo;

/*****************************************************************************
 * SKT TStore Project ::: Refactoring SAC
 ***************************************************************************** 
 * 1.클래스 개요 : 2.작 성 자 : Kim Hyung Sik 3.작 성 일 자 : 2013. 12. 28.
 * 
 * <pre>
 * 4.수 정 일 자 :
 *      . <날짜> : <수정 내용> (성명)
 *      . 2013. 12. 28..  : 최초 생성 (Kim Hyung Sik)
 * @author Kim Hyung Sik
 * </pre>
 * 
 * @version 1.0
 *****************************************************************************/
public class DpItemInfo {

	String itemCode = ""; // 단품코드
	String storeLicenseCode = ""; // 스토어 라이선스 번호
	String itemName = ""; // 단품명
	String orgPrice = ""; // 정상가격
	String salePrice = ""; // 할인가격
	String itemPrice = ""; // 단품가격
	String dcRate = ""; // 할인율
	String maxCount = ""; // 판매개수
	String maxCountMonthly = ""; // 월간 상품 최대 판매 수량
	String maxCountDaily = ""; // 일간 상품 최대 판매 수량
	String maxCountMonthlyUser = ""; // 1인 당월 최대 구매 수량
	String maxCountDailyUser = ""; // 1인 당일 최대 구매 수량
	String buyMaxLimit = ""; // 최대결제수량
	String itemValue1 = ""; // 옵션1
	String itemValue2 = ""; // 옵션2
	String bpManageId = ""; // BP관리ID
	String shippingUrl = ""; // 배송지 정보 입력 URL
	String itemStatus = ""; // 단품상태
	String cudType = ""; // 추가수정플래그

	public String getItemCode() {
		return this.itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getStoreLicenseCode() {
		return this.storeLicenseCode;
	}

	public void setStoreLicenseCode(String storeLicenseCode) {
		this.storeLicenseCode = storeLicenseCode;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getOrgPrice() {
		return this.orgPrice;
	}

	public void setOrgPrice(String orgPrice) {
		this.orgPrice = orgPrice;
	}

	public String getSalePrice() {
		return this.salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public String getItemPrice() {
		return this.itemPrice;
	}

	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getDcRate() {
		return this.dcRate;
	}

	public void setDcRate(String dcRate) {
		this.dcRate = dcRate;
	}

	public String getMaxCount() {
		return this.maxCount;
	}

	public void setMaxCount(String maxCount) {
		this.maxCount = maxCount;
	}

	public String getMaxCountMonthly() {
		return this.maxCountMonthly;
	}

	public void setMaxCountMonthly(String maxCountMonthly) {
		this.maxCountMonthly = maxCountMonthly;
	}

	public String getMaxCountDaily() {
		return this.maxCountDaily;
	}

	public void setMaxCountDaily(String maxCountDaily) {
		this.maxCountDaily = maxCountDaily;
	}

	public String getMaxCountMonthlyUser() {
		return this.maxCountMonthlyUser;
	}

	public void setMaxCountMonthlyUser(String maxCountMonthlyUser) {
		this.maxCountMonthlyUser = maxCountMonthlyUser;
	}

	public String getMaxCountDailyUser() {
		return this.maxCountDailyUser;
	}

	public void setMaxCountDailyUser(String maxCountDailyUser) {
		this.maxCountDailyUser = maxCountDailyUser;
	}

	public String getBuyMaxLimit() {
		return this.buyMaxLimit;
	}

	public void setBuyMaxLimit(String buyMaxLimit) {
		this.buyMaxLimit = buyMaxLimit;
	}

	public String getItemValue1() {
		return this.itemValue1;
	}

	public void setItemValue1(String itemValue1) {
		this.itemValue1 = itemValue1;
	}

	public String getItemValue2() {
		return this.itemValue2;
	}

	public void setItemValue2(String itemValue2) {
		this.itemValue2 = itemValue2;
	}

	public String getBpManageId() {
		return this.bpManageId;
	}

	public void setBpManageId(String bpManageId) {
		this.bpManageId = bpManageId;
	}

	public String getShippingUrl() {
		return this.shippingUrl;
	}

	public void setShippingUrl(String shippingUrl) {
		this.shippingUrl = shippingUrl;
	}

	public String getCudType() {
		return this.cudType;
	}

	public void setCudType(String cudType) {
		this.cudType = cudType;
	}

	public String getItemStatus() {
		return this.itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

}
