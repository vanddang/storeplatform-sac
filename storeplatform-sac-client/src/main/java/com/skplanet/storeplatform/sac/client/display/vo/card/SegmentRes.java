/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.display.vo.card;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * <p>
 * SegmentRes
 * </p>
 * Updated on : 2014. 10. 30 Updated by : 서대영, SK 플래닛.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class SegmentRes extends CommonInfo {

    private static final long serialVersionUID = 1L;

    private String outsdMbrGrdCd;

    private String insdMbrGrdCd;

    private String sex;

    private String ageClsfCd;

    private String deviceChgYn;

    private String newEntryYn;

    private List<String> categoryBest;

    private String tingYn;
    private String testMdnYn;

    public String getOutsdMbrGrdCd() {
        return outsdMbrGrdCd;
    }

    public void setOutsdMbrGrdCd(String outsdMbrGrdCd) {
        this.outsdMbrGrdCd = outsdMbrGrdCd;
    }

    public String getInsdMbrGrdCd() {
        return insdMbrGrdCd;
    }

    public void setInsdMbrGrdCd(String insdMbrGrdCd) {
        this.insdMbrGrdCd = insdMbrGrdCd;
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

    public List<String> getCategoryBest() {
        return categoryBest;
    }

    public void setCategoryBest(List<String> categoryBest) {
        this.categoryBest = categoryBest;
    }

	/**
	 * TING 요금제 대상여부
	 *
	 * @return Y/N
	 */
	public String getTingYn() {
	    return tingYn;
    }

	/**
	 * TING 요금제 대상여부를 세팅한다.
	 *
	 * @param tingYn Y/N
	 */
	public void setTingYn( String tingYn ) {
	    this.tingYn = tingYn;
    }

	/**
	 * 카드 테스트 MDN 유저 여부
	 *
	 * @return Y/N
	 */
	public String getTestMdnYn() {
	    return testMdnYn;
    }

	/**
	 * 카드 테스트 MDN 유저 여부를 세팅한다.
	 *
	 * @param testMdnYn Y/N
	 */
	public void setTestMdnYn( String testMdnYn ) {
	    this.testMdnYn = testMdnYn;
    }
}
