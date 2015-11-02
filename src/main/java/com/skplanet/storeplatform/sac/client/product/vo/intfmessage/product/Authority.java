/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Interface Message Accrual Value Object.
 * 
 * Updated on : 2015. 10. 15. Updated by : 이석희, I-S PLUS.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Authority extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String allow; // 이용권한 (freepass : 자유이용권 지원, domestic : 국내만 지원, feedback : 사용후기 가능)
	private String grade; // 이용등급 (0 : 전체이용가, 1 : 12세 이용가, 2 : 15세 이용가, 4 : 청소년사용불가)
	private Preview preview; // 미리보기 정보를 정의
	private List<Store> storeList = null; // 소장상품 List
	private List<Play> playList = null; // 대여상품 List
	private Store store; // 소장 상품 정보
	private Play play; // 대여 상품 정보
	private String plus19Yn; // 19+ 상품 Yn
	private String baseYn; // 기준 상품 여부(에피소드 상품)
	
	/**
	 * @return the allow
	 */
	public String getAllow() {
		return allow;
	}
	/**
	 * @param allow the allow to set
	 */
	public void setAllow(String allow) {
		this.allow = allow;
	}
	/**
	 * @return the grade
	 */
	public String getGrade() {
		return grade;
	}
	/**
	 * @param grade the grade to set
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}
	/**
	 * @return the preview
	 */
	public Preview getPreview() {
		return preview;
	}
	/**
	 * @param preview the preview to set
	 */
	public void setPreview(Preview preview) {
		this.preview = preview;
	}
	/**
	 * @return the storeList
	 */
	public List<Store> getStoreList() {
		return storeList;
	}
	/**
	 * @param storeList the storeList to set
	 */
	public void setStoreList(List<Store> storeList) {
		this.storeList = storeList;
	}
	/**
	 * @return the playList
	 */
	public List<Play> getPlayList() {
		return playList;
	}
	/**
	 * @param playList the playList to set
	 */
	public void setPlayList(List<Play> playList) {
		this.playList = playList;
	}
	/**
	 * @return the store
	 */
	public Store getStore() {
		return store;
	}
	/**
	 * @param store the store to set
	 */
	public void setStore(Store store) {
		this.store = store;
	}
	/**
	 * @return the play
	 */
	public Play getPlay() {
		return play;
	}
	/**
	 * @param play the play to set
	 */
	public void setPlay(Play play) {
		this.play = play;
	}
	/**
	 * @return the plus19Yn
	 */
	public String getPlus19Yn() {
		return plus19Yn;
	}
	/**
	 * @param plus19Yn the plus19Yn to set
	 */
	public void setPlus19Yn(String plus19Yn) {
		this.plus19Yn = plus19Yn;
	}

	/**
	 * @return the baseYn
	 */
	public String getBaseYn() {
		return baseYn;
	}

	/**
	 * @param baseYn the baseYn to set
	 */
	public void setBaseYn(String baseYn) {
		this.baseYn = baseYn;
	}
}
