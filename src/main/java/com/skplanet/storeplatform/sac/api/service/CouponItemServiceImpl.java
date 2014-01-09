package com.skplanet.storeplatform.sac.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.api.conts.CouponConstants;
import com.skplanet.storeplatform.sac.api.except.CouponException;
import com.skplanet.storeplatform.sac.api.vo.CouponResponseInfo;
import com.skplanet.storeplatform.sac.api.vo.TbDpProdInfo;

@Service
@Transactional
public class CouponItemServiceImpl implements CouponItemService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/**
	 * <pre>
	 * 쿠폰ID 생성
	 * </pre>
	 */
	@Override
	public String couponGenerateId() {
		try {
			return (String) this.commonDAO.queryForObject("Coupon.couponGenerateId", "");
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, e.getMessage(), null);
		}
	}

	/**
	 * <pre>
	 * 아이템ID 생성
	 * </pre>
	 */
	@Override
	public String itemGenerateId() {
		try {
			return (String) this.commonDAO.queryForObject("Coupon.itemGenerateId", "");
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, e.getMessage(), null);
		}
	}

	@Override
	public void insertTbDpProdInfo(List<TbDpProdInfo> tblDpProdList, String txType) {
		try {
			for (TbDpProdInfo vo : tblDpProdList) {
				if (txType.equals("C")) {
					this.commonDAO.insert("Coupon.insertTbDpProdInfo", vo);
				} else { // 수정
					this.commonDAO.update("Coupon.updateTbDpProdInfo", vo);
				}
			}
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}

	}

	/**
	 * <pre>
	 * 기존 ProductId를 가지고 새로 만든 productId 조회 한다.
	 * </pre>
	 */
	@Override
	public String getProductID(String couponCode) {
		try {
			return (String) this.commonDAO.queryForObject("Coupon.getProductID", couponCode);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, e.getMessage(), null);
		}
	}

	/**
	 * <pre>
	 * 상태값을 변경 한다.
	 * </pre>
	 */
	@Override
	public void updateCouponStatus(String prodId, String dpStatusCode, String upType, String itemCode) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("prodId", prodId);
			map.put("dpStatusCode", dpStatusCode);
			map.put("upType", upType);
			map.put("itemCode", itemCode);

			if (this.commonDAO.update("Coupon.updateDPCouponStatus", map) <= 0) {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC,
						"Coupon.updateDPCouponStatus 실패", null);
			}
			if (this.commonDAO.update("Coupon.updateDPCouponItemCNT", prodId) <= 0) {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC,
						"Coupon.updateDPcouponItemCNT 실패", null);
			}
			if (this.commonDAO.update("Coupon.updateDPCouponCNT", prodId) <= 0) {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "Coupon.updateDPcouponCNT 실패",
						null);
			}
			if (!StringUtils.equalsIgnoreCase(upType, "0")) {
				this.commonDAO.update("Coupon.updateDPYNStatus", prodId);
			}
		} catch (CouponException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, e.getMessage(), null);
		}

	}

	/**
	 * <pre>
	 * 특가 상품 목록 조회 한다.
	 * </pre>
	 */
	@Override
	public List<CouponResponseInfo> getSpecialProductList(String[] couponCodes) {
		List<CouponResponseInfo> list = new ArrayList<CouponResponseInfo>();

		try {

			for (String couponCode : couponCodes) {
				couponCode = couponCode.trim();
				int cnt = (Integer) this.commonDAO.queryForObject("Coupon.GET_SPECIAL_PRODUCT_INFO", couponCode);
				CouponResponseInfo crinfo = new CouponResponseInfo();
				crinfo.setCouponCode(couponCode);
				if (cnt > 0)
					crinfo.setSpecialYN("Y");
				else
					crinfo.setSpecialYN("N");
				list.add(crinfo);
			}

		} catch (Exception e) {
			this.log.error("COUPON.IF_META_SELECT_LIST FAILED", e);
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ERR, e.getMessage(), null);
		}

		return list;
	}

	/**
	 * <pre>
	 * 특가 상품 상세 조회 한다.
	 * </pre>
	 */

	@Override
	public CouponResponseInfo getSpecialProductDetail(String couponCode) {

		CouponResponseInfo info = null;

		try {
			int cnt = (Integer) this.commonDAO.queryForObject("Coupon.GET_COUPON_INFO", couponCode);
			if (cnt > 0) {
				info = (CouponResponseInfo) this.commonDAO.queryForObject("Coupon.GET_SPECIAL_PRODUCT_DETAIL",
						couponCode);
				if (info == null) {
					info = new CouponResponseInfo();
					info.setRCode(CouponConstants.COUPON_IF_ERROR_CODE_NOT_SPECIAL);
				} else
					info.setRCode("");
			} else {

				info = new CouponResponseInfo();
				info.setRCode(CouponConstants.COUPON_IF_ERROR_CODE_COUPONCODE);

			}

		} catch (Exception e) {
			this.log.error("COUPON.GET_SPECIAL_PRODUCT_DETAIL", e);
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ERR, e.getMessage(), null);
		}

		return info;
	}

}
