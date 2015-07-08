/**
 *
 */
package com.skplanet.storeplatform.sac.display.personal.service;

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
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.*;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.*;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.cache.service.CachedExtraInfoManager;
import com.skplanet.storeplatform.sac.display.cache.vo.UpdateProduct;
import com.skplanet.storeplatform.sac.display.cache.vo.UpdateProductParam;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.personal.vo.SubContentInfo;
import com.skplanet.storeplatform.sac.display.response.AppInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 업데이트 대상 목록 조회 Service 구현체
 *
 * Updated on : 2014. 2. 10. Updated by : 오승민, 인크로스.
 */
@Service
public class PersonalUpdateProductServiceImpl implements PersonalUpdateProductService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final String TENANT_ID_TSTORE = "S01";

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

	@Autowired
	private AppInfoGenerator appGenerator;

	@Autowired
	private HistoryInternalSCI historyInternalSCI;

	@Autowired
	private SearchUserSCI searchUserSCI;

    @Autowired
    private AppUpdateSupportService appUpdateSupportService;

    @Autowired
    private CachedExtraInfoManager cachedExtraInfoManager;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.skplanet.storeplatform.sac.display.personal.service.PersonalUpdateProductService#searchUpdateProductList(
	 * com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalUpdateProductReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader, java.util.List)
	 */
	@Override
	public PersonalUpdateProductRes searchUpdateProductList(PersonalUpdateProductReq req, SacRequestHeader header,
			List<String> packageInfoList) {


        String tenantId = header.getTenantHeader().getTenantId();
        String langCd = header.getTenantHeader().getLangCd();
        String deviceModelCd = header.getDeviceHeader().getModel();

		/**************************************************************
		 * Package 명으로 상품 조회
		 **************************************************************/
        List<String> pkgList = new ArrayList<String>(packageInfoList.size());
        for (String s : packageInfoList) {
			String[] arrInfo = StringUtils.split(s, "/");
			if (arrInfo.length >= 2) {
				String pkgNm = arrInfo[0];
				// parameter가 적어도 packageName/version정보로 와야지만 update 리스트에 추가한다.
				this.log.debug("##### update package name : {}", pkgNm);
                pkgList.add(pkgNm);
            }
		}

        List<SubContentInfo> subContentInfos = appUpdateSupportService.searchSubContentByPkg(deviceModelCd, pkgList);
        if (!StringUtils.equals(TENANT_ID_TSTORE, tenantId)) {
            // 타사 요청인 경우 MAPG_PKG_NM으로 한번더 subContent를 조회 하여 추출 리스트에 추가한다.
            appUpdateSupportService.addSubContentByMapgPkg(subContentInfos, deviceModelCd, pkgList);
        }

		List<Map<String, Object>> listPkg = new ArrayList<Map<String, Object>>();
		for (SubContentInfo scInfo : subContentInfos) {

            UpdateProduct up = cachedExtraInfoManager.getUpdateProductInfo(new UpdateProductParam(tenantId, langCd, scInfo.getProdId(), scInfo.getSubContentsId()));
            if(up != null) {
                listPkg.add(makeTargetProdFromDB(up, scInfo));
            }
        }

		this.log.debug("##### update target list  : {}", listPkg);
		this.log.debug("##### update target cnt   : {}", listPkg.size());

		if (listPkg.isEmpty()) {
			throw new StorePlatformException("SAC_DSP_0006");
		}

		// Version Code가 높은 Data만 추출
        List<Map<String, Object>> listProd = new ArrayList<Map<String, Object>>();
        List<String> listPid = new ArrayList<String>();

		extractProdHighVersionCd(listProd, listPid, listPkg, packageInfoList);

		if (listPid.isEmpty()) {
			throw new StorePlatformException("SAC_DSP_0006");
		}

        /**************************************************************
         * 업데이트 목록 가공
         **************************************************************/
        List<Map<String, Object>> listUpdate = new ArrayList<Map<String, Object>>();    // FIXME Map -> VO
        // ◆ 회원
        // - 구매내역 존재 : 유료상품만 포함
        // - 구매내역 미존재 : 무료상품만 포함
        // ◆ 비회원
        // - 전체상품 포함
        String memberType = req.getMemberType();
        if (memberType.equals("updatedList")) {
            // 회원일 경우 회원 상태 조회 후 비정상 회원인 경우 예외 발생.
			if (!isNormalUser(req, header)) {
				throw new StorePlatformException("SAC_DSP_0006");
			}

			// 구매내역 조회.
			List<HistorySacIn> listPrchs = getPrchsHistoryList(req, header, listPid);

			// 최종 업데이트 목록 추출.
			extractFinalUpdateList(listUpdate, listPrchs, listProd);


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
		List<Product> productList = new ArrayList<Product>();
		List<String> updatePidList = new ArrayList<String>();

		makeResponse(productList, updatePidList, listUpdate);

		PersonalUpdateProductRes res = new PersonalUpdateProductRes();
		CommonResponse commonResponse = new CommonResponse();
        commonResponse.setTotalCount(productList.size());
        res.setCommonResponse(commonResponse);
        res.setProductList(productList);

        appUpdateSupportService.logUpdateResult("Manual", "", req.getUserKey(), req.getDeviceKey(), header.getNetworkHeader().getType(), updatePidList);

		return res;
	}

	private Map<String, Object> makeTargetProdFromDB(final UpdateProduct up, final SubContentInfo scInfo) {

		Map<String, Object> prod = new HashMap<String, Object>();
		prod.put("PROD_ID", up.getProdId());
		prod.put("PART_PROD_ID", up.getPartProdId());
		prod.put("APK_VER", up.getApkVer());
		prod.put("APK_PKG_NM", scInfo.getApkPkgNm());
		prod.put("FAKE_YN", up.getFakeYn());
		prod.put("PROD_AMT", up.getProdAmt());
		prod.put("TOP_MENU_ID", up.getTopMenuId());
		prod.put("TOP_MENU_NM", up.getTopMenuNm());
		prod.put("MENU_ID", up.getMenuId());
		prod.put("MENU_NM", up.getMenuNm());
		prod.put("PROD_NM", up.getProdNm());
		prod.put("IMAGE_PATH", up.getImagePath());
		prod.put("PROD_GRD_CD", up.getProdGrdCd());
		prod.put("AID", up.getAid());
		prod.put("PROD_VER", up.getProdVer());
		prod.put("FILE_SIZE", up.getFileSize());
		prod.put("FILE_PATH", up.getFilePath());
		prod.put("LAST_DEPLOY_DT", up.getLastDeployDt());
		prod.put("UPDT_TEXT", up.getUpdtText());
		prod.put("HAS_DIFF_PKG_YN", scInfo.getHasDiffPkgYn());

		return prod;
	}

	/**
	 * Version Code 가 높은 Data 만 추출
	 */
	private void extractProdHighVersionCd(List<Map<String, Object>> listProd, List<String> listPid,
										  final List<Map<String, Object>> listPkg, final List<String> packageInfoList) {
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

			// 파라메터로 넘어온 패키지명과 대조 TODO 이 루프를 위의 루프에서 처리하도록 개선
			for (String s : packageInfoList) {
				String sArrPkgInfo[] = StringUtils.split(s, "/");
				if (sArrPkgInfo.length < 2)
					continue;

				if (sPkgNm.equals(sArrPkgInfo[0])) {
					int iReqPkgVerCd = NumberUtils.toInt(sArrPkgInfo[1]);

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
							this.log.debug("##### package nm: {}", sPkgNm);
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
	}

	private boolean isNormalUser(final PersonalUpdateProductReq req, final SacRequestHeader header) {

		String userKey = req.getUserKey();
		String tenantId = header.getTenantHeader().getTenantId();

		List<String> userKeyList = new ArrayList<String>();
		userKeyList.add(userKey);

		SearchUserSacReq searchUserSacReq = new SearchUserSacReq();
		searchUserSacReq.setUserKeyList(userKeyList);
		searchUserSacReq.setTenantId(tenantId);

		SearchUserSacRes searchUserSacRes = this.searchUserSCI.searchUserByUserKey(searchUserSacReq);

		Map<String, UserInfoSac> userInfo = searchUserSacRes.getUserInfo();
		UserInfoSac userInfoSac = userInfo.get(userKey);
		String userMainStatus = userInfoSac.getUserMainStatus();

		if (DisplayConstants.MEMBER_MAIN_STATUS_NORMAL.equals(userMainStatus)) {
			this.log.debug("##### This user is normal user!!!!");
			return true;
		} else {
			this.log.debug("##### This user is unnormal user!!!!");
			return false;
		}
	}

	private List<HistorySacIn> getPrchsHistoryList(final PersonalUpdateProductReq req, final SacRequestHeader header,
												   final List<String> listPid) {
		List<HistorySacIn> listPrchs = null;

		try {
			this.log.debug("##### Purchase check start!!!!!!!!!");
			List<ProductListSacIn> productListSacInList = new ArrayList<ProductListSacIn>();
			String endDate = "20991231235959";

			this.log.debug("##### endDate :: {}", endDate);
			for (String prodId : listPid) {
				ProductListSacIn productListSacIn = new ProductListSacIn();
				productListSacIn.setProdId(prodId);
				productListSacInList.add(productListSacIn);
			}

			HistoryListSacInReq historyListSacReq = new HistoryListSacInReq();
			historyListSacReq.setTenantId(header.getTenantHeader().getTenantId());
			historyListSacReq.setUserKey(req.getUserKey());
			historyListSacReq.setDeviceKey(req.getDeviceKey());
			historyListSacReq.setPrchsProdHaveYn(DisplayConstants.PRCHS_PROD_HAVE_YES);
			historyListSacReq.setStartDt(DisplayConstants.PRCHS_START_DATE);
			historyListSacReq.setEndDt(endDate);
			historyListSacReq.setOffset(1);
			historyListSacReq.setPrchsStatusCd(DisplayConstants.PRCHS_STSTUS_COMPLETE_CD);
			historyListSacReq.setCount(1000);
			historyListSacReq.setProductList(productListSacInList);

			// 구매내역 조회 실행
			HistoryListSacInRes historyListSacRes = this.historyInternalSCI.searchHistoryList(historyListSacReq);
			if (historyListSacRes != null) {
				listPrchs = historyListSacRes.getHistoryList();

				this.log.debug("##### Purchase check result size : {}", listPrchs.size());
				this.log.debug("##### Purchase check result  : {}", listPrchs);
			} else {
				this.log.debug("##### No purchase result!!");
			}

		} catch (Exception e) {
			// Exception 무시
			this.log.error("Exception has occured using search purchase history!!!!!!!!!!!", e);
		}

		return listPrchs;
	}

	private void extractFinalUpdateList(List<Map<String, Object>> listUpdate, final List<HistorySacIn> listPrchs,
										final List<Map<String, Object>> listProd) {

		String sProdAmt = "";
		int iPkgVerCd = 0;
		String sFakeYn = "";
		Map<String, Object> mapPkg = null;

		if (CollectionUtils.isNotEmpty(listPrchs)) {

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


						// 구매내역 존재 시 업데이트 목록 가공
						// ① 서버 Version > 단말 Version 인 경우
						// ② 서버 Version = 단말 Version 인 경우
						// Fake Update 상품이고 Google Play에서 설치(installer가 'com.android.vending') 했으면 업데이트 대상

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

						this.log.debug("###########################################");
						isPrchsExists = true;
						break;
					}
				}

				// 구매내역이 존재하지 않는 경우

				if (!isPrchsExists) {
					this.log.debug(	"##### Tstore user && Has update target && Normal user But {} is no purchase history", sPackageNm);

					// 유료상품은 구매내역이 없으면 업데이트 목록에서 제외
					if (!sProdAmt.equals("0")) {
						this.log.debug("##### {} is paid app. Hence Remove from update list", sPackageNm);
						mapPkg.clear();
					} else {
						// Fake Update 대상일 경우 FUPDATE+상품ID 로 구매ID 생성
						if (sFakeYn.equals("Y")) {
							String fupdateId = "FUPDATE" + sPid;
							this.log.debug("##### {} is free app and fake update target", sPackageNm);
							this.log.debug("##### Set purchase id to {}", fupdateId);
							mapPkg.put("PRCHS_ID", fupdateId);
						} else {
							this.log.debug("##### {} is free app hence add update list", sPackageNm);
						}
					}

					// TODO : 타사 회원인경우 제외.

					this.log.debug("###########################################");
				}

				if (!mapPkg.isEmpty()) {
					listUpdate.add(mapPkg);
				}
			}

		} else {
			this.log.debug("##### Tstore user && Has update target But has no whole purchase history");
			this.log.debug("##### Add result for only free app!!!!!!!!!!");

			// TODO : 타사 회원인경우 제외.

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
	}

	private void makeResponse(List<Product> productList, List<String> updatePidList,
							  final List<Map<String, Object>> listUpdate) {

		for (Map<String, Object> updateTargetApp : listUpdate) {
			String prodId = (String) updateTargetApp.get("PROD_ID");
			updatePidList.add(prodId);

			Product product = new Product();
			History history = new History();
			List<Update> updateList = new ArrayList<Update>();
			List<Source> sourceList = new ArrayList<Source>();

			String prchId = (String) updateTargetApp.get("PRCHS_ID");
			List<Menu> menuList = this.appGenerator.generateMenuList(
					(String) updateTargetApp.get("TOP_MENU_ID"), (String) updateTargetApp.get("TOP_MENU_NM"),
					(String) updateTargetApp.get("MENU_ID"), (String) updateTargetApp.get("MENU_NM"));
			product.setMenuList(menuList);

			List<Identifier> identifierList = new ArrayList<Identifier>();
			identifierList.add(this.commonGenerator.generateIdentifier(
					DisplayConstants.DP_EPISODE_IDENTIFIER_CD, (String) updateTargetApp.get("PART_PROD_ID")));
			identifierList.add(this.commonGenerator.generateIdentifier(
					DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, prodId));
			product.setIdentifierList(identifierList);

			Title title = new Title();
			title.setText((String) updateTargetApp.get("PROD_NM"));
			product.setTitle(title);

			Source source = this.commonGenerator.generateSource(ObjectUtils.toString(updateTargetApp.get("IMAGE_PATH")));
			sourceList.add(source);
			product.setSourceList(sourceList);

			Rights rights = new Rights();
			rights.setGrade(ObjectUtils.toString(updateTargetApp.get("PROD_GRD_CD")));
			product.setRights(rights);

			Price price = this.commonGenerator.generatePrice(
					((Integer) updateTargetApp.get("PROD_AMT")), null);
			product.setPrice(price);
			// 구매 정보는 구매 내역이 있는 App만 표시한다.
			if (!StringUtils.isEmpty(prchId)) {
				Purchase purchage = this.commonGenerator.generatePurchase(
						(String) updateTargetApp.get("PRCHS_ID"), prodId, null, null, null);
				product.setPurchase(purchage);
			}

			App app = this.appGenerator.generateApp((String) updateTargetApp.get("AID"),
					(String) updateTargetApp.get("APK_PKG_NM"),
					ObjectUtils.toString(updateTargetApp.get("APK_VER")),
					ObjectUtils.toString(updateTargetApp.get("PROD_VER")),
					((Long) updateTargetApp.get("FILE_SIZE")), null, null,
					ObjectUtils.toString(updateTargetApp.get("FILE_PATH")));

			app.setHasDiffPkgYn((String) updateTargetApp.get("HAS_DIFF_PKG_YN"));

			Update update = this.appGenerator.generateUpdate(
					new Date(null, (java.util.Date) updateTargetApp.get("LAST_DEPLOY_DT")),
					(String) updateTargetApp.get("UPDT_TEXT"));
			updateList.add(update);
			history.setUpdate(updateList);
			app.setHistory(history);
			product.setApp(app);
			productList.add(product);
		}

	}
}
