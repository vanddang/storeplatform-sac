package com.skplanet.storeplatform.sac.display.download.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadAppSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadAppSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadComicSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadComicSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadEbookSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadEbookSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadMusicSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadMusicSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadVodSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadVodSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.download.service.DownloadAppService;
import com.skplanet.storeplatform.sac.display.download.service.DownloadComicService;
import com.skplanet.storeplatform.sac.display.download.service.DownloadEbookService;
import com.skplanet.storeplatform.sac.display.download.service.DownloadMusicService;
import com.skplanet.storeplatform.sac.display.download.service.DownloadVodService;

/**
 * 상품 정보 요청(for download)
 * 
 * Updated on : 2014. 1. 28. Updated by : 이태희.
 */
@Controller
@RequestMapping("/display/download")
public class DownloadController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DownloadAppService downloadAppService;

	@Autowired
	private DownloadMusicService downloadMusicService;

	@Autowired
	private DownloadVodService downloadVodService;

	@Autowired
	private DownloadEbookService downloadEbookService;

	@Autowired
	private DownloadComicService downloadComicService;

	@InitBinder("downloadAppSacReq")
	public void initDownloadAppSacReqBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new DownloadAppSacReqValidator());
	}

	/**
	 * 
	 * <pre>
	 * Download 앱 정보 조회(for download).
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param downloadAppSacReq
	 *            downloadAppSacReq
	 * @return downloadAppSacReq
	 */
	@RequestMapping(value = "/app/detail/v1", method = RequestMethod.POST)
	@ResponseBody
	public DownloadAppSacRes downloadApp(SacRequestHeader requestheader,
			@RequestBody @Validated DownloadAppSacReq downloadAppSacReq) {
		return this.downloadAppService.searchDownloadApp(requestheader, downloadAppSacReq);
	}

	/**
	 * 
	 * <pre>
	 * Download Music 정보 조회(for download).
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param downloadMusicSacReq
	 *            downloadMusicSacReq
	 * @return DownloadMusicSacRes
	 */
	@RequestMapping(value = "/music/detail/v1", method = RequestMethod.POST)
	@ResponseBody
	public DownloadMusicSacRes downloadMusic(SacRequestHeader requestheader,
			@RequestBody @Validated DownloadMusicSacReq downloadMusicSacReq) {
		return this.downloadMusicService.searchDownloadMusic(requestheader, downloadMusicSacReq);
	}

	/**
	 * 
	 * <pre>
	 * Download Vod 정보 조회(for download).
	 * </pre>
	 * 
	 * @param requestheader
	 *            requestheader
	 * @param downloadVodSacReq
	 *            downloadVodSacReq
	 * @return DownloadVodSacRes
	 */
	@RequestMapping(value = "/vod/detail/v1", method = RequestMethod.POST)
	@ResponseBody
	public DownloadVodSacRes downloadVod(SacRequestHeader requestheader,
			@RequestBody @Validated DownloadVodSacReq downloadVodSacReq) {
		return this.downloadVodService.searchDownloadVod(requestheader, downloadVodSacReq);
	}

	/**
	 * <pre>
	 * ebook 상품 정보 조회(for download).
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param ebookReq
	 *            ebookReq
	 * @return DownloadEbookSacRes
	 */
	@RequestMapping(value = "/epub/detail/v1", method = RequestMethod.POST)
	@ResponseBody
	public DownloadEbookSacRes getDownloadEbookInfo(SacRequestHeader header,
			@RequestBody @Validated DownloadEbookSacReq ebookReq) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("[getDownloadEbookInfo] SacRequestHeader\n{}", header.toString());
		this.logger.debug("[getDownloadEbookInfo] DownloadEbookSacReq\n{}", ebookReq.toString());
		this.logger.debug("----------------------------------------------------------------");

		return this.downloadEbookService.getDownloadEbookInfo(header, ebookReq);
	}

	/**
	 * <pre>
	 * Comic 상품 정보 조회(for download).
	 * </pre>
	 * 
	 * @param header
	 *            header
	 * @param comicReq
	 *            comicReq
	 * @return DownloadComicSacRes
	 */
	@RequestMapping(value = "/comic/detail/v1", method = RequestMethod.POST)
	@ResponseBody
	public DownloadComicSacRes getDownloadComicInfo(SacRequestHeader header,
			@RequestBody @Validated DownloadComicSacReq comicReq) {
		this.logger.debug("----------------------------------------------------------------");
		this.logger.debug("[getDownloadComicInfo] SacRequestHeader\n{}", header.toString());
		this.logger.debug("[getDownloadComicInfo] DownloadComicSacReq\n{}", comicReq.toString());
		this.logger.debug("----------------------------------------------------------------");

		return this.downloadComicService.getDownloadComicInfo(header, comicReq);
	}

}
