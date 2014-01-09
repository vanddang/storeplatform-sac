package com.skplanet.storeplatform.sac.api.service;

import java.util.List;

import com.skplanet.storeplatform.sac.api.vo.CouponContainer;
import com.skplanet.storeplatform.sac.api.vo.CouponParameterInfo;
import com.skplanet.storeplatform.sac.api.vo.CouponResponseInfo;

public interface CouponProcessService {

	/**
	 * <pre>
	 * 쿠폰 정보 입력
	 * </pre>
	 */
	public boolean insertCouponInfo(CouponContainer containers, CouponParameterInfo couponParameterInfo);

	/**
	 * <pre>
	 * 상태 변경
	 * </pre>
	 */
	public boolean updateForCouponStatus(CouponParameterInfo couponParameterInfo);

	/**
	 * <pre>
	 * 특가 상품 목록 조회 한다.
	 * </pre>
	 */
	public List<CouponResponseInfo> getSpecialProductList(String[] couponCodes);

	/**
	 * <pre>
	 * 특가 상품 상세 조회 한다.
	 * </pre>
	 */
	public CouponResponseInfo getSpecialProductDetail(String couponCode);

}
