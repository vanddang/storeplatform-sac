/**
 * 
 */
package com.skplanet.storeplatform.sac.display.related.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.display.vo.related.AlbumProductSacReq;
import com.skplanet.storeplatform.sac.client.display.vo.related.AlbumProductSacRes;
import com.skplanet.storeplatform.sac.client.display.vo.related.ArtistProductSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;
import com.skplanet.storeplatform.sac.display.common.service.MemberBenefitService;
import com.skplanet.storeplatform.sac.display.meta.service.MetaInfoService;
import com.skplanet.storeplatform.sac.display.meta.vo.MetaInfo;
import com.skplanet.storeplatform.sac.display.meta.vo.ProductBasicInfo;
import com.skplanet.storeplatform.sac.display.response.CommonMetaInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.MusicInfoGenerator;
import com.skplanet.storeplatform.sac.display.response.ResponseInfoGenerateFacade;

/**
 * 앨범별 음악 리스트
 * 
 * Updated on : 2014. 10. 23.
 * Updated by : 1002177
 */
@Service
public class AlbumProductServiceImpl implements AlbumProductService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	@Autowired
	private MetaInfoService metaInfoService;

	@Autowired
	private MusicInfoGenerator musicGenerator;

	@Autowired
	private ResponseInfoGenerateFacade responseInfoGenerateFacade;

	@Autowired
	private CommonMetaInfoGenerator commonGenerator;

	@Autowired
    private MemberBenefitService memberBenefitService;
	
	/**
	 *
	 * <pre>
	 * [??] 2.5.?. 특정 앨범별 상품(곡) 조회
	 * </pre>
	 *
	 * @param requestVO
	 *            AlbumProductSacReq
	 * @param requestHeader
	 *            AlbumProductSacReq
	 * @return AlbumProductSacRes
	 */
	@Override
	public AlbumProductSacRes searchAlbumProductList(
			String tenantId, String langCd, String deviceModelCd, String prodId) {
		
		AlbumProductSacRes albumProductSacRes = new AlbumProductSacRes();
		
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("tenantId", tenantId);
		reqMap.put("langCd", langCd);
		reqMap.put("deviceModelCd", deviceModelCd);
		reqMap.put("mmDeviceModelCd", DisplayConstants.DP_ANY_PHONE_4MM);
		reqMap.put("prodId", prodId);
		
		this.log.debug("특정 앨범별 상품(곡) 조회");
		
		List<ProductBasicInfo> albumProductList = this.commonDAO.queryForList(
				"AlbumProduct.selectAlbumProductList", reqMap, ProductBasicInfo.class);
		
		List<Product> productList = new ArrayList<Product>();
		MetaInfo retMetaInfo = null;
		Product product = null;
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tenantHeader", reqMap);
		param.put("imageCd", DisplayConstants.DP_MUSIC_REPRESENT_IMAGE_CD);
		
		for (ProductBasicInfo productBasicInfo : albumProductList) {
			param.put("productBasicInfo", productBasicInfo);	
			retMetaInfo = this.commonDAO.queryForObject("RelatedProduct.selectMusicMetaInfo", param,
					MetaInfo.class);
			if (retMetaInfo != null) {
				retMetaInfo.setMileageInfo(memberBenefitService.getMileageInfo(tenantId, retMetaInfo.getTopMenuId(), retMetaInfo.getProdId(), retMetaInfo.getProdAmt()));
				product = this.responseInfoGenerateFacade.generateMusicProduct(retMetaInfo);
				product.setAccrual(this.commonGenerator.generateAccrual(retMetaInfo)); // 통계 건수 재정의
				product.setProductExplain(retMetaInfo.getProdBaseDesc()); // 상품 설명
				productList.add(product);
			}
		}
		albumProductSacRes.setProductList(productList);
		
		CommonResponse commonResponse = new CommonResponse();
		int totalCount = albumProductList.isEmpty() ? 0 : albumProductList.get(0).getTotalCount();
		commonResponse.setTotalCount(totalCount);
		albumProductSacRes.setCommonResponse(commonResponse);
		
		return albumProductSacRes;
	}

}
