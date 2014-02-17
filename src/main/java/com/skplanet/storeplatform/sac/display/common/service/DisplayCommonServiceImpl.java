package com.skplanet.storeplatform.sac.display.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
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

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

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

		String repDeviceId = deviceId.replaceAll("-", "");
		String subStrDeviceId = repDeviceId.substring(0, 2);
		Boolean isNum = deviceId.matches("^[0-9]+$");
		String deviceType = "";

		if (repDeviceId.length() < 12 && subStrDeviceId.equals("01")) {
			deviceType = DisplayConstants.DP_DEVICE_ID_TYPE_MSISDN;
		} else if (!isNum && deviceId.length() == 12) {
			deviceType = DisplayConstants.DP_DEVICE_ID_TYPE_MAC;
		} else {
			deviceType = DisplayConstants.DP_DEVICE_ID_TYPE_UUID;
		}

		return deviceType;
	}
}
