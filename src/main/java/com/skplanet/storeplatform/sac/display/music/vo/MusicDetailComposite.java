package com.skplanet.storeplatform.sac.display.music.vo;

import com.skplanet.storeplatform.sac.display.common.vo.MenuItem;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: joyspring
 * Date: 1/28/14
 * Time: 3:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class MusicDetailComposite {
    
    private MusicDetail musicDetail;
    private List<MenuItem> menuList;
    private List<SubContent> contentList;

    public MusicDetail getMusicDetail() {
        return musicDetail;
    }

    public void setMusicDetail(MusicDetail musicDetail) {
        this.musicDetail = musicDetail;
    }

    public List<MenuItem> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuItem> menuList) {
        this.menuList = menuList;
    }

    public List<SubContent> getContentList() {
        return contentList;
    }

    public void setContentList(List<SubContent> contentList) {
        this.contentList = contentList;
    }
}
