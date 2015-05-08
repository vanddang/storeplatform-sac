package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.CmpxBasicInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.CmpxBasicInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.CmpxBasicInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.CmpxProductInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.CmpxProductInfoList;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.CmpxProductInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.CmpxProductListRes;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.CmpxProductSacReq;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.SupportDevice;

/**
 * 
 * 이용권 상품 정보 조회.
 * 
 * Updated on : 2015. 5. 07. Updated by : 김형식 , 지티소프트
 */
@Service
public class CmpxInfoServiceImpl implements CmpxInfoService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService displayCommonService;

	/**
	 * <pre>
	 * 이용권 기본 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return CmpxBasicInfoSacRes
	 */
	@Override
	public CmpxBasicInfoSacRes searchCmpxBasicInfoList(CmpxBasicInfoSacReq req){
		this.log.info("################ [SAC DP LocalSCI] SAC Cmpx Start : searchCmpxBasicInfoList :{} ",
				DateUtil.getToday("yyyy-MM-dd hh:mm:ss.SSS"));
		
		if (StringUtils.isEmpty(req.getTenantId())) {
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", req.getTenantId());
		}
		
		if (StringUtils.isEmpty(req.getLang())) {
			throw new StorePlatformException("SAC_DSP_0002", "lang", req.getLang());
		}
		
		CmpxBasicInfoSacRes res = new CmpxBasicInfoSacRes();
		List<CmpxBasicInfo> cmpxBasicInfoList = new ArrayList<CmpxBasicInfo>();
		List<String> prodIdList = req.getList();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lang", req.getLang());
		paramMap.put("tenantId", req.getTenantId());

		for (String prodId : prodIdList) {
			paramMap.put("prodId", prodId);
			CmpxBasicInfo cmpxBasicInfo = this.commonDAO.queryForObject("CmpxInfo.searchCmpxBasicInfoList",
					paramMap, CmpxBasicInfo.class);

			if (cmpxBasicInfo != null) {
				cmpxBasicInfoList.add(cmpxBasicInfo);
			}

			res.setCmpxBasicInfo(cmpxBasicInfoList);
		}
		this.log.info("################ [SAC DP LocalSCI] SAC Cmpx End : searchCmpxBasicInfoList :{} ",
				DateUtil.getToday("yyyy-MM-dd hh:mm:ss.SSS"));

		return res;
	}

	/**
	 * <pre>
	 * 이용권에 등록된 상품 정보 조회 .
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return CmpxProductListRes 
	 */
	@Override
	public CmpxProductListRes searchCmpxProductList(CmpxProductSacReq req) {
		
		if (StringUtils.isEmpty(req.getTenantId())) {
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", req.getTenantId());
		}
		if (StringUtils.isEmpty(req.getCmpxProdClsfCd())) {
			throw new StorePlatformException("SAC_DSP_0002", "cmpxProdClsfCd", req.getCmpxProdClsfCd());
		}
		if (StringUtils.isEmpty(req.getProdId())) {
			throw new StorePlatformException("SAC_DSP_0002", "prodId", req.getProdId());
		}
		if (StringUtils.isEmpty(req.getPossLendClsfCd())) {
			throw new StorePlatformException("SAC_DSP_0002", "possLendClsfCd", req.getPossLendClsfCd());
		}
		
		if (StringUtils.isEmpty(req.getDeviceModelCd())) {
			throw new StorePlatformException("SAC_DSP_0002", "deviceModelCd", req.getDeviceModelCd());
		}
		if (StringUtils.isEmpty(req.getLangCd())) {
			throw new StorePlatformException("SAC_DSP_0002", "langCd", req.getLangCd());
		}
			
		
		// / 단말 지원 정보 조회
		SupportDevice supportDevice = this.displayCommonService.getSupportDeviceInfo(req.getDeviceModelCd());

		if(supportDevice == null){
			throw new StorePlatformException("SAC_DSP_0012", req.getDeviceModelCd());
		}
		
		CmpxProductListRes res = new CmpxProductListRes();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("langCd", req.getLangCd());
		paramMap.put("deviceModelCd", req.getDeviceModelCd());
		paramMap.put("tenantId", req.getTenantId());
		paramMap.put("prodId", req.getProdId());
		if (StringUtils.isEmpty(req.getChapter())) {
			paramMap.put("chapter", null);
		}else{
			paramMap.put("chapter", req.getChapter());			
		}
		
		paramMap.put("possLendClsfCd", req.getPossLendClsfCd());
		paramMap.put("cmpxProdClsfCd", req.getCmpxProdClsfCd());
		paramMap.put("supportDevice", supportDevice);
		paramMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
		paramMap.put("dpAnyPhone4mm", DisplayConstants.DP_ANY_PHONE_4MM);

		List<CmpxProductInfoList> cmpxProductInfoList = null;
		cmpxProductInfoList = this.commonDAO.queryForList("CmpxInfo.searchCmpxProductList", paramMap,
				CmpxProductInfoList.class);
		res.setCmpxProductInfoList(cmpxProductInfoList);
		return res;
	}

	/**
	 * <pre>
	 * 이용권 및 에피소드 상품 정보 조회 .
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return CmpxProductInfo 
	 */
	@Override
	public CmpxProductInfo searchCmpxProductInfo(CmpxProductInfoSacReq req) {
		if (StringUtils.isEmpty(req.getTenantId())) {
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", req.getTenantId());
		}
		if (StringUtils.isEmpty(req.getLangCd())) {
			throw new StorePlatformException("SAC_DSP_0002", "langCd", req.getLangCd());
		}
			
		if (StringUtils.isEmpty(req.getProdId())) {
			throw new StorePlatformException("SAC_DSP_0002", "prodId", req.getProdId());
		}
		if (StringUtils.isEmpty(req.getEpisodeProdId())) {
			throw new StorePlatformException("SAC_DSP_0002", "episodeProdId", req.getEpisodeProdId());
		}
		
		if (StringUtils.isEmpty(req.getChapter())) {
			req.setChapter(null);
		}
		CmpxProductInfo cmpxProductInfo = this.commonDAO.queryForObject("CmpxInfo.searchCmpxProductInfo", req,
				CmpxProductInfo.class);
		return cmpxProductInfo;
	}
}
