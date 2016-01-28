/**
 *
 */
package com.skplanet.storeplatform.sac.display.feature.product.vo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.storeplatform.sac.client.display.vo.feature.product.ProductListSacReq;

public class ListProductCriteria {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private static final int DEFAULT_LIST_COUNT = 20;
	private static final int MIN_EXPO_ORD       = -99999999;

    private String                        tenantId;
	private String                        listId;
	private String                        stdDt; //YYYYMM DDHH24MISS
	private List<HashMap<String, String>> menuIdCondList;
	private List<String>                  prodGradeCdList;
	private Integer                       lastExpoOrd;
	private Integer                       lastExpoOrdSub;
	private boolean                       prodCharge;
	private Integer                       count;

	private String                        rawMenuId;
	private String                        rawProdGradeCd;

	public ListProductCriteria( ProductListSacReq req, String tenantId, String stdDt ) {

        this.tenantId = tenantId;
		this.listId   = req.getListId();
		this.stdDt    = stdDt;

        setupMenuIdCondList(req);
		setupProdGradeCdList(req);
		setupOrders(req);
		setProdChage(req);
		setCount(req);

		this.rawMenuId      = req.getMenuId();
		this.rawProdGradeCd = req.getProdGradeCd();

	}

	public String getCacheKey() {

		return String.format( "key:[%s-%s-%s],menu:[%s],grade:[%s],charge:[%s],start:[%s/%s],count:[%s]",
				tenantId, listId, stdDt,
				rawMenuId, rawProdGradeCd, prodCharge,
				lastExpoOrd, lastExpoOrdSub,
				count - 1
		);

	}

	private void setupMenuIdCondList(ProductListSacReq req) {

		if( req.getMenuId() == null ) return;

		menuIdCondList = new ArrayList<HashMap<String, String>>();
		StringTokenizer st = new StringTokenizer(req.getMenuId(), "+|^", true);
		HashMap<String, String> hm = new HashMap<String, String>();
		menuIdCondList.add(hm);

		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (token.equals("+")) {
				hm = new HashMap<String, String>();
				menuIdCondList.add(hm);
				hm.put("op1st", "AND");
			} else if (token.equals("|")) {
				hm = new HashMap<String, String>();
				menuIdCondList.add(hm);
				hm.put("op1st", "OR");
			} else if (token.equals("^")) {
				hm.put("op2nd", "NOT");
			} else {
				hm.put("menuId", token);
			}
		}
	}

	private void setupProdGradeCdList(ProductListSacReq req) {
		String exp = req.getProdGradeCd();
		if(exp == null)
			return;
		String[] array = exp.split("\\+");
		prodGradeCdList=Arrays.asList(array);
	}

	private void setupOrders(ProductListSacReq req) {
		String startKey = req.getStartKey();

		if(startKey==null || startKey.isEmpty()){
			lastExpoOrd    = MIN_EXPO_ORD;
			lastExpoOrdSub = MIN_EXPO_ORD;
		}else{
			String[] keys = startKey.split("/");
			lastExpoOrd    = Integer.valueOf(keys[0]);
			lastExpoOrdSub = Integer.valueOf(keys[1]);
		}
	}

	private void setCount( ProductListSacReq req ) {

		count = req.getCount() == null ? DEFAULT_LIST_COUNT : req.getCount();

		// To set "hasNext"
		count++;

	}

	private void setProdChage(ProductListSacReq req) {
		if(req.getProdCharge()!=null){
			prodCharge = req.getProdCharge().equals("Y");
		}
	}

	public String getListId() {
		return listId;
	}
	public void setListId(String listId) {
		this.listId = listId;
	}

	public List<HashMap<String, String>> getMenuIdCondList() {
		return menuIdCondList;
	}
	public void setMenuIdCondList(List<HashMap<String, String>> menuIdCondList) {
		this.menuIdCondList = menuIdCondList;
	}
	public List<String> getProdGradeCdList() {
		return prodGradeCdList;
	}
	public void setProdGradeCdList(List<String> prodGradeCdList) {
		this.prodGradeCdList = prodGradeCdList;
	}
	public Integer getLastExpoOrd() {
		return lastExpoOrd;
	}
	public void setLastExpoOrd(Integer lastExpoOrd) {
		this.lastExpoOrd = lastExpoOrd;
	}
	public Integer getLastExpoOrdSub() {
		return lastExpoOrdSub;
	}
	public void setLastExpoOrdSub(Integer lastExpoOrdSub) {
		this.lastExpoOrdSub = lastExpoOrdSub;
	}
	public boolean isProdCharge() {
		return prodCharge;
	}
	public void setProdCharge(boolean prodCharge) {
		this.prodCharge = prodCharge;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

	public String getStdDt() {
		return stdDt;
	}

	public void setStdDt(String stdDt) {
		this.stdDt = stdDt;
	}

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
