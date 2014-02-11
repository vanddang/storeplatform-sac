/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.freepass.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skplanet.storeplatform.sac.client.display.vo.freepass.FreepassDetailRes;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.FreepassListReq;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.FreepassListRes;
import com.skplanet.storeplatform.sac.client.display.vo.freepass.SeriespassListRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Accrual;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.AutoPay;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Coupon;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * Freepass Service (CoreStoreBusiness)
 * 
 * Updated on : 2014. 2. 7. Updated by : 서영배, GTSOFT.
 */
@Service
@Transactional
public class FreepassServiceImpl implements FreepassService {

	@Autowired
	private MetaInfoService metaInfoService;
	
	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;
	
	/* (non-Javadoc)
	 * @see com.skplanet.storeplatform.sac.display.freepass.service.FreepassService#searchFreepassList(com.skplanet.storeplatform.sac.client.display.vo.freepass.FreepassListReq, com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public FreepassListRes searchFreepassList(FreepassListReq req,
			SacRequestHeader header) {
		// TODO Auto-generated method stub
		
		// 공통 응답 변수 선언
		int totalCount = 0;
		FreepassListRes responseVO = null;
		CommonResponse commonResponse = null;

		List<Coupon> couponList = new ArrayList<Coupon>();
		
		couponList = getDummyCoupon();
		
		responseVO = new FreepassListRes();
		commonResponse = new CommonResponse();
		commonResponse.setTotalCount(couponList.size());

		responseVO.setCommonResponse(commonResponse);
		responseVO.setCouponList(couponList);
		return responseVO;
	}

	/* (non-Javadoc)
	 * @see com.skplanet.storeplatform.sac.display.freepass.service.FreepassService#searchFreepassDetail(com.skplanet.storeplatform.sac.client.display.vo.freepass.FreepassListReq, com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public FreepassDetailRes searchFreepassDetail(FreepassListReq req,
			SacRequestHeader header) {
		// TODO Auto-generated method stub
		
		FreepassDetailRes freepassDetailRes;
		freepassDetailRes = new FreepassDetailRes();
		
		//더미용 데이터 제공
		List<Product> productList = new ArrayList<Product>();
		Map<String, Object> reqMap = new HashMap<String, Object>();
		ProductBasicInfo productBasicInfo = new ProductBasicInfo();
		Product product;
		Coupon coupon;
		CommonResponse commonResponse;
		
		reqMap.put("tenantHeader", header.getTenantHeader());
		reqMap.put("deviceHeader", header.getDeviceHeader());
		reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
		reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
		String[] prodIdList = {"H000043398","H000043398","H000043398"};
		MetaInfo retMetaInfo = null;
		
		for(int i = 0 ; i < prodIdList.length ; i++) {
			productBasicInfo.setProdId(prodIdList[i]);
			productBasicInfo.setTenantId("S01");
			productBasicInfo.setContentsTypeCd("PD002501");
			reqMap.put("productBasicInfo", productBasicInfo);
			retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap);
			product = this.responseInfoGenerateFacade.generateBroadcastProduct(retMetaInfo);
			productList.add(product);
		}
		
		List<Coupon> couponList = new ArrayList<Coupon>();
		couponList = getDummyCoupon();
		coupon = (Coupon)couponList.get(0);
		
		commonResponse = new CommonResponse();
		commonResponse.setTotalCount(productList.size());
	
		freepassDetailRes.setCommonResponse(commonResponse);
		freepassDetailRes.setCoupon(coupon);
		freepassDetailRes.setProductList(productList);
		
		return freepassDetailRes;
	}

	/* (non-Javadoc)
	 * @see com.skplanet.storeplatform.sac.display.freepass.service.FreepassService#searchSeriesPassList(com.skplanet.storeplatform.sac.client.display.vo.freepass.FreepassListReq, com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public SeriespassListRes searchSeriesPassList(FreepassListReq req,
			SacRequestHeader header) {
		// TODO Auto-generated method stub
		
		SeriespassListRes seriespassListRes;
		seriespassListRes = new SeriespassListRes();
		
		//더미용 데이터 제공
		List<Product> productList = new ArrayList<Product>();
		Map<String, Object> reqMap = new HashMap<String, Object>();
		ProductBasicInfo productBasicInfo = new ProductBasicInfo();
		Product product;
		Coupon coupon;
		CommonResponse commonResponse;
		
		reqMap.put("tenantHeader", header.getTenantHeader());
		reqMap.put("deviceHeader", header.getDeviceHeader());
		reqMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
		reqMap.put("imageCd", DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
		String[] prodIdList = {"H000043398","H000043398","H000043398"};
		MetaInfo retMetaInfo = null;
		
		List<Coupon> couponList = new ArrayList<Coupon>();
		couponList = getDummyCoupon();
		coupon = (Coupon)couponList.get(0);
		
		for(int i = 0 ; i < prodIdList.length ; i++) {
			productBasicInfo.setProdId(prodIdList[i]);
			productBasicInfo.setTenantId("S01");
			productBasicInfo.setContentsTypeCd("PD002501");
			reqMap.put("productBasicInfo", productBasicInfo);
			retMetaInfo = this.metaInfoService.getVODMetaInfo(reqMap);
			product = this.responseInfoGenerateFacade.generateBroadcastProduct(retMetaInfo);
			product.setCoupon(coupon);
			productList.add(product);
		}

		commonResponse = new CommonResponse();
		commonResponse.setTotalCount(productList.size());
	
		seriespassListRes.setCommonResponse(commonResponse);
		seriespassListRes.setProductList(productList);
		
		return seriespassListRes;
		
	}

	/* (non-Javadoc)
	 * @see com.skplanet.storeplatform.sac.display.freepass.service.FreepassService#searchFreepassListByChannel(com.skplanet.storeplatform.sac.client.display.vo.freepass.FreepassListReq, com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public FreepassListRes searchFreepassListByChannel(FreepassListReq req,
			SacRequestHeader header) {
		// TODO Auto-generated method stub
		// 공통 응답 변수 선언
		int totalCount = 0;
		FreepassListRes responseVO = null;
		CommonResponse commonResponse = null;

		List<Coupon> couponList = new ArrayList<Coupon>();
				
		couponList = getDummyCoupon();
				
		responseVO = new FreepassListRes();
		commonResponse = new CommonResponse();
		commonResponse.setTotalCount(couponList.size());

		responseVO.setCommonResponse(commonResponse);
		responseVO.setCouponList(couponList);
		return responseVO;
	}
	
	public List<Coupon> getDummyCoupon() {
		// TODO Auto-generated method stub
		Identifier identifier;
		Title title;
		Accrual accrual;
		Rights rights;
		Source source;
		Price price;
		Support support;
		Menu menu;
		Coupon coupon;
		AutoPay autoPay;
		Date date;

		// Response VO를 만들기위한 생성자
		// List<Product> productList = new ArrayList<Product>();
		List<Menu> menuList;
		List<Source> sourceList;
		List<Support> supportList;
		List<Identifier> identifierList;
		List<Coupon> couponList = new ArrayList<Coupon>();
		
		for (int i = 0; i < 3; i++) {

			identifier = new Identifier();
			title = new Title();
			accrual = new Accrual();
			rights = new Rights();
			source = new Source();
			price = new Price();
			support = new Support();
			coupon = new Coupon();
			autoPay = new AutoPay();
			date = new Date();

			// 상품ID
			identifier = new Identifier();

			// Response VO를 만들기위한 생성자
			menuList = new ArrayList<Menu>();
			sourceList = new ArrayList<Source>();
			supportList = new ArrayList<Support>();
			identifierList = new ArrayList<Identifier>();

			identifier.setType("freepass");
			identifier.setText("FR9010117" + i);
			identifierList.add(identifier);
			
			coupon.setKind("OR004301");
			coupon.setCouponExplain("특선영화 30일 자유이용권");

			price.setText(6500);
			
			autoPay.setType("auto");
		
			date.setType("duration/usagePeriod");
			date.setText("2013-10-06 14:22:51/2015-02-06 14:22:51");
			
			title.setText("특선영화 30일 자유이용권");
			title.setAlias("특선영화 30일 자유이용권");
			
			source.setType("banner");
			source.setUrl("/data4/img/original/0000258524_DP000101.png");
			sourceList.add(source);
			
			source = new Source();
			source.setType("thumnail");
			source.setUrl("/data4/img/original/0000258524_DP000101.png");
			sourceList.add(source);
			
			coupon.setAutopay(autoPay);
			coupon.setDate(date);
			coupon.setPrice(price);
			coupon.setSourceList(sourceList);
			coupon.setTitle(title);
			coupon.setIdentifierList(identifierList);

			couponList.add(coupon);
		}
		return couponList;
	}

}
