/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.best;

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.sac.client.product.vo.BestAppProto;

/**
 * 일반/특정 상품 카테고리 리스트 조회 Input Value Object.
 * 
 * Updated on : 2013. 11. 28. Updated by : 이태희, SK 플래닛.
 */
@ProtobufMapping(BestAppProto.reqBestApp.class)
public class BestDownloadRequestVO {

	private String listId; // 리스트 Id
	private String imageSizeCd; // 이미지 사이즈 코드
	private String menuId; // 메뉴 Id
	private String fiteredBy; // 카테고리 유형
	private String b2bprod; // B2B 상품구분
	private String hdv; // HDV 지원구분
	private String drm; // drm 지원구분
	private String prodGradeCd; // 상품등급코드
	private String offset; // 시작점 ROW
	private String count; // 페이지당 노출 ROW 수

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getListId() {
		return this.listId;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param listId
	 *            listId
	 */
	public void setListId(String listId) {
		this.listId = listId;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getImageSizeCd() {
		return this.imageSizeCd;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param imageSizeCd
	 *            imageSizeCd
	 */
	public void setImageSizeCd(String imageSizeCd) {
		this.imageSizeCd = imageSizeCd;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuId() {
		return this.menuId;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param menuId
	 *            menuId
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getFiteredBy() {
		return this.fiteredBy;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param fiteredBy
	 *            fiteredBy
	 */
	public void setFiteredBy(String fiteredBy) {
		this.fiteredBy = fiteredBy;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getB2bprod() {
		return this.b2bprod;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param String
	 *            b2bprod String b2bprod
	 */
	public void setB2bprod(String b2bprod) {
		this.b2bprod = b2bprod;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getHdv() {
		return this.hdv;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param String
	 *            hdv String hdv
	 */
	public void setHdv(String hdv) {
		this.hdv = hdv;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getDrm() {
		return this.drm;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param drm
	 *            drm
	 */
	public void setDrm(String drm) {
		this.drm = drm;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getProdGradeCd() {
		return this.prodGradeCd;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param prodGradeCd
	 *            prodGradeCd
	 */
	public void setProdGradeCd(String prodGradeCd) {
		this.prodGradeCd = prodGradeCd;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getOffset() {
		return this.offset;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param offset
	 *            offset
	 */
	public void setOffset(String offset) {
		this.offset = offset;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getCount() {
		return this.count;
	}

	/**
	 * 
	 * <pre>
	 * method 설명.
	 * </pre>
	 * 
	 * @param offset
	 *            offset
	 */
	public void setCount(String count) {
		this.count = count;
	}

}
