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

import com.skplanet.storeplatform.external.client.shopping.vo.CouponReq;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponRes;
import com.skplanet.storeplatform.external.client.shopping.vo.EventInfo;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.api.conts.CouponConstants;
import com.skplanet.storeplatform.sac.api.except.CouponException;
import com.skplanet.storeplatform.sac.api.vo.BrandCatalogProdImgInfo;
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
 * 쿠폰,아이템 서비스 인터페이스 Imple.
 * </pre>
 * 
 * Created on : 2014-01-03 Created by : 김형식, SK 플래닛. Last Updated on : 2014-01-03 Last Updated by : 김형식, SK 플래닛
 */
@Service
public class CouponItemServiceImpl implements CouponItemService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	@Qualifier("cmsApp")
	private CommonDAO commonDAO;

	/**
	 * <pre>
	 * 쿠폰ID 생성.
	 * </pre>
	 * 
	 * @return String
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
	 * 아이템ID 생성.
	 * </pre>
	 * 
	 * @return String
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
	 * 쿠폰ID 가져오기.
	 * </pre>
	 * 
	 * @param scrContentId
	 *            scrContentId
	 * @return int
	 */
	@Override
	public int getCouponCountCudType(String scrContentId) {
		try {
			return (Integer) this.commonDAO.queryForObject("Coupon.getCouponCountCudType", scrContentId);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, e.getMessage(), null);
		}
	}

	/**
	 * <pre>
	 * 아이템ID 가져오기.
	 * </pre>
	 * 
	 * @param scrContentId
	 *            scrContentId
	 * @return int
	 */
	@Override
	public int getItemCountCudType(String scrContentId) {
		try {
			return (Integer) this.commonDAO.queryForObject("Coupon.getItemCountCudType", scrContentId);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, e.getMessage(), null);
		}
	}

	/**
	 * <pre>
	 * 쿠폰ID 가져오기.
	 * </pre>
	 * 
	 * @param scrContentId
	 *            scrContentId
	 * @return String
	 */
	@Override
	public String getCouponGenerateId(String scrContentId) {
		try {
			return (String) this.commonDAO.queryForObject("Coupon.getCouponGenerateId", scrContentId);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, e.getMessage(), null);
		}
	}

	/**
	 * <pre>
	 * 아이템ID 가져오기.
	 * </pre>
	 * 
	 * @param scrContentId
	 *            scrContentId
	 * @return String
	 */
	@Override
	public String getItemGenerateId(String scrContentId) {
		try {
			return (String) this.commonDAO.queryForObject("Coupon.getItemGenerateId", scrContentId);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, e.getMessage(), null);
		}
	}

	/**
	 * <pre>
	 * TB_DP_PROD 테이블 입력및 수정한다.
	 * </pre>
	 * 
	 * @param tblDpProdList
	 *            tblDpProdList
	 * @return
	 */

	@Override
	public void insertTbDpProdInfo(List<TbDpProdInfo> tblDpProdList) {
		try {
			for (TbDpProdInfo vo : tblDpProdList) {
				if ("C".equalsIgnoreCase(vo.getCudType())) {
					this.commonDAO.insert("Coupon.createTbDpProdInfo", vo);
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
	 * 
	 * @param tbDpShpgProdList
	 *            tbDpShpgProdList
	 * @return
	 */

	@Override
	public void insertTbDpShpgProdInfo(List<TbDpShpgProdInfo> tbDpShpgProdList) {
		try {
			for (TbDpShpgProdInfo vo : tbDpShpgProdList) {

				if ("C".equalsIgnoreCase(vo.getCudType())) {
					this.commonDAO.insert("Coupon.createTbDpShpgProdInfo", vo);
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
	 * 
	 * @param tbDpProdDescList
	 *            tbDpProdDescList
	 */

	@Override
	public void insertTbDpProdDescInfo(List<TbDpProdDescInfo> tbDpProdDescList) {
		try {
			for (TbDpProdDescInfo vo : tbDpProdDescList) {

				if ("C".equalsIgnoreCase(vo.getCudType())) {
					this.commonDAO.insert("Coupon.createTbDpProdDescInfo", vo);
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
	 * 
	 * @param tbDpProdRshpList
	 *            tbDpProdRshpList
	 */

	@Override
	public void insertTbDpProdRshpInfo(List<TbDpProdRshpInfo> tbDpProdRshpList) {
		try {
			for (TbDpProdRshpInfo vo : tbDpProdRshpList) {

				if ("C".equalsIgnoreCase(vo.getCudType())) {
					this.commonDAO.insert("Coupon.createTbDpProdRshpInfo", vo);
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
	 * 
	 * @param tbDpTenantProdList
	 *            tbDpTenantProdList
	 */

	@Override
	public void insertTbDpTenantProdInfo(List<TbDpTenantProdInfo> tbDpTenantProdList) {
		try {
			for (TbDpTenantProdInfo vo : tbDpTenantProdList) {

				if ("C".equalsIgnoreCase(vo.getCudType())) {
					this.commonDAO.insert("Coupon.createTbDpTenantProdInfo", vo);
				} else { // 수정
					this.commonDAO.update("Coupon.updateTbDpTenantProdInfo", vo);
				}
				// tb_dp_prod 최초 판매시간 2014.05.22 일 수정
				if (CouponConstants.DP_STATUS_IN_SERVICE.equals(vo.getProdStatusCd())) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("prodId", vo.getProdId());
					map.put("upType", "0");
					this.commonDAO.update("Coupon.updateTbDpProdLastDeployDt", map);
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
	 * 
	 * @param tbDpTenantProdPriceList
	 *            tbDpTenantProdPriceList
	 */
	@Override
	public void insertTbDpTenantProdPriceInfo(List<TbDpTenantProdPriceInfo> tbDpTenantProdPriceList) {
		try {
			for (TbDpTenantProdPriceInfo vo : tbDpTenantProdPriceList) {

				if ("C".equalsIgnoreCase(vo.getCudType())) {
					this.commonDAO.insert("Coupon.createTbDpTenantProdPriceInfo", vo);
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
	 * 
	 * @param tbDpProdCatalogMapg
	 *            tbDpProdCatalogMapg
	 * @param prodId
	 *            prodId
	 */
	@Override
	public void insertTbDpProdCatalogMapgInfo(List<TbDpProdCatalogMapgInfo> tbDpProdCatalogMapg, String prodId) {
		try {
			for (TbDpProdCatalogMapgInfo vo : tbDpProdCatalogMapg) {

				if ("C".equalsIgnoreCase(vo.getCudType())) {
					this.commonDAO.insert("Coupon.createTbDpProdCatalogMapgInfo", vo);
				} else { // 수정
					// 2014.03.14 쇼핑 상품 고도화건 (쇼핑상품 변경연동시 카탈로그 변경여부 확인하여 카탈로그 변경시 연동Error Return 로직 추가)
					String resuntCatalog = (String) this.commonDAO.queryForObject(
							"Coupon.selectTbDpProdCatalogMapgInfo", vo);
					if (vo.getCatalogId().equals(resuntCatalog)) {
						this.commonDAO.update("Coupon.updateTbDpProdCatalogMapgInfo", vo);
					} else {
						throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_CATALOG_DIFFERENT_PROD,
								"기존 등록된 상품과 CATALOG_ID가 다릅니다.", null);
					}
				}
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("prodId", prodId);
			map.put("tenentId", CouponConstants.TENANT_ID);

			this.commonDAO.update("Coupon.updateDPCouponCNT", map);

		} catch (CouponException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}

	}

	/**
	 * <pre>
	 * TB_DP_PROD_OPT 테이블 입력및 수정한다.
	 * </pre>
	 * 
	 * @param tbDpProdOptList
	 *            tbDpProdOptList
	 */
	@Override
	public void insertTbDpProdOptInfo(List<TbDpProdOpt> tbDpProdOptList) {
		try {
			String prodId = "";
			for (TbDpProdOpt vo : tbDpProdOptList) {
				this.commonDAO.delete("Coupon.removeTbDpProdOpt", vo);
				break;
			}
			for (TbDpProdOpt vo : tbDpProdOptList) {
				this.commonDAO.insert("Coupon.createTbDpProdOpt", vo);
				prodId = vo.getChnlProdId();
			}

			if (StringUtils.isNotBlank(prodId)) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("prodId", prodId);
				map.put("tenentId", CouponConstants.TENANT_ID);
				this.commonDAO.update("Coupon.updateDPYNStatus", map);
			}
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}

	}

	/**
	 * <pre>
	 * TB_DP_SPRT_DEVICE 테이블 입력한다.
	 * </pre>
	 * 
	 * @param tbDpSprtDeviceList
	 *            tbDpSprtDeviceList
	 */

	@Override
	public void insertTbDpSprtDeviceInfo(List<TbDpSprtDeviceInfo> tbDpSprtDeviceList) {
		try {
			for (TbDpSprtDeviceInfo vo : tbDpSprtDeviceList) {

				if ("C".equalsIgnoreCase(vo.getCudType())) {
					this.commonDAO.insert("Coupon.createTbDpSprtDeviceInfo", vo);
				}
			}
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}

	}

	/**
	 * <pre>
	 * TBL_TAG_INFO 테이블 입력및 수정한다.
	 * </pre>
	 * 
	 * @param tagList
	 *            tagList
	 */

	@Override
	public void insertTblTagInfo(ArrayList<DpCatalogTagInfo> tagList) {
		try {
			boolean delOK = true;
			for (DpCatalogTagInfo vo : tagList) {
				if (delOK) {
					String cId = vo.getCid();
					this.commonDAO.delete("BrandCatalog.removeTbDpProdTag", cId);
					delOK = false;
				}
				this.commonDAO.insert("BrandCatalog.createTbDpProdTag", vo);
			}
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}

	}

	/**
	 * <pre>
	 * SP_REGIST_PROD 프로시저 호출한다.
	 * </pre>
	 * 
	 * @param spRegistProdList
	 *            spRegistProdList
	 */

	@Override
	public void insertCallSpSettRegProd(List<SpRegistProd> spRegistProdList) {
		try {
			HashMap<String, String> param = new HashMap<String, String>();

			for (SpRegistProd vo : spRegistProdList) {
				param.put("prodId", vo.getProdId());
				param.put("settlRt", String.valueOf(vo.getSettlRt()));
				param.put("saleMbrNo", null);
				param.put("saleStdDt", null);
				param.put("saleEndDt", null);
				param.put("regId", vo.getRegId());
				param.put("rtn", "");
				this.commonDAO.queryForObject("Coupon.selectSpSettleRegProd", param);
				String result = param.get("rtn");
				this.log.info("정산율 배포 결과 : " + result);

				if (!("S".equals(result) || "BP".equals(result))) {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "정산율 배포 실패!!", null);
				}
			}

		} catch (CouponException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_QUESTION, e.getMessage(), null);
		}

	}

	/**
	 * <pre>
	 * 상태값을 변경 한다.
	 * </pre>
	 * 
	 * @param couponCode
	 *            couponCode
	 * @param dpStatusCode
	 *            dpStatusCode
	 * @param upType
	 *            upType
	 * @param itemCode
	 *            itemCode
	 */
	@Override
	public void updateCouponStatus(String couponCode, String dpStatusCode, String upType, String itemCode) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("prodId", couponCode);
			map.put("dpStatusCode", dpStatusCode);
			map.put("upType", upType);
			map.put("itemCode", itemCode);
			map.put("tenentId", CouponConstants.TENANT_ID);

			if (this.commonDAO.update("Coupon.updateDPCouponStatus", map) <= 0) {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC,
						"Coupon.updateDPCouponStatus 실패", null);
			}
			if (this.commonDAO.update("Coupon.updateDPCouponItemCNT", map) <= 0) {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC,
						"Coupon.updateDPcouponItemCNT 실패", null);
			}
			if (this.commonDAO.update("Coupon.updateDPCouponCNT", map) <= 0) {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "Coupon.updateDPcouponCNT 실패",
						null);
			}
			if (!StringUtils.equalsIgnoreCase(upType, "0")) {
				this.commonDAO.update("Coupon.updateDPYNStatus", map);
			}

			// tb_dp_prod 최초 판매시간 2014.05.22 일 수정
			if (CouponConstants.DP_STATUS_IN_SERVICE.equals(dpStatusCode)) {
				this.commonDAO.update("Coupon.updateTbDpProdLastDeployDt", map);
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
	 * 
	 * @param couponCodes
	 *            couponCodes
	 * @return List<CouponRes>
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
				if (cnt > 0) {
					crinfo.setSpecialYN("Y");
				} else {
					int tingCnt = (Integer) this.commonDAO.queryForObject("Coupon.GET_TING_PRODUCT_INFO", couponCode);
					if (tingCnt > 0) {
						crinfo.setSpecialYN("T");
					} else {
						crinfo.setSpecialYN("N");
					}
				}
				list.add(crinfo);
			}

		} catch (CouponException e) {
			throw e;
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
	 * 
	 * @param couponCode
	 *            couponCode
	 * @return CouponRes
	 */

	@Override
	public CouponRes getSpecialProductDetail(String couponCode, String[] itemsCodes) {

		CouponRes info = new CouponRes();
		
		List<EventInfo> eventInfoList =  new ArrayList<EventInfo>();  
		
		boolean specialFlag = false;		// 특가 상품인지 확인 flag
		boolean itemFlag = false; 			// 정상적인 아이템인지 확인 flag


		
		int cnt = (Integer) this.commonDAO.queryForObject("Coupon.GET_COUPON_INFO", couponCode);
		if (cnt <=0) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_COUPONCODE, "잘못된 쿠폰ID", null);
		}		
		
		for (String strItem : itemsCodes) {
			try {
				Map<String, String> map = new HashMap<String, String>();
				map.put("couponCode", couponCode);
				map.put("itemCode", strItem);
				EventInfo eventInfo = new EventInfo();
				cnt = (Integer) this.commonDAO.queryForObject("Coupon.GET_COUPON_INFO", strItem);
				if (cnt > 0) {
					eventInfo = (EventInfo) this.commonDAO.queryForObject("Coupon.GET_SPECIAL_PRODUCT_DETAIL", map);
					if (eventInfo != null) {
						eventInfoList.add(eventInfo);
						specialFlag = true;
					}
					itemFlag =true;
				} 
				
			} catch (CouponException e) {
				throw e;
			} catch (Exception e) {
				this.log.error("COUPON.GET_SPECIAL_PRODUCT_DETAIL", e);
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ERR, e.getMessage(), null);
			}
		}
		
		if(!itemFlag){
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_ITEMCODE, "잘못된 아이템ID", null);
		}				

		if(!specialFlag){
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_NOT_SPECIAL, "특가상품 없음", null);
		}

		info.setEventInfoList(eventInfoList);
		return info;
	}

	/**
	 * 쿠폰(아이템) 판매상태 변경.
	 * 
	 * @param couponList
	 *            couponList
	 * @return ArrayList<String>
	 */
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
				map.put("tenentId", CouponConstants.TENANT_ID);
				// if (this.commonDAO.update("Coupon.updateDPCouponStatus", map) <= 0) {
				// throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC,
				// "Coupon.updateDPCouponStatus 실패", null);
				// }
				// if (this.commonDAO.update("Coupon.updateDPCouponItemCNT", map) <= 0) {
				// throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC,
				// "Coupon.updateDPcouponItemCNT 실패", null);
				// }
				// if (this.commonDAO.update("Coupon.updateDPCouponCNT", map) <= 0) {
				// throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC,
				// "Coupon.updateDPcouponCNT 실패", null);
				// }
				// this.commonDAO.update("Coupon.updateDPYNStatus", map);

			}
		}
		return result;
	}

	/**
	 * 카탈로그 및 메뉴ID 조회 한다.
	 * 
	 * @param catalogId
	 *            catalogId
	 * @return CouponRes
	 */
	@Override
	public CouponRes getCatalogNmMenuId(String catalogId) {
		CouponRes info = null;
		info = (CouponRes) this.commonDAO.queryForObject("Coupon.selectCatalogNmMenuId", catalogId);
		return info;
	}

}
