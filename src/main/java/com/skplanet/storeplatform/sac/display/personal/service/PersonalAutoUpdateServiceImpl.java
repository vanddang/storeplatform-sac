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
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.SearchUserSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UserInfoSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.*;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Date;
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
import com.skplanet.storeplatform.sac.display.personal.vo.MemberInfo;
import com.skplanet.storeplatform.sac.display.personal.vo.SubContentInfo;
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
 * <p/>
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

        String tenantId = header.getTenantHeader().getTenantId();
        String langCd = header.getTenantHeader().getLangCd();
        String deviceModelCd = header.getDeviceHeader().getModel();

        this.log.debug("##### updateAutoUpdateList start!!!!!!!!!!");
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
        MemberInfo member = appUpdateSupportService.getMemberInfo(deviceId);

        final Integer limitCnt = req.getUpdLimitCnt();
        if(limitCnt != null && limitCnt < 0)
            throw new StorePlatformException("SAC_DSP_0006");

        // 다운로드 서버 상태 조회는 & 앱 버전 정보 활용 조회 처리 & 업그레이드 관리이력 조회는 tenant 단에서 처리하기 때문에 제외

        /**************************************************************
         * Package 명으로 상품 조회
         **************************************************************/
        final int reqSize = packageInfoList.size();
        List<String> hashedPkgList = new ArrayList<String>(reqSize);
        Map<String, UpdatePkgDetail> pkgReqMap = new LinkedHashMap<String, UpdatePkgDetail>(reqSize);

        for (String s : packageInfoList) {
            UpdatePkgDetail dtl = new UpdatePkgDetail(s);
            if (StringUtils.isNotEmpty(dtl.getPkgNm())) {
                hashedPkgList.add(DisplayCryptUtils.hashPkgNm(dtl.getPkgNm()));
                pkgReqMap.put(dtl.getPkgNm(), dtl);
            }
        }

        List<SubContentInfo> subContentInfos = appUpdateSupportService.searchSubContentByPkg(deviceModelCd, hashedPkgList, true);

        this.log.debug("##### auto update target list  : {}", hashedPkgList);
        this.log.debug("##### auto update target cnt   : {}", hashedPkgList.size());

        Map<String, UpdateProduct> upMap = new LinkedHashMap<String, UpdateProduct>(reqSize);
        List<String> pidPurList = new ArrayList<String>();

        for (SubContentInfo scInfo : subContentInfos) {

            // 상품 메타데이터 조회
            UpdateProduct up = updateProductInfoManager.getUpdateProductInfo(new UpdateProductParam(tenantId, langCd, scInfo.getProdId(), scInfo.getSubContentsId()));

            if(up == null) {
                continue;
            }

            // Version 이 높은 대상 가려내기
            UpdatePkgDetail pkgReqDtl = pkgReqMap.get(up.getApkPkgNm());

            if (pkgReqDtl != null) {
                if (up.getApkVer() > pkgReqDtl.getVer()) {
                    upMap.put(scInfo.getApkPkgNm(), up);
                    pidPurList.add(up.getPartProdId());
                }
            }
        }

        if (upMap.isEmpty()) {
            throw new StorePlatformException("SAC_DSP_0006");
        }

        /**************************************************************
         * 구매여부 및 최근 업데이트 정보 추출
         **************************************************************/
        List<Product> productList = new ArrayList<Product>();
        final List<String> forTlogAppIdList = new ArrayList<String>();
        int resultSize = 0;

        Set<String> purSet = appUpdateSupportService.getPurchaseSet(tenantId, member.getUserKey(), member.getDeviceKey(), pidPurList);
        if (!purSet.isEmpty()) {

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

        if (productList.isEmpty()) {
            throw new StorePlatformException("SAC_DSP_0006");
        }

        new TLogUtil().set(new ShuttleSetter() {
            @Override
            public void customize(TLogSentinelShuttle shuttle) {
                shuttle.app_id(forTlogAppIdList);
            }
        });

        // Response 정보 가공
        CommonResponse commonResponse = new CommonResponse();
        PersonalAutoUpdateRes res = new PersonalAutoUpdateRes();

        commonResponse.setTotalCount(productList.size());
        res.setCommonResponse(commonResponse);
        res.setProductList(productList);

        this.log.debug("##### updateAutoUpdateList finished !!!!!!!!!!");
        return res;
    }

    private Product generateUpdateApp(UpdateProduct up) {
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

        return product;
    }
}
