package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 상품권 충전 정보
 * 
 * Updated on : 2015. 11. 11. Updated by : 반범진.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class GiftChargeInfoSac extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 제휴사 브랜드명.
	 */
	private String brandName;

	/**
	 * 충전자 아이디.
	 */
	private String chargerId;

	/**
	 * 충전자 이름.
	 */
	private String chargerName;

	/**
	 * 등록 일자.
	 */
	private String regDate;

	/**
	 * 업데이트 일자.
	 */
	private String updateDate;

	/**
	 * 판매자 정보.
	 */
	private SellerMbrSac sellerInfo;

	/**
	 * @return brandName
	 */
	public String getBrandName() {
		return this.brandName;
	}

	/**
	 * @param brandName
	 *            String
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * @return chargerId
	 */
	public String getChargerId() {
		return this.chargerId;
	}

	/**
	 * @param chargerId
	 *            String
	 */
	public void setChargerId(String chargerId) {
		this.chargerId = chargerId;
	}

	/**
	 * @return chargerName
	 */
	public String getChargerName() {
		return this.chargerName;
	}

	/**
	 * @param chargerName
	 *            String
	 */
	public void setChargerName(String chargerName) {
		this.chargerName = chargerName;
	}

	/**
	 * @return regDate
	 */
	public String getRegDate() {
		return this.regDate;
	}

	/**
	 * @param regDate
	 *            String
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return updateDate
	 */
	public String getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * @param updateDate
	 *            String
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return sellerInfo
	 */
	public SellerMbrSac getSellerInfo() {
		return this.sellerInfo;
	}

	/**
	 * @param sellerInfo
	 *            SellerMbrSac
	 */
	public void setSellerInfo(SellerMbrSac sellerInfo) {
		this.sellerInfo = sellerInfo;
	}

}
