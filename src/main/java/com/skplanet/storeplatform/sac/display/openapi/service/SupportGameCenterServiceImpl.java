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
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SupportGameCenterSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.openapi.SupportGameCenterSacRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.sci.HistoryInternalSCI;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInReq;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.HistoryListSacInRes;
import com.skplanet.storeplatform.sac.client.internal.purchase.history.vo.ProductListSacIn;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Identifier;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.App;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Purchase;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Support;
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
public class SupportGameCenterServiceImpl implements SupportGameCenterService {

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
	HistoryInternalSCI historyInternalSCI;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.openapi.service.AppDetailByPkgNmService(com.skplanet
	 * com.skplanet.storeplatform.sac.client.display.vo.openapi.appDetailByPackageNameSacReq)
	 */
	@Override
	public SupportGameCenterSacRes searchSupportGameCenterByAid(SupportGameCenterSacReq supportGameCenterSacReq,
			SacRequestHeader requestheader) {
		TenantHeader tenantHeader = requestheader.getTenantHeader();

		MetaInfo downloadSystemDate = this.commonDAO.queryForObject("Download.selectDownloadSystemDate", "",
				MetaInfo.class);
		String sysDate = downloadSystemDate.getSysDate();

		supportGameCenterSacReq.setTenantId(tenantHeader.getTenantId());
		supportGameCenterSacReq.setLangCd(tenantHeader.getLangCd());

		SupportGameCenterSacRes response = new SupportGameCenterSacRes();
		CommonResponse commonResponse = new CommonResponse();
		List<Product> productList = new ArrayList<Product>();

		int totalCnt = 0;
		HistoryListSacInReq historyReq = null;
		HistoryListSacInRes historyRes = null;
		boolean purchaseFlag = true;
		/*
		 * AID LIST
		 */
		String[] arrayAid = supportGameCenterSacReq.getAidList().split("\\+");
		supportGameCenterSacReq.setArrayAId(arrayAid);

		String userKey = supportGameCenterSacReq.getUserKey();
		String deviceKey = supportGameCenterSacReq.getDeviceKey();

		this.log.debug("####### deviceModelNo : " + supportGameCenterSacReq.getDeviceModelNo());
		this.log.debug("####### userKey : " + supportGameCenterSacReq.getUserKey());
		this.log.debug("####### deviceKey : " + supportGameCenterSacReq.getDeviceKey());

		// 상품ID

		/*
		 * AID로 상품ID 가져오기
		 */
		List<MetaInfo> prodIdList = null;
		prodIdList = this.commonDAO.queryForList("OpenApi.getProductIdByAid", supportGameCenterSacReq, MetaInfo.class);

		if (prodIdList.size() != 0) {
			/*****************************/
			// 구매내역 조회를 위한 생성자
			ProductListSacIn productListSacIn = null;
			List<ProductListSacIn> ProductListSacInList = null;
			try {
				productListSacIn = new ProductListSacIn();
				ProductListSacInList = new ArrayList<ProductListSacIn>();

				int k = 0;
				String[] arrayProductId = new String[prodIdList.size()];
				; // 상품ID LIST
				Iterator<MetaInfo> iterator = prodIdList.iterator();
				while (iterator.hasNext()) {
					MetaInfo metaInfo = iterator.next();
					this.log.debug("####### prodId : " + metaInfo.getProdId());
					// 조회할 상품ID SET
					arrayProductId[k] = metaInfo.getProdId();
					productListSacIn.setProdId(metaInfo.getProdId());
					ProductListSacInList.add(productListSacIn);
					k++;
				}
				supportGameCenterSacReq.setArrayProductId(arrayProductId);

				historyReq = new HistoryListSacInReq();
				historyReq.setTenantId(supportGameCenterSacReq.getTenantId());
				historyReq.setUserKey(userKey);
				historyReq.setDeviceKey(deviceKey);
				historyReq.setPrchsProdHaveYn(DisplayConstants.PRCHS_PROD_HAVE_YES);
				historyReq.setPrchsProdType(DisplayConstants.PRCHS_PROD_TYPE_UNIT);
				historyReq.setStartDt(DisplayConstants.PRCHS_START_DATE);
				historyReq.setEndDt(sysDate);
				historyReq.setOffset(1);
				historyReq.setCount(prodIdList.size());
				historyReq.setProductList(ProductListSacInList);
				historyReq.setPrchsStatusCd("OR000301"); // 구매완료

				// 구매내역 조회 실행
				historyRes = this.historyInternalSCI.searchHistoryList(historyReq);

			} catch (Exception ex) {
				purchaseFlag = false;
				this.log.error("구매내역 조회 연동 중 오류가 발생하였습니다. \n{}", ex);
				// throw new StorePlatformException("SAC_DSP_2001", ex);
			}
			/*****************************/
		} else {
			// 요청한 AID 상품이 존재하지 않는다.
			throw new StorePlatformException("SAC_DSP_0005", supportGameCenterSacReq.getAidList());
		}

		Identifier identifier = new Identifier();
		Product product = null;
		Purchase purchase = null;

		List<MetaInfo> productMetaInfoList = null;

		productMetaInfoList = this.commonDAO.queryForList("OpenApi.searchSupportGameCenter", supportGameCenterSacReq,
				MetaInfo.class);
		totalCnt = productMetaInfoList.size();
		if (totalCnt != 0) {

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

				product.setPrice(this.commonGenerator.generatePrice(metaInfo)); // 상품가격

				product.setAccrual(this.commonGenerator.generateAccrual(metaInfo)); // 평점정보

				product.setRights(this.commonGenerator.generateRights(metaInfo)); // 이용권한

				if (purchaseFlag && historyRes != null) {
					String prchsId = null; // 구매ID
					String prchsDt = null; // 구매일시
					String useExprDt = null; // 이용 만료일시
					String dwldExprDt = null; // 다운로드 만료일시
					String prchsCaseCd = null; // 선물 여부
					String prchsState = null; // 구매상태
					String prchsProdId = null; // 구매 상품ID
					String puchsPrice = null; // 구매 상품금액
					String drmYn = null; // 구매상품 Drm여부

					this.log.debug("############# historyRes.getTotalCnt() : " + historyRes.getTotalCnt());
					if (historyRes.getTotalCnt() > 0) {
						// List<Purchase> purchaseList = new ArrayList<Purchase>();

						for (int i = 0; i < historyRes.getTotalCnt(); i++) {
							prchsId = historyRes.getHistoryList().get(i).getPrchsId();
							prchsDt = historyRes.getHistoryList().get(i).getPrchsDt();
							useExprDt = historyRes.getHistoryList().get(i).getUseExprDt();
							dwldExprDt = historyRes.getHistoryList().get(i).getDwldExprDt();
							prchsCaseCd = historyRes.getHistoryList().get(i).getPrchsCaseCd();
							prchsProdId = historyRes.getHistoryList().get(i).getProdId();
							puchsPrice = historyRes.getHistoryList().get(i).getProdAmt();
							drmYn = historyRes.getHistoryList().get(i).getDrmYn();

							this.log.debug("############# prchsId : " + prchsId + " ### prchsProdId : " + prchsProdId
									+ " #### prchsCaseCd : " + prchsCaseCd + " #### dwldExprDt : " + dwldExprDt);

							// 다운로드 만료일자가 오늘보다 큰날짜의 상품ID만 가져온다.
							if (Long.parseLong(sysDate) <= Long.parseLong(dwldExprDt)) {
								purchase = new Purchase();
								// 조회한 상품이 구매한 상품인 경우
								if (prchsProdId.equals(metaInfo.getProdId())) {

									// 구매 및 선물 여부 확인
									if (DisplayConstants.PRCHS_CASE_PURCHASE_CD.equals(prchsCaseCd)) {
										prchsState = "payment";
									} else if (DisplayConstants.PRCHS_CASE_GIFT_CD.equals(prchsCaseCd)) {
										prchsState = "gift";
									}

									purchase.setState(prchsState);
									product.setPurchase(purchase);
								} else {
									purchase.setState("none");
									product.setPurchase(purchase);
								}
							}
						}
					}
				}

				Support support = new Support();
				support.setType("device");
				support.setText(metaInfo.getDeviceSupport());
				product.setSupport(support);

				// App 상세정보
				App app = new App();
				app.setAid(metaInfo.getAid());
				app.setPackageName(metaInfo.getApkPkgNm());
				product.setApp(app);
				product.setTitle(this.commonGenerator.generateTitle(metaInfo)); // 상품명

				productList.add(product);
				commonResponse.setTotalCount(totalCnt);
			}
		}
		response.setCommonResponse(commonResponse);
		response.setProductList(productList);
		return response;
	}
}
