/**
 * 
 */
package com.skplanet.storeplatform.sac.display.stat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.display.vo.stat.StatActionReq;
import com.skplanet.storeplatform.sac.client.display.vo.stat.StatActionRes;
import com.skplanet.storeplatform.sac.client.display.vo.stat.StatDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.stat.StatDetailRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.album.controller.AlbumController;
import com.skplanet.storeplatform.sac.display.album.service.AlbumService;
import com.skplanet.storeplatform.sac.display.stat.service.StatService;

/**
 * StatController
 * 
 * Updated on : 2014. 10. 15.
 * Updated by : 김희민, SK 플래닛.
*/
@Controller
@RequestMapping(value = "/display")
public class StatController {
	private static final Logger logger = LoggerFactory.getLogger(StatController.class);

	@Autowired
	private StatService statService;
    
    @RequestMapping(value = "/stat/detail/v1", method = RequestMethod.GET)
    @ResponseBody
    public StatDetailRes getStatDetail(SacRequestHeader header, @Validated StatDetailReq req) {
    	String key = req.getKey();
    	String tenantId = header.getTenantHeader().getTenantId();
    	
    	logger.debug("tenantId: " + tenantId + "key: " + key);
    	StatDetailRes res = statService.getStatDetail(tenantId, key);
    	if (res == null) {
    		throw new StorePlatformException("SAC_DSP_0009");
    	}
    	
        return res;
    }
    
    @RequestMapping(value = "/stat/create/v1", method = RequestMethod.POST)
    @ResponseBody
    public StatActionRes createStat(SacRequestHeader header, @RequestBody @Validated StatActionReq req) {
    	String tenantId = header.getTenantHeader().getTenantId();
    	String userKey = req.getUserKey();
    	String key = req.getKey();
    	String regCaseCd = req.getRegCaseCd();
    	
    	logger.debug("tenantId: " + tenantId + "userKey: " + userKey + "key: " + key + "regCaseCd: " + regCaseCd);
    	StatActionRes res = statService.createStat(tenantId, userKey, key, regCaseCd);
    	
    	return res;
    }
    
    @RequestMapping(value = "/stat/remove/v1", method = RequestMethod.POST)
    @ResponseBody
    public StatActionRes removeStat(SacRequestHeader header, @RequestBody @Validated StatActionReq req) {
    	String tenantId = header.getTenantHeader().getTenantId();
    	String userKey = req.getUserKey();
    	String key = req.getKey();
    	String regCaseCd = req.getRegCaseCd();
    	
    	logger.debug("tenantId: " + tenantId + "userKey: " + userKey + "key: " + key + "regCaseCd: " + regCaseCd);
    	StatActionRes res = statService.removeStat(tenantId, userKey, key, regCaseCd);

    	return res;
    }
}
