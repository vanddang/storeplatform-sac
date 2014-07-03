/**
 * 
 */
package com.skplanet.storeplatform.sac.display.personal.service;

import com.skplanet.pdp.sentinel.shuttle.TLogSentinelShuttle;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.util.NumberUtils;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil;
import com.skplanet.storeplatform.framework.core.util.log.TLogUtil.ShuttleSetter;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalAutoUpdateReq;
import com.skplanet.storeplatform.sac.client.display.vo.personal.PersonalAutoUpdateRes;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserInfoSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.*;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.History;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Update;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.cache.service.UpdateProductInfoManager;
import com.skplanet.storeplatform.sac.display.cache.vo.UpdateProduct;
import com.skplanet.storeplatform.sac.display.cache.vo.UpdateProductParam;
import com.skplanet.storeplatform.sac.display.common.DisplayCryptUtils;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.personal.vo.SubContentInfo;
import com.skplanet.storeplatform.sac.display.response.AppInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 자동 Update 목록 조회 Service 구현체
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
	private SearchUserSCI searchUserSCI;

    @Autowired
    private AppUpdateSupportService appUpdateSupportService;

    @Autowired
    private UpdateProductInfoManager updateProductInfoManager;

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
		this.log.info("##### updateAutoUpdateList start!!!!!!!!!!");
		final String deviceId = req.getDeviceId();
		new TLogUtil().set(new ShuttleSetter() {
			@Override
			public void customize(TLogSentinelShuttle shuttle) {
				shuttle.log_id("TL_SAC_DSP_0001").device_id(deviceId);
			}
		});

        /**************************************************************
         * 회원 상태 조회
         **************************************************************/
        this.log.debug("##### check user status");
        this.log.debug("##### deviceId :: {} " + deviceId);
        UserInfoSacReq userInfoSacReq = new UserInfoSacReq();
        userInfoSacReq.setDeviceId(deviceId);
        this.log.info("##### [SAC DSP LocalSCI] SAC Member Start : searchUserSCI.searchUserBydeviceId");
        long start = System.currentTimeMillis();
        UserInfoSacRes userInfoSacRes = this.searchUserSCI.searchUserBydeviceId(userInfoSacReq);
        this.log.info("##### [SAC DSP LocalSCI] SAC Member End : searchUserSCI.searchUserBydeviceId");
        long end = System.currentTimeMillis();
        this.log.info("##### [SAC DSP LocalSCI] SAC Member searchUserSCI.searchUserBydeviceId takes {} ms",
                (end - start));

        String userMainStatus = userInfoSacRes.getUserMainStatus();
        final String userKey = userInfoSacRes.getUserKey();
        final String deviceKey = userInfoSacRes.getDeviceKey();
        this.log.debug("##### userKey :: {} " + userKey);
        this.log.debug("##### deviceKey :: {} " + deviceKey);

        new TLogUtil().set(new ShuttleSetter() {
            @Override
            public void customize(TLogSentinelShuttle shuttle) {
                shuttle.insd_usermbr_no(userKey).insd_device_id(deviceKey);
            }
        });

        // 회원에 의하면 정상은 UserMainStatus를 참고
        // 정상 회원일이 아닐 경우 -> '업데이트 내역이 없습니다' 에러 발생
        // 탈퇴 회원일 경우 -> 회원에서 '탈퇴한 회원입니다.'에러 발생하여 그대로 throw 함
        if (DisplayConstants.MEMBER_MAIN_STATUS_NORMAL.equals(userMainStatus)) {
            this.log.debug("##### This user is normal user!!!!");
        } else {
            this.log.debug("##### This user is unnormal user!!!!");
            throw new StorePlatformException("SAC_DSP_0006");
        }

		CommonResponse commonResponse = new CommonResponse();
		PersonalAutoUpdateRes res = new PersonalAutoUpdateRes();
		List<Product> productList = new ArrayList<Product>();
        String tenantId = header.getTenantHeader().getTenantId();
        String langCd = header.getTenantHeader().getLangCd();
        String deviceModelCd = header.getDeviceHeader().getModel();

		final List<String> forTlogAppIdList = new ArrayList<String>();

		// 다운로드 서버 상태 조회는 & 앱 버전 정보 활용 조회 처리 & 업그레이드 관리이력 조회는 tenant 단에서 처리하기 때문에 제외

		/**************************************************************
		 * Package 명으로 상품 조회
		 **************************************************************/
		List<String> listPkgNm = new ArrayList<String>();
        List<String> hashedPkgList = new ArrayList<String>();
        for (String s : packageInfoList) {
			String[] arrInfo = StringUtils.split(s, "/");
			if (arrInfo.length >= 2) {
				String pkgNm = arrInfo[0];
				// parameter가 적어도 packageName/version정보로 와야지만 update 리스트에 추가한다.
				this.log.debug("##### update package name : {}", pkgNm);
				listPkgNm.add(pkgNm);
                hashedPkgList.add(DisplayCryptUtils.hashPkgNm(pkgNm));
            }
		}

        List<SubContentInfo> subContentInfos = appUpdateSupportService.searchSubContentByPkg(deviceModelCd, hashedPkgList, true);

        this.log.debug("##### auto update target list  : {}", listPkgNm);
		this.log.debug("##### auto update target cnt   : {}", listPkgNm.size());

		List<UpdateProduct> updateProductList = new ArrayList<UpdateProduct>();

		for (SubContentInfo scInfo : subContentInfos) {

            UpdateProduct appInfoMap = updateProductInfoManager.getUpdateProductInfo(new UpdateProductParam(tenantId, langCd, scInfo.getProdId(), scInfo.getSubContentsId()));

            if (appInfoMap != null) {
                updateProductList.add(appInfoMap);
            }
        }

		if (updateProductList.isEmpty()) {
			throw new StorePlatformException("SAC_DSP_0006");
		}

        List<UpdateProduct> updateProductList2 = new ArrayList<UpdateProduct>();
        List<UpdateProduct> updateProductList3 = new ArrayList<UpdateProduct>();
        List<String> pidPurList = new ArrayList<String>();

        /**************************************************************
         * Version Code 가 높은 Data 만 추출
         **************************************************************/
        for (UpdateProduct up : updateProductList) {

            for (String s : packageInfoList) {
                String pkgNm = up.getApkPkgNm();
                int apkVer = up.getApkVer();
                String[] sArrPkgInfo = StringUtils.split(s, "/");

                if (sArrPkgInfo.length >= 2) {
                    this.log.debug("###########################################");
                    this.log.debug("##### sArrPkgInfo's length is over 2!!!!");
                    this.log.debug("##### {}'s server version is {} !!!!!!!!!!", pkgNm, apkVer);
                    this.log.debug("##### {}'s user   version is {} !!!!!!!!!!", pkgNm, sArrPkgInfo[1]);

                    // 단말보다 Version Code 가 높은경우
                    if (pkgNm.equals(sArrPkgInfo[0])) {
                        if (apkVer > NumberUtils.toInt(sArrPkgInfo[1])) {
                            this.log.debug("##### Add to update target!!!!!!!!!");
                            updateProductList2.add(up);
                            pidPurList.add(up.getPartProdId());
                        }
                        break;
                    }
                }
            }
        }

        if(pidPurList.isEmpty())
            throw new StorePlatformException("SAC_DSP_0006");

        /**************************************************************
         * 구매여부 및 최근 업데이트 정보 추출
         **************************************************************/
        final boolean checkPurchase = true;
        if(checkPurchase) {
            Set<String> purSet = appUpdateSupportService.getPurchaseSet(tenantId, userKey, deviceKey, pidPurList);
            if (!purSet.isEmpty()) {

                for (UpdateProduct up : updateProductList2) {
                    String epsdId = up.getPartProdId();
                    if(purSet.contains(epsdId)) {
                        updateProductList3.add(up);
                        forTlogAppIdList.add(epsdId);
                    }
                }
            }
        }
        else {
            for (UpdateProduct up : updateProductList2) {
                String epsdId = up.getPartProdId();
                updateProductList3.add(up);
                forTlogAppIdList.add(epsdId);
            }
        }

        if (updateProductList3.isEmpty()) {
            throw new StorePlatformException("SAC_DSP_0006");
        }

        // 업데이트 제한 기능이 필요할 경우
        Integer restCnt = req.getUpdLimitCnt();
        if (restCnt != null) {
            if (restCnt > 0) {
                if (updateProductList3.size() > restCnt) {
                    updateProductList3 = updateProductList3.subList(0, restCnt);
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
        for (UpdateProduct up : updateProductList3) {
            Product product = new Product();
            History history = new History();
            List<Update> updateList = new ArrayList<Update>();
            List<Identifier> identifierList = new ArrayList<Identifier>();
            identifierList.add(this.commonGenerator.generateIdentifier(
                    DisplayConstants.DP_EPISODE_IDENTIFIER_CD, up.getPartProdId()));
            identifierList.add(this.commonGenerator.generateIdentifier(
                    DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, up.getProdId()));
            product.setIdentifierList(identifierList);
            // List<Identifier> identifierList = this.appGenerator.generateIdentifierList(
            // DisplayConstants.DP_EPISODE_IDENTIFIER_CD, (String) updateTargetApp.get("PART_PROD_ID"));

            List<Menu> menuList = this.appGenerator.generateMenuList(
                    up.getTopMenuId(), up.getTopMenuNm(),
                    up.getMenuId(), up.getMenuNm());
            product.setMenuList(menuList);

            Title title = new Title();
            title.setText(up.getProdNm());
            product.setTitle(title);

            // if (!StringUtils.isEmpty(prchId)) {
            // Purchase purchage = this.commonGenerator.generatePurchase(
            // (String) updateTargetApp.get("PRCHS_ID"), (String) updateTargetApp.get("PROD_ID"),
            // null, null, null);
            // product.setPurchase(purchage);
            // }

            App app = this.appGenerator.generateApp(up.getAid(),
                    up.getApkPkgNm(),
                    up.getApkVer() != null ? up.getApkVer().toString() : "",
                    up.getProdVer(),
                    up.getFileSize(), null, null,
                    up.getFilePath());

            Update update = this.appGenerator.generateUpdate(
                    new Date(null, up.getLastDeployDt()),
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

		this.log.info("##### updateAutoUpdateList start!!!!!!!!!!");
		return res;
	}
}
