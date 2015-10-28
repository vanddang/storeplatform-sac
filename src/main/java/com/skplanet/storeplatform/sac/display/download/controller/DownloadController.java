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

import com.google.common.base.Strings;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
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
import com.skplanet.storeplatform.sac.client.display.vo.download.DownloadVodV3SacReq;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.download.service.DownloadAppService;
import com.skplanet.storeplatform.sac.display.download.service.DownloadComicService;
import com.skplanet.storeplatform.sac.display.download.service.DownloadEbookService;
import com.skplanet.storeplatform.sac.display.download.service.DownloadMusicService;
import com.skplanet.storeplatform.sac.display.download.service.DownloadSupportService;
import com.skplanet.storeplatform.sac.display.download.service.DownloadVodService;
import com.skplanet.storeplatform.sac.display.download.vo.SearchDownloadAppResult;

/**
 * 상품 정보 요청(for download)
 * 
 * Updated on : 2014. 1. 28. Updated by : 이태희.
 */
@Controller
@RequestMapping("/display/download")
public class DownloadController {
    public static final String DL_TP_AUTO = "DP012702";
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

    @Autowired
    private DownloadSupportService downloadSupportService;

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
        SearchDownloadAppResult result = this.downloadAppService.searchDownloadApp(requestheader, downloadAppSacReq);

        if (!StringUtils.equals(downloadAppSacReq.getDwldTypeCd(), DL_TP_AUTO)
                && !Strings.isNullOrEmpty(downloadAppSacReq.getMdn())
                && result.isHasDl()) {

            downloadSupportService.createUserDownloadInfo(
                    downloadAppSacReq.getMdn(),
                    result.getAid(),
                    requestheader.getTenantHeader().getTenantId(),
                    result.getProdId());
        }

        return result.getDownloadAppSacRes();
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
		downloadVodSacReq.setBaseYn("Y"); // 화질별 가격분리 정책에 따른 기준 상품 구분값 추가
		return this.downloadVodService.searchDownloadVod(requestheader, downloadVodSacReq, false);
	}


	/**
	 * (V2) Download Vod 정보 조회(for download).
	 * @param requestheader
	 *            requestheader
	 * @param downloadVodSacReq
	 *            downloadVodSacReq
	 * @return DownloadVodSacRes
	 */
	@RequestMapping(value = "/vod/detail/v2", method = RequestMethod.POST)
	@ResponseBody
	public DownloadVodSacRes downloadVodV2(SacRequestHeader requestheader,
			@RequestBody @Validated DownloadVodSacReq downloadVodSacReq) {
		downloadVodSacReq.setBaseYn("Y"); // 화질별 가격분리 정책에 따른 기준 상품 구분값 추가
		return this.downloadVodService.searchDownloadVod(requestheader, downloadVodSacReq, true);
	}
	
	/**
	 * (V3) Download Vod 정보 조회(for download).
	 * @param requestheader
	 *            requestheader
	 * @param downloadVodV3SacReq
	 *            downloadVodV3SacReq
	 * @return DownloadVodSacRes
	 */
	@RequestMapping(value = "/vod/detail/v3", method = RequestMethod.POST)
	@ResponseBody
	public DownloadVodSacRes downloadVodV3(SacRequestHeader requestheader,
			@RequestBody @Validated DownloadVodV3SacReq downloadVodV3SacReq) {
		downloadVodV3SacReq.setBaseYn("N"); // 화질별 가격분리 정책에 따른 기준 상품 구분값 추가
		return this.downloadVodService.searchDownloadVodV3(requestheader, downloadVodV3SacReq, true);
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
