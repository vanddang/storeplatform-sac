package com.skplanet.storeplatform.sac.display.music.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Point;
import com.skplanet.storeplatform.sac.display.common.vo.MenuItem;

import java.util.List;

/**
 * 음악 상품 정보
 * User: joyspring
 * Date: 1/28/14
 * Time: 3:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class MusicDetailComposite extends CommonInfo {

    private static final long serialVersionUID = 1L;
    
    private MusicDetail musicDetail;
    private List<SubContent> contentList;
    private List<RelatedProduct> relatedProductList;
    private List<Point> pointList;
    
    public MusicDetail getMusicDetail() {
        return musicDetail;
    }

    public void setMusicDetail(MusicDetail musicDetail) {
        this.musicDetail = musicDetail;
    }

    public List<SubContent> getContentList() {
        return contentList;
    }

    public void setContentList(List<SubContent> contentList) {
        this.contentList = contentList;
    }

    public List<RelatedProduct> getRelatedProductList() {
        return relatedProductList;
    }

    public void setRelatedProductList(List<RelatedProduct> relatedProductList) {
        this.relatedProductList = relatedProductList;
    }

	public List<Point> getPointList() {
		return pointList;
	}

	public void setPointList(List<Point> pointList) {
		this.pointList = pointList;
	}
    
}
