/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.card.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <p>
 * User Segment
 * </p>
 * Updated on : 2014. 11. 13 Updated by : 서대영, SK 플래닛.
 */
public class MemberSegment extends CommonInfo {

	private final static long serialVersionUID = 1L;

    private String outsdMbrLevelCd;
    private String insdMbrLevelCd;    private String sex;    private String ageClsfCd;    private String deviceChgYn;    private String newEntryYn;    private String mnoCd;    private String categoryBest;
    private String categoryPrefer;
    
	public String getOutsdMbrLevelCd() {
		return outsdMbrLevelCd;
	}
	public void setOutsdMbrLevelCd(String outsdMbrLevelCd) {
		this.outsdMbrLevelCd = outsdMbrLevelCd;
	}
	public String getInsdMbrLevelCd() {
		return insdMbrLevelCd;
	}
	public void setInsdMbrLevelCd(String insdMbrLevelCd) {
		this.insdMbrLevelCd = insdMbrLevelCd;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAgeClsfCd() {
		return ageClsfCd;
	}
	public void setAgeClsfCd(String ageClsfCd) {
		this.ageClsfCd = ageClsfCd;
	}
	public String getDeviceChgYn() {
		return deviceChgYn;
	}
	public void setDeviceChgYn(String deviceChgYn) {
		this.deviceChgYn = deviceChgYn;
	}
	public String getNewEntryYn() {
		return newEntryYn;
	}
	public void setNewEntryYn(String newEntryYn) {
		this.newEntryYn = newEntryYn;
	}
	public String getMnoCd() {
		return mnoCd;
	}
	public void setMnoCd(String mnoCd) {
		this.mnoCd = mnoCd;
	}
	public String getCategoryBest() {
		return categoryBest;
	}
	public void setCategoryBest(String categoryBest) {
		this.categoryBest = categoryBest;
	}
	public String getCategoryPrefer() {
		return categoryPrefer;
	}
	public void setCategoryPrefer(String categoryPrefer) {
		this.categoryPrefer = categoryPrefer;
	}
    
}