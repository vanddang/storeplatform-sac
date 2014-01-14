package com.skplanet.storeplatform.sac.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.api.conts.CouponConstants;
import com.skplanet.storeplatform.sac.api.except.CouponException;
import com.skplanet.storeplatform.sac.api.vo.BrandCatalogProdImgInfo;
import com.skplanet.storeplatform.sac.api.vo.DpBrandInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogTagInfo;

@Service
@Transactional
public class BrandCatalogServiceImpl implements BrandCatalogService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/**
	 * brandId seq 가져오기
	 * 
	 */
	@Override
	public String searchCreateBrandId() {
		String createBrandId = "";
		createBrandId = (String) this.commonDAO.queryForObject("BrandCatalog.ID_GEN_SELECT_BRAND_ID", "");
		return createBrandId;
	}

	/**
	 * 카테고리가 있는지 조회
	 * 
	 * @param dpBrandInfo
	 * @throws CouponException
	 */

	public int selectCountBrandCategory(String dpCatNo) {
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

	public int selectBrandCountCudType(String brandId) {
		int cnt = 0;
		try {
			cnt = (Integer) this.commonDAO.queryForObject("BrandCatalog.SELECT_COUNT_CUDTYPE", brandId);
			this.log.info("cnt:::" + cnt);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ERR, e.getMessage(), null);
		}
		return cnt;
	}

	/**
	 * 카탈로그가 있는지 조회
	 * 
	 * @param dpBrandInfo
	 * @throws CouponException
	 */

	public int selectCatalogCountCudType(String catalogId) {
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

	// @Autowired
	@Override
	public void insertBrandInfo(DpBrandInfo dpBrandInfo) {

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
		} catch (CouponException e) {
			throw e;
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

	@Override
	public void updateBrandInfo(DpBrandInfo dpBrandInfo) {

		try {
			if (this.selectBrandCountCudType(dpBrandInfo.getBrandId()) > 0) {
				if (this.selectCountBrandCategory(dpBrandInfo.getDpCatNo()) > 0) {
					this.commonDAO.update("BrandCatalog.UPDATE_TB_DP_SHPG_BRAND", dpBrandInfo);
				} else {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_NOT_CATID,
							"등록된 CATEGORY가 아닙니다. CATEGORY_ID = " + dpBrandInfo.getDpCatNo(), null);
				}
			} else {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_NOT_CATID,
						"해당 brand_id로 등록한 brand가 없습니다. BRAND_ID = " + dpBrandInfo.getBrandId(), null);
			}
		} catch (CouponException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}
	}

	/**
	 * ㅊatalogId seq 가져오기
	 * 
	 */
	@Override
	public String searchCreateCatalogId() {
		String createCatalogId = "";
		createCatalogId = (String) this.commonDAO.queryForObject("BrandCatalog.ID_GEN_SELECT_CATALOG_ID", "");
		return createCatalogId;
	}

	/**
	 * Catalog 정보 넣기
	 * 
	 * @param dpCatalogInfo
	 * @throws CouponException
	 */

	@Override
	public void insertCatalogInfo(DpCatalogInfo dpCatalogInfo) {

		try {
			// CUD가 C로 왔을경우 해당 CATALOG_ID로 등록된 브랜드가 없을 때
			if (this.selectCatalogCountCudType(dpCatalogInfo.getCatalogId()) < 1) {
				if (this.selectCountBrandCategory(dpCatalogInfo.getDpCatNo()) > 0) {
					this.commonDAO.insert("BrandCatalog.INSERT_TB_DP_SHPG_CATALOG", dpCatalogInfo);
					this.commonDAO.insert("BrandCatalog.INSERT_TB_DP_SHPG_CATALOG_DESC", dpCatalogInfo);
					this.commonDAO.insert("BrandCatalog.INSERT_TB_DP_MENU_CATE_SHPG_CATA_MAPG", dpCatalogInfo);
				} else {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_NOT_CATID,
							"등록된 CATEGORY가 아닙니다. CATEGORY_ID = " + dpCatalogInfo.getDpCatNo(), null);
				}
			} else {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DUP_CATALOGID,
						"해당 catalog_id로 등록한 catalog가 있습니다. CATALOG_ID = " + dpCatalogInfo.getCatalogId(), null);
			}
		} catch (CouponException e) {
			throw e;
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

	@Override
	public void updateCatalogInfo(DpCatalogInfo dpCatalogInfo) {
		try {
			if (this.selectCatalogCountCudType(dpCatalogInfo.getCatalogId()) > 0) {
				if (this.selectBrandCountCudType(dpCatalogInfo.getBrandId()) > 0) {
					if (this.selectCountBrandCategory(dpCatalogInfo.getDpCatNo()) > 0) {
						this.commonDAO.update("BrandCatalog.UPDATE_TB_DP_SHPG_CATALOG", dpCatalogInfo);
						this.commonDAO.update("BrandCatalog.UPDATE_TB_DP_SHPG_CATALOG_DESC", dpCatalogInfo);
						this.commonDAO.update("BrandCatalog.UPDATE_TB_DP_MENU_CATE_SHPG_CATA_MAPG", dpCatalogInfo);
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
			throw e;
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}
	}

	/**
	 * Catalog Tag 넣기
	 * 
	 * @param dpCatalogInfo
	 * @throws CouponException
	 */

	@Override
	public void insertTblTagInfo(DpCatalogTagInfo tagInfo) {

		try {

			this.commonDAO.update("BrandCatalog.INSERT_TBL_TAG_INFO", tagInfo);

		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}
	}

	/**
	 * TBL_TAG_INFO를 삭제 한다.
	 */
	@Override
	public void deleteTblTagInfo(String cid) {

		try {
			this.log.info("catalogId = " + cid);
			this.commonDAO.delete("BrandCatalog.DELETE_TBL_TAG_INFO", cid);
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
	@Override
	public void insertTblDpProdImg(BrandCatalogProdImgInfo brandCatalogProdImgInfo) {
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
	@Override
	public void deleteDpProdImg(String prodId) {
		try {
			this.log.info("prodId = " + prodId);
			this.commonDAO.delete("BrandCatalog.DELETE_BRAND_CATALOG_TB_DP_PROD_IMG", prodId);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}
	}

	/**
	 * 브랜드 Brand_Id SELECT
	 */
	@Override
	public String getCreateBrandId(String brandId) {

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
	@Override
	public String getCreateCatalogId(String catalogId) {

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
