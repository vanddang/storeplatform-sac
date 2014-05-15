package com.skplanet.storeplatform.sac.display.localsci.sci.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PaymentInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PossLendProductInfo;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PossLendProductInfoSacReq;
import com.skplanet.storeplatform.sac.client.internal.display.localsci.vo.PossLendProductInfoSacRes;
import com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants;

/**
 * 
 * 
 * 소장/대여 상품 정보 조회 서비스 구현체.
 * 
 * Updated on : 2014. 4. 15. Updated by : 홍지호, 엔텔스
 */
@Service
public class PossLendProductInfoServiceImpl implements PossLendProductInfoService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("sac")
	private CommonDAO commonDAO;

	/**
	 * <pre>
	 * 소장/대여 상품 정보 조회.
	 * </pre>
	 * 
	 * @param req
	 *            파라미터
	 * @return PossLendProductInfoSacRes 소장/대여 상품 정보 리스트
	 */
	@Override
	public PossLendProductInfoSacRes searchPossLendProductInfo(PossLendProductInfoSacReq req) {
		PossLendProductInfoSacRes res = new PossLendProductInfoSacRes();
		List<PossLendProductInfo> possLendProductInfoList = new ArrayList<PossLendProductInfo>();
		List<String> prodIdList = req.getProdIdList();
		List<String> possLendClsfCdList = req.getPossLendClsfCdList();
		String tenantId = req.getTenantId();
		String langCd = req.getLangCd();

		// 파라미터 존재 여부 체크
		if (prodIdList == null || prodIdList.size() == 0) {
			throw new StorePlatformException("SAC_DSP_0002", "prodIdList",
					prodIdList == null ? "null" : prodIdList.toString());
		}
		if (possLendClsfCdList == null || possLendClsfCdList.size() == 0) {
			throw new StorePlatformException("SAC_DSP_0002", "possLendClsfCdList",
					possLendClsfCdList == null ? "null" : possLendClsfCdList.toString());
		}
		if (prodIdList.size() != possLendClsfCdList.size()) {
			throw new StorePlatformException("SAC_DSP_0003", "possLendClsfCdList's size()",
					possLendClsfCdList.toString());
		}
		if (StringUtils.isEmpty(tenantId)) {
			throw new StorePlatformException("SAC_DSP_0002", "tenantId", tenantId);
		}
		if (StringUtils.isEmpty(langCd)) {
			throw new StorePlatformException("SAC_DSP_0002", "langCd", langCd);
		}

		// 파라미터 유효 값 체크
		if (prodIdList.size() > DisplayConstants.DP_CATEGORY_SPECIFIC_PRODUCT_PARAMETER_LIMIT) {
			throw new StorePlatformException("SAC_DSP_0004", "prodIdList",
					DisplayConstants.DP_CATEGORY_SPECIFIC_PRODUCT_PARAMETER_LIMIT);
		}

		// 상품 군 조회
		PaymentInfo paymentProdType = this.commonDAO.queryForObject("PaymentInfo.searchProdType", prodIdList.get(0),
				PaymentInfo.class);

		if (paymentProdType == null) {
			throw new StorePlatformException("SAC_DSP_0005", "[상품 군 조회]" + prodIdList.get(0));
		} else {
			this.log.debug("##### searchProdType result : {}, {}, {}", paymentProdType.getTopMenuId(),
					paymentProdType.getSvcGrpCd(), paymentProdType.getInAppYn());

			for (int i = 0; i < prodIdList.size(); i++) {
				req.setTopMenuId(paymentProdType.getTopMenuId()); // 상품군 Setting
				req.setProdId(prodIdList.get(i)); // prodIdList 에 있는 상품ID 1개씩 setting
				req.setPossLendClsfCd(possLendClsfCdList.get(i)); // possLendClsfCdList 에 있는 소장/대여 구분 1개씩 setting
				PossLendProductInfo possLendProductInfo = this.commonDAO.queryForObject(
						"PossLendProductInfo.searchPossLendProductInfo", req, PossLendProductInfo.class);

				if (possLendProductInfo == null) {
					possLendProductInfoList.add(new PossLendProductInfo()); // 소장/대여 상품이 없을 경우 빈 객체로 전송
					this.log.debug("possLendProductInfo is null [{}]", prodIdList.get(i));
				} else {
					possLendProductInfoList.add(possLendProductInfo);
				}
			}
		}

		res.setPossLendProductInfoList(possLendProductInfoList);
		return res;
	}
}
