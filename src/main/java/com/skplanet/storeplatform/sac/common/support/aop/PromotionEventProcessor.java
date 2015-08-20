/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.common.support.aop;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.PromotionEventAccessor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Coupon;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Point;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.cache.service.CachedExtraInfoManager;
import com.skplanet.storeplatform.sac.display.cache.vo.GetProductBaseInfoParam;
import com.skplanet.storeplatform.sac.display.cache.vo.GetPromotionEventParam;
import com.skplanet.storeplatform.sac.display.cache.vo.ProductBaseInfo;
import com.skplanet.storeplatform.sac.display.cache.vo.PromotionEvent;
import com.skplanet.storeplatform.sac.display.common.vo.MileageInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.other.common.constant.OtherConstants;
import com.skplanet.storeplatform.sac.other.sacservice.service.SacServiceService;
import com.skplanet.storeplatform.sac.other.sacservice.vo.SacService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * PromotionEventProcessor
 * </p>
 * Updated on : 2015. 07. 02 Updated by : 정희원, SK 플래닛.
 */
public class PromotionEventProcessor {

    private final Logger logger = LoggerFactory.getLogger(PromotionEventProcessor.class);
    public static final String DEFAULT_TENANT_ID = "S01";

    @Autowired
    private CachedExtraInfoManager cachedExtraInfoManager;

    @Autowired
    private CommonMetaInfoGenerator metaInfoGenerator;

    @Autowired
    private SacServiceService sacServiceDataService;

    /**
     * 프로모션 이벤트는 목록 응답(productList) 대상으로만 처리한다.
     * @param retVal 컨트롤러 응답(*Res)객체
     */
    public void postProcess(Object retVal) {

//        if(!checkServiceAvailable())
//            return;

        if(!PromotionEventManager.isRun())
            return;

        List productListTarget = extractTarget(retVal);
        String tenantId = getTenantId();

        for (Object item : productListTarget) {
            if(!(item instanceof PromotionEventAccessor))
                continue;

            PromotionEventAccessor product = (PromotionEventAccessor) item;

            if (containsPromotionInfo(product.getPointList()))
                continue;

            // 이용권은 식별자가 freepass라는 이름으로 되어 있음
            Map<String, String> idMap = convertIdentifierList(product.getIdentifierList());

            String chnlId = idMap.get("channel");
            String menuId = extractMenu(product.getMenuList());

            // chnlId가 존재하지 않으면 epsdId로 chnlId 조회
            if (Strings.isNullOrEmpty(chnlId) || Strings.isNullOrEmpty(menuId)) {

                String prodId = chnlId != null ? chnlId : idMap.get("episode");

                if (!Strings.isNullOrEmpty(prodId)) {
                    logger.debug("유효한 상품 식별자가 존재하지 않습니다.");
                    ProductBaseInfo productBaseInfo = cachedExtraInfoManager.getProductBaseInfo(new GetProductBaseInfoParam(prodId));

                    if (productBaseInfo == null) {
                        logger.warn("#{} 상품은 존재하지 않습니다.", prodId);
                        continue;
                    }
                    chnlId = productBaseInfo.getChnlId();
                    // TODO menuId = productBaseInfo.getMenuId();
                }

                if(Strings.isNullOrEmpty(chnlId) && Strings.isNullOrEmpty(menuId))
                    continue;
            }

            // 매핑된 메뉴ID를 찾을 수 없더라도 상품ID로 시도한다.
            if (Strings.isNullOrEmpty(menuId)) {
                logger.warn("#{}는 유효한 메뉴ID매핑을 찾을 수 없습니다.", chnlId != null ? chnlId : idMap.get("episode"));
            }

            // attach promotion event
            PromotionEvent promotionEvent = cachedExtraInfoManager.getPromotionEvent(new GetPromotionEventParam(tenantId, menuId, chnlId));
            MileageInfo mileageInfo = new MileageInfo();
            if (promotionEvent != null) {
                mileageInfo.setPolicyTargetCd(promotionEvent.getTargetTp());
                mileageInfo.setRateLv1(promotionEvent.getRateGrd1());
                mileageInfo.setRateLv2(promotionEvent.getRateGrd2());
                mileageInfo.setRateLv3(promotionEvent.getRateGrd3());
            }

            product.setPointList(Objects.firstNonNull(product.getPointList(), new ArrayList<Point>()));
            product.getPointList().addAll(metaInfoGenerator.generateMileage(mileageInfo));
        }
    }

    private boolean checkServiceAvailable() {
        SacService rtnSacService = sacServiceDataService.getServiceActive(new SacService(OtherConstants.SAC_SERVICE_MILEAGE));
        return rtnSacService != null && rtnSacService.isActive();
    }

    @SuppressWarnings("unchecked")
    private List extractTarget(Object retVal) {
        Class<?> aClass = retVal.getClass();

        try {
            Field productListFld = aClass.getDeclaredField("productList");
            productListFld.setAccessible(true);
            Object value = productListFld.get(retVal);
            if(value != null)
                return (List)value;

        } catch (Exception e) {
            logger.debug("productList 필드를 찾을 수 없음.", e);
        }

        return Lists.newArrayList();
    }

    /**
     * 프로모션 정보가 기존재 하는지 확인한다.
     * @param pointList
     * @return 기존재 하는 경우 true
     */
    private boolean containsPromotionInfo(List<Point> pointList) {
        if(CollectionUtils.isEmpty(pointList))
            return false;

        for (Point point : pointList) {
            if("mileage".equals(point.getName())) {
                return true;
            }
        }

        return false;
    }

    /**
     * channelId 또는 episodeId만 추출해낸다.
     * @param obj identifierList
     * @return 맵
     */
    private Map<String, String> convertIdentifierList(List<Identifier> obj) {
        Map<String, String> res = Maps.newHashMap();

        if(CollectionUtils.isEmpty(obj))
            return res;

        for (Identifier id : obj) {
            String type = id.getType();
            if("channel".equals(type) || "episode".equals(type))
                res.put(type, id.getText());
        }
        return res;
    }

    /**
     * 유효한 메뉴ID를 추출해낸다.
     * 2Depth 메뉴를 우선으로 찾으며 만약 없는 경우 topMenuId를 응답한다.
     * @param obj 메뉴리스트
     * @return 2Depth or 1Depth 메뉴. 유효한 것이 없는 경우 null
     */
    private String extractMenu(List<Menu> obj) {
        if(CollectionUtils.isEmpty(obj))
            return null;

        String topMenuId = null;
        for (Menu menu : obj) {
            if(menu.getId() == null)
                continue;

            if (menu.getId().startsWith("DP") && menu.getType() == null)
                return menu.getId();

            if ("topClass".equals(menu.getType()))
                topMenuId = menu.getId();
        }

        return topMenuId;
    }

    /**
     * 테넌트ID를 조회한다.
     * @return tenantId
     */
    private String getTenantId() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        if(requestAttributes == null)
            return DEFAULT_TENANT_ID;

        SacRequestHeader header = (SacRequestHeader) requestAttributes.getAttribute(SacRequestHeader.class.getName(), RequestAttributes.SCOPE_REQUEST);

        if(header == null)
            return DEFAULT_TENANT_ID;

        return header.getTenantHeader().getTenantId();
    }

}
