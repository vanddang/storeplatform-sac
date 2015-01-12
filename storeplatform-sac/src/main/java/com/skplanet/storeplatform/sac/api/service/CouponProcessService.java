package com.skplanet.storeplatform.sac.api.service;

import java.util.List;

import com.skplanet.storeplatform.external.client.shopping.vo.CouponReq;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponRes;

/**
 * <pre>
 * 쿠폰아이템 서비스 인터페이스
 * </pre>
 * 
 * Created on : 2014-01-03 Created by : 김형식, SK 플래닛. Last Updated on : 2014-01-03 Last Updated by : 김형식, SK 플래닛
 */
public interface CouponProcessService {

	/**
	 * <pre>
	 * 쿠폰 정보 입력.
	 * </pre>
	 * 
	 * @param couponReq
	 *            couponReq
	 * @return boolean
	 */
	public boolean insertCouponInfo(CouponReq couponReq);

	/**
	 * <pre>
	 * 상태 변경.
	 * </pre>
	 * 
	 * @param couponReq
	 *            couponReq
	 * @return boolean
	 */
	public boolean updateForCouponStatus(CouponReq couponReq);

	/**
	 * <pre>
	 * 특가 상품 목록 조회 한다.
	 * </pre>
	 * 
	 * @param couponCodes
	 *            couponCodes
	 * @return List<CouponRes>
	 */
	public List<CouponRes> getSpecialProductList(String[] couponCodes);

	/**
	 * <pre>
	 * 특가 상품 상세 조회 한다.
	 * </pre>
	 * 
	 * @param couponCode
	 *            couponCode
	 * @return CouponRes
	 */
	public CouponRes getSpecialProductDetail(String couponCode);

	/**
	 * <pre>
	 * 쿠폰(아이템) Batch 처리 호출 메소드 (수동 호출용).
	 * </pre>
	 * 
	 * @param nowTime
	 *            nowTime
	 * @return
	 */
	public void couponStateUpdateStart(String nowTime);

}
