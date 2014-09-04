/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.feature.best.service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.best.BestDownloadSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.best.BestDownloadSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 05. 20. Updated by : 홍지호, SK 플래닛.
 */

@Service
public class BestDownloadServiceImpl implements BestDownloadService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService commonService;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	/**
	 * 
	 * <pre>
	 * BEST 다운로드 리스트 조회.
	 * </pre>
	 * 
	 * @param requestheader
	 *            공통헤더
	 * @param bestDownloadReq
	 *            파라미터
	 * @return BEST 다운로드 리스트
	 */
	@Override
	public BestDownloadSacRes searchBestDownloadList(SacRequestHeader requestheader, BestDownloadSacReq bestDownloadReq) {
		TenantHeader tenantHeader = requestheader.getTenantHeader();
		DeviceHeader deviceHeader = requestheader.getDeviceHeader();

		this.log.debug("########################################################");
		this.log.debug("tenantHeader.getTenantId()	:	" + tenantHeader.getTenantId());
		this.log.debug("tenantHeader.getLangCd()	:	" + tenantHeader.getLangCd());
		this.log.debug("deviceHeader.getModel()		:	" + deviceHeader.getModel());
		this.log.debug("########################################################");

		bestDownloadReq.setTenantId(tenantHeader.getTenantId());
		bestDownloadReq.setLangCd(tenantHeader.getLangCd());
		bestDownloadReq.setDeviceModelCd(deviceHeader.getModel());

		BestDownloadSacRes response = new BestDownloadSacRes();

		CommonResponse commonResponse = new CommonResponse();
		List<Product> productList = new ArrayList<Product>();

		// 파라미터 유효값 체크
		if (StringUtils.isNotEmpty(bestDownloadReq.getProdGradeCd())) {
			String[] arrayProdGradeCd = bestDownloadReq.getProdGradeCd().split("\\+");
			for (int i = 0; i < arrayProdGradeCd.length; i++) {
				if (StringUtils.isNotEmpty(arrayProdGradeCd[i])) {
					if (!"PD004401".equals(arrayProdGradeCd[i]) && !"PD004402".equals(arrayProdGradeCd[i])
							&& !"PD004403".equals(arrayProdGradeCd[i])) {
						this.log.debug("----------------------------------------------------------------");
						this.log.debug("유효하지않은 상품 등급 코드 : " + arrayProdGradeCd[i]);
						this.log.debug("----------------------------------------------------------------");

						throw new StorePlatformException("SAC_DSP_0003", (i + 1) + " 번째 prodGradeCd",
								arrayProdGradeCd[i]);
					}
				}
			}
		}

		int offset = 1; // default
		int count = 20; // default

		if (bestDownloadReq.getOffset() != null) {
			offset = bestDownloadReq.getOffset();
		}
		bestDownloadReq.setOffset(offset); // set offset

		if (bestDownloadReq.getCount() != null) {
			count = bestDownloadReq.getCount();
		}
		count = offset + count - 1;
		bestDownloadReq.setCount(count); // set count

		// STD_DT 설정
		String stdDt = bestDownloadReq.getStdDt();
		if (StringUtils.isNotEmpty(stdDt)) { // 집계 일자가 존재할 시 - 통계 이력 데이터 조회
			if (stdDt.length() != 8) { // 날짜 형식 틀림
				throw new StorePlatformException("SAC_DSP_0003", "stdDt", stdDt);
			}

			if (this.recentStdDt(bestDownloadReq.getListId(), stdDt)) { // 집계 일자가 최신 일자이면 리스트 테이블 조회
				bestDownloadReq.setSearchHisYn("N"); // TB_DP_LIST_PROD : 리스트 테이블 조회
			} else {
				bestDownloadReq.setSearchHisYn("Y"); // TB_DP_LIST_PROD_HIS : 리스트 이력 테이블 조회
			}
			stdDt += "000000"; // 시,분,초 추가

		} else { // 최근 집계 일자 조회
			stdDt = this.commonService.getBatchStandardDateString(bestDownloadReq.getTenantId(),
					bestDownloadReq.getListId());
			bestDownloadReq.setSearchHisYn("N"); // TB_DP_LIST_PROD : 리스트 테이블 조회
		}
		bestDownloadReq.setStdDt(stdDt);

		// 유/무료 리스트 선택(APP)
		if ("RNK000000006".equals(bestDownloadReq.getListId()) || "RNK000000008".equals(bestDownloadReq.getListId())
				|| "RNK000000007".equals(bestDownloadReq.getListId())) { // 유료 리스트
			bestDownloadReq.setProdChrgYn("Y");
		} else { // 무료 리스트
			bestDownloadReq.setProdChrgYn("N");
		}

		// 검색하는 상품 타입(APP, MM)
		String prodType = "APP"; // default

		// '+'로 연결 된 topMenuId를 배열로 전달
		if (StringUtils.isNotEmpty(bestDownloadReq.getTopMenuId())) {
			String[] arrayTopMenuId = bestDownloadReq.getTopMenuId().split("\\+");
			bestDownloadReq.setArrayTopMenuId(arrayTopMenuId);

			// 검색하는 상품 타입을 MM 으로 설정
			if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(arrayTopMenuId[0])
					|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(arrayTopMenuId[0])
					|| DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(arrayTopMenuId[0])
					|| DisplayConstants.DP_TV_TOP_MENU_ID.equals(arrayTopMenuId[0])) { // 멀티미디어 상품
				prodType = "MM";
			}
		}
		// '+'로 연결 된 상품등급코드를 배열로 전달
		if (StringUtils.isNotEmpty(bestDownloadReq.getProdGradeCd())) {
			String[] arrayProdGradeCd = bestDownloadReq.getProdGradeCd().split("\\+");
			bestDownloadReq.setArrayProdGradeCd(arrayProdGradeCd);
		}

		// 검색하는 상품 타입을 MM 으로 설정
		String menuIdSubString = StringUtils.substring(bestDownloadReq.getMenuId(), 0, 4);
		if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(menuIdSubString)
				|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(menuIdSubString)
				|| DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(menuIdSubString)
				|| DisplayConstants.DP_TV_TOP_MENU_ID.equals(menuIdSubString)) { // 멀티미디어 상품
			prodType = "MM";
		}

		// BEST 다운로드 상품 조회
		List<ProductBasicInfo> bestList = null;

		if (prodType.equals("MM")) { // 멀티미디어_상품
			bestList = this.commonDAO.queryForList("BestDownload.selectBestDownloadMMList", bestDownloadReq,
					ProductBasicInfo.class);

			if (!bestList.isEmpty()) {
				Map<String, Object> reqMap = new HashMap<String, Object>();
				reqMap.put("tenantHeader", tenantHeader);
				reqMap.put("deviceHeader", deviceHeader);
				reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
				for (ProductBasicInfo productBasicInfo : bestList) {
					reqMap.put("productBasicInfo", productBasicInfo);
					MetaInfo retMetaInfo = null;
					if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(bestDownloadReq.getTopMenuId())
							|| DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(menuIdSubString)
							|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(bestDownloadReq.getTopMenuId())
							|| DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(menuIdSubString)) {
						// 이북, 코믹
						reqMap.put("imageCd", DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
						retMetaInfo = this.metaInfoService.getEbookComicMetaInfo(reqMap);
					} else {
						// 영화, 방송
						reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
						retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap);
					}

					if (retMetaInfo != null) {
						if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(retMetaInfo.getTopMenuId())) {
							Product product = this.responseInfoGenerateFacade.generateEbookProduct(retMetaInfo);
							productList.add(product);
						} else if (DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(retMetaInfo.getTopMenuId())) {
							Product product = this.responseInfoGenerateFacade.generateComicProduct(retMetaInfo);
							productList.add(product);
						} else if (DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(retMetaInfo.getTopMenuId())) {
							Product product = this.responseInfoGenerateFacade.generateMovieProduct(retMetaInfo);
							productList.add(product);
						} else if (DisplayConstants.DP_TV_TOP_MENU_ID.equals(retMetaInfo.getTopMenuId())) {
							Product product = this.responseInfoGenerateFacade.generateBroadcastProduct(retMetaInfo);
							productList.add(product);
						}
					}
				}
				commonResponse.setTotalCount(bestList.get(0).getTotalCount());
				response.setProductList(productList);
				response.setCommonResponse(commonResponse);
			} else {
				// 조회 결과 없음
				commonResponse.setTotalCount(0);
				response.setProductList(productList);
				response.setCommonResponse(commonResponse);
			}

		} else { // App 상품
			bestList = this.commonDAO.queryForList("BestDownload.selectBestDownloadAppList", bestDownloadReq,
					ProductBasicInfo.class);

			if (!bestList.isEmpty()) {
				Map<String, Object> reqMap = new HashMap<String, Object>();
				reqMap.put("tenantHeader", tenantHeader);
				reqMap.put("deviceHeader", deviceHeader);
				reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
				for (ProductBasicInfo productBasicInfo : bestList) {
					reqMap.put("productBasicInfo", productBasicInfo);
					reqMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
					MetaInfo retMetaInfo = this.metaInfoService.getAppMetaInfo(reqMap);

					if (retMetaInfo != null) {
						Product product = this.responseInfoGenerateFacade.generateAppProduct(retMetaInfo);
						productList.add(product);
					}
				}
				commonResponse.setTotalCount(bestList.get(0).getTotalCount());
				response.setProductList(productList);
				response.setCommonResponse(commonResponse);
			} else {
				// 조회 결과 없음
				commonResponse.setTotalCount(0);
				response.setProductList(productList);
				response.setCommonResponse(commonResponse);
			}
		}

		response.setCommonResponse(commonResponse);
		response.setProductList(productList);

		return response;
	}

	public boolean recentStdDt(String listId, String stdDt) {
		Calendar calendar = Calendar.getInstance();
		String recentDate = "";
		if ("RNK000000006".equals(listId) || "RNK000000003".equals(listId)) { // 일간 BEST
			recentDate = new Integer(calendar.get(Calendar.YEAR)).toString()
					+ StringUtils.leftPad(new Integer(calendar.get(Calendar.MONTH) + 1).toString(), 2, '0')
					+ StringUtils.leftPad(new Integer(calendar.get(Calendar.DATE)).toString(), 2, '0'); // YYYYMMDD_형식의_날짜
		} else if ("RNK000000008".equals(listId) || "RNK000000005".equals(listId)) { // 주간 BEST
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); // 요일 set
			recentDate = new Integer(calendar.get(Calendar.YEAR)).toString()
					+ StringUtils.leftPad(new Integer(calendar.get(Calendar.MONTH) + 1).toString(), 2, '0')
					+ StringUtils.leftPad(new Integer(calendar.get(Calendar.DATE)).toString(), 2, '0'); // YYYYMMDD_형식의_날짜(일요일)
		} else if ("RNK000000007".equals(listId) || "RNK000000004".equals(listId)) { // 월간 BEST
			recentDate = new Integer(calendar.get(Calendar.YEAR)).toString()
					+ StringUtils.leftPad(new Integer(calendar.get(Calendar.MONTH) + 1).toString(), 2, '0') + "01"; // YYYYMM01_형식의_날짜
		}
		if (stdDt.equals(recentDate)) { // 집계 일자가 최신 일자이면
			return true;
		} else {
			return false;
		}
	}
}
