package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

@JsonSerialize(include = Inclusion.NON_NULL)
public class SecedeReson extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String secedeReasonCd;
	private String secedeReasonDes;

	public String getSecedeReasonCd() {
		return this.secedeReasonCd;
	}

	public void setSecedeReasonCd(String secedeReasonCd) {
		this.secedeReasonCd = secedeReasonCd;
	}

	public String getSecedeReasonDes() {
		return this.secedeReasonDes;
	}

	public void setSecedeReasonDes(String secedeReasonDes) {
		this.secedeReasonDes = secedeReasonDes;
	}

}
