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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Url;

/**
 * Interface Message Banner Value Object.
 * 
 * Updated on : 2014. 02. 21. Updated by : 이태희.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Banner extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Identifier> identifierList;
	private String type;
	private String imgSizeCd;
	private Title title;
	private List<Menu> menuList;
	private List<Source> sourceList;
	private Preview preview;
	private SalesOption salesOption;
	private Url url;

	/**
	 * @return the identifierList
	 */
	public List<Identifier> getIdentifierList() {
		return this.identifierList;
	}

	/**
	 * @param identifierList
	 *            the identifierList to set
	 */
	public void setIdentifierList(List<Identifier> identifierList) {
		this.identifierList = identifierList;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the imgSizeCd
	 */
	public String getImgSizeCd() {
		return this.imgSizeCd;
	}

	/**
	 * @param imgSizeCd
	 *            the imgSizeCd to set
	 */
	public void setImgSizeCd(String imgSizeCd) {
		this.imgSizeCd = imgSizeCd;
	}

	/**
	 * @return the title
	 */
	public Title getTitle() {
		return this.title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(Title title) {
		this.title = title;
	}

	/**
	 * @return the menuList
	 */
	public List<Menu> getMenuList() {
		return this.menuList;
	}

	/**
	 * @param menuList
	 *            the menuList to set
	 */
	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	/**
	 * @return the sourceList
	 */
	public List<Source> getSourceList() {
		return this.sourceList;
	}

	/**
	 * @param sourceList
	 *            the sourceList to set
	 */
	public void setSourceList(List<Source> sourceList) {
		this.sourceList = sourceList;
	}

	/**
	 * @return the preview
	 */
	public Preview getPreview() {
		return this.preview;
	}

	/**
	 * @param preview
	 *            the preview to set
	 */
	public void setPreview(Preview preview) {
		this.preview = preview;
	}

	/**
	 * @return the salesOption
	 */
	public SalesOption getSalesOption() {
		return this.salesOption;
	}

	/**
	 * @param salesOption
	 *            the salesOption to set
	 */
	public void setSalesOption(SalesOption salesOption) {
		this.salesOption = salesOption;
	}

	/**
	 * @return the url
	 */
	public Url getUrl() {
		return this.url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(Url url) {
		this.url = url;
	}
}
