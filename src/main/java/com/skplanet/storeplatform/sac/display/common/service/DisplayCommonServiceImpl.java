package com.skplanet.storeplatform.sac.display.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.purchase.client.history.sci.ExistenceSCI;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceItemSc;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScRes;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.vo.BatchStandardDateRequest;
import com.skplanet.storeplatform.sac.display.common.vo.MenuItem;
import com.skplanet.storeplatform.sac.display.common.vo.MenuItemReq;

/**
 * 전시 공통 서비스
 * 
 * Updated on : 2014. 01. 07 Updated by : 정희원, SK 플래닛.
 */
@Service
public class DisplayCommonServiceImpl implements DisplayCommonService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private ExistenceSCI existenceSCI;

    @Value("#{propertiesForSac['display.previewUrlPrefix']}")
    private String previewPrefix = "";

	@Override
	public String getBatchStandardDateString(String tenantId, String listId) {

		return (String) this.commonDAO.queryForObject("Common.getBatchStandardDate", new BatchStandardDateRequest(
				tenantId, listId));
	}

	@Override
	public List<MenuItem> getMenuItemList(String prodId, String langCd) {

		return this.commonDAO.queryForList("Common.getMenuItemList", new MenuItemReq(prodId, langCd), MenuItem.class);
	}

	@Override
	public String getResolutionCode(String resolution) {

		String[] resolutionTemp = resolution.trim().split("\\*");

		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("pixel1", resolutionTemp[0]);
		paramMap.put("pixel2", resolutionTemp[1]);

		return (String) this.commonDAO.queryForObject("Common.getResolutionCode", paramMap);
	}

	@Override
	public String getDeviceIdType(String deviceId) {

		if (!StringUtils.isEmpty(deviceId)) {

			String repDeviceId = deviceId.replaceAll("-", "");
			String subStrDeviceId = repDeviceId.substring(0, 2);
			Boolean isNum = deviceId.matches("^[0-9]+$");
			String deviceType = "";

			if (repDeviceId.length() < 12 && (subStrDeviceId.equals("01") || subStrDeviceId.equals("98"))) {
				deviceType = DisplayConstants.DP_DEVICE_ID_TYPE_MSISDN;
			} else if (!isNum && deviceId.length() == 12) {
				deviceType = DisplayConstants.DP_DEVICE_ID_TYPE_MAC;
			} else {
				deviceType = DisplayConstants.DP_DEVICE_ID_TYPE_UUID;
			}

			return deviceType;
		} else {
			return "";
		}

	}

	@Override
	public boolean checkPurchase(String tenantId, String userKey, String deviceKey, String episodeId) {
		ExistenceScReq existenceScReq = new ExistenceScReq();
		existenceScReq.setTenantId(tenantId);
		existenceScReq.setUserKey(userKey);
		existenceScReq.setDeviceKey(deviceKey);
		ExistenceItemSc itemSc = new ExistenceItemSc();
		itemSc.setProdId(episodeId);
		List<ExistenceItemSc> itemScList = new ArrayList<ExistenceItemSc>();
		itemScList.add(itemSc);
		existenceScReq.setProductList(itemScList);

		this.log.debug("##### [SAC DSP LocalSCI] SAC Purchase Start : existenceSCI.searchExistenceList");
		long start = System.currentTimeMillis();
		List<ExistenceScRes> resList = this.existenceSCI.searchExistenceList(existenceScReq);
		this.log.debug("##### [SAC DSP LocalSCI] SAC Purchase End : existenceSCI.searchExistenceList");
		long end = System.currentTimeMillis();
		this.log.debug("##### [SAC DSP LocalSCI] SAC Purchase existenceSCI.searchExistenceList takes {} ms",
				(end - start));
		return resList != null && resList.size() > 0;
	}

	@Override
	public List<ExistenceScRes> checkPurchaseList(String tenantId, String userKey, String deviceKey,
			List<String> episodeIdList) {
		ExistenceScReq existenceScReq = new ExistenceScReq();
		existenceScReq.setTenantId(tenantId);
		existenceScReq.setUserKey(userKey);
		existenceScReq.setDeviceKey(deviceKey);

		List<ExistenceItemSc> itemScList = new ArrayList<ExistenceItemSc>();
		for (String episodeId : episodeIdList) {
			ExistenceItemSc itemSc = new ExistenceItemSc();
			itemSc.setProdId(episodeId);
			itemScList.add(itemSc);
		}
		existenceScReq.setProductList(itemScList);

		this.log.debug("##### [SAC DSP LocalSCI] SAC Purchase Start : existenceSCI.searchExistenceList");
		long start = System.currentTimeMillis();
		List<ExistenceScRes> existenceListRes = this.existenceSCI.searchExistenceList(existenceScReq);
		this.log.debug("##### [SAC DSP LocalSCI] SAC Purchase End : existenceSCI.searchExistenceList");
		long end = System.currentTimeMillis();
		this.log.debug("##### [SAC DSP LocalSCI] SAC Purchase existenceSCI.searchExistenceList takes {} ms",
				(end - start));

		return existenceListRes;
	}

    @Override
    public String makePreviewUrl(String phyPath) {
        if(StringUtils.isNotEmpty(phyPath))
            return previewPrefix + phyPath;
        else
            return "";
    }
}
