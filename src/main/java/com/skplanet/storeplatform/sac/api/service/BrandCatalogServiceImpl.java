/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.api.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.api.conts.CouponConstants;
import com.skplanet.storeplatform.sac.api.except.CouponException;
import com.skplanet.storeplatform.sac.api.vo.BrandCatalogProdImgInfo;
import com.skplanet.storeplatform.sac.api.vo.DpBrandInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogTagInfo;
import com.skplanet.storeplatform.sac.client.product.vo.category.WebtoonRequest;

public class BrandCatalogServiceImpl {

	private final static Logger log = Logger.getLogger(BrandCatalogServiceImpl.class);
	private final String errorCode = "";
	private final String message = "";
	CouponConstants couponcontants = new CouponConstants();
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/**
	 * 카테고리가 있는지 조회
	 * 
	 * @param dpBrandInfo
	 * @throws CouponException
	 */

	public int selectCountBrandCategory(String dpCatNo) throws CouponException {
		Integer cnt = null;
		cnt = (Integer) this.commonDAO.queryForObject("BrandCatalog.SELECT_COUNT_TBL_DP_MENU", dpCatNo);
		return cnt.intValue();
	}

	/**
	 * 브랜드가 있는지 조회
	 * 
	 * @param dpBrandInfo
	 * @throws CouponException
	 */

	public int selectBrandCountCudType(String brandId) throws CouponException {
		int cnt = 0;
		try {
			WebtoonRequest req = new WebtoonRequest();
			req.setOffset(1);
			req.setCount(20);
			req.setTenantId("S01");
			req.setSystemId("S009");
			req.setWeekDayCd("DP010101");
			req.setImageCd("DP000196");

			System.out.println("+++++++++++++++++++++++++LO11119999++++++++++++++++++++");
			// List<WebtoonDTO> resultList = this.commonDAO.queryForList("Webtoon.getWebtoonList", req,
			// WebtoonDTO.class);
			// cnt = (Integer) this.commonDAO.queryForObject("BrandCatalog.SELECT_COUNT_CUDTYPE", brandId);
			// System.out.println("+++++++++++++++++++++++++LO22229999++++++++++++++++++++");
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}
		return cnt;
	}

	/**
	 * 카탈로그가 있는지 조회
	 * 
	 * @param dpBrandInfo
	 * @throws CouponException
	 */

	public int selectCatalogCountCudType(String catalogId) throws CouponException {
		Integer cnt = null;
		try {
			cnt = (Integer) this.commonDAO.queryForObject("BrandCatalog.SELECT_CATALOG_COUNT_CUDTYPE", catalogId);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}
		return cnt.intValue();
	}

	/**
	 * brand 정보 넣기
	 * 
	 * @param dpBrandInfo
	 * @throws CouponException
	 */

	public void insertBrandInfo(DpBrandInfo dpBrandInfo) throws CouponException {

		try {

			// CUD가 C로 왔을경우 해당 BRAND_ID로 등록된 브랜드가 없을 때
			if (this.selectBrandCountCudType(dpBrandInfo.getBrandId()) < 1) {
				if (this.selectCountBrandCategory(dpBrandInfo.getDpCatNo()) > 0) {
					this.commonDAO.insert("BrandCatalog.INSERT_TB_DP_SHPG_BRAND", dpBrandInfo);
				} else {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_NOT_CATID,
							"등록된 CATEGORY가 아닙니다. Category_Id = " + dpBrandInfo.getDpCatNo(), null);
				}
			} else {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DUP_BRID,
						"해당 brand_id로 등록한 brand가 있습니다.", null);
			}

		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}
	}

	//
	/**
	 * brand 정보 수정
	 * 
	 * @param dpBrandInfo
	 * @throws CouponException
	 */

	public void updateBrandInfo(DpBrandInfo dpBrandInfo) throws CouponException {

		try {
			if (this.selectBrandCountCudType(dpBrandInfo.getBrandId()) > 0) {
				if (this.selectCountBrandCategory(dpBrandInfo.getDpCatNo()) > 0) {
					this.commonDAO.update("BrandCatalog.UPDATE_TB_DP_SHPG_BRAND", dpBrandInfo);
				} else {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_NOT_CATID,
							"등록된 CATEGORY가 아닙니다. CATEGORY_ID = " + dpBrandInfo.getDpCatNo(), null);
				}
			} else {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_NOT_BRID,
						"해당 brand_id로 등록한 brand가 없습니다. BRAND_ID = " + dpBrandInfo.getBrandId(), null);
			}
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}
	}

	/**
	 * Catalog 정보 넣기
	 * 
	 * @param dpCatalogInfo
	 * @throws CouponException
	 */

	public void insertCatalogInfo(DpCatalogInfo dpCatalogInfo) throws CouponException {

		try {
			// CUD가 C로 왔을경우 해당 CATALOG_ID로 등록된 브랜드가 없을 때
			if (this.selectCatalogCountCudType(dpCatalogInfo.getCatalogId()) < 1) {
				// if(selectBrandCountCudType(dpCatalogInfo.getBrandId()) > 0){
				if (this.selectCountBrandCategory(dpCatalogInfo.getDpCatNo()) > 0) {
					this.commonDAO.insert("BrandCatalog.INSERT_TB_DP_SHPG_CATALOG", dpCatalogInfo);
					this.commonDAO.insert("BrandCatalog.INSERT_TB_DP_SHPG_CATALOG_DESC", dpCatalogInfo);
					this.commonDAO.insert("BrandCatalog.INSERT_TB_DP_SHPG_CATALOG", dpCatalogInfo);
				} else {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_NOT_CATID,
							"등록된 CATEGORY가 아닙니다. CATEGORY_ID = " + dpCatalogInfo.getDpCatNo(), null);
				}
			} else {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DUP_CATALOGID,
						"해당 catalog_id로 등록한 catalog가 있습니다. CATALOG_ID = " + dpCatalogInfo.getCatalogId(), null);
			}
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}
	}

	/**
	 * Catalog 정보 수정
	 * 
	 * @param dpCatalogInfo
	 * @throws CouponException
	 */

	public void updateCatalogInfo(DpCatalogInfo dpCatalogInfo) throws CouponException {
		try {
			if (this.selectCatalogCountCudType(dpCatalogInfo.getCatalogId()) > 0) {
				if (this.selectBrandCountCudType(dpCatalogInfo.getBrandId()) > 0) {
					if (this.selectCountBrandCategory(dpCatalogInfo.getDpCatNo()) > 0) {
						this.commonDAO.update("BrandCatalog.UPDATE_TB_DP_SHPG_CATALOG", dpCatalogInfo);
						this.commonDAO.update("BrandCatalog.UPDATE_TB_DP_SHPG_CATALOG_DESC", dpCatalogInfo);
						this.commonDAO.update("BrandCatalog.INSERT_TB_DP_CATEGORY_MENU_MAPG", dpCatalogInfo);
					} else {
						throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_NOT_CATID,
								"등록된 CATEGORY가 아닙니다. CATEGORY_ID = " + dpCatalogInfo.getDpCatNo(), null);
					}
				} else {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_NOT_BRID,
							"해당 brand_id로 등록한 brand가 없습니다. BRAND_ID = " + dpCatalogInfo.getBrandId(), null);
				}
			} else {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DUP_CATALOGID,
						"해당 catalog_id로 등록한 catalog가 있습니다. CATALOG_ID = " + dpCatalogInfo.getCatalogId(), null);
			}

		} catch (CouponException e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_NOT_BRID,
					"해당 brand_id로 등록한 brand가 없습니다. BRAND_ID = " + dpCatalogInfo.getBrandId(), null);
		}
	}

	/**
	 * Catalog Tag 넣기
	 * 
	 * @param dpCatalogInfo
	 * @throws CouponException
	 */

	public void insertTblTagInfo(DpCatalogTagInfo tagInfo) throws CouponException {

		try {

			this.commonDAO.update("BrandCatalog.INSERT_TBL_TAG_INFO", tagInfo);

		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}
	}

	/**
	 * TBL_TAG_INFO를 삭제 한다.
	 */
	public void deleteTblTagInfo(String prodId) throws CouponException {

		try {
			log.info("prodId = " + prodId);
			this.commonDAO.delete("BrandCatalog.DELETE_TBL_TAG_INFO", prodId);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}
	}

	/**
	 * 전시 상품 이미지 테이블에 추가 한다.
	 * 
	 * @param brandCatalogProdImgInfo
	 *            전시 상품 이미지 정보
	 */
	public void insertTblDpProdImg(BrandCatalogProdImgInfo brandCatalogProdImgInfo) throws CouponException {
		try {
			this.commonDAO.insert("BrandCatalog.INSERT_BRAND_CATALOG_TB_DP_PROD_IMG", brandCatalogProdImgInfo);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}
	}

	/**
	 * 전시 상품 이미지 테이블에 삭제 한다.
	 * 
	 * @param dpProdImgInfo
	 *            전시 상품 이미지 정보
	 */
	public void deleteDpProdImg(String prodId) throws CouponException {
		try {
			log.info("prodId = " + prodId);
			this.commonDAO.delete("BrandCatalog.DELETE_BRAND_CATALOG_TB_DP_PROD_IMG", prodId);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}
	}

	/**
	 * 브랜드 Brand_Id SELECT
	 */
	public String getCreateBrandId(String brandId) throws CouponException {

		try {
			if (this.selectBrandCountCudType(brandId) > 0) {

				return (String) this.commonDAO.queryForObject("BrandCatalog.SELECT_BRAND_ID", brandId);

			} else {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_NOT_BRID,
						"해당 brand_id로 등록한 brand가 없습니다. BRAND_ID = " + brandId, null);
			}

		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}
	}

	/**
	 * 카탈로그 Catalog_Id SELECT
	 */
	public String getCreateCatalogId(String catalogId) throws CouponException {

		try {
			if (this.selectCatalogCountCudType(catalogId) > 0) {

				return (String) this.commonDAO.queryForObject("BrandCatalog.SELECT_CATALOG_ID", catalogId);

			} else {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_NOT_CATALOGID,
						"해당 catalog_id로 등록한 catalog가 없습니다. CATALOG_ID = " + catalogId, null);
			}

		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}
	}
	//
	// /**
	// * 브랜드 카탈로그 REPOSITORY_PATH SELECT
	// */
	// public String getLabelForQuery(Object[] params) {
	// HashMap<String, Object> paramMap = new HashMap<String, Object>();
	//
	// try {
	// if (params != null) {
	// for (int i = 0; i < params.length; i++) {
	// paramMap.put("PARAM" + i, params[i]);
	// }
	// }
	// return (String) queryForObject("BrandCatalog.SELECT_REPOSITORY_PATH", paramMap);
	// } catch (DaoException de) {
	// log.error("■■■■■    getLabelForQuery() ■■■■■");
	// log.error(de.getMessage());
	// throw new InfraException("getLabelForQuery()", "select Fail", de);
	// }
	// }

}
