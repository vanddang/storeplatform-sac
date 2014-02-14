/**
 * 
 */
package com.skplanet.storeplatform.sac.display.personal.service;

import java.math.BigDecimal;
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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.NumberUtils;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceItemSc;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScReq;
import com.skplanet.storeplatform.purchase.client.history.vo.ExistenceScRes;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalAutoUpdateReq;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalAutoUpdateRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.History;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Purchase;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Update;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.response.AppInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.purchase.history.service.ExistenceSacService;

/**
 * 자동 Update 목록 조회 Service 구현체
 * 
 * Updated on : 2014. 2. 10. Updated by : 오승민, 인크로스.
 */
@Service
public class PersonalAutoUpdateServiceImpl implements PersonalAutoUpdateService {
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
	 * @see
	 * com.skplanet.storeplatform.sac.display.personal.service.PersonalAutoUpgradeService#searchAutoUpgradeList(com.
	 * skplanet.storeplatform.sac.client.display.vo.personal.PersonalAutoUpgradeReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public PersonalAutoUpdateRes updateAutoUpdateList(PersonalAutoUpdateReq req, SacRequestHeader header) {

		CommonResponse commonResponse = new CommonResponse();
		PersonalAutoUpdateRes res = new PersonalAutoUpdateRes();
		List<Product> productList = new ArrayList<Product>();
		Map<String, Object> mapReq = new HashMap<String, Object>();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		TenantHeader tenantHeader = header.getTenantHeader();

		// 다운로드 서버 상태 조회는 & 앱 버전 정보 활용 조회 처리 & 업그레이드 관리이력 조회는 tenant 단에서 처리하기 때문에 제외

		String sPkgNms = ObjectUtils.toString(mapReq.get("PKG_NM_LIST"));
		// String sPolicy = ObjectUtils.toString(mapReq.get("policy"));
		// String sDigest = ObjectUtils.toString(mapReq.get("digest"));
		String sArrPkgNm[] = StringUtils.split(sPkgNms, ";");

		/**************************************************************
		 * Package 명으로 상품 조회
		 **************************************************************/
		List<String> listPkgNm = new ArrayList<String>();
		for (String s : sArrPkgNm) {
			listPkgNm.add(StringUtils.split(s, "/")[0]);
		}

		// Oracle SQL 리터럴 수행 방지를 위한 예외처리
		int iListPkgSize = listPkgNm.size();
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
			listPkgNm.add("");
		}
		mapReq.put("PKG_LIST", listPkgNm);
		mapReq.put("deviceHeader", deviceHeader);
		mapReq.put("tenantHeader", tenantHeader);
		mapReq.put("contentsTypeCd", DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD);
		mapReq.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
		mapReq.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);

		List<Map> listPkg = this.commonDAO.queryForList("PersonalUpdateProduct.searchRecentFromPkgNm", mapReq,
				Map.class);
		mapReq.remove("PKG_LIST");
		if (listPkg.isEmpty()) {
			throw new StorePlatformException("SAC_DSP_0006");
		} else {

			List<Object> listProd = new ArrayList<Object>();
			List<String> listPid = new ArrayList<String>();
			List<Map<String, Object>> listUpdate = new ArrayList<Map<String, Object>>();

			/**************************************************************
			 * Version Code 가 높은 Data 만 추출
			 **************************************************************/
			int iPkgVerCd = 0;
			String sPkgNm = "";
			Map<String, Object> mapPkg = null;
			for (int i = 0; i < listPkg.size(); i++) {
				mapPkg = listPkg.get(i);
				sPkgNm = ObjectUtils.toString(mapPkg.get("APK_PKG"));
				iPkgVerCd = NumberUtils.toInt(ObjectUtils.toString(mapPkg.get("APK_VER_CD")));
				String sArrPkgInfo[] = null;
				for (String s : sArrPkgNm) {
					sArrPkgInfo = StringUtils.split(s, "/");
					// 단말보다 Version Code 가 높은경우
					if (sPkgNm.equals(sArrPkgInfo[0])) {
						if (iPkgVerCd > NumberUtils.toInt(sArrPkgInfo[1])) {
							listProd.add(mapPkg);
							listPid.add(ObjectUtils.toString(mapPkg.get("PROD_ID")));
						}
						break;
					}
				}
			}

			/**************************************************************
			 * 구매여부 및 최근 업데이트 정보 추출
			 **************************************************************/
			if (!listPid.isEmpty()) {

				// Oracle SQL 리터럴 수행 방지를 위한 예외처리
				// int iListPidSize = listPid.size();
				// int iPidLimited = 0;
				// if (iListPidSize < 300) {
				// iPidLimited = 300;
				// } else if (iListPidSize >= 300 && iListPidSize < 500) {
				// iPidLimited = 500;
				// } else if (iListPidSize >= 500 && iListPidSize < 700) {
				// iPidLimited = 700;
				// } else if (iListPidSize >= 700 && iListPidSize < 1000) {
				// iPidLimited = 1000;
				// }
				// for (int i = iListPidSize; i < iPidLimited; i++) {
				// listPid.add("");
				// }
				// mapReq.put("PID_LIST", listPid);

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
				existenceScReq.setExistenceItemSc(existenceItemScList);
				List<ExistenceScRes> listPrchs = this.existenceSacService.searchExistenceList(existenceScReq);

				// List<Object> listPrchs = queryForList("updateAlarm.getPrchsInfo", mapReq);
				if (!listPrchs.isEmpty()) {
					String sPid = "";
					Map<String, Object> mapUpdate = null;
					for (int i = 0; i < listProd.size(); i++) {
						mapUpdate = (Map<String, Object>) listProd.get(i);
						sPid = ObjectUtils.toString(mapUpdate.get("PROD_ID"));
						// Map<String, String> mapPrchs = null;

						// 구매내역이 존재하는 경우만
						for (ExistenceScRes prchInfo : listPrchs) {
							// for (int j = 0; j < listPrchs.size(); j++) {
							if (sPid.equals(prchInfo.getProdId())) {
								mapUpdate.put("PRCHS_ID", prchInfo.getPrchsId());
								listUpdate.add(mapUpdate);
								break;
							}
						}
					}
				}

				if (listUpdate.isEmpty()) {
					throw new StorePlatformException("SAC_DSP_0006");
				}

				Integer restCnt = req.getUpdLimitCnt();
				// 업데이트 제한 기능이 필요할 경우
				if (restCnt > 0) {
					if (listUpdate.size() > restCnt) {
						listUpdate = listUpdate.subList(0, restCnt);
					}
				}

				// Response 정보 가공
				for (Map<String, Object> updateTargetApp : listUpdate) {
					Product product = new Product();
					History history = new History();
					List<Update> updateList = new ArrayList<Update>();
					List<Source> sourceList = new ArrayList<Source>();

					String prchId = (String) updateTargetApp.get("PRCHS_ID");
					List<Identifier> identifierList = this.appGenerator.generateIdentifierList(
							DisplayConstants.DP_EPISODE_IDENTIFIER_CD, (String) updateTargetApp.get("PROD_ID"));
					product.setIdentifierList(identifierList);

					Title title = new Title();
					title.setText((String) updateTargetApp.get("PROD_NM"));
					product.setTitle(title);

					// 구매 정보는 구매 내역이 있는 App만 표시한다.
					if (!StringUtils.isEmpty(prchId)) {
						Purchase purchage = this.commonGenerator.generatePurchase(
								(String) updateTargetApp.get("PRCHS_ID"), null, null);
						product.setPurchase(purchage);
					}

					App app = this.appGenerator.generateApp((String) updateTargetApp.get("AID"),
							(String) updateTargetApp.get("APK_PKG_NM"),
							ObjectUtils.toString(updateTargetApp.get("APK_VER")),
							ObjectUtils.toString(updateTargetApp.get("PROD_VER")),
							((BigDecimal) updateTargetApp.get("FILE_SIZE")).intValue(), null, null, null);

					app.setHistory(history);
					product.setApp(app);
					productList.add(product);
				}

				commonResponse.setTotalCount(productList.size());
				res.setCommonResponse(commonResponse);
				res.setProductList(productList);
				// Log.info("[업데이트 내역] " + listUpdate);
				// ods.putRes("0", listUpdate);

			} else {
				throw new StorePlatformException("SAC_DSP_0006");
			}
		}
		return res;
	}
}
