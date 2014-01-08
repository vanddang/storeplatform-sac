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

	public int getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuNm() {
		return this.menuNm;
	}

	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}

	public String getInfrMenuYn() {
		return this.infrMenuYn;
	}

	public void setInfrMenuYn(String infrMenuYn) {
		this.infrMenuYn = infrMenuYn;
	}

	public String getMenuDepth() {
		return this.menuDepth;
	}

	public void setMenuDepth(String menuDepth) {
		this.menuDepth = menuDepth;
	}

	public String getFilePos() {
		return this.filePos;
	}

	public void setFilePos(String filePos) {
		this.filePos = filePos;
	}

	public int getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public String getMenuDesc() {
		return this.menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public int getMenuProdCnt() {
		return this.menuProdCnt;
	}

	public void setMenuProdCnt(int menuProdCnt) {
		this.menuProdCnt = menuProdCnt;
	}

}
