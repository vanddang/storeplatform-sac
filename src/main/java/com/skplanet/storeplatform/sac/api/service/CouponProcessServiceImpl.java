package com.skplanet.storeplatform.sac.api.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.shopping.vo.CouponReq;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponRes;
import com.skplanet.storeplatform.external.client.shopping.vo.DpCouponInfo;
import com.skplanet.storeplatform.external.client.shopping.vo.DpItemInfo;
import com.skplanet.storeplatform.sac.api.conts.CouponConstants;
import com.skplanet.storeplatform.sac.api.except.CouponException;
import com.skplanet.storeplatform.sac.api.inf.IcmsJobPrint;
import com.skplanet.storeplatform.sac.api.util.CSVReader;
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
import com.skplanet.storeplatform.sac.client.internal.member.seller.sci.SellerSearchSCI;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacRes;

/**
 * <pre>
 * 쿠폰아이템 서비스 인터페이스 imple.
 * </pre>
 * 
 * Created on : 2014-01-03 Created by : 김형식, SK 플래닛. Last Updated on : 2014-01-03 Last Updated by : 김형식, SK 플래닛
 */
@Service
public class CouponProcessServiceImpl implements CouponProcessService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private String errorCode = "";
	private String message = "";
	private final String mbrNo = "";
	private final String compNm = "";
	@Autowired
	private CouponItemService couponItemService;

	@Autowired
	private BrandCatalogService brandCatalogService;

	@Autowired
	private SellerSearchSCI sellerSearchSCI;

	@Override
	public boolean insertCouponInfo(CouponReq couponReq) {
		boolean result = true;
		// 호출
		this.log.info("■■■■■ processForCouponCSP ■■■■■");
		// 상품 추가/수정 작업을 호출한다.
		DpCouponInfo couponInfo = null; // 쿠폰 정보
		couponInfo = new DpCouponInfo(); // 쿠폰 정보
		couponInfo = couponReq.getDpCouponInfo();

		List<TbDpProdInfo> tblDpProdList = new ArrayList<TbDpProdInfo>();
		List<TbDpShpgProdInfo> tbDpShpgProdList = new ArrayList<TbDpShpgProdInfo>();
		List<TbDpProdDescInfo> tbDpProdDescList = new ArrayList<TbDpProdDescInfo>();
		List<TbDpProdRshpInfo> tbDpProdRshpList = new ArrayList<TbDpProdRshpInfo>();
		List<TbDpProdCatalogMapgInfo> tbDpProdCatalogMapgList = new ArrayList<TbDpProdCatalogMapgInfo>();
		List<TbDpProdOpt> tbDpProdOptList = new ArrayList<TbDpProdOpt>();
		List<TbDpTenantProdInfo> tbDpTenantProdList = new ArrayList<TbDpTenantProdInfo>();
		List<TbDpTenantProdPriceInfo> tbDpTenantProdPriceList = new ArrayList<TbDpTenantProdPriceInfo>();
		List<DpCatalogTagInfo> tbDpProdTagList = new ArrayList<DpCatalogTagInfo>();
		List<TbDpSprtDeviceInfo> tbDpSprtDeviceList = new ArrayList<TbDpSprtDeviceInfo>();

		if (couponReq != null) {
			List<DpItemInfo> itemInfoList = null; // 아이템 정보 List;
			itemInfoList = new ArrayList<DpItemInfo>(); // 아이템 정보 List;
			itemInfoList = couponReq.getDpItemInfo();
			// 1. Validation Check

			// COUPON 기본정보 등록상태확인
			this.log.info("■■■■■ validateContentInfo 시작 ■■■■■");
			if (!this.validateCouponInfo(couponInfo)) {
				throw new CouponException(this.errorCode, this.message, null);
			}
			this.log.info("■■■■■ validateContentInfo 패스 ■■■■■");

			// 쿠폰 , 아이템 유효성 검증
			if (!this.validateCouponItemCount(couponInfo, itemInfoList, couponReq.getCudType())) { // 쿠폰 , 아이템 유효성 확인 해야
				throw new CouponException(this.errorCode, this.message, null);
			}

			// TB_DP_PROD 값 셋팅
			// log.info("■■■■■ setTbDpProdInfoValue 시작 ■■■■■");
			if (!this.setTbDpProdInfoValue(couponInfo, itemInfoList, tblDpProdList, couponReq.getCudType())) {
				throw new CouponException(this.errorCode, this.message, null);
			}

			// TB_DP_SHPG_PROD 값 셋팅
			// log.info("■■■■■ setTbDpShpgProdInfoValue 시작 ■■■■■");
			if (!this.setTbDpShpgProdListValue(couponInfo, itemInfoList, tbDpShpgProdList, couponReq.getCudType())) {
				throw new CouponException(this.errorCode, this.message, null);
			}

			// TB_DP_PROD_DESC 값 셋팅
			// log.info("■■■■■ setTbDpProdDescInfoValue 시작 ■■■■■");
			if (!this.setTbDpProdDescListValue(couponInfo, itemInfoList, tbDpProdDescList, couponReq.getCudType())) {
				throw new CouponException(this.errorCode, this.message, null);
			}

			// TB_DP_PROD_RSHP 값 셋팅
			// log.info("■■■■■ setTbDpProdRshpValue 시작 ■■■■■");
			if (!this.setTbDpProdRshpValue(couponInfo, itemInfoList, tbDpProdRshpList, couponReq.getCudType())) {
				throw new CouponException(this.errorCode, this.message, null);
			}

			// TB_DP_TENANT_PROD 값 셋팅
			// log.info("■■■■■ setTbDpTenantProdListValue 시작 ■■■■■");
			if (!this.setTbDpTenantProdListValue(couponInfo, itemInfoList, tbDpTenantProdList, couponReq.getCudType())) {
				throw new CouponException(this.errorCode, this.message, null);
			}

			// TB_DP_TENANT_PROD_PRICE 값 셋팅
			// log.info("■■■■■ setTbDpTenantProdPriceListValue 시작 ■■■■■");
			if (!this.setTbDpTenantProdPriceListValue(couponInfo, itemInfoList, tbDpTenantProdPriceList,
					couponReq.getCudType())) {
				throw new CouponException(this.errorCode, this.message, null);
			}

			// TB_DP_PROD_OPT 값 셋팅
			// log.info("■■■■■ setTbDpProdRshpValue 시작 ■■■■■");
			if (!this.setTbDpProdOptValue(couponInfo, itemInfoList, tbDpProdOptList, couponReq.getCudType())) {
				throw new CouponException(this.errorCode, this.message, null);
			}

			// TB_DP_PROD_CATALOG_MAPG 값 셋팅
			// log.info("■■■■■ setTbDpProdCatalogMapgInfoValue 시작 ■■■■■");
			if (!this.setTbDpProdCatalogMapgInfoValue(couponInfo, itemInfoList, tbDpProdCatalogMapgList,
					couponReq.getCudType())) {
				throw new CouponException(this.errorCode, this.message, null);
			}

			// TB_DP_PROD_TAG 값 셋팅
			// log.info("■■■■■ setTbDpTenantProdPriceListValue 시작 ■■■■■");
			if (!this.setTbDpProdTagListValue(couponInfo, tbDpProdTagList)) {
				throw new CouponException(this.errorCode, this.message, null);
			}

			// TB_DP_SPRT_DEVICE 값 셋팅
			// log.info("■■■■■ setTbDpTenantProdPriceListValue 시작 ■■■■■");
			if (!this.setTbDpSprtDeviceListValue(couponInfo, itemInfoList, tbDpSprtDeviceList, couponReq.getCudType())) {
				throw new CouponException(this.errorCode, this.message, null);
			}

			this.log.info("■■■■■ setTbDpProdInfoValue 완료 ■■■■■");

		} else {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "couponReq is NULL!!", null);
		}
		this.log.info("◆◆◆ to TimeString... 전체 처리 완료:  ◆◆◆");
		return result;
	} // End processForCouponCSP

	/**
	 * 쿠폰 컨텐트 Validation Check.
	 * 
	 * @param couponInfo
	 *            couponInfo
	 * @return boolean result @couponInfo
	 * 
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
	} // End validateContentInfo

	/**
	 * TbDpProd Info value 셋팅.
	 * 
	 * @param couponInfo
	 *            couponInfo
	 * 
	 * @param itemInfoList
	 *            itemInfoList
	 * @param tblDpProdList
	 *            tblDpProdList
	 * @param cudType
	 *            cudType
	 * @return boolean result
	 */
	private boolean setTbDpProdInfoValue(DpCouponInfo couponInfo, List<DpItemInfo> itemInfoList,
			List<TbDpProdInfo> tblDpProdList, String cudType) {
		TbDpProdInfo dp = new TbDpProdInfo();
		try {

			// ////////////////// Coupon 정보 S////////////////////////////
			dp.setProdId(couponInfo.getProdId());
			dp.setProdChrgYn("N");
			dp.setSellerMbrNo(this.mbrNo);
			dp.setSvcGrpCd(CouponConstants.CUPON_SVC_GRP_CD); // "DP000206"; // MALL 구분 코드(쇼핑쿠폰)
			dp.setSvcTypeCd(CouponConstants.CHNL_TP_CD_CUPON); // "DP001117"; // 채널 타입 코드(쇼핑쿠폰)
			dp.setProdGrdCd(CouponConstants.AGE_RESTRICTIONS_DEFAULT_COMMON_CODE); // PD004401 전체 이용가
			dp.setCid(couponInfo.getCouponCode());
			dp.setFeeCaseCd(CouponConstants.PAY_CODE_TIME); // "PD00292"; // 과금유형(기간정액제)

			if (Integer.parseInt(couponInfo.getValidUntil()) > 0) { // 유효일수 값 비교
				dp.setUsePeriodUnitCd(CouponConstants.USE_PERIOD_UNIT_DAY); // PD00312 기간제(일)
				dp.setUsePeriod(Integer.parseInt(couponInfo.getValidUntil())); // 유효일수로 셋팅
			} else {
				dp.setUsePeriodUnitCd(CouponConstants.USE_PERIOD_UNIT_SELECT); // PD00319 기간선택 USE_TERM_UNIT
				dp.setUsePeriod(Long.parseLong(couponInfo.getValidEDate())); // 유효종료일시로 셋팅
			}
			dp.setDrmYn("Y");
			dp.setMetaClsfCd(CouponConstants.CATEGORY_ID_CUPON_SERIES_CONTENT); // "CT28"; // 쇼핑쿠폰 부모 카테고리ID
			dp.setContentsTypeCd(CouponConstants.COUPON_CONTENT_TP_CHA); // PD002501 채널타입
			dp.setTopMenuId(CouponConstants.TOP_MENU_ID_CUPON_CONTENT); // 상위 메뉴 아이디
			dp.setRegId(couponInfo.getBpId());
			dp.setUpdId(couponInfo.getBpId());
			dp.setCudType(cudType);
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
				dp.setSellerMbrNo(this.mbrNo);
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
				dp.setMetaClsfCd(CouponConstants.CATEGORY_ID_CUPON_SERIES_CONTENT); // "CT28"; // 쇼핑쿠폰 부모 카테고리ID
				dp.setContentsTypeCd(CouponConstants.COUPON_CONTENT_TP_EPI); // PD002501 에피소드타입
				dp.setTopMenuId(CouponConstants.TOP_MENU_ID_CUPON_CONTENT); // 상위 메뉴 아이디
				dp.setRegId(couponInfo.getBpId());
				dp.setUpdId(couponInfo.getBpId());
				dp.setCudType(itemInfo.getCudType());
				tblDpProdList.add(dp);
				IcmsJobPrint.printTblDpProd(dp, "TBL_DP_PROD - ITEM::" + i);
			}
			// ////////////////// Item 정보 E////////////////////////////

			// 저장
			this.couponItemService.insertTbDpProdInfo(tblDpProdList);

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
	} // End setTbDpProdInfoValue

	/**
	 * TbDpShpgProd Info value 셋팅.
	 * 
	 * @param couponInfo
	 *            couponInfo
	 * @param itemInfoList
	 *            itemInfoList
	 * @param tbDpShpgProdList
	 *            tbDpShpgProdList
	 * @param cudType
	 *            cudType
	 * @return boolean result
	 */

	private boolean setTbDpShpgProdListValue(DpCouponInfo couponInfo, List<DpItemInfo> itemInfoList,
			List<TbDpShpgProdInfo> tbDpShpgProdList, String cudType) {
		TbDpShpgProdInfo dsp = new TbDpShpgProdInfo();
		try {

			// ////////////////// Coupon 정보 S////////////////////////////
			int itemCnt = 0;
			for (DpItemInfo itemInfo : itemInfoList) {
				// 판매중인 아이템 수만 count
				if (itemInfo.getItemStatus().equals("3")) { // 2:판매대기 3:판매중 4:판매중지 5:판매금지
					itemCnt++;
				}
			}

			dsp.setProdId(couponInfo.getProdId());

			dsp.setEpsdCnt(itemCnt); //
			dsp.setChnlCompNm(this.compNm);
			if (couponInfo.getCoupnStatus().equals("3")) { // 2:판매대기 3:판매중 4:판매중지 5:판매금지
				dsp.setSaleYn("Y");
			} else {
				dsp.setSaleYn("N");
			}

			dsp.setContentsOrdrCd(CouponConstants.UPDATE_TYPE_CD_DESC); // D
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
			dsp.setCudType(cudType);
			tbDpShpgProdList.add(dsp);
			IcmsJobPrint.printTbDpShpgProd(dsp, "TB_DP_SHPG_PROD - COUPON");
			// ////////////////// Coupon 정보 E////////////////////////////

			// ////////////////// Item 정보 S////////////////////////////

			for (int i = 0; i < itemInfoList.size(); i++) {
				int itemOptionCnt = 0;
				DpItemInfo itemInfo = itemInfoList.get(i);
				dsp = new TbDpShpgProdInfo();
				dsp.setProdId(itemInfo.getProdId());
				if (StringUtils.isNotBlank(itemInfo.getItemValue1())) {
					itemOptionCnt++;
				}
				if (StringUtils.isNotBlank(itemInfo.getItemValue2())) {
					itemOptionCnt++;
				}
				dsp.setEpsdCnt(itemOptionCnt); // 아이템에 대한 판매중인 옵션 수만 count
				dsp.setChnlCompNm(this.compNm);
				if (itemInfo.getItemStatus().equals("3")) { // 2:판매대기 3:판매중 4:판매중지 5:판매금지
					dsp.setSaleYn("Y");
				} else {
					dsp.setSaleYn("N");
				}

				dsp.setContentsOrdrCd(CouponConstants.UPDATE_TYPE_CD_DESC); // D
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
				dsp.setCudType(itemInfo.getCudType());
				tbDpShpgProdList.add(dsp);
				IcmsJobPrint.printTbDpShpgProd(dsp, "TB_DP_SHPG_PROD - ITEM::" + i);
			}

			// 저장
			this.couponItemService.insertTbDpShpgProdInfo(tbDpShpgProdList);
		} catch (CouponException e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_SHPG_PROD VO 셋팅 실패", null);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_SHPG_PROD VO 셋팅 실패", null);
		}
		return true;
	} // End setTbDpProdInfoValue

	/**
	 * setTbDpProdDesc Info value 셋팅.
	 * 
	 * @param couponInfo
	 *            couponInfo
	 * @param itemInfoList
	 *            itemInfoList
	 * @param tbDpProdDescList
	 *            tbDpProdDescList
	 * @param cudType
	 *            cudType
	 * @return boolean
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
			dpd.setCudType(cudType);
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
				dpd.setCudType(itemInfo.getCudType());
				tbDpProdDescList.add(dpd);
				IcmsJobPrint.printTbDpProdDesc(dpd, "TB_DP_PROD_DESC- ITEM:::" + i);
			}

			// 저장
			this.couponItemService.insertTbDpProdDescInfo(tbDpProdDescList);

		} catch (CouponException e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_PROD_DESC VO 셋팅 실패", null);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_PROD_DESC VO 셋팅 실패", null);
		}
		return true;
	} // End setTbDpProdDesc

	/**
	 * setTbDpProdRshp Info value 셋팅.
	 * 
	 * @param couponInfo
	 *            couponInfo
	 * @param itemInfoList
	 *            itemInfoList
	 * @param tbDpProdRshpList
	 *            tbDpProdRshpList
	 * @param cudType
	 *            cudType
	 * @return boolean
	 */
	private boolean setTbDpProdRshpValue(DpCouponInfo couponInfo, List<DpItemInfo> itemInfoList,
			List<TbDpProdRshpInfo> tbDpProdRshpList, String cudType) {
		TbDpProdRshpInfo dps = null;
		dps = new TbDpProdRshpInfo();
		try {

			// ////////////////// Item 정보 S////////////////////////////
			for (int i = 0; i < itemInfoList.size(); i++) {
				DpItemInfo itemInfo = itemInfoList.get(i);
				dps = new TbDpProdRshpInfo();
				dps.setProdId(couponInfo.getProdId()); // 채널 상품
				dps.setPartProdId(itemInfo.getProdId()); // 에피소드 상품
				dps.setProdRshpCd(CouponConstants.PROD_RSHP_CD); // 채널-에피소드 상품 관계
				dps.setRegId(couponInfo.getBpId());
				dps.setCudType(itemInfo.getCudType());
				tbDpProdRshpList.add(dps);
				IcmsJobPrint.printTbDpProdRshp(dps, "TB_DP_PROD_RSHP- ITEM:::" + i);
			}
			// 저장
			this.couponItemService.insertTbDpProdRshpInfo(tbDpProdRshpList);

		} catch (CouponException e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_PROD_RSHP VO 셋팅 실패", null);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_PROD_RSHP VO 셋팅 실패", null);
		}
		return true;
	} // End setTbDpProdDesc

	/**
	 * setTbDpTenantProdList Info value 셋팅.
	 * 
	 * @param couponInfo
	 *            couponInfo
	 * @param itemInfoList
	 *            itemInfoList
	 * @param tbDpTenantProdList
	 *            tbDpTenantProdList
	 * @param cudType
	 *            cudType
	 * @return boolean
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
			dtpd.setCudType(cudType);
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
				dtpd.setCudType(itemInfo.getCudType());
				tbDpTenantProdList.add(dtpd);
				IcmsJobPrint.printTbTenantDpProd(dtpd, "TB_DP_TENANT_PROD - ITEM:::" + i);
			}
			// 저장
			this.couponItemService.insertTbDpTenantProdInfo(tbDpTenantProdList);
		} catch (CouponException e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_TENANT_PROD VO 셋팅 실패", null);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_TENANT_PROD VO 셋팅 실패", null);
		}
		return true;
	} // End setTbDpTenantProdList

	/**
	 * setTbDpTenantProdPrice Info value 셋팅.
	 * 
	 * @param couponInfo
	 *            couponInfo
	 * @param itemInfoList
	 *            itemInfoList
	 * @param tbDpTenantProdPriceList
	 *            tbDpTenantProdPriceList
	 * @param cudType
	 *            cudType
	 * @return boolean
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
			dtpp.setProdAmt(0); // 상품_금액
			dtpp.setChnlUnlmtAmt(0); // 채널_무제한_금액
			dtpp.setChnlPeriodAmt(0); // 채널_기간_금액
			dtpp.setProdNetAmt(0); // 상품_정찰_금액
			dtpp.setDcRate(Long.parseLong(couponInfo.getAccountingRate())); // 할인_율
			dtpp.setDcAmt(0); // 할인_금액
			dtpp.setTaxClsf(""); // 세금_구분
			dtpp.setRegId(couponInfo.getBpId());
			dtpp.setUpdId(couponInfo.getBpId());
			dtpp.setCudType(cudType);
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
				dtpp.setCudType(itemInfo.getCudType());
				tbDpTenantProdPriceList.add(dtpp);
				IcmsJobPrint.printTbTenantDpProdPrice(dtpp, "TB_DP_TENANT_PROD_PRICE- ITEM:::" + i);
			}
			// 저장
			this.couponItemService.insertTbDpTenantProdPriceInfo(tbDpTenantProdPriceList);
		} catch (CouponException e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_TENANT_PROD_PRICE VO 셋팅 실패",
					null);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_TENANT_PROD_PRICE VO 셋팅 실패",
					null);
		}
		return true;
	} // End setTbDpTenantProdPrice

	/**
	 * setTbDpProdCatalogMapgInfoValue Info value 셋팅.
	 * 
	 * @param couponInfo
	 *            couponInfo
	 * @param itemInfoList
	 *            itemInfoList
	 * @param tbDpProdCatalogMapgList
	 *            tbDpProdCatalogMapgList
	 * @param cudType
	 *            cudType
	 * @return boolean
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
			dpcm.setCudType(cudType);
			tbDpProdCatalogMapgList.add(dpcm);
			IcmsJobPrint.tbDpProdCatalogMapg(dpcm, "TB_DP_PROD_CATALOG_MAPG- COUPON");
			// ////////////////// Coupon 정보 E////////////////////////////

			this.couponItemService.insertTbDpProdCatalogMapgInfo(tbDpProdCatalogMapgList, couponInfo.getProdId());
		} catch (CouponException e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_PROD_CATALOG_MAPG VO 셋팅 실패",
					null);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_PROD_CATALOG_MAPG VO 셋팅 실패",
					null);
		}
		return true;
	} // End setTbDpProdCatalogMapgInfoValue

	/**
	 * setTbDpProdOptValue Info value 셋팅.
	 * 
	 * @param couponInfo
	 *            couponInfo
	 * @param itemInfoList
	 *            itemInfoList
	 * @param tbDpProdOptList
	 *            tbDpProdOptList
	 * @param cudType
	 *            cudType
	 * @return boolean
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
			this.couponItemService.insertTbDpProdOptInfo(tbDpProdOptList);

		} catch (CouponException e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_PROD_OPT VO 셋팅 실패", null);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_PROD_OPT VO 셋팅 실패", null);
		}
		return true;
	} // End setTbDpProdOptValue

	/**
	 * setTbDpSprtDeviceListValue Info value 셋팅.
	 * 
	 * @param couponInfo
	 *            couponInfo
	 * @param itemInfoList
	 *            itemInfoList
	 * @param tbDpSprtDeviceList
	 *            tbDpSprtDeviceList
	 * @param cudType
	 *            cudType
	 * @return boolean
	 */
	private boolean setTbDpSprtDeviceListValue(DpCouponInfo couponInfo, List<DpItemInfo> itemInfoList,
			List<TbDpSprtDeviceInfo> tbDpSprtDeviceList, String cudType) {
		TbDpSprtDeviceInfo tdsd = new TbDpSprtDeviceInfo();
		try {

			// ////////////////// Item 정보 S////////////////////////////
			for (int i = 0; i < itemInfoList.size(); i++) {
				DpItemInfo itemInfo = itemInfoList.get(i);
				tdsd = new TbDpSprtDeviceInfo();
				tdsd.setProdId(itemInfo.getProdId()); // 에피소드 상품
				tdsd.setDeviceModelCd(CouponConstants.ANDROID_STANDARD2);
				tdsd.setCudType(itemInfo.getCudType());
				tdsd.setRegId(couponInfo.getBpId());
				tdsd.setUpdId(couponInfo.getBpId());
				tbDpSprtDeviceList.add(tdsd);
			}
			// 저장
			this.couponItemService.insertTbDpSprtDeviceInfo(tbDpSprtDeviceList);

		} catch (CouponException e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_SPRT_DEVICE VO 셋팅 실패", null);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_SPRT_DEVICE VO 셋팅 실패", null);
		}
		return true;
	} // End setTbDpProdDesc

	/**
	 * setTbDpProdTagListValue Info value 셋팅.
	 * 
	 * @param couponInfo
	 *            couponInfo
	 * 
	 * @param tbDpProdTagList
	 *            tbDpProdTagList
	 * @return boolean
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
	} // End setTbDpTenantProdPrice

	/**
	 * 해당 업체가 존재 하는지 체크 한다.
	 * 
	 * @param couponInfo
	 *            couponInfo
	 * @return boolean
	 */
	private boolean validateBusinessPartner(DpCouponInfo couponInfo) {
		this.log.info("■■■■■ validateBusinessPartner ■■■■■");
		DetailInformationSacReq req = new DetailInformationSacReq();
		DetailInformationSacRes res = new DetailInformationSacRes();
		try {
			req.setSellerKey("");
			req.setSellerId(couponInfo.getBpId());
			res = this.sellerSearchSCI.detailInformation(req);
			if (res != null) {
				// if (StringUtils.isBlank(res.getSellerMbr().getSellerCompany())) {
				// throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "상호명이 없습니다.",
				// couponInfo.getBpId());
				// }

				// this.compNm = res.getSellerMbr().getSellerCompany();
				// this.mbrNo = res.getSellerMbr().getSellerKey();
			} else {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "상호명이 없습니다.",
						couponInfo.getBpId());
			}
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "상호명이 없습니다.", couponInfo.getBpId());

		}
		// this.compNm = "GTSOFT";
		// this.mbrNo = "IF1023541432620111207152255";
		return true;
	} // End validateBusinessPartner

	/**
	 * 해당 카탈로그가 존재 하는지 체크 한다.
	 * 
	 * @param couponInfo
	 *            couponInfo
	 * @return boolean
	 */
	private boolean validateCatalog(DpCouponInfo couponInfo) {
		String catalogID = this.brandCatalogService.getCreateCatalogId(couponInfo.getStoreCatalogCode());
		if (StringUtils.isNotBlank(catalogID)) {
			this.log.info("validateCatalog ■■■ catalogID : " + catalogID + "■■■");
			couponInfo.setStoreCatalogCode(catalogID);
		} else {
			return false;
		}
		return true;
	} // End validateCatalog

	/**
	 * 쿠폰 , 아이템 유효성 검증.
	 * 
	 * @param couponInfo
	 *            couponInfo
	 * @param itemInfoList
	 *            itemInfoList
	 * @param cudType
	 *            cudType
	 * @return boolean
	 */
	private boolean validateCouponItemCount(DpCouponInfo couponInfo, List<DpItemInfo> itemInfoList, String cudType) {
		if ("C".equals(cudType)) {
			if (this.couponItemService.getCouponItemCountCudType(couponInfo.getCouponCode()) > 0) {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DUP_COUPONID,
						"해당 Coupon_Id로 등록한 coupon가 있습니다.", couponInfo.getCouponCode());
			}
		}
		for (int i = 0; i < itemInfoList.size(); i++) {
			if ("C".equals(itemInfoList.get(i).getCudType())) {
				if (this.couponItemService.getCouponItemCountCudType(itemInfoList.get(i).getItemCode()) > 0) {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DUP_ITEMID,
							"해당 Item_Id로 등록한 item이 있습니다.", itemInfoList.get(i).getItemCode());
				}
			}
		}
		return true;
	} // End validateCatalog

	/**
	 * 쇼핑쿠폰 상품 상태 변경.
	 * 
	 * @param couponReq
	 *            couponReq
	 * @return boolean
	 */
	@Override
	public boolean updateForCouponStatus(CouponReq couponReq) {
		// 호출
		this.log.info("■■■■■ processForCouponStatus ■■■■■");
		if (couponReq != null && StringUtils.isNotBlank(couponReq.getCouponCode())
				&& StringUtils.isNotBlank(couponReq.getCoupnStatus())) {

			String couponCode = couponReq.getCouponCode();
			String coupnStatus = couponReq.getCoupnStatus();
			String itemCode = couponReq.getItemCode();
			String upType = couponReq.getUpType(); // 0=상품상태변경, 1=단품상태변경, 2=상품+단품상태 모두 변경

			this.log.debug("===========================\n" + "couponCode : " + couponCode + "/n" + "itemCode : "
					+ itemCode + "/n" + "upType : " + upType + "/n" + "coupnStatus : " + coupnStatus + "/n"
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

			if (StringUtils.isBlank(newCouponCode)) {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_NOT_COUPONID, null, null);
			}
			if ((StringUtils.equalsIgnoreCase(upType, "1"))) { // 1=단품상태변경 item 값은 필수
				itemCode = this.couponItemService.getGenerateId(itemCode);
				if (StringUtils.isBlank(itemCode)) {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_NOT_ITEMID, null, null);
				}
			}
			// 쿠폰상태 코드 -> 전시 코드로 변환
			dpStatusCode = this.getDPStatusCode(coupnStatus);

			// 2. DB Update
			try {
				// TB_DP_TENANT_PROD 상태 처리
				this.couponItemService.updateCouponStatus(newCouponCode, dpStatusCode, upType, itemCode);

			} catch (CouponException e) {
				throw new CouponException(e.getErrCode(), e.getError_data().getErrorMsg(), null);
			} catch (Exception e) {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, e.getMessage(), null);
			} finally {
				this.log.info("■■■■■ DB Transaction END ■■■■■");
			}

		} else {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "couponReq is NULL!!", null);
		}
		return true;
	} // End processForCouponStatus

	/**
	 * 쿠폰 상태코드별 전시상태코드를 반환한다.
	 * 
	 * @param coupnStatus
	 *            coupnStatus
	 * @return String
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
	} // End getDPStatusCode

	/**
	 * 특가 상품 목록 조회 한다.
	 * 
	 * @param couponCodes
	 *            couponCodes
	 * @return List<CouponRes>
	 */
	@Override
	public List<CouponRes> getSpecialProductList(String[] couponCodes) {
		this.log.info("<<<<< CouponContentService >>>>> getSpecialProductList...");
		List<CouponRes> list = null;
		try {
			list = this.couponItemService.getSpecialProductList(couponCodes);

		} catch (CouponException e) {
			throw e;

		}
		return list;
	}

	/**
	 * 특가 상품 상세 조회 한다.
	 * 
	 * @param couponCode
	 *            couponCode
	 * @return CouponRes
	 */
	@Override
	public CouponRes getSpecialProductDetail(String couponCode) {
		this.log.info("<<<<< CouponContentService >>>>> getSpecialProductDetail...");
		CouponRes info = null;
		try {
			info = this.couponItemService.getSpecialProductDetail(couponCode);
		} catch (CouponException e) {
			throw e;

		}
		return info;
	}

	/**
	 * <pre>
	 * 쿠폰(아이템) Batch 처리 호출 메소드 (수동 호출용).
	 * </pre>
	 * 
	 * @param nowTime
	 *            nowTime
	 * @return
	 */
	@Override
	public void couponStateUpdateStart(String nowTime) {
		this.log.debug("<<<<< BatchProductSaleStatService.couponStateUpdateStart CREATE >>>>>");
		this.log.info("nowTime:::" + nowTime);

		String lineSeparator = System.getProperty("line.separator");
		StringBuffer sb = new StringBuffer();
		boolean isOk = false;
		boolean flag = true;

		ArrayList<CouponReq> couponList = new ArrayList<CouponReq>();
		ArrayList<String> resultList = new ArrayList<String>();

		CSVReader reader = null;
		// 파일명을 위한 날짜
		Calendar now = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		StringBuffer strFirstValueTot = new StringBuffer();

		try {

			this.log.info(" 쿠폰(아이템) 상태변경 배치 작업 시작 ");
			// String url = prop.getString("omp.bp.shopping.coupon.batch.url") + nowTime; // 호출 URL
			// String url = "http://api.coupon.itopping.co.kr/Bp/getCouponStatusList?scrRange=date&scrDate=" + nowTime;
			String url = "http://api.coupon.itopping.co.kr/Bp/getCouponStatusList?scrRange=date&scrDate=20131015";
			// 운영 URL
			// String url ="http://api.coupon.tstore.co.kr/Bp/getCouponStatusList?scrRange=date&scrDate="+nowTime;
			this.log.info("호출 URL :::::: " + url);

			this.log.debug("■■■■■ 파일디렉토리 설정 S  ■■■■■");
			String downDir = this.makeFileDirectory();
			this.log.debug("■■■■■ 파일디렉토리 설정 E  ■■■■■");

			this.log.debug("■■■■■ 파일명 설정 S  ■■■■■");
			String fileName = "sync_" + sf.format(now.getTime());
			fileName = "sync_20140120131746";
			String fileNameCsv = fileName.replaceAll("-", "") + ".csv";
			String fileNameLog = fileName.replaceAll("-", "") + ".log";
			this.log.debug("■■■■■ 파일명 설정 E  ■■■■■");

			// 다운로드 호출
			this.log.debug("■■■■■ 파일 다운로드 시작 ■■■■■");
			// this.fileUrlReadAndDownload(url, fileNameCsv, downDir);
			this.log.debug("■■■■■ 파일 다운로드 끝 ■■■■■");

			this.log.info("■■■■■ read CSV 시작 ■■■■■");
			reader = new CSVReader(new InputStreamReader(new FileInputStream(downDir + File.separator + fileNameCsv),
					"UTF-8"), '\t');
			String[] nextLine = null;
			String nonContentId = "";
			String nonItemId = "";
			String itemCode = "";
			String newItemId = "";

			while ((nextLine = reader.readNext()) != null) {
				CouponReq info = new CouponReq();

				if (nextLine[0].toString().equals("1")) {
					info.setUpType("0"); // 0=상품상태변경, 1=단품상태변경
				} else {
					info.setUpType("1"); // 0=상품상태변경, 1=단품상태변경
				}

				String newCouponId = this.couponItemService.getGenerateId(nextLine[1].toString());
				if (StringUtils.isBlank(newCouponId)) { // content_id 값이 없으면 log에 남기기
					nonContentId = "nonContentId::" + sf.format(now.getTime()) + ">>>>쿠폰코드::::"
							+ nextLine[1].toString();
					strFirstValueTot.append(nonContentId + lineSeparator);
					flag = false;
				}
				if ((StringUtils.equalsIgnoreCase(info.getUpType(), "1"))) { // 1=단품상태변경 item 값은 필수
					info.setItemCode(nextLine[2].toString()); // 아이템 코드
					newItemId = this.couponItemService.getGenerateId(nextLine[2].toString());
					if (StringUtils.isBlank(newItemId)) {
						nonItemId = "nonItemId::" + sf.format(now.getTime()) + ">>>>아이템코드::::" + nextLine[2].toString();
						strFirstValueTot.append(nonItemId + lineSeparator);
						flag = false;
					}
				}
				if (flag) {
					info.setCouponCode(nextLine[1].toString()); // 쿠폰 코드
					info.setCoupnStatus(this.getDPStatusCode(nextLine[3].toString())); // 2:판매대기 , 3:판매중 , 4:판매중지
																					   // ,5:판매금지
					info.setNewCouponId(newCouponId);
					info.setItemCode(nextLine[2].toString()); // 아이템 코드
					info.setNewItemId(newItemId);
					couponList.add(info);
				}
			}

			reader.close();
			this.log.info("■■■■■ read CSV 끝 ■■■■■");
			this.log.info("■■■■■ couponList.size() ■■■■■" + couponList.size());

			resultList = this.couponItemService.updateBatchForCouponStatus(couponList);

			File logFile = new File(downDir, fileNameLog);

			for (String resultCd : resultList) {
				strFirstValueTot.append(resultCd + lineSeparator);
			}
			isOk = this.writeStringBufferMy(logFile, strFirstValueTot); // log 파일 쌓기

			if (!isOk) {
				this.log.info(" (쿠폰(아이템) 상태 판매대기 -> 판매중 상태 전환 오류 ( " + nowTime + ")");
				sb.append(" (쿠폰(아이템) 상태 판매대기 -> 판매중 상태 전환 오류 ( " + nowTime + ")");
			} else {
				sb.append(" (쿠폰(아이템) 상태 변경 성공");
			}

			this.log.info(" 쿠폰(아이템) 상태변경 배치 작업 종료 ");
		} catch (Exception e) {
			this.log.error("couponStateUpdateStart 생성 중 예외 발생 - (쿠폰(아이템) 상태변경 Batch 처리에러 )" + e.getMessage());

		}

	}

	/**
	 * <pre>
	 *  File Directory Create.
	 * </pre>
	 * 
	 * 
	 * @return String
	 */
	private String makeFileDirectory() {

		String fullPath = "";

		try {
			String filePath = "/log/omp/coupon/";

			File dir = new File(filePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			fullPath = dir.getPath();
		} catch (Exception e) {
			this.log.info("ERROR make File Directory : " + e);
		}

		return fullPath;
	}

	/**
	 * fileAddress에서 파일을 읽어, 다운로드 디렉토리에 다운로드.
	 * 
	 * @param fileAddress
	 *            fileAddress
	 * @param fileName
	 *            fileName
	 * @param downloadDir
	 *            downloadDir
	 */
	private void fileUrlReadAndDownload(String fileAddress, String fileName, String downloadDir) {

		OutputStream outStream = null;
		URLConnection uCon = null;

		InputStream is = null;
		try {

			this.log.debug("□□□□□□□□□ Download Start □□□□□□□□□");

			URL url;
			byte[] buf;
			int byteRead;
			int byteWritten = 0;
			url = new URL(fileAddress);
			outStream = new BufferedOutputStream(new FileOutputStream(downloadDir + File.separator + fileName));
			this.log.debug("□□□□□□□□□ Download Start □□□□□□□□□" + downloadDir + File.separator + fileName);

			uCon = url.openConnection();
			is = uCon.getInputStream();
			int bufferSize = 1024 * 8;
			buf = new byte[bufferSize];
			while ((byteRead = is.read(buf)) != -1) {
				outStream.write(buf, 0, byteRead);
				byteWritten += byteRead;
			}
			this.log.info("File name : " + fileName);
			this.log.debug("□□□□□□□□□ Download End □□□□□□□□□");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * writeStringBufferMy log 파일 만들기.
	 * 
	 * @param file
	 *            file
	 * @param sb
	 *            sb
	 * @return boolean
	 * @throws Exception
	 *             Exception
	 */

	private boolean writeStringBufferMy(File file, StringBuffer sb) throws Exception {
		boolean result = true;
		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
		int length = sb.length();
		int bt = 1000;
		for (int i = 0; i < length;) {
			int end = i + bt;
			if (end > length) {
				end = length;
			}
			osw.write(sb.substring(i, end));
			i = end;
		}
		osw.close();
		fos.close();
		return result;
	}

}
