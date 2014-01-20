package com.skplanet.storeplatform.sac.api.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.external.client.shopping.inf.ITX_TYPE_CODE.TX_TYPE_CODE;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponReq;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponRes;
import com.skplanet.storeplatform.external.client.shopping.vo.DpCouponInfo;
import com.skplanet.storeplatform.external.client.shopping.vo.DpItemInfo;
import com.skplanet.storeplatform.sac.api.conts.CouponConstants;
import com.skplanet.storeplatform.sac.api.except.CouponException;
import com.skplanet.storeplatform.sac.api.inf.IcmsJobPrint;
import com.skplanet.storeplatform.sac.api.service.CouponItemService;
import com.skplanet.storeplatform.sac.api.service.CouponProcessService;
import com.skplanet.storeplatform.sac.api.service.ShoppingCouponService;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.api.vo.BrandCatalogProdImgInfo;
import com.skplanet.storeplatform.sac.api.vo.CouponContainer;
import com.skplanet.storeplatform.sac.api.vo.DpBrandInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogInfo;
import com.skplanet.storeplatform.sac.client.display.vo.shopping.ShoppingRes;

@Controller
@RequestMapping("/shopping")
public class ShoppingCouponSacController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private String ERR_MESSAGE;
	private String ERR_CODE;
	private String PARAMETER_STR;
	public final DpBrandInfo brandInfo;
	public final DpCatalogInfo catalogInfo;
	public final BrandCatalogProdImgInfo brandCatalogProdImgInfo;
	private CouponContainer containers;
	private List<CouponRes> couponList = null;
	public CouponRes couponRes;
	public String response;

	@Autowired
	private ShoppingCouponService shoppingCouponService;

	@Autowired
	private CouponItemService couponItemService;

	@Autowired
	private CouponProcessService couponProcessService;

	public ShoppingCouponSacController() {
		this.brandInfo = new DpBrandInfo();
		this.catalogInfo = new DpCatalogInfo();
		this.brandCatalogProdImgInfo = new BrandCatalogProdImgInfo();
		this.couponRes = new CouponRes();
	}

	@RequestMapping(value = "/api/couponInterface/v1", method = RequestMethod.POST)
	@ResponseBody
	public CouponRes apiCouponInterface(@RequestBody CouponReq couponReq) {

		this.log.debug("----------------------------------------------------------------");
		this.log.debug("apiCouponInterface Controller started!!");
		this.log.debug("----------------------------------------------------------------");

		try {
			this.dePloy(couponReq);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.couponRes;

	}

	@RequestMapping(value = "/api/couponStateUpdateStart/v1", method = RequestMethod.GET)
	@ResponseBody
	public ShoppingRes couponStateUpdateStart() {
		this.log.debug("----------------------------------------------------------------");
		this.log.debug("CouponStateUpdateStart Controller started!!");
		this.log.debug("----------------------------------------------------------------");
		try {
			String nowTime = DateUtil.getToday() + DateUtil.getTime();
			if (nowTime.length() >= 8)
				nowTime = nowTime.substring(0, 8);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date yesDate = DateUtil.getYesterday(sdf.parse(nowTime));
			String yesterdayTime = DateUtil.getDateString(yesDate, "yyyyMMdd");
			this.couponProcessService.CouponStateUpdateStart(yesterdayTime);
		} catch (Exception e) {
			this.log.error("couponStateUpdateStart 생성 중 예외 발생 - ( 쿠폰(아이템) 상태변경 Batch  Call 에러 )", e);
		}
		return null;

	}

	public boolean dePloy(CouponReq couponReq) throws Exception {

		this.log.info("<CouponControl> dePloy...");

		boolean result = false;

		Map<String, String> map = null;

		try {

			// 01.log delete
			// 02.Validation
			// 03.writeLog
			// 04.이력저장
			// 05.업무처리:return값 있음
			// 06.응답 및 처리결과이력 update
			// this.log.debug("<CouponControl> dePloy...txID = " + couponReq.getTxId());
			// this.log.debug("<CouponControl> dePloy...txTYPE = " + couponReq.getTxType());
			// this.log.debug("<CouponControl> dePloy...XML = " + couponReq.getRData());

			map = new HashMap<String, String>();
			map.put("TX_ID", couponReq.getTxId());

			result = this.doValidParameter1(couponReq); // 기본적인 validation 확인 및 parameter 정보 setting

			if (result) {
				boolean success = false;
				switch (TX_TYPE_CODE.get(couponReq.getTxType())) {

				case BD:
					// brand 작업을 호출한다.
					result = this.doValidParameterBD(couponReq); // 기본적인 validation 확인 및 parameter 정보 setting
					IcmsJobPrint.printBrand(this.brandInfo, "브랜드");
					if (result) {
						success = this.insertBrandInfo(this.brandInfo);
					}
					if (success) {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_SUCCESS);
						map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_OK);
						map.put("ERROR_MSG", this.getERR_MESSAGE());
					} else {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
						map.put("ERROR_CODE", this.getERR_CODE());
						map.put("ERROR_MSG", this.getERR_MESSAGE());
					}
					break;
				case CT:
					// 카달로그 작업을 호출한다.

					result = this.doValidParameterCT(couponReq); // 기본적인 validation 확인 및 parameter 정보 setting
					IcmsJobPrint.printCatalog(this.catalogInfo, "카달로그");
					if (result) {
						success = this.insertCatalogInfo(this.catalogInfo);
					}
					if (success) {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_SUCCESS);
						map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_OK);
						map.put("ERROR_MSG", this.getERR_MESSAGE());
					} else {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
						map.put("ERROR_CODE", this.getERR_CODE());
						map.put("ERROR_MSG", this.getERR_MESSAGE());
					}
					break;
				case CP:
					// 쿠폰 정보 작업을 호출한다

					result = this.doValidParameterCP(couponReq);
					if (result) {
						success = this.insertCouponInfo(this.containers, couponReq);
					}

					if (success) {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_SUCCESS);
						map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_OK);
						map.put("ERROR_MSG", this.getERR_MESSAGE());
					} else {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
						map.put("ERROR_CODE", this.getERR_CODE());
						map.put("ERROR_MSG", this.getERR_MESSAGE());
					}

					break;
				case ST:
					// 쿠폰 상태 변경 호출한다
					success = this.updateForCouponStatus(couponReq);
					if (success) {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_SUCCESS);
						map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_OK);
						map.put("ERROR_MSG", this.getERR_MESSAGE());
					} else {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
						map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_DATA_ERR);
						map.put("ERROR_MSG", this.getERR_MESSAGE());
					}
					break;
				case LS:
					// 특가 상품 목록 조회 작업을 호출한다.
					String[] couponCodes = couponReq.getCouponCode().split(",");
					this.couponList = this.getSpecialProductList(couponCodes);
					map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_SUCCESS);
					map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_OK);
					map.put("ERROR_MSG", this.getERR_MESSAGE());
					break;
				case DT:
					// 특가 상품 상세 조회 작업을 호출한다.
					this.couponRes = this.getSpecialProductDetail(couponReq.getCouponCode());
					if (this.couponRes.getRCode().equals("")) {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_SUCCESS);
						map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_OK);
					} else {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
						map.put("ERROR_CODE", this.couponRes.getRCode());
					}

					break;
				default:
					map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
					map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_MISS);
					map.put("ERROR_MSG", this.getERR_MESSAGE());
					break;
				}

				this.sendResponseData(couponReq, map);

			} else {
				// Error Messag 를 작성한다.
				map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
				map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC);
				map.put("ERROR_MSG", this.getERR_MESSAGE());
				this.sendResponseData(couponReq, map);
			}

		} catch (CouponException ex) {
			// 1차 Logic 처리시 벌생한 Coupon Exception 처리.
			this.log.error("Coupon Control CouponException err_code=" + ex.getErrCode() + ", msg=" + ex.getMessage(),
					ex);
			map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
			map.put("ERROR_CODE", ex.getErrCode());
			if (map.get("ERROR_CODE") == null || map.get("ERROR_CODE").equals(""))
				map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_DB_ERR);
			map.put("ERROR_MSG", ex.getMessage());
			map.put("ERROR_VALUE", ex.getErr_value());
			this.sendResponseData(couponReq, map);
			result = false;

		} catch (Exception e) {
			// Exception 처리.
			this.log.error("Coupon Control Exception", e);
			map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
			map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_SERVICE_STOP);
			map.put("ERROR_MSG", e.getMessage());
			this.sendResponseData(couponReq, map);
			result = false;
		}

		return result;
	}

	/**
	 * 브랜드 정보를 추가한다.
	 * 
	 * @param DpBrandInfo
	 *            dpBrandInfo
	 * @throws
	 */
	public boolean insertBrandInfo(DpBrandInfo dpBrandInfo) {
		boolean result = this.shoppingCouponService.insertBrandInfo(dpBrandInfo);
		return result;

	}

	/**
	 * 카탈로그 정보를 추가한다.
	 * 
	 * @param DpCatalogInfo
	 *            dpCatalogInfo
	 * @throws
	 */
	public boolean insertCatalogInfo(DpCatalogInfo dpCatalogInfo) {
		boolean result = this.shoppingCouponService.insertCatalogInfo(dpCatalogInfo);
		return result;

	}

	/**
	 * 쿠폰 정보를 추가한다.
	 * 
	 * @param CouponContainer
	 *            containers, String txType
	 * @response boolean
	 */
	public boolean insertCouponInfo(CouponContainer containers, CouponReq couponReq) {
		boolean result = this.couponProcessService.insertCouponInfo(containers, couponReq);
		return result;

	}

	/**
	 * 상품 상태 변경한다.
	 * 
	 * @param CouponReq
	 *            couponReq
	 * @response boolean
	 */
	public boolean updateForCouponStatus(CouponReq couponReq) {
		boolean result = this.couponProcessService.updateForCouponStatus(couponReq);
		return result;

	}

	/**
	 * 특가 상품 목록 조회 한다.
	 * 
	 * @param String
	 *            [] couponCodes
	 * @response List<CouponRes>
	 */
	public List<CouponRes> getSpecialProductList(String[] couponCodes) {
		List<CouponRes> result = this.couponProcessService.getSpecialProductList(couponCodes);
		return result;

	}

	/**
	 * 특가 상품 상세 조회 한다.
	 * 
	 * @param String
	 *            couponCode
	 * @response CouponRes
	 */
	public CouponRes getSpecialProductDetail(String couponCode) {
		CouponRes result = this.couponProcessService.getSpecialProductDetail(couponCode);
		return result;

	}

	public boolean doValidParameter1(CouponReq couponReq) {
		this.log.info("<CouponControl> doValidParameter1...");
		StringBuffer sb = new StringBuffer();
		boolean result = true;

		try {

			if (couponReq == null) {
				result = false;
				sb.append("Parameter정보가 없습니다.");
			}
			if (!couponReq.checkTX_ID()) {
				result = false;
				sb.append("TX_ID 형식에 맞지 않습니다. [22자리]\n");
			}
			if (!couponReq.checkTX_TYPE()) {
				result = false;
				sb.append("TX_TYPE 형식에 맞지 않습니다. [2자리]\n");
			}

			if (!result) {
				this.setERR_MESSAGE(sb.toString());
				this.setERR_CODE(CouponConstants.COUPON_IF_ERROR_CODE_TYPE);
			}
			return result;

		} catch (Exception e) {
			this.log.error("doValidParameter 작업중 예외 발생", e);
			sb.append(e.getMessage() + "\n");
			this.setERR_MESSAGE(sb.toString());
			this.setERR_CODE(CouponConstants.COUPON_IF_ERROR_CODE_DATA_ERR);
			result = false;
			return result;

		}

	}

	public boolean doValidParameterBD(CouponReq couponReq) {
		this.log.info("<CouponControl> doValidParameterBD...");
		StringBuffer sb = new StringBuffer();
		boolean result = true;
		try {
			this.brandInfo.setBrandNm(couponReq.getBrandName());
			this.brandInfo.setDpCatNo(couponReq.getBrandCategory());
			this.brandInfo.setBrandId(couponReq.getBrandCode());
			this.brandInfo.setBrandImgPath(couponReq.getBrandImage());
			this.brandInfo.setCudType(couponReq.getCudType());
			this.brandInfo.setTxType(couponReq.getTxType());
			if (this.brandInfo.getBrandId().equals("") || this.brandInfo.getBrandId() == null) {
				result = false;
				sb.append("브랜드 ID는 null을가질수 없습니다.");
			}
			if (this.brandInfo.getDpCatNo().equals("") || this.brandInfo.getDpCatNo() == null) {
				result = false;
				sb.append("브랜드 카탈로그는 null을가질수 없습니다.");
			}
			if (this.brandInfo.getBrandNm().equals("") || this.brandInfo.getBrandNm() == null) {
				result = false;
				sb.append("브랜드명은 null을가질수 없습니다.");
			} else if (this.brandInfo.getBrandNm().length() > 50) {
				result = false;
				sb.append("브랜드명은 length 50을 가질수 없습니다.");
			}
			if (this.brandInfo.getBrandImgPath().equals("") || this.brandInfo.getBrandImgPath() == null) {
				result = false;
				sb.append("브랜드 이미지 은 null을가질수 없습니다.");
			}
			if (!result) {
				this.setERR_MESSAGE(sb.toString());
				this.setERR_CODE(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC);
			}
			return result;

		} catch (Exception e) {
			this.log.error("doValidParameter 작업중 예외 발생", e);
			sb.append(e.getMessage() + "\n");
			result = false;
			this.setERR_MESSAGE(sb.toString());
			this.setERR_CODE(CouponConstants.COUPON_IF_ERROR_CODE_DATA_ERR);
			return result;
		}

	}

	public boolean doValidParameterCT(CouponReq couponReq) {
		this.log.info("<CouponControl> doValidParameterCT...");
		StringBuffer sb = new StringBuffer();
		boolean result = true;
		try {
			this.catalogInfo.setCudType(couponReq.getCudType());
			this.catalogInfo.setCatalogId(couponReq.getCatalogCode());
			this.catalogInfo.setDpCatNo(couponReq.getCatalogCategory());
			this.catalogInfo.setBrandId(couponReq.getBrandCode());
			this.catalogInfo.setCatalogDesc(couponReq.getCatalogDescription());
			this.catalogInfo.setCatalogNm(couponReq.getCatalogName());
			this.catalogInfo.setTopImgPath(couponReq.getCatalogImage1());
			this.catalogInfo.setDtlImgPath(couponReq.getCatalogImage2());
			this.catalogInfo.setIntroText(couponReq.getIntro_text());
			this.catalogInfo.setCatalogTag(couponReq.getTag());

			if (this.catalogInfo.getCatalogNm().equals("") || this.catalogInfo.getCatalogNm() == null) {
				result = false;
				sb.append("카탈로그명은 null을가질수 없습니다.");
			} else if (this.catalogInfo.getCatalogNm().length() > 100) {
				result = false;
				sb.append("카탈로그명은 length 100을 가질수 없습니다.");
			}
			if (this.catalogInfo.getDpCatNo().equals("") || this.catalogInfo.getDpCatNo() == null) {
				result = false;
				sb.append("카탈로그 카테고리은 null을가질수 없습니다.");
			}

			if (this.catalogInfo.getCatalogDesc().equals("") || this.catalogInfo.getCatalogDesc() == null) {
				result = false;
				sb.append("카탈로그 상세설명은 null을가질수 없습니다.");
			} else if (this.catalogInfo.getCatalogDesc().length() > 4000) {
				result = false;
				sb.append("카탈로그 상세설명은 length 4000 가질수 없습니다.");
			}

			if (this.catalogInfo.getTopImgPath().equals("") || this.catalogInfo.getTopImgPath() == null) {
				result = false;
				sb.append("카탈로그 탑이미지 은 null을가질수 없습니다.");
			}
			if (this.catalogInfo.getDtlImgPath().equals("") || this.catalogInfo.getDtlImgPath() == null) {
				result = false;
				sb.append("카탈로그 탑이미지 은 null을가질수 없습니다.");
			}
			if (this.catalogInfo.getBrandId().equals("") || this.catalogInfo.getBrandId() == null) {
				result = false;
				sb.append("카탈로그 브랜드코드 은 null을가질수 없습니다.");
			}
			if (this.catalogInfo.getIntroText().equals("") || this.catalogInfo.getIntroText() == null) {
				result = false;
				sb.append("카탈로그 한줄소개는 null을가질수 없습니다.");
			} else if (this.catalogInfo.getCatalogNm().length() > 150) {
				result = false;
				sb.append("카탈로그 한줄소개는 length 150을 가질수 없습니다.");
			}

			if (!result) {
				this.setERR_MESSAGE(sb.toString());
				this.setERR_CODE(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC);
			}

			return result;

		} catch (Exception e) {
			this.log.error("doValidParameter 작업중 예외 발생", e);
			sb.append(e.getMessage() + "\n");
			result = false;
			this.setERR_MESSAGE(sb.toString());
			this.setERR_CODE(CouponConstants.COUPON_IF_ERROR_CODE_DATA_ERR);
			return result;
		}

	}

	private boolean doValidParameterCP(CouponReq couponReq) {
		String couponProdId = "";
		String itemProdId = "";
		boolean result = true;
		DpCouponInfo couponInfo = couponReq.getDpCouponInfo(); // 쿠폰 정보
		List<DpItemInfo> itemInfoList = couponReq.getDpItemInfo();

		if ("C".equalsIgnoreCase(couponReq.getCudType())) {
			couponProdId = this.couponItemService.couponGenerateId(); // 쿠폰 ID 생성
		} else if ("U".equalsIgnoreCase(couponReq.getCudType())) {
			couponProdId = this.couponItemService.getGenerateId(couponInfo.getCouponCode()); // 기존 쿠폰 가져오기
		}
		if (StringUtils.isBlank(couponProdId)) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "[COUPON_PRODUCT_ID]를 생성하지 못했습니다.",
					"");
		}
		couponInfo.setProdId(couponProdId);

		int kk = 0;
		for (DpItemInfo itemInfo : itemInfoList) {
			if ("C".equalsIgnoreCase(couponReq.getCudType())) {
				itemProdId = this.couponItemService.itemGenerateId(); // 아이템 prodId 생성
				itemInfo.setProdId("S90000" + (Long.parseLong(itemProdId) + kk)); // 아이템 prodId 생성
			} else if ("U".equalsIgnoreCase(couponReq.getCudType())) {
				itemProdId = this.couponItemService.getGenerateId(itemInfo.getItemCode()); // 기존 아이템 ID 가져오기
				itemInfo.setProdId(itemProdId);
			}
			if (StringUtils.isBlank(itemProdId)) {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC,
						"[ITEM_PRODUCT_ID]를 생성하지 못했습니다.", "");
			}
			kk++;
		}

		if (!this.doValidateCouponInfo(couponInfo)) {
			result = false;
		}
		for (int i = 0; i < itemInfoList.size(); i++) {
			if (!this.doValidateItemInfo(itemInfoList.get(i))) {
				result = false;
			}
			break;
		}
		this.containers = new CouponContainer();
		this.containers.setDpCouponInfo(couponInfo);
		this.containers.setDpItemlist(itemInfoList);
		this.log.info("<<<<< MetaDefXMLParser.makeContentXMLMap >>>>> END");

		return result;

	}

	// 쇼핑쿠폰 API 응답은 XML 으로 전송한다.
	private boolean sendResponseData(CouponReq couponReq, Map<String, String> map) {
		this.log.info("<CouponControl> sendResponseData...");

		boolean success = false;
		try {

			// Response 는 무조건 처리 .Response 처리도 DB 화 한다.
			String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n";
			StringBuffer xmlSb = new StringBuffer();
			xmlSb.append(xmlHeader);
			xmlSb.append("<cms>");
			xmlSb.append("<rCode><![CDATA[" + map.get("ERROR_CODE") + "]]></rCode>");
			xmlSb.append("<rMsg><![CDATA["
					+ CouponConstants.getCouponErrorMsg(map.get("ERROR_CODE"), map.get("ERROR_MSG")));
			if (map.get("ERROR_VALUE") != null && !map.get("ERROR_VALUE").equals(""))
				xmlSb.append("[" + map.get("ERROR_VALUE") + "]");
			xmlSb.append("]]></rMsg>");
			xmlSb.append("<txId><![CDATA[" + couponReq.getTxId() + "]]></txId>");

			this.log.debug("-------------------jade 추가 S---------------------------------------------");
			this.couponRes.setRCode(map.get("ERROR_CODE"));
			this.couponRes.setRMsg(CouponConstants.getCouponErrorMsg(map.get("ERROR_CODE"), map.get("ERROR_MSG")));
			if (map.get("ERROR_VALUE") != null && !map.get("ERROR_VALUE").equals("")) {
				this.couponRes.setRMsg(CouponConstants.getCouponErrorMsg(map.get("ERROR_CODE"), map.get("ERROR_MSG"))
						+ map.get("ERROR_VALUE"));
			}
			this.couponRes.setTxId(couponReq.getTxId());
			this.log.debug("-------------------jade 추가 E---------------------------------------------");

			if (couponReq.checkTX_TYPE()) {

				switch (TX_TYPE_CODE.get(couponReq.getTxType())) {

				case BD:
					xmlSb.append("<rData>");
					xmlSb.append("<brandCode><![CDATA[" + couponReq.getBrandCode() + "]]></brandCode>");
					xmlSb.append("</rData>");
					map.put("COMMON_CODE", couponReq.getBrandCode());
					this.log.debug("-------------------jade 추가 S---------------------------------------------");
					this.couponRes.setBrandCode(couponReq.getBrandCode());
					this.log.debug("-------------------jade 추가 E---------------------------------------------");
					break;
				case CT:
					xmlSb.append("<rData>");
					xmlSb.append("<catalogCode><![CDATA[" + couponReq.getCatalogCode() + "]]></catalogCode>");
					xmlSb.append("</rData>");
					map.put("COMMON_CODE", couponReq.getCatalogCode());
					this.log.debug("-------------------jade 추가 S---------------------------------------------");
					this.couponRes.setCatalogCode(couponReq.getCatalogCode());
					this.log.debug("-------------------jade 추가 E---------------------------------------------");
					break;
				case CP:
					xmlSb.append("<rData>");
					xmlSb.append("<couponCode><![CDATA[" + StringUtil.nvl(couponReq.getCouponCode(), "")
							+ "]]></couponCode>");
					xmlSb.append("</rData>");
					map.put("COMMON_CODE", StringUtil.nvl(couponReq.getCouponCode(), "")); // 상품추가/수정시에는 xml
																						   // 전문에 couponcode
																						   // 가포함되어 마지막에 , put
																						   // 해준다.
					this.log.debug("-------------------jade 추가 S---------------------------------------------");
					this.couponRes.setCouponCode(couponReq.getCouponCode());
					this.log.debug("-------------------jade 추가 E---------------------------------------------");
					break;
				case ST:
					xmlSb.append("<rData>");
					xmlSb.append("<couponCode><![CDATA[" + couponReq.getCouponCode() + "]]></couponCode>");
					xmlSb.append("</rData>");
					map.put("COMMON_CODE", couponReq.getCouponCode());
					this.log.debug("-------------------jade 추가 S---------------------------------------------");
					this.couponRes.setCouponCode(couponReq.getCouponCode());
					this.log.debug("-------------------jade 추가 E---------------------------------------------");
				case AT:
					break;
				case LS:
					xmlSb.append("<rData>");
					xmlSb.append("<eventList><![CDATA[");
					String seperator_comma = "";
					String eventList = "";
					int j = 0;
					if (this.couponList != null) {
						for (CouponRes couponInfo : this.couponList) {
							if (j > 0)
								seperator_comma = ",";
							xmlSb.append(seperator_comma + couponInfo.getCouponCode() + ":" + couponInfo.getSpecialYN());
							eventList = eventList + seperator_comma + couponInfo.getCouponCode() + ":"
									+ couponInfo.getSpecialYN();

							j++;
						}
					}
					xmlSb.append("]]></eventList>");
					xmlSb.append("</rData>");
					this.log.debug("-------------------jade 추가 S---------------------------------------------");
					this.couponRes.setEventList(eventList);
					this.log.debug("-------------------jade 추가 E---------------------------------------------");

					break;
				case DT:
					xmlSb.append("<rData>");
					if (this.couponRes == null)
						xmlSb.append("<couponCode><![CDATA[" + StringUtil.nvl(couponReq.getCouponCode(), "")
								+ "]]></couponCode>");
					xmlSb.append("<eventName><![CDATA[" + StringUtil.nvl(this.couponRes.getEventName(), "")
							+ "]]></eventName>");
					xmlSb.append("<eventStartDate><![CDATA[" + StringUtil.nvl(this.couponRes.getEventStartDate(), "")
							+ "]]></eventStartDate>");
					xmlSb.append("<eventEndDate><![CDATA[" + StringUtil.nvl(this.couponRes.getEventEndDate(), "")
							+ "]]></eventEndDate>");
					xmlSb.append("<eventDcRate><![CDATA[" + StringUtil.nvl(this.couponRes.getEventDcRate(), "")
							+ "]]></eventDcRate>");
					xmlSb.append("</rData>");
					this.log.debug("-------------------jade 추가 S---------------------------------------------");
					this.couponRes.setCouponCode(couponReq.getCouponCode());
					this.log.debug("-------------------jade 추가 E---------------------------------------------");

					break;
				default:
					xmlSb.append("");
					break;
				}
			} else
				xmlSb.append("");

			xmlSb.append("</cms>");

			this.response = xmlSb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return success;
	}

	/**
	 * 쿠폰 정보 유효성 체크
	 * 
	 * @param CouponInfo
	 *            couponInfo
	 * @return boolean
	 */
	public boolean doValidateCouponInfo(DpCouponInfo couponInfo) {
		boolean result = true;
		String message = "";

		try {
			if (couponInfo.getProdId() == "") {
				message = "유효성 검사 실패 [couponName : 상품코드] 이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (couponInfo.getCouponName() == "") {
				message = "유효성 검사 실패 [couponName : 쿠폰명] 이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (couponInfo.getCouponName().length() > 100) {
				message = "유효성 검사 실패 [couponName : 쿠폰명:" + couponInfo.getCouponName() + "]";
				result = false;
			}
			if (couponInfo.getIssueSDate() == "") {
				message = "유효성 검사 실패 [issueSDate : 발급시작일시] 이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (couponInfo.getIssueSDate() != "") {
				if (couponInfo.getIssueSDate().length() > 14 || !StringUtils.isNumeric(couponInfo.getIssueSDate())) {
					message = "유효성 검사 실패 [issueSDate : 발급시작일시:" + couponInfo.getIssueSDate() + "]";
					result = false;
				}
			}
			if (couponInfo.getIssueEDate() == "") {
				message = "유효성 검사 실패 [issueEDate : 발급종료일시] 이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (couponInfo.getIssueEDate() != "") {
				if (couponInfo.getIssueEDate().length() > 14 || !StringUtils.isNumeric(couponInfo.getIssueEDate())) {
					message = "유효성 검사 실패 [issueEDate : 발급종료일시:" + couponInfo.getIssueEDate() + "]";
					result = false;
				}
			}
			if (couponInfo.getValidSDate() == "") {
				message = "유효성 검사 실패 [validSDate : 유효시작일시] 이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (couponInfo.getValidSDate() != "") {
				if (couponInfo.getValidSDate().length() > 14 || !StringUtils.isNumeric(couponInfo.getValidSDate())) {
					message = "유효성 검사 실패 [validSDate : 유효시작일시:" + couponInfo.getValidSDate() + "]";
					result = false;
				}
			}
			if (couponInfo.getValidEDate() == "") {
				message = "유효성 검사 실패 [validEDate : 유효종료일시] 이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (couponInfo.getValidEDate() != "") {
				if (couponInfo.getValidEDate().length() > 14 || !StringUtils.isNumeric(couponInfo.getValidEDate())) {
					message = "유효성 검사 실패 [validEDate : 유효종료일시:" + couponInfo.getValidEDate() + "]";
					result = false;
				}
			}
			if (couponInfo.getValidUntil() == "") {
				message = "유효성 검사 실패 [validUntil : 유효일수] 이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (couponInfo.getValidUntil() != "") {
				if (!StringUtils.isNumeric(couponInfo.getValidUntil())) {
					message = "유효성 검사 실패 [validUntil : 유효일수:" + couponInfo.getValidUntil() + "]";
					result = false;
				}
			}
			if (couponInfo.getDescription() != "") {
				if (couponInfo.getDescription().length() > 250) {
					message = "유효성 검사 실패 [description : 쿠폰설명:" + couponInfo.getDescription() + "]";
					result = false;
				}
			}
			if (couponInfo.getDirection() != "") {
				if (couponInfo.getDirection().length() > 4000) {
					message = "유효성 검사 실패 [direction : 사용장소:" + couponInfo.getDirection() + "]";
					result = false;
				}
			}
			if (couponInfo.getUseCondition() != "") {
				if (couponInfo.getUseCondition().length() > 4000) {
					message = "유효성 검사 실패 [useCondition : 사용제한:" + couponInfo.getUseCondition() + "]";
					result = false;
				}
			}
			if (couponInfo.getAddtionalInfo() != "") {
				if (couponInfo.getAddtionalInfo().length() > 1000) {
					message = "유효성 검사 실패 [addtionalInfo : 주의사항:" + couponInfo.getAddtionalInfo() + "]";
					result = false;
				}
			}
			if (couponInfo.getRefundCondition() != "") {
				if (couponInfo.getRefundCondition().length() > 4000) {
					message = "유효성 검사 실패 [refundCondition : 구매취소(환불) 조건:" + couponInfo.getRefundCondition() + "]";
					result = false;
				}
			}

			if (!couponInfo.getStoreSaleType().equals("1") && !couponInfo.getStoreSaleType().equals("2")
					&& !couponInfo.getStoreSaleType().equals("3")) {
				message = "유효성 검사 실패 [storeSaleType : 상품유형:" + couponInfo.getStoreSaleType() + "]";
				result = false;
			}
			if (couponInfo.getStoreSaleType() == "") {
				message = "유효성 검사 실패 [storeSaleType : 상품유형] 이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (!couponInfo.getStoreb2bFlag().equals("Y") && !couponInfo.getStoreb2bFlag().equals("N")) {
				message = "유효성 검사 실패 [storeb2bFlag : B2B상품여부:" + couponInfo.getStoreb2bFlag() + "]";
				result = false;
			}
			if (couponInfo.getStoreb2bFlag() == "") {
				message = "유효성 검사 실패 [storeb2bFlag : B2B상품여부] 이 XML에 존재하지 않습니다.";
				result = false;
			}

			if (couponInfo.getStoreCatalogCode() == "") {
				message = "유효성 검사 실패 [storeCatalogCode : 카탈로그 번호] 이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (couponInfo.getAccountingRate() == "") {
				message = "유효성 검사 실패 [accountingRate : 정산율 번호] 이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (couponInfo.getAccountingRate() != "") {
				if (!StringUtils.isNumeric(couponInfo.getAccountingRate())) {
					message = "유효성 검사 실패 [accountingRate : 정산율 번호:" + couponInfo.getAccountingRate() + "]";
					result = false;
				}
			}

			if (!couponInfo.getTaxType().equals("01") && !couponInfo.getTaxType().equals("02")) {
				message = "유효성 검사 실패 [taxType : 세금구분유형:" + couponInfo.getTaxType() + "]";
				result = false;
			}
			if (couponInfo.getTaxType() == "") {
				message = "유효성 검사 실패 [taxType : 세금구분유형] 이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (couponInfo.getBpId() == "") {
				message = "유효성 검사 실패 [bpId : 업체아이디] 이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (couponInfo.getCoupnStatus() == "") {
				message = "유효성 검사 실패 [coupnStatus : 쿠폰상태] 이 XML에 존재하지 않습니다.";
				result = false;
			}

			if (!result) {
				this.setERR_MESSAGE(message);
				this.setERR_CODE(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC);
			} else {
				result = true;
			}
		} catch (Exception e) {
			this.log.error("유효성 검사 실패", e);
			message = e.getMessage() + "\n";
			result = false;
			this.setERR_MESSAGE(message);
			this.setERR_CODE(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC);
			return result;
		}

		return result;
	}

	/**
	 * 쿠폰 정보 유효성 체크
	 * 
	 * @param CouponInfo
	 *            couponInfo
	 * @return boolean
	 */
	public boolean doValidateItemInfo(DpItemInfo itemInfo) {
		boolean result = true;
		String message = "";
		try {
			if (itemInfo.getItemCode() == "") {
				message = "유효성 검사 실패 [itemName : 단품코드 :이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (itemInfo.getStoreLicenseCode() == "") {
				message = "유효성 검사 실패 [storeLicenseCode : 스토어 라이선스 번호 :이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (itemInfo.getItemName() == "") {
				message = "유효성 검사 실패 [itemName : 단품명 :이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (itemInfo.getItemName().length() > 100) {
				message = "유효성 검사 실패 [itemName : 단품명 :" + itemInfo.getItemName() + "]";
				result = false;
			}
			if (itemInfo.getOrgPrice() == "") {
				message = "유효성 검사 실패 [orgPrice : 정상가격 :이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (itemInfo.getOrgPrice() != "") {
				if (!StringUtils.isNumeric(itemInfo.getOrgPrice())) {
					message = "유효성 검사 실패 [orgPrice : 정상가격 :" + itemInfo.getOrgPrice() + "]";
					result = false;
				}
			}
			if (itemInfo.getSalePrice() == "") {
				message = "유효성 검사 실패 [salePrice : 할인가격 ]이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (itemInfo.getSalePrice() != "") {
				if (!StringUtils.isNumeric(itemInfo.getSalePrice())) {
					message = "유효성 검사 실패 [salePrice : 할인가격 :" + itemInfo.getSalePrice() + "]";
					result = false;
				}
			}
			if (itemInfo.getItemPrice() == "") {
				message = "유효성 검사 실패 [itemPrice : 단품가격 ]이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (itemInfo.getItemPrice() != "") {
				if (!StringUtils.isNumeric(itemInfo.getItemPrice())) {
					message = "유효성 검사 실패 [itemPrice : 단품가격 :" + itemInfo.getItemPrice() + "]";
					result = false;
				}
			}
			if (itemInfo.getDcRate() == "") {
				message = "유효성 검사 실패 [dcRate : 할인율 ]이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (itemInfo.getDcRate() != "") {
				if (!StringUtils.isNumeric(itemInfo.getDcRate())) {
					message = "유효성 검사 실패 [dcRate : 할인율 :" + itemInfo.getDcRate() + "]";
					result = false;
				}
			}
			if (itemInfo.getMaxCount() == "") {
				message = "유효성 검사 실패 [maxCount : 판매개수 ]이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (itemInfo.getMaxCount() != "") {
				if (!StringUtils.isNumeric(itemInfo.getMaxCount())) {
					message = "유효성 검사 실패 [maxCount : 판매개수 :" + itemInfo.getMaxCount() + "]";
					result = false;
				}
			}
			if (itemInfo.getMaxCountMonthly() == "") {
				message = "유효성 검사 실패 [maxCountMonthly : 월간 상품 최대 판매 수량 ]이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (itemInfo.getMaxCountMonthly() != "") {
				if (!StringUtils.isNumeric(itemInfo.getMaxCountMonthly())) {
					message = "유효성 검사 실패 [maxCountMonthly : 월간 상품 최대 판매 수량 :" + itemInfo.getMaxCountMonthly() + "]";
					result = false;
				}
			}
			if (itemInfo.getMaxCountDaily() == "") {
				message = "유효성 검사 실패 [maxCountMonthly : 일간 상품 최대 판매 수량 ]이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (itemInfo.getMaxCountDaily() != "") {
				if (!StringUtils.isNumeric(itemInfo.getMaxCountDaily())) {
					message = "유효성 검사 실패 [maxCountDaily : 일간 상품 최대 판매 수량 :" + itemInfo.getMaxCountDaily() + "]";
					result = false;
				}
			}
			if (itemInfo.getMaxCountMonthlyUser() == "") {
				message = "유효성 검사 실패 [maxCountMonthlyUser : 1인 당월 최대 구매 수량 ]이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (itemInfo.getMaxCountMonthlyUser() != "") {
				if (!StringUtils.isNumeric(itemInfo.getMaxCountMonthlyUser())) {
					message = "유효성 검사 실패 [maxCountMonthlyUser : 1인 당월 최대 구매 수량 :" + itemInfo.getMaxCountMonthlyUser()
							+ "]";
					result = false;
				}
			}
			if (itemInfo.getMaxCountDailyUser() == "") {
				message = "유효성 검사 실패 [maxCountDailyUser : 1인 당일 최대 구매 수량 ]이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (itemInfo.getMaxCountDailyUser() != "") {
				if (!StringUtils.isNumeric(itemInfo.getMaxCountDailyUser())) {
					message = "유효성 검사 실패 [maxCountDailyUser : 1인 당일 최대 구매 수량 :" + itemInfo.getMaxCountDailyUser() + "]";
					result = false;
				}
			}
			if (itemInfo.getBuyMaxLimit() == "") {
				message = "유효성 검사 실패 [buyMaxLimit : 최대결제수량 ]이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (itemInfo.getBuyMaxLimit() != "") {
				if (!StringUtils.isNumeric(itemInfo.getBuyMaxLimit())) {
					message = "유효성 검사 실패 [buyMaxLimit : 최대결제수량 :" + itemInfo.getBuyMaxLimit() + "]";
					result = false;
				}
			}

			if (itemInfo.getBpManageId() != "") {
				if (itemInfo.getBpManageId().length() > 32) {
					message = "유효성 검사 실패 [bpManageId : BP관리ID :" + itemInfo.getBpManageId() + "]";
					result = false;
				}
			}

			if (!itemInfo.getCudType().equals("C") && !itemInfo.getCudType().equals("U")) {
				message = "유효성 검사 실패 [cudType : 추가수정플래그 :" + itemInfo.getCudType() + "]";
				result = false;
			}
			if (itemInfo.getCudType() == "") {
				message = "유효성 검사 실패 [cudType : 추가수정플래그 ]이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (itemInfo.getItemStatus() == "") {
				message = "유효성 검사 실패 [itemStatus : 단품상태 ]이 XML에 존재하지 않습니다.";
				result = false;
			}
			if (!result) {
				this.setERR_MESSAGE(message);
				this.setERR_CODE(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC);
			} else {
				result = true;
			}
		} catch (Exception e) {
			this.log.error("유효성 검사 실패", e);
			message = e.getMessage() + "\n";
			result = false;
			this.setERR_MESSAGE(message);
			this.setERR_CODE(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC);
			return result;
		}
		return result;
	}

	public String getERR_MESSAGE() {
		return this.ERR_MESSAGE;
	}

	public void setERR_MESSAGE(String eRR_MESSAGE) {
		this.ERR_MESSAGE = eRR_MESSAGE;
	}

	public String getERR_CODE() {
		return this.ERR_CODE;
	}

	public void setERR_CODE(String eRR_CODE) {
		this.ERR_CODE = eRR_CODE;
	}

	public String getPARAMETER_STR() {
		return this.PARAMETER_STR;
	}

	public void setPARAMETER_STR(String pARAMETER_STR) {
		this.PARAMETER_STR = pARAMETER_STR;
	}

	public String getReponse() {
		return this.response;
	}

	public void setReponse(String response) {
		this.response = response;
	}

}
