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

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.AppDetailByPackageNameSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.AppDetailByPackageNameSacRes;
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
 * 상품 상세 정보 요청(Package Name) Service 구현체
 * 
 * Updated on : 2014. 3. 6. Updated by : 백승현, 인크로스.
 */
@Service
public class AppDetailByPkgNmServiceImpl implements AppDetailByPkgNmService {

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
	 * @see com.skplanet.storeplatform.sac.display.openapi.service.AppDetailByPkgNmService(com.skplanet
	 * com.skplanet.storeplatform.sac.client.display.vo.openapi.appDetailByPackageNameSacReq)
	 */
	@Override
	public AppDetailByPackageNameSacRes searchProductByPackageName(
			AppDetailByPackageNameSacReq appDetailByPackageNameSacReq, SacRequestHeader requestheader) {
		TenantHeader tenantHeader = requestheader.getTenantHeader();

		appDetailByPackageNameSacReq.setTenantId(tenantHeader.getTenantId());
		appDetailByPackageNameSacReq.setLangCd(tenantHeader.getLangCd());
		appDetailByPackageNameSacReq.setImageCd(DisplayConstants.DP_OPENAPI_APP_REPRESENT_IMAGE_CD);
		appDetailByPackageNameSacReq.setWebPocUrl(DisplayConstants.DP_OPENAPI_APP_URL);
		appDetailByPackageNameSacReq.setScUrl(DisplayConstants.DP_OPENAPI_SC_URL);

		AppDetailByPackageNameSacRes response = new AppDetailByPackageNameSacRes();
		CommonResponse commonResponse = new CommonResponse();
		List<Product> productList = new ArrayList<Product>();

		// 패키지명
		String packageName = appDetailByPackageNameSacReq.getPackageName();
		appDetailByPackageNameSacReq.setPackageName(packageName);

		this.log.debug("####### packageName : " + packageName);

		// 상품ID
		String prodId = "";
		/*
		 * 패키지명을 이용하여 상품ID 가져오기
		 */
		List<MetaInfo> prodIdList = null;
		prodIdList = this.commonDAO.queryForList("OpenApi.searchProductIdByPackageName", appDetailByPackageNameSacReq,
				MetaInfo.class);

		if (prodIdList.size() != 0) {
			Iterator<MetaInfo> iterator = prodIdList.iterator();
			while (iterator.hasNext()) {
				MetaInfo metaInfo = iterator.next();

				prodId = metaInfo.getProdId();
				appDetailByPackageNameSacReq.setProdId(prodId);
				this.log.debug("####### prodId : " + prodId);
			}
		} else {
			// 패키지 정보에 대한 상품이 존재하지 않는다.
			throw new StorePlatformException("SAC_DSP_0005", packageName);
		}

		Identifier identifier = new Identifier();
		Product product = null;

		List<MetaInfo> productMetaInfoList = null;

		productMetaInfoList = this.commonDAO.queryForList("OpenApi.searchProductByProductId",
				appDetailByPackageNameSacReq, MetaInfo.class);

		if (productMetaInfoList.size() != 0) {

			// 지원 단말 리스트 조회
			List<Device> deviceList = null;
			List<Device> supportDeviceList = null;

			supportDeviceList = this.commonDAO.queryForList("OpenApi.searchSupportDeviceList",
					appDetailByPackageNameSacReq, Device.class);

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

				product.setApp(this.appInfoGenerator.generateApp(metaInfo)); // App 상세정보

				App app = new App();
				app.setAid(metaInfo.getAid());
				app.setSupportedOs(metaInfo.getOsVersion());
				app.setPackageName(metaInfo.getApkPkgNm());
				app.setSupportDeviceList(supportDeviceList);
				product.setApp(app);

				// product.setRights(this.commonGenerator.generateRights(metaInfo)); // 이용권한

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
