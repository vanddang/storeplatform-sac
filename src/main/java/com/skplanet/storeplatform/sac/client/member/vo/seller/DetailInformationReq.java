package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 판매자회원 기본 정보 조회
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailInformationReq extends CommonInfo {

	private static final long serialVersionUID = 1L;

	// List<KeySearch> keySearchList;
	//
	// public List<KeySearch> getKeySearchList() {
	// return this.keySearchList;
	// }
	//
	// public void setKeySearchList(List<KeySearch> keySearchList) {
	// this.keySearchList = keySearchList;
	// }

	/** 판매자 key */
	private String sellerKey;
	/** App ID */
	private String aid;
	/** 조회필드명 */
	private String keyType;

	public String getKeyType() {
		return this.keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public String getSellerKey() {
		return this.sellerKey;
	}

	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

	public String getAid() {
		return this.aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}
}
