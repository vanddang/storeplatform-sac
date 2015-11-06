package com.skplanet.storeplatform.sac.client.display.vo.category;

import org.hibernate.validator.constraints.NotBlank;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class CategorySpecificSacReq extends CommonInfo {
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "필수 파라미터 입니다.")
	private String list;

    private String ignoreProvisionYn = "N";

    private String prodGradeCd;

    private String plus19Yn;

	public String getList() {
		return this.list;
	}

	public void setList(String list) {
		this.list = list;
	}

    public String getIgnoreProvisionYn() {
        return ignoreProvisionYn;
    }

    public void setIgnoreProvisionYn(String ignoreProvisionYn) {
        this.ignoreProvisionYn = ignoreProvisionYn;
    }

    public String getProdGradeCd() {
        return prodGradeCd;
    }

    public void setProdGradeCd(String prodGradeCd) {
        this.prodGradeCd = prodGradeCd;
    }

    public String getPlus19Yn() {
        return plus19Yn;
    }

    public void setPlus19Yn(String plus19Yn) {
        this.plus19Yn = plus19Yn;
    }
}
