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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.BestDownloadMMSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.BestDownloadMMSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.sci.SellerSearchSCI;
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
public class BestDownloadMMServiceImpl implements BestDownloadMMService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	@Autowired
	private DisplayCommonService commonService;
	@Autowired
	private CommonMetaInfoGenerator commonGenerator;
	@Autowired
	private AppInfoGenerator appInfoGenerator;
	@Autowired
	private SellerSearchSCI sellerSearchSCI;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.BestDownloadMMService#BestDownloadMMService(com.skplanet
	 * .storeplatform.sac.client.product.vo.BestDownloadMMSacReqVO)
	 */
	@Override
	public BestDownloadMMSacRes searchBestDownloadMMList(SacRequestHeader requestheader,
			BestDownloadMMSacReq bestDownloadMMSacReq) {
		TenantHeader tenantHeader = requestheader.getTenantHeader();
		DeviceHeader deviceHeader = requestheader.getDeviceHeader();

		bestDownloadMMSacReq.setTenantId(tenantHeader.getTenantId());
		bestDownloadMMSacReq.setLangCd(tenantHeader.getLangCd());
		bestDownloadMMSacReq.setDeviceModelCd(deviceHeader.getModel());
		bestDownloadMMSacReq.setAnyDeviceModelCd(DisplayConstants.DP_ANY_PHONE_4MM);

		BestDownloadMMSacRes response = new BestDownloadMMSacRes();
		CommonResponse commonResponse = new CommonResponse();

		List<Product> productList = new ArrayList<Product>();

		Product product = null;
		String stdDt = null;
		String listId = bestDownloadMMSacReq.getListId();
		String orderedBy = bestDownloadMMSacReq.getOrderedBy();
		String topMenuId = bestDownloadMMSacReq.getTopMenuId();
		String channelId = bestDownloadMMSacReq.getChannelId();

		if (StringUtils.isNotEmpty(listId)) {
			stdDt = this.commonService.getBatchStandardDateString(tenantHeader.getTenantId(),
					bestDownloadMMSacReq.getListId());
			bestDownloadMMSacReq.setStdDt(stdDt);
		}

		int offset = 1; // default
		int count = 20; // default

		if (bestDownloadMMSacReq.getOffset() != null) {
			offset = bestDownloadMMSacReq.getOffset();
		}
		bestDownloadMMSacReq.setOffset(offset); // set offset

		if (bestDownloadMMSacReq.getCount() != null) {
			count = bestDownloadMMSacReq.getCount();
		}
		count = offset + count - 1;
		bestDownloadMMSacReq.setCount(count); // set count

		List<MetaInfo> bestDownloadMMList = null;

		if (StringUtils.isNotEmpty(orderedBy)) {
			if (!DisplayConstants.DP_ORDEREDBY_TYPE_RECENT.equals(orderedBy)
					&& !DisplayConstants.DP_ORDEREDBY_TYPE_POPULAR.equals(orderedBy)) {
				throw new StorePlatformException("SAC_DSP_0015", "recent|popular", orderedBy);
			}
		}

		/** OpenApi Best Download 멀티미디어 상품 조회 **/
		this.log.debug("---------------------------------------------------------------------");
		this.log.debug("[BestDownloadMMServiceImpl] topMenuId : {}" + topMenuId);
		this.log.debug("[BestDownloadMMServiceImpl] listId : {}" + listId);
		this.log.debug("[BestDownloadMMServiceImpl] orderedBy : {}" + orderedBy);
		this.log.debug("[BestDownloadMMServiceImpl] channelId : {}" + channelId);
		this.log.debug("---------------------------------------------------------------------");

		if (StringUtils.isEmpty(orderedBy) && StringUtils.isEmpty(channelId)) {
			if (DisplayConstants.DP_COMIC_TOP_MENU_ID.equals(topMenuId)
					|| DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId)
					|| DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(topMenuId)) { // Ebook ,Comic, Music
				bestDownloadMMSacReq.setImageCd(DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);

				if (DisplayConstants.DP_EBOOK_TOP_MENU_ID.equals(topMenuId) && "RNK000000003".equals(listId)) {
					throw new StorePlatformException("SAC_DSP_0016");
				} else {
					// 유/무료
					if ("RNK000000006".equals(listId) || "RNK000000003".equals(listId)) {
						this.log.debug("---------------------------------------------------------------------");
						this.log.debug("[BestDownloadMMServiceImpl] call OpenApi.searchPayFreeList : {}");
						this.log.debug("---------------------------------------------------------------------");
						bestDownloadMMList = this.commonDAO.queryForList("OpenApi.searchPayFreeList",
								bestDownloadMMSacReq, MetaInfo.class);
						// 추천/신규
					} else if ("ADM000000013".equals(listId) || "TGR000000001".equals(listId)
							|| "ADM000000001".equals(listId)) {
						this.log.debug("---------------------------------------------------------------------");
						this.log.debug("[BestDownloadMMServiceImpl] call OpenApi.searchRecommendNewList : {}");
						this.log.debug("---------------------------------------------------------------------");
						bestDownloadMMList = this.commonDAO.queryForList("OpenApi.searchRecommendNewList",
								bestDownloadMMSacReq, MetaInfo.class);
					} else {
						throw new StorePlatformException("SAC_DSP_0016");
					}
				}

			} else if (DisplayConstants.DP_VOD_TOP_MENU_ID.equals(topMenuId)
					|| DisplayConstants.DP_MOVIE_TOP_MENU_ID.equals(topMenuId)
					|| DisplayConstants.DP_TV_TOP_MENU_ID.equals(topMenuId)) { // VOD
				bestDownloadMMSacReq.setImageCd(DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);

				if (StringUtils.isEmpty(listId)) {
					throw new StorePlatformException("SAC_DSP_0002", "listId", listId);
				}
				// VOD 신규
				if ("TGR000000002".equals(listId)) {
					this.log.debug("---------------------------------------------------------------------");
					this.log.debug("[BestDownloadMMServiceImpl] call OpenApi.searchNewVodList : {}");
					this.log.debug("---------------------------------------------------------------------");
					bestDownloadMMList = this.commonDAO.queryForList("OpenApi.searchNewVodList", bestDownloadMMSacReq,
							MetaInfo.class);
				}
			}
		} else {
			if (StringUtils.isEmpty(orderedBy)) {
				throw new StorePlatformException("SAC_DSP_0002", "orderedBy", orderedBy);
			}

			if (DisplayConstants.DP_MUSIC_TOP_MENU_ID.equals(topMenuId)) {
				this.log.debug("---------------------------------------------------------------------");
				this.log.debug("[BestDownloadMMServiceImpl] call OpenApi.searchBestDownloadAppListByOrder : {}");
				this.log.debug("---------------------------------------------------------------------");
				bestDownloadMMList = this.commonDAO.queryForList("OpenApi.searchBestDownloadAppListByOrder",
						bestDownloadMMSacReq, MetaInfo.class);
				// throw new StorePlatformException("SAC_DSP_0016");
			} else {
				// 뮤직을 제외한 나머지 Menu 최신순 평점순일때
				this.log.debug("---------------------------------------------------------------------");
				this.log.debug("[BestDownloadMMServiceImpl] call OpenApi.searchProductList : {}");
				this.log.debug("---------------------------------------------------------------------");
				bestDownloadMMList = this.commonDAO.queryForList("OpenApi.searchProductList", bestDownloadMMSacReq,
						MetaInfo.class);
			}

		}

		if (bestDownloadMMList.size() != 0) {

			Iterator<MetaInfo> iterator = bestDownloadMMList.iterator();
			while (iterator.hasNext()) {

				MetaInfo metaInfo = iterator.next();
				List<Identifier> identifierList = new ArrayList<Identifier>();
				product = new Product();

				Identifier identifier = this.commonGenerator.generateIdentifier(
						DisplayConstants.DP_EPISODE_IDENTIFIER_CD, metaInfo.getProdId());
				identifierList.add(identifier);
				product.setIdentifierList(identifierList); // 상품 ID
				product.setMenuList(this.commonGenerator.generateMenuList(metaInfo)); // 상품 메뉴정보
				product.setSourceList(this.commonGenerator.generateSourceList(metaInfo)); // 상품 이미지
				product.setPrice(this.commonGenerator.generatePrice(metaInfo)); // 상품가격
				product.setAccrual(this.commonGenerator.generateAccrual(metaInfo)); // 참여자 정보
				// product.setApp(this.appInfoGenerator.generateApp(metaInfo)); // App 상세정보
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
