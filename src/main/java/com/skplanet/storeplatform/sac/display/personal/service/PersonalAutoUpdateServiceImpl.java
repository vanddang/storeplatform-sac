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
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalAutoUpdateReq;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalAutoUpdateRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.sci.ExistenceInternalSacSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceItem;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceListRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.vo.ExistenceRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
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

	@Autowired
	SearchUserSCI searchUserSCI;

	@Autowired
	ExistenceInternalSacSCI existenceInternalSacSCI;

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
		List<ExistenceRes> listPrchs = null;

		// 다운로드 서버 상태 조회는 & 앱 버전 정보 활용 조회 처리 & 업그레이드 관리이력 조회는 tenant 단에서 처리하기 때문에 제외

		// String sPkgNms = ObjectUtils.toString(mapReq.get("PKG_NM_LIST"));
		// String sPolicy = ObjectUtils.toString(mapReq.get("policy"));
		// String sDigest = ObjectUtils.toString(mapReq.get("digest"));
		String sArrPkgNm[] = StringUtils.split(req.getPackageInfo(), "+");

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
		this.log.debug("##### auto update target list  : {}", listPkg);
		this.log.debug("##### auto update target cnt   : {}", listPkg.size());
		if (listPkg.isEmpty()) {
			throw new StorePlatformException("SAC_DSP_0006");
		} else {

			List<Map<String, Object>> listProd = new ArrayList<Map<String, Object>>();
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
				sPkgNm = ObjectUtils.toString(mapPkg.get("APK_PKG_NM"));
				iPkgVerCd = NumberUtils.toInt(ObjectUtils.toString(mapPkg.get("APK_VER")));
				String sArrPkgInfo[] = null;
				for (String s : sArrPkgNm) {
					sArrPkgInfo = StringUtils.split(s, "/");
					this.log.debug("###########################################");
					this.log.debug("##### {}'s server version is {} !!!!!!!!!!", sPkgNm, iPkgVerCd);
					this.log.debug("##### {}'s user   version is {} !!!!!!!!!!", sPkgNm, sArrPkgInfo[1]);
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

			try {
				this.log.debug("##### check user status");
				SearchUserSacReq searchUserSacReq = new SearchUserSacReq();
				searchUserSacReq.setUserKey(req.getUserKey());
				SearchUserSacRes searchUserSacRes = this.searchUserSCI.searchUserByUserKey(searchUserSacReq);
				String userMainStatus = searchUserSacRes.getUserMainStatus();
				this.log.debug("##### userMainStatus :: {} " + userMainStatus);
				// TODO osm1021 예외 처리 및 pass가 안 될때 처리 정리 필요
				// 정상 일시 정지 회원이 아닐 경우 -> 구매 내역이 없는 것으로 간주하고 Update 대상 무료 앱만 Response한다.
				if (DisplayConstants.MEMBER_MAIN_STATUS_NORMAL.equals(userMainStatus)
						|| DisplayConstants.MEMBER_MAIN_STATUS_PAUSE.equals(userMainStatus)) {
					this.log.debug("##### This user is normal user!!!!");
				} else {
					this.log.debug("##### This user is unnormal user!!!!");
					throw new StorePlatformException("SAC_DSP_0006");
				}
			} catch (Exception e) {
				throw new StorePlatformException("SAC_DSP_1002", e);
			}

			/**************************************************************
			 * 구매여부 및 최근 업데이트 정보 추출
			 **************************************************************/
			if (!listPid.isEmpty()) {

				// 기구매 체크
				try {
					ExistenceReq existenceReq = new ExistenceReq();
					List<ExistenceItem> existenceItemList = new ArrayList<ExistenceItem>();
					for (String prodId : listPid) {
						ExistenceItem existenceItem = new ExistenceItem();
						existenceItem.setProdId(prodId);
						existenceItemList.add(existenceItem);

					}
					existenceReq.setTenantId(tenantHeader.getTenantId());
					existenceReq.setUserKey(req.getUserKey());
					existenceReq.setDeviceKey(req.getDeviceKey());
					existenceReq.setExistenceItem(existenceItemList);

					ExistenceListRes existenceListRes = this.existenceInternalSacSCI.searchExistenceList(existenceReq);
					listPrchs = existenceListRes.getExistenceListRes();
				} catch (Exception e) {
					throw new StorePlatformException("SAC_DSP_2002", e);
				}
				if (!listPrchs.isEmpty()) {
					String sPid = "";
					Map<String, Object> mapUpdate = null;
					for (int i = 0; i < listProd.size(); i++) {
						mapUpdate = listProd.get(i);
						sPid = ObjectUtils.toString(mapUpdate.get("PROD_ID"));
						// Map<String, String> mapPrchs = null;

						// 구매내역이 존재하는 경우만
						for (ExistenceRes prchInfo : listPrchs) {
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
					String prchId = (String) updateTargetApp.get("PRCHS_ID");
					List<Update> updateList = new ArrayList<Update>();
					List<Identifier> identifierList = this.appGenerator.generateIdentifierList(
							DisplayConstants.DP_EPISODE_IDENTIFIER_CD, (String) updateTargetApp.get("PROD_ID"));
					product.setIdentifierList(identifierList);

					List<Menu> menuList = this.appGenerator.generateMenuList(
							(String) updateTargetApp.get("TOP_MENU_ID"), (String) updateTargetApp.get("TOP_MENU_NM"),
							(String) updateTargetApp.get("MENU_ID"), (String) updateTargetApp.get("MENU_NM"));
					product.setMenuList(menuList);

					Title title = new Title();
					title.setText((String) updateTargetApp.get("PROD_NM"));
					product.setTitle(title);

					// 구매 정보는 구매 내역이 있는 App만 표시한다.
					if (!StringUtils.isEmpty(prchId)) {
						Purchase purchage = this.commonGenerator.generatePurchase(
								(String) updateTargetApp.get("PRCHS_ID"), (String) updateTargetApp.get("PROD_ID"),
								null, null, null);
						product.setPurchase(purchage);
					}

					App app = this.appGenerator.generateApp((String) updateTargetApp.get("AID"),
							(String) updateTargetApp.get("APK_PKG_NM"),
							ObjectUtils.toString(updateTargetApp.get("APK_VER")),
							ObjectUtils.toString(updateTargetApp.get("PROD_VER")),
							((BigDecimal) updateTargetApp.get("FILE_SIZE")).intValue(), null, null, null);

					Update update = this.appGenerator.generateUpdate(
							new Date(null, ObjectUtils.toString(updateTargetApp.get("UPD_DT"))), null);
					updateList.add(update);
					history.setUpdate(updateList);
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
