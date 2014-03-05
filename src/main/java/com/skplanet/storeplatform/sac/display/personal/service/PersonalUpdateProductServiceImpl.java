/**
 * 
 */
package com.skplanet.storeplatform.sac.display.personal.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.NumberUtils;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalUpdateProductReq;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalUpdateProductRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.SearchUserSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserInfoSac;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistorySacIn;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.ProductListSacIn;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Price;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Title;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.History;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Purchase;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Rights;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Update;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.response.AppInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.purchase.history.service.ExistenceSacService;

/**
 * 업데이트 대상 목록 조회 Service 구현체
 * 
 * Updated on : 2014. 2. 10. Updated by : 오승민, 인크로스.
 */
@Service
public class PersonalUpdateProductServiceImpl implements PersonalUpdateProductService {
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
	HistoryInternalSCI historyInternalSCI;

	@Autowired
	SearchUserSCI searchUserSCI;

	@Autowired
	MetaInfoService metaInfoService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.personal.service.PersonalUpgradeProductService#searchUpdateProductList
	 * (com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalUpgradeProductReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public PersonalUpdateProductRes searchUpdateProductList(PersonalUpdateProductReq req, SacRequestHeader header) {
		PersonalUpdateProductRes res = new PersonalUpdateProductRes();
		CommonResponse commonResponse = new CommonResponse();
		List<Product> productList = new ArrayList<Product>();
		Map<String, Object> mapReq = new HashMap<String, Object>();
		DeviceHeader deviceHeader = header.getDeviceHeader();
		TenantHeader tenantHeader = header.getTenantHeader();
		List<HistorySacIn> listPrchs = null;
		boolean isNormalUser = false;

		String memberType = req.getMemberType();
		/**************************************************************
		 * Package 명으로 상품 조회
		 **************************************************************/
		String sArrPkgNm[] = StringUtils.split(req.getPackageInfo(), "+");
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
		// List<Object> listPkg = queryForList("updateAlarm.getRecentFromPkgNm", mapReq);
		List<Map> updateTargetList = this.commonDAO.queryForList("PersonalUpdateProduct.searchRecentFromPkgNm", mapReq,
				Map.class);
		mapReq.remove("PKG_LIST");

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

		this.log.debug("##### update target list  : {}", listPkg);
		this.log.debug("##### update target cnt   : {}", listPkg.size());

		if (listPkg.isEmpty()) {
			throw new StorePlatformException("SAC_DSP_0006");
		} else {

			List<Map<String, Object>> listProd = new ArrayList<Map<String, Object>>();
			List<String> listPid = new ArrayList<String>();

			/**************************************************************
			 * Version Code 가 높은 Data 만 추출
			 **************************************************************/
			int iPkgVerCd = 0;
			String sPkgNm = "";
			String sFakeYn = "";
			Map<String, Object> mapPkg = null;

			for (int i = 0; i < listPkg.size(); i++) {
				mapPkg = listPkg.get(i);

				mapPkg.put("APK_VER_CD_TMP", mapPkg.get("APK_VER"));

				sPkgNm = ObjectUtils.toString(mapPkg.get("APK_PKG_NM"));
				sFakeYn = ObjectUtils.toString(mapPkg.get("FAKE_YN"));
				iPkgVerCd = NumberUtils.toInt(ObjectUtils.toString(mapPkg.get("APK_VER")));

				String sArrPkgInfo[] = null;
				int iReqPkgVerCd = 0;

				for (String s : sArrPkgNm) {
					sArrPkgInfo = StringUtils.split(s, "/");
					if (sPkgNm.equals(sArrPkgInfo[0])) {
						iReqPkgVerCd = NumberUtils.toInt(sArrPkgInfo[1]);

						// 단말에서 올라온 VERSION_CODE, installer 셋팅
						mapPkg.put("REQ_APK_VER_CD", sArrPkgInfo[1]);
						mapPkg.put("REQ_INSTALLER", ((sArrPkgInfo.length > 2) ? sArrPkgInfo[2] : ""));
						this.log.debug("###########################################");
						this.log.debug("##### {}'s server version is {} !!!!!!!!!!", sPkgNm, iPkgVerCd);
						this.log.debug("##### {}'s user   version is {} !!!!!!!!!!", sPkgNm, iReqPkgVerCd);
						this.log.debug("##### Is fake update?????????? : {} ", sFakeYn);
						if (sFakeYn.equals("Y")) {
							// Fake Update 대상일 경우( 단말 Version <= 서버 Version )
							if (iPkgVerCd >= iReqPkgVerCd) {
								this.log.debug("##### is fake update target? : {}", sPkgNm);
								// Version 이 동일할 경우 +1
								if (iPkgVerCd == iReqPkgVerCd) {
									this.log.debug("##### fake update target & same version !!!!!!!!!");
									mapPkg.put("APK_VER_CD", Integer.toString((iPkgVerCd + 1)));
									this.log.debug("##### fake update target version up from {} to {}", iPkgVerCd,
											iPkgVerCd + 1);
								}
								listProd.add(mapPkg);
								listPid.add(ObjectUtils.toString(mapPkg.get("PROD_ID")));
							}
						} else {
							// 일반 업데이트 대상 ( 단말 Version < 서버 Version )
							if (iPkgVerCd > iReqPkgVerCd) {
								this.log.debug("##### is fake update target? : {}", sPkgNm);
								listProd.add(mapPkg);
								listPid.add(ObjectUtils.toString(mapPkg.get("PROD_ID")));
							} else {
								this.log.debug("##### {} is not update target ", sPkgNm);
							}
						}
						break;
					}
				}
			}

			/**************************************************************
			 * 업데이트 목록 가공
			 **************************************************************/
			List<Map<String, Object>> listUpdate = new ArrayList<Map<String, Object>>();
			// ◆ 회원
			// - 구매내역 존재 : 유료상품만 포함
			// - 구매내역 미존재 : 무료상품만 포함
			// ◆ 비회원
			// - 전체상품 포함
			if (!listPid.isEmpty()) {

				if (memberType.equals("updatedList")) {
					this.log.debug("##### Tstore user process start!!!!!!!!!");
					// 회원일 경우 회원 상태 조회
					try {
						this.log.debug("##### check user status");
						String userKey = req.getUserKey();
						this.log.debug("##### userKey :: {} " + userKey);
						SearchUserSacReq searchUserSacReq = new SearchUserSacReq();
						List<String> userKeyList = new ArrayList<String>();
						userKeyList.add(userKey);
						searchUserSacReq.setUserKeyList(userKeyList);
						SearchUserSacRes searchUserSacRes = this.searchUserSCI.searchUserByUserKey(searchUserSacReq);
						Map<String, UserInfoSac> userInfo = searchUserSacRes.getUserInfo();
						UserInfoSac userInfoSac = userInfo.get(userKey);
						String userMainStatus = userInfoSac.getUserMainStatus();
						this.log.debug("##### userMainStatus :: {} " + userMainStatus);
						// TODO osm1021 예외 처리 및 pass가 안 될때 처리 정리 필요
						// 정상 일시 정지 회원이 아닐 경우 -> 구매 내역이 없는 것으로 간주하고 Update 대상 무료 앱만 Response한다.
						if (DisplayConstants.MEMBER_MAIN_STATUS_NORMAL.equals(userMainStatus)
								|| DisplayConstants.MEMBER_MAIN_STATUS_PAUSE.equals(userMainStatus)) {
							isNormalUser = true;
							this.log.debug("##### This user is normal user!!!!");
						} else {
							this.log.debug("##### This user is unnormal user!!!! Skip the purchase check!!!!!!!");
						}
					} catch (Exception e) {
						throw new StorePlatformException("SAC_DSP_1002", e);
					}

					if (isNormalUser) {
						try {
							this.log.debug("##### Purchase check start!!!!!!!!!");
							List<ProductListSacIn> productListSacInList = new ArrayList<ProductListSacIn>();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
							String endDate = sdf.format(new java.util.Date());

							this.log.debug("##### endDate :: {}", endDate);
							for (String prodId : listPid) {
								ProductListSacIn productListSacIn = new ProductListSacIn();
								productListSacIn.setProdId(prodId);
								productListSacInList.add(productListSacIn);
							}

							HistoryListSacInReq historyListSacReq = new HistoryListSacInReq();
							historyListSacReq.setTenantId(tenantHeader.getTenantId());
							historyListSacReq.setUserKey(req.getUserKey());
							historyListSacReq.setDeviceKey(req.getDeviceKey());
							historyListSacReq.setPrchsProdHaveYn(DisplayConstants.PRCHS_PROD_HAVE_YES);
							historyListSacReq.setStartDt(DisplayConstants.PRCHS_START_DATE);
							historyListSacReq.setEndDt(endDate);
							historyListSacReq.setOffset(1);
							historyListSacReq.setCount(1000);
							historyListSacReq.setProductList(productListSacInList);

							// 구매내역 조회 실행
							HistoryListSacInRes historyListSacRes = this.historyInternalSCI
									.searchHistoryList(historyListSacReq);
							if (historyListSacRes != null) {
								listPrchs = historyListSacRes.getHistoryList();
								this.log.debug("##### Purchase check result size : {}", listPrchs.size());
								this.log.debug("##### Purchase check result  : {}", listPrchs);
							} else {
								this.log.debug("##### No purchase result!!");
							}
						} catch (Exception e) {
							throw new StorePlatformException("SAC_DSP_2001", e);
						}
					}
					// mapReq.remove("PID_LIST");

					String sProdAmt = "";
					if (listPrchs != null) {

						int iReqPkgVerCd = 0;
						String sPid = "";
						String sInstaller = "";
						String sPackageNm = null;
						for (int i = 0; i < listProd.size(); i++) {

							mapPkg = listProd.get(i);
							sPid = ObjectUtils.toString(mapPkg.get("PROD_ID"));
							sFakeYn = ObjectUtils.toString(mapPkg.get("FAKE_YN"));
							sProdAmt = ObjectUtils.toString(mapPkg.get("PROD_AMT"));
							iPkgVerCd = NumberUtils.toInt(ObjectUtils.toString(mapPkg.get("APK_VER_CD_TMP")));
							iReqPkgVerCd = NumberUtils.toInt(ObjectUtils.toString(mapPkg.get("REQ_APK_VER_CD")));
							sInstaller = ObjectUtils.toString(mapPkg.get("REQ_INSTALLER"));
							sPackageNm = ObjectUtils.toString(mapPkg.get("APK_PKG_NM"));
							// 구매내역이 존재하는 경우 구매 ID Setting
							boolean isPrchsExists = false;
							// for (int j = 0; j < listPrchs.size(); j++) {
							for (HistorySacIn prchInfo : listPrchs) {
								// 구매한 상품의 상품 Id
								String prchProdId = prchInfo.getProdId();
								// 구매한 상품의 구매 Id
								String prchID = prchInfo.getPrchsId();
								if (sPid.equals(prchProdId)) {
									this.log.debug("##### Hit purchase app & update target !!!!!!!!!!!!");
									this.log.debug("##### Purchased app's detail info");
									this.log.debug("##### Package Name : {} ", sPackageNm);
									this.log.debug("##### Pid : {}", sPid);
									this.log.debug("##### FakeYn : {}", sFakeYn);
									this.log.debug("##### App price : {}", sProdAmt);
									this.log.debug("##### Installer : {}", sInstaller);
									this.log.debug("##### Server app version : {}", iPkgVerCd);
									this.log.debug("##### User app version : {}", iReqPkgVerCd);

									String alarmYn = prchInfo.getAlarmYn();
									this.log.debug("##### Alarm Yn : {}", alarmYn);
									// 알람 설정이 Y인 app들만 업데이트 목록에 추가
									if ("Y".equals(alarmYn)) {
										// 구매내역 존재 시 업데이트 목록 가공
										// ① 서버 Version > 단말 Version 인 경우
										// ② 서버 Version = 단말 Version 인 경우
										// Fake Update 상품이고 Google Play에서 설치(installer가 'com.android.vending') 했으면 업데이트
										// 대상

										if (iPkgVerCd > iReqPkgVerCd) {
											this.log.debug("##### Server app version > User app verion");
											mapPkg.put("PRCHS_ID", prchID);
											this.log.debug("##### Set PRCH_ID to {}", prchID);
										} else if (iPkgVerCd == iReqPkgVerCd) {
											this.log.debug("##### Server app version = User app verion");
											if (sFakeYn.equals("Y") && sInstaller.equals("com.android.vending")) {
												this.log.debug("##### Fake update target && install is com.android.vending");
												mapPkg.put("PRCHS_ID", prchID);
												this.log.debug("##### Set PRCH_ID to {}", prchID);
											} else {
												mapPkg.clear();
											}
										} else {
											mapPkg.clear();
										}
									} else {
										this.log.debug("##### Not alarm setting app!!!!!!!!!!!");
										mapPkg.clear();
									}
									this.log.debug("###########################################");
									isPrchsExists = true;
									break;
								}
							}

							// 구매내역이 존재하지 않는 경우
							if (!isPrchsExists) {
								this.log.debug(
										"##### Tstore user && Has update target && Normal user But {} is no purchase history",
										sPackageNm);

								// 유료상품은 구매내역이 없으면 업데이트 목록에서 제외
								if (!sProdAmt.equals("0")) {
									this.log.debug("##### Remove paid app from update list");
									mapPkg.clear();
								} else {
									// Fake Update 대상일 경우 FUPDATE+상품ID 로 구매ID 생성
									if (sFakeYn.equals("Y")) {
										String fupdateId = "FUPDATE" + sPid;
										this.log.debug("##### It's free app and fake update target");
										this.log.debug("##### Set purchase id to {}", fupdateId);
										mapPkg.put("PRCHS_ID", fupdateId);
									} else {
										this.log.debug("##### It's free app hence add update list");
									}
								}
								this.log.debug("###########################################");
							}

							if (!mapPkg.isEmpty()) {
								listUpdate.add(mapPkg);
							}
						}

					} else {
						this.log.debug(
								"##### Tstore user && Has update target But has no whole purchase history or isNormalUser : {}!!!!!!",
								isNormalUser);
						this.log.debug("##### Add result for only free app!!!!!!!!!!");
						// 구매내역이 없을 경우 무료상품만 리스트에 포함
						for (int i = 0; i < listProd.size(); i++) {
							mapPkg = listProd.get(i);
							sProdAmt = ObjectUtils.toString(mapPkg.get("PROD_AMT"));
							this.log.debug("##### Price : {}", sProdAmt);
							if (sProdAmt.equals("0")) {
								sFakeYn = ObjectUtils.toString(mapPkg.get("FAKE_YN"));
								this.log.debug("##### Is fake update target??? : " + sFakeYn);
								// Fake Update 대상일 경우 FUPDATE+상품ID 로 구매ID 생성
								if (sFakeYn.equals("Y")) {
									String fupdateId = "FUPDATE" + mapPkg.get("PROD_ID");
									mapPkg.put("PRCHS_ID", fupdateId);
									this.log.debug("##### Set purchase id to {}", fupdateId);
								}
								this.log.debug("##### Set Update list for product Id :  {}", mapPkg.get("PROD_ID"));
								listUpdate.add(mapPkg);
							} else {
								this.log.debug("##### Paid app, hence pass the process!!");
							}
						}
					}
				} else if (memberType.equals("updatedListForGuest")) {
					this.log.debug("##### Guest process start!!!!!!!!!");
					// 비회원일 경우 구매 이력에 상관없이 update 대상 전부를 추가
					listUpdate.addAll(listProd);
					this.log.debug("##### Update list for guest : {}", listUpdate);
				}

				if (listUpdate.isEmpty()) {
					throw new StorePlatformException("SAC_DSP_0006");
				}

				// Response 정보 가공
				for (Map<String, Object> updateTargetApp : listUpdate) {
					Product product = new Product();
					History history = new History();
					List<Update> updateList = new ArrayList<Update>();
					List<Source> sourceList = new ArrayList<Source>();

					String prchId = (String) updateTargetApp.get("PRCHS_ID");
					List<Menu> menuList = this.appGenerator.generateMenuList(
							(String) updateTargetApp.get("TOP_MENU_ID"), (String) updateTargetApp.get("TOP_MENU_NM"),
							(String) updateTargetApp.get("MENU_ID"), (String) updateTargetApp.get("MENU_NM"));
					product.setMenuList(menuList);
					List<Identifier> identifierList = this.appGenerator.generateIdentifierList(
							DisplayConstants.DP_EPISODE_IDENTIFIER_CD, (String) updateTargetApp.get("PROD_ID"));
					product.setIdentifierList(identifierList);

					Title title = new Title();
					title.setText((String) updateTargetApp.get("PROD_NM"));
					product.setTitle(title);

					Source source = this.commonGenerator.generateSource(ObjectUtils.toString(updateTargetApp
							.get("IMAGE_PATH")));
					sourceList.add(source);
					product.setSourceList(sourceList);

					Rights rights = new Rights();
					rights.setGrade(ObjectUtils.toString(updateTargetApp.get("PROD_GRD_CD")));
					product.setRights(rights);

					Price price = this.commonGenerator.generatePrice(
							((BigDecimal) updateTargetApp.get("PROD_AMT")).intValue(), null);
					product.setPrice(price);
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
