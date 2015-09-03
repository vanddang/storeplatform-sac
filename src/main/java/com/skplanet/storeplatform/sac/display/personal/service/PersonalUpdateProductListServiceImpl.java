/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.display.personal.service;

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
import com.skplanet.storeplatform.sac.display.personal.vo.MemberInfo;
import com.skplanet.storeplatform.sac.display.personal.vo.SubContentInfo;
import com.skplanet.storeplatform.sac.display.personal.vo.UpdateContextParam;
import com.skplanet.storeplatform.sac.display.personal.vo.UpdatePkgDetail;
import com.skplanet.storeplatform.sac.display.response.AppInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Updated on : 2015-08-06. Updated by : 양해엽, SK Planet.
 */
@Service
public class PersonalUpdateProductListServiceImpl implements PersonalUpdateProductListService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final String TENANT_ID_TSTORE = "S01";
	private final String TENANT_ID_KSTORE = "S02";
	private final String TENANT_ID_USTORE = "S03";
	private final Set<String> NET_OPR_TSTORE = new HashSet<String>(Arrays.asList("450/05"));
	private final Set<String> NET_OPR_KSTORE = new HashSet<String>(Arrays.asList("450/02", "450/04", "450/08"));
	private final Set<String> NET_OPR_USTORE = new HashSet<String>(Arrays.asList("450/06"));


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

	@Override
	public PersonalUpdateProductRes searchUpdateProductList(PersonalUpdateProductReq req, SacRequestHeader header, List<String> packageInfoList) {

		String tenantId = header.getTenantHeader().getTenantId();
		String langCd = header.getTenantHeader().getLangCd();
		String deviceModelCd = header.getDeviceHeader().getModel();
		String networkType = header.getNetworkHeader().getType();
		String operator = header.getNetworkHeader().getOperator();
		String memberType = req.getMemberType();
		String userKey = req.getUserKey();
		String deviceKey = req.getDeviceKey();

		UpdateContextParam updCtxParam = new UpdateContextParam(tenantId, langCd, deviceModelCd, networkType, memberType, operator);
		updCtxParam.setMemberInfo(new MemberInfo(userKey, deviceKey));

		Map<String, UpdatePkgDetail> pkgReqMap = appUpdateSupportService.parsePkgInfoList(packageInfoList);

		Map<String, UpdateProduct> upMap = new LinkedHashMap<String, UpdateProduct>(packageInfoList.size());
		List<String> pidPurList = new ArrayList<String>();

		extractProdHighVersionCd(upMap, pidPurList, updCtxParam, pkgReqMap);


		List<Product> productList = getFinalUpdateList(upMap, pidPurList, updCtxParam);

		// Response 정보 가공
		CommonResponse commonResponse = new CommonResponse();
		PersonalUpdateProductRes res = new PersonalUpdateProductRes();

		commonResponse.setTotalCount(productList.size());
		res.setCommonResponse(commonResponse);
		res.setProductList(productList);

		return res;
	}

	private void extractProdHighVersionCd(Map<String, UpdateProduct> upMap,
										  List<String> pidPurList,
										  final UpdateContextParam updCtxParam,
										  final Map<String, UpdatePkgDetail> pkgReqMap) {

		List<SubContentInfo> subContentInfos = appUpdateSupportService.searchSubContentByPkg(updCtxParam.getTenantId()
																							,updCtxParam.getDeviceModelCd()
																							,new ArrayList<String>(pkgReqMap.keySet()));
		if (!StringUtils.equals(TENANT_ID_TSTORE, updCtxParam.getTenantId())) {
			// 타사 요청인 경우 MAPG_PKG_NM으로 한번더 subContent를 조회 하여 추출 리스트에 추가한다.
			appUpdateSupportService.addSubContentByMapgPkg(	subContentInfos,
															updCtxParam.getDeviceModelCd(),
															new ArrayList<String>(pkgReqMap.keySet()) );
		}

		for (SubContentInfo scInfo : subContentInfos) {

			// 상품 메타데이터 조회
			UpdateProduct up = cachedExtraInfoManager.getUpdateProductInfo(
					new UpdateProductParam(updCtxParam.getTenantId(), updCtxParam.getLangCd(), scInfo.getProdId(), scInfo.getSubContentsId()));
			if(up == null) continue;

			// hasDiffPkgYn이 Y인 경우 응답으로 사용할 pkgNm 저장.
			up.setHasDiffApkPkgNm(scInfo.getApkPkgNm());
			up.setHasDiffPkgYn(scInfo.getHasDiffPkgYn());

			// Version 이 높은 대상 가려내기
			UpdatePkgDetail updPkgDtl = pkgReqMap.get(up.getApkPkgNm());
			if (updPkgDtl == null) continue;

			// Fake Update 상품이고 Google Play에서 설치(installer가 'com.android.vending')
			if ("Y".equals(up.getFakeYn()) && "com.android.vending".equals(updPkgDtl.getInstaller())) {

				// Fake Update 대상( 단말 Version <= 서버 Version )
				if (updPkgDtl.getVer() <= up.getApkVer()) {

					// Version 이 동일할 경우 + 1
					if (updPkgDtl.getVer().intValue() == up.getApkVer().intValue()) {
						up.setApkVer(up.getApkVer()+1);
					}
					upMap.put(up.getApkPkgNm(), up);
					pidPurList.add(up.getPartProdId());
				}

			} else {
				if (appUpdateSupportService.isTargetUpdateProduct(updPkgDtl.getVer(), up.getApkVer(),
						updPkgDtl.getApkSignedKeyHash(), up.getApkSignedKeyHash())) {
					upMap.put(up.getApkPkgNm(), up);
					pidPurList.add(up.getPartProdId());
				}
			}
		}
	}

	private HistorySacIn getPrchsHisByProdId(List<HistorySacIn> prchsHisList, String prodId) {

		if (prchsHisList == null || prchsHisList.isEmpty() ) return null;

		for (HistorySacIn prchsHis : prchsHisList) {
			if (prodId.equals(prchsHis.getProdId())) return prchsHis;
		}

		return null;
	}

	private boolean isEqualTenantNetworkOperator(UpdateContextParam updCtxParam) {
		String tenantId = updCtxParam.getTenantId();
		String operator = updCtxParam.getOperator();

		if (StringUtils.isEmpty(operator) || StringUtils.isEmpty(tenantId))
			return true;

		if (TENANT_ID_TSTORE.equals(tenantId) && NET_OPR_TSTORE.contains(operator))
			return true;
		else if (TENANT_ID_KSTORE.equals(tenantId) && NET_OPR_KSTORE.contains(operator))
			return true;
		else if (TENANT_ID_USTORE.equals(tenantId) && NET_OPR_USTORE.contains(operator))
			return true;

		return false;
	}

	private List<Product> getFinalUpdateList(Map<String, UpdateProduct> upMap, List<String> pidPurList, UpdateContextParam updCtxParam) {

		List<Product> productList = new ArrayList<Product>();
		List<HistorySacIn> prchsHisList = null;

		if (upMap.isEmpty()) {
			// 업데이트 대상이 없는 경우, 0 건으로 응답 처리.
			return productList;
		}

		if ("updatedList".equals(updCtxParam.getMemberType())) {
			// 회원으로 조회인 경우 회원 상태 조회 후 비정상 회원인 경우 0 건으로 응답 처리
			if (!isNormalUser(updCtxParam)) {
				return productList;
			}

			// 구매내역 조회
			prchsHisList = getPrchsHistoryList(pidPurList, updCtxParam);
		}


		List<String> forLogPidList = new ArrayList<String>();
		for (UpdateProduct up : upMap.values()) {

			if ("updatedListForGuest".equals(updCtxParam.getMemberType())) {
				// 비회원 조회인경우 모두 대상리스트에 포함.
				forLogPidList.add(up.getProdId());
				productList.add(generateUpdateApp(up));
				continue;
			}

			HistorySacIn prchsHis = getPrchsHisByProdId(prchsHisList, up.getProdId());
			if (prchsHis == null) {
				// 사용자가 embedded된 Store외에 타사 Store을 설치한 경우.
				// 구매내역이 존재하지 않으면 대상에서 제외 시킨다.
				if (!isEqualTenantNetworkOperator(updCtxParam)) continue;

				// 구매내역이 존재하지 않는 경우 무료 상품만 포함.
				if (up.getProdAmt() == 0) {

					if ("Y".equals(up.getFakeYn())) {
						// Fake Update 대상일 경우 FUPDATE+상품ID로 구매ID 강제 생성
						up.setPrchsId("FUPDATE" + up.getProdId());
					}

					forLogPidList.add(up.getProdId());
					productList.add(generateUpdateApp(up));
				}

				continue;
			}

			// 구매내역이 존재하는 경우. 구매ID 저장.
			up.setPrchsId(prchsHis.getPrchsId());

			forLogPidList.add(up.getProdId());
			productList.add(generateUpdateApp(up));
		}

		if (!productList.isEmpty()) {

			MemberInfo memberInfo = updCtxParam.getMemberInfo();
			appUpdateSupportService.logUpdateResult("Manual", null, memberInfo.getUserKey(),
					memberInfo.getDeviceKey(), updCtxParam.getNetworkType(), forLogPidList);
		}


		return productList;
	}

	/**
	 * 회원 정보를 조회하여 유효한 회원인지 판단한다.
	 * @param updCtxParam
	 * @return
	 */
	private boolean isNormalUser(UpdateContextParam updCtxParam) {

		MemberInfo memberInfo = updCtxParam.getMemberInfo();

		SearchUserSacReq searchUserSacReq = new SearchUserSacReq();
		List<String> userKeyList = new ArrayList<String>();
		userKeyList.add(memberInfo.getUserKey());
		searchUserSacReq.setUserKeyList(userKeyList);
		searchUserSacReq.setTenantId(updCtxParam.getTenantId());

		SearchUserSacRes searchUserSacRes = this.searchUserSCI.searchUserByUserKey(searchUserSacReq);

		Map<String, UserInfoSac> userInfo = searchUserSacRes.getUserInfo();
		UserInfoSac userInfoSac = userInfo.get(memberInfo.getUserKey());
		String userMainStatus = userInfoSac.getUserMainStatus();

		return DisplayConstants.MEMBER_MAIN_STATUS_NORMAL.equals(userMainStatus);
	}

	/**
	 * 구매내역을 조회 한다.
	 * 예외가 발생하면 무시, 구매내역이 없다고 판단하고 무료 상품은 내려줄 수 있도록 한다.
	 * @param pidPurList
	 * @param updCtxParam
	 * @return
	 */
	private List<HistorySacIn> getPrchsHistoryList(List<String> pidPurList, UpdateContextParam updCtxParam) {
		List<HistorySacIn> prchsHisList = null;

		try {
			List<ProductListSacIn> productListSacInList = new ArrayList<ProductListSacIn>();
			String endDate = "20991231235959";

			for (String prodId : pidPurList) {
				ProductListSacIn productListSacIn = new ProductListSacIn();
				productListSacIn.setProdId(prodId);
				productListSacInList.add(productListSacIn);
			}

			MemberInfo memberInfo = updCtxParam.getMemberInfo();

			HistoryListSacInReq historyListSacReq = new HistoryListSacInReq();
			historyListSacReq.setTenantId(updCtxParam.getTenantId());
			historyListSacReq.setUserKey(memberInfo.getUserKey());
			historyListSacReq.setDeviceKey(memberInfo.getDeviceKey());
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
				prchsHisList = historyListSacRes.getHistoryList();
			} else {
				this.log.debug("##### No purchase result!!");
			}

		} catch (Exception e) {
			// Exception 무시
			this.log.error("Exception has occurred using search purchase history!", e);
		}

		return prchsHisList;
	}

	private Product generateUpdateApp(UpdateProduct up) {
		Product product = new Product();

		List<Update> updateList = new ArrayList<Update>();
		List<Identifier> identifierList = new ArrayList<Identifier>();
		identifierList.add(this.commonGenerator.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD, up.getPartProdId()));
		identifierList.add(this.commonGenerator.generateIdentifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, up.getProdId()));
		product.setIdentifierList(identifierList);

		List<Menu> menuList = this.appGenerator.generateMenuList(up.getTopMenuId(), up.getTopMenuNm(), up.getMenuId(), up.getMenuNm());
		product.setMenuList(menuList);

		Title title = new Title();
		title.setText(up.getProdNm());
		product.setTitle(title);

		List<Source> sourceList = new ArrayList<Source>();
		Source source = this.commonGenerator.generateSource(up.getImagePath());
		sourceList.add(source);
		product.setSourceList(sourceList);

		Rights rights = new Rights();
		rights.setGrade(up.getProdGrdCd());
		product.setRights(rights);

		Price price = this.commonGenerator.generatePrice(up.getProdAmt(), null);
		product.setPrice(price);

		// 구매 정보는 구매 내역이 있는 App만 표시한다.
		if (StringUtils.isNotEmpty(up.getPrchsId())) {
			Purchase purchase = this.commonGenerator.generatePurchase(up.getPrchsId(), up.getProdId(), null, null, null);
			product.setPurchase(purchase);
		}

		App app = this.appGenerator.generateApp(up.getAid(),
				"Y".equals(up.getHasDiffPkgYn()) ? up.getHasDiffApkPkgNm() : up.getApkPkgNm(),
				up.getApkVer() != null ? up.getApkVer().toString() : "",
				up.getProdVer(),
				up.getFileSize(),
				null,
				null,
				up.getFilePath());

		app.setHasDiffPkgYn(up.getHasDiffPkgYn());

		History history = new History();
		Update update = this.appGenerator.generateUpdate(new Date(null, up.getLastDeployDt()), up.getUpdtText());
		updateList.add(update);
		history.setUpdate(updateList);
		app.setHistory(history);
		product.setApp(app);

		return product;
	}
}
