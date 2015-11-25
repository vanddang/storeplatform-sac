package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Url;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class IntimateMessage extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Identifier> identifierList;
	private Title title;
	private Title subTitle;
	private Url url;
	private String themeRecommId;
	private String appCodi;
	private String purchaseHistory;
	private List<Source> sourceList;

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
	 * @return the subTitle
	 */
	public Title getSubTitle() {
		return this.subTitle;
	}

	/**
	 * @param subTitle
	 *            the subTitle to set
	 */
	public void setSubTitle(Title subTitle) {
		this.subTitle = subTitle;
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

	/**
	 * @return the themeRecommId
	 */
	public String getThemeRecommId() {
		return this.themeRecommId;
	}

	/**
	 * @param themeRecommId
	 *            the themeRecommId to set
	 */
	public void setThemeRecommId(String themeRecommId) {
		this.themeRecommId = themeRecommId;
	}

	/**
	 * @return the appCodi
	 */
	public String getAppCodi() {
		return this.appCodi;
	}

	/**
	 * @param appCodi
	 *            the appCodi to set
	 */
	public void setAppCodi(String appCodi) {
		this.appCodi = appCodi;
	}

	/**
	 * @return the purchaseHistory
	 */
	public String getPurchaseHistory() {
		return this.purchaseHistory;
	}

	/**
	 * @param purchaseHistory
	 *            the purchaseHistory to set
	 */
	public void setPurchaseHistory(String purchaseHistory) {
		this.purchaseHistory = purchaseHistory;
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
}
