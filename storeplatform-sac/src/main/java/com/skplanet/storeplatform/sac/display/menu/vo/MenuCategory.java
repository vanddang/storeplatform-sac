package com.skplanet.storeplatform.sac.display.menu.vo;

/**
 * 메뉴 카테고리
 *
 * @author 1002159
 * @since 2015-10-01
 */
public class MenuCategory {

    private String tenantId;
    private String menuId;
    private String menuNm;
    private String menuDesc;

    /**
     * 테넌트ID
     *
     * @return 테넌트ID
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * 테넌트ID
     * @param tenantId 테넌트ID
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * 메뉴ID
     * @return 메뉴ID
     */
    public String getMenuId() {
        return menuId;
    }

    /**
     * 메뉴ID
     * @param menuId 메뉴ID
     */
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    /**
     * 메뉴명
     *
     * @return 메뉴명
     */
    public String getMenuNm() {
        return menuNm;
    }

    /**
     * 메뉴명
     * @param menuNm 메뉴명
     */
    public void setMenuNm(String menuNm) {
        this.menuNm = menuNm;
    }

    /**
     * 메뉴상세설명 (Location)
     * @return 메뉴상세설명 (Location)
     */
    public String getMenuDesc() {
        return menuDesc;
    }

    /**
     * 메뉴상세설명 (Location)
     * @param menuDesc 메뉴상세설명 (Location)
     */
    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }



}
