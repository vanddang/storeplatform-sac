package com.skplanet.storeplatform.sac.api.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.api.conts.CouponConstants;
import com.skplanet.storeplatform.sac.api.except.CouponException;
import com.skplanet.storeplatform.sac.api.inf.IcmsJobPrint;
import com.skplanet.storeplatform.sac.api.vo.CouponContainer;
import com.skplanet.storeplatform.sac.api.vo.CouponParameterInfo;
import com.skplanet.storeplatform.sac.api.vo.CouponResponseInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCatalogTagInfo;
import com.skplanet.storeplatform.sac.api.vo.DpCouponInfo;
import com.skplanet.storeplatform.sac.api.vo.DpItemInfo;
import com.skplanet.storeplatform.sac.api.vo.TbDpProdCatalogMapgInfo;
import com.skplanet.storeplatform.sac.api.vo.TbDpProdDescInfo;
import com.skplanet.storeplatform.sac.api.vo.TbDpProdInfo;
import com.skplanet.storeplatform.sac.api.vo.TbDpProdOpt;
import com.skplanet.storeplatform.sac.api.vo.TbDpProdRshpInfo;
import com.skplanet.storeplatform.sac.api.vo.TbDpShpgProdInfo;
import com.skplanet.storeplatform.sac.api.vo.TbDpTenantProdInfo;
import com.skplanet.storeplatform.sac.api.vo.TbDpTenantProdPriceInfo;

@Service
@Transactional
public class CouponProcessServiceImpl implements CouponProcessService {
	private final static Logger log = Logger.getLogger(CouponProcessServiceImpl.class);
	private final boolean result = true;
	private String errorCode = "";
	private String message = "";

	@Autowired
	private CouponItemService couponItemService;

	@Autowired
	private BrandCatalogService brandCatalogService;

	@Override
	public boolean insertCouponInfo(CouponContainer containers, CouponParameterInfo couponParameterInfo) {

		// 호출
		log.info("■■■■■ processForCouponCSP ■■■■■");
		// 상품 추가/수정 작업을 호출한다.
		DpCouponInfo couponInfo = new DpCouponInfo(); // 쿠폰 정보
		couponInfo = containers.getDpCouponInfo();
		System.out.println("couponInfo::::" + couponInfo.getTag());
		List<TbDpProdInfo> tblDpProdList = new ArrayList<TbDpProdInfo>();
		List<TbDpShpgProdInfo> tbDpShpgProdList = new ArrayList<TbDpShpgProdInfo>();
		List<TbDpProdDescInfo> tbDpProdDescList = new ArrayList<TbDpProdDescInfo>();
		List<TbDpProdRshpInfo> tbDpProdRshpList = new ArrayList<TbDpProdRshpInfo>();
		List<TbDpProdCatalogMapgInfo> tbDpProdCatalogMapgList = new ArrayList<TbDpProdCatalogMapgInfo>();
		List<TbDpProdOpt> tbDpProdOptList = new ArrayList<TbDpProdOpt>();
		List<TbDpTenantProdInfo> tbDpTenantProdList = new ArrayList<TbDpTenantProdInfo>();
		List<TbDpTenantProdPriceInfo> tbDpTenantProdPriceList = new ArrayList<TbDpTenantProdPriceInfo>();
		List<DpCatalogTagInfo> tbDpProdTagList = new ArrayList<DpCatalogTagInfo>();

		if (containers != null) {
			List<DpItemInfo> itemInfoList = new ArrayList<DpItemInfo>(); // 아이템 정보 List;
			itemInfoList = containers.getDpItemInfo();
			// 1. Validation Check

			// COUPON 기본정보 등록상태확인
			log.info("■■■■■ validateContentInfo 시작 ■■■■■");
			if (!this.validateCouponInfo(couponInfo)) {
				throw new CouponException(this.errorCode, this.message, null);
			}
			log.info("■■■■■ validateContentInfo 패스 ■■■■■");

			// TB_DP_PROD 값 셋팅
			// log.info("■■■■■ setTbDpProdInfoValue 시작 ■■■■■");
			if (!this.setTbDpProdInfoValue(couponInfo, itemInfoList, tblDpProdList, couponParameterInfo.getCudType())) {
				throw new CouponException(this.errorCode, this.message, null);
			}

			// TB_DP_SHPG_PROD 값 셋팅
			// log.info("■■■■■ setTbDpShpgProdInfoValue 시작 ■■■■■");
			if (!this.setTbDpShpgProdListValue(couponInfo, itemInfoList, tbDpShpgProdList,
					couponParameterInfo.getCudType())) {
				throw new CouponException(this.errorCode, this.message, null);
			}

			// TB_DP_PROD_DESC 값 셋팅
			// log.info("■■■■■ setTbDpProdDescInfoValue 시작 ■■■■■");
			if (!this.setTbDpProdDescListValue(couponInfo, itemInfoList, tbDpProdDescList,
					couponParameterInfo.getCudType())) {
				throw new CouponException(this.errorCode, this.message, null);
			}

			// TB_DP_PROD_RSHP 값 셋팅
			// log.info("■■■■■ setTbDpProdRshpValue 시작 ■■■■■");
			if (!this
					.setTbDpProdRshpValue(couponInfo, itemInfoList, tbDpProdRshpList, couponParameterInfo.getCudType())) {
				throw new CouponException(this.errorCode, this.message, null);
			}

			// TB_DP_TENANT_PROD 값 셋팅
			// log.info("■■■■■ setTbDpTenantProdListValue 시작 ■■■■■");
			if (!this.setTbDpTenantProdListValue(couponInfo, itemInfoList, tbDpTenantProdList,
					couponParameterInfo.getCudType())) {
				throw new CouponException(this.errorCode, this.message, null);
			}

			// TB_DP_TENANT_PROD_PRICE 값 셋팅
			// log.info("■■■■■ setTbDpTenantProdPriceListValue 시작 ■■■■■");
			if (!this.setTbDpTenantProdPriceListValue(couponInfo, itemInfoList, tbDpTenantProdPriceList,
					couponParameterInfo.getCudType())) {
				throw new CouponException(this.errorCode, this.message, null);
			}

			// TB_DP_PROD_OPT 값 셋팅
			// log.info("■■■■■ setTbDpProdRshpValue 시작 ■■■■■");
			if (!this.setTbDpProdOptValue(couponInfo, itemInfoList, tbDpProdOptList, couponParameterInfo.getCudType())) {
				throw new CouponException(this.errorCode, this.message, null);
			}

			// TB_DP_PROD_CATALOG_MAPG 값 셋팅
			// log.info("■■■■■ setTbDpProdCatalogMapgInfoValue 시작 ■■■■■");
			if (!this.setTbDpProdCatalogMapgInfoValue(couponInfo, itemInfoList, tbDpProdCatalogMapgList,
					couponParameterInfo.getCudType())) {
				throw new CouponException(this.errorCode, this.message, null);
			}

			// TB_DP_PROD_TAG 값 셋팅
			// log.info("■■■■■ setTbDpTenantProdPriceListValue 시작 ■■■■■");
			if (!this.setTbDpProdTagListValue(couponInfo, tbDpProdTagList)) {
				throw new CouponException(this.errorCode, this.message, null);
			}

			System.out.println("::::::" + tbDpTenantProdPriceList.size());
			// log.info("■■■■■ setTbDpProdInfoValue 완료 ■■■■■");

		} else {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "containers is NULL!!", null);
		}
		// watch.stop();
		log.info("◆◆◆ to TimeString... 전체 처리 완료:  ◆◆◆");
		return this.result;
	}// End processForCouponCSP

	/**
	 * 쿠폰 컨텐트 Validation Check
	 * 
	 * @param ContentInfo
	 * @return Boolean result @
	 */
	private boolean validateCouponInfo(DpCouponInfo couponInfo) {

		// B2B상품 유효성 검증
		if (StringUtils.equalsIgnoreCase(couponInfo.getStoreb2bFlag(), "Y")) {
			if (StringUtils.equalsIgnoreCase(couponInfo.getStoreSaleType(),
					CouponConstants.COUPON_SALE_TYPE_SHIPPING_PROD)) {
				this.errorCode = CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC;
				this.message = "B2B상품은 배송상품 유형일 수 없습니다.";
				return false;
			}
		}

		// 업체 유효성 검증
		if (!this.validateBusinessPartner(couponInfo)) {
			this.errorCode = CouponConstants.COUPON_IF_ERROR_CODE_NOT_BPID;
			return false;
		}

		// 카탈로그 유효성 검증
		if (!this.validateCatalog(couponInfo)) { // CatalogCode 확인 해야 함
			this.errorCode = CouponConstants.COUPON_IF_ERROR_CODE_NOT_CATALOGID;
			return false;
		}

		return true;
	}// End validateContentInfo

	/**
	 * TbDpProd Info value 셋팅
	 * 
	 * @param DpCouponInfo
	 *            couponInfo, List<DpItemInfo> itemInfoList, List<TbDpProdInfo> tblDpProdList, String txType
	 * @return Boolean result @
	 */
	private boolean setTbDpProdInfoValue(DpCouponInfo couponInfo, List<DpItemInfo> itemInfoList,
			List<TbDpProdInfo> tblDpProdList, String cudType) {
		TbDpProdInfo dp = new TbDpProdInfo();
		try {

			// ////////////////// Coupon 정보 S////////////////////////////
			dp.setProdId(couponInfo.getProdId());
			dp.setProdChrgYn("N");
			dp.setSellerMbrNo("IF1023541432620111207152255");
			dp.setSvcGrpCd(CouponConstants.CUPON_SVC_GRP_CD); // "DP000206"; // MALL 구분 코드(쇼핑쿠폰)
			dp.setSvcTypeCd(CouponConstants.CHNL_TP_CD_CUPON); // "DP001117"; // 채널 타입 코드(쇼핑쿠폰)
			dp.setProdGrdCd(CouponConstants.AGE_RESTRICTIONS_DEFAULT_COMMON_CODE); // PD004401 전체 이용가
			dp.setCid(couponInfo.getCouponCode());
			dp.setFeeCaseCd(CouponConstants.PAY_CODE_TIME);// "PD00292"; // 과금유형(기간정액제)

			if (Integer.parseInt(couponInfo.getValidUntil()) > 0) { // 유효일수 값 비교
				dp.setUsePeriodUnitCd(CouponConstants.USE_PERIOD_UNIT_DAY); // PD00312 기간제(일)
				dp.setUsePeriod(Integer.parseInt(couponInfo.getValidUntil())); // 유효일수로 셋팅
			} else {
				dp.setUsePeriodUnitCd(CouponConstants.USE_PERIOD_UNIT_SELECT); // PD00319 기간선택 USE_TERM_UNIT
				dp.setUsePeriod(Long.parseLong(couponInfo.getValidEDate())); // 유효종료일시로 셋팅
			}

			dp.setDrmYn("Y");
			dp.setRegId(couponInfo.getBpId());
			dp.setUpdId(couponInfo.getBpId());
			tblDpProdList.add(dp);
			IcmsJobPrint.printTblDpProd(dp, "TBL_DP_PROD - COUPON");
			// ////////////////// Coupon 정보 E////////////////////////////

			// ////////////////// Item 정보 S////////////////////////////
			for (int i = 0; i < itemInfoList.size(); i++) {
				DpItemInfo itemInfo = itemInfoList.get(i);
				dp = new TbDpProdInfo();
				dp.setProdId(itemInfo.getProdId());
				if (String.valueOf(itemInfo.getItemPrice()).equals("0")) { // 무료
					dp.setProdChrgYn("N");
				} else { // 유료
					dp.setProdChrgYn("Y");
				}
				dp.setSellerMbrNo("IF1023541432620111207152255");
				dp.setSvcGrpCd(CouponConstants.CUPON_SVC_GRP_CD); // "DP000206"; // MALL 구분 코드(쇼핑쿠폰)
				dp.setSvcTypeCd(CouponConstants.CHNL_TP_CD_CUPON); // "DP001117"; // 채널 타입 코드(쇼핑쿠폰)
				dp.setProdGrdCd(CouponConstants.AGE_RESTRICTIONS_DEFAULT_COMMON_CODE); // PD004401 전체 이용가
				dp.setCid(itemInfo.getItemCode()); // 아이템코드
				dp.setFeeCaseCd(CouponConstants.PAY_CODE_TIME); // "PD00292"; // 과금유형(기간정액제)

				if (Integer.parseInt(couponInfo.getValidUntil()) > 0) { // 유효일수 값 비교
					dp.setUsePeriodUnitCd(CouponConstants.USE_PERIOD_UNIT_DAY); // PD00312 기간제(일)
					dp.setUsePeriod(Integer.parseInt(couponInfo.getValidUntil())); // 유효일수로 셋팅
				} else {
					dp.setUsePeriodUnitCd(CouponConstants.USE_PERIOD_UNIT_SELECT); // PD00319 기간선택 USE_TERM_UNIT
					dp.setUsePeriod(Long.parseLong(couponInfo.getValidEDate())); // 유효종료일시로 셋팅
				}

				dp.setDrmYn("Y");
				dp.setRegId(couponInfo.getBpId());
				dp.setUpdId(couponInfo.getBpId());
				tblDpProdList.add(dp);
				IcmsJobPrint.printTblDpProd(dp, "TBL_DP_PROD - ITEM::" + i);
			}
			// ////////////////// Item 정보 E////////////////////////////

			// 저장
			this.couponItemService.insertTbDpProdInfo(tblDpProdList, cudType);

			// // Phone 목록저장
			// PhoneService phService = new PhoneService();
			// phService.setPhoneListToInfo(cInfo);

			// 세팅 값 확인

		} catch (CouponException e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TBL_DP_PROD VO 셋팅 실패", null);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TBL_DP_PROD VO 셋팅 실패", null);
		}
		return true;
	}// End setTbDpProdInfoValue

	/**
	 * TbDpShpgProd Info value 셋팅
	 * 
	 * @param DpCouponInfo
	 *            couponInfo, List<DpItemInfo> itemInfoList, List<TbDpShpgProdInfo> tbDpShpgProdList, String txType
	 * @return Boolean result @
	 */
	private boolean setTbDpShpgProdListValue(DpCouponInfo couponInfo, List<DpItemInfo> itemInfoList,
			List<TbDpShpgProdInfo> tbDpShpgProdList, String cudType) {
		TbDpShpgProdInfo dsp = new TbDpShpgProdInfo();
		try {

			// ////////////////// Coupon 정보 S////////////////////////////
			dsp.setProdId(couponInfo.getProdId());
			dsp.setContentsTypeCd("");
			dsp.setEpsdCnt(0);//
			dsp.setChnlCompNm("GTSOFT");
			if (couponInfo.getCoupnStatus().equals("3")) { // 2:판매대기 3:판매중 4:판매중지 5:판매금지
				dsp.setSaleYn("Y");
			} else {
				dsp.setSaleYn("N");
			}

			dsp.setContentsOrdrCd(CouponConstants.UPDATE_TYPE_CD_DESC);// D
			dsp.setMetaClsfCd(CouponConstants.CATEGORY_ID_CUPON_SERIES_CONTENT); // "CT28"; // 쇼핑쿠폰 부모 카테고리ID
			dsp.setMmMaxSaleQty(0);
			dsp.setDayMaxSaleQty(0);
			dsp.setMmMbrMaxPrchsQty(0);
			dsp.setDayMbrMaxPrchsQty(0);
			dsp.setFirstMaxPrchsQty(0);
			dsp.setUsePlac(couponInfo.getDirection());
			dsp.setUseLimtDesc(couponInfo.getUseCondition());
			dsp.setNoticeMatt(couponInfo.getAddtionalInfo());
			dsp.setPrchsCancelDrbkReason(couponInfo.getRefundCondition());

			if (couponInfo.getStoreSaleType().equals("1")) { // 상품권
				dsp.setProdCaseCd(CouponConstants.PAY_CODE_GIFTCARD);
				dsp.setDlvProdYn("N");
			} else if (couponInfo.getStoreSaleType().equals("2")) { // 교환권
				dsp.setProdCaseCd(CouponConstants.PAY_CODE_CUPON);
				dsp.setDlvProdYn("N");
			} else if (couponInfo.getStoreSaleType().equals("3")) { // 배송상품
				dsp.setProdCaseCd(CouponConstants.PAY_CODE_DELIVER);
				dsp.setDlvProdYn("Y");
			}
			dsp.setB2bProdYn(couponInfo.getStoreb2bFlag());
			dsp.setMangBpId("");
			dsp.setSrcContentId(couponInfo.getCouponCode());
			dsp.setRegId(couponInfo.getBpId());
			dsp.setUpdId(couponInfo.getBpId());
			tbDpShpgProdList.add(dsp);
			IcmsJobPrint.printTbDpShpgProd(dsp, "TB_DP_SHPG_PROD - COUPON");
			// ////////////////// Coupon 정보 E////////////////////////////

			int itemCnt = 0;
			for (DpItemInfo itemInfo : itemInfoList) {
				// 판매중인 아이템 수만 count
				if (itemInfo.getItemStatus().equals("3")) {// 2:판매대기 3:판매중 4:판매중지 5:판매금지
					itemCnt++;
				}
			}

			// ////////////////// Item 정보 S////////////////////////////

			for (int i = 0; i < itemInfoList.size(); i++) {
				DpItemInfo itemInfo = itemInfoList.get(i);
				dsp = new TbDpShpgProdInfo();
				dsp.setProdId(itemInfo.getProdId());
				dsp.setContentsTypeCd("");
				dsp.setEpsdCnt(itemCnt);// 판매중인 아이템 수만 count
				dsp.setChnlCompNm("GTSOFT");
				if (itemInfo.getItemStatus().equals("3")) { // 2:판매대기 3:판매중 4:판매중지 5:판매금지
					dsp.setSaleYn("Y");
				} else {
					dsp.setSaleYn("N");
				}

				dsp.setContentsOrdrCd(CouponConstants.UPDATE_TYPE_CD_DESC);// D
				dsp.setMetaClsfCd(CouponConstants.CATEGORY_ID_CUPON_SERIES_CONTENT); // "CT28"; // 쇼핑쿠폰 부모 카테고리ID
				dsp.setMmMaxSaleQty(Long.parseLong(itemInfo.getMaxCountMonthly()));
				dsp.setDayMaxSaleQty(Long.parseLong(itemInfo.getMaxCountDaily()));
				dsp.setMmMbrMaxPrchsQty(Long.parseLong(itemInfo.getMaxCountMonthlyUser()));
				dsp.setDayMbrMaxPrchsQty(Long.parseLong(itemInfo.getMaxCountDailyUser()));
				dsp.setFirstMaxPrchsQty(Long.parseLong(itemInfo.getBuyMaxLimit()));
				dsp.setUsePlac(couponInfo.getDirection());
				dsp.setUseLimtDesc(couponInfo.getUseCondition());
				dsp.setNoticeMatt(couponInfo.getAddtionalInfo());
				dsp.setPrchsCancelDrbkReason(couponInfo.getRefundCondition());

				if (couponInfo.getStoreSaleType().equals("1")) { // 상품권
					dsp.setProdCaseCd(CouponConstants.PAY_CODE_GIFTCARD);
					dsp.setDlvProdYn("N");
				} else if (couponInfo.getStoreSaleType().equals("2")) { // 교환권
					dsp.setProdCaseCd(CouponConstants.PAY_CODE_CUPON);
					dsp.setDlvProdYn("N");
				} else if (couponInfo.getStoreSaleType().equals("3")) { // 배송상품
					dsp.setProdCaseCd(CouponConstants.PAY_CODE_DELIVER);
					dsp.setDlvProdYn("Y");
				}
				dsp.setB2bProdYn(couponInfo.getStoreb2bFlag());
				dsp.setMangBpId(itemInfo.getBpManageId());
				dsp.setSrcContentId(itemInfo.getItemCode());
				dsp.setRegId(couponInfo.getBpId());
				dsp.setUpdId(couponInfo.getBpId());
				tbDpShpgProdList.add(dsp);
				IcmsJobPrint.printTbDpShpgProd(dsp, "TB_DP_SHPG_PROD - ITEM::" + i);
			}

			// 저장
			this.couponItemService.insertTbDpShpgProdInfo(tbDpShpgProdList, cudType);
		} catch (CouponException e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_SHPG_PROD VO 셋팅 실패", null);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_SHPG_PROD VO 셋팅 실패", null);
		}
		return true;
	}// End setTbDpProdInfoValue

	/**
	 * setTbDpProdDesc Info value 셋팅
	 * 
	 * @param DpCouponInfo
	 *            couponInfo, List<DpItemInfo> itemInfoList, List<TbDpProdDescInfo> tblDpProdList, String cudType
	 * @return Boolean result @
	 */
	private boolean setTbDpProdDescListValue(DpCouponInfo couponInfo, List<DpItemInfo> itemInfoList,
			List<TbDpProdDescInfo> tbDpProdDescList, String cudType) {
		TbDpProdDescInfo dpd = new TbDpProdDescInfo();
		try {

			// ////////////////// Coupon 정보 S////////////////////////////
			dpd.setProdId(couponInfo.getProdId());
			dpd.setLangCd(CouponConstants.LANG_CD_KO); // ko 한글 코드값
			dpd.setProdNm(couponInfo.getCouponName());
			dpd.setProdBaseDesc("");
			dpd.setProdDtlDesc(couponInfo.getDescription());
			dpd.setProdIntrDscr("");
			dpd.setProdUseMtd("");
			dpd.setRegId(couponInfo.getBpId());
			dpd.setUpdId(couponInfo.getBpId());
			tbDpProdDescList.add(dpd);
			IcmsJobPrint.printTbDpProdDesc(dpd, "TB_DP_PROD_DESC- COUPON");
			// ////////////////// Coupon 정보 E////////////////////////////

			// ////////////////// Item 정보 S////////////////////////////
			for (int i = 0; i < itemInfoList.size(); i++) {
				DpItemInfo itemInfo = itemInfoList.get(i);
				dpd = new TbDpProdDescInfo();
				dpd.setProdId(itemInfo.getProdId());
				dpd.setLangCd(CouponConstants.LANG_CD_KO); // ko 한글 코드값
				dpd.setProdNm(itemInfo.getItemName());
				dpd.setProdBaseDesc("");
				dpd.setProdDtlDesc("");
				dpd.setProdIntrDscr("");
				dpd.setProdUseMtd("");
				dpd.setRegId(couponInfo.getBpId());
				dpd.setUpdId(couponInfo.getBpId());
				tbDpProdDescList.add(dpd);
				IcmsJobPrint.printTbDpProdDesc(dpd, "TB_DP_PROD_DESC- ITEM:::" + i);
			}

			// 저장
			this.couponItemService.insertTbDpProdDescInfo(tbDpProdDescList, cudType);

		} catch (CouponException e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_PROD_DESC VO 셋팅 실패", null);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_PROD_DESC VO 셋팅 실패", null);
		}
		return true;
	}// End setTbDpProdDesc

	/**
	 * setTbDpProdRshp Info value 셋팅
	 * 
	 * @param DpCouponInfo
	 *            couponInfo, List<DpItemInfo> itemInfoList,List<TbDpProdRshpInfo> tbDpProdRshpList, String cudType
	 * @return Boolean result @
	 */
	private boolean setTbDpProdRshpValue(DpCouponInfo couponInfo, List<DpItemInfo> itemInfoList,
			List<TbDpProdRshpInfo> tbDpProdRshpList, String cudType) {
		TbDpProdRshpInfo dps = new TbDpProdRshpInfo();
		try {

			// ////////////////// Item 정보 S////////////////////////////
			for (int i = 0; i < itemInfoList.size(); i++) {
				DpItemInfo itemInfo = itemInfoList.get(i);
				dps = new TbDpProdRshpInfo();
				dps.setProdId(couponInfo.getProdId()); // 채널 상품
				dps.setPartProdId(itemInfo.getProdId()); // 에피소드 상품
				dps.setProdRshpCd(CouponConstants.PROD_RSHP_CD); // 채널-에피소드 상품 관계
				dps.setRegId(couponInfo.getBpId());
				tbDpProdRshpList.add(dps);
				IcmsJobPrint.printTbDpProdRshp(dps, "TB_DP_PROD_RSHP- ITEM:::" + i);
			}
			// 저장
			this.couponItemService.insertTbDpProdRshpInfo(tbDpProdRshpList, cudType);

		} catch (CouponException e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_PROD_RSHP VO 셋팅 실패", null);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_PROD_RSHP VO 셋팅 실패", null);
		}
		return true;
	}// End setTbDpProdDesc

	/**
	 * setTbDpTenantProdList Info value 셋팅
	 * 
	 * @param DpCouponInfo
	 *            couponInfo, List<DpItemInfo> itemInfoList,List<TbDpTenantProdInfo> tbDpTenantProdList, String cudType
	 * @return Boolean result @
	 */
	private boolean setTbDpTenantProdListValue(DpCouponInfo couponInfo, List<DpItemInfo> itemInfoList,
			List<TbDpTenantProdInfo> tbDpTenantProdList, String cudType) {
		TbDpTenantProdInfo dtpd = new TbDpTenantProdInfo();
		try {

			// ////////////////// Coupon 정보 S////////////////////////////
			dtpd.setProdId(couponInfo.getProdId());
			dtpd.setTenantId(CouponConstants.TENANT_ID);
			dtpd.setProdStatusCd(this.getDPStatusCode(couponInfo.getCoupnStatus()));
			dtpd.setExpoYn("Y");
			dtpd.setExpoOrd(1);
			dtpd.setRegId(couponInfo.getBpId());
			dtpd.setUpdId(couponInfo.getBpId());
			tbDpTenantProdList.add(dtpd);
			IcmsJobPrint.printTbTenantDpProd(dtpd, "TB_DP_TENANT_PROD - COUPON");
			// ////////////////// Coupon 정보 E////////////////////////////

			// ////////////////// Item 정보 S////////////////////////////
			for (int i = 0; i < itemInfoList.size(); i++) {
				DpItemInfo itemInfo = itemInfoList.get(i);
				dtpd = new TbDpTenantProdInfo();
				dtpd.setProdId(itemInfo.getProdId());
				dtpd.setTenantId(CouponConstants.TENANT_ID);
				dtpd.setProdStatusCd(this.getDPStatusCode(itemInfo.getItemStatus()));
				dtpd.setExpoYn("Y");
				dtpd.setExpoOrd(i + 1);
				dtpd.setRegId(couponInfo.getBpId());
				dtpd.setUpdId(couponInfo.getBpId());
				tbDpTenantProdList.add(dtpd);
				IcmsJobPrint.printTbTenantDpProd(dtpd, "TB_DP_TENANT_PROD - ITEM:::" + i);
			}
			// 저장
			this.couponItemService.insertTbDpTenantProdInfo(tbDpTenantProdList, cudType);
		} catch (CouponException e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_TENANT_PROD VO 셋팅 실패", null);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_TENANT_PROD VO 셋팅 실패", null);
		}
		return true;
	}// End setTbDpTenantProdList

	/**
	 * setTbDpTenantProdPrice Info value 셋팅
	 * 
	 * @param DpCouponInfo
	 *            couponInfo, List<DpItemInfo> itemInfoList, List<TbDpTenantProdPriceInfo> tbDpTenantProdPriceList,
	 *            String cudType
	 * @return Boolean result @
	 */
	private boolean setTbDpTenantProdPriceListValue(DpCouponInfo couponInfo, List<DpItemInfo> itemInfoList,
			List<TbDpTenantProdPriceInfo> tbDpTenantProdPriceList, String cudType) {
		TbDpTenantProdPriceInfo dtpp = new TbDpTenantProdPriceInfo();
		try {

			// ////////////////// Coupon 정보 S////////////////////////////
			dtpp.setTenantId(CouponConstants.TENANT_ID);
			dtpp.setProdId(couponInfo.getProdId());
			dtpp.setApplyStartDt(couponInfo.getIssueSDate()); // 적용_시작_일시
			dtpp.setSeq(1);
			dtpp.setApplyEndDt(couponInfo.getIssueEDate()); // 적용_종료_일시
			dtpp.setProdAmt(0);// 상품_금액
			dtpp.setChnlUnlmtAmt(0);// 채널_무제한_금액
			dtpp.setChnlPeriodAmt(0);// 채널_기간_금액
			dtpp.setProdNetAmt(0); // 상품_정찰_금액
			dtpp.setDcRate(Long.parseLong(couponInfo.getAccountingRate())); // 할인_율
			dtpp.setDcAmt(0); // 할인_금액
			dtpp.setTaxClsf(""); // 세금_구분
			dtpp.setRegId(couponInfo.getBpId());
			dtpp.setUpdId(couponInfo.getBpId());
			tbDpTenantProdPriceList.add(dtpp);
			IcmsJobPrint.printTbTenantDpProdPrice(dtpp, "TB_DP_TENANT_PROD_PRICE - COUPON");
			// ////////////////// Coupon 정보 E////////////////////////////

			// ////////////////// Item 정보 S////////////////////////////
			for (int i = 0; i < itemInfoList.size(); i++) {
				DpItemInfo itemInfo = itemInfoList.get(i);
				dtpp = new TbDpTenantProdPriceInfo();
				dtpp.setProdId(itemInfo.getProdId());
				dtpp.setTenantId(CouponConstants.TENANT_ID);
				dtpp.setApplyStartDt(couponInfo.getIssueSDate());
				dtpp.setSeq(1);
				dtpp.setApplyEndDt(couponInfo.getIssueEDate());
				dtpp.setProdAmt(Long.parseLong(itemInfo.getSalePrice()));
				dtpp.setChnlUnlmtAmt(0); // ??
				dtpp.setChnlPeriodAmt(0); // ??
				dtpp.setProdNetAmt(Long.parseLong(itemInfo.getOrgPrice()));
				dtpp.setDcRate(Long.parseLong(itemInfo.getDcRate()));
				if (Long.parseLong(itemInfo.getOrgPrice()) - Long.parseLong(itemInfo.getSalePrice()) > 0) { // 금액이 >0 보다
					dtpp.setDcAmt(Long.parseLong(itemInfo.getOrgPrice()) - Long.parseLong(itemInfo.getSalePrice()));
				} else {
					dtpp.setDcAmt(0);
				}
				dtpp.setTaxClsf(""); // 세금_구분
				dtpp.setRegId(couponInfo.getBpId());
				dtpp.setUpdId(couponInfo.getBpId());
				tbDpTenantProdPriceList.add(dtpp);
				IcmsJobPrint.printTbTenantDpProdPrice(dtpp, "TB_DP_TENANT_PROD_PRICE- ITEM:::" + i);
			}
			// 저장
			this.couponItemService.insertTbDpTenantProdPriceInfo(tbDpTenantProdPriceList, cudType);
		} catch (CouponException e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_TENANT_PROD_PRICE VO 셋팅 실패",
					null);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_TENANT_PROD_PRICE VO 셋팅 실패",
					null);
		}
		return true;
	}// End setTbDpTenantProdPrice

	/**
	 * setTbDpProdCatalogMapgInfoValue Info value 셋팅
	 * 
	 * @param DpCouponInfo
	 *            couponInfo, List<DpItemInfo> itemInfoList, List<TbDpProdCatalogMapgInfo> tbDpProdCatalogMapgList,
	 *            String cudType
	 * @return Boolean result @
	 */
	private boolean setTbDpProdCatalogMapgInfoValue(DpCouponInfo couponInfo, List<DpItemInfo> itemInfoList,
			List<TbDpProdCatalogMapgInfo> tbDpProdCatalogMapgList, String cudType) {
		TbDpProdCatalogMapgInfo dpcm = new TbDpProdCatalogMapgInfo();
		try {
			// 상품- 카탈로그 에서는 상품 채널 - 카탈로그만 저장
			// ////////////////// Coupon 정보 S////////////////////////////
			dpcm.setProdId(couponInfo.getProdId());
			dpcm.setCatalogId(couponInfo.getStoreCatalogCode());
			dpcm.setBaseYn("Y");
			dpcm.setUseYn("Y");
			dpcm.setRegId(couponInfo.getBpId());
			dpcm.setUpdId(couponInfo.getBpId());
			tbDpProdCatalogMapgList.add(dpcm);
			IcmsJobPrint.TbDpProdCatalogMapg(dpcm, "TB_DP_PROD_CATALOG_MAPG- COUPON");
			// ////////////////// Coupon 정보 E////////////////////////////

			this.couponItemService.insertTbDpProdCatalogMapgInfo(tbDpProdCatalogMapgList, cudType,
					couponInfo.getProdId());
		} catch (CouponException e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_PROD_CATALOG_MAPG VO 셋팅 실패",
					null);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_PROD_CATALOG_MAPG VO 셋팅 실패",
					null);
		}
		return true;
	}// End setTbDpProdCatalogMapgInfoValue

	/**
	 * setTbDpProdOptValue Info value 셋팅
	 * 
	 * @param DpCouponInfo
	 *            couponInfo, List<DpItemInfo> itemInfoList,List<DpItemInfo> tbDpProdOptList, String cudType
	 * @return Boolean result @
	 */
	private boolean setTbDpProdOptValue(DpCouponInfo couponInfo, List<DpItemInfo> itemInfoList,
			List<TbDpProdOpt> tbDpProdOptList, String cudType) {
		TbDpProdOpt dpo = new TbDpProdOpt();
		try {

			boolean newOpt = true;
			ArrayList<String> optListForCha = new ArrayList<String>();
			// ////////////////// Item 정보 S////////////////////////////
			for (int i = 0; i < itemInfoList.size(); i++) {
				DpItemInfo itemInfo = itemInfoList.get(i);

				// 옵션2가 없는 경우는 채널 생성하지 않음 20130603 이성수차장요청
				if (StringUtils.isNotBlank(itemInfo.getItemValue2())) {
					if (StringUtils.isNotBlank(itemInfo.getItemValue1())) {
						for (String optpdNm1 : optListForCha) {
							// 중복 된 옵션이 있거나 옵션이 없는 경우 flase
							if (StringUtils.equalsIgnoreCase(optpdNm1, itemInfo.getItemValue1())) {
								newOpt = false;
							}
						}
						if (newOpt) {
							optListForCha.add(itemInfo.getItemValue1());
						}
						// 옵션 중복여부 초기화
						newOpt = true;
					}
				}
			}
			// 채널용 옵션 설정
			for (String optNmStr : optListForCha) {

				dpo = new TbDpProdOpt();
				dpo.setChnlProdId(couponInfo.getProdId());
				dpo.setEpsdProdId(couponInfo.getProdId());
				dpo.setOpt1Nm(optNmStr);
				dpo.setOpt2Nm(couponInfo.getProdId());
				dpo.setExpoOrd(CouponConstants.OPT_NUMBER_FOR_CHANNEL);
				dpo.setExpoYn("");
				dpo.setRegId(couponInfo.getBpId());
				dpo.setUpdId(couponInfo.getBpId());
				tbDpProdOptList.add(dpo);
				IcmsJobPrint.printTbDpProdOpt(dpo, "TB_DP_PROD_OPT- ITEM:::");
			}
			boolean firstFlag = false;

			// ////////////////// Item 정보 S////////////////////////////
			// 에피소드용 옵션 설정
			for (int i = 0; i < itemInfoList.size(); i++) {
				DpItemInfo itemInfo = itemInfoList.get(i);
				if (StringUtils.isNotBlank(itemInfo.getItemValue1())) {
					firstFlag = true;
				}

				if (firstFlag) {
					dpo = new TbDpProdOpt();
					dpo.setChnlProdId(couponInfo.getProdId());
					dpo.setEpsdProdId(itemInfo.getProdId());
					dpo.setOpt1Nm(itemInfo.getItemValue1());
					// 옵션2가 없는 경우 : 옵션 1로 설정 20130603 이성수차장요청
					if (StringUtils.isNotBlank(itemInfo.getItemValue2())) {
						dpo.setOpt2Nm(itemInfo.getItemValue2());
					} else {
						dpo.setOpt2Nm(itemInfo.getItemValue1());
					}
					// 옵션2가 없는 경우 : 옵션 1로 설정 20130603 이성수차장요청
					if (StringUtils.isNotBlank(itemInfo.getItemValue2())) {
						dpo.setExpoOrd(CouponConstants.OPT_NUMBER_FOR_EPISODE);
					} else {
						dpo.setExpoOrd(CouponConstants.OPT_NUMBER_FOR_CHANNEL);
					}
					dpo.setExpoYn("");
					dpo.setRegId(couponInfo.getBpId());
					dpo.setUpdId(couponInfo.getBpId());
					tbDpProdOptList.add(dpo);
					IcmsJobPrint.printTbDpProdOpt(dpo, "TB_DP_PROD_OPT- ITEM:::" + i);
				}
			}
			// 저장
			this.couponItemService.insertTbDpProdOptInfo(tbDpProdOptList, cudType);

		} catch (CouponException e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_PROD_OPT VO 셋팅 실패", null);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_PROD_OPT VO 셋팅 실패", null);
		}
		return true;
	}// End setTbDpProdOptValue

	/**
	 * setTbDpProdTagListValue Info value 셋팅
	 * 
	 * @param DpCouponInfo
	 *            couponInfo, List<DpCatalogTagInfo> tbDpProdTagList
	 * @return Boolean result @
	 */
	private boolean setTbDpProdTagListValue(DpCouponInfo couponInfo, List<DpCatalogTagInfo> tbDpProdTagList) {
		try {

			// ////////////////// Coupon 정보 S////////////////////////////
			ArrayList<DpCatalogTagInfo> tagList = new ArrayList<DpCatalogTagInfo>(); // TBL_DP_TAG_INFO 정보
			String[] tags = couponInfo.getTag().split(",");

			for (String tagNm : tags) {
				DpCatalogTagInfo tagInfo = new DpCatalogTagInfo();

				tagInfo.setCid(couponInfo.getProdId());
				tagInfo.setTagTypeCd(CouponConstants.TAG_TYPE_FOR_COUPON_TAG);
				tagInfo.setTagCd("");
				tagInfo.setTagNm(tagNm);
				tagInfo.setRegId(couponInfo.getBpId());
				tagInfo.setUpdId(couponInfo.getBpId());
				tagList.add(tagInfo);
			}
			// 저장
			this.couponItemService.insertTblTagInfo(tagList);

		} catch (CouponException e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TBL_DP_TAG_INFO VO 셋팅 실패", null);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TBL_DP_TAG_INFO VO 셋팅 실패", null);
		}
		return true;
	}// End setTbDpTenantProdPrice

	/**
	 * 해당 업체가 존재 하는지 체크 한다.
	 * 
	 * @param ContentInfo
	 * @return Boolean result @
	 */
	private boolean validateBusinessPartner(DpCouponInfo couponInfo) {
		log.info("■■■■■ validateBusinessPartner ■■■■■");

		// UserMember mbrInfo = cpContentDAO.getUserMemberInfo(cInfo.getCreateId());
		if (true) {
			// if (mbrInfo != null) {
			// if (StringUtils.isBlank(mbrInfo.getCompNm())) {
			// throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "상호명이 없습니다.", mbrInfo.getMbrId());
			// }
		} else {
			return false;
		}
		return true;
	}// End validateBusinessPartner

	// /**
	// * 해당 카탈로그가 존재 하는지 체크 한다.
	// *
	// * @param String
	// * catalogCode
	// * @return Boolean result
	// * @
	// */
	private boolean validateCatalog(DpCouponInfo couponInfo) {
		String catalogID = this.brandCatalogService.getCreateCatalogId(couponInfo.getStoreCatalogCode());
		if (StringUtils.isNotBlank(catalogID)) {
			log.info("validateCatalog ■■■ catalogID : " + catalogID + "■■■");
			couponInfo.setStoreCatalogCode(catalogID);
		} else {
			return false;
		}
		return true;
	}// End validateCatalog

	/**
	 * 쇼핑쿠폰 상품 상태 변경
	 * 
	 * @param CouponParameterInfo
	 * @return Boolean result
	 */
	@Override
	public boolean updateForCouponStatus(CouponParameterInfo couponParameterInfo) {
		// 호출
		log.info("■■■■■ processForCouponStatus ■■■■■");
		if (couponParameterInfo != null && StringUtils.isNotBlank(couponParameterInfo.getCouponCode())
				&& StringUtils.isNotBlank(couponParameterInfo.getCoupnStatus())) {

			String couponCode = couponParameterInfo.getCouponCode();
			String coupnStatus = couponParameterInfo.getCoupnStatus();
			String itemCode = couponParameterInfo.getItemCode();
			String upType = couponParameterInfo.getUpType(); // 0=상품상태변경, 1=단품상태변경, 2=상품+단품상태 모두 변경

			log.debug("===========================\n" + "couponCode : " + couponCode + "/n" + "itemCode : " + itemCode
					+ "/n" + "upType : " + upType + "/n" + "coupnStatus : " + coupnStatus + "/n"
					+ "===========================");

			String dpStatusCode = "";

			// 1. Validation Check
			// 쿠폰상태 값 유효성 검증
			if (!(StringUtils.equalsIgnoreCase(upType, "0") || StringUtils.equalsIgnoreCase(upType, "1") || StringUtils
					.equalsIgnoreCase(upType, "2"))) {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "쿠폰상태 값이 유효하지 않습니다.",
						coupnStatus);
			}

			// 기등록된 컨텐트 존재여부 확인 old쿠폰ID,아이템ID로 new쿠폰ID,아이템ID 가져오기 조회
			String newCouponCode = this.couponItemService.getGenerateId(couponCode);
			System.out.println("itemCode:::" + itemCode);
			if ((StringUtils.equalsIgnoreCase(upType, "1"))) { // 1=단품상태변경 item 값은 필수
				itemCode = "";
				itemCode = this.couponItemService.getGenerateId(itemCode);
				if (StringUtils.isBlank(itemCode)) {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_NOT_ITEMID, null, null);
				}
			}

			if (StringUtils.isBlank(newCouponCode)) {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_NOT_COUPONID, null, null);
			}

			// 쿠폰상태 코드 -> 전시 코드로 변환
			dpStatusCode = this.getDPStatusCode(coupnStatus);

			// 2. DB Update
			try {

				// TB_DP_TENANT_PROD 상태 처리
				this.couponItemService.updateCouponStatus(newCouponCode, dpStatusCode, upType, itemCode);

			} catch (CouponException e) {
				throw new CouponException(e.getErrCode(), e.getError_data().getERROR_MSG(), null);
			} catch (Exception e) {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, e.getMessage(), null);
			} finally {
				log.info("■■■■■ DB Transaction END ■■■■■");
			}

		} else {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "CouponParameterInfo is NULL!!",
					null);
		}
		return true;
	}// End processForCouponStatus

	/**
	 * 쿠폰 상태코드별 전시상태코드를 반환한다.
	 * 
	 * @param coupnStatus
	 * @return DPStatusCode @
	 */
	private String getDPStatusCode(String coupnStatus) {
		String dpStatusCode = "";
		if (coupnStatus.equals("2")) {
			dpStatusCode = CouponConstants.DP_STATUS_PREPARE_SERVICE; // "PD000402" 판매대기
		} else if (coupnStatus.equals("3")) {
			dpStatusCode = CouponConstants.DP_STATUS_IN_SERVICE; // "PD000403" 판매중
		} else if (coupnStatus.equals("4")) {
			dpStatusCode = CouponConstants.DP_STATUS_STOP_SERVICE; // "PD000404" 판매중지
		} else if (coupnStatus.equals("5")) {
			dpStatusCode = CouponConstants.DP_STATUS_FORBID_SERVICE_DL_AVIL; // "PD000409" 판매금지(다운로드 허용)
		} else {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC,
					"CouponStatus 또는 itemStatus 값이 유효하지 않습니다.", coupnStatus);
		}
		return dpStatusCode;
	}// End getDPStatusCode

	/**
	 * 특가 상품 목록 조회 한다.
	 * 
	 * @param String
	 *            [] couponCodes
	 * @response List<CouponResponseInfo>
	 */
	@Override
	public List<CouponResponseInfo> getSpecialProductList(String[] couponCodes) {
		log.info("<<<<< CouponContentService >>>>> getSpecialProductList...");
		List<CouponResponseInfo> list = null;
		try {
			list = this.couponItemService.getSpecialProductList(couponCodes);

		} catch (CouponException e) {
			log.error(e);
			throw e;

		}
		return list;
	}

	/**
	 * 특가 상품 상세 조회 한다.
	 * 
	 * @param String
	 *            couponCode
	 * @response CouponResponseInfo
	 */
	@Override
	public CouponResponseInfo getSpecialProductDetail(String couponCode) {
		log.info("<<<<< CouponContentService >>>>> getSpecialProductDetail...");
		CouponResponseInfo info = null;
		try {
			info = this.couponItemService.getSpecialProductDetail(couponCode);
		} catch (CouponException e) {
			log.error(e);
			throw e;

		}
		return info;
	}
}
