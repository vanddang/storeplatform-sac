package com.skplanet.storeplatform.sac.api.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.skplanet.storeplatform.external.client.shopping.vo.CouponReq;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponRes;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.api.conts.CouponConstants;
import com.skplanet.storeplatform.sac.api.except.CouponException;
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
			return (String) this.commonDAO.queryForObject("Coupon.prodGenerateId", "");
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
			return (String) this.commonDAO.queryForObject("Coupon.prodGenerateId", "");
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, e.getMessage(), null);
		}
	}

	/**
	 * <pre>
	 * 쿠폰ID,아이템ID 가져오기
	 * </pre>
	 */
	@Override
	public String getGenerateId(String scrContentId) {
		try {
			return (String) this.commonDAO.queryForObject("Coupon.getGenerateId", scrContentId);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, e.getMessage(), null);
		}
	}

	/**
	 * <pre>
	 * TB_DP_PROD 테이블 입력및 수정한다.
	 * </pre>
	 */

	@Override
	public void insertTbDpProdInfo(List<TbDpProdInfo> tblDpProdList) {
		try {
			for (TbDpProdInfo vo : tblDpProdList) {
				if ("C".equalsIgnoreCase(vo.getCudType())) {
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
	 * TB_DP_SHPG_PROD 테이블 입력및 수정한다.
	 * </pre>
	 */

	@Override
	public void insertTbDpShpgProdInfo(List<TbDpShpgProdInfo> tbDpShpgProdList) {
		try {
			for (TbDpShpgProdInfo vo : tbDpShpgProdList) {

				if ("C".equalsIgnoreCase(vo.getCudType())) {
					this.commonDAO.insert("Coupon.insertTbDpShpgProdInfo", vo);
				} else { // 수정
					this.commonDAO.update("Coupon.updateTbDpShpgProdInfo", vo);
				}
			}
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}

	}

	/**
	 * <pre>
	 * TB_DP_PROD_DESC 테이블 입력및 수정한다.
	 * </pre>
	 */

	@Override
	public void insertTbDpProdDescInfo(List<TbDpProdDescInfo> tbDpProdDescList) {
		try {
			for (TbDpProdDescInfo vo : tbDpProdDescList) {

				if ("C".equalsIgnoreCase(vo.getCudType())) {
					this.commonDAO.insert("Coupon.insertTbDpProdDescInfo", vo);
				} else { // 수정
					this.commonDAO.update("Coupon.updateTbDpProdDescInfo", vo);
				}
			}
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}

	}

	/**
	 * <pre>
	 * TB_DP_PROD_RSHP 테이블 입력및 수정한다.
	 * </pre>
	 */

	@Override
	public void insertTbDpProdRshpInfo(List<TbDpProdRshpInfo> tbDpProdRshpList) {
		try {
			for (TbDpProdRshpInfo vo : tbDpProdRshpList) {

				if ("C".equalsIgnoreCase(vo.getCudType())) {
					this.commonDAO.insert("Coupon.insertTbDpProdRshpInfo", vo);
				}
			}
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}

	}

	/**
	 * <pre>
	 * TB_DP_TENANT_PROD 테이블 입력및 수정한다.
	 * </pre>
	 */

	@Override
	public void insertTbDpTenantProdInfo(List<TbDpTenantProdInfo> tbDpTenantProdList) {
		try {
			for (TbDpTenantProdInfo vo : tbDpTenantProdList) {

				if ("C".equalsIgnoreCase(vo.getCudType())) {
					this.commonDAO.insert("Coupon.insertTbDpTenantProdInfo", vo);
				} else { // 수정
					this.commonDAO.update("Coupon.updateTbDpTenantProdInfo", vo);
				}
			}
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}

	}

	/**
	 * <pre>
	 * TB_DP_TENANT_PROD_PRICE 테이블 입력및 수정한다.
	 * </pre>
	 */

	@Override
	public void insertTbDpTenantProdPriceInfo(List<TbDpTenantProdPriceInfo> tbDpTenantProdPriceList) {
		try {
			for (TbDpTenantProdPriceInfo vo : tbDpTenantProdPriceList) {

				if ("C".equalsIgnoreCase(vo.getCudType())) {
					this.commonDAO.insert("Coupon.insertTbDpTenantProdPriceInfo", vo);
				} else { // 수정
					this.commonDAO.update("Coupon.updateTbDpTenantProdPriceInfo", vo);
				}
			}
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}

	}

	/**
	 * <pre>
	 * TB_DP_PROD_CATALOG_MAPG 테이블 입력및 수정한다.
	 * </pre>
	 */

	@Override
	public void insertTbDpProdCatalogMapgInfo(List<TbDpProdCatalogMapgInfo> tbDpProdCatalogMapg, String prodId) {
		try {
			for (TbDpProdCatalogMapgInfo vo : tbDpProdCatalogMapg) {

				if ("C".equalsIgnoreCase(vo.getCudType())) {
					this.commonDAO.insert("Coupon.insertTbDpProdCatalogMapgInfo", vo);
				} else { // 수정
					this.commonDAO.update("Coupon.updateTbDpProdCatalogMapgInfo", vo);
				}
			}
			this.commonDAO.update("Coupon.updateDPCouponCNT", prodId);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}

	}

	/**
	 * <pre>
	 * TB_DP_PROD_OPT 테이블 입력및 수정한다.
	 * </pre>
	 */

	@Override
	public void insertTbDpProdOptInfo(List<TbDpProdOpt> tbDpProdOptList) {
		try {
			String prodId = "";
			for (TbDpProdOpt vo : tbDpProdOptList) {
				this.commonDAO.delete("Coupon.deleteTbDpProdOpt", vo);
				break;
			}
			for (TbDpProdOpt vo : tbDpProdOptList) {
				this.commonDAO.insert("Coupon.insertTbDpProdOpt", vo);
				prodId = vo.getChnlProdId();
			}

			if (StringUtils.isNotBlank(prodId)) {
				this.commonDAO.update("Coupon.updateDPYNStatus", prodId);
			}
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}

	}

	/**
	 * <pre>
	 * TB_DP_SPRT_DEVICE 테이블 입력및 수정한다.
	 * </pre>
	 */

	@Override
	public void insertTbDpSprtDeviceInfo(List<TbDpSprtDeviceInfo> tbDpSprtDeviceList) {
		try {
			for (TbDpSprtDeviceInfo vo : tbDpSprtDeviceList) {

				if ("C".equalsIgnoreCase(vo.getCudType())) {
					this.commonDAO.insert("Coupon.insertTbDpSprtDeviceInfo", vo);
				}
			}
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}

	}

	/**
	 * <pre>
	 * TBL_TAG_INFO 테이블 입력한다.
	 * </pre>
	 */
	@Override
	public void insertTblTagInfo(ArrayList<DpCatalogTagInfo> tagList) {
		try {
			boolean delOK = true;
			for (DpCatalogTagInfo vo : tagList) {
				if (delOK) {
					String cId = vo.getCid();
					this.commonDAO.delete("BrandCatalog.DELETE_TBL_TAG_INFO", cId);
					delOK = false;
				}
				this.commonDAO.insert("BrandCatalog.INSERT_TBL_TAG_INFO", vo);
			}
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}

	}

	/**
	 * <pre>
	 * 상태값을 변경 한다.
	 * </pre>
	 */
	@Override
	public void updateCouponStatus(String couponCode, String dpStatusCode, String upType, String itemCode) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("prodId", couponCode);
			map.put("dpStatusCode", dpStatusCode);
			map.put("upType", upType);
			map.put("itemCode", itemCode);

			if (this.commonDAO.update("Coupon.updateDPCouponStatus", map) <= 0) {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC,
						"Coupon.updateDPCouponStatus 실패", null);
			}
			if (this.commonDAO.update("Coupon.updateDPCouponItemCNT", couponCode) <= 0) {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC,
						"Coupon.updateDPcouponItemCNT 실패", null);
			}
			if (this.commonDAO.update("Coupon.updateDPCouponCNT", couponCode) <= 0) {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "Coupon.updateDPcouponCNT 실패",
						null);
			}
			if (!StringUtils.equalsIgnoreCase(upType, "1")) {
				this.commonDAO.update("Coupon.updateDPYNStatus", couponCode);
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
	public List<CouponRes> getSpecialProductList(String[] couponCodes) {
		List<CouponRes> list = new ArrayList<CouponRes>();

		try {

			for (String couponCode : couponCodes) {
				couponCode = couponCode.trim();
				int cnt = (Integer) this.commonDAO.queryForObject("Coupon.GET_SPECIAL_PRODUCT_INFO", couponCode);
				CouponRes crinfo = new CouponRes();
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
	public CouponRes getSpecialProductDetail(String couponCode) {

		CouponRes info = null;

		try {
			int cnt = (Integer) this.commonDAO.queryForObject("Coupon.GET_COUPON_INFO", couponCode);
			if (cnt > 0) {
				info = (CouponRes) this.commonDAO.queryForObject("Coupon.GET_SPECIAL_PRODUCT_DETAIL", couponCode);
				if (info == null) {
					info = new CouponRes();
					info.setRCode(CouponConstants.COUPON_IF_ERROR_CODE_NOT_SPECIAL);
				} else
					info.setRCode("");
			} else {

				info = new CouponRes();
				info.setRCode(CouponConstants.COUPON_IF_ERROR_CODE_COUPONCODE);

			}

		} catch (Exception e) {
			this.log.error("COUPON.GET_SPECIAL_PRODUCT_DETAIL", e);
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ERR, e.getMessage(), null);
		}

		return info;
	}

	@Override
	public ArrayList<String> updateBatchForCouponStatus(ArrayList<CouponReq> couponList) {
		ArrayList<String> result = new ArrayList<String>();
		Calendar now = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		String resultStatus = "";

		for (CouponReq couponCd : couponList) {

			resultStatus = (String) this.commonDAO.queryForObject("Coupon.SELECT_COUPON_VALID_STATUS", couponCd);
			if (!resultStatus.equals(couponCd.getCoupnStatus())) { // 있으면 기존 상태랑 비교한후 다르면 업데이트 한다.
				// 쿠폰 판매상태 변경
				if (couponCd.getUpType().equals("0")) {
					result.add("C::" + sf.format(now.getTime()) + ">>>>쿠폰코드::::" + couponCd.getCouponCode()
							+ ">>>변경 상태:::" + couponCd.getCoupnStatus());
				} else if (couponCd.getUpType().equals("1")) {
					result.add("I::" + sf.format(now.getTime()) + ">>>>아이템 코드::::" + couponCd.getItemCode()
							+ ">>>변경 상태:::" + couponCd.getCoupnStatus());
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("prodId", couponCd.getNewCouponId());
				map.put("dpStatusCode", couponCd.getCoupnStatus());
				map.put("upType", couponCd.getUpType());
				map.put("itemCode", couponCd.getNewItemId());

				// if (this.commonDAO.update("Coupon.updateDPCouponStatus", map) <= 0) {
				// throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC,
				// "Coupon.updateDPCouponStatus 실패", null);
				// }
				// if (this.commonDAO.update("Coupon.updateDPCouponItemCNT", couponCd.getNewCouponId()) <= 0) {
				// throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC,
				// "Coupon.updateDPcouponItemCNT 실패", null);
				// }
				// if (this.commonDAO.update("Coupon.updateDPCouponCNT", couponCd.getNewCouponId()) <= 0) {
				// throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC,
				// "Coupon.updateDPcouponCNT 실패", null);
				// }
				// this.commonDAO.update("Coupon.updateDPYNStatus", couponCd.getNewCouponId());

			}
		}
		return result;
	}

}
