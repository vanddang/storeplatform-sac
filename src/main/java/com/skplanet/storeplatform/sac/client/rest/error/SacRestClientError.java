package com.skplanet.storeplatform.sac.client.rest.error;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlRootElement
public class SacRestClientError extends CommonInfo {
	private static final long serialVersionUID = 1L;
	private String code;

	@JsonIgnore
	private Object[] args;
	private String message;
	private String hostName;
	private String instanceName;
	private SacRestClientError cause;

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public SacRestClientError getCause() {
		return this.cause;
	}

	public void setCause(SacRestClientError cause) {
		this.cause = cause;
	}

	public Object[] getArgs() {
		return this.args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public String getHostName() {
		return this.hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getInstanceName() {
		return this.instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

}
