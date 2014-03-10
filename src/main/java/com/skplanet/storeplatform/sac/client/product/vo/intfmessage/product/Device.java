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
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;

/**
 * Interface Message Device Value Object.
 * 
 * Updated on : 2013. 12. 17. Updated by : 오승민, Incross..
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Device extends CommonInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 단말 식별자 - {제조사/모델명}test.
	 */
	private String identifier;
	/**
	 * 단말 타입 > OMD단말 : type="omd" > 미지원단말 : identifier="android_standard" type="restrict".
	 */
	private String type;

	/**
	 * 미지원단말은 아래 정의 안함.
	 */
	private String manufacturer; // 단말 제조사 정보
	/**
	 * 단말 모델명.
	 */
	private String model;

	/**
	 * UA CD.
	 */
	private String uaCd;
	/**
	 * 단말 OS 플랫폼.
	 */
	private String platform;
	/**
	 * 단말 모델 설명.
	 */
	private String modelExplain;

	/**
	 * 폰 타입.
	 */
	private String phoneType;

	/**
	 * 지원가능한 SK Planet 서비스 정보.
	 */
	private List<Service> serviceList;

	/**
	 * 단말에서 지원가능한 하드웨어 정보.
	 */
	// private SupportedHardware supportedHardware;
	private Map<String, Object> supportedHardware;

	/**
	 * Binding Information.
	 */
	private BindingInfo bindingInfo;

	/**
	 * 휴대폰번호.
	 */
	private String msisdn;

	private Map<String, Object> services;

	/********************************************
	 * 이용 단말 조회 영역
	 ********************************************/

	/**
	 * 상품 ID.
	 */
	private List<Identifier> identifierList;

	/**
	 * Service Group Code.
	 */
	private String svcGrpCd;

	/**
	 * 단말 모델코드.
	 */
	private String deviceModelCd;

	/**
	 * 단말 모델명.
	 */
	private String deviceModelNm;

	/**
	 * 제조사 코드.
	 */
	private String mnftCompCd;

	/**
	 * 제조사 명.
	 */
	private String mnftCompNm;

	/**
	 * 리스트 노출 이미지 경로.
	 */
	private String listImagePath;

	/**
	 * 상세 노출 이미지 경로.
	 */
	private String dtlImagePath;

	/**
	 * @return String
	 */
	public String getIdentifier() {
		return this.identifier;
	}

	/**
	 * @param identifier
	 *            identifier
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return String
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param type
	 *            type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return String
	 */
	public String getManufacturer() {
		return this.manufacturer;
	}

	/**
	 * @param manufacturer
	 *            manufacturer
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * @return String
	 */
	public String getModel() {
		return this.model;
	}

	/**
	 * @param model
	 *            model
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return String
	 */
	public String getUaCd() {
		return this.uaCd;
	}

	/**
	 * @param uaCd
	 *            uaCd
	 */
	public void setUaCd(String uaCd) {
		this.uaCd = uaCd;
	}

	/**
	 * @return String
	 */
	public String getPlatform() {
		return this.platform;
	}

	/**
	 * @param platform
	 *            platform
	 */
	public void setPlatform(String platform) {
		this.platform = platform;
	}

	/**
	 * @return String
	 */
	public String getModelExplain() {
		return this.modelExplain;
	}

	/**
	 * @param modelExplain
	 *            modelExplain
	 */
	public void setModelExplain(String modelExplain) {
		this.modelExplain = modelExplain;
	}

	/**
	 * @return String
	 */
	public String getPhoneType() {
		return this.phoneType;
	}

	/**
	 * @param phoneType
	 *            phoneType
	 */
	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	/**
	 * @return List<Service>
	 */
	public List<Service> getServiceList() {
		return this.serviceList;
	}

	/**
	 * @param serviceList
	 *            serviceList
	 */
	public void setServiceList(List<Service> serviceList) {
		this.serviceList = serviceList;
	}

	/**
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getSupportedHardware() {
		return this.supportedHardware;
	}

	/**
	 * @param supportedHardware
	 *            supportedHardware
	 */
	public void setSupportedHardware(Map<String, Object> supportedHardware) {
		this.supportedHardware = supportedHardware;
	}

	/**
	 * @return BindingInfo
	 */
	public BindingInfo getBindingInfo() {
		return this.bindingInfo;
	}

	/**
	 * @param bindingInfo
	 *            bindingInfo
	 */
	public void setBindingInfo(BindingInfo bindingInfo) {
		this.bindingInfo = bindingInfo;
	}

	/**
	 * @return String
	 */
	public String getMsisdn() {
		return this.msisdn;
	}

	/**
	 * @param msisdn
	 *            msisdn
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	/**
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getServices() {
		return this.services;
	}

	/**
	 * @param services
	 *            services
	 */
	public void setServices(Map<String, Object> services) {
		this.services = services;
	}

	/********************************************
	 * 이용 단말 조회 영역
	 ********************************************/

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
	 * @return the svcGrpCd
	 */
	public String getSvcGrpCd() {
		return this.svcGrpCd;
	}

	/**
	 * @param svcGrpCd
	 *            the svcGrpCd to set
	 */
	public void setSvcGrpCd(String svcGrpCd) {
		this.svcGrpCd = svcGrpCd;
	}

	/**
	 * @return the deviceModelCd
	 */
	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}

	/**
	 * @param deviceModelCd
	 *            the deviceModelCd to set
	 */
	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

	/**
	 * @return the deviceModelNm
	 */
	public String getDeviceModelNm() {
		return this.deviceModelNm;
	}

	/**
	 * @param deviceModelNm
	 *            the deviceModelNm to set
	 */
	public void setDeviceModelNm(String deviceModelNm) {
		this.deviceModelNm = deviceModelNm;
	}

	/**
	 * @return the mnftCompCd
	 */
	public String getMnftCompCd() {
		return this.mnftCompCd;
	}

	/**
	 * @param mnftCompCd
	 *            the mnftCompCd to set
	 */
	public void setMnftCompCd(String mnftCompCd) {
		this.mnftCompCd = mnftCompCd;
	}

	/**
	 * @return the mnftCompNm
	 */
	public String getMnftCompNm() {
		return this.mnftCompNm;
	}

	/**
	 * @param mnftCompNm
	 *            the mnftCompNm to set
	 */
	public void setMnftCompNm(String mnftCompNm) {
		this.mnftCompNm = mnftCompNm;
	}

	/**
	 * @return the listImagePath
	 */
	public String getListImagePath() {
		return this.listImagePath;
	}

	/**
	 * @param listImagePath
	 *            the listImagePath to set
	 */
	public void setListImagePath(String listImagePath) {
		this.listImagePath = listImagePath;
	}

	/**
	 * @return the dtlImagePath
	 */
	public String getDtlImagePath() {
		return this.dtlImagePath;
	}

	/**
	 * @param dtlImagePath
	 *            the dtlImagePath to set
	 */
	public void setDtlImagePath(String dtlImagePath) {
		this.dtlImagePath = dtlImagePath;
	}

}
