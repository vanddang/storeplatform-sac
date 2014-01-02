/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.api.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.api.conts.CouponConstants;
import com.skplanet.storeplatform.sac.api.except.CouponException;
import com.skplanet.storeplatform.sac.api.vo.DpBrandInfo;
import com.skplanet.storeplatform.sac.client.product.vo.category.WebtoonRequest;
import com.skplanet.storeplatform.sac.product.vo.WebtoonDTO;

@Service
public class BrandCatalogDaoImpl implements BrandCatalogDao {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/**
	 * 카테고리가 있는지 조회
	 * 
	 * @param dpBrandInfo
	 * @throws DaoException
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
	 * @throws DaoException
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

			// WebtoonService service = new WebtoonServiceImpl();
			// service.getAdminWebtoonList(req);
			System.out.println("+++++++++++++++++++++++++brandId++++++++++++++++++++" + brandId);
			List<WebtoonDTO> resultList = this.commonDAO.queryForList("Webtoon.getWebtoonList", req, WebtoonDTO.class);
			// cnt = (Integer) this.commonDAO.queryForObject("BrandCatalog.SELECT_COUNT_CUDTYPE", brandId);
			System.out.println("+++++++++++++++++++++++++LO22229999++++++++++++++++++++");
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ERR, e.getMessage(), null);
		}
		return cnt;
	}

	//
	// /**
	// * 카탈로그가 있는지 조회
	// *
	// * @param dpBrandInfo
	// * @throws DaoException
	// */
	//
	// public int selectCatalogCountCudType(String catalogId) throws CouponException {
	// Integer cnt = null;
	// try {
	// cnt = (Integer) queryForObject("BrandCatalog.SELECT_CATALOG_COUNT_CUDTYPE", catalogId);
	// } catch (DaoException e) {
	// throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_NOT_CATALOGID, "카탈로그의 catalogId 조회 실패", null);
	// } catch (Exception e) {
	// throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
	// }
	// return cnt.intValue();
	// }
	//
	/**
	 * brand 정보 넣기
	 * 
	 * @param dpBrandInfo
	 * @throws DaoException
	 */

	@Override
	public void insertBrandInfo(DpBrandInfo dpBrandInfo) throws CouponException {

		try {
			// CUD가 C로 왔을경우 해당 BRAND_ID로 등록된 브랜드가 없을 때
			if (this.selectBrandCountCudType(dpBrandInfo.getBrandId()) < 1) {
				if (this.selectCountBrandCategory(dpBrandInfo.getDpCatNo()) > 0) {
					this.commonDAO.insert("BrandCatalog.INSERT_TBL_DP_BRAND_INFO", dpBrandInfo);
				} else {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_NOT_CATID,
							"등록된 CATEGORY가 아닙니다. Category_Id = " + dpBrandInfo.getDpCatNo(), null);
				}
			} else {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DUP_BRID,
						"해당 brand_id로 등록한 brand가 있습니다.", null);
			}

		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ERR, e.getMessage(), null);
		}
	}

}
