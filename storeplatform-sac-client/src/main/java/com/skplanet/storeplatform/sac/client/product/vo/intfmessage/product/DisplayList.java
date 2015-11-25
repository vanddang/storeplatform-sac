package com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonRawValue;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;


@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class DisplayList extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String listId;
	private String title;
	private List<Source> sourceList;
	
	@JsonRawValue
	private String etcProp;

	public String getListId() {
		return listId;
	}

	public void setListId(String listId) {
		this.listId = listId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Source> getSourceList() {
		return sourceList;
	}

	public void setSourceList(List<Source> sourceList) {
		this.sourceList = sourceList;
	}

	public String getEtcProp() {
		return etcProp;
	}

	public void setEtcProp(String etcProp) {
		this.etcProp = etcProp;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
