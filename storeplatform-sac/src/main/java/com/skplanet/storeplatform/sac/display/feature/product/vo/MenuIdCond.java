/**
 * 
 */
package com.skplanet.storeplatform.sac.display.feature.product.vo;

/**
 * ListProduct에서 사용하기 위한 모델
 * 
 * Updated on : 2014. 10. 8.
 * Updated by : 문동선
 */
public class MenuIdCond {
	private String op1st;
	private String op2nd;
	private String menuId;
	
	public String getOp1st() {
		return op1st;
	}
	public void setOp1st(String op1st) {
		this.op1st = op1st.toUpperCase();
	}
	public String getOp2nd() {
		return op2nd;
	}
	public void setOp2nd(String op2nd) {
		this.op2nd = op2nd.toUpperCase();
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
}
