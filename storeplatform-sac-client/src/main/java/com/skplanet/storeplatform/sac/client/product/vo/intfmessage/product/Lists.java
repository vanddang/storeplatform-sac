package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product;

import org.codehaus.jackson.annotate.JsonRawValue;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Lists {
	private String listId;
	private String listNm;
	private String title;
	private String startKey;
	private String hasNext; // Y/N
	private Integer count;
	private Date  date;
	@JsonRawValue
	private String etcProp;

	public String getListId() {
		return listId;
	}
	public void setListId(String listId) {
		this.listId = listId;
	}
	public String getListNm() {
		return listNm;
	}
	public void setListNm(String listNm) {
		this.listNm = listNm;
	}
	public String getStartKey() {
		return startKey;
	}
	public void setStartKey(String startKey) {
		this.startKey = startKey;
	}

	public String getHasNext() {
		return hasNext;
	}
	public void setHasNext(String hasNext) {
		this.hasNext = hasNext;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public String getEtcProp() {
		return etcProp;
	}

	public void setEtcProp(String etcProp) {
		this.etcProp = etcProp;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
