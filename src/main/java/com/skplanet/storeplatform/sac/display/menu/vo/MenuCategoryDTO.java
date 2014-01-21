/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.menu.vo;

/**
 * 메뉴 조회 Default Value Object.
 * 
 * Updated on : 2013. 12. 19. Updated by : 윤주영, SK 플래닛.
 */
public class MenuCategoryDTO {

	private int totalCount;

	private String menuId;
	private String menuNm;
	private String infrMenuYn;
	private String menuDepth;
	private String filePos;
	private int fileSize;
	private String menuDesc;
	private int menuProdCnt;
	private int menuTotalProdCnt;

	/**
	 * 
	 * <pre>
	 * 전체건수.
	 * </pre>
	 * 
	 * @return int
	 */
	public int getTotalCount() {
		return this.totalCount;
	}

	/**
	 * 
	 * <pre>
	 * 전체건수.
	 * </pre>
	 * 
	 * @param int totalCount
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴별 전체 상품 건수.
	 * </pre>
	 * 
	 * @return int
	 */
	public int getMenuTotalProdCount() {
		return this.menuTotalProdCnt;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴별 전체 상품 건수.
	 * </pre>
	 * 
	 * @param int menuTotalProdCnt
	 */
	public void setMenuTotalProdCount(int menuTotalProdCnt) {
		this.menuTotalProdCnt = menuTotalProdCnt;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴 id.
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
	 * 메뉴 id.
	 * </pre>
	 * 
	 * @param String
	 *            menuId
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuNm() {
		return this.menuNm;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴명.
	 * </pre>
	 * 
	 * @param String
	 *            menuNm
	 */
	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	/**
	 * 
	 * <pre>
	 * 하위 메뉴 여부.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getInfrMenuYn() {
		return this.infrMenuYn;
	}

	/**
	 * 
	 * <pre>
	 * 하위 메뉴 여부.
	 * </pre>
	 * 
	 * @param String
	 *            infrMenuYn
	 */
	public void setInfrMenuYn(String infrMenuYn) {
		this.infrMenuYn = infrMenuYn;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴 깊이.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuDepth() {
		return this.menuDepth;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴 깊이.
	 * </pre>
	 * 
	 * @param String
	 *            menuDepth
	 */
	public void setMenuDepth(String menuDepth) {
		this.menuDepth = menuDepth;
	}

	/**
	 * 
	 * <pre>
	 * 파일 위치 정보.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getFilePos() {
		return this.filePos;
	}

	/**
	 * 
	 * <pre>
	 * 파일 위치 정보.
	 * </pre>
	 * 
	 * @param String
	 *            filePos
	 */
	public void setFilePos(String filePos) {
		this.filePos = filePos;
	}

	/**
	 * 
	 * <pre>
	 * 파일 크기.
	 * </pre>
	 * 
	 * @return int
	 */
	public int getFileSize() {
		return this.fileSize;
	}

	/**
	 * 
	 * <pre>
	 * 파일 크기.
	 * </pre>
	 * 
	 * @param int fileSize
	 */
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴 설명.
	 * </pre>
	 * 
	 * @return String
	 */
	public String getMenuDesc() {
		return this.menuDesc;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴 설명.
	 * </pre>
	 * 
	 * @param String
	 *            menuDesc
	 */
	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴별 상품수.
	 * </pre>
	 * 
	 * @return int
	 */
	public int getMenuProdCnt() {
		return this.menuProdCnt;
	}

	/**
	 * 
	 * <pre>
	 * 메뉴별 상품수.
	 * </pre>
	 * 
	 * @param int menuProdCnt
	 */
	public void setMenuProdCnt(int menuProdCnt) {
		this.menuProdCnt = menuProdCnt;
	}

}
