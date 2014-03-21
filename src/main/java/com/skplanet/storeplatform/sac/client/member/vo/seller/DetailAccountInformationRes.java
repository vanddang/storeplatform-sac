package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.Document;
import com.skplanet.storeplatform.sac.client.member.vo.common.ExtraRight;
import com.skplanet.storeplatform.sac.client.member.vo.common.SellerAccount;

/**
 * 판매자회원 정산 정보 조회
 * 
 * Updated on : 2014. 3. 20. Updated by : 김다슬, 인크로스.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DetailAccountInformationRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 판매자 Key.
	 */
	private String sellerKey;

	/**
	 * 판매자 문서정보 목록.
	 */
	private List<Document> documentList;

	/**
	 * 판매자 멀티미디어 정보 목록.
	 */
	private List<ExtraRight> extraRightList;

	/**
	 * 판매자 정산정보.
	 */
	private SellerAccount sellerAccount;

	/**
	 * @return the sellerKey
	 */
	public String getSellerKey() {
		return this.sellerKey;
	}

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
	 * @return the extraRightList
	 */
	public List<ExtraRight> getExtraRightList() {
		return this.extraRightList;
	}

	/**
	 * @param extraRightList
	 *            the extraRightList to set
	 */
	public void setExtraRightList(List<ExtraRight> extraRightList) {
		this.extraRightList = extraRightList;
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
	 * @param sellerKey
	 *            the sellerKey to set
	 */
	public void setSellerKey(String sellerKey) {
		this.sellerKey = sellerKey;
	}

}
