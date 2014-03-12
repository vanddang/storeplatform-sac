/**
 * 
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

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.AppDetailByProductIdSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.AppDetailByProductIdSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Url;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Device;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.header.vo.TenantHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
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
	private DisplayCommonService commonService;
	@Autowired
	private CommonMetaInfoGenerator commonGenerator;
	@Autowired
	private AppInfoGenerator appInfoGenerator;

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

		appDetailByProductIdSacReq.setTenantId(tenantHeader.getTenantId());
		appDetailByProductIdSacReq.setLangCd(tenantHeader.getLangCd());
		appDetailByProductIdSacReq.setImageCd(DisplayConstants.DP_OPENAPI_APP_REPRESENT_IMAGE_CD);
		appDetailByProductIdSacReq.setWebPocUrl(DisplayConstants.DP_OPENAPI_APP_URL);
		appDetailByProductIdSacReq.setScUrl(DisplayConstants.DP_OPENAPI_SC_URL);

		AppDetailByProductIdSacRes response = new AppDetailByProductIdSacRes();
		CommonResponse commonResponse = new CommonResponse();
		List<Product> productList = new ArrayList<Product>();

		// 상품ID
		this.log.debug("####### ProductId : " + appDetailByProductIdSacReq.getProductId());

		Identifier identifier = new Identifier();
		Product product = null;

		List<MetaInfo> productMetaInfoList = null;
		productMetaInfoList = this.commonDAO.queryForList("OpenApi.searchProductByProductId",
				appDetailByProductIdSacReq, MetaInfo.class);

		if (productMetaInfoList.size() != 0) {

			// 지원 단말 리스트 조회
			Device device = null;
			List<Device> deviceList = new ArrayList<Device>();

			List<MetaInfo> resultList = this.commonDAO.queryForList("OpenApi.searchSupportDeviceListByProdId",
					appDetailByProductIdSacReq, MetaInfo.class);

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
				product.setApp(app);

				product.setDeviceList(deviceList); // 지원 단말 목록

				product.setTitle(this.commonGenerator.generateTitle(metaInfo)); // 상품명

				// PreviewUrl
				String[] previewUrls = metaInfo.getPreviewUrl().split(";");
				List<Source> preveiwSourceList = new ArrayList<Source>();
				if (previewUrls.length > 0) {
					Source source = new Source();
					for (int i = 0; i < previewUrls.length; i++) {
						source = this.commonGenerator.generatePreviewSourceList(metaInfo);
						source.setUrl(previewUrls[i]);
						preveiwSourceList.add(source);
					}
					product.setPreviewSourceList(preveiwSourceList);
				}

				product.setProductExplain(metaInfo.getProdBaseDesc()); // 상품 설명

				product.setProductDetailExplain(metaInfo.getProdDtlDesc()); // 상품 상세 설명

				product.setDistributor(this.commonGenerator.generateDistributor(metaInfo)); // 판매자 정보

				productList.add(product);
				commonResponse.setTotalCount(1);
			}
		}
		response.setCommonResponse(commonResponse);
		response.setProductList(productList);
		return response;
	}
}
