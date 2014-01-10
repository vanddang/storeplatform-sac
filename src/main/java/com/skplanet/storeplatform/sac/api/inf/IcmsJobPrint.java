/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.api.inf;

import org.apache.log4j.Logger;

import com.google.protobuf.ServiceException;
import com.skplanet.storeplatform.sac.api.vo.DpBrandInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogInfo;
import com.skplanet.storeplatform.sac.api.vo.TbDpProdCatalogMapgInfo;
import com.skplanet.storeplatform.sac.api.vo.TbDpProdDescInfo;
import com.skplanet.storeplatform.sac.api.vo.TbDpProdInfo;
import com.skplanet.storeplatform.sac.api.vo.TbDpProdOpt;
import com.skplanet.storeplatform.sac.api.vo.TbDpProdRshpInfo;
import com.skplanet.storeplatform.sac.api.vo.TbDpShpgProdInfo;
import com.skplanet.storeplatform.sac.api.vo.TbDpTenantProdInfo;
import com.skplanet.storeplatform.sac.api.vo.TbDpTenantProdPriceInfo;

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

	public static boolean printCatalog(DpCatalogInfo info, String title) throws Exception {

		StringBuffer debugMsg = new StringBuffer();

		try {

			debugMsg.append("################################### CATALOG INFO [" + title
					+ "] CATALOG INFO #################################### \n");
			debugMsg.append("catalogId = " + info.getCatalogId() + " \n");
			debugMsg.append("dpCatNo = " + info.getDpCatNo() + " \n");
			debugMsg.append("brandId = " + info.getBrandId() + " \n");
			debugMsg.append("catalogDesc = " + info.getCatalogDesc() + " \n");
			debugMsg.append("catalogNm = " + info.getCatalogNm() + " \n");
			debugMsg.append("topImgPath = " + info.getTopImgPath() + " \n");
			debugMsg.append("dtlImgPath = " + info.getDtlImgPath() + " \n");

			debugMsg.append("txType = " + info.getTxType() + " \n");
			debugMsg.append("cudType = " + info.getCudType() + " \n");
			debugMsg.append("################################### CATALOG INFO [" + title
					+ "] CATALOG INFO #################################### \n");

			System.out.println(debugMsg.toString());
			debugMsg.setLength(0);
			debugMsg = null;

		} catch (Exception e) {
			log.error("■■■■■printCatalog()■■■■■ : " + e.getMessage());
			log.debug(e);
			throw new ServiceException("printCatalog  Fail", e);
		}

		return true;
	}

	public static boolean printTblDpProd(TbDpProdInfo info, String title) throws Exception {

		StringBuffer debugMsg = new StringBuffer();

		try {

			debugMsg.append("################################### TbDpProdInfo INFO [" + title
					+ "] TbDpProdInfo INFO #################################### \n");
			debugMsg.append("prodId = " + info.getProdId() + " \n");
			debugMsg.append("prodChrgYn = " + info.getProdChrgYn() + " \n");
			debugMsg.append("sellerMbrNo = " + info.getSellerMbrNo() + " \n");
			debugMsg.append("svcGrpCd = " + info.getSvcGrpCd() + " \n");
			debugMsg.append("svcTypeCd = " + info.getSvcTypeCd() + " \n");
			debugMsg.append("prodGrdCd = " + info.getProdGrdCd() + " \n");
			debugMsg.append("cid = " + info.getCid() + " \n");
			debugMsg.append("feeCaseCd = " + info.getFeeCaseCd() + " \n");
			debugMsg.append("feeUnitCd = " + info.getFeeUnitCd() + " \n");
			debugMsg.append("usePeriodUnitCd = " + info.getUsePeriodUnitCd() + " \n");
			debugMsg.append("usePeriod = " + info.getUsePeriod() + " \n");
			debugMsg.append("drmYn = " + info.getDrmYn() + " \n");
			debugMsg.append("drmSetCd = " + info.getDrmSetCd() + " \n");
			debugMsg.append("regId = " + info.getRegId() + " \n");
			debugMsg.append("updId = " + info.getUpdId() + " \n");
			debugMsg.append("################################### TbDpProdInfo INFO [" + title
					+ "] TbDpProdInfo INFO #################################### \n");

			System.out.println(debugMsg.toString());
			debugMsg.setLength(0);
			debugMsg = null;

		} catch (Exception e) {
			log.error("■■■■■TbDpProdInfo()■■■■■ : " + e.getMessage());
			log.debug(e);
			throw new ServiceException("TbDpProdInfo  Fail", e);
		}

		return true;
	}

	public static boolean printTbDpShpgProd(TbDpShpgProdInfo info, String title) throws Exception {

		StringBuffer debugMsg = new StringBuffer();

		try {

			debugMsg.append("################################### TbDpShpgProdInfo INFO [" + title
					+ "] TbDpShpgProdInfo INFO #################################### \n");
			debugMsg.append("prodId = " + info.getProdId() + " \n");
			debugMsg.append("epsdCnt = " + info.getEpsdCnt() + " \n");
			debugMsg.append("chnlCompNm = " + info.getChnlCompNm() + " \n");
			debugMsg.append("samplUrl = " + info.getSamplUrl() + " \n");
			debugMsg.append("saleYn = " + info.getSaleYn() + " \n");
			debugMsg.append("contentsOrdrCd = " + info.getContentsOrdrCd() + " \n");
			debugMsg.append("metaClsfCd = " + info.getMetaClsfCd() + " \n");

			debugMsg.append("mgzinSubscripCd = " + info.getMgzinSubscripCd() + " \n");
			debugMsg.append("mmMaxSaleQty = " + info.getMmMaxSaleQty() + " \n");
			debugMsg.append("dayMaxSaleQty = " + info.getDayMaxSaleQty() + " \n");
			debugMsg.append("mmMbrMaxPrchsQty = " + info.getMmMbrMaxPrchsQty() + " \n");
			debugMsg.append("dayMbrMaxPrchsQty = " + info.getDayMbrMaxPrchsQty() + " \n");
			debugMsg.append("firstMaxPrchsQty = " + info.getFirstMaxPrchsQty() + " \n");
			debugMsg.append("usePlac = " + info.getUsePlac() + " \n");

			debugMsg.append("useLimtDesc = " + info.getUseLimtDesc() + " \n");
			debugMsg.append("noticeMatt = " + info.getNoticeMatt() + " \n");
			debugMsg.append("prchsCancelDrbkReason = " + info.getPrchsCancelDrbkReason() + " \n");
			debugMsg.append("prodCaseCd = " + info.getProdCaseCd() + " \n");
			debugMsg.append("b2bProdYn = " + info.getB2bProdYn() + " \n");
			debugMsg.append("dlvProdYn = " + info.getDlvProdYn() + " \n");
			debugMsg.append("mangBpId = " + info.getMangBpId() + " \n");
			debugMsg.append("mangBpId = " + info.getSrcContentId() + " \n");

			debugMsg.append("regId = " + info.getRegId() + " \n");
			debugMsg.append("updId = " + info.getUpdId() + " \n");
			debugMsg.append("################################### TbDpShpgProdInfo INFO [" + title
					+ "] TbDpShpgProdInfo INFO #################################### \n");

			System.out.println(debugMsg.toString());
			debugMsg.setLength(0);
			debugMsg = null;

		} catch (Exception e) {
			log.error("■■■■■TbDpShpgProdInfo()■■■■■ : " + e.getMessage());
			log.debug(e);
			throw new ServiceException("TbDpShpgProdInfo  Fail", e);
		}

		return true;
	}

	public static boolean printTbDpProdDesc(TbDpProdDescInfo info, String title) throws Exception {

		StringBuffer debugMsg = new StringBuffer();

		try {

			debugMsg.append("################################### TbDpProdDescInfo INFO [" + title
					+ "] TbDpProdDescInfo INFO #################################### \n");
			debugMsg.append("prodId = " + info.getProdId() + " \n");
			debugMsg.append("langCd = " + info.getLangCd() + " \n");
			debugMsg.append("prodNm = " + info.getProdNm() + " \n");
			debugMsg.append("prodDtlDesc = " + info.getProdDtlDesc() + " \n");
			debugMsg.append("regId = " + info.getRegId() + " \n");
			debugMsg.append("updId = " + info.getUpdId() + " \n");
			debugMsg.append("################################### TbDpProdDescInfo INFO [" + title
					+ "] TbDpProdDescInfo INFO #################################### \n");

			System.out.println(debugMsg.toString());
			debugMsg.setLength(0);
			debugMsg = null;

		} catch (Exception e) {
			log.error("■■■■■TbDpShpgProdInfo()■■■■■ : " + e.getMessage());
			log.debug(e);
			throw new ServiceException("TbDpShpgProdInfo  Fail", e);
		}

		return true;
	}

	public static boolean printTbDpProdRshp(TbDpProdRshpInfo info, String title) throws Exception {

		StringBuffer debugMsg = new StringBuffer();

		try {

			debugMsg.append("################################### TbDpProdRshpInfo INFO [" + title
					+ "] TbDpProdRshpInfo INFO #################################### \n");
			debugMsg.append("prodId = " + info.getProdId() + " \n");
			debugMsg.append("partProdId = " + info.getPartProdId() + " \n");
			debugMsg.append("regId = " + info.getRegId() + " \n");
			debugMsg.append("################################### TbDpProdRshpInfo INFO [" + title
					+ "] TbDpProdRshpInfo INFO #################################### \n");

			System.out.println(debugMsg.toString());
			debugMsg.setLength(0);
			debugMsg = null;

		} catch (Exception e) {
			log.error("■■■■■TbDpProdRshpInfo()■■■■■ : " + e.getMessage());
			log.debug(e);
			throw new ServiceException("TbDpProdRshpInfo  Fail", e);
		}

		return true;
	}

	public static boolean TbDpProdCatalogMapg(TbDpProdCatalogMapgInfo info, String title) throws Exception {

		StringBuffer debugMsg = new StringBuffer();

		try {

			debugMsg.append("################################### TbDpProdCatalogMapgInfo INFO [" + title
					+ "] TbDpProdCatalogMapgInfo INFO #################################### \n");
			debugMsg.append("prodId = " + info.getProdId() + " \n");
			debugMsg.append("catalogId" + info.getCatalogId() + " \n");
			debugMsg.append("regId = " + info.getRegId() + " \n");
			debugMsg.append("################################### TbDpProdCatalogMapgInfo INFO [" + title
					+ "] TbDpProdCatalogMapgInfo INFO #################################### \n");

			System.out.println(debugMsg.toString());
			debugMsg.setLength(0);
			debugMsg = null;

		} catch (Exception e) {
			log.error("■■■■■TbDpProdCatalogMapgInfo()■■■■■ : " + e.getMessage());
			log.debug(e);
			throw new ServiceException("TbDpProdCatalogMapgInfo  Fail", e);
		}

		return true;
	}

	public static boolean printTbDpProdOpt(TbDpProdOpt info, String title) throws Exception {

		StringBuffer debugMsg = new StringBuffer();

		try {

			debugMsg.append("################################### TbDpProdOpt INFO [" + title
					+ "] TbDpProdOpt INFO #################################### \n");
			debugMsg.append("regId = " + info.getRegId() + " \n");
			debugMsg.append("################################### TbDpProdRshpInfo INFO [" + title
					+ "] TbDpProdOpt INFO #################################### \n");

			System.out.println(debugMsg.toString());
			debugMsg.setLength(0);
			debugMsg = null;

		} catch (Exception e) {
			log.error("■■■■■TbDpProdRshpInfo()■■■■■ : " + e.getMessage());
			log.debug(e);
			throw new ServiceException("TbDpProdOpt  Fail", e);
		}

		return true;
	}

	public static boolean printTbTenantDpProd(TbDpTenantProdInfo info, String title) throws Exception {

		StringBuffer debugMsg = new StringBuffer();

		try {

			debugMsg.append("################################### TbDpTenantProdInfo INFO [" + title
					+ "] TbDpTenantProdInfo INFO #################################### \n");
			debugMsg.append("prodId = " + info.getProdId() + " \n");
			debugMsg.append("tenantId = " + info.getTenantId() + " \n");
			debugMsg.append("prodStatusCd = " + info.getProdStatusCd() + " \n");
			debugMsg.append("expoYn = " + info.getExpoYn() + " \n");
			debugMsg.append("expoOrd = " + info.getExpoOrd() + " \n");

			debugMsg.append("regId = " + info.getRegId() + " \n");
			debugMsg.append("updId = " + info.getUpdId() + " \n");
			debugMsg.append("################################### TbDpTenantProdInfo INFO [" + title
					+ "] TbDpTenantProdInfo INFO #################################### \n");

			System.out.println(debugMsg.toString());
			debugMsg.setLength(0);
			debugMsg = null;

		} catch (Exception e) {
			log.error("■■■■■TbDpShpgProdInfo()■■■■■ : " + e.getMessage());
			log.debug(e);
			throw new ServiceException("TbDpShpgProdInfo  Fail", e);
		}

		return true;
	}

	public static boolean printTbTenantDpProdPrice(TbDpTenantProdPriceInfo info, String title) throws Exception {

		StringBuffer debugMsg = new StringBuffer();

		try {

			debugMsg.append("################################### TbDpTenantProdPriceInfo INFO [" + title
					+ "] TbDpTenantProdPriceInfo INFO #################################### \n");
			debugMsg.append("taxClsf = " + info.getTaxClsf() + " \n");
			debugMsg.append("prodId = " + info.getProdId() + " \n");
			debugMsg.append("tenantId = " + info.getTenantId() + " \n");
			debugMsg.append("applyStartDt = " + info.getApplyStartDt() + " \n");
			debugMsg.append("seq = " + info.getSeq() + " \n");
			debugMsg.append("applyEndDt = " + info.getApplyEndDt() + " \n");
			debugMsg.append("prodAmt = " + info.getProdAmt() + " \n");
			debugMsg.append("chnlUnlmtAmt = " + info.getChnlUnlmtAmt() + " \n");
			debugMsg.append("chnlPeriodAmt = " + info.getChnlPeriodAmt() + " \n");
			debugMsg.append("prodNetAmt = " + info.getProdNetAmt() + " \n");
			debugMsg.append("dcRate = " + info.getDcRate() + " \n");
			debugMsg.append("dcAmt = " + info.getDcAmt() + " \n");
			debugMsg.append("regId = " + info.getRegId() + " \n");
			debugMsg.append("################################### TbDpTenantProdPriceInfo INFO [" + title
					+ "] TbDpTenantProdPriceInfo INFO #################################### \n");

			System.out.println(debugMsg.toString());
			debugMsg.setLength(0);
			debugMsg = null;

		} catch (Exception e) {
			log.error("■■■■■TbDpShpgProdInfo()■■■■■ : " + e.getMessage());
			log.debug(e);
			throw new ServiceException("TbDpShpgProdInfo  Fail", e);
		}

		return true;
	}

}
