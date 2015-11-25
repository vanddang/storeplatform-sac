/**
 * 
 */
package com.skplanet.storeplatform.sac.display.personal.service;

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalAutoUpdateReq;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalAutoUpdateRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.*;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.History;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Update;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 자동 Update 목록 조회 Service 구현체
 *
 * 자동업데이트 전문 응답 처리과정
 * 1. 패키지명으로 업데이트 대상 조회
 * 2. 업데이트 대상들의 메타를 조회하여 요청 버전과 비교
 * 3. 구매 내역에 존재하는 것들만 선택
 *
 * - 자동업데이트 요청 deviceId는 MDN을 의미한다.
 * - 자동업데이트 대상은 구매 내역이 있는 경우에 한한다.
 * - 일반 업데이트로 조회시 구매내역이 있는 상품의 경우 구매키가 응답되는데 구매키가 존재하는 상품에 한해 자동업데이트에 응답이 되어야 한다.
 *
 * Updated on : 2014. 2. 10. Updated by : 오승민, 인크로스.
 */
@Service
public class PersonalAutoUpdateServiceImpl implements PersonalAutoUpdateService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommonMetaInfoGenerator commonGenerator;

    @Autowired
    private AppInfoGenerator appGenerator;

    @Autowired
    private AppUpdateSupportService appUpdateSupportService;

    @Autowired
    private CachedExtraInfoManager cachedExtraInfoManager;

    @Override
    public PersonalAutoUpdateRes updateAutoUpdateList(PersonalAutoUpdateReq req, SacRequestHeader header,
                                                      List<String> packageInfoList) {

        String tenantId = header.getTenantHeader().getTenantId();
        String langCd = header.getTenantHeader().getLangCd();
        String deviceModelCd = header.getDeviceHeader().getModel();

        final String deviceId = req.getDeviceId();
        final String networkType = header.getNetworkHeader().getType();

        new TLogUtil().set(new ShuttleSetter() {
            @Override
            public void customize(TLogSentinelShuttle shuttle) {
                shuttle.log_id("TL_SAC_DSP_0001").device_id(deviceId);
            }
        });

		/**
		 * 회원 상태 조회 후 비정상 회원인경우 예외 throw.
		 */
        MemberInfo memberInfo = appUpdateSupportService.getMemberInfo(deviceId);

		UpdateContextParam updCtxParam = new UpdateContextParam(tenantId, langCd, deviceModelCd, deviceId, networkType, req.getUpdLimitCnt());
		updCtxParam.setMemberInfo(memberInfo);

		List<Product> productList = getAutoUpdateProductList(updCtxParam, packageInfoList);

        // Response 정보 가공
        CommonResponse commonResponse = new CommonResponse();
        PersonalAutoUpdateRes res = new PersonalAutoUpdateRes();

        commonResponse.setTotalCount(productList.size());
        res.setCommonResponse(commonResponse);
        res.setProductList(productList);

        return res;
    }

	private List<Product> getAutoUpdateProductList(UpdateContextParam updCtxParam, List<String> packageInfoList) {

		Map<String, UpdatePkgDetail> pkgReqMap = appUpdateSupportService.parsePkgInfoList(packageInfoList);

		List<SubContentInfo> subContentInfos = appUpdateSupportService.searchSubContentByPkg(
				updCtxParam.getTenantId(), updCtxParam.getDeviceModelCd(), new ArrayList<String>(pkgReqMap.keySet()) );

		Map<String, UpdateProduct> upMap = new LinkedHashMap<String, UpdateProduct>(packageInfoList.size());
		List<String> pidPurList = new ArrayList<String>();
		for (SubContentInfo scInfo : subContentInfos) {

			// 상품 메타데이터 조회
			UpdateProduct up = cachedExtraInfoManager.getUpdateProductInfo(
					new UpdateProductParam(updCtxParam.getTenantId(), updCtxParam.getLangCd(), scInfo.getProdId(), scInfo.getSubContentsId()));

			if(up == null) {
				continue;
			}

			// Version 이 높은 대상 가려내기
			UpdatePkgDetail updPkgDtl = pkgReqMap.get(up.getApkPkgNm());

			if (updPkgDtl != null) {
				if (appUpdateSupportService.isTargetUpdateProduct(updPkgDtl.getVer(), up.getApkVer(),
						updPkgDtl.getApkSignedKeyHash(), up.getApkSignedKeyHash())) {
					upMap.put(scInfo.getApkPkgNm(), up);
					pidPurList.add(up.getPartProdId());
				}
			}
		}

		if (upMap.isEmpty()) {
			// 업데이트 대상이 없는 경우, 0 건으로 응답 처리.
			return new ArrayList<Product>();
		}

		/**
		 * 구매내역이 존재하는 상품만 업데이트 목록에 추출
		 */
		List<Product> productList = new ArrayList<Product>();
		final List<String> forTlogAppIdList = new ArrayList<String>();
		MemberInfo memberInfo = updCtxParam.getMemberInfo();

		Set<String> purSet = appUpdateSupportService.getPurchaseSet(updCtxParam.getTenantId(),
																	memberInfo.getUserKey(),
																	memberInfo.getDeviceKey(),
																	pidPurList);
		if (!purSet.isEmpty()) {

			Integer limitCnt = updCtxParam.getUpdLimitCnt();
			int resultSize = 0;

			for (UpdateProduct up : upMap.values()) {
				if (limitCnt != null && resultSize >= limitCnt)
					break;

				String epsdId = up.getPartProdId();
				if (purSet.contains(epsdId)) {
					forTlogAppIdList.add(epsdId);
					productList.add(generateUpdateApp(up));

					++resultSize;
				}
			}
		}

		if (!productList.isEmpty()) {
			final String networkType = updCtxParam.getNetworkType();

			new TLogUtil().set(new ShuttleSetter() {
				@Override
				public void customize(TLogSentinelShuttle shuttle) {
					shuttle.app_id(forTlogAppIdList).network_type(networkType);
				}
			});

			appUpdateSupportService.logUpdateResult("Auto", updCtxParam.getDeviceId(), memberInfo.getUserKey(),
					memberInfo.getDeviceKey(), updCtxParam.getNetworkType(), forTlogAppIdList);
		}

		return productList;
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


        App app = this.appGenerator.generateApp(up.getAid(),
												up.getApkPkgNm(),
												up.getApkVer() != null ? up.getApkVer().toString() : "",
												up.getProdVer(),
												up.getFileSize(),
												null,
												null,
												up.getFilePath());

		History history = new History();
        Update update = this.appGenerator.generateUpdate(new Date(null, up.getLastDeployDt()), null);
		updateList.add(update);
        history.setUpdate(updateList);
        app.setHistory(history);
        product.setApp(app);

        return product;
    }
}
