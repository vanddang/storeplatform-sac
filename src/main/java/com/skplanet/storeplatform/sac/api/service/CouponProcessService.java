package com.skplanet.storeplatform.sac.api.service;

import java.util.List;

import com.skplanet.storeplatform.external.client.shopping.vo.CouponReq;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponRes;
import com.skplanet.storeplatform.sac.api.vo.CouponContainer;

public interface CouponProcessService {

	/**
	 * <pre>
	 * 쿠폰 정보 입력
	 * </pre>
	 */
	public boolean insertCouponInfo(CouponContainer containers, CouponReq couponReq);

	/**
	 * <pre>
	 * 상태 변경
	 * </pre>
	 */
	public boolean updateForCouponStatus(CouponReq couponReq);

	/**
	 * <pre>
	 * 특가 상품 목록 조회 한다.
	 * </pre>
	 */
	public List<CouponRes> getSpecialProductList(String[] couponCodes);

	/**
	 * <pre>
	 * 특가 상품 상세 조회 한다.
	 * </pre>
	 */
	public CouponRes getSpecialProductDetail(String couponCode);

	/**
	 * <pre>
	 * 쿠폰(아이템) Batch 처리 호출 메소드 (수동 호출용)
	 * </pre>
	 */
	public void CouponStateUpdateStart(String nowTime);

}
