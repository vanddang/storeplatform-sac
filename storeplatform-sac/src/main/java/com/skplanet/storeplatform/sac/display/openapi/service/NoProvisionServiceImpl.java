/**
 * 
 */
package com.skplanet.storeplatform.sac.display.openapi.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.NoProvisionSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.NoProvisionSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 상품 검색 요청(BY 상품명) 구현체 Service 구현체
 * 
 * Updated on : 2014. 3. 3. Updated by : 백승현, 인크로스.
 */
@Service
public class NoProvisionServiceImpl implements NoProvisionService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.biz.product.service.DownloadBestService#DownloadBestService(com.skplanet
	 * .storeplatform.sac.client.product.vo.downloadBestSacReqVO)
	 */
	@Override
	public NoProvisionSacRes searchProductByNameNoProvisioningList(NoProvisionSacReq noProvisionSacReq,
			SacRequestHeader requestheader) {

		TenantHeader tenantHeader = requestheader.getTenantHeader();

		String tenantId = tenantHeader.getTenantId();
		String langCd = tenantHeader.getLangCd();
		String imageCd = DisplayConstants.DP_APP_REPRESENT_IMAGE_CD;
		String rshpCd = DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD;

		NoProvisionSacRes response = new NoProvisionSacRes();
		CommonResponse commonResponse = new CommonResponse();

		int offset = 1; // default
		int count = 20; // default

		if (noProvisionSacReq.getOffset() != null) {
			offset = noProvisionSacReq.getOffset();
		}
		noProvisionSacReq.setOffset(offset); // set offset

		if (noProvisionSacReq.getCount() != null) {
			count = noProvisionSacReq.getCount();
		}
		count = offset + count - 1;
		noProvisionSacReq.setCount(count); // set count

		/*
		 * 정렬조건 (최신순: "recent", 평점순: "popular")
		 */
		noProvisionSacReq.setOrderedBy(noProvisionSacReq.getOrderedBy());
		noProvisionSacReq.setSearchKeyword(noProvisionSacReq.getSearchKeyword()); // 검색어

		/*
		 * TOP_MENU_ID 확인
		 */
		String[] arrayTopMenuId = noProvisionSacReq.getTopMenuIdList().split("\\+");
		noProvisionSacReq.setArrayTopMenuId(arrayTopMenuId);

		// Request Parameter
		this.log.debug("####### tenantId : " + tenantId);
		this.log.debug("####### langCd : " + langCd);
		this.log.debug("####### imageCd : " + imageCd);
		this.log.debug("####### searchKeyword : " + noProvisionSacReq.getSearchKeyword());
		this.log.debug("####### orderedBy : " + noProvisionSacReq.getOrderedBy());
		this.log.debug("####### offset : " + noProvisionSacReq.getOffset());
		this.log.debug("####### count : " + noProvisionSacReq.getCount());

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("req", noProvisionSacReq);
		paramMap.put("tenantId", tenantId);
		paramMap.put("langCd", langCd);
		paramMap.put("imageCd", imageCd);
		paramMap.put("rshpCd", rshpCd);
		paramMap.put("arrayTopMenuId", arrayTopMenuId);
		paramMap.put("searchKeyword", noProvisionSacReq.getSearchKeyword());
		paramMap.put("orderedBy", noProvisionSacReq.getOrderedBy());
		paramMap.put("offset", noProvisionSacReq.getOffset());
		paramMap.put("count", noProvisionSacReq.getCount());

		Identifier identifier = null;
		List<Product> productList = new ArrayList<Product>();

		Product product = null;
		List<MetaInfo> noProvisioning = null;

		// OpenApi 상품 검색 요청(BY 상품명) - No Provisioning
		noProvisioning = this.commonDAO.queryForList("OpenApi.searchProductByNameNoProvisioningList", paramMap,
				MetaInfo.class);

		if (noProvisioning.size() != 0) {

			Iterator<MetaInfo> iterator = noProvisioning.iterator();
			List<Identifier> identifierList = null;

			while (iterator.hasNext()) {

				MetaInfo metaInfo = iterator.next();

				product = new Product();
				identifierList = new ArrayList<Identifier>();

				identifier = this.commonGenerator.generateIdentifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD,
						metaInfo.getChnlProdId());
				identifierList.add(identifier);

				identifier = this.commonGenerator.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD,
						metaInfo.getProdId());
				identifierList.add(identifier);

				product.setIdentifierList(identifierList); // 상품 ID

				product.setMenuList(this.commonGenerator.generateMenuList(metaInfo)); // 상품 메뉴정보

				List<Source> sourceList = new ArrayList<Source>();
				sourceList.add(this.commonGenerator.generateSource(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL,
						metaInfo.getImagePath()));

				product.setSourceList(sourceList); // 상품 이미지 정보

				product.setPrice(this.commonGenerator.generatePrice(metaInfo)); // 상품가격

				product.setRights(this.commonGenerator.generateRights(metaInfo)); // 이용권한

				product.setAccrual(this.commonGenerator.generateAccrual(metaInfo)); // 평점정보

				product.setTitle(this.commonGenerator.generateTitle(metaInfo)); // 상품명

				product.setProductExplain(metaInfo.getProdBaseDesc()); // 상품 설명

				productList.add(product);
				commonResponse.setTotalCount(metaInfo.getTotalCount());
			}
		}
		response.setCommonResponse(commonResponse);
		response.setProductList(productList);
		return response;
	}
}
