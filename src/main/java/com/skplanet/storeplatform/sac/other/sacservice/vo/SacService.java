package com.skplanet.storeplatform.sac.other.sacservice.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class SacService extends CommonInfo {

	private static final long serialVersionUID = 1L;
	
	private String serviceCd;
	
	private String simOperator;
	private String model;
	
	private SacServiceStatus status;
	private SacServiceAuthType simOperatorAuthType;
	private SacServiceAuthType modelAuthType;
	
	private boolean active;
	private boolean applied;
	
	private String name;
	private String description;
	
	private String[] simOperators;
	private String[] models;
	
	public SacService() {
	}
	
	public SacService(String serviceCd) {
		this.serviceCd = serviceCd;
	}
	
	public String getServiceCd() {
		return serviceCd;
	}
	public void setServiceCd(String serviceCd) {
		this.serviceCd = serviceCd;
	}
	public SacServiceStatus getStatus() {
		return status;
	}
	public void setStatus(SacServiceStatus status) {
		this.status = status;
	}
	public SacServiceAuthType getSimOperatorAuthType() {
		return simOperatorAuthType;
	}
	public void setSimOperatorAuthType(SacServiceAuthType simOperatorAuthType) {
		this.simOperatorAuthType = simOperatorAuthType;
	}
	public SacServiceAuthType getModelAuthType() {
		return modelAuthType;
	}
	public void setModelAuthType(SacServiceAuthType modelAuthType) {
		this.modelAuthType = modelAuthType;
	}
	public Boolean isApplied() {
		return applied;
	}
	public void setApplied(Boolean applied) {
		this.applied = applied;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String[] getSimOperators() {
		return simOperators;
	}
	public void setSimOperators(String[] simOperators) {
		this.simOperators = simOperators;
	}
	public String[] getModels() {
		return models;
	}
	public void setModels(String[] models) {
		this.models = models;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getSimOperator() {
		return simOperator;
	}

	public void setSimOperator(String simOperator) {
		this.simOperator = simOperator;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

}
