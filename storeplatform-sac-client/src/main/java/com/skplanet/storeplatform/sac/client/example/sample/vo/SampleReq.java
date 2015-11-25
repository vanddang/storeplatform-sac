package com.skplanet.storeplatform.sac.client.example.sample.vo;

import java.util.Date;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class SampleReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private Integer no;
	private String id;
	private String name;
	private String description;
	private Date date;

	public SampleReq() {
	}

	public SampleReq(Integer no) {
		this.no = no;
	}

	public Integer getNo() {
		return this.no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return this.date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

}
