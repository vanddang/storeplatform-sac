package com.skplanet.storeplatform.sac.api.service;

import java.util.ArrayList;
import java.util.List;

import com.skplanet.storeplatform.external.client.shopping.vo.CouponReq;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponRes;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogTagInfo;
import com.skplanet.storeplatform.sac.api.vo.TbDpProdCatalogMapgInfo;
import com.skplanet.storeplatform.sac.api.vo.TbDpProdDescInfo;
import com.skplanet.storeplatform.sac.api.vo.TbDpProdInfo;
import com.skplanet.storeplatform.sac.api.vo.TbDpProdOpt;
import com.skplanet.storeplatform.sac.api.vo.TbDpProdRshpInfo;
import com.skplanet.storeplatform.sac.api.vo.TbDpShpgProdInfo;
import com.skplanet.storeplatform.sac.api.vo.TbDpSprtDeviceInfo;
import com.skplanet.storeplatform.sac.api.vo.TbDpTenantProdInfo;
import com.skplanet.storeplatform.sac.api.vo.TbDpTenantProdPriceInfo;

/**
 * <pre>
 * 쿠폰아이템 서비스 인터페이스
 * </pre>
 * 
 * Created on : 2014-01-03 Created by : 김형식, SK 플래닛. Last Updated on : 2014-01-03 Last Updated by : 김형식, SK 플래닛
 */
public interface CouponItemService {

	/**
	 * <pre>
	 * 쿠폰ID 생성.
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
	 * 쿠폰 , 아이템 유효성 검증
	 * </pre>
	 */
	public int getCouponItemCountCudType(String scrContentId);

	/**
	 * <pre>
	 * 쿠폰ID,아이템ID 가져오기
	 * </pre>
	 */
	public String getGenerateId(String scrContentId);

	/**
	 * <pre>
	 * TB_DP_PROD 테이블 입력및 수정한다.
	 * </pre>
	 */
	public void insertTbDpProdInfo(List<TbDpProdInfo> tblDpProdList);

	/**
	 * <pre>
	 * TB_DP_SHPG_PROD 테이블 입력및 수정한다.
	 * </pre>
	 */
	public void insertTbDpShpgProdInfo(List<TbDpShpgProdInfo> tbDpShpgProdList);

	/**
	 * <pre>
	 * TB_DP_PROD_DESC 테이블 입력및 수정한다.
	 * </pre>
	 */
	public void insertTbDpProdDescInfo(List<TbDpProdDescInfo> tbDpProdDescList);

	/**
	 * <pre>
	 * TB_DP_PROD_RSHP 테이블 입력및 수정한다.TB_DP_PROD_RSHP
	 * </pre>
	 */
	public void insertTbDpProdRshpInfo(List<TbDpProdRshpInfo> tbDpProdRshpList);

	/**
	 * <pre>
	 * TB_DP_TENANT_PROD 테이블 입력및 수정한다.
	 * </pre>
	 */
	public void insertTbDpTenantProdInfo(List<TbDpTenantProdInfo> tbDpTenantProdList);

	/**
	 * <pre>
	 * TB_DP_TENANT_PROD_PRICE 테이블 입력및 수정한다.
	 * </pre>
	 */
	public void insertTbDpTenantProdPriceInfo(List<TbDpTenantProdPriceInfo> tbDpTenantProdPriceList);

	/**
	 * <pre>
	 * TB_DP_PROD_CATALOG_MAPG 테이블 입력및 수정한다.
	 * </pre>
	 */
	public void insertTbDpProdCatalogMapgInfo(List<TbDpProdCatalogMapgInfo> tbDpProdCatalogMapg, String prodId);

	/**
	 * <pre>
	 * TB_DP_PROD_OPT 테이블 입력및 수정한다.
	 * </pre>
	 */
	public void insertTbDpProdOptInfo(List<TbDpProdOpt> tbDpProdOptList);

	/**
	 * <pre>
	 * TB_DP_SPRT_DEVICE 테이블 입력한다.
	 * </pre>
	 */
	public void insertTbDpSprtDeviceInfo(List<TbDpSprtDeviceInfo> tbDpSprtDeviceList);

	/**
	 * <pre>
	 * TB_DP_TENANT_PROD_PRICE 테이블 입력및 수정한다.
	 * </pre>
	 */
	public void insertTblTagInfo(ArrayList<DpCatalogTagInfo> tagList);

	/**
	 * <pre>
	 * 상태값을 변경 한다.
	 * </pre>
	 */
	public void updateCouponStatus(String newCouponCode, String dpStatusCode, String upType, String itemCode);

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
	 * 쿠폰(아이템) 판매상태 변경
	 */
	public ArrayList<String> updateBatchForCouponStatus(ArrayList<CouponReq> couponList);

}
