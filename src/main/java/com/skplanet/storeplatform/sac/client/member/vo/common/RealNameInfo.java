package com.skplanet.storeplatform.sac.client.member.vo.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 사용자 부가정보
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class RealNameInfo extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String isParent;
	private String parentDate;
	private String parentType;
	private String parentName;
	private String parentBirthDay;
	private String parentEmail;
	private String parentMDN;
	private String parentTelecom;
	private String parentRealNameDate;
	private String parentCI;
	private String parentRealNameMethod;
	private String parentRealNameSystemId;
	private String isRealName;
	private String realNameDate;
	private String sellerCI;
	private String sellerDI;
	private String realNameMethod;
	private String realNameSystemId;

	public String getIsParent() {
		return this.isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public String getParentDate() {
		return this.parentDate;
	}

	public void setParentDate(String parentDate) {
		this.parentDate = parentDate;
	}

	public String getParentType() {
		return this.parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentBirthDay() {
		return this.parentBirthDay;
	}

	public void setParentBirthDay(String parentBirthDay) {
		this.parentBirthDay = parentBirthDay;
	}

	public String getParentEmail() {
		return this.parentEmail;
	}

	public void setParentEmail(String parentEmail) {
		this.parentEmail = parentEmail;
	}

	public String getParentMDN() {
		return this.parentMDN;
	}

	public void setParentMDN(String parentMDN) {
		this.parentMDN = parentMDN;
	}

	public String getParentTelecom() {
		return this.parentTelecom;
	}

	public void setParentTelecom(String parentTelecom) {
		this.parentTelecom = parentTelecom;
	}

	public String getParentRealNameDate() {
		return this.parentRealNameDate;
	}

	public void setParentRealNameDate(String parentRealNameDate) {
		this.parentRealNameDate = parentRealNameDate;
	}

	public String getParentCI() {
		return this.parentCI;
	}

	public void setParentCI(String parentCI) {
		this.parentCI = parentCI;
	}

	public String getParentRealNameMethod() {
		return this.parentRealNameMethod;
	}

	public void setParentRealNameMethod(String parentRealNameMethod) {
		this.parentRealNameMethod = parentRealNameMethod;
	}

	public String getParentRealNameSystemId() {
		return this.parentRealNameSystemId;
	}

	public void setParentRealNameSystemId(String parentRealNameSystemId) {
		this.parentRealNameSystemId = parentRealNameSystemId;
	}

	public String getIsRealName() {
		return this.isRealName;
	}

	public void setIsRealName(String isRealName) {
		this.isRealName = isRealName;
	}

	public String getRealNameDate() {
		return this.realNameDate;
	}

	public void setRealNameDate(String realNameDate) {
		this.realNameDate = realNameDate;
	}

	public String getSellerCI() {
		return this.sellerCI;
	}

	public void setSellerCI(String sellerCI) {
		this.sellerCI = sellerCI;
	}

	public String getSellerDI() {
		return this.sellerDI;
	}

	public void setSellerDI(String sellerDI) {
		this.sellerDI = sellerDI;
	}

	public String getRealNameMethod() {
		return this.realNameMethod;
	}

	public void setRealNameMethod(String realNameMethod) {
		this.realNameMethod = realNameMethod;
	}

	public String getRealNameSystemId() {
		return this.realNameSystemId;
	}

	public void setRealNameSystemId(String realNameSystemId) {
		this.realNameSystemId = realNameSystemId;
	}

}
