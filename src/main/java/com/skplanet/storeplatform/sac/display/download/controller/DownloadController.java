package com.skplanet.storeplatform.sac.display.download.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadAppReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadAppRes;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadMusicReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadMusicRes;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadVodReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadVodRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.download.service.DownloadAppService;
import com.skplanet.storeplatform.sac.display.download.service.DownloadMusicService;
import com.skplanet.storeplatform.sac.display.download.service.DownloadVodService;

/**
 * 
 * Calss 설명(Download 앱 정보 관련)
 * 
 * Updated on : 2014. 1. 22. Updated by : 이석희, 인크로스
 */
@Controller
public class DownloadController {

	@Autowired
	private DownloadAppService downloadAppService;

	@Autowired
	private DownloadMusicService downloadMusicService;

	@Autowired
	private DownloadVodService downloadVodService;

	/**
	 * 
	 * <pre>
	 * Download 앱 정보 조회(for download).
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param downloadAppReq
	 *            downloadAppReq
	 * @return DownloadAppRes
	 */
	@RequestMapping(value = "/display/download/app/detail/v1", method = RequestMethod.POST)
	@ResponseBody
	public DownloadAppRes downloadApp(SacRequestHeader requestheader, @RequestBody DownloadAppReq downloadAppReq) {
		return this.downloadAppService.searchDownloadApp(requestheader, downloadAppReq);
	}

	/**
	 * 
	 * <pre>
	 * Download Music 정보 조회(for download).
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param downloadMusicReq
	 *            downloadMusicReq
	 * @return DownloadMusicRes
	 */
	@RequestMapping(value = "/display/download/music/detail/v1", method = RequestMethod.POST)
	@ResponseBody
	public DownloadMusicRes downloadMusic(SacRequestHeader requestheader,
			@RequestBody @Valid DownloadMusicReq downloadMusicReq) {
		return this.downloadMusicService.searchDownloadMusic(requestheader, downloadMusicReq);
	}

	/**
	 * 
	 * <pre>
	 * Download Vod 정보 조회(for download).
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param downloadVodReq
	 *            downloadVodReq
	 * @return DownloadVodRes
	 */
	@RequestMapping(value = "/display/download/vod/detail/v1", method = RequestMethod.POST)
	@ResponseBody
	public DownloadVodRes downloadVod(SacRequestHeader requestheader, @RequestBody DownloadVodReq downloadVodReq) {
		return this.downloadVodService.searchDownloadVod(requestheader, downloadVodReq);
	}

}
