package com.skplanet.storeplatform.sac.display.music.detail.controller;

import com.skplanet.storeplatform.sac.client.display.vo.music.MusicDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.music.MusicDetailRes;
import com.skplanet.storeplatform.sac.display.music.detail.service.MusicDetailService;
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
public class MusicDetailController {

    @Autowired
    private MusicDetailService musicDetailService;

    @RequestMapping(value = "/music/detail/v1", method = RequestMethod.GET)
    @ResponseBody
    public MusicDetailRes getMusicDetail(String episodeId) {
        MusicDetailReq req = new MusicDetailReq();
        req.setEpisodeId(episodeId);

        return musicDetailService.getMusicDetail(req);
    }
}
