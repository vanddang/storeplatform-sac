/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.other.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherServiceGroupSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherServiceGroupSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherTMembershipReq;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherTMembershipRes;
import com.skplanet.storeplatform.sac.client.display.vo.other.OtherTMembershipUseStatusRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Menu;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Point;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.TmembershipDcInfo;

/**
 * Tmembership 할인율 조회
 * 
 * Updated on : 2014. 3. 31. Updated by : 윤주영, SK 플래닛.
 */
@Service
public class OtherTMembershipServiceImpl implements OtherTMembershipService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OtherServiceGroupService otherServiceGroupService;

	@Autowired
	private DisplayCommonService commonService;

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/**
	 * <pre>
	 * Tmembership 할인율 조회.
	 * </pre>
	 * 
	 * @param OtherTMembershipReq
	 * @return OtherTMembershipRes
	 * 
	 * @param req
	 *            req
	 * @param header
	 *            header
	 */
	@Override
	public OtherTMembershipRes searchTMembership(OtherTMembershipReq req, SacRequestHeader header) {
        this.logger.debug("---------------------------------------------------------------------------------------------");
        this.logger.debug("searchTMembership.start !!");
        this.logger.debug("req.getProductId() {}", req.getProductId());
        this.logger.debug("tenantId {}", header.getTenantHeader().getTenantId());
        this.logger.debug("---------------------------------------------------------------------------------------------");

		int totalCount = 0;

		String tenantId = header.getTenantHeader().getTenantId();

		OtherTMembershipRes tmembershipRes = new OtherTMembershipRes();

		OtherServiceGroupSacReq appReq = new OtherServiceGroupSacReq();
		CommonResponse commonRes = new CommonResponse();

		appReq.setList(req.getProductId());

		OtherServiceGroupSacRes appRes = new OtherServiceGroupSacRes();
		try {
			appRes = this.otherServiceGroupService.searchServiceGroupList(appReq, header);
		} catch (StorePlatformException se) {
			throw se;
		} catch (Exception e) {
			throw new StorePlatformException("SAC_DSP_0005", "T Membership");
		}

		if (appRes.getCommonResponse().getTotalCount() <= 0) {
			throw new StorePlatformException("SAC_DSP_0005", "T Membership");
		}
		if (appRes.getProductList().isEmpty()) {
			throw new StorePlatformException("SAC_DSP_0005", "T Membership");
		}
        this.logger.debug("appRes {}", appRes);

		List<Product> productList = appRes.getProductList();
		Point point = new Point();
		String svcGrpCd = "";

		for (Product product : productList) {
			// 서비스 그룹 코드 추출
			for (Menu menu : product.getMenuList()) {
				if (menu.getType().equalsIgnoreCase(DisplayConstants.DP_SVC_GRP_CD_TYPE)) {
					svcGrpCd = menu.getId();
				}
			}
			for (Menu menu : product.getMenuList()) {
				if (menu.getType().equalsIgnoreCase(DisplayConstants.DP_MENU_TOPCLASS_TYPE)) {
					// tmembership 할인율
					TmembershipDcInfo dcInfo = this.commonService.getTmembershipDcRateForMenu(tenantId, menu.getId());
                    this.logger.debug("dcInfo {}", dcInfo);

					if (svcGrpCd.equalsIgnoreCase(DisplayConstants.DP_TSTORE_FREEPASS_PROD_SVC_GRP_CD)) { // freepass
						point.setDiscountRate(dcInfo.getFreepassDcRate());
						point.setType(DisplayConstants.DC_RATE_TYPE_FREEPASS);
					} else { // normal
						point.setDiscountRate(dcInfo.getNormalDcRate());
						point.setType(DisplayConstants.DC_RATE_TYPE_NORMAL);
					}
					point.setName(DisplayConstants.DC_RATE_TMEMBERSHIP);
					totalCount = 1;
					break;
				}
			}
		}
        this.logger.debug("point {}", point);

		tmembershipRes.setPoint(point);
		commonRes.setTotalCount(totalCount);
		tmembershipRes.setCommonResponse(commonRes);

		return tmembershipRes;
	}

	/**
	 * <pre>
	 * T맴버십 할인 사용여부 조회
	 * </pre>
	 * 
	 * @return OtherTMembershipRes
	 * 
	 * @param header
	 *            header
	 */
	@Override
	public OtherTMembershipUseStatusRes searchTMembershipUseStatus(SacRequestHeader header) {

		Double tMemberShipDcRate;
		OtherTMembershipUseStatusRes res = new OtherTMembershipUseStatusRes();
		TenantHeader tenantHeader = header.getTenantHeader();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tenantHeader", tenantHeader);
		tMemberShipDcRate = (Double) this.commonDAO.queryForObject("OtherTmembership.searchTMembershipUseStatus",
				paramMap);
		if (tMemberShipDcRate == null || tMemberShipDcRate <= 0) {
			res.setUseStatus("N");
		} else if (tMemberShipDcRate > 0) {
			res.setUseStatus("Y");
		}
		return res;
	}
}
