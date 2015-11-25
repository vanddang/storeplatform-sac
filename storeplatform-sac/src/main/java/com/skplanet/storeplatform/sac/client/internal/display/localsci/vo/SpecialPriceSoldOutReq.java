package com.skplanet.storeplatform.sac.client.internal.display.localsci.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 쇼핑 특가 상품에 대한 품절 여부(Sold Out) 등록
 * 
 * Updated on : 2014. 12. 04. Updated by : 김형식
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SpecialPriceSoldOutReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String lang; // 언어코드
	private String tenantId; // 테넌트 Id
	private String productId; // 상품 ID

	/**
	 * @return the lang
	 */
	public String getLang() {
		return this.lang;
	}

	/**
	 * @param lang
	 *            the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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

}
