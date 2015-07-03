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
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfo;
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
	public CmpxBasicInfoSacRes searchCmpxBasicInfoList(CmpxBasicInfoSacReq req) {
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
			CmpxBasicInfo cmpxBasicInfo = this.commonDAO.queryForObject("CmpxInfo.searchCmpxBasicInfoList", paramMap,
					CmpxBasicInfo.class);

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

		if (!req.getCmpxProdClsfCd().equals("OR004305")) {
			if (StringUtils.isEmpty(req.getPossLendClsfCd())) {
				throw new StorePlatformException("SAC_DSP_0002", "possLendClsfCd", req.getPossLendClsfCd());
			}
		} else {
			req.setPossLendClsfCd(null);
		}

		if (StringUtils.isEmpty(req.getDeviceModelCd())) {
			throw new StorePlatformException("SAC_DSP_0002", "deviceModelCd", req.getDeviceModelCd());
		}
		if (StringUtils.isEmpty(req.getLangCd())) {
			throw new StorePlatformException("SAC_DSP_0002", "langCd", req.getLangCd());
		}

		if (StringUtils.isNotEmpty(req.getSeriesBookClsfCd())) {
			if (!"DP004301".equals(req.getSeriesBookClsfCd()) && !"DP004302".equals(req.getSeriesBookClsfCd())
					&& !"DP004305".equals(req.getSeriesBookClsfCd())) {
				throw new StorePlatformException("SAC_DSP_0003", "seriesBookClsfCd", req.getSeriesBookClsfCd());
			}
		}else{
			req.setSeriesBookClsfCd(null);
		}
		


		this.log.info("----------------------------------------------------------------");
		this.log.info("tenantId : " + req.getTenantId());
		this.log.info("cmpxProdClsfCd : " + req.getCmpxProdClsfCd());
		this.log.info("prodId : " + req.getProdId());
		this.log.info("possLendClsfCd : " + req.getPossLendClsfCd());
		this.log.info("deviceModelCd : " + req.getDeviceModelCd());
		this.log.info("langCd : " + req.getLangCd());
		this.log.info("episodeProdStatusCdList : " + req.getEpisodeProdStatusCdList().toString());
		this.log.info("----------------------------------------------------------------");
		
		for(int kk =0; kk < 1 ; kk++){
			if(StringUtils.isEmpty(req.getEpisodeProdStatusCdList().get(kk).toString())){
				req.setEpisodeProdStatusCdList(null);
			}
		}
		
		// / 단말 지원 정보 조회
		SupportDevice supportDevice = this.displayCommonService.getSupportDeviceInfo(req.getDeviceModelCd());

		if (supportDevice == null) {
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
		} else {
			paramMap.put("chapter", req.getChapter());
		}
		paramMap.put("seriesBookClsfCd", req.getSeriesBookClsfCd());
		paramMap.put("possLendClsfCd", req.getPossLendClsfCd());
		paramMap.put("cmpxProdClsfCd", req.getCmpxProdClsfCd());
		paramMap.put("episodeProdStatusCdList", req.getEpisodeProdStatusCdList());
		paramMap.put("supportDevice", supportDevice);
		paramMap.put("prodRshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
		paramMap.put("dpAnyPhone4mm", DisplayConstants.DP_ANY_PHONE_4MM);

		List<CmpxProductInfoList> cmpxProductInfoList = null;
		cmpxProductInfoList = this.commonDAO.queryForList("CmpxInfo.searchCmpxProductList", paramMap,
				CmpxProductInfoList.class);
		 for (CmpxProductInfoList cmpxProductInfo : cmpxProductInfoList) {
			 cmpxProductInfo.setUsePeriodSetCd(displayCommonService.getUsePeriodSetCd(cmpxProductInfo.getTopMenuId(), cmpxProductInfo.getProdId(), cmpxProductInfo.getDrmYn(),""));
		 }
		
		
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

		this.log.info("----------------------------------------------------------------");
		this.log.info("tenantId : " + req.getTenantId());
		this.log.info("langCd : " + req.getLangCd());
		this.log.info("prodId : " + req.getProdId());
		this.log.info("episodeProdId : " + req.getEpisodeProdId());
		this.log.info("chapter : " + req.getChapter());
		this.log.info("episodeProdStatusCdList : " + req.getEpisodeProdStatusCdList().toString());
		this.log.info("----------------------------------------------------------------");
		
		for(int kk =0; kk < 1 ; kk++){
			if(StringUtils.isEmpty(req.getEpisodeProdStatusCdList().get(kk).toString())){
				req.setEpisodeProdStatusCdList(null);
			}
		}		
		
		CmpxProductInfo cmpxProductInfo = null;
		cmpxProductInfo = this.commonDAO.queryForObject("CmpxInfo.searchCmpxProductInfo", req, CmpxProductInfo.class);

		if (cmpxProductInfo == null) {
			throw new StorePlatformException("SAC_DSP_0005", "이용권 " + req.getProdId());
		}
		cmpxProductInfo.setUsePeriodSetCd(displayCommonService.getUsePeriodSetCd(cmpxProductInfo.getTopMenuId(), cmpxProductInfo.getEpisodeProdId(), cmpxProductInfo.getDrmYn(),""));
		
		return cmpxProductInfo;
	}
}
