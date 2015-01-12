/**
 * 
 */
package com.skplanet.storeplatform.sac.display.album.controller;

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
import com.skplanet.storeplatform.sac.client.display.vo.album.AlbumDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.album.AlbumDetailRes;
import com.skplanet.storeplatform.sac.client.display.vo.album.AlbumListByArtistReq;
import com.skplanet.storeplatform.sac.client.display.vo.album.AlbumListByArtistRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.album.service.AlbumService;

/**
 * AlbumController
 * 
 * Updated on : 2014. 10. 13.
 * Updated by : 김희민, SK 플래닛.
 */
@Controller
@RequestMapping(value = "/display")
public class AlbumController {
	private static final Logger logger = LoggerFactory.getLogger(AlbumController.class);

	@Autowired
	private AlbumService albumService;
	
    @RequestMapping(value = "/album/detail/v1", method = RequestMethod.POST)
    @ResponseBody
    public AlbumDetailRes getAlbumDetail(SacRequestHeader header, @Validated @RequestBody AlbumDetailReq req) {
    	String prodId = req.getAlbumId();
    	String userKey = req.getUserKey();
    	String langCd = header.getTenantHeader().getLangCd();
    	String tenantId = header.getTenantHeader().getTenantId();
    	
    	AlbumDetailRes res = albumService.getAlbumDetail(tenantId, prodId, langCd, userKey);
    	if (res == null) {
    		throw new StorePlatformException("SAC_DSP_0009");
    	}
    	
        return res;
    }
    
    
    @RequestMapping(value = "/album/listByArtist/v1", method = RequestMethod.GET)
    @ResponseBody
    public AlbumListByArtistRes getAlbumListByArtist(SacRequestHeader header, @Validated AlbumListByArtistReq req) {
    	String artistId = req.getArtistId();
    	String langCd = header.getTenantHeader().getLangCd();
    	
    	logger.debug("artistId: " + artistId + ", langCd: " + langCd);
    	AlbumListByArtistRes res = albumService.getAlbumListByArtist(artistId, langCd);
    	if (res == null) {
    		throw new StorePlatformException("SAC_DSP_0009");
    	}
    	return res;
    }
}
