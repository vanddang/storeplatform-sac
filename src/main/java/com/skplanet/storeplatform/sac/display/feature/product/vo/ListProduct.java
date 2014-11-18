/**
 * 
 */
package com.skplanet.storeplatform.sac.display.feature.product.vo;

import java.io.Serializable;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;

public class ListProduct extends CommonInfo implements Serializable  {
	private static final long serialVersionUID = -4279175037031248550L;
	
	private String prodId;  // 상품ID
	private String contentsTypeCd; // CONTENTS_TYPE_CD
	private String svcGrpCd; //SVC_GRP_CD
	private String topMenuId;
	private String menuId;  // 상세 카테고리
	private String imgPath; // 썸네일 url
	private String expoOrd;
	private String expoOrdSub;
	private String recomReason; // RECOM_REASON
	private String etcProp;     // ETC_PROP
	
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public String getContentsTypeCd() {
		return contentsTypeCd;
	}
	public void setContentsTypeCd(String contentsTypeCd) {
		this.contentsTypeCd = contentsTypeCd;
	}
	public String getSvcGrpCd() {
		return svcGrpCd;
	}
	public void setSvcGrpCd(String svcGrpCd) {
		this.svcGrpCd = svcGrpCd;
	}
	public String getTopMenuId() {
		return topMenuId;
	}
	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public String getRecomReason() {
		return recomReason;
	}
	public void setRecomReason(String recomReason) {
		this.recomReason = recomReason;
	}
	public String getEtcProp() {
		return etcProp;
	}
	public void setEtcProp(String etcProp) {
		this.etcProp = etcProp;
	}
	
	public boolean isApp(){
		return isFun()||isLifeLiving()||isEdu()||isGame();
	}
	
	// DP01
	public boolean isGame(){
		return DisplayConstants.DP_GAME_TOP_MENU_ID.equals(topMenuId);
	}
	
	// DP03
	public boolean isFun(){
		return DisplayConstants.DP_FUN_TOP_MENU_ID.equals(topMenuId);
	}
	// DP04
	public boolean isLifeLiving(){
		return DisplayConstants.DP_LIFE_LIVING_TOP_MENU_ID.equals(topMenuId);
	}
	// DP08
	public boolean isEdu(){
		return DisplayConstants.DP_LANG_EDU_TOP_MENU_ID.equals(topMenuId);
	}
	// DP09
	public boolean isVod(){
		return DisplayConstants.DP_VOD_TOP_MENU_ID.equals(topMenuId);
	}
	// DP13
	public boolean isEbook(){
		return DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId);
	}
	// DP14
	public boolean isComic(){
		return DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(topMenuId);
	}
	// DP15
	public boolean isShoppingCoupon(){
		return DisplayConstants.DP_SHOPPING_COUPON_TOP_MENU_ID.equals(topMenuId);
	}
	// DP16
	public boolean isMusic(){
		return DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(topMenuId);
	}
	// DP17
	public boolean isMovie(){
		return DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId);
	}
	// DP18
	public boolean isTV(){
		return DisplayConstants.DP_TV_TOP_MENU_ID.equals(topMenuId);
	}
	
	// DP26
	public boolean isWebtoon(){
		return DisplayConstants.DP_WEBTOON_TOP_MENU_ID.equals(topMenuId);
	}
	// DP28
	public boolean isShopping(){
		return DisplayConstants.DP_SHOPPING_TOP_MENU_ID.equals(topMenuId);
	}


}