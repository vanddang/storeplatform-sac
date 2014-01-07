package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.Agreement;
import com.skplanet.storeplatform.sac.client.member.vo.common.RealNameInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.RightInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerInfo;

/**
 * 사용자 부가정보
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailInformationRes extends CommonInfo {

	private static final long serialVersionUID = 1L;
	SellerInfo sellerInfo;

	public SellerInfo getSellerInfo() {
		return this.sellerInfo;
	}

	public void setSellerInfo(SellerInfo sellerInfo) {
		this.sellerInfo = sellerInfo;
	}

	RealNameInfo realNameInfo;

	public RealNameInfo getRealNameInfo() {
		return this.realNameInfo;
	}

	public void setRealNameInfo(RealNameInfo realNameInfo) {
		this.realNameInfo = realNameInfo;
	}

	private String regDate;
	private String approveDate;
	private String pwRegDate;

	public String getRegDate() {
		return this.regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getApproveDate() {
		return this.approveDate;
	}

	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}

	public String getPwRegDate() {
		return this.pwRegDate;
	}

	public void setPwRegDate(String pwRegDate) {
		this.pwRegDate = pwRegDate;
	}

	List<RightInfo> rightInfoList;

	public List<RightInfo> getRightInfoList() {
		return this.rightInfoList;
	}

	public void setRightInfoList(List<RightInfo> rightInfoList) {
		this.rightInfoList = rightInfoList;
	}

	List<Agreement> agreementList;

	public List<Agreement> getAgreementList() {
		return this.agreementList;
	}

	public void setAgreementList(List<Agreement> agreementList) {
		this.agreementList = agreementList;
	}

	private String flurryInfo;

	public String getFlurryInfo() {
		return this.flurryInfo;
	}

	public void setFlurryInfo(String flurryInfo) {
		this.flurryInfo = flurryInfo;
	}

}
