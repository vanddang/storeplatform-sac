package com.skplanet.storeplatform.sac.display.common.vo;

/**
 * Menu
 * Updated on : 2014. 01. 24 Updated by : 정희원, SK 플래닛.
 */
public class MenuItem {
    private String menuId;
    private int menuDepth;
    private boolean isInfrMenu;
    private String upMenuId;
    private String topMenuId;
    private String menuNm;
    private String menuDesc;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public int getMenuDepth() {
        return menuDepth;
    }

    public void setMenuDepth(int menuDepth) {
        this.menuDepth = menuDepth;
    }

    public boolean isInfrMenu() {
        return isInfrMenu;
    }

    public void setInfrMenu(boolean infrMenu) {
        isInfrMenu = infrMenu;
    }

    public String getUpMenuId() {
        return upMenuId;
    }

    public void setUpMenuId(String upMenuId) {
        this.upMenuId = upMenuId;
    }

    public String getTopMenuId() {
        return topMenuId;
    }

    public void setTopMenuId(String topMenuId) {
        this.topMenuId = topMenuId;
    }

    public String getMenuNm() {
        return menuNm;
    }

    public void setMenuNm(String menuNm) {
        this.menuNm = menuNm;
    }

    public String getMenuDesc() {
        return menuDesc;
    }

    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }
}
