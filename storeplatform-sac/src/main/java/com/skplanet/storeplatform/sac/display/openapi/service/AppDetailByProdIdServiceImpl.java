/**
 *
 */
package com.skplanet.storeplatform.sac.display.openapi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.AppDetailByProductIdSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.AppDetailByProductIdSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.sci.SellerSearchSCI;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationListForProductSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationListForProductSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationListForProductSacRes.SellerMbrInfoSac;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Url;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Device;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Distributor;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.response.AppInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;

/**
 * 상품 상세 정보 요청(Product Id) Service 구현체
 *
 * Updated on : 2014. 3. 12. Updated by : 백승현, 인크로스.
 */
@Service
public class AppDetailByProdIdServiceImpl implements AppDetailByProdIdService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;
	@Autowired
	private CommonMetaInfoGenerator commonGenerator;
	@Autowired
	private AppInfoGenerator appInfoGenerator;

	@Value("#{propertiesForSac['web.poc.domain']}")
	private String webPocDomain;

	@Value("#{propertiesForSac['web.poc.game.detail.url']}")
	private String webPocGameDetailUrl;

	@Value("#{propertiesForSac['web.poc.apps.detail.url']}")
	private String webPocAppsDetailUrl;

	@Autowired
	private SellerSearchSCI sellerSearchSCI;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.skplanet.storeplatform.sac.display.openapi.service.AppDetailByProdIdService(com.skplanet
	 * com.skplanet.storeplatform.sac.client.display.vo.openapi.appDetailByProductIdSacReq)
	 */
	@Override
	public AppDetailByProductIdSacRes searchProductByProductId(AppDetailByProductIdSacReq appDetailByProductIdSacReq,
			SacRequestHeader requestheader) {
		TenantHeader tenantHeader = requestheader.getTenantHeader();

		String tenantId = tenantHeader.getTenantId();
		String langCd = tenantHeader.getLangCd();
		String imageCd = DisplayConstants.DP_APP_REPRESENT_IMAGE_CD;
		String webPocGameUrl = this.webPocDomain + this.webPocGameDetailUrl;
		String webPocAppsUrl = this.webPocDomain + this.webPocAppsDetailUrl;
		String scUrl = DisplayConstants.DP_OPENAPI_SC_URL;
		String rshpCd = DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD;

		// appDetailByProductIdSacReq.setTenantId(tenantHeader.getTenantId());
		// appDetailByProductIdSacReq.setLangCd(tenantHeader.getLangCd());
		// appDetailByProductIdSacReq.setImageCd(DisplayConstants.DP_APP_REPRESENT_IMAGE_CD);
		// appDetailByProductIdSacReq.setWebPocUrl(DisplayConstants.DP_OPENAPI_APP_URL);
		// appDetailByProductIdSacReq.setScUrl(DisplayConstants.DP_OPENAPI_SC_URL);

		AppDetailByProductIdSacRes response = new AppDetailByProductIdSacRes();
		CommonResponse commonResponse = new CommonResponse();
		List<Product> productList = new ArrayList<Product>();

		// Request Parameter
		this.log.debug("####### tenantId : " + tenantId);
		this.log.debug("####### langCd : " + langCd);
		this.log.debug("####### imageCd : " + imageCd);
		this.log.debug("####### webPocGameUrl : " + webPocGameUrl);
		this.log.debug("####### webPocAppsUrl : " + webPocAppsUrl);
		this.log.debug("####### scUrl : " + scUrl);
		this.log.debug("####### productId : " + appDetailByProductIdSacReq.getProductId());
		this.log.debug("####### rshpCd : " + rshpCd);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("req", appDetailByProductIdSacReq);
		paramMap.put("tenantId", tenantId);
		paramMap.put("langCd", langCd);
		paramMap.put("imageCd", imageCd);
		paramMap.put("webPocGameUrl", webPocGameUrl);
		paramMap.put("webPocAppsUrl", webPocAppsUrl);
		paramMap.put("scUrl", scUrl);
		paramMap.put("productId", appDetailByProductIdSacReq.getProductId());
		paramMap.put("rshpCd", rshpCd);

		Identifier identifier = null;
		Product product = null;

		List<MetaInfo> productMetaInfoList = null;
		productMetaInfoList = this.commonDAO.queryForList("OpenApi.searchProductByProductId", paramMap, MetaInfo.class);

		if (productMetaInfoList.size() != 0) {

			// 지원 단말 리스트 조회
			Device device = null;
			List<Device> deviceList = new ArrayList<Device>();

			paramMap.clear();
			paramMap.put("productId", appDetailByProductIdSacReq.getProductId());

			List<MetaInfo> resultList = this.commonDAO.queryForList("OpenApi.searchSupportDeviceListByProdId",
					paramMap, MetaInfo.class);

			if (resultList != null && !resultList.isEmpty()) {
				for (int i = 0; i < resultList.size(); i++) {
					device = new Device();

					device.setDeviceModelCd(resultList.get(i).getDeviceModelCd());
					device.setDeviceModelNm(resultList.get(i).getDeviceModelNm());
					deviceList.add(device);
				}
			} else {
				this.log.debug("####### 지원단말 목록이 존재하지 않음 #######");
			}

			// 상품 상세정보 Meta 데이타
			Iterator<MetaInfo> iterator = productMetaInfoList.iterator();

			while (iterator.hasNext()) {

				MetaInfo metaInfo = iterator.next();

				product = new Product();

				List<Identifier> identifierList = new ArrayList<Identifier>();

				identifier = this.commonGenerator.generateIdentifier(DisplayConstants.DP_CHANNEL_IDENTIFIER_CD,
						metaInfo.getChnlProdId());
				identifierList.add(identifier);

				identifier = this.commonGenerator.generateIdentifier(DisplayConstants.DP_EPISODE_IDENTIFIER_CD,
						metaInfo.getProdId());
				identifierList.add(identifier);

				product.setIdentifierList(identifierList); // 상품 ID

				product.setMenuList(this.commonGenerator.generateMenuList(metaInfo)); // 상품 메뉴정보

				List<Url> urlList = new ArrayList<Url>();
				urlList.add(this.commonGenerator.generateUrl(DisplayConstants.DP_EXTERNAL_CLIENT, metaInfo.getTinyUrl()));
				urlList.add(this.commonGenerator.generateUrl(DisplayConstants.DP_EXTERNAL_PORTAL, metaInfo.getWebUrl()));
				product.setUrlList(urlList);

				List<Source> sourceList = new ArrayList<Source>();
				sourceList.add(this.commonGenerator.generateSource(DisplayConstants.DP_SOURCE_TYPE_THUMBNAIL,
						metaInfo.getImagePath()));

				product.setSourceList(sourceList); // 상품 이미지 정보

				product.setPrice(this.commonGenerator.generatePrice(metaInfo)); // 상품가격

				product.setAccrual(this.commonGenerator.generateAccrual(metaInfo)); // 평점정보

				// App 상세정보
				App app = new App();
				app.setAid(metaInfo.getAid());
				app.setSupportedOs(metaInfo.getOsVersion());
				app.setPackageName(metaInfo.getApkPkgNm());
				app.setVersion(metaInfo.getProdVer());
				app.setVersionCode(metaInfo.getApkVer());
				product.setApp(app);

				product.setDeviceList(deviceList); // 지원 단말 목록

				product.setTitle(this.commonGenerator.generateTitle(metaInfo)); // 상품명

				// PreviewUrl
				String[] previewUrls = metaInfo.getPreviewUrl().split(";");
				List<Source> preveiwSourceList = new ArrayList<Source>();
				if (previewUrls.length > 0) {
					Source source = null;

					for (int i = 0; i < previewUrls.length; i++) {
						source = this.commonGenerator.generatePreviewSourceList(metaInfo);
						source.setUrl(previewUrls[i]);
						preveiwSourceList.add(source);
					}
					product.setPreviewSourceList(preveiwSourceList);
				}

				product.setProductExplain(metaInfo.getProdBaseDesc()); // 상품 설명

				product.setProductDetailExplain(metaInfo.getProdDtlDesc()); // 상품 상세 설명

				// 2014.06.13 판매자 정보 목록 조회 추가 (백승현K)
				String sellerMbrNo = null;
				List<String> sellerKeyList = null;
				DetailInformationListForProductSacReq sellerReq = null;
				DetailInformationListForProductSacRes sellerRes = null;

				String sellerId = null;
				try {
					sellerKeyList = new ArrayList<String>();
					sellerReq = new DetailInformationListForProductSacReq();

					// 회원 판매자 정보를 위한 판매자키 파라미터 세팅
					sellerMbrNo = metaInfo.getSellerMbrNo();

					if (StringUtils.isNotEmpty(sellerMbrNo)) {
						sellerKeyList.add(sellerMbrNo);
					}

					sellerReq.setSellerKeyList(sellerKeyList);

					this.log.info("##### [SAC DSP LocalSCI] SAC Member Start : sellerSearchSCI.detailInformationListForProduct");
					long start = System.currentTimeMillis();

					// 회원 판매자 정보 조회
					sellerRes = this.sellerSearchSCI.detailInformationListForProduct(sellerReq);

					this.log.info("##### [SAC DSP LocalSCI] SAC Member End : sellerSearchSCI.detailInformationListForProduct");
					long end = System.currentTimeMillis();
					this.log.info("##### [SAC DSP LocalSCI] SAC Member sellerSearchSCI.detailInformationListForProduct takes {} ms",
							(end - start));

					if (StringUtils.isNotEmpty(sellerMbrNo)) {
						SellerMbrInfoSac SellerMbrInfoSac = sellerRes.getSellerMbrMap().get(sellerMbrNo);

						if (SellerMbrInfoSac != null) {
							sellerId = SellerMbrInfoSac.getSellerId();
						}
					}
				} catch (Exception e) {
					this.log.error("판매자 정보 목록 조회 연동 중 오류가 발생하였습니다.\n", e);
				}

				// Distributor 상세정보(판매자 정보)
				Distributor distributor = new Distributor();
				distributor.setIdentifier(sellerId);
				distributor.setName(metaInfo.getExpoSellerNm());
				product.setDistributor(distributor);

				productList.add(product);
				commonResponse.setTotalCount(1);
			}
		} else {
			throw new StorePlatformException("SAC_DSP_0009");
		}
		response.setCommonResponse(commonResponse);
		response.setProductList(productList);
		return response;
	}
}
