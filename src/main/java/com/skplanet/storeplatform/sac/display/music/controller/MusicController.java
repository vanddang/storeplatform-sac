package com.skplanet.storeplatform.sac.display.music.controller;

import com.skplanet.storeplatform.sac.client.display.vo.music.MusicDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.music.MusicDetailRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.music.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(value = "/music/detail/v1", method = RequestMethod.GET)
    @ResponseBody
    public MusicDetailRes getMusicDetail(String episodeId, SacRequestHeader header) {

        MusicDetailReq req = new MusicDetailReq();
        req.setEpisodeId(episodeId);
        req.setTenantId(header.getTenantHeader().getTenantId());
        req.setLangCd(header.getTenantHeader().getLangCd());

        return musicService.getMusicDetail(req);
    }
}
