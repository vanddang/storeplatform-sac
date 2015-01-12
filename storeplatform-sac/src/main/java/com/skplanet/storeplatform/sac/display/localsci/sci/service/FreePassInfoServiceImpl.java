package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.*;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.SupportDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 정액제 상품 DRM 정보 조회.
 * 
 * Updated on : 2014. 2. 28. Updated by : 김형식 , 지티소프트
 */
@Service
public class FreePassInfoServiceImpl implements FreePassInfoService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService displayCommonService;

	/**
	 * <pre>
	 * 정액제 상품 DRM 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return FreepassDrmInfo 정액제 상품 DRM 정보
	 */
	@Override
	public FreePassInfo searchFreePassDrmInfo(FreePassInfoSacReq req) {
		FreePassInfo freepassDrmInfo = this.commonDAO.queryForObject("FreePassInfo.searchFreePassDrmInfo", req,
				FreePassInfo.class);
		return freepassDrmInfo;
	}

	/**
	 * <pre>
	 * 정액권의 에피소드 상품 목록 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return EpisodeInfoSacRes 상품 메타 정보 리스트
	 */
	@Override
	public EpisodeInfoSacRes searchEpisodeList(EpisodeInfoReq req) {

		// / 단말 지원 정보 조회
		SupportDevice supportDevice = this.displayCommonService.getSupportDeviceInfo(req.getDeviceModelCd());

		EpisodeInfoSacRes res = new EpisodeInfoSacRes();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("langCd", req.getLangCd());
		paramMap.put("deviceModelCd", req.getDeviceModelCd());
		paramMap.put("tenantId", req.getTenantId());
		paramMap.put("prodId", req.getProdId());
		paramMap.put("cmpxProdClsfCd", req.getCmpxProdClsfCd());
		paramMap.put("supportDevice", supportDevice);
		paramMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
		paramMap.put("dpAnyPhone4mm", DisplayConstants.DP_ANY_PHONE_4MM);

		List<EpisodeInfoRes> freePassInfoResList = null;
		freePassInfoResList = this.commonDAO.queryForList("FreePassInfo.searchEpisodeList", paramMap,
				EpisodeInfoRes.class);
		res.setFreePassInfoRes(freePassInfoResList);
		return res;
	}

	/**
	 * <pre>
	 * 정액권 기본 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return FreePassBasicInfoSacRes 상품 메타 정보 리스트
	 */
	@Override
	public FreePassBasicInfoSacRes searchFreepassBasicList(FreePassBasicInfoSacReq req) {
		this.log.info("################ [SAC DP LocalSCI] SAC FreePass Stat : searchFreepassBasicList :{} ",
				DateUtil.getToday("yyyy-MM-dd hh:mm:ss.SSS"));
		FreePassBasicInfoSacRes res = new FreePassBasicInfoSacRes();
		List<FreePassBasicInfo> freePassBasicInfoList = new ArrayList<FreePassBasicInfo>();
		List<String> prodIdList = req.getList();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lang", req.getLang());
		paramMap.put("tenantId", req.getTenantId());

		for (String prodId : prodIdList) {
			paramMap.put("prodId", prodId);
			FreePassBasicInfo freePassBasicInfo = this.commonDAO.queryForObject("FreePassInfo.searchFreepassBasicList",
					paramMap, FreePassBasicInfo.class);

			if (freePassBasicInfo != null) {
				freePassBasicInfoList.add(freePassBasicInfo);
			}

			res.setFreePassBasicInfo(freePassBasicInfoList);
		}
		this.log.info("################ [SAC DP LocalSCI] SAC FreePass End : searchFreepassBasicList :{} ",
				DateUtil.getToday("yyyy-MM-dd hh:mm:ss.SSS"));

		return res;
	}
}
