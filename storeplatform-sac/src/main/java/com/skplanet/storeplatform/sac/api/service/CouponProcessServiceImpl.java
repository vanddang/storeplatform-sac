package com.skplanet.storeplatform.sac.api.service;

import static com.skplanet.storeplatform.sac.display.common.ProductType.Shopping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.shopping.vo.CouponReq;
import com.skplanet.storeplatform.external.client.shopping.vo.CouponRes;
import com.skplanet.storeplatform.external.client.shopping.vo.DpCouponInfo;
import com.skplanet.storeplatform.external.client.shopping.vo.DpItemInfo;
import com.skplanet.storeplatform.iprm.mq.client.common.vo.NotificationIprm;
import com.skplanet.storeplatform.iprm.mq.client.product.vo.Product;
import com.skplanet.storeplatform.iprm.mq.client.product.vo.ProductTenantPrice;
import com.skplanet.storeplatform.iprm.mq.client.product.vo.ProductTenantRate;
import com.skplanet.storeplatform.sac.api.conts.CouponConstants;
import com.skplanet.storeplatform.sac.api.except.CouponException;
import com.skplanet.storeplatform.sac.api.inf.IcmsJobPrint;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
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
import com.skplanet.storeplatform.sac.client.internal.member.seller.sci.SellerSearchSCI;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac;
import com.skplanet.storeplatform.sac.display.cache.service.CacheEvictHelperComponent;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.mq.client.rater.vo.RaterMessage;
import com.skplanet.storeplatform.sac.mq.client.search.constant.SearchConstant;
import com.skplanet.storeplatform.sac.mq.client.search.util.SearchQueueUtils;
import com.skplanet.storeplatform.sac.mq.client.search.vo.SearchInterfaceQueue;
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
	@Autowired
	private CouponItemService couponItemService;

	@Autowired
	private BrandCatalogService brandCatalogService;

	@Autowired
	private SellerSearchSCI sellerSearchSCI;

	@Autowired
	private CacheEvictHelperComponent cacheEvictHelperComponent;

	@Autowired
	@Resource(name = "shoppingIprmAmqpTemplate")
	private AmqpTemplate shoppingIprmAmqpTemplate; // MQ 연동.

	@Autowired
	@Resource(name = "sacSearchAmqpTemplate")
	private AmqpTemplate sacSearchAmqpTemplate; // 검색 서버 MQ 연동.
	
	@Autowired
	@Resource(name = "raterAmqpTemplate")
	private AmqpTemplate raterAmqpTemplate; // skt receipt.	

	@Override
	public boolean insertCouponInfo(CouponReq couponReq) {
		boolean result = true;
		// 호출
		this.log.info("■■■■■ processForCouponCSP ■■■■■");
		// 상품 추가/수정 작업을 호출한다.
		DpCouponInfo couponInfo = null; // 쿠폰 정보
		couponInfo = new DpCouponInfo(); // 쿠폰 정보
		if (couponReq != null) {
			couponInfo = couponReq.getDpCouponInfo();
		}

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
		List<SpRegistProd> spRegistProdList = new ArrayList<SpRegistProd>();
		if (couponReq != null) {
			List<DpItemInfo> itemInfoList = null; // 아이템 정보 List;
			itemInfoList = couponReq.getDpItemInfo();
			// 1. Validation Check

			// COUPON 기본정보 등록상태확인
			this.log.info("■■■■■ validateContentInfo 시작 ■■■■■");
			if (!this.validateCouponInfo(couponInfo)) {
				throw new CouponException(couponInfo.getErrorCode(), couponInfo.getMessage(), null);
			}
			this.log.info("■■■■■ validateContentInfo 패스 ■■■■■");

			// 쿠폰 , 아이템 유효성 검증
			if (!this.validateCouponItemCount(couponInfo, itemInfoList, couponReq.getCudType())) { // 쿠폰 , 아이템 유효성 확인 해야
				throw new CouponException(couponInfo.getErrorCode(), couponInfo.getMessage(), null);
			}

			// TB_DP_PROD 값 셋팅
			this.log.info("■■■■■ setTbDpProdInfoValue start ■■■■■", DateUtil.getToday("yyyy-MM-dd hh:mm:ss.SSS"));
			if (!this.setTbDpProdInfoValue(couponInfo, itemInfoList, tblDpProdList, couponReq.getCudType())) {
				throw new CouponException(couponInfo.getErrorCode(), couponInfo.getMessage(), null);
			}
			this.log.info("■■■■■ setTbDpProdInfoValue End ■■■■■", DateUtil.getToday("yyyy-MM-dd hh:mm:ss.SSS"));

			// TB_DP_SHPG_PROD 값 셋팅
			// log.info("■■■■■ setTbDpShpgProdInfoValue 시작 ■■■■■");
			if (!this.setTbDpShpgProdListValue(couponInfo, itemInfoList, tbDpShpgProdList, couponReq.getCudType())) {
				throw new CouponException(couponInfo.getErrorCode(), couponInfo.getMessage(), null);
			}

			// TB_DP_PROD_DESC 값 셋팅
			// log.info("■■■■■ setTbDpProdDescInfoValue 시작 ■■■■■");
			if (!this.setTbDpProdDescListValue(couponInfo, itemInfoList, tbDpProdDescList, couponReq.getCudType())) {
				throw new CouponException(couponInfo.getErrorCode(), couponInfo.getMessage(), null);
			}

			// TB_DP_PROD_CATALOG_MAPG 값 셋팅
			// log.info("■■■■■ setTbDpProdCatalogMapgInfoValue 시작 ■■■■■");
			if (!this.setTbDpProdCatalogMapgInfoValue(couponInfo, itemInfoList, tbDpProdCatalogMapgList,
					couponReq.getCudType())) {
				throw new CouponException(couponInfo.getErrorCode(), couponInfo.getMessage(), null);
			}

			// TB_DP_PROD_RSHP 값 셋팅
			// log.info("■■■■■ setTbDpProdRshpValue 시작 ■■■■■");
			if (!this.setTbDpProdRshpValue(couponInfo, itemInfoList, tbDpProdRshpList, couponReq.getCudType())) {
				throw new CouponException(couponInfo.getErrorCode(), couponInfo.getMessage(), null);
			}

			// TB_DP_TENANT_PROD 값 셋팅
			// log.info("■■■■■ setTbDpTenantProdListValue 시작 ■■■■■");
			if (!this.setTbDpTenantProdListValue(couponInfo, itemInfoList, tbDpTenantProdList, couponReq.getCudType())) {
				throw new CouponException(couponInfo.getErrorCode(), couponInfo.getMessage(), null);
			}

			// TB_DP_TENANT_PROD_PRICE 값 셋팅
			// log.info("■■■■■ setTbDpTenantProdPriceListValue 시작 ■■■■■");
			if (!this.setTbDpTenantProdPriceListValue(couponInfo, itemInfoList, tbDpTenantProdPriceList,
					couponReq.getCudType())) {
				throw new CouponException(couponInfo.getErrorCode(), couponInfo.getMessage(), null);
			}

			// TB_DP_PROD_OPT 값 셋팅
			// log.info("■■■■■ setTbDpProdRshpValue 시작 ■■■■■");
			if (!this.setTbDpProdOptValue(couponInfo, itemInfoList, tbDpProdOptList, couponReq.getCudType())) {
				throw new CouponException(couponInfo.getErrorCode(), couponInfo.getMessage(), null);
			}

			// TB_DP_PROD_TAG 값 셋팅
			// log.info("■■■■■ setTbDpTenantProdPriceListValue 시작 ■■■■■");
			if (!this.setTbDpProdTagListValue(couponInfo, tbDpProdTagList)) {
				throw new CouponException(couponInfo.getErrorCode(), couponInfo.getMessage(), null);
			}

			// TB_DP_SPRT_DEVICE 값 셋팅
			// log.info("■■■■■ setTbDpTenantProdPriceListValue 시작 ■■■■■");
			if (!this.setTbDpSprtDeviceListValue(couponInfo, itemInfoList, tbDpSprtDeviceList, couponReq.getCudType())) {
				throw new CouponException(couponInfo.getErrorCode(), couponInfo.getMessage(), null);
			}
			// SP_SETT_REG_PROD 프로시저 호출
			this.log.info("■■■■■ setCallSpSettRegProd 시작 ■■■■■", DateUtil.getToday("yyyy-MM-dd hh:mm:ss.SSS"));
			if (!this.setCallSpSettRegProd(couponInfo, itemInfoList, spRegistProdList, couponReq.getCudType(),
					couponReq)) {
				throw new CouponException(couponInfo.getErrorCode(), couponInfo.getMessage(), null);
			}
			this.log.info("■■■■■ setTbDpProdInfoValue 완료 ■■■■■", DateUtil.getToday("yyyy-MM-dd hh:mm:ss.SSS"));

			// 검색 서버를 위한 MQ 연동
			this.setForMakeMq(couponInfo, itemInfoList, couponReq);

			this.log.info("■■■■■ cacheEvictShoppingMeta 시작 ■■■■■");
			this.cacheEvictShoppingMeta(couponInfo, couponReq);
			this.log.info("■■■■■ cacheEvictShoppingMeta 완료 ■■■■■");

		} else {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "필수 파라미터 값이 없습니다.", null);
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
				couponInfo.setErrorCode(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC);
				couponInfo.setMessage("B2B상품은 배송상품 유형일 수 없습니다.");
				return false;
			}
		}

		// 업체 유효성 검증
		if (!this.validateBusinessPartner(couponInfo)) {
			couponInfo.setErrorCode(CouponConstants.COUPON_IF_ERROR_CODE_NOT_BPID);
			return false;
		}

		// 카탈로그 유효성 검증
		if (!this.validateCatalog(couponInfo)) { // CatalogCode 확인 해야 함
			couponInfo.setErrorCode(CouponConstants.COUPON_IF_ERROR_CODE_NOT_CATALOGID);
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
		this.log.info("■■■■■ setTbDpProdInfoValue Start ■■■■■");
		TbDpProdInfo dp = new TbDpProdInfo();
		try {

			// ////////////////// Coupon 정보 S////////////////////////////
			dp.setProdId(couponInfo.getProdId());
			dp.setProdChrgYn("N");
			dp.setSellerMbrNo(couponInfo.getMbrNo());
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
			IcmsJobPrint.printTblDpProd(dp, "TB_DP_PROD - COUPON");
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
				dp.setSellerMbrNo(couponInfo.getMbrNo());
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
				dp.setBaseYn("Y");   // 에피소드만 입력
				tblDpProdList.add(dp);
				IcmsJobPrint.printTblDpProd(dp, "TB_DP_PROD - ITEM::" + i);
			}
			// ////////////////// Item 정보 E////////////////////////////

			// 저장
			this.couponItemService.insertTbDpProdInfo(tblDpProdList);

			// // Phone 목록저장
			// PhoneService phService = new PhoneService();
			// phService.setPhoneListToInfo(cInfo);

			// 세팅 값 확인
			this.log.info("■■■■■ setTbDpProdInfoValue End ■■■■■");
		} catch (CouponException e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_PROD VO 셋팅 실패", null);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_PROD VO 셋팅 실패", null);
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
		this.log.info("■■■■■ setTbDpShpgProdListValue Start ■■■■■");
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
			dsp.setChnlCompNm(couponInfo.getCompNm());
			if (couponInfo.getCoupnStatus().equals("3")) { // 2:판매대기 3:판매중 4:판매중지 5:판매금지
				dsp.setSaleYn("Y");
			} else {
				dsp.setSaleYn("N");
			}

			dsp.setContentsOrdrCd(CouponConstants.UPDATE_TYPE_CD_DESC); // D
			dsp.setSaleCnt(0);
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
			}else if (couponInfo.getStoreSaleType().equals("4")) { //  금액교환권
				dsp.setProdCaseCd(CouponConstants.PAY_CODE_CASH_EXCHANGE);
				dsp.setDlvProdYn("N");
			}else if (couponInfo.getStoreSaleType().equals("5")) { // 충전권
				dsp.setProdCaseCd(CouponConstants.PAY_CODE_CHARGE);
				dsp.setDlvProdYn("N");
			}
			dsp.setB2bProdYn(couponInfo.getStoreb2bFlag());
			dsp.setMangBpId("");
			dsp.setSrcContentId(couponInfo.getCouponCode());
			dsp.setCouponSendType(couponInfo.getSendMsgType());
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
				dsp.setChnlCompNm(couponInfo.getCompNm());
				if (itemInfo.getItemStatus().equals("3")) { // 2:판매대기 3:판매중 4:판매중지 5:판매금지
					dsp.setSaleYn("Y");
				} else {
					dsp.setSaleYn("N");
				}

				dsp.setContentsOrdrCd(CouponConstants.UPDATE_TYPE_CD_DESC); // D
				dsp.setSaleCnt(Long.parseLong(itemInfo.getMaxCount()));
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
				}else if (couponInfo.getStoreSaleType().equals("4")) { //  금액교환권
					dsp.setProdCaseCd(CouponConstants.PAY_CODE_CASH_EXCHANGE);
					dsp.setDlvProdYn("N");
				}else if (couponInfo.getStoreSaleType().equals("5")) { // 충전권
					dsp.setProdCaseCd(CouponConstants.PAY_CODE_CHARGE);
					dsp.setDlvProdYn("N");
				}
				dsp.setB2bProdYn(couponInfo.getStoreb2bFlag());
				dsp.setMangBpId(itemInfo.getBpManageId());
				dsp.setSrcContentId(itemInfo.getItemCode());
				dsp.setCouponSendType(couponInfo.getSendMsgType());
				dsp.setRegId(couponInfo.getBpId());
				dsp.setUpdId(couponInfo.getBpId());
				dsp.setCudType(itemInfo.getCudType());
				tbDpShpgProdList.add(dsp);
				IcmsJobPrint.printTbDpShpgProd(dsp, "TB_DP_SHPG_PROD - ITEM::" + i);
			}

			// 저장
			this.couponItemService.insertTbDpShpgProdInfo(tbDpShpgProdList);
			this.log.info("■■■■■ setTbDpShpgProdListValue End ■■■■■");
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
		this.log.info("■■■■■ setTbDpProdDescListValue Start ■■■■■");
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
			
			this.getConnectMqForSkReceipt(couponInfo, itemInfoList); // 김희민 매니저 요청으로 SKT로 전송하여 휴대폰 영수증에 티스토어 구매 내역을 보여 주기위한 시스템 
			
			
			
			this.log.info("■■■■■ setTbDpProdDescListValue End ■■■■■");
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
		this.log.info("■■■■■ setTbDpProdRshpValue Start ■■■■■");
		TbDpProdRshpInfo dps = null;
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
			this.log.info("■■■■■ setTbDpProdRshpValue End ■■■■■");
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
		this.log.info("■■■■■ setTbDpTenantProdListValue Start ■■■■■");
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
			this.couponItemService.insertTbDpTenantProdInfo(tbDpTenantProdList, couponInfo.getProdId());
			this.log.info("■■■■■ setTbDpTenantProdListValue End ■■■■■");
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
		this.log.info("■■■■■ setTbDpTenantProdPriceListValue Start ■■■■■");
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
			dtpp.setDcRate(0); // 할인_율
			dtpp.setDcAmt(0); // 할인_금액
			dtpp.setDcAftProdAmt(0); // 할인_후_상품_금액
			dtpp.setTaxClsf(couponInfo.getTaxType()); // 세금_구분
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
				dtpp.setProdAmt(Long.parseLong(itemInfo.getItemPrice()));
				dtpp.setChnlUnlmtAmt(0); // ??
				dtpp.setChnlPeriodAmt(0); // ??
				dtpp.setProdNetAmt(Long.parseLong(itemInfo.getOrgPrice()));
				dtpp.setDcRate(Long.parseLong(itemInfo.getDcRate()));
				if (Long.parseLong(itemInfo.getOrgPrice()) - Long.parseLong(itemInfo.getSalePrice()) > 0) { // 금액이 >0 보다
					dtpp.setDcAmt(Long.parseLong(itemInfo.getOrgPrice()) - Long.parseLong(itemInfo.getSalePrice()));
				} else {
					dtpp.setDcAmt(0);
				}
				dtpp.setDcAftProdAmt(Long.parseLong(itemInfo.getSalePrice()));
				dtpp.setTaxClsf(couponInfo.getTaxType()); // 세금_구분
				dtpp.setRegId(couponInfo.getBpId());
				dtpp.setUpdId(couponInfo.getBpId());
				dtpp.setCudType(itemInfo.getCudType());
				tbDpTenantProdPriceList.add(dtpp);
				IcmsJobPrint.printTbTenantDpProdPrice(dtpp, "TB_DP_TENANT_PROD_PRICE- ITEM:::" + i);
			}
			// 저장
			this.couponItemService.insertTbDpTenantProdPriceInfo(tbDpTenantProdPriceList);
			this.log.info("■■■■■ setTbDpTenantProdPriceListValue End ■■■■■");
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
		this.log.info("■■■■■ setTbDpProdCatalogMapgInfoValue Start ■■■■■");
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
			this.log.info("■■■■■ setTbDpProdCatalogMapgInfoValue End ■■■■■");
		} catch (CouponException e) {
			throw new CouponException(e.getErrCode(), e.getMessage(), null);
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
		this.log.info("■■■■■ setTbDpProdOptValue Start ■■■■■");
		TbDpProdOpt dpo = null;
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
			this.log.info("■■■■■ setTbDpProdOptValue End ■■■■■");
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
		this.log.info("■■■■■ setTbDpSprtDeviceListValue Start ■■■■■");
		TbDpSprtDeviceInfo tdsd = null;
		try {

			// ////////////////// Item 정보 S////////////////////////////
			for (int i = 0; i < itemInfoList.size(); i++) {
				DpItemInfo itemInfo = itemInfoList.get(i);
				tdsd = new TbDpSprtDeviceInfo();
				tdsd.setProdId(itemInfo.getProdId()); // 에피소드 상품
				tdsd.setDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);
				tdsd.setCudType(itemInfo.getCudType());
				tdsd.setRegId(couponInfo.getBpId());
				tdsd.setUpdId(couponInfo.getBpId());
				tbDpSprtDeviceList.add(tdsd);
			}
			// 저장
			this.couponItemService.insertTbDpSprtDeviceInfo(tbDpSprtDeviceList);
			this.log.info("■■■■■ setTbDpSprtDeviceListValue End ■■■■■");
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
			this.log.info("■■■■■ setTbDpProdTagListValue Start ■■■■■");
			// ////////////////// Coupon 정보 S////////////////////////////
			ArrayList<DpCatalogTagInfo> tagList = new ArrayList<DpCatalogTagInfo>(); // TB_DP_PROD_TAG 정보
			if (StringUtils.isNotEmpty(couponInfo.getTag())) {
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
			}
			this.log.info("■■■■■ setTbDpProdTagListValue End ■■■■■");
		} catch (CouponException e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_PROD_TAG VO 셋팅 실패", null);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "TB_DP_PROD_TAG VO 셋팅 실패", null);
		}
		return true;
	} // End setTbDpTenantProdPrice

	/**
	 * setCallSpRegistProd value 셋팅.
	 * 
	 * @param couponInfo
	 *            couponInfo
	 * @param itemInfoList
	 *            itemInfoList
	 * @param spRegistProdList
	 *            spRegistProdList
	 * @param cudType
	 *            cudType
	 * @param couponReq
	 *            couponReq
	 * @return boolean
	 */
	private boolean setCallSpSettRegProd(DpCouponInfo couponInfo, List<DpItemInfo> itemInfoList,
			List<SpRegistProd> spRegistProdList, String cudType, CouponReq couponReq) {
		this.log.info("■■■■■ setCallSpSettRegProd Start ■■■■■");
		SpRegistProd spRegistProd = new SpRegistProd();
		try {

			// ////////////////// Coupon 정보 S////////////////////////////
			spRegistProd.setProdId(couponInfo.getProdId());
			spRegistProd.setSettlRt(Integer.parseInt(couponInfo.getAccountingRate()));
			spRegistProd.setSaleMbrNo(couponInfo.getMbrNo());
			spRegistProd.setSaleStdDt(couponInfo.getIssueSDate());
			spRegistProd.setSaleEndDt(couponInfo.getIssueEDate());
			spRegistProd.setRegId(couponInfo.getBpId());
			spRegistProd.setCudType(cudType);
			spRegistProdList.add(spRegistProd);
			// ////////////////// Coupon 정보 E////////////////////////////

			// ////////////////// Item 정보 S////////////////////////////
			for (int i = 0; i < itemInfoList.size(); i++) {
				DpItemInfo itemInfo = itemInfoList.get(i);
				spRegistProd = new SpRegistProd();
				spRegistProd.setProdId(itemInfo.getProdId());
				spRegistProd.setSettlRt(Integer.parseInt(couponInfo.getAccountingRate()));
				spRegistProd.setSaleMbrNo(couponInfo.getMbrNo());
				spRegistProd.setSaleStdDt(couponInfo.getIssueSDate());
				spRegistProd.setSaleEndDt(couponInfo.getIssueEDate());
				spRegistProd.setRegId(couponInfo.getBpId());
				spRegistProd.setCudType(itemInfo.getCudType());
				spRegistProdList.add(spRegistProd);
			}

			// 저장
			this.couponItemService.insertCallSpSettRegProd(spRegistProdList);
			this.log.info("■■■■■ setCallSpSettRegProd End ■■■■■");

		} catch (CouponException e) {
			throw new CouponException(e.getErrCode(), e.getMessage(), null);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "정산율 배포 실패!!", null);
		}

		this.getConnectMq(couponInfo, itemInfoList, couponReq, cudType);

		return true;
	} // End setTbDpProdDesc

	/**
	 * 해당 업체가 존재 하는지 체크 한다.
	 * 
	 * @param couponInfo
	 *            couponInfo
	 * @return boolean
	 */
	private boolean validateBusinessPartner(DpCouponInfo couponInfo) {
		this.log.info("■■■■■ validateBusinessPartner ■■■■■");
		this.log.info("################ [SAC DP LocalSCI] SAC Member Stat : sellerSearchSCI.detailInformation : "
				+ DateUtil.getToday("yyyy-MM-dd hh:mm:ss.SSS"));
		DetailInformationSacReq detailInformationSacReq = new DetailInformationSacReq();
		DetailInformationSacRes detailInformationSacRes = null;
		List<SellerMbrSac> sellerMbrSacList = new ArrayList<SellerMbrSac>();
		SellerMbrSac sellerMbrSac = new SellerMbrSac();
		this.log.debug("#########################################################");
		this.log.debug("sellerMbrNo	:	" + couponInfo.getBpId());
		this.log.debug("#########################################################");
		sellerMbrSac.setSellerId(couponInfo.getBpId());
		sellerMbrSacList.add(sellerMbrSac);
		detailInformationSacReq.setSellerMbrSacList(sellerMbrSacList);

		try {
			detailInformationSacRes = this.sellerSearchSCI.detailInformation(detailInformationSacReq);
			Iterator<String> it = detailInformationSacRes.getSellerMbrListMap().keySet().iterator();
			List<SellerMbrSac> sellerMbrs = null;
			while (it.hasNext()) {
				String key = it.next();
				sellerMbrs = detailInformationSacRes.getSellerMbrListMap().get(key);
				if (sellerMbrs != null) {
					if (StringUtils.isBlank(sellerMbrs.get(0).getSellerCompany())) {
						throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "상호명이 없습니다.",
								couponInfo.getBpId());
					}
					couponInfo.setCompNm(sellerMbrs.get(0).getSellerCompany());
					couponInfo.setMbrNo(sellerMbrs.get(0).getSellerKey());

				} else {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "상호명이 없습니다.",
							couponInfo.getBpId());
				}
			}
			this.log.info("################ [SAC DP LocalSCI] SAC Member End : sellerSearchSCI.detailInformation : "
					+ DateUtil.getToday("yyyy-MM-dd hh:mm:ss.SSS"));

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
		if (StringUtils.equalsIgnoreCase("C", cudType)) {
			if (this.couponItemService.getCouponCountCudType(couponInfo.getCouponCode()) > 0) {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DUP_COUPONID,
						"해당 Coupon_Id로 등록한 coupon가 있습니다.", couponInfo.getCouponCode());
			}
		}
		for (int i = 0; i < itemInfoList.size(); i++) {
			if (StringUtils.equalsIgnoreCase("C", itemInfoList.get(i).getCudType())) {
				if (this.couponItemService.getItemCountCudType(itemInfoList.get(i).getItemCode()) > 0) {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DUP_ITEMID,
							"해당 Item_Id로 등록한 item이 있습니다.", itemInfoList.get(i).getItemCode());
				}
			}
		}
		return true;
	} // End validateCatalog

	/**
	 * cash flush .
	 * 
	 * @param couponInfo
	 *            couponInfo
	 * @param couponReq
	 *            couponReq
	 * @return
	 */
	private void cacheEvictShoppingMeta(DpCouponInfo couponInfo, CouponReq couponReq) {
		if ("U".equalsIgnoreCase(couponReq.getCudType())) {
			this.log.info("Product Upate cash Catalog Id : " + couponInfo.getStoreCatalogCode());
			this.cacheEvictHelperComponent.evictProductMeta(Shopping, couponInfo.getStoreCatalogCode());
		}
	}

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
		if (couponReq != null && StringUtils.isNotBlank(couponReq.getCouponCode())) {

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

			if(StringUtils.isBlank(coupnStatus)){
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "필수 파라미터 값이 없습니다. (coupnStatus)",
						coupnStatus);			
			}			
			
			if(StringUtils.isBlank(upType)){
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "필수 파라미터 값이 없습니다. (upType)",
						coupnStatus);			
			}
			
			if (!(StringUtils.equalsIgnoreCase(upType, "0") || StringUtils.equalsIgnoreCase(upType, "1") || StringUtils
					.equalsIgnoreCase(upType, "2"))) {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "쿠폰상태 값이 유효하지 않습니다.",
						coupnStatus);
			}

			// 기등록된 컨텐트 존재여부 확인 old쿠폰ID,아이템ID로 new쿠폰ID,아이템ID 가져오기 조회
			String newCouponCode = this.couponItemService.getCouponGenerateId(couponCode);

			if (StringUtils.isBlank(newCouponCode)) {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_NOT_COUPONID, null, null);
			}
			if ((StringUtils.equalsIgnoreCase(upType, "1"))) { // 1=단품상태변경 item 값은 필수
				itemCode = this.couponItemService.getItemGenerateId(itemCode);
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
				throw new CouponException(e.getErrCode(), e.getErrorData().getErrorMsg(), null);
			} catch (Exception e) {
				throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, e.getMessage(), null);
			} finally {
				this.log.info("■■■■■ DB Transaction END ■■■■■");
			}
			
			// 캐쉬 삭제 로직 추가 (2015.07.31 Jade 추가)
			String catalogId = this.couponItemService.getShoppingCatalogIdByChannelId(newCouponCode);
			this.cacheEvictHelperComponent.evictProductMeta(Shopping, catalogId);
			this.log.info("■■■■■■■■■■■■■■■■■■■■■■■■■");
			this.log.info("■■■■■catalogId:■■■■■" +catalogId);
			this.log.info("■■■■■■■■■■■■■■■■■■■■■■■■■");
			// 검색 서버를 위한 MQ 연동
			this.setShoppingCatalogIdByChannelIdForMq(newCouponCode);

		} else {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "필수 파라미터 값이 없습니다. (couponCode)", null);
		}
		return true;
	} // End processForCouponStatus
	
	
	/**
	 * 팅/특가 쿠폰 ID 조회 한다.
	 * 
	 * @param couponCodes
	 *            couponCodes
	 * @return List<CouponRes>
	 */
	@Override
	public String getSpecialProductCouponId(CouponReq couponReq) {
		this.log.info("<<<<< CouponContentService >>>>> getSpecialProductCouponId...");
		String couponCode = couponReq.getCouponCode();
		
		if (StringUtils.isBlank(couponReq.getCouponCode())) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "필수 파라미터 값이 없습니다. (couponCode)", null);
		}		
		
		if (StringUtils.isBlank(couponReq.getItemCodes())) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "필수 파라미터 값이 없습니다. (itemCodes)", null);
		}		
		String[] itemCodes = couponReq.getItemCodes().split(",");
		// 기등록된 컨텐트 존재여부 확인 old쿠폰ID,아이템ID로 new쿠폰ID,아이템ID 가져오기 조회
		String newCouponCode = this.couponItemService.getCouponGenerateId(couponCode);

		if (StringUtils.isBlank(newCouponCode)) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_NOT_COUPONID, null, null);
		}
		
		List<String> itemList = this.couponItemService.getCouponCompareItemCode(newCouponCode);
		
		boolean compareFlag = false;
		for (int kk=0 ; kk< itemCodes.length; kk++){
			for (String val : itemList) {
    			if(val.equals(itemCodes[kk])){
    				compareFlag = true;
    			}
			}
			if(compareFlag){
    			if(kk != itemCodes.length-1){
    				compareFlag = false;
    			}
			}else{
				break;
			}
		}
		if(!compareFlag){
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "couponCode 에 매핑되어 있는 itemCodes 값이 아닙니다.", null);
		}
		
		
		
		StringBuffer couponIdBuff = new StringBuffer();
		String result="";
		try {
			String episodeId ="";
			int kk = 0;
			for (String itemCode : itemCodes) {
				if (StringUtils.isBlank(itemCode)) {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_NOT_ITEMID, null, null);
				}// end if
				episodeId = this.couponItemService.getItemGenerateId(itemCode);

				int ssCnt = this.couponItemService.getSpecialProdCnt(episodeId);

				if (ssCnt <= 0) {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_NOT_SPECIAL, "특가(팅) 상품이 아닙니다.",
							episodeId);
				}//end if

				result = this.couponItemService.getSpecialProductCouponId(episodeId);

				if (StringUtils.isBlank(result)) {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_COUPON_ID, null, null);
				}//end if
				if(kk ==0){
					couponIdBuff.append(result);
				}else{
					couponIdBuff.append(",");
					couponIdBuff.append(result);
				}
				kk++;
			}//end for

		} catch (CouponException e) {
			throw e;
		}
		return couponIdBuff.toString();
	}	
	
	/**
	 * 팅/특가 상품 상태 변경 한다.
	 * 
	 * @param couponReq
	 *            couponReq
	 * @return boolean
	 */
	@Override
	public boolean updateForSpecialCouponStatus(CouponReq couponReq) {
		// 호출
		this.log.info("■■■■■ updateForSpecialCouponStatus ■■■■■");
		String couponCode = couponReq.getCouponCode();
		String[] itemCodes = couponReq.getItemCodes().split(",");

		String dpStatusCode = "";

		// 기등록된 컨텐트 존재여부 확인 old쿠폰ID,아이템ID로 new쿠폰ID,아이템ID 가져오기 조회
		String newCouponCode = this.couponItemService.getCouponGenerateId(couponCode);

		// 쿠폰상태 코드 -> 전시 코드로 변환
		dpStatusCode = this.getDPStatusCode("4");
		String episodeId = "";
		StringBuffer episodeIdBuff = new StringBuffer();
		int kk =0;
		for (String itemCode : itemCodes) {
			episodeId = this.couponItemService.getItemGenerateId(itemCode);
			if(kk ==0){
				episodeIdBuff.append(episodeId);
			}else{
				episodeIdBuff.append(",");
				episodeIdBuff.append(episodeId);
			}
			kk++;
		}
		// 2. DB Update
		try {
			// TB_DP_TENANT_PROD 상태 처리
			this.couponItemService.updateCouponStatusForSpecialProd(newCouponCode, dpStatusCode, "2", episodeIdBuff.toString());

		} catch (CouponException e) {
			throw new CouponException(e.getErrCode(), e.getErrorData().getErrorMsg(), null);
		} catch (Exception e) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, e.getMessage(), null);
		} finally {
			this.log.info("■■■■■ DB Transaction END ■■■■■");
		}

		// 검색 서버를 위한 MQ 연동
		this.setShoppingCatalogIdByChannelIdForMq(newCouponCode);

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
			for (String couponCode : couponCodes) {
				if (StringUtils.isBlank(couponCode)) {
					throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "필수 파라미터 값이 없습니다. (couponCode)", null);
				}
			}
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
	 * @param itemsCodes
	 *            itemsCodes
	 * @return CouponRes
	 */
	@Override
	public CouponRes getSpecialProductDetail(String couponCode, String[] itemsCodes) {
		this.log.info("<<<<< CouponContentService >>>>> getSpecialProductDetail...");
		CouponRes info = null;
		try {
			info = this.couponItemService.getSpecialProductDetail(couponCode, itemsCodes);
		} catch (CouponException e) {
			throw e;

		}
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
		this.log.info("<<<<< CouponContentService >>>>> getSpecialProductDetailForDate...");
		if (StringUtils.isBlank(couponReq.getStartDate())) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "필수 파라미터 값이 없습니다. (startDate)", null);
		}	
		if (StringUtils.isBlank(couponReq.getEndDate())) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "필수 파라미터 값이 없습니다. (endDate)", null);
		}		
		
		if (couponReq.getStartDate().compareTo(couponReq.getEndDate()) == 1) {
			throw new CouponException(CouponConstants.COUPON_IF_ERROR_CODE_DB_ETC, "startDate가 endDate 값보다 클수 없습니다.", null);
		}
		
		CouponRes info = null;
		try {
			info = this.couponItemService.getSpecialProductDetailForDate(couponReq);
		} catch (CouponException e) {
			throw e;

		}
		return info;
	}


	/**
	 * 카탈로그 및 메뉴ID 조회 한다.
	 * 
	 * @param catalogId
	 *            catalogId
	 * @return CouponRes
	 */
	private CouponRes getCatalogNmMenuId(String catalogId) {
		this.log.info("<<<<< CouponContentService >>>>> getCatalogNmMenuId...");
		CouponRes info = null;
		info = this.couponItemService.getCatalogNmMenuId(catalogId);
		return info;
	}
	
	/**
	 * getConnectMqForSkReceipt MQ 연동
	 * 
	 * @param couponInfo
	 *            couponInfo
	 * @param itemInfoList
	 *            itemInfoList
	 * @return boolean
	 */

	private boolean getConnectMqForSkReceipt(DpCouponInfo couponInfo, List<DpItemInfo> itemInfoList) {
		boolean result = true;
		try{
			List<String> ids  = new  ArrayList<String>();
			for(DpItemInfo info : itemInfoList){
				ids.add(info.getProdId());
			}
			RaterMessage raterMessage = RaterMessage.newMsgWithProdIds(ids);
			this.log.info("raterAmqpTemplate episode_prod_ids S:::" + ids.toString());
			this.raterAmqpTemplate.convertAndSend(raterMessage);
			this.log.info("raterAmqpTemplate episode_prod_ids E:::" + ids.toString());
		} catch (AmqpException ae) {
			result = false;
			this.log.info("MQ 연동중 Error 발생. - error msg:{}, raterAmqpTemplate:{}", ae.getMessage(), RaterMessage.class);
		} catch (Exception e) {
			result = false;
			this.log.info("MQ 연동중 Error 발생. - error msg:{}, Exception:{}", e.getMessage());
		}
	
		return result;
	}
	/**
	 * getConnectMq MQ 연동
	 * 
	 * @param couponInfo
	 *            couponInfo
	 * @param itemInfoList
	 *            itemInfoList
	 * @param couponReq
	 *            couponReq
	 * @return boolean
	 */

	private boolean getConnectMq(DpCouponInfo couponInfo, List<DpItemInfo> itemInfoList, CouponReq couponReq, String cudType) {
		boolean result = true;
		this.log.info("■■■■■ MQ 연동 start ■■■■■");

		NotificationIprm noti = new NotificationIprm();
		List<ProductTenantPrice> productTenantPriceList = null;
		List<ProductTenantRate> productTenantRateList = null;
		try {
			CouponRes couponRes = this.getCatalogNmMenuId(couponInfo.getStoreCatalogCode());

			// //////////////////////////////////채널 MQ 연동 //////////////////////////////////////////////////////
			noti.setTransactionKey(couponReq.getTxId() + "0000");
			/**
			 * 상품정보 세팅.
			 */
			Product product = new Product();

			product.setSyncDataControlType(cudType); // 구분
			product.setProdId(couponInfo.getProdId());// 상품ID
			product.setProdNm(couponInfo.getCouponName()); // 상품명

			product.setProdFdTypCd("PD000502");// 무료
			product.setSvcGrpTypCd(CouponConstants.CUPON_SVC_GRP_CD);// 서비스그룹코드DP000206

			product.setMbrNo(couponInfo.getMbrNo());// 판매자mbrNO
			product.setCid(couponInfo.getCouponCode()); // cid
			product.setCateNo(CouponConstants.TOP_MENU_ID_CUPON_CONTENT); // 탑카테고리 DP28
			product.setSubCateNo(couponRes.getMenuId());// 서브카테고리
			product.setCoContentsId(couponInfo.getCouponCode());// 업체컨텐츠ID == CID랑 같음

			if (Integer.parseInt(couponInfo.getValidUntil()) > 0) { // 유효일수 값 비교
				product.setUseTermUnitCd(CouponConstants.USE_PERIOD_UNIT_DAY); // PD00312 기간제(일)
				product.setUserTerm(couponInfo.getValidUntil()); // 유효일수로 셋팅
			} else {
				product.setUseTermUnitCd(CouponConstants.USE_PERIOD_UNIT_SELECT); // PD00319 기간선택
				// USE_TERM_UNIT
				product.setUserTerm(couponInfo.getValidEDate()); // 유효종료일시로 셋팅
			}
			product.setCatalogId(couponInfo.getStoreCatalogCode());// 카테고리ID
			product.setCatalogNm(couponRes.getCatalogName()); // 카테고리명
			product.setTaxTypCd(couponInfo.getTaxType()); // 세금구분코드
			product.setChnlTypCd(CouponConstants.COUPON_CONTENT_TP_CHA);
			Date date = new Date();
			String modifiedDate = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
			if ("C".equalsIgnoreCase(cudType)) {
				product.setRegId(couponInfo.getBpId()); // 등록ID
				product.setRegDt(modifiedDate); // 등록일시
				product.setUpdId(couponInfo.getBpId()); // 수정ID
				product.setUpdDt(modifiedDate); // 수정일시

			} else {
				product.setRegId(couponRes.getRegId()); // 등록ID
				product.setRegDt(couponRes.getRegDt()); // 등록일시
				product.setUpdId(couponInfo.getBpId()); // 수정ID
				product.setUpdDt(modifiedDate); // 수정일시
			}
			noti.setProduct(product);
			/**
			 * 상품가격정보 세팅.
			 */
			productTenantPriceList = new ArrayList<ProductTenantPrice>();
			ProductTenantPrice productTenantPrice = new ProductTenantPrice();
			productTenantPrice.setProdId(couponInfo.getProdId());
			productTenantPrice.setSyncDataControlType(cudType);
			productTenantPrice.setTenantId(CouponConstants.TENANT_ID); // tenentId
			productTenantPrice.setApplyStartDt(couponInfo.getIssueSDate());// 적용_시작_일시
			productTenantPrice.setProdAmt("0");// 상품가격

			if ("C".equalsIgnoreCase(cudType)) {
				productTenantPrice.setRegId(couponInfo.getBpId()); // 등록ID
				productTenantPrice.setRegDt(modifiedDate); // 등록일시
				productTenantPrice.setUpdId(couponInfo.getBpId()); // 수정ID
				productTenantPrice.setUpdDt(modifiedDate); // 수정일시

			} else {
				productTenantPrice.setRegId(couponRes.getRegId()); // 등록ID
				productTenantPrice.setRegDt(couponRes.getRegDt()); // 등록일시
				productTenantPrice.setUpdId(couponInfo.getBpId()); // 수정ID
				productTenantPrice.setUpdDt(modifiedDate); // 수정일시
			}
			
			productTenantPriceList.add(productTenantPrice);
			noti.setProductTenantPriceList(productTenantPriceList);
			
			
			/**
			 * 상품의 테넌트별 정산율.
			 */
			productTenantRateList = new ArrayList<ProductTenantRate>();
			ProductTenantRate productTenantRate = new ProductTenantRate();
			productTenantRate.setProdId(couponInfo.getProdId());
			productTenantRate.setSyncDataControlType(cudType);
			productTenantRate.setTenantId(CouponConstants.TENANT_ID); // tenentId
			productTenantRate.setApplyStartDt(couponInfo.getIssueSDate());// 적용_시작_일시
			productTenantRate.setProdRate(couponInfo.getAccountingRate());// 상품정산율
			productTenantRate.setSttlTyp("01");// 정산_유형
			productTenantRate.setSttlMthd("01");// 정산_방법
			productTenantRate.setSttlApplStdCd("02");// 정산_적용_기준_코드
			productTenantRate.setUnitAmt("0"); //단위_금액
			productTenantRate.setRegId("SAC_SHOPPING"); // 등록ID
			productTenantRate.setRegDt(modifiedDate); // 등록일시
			productTenantRate.setUpdId("SAC_SHOPPING"); // 수정ID
			productTenantRate.setUpdDt(modifiedDate); // 수정일시
			
			productTenantRateList.add(productTenantRate);
			noti.setProductTenantRateList(productTenantRateList);
			
			
			
			this.log.info("channel_prod_id S:::" + couponInfo.getProdId());
			this.shoppingIprmAmqpTemplate.convertAndSend(noti); // async
			this.log.info("channel_prod_id E:::" + couponInfo.getProdId());

			// //////////////////////////////////에피소드 MQ 연동 //////////////////////////////////////////////////////
			for (int i = 0; i < itemInfoList.size(); i++) {
				DpItemInfo itemInfo = itemInfoList.get(i);
				noti.setTransactionKey(couponReq.getTxId() + "000" + (i + 1));
				/**
				 * 상품정보 세팅.
				 */
				Product productVO = new Product();

				productVO.setSyncDataControlType(itemInfo.getCudType()); // 구분
				productVO.setProdId(itemInfo.getProdId());// 상품ID
				productVO.setProdNm(itemInfo.getItemName()); // 상품명

				if (!String.valueOf(itemInfo.getItemPrice()).equals("0")) { // 유료
					productVO.setProdFdTypCd("PD000501");// 유료
				} else {
					productVO.setProdFdTypCd("PD000502");// 무료
				}
				productVO.setSvcGrpTypCd(CouponConstants.CUPON_SVC_GRP_CD);// 서비스그룹코드DP000206

				productVO.setMbrNo(couponInfo.getMbrNo());// 판매자mbrNO
				productVO.setCid(itemInfo.getItemCode()); // cid
				productVO.setCateNo(CouponConstants.TOP_MENU_ID_CUPON_CONTENT); // 탑카테고리 DP28
				productVO.setSubCateNo(couponRes.getMenuId());// 서브카테고리
				productVO.setCoContentsId(itemInfo.getItemCode());// 업체컨텐츠ID == CID랑 같음

				if (Integer.parseInt(couponInfo.getValidUntil()) > 0) { // 유효일수 값 비교
					productVO.setUseTermUnitCd(CouponConstants.USE_PERIOD_UNIT_DAY); // PD00312 기간제(일)
					productVO.setUserTerm(couponInfo.getValidUntil()); // 유효일수로 셋팅
				} else {
					productVO.setUseTermUnitCd(CouponConstants.USE_PERIOD_UNIT_SELECT); // PD00319 기간선택
					// USE_TERM_UNIT
					productVO.setUserTerm(couponInfo.getValidEDate()); // 유효종료일시로 셋팅
				}
				productVO.setCatalogId(couponInfo.getStoreCatalogCode());// 카테고리ID
				productVO.setCatalogNm(couponRes.getCatalogName()); // 카테고리명
				productVO.setTaxTypCd(couponInfo.getTaxType()); // 세금구분코드
				productVO.setChnlTypCd(CouponConstants.COUPON_CONTENT_TP_EPI);
				if ("C".equalsIgnoreCase(itemInfo.getCudType())) {
					productVO.setRegId(couponInfo.getBpId()); // 등록ID
					productVO.setRegDt(modifiedDate); // 등록일시
					productVO.setUpdId(couponInfo.getBpId()); // 수정ID
					productVO.setUpdDt(modifiedDate); // 수정일시

				} else {
					productVO.setRegId(couponRes.getRegId()); // 등록ID
					productVO.setRegDt(couponRes.getRegDt()); // 등록일시
					productVO.setUpdId(couponInfo.getBpId()); // 수정ID
					productVO.setUpdDt(modifiedDate); // 수정일시
				}
				noti.setProduct(productVO);
				/**
				 * 상품가격정보 세팅.
				 */
				productTenantPriceList = new ArrayList<ProductTenantPrice>();
				ProductTenantPrice productTenantPriceVO = new ProductTenantPrice();
				productTenantPriceVO.setProdId(itemInfo.getProdId());
				productTenantPriceVO.setSyncDataControlType(itemInfo.getCudType());
				productTenantPriceVO.setTenantId(CouponConstants.TENANT_ID); // tenentId
				productTenantPriceVO.setApplyStartDt(couponInfo.getIssueSDate());// 적용_시작_일시
				productTenantPriceVO.setProdAmt(itemInfo.getItemPrice());// 상품가격

				if ("C".equalsIgnoreCase(itemInfo.getCudType())) {
					productTenantPriceVO.setRegId(couponInfo.getBpId()); // 등록ID
					productTenantPriceVO.setRegDt(modifiedDate); // 등록일시
					productTenantPriceVO.setUpdId(couponInfo.getBpId()); // 수정ID
					productTenantPriceVO.setUpdDt(modifiedDate); // 수정일시

				} else {
					productTenantPriceVO.setRegId(couponRes.getRegId()); // 등록ID
					productTenantPriceVO.setRegDt(couponRes.getRegDt()); // 등록일시
					productTenantPriceVO.setUpdId(couponInfo.getBpId()); // 수정ID
					productTenantPriceVO.setUpdDt(modifiedDate); // 수정일시
				}

				productTenantPriceList.add(productTenantPriceVO);
				noti.setProductTenantPriceList(productTenantPriceList);
				
				
				
				/**
				 * 상품의 테넌트별 정산율.
				 */
				productTenantRateList = new ArrayList<ProductTenantRate>();
				ProductTenantRate productTenantRateVo = new ProductTenantRate();
				productTenantRateVo.setProdId(itemInfo.getProdId());
				productTenantRateVo.setSyncDataControlType(itemInfo.getCudType());
				productTenantRateVo.setTenantId(CouponConstants.TENANT_ID); // tenentId
				productTenantRateVo.setApplyStartDt(couponInfo.getIssueSDate());// 적용_시작_일시
				productTenantRateVo.setProdRate(couponInfo.getAccountingRate());// 상품정산율
				productTenantRateVo.setSttlTyp("01");// 정산_유형
				productTenantRateVo.setSttlMthd("01");// 정산_방법
				productTenantRateVo.setSttlApplStdCd("02");// 정산_적용_기준_코드
				productTenantRateVo.setUnitAmt(itemInfo.getItemPrice()); //단위_금액
				productTenantRateVo.setRegId("SAC_SHOPPING"); // 등록ID
				productTenantRateVo.setRegDt(modifiedDate); // 등록일시
				productTenantRateVo.setUpdId("SAC_SHOPPING"); // 수정ID
				productTenantRateVo.setUpdDt(modifiedDate); // 수정일시
				
				productTenantRateList.add(productTenantRateVo);
				noti.setProductTenantRateList(productTenantRateList);				
				
				
				this.log.info("episode_prod_id S:::" + itemInfo.getProdId());
				this.shoppingIprmAmqpTemplate.convertAndSend(noti); // async
				this.log.info("episode_prod_id E:::" + itemInfo.getProdId());
			}
			
			this.log.info("■■■■■ MQ 연동 End ■■■■■");
		} catch (AmqpException ae) {
			result = false;
			this.log.info("MQ 연동중 Error 발생. - error msg:{}, NotificationIprm:{}", ae.getMessage(), noti);
		} catch (Exception e) {
			result = false;
			this.log.info("MQ 연동중 Error 발생. - error msg:{}, Exception:{}", e.getMessage());
		}

		return result;
	}


	/**
	 * setForMakeMq 값 만들기 (채널 에피소드 값 xml일경우)
	 * 
	 * @param couponInfo
	 *            couponInfo
	 * @param itemInfoList
	 *            itemInfoList
	 * @param couponReq
	 *            couponReq
	 */

	private void setForMakeMq(DpCouponInfo couponInfo, List<DpItemInfo> itemInfoList, CouponReq couponReq) {

		boolean cudFlag = false; // 신규 일때만 true 수정일땐 false

		if ("C".equals(couponReq.getCudType())) {
			cudFlag = true;
		}
		List<String> list = new ArrayList<String>();
		Map<String, Object> reqMap = new HashMap<String, Object>();
		// ////////////////// Item 정보 S////////////////////////////
		for (int i = 0; i < itemInfoList.size(); i++) {
			DpItemInfo itemInfo = itemInfoList.get(i);
			if ("C".equals(itemInfo.getCudType())) {
				cudFlag = true;
				list.add(itemInfo.getProdId());
			}
		}
		// ////////////////// Item 정보 E////////////////////////////
		reqMap.put("list", list);
		String ingYn = "";
		this.log.info("====================================================");
		this.log.info("list::" + list.toString());
		this.log.info("====================================================");
		if (cudFlag) { // 신규일 경우는 판매중인것만 MQ 연동
			ingYn = this.couponItemService.getShoppingIngYn(reqMap);
		} else {
			ingYn = "Y"; // 수정일 경우는 무조건 MQ 연동
		}

		this.getConnectMqForSearchServer(ingYn, couponInfo.getStoreCatalogCode());

	}

	/**
	 * setShoppingCatalogIdByChannelIdForMq 상태값만 변경시.
	 * 
	 * @param ChannelId
	 *            ChannelId
	 */

	private void setShoppingCatalogIdByChannelIdForMq(String ChannelId) {

		String catalogId = this.couponItemService.getShoppingCatalogIdByChannelId(ChannelId);

		this.getConnectMqForSearchServer("Y", catalogId);

	}

	/**
	 * getConnectMqForSearchServer MQ 연동
	 * 
	 * @param ingYn
	 *            ingYn
	 * @param catalogId
	 *            catalogId
	 */

	private void getConnectMqForSearchServer(String ingYn, String catalogId) {
		this.log.info("■■■■■ 상품정보 - 검색 서버 를 위한 MQ 연동 start ■■■■■");

		SearchInterfaceQueue queueMsg = SearchQueueUtils.makeMsg(
				"U"
				, CouponConstants.TOP_MENU_ID_CUPON_CONTENT
				, SearchConstant.UPD_ID_SAC_SHOPPING.toString()
				, SearchConstant.CONTENT_TYPE_CATALOG.toString()
				, catalogId
				);

		if ("Y".equals(ingYn)) { // 신규일 경우는 판매중인것만 MQ 연동 , 수정일 경우는 무조건 MQ 연동
			this.sacSearchAmqpTemplate.convertAndSend(queueMsg);
			this.log.info("=================================================");
			this.log.info("==MQ 연동 성공 :: queueMsg ::================" + queueMsg.toString());
			this.log.info("==MQ 연동 성공 :: catalogId ::================" + catalogId);
			this.log.info("=================================================");
		}
		this.log.info("■■■■■ 상품정보 - 검색 서버 를 위한 MQ 연동 end ■■■■■");
	}
}
