package com.skplanet.storeplatform.sac.api.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.skplanet.storeplatform.sac.api.conts.CouponConstants;
import com.skplanet.storeplatform.sac.api.except.CouponException;
import com.skplanet.storeplatform.sac.api.inf.ITX_TYPE_CODE.TX_TYPE_CODE;
import com.skplanet.storeplatform.sac.api.inf.IcmsJobPrint;
import com.skplanet.storeplatform.sac.api.service.CouponItemService;
import com.skplanet.storeplatform.sac.api.service.CouponProcessService;
import com.skplanet.storeplatform.sac.api.service.ShoppingCouponService;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.api.vo.BrandCatalogProdImgInfo;
import com.skplanet.storeplatform.sac.api.vo.CouponContainer;
import com.skplanet.storeplatform.sac.api.vo.CouponParameterInfo;
import com.skplanet.storeplatform.sac.api.vo.CouponResponseInfo;
import com.skplanet.storeplatform.sac.api.vo.DpBrandInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCouponInfo;
import com.skplanet.storeplatform.sac.api.vo.DpItemInfo;

@Controller
@RequestMapping("/shopping")
public class ShoppingCouponContoller {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private String ERR_MESSAGE;
	private String ERR_CODE;
	private String PARAMETER_STR;
	public final DpBrandInfo brandInfo;
	public final DpCatalogInfo catalogInfo;
	public final BrandCatalogProdImgInfo brandCatalogProdImgInfo;
	private CouponContainer containers;
	private List<CouponResponseInfo> couponList = null;
	private CouponResponseInfo couponInfo = null;
	public String response;

	@Autowired
	private ShoppingCouponService shoppingCouponService;
	@Autowired
	private CouponItemService couponItemService;

	@Autowired
	private CouponProcessService couponProcessService;

	public ShoppingCouponContoller() {
		this.brandInfo = new DpBrandInfo();
		this.catalogInfo = new DpCatalogInfo();
		this.brandCatalogProdImgInfo = new BrandCatalogProdImgInfo();
	}

	@RequestMapping(value = "/api/couponInterface", method = RequestMethod.GET)
	@ResponseBody
	public CouponResponseInfo apiCouponInterface(CouponParameterInfo couponParameterInfo) {
		CouponResponseInfo responseVO = null;
		this.log.debug("----------------------------------------------------------------");
		this.log.debug("apiCouponInterface Controller started!!");
		this.log.debug("----------------------------------------------------------------");

		try {
			this.dePloy(couponParameterInfo);
			this.log.info("log:" + this.response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseVO;

	}

	public boolean dePloy(CouponParameterInfo couponParameterInfo) throws Exception {

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
			// this.log.info("<CouponControl> dePloy...txID = " + couponParameterInfo.getTxId());
			// this.log.info("<CouponControl> dePloy...txTYPE = " + couponParameterInfo.getTxType());
			// this.log.info("<CouponControl> dePloy...XML = " + couponParameterInfo.getRData());

			map = new HashMap<String, String>();
			map.put("TX_ID", couponParameterInfo.getTxId());

			result = this.doValidParameter1(couponParameterInfo); // 기본적인 validation 확인 및 parameter 정보 setting

			if (result) {
				boolean success = false;
				switch (TX_TYPE_CODE.get(couponParameterInfo.getTxType())) {

				case BD:
					// brand 작업을 호출한다.
					result = this.doValidParameterBD(couponParameterInfo); // 기본적인 validation 확인 및 parameter 정보 setting
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

					result = this.doValidParameterCT(couponParameterInfo); // 기본적인 validation 확인 및 parameter 정보 setting
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
					// XML 전문 Parsing
					result = this.parserXML(couponParameterInfo);
					if (result) {
						success = this.insertCouponInfo(this.containers, couponParameterInfo);
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
					success = this.updateForCouponStatus(couponParameterInfo);
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
					String[] couponCodes = couponParameterInfo.getCouponCode().split(",");
					this.couponList = this.getSpecialProductList(couponCodes);
					map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_SUCCESS);
					map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_OK);
					map.put("ERROR_MSG", this.getERR_MESSAGE());
					break;
				case DT:
					// 특가 상품 상세 조회 작업을 호출한다.
					this.couponInfo = this.getSpecialProductDetail(couponParameterInfo.getCouponCode());
					if (this.couponInfo.getRCode().equals("")) {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_SUCCESS);
						map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_OK);
					} else {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
						map.put("ERROR_CODE", this.couponInfo.getRCode());
					}

					break;
				default:
					map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
					map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_MISS);
					map.put("ERROR_MSG", this.getERR_MESSAGE());
					break;
				}

				this.sendResponseData(couponParameterInfo, map);

			} else {
				// Error Messag 를 작성한다.
				map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
				map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC);
				map.put("ERROR_MSG", this.getERR_MESSAGE());
				this.sendResponseData(couponParameterInfo, map);
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
			this.sendResponseData(couponParameterInfo, map);
			result = false;

		} catch (Exception e) {
			// Exception 처리.
			this.log.error("Coupon Control Exception", e);
			map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
			map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_SERVICE_STOP);
			map.put("ERROR_MSG", e.getMessage());
			this.sendResponseData(couponParameterInfo, map);
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
	public boolean insertCouponInfo(CouponContainer containers, CouponParameterInfo couponParameterInfo) {
		boolean result = this.couponProcessService.insertCouponInfo(containers, couponParameterInfo);
		return result;

	}

	/**
	 * 상품 상태 변경한다.
	 * 
	 * @param CouponParameterInfo
	 *            couponParameterInfo
	 * @response boolean
	 */
	public boolean updateForCouponStatus(CouponParameterInfo couponParameterInfo) {
		boolean result = this.couponProcessService.updateForCouponStatus(couponParameterInfo);
		return result;

	}

	/**
	 * 특가 상품 목록 조회 한다.
	 * 
	 * @param String
	 *            [] couponCodes
	 * @response List<CouponResponseInfo>
	 */
	public List<CouponResponseInfo> getSpecialProductList(String[] couponCodes) {
		List<CouponResponseInfo> result = this.couponProcessService.getSpecialProductList(couponCodes);
		return result;

	}

	/**
	 * 특가 상품 상세 조회 한다.
	 * 
	 * @param String
	 *            couponCode
	 * @response CouponResponseInfo
	 */
	public CouponResponseInfo getSpecialProductDetail(String couponCode) {
		CouponResponseInfo result = this.couponProcessService.getSpecialProductDetail(couponCode);
		return result;

	}

	public boolean doValidParameter1(CouponParameterInfo couponParameterInfo) {
		this.log.info("<CouponControl> doValidParameter1...");
		StringBuffer sb = new StringBuffer();
		boolean result = true;

		try {

			if (couponParameterInfo == null) {
				result = false;
				sb.append("Parameter정보가 없습니다.");
			}
			if (!couponParameterInfo.checkTX_ID()) {
				result = false;
				sb.append("TX_ID 형식에 맞지 않습니다. [22자리]\n");
			}
			if (!couponParameterInfo.checkTX_TYPE()) {
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

	public boolean doValidParameterBD(CouponParameterInfo couponParameterInfo) {
		this.log.info("<CouponControl> doValidParameterBD...");
		StringBuffer sb = new StringBuffer();
		boolean result = true;
		try {
			this.brandInfo.setBrandNm(couponParameterInfo.getBrandName());
			this.brandInfo.setDpCatNo(couponParameterInfo.getBrandCategory());
			this.brandInfo.setBrandId(couponParameterInfo.getBrandCode());
			this.brandInfo.setBrandImgPath(couponParameterInfo.getBrandImage());
			this.brandInfo.setCudType(couponParameterInfo.getCudType());
			this.brandInfo.setTxType(couponParameterInfo.getTxType());
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

	public boolean doValidParameterCT(CouponParameterInfo couponParameterInfo) {
		this.log.info("<CouponControl> doValidParameterCT...");
		StringBuffer sb = new StringBuffer();
		boolean result = true;
		try {
			this.catalogInfo.setCudType(couponParameterInfo.getCudType());
			this.catalogInfo.setCatalogId(couponParameterInfo.getCatalogCode());
			this.catalogInfo.setDpCatNo(couponParameterInfo.getCatalogCategory());
			this.catalogInfo.setBrandId(couponParameterInfo.getBrandCode());
			this.catalogInfo.setCatalogDesc(couponParameterInfo.getCatalogDescription());
			this.catalogInfo.setCatalogNm(couponParameterInfo.getCatalogName());
			this.catalogInfo.setTopImgPath(couponParameterInfo.getCatalogImage1());
			this.catalogInfo.setDtlImgPath(couponParameterInfo.getCatalogImage2());
			this.catalogInfo.setIntroText(couponParameterInfo.getIntro_text());
			this.catalogInfo.setCatalogTag(couponParameterInfo.getTag());

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

	// 쇼핑쿠폰 API 응답은 XML 으로 전송한다.
	private boolean sendResponseData(CouponParameterInfo couponParameterInfo, Map<String, String> map) {
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
			xmlSb.append("<txId><![CDATA[" + couponParameterInfo.getTxId() + "]]></txId>");

			if (couponParameterInfo.checkTX_TYPE()) {

				switch (TX_TYPE_CODE.get(couponParameterInfo.getTxType())) {

				case BD:
					xmlSb.append("<rData>");
					xmlSb.append("<brandCode><![CDATA[" + couponParameterInfo.getBrandCode() + "]]></brandCode>");
					xmlSb.append("</rData>");
					map.put("COMMON_CODE", couponParameterInfo.getBrandCode());

					break;
				case CT:
					xmlSb.append("<rData>");
					xmlSb.append("<catalogCode><![CDATA[" + couponParameterInfo.getCatalogCode() + "]]></catalogCode>");
					xmlSb.append("</rData>");
					map.put("COMMON_CODE", couponParameterInfo.getCatalogCode());
					break;
				case CP:
					xmlSb.append("<rData>");
					xmlSb.append("<couponCode><![CDATA[" + StringUtil.nvl(couponParameterInfo.getCouponCode(), "")
							+ "]]></couponCode>");
					xmlSb.append("</rData>");
					map.put("COMMON_CODE", StringUtil.nvl(couponParameterInfo.getCouponCode(), "")); // 상품추가/수정시에는 xml
																									 // 전문에 couponcode
																									 // 가포함되어 마지막에 , put
																									 // 해준다.
					break;
				case ST:
					xmlSb.append("<rData>");
					xmlSb.append("<couponCode><![CDATA[" + couponParameterInfo.getCouponCode() + "]]></couponCode>");
					xmlSb.append("</rData>");
					map.put("COMMON_CODE", couponParameterInfo.getCouponCode());
				case AT:
					break;
				case LS:
					xmlSb.append("<rData>");
					xmlSb.append("<eventList><![CDATA[");
					String seperator_comma = "";
					int j = 0;
					if (this.couponList != null) {
						for (CouponResponseInfo couponInfo : this.couponList) {
							if (j > 0)
								seperator_comma = ",";
							xmlSb.append(seperator_comma + couponInfo.getCouponCode() + ":" + couponInfo.getSpecialYN());
							j++;
						}
					}
					xmlSb.append("]]></eventList>");
					xmlSb.append("</rData>");
					break;
				case DT:
					xmlSb.append("<rData>");
					if (this.couponInfo == null)
						this.couponInfo = new CouponResponseInfo();
					xmlSb.append("<couponCode><![CDATA[" + StringUtil.nvl(couponParameterInfo.getCouponCode(), "")
							+ "]]></couponCode>");
					xmlSb.append("<eventName><![CDATA[" + StringUtil.nvl(this.couponInfo.getEventName(), "")
							+ "]]></eventName>");
					xmlSb.append("<eventStartDate><![CDATA[" + StringUtil.nvl(this.couponInfo.getEventStartDate(), "")
							+ "]]></eventStartDate>");
					xmlSb.append("<eventEndDate><![CDATA[" + StringUtil.nvl(this.couponInfo.getEventEndDate(), "")
							+ "]]></eventEndDate>");
					xmlSb.append("<eventDcRate><![CDATA[" + StringUtil.nvl(this.couponInfo.getEventDcRate(), "")
							+ "]]></eventDcRate>");
					xmlSb.append("</rData>");
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

	private boolean parserXML(CouponParameterInfo couponParameterInfo) throws ParserConfigurationException,
			SAXException, IOException {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		boolean result = true;
		StringBuffer sb = new StringBuffer();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = null;
			ByteArrayInputStream in = new ByteArrayInputStream(couponParameterInfo.getRData().getBytes());
			doc = builder.parse(in);

			Element root = doc.getDocumentElement();

			Node chNode = null;
			String chNodeNm = "";
			Map hashMap = new HashMap<String, String>();

			List<List<Map<String, String>>> list = new ArrayList<List<Map<String, String>>>(); // 전체 List
			List<Map<String, String>> contentList = new ArrayList<Map<String, String>>(); // 쿠폰 정보 Map의 List
			List<Map<String, String>> itemList = new ArrayList<Map<String, String>>(); // 단품 정보 Map의 List
			List[] contentLists = { contentList, itemList };

			// 쿠폰목록, 단품 목록의 순서로 List로 미리 저장
			list.add(contentList);
			list.add(itemList);

			for (chNode = root.getFirstChild(); chNode != null; chNode = chNode.getNextSibling()) {

				if (chNode.getNodeType() == Node.ELEMENT_NODE) {
					chNodeNm = StringUtil.setTrim(chNode.getNodeName().trim());
					if (!StringUtils.equals(chNodeNm, "items")) {
						// MAIN_CONTENT

						String nodeName = chNode.getNodeName();
						String sContext = chNode.getTextContent().trim();
						// this.log.info("[ nodeName = " + nodeName + "] : " + sContext);

						// <br> 태그 먼저 처리 - 삭제되기 전에
						if (true)
							sContext = StringUtil.changeTag(sContext, "<br>", "\r\n");

						// 태그 삭제하기
						if (true) {

							// a 태그 제외 삭제
							if (!true)
								sContext = StringUtil.replaceAllTagsExceptA(sContext, "");
							// 모든 태그 삭제
							else
								sContext = StringUtil.replaceAllTags(sContext, "");
						}
						hashMap.put(nodeName, sContext);
					}
				}

				int subContentCount = 0;

				if (StringUtils.equals(chNodeNm, "items")) {

					for (Node bodyNode = chNode.getFirstChild(); bodyNode != null; bodyNode = bodyNode.getNextSibling()) {
						chNodeNm = StringUtil.setTrim(bodyNode.getNodeName().trim());
						if (StringUtils.equals(chNodeNm, "item")) {
							// SUB_CONTENT

							NodeList subContentNodes = bodyNode.getChildNodes();
							Map itemHashMap = new HashMap<String, String>();
							for (int i = 0; i < subContentNodes.getLength(); i++) {
								Node node = subContentNodes.item(i);

								if (node.getNodeType() == Node.ELEMENT_NODE) {
									String nodeName = node.getNodeName();
									String sContext = node.getTextContent().trim();

									itemHashMap.put(nodeName, sContext);
									// this.log.info("[ nodeName = " + nodeName + "] : " + sContext);
								}
							} // end for

							itemList.add(subContentCount, itemHashMap);
							subContentCount++;

						} // end if
					} // end for

				}

			}
			contentLists[0].add(hashMap);
			DpCouponInfo couponInfo = new DpCouponInfo(); // 쿠폰 정보
			List<DpItemInfo> itemInfoList = new ArrayList<DpItemInfo>(); // 아이템 정보 List;
			String couponProdId = "";
			String srcCouponContentId = "";
			for (int i = 0; i < 1; i++) { // 쿠폰 정보 Add
				List<Map<String, String>> mapList = list.get(i);
				for (Map<String, String> map : mapList) {
					for (Map.Entry<String, String> entry : map.entrySet()) {
						if (entry.getKey().equals("couponCode")) {
							srcCouponContentId = entry.getValue();
						}
						if (!this.invoke(couponInfo, "set" + entry.getKey().substring(0, 1).toUpperCase()
								+ entry.getKey().substring(1), new Object[] { entry.getValue() })) { //
							throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "매핑 실패 ["
									+ entry.getKey() + ":" + entry.getValue() + "]이 형식이 잘못 됐습니다.", "");
						}
					}
				}
			}
			System.out.println("srcCouponContentId::" + srcCouponContentId);
			if ("C".equalsIgnoreCase(couponParameterInfo.getCudType())) {
				couponProdId = this.couponItemService.couponGenerateId(); // 쿠폰 ID 생성
			} else if ("U".equalsIgnoreCase(couponParameterInfo.getCudType())) {
				couponProdId = this.couponItemService.getGenerateId(srcCouponContentId); // 기존 쿠폰 가져오기
			}
			if (StringUtils.isBlank(couponProdId)) {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC,
						"[COUPON_PRODUCT_ID]를 생성하지 못했습니다.", "");
			}
			couponInfo.setProdId(couponProdId);

			int kk = 0;
			String itemProdId = "";
			String srcItemContentId = "";
			for (int i = 1; i < list.size(); i++) { // 아이템 정보 List Add
				List<Map<String, String>> mapList = list.get(i);
				for (Map<String, String> map : mapList) {
					DpItemInfo itemInfo = new DpItemInfo();
					for (Map.Entry<String, String> entry : map.entrySet()) {
						if (entry.getKey().equals("itemCode")) {
							srcItemContentId = entry.getValue();
						}
						if (!this.invoke(itemInfo, "set" + entry.getKey().substring(0, 1).toUpperCase()
								+ entry.getKey().substring(1), new Object[] { entry.getValue() })) { // itemInfo VO에 값
																									 // 셋팅
							throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "매핑 실패 ["
									+ entry.getKey() + ":" + entry.getKey() + "]이 형식이 잘못 됐습니다.", "");
						}

					}
					if ("C".equalsIgnoreCase(couponParameterInfo.getCudType())) {
						itemProdId = this.couponItemService.itemGenerateId(); // 아이템 prodId 생성
						itemInfo.setProdId("S90000" + (Long.parseLong(itemProdId) + kk)); // 아이템 prodId 생성
					} else if ("U".equalsIgnoreCase(couponParameterInfo.getCudType())) {
						System.out.println(":::::::::::" + i + ":::::::::::::" + itemProdId);
						itemProdId = this.couponItemService.getGenerateId(srcItemContentId); // 기존 아이템 ID 가져오기
						itemInfo.setProdId(itemProdId);
					}
					if (StringUtils.isBlank(itemProdId)) {
						throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC,
								"[ITEM_PRODUCT_ID]를 생성하지 못했습니다.", "");
					}

					itemInfoList.add(itemInfo);
					kk++;
				}
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
			this.containers.setDpItemInfo(itemInfoList);
			this.log.info("<<<<< MetaDefXMLParser.makeContentXMLMap >>>>> END");

			return result;
		} catch (CouponException e) {
			this.log.error("파싱처리중 예외 발생", e);
			sb.append(e.getMessage() + "\n");
			result = false;
			this.setERR_MESSAGE(sb.toString());
			this.setERR_CODE(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC);
			return result;
		} catch (ParserConfigurationException e) {
			this.log.error("파싱처리중 예외 발생", e);
			sb.append(e.getMessage() + "\n");
			result = false;
			this.setERR_MESSAGE(sb.toString());
			this.setERR_CODE(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC);
			return result;
		} catch (SAXException e) {
			this.log.error("파싱처리중 예외 발생", e);
			sb.append(e.getMessage() + "\n");
			result = false;
			this.setERR_MESSAGE(sb.toString());
			this.setERR_CODE(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC);
			return result;
		}

	}

	/**
	 * 특정 클래스의 내용을 invoke
	 * 
	 * @param obj
	 *            Method Invoke할 오브젝트
	 * @param methodName
	 *            Method Name
	 * @param objList
	 *            Parameter Object List
	 * @return
	 */
	public boolean invoke(Object obj, String methodName, Object[] objList) {
		Method[] methods = obj.getClass().getMethods();
		boolean result = false;

		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().equals(methodName)) {
				try {
					if (methods[i].getReturnType().getName().equals("void")) {
						result = true;
						methods[i].invoke(obj, objList);
					}
				} catch (IllegalAccessException lae) {
					System.out.println("LAE : " + lae.getMessage());
				} catch (InvocationTargetException ite) {
					System.out.println("ITE : " + ite.getMessage());
				}
			}
		}
		return result;
	}

	/**
	 * 쿠폰 정보 유효성 체크
	 * 
	 * @param DpCouponInfo
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
	 * @param DpCouponInfo
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
