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

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.NumberUtils;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalAutoUpdateReq;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalAutoUpdateRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserInfoSac;
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
import com.skplanet.storeplatform.sac.common.util.DateUtils;
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
	 * com.skplanet.storeplatform.sac.display.personal.service.PersonalAutoUpdateService#updateAutoUpdateList(com.skplanet
	 * .storeplatform.sac.client.display.vo.personal.PersonalAutoUpdateReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader, java.util.List)
	 */
	@Override
	public PersonalAutoUpdateRes updateAutoUpdateList(PersonalAutoUpdateReq req, SacRequestHeader header,
			List<String> packageInfoList) {

		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.log_id("TL00038");
			}
		});

		CommonResponse commonResponse = new CommonResponse();
		PersonalAutoUpdateRes res = new PersonalAutoUpdateRes();
		List<Product> productList = new ArrayList<Product>();
		Map<String, Object> mapReq = new HashMap<String, Object>();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		TenantHeader tenantHeader = header.getTenantHeader();
		List<ExistenceRes> listPrchs = null;
		final List<String> forTlogAppIdList = new ArrayList<String>();

		// 다운로드 서버 상태 조회는 & 앱 버전 정보 활용 조회 처리 & 업그레이드 관리이력 조회는 tenant 단에서 처리하기 때문에 제외

		/**************************************************************
		 * Package 명으로 상품 조회
		 **************************************************************/
		List<String> listPkgNm = new ArrayList<String>();
		for (String s : packageInfoList) {
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

		List<Map> updateTargetList = this.commonDAO.queryForList("PersonalUpdateProduct.searchRecentFromPkgNm", mapReq,
				Map.class);
		mapReq.remove("PKG_LIST");

		this.log.debug("##### auto update target list  : {}", updateTargetList);
		this.log.debug("##### auto update target cnt   : {}", updateTargetList.size());

		List<Map<String, Object>> listPkg = new ArrayList<Map<String, Object>>();

		for (Map<String, Object> updateTargetMap : updateTargetList) {
			updateTargetMap.put("deviceHeader", deviceHeader);
			updateTargetMap.put("tenantHeader", tenantHeader);
			updateTargetMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
			updateTargetMap.put("prodStatusCd", DisplayConstants.DP_SALE_STAT_ING);
			updateTargetMap.put("prodId", updateTargetMap.get("PROD_ID"));
			updateTargetMap.put("subContentsId", updateTargetMap.get("SUB_CONTENTS_ID"));
			updateTargetMap.put("contentsTypeCd", DisplayConstants.DP_EPISODE_CONTENT_TYPE_CD);
			updateTargetMap.put("svcGrpCd", DisplayConstants.DP_APP_PROD_SVC_GRP_CD);

			Map<String, Object> appInfoMap = this.commonDAO.queryForObject("PersonalUpdateProduct.getAppInfo",
					updateTargetMap, Map.class);
			if (appInfoMap != null) {
				listPkg.add(appInfoMap);
			}
		}

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
				for (String s : packageInfoList) {
					sArrPkgInfo = StringUtils.split(s, "/");
					this.log.debug("###########################################");
					this.log.debug("##### {}'s server version is {} !!!!!!!!!!", sPkgNm, iPkgVerCd);
					this.log.debug("##### {}'s user   version is {} !!!!!!!!!!", sPkgNm, sArrPkgInfo[1]);
					// 단말보다 Version Code 가 높은경우
					if (sPkgNm.equals(sArrPkgInfo[0])) {
						if (iPkgVerCd > NumberUtils.toInt(sArrPkgInfo[1])) {
							this.log.debug("##### Add to update target!!!!!!!!!");
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

				this.log.debug("##### check user status");
				String userKey = req.getUserKey();
				this.log.debug("##### userKey :: {} " + userKey);
				SearchUserSacReq searchUserSacReq = new SearchUserSacReq();
				List<String> userKeyList = new ArrayList<String>();
				userKeyList.add(userKey);
				searchUserSacReq.setUserKeyList(userKeyList);
				this.log.info("##### [SAC DSP LocalSCI] SAC Member Start : searchUserSCI.searchUserByUserKey");
				long start = System.currentTimeMillis();
				SearchUserSacRes searchUserSacRes = this.searchUserSCI.searchUserByUserKey(searchUserSacReq);
				this.log.info("##### [SAC DSP LocalSCI] SAC Member End : searchUserSCI.searchUserByUserKey");
				long end = System.currentTimeMillis();
				this.log.info("##### [SAC DSP LocalSCI] SAC Member searchUserSCI.searchUserByUserKey takes {} ms",
						(end - start));
				Map<String, UserInfoSac> userInfo = searchUserSacRes.getUserInfo();
				UserInfoSac userInfoSac = userInfo.get(userKey);
				String userMainStatus = userInfoSac.getUserMainStatus();

				this.log.debug("##### userMainStatus :: {} " + userMainStatus);
				// 정상 일시 정지 회원이 아닐 경우 -> '업데이트 내역이 없습니다.' 에러 발생
				// 탈퇴 회원일 경우 -> 회원에서 '탈퇴한 회원입니다.'에러 발생하여 그대로 throw 함
				if (DisplayConstants.MEMBER_MAIN_STATUS_NORMAL.equals(userMainStatus)
						|| DisplayConstants.MEMBER_MAIN_STATUS_PAUSE.equals(userMainStatus)) {
					this.log.debug("##### This user is normal user!!!!");
				} else {
					this.log.debug("##### This user is unnormal user!!!!");
					throw new StorePlatformException("SAC_DSP_0006");
				}

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

					this.log.info("##### [SAC DSP LocalSCI] SAC Purchase Start : existenceInternalSacSCI.searchExistenceList");
					start = System.currentTimeMillis();
					ExistenceListRes existenceListRes = this.existenceInternalSacSCI.searchExistenceList(existenceReq);
					this.log.info("##### [SAC DSP LocalSCI] SAC Purchase End : existenceInternalSacSCI.searchExistenceList");
					end = System.currentTimeMillis();
					this.log.info(
							"##### [SAC DSP LocalSCI] SAC Purchase existenceInternalSacSCI.searchExistenceList {} ms",
							(end - start));
					listPrchs = existenceListRes.getExistenceListRes();
					this.log.debug("##### Purchase check result size : {}", listPrchs.size());
					this.log.debug("##### Purchase check result  : {}", listPrchs);
				} catch (Exception e) {
					// Exception 무시
					this.log.error("Exception has occured using existence purchase!!!!!!!!!!!", e);
				}
				if (listPrchs != null) {
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
									sPkgNm = ObjectUtils.toString(mapUpdate.get("APK_PKG_NM"));
									this.log.debug(
											"#####  Exist purchase history of {}! Hence add purchase id to update target!!!!!!!!!",
											sPkgNm);
									mapUpdate.put("PRCHS_ID", prchInfo.getPrchsId());
									listUpdate.add(mapUpdate);
									forTlogAppIdList.add(sPid);
									break;
								}
							}
						}
					}
				}
				if (listUpdate.isEmpty()) {
					throw new StorePlatformException("SAC_DSP_0006");
				}

				Integer restCnt = req.getUpdLimitCnt();
				// 업데이트 제한 기능이 필요할 경우
				if (restCnt != null) {
					if (restCnt > 0) {
						if (listUpdate.size() > restCnt) {
							listUpdate = listUpdate.subList(0, restCnt);
						}
					}
				}

				new TLogUtil().set(new ShuttleSetter() {
					@Override
					public void customize(TLogSentinelShuttle shuttle) {
						shuttle.app_id(forTlogAppIdList);
					}
				});
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
							((BigDecimal) updateTargetApp.get("FILE_SIZE")).intValue(), null, null,
							ObjectUtils.toString(updateTargetApp.get("FILE_PATH")));

					Update update = this.appGenerator.generateUpdate(
							new Date(null, DateUtils.parseDate(ObjectUtils.toString(updateTargetApp.get("UPD_DT")))),
							null);
					updateList.add(update);
					history.setUpdate(updateList);
					app.setHistory(history);
					product.setApp(app);
					productList.add(product);
				}

				commonResponse.setTotalCount(productList.size());
				res.setCommonResponse(commonResponse);
				res.setProductList(productList);
			} else {
				throw new StorePlatformException("SAC_DSP_0006");
			}
		}
		return res;
	}
}
