package com.skplanet.storeplatform.sac.api.action;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StopWatch;

import com.skplanet.storeplatform.sac.api.conts.CouponConstants;
import com.skplanet.storeplatform.sac.api.except.CouponException;
import com.skplanet.storeplatform.sac.api.inf.ITX_TYPE_CODE.TX_TYPE_CODE;
import com.skplanet.storeplatform.sac.api.inf.IcmsJobPrint;
import com.skplanet.storeplatform.sac.api.service.BrandCatalogService;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.api.vo.CouponParameterInfo;
import com.skplanet.storeplatform.sac.api.vo.DpBrandInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogInfo;

public class CallShoppin {
	@Autowired
	@Qualifier("sac")
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private String ERR_MESSAGE;
	private String ERR_CODE;
	private String PARAMETER_STR;
	public DpBrandInfo brandInfo;
	public DpCatalogInfo catalogInfo;
	private final HttpServletResponse response;
	private final StopWatch watch;

	public CallShoppin() {
		this.brandInfo = new DpBrandInfo();
		this.catalogInfo = new DpCatalogInfo();
		this.response = this.response;
		this.watch = new StopWatch();
		this.watch.start();
	}

	public void startApi(CouponParameterInfo couponParameterInfo) throws Exception {
		this.dePloy(couponParameterInfo);
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
			// log.info("<CouponControl> dePloy...txID = " + couponParameterInfo.getTxId());
			// log.info("<CouponControl> dePloy...txTYPE = " + couponParameterInfo.getTxType());
			// log.info("<CouponControl> dePloy...XML = " + couponParameterInfo.getRData());

			map = new HashMap<String, String>();
			map.put("TX_ID", couponParameterInfo.getTxId());

			// removeLogXML();

			result = this.doValidParameter1(couponParameterInfo); // 기본적인 validation 확인 및 parameter 정보 setting

			if (result) {
				this.getParameterStr(couponParameterInfo, this.brandInfo); // parameter정보를 보관하기위해서 String 으로 반환
			}
			if (result) {

				boolean success = false;
				BrandCatalogService bcs = null;
				switch (TX_TYPE_CODE.get(couponParameterInfo.getTxType())) {

				case BD:
					// brand 작업을 호출한다.
					result = this.doValidParameterBD(couponParameterInfo); // 기본적인 validation 확인 및 parameter 정보 setting
					IcmsJobPrint.printBrand(this.brandInfo, "브랜드");
					System.out.println("+++++++++++++++++++++++++result++++++++++++++++++++" + result);
					System.out.println("+++++++++++++++++++++++++result++++++++++++++++++++" + result);
					if (result) {
						bcs = new BrandCatalogService();
						success = bcs.insertBrandInfo(this.brandInfo);
					} else {
						success = false;
					}

					System.out.println("+++++++++++++++++++++++++success++++++++++++++++++++" + success);
					if (success) {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_SUCCESS);
						map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_OK);
						map.put("ERROR_MSG", this.getERR_MESSAGE());
					} else {
						map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
						map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_DATA_ERR);
						map.put("ERROR_MSG", this.getERR_MESSAGE());
					}

				case CT:
					// 카달로그 작업을 호출한다.
					// IcmsJobPrint.printCatalog(catalogInfo, "카달로그");
					result = this.doValidParameterCT(couponParameterInfo); // 기본적인 validation 확인 및 parameter 정보 setting
					// IcmsJobPrint.printBrand(this.brandInfo, "브랜드");
					System.out.println("+++++++++++++++++++++++++LOG3333++++++++++++++++++++" + result);

					// this.setBrandCategory(couponParameterInfo);
					// bcs = new BrandCatalogService();
					// success = bcs.insertCatalogInfo(this.catalogInfo);
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
				case CP:
					// xml write
					// couponParameterInfo.setRData(LocalString(couponParameterInfo.getRData()));
					// this.writeRQ_XMLtoFile(couponParameterInfo);
					// XML 전문 Parsing
					// this.containers = new CouponContainer[2];
					// this.containers[0] = new CouponContainer();
					// this.containers[1] = new CouponContainer();
					// this.parserXML(couponParameterInfo);
					// 상품 추가/수정 작업을 호출한다.
					// CouponContentService ccs = new CouponContentService();
					// success = ccs.processForCouponCSP(this.containers[1]);

					if (success) {
						// SenderCuponAdapter sca = new SenderCuponAdapter();
						// success = sca.executeDeployCuponsByTx(this.containers[1].getContentInfo().getTxId());
					}

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
				case ST:
					// CouponContentService pcs = new CouponContentService();
					// success = pcs.processForCouponStatus(couponParameterInfo);
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
				case AT:
					// CouponContentService pcs2 = new CouponContentService();
					// success = pcs2.processForCouponStatus(couponParameterInfo);
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
				// throw new CouponException(ERROR_CODE.SYSTEM_ERROR.getAbbreviation(), "중복ID", null);
				// map.put("ERROR_MSG", this.getERR_MESSAGE());

			}

			result = true;

		} catch (CouponException ex) {
			// 1차 Logic 처리시 벌생한 Coupon Exception 처리.
			System.out.println("_>>>>>>>>>>>>>>>>>>>>error");
			// log.error("Coupon Control CouponException err_code=" + ex.getErrCode() + ", msg=" + ex.getMessage(), ex);
			map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
			map.put("ERROR_CODE", ex.getErrCode());
			if (map.get("ERROR_CODE") == null || map.get("ERROR_CODE").equals(""))
				map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_DB_ERR);
			map.put("ERROR_MSG", ex.getMessage());
			map.put("ERROR_VALUE", ex.getErr_value());
			// this.sendResponseData(couponParameterInfo, map);
			result = false;

		} catch (Exception e) {
			// Exception 처리.
			// log.error("Coupon Control Exception", e);
			map.put("TX_STATUS", CouponConstants.COUPON_IF_TX_STATUS_ERROR);
			map.put("ERROR_CODE", CouponConstants.COUPON_IF_ERROR_CODE_SERVICE_STOP);
			map.put("ERROR_MSG", e.getMessage());
			// this.sendResponseData(couponParameterInfo, map);
			result = false;

		}

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
			// log.error("doValidParameter 작업중 예외 발생", e);
			sb.append(e.getMessage() + "\n");
			result = false;
			this.setERR_MESSAGE(sb.toString());
			this.setERR_CODE(CouponConstants.COUPON_IF_ERROR_CODE_DATA_ERR);
			return result;
		}

	}

	/**
	 * parameter 정보를 String 으로 반환한다.
	 * 
	 * @param couponParameterInfo
	 */
	private void getParameterStr(CouponParameterInfo couponParameterInfo, DpBrandInfo brandInfo) throws Exception,
			CouponException {
		this.log.info("<CouponControl> getParameterStr...");

		try {

			StringBuffer parameterSb = new StringBuffer();
			int j = 0;
			String comma_regex = "";
			for (Method method : couponParameterInfo.getClass().getDeclaredMethods().clone()) {
				if (method.getName().contains("get") && !method.getName().contains("getRData")) {
					if (method.invoke(couponParameterInfo, null) != null) {
						if (j > 0) {
							comma_regex = " ,";
						}

						parameterSb.append(comma_regex + method.getName().replace("get", "") + "="
								+ method.invoke(couponParameterInfo, null));
						j++;
					}
				}

			}

			this.PARAMETER_STR = parameterSb.toString();
			System.out.println("log::::" + this.PARAMETER_STR);
			// throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_NOT_CATID, "TEST 입니다. " , null);

		} catch (Exception e) {
			// log.error("getParameterStr 작업중 예외 발생", e);
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DATA_ERR, e.getMessage(), "");
		}

	}

	public boolean doValidParameterBD(CouponParameterInfo couponParameterInfo) {
		this.log.info("<CouponControl> doValidParameterBD...");
		StringBuffer sb = new StringBuffer();
		String sb1 = "";
		boolean result = true;
		try {
			this.brandInfo.setBrandNm(couponParameterInfo.getBrandName());
			this.brandInfo.setDpCatNo(couponParameterInfo.getBrandCategory());
			this.brandInfo.setBrandId(couponParameterInfo.getBrandCode());
			this.brandInfo.setBrandImgPath(couponParameterInfo.getBrandImage());
			this.brandInfo.setCudType(couponParameterInfo.getCudType());
			this.brandInfo.setTxType(couponParameterInfo.getTxType());
			this.brandInfo.setCreateBrandId("BR00000456");

			if (this.brandInfo.getDpCatNo().equals("") || this.brandInfo.getDpCatNo() == null) {
				result = false;
				sb1 = "브랜드 카탈로그는 null을가질수 없습니다.";
			} else if (this.brandInfo.getDpCatNo().length() > 50) {
				result = false;
				sb1 = "브랜드 카탈로그는 length 50을 가질수 없습니다.";
			}

			if (this.brandInfo.getBrandNm().equals("") || this.brandInfo.getBrandNm() == null) {
				result = false;
				sb1 = "브랜드명은 null을가질수 없습니다.";
			}
			if (this.brandInfo.getBrandId().equals("") || this.brandInfo.getBrandId() == null) {
				result = false;
				sb1 = "브랜드 ID 은 null을가질수 없습니다.";
			}
			if (this.brandInfo.getBrandImgPath().equals("") || this.brandInfo.getBrandImgPath() == null) {
				result = false;
				sb1 = "브랜드 이미지 은 null을가질수 없습니다.";
			}

			if (!result) {
				this.setERR_MESSAGE(sb1);
				this.setERR_CODE(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC);
			}
			this.log.info("++++++++++이제는 끝");
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
		String sb1 = "";
		boolean result = true;
		this.log.info("+++4234234++++++" + couponParameterInfo.getBrandName());
		try {
			this.log.info("+++4234234++++++" + couponParameterInfo.getBrandName());

			this.catalogInfo.setCatalogId(couponParameterInfo.getCatalogCode());
			this.catalogInfo.setDpCatNo(couponParameterInfo.getCatalogCategory());
			this.catalogInfo.setBrandId(couponParameterInfo.getBrandCode());
			this.catalogInfo.setCatalogDesc(couponParameterInfo.getCatalogDescription());
			this.catalogInfo.setCatalogNm(couponParameterInfo.getCatalogName());
			this.catalogInfo.setTopImgPath(couponParameterInfo.getCatalogImage1());
			this.catalogInfo.setDtlImgPath(couponParameterInfo.getCatalogImage2());
			this.catalogInfo.setIntroText(couponParameterInfo.getIntro_text());
			this.catalogInfo.setCatalogTag(couponParameterInfo.getTag());

			if (this.catalogInfo.getDpCatNo().equals("") || this.catalogInfo.getDpCatNo() == null) {
				result = false;
				sb1 = "카탈로그명은 null을가질수 없습니다.";
			}

			if (this.catalogInfo.getCatalogNm().equals("") || this.catalogInfo.getCatalogNm() == null) {
				result = false;
				sb1 = "카탈로그명은 null을가질수 없습니다.";
			} else if (this.catalogInfo.getCatalogNm().length() > 50) {
				result = false;
				sb1 = "카탈로그명은 length 100을 가질수 없습니다.";
			}
			if (this.catalogInfo.getCatalogId().equals("") || this.catalogInfo.getCatalogId() == null) {
				result = false;
				sb1 = "브랜드 ID 은 null을가질수 없습니다.";
			}
			if (this.brandInfo.getBrandImgPath().equals("") || this.brandInfo.getBrandImgPath() == null) {
				result = false;
				sb1 = "브랜드 이미지 은 null을가질수 없습니다.";
			}

			if (!result) {
				this.setERR_MESSAGE(sb1);
				this.setERR_CODE(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC);
			}

			return result;

		} catch (Exception e) {
			// log.error("doValidParameter 작업중 예외 발생", e);
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
				default:
					xmlSb.append("");
					break;
				}
			} else
				xmlSb.append("");

			xmlSb.append("</cms>");

			// this.response.setContentType("application/xml");
			// this.response.setCharacterEncoding("utf-8");
			// this.response.setHeader("Cache-Control", "no-cache");
			// this.response.setContentLength(xmlSb.toString().getBytes("utf-8").length);

			try {
				this.log.info(":::" + xmlSb.toString());
				this.response.getWriter().print(xmlSb.toString());
				this.watch.stop();
				// setLog(this.watch.getTime(), couponParameterInfo.getTxId(), "response", "0", "SUCCESS");
			} catch (Exception e) {

				this.watch.stop();
				// setLog(this.watch.getTime(), couponParameterInfo.getTxId(), "response", "50", e.getMessage());
			}

			// response.getWriter().flush();
			// log.info("<CouponControl> sendResponseData...RS_XML =" + xmlSb.toString());

		} catch (Exception e) {
			// PrintUtil.addMsg("sendResponseData Exception");
			// PrintUtil.println();
			this.watch.stop();
			// setLog(this.watch.getTime(), couponParameterInfo.getTxId(), "response", "30", e.getMessage());
			// setErrorLog(this.watch.getTime(), couponParameterInfo.getTxId(), e.getMessage());
		} finally {

		}
		return success;
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

	public DpBrandInfo getBrandInfo() {
		return this.brandInfo;
	}

	public void setBrandInfo(DpBrandInfo brandInfo) {
		this.brandInfo = brandInfo;
	}

}
