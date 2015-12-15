package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.MapgProdMeta;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ProductInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ProductInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.ProductInfoSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.sci.SellerSearchSCI;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationListForProductSacReq;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationListForProductSacRes;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.DetailInformationListForProductSacRes.SellerMbrInfoSac;
import com.skplanet.storeplatform.sac.client.internal.member.seller.vo.SellerMbrSac;
import com.skplanet.storeplatform.sac.display.cache.service.CachedExtraInfoManager;
import com.skplanet.storeplatform.sac.display.cache.vo.GetProductBaseInfoParam;
import com.skplanet.storeplatform.sac.display.cache.vo.ProductBaseInfo;
import com.skplanet.storeplatform.sac.display.common.ProductType;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.common.vo.SupportDevice;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 구매 내역 조회 시 필요한 상품 메타 정보 조회 서비스 구현체.
 * 
 * Updated on : 2014. 2. 24. Updated by : 오승민, 인크로스
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

	public static final String SEED_REF_LAUNCHER_CD = "PD013018";
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private DisplayCommonService displayCommonService;

	@Autowired
	private SellerSearchSCI sellerSearchSCI;

	@Value("#{propertiesForSac['plus19.list.img.url']}")
	private String plus19ImgUrl;

    @Autowired
    private CachedExtraInfoManager cachedExtraInfoManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.display.localsci.sci.service.ProductInfoService#getProductList(com.skplanet.
	 * storeplatform.sac.client.internal.display.localsci.vo.ProductInfoSacReq)
	 */
	@Override
	public ProductInfoSacRes getProductList(ProductInfoSacReq req) {

		List<String> prodIdList = req.getList();

        if(CollectionUtils.isEmpty(prodIdList))
            return null;

        SupportDevice supportDevice = this.displayCommonService.getSupportDeviceInfo(req.getDeviceModelNo());

        final Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("lang", req.getLang());
        paramMap.put("deviceModelNo", req.getDeviceModelNo());
        paramMap.put("tenantId", req.getTenantId());
        paramMap.put("rshpCd", DisplayConstants.DP_CHANNEL_EPISHODE_RELATIONSHIP_CD);
        paramMap.put("supportDevice", supportDevice);
        paramMap.put("dpAnyPhone4mm", DisplayConstants.DP_ANY_PHONE_4MM);
        paramMap.put("inAppRshpCd", DisplayConstants.DP_PARENT_CHILD_RELATIONSHIP_CD);
        paramMap.put("seedLauncherCd", SEED_REF_LAUNCHER_CD);
        paramMap.put("ebookImageCd", DisplayConstants.DP_FREEPASS_EBOOK_THUMBNAIL_IMAGE_CD);

        List<ProductInfo> productList = Lists.newArrayList();

        for(String epsdId : prodIdList) {
            ProductBaseInfo baseInfo = cachedExtraInfoManager.getProductBaseInfo(new GetProductBaseInfoParam(epsdId));
            if(baseInfo == null)
                continue;

            String topMenuId = baseInfo.getTopMenuId();
            String svcGrpCd = baseInfo.getSvcGrpCd();
            String metaClsfCd = baseInfo.getMetaClsfCd();
            ProductType typeInfo = baseInfo.getProductType();

            paramMap.put("epsdId", epsdId);
            paramMap.put("metaClsfCd", metaClsfCd);
            paramMap.put("topMenuId", topMenuId);
            paramMap.put("imageCd", typeInfo.getImageCd());

            log.debug("##### Top Menu Id : {}", topMenuId);
            log.debug("##### Service Group Cd : {}", svcGrpCd);
            log.debug(":: ProductType={}", typeInfo.getName());
            log.debug(":: MetaClsfCd={}", metaClsfCd);

            ProductInfo product = null;
            switch(typeInfo) {
                case App:
                case InApp:
                    product = commonDAO.queryForObject("ProductInfo.getAppMetaInfo", paramMap, ProductInfo.class);
                    break;
                case VodMovie:
                case VodTv:
                    product = commonDAO.queryForObject("ProductInfo.getVODMetaInfo", paramMap, ProductInfo.class);
                    break;
                case Ebook:
                    product = commonDAO.queryForObject("ProductInfo.getEbookComicMetaInfo", paramMap, ProductInfo.class);
                    break;
                case Comic:
                    product = commonDAO.queryForObject("ProductInfo.getEbookComicMetaInfo", paramMap, ProductInfo.class);
                    break;
                case Music:
                    product = commonDAO.queryForObject("ProductInfo.getMusicMetaInfo", paramMap, ProductInfo.class);
                    break;
                case RingBell:
                    product = commonDAO.queryForObject("ProductInfo.getRingBellMetaInfo", paramMap, ProductInfo.class);
                    break;
                case Shopping:
                    product = commonDAO.queryForObject("ProductInfo.getShoppingMetaInfo", paramMap, ProductInfo.class);
                    break;
                case Voucher:
                    product = commonDAO.queryForObject("ProductInfo.getFreePassMetaInfo", paramMap, ProductInfo.class);
                    break;
            }

            if(product == null)
                continue;

            // 상품별 후처리
            switch(typeInfo) {
                case VodMovie:
                case VodTv:
                    product.setChapterUnit(displayCommonService.getVodChapterUnit());
                    check19Plus(product, DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD);
                    setChromeCast(product);
                    break;
                case Ebook:
                case Comic:
                    product.setChapterUnit(displayCommonService.getEpubChapterUnit(product.getBookClsfCd()));
                    check19Plus(product, DisplayConstants.DP_EBOOK_COMIC_REPRESENT_IMAGE_CD);
                    break;
                case Voucher:
                    // 시리즈 패스일 경우만 채널 상품 목록 추가
                    if (product.getKind().equals("seriesFreepass")) {
                        List<MapgProdMeta> mapgProdIdList = commonDAO.queryForList("ProductInfo.getMapgProdIdList", paramMap, MapgProdMeta.class);
                        if (CollectionUtils.isNotEmpty(mapgProdIdList))
                            product.setMapgProdIdList(mapgProdIdList);
                    }
                    break;
            }

            // 상품 공통 처리
            product.setContentsTypeCd(baseInfo.getContentsTypeCd());
            product.setSeriesYn(baseInfo.isSeries() ? "Y" : "N");

            productList.add(product);
        }

        mapSellerInfo(productList);

        ProductInfoSacRes res = new ProductInfoSacRes();
		res.setProductList(productList);
		return res;
	}

    /**
     * 판매자 정보를 매핑
     * @param productList
     */
    private void mapSellerInfo(List<ProductInfo> productList) {

        // 회원 판매자 정보를 위한 판매자키 파라미터 세팅
        List<SellerMbrSac> sellerKeyList = new ArrayList<SellerMbrSac>();
        for (ProductInfo product : productList) {
            String sellerMbrNo = product.getSellerMbrNo();

            if (StringUtils.isNotEmpty(sellerMbrNo)) {
                SellerMbrSac sellerMbrSac = new SellerMbrSac();
                sellerMbrSac.setSellerKey(sellerMbrNo);

                sellerKeyList.add(sellerMbrSac);
            }
        }

        // 2014.06.12 판매자 정보 목록 조회 추가 (이태희D)
        DetailInformationListForProductSacReq sellerReq = new DetailInformationListForProductSacReq();
        sellerReq.setSellerMbrSacList(sellerKeyList);

        Map<String, SellerMbrInfoSac> sellerMbrMap = null;

        try {
            // 회원 판매자 정보 조회
            DetailInformationListForProductSacRes sellerRes = sellerSearchSCI.detailInformationListForProduct(sellerReq);
            sellerMbrMap = sellerRes.getSellerMbrMap();
        } catch (StorePlatformException e) {
            this.log.error("판매자 정보 목록 조회 연동 중 오류가 발생하였습니다.\n", e);
        }

        if(sellerMbrMap == null)
            return;

        for (ProductInfo product : productList) {
            String sellerMbrNo = product.getSellerMbrNo();

            if (StringUtils.isEmpty(sellerMbrNo))
                continue;

            SellerMbrInfoSac sellerMbrInfoSac = sellerMbrMap.get(sellerMbrNo);
            if(sellerMbrInfoSac == null)
                continue;

            product.setSellerMbrInfoSac(sellerMbrInfoSac);
        }
    }


    /**
	 * <pre>
	 * 19Plus 상품인지 체크, 19Plus 상품이면 19+ 이미지로 변경
	 * </pre>
	 * 
	 * @param product
	 * @param imageCd
	 */
	private void check19Plus(ProductInfo product, String imageCd) {

		// 19+ 상품이면
		if ("Y".equals(product.getPlus19Yn())) {
			// filePath
			product.setFilePath(this.plus19ImgUrl);

			// vod epsdImgPath
			if (DisplayConstants.DP_VOD_REPRESENT_IMAGE_CD.equals(imageCd))
				product.setEpsdImgPath(this.plus19ImgUrl);
		}
	}

    /**
     * 크롬캐스트 파라미터 체크후 다시 세팅.
     * @param product
     */
    private void setChromeCast(ProductInfo product) {

        // Chrome Cast 재생 허용 Player
        if(StringUtils.isNotEmpty(product.getAvailablePlayer())){
            String availablePlayerStr = "";
            StringTokenizer st = new StringTokenizer(product.getAvailablePlayer(), "\\|");
            while (st.hasMoreTokens()) {
                String token = st.nextToken();
                availablePlayerStr = availablePlayerStr + token + "|";
            }
            int lastGubunInt = availablePlayerStr.lastIndexOf("|");
            availablePlayerStr = availablePlayerStr.substring(0, (lastGubunInt));
            product.setAvailablePlayer(availablePlayerStr);
        }

    }
}
