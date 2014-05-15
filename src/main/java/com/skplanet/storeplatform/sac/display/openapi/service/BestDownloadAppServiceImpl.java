/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.openapi.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.BestDownloadAppSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.BestDownloadAppSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.DeviceHeader;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.AppInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;

/**
 * ProductCategory Service 인터페이스(CoreStoreBusiness) 구현체
 * 
 * Updated on : 2014. 1. 21. Updated by : 이석희, 인크로스.
 */
@Service
public class BestDownloadAppServiceImpl implements BestDownloadAppService {

	// private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	@Autowired
	private DisplayCommonService commonService;
	@Autowired
	private CommonMetaInfoGenerator commonGenerator;
	@Autowired
	private AppInfoGenerator appInfoGenerator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.biz.product.service.BestDownloadAppService#BestDownloadAppService(com.skplanet
	 * .storeplatform.sac.client.product.vo.BestDownloadAppSacReqVO)
	 */
	@Override
	public BestDownloadAppSacRes searchBestDownloadAppList(SacRequestHeader requestheader,
			BestDownloadAppSacReq bestDownloadAppSacReq) {
		TenantHeader tenantHeader = requestheader.getTenantHeader();
		DeviceHeader deviceHeader = requestheader.getDeviceHeader();

		bestDownloadAppSacReq.setTenantId(tenantHeader.getTenantId());
		bestDownloadAppSacReq.setLangCd(tenantHeader.getLangCd());
		bestDownloadAppSacReq.setDeviceModelCd(deviceHeader.getModel());
		bestDownloadAppSacReq.setAnyDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);

		BestDownloadAppSacRes response = new BestDownloadAppSacRes();
		CommonResponse commonResponse = new CommonResponse();

		List<Product> productList = new ArrayList<Product>();

		Product product = null;
		String stdDt = null;
		String listId = bestDownloadAppSacReq.getListId();
		String orderedBy = bestDownloadAppSacReq.getOrderedBy();
		String topMenuId = bestDownloadAppSacReq.getTopMenuId();

		// if (( sOrder.equals("C") &&
		// (sCateGoryType.equals("DP000509")||sCateGoryType.equals("DP000517")||sCateGoryType.equals("DP000518")) )
		// || ( sOrder.equals("F") &&
		// (sCateGoryType.equals("DP000509")||sCateGoryType.equals("DP000517")||sCateGoryType.equals("DP000518")) )
		// || ( sOrder.equals("A") &&
		// (sCateGoryType.equals("DP000509")||sCateGoryType.equals("DP000517")||sCateGoryType.equals("DP000518")) )
		// || ( sOrder.equals("F") && sCateGoryType.equals("DP000513") )) {
		// throw new NoDataException();
		// }

		if (StringUtils.isNotEmpty(listId)) {
			stdDt = this.commonService.getBatchStandardDateString(tenantHeader.getTenantId(),
					bestDownloadAppSacReq.getListId());
			bestDownloadAppSacReq.setStdDt(stdDt);
		}

		int offset = 1; // default
		int count = 20; // default

		if (bestDownloadAppSacReq.getOffset() != null) {
			offset = bestDownloadAppSacReq.getOffset();
		}
		bestDownloadAppSacReq.setOffset(offset); // set offset

		if (bestDownloadAppSacReq.getCount() != null) {
			count = bestDownloadAppSacReq.getCount();
		}
		count = offset + count - 1;
		bestDownloadAppSacReq.setCount(count); // set count

		List<MetaInfo> bestDownloadAppList = null;

		if (StringUtils.isNotEmpty(orderedBy)) {
			if (!DisplayConstants.DP_ORDEREDBY_TYPE_RECENT.equals(orderedBy)
					&& !DisplayConstants.DP_ORDEREDBY_TYPE_POPULAR.equals(orderedBy)) {
				throw new StorePlatformException("SAC_DSP_0015", "recent|popular", orderedBy);
			}
		}

		bestDownloadAppSacReq.setImageCd(DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);

		if (DisplayConstants.DP_ORDEREDBY_TYPE_RECENT.equals(orderedBy)
				|| DisplayConstants.DP_ORDEREDBY_TYPE_POPULAR.equals(orderedBy)) {
			// OpenApi Best Download 상품 조회 (최신, 평점순)
			bestDownloadAppList = this.commonDAO.queryForList("OpenApi.searchBestDownloadAppListByOrder",
					bestDownloadAppSacReq, MetaInfo.class);
		} else {
			// OpenApi Best Download 상품 조회 (유료, 무료, 신규, 추천)
			bestDownloadAppList = this.commonDAO.queryForList("OpenApi.searchBestDownloadAppListByListId",
					bestDownloadAppSacReq, MetaInfo.class);
		}

		if (bestDownloadAppList.size() != 0) {

			Iterator<MetaInfo> iterator = bestDownloadAppList.iterator();
			while (iterator.hasNext()) {

				MetaInfo metaInfo = iterator.next();
				product = new Product();
				List<Identifier> identifierList = new ArrayList<Identifier>();
				Identifier chnlIdentifier = this.commonGenerator.generateIdentifier(
						DisplayConstants.DP_CHANNEL_IDENTIFIER_CD, metaInfo.getProdId());
				identifierList.add(chnlIdentifier);
				Identifier epsdIdentifier = this.commonGenerator.generateIdentifier(
						DisplayConstants.DP_EPISODE_IDENTIFIER_CD, metaInfo.getPartProdId());
				identifierList.add(epsdIdentifier);
				product.setIdentifierList(identifierList); // 상품 ID
				product.setMenuList(this.commonGenerator.generateMenuList(metaInfo)); // 상품 메뉴정보
				product.setSourceList(this.commonGenerator.generateSourceList(metaInfo)); // 상품 이미지
				product.setPrice(this.commonGenerator.generatePrice(metaInfo)); // 상품가격
				product.setAccrual(this.commonGenerator.generateAccrual(metaInfo)); // 참여자 정보
				if (DisplayConstants.DP_APPALL_TOP_MENU_ID.equals(topMenuId)
						|| DisplayConstants.DP_GAME_TOP_MENU_ID.equals(topMenuId)
						|| DisplayConstants.DP_FUN_TOP_MENU_ID.equals(topMenuId)
						|| DisplayConstants.DP_LIFE_LIVING_TOP_MENU_ID.equals(topMenuId)
						|| DisplayConstants.DP_LANG_EDU_TOP_MENU_ID.equals(topMenuId)) {
					product.setApp(this.appInfoGenerator.generateApp(metaInfo)); // App 상세정보
				}
				product.setTitle(this.commonGenerator.generateTitle(metaInfo)); // 상품명
				product.setProductExplain(metaInfo.getProdBaseDesc()); // 상품 설명
				product.setRights(this.commonGenerator.generateRights(metaInfo)); // 상품 이용 등급

				productList.add(product);

				commonResponse.setTotalCount(metaInfo.getTotalCount());
			}
		}

		response.setCommonResponse(commonResponse);
		response.setProductList(productList);
		return response;
	}
}
