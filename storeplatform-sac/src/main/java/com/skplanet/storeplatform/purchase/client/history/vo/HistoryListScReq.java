package com.skplanet.storeplatform.purchase.client.history.vo;

import java.io.Serializable;
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.framework.core.persistence.dao.page.PagenateInfo;

/**
 * 구매내역 조회 요청 VO
 * 
 * Updated on : 2013. 12. 13. Updated by : ntels_yjw
 */
public class HistoryListScReq extends CommonInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String systemId;
	private String userKey; // 내부사용자번호
	private String deviceKey; // 내부디바이스ID
	private String startDt;
	private String endDt;
	private String prchsCaseCd;
	private String prchsProdType;
	private String hidingYn;
	private String prchsStatusCd;
	private String useFixrateProdId;
	private String tenantProdGrpCd;
	private String prchsProdHaveYn; // 상품보유여부
	private String prchsReqPathCd; // 구매요청경로코드
	private String giftRecvConfYn; // 선물수신확인여부
	private int offset;
	private int count;

	private int startRow;
	private int endRow;

	private List<String> mdnCategoryList;
	private List<String> productList;

	private String selectDeviceYn;

	private String prchsDt;
	private String prchsId;
	private Integer prchsDtlId;

	private String notTenantProdGrpCd;

	/**
	 * paging.
	 */
	private PagenateInfo page = new PagenateInfo();

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
	 * @return the page
	 */
	public PagenateInfo getPage() {
		return this.page;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(PagenateInfo page) {
		this.page = page;
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
	 * @return the tenantId
	 */
	public String getTenantId() {
		return this.tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return this.systemId;
	}

	/**
	 * @param systemId
	 *            the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
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
	 * @return the startDt
	 */
	public String getStartDt() {
		return this.startDt;
	}

	/**
	 * @param startDt
	 *            the startDt to set
	 */
	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}

	/**
	 * @return the endDt
	 */
	public String getEndDt() {
		return this.endDt;
	}

	/**
	 * @param endDt
	 *            the endDt to set
	 */
	public void setEndDt(String endDt) {
		this.endDt = endDt;
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
	 * @return the offset
	 */
	public int getOffset() {
		return this.offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
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
	public List<String> getProductList() {
		return this.productList;
	}

	/**
	 * @param productList
	 *            the productList to set
	 */
	public void setProductList(List<String> productList) {
		this.productList = productList;
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
	 * @return the mdnCategoryList
	 */
	public List<String> getMdnCategoryList() {
		return this.mdnCategoryList;
	}

	/**
	 * @param mdnCategoryList
	 *            the mdnCategoryList to set
	 */
	public void setMdnCategoryList(List<String> mdnCategoryList) {
		this.mdnCategoryList = mdnCategoryList;
	}

	/**
	 * @return the selectDeviceYn
	 */
	public String getSelectDeviceYn() {
		return this.selectDeviceYn;
	}

	/**
	 * @param selectDeviceYn
	 *            the selectDeviceYn to set
	 */
	public void setSelectDeviceYn(String selectDeviceYn) {
		this.selectDeviceYn = selectDeviceYn;
	}

	/**
	 * @return the startRow
	 */
	public int getStartRow() {
		return this.startRow;
	}

	/**
	 * @param startRow
	 *            the startRow to set
	 */
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	/**
	 * @return the endRow
	 */
	public int getEndRow() {
		return this.endRow;
	}

	/**
	 * @param endRow
	 *            the endRow to set
	 */
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	/**
	 * @return the prchsDt
	 */
	public String getPrchsDt() {
		return this.prchsDt;
	}

	/**
	 * @param prchsDt
	 *            the prchsDt to set
	 */
	public void setPrchsDt(String prchsDt) {
		this.prchsDt = prchsDt;
	}

	/**
	 * @return the prchsId
	 */
	public String getPrchsId() {
		return this.prchsId;
	}

	/**
	 * @param prchsId
	 *            the prchsId to set
	 */
	public void setPrchsId(String prchsId) {
		this.prchsId = prchsId;
	}

	/**
	 * @return the prchsDtlId
	 */
	public Integer getPrchsDtlId() {
		return this.prchsDtlId;
	}

	/**
	 * @param prchsDtlId
	 *            the prchsDtlId to set
	 */
	public void setPrchsDtlId(Integer prchsDtlId) {
		this.prchsDtlId = prchsDtlId;
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
