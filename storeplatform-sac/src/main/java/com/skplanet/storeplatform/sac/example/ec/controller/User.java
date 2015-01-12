package com.skplanet.storeplatform.sac.example.ec.controller;

import javax.xml.bind.annotation.XmlRootElement;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

@XmlRootElement
public class User extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private Integer age;

	private String address;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
