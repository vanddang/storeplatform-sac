package com.skplanet.storeplatform.sac.display.music.controller.binder;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.display.common.vo.MenuItem;
import com.skplanet.storeplatform.sac.display.music.vo.MusicDetail;
import com.skplanet.storeplatform.sac.display.music.vo.SubContent;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: joyspring
 * Date: 1/28/14
 * Time: 4:37 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MusicDetailBinder {
    void mapMusic(Product product, MusicDetail musicDetail, List<SubContent> contentList);

    void mapThumbnail(Product product);

    void mapMenu(Product product, List<MenuItem> menuList);

    void mapBasicInfo(Product product, MusicDetail musicDetail);
}
