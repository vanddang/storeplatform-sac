package com.skplanet.storeplatform.sac.api.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.api.conts.CouponConstants;
import com.skplanet.storeplatform.sac.api.except.CouponException;
import com.skplanet.storeplatform.sac.api.vo.BrandCatalogProdImgInfo;
import com.skplanet.storeplatform.sac.api.vo.DpBrandInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogTagInfo;

/**
 * <pre>
 * 브랜드 카탈로그 서비스 인터페이스 Imple.
 * </pre>
 * 
 * Created on : 2014-01-03 Created by : 김형식, SK 플래닛. Last Updated on : 2014-01-03 Last Updated by : 김형식, SK 플래닛
 */
@Service
public class BrandCatalogServiceImpl implements BrandCatalogService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
    @Qualifier("sac")
	private CommonDAO commonDAO;

	/**
	 * <pre>
	 * 브랜드 정보(brandId) SEQ 가져오기.
	 * </pre>
	 * 
	 * @return String
	 */
	@Override
	public String searchCreateBrandId() {
		String createBrandId = "";
		createBrandId = (String) this.commonDAO.queryForObject("BrandCatalog.ID_GEN_SELECT_BRAND_ID", "");
		return createBrandId;
	}

	/**
	 * 카테고리가 있는지 조회.
	 * 
	 * @param dpCatNo
	 *            dpCatNo
	 * @return int
	 */

	public int selectCountBrandCategory(String dpCatNo) {
		Integer cnt = null;
		cnt = (Integer) this.commonDAO.queryForObject("BrandCatalog.SELECT_COUNT_TB_DP_MENU_CATEGORY", dpCatNo);
		return cnt.intValue();
	}

	/**
	 * 브랜드가 있는지 조회.
	 * 
	 * @param brandId
	 *            brandId
	 * @return int
	 */

	public int selectBrandCountCudType(String brandId) {
		int cnt = 0;
		try {
			cnt = (Integer) this.commonDAO.queryForObject("BrandCatalog.SELECT_COUNT_CUDTYPE", brandId);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ERR, e.getMessage(), null);
		}
		return cnt;
	}

	/**
	 * 카탈로그가 있는지 조회.
	 * 
	 * @param catalogId
	 *            catalogId
	 * @return int
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
	 * <pre>
	 * 브랜드 정보 입력.
	 * </pre>
	 * 
	 * @param dpBrandInfo
	 *            brandInfo
	 * 
	 */
	@Override
	public void insertBrandInfo(DpBrandInfo dpBrandInfo) {

		try {

			// CUD가 C로 왔을경우 해당 BRAND_ID로 등록된 브랜드가 없을 때
			if (this.selectBrandCountCudType(dpBrandInfo.getBrandId()) < 1) {
				if (this.selectCountBrandCategory(dpBrandInfo.getDpCatNo()) > 0) {
					this.commonDAO.insert("BrandCatalog.createTbShpgBrand", dpBrandInfo);
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

	/**
	 * <pre>
	 * 브랜드 정보 수정.
	 * </pre>
	 * 
	 * @param dpBrandInfo
	 *            brandInfo
	 * @return
	 */

	@Override
	public void updateBrandInfo(DpBrandInfo dpBrandInfo) {

		try {
			if (this.selectBrandCountCudType(dpBrandInfo.getBrandId()) > 0) {
				if (this.selectCountBrandCategory(dpBrandInfo.getDpCatNo()) > 0) {
					this.commonDAO.update("BrandCatalog.updateTbDpShpgBrand", dpBrandInfo);
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
	 * <pre>
	 * 브랜드 정보(catalogId) SEQ 가져오기.
	 * </pre>
	 * 
	 * @return String
	 */
	@Override
	public String searchCreateCatalogId() {
		String createCatalogId = "";
		createCatalogId = (String) this.commonDAO.queryForObject("BrandCatalog.ID_GEN_SELECT_CATALOG_ID", "");
		return createCatalogId;
	}

	/**
	 * <pre>
	 * 카탈로그 정보 입력.
	 * </pre>
	 * 
	 * @param dpCatalogInfo
	 *            catalogInfo
	 * @return
	 */

	@Override
	public void insertCatalogInfo(DpCatalogInfo dpCatalogInfo) {

		try {
			// CUD가 C로 왔을경우 해당 CATALOG_ID로 등록된 브랜드가 없을 때
			if (this.selectCatalogCountCudType(dpCatalogInfo.getCatalogId()) < 1) {
				if (this.selectCountBrandCategory(dpCatalogInfo.getDpCatNo()) > 0) {
					this.commonDAO.insert("BrandCatalog.createTbDpShpgCatalog", dpCatalogInfo);
					this.commonDAO.insert("BrandCatalog.createTbDpShpgCatalogDesc", dpCatalogInfo);
					this.commonDAO.insert("BrandCatalog.createTbDpMenuShpgMapg", dpCatalogInfo);
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
	 * <pre>
	 * 카탈로그 정보 수정.
	 * </pre>
	 * 
	 * @param dpCatalogInfo
	 *            catalogInfo
	 * @return
	 */

	@Override
	public void updateCatalogInfo(DpCatalogInfo dpCatalogInfo) {
		try {
			if (this.selectCatalogCountCudType(dpCatalogInfo.getCatalogId()) > 0) {
				if (this.selectBrandCountCudType(dpCatalogInfo.getBrandId()) > 0) {
					if (this.selectCountBrandCategory(dpCatalogInfo.getDpCatNo()) > 0) {
						this.commonDAO.update("BrandCatalog.updateTbDpShpgCatalog", dpCatalogInfo);
						this.commonDAO.update("BrandCatalog.updateTbDpShpgCatalogDesc", dpCatalogInfo);
						this.commonDAO.update("BrandCatalog.updateTbDpMenuShpgMapg", dpCatalogInfo);
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
	 * <pre>
	 * 태그 정보 저장.
	 * </pre>
	 * 
	 * @param tagInfo
	 *            tagInfo
	 * @return
	 */

	@Override
	public void insertTblTagInfo(DpCatalogTagInfo tagInfo) {

		try {

			this.commonDAO.update("BrandCatalog.createTbDpProdTag", tagInfo);

		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}
	}

	/**
	 * <pre>
	 * 태그 정보 삭제.
	 * </pre>
	 * 
	 * @param cid
	 *            cid
	 * @return
	 */
	@Override
	public void deleteTblTagInfo(String cid) {

		try {
			this.log.info("catalogId = " + cid);
			this.commonDAO.delete("BrandCatalog.removeTbDpProdTag", cid);
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
			this.commonDAO.insert("BrandCatalog.createBrandCatalogTbDpProdImg", brandCatalogProdImgInfo);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}
	}

	/**
	 * 전시 상품 이미지 테이블에 삭제 한다.
	 * 
	 * @param prodId
	 *            전시 상품 이미지 정보
	 */
	@Override
	public void deleteDpProdImg(String prodId) {
		try {
			this.log.info("prodId = " + prodId);
			this.commonDAO.delete("BrandCatalog.removeBrandCatalogTbDpProdImg", prodId);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}
	}

	/**
	 * <pre>
	 * 브랜드 정보(brandId)가져오기.
	 * </pre>
	 * 
	 * @param brandId
	 *            brandId
	 * @return String
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
		} catch (CouponException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}
	}

	/**
	 * <pre>
	 * 카탈로그 정보(catalogId)가져오기.
	 * </pre>
	 * 
	 * @param catalogId
	 *            catalogId
	 * @return String
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
		} catch (CouponException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}
	}

	/**
	 * <pre>
	 * 브랜드,카탈로그 정보(brandId) C->U 로 변경 가져오기.
	 * </pre>
	 * 
	 * @param brandId
	 *            brandId
	 * @param catalogId
	 *            catalogId
	 * @return int
	 */
	@Override
	public int getBrandCatalogChangeCudType(String brandId, String catalogId) {

		int resuntCnt = 0;
		if (brandId != null) {
			resuntCnt = this.selectBrandCountCudType(brandId);
		}
		if (catalogId != null) {
			resuntCnt = this.selectCatalogCountCudType(catalogId);
		}
		return resuntCnt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.api.service.BrandCatalogService#getBrandCatalogImgPath(java.lang.String)
	 */
	@Override
	public ArrayList<BrandCatalogProdImgInfo> getBrandCatalogImgPath(String imgId) {

		List<BrandCatalogProdImgInfo> productBasicInfoList = this.commonDAO.queryForList(
				"BrandCatalog.getBrandCatalogImgPath", imgId, BrandCatalogProdImgInfo.class);

		return (ArrayList<BrandCatalogProdImgInfo>) productBasicInfoList;
	}
}
