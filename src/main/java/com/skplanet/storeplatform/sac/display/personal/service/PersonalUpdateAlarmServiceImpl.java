/**
 * 
 */
package com.skplanet.storeplatform.sac.display.personal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceItemSc;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScRes;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalUpdateAlarmReq;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalUpdateAlarmRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.response.AppInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.purchase.history.service.ExistenceSacService;

/**
 * Application Update 알림 설정 Service 구현체
 * 
 * Updated on : 2014. 2. 10. Updated by : 오승민, 인크로스.
 */
@Service
public class PersonalUpdateAlarmServiceImpl implements PersonalUpdateAlarmService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private ExistenceSacService existenceSacService;

	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

	@Autowired
	private AppInfoGenerator appGenerator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.personal.service.PersonalUpdateAlarmService#updateAlarm(com.skplanet.
	 * storeplatform.sac.client.display.vo.personal.PersonalUpdateAlarmReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public PersonalUpdateAlarmRes updateAlarm(PersonalUpdateAlarmReq req, SacRequestHeader header) {

		CommonResponse commonResponse = new CommonResponse();
		PersonalUpdateAlarmRes res = new PersonalUpdateAlarmRes();
		// List<Product> productList = new ArrayList<Product>();
		Map<String, Object> mapReq = new HashMap<String, Object>();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		TenantHeader tenantHeader = header.getTenantHeader();

		String sAction = ObjectUtils.toString(mapReq.get("action"));
		// String sAlarms = ObjectUtils.toString(mapReq.get("ALARM_LIST"));

		String sType = req.getType();
		String sArrProds[] = StringUtils.split(req.getList(), "+");

		List<String> listProds = new ArrayList<String>();
		for (String s : sArrProds) {
			listProds.add(s);
		}

		List<String> listPid = new ArrayList<String>();
		if (sType.equals("package")) {

			// Oracle SQL 리터럴 수행 방지를 위한 예외처리
			int iListPkgSize = listProds.size();
			int iPkgLimited = 0;
			if (iListPkgSize < 300) {
				iPkgLimited = 300;
			} else if (iListPkgSize >= 300 && iListPkgSize < 500) {
				iPkgLimited = 500;
			} else if (iListPkgSize >= 500 && iListPkgSize < 700) {
				iPkgLimited = 700;
			} else if (iListPkgSize >= 700 && iListPkgSize < 1000) {
				iPkgLimited = 1000;
			}
			for (int i = iListPkgSize; i < iPkgLimited; i++) {
				listProds.add("");
			}
			mapReq.put("PKG_LIST", listProds);
			mapReq.put("deviceHeader", deviceHeader);
			mapReq.put("tenantHeader", tenantHeader);
			mapReq.put("contentsTypeCd", DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD);
			mapReq.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
			mapReq.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
			// List<Object> listPkg = queryForList("updateAlarm.getRecentFromPkgNm", mapReq);
			// Package 명으로 해지시 상품 ID 조회
			List<Map> listPkg = this.commonDAO.queryForList("PersonalUpdateProduct.searchRecentFromPkgNm", mapReq,
					Map.class);
			mapReq.remove("PKG_LIST");

			if (!listPkg.isEmpty()) {
				Map<String, Object> mapPkg = null;
				for (int i = 0; i < listPkg.size(); i++) {
					mapPkg = listPkg.get(i);
					listPid.add(ObjectUtils.toString(mapPkg.get("PROD_ID")));
				}
			}

		} else if (sType.equals("pid")) {
			listPid.addAll(listProds);
		}

		if (!listPid.isEmpty()) {
			// TODO osm1021 추후 LocalSCI 처리로 변환 필요
			// 기구매 체크
			ExistenceScReq existenceScReq = new ExistenceScReq();
			List<ExistenceItemSc> existenceItemScList = new ArrayList<ExistenceItemSc>();
			for (String prodId : listPid) {
				ExistenceItemSc existenceItemSc = new ExistenceItemSc();
				existenceItemSc.setProdId(prodId);
				existenceItemScList.add(existenceItemSc);
			}
			existenceScReq.setTenantId(tenantHeader.getTenantId());
			existenceScReq.setUserKey(req.getUserKey());
			existenceScReq.setDeviceKey(req.getDeviceKey());
			// existenceScReq.setExistenceItemSc(existenceItemScList);
			existenceScReq.setProductList(existenceItemScList);
			List<ExistenceScRes> listPrchs = this.existenceSacService.searchExistenceList(existenceScReq);

			if (!listPrchs.isEmpty()) {
				mapReq.put("PRCHS_LIST", listPrchs);
				// TODO osm1021 SQL 작성 필요
				// int iUpdateCnt = this.commonDAO.update("", mapReq);
				int iUpdateCnt = 10;
				if (sAction.equals("upgradeAlarmOn")) {
					this.log.info("## Alarm on success cnt : {}", iUpdateCnt);
					this.log.info("## Alarm on success prod: {}", listPrchs);
				} else {
					this.log.info("## Alarm off success cnt : {}", iUpdateCnt);
					this.log.info("## Alarm off success prod: {}", listPrchs);
				}
			} else {
				this.log.info("## No Alarm data");
			}

		} else {
			this.log.info("## No Alarm data");
		}

		res.setCommonResponse(commonResponse);
		return res;
	}

}
