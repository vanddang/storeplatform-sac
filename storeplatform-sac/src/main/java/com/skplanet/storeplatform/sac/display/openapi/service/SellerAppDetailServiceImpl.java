/**
 * 
 */
package com.skplanet.storeplatform.sac.display.openapi.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SellerAppDetailReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SellerAppDetailRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Url;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.AppInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * App 상세 정보 요청(CoreStoreBusiness) 구현체.
 * 
 * Updated on : 2014. 3. 6. Updated by : 오승민, 인크로스.
 */
@Service
public class SellerAppDetailServiceImpl implements SellerAppDetailService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

	@Autowired
	private AppInfoGenerator appGenerator;

	@Value("#{propertiesForSac['web.poc.domain']}")
	private String webPocDomain;

	@Value("#{propertiesForSac['web.poc.game.detail.url']}")
	private String webPocGameDetailUrl;

	@Value("#{propertiesForSac['web.poc.apps.detail.url']}")
	private String webPocAppsDetailUrl;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.skplanet.storeplatform.sac.display.openapi.service.SellerAppDetailService#getSellerAppDetail(com.skplanet
	 * .storeplatform.sac.client.display.vo.openapi.SellerAppDetailReq,
	 * com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader)
	 */
	@Override
	public SellerAppDetailRes getSellerAppDetail(SellerAppDetailReq req, SacRequestHeader header) {
		SellerAppDetailRes res = new SellerAppDetailRes();
		Product product = new Product();
		CommonResponse commonResponse = new CommonResponse();
		TenantHeader tenantHeader = header.getTenantHeader();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tenantHeader", tenantHeader);
		paramMap.put("req", req);
		paramMap.put("partClsfCd", DisplayConstants.DP_PART_PARENT_CLSF_CD);
		paramMap.put("gameUrl", this.webPocDomain + this.webPocGameDetailUrl);
		paramMap.put("appUrl", this.webPocDomain + this.webPocAppsDetailUrl);
		paramMap.put("imageCd", DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
		MetaInfo metaInfo = this.commonDAO.queryForObject("OpenApi.getAppDetail", paramMap, MetaInfo.class);

		if (metaInfo != null) {
			product.setTitle(this.commonGenerator.generateTitle(metaInfo));
			product.setDate(this.commonGenerator.generateDate(DisplayConstants.DP_DATE_REG, metaInfo.getRegDt()));
			product.setSalesStatus(metaInfo.getProdStatusCd());
			product.setApp(this.appGenerator.generateApp(metaInfo));
			product.setDistributor(this.commonGenerator.generateDistributor(metaInfo));
			product.setProductExplain(metaInfo.getProdBaseDesc());
			product.setSourceList(this.commonGenerator.generateSourceList(metaInfo));
			List<Url> urlList = new ArrayList<Url>();
			urlList.add(this.commonGenerator.generateUrl(DisplayConstants.DP_EXTERNAL, metaInfo.getWebUrl()));
			product.setUrlList(urlList);

			commonResponse.setTotalCount(1);
		} else {
			commonResponse.setTotalCount(0);
		}
		res.setProduct(product);
		res.setCommonResponse(commonResponse);
		return res;
	}

}
