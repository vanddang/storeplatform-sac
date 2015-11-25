package com.skplanet.storeplatform.sac.client.display.vo.category;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Coupon;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CategoryVoucherSacRes extends CommonInfo {
	private static final long serialVersionUID = 1L;
	private CommonResponse commonResponse;
	private Coupon coupon;

	/**
	 * @return the commonResponse
	 */
	public CommonResponse getCommonResponse() {
		return commonResponse;
	}

	/**
	 * @param commonResponse the commonResponse to set
	 */
	public void setCommonResponse(CommonResponse commonResponse) {
		this.commonResponse = commonResponse;
	}

	/**
	 * @return the coupon
	 */
	public Coupon getCoupon() {
		return coupon;
	}

	/**
	 * @param coupon the coupon to set
	 */
	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}
}
