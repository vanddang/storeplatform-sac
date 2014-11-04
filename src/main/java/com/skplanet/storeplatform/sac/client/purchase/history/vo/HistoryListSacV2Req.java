package com.skplanet.storeplatform.sac.client.purchase.history.vo;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.sac.client.purchase.common.vo.PurchaseHeaderSacReq;

/**
 * 구매내역 조회 요청 VO
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
public class HistoryListSacV2Req extends PurchaseHeaderSacReq {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String userKey; // 내부사용자번호
	private String deviceKey; // 내부디바이스ID

	@NotBlank
	private String prchsProdHaveYn; // 상품보유여부
	@Max(100)
	private int count; // 데이터갯수

	private String tenantProdGrpCd; // 테넌트상품분류코드
	private String notTenantProdGrpCd;// 제외 테넌트상품분류코드

	@Size(min = 0, max = 100)
	private List<ProductListSac> productList; // 조회 상품PID LIST
	private String prchsCaseCd; // 구매유형코드
	private String prchsProdType; // 구매상품타입
	private String hidingYn; // 숨김여부
	private String prchsStatusCd; // 구매상태
	private String useFixrateProdId; // 정액권ID

	private String prchsReqPathCd; // 구매요청경로코드
	private String giftRecvConfYn; // 선물수신확인여부

	private String startKey;

	/**
	 * @return the prchsReqPathCd
	 */
	public String getPrchsReqPathCd() {
		return this.prchsReqPathCd;
	}

	/**
	 * @param prchsReqPathCd
	 *            the prchsReqPathCd to set
	 */
	public void setPrchsReqPathCd(String prchsReqPathCd) {
		this.prchsReqPathCd = prchsReqPathCd;
	}

	/**
	 * @return the prchsProdHaveYn
	 */
	public String getPrchsProdHaveYn() {
		return this.prchsProdHaveYn;
	}

	/**
	 * @param prchsProdHaveYn
	 *            the prchsProdHaveYn to set
	 */
	public void setPrchsProdHaveYn(String prchsProdHaveYn) {
		this.prchsProdHaveYn = prchsProdHaveYn;
	}

	/**
	 * @return the prchsCaseCd
	 */
	public String getPrchsCaseCd() {
		return this.prchsCaseCd;
	}

	/**
	 * @param prchsCaseCd
	 *            the prchsCaseCd to set
	 */
	public void setPrchsCaseCd(String prchsCaseCd) {
		this.prchsCaseCd = prchsCaseCd;
	}

	/**
	 * @return the userKey
	 */
	public String getUserKey() {
		return this.userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the deviceKey
	 */
	public String getDeviceKey() {
		return this.deviceKey;
	}

	/**
	 * @param deviceKey
	 *            the deviceKey to set
	 */
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	/**
	 * @return the tenantProdGrpCd
	 */
	public String getTenantProdGrpCd() {
		return this.tenantProdGrpCd;
	}

	/**
	 * @param tenantProdGrpCd
	 *            the tenantProdGrpCd to set
	 */
	public void setTenantProdGrpCd(String tenantProdGrpCd) {
		this.tenantProdGrpCd = tenantProdGrpCd;
	}

	/**
	 * @return the productList
	 */
	public List<ProductListSac> getProductList() {
		return this.productList;
	}

	/**
	 * @param productList
	 *            the productList to set
	 */
	public void setProductList(List<ProductListSac> productList) {
		this.productList = productList;
	}

	/**
	 * @return the prchsProdType
	 */
	public String getPrchsProdType() {
		return this.prchsProdType;
	}

	/**
	 * @param prchsProdType
	 *            the prchsProdType to set
	 */
	public void setPrchsProdType(String prchsProdType) {
		this.prchsProdType = prchsProdType;
	}

	/**
	 * @return the hidingYn
	 */
	public String getHidingYn() {
		return this.hidingYn;
	}

	/**
	 * @param hidingYn
	 *            the hidingYn to set
	 */
	public void setHidingYn(String hidingYn) {
		this.hidingYn = hidingYn;
	}

	/**
	 * @return the prchsStatusCd
	 */
	public String getPrchsStatusCd() {
		return this.prchsStatusCd;
	}

	/**
	 * @param prchsStatusCd
	 *            the prchsStatusCd to set
	 */
	public void setPrchsStatusCd(String prchsStatusCd) {
		this.prchsStatusCd = prchsStatusCd;
	}

	/**
	 * @return the useFixrateProdId
	 */
	public String getUseFixrateProdId() {
		return this.useFixrateProdId;
	}

	/**
	 * @param useFixrateProdId
	 *            the useFixrateProdId to set
	 */
	public void setUseFixrateProdId(String useFixrateProdId) {
		this.useFixrateProdId = useFixrateProdId;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return this.count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the giftRecvConfYn
	 */
	public String getGiftRecvConfYn() {
		return this.giftRecvConfYn;
	}

	/**
	 * @param giftRecvConfYn
	 *            the giftRecvConfYn to set
	 */
	public void setGiftRecvConfYn(String giftRecvConfYn) {
		this.giftRecvConfYn = giftRecvConfYn;
	}

	/**
	 * @return the startKey
	 */
	public String getStartKey() {
		return this.startKey;
	}

	/**
	 * @param startKey
	 *            the startKey to set
	 */
	public void setStartKey(String startKey) {
		this.startKey = startKey;
	}

	/**
	 * @return the notTenantProdGrpCd
	 */
	public String getNotTenantProdGrpCd() {
		return this.notTenantProdGrpCd;
	}

	/**
	 * @param notTenantProdGrpCd
	 *            the notTenantProdGrpCd to set
	 */
	public void setNotTenantProdGrpCd(String notTenantProdGrpCd) {
		this.notTenantProdGrpCd = notTenantProdGrpCd;
	}

}
