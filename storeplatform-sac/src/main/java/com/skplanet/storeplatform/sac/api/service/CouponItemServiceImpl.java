package com.skplanet.storeplatform.sac.api.service;

import com.skplanet.storeplatform.external.client.shopping.vo.*;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.api.conts.CouponConstants;
import com.skplanet.storeplatform.sac.api.except.CouponException;
import com.skplanet.storeplatform.sac.api.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

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
    @Qualifier("sac")
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
	 * 쿠폰코드 ID 를 이용 상품유명코드 조회.
	 * </pre>
	 * 
	 * @param couponCode
	 *            couponCode
	 * @return String
	 */
	@Override
	public String getCouponProdCaseCode(String couponCode) {
		try {
			return (String) this.commonDAO.queryForObject("Coupon.getCouponProdCaseCode", couponCode);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, e.getMessage(), null);
		}
	}

	/**
	 * <pre>
	 * 에피소드id를 이용해 특가 여부 조회.
	 * </pre>
	 * 
	 * @param episodeId
	 *            episodeId
	 * @return String
	 */
	@Override
	public int getSpecialProdCnt(String episodeId) {
		try {
			return (Integer) this.commonDAO.queryForObject("Coupon.getSpecialProdCnt", episodeId);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, e.getMessage(), null);
		}
	}

	/**
	 * <pre>
	 * One Store 에피소드id를 이용해 특가 여부 조회.
	 * </pre>
	 *
	 * @param reqMap
	 *            reqMap
	 * @return String
	 */
	@Override
	public int getOneBrandSpecialProdCnt(Map<String, Object> reqMap) {
		try {
			return (Integer) this.commonDAO.queryForObject("Coupon.getOneBrandSpecialProdCnt", reqMap);
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
	public void insertTbDpTenantProdInfo(List<TbDpTenantProdInfo> tbDpTenantProdList , DpCouponInfo couponInfo) {
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
			this.log.info("■■■■■ updateCatalogTenantStatus Start ■■■■■");

			List<Map<String, Object>> couponList = new ArrayList<Map<String, Object>>();
			Map<String, Object> coupon = new HashMap<String, Object>();

			if (StringUtils.isNotBlank(couponInfo.getCoupnStatus())) {
				coupon.put("tempTenantId", CouponConstants.TENANT_ID);
				couponList.add(coupon);
			}
			if (StringUtils.isNotBlank(couponInfo.getCoupnStatus_kt())) {
				coupon = new HashMap<String, Object>();
				coupon.put("tempTenantId", CouponConstants.TENANT_ID_S02);
				couponList.add(coupon);
			}
			if (StringUtils.isNotBlank(couponInfo.getCoupnStatus_lgt())) {
				coupon = new HashMap<String, Object>();
				coupon.put("tempTenantId", CouponConstants.TENANT_ID_S03);
				couponList.add(coupon);
			}
			for(int kk =0 ; kk< couponList.size() ; kk++) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("catalogId", couponInfo.getStoreCatalogCode());
				map.put("prodId", couponInfo.getProdId());
				map.put("tenentId", (String) couponList.get(kk).get("tempTenantId"));
				map.put("regId", couponInfo.getBpId());
				map.put("updId", couponInfo.getBpId());
				this.commonDAO.update("Coupon.updateCatalogTenantStatus", map);
				this.log.info("■■■■■ updateCatalogTenantStatus End ■■■■■");
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
	 * TB_DP_PROD_TAG 테이블 입력및 수정한다.
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
	 * @param couponList
	 *            couponList
	 * @param upType
	 *            upType
	 * @param itemCode
	 *            itemCode
	 */
	@Override
	public void updateCouponStatus(String couponCode, List<Map<String, Object>> couponList, String upType, String itemCode) {
		try {

			String catalogId = (String) this.commonDAO.queryForObject("Coupon.getShoppingCatalogIdByChannelId", couponCode);

			for(int kk =0 ; kk< couponList.size() ;kk++){

				Map<String, String> map = new HashMap<String, String>();
				map.put("prodId", couponCode);
				map.put("dpStatusCode", (String) couponList.get(kk).get("tempCouponStatus"));
				map.put("upType", upType);
				map.put("itemCode", itemCode);
				map.put("tenentId", (String) couponList.get(kk).get("tempTenantId"));
				map.put("catalogId", catalogId);
				map.put("regId", "SAC");
				map.put("updId", "SAC");

				if (this.commonDAO.update("Coupon.updateDPCouponStatus", map) <= 0) {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC,
							"Coupon.updateDPCouponStatus 실패", null);
				}
				if (this.commonDAO.update("Coupon.updateDPCouponItemCNT", map) <= 0) {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC,
							"Coupon.updateDPcouponItemCNT 실패", null);
				}
				if (this.commonDAO.update("Coupon.updateCatalogTenantStatus", map) <= 0) {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "Coupon.updateCatalogTenantStatus 실패",
							null);
				}
				if (!StringUtils.equalsIgnoreCase(upType, "0")) {
					this.commonDAO.update("Coupon.updateDPYNStatus", map);
				}

				// tb_dp_prod 최초 판매시간 2014.05.22 일 수정
				if (CouponConstants.DP_STATUS_IN_SERVICE.equals((String) couponList.get(kk).get("tempCouponStatus"))) {
					this.commonDAO.update("Coupon.updateTbDpProdLastDeployDt", map);
				}

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
			this.log.error("Coupon.GET_SPECIAL_PRODUCT_INFO", e);
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ERR, e.getMessage(), null);
		}

		return list;
	}

	/**
	 * <pre>
	 * One Store 특가 상품 목록 조회 한다.
	 * </pre>
	 *
	 * @param itemCodes
	 *            itemCodes
	 * @return List<CouponRes>
	 */
	@Override
	public List<CouponRes> getOneBrandSpecialProductList(String[] itemCodes) {
		List<CouponRes> list = new ArrayList<CouponRes>();

		try {

			for (String itemCode : itemCodes) {
				itemCode = itemCode.trim();
				Map<String, String> map = new HashMap<String, String>();
				map.put("itemCode", itemCode);
				for(int kk =0 ; kk <3 ; kk++) {
					CouponRes crinfo = new CouponRes();
					crinfo.setCouponCode(itemCode);
					if(kk ==0) {
						map.put("tenantId", CouponConstants.TENANT_ID);
						crinfo.setTenantId(CouponConstants.TENANT_ID);
					}else if(kk ==1){
						map.put("tenantId", CouponConstants.TENANT_ID_S02);
						crinfo.setTenantId(CouponConstants.TENANT_ID_S02);
					}else if(kk ==2){
						map.put("tenantId", CouponConstants.TENANT_ID_S03);
						crinfo.setTenantId(CouponConstants.TENANT_ID_S03);
					}
					int cnt = (Integer) this.commonDAO.queryForObject("Coupon.GET_ONE_BRAND_SPECIAL_PRODUCT_INFO", map);

					if (cnt > 0) {
						crinfo.setSpecialYN("Y");
					} else {
						int tingCnt = (Integer) this.commonDAO.queryForObject("Coupon.GET_ONE_BRAND_TING_PRODUCT_INFO", map);
						if (tingCnt > 0) {
							crinfo.setSpecialYN("T");
						} else {
							crinfo.setSpecialYN("N");
						}
					}
					list.add(crinfo);
				}
			}

		} catch (CouponException e) {
			throw e;
		} catch (Exception e) {
			this.log.error("Coupon.GET_SPECIAL_PRODUCT_INFO", e);
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
	 * @param itemsCodes
	 *            itemsCodes
	 * @return CouponRes
	 */

	@Override
	public CouponRes getSpecialProductDetail(String couponCode, String[] itemsCodes) {

		CouponRes info = new CouponRes();
		
		List<EventInfo> eventInfoList =  new ArrayList<EventInfo>();  
		
		boolean specialFlag = false;		// 특가 상품인지 확인 flag
		boolean itemFlag = false; 			// 정상적인 아이템인지 확인 flag

		if (StringUtils.isBlank(couponCode)) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "필수 파라미터 값이 없습니다. (couponCode)", null);
		}
		for (String strItem : itemsCodes) {
			if (StringUtils.isBlank(strItem)) {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "필수 파라미터 값이 없습니다. (itemsCodes)", null);
			}
		}
		
		int cnt = (Integer) this.commonDAO.queryForObject("Coupon.GET_COUPON_INFO", couponCode);
		if (cnt <=0) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "쇼핑 상품에 대한 쿠폰ID가 아닙니다.", null);
		}		
		
		for (String strItem : itemsCodes) {
			try {
				Map<String, String> map = new HashMap<String, String>();
				map.put("couponCode", couponCode);
				map.put("itemCode", strItem);
				EventInfo eventInfo = null;
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
				this.log.error("Coupon.GET_COUPON_INFO", e);
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ERR, e.getMessage(), null);
			}
		}
		
		if(!itemFlag){
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "쇼핑 상품에 대한 아이템ID가 아닙니다.", null);
		}				

		if(!specialFlag){
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_NOT_SPECIAL, "특가상품 없음", null);
		}

		info.setEventInfoList(eventInfoList);
		return info;
	}


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

	@Override
	public CouponRes getOneBrandSpecialProductDetail(String couponCode, String[] itemsCodes) {

		CouponRes info = new CouponRes();

		List<ItemCodeInfo> itemCodeInfoList =  new ArrayList<ItemCodeInfo>();

		boolean specialFlag = false;		// 특가 상품인지 확인 flag
		boolean itemFlag = false; 			// 정상적인 아이템인지 확인 flag

		if (StringUtils.isBlank(couponCode)) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "필수 파라미터 값이 없습니다. (couponCode)", null);
		}
		for (String strItem : itemsCodes) {
			if (StringUtils.isBlank(strItem)) {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "필수 파라미터 값이 없습니다. (itemsCodes)", null);
			}
		}

		int cnt = (Integer) this.commonDAO.queryForObject("Coupon.GET_COUPON_INFO", couponCode);
		if (cnt <=0) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "쇼핑 상품에 대한 쿠폰ID가 아닙니다.", null);
		}

		for (String strItem : itemsCodes) {
			try {
				Map<String, String> map = new HashMap<String, String>();
				map.put("couponCode", couponCode);
				map.put("itemCode", strItem);
				ItemCodeInfo itemCodeInfo = null;
				List<String> tenantIdList =  null;
				TenantIdList tenantIdVoList = new TenantIdList();
				cnt = (Integer) this.commonDAO.queryForObject("Coupon.GET_COUPON_INFO", strItem);
				if (cnt > 0) {
					itemCodeInfo = (ItemCodeInfo) this.commonDAO.queryForObject("Coupon.GET_ONE_BRAND_SPECIAL_PRODUCT_DETAIL", map);
					if (itemCodeInfo != null) {
						tenantIdList  = (List<String>) this.commonDAO.queryForList("Coupon.GET_ONE_BRAND_TENANT_ID_SPECIAL_PRODUCT_DETAIL", map);
						tenantIdVoList.setTenantId(tenantIdList);
						itemCodeInfo.setTenantIdList(tenantIdVoList);
						itemCodeInfoList.add(itemCodeInfo);
						specialFlag = true;
					}
					itemFlag =true;
				}

			} catch (CouponException e) {
				throw e;
			} catch (Exception e) {
				this.log.error("Coupon.GET_COUPON_INFO", e);
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ERR, e.getMessage(), null);
			}
		}

		if(!itemFlag){
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "쇼핑 상품에 대한 아이템ID가 아닙니다.", null);
		}

		if(!specialFlag){
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_NOT_SPECIAL, "특가상품 없음", null);
		}

		info.setItemCodeInfo(itemCodeInfoList);
		return info;
	}

	/**
	 * <pre>
	 * 특정 기간에 대한 특가 상품 상세 조회 작업을 호출한다.
	 * </pre>
	 * 
	 * @param couponReq
	 *            couponReq
	 * @return CouponRes
	 */
	@Override
	public CouponRes getSpecialProductDetailForDate(CouponReq couponReq) {

		CouponRes info = new CouponRes();
		
		List<EventInfo> eventInfoList = null;

		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("startDate", couponReq.getStartDate());
			map.put("endDate", couponReq.getEndDate());
			
			eventInfoList = (List<EventInfo>) this.commonDAO.queryForList("Coupon.GET_SPECIAL_PRODUCT_DETAIL_LIST_FOR_DATE", map);
		
		} catch (CouponException e) {
			throw e;
		} catch (Exception e) {
			this.log.error("Coupon.GET_SPECIAL_PRODUCT_DETAIL_LIST_FOR_DATE", e);
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ERR, e.getMessage(), null);
		}
		

		info.setEventInfoList(eventInfoList);
		return info;
	}

	/**
	 * <pre>
	 * One Store 특정 기간에 대한 특가 상품 상세 조회 작업을 호출한다.
	 * </pre>
	 *
	 * @param couponReq
	 *            couponReq
	 * @return CouponRes
	 */
	@Override
	public CouponRes getOneBrandSpecialProductDetailForDate(CouponReq couponReq) {

		CouponRes info = new CouponRes();
		List<ItemCodeInfo> itemCodeInfoList =  null;
		List<TenantSprcInfo> tenantSprcInfoList =  null;

		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("startDate", couponReq.getStartDate());
			map.put("endDate", couponReq.getEndDate());

			itemCodeInfoList = (List<ItemCodeInfo>) this.commonDAO.queryForList("Coupon.GET_ONE_BRAND_SPECIAL_PRODUCT_DETAIL_LIST_FOR_DATE", map);

			for(ItemCodeInfo vo : itemCodeInfoList){
				map.put("itemCode", vo.getItemCode());
				tenantSprcInfoList = (List<TenantSprcInfo>) this.commonDAO.queryForList("Coupon.GET_ONE_BRAND_TENANT_ID_SPECIAL_PRODUCT_DETAIL_LIST_FOR_DATE", map);
				vo.setTenantSprcInfo(tenantSprcInfoList);
			}

		} catch (CouponException e) {
			throw e;
		} catch (Exception e) {
			this.log.error("Coupon.GET_ONE_BRAND_SPECIAL_PRODUCT_DETAIL_LIST_FOR_DATE", e);
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ERR, e.getMessage(), null);
		}

		info.setItemCodeInfo(itemCodeInfoList);
		return info;
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

	/**
	 * <pre>
	 * 실제로 판매하는 상품을 조회 한다.
	 * </pre>
	 * 
	 * @param reqMap
	 *            reqMap
	 * @return String
	 */
	@Override
	public String getShoppingIngYn(Map<String, Object> reqMap) {
		String ingYn = (String) this.commonDAO.queryForObject("Coupon.getShoppingIngYn", reqMap);
		return ingYn;
	}

	/**
	 * <pre>
	 * 채널ID를 이용 카탈로그ID 조회 한다.
	 * </pre>
	 * 
	 * @param channelId
	 *            channelId
	 * @return String
	 */
	@Override
	public String getShoppingCatalogIdByChannelId(String channelId) {
		String catalogId = (String) this.commonDAO.queryForObject("Coupon.getShoppingCatalogIdByChannelId", channelId);
		return catalogId;
	}

	/**
	 * <pre>
	 * 특가(팅) 상품에 상태값을 변경 한다(판매중지 전용).
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
	@Override
	public void updateCouponStatusForSpecialProd(String newCouponCode, String dpStatusCode, String upType,
			String itemCodes) {
		try {
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("prodId", newCouponCode);
			map.put("dpStatusCode", dpStatusCode);
			map.put("upType", upType);
			String catalogId = (String) this.commonDAO.queryForObject("Coupon.getShoppingCatalogIdByChannelId", newCouponCode);
			map.put("catalogId", catalogId);
			map.put("regId", "SAC");
			map.put("updId", "SAC");

			map.put("tenentId", CouponConstants.TENANT_ID);
			if (this.commonDAO.update("Coupon.updateDPCouponStatus", map) <= 0) {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC,
						"Coupon.updateDPCouponStatus 실패", null);
			}
			if (this.commonDAO.update("Coupon.updateDPCouponItemCNT", map) <= 0) {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC,
						"Coupon.updateDPcouponItemCNT 실패", null);
			}

			if (this.commonDAO.update("Coupon.updateCatalogTenantStatus", map) <= 0) {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "Coupon.updateCatalogTenantStatus 실패",
						null);
			}
			if (!StringUtils.equalsIgnoreCase(upType, "0")) {
				this.commonDAO.update("Coupon.updateDPYNStatus", map);
			}
			
			String[] episodeIds = itemCodes.split(",");
			for (String episodeId : episodeIds) {
				map.put("itemCode", episodeId);
				
				if (this.commonDAO.update("Coupon.updateSpecialProdStop", map) <= 0) {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC,
							"Coupon.updateSpecialProdStop 실패", null);
				}
			}

		} catch (CouponException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, e.getMessage(), null);
		}

		
	}

	/**
	 * <pre>
	 * One Store 특가(팅) 상품에 상태값을 변경 한다(판매중지 전용).
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
	@Override
	public void updateCouponStatusForOneBrandSpecialProd(String newCouponCode, String dpStatusCode, String upType,String itemCodes,String[] tenantIds) {
		try {

			Map<String, String> map = new HashMap<String, String>();
			map.put("prodId", newCouponCode);
			map.put("dpStatusCode", dpStatusCode);
			map.put("upType", upType);

			String catalogId = (String) this.commonDAO.queryForObject("Coupon.getShoppingCatalogIdByChannelId", newCouponCode);
			map.put("catalogId", catalogId);
			map.put("regId", "SAC");
			map.put("updId", "SAC");

			for (String tenantId : tenantIds) {
				map.put("tenentId", tenantId );

				if (this.commonDAO.update("Coupon.updateDPCouponStatus", map) <= 0) {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC,
							"Coupon.updateDPCouponStatus 실패", null);
				}
				if (this.commonDAO.update("Coupon.updateDPCouponItemCNT", map) <= 0) {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC,
							"Coupon.updateDPcouponItemCNT 실패", null);
				}

				if (this.commonDAO.update("Coupon.updateCatalogTenantStatus", map) <= 0) {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "Coupon.updateCatalogTenantStatus 실패",
							null);
				}

				if (!StringUtils.equalsIgnoreCase(upType, "0")) {
					this.commonDAO.update("Coupon.updateDPYNStatus", map);

				}


				String[] episodeIds = itemCodes.split(",");
				for (String episodeId : episodeIds) {
					map.put("itemCode", episodeId);

					if (this.commonDAO.update("Coupon.updateSpecialProdStop", map) <= 0) {
						throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC,
								"Coupon.updateSpecialProdStop 실패", null);
					}
				}
			}
		} catch (CouponException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, e.getMessage(), null);
		}
	}

	/**
	 * <pre>
	 * 팅/특가 쿠폰 ID 조회 한다.
	 * </pre>
	 * 
	 * @param episodeId
	 *            episodeId
	 * @return CouponRes
	 */

	@Override
	public String getSpecialProductCouponId(String episodeId) {
		String couponId = (String) this.commonDAO.queryForObject("Coupon.getSpecialProductCouponId", episodeId);
		return couponId;
	}

	/**
	 * <pre>
	 * One Store 팅/특가 쿠폰 ID 조회 한다.
	 * </pre>
	 *
	 * @param reqMap
	 *            reqMap
	 * @return List<String>
	 */

	@Override
	public List<String> getOneBrandSpecialProductCouponId(Map<String, Object> reqMap) {
		List<String> couponIdList = (List<String>) this.commonDAO.queryForList("Coupon.getOneBrandSpecialProductCouponId", reqMap);
		return couponIdList;
	}


	/**
	 * <pre>
	 * 쿠폰코드를 이용 아이템 코드값을 가져온다.
	 * </pre>
	 * 
	 * @param channelId
	 *            channelId
	 * @return ArrayList<String>
	 */
	@Override
	public ArrayList<String> getCouponCompareItemCode(String channelId) {
		List<String> itemList = (List<String>) this.commonDAO.queryForList("Coupon.getCouponCompareItemCode", channelId);
		return (ArrayList<String>) itemList;
	}
	
	
	
}
