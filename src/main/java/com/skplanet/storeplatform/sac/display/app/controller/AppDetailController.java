package com.skplanet.storeplatform.sac.display.app.controller;

import com.skplanet.storeplatform.sac.client.display.vo.app.AppDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.app.AppDetailRes;
import com.skplanet.storeplatform.sac.display.app.service.AppDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * AppController
 * <p/>
 * Updated on : 2014. 01. 06 Updated by : 정희원, SK 플래닛.
 */
@Controller
@RequestMapping(value = "/display")
public class AppDetailController {

    @Autowired
    private AppDetailService appDetailService;

    @RequestMapping(value = "/app/detail/v1", method = RequestMethod.GET)
    @ResponseBody
    public AppDetailRes getAppDetail(String episodeId, @RequestParam(required = false) String seedApp) {
        AppDetailReq request = new AppDetailReq();
        request.setEpisodeId(episodeId);
        request.setSeedApp(seedApp);

        return appDetailService.getAppDetail(request);
    }
}
