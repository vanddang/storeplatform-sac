/**
 * 
 */
package com.skplanet.storeplatform.sac.display.cache.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 10. 23.
 * Updated by : 1002177
 */
public class AlbumMetaParam extends CommonInfo{
	
	private static final long serialVersionUID = 1484456390054965555L;
	
	private String langCd;
    private String prodId;
    
    private String tenantId;
    private String userKey;
    
	public String getLangCd() {
		return langCd;
	}
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getCacheKey() {
        return prodId + "_" + langCd;
    }
}
