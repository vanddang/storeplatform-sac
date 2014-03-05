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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;

/**
 * Interface Message Layout Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 이태희, SK 플래닛.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Layout extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name; // 베너이름
	private List<Banner> bannerList; // 베너리스트
	private Title title; // 베너제목
	private Source source; // 베너 resource 정보
	private Menu menu; // 메뉴 정보

	/* 웹툰 회차별 정보에 채널 정보노출 필요로 추가 */
	/**
	 * 상품 ID.
	 */
	private Identifier identifier;

	private List<Identifier> identifierList;
	/**
	 * 상품설명.
	 */
	private String productExplain;
	/**
	 * Accrual.
	 */
	private Accrual accrual;
	/**
	 * 제작자 정보.
	 */
	private Contributor contributor;

	/**
	 * resource 정보.
	 */
	private List<Source> sourceList;

	/**
	 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return List<Banner>
	 */
	public List<Banner> getBannerList() {
		return this.bannerList;
	}

	/**
	 * @param bannerList
	 *            bannerList
	 */
	public void setBannerList(List<Banner> bannerList) {
		this.bannerList = bannerList;
	}

	/**
	 * @return Title
	 */
	public Title getTitle() {
		return this.title;
	}

	/**
	 * @param title
	 *            title
	 */
	public void setTitle(Title title) {
		this.title = title;
	}

	/**
	 * @return Source
	 */
	public Source getSource() {
		return this.source;
	}

	/**
	 * @param source
	 *            source
	 */
	public void setSource(Source source) {
		this.source = source;
	}

	/**
	 * @return Menu
	 */
	public Menu getMenu() {
		return this.menu;
	}

	/**
	 * @param menu
	 *            menu
	 */
	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Identifier getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}

	public List<Identifier> getIdentifierList() {
		return this.identifierList;
	}

	public void setIdentifierList(List<Identifier> identifierList) {
		this.identifierList = identifierList;
	}

	public String getProductExplain() {
		return this.productExplain;
	}

	public void setProductExplain(String productExplain) {
		this.productExplain = productExplain;
	}

	public Accrual getAccrual() {
		return this.accrual;
	}

	public void setAccrual(Accrual accrual) {
		this.accrual = accrual;
	}

	public Contributor getContributor() {
		return this.contributor;
	}

	public void setContributor(Contributor contributor) {
		this.contributor = contributor;
	}

	public List<Source> getSourceList() {
		return this.sourceList;
	}

	public void setSourceList(List<Source> sourceList) {
		this.sourceList = sourceList;
	}

}
