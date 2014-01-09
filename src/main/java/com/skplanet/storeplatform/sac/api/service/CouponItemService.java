package com.skplanet.storeplatform.sac.api.service;

import java.util.List;

import com.skplanet.storeplatform.sac.api.vo.CouponResponseInfo;
import com.skplanet.storeplatform.sac.api.vo.TbDpProdInfo;

public interface CouponItemService {

	/**
	 * <pre>
	 * 쿠폰ID 생성
	 * </pre>
	 */
	public String couponGenerateId();

	/**
	 * <pre>
	 * 아이템ID 생성
	 * </pre>
	 */
	public String itemGenerateId();

	/**
	 * <pre>
	 * 상태값을 변경 한다.
	 * </pre>
	 */
	public void insertTbDpProdInfo(List<TbDpProdInfo> tblDpProdList, String txType);

	/**
	 * <pre>
	 * 기존 ProductId를 가지고 새로 만든 productId 조회 한다.
	 * </pre>
	 */
	public String getProductID(String couponCode);

	/**
	 * <pre>
	 * 상태값을 변경 한다.
	 * </pre>
	 */
	public void updateCouponStatus(String productID, String dpStatusCode, String upType, String itemCode);

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
