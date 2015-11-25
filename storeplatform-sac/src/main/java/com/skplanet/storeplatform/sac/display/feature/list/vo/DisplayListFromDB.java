
package com.skplanet.storeplatform.sac.display.feature.list.vo;

/**
 * 상품리스트 응답조건
 *
 * Updated on : 2015. 4. 27.
 * Updated by : 1002159
 */
public class DisplayListFromDB {

	private String listId;
	private String listNm;
	private String imgPath;
	private String expoOrd;
	private String expoOrdSub;
	private String etcProp;

	private boolean mergeTestFlag = false;

	public String getListId() {
		return listId;
	}
	public void setListId(String listId) {
		this.listId = listId;
	}
	public String getListNm() {
		return listNm;
	}
	public void setListNm(String listNm) {
		this.listNm = listNm;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getExpoOrd() {
		return expoOrd;
	}
	public void setExpoOrd(String expoOrd) {
		this.expoOrd = expoOrd;
	}
	public String getExpoOrdSub() {
		return expoOrdSub;
	}
	public void setExpoOrdSub(String expoOrdSub) {
		this.expoOrdSub = expoOrdSub;
	}
	public String getEtcProp() {
		return etcProp;
	}
	public void setEtcProp(String etcProp) {
		this.etcProp = etcProp;
	}
}
