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

import com.skplanet.storeplatform.framework.core.annotation.ProtobufMapping;
import com.skplanet.storeplatform.framework.core.common.vo.CommonVO;
import com.skplanet.storeplatform.sac.client.intfmessage.device.vo.DeviceProto;

/**
 * Interface Message Device Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross.
 */
@ProtobufMapping(DeviceProto.Device.class)
public class Device extends CommonVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Device내 사용된 Language 정보 ([LANG}에 정의된 language Tag값을 갖음
	 */
	private String lang;
	/**
	 * Device내 사용된 Base URI 정보
	 */
	private String base;
	/**
	 * 단말 식별자 - {제조사/모델명}test
	 */
	private String identifier;
	/**
	 * 단말 타입 > OMD단말 : type="omd" > 미지원단말 : identifier="android_standard" type="restrict"
	 */
	private String type;

	/**
	 * 미지원단말은 아래 정의 안함
	 */
	private String manufacturer; // 단말 제조사 정보
	/**
	 * 단말 모델명
	 */
	private String model;
	/**
	 * 단말 OS 플랫폼
	 */
	private String platform;
	/**
	 * 단말 모델 설명
	 */
	private String modelExplain;

	/**
	 * 지원가능한 SK Planet 서비스 정보
	 */
	private List<Service> serviceList;

	/**
	 * 단말에서 지원가능한 하드웨어 정보
	 */
	private SupportedHardware supportedHardware;

	/**
	 * Binding Information
	 */
	private BindingInfo bindingInfo;

	/**
	 * 휴대폰번호
	 */
	private String msisdn;

	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getBase() {
		return this.base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getPlatform() {
		return this.platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getModelExplain() {
		return this.modelExplain;
	}

	public void setModelExplain(String modelExplain) {
		this.modelExplain = modelExplain;
	}

	public List<Service> getServiceList() {
		return this.serviceList;
	}

	public void setServiceList(List<Service> serviceList) {
		this.serviceList = serviceList;
	}

	public SupportedHardware getSupportedHardware() {
		return this.supportedHardware;
	}

	public void setSupportedHardware(SupportedHardware supportedHardware) {
		this.supportedHardware = supportedHardware;
	}

	public BindingInfo getBindingInfo() {
		return this.bindingInfo;
	}

	public void setBindingInfo(BindingInfo bindingInfo) {
		this.bindingInfo = bindingInfo;
	}

	public String getMsisdn() {
		return this.msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

}
