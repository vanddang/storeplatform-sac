/*****************************************************************************
 * SKT TStore Project ::: PARTNER Site :::: Shopping Coupon interface
 *****************************************************************************
 * 1.클래스 개요 :
 * 2.작   성  자 : Kim Hyung Sik
 * 3.작 성 일 자 : 2013. 12. 29.
 * <pre>
 * 4.수 정 일 자 :
 *      . <날짜> : <수정 내용> (성명)
 *      . 2013. 12. 29.  : 최초 생성 (jade)
 * @author Kim Hyung Sik
 * </pre>
 * @version 1.0
 *****************************************************************************/

package com.skplanet.storeplatform.sac.api.inf;

import org.apache.log4j.Logger;

import com.skplanet.storeplatform.sac.api.vo.DpBrandInfo;

public class IcmsJobPrint {

	private final static Logger log = Logger.getLogger(IcmsJobPrint.class);

	public static boolean printBrand(DpBrandInfo info, String title) throws Exception {

		StringBuffer debugMsg = new StringBuffer();

		try {

			debugMsg.append("################################### BRAND INFO [" + title
					+ "] BRAND INFO #################################### \n");
			debugMsg.append("brandId = " + info.getBrandId() + " \n");
			debugMsg.append("brandNm = " + info.getBrandNm() + " \n");
			debugMsg.append("dpCatNo = " + info.getDpCatNo() + " \n");
			debugMsg.append("brandImgPath = " + info.getBrandImgPath() + " \n");
			debugMsg.append("txType = " + info.getTxType() + " \n");
			debugMsg.append("cudType = " + info.getCudType() + " \n");
			debugMsg.append("################################### BRAND INFO [" + title
					+ "] BRAND INFO #################################### \n");

			System.out.println(debugMsg.toString());
			debugMsg.setLength(0);
			debugMsg = null;

		} catch (Exception e) {
			log.error("■■■■■printBrand()■■■■■ : " + e.getMessage());
			log.debug(e);
			// throw new ServiceException("printBrand  Fail", e);
		}

		return true;
	}

	// public static boolean printCatalog(DpCatalogInfo info,String title) throws Exception {
	//
	// StringBuffer debugMsg = new StringBuffer();
	//
	// try {
	//
	//
	// debugMsg.append("################################### CATALOG INFO ["+ title
	// +"] CATALOG INFO #################################### \n");
	// debugMsg.append("catalogId = " + info.getCatalogId() + " \n");
	// debugMsg.append("dpCatNo = " + info.getDpCatNo() + " \n");
	// debugMsg.append("brandId = " + info.getBrandId() + " \n");
	// debugMsg.append("catalogDesc = " + info.getCatalogDesc() + " \n");
	// debugMsg.append("catalogNm = " + info.getCatalogNm() + " \n");
	// debugMsg.append("topImgPath = " + info.getTopImgPath() + " \n");
	// debugMsg.append("dtlImgPath = " + info.getDtlImgPath() + " \n");
	//
	//
	// debugMsg.append("txType = " + info.getTxType() + " \n");
	// debugMsg.append("cudType = " + info.getCudType() + " \n");
	// debugMsg.append("################################### CATALOG INFO ["+ title
	// +"] CATALOG INFO #################################### \n");
	//
	// log.debug(debugMsg.toString());
	// debugMsg.setLength(0);
	// debugMsg = null;
	//
	// }catch (Exception e) {
	// log.error("■■■■■printCatalog()■■■■■ : " + e.getMessage());
	// log.debug(e);
	// throw new ServiceException("printCatalog  Fail", e);
	// }
	//
	// return true;
	// }

}
