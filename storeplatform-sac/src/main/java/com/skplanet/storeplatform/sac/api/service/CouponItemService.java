package com.skplanet.storeplatform.sac.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.skplanet.storeplatform.external.client.shopping.vo.CouponReq;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponRes;
import com.skplanet.storeplatform.external.client.shopping.vo.DpCouponInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogTagInfo;
import com.skplanet.storeplatform.sac.api.vo.SpRegistProd;
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
	 * 
	 * @return String
	 */
	public String couponGenerateId();

	/**
	 * <pre>
	 * 아이템ID 생성.
	 * </pre>
	 * 
	 * @return String
	 */
	public String itemGenerateId();

	/**
	 * <pre>
	 * 쿠폰 유효성 검증.
	 * </pre>
	 * 
	 * @param scrContentId
	 *            scrContentId
	 * @return int
	 */
	public int getCouponCountCudType(String scrContentId);

	/**
	 * <pre>
	 * 아이템 유효성 검증.
	 * </pre>
	 * 
	 * @param scrContentId
	 *            scrContentId
	 * @return int
	 */
	public int getItemCountCudType(String scrContentId);

	/**
	 * <pre>
	 * 쿠폰ID 가져오기.
	 * </pre>
	 * 
	 * @param scrContentId
	 *            scrContentId
	 * @return String
	 */
	public String getCouponGenerateId(String scrContentId);

	/**
	 * <pre>
	 * 아이템ID 가져오기.
	 * </pre>
	 * 
	 * @param scrContentId
	 *            scrContentId
	 * @return String
	 */
	public String getItemGenerateId(String scrContentId);

	/**
	 * <pre>
	 * 쿠폰코드 ID 를 이용 상품유명코드 조회 한다.
	 * </pre>
	 * 
	 * @param couponCode
	 *            couponCode
	 * @return String
	 */	
	public String getCouponProdCaseCode(String couponCode);	
	
	/**
	 * <pre>
	 * 에피소드id를 이용해 특가 여부 조회 한다.
	 * </pre>
	 * 
	 * @param episodeId
	 *            episodeId
	 * @return int
	 */	
	public int getSpecialProdCnt(String episodeId);


	/**
	 * <pre>
	 * One Store 에피소드id를 이용해 특가 여부 조회 한다.
	 * </pre>
	 *
	 * @param reqMap
	 *            reqMap
	 * @return int
	 */
	public int getOneBrandSpecialProdCnt(Map<String, Object> reqMap);

	/**
	 * <pre>
	 * TB_DP_PROD 테이블 입력및 수정한다.
	 * </pre>
	 * 
	 * @param tblDpProdList
	 *            tblDpProdList
	 * @return
	 */
	public void insertTbDpProdInfo(List<TbDpProdInfo> tblDpProdList);

	/**
	 * <pre>
	 * TB_DP_SHPG_PROD 테이블 입력및 수정한다.
	 * </pre>
	 * 
	 * @param tbDpShpgProdList
	 *            tbDpShpgProdList
	 * @return
	 */
	public void insertTbDpShpgProdInfo(List<TbDpShpgProdInfo> tbDpShpgProdList);

	/**
	 * <pre>
	 * TB_DP_PROD_DESC 테이블 입력및 수정한다.
	 * </pre>
	 * 
	 * @param tbDpProdDescList
	 *            tbDpProdDescList
	 */
	public void insertTbDpProdDescInfo(List<TbDpProdDescInfo> tbDpProdDescList);

	/**
	 * <pre>
	 * TB_DP_PROD_RSHP 테이블 입력및 수정한다.
	 * </pre>
	 * 
	 * @param tbDpProdRshpList
	 *            tbDpProdRshpList
	 */
	public void insertTbDpProdRshpInfo(List<TbDpProdRshpInfo> tbDpProdRshpList);

	/**
	 * <pre>
	 * TB_DP_TENANT_PROD 테이블 입력및 수정한다.
	 * </pre>
	 * 
	 * @param tbDpTenantProdList
	 *            tbDpTenantProdList
	 */
	public void insertTbDpTenantProdInfo(List<TbDpTenantProdInfo> tbDpTenantProdList , DpCouponInfo couponInfo);

	/**
	 * <pre>
	 * TB_DP_TENANT_PROD_PRICE 테이블 입력및 수정한다.
	 * </pre>
	 * 
	 * @param tbDpTenantProdPriceList
	 *            tbDpTenantProdPriceList
	 */
	public void insertTbDpTenantProdPriceInfo(List<TbDpTenantProdPriceInfo> tbDpTenantProdPriceList);

	/**
	 * <pre>
	 * TB_DP_PROD_CATALOG_MAPG 테이블 입력및 수정한다.
	 * </pre>
	 * 
	 * @param tbDpProdCatalogMapg
	 *            tbDpProdCatalogMapg
	 * @param prodId
	 *            prodId
	 */
	public void insertTbDpProdCatalogMapgInfo(List<TbDpProdCatalogMapgInfo> tbDpProdCatalogMapg, String prodId);

	/**
	 * <pre>
	 * TB_DP_PROD_OPT 테이블 입력및 수정한다.
	 * </pre>
	 * 
	 * @param tbDpProdOptList
	 *            tbDpProdOptList
	 */
	public void insertTbDpProdOptInfo(List<TbDpProdOpt> tbDpProdOptList);

	/**
	 * <pre>
	 * TB_DP_SPRT_DEVICE 테이블 입력한다.
	 * </pre>
	 * 
	 * @param tbDpSprtDeviceList
	 *            tbDpSprtDeviceList
	 */
	public void insertTbDpSprtDeviceInfo(List<TbDpSprtDeviceInfo> tbDpSprtDeviceList);

	/**
	 * <pre>
	 * TB_DP_PROD_TAG 테이블 입력및 수정한다.
	 * </pre>
	 * 
	 * @param tagList
	 *            tagList
	 */
	public void insertTblTagInfo(ArrayList<DpCatalogTagInfo> tagList);

	/**
	 * <pre>
	 * 정산율 프로시저 콜 한다.
	 * </pre>
	 * 
	 * @param spRegistProdList
	 *            spRegistProdList
	 */
	public void insertCallSpSettRegProd(List<SpRegistProd> spRegistProdList);

	/**
	 * <pre>
	 * 상태값을 변경 한다.
	 * </pre>
	 * 
	 * @param newCouponCode
	 *            newCouponCode
	 * @param couponList
	 *            couponList
	 * @param upType
	 *            upType
	 * @param itemCode
	 *            itemCode
	 */
	public void updateCouponStatus(String newCouponCode, List<Map<String, Object>> couponList , String upType, String itemCode);

	
	/**
	 * <pre>
	 * 팅/특가 쿠폰 ID 조회 한다.
	 * </pre>
	 * 
	 * @param episodeId
	 *            episodeId
	 * @return String
	 */
	public String getSpecialProductCouponId(String episodeId);

	/**
	 * <pre>
	 * One Store 팅/특가 쿠폰 ID 조회 한다.
	 * </pre>
	 *
	 * @param reqMap
	 *            reqMap
	 * @return List<String>
	 */
	public List<String> getOneBrandSpecialProductCouponId(Map<String, Object> reqMap);
	/**
	 * <pre>
	 * 상태값을 변경 한다.
	 * </pre>
	 * 
	 * @param newCouponCode
	 *            newCouponCode
	 * @param dpStatusCode
	 *            dpStatusCode
	 * @param upType
	 *            upType
	 * @param itemCodes
	 *            itemCodes
	 */
	public void updateCouponStatusForSpecialProd(String newCouponCode, String dpStatusCode, String upType, String itemCodes );

	/**
	 * <pre>
	 * One Store 상태값을 변경 한다.
	 * </pre>
	 *
	 * @param newCouponCode
	 *            newCouponCode
	 * @param dpStatusCode
	 *            dpStatusCode
	 * @param upType
	 *            upType
	 * @param itemCodes
	 *            itemCodes
	 */
	public void updateCouponStatusForOneBrandSpecialProd(String newCouponCode, String dpStatusCode, String upType, String itemCodes,String[] tenantIds );


	
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
	 * One Store 특가 상품 목록 조회 한다.
	 * </pre>
	 *
	 * @param itemCodes
	 *            itemCodes
	 * @return List<CouponRes>
	 */
	public List<CouponRes> getOneBrandSpecialProductList(String[] itemCodes);

	/**
	 * <pre>
	 * 특가 상품 상세 조회 한다.
	 * </pre>
	 * 
	 * @param couponCode
	 *            couponCode
	 * @param itemsCodes
	 *            itemsCodes
	 * @return CouponRes
	 */
	public CouponRes getSpecialProductDetail(String couponCode ,String[] itemsCodes);

	/**
	 * <pre>
	 * One Store 특가 상품 상세 조회 한다.
	 * </pre>
	 *
	 * @param couponCode
	 *            couponCode
	 * @param itemsCodes
	 *            itemsCodes
	 * @return CouponRes
	 */
	public CouponRes getOneBrandSpecialProductDetail(String couponCode, String[] itemsCodes);



	/**
	 * <pre>
	 * 카탈로그 및 메뉴ID 조회 한다.
	 * </pre>
	 * 
	 * @param catalogId
	 *            catalogId
	 * @return CouponRes
	 */
	public CouponRes getCatalogNmMenuId(String catalogId);
	
	/**
	 * <pre>
	 * 실제로 판매하는 상품을 조회 한다.
	 * </pre>
	 * 
	 * @param reqMap
	 *            reqMap
	 * @return String
	 */
	
	public String getShoppingIngYn(Map<String, Object> reqMap );
	
	/**
	 * <pre>
	 * 채널ID를 이용 카탈로그ID 조회 한다.
	 * </pre>
	 * 
	 * @param channelId
	 *            catalogId
	 * @return String
	 */
	public String getShoppingCatalogIdByChannelId(String channelId);	
	/**
	 * <pre>
	 * 특정 기간에 대한 특가 상품 상세 조회 작업을 호출한다.
	 * </pre>
	 * 
	 * @param couponReq
	 *            couponReq
	 * @return CouponRes
	 */
	public CouponRes getSpecialProductDetailForDate(CouponReq couponReq);

	/**
	 * <pre>
	 * One Store 특정 기간에 대한 특가 상품 상세 조회 작업을 호출한다.
	 * </pre>
	 *
	 * @param couponReq
	 *            couponReq
	 * @return CouponRes
	 */
	public CouponRes getOneBrandSpecialProductDetailForDate(CouponReq couponReq);
	
	/**
	 * <pre>
	 * 쿠폰코드를 이용 아이템 코드값을 가져온다.
	 * </pre>
	 * 
	 * @param channelId
	 *            channelId
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getCouponCompareItemCode(String channelId);




}
