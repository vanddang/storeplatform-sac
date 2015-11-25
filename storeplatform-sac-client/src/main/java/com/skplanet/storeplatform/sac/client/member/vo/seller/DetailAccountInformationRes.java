package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.Document;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerAccount;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerBpSac;

/**
 * 판매자회원 정산 정보 조회
 * 
 * Updated on : 2014. 3. 20. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailAccountInformationRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 판매자 정산정보. */
	private SellerAccount sellerAccount;

	/** BP 정보. (TB_US_SELLERMBR_BP) */
	private SellerBpSac sellerBp;

	/** 판매자 문서정보 목록. */
	private List<Document> documentList;

	/**
	 * @return the documentList
	 */
	public List<Document> getDocumentList() {
		return this.documentList;
	}

	/**
	 * @param documentList
	 *            the documentList to set
	 */
	public void setDocumentList(List<Document> documentList) {
		this.documentList = documentList;
	}

	/**
	 * @return the sellerAccount
	 */
	public SellerAccount getSellerAccount() {
		return this.sellerAccount;
	}

	/**
	 * @param sellerAccount
	 *            the sellerAccount to set
	 */
	public void setSellerAccount(SellerAccount sellerAccount) {
		this.sellerAccount = sellerAccount;
	}

	/**
	 * @return the sellerBp
	 */
	public SellerBpSac getSellerBp() {
		return this.sellerBp;
	}

	/**
	 * @param sellerBp
	 *            the sellerBp to set
	 */
	public void setSellerBp(SellerBpSac sellerBp) {
		this.sellerBp = sellerBp;
	}

}
