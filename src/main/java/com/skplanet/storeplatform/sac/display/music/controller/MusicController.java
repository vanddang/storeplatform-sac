package com.skplanet.storeplatform.sac.display.music.controller;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.*;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.*;
import com.skplanet.storeplatform.sac.display.common.vo.MenuItem;
import com.skplanet.storeplatform.sac.display.music.controller.binder.MusicDetailBinder;
import com.skplanet.storeplatform.sac.display.music.vo.MusicDetail;
import com.skplanet.storeplatform.sac.display.music.vo.MusicDetailComposite;
import com.skplanet.storeplatform.sac.display.music.vo.MusicDetailParam;
import com.skplanet.storeplatform.sac.client.display.vo.music.MusicDetailRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.music.service.MusicService;
import com.skplanet.storeplatform.sac.display.music.vo.SubContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MusicDetailController
 *
 * Updated on : 2014. 01. 08 Updated by : 정희원, SK 플래닛.
 */
@Controller
@RequestMapping(value = "/display")
public class MusicController {

    @Autowired
    private MusicService musicService;

    @Autowired
    private MusicDetailBinder musicDetailBinder;

    @RequestMapping(value = "/music/detail/v1", method = RequestMethod.GET)
    @ResponseBody
    public MusicDetailRes getMusicDetail(@RequestParam String episodeId, SacRequestHeader header) {
        MusicDetailParam param = new MusicDetailParam();
        param.setEpisodeId(episodeId);
        param.setTenantId(header.getTenantHeader().getTenantId());
        param.setLangCd(header.getTenantHeader().getLangCd());

        MusicDetailComposite musicDetailComp = musicService.getMusicDetail(param);
        MusicDetailRes res = new MusicDetailRes();
        Product product = new Product();

        mapProduct(musicDetailComp, product);

        res.setProduct(product);

        return res;
    }

    private void mapProduct(MusicDetailComposite musicDetailComp, Product product) {

        MusicDetail musicDetail = musicDetailComp.getMusicDetail();
        List<MenuItem> menuList = musicDetailComp.getMenuList();
        List<SubContent> contentList = musicDetailComp.getContentList();

        musicDetailBinder.mapBasicInfo(product, musicDetail);
        musicDetailBinder.mapMenu(product, menuList);
        musicDetailBinder.mapThumbnail(product);
        musicDetailBinder.mapMusic(product, musicDetail, contentList);
    }

}
