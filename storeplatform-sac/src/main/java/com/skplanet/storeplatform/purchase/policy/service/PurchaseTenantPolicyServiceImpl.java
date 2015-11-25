package com.skplanet.storeplatform.purchase.policy.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.purchase.client.common.vo.TenantSalePolicy;
import com.skplanet.storeplatform.purchase.constant.PurchaseCDConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseTenantPolicyServiceImpl implements PurchaseTenantPolicyService {

	@Autowired
	@Qualifier("scPurchase")
	private CommonDAO commonDao;

	/**
	 * 
	 * <pre>
	 * 해당 테넌트의 구매Part 정책 중 특정 정책만 조회한다.
	 * </pre>
	 * 
	 * @param tenantId
	 *            정책을 조회할 대상 테넌트 ID
	 * @param tenantProdGrpCd
	 *            정책 기준이 되는 테넌트 상품 그룹 코드
	 * @param procPatternCd
	 *            조회할 정책처리패턴
	 * @param ignoreTenantProdGrpCd
	 *            정책 대상이 카테고리 무시 여부: true-해당 처리패턴의 모든 정책 조회, false-해당 처리패턴 정책 중 기준 테넌트 상품 그룹 코드에 속하는 정책 조회
	 * @return 해당 테넌트의 구매Part 정책 목록
	 */
	@Override
	public List<TenantSalePolicy> searchTenantSalePolicyList(String tenantId, String tenantProdGrpCd,
			String procPatternCd, String extraUnitCd, boolean ignoreTenantProdGrpCd) {
		TenantSalePolicy qryParam = new TenantSalePolicy();
		qryParam.setTenantId(tenantId);
		qryParam.setTenantProdGrpCd(tenantProdGrpCd);
		qryParam.setProcPatternCd(procPatternCd);
		qryParam.setExtraUnitCd(extraUnitCd);
		qryParam.setIgnoreTenantProdGrpCd(ignoreTenantProdGrpCd);

		return this.commonDao.queryForList("PurchasePolicy.searchTenantSalePolicyList", qryParam,
				TenantSalePolicy.class);
	}

	/**
	 * 
	 * <pre>
	 * 해당 테넌트의 결제 정책 조회.
	 * 카테고리 > 부모상품 > 상품 순서 결제 수단 상속.
	 * </pre>
	 * 
	 * @param tenantId
	 *            정책을 조회할 대상 테넌트 ID
	 * @param tenantProdGrpCd
	 *            정책 기준이 되는 테넌트 상품 그룹 코드
	 * @param prodKindCd
	 *            상품종류코드
	 * @param prodId
	 *            상품별 정책 조회할 상품ID
	 * @param parentProdId
	 *            정책 조회할 모상품ID (인앱 경우 AID)
	 * @return 해당 테넌트의 구매Part 정책 목록
	 */
	@Override
	public TenantSalePolicy searchPaymentPolicy(String tenantId, String tenantProdGrpCd, String prodKindCd,
			String prodId, String parentProdId) {
		TenantSalePolicy resultSalePolicy = null;
		// 쇼핑/정액권 경우, 상품타입까지.
		String baseSalePolicy = tenantProdGrpCd+ StringUtils.defaultString(prodKindCd);
		List<TenantSalePolicy> tenantSalePolicyList = searchTenantSalePolicyList(tenantId, baseSalePolicy, PurchaseCDConstants.POLICY_PATTERN_ADJUST_PAYMETHOD, null, false);
		resultSalePolicy = getTargetSalePolicy(baseSalePolicy, tenantSalePolicyList);

		if (StringUtils.isNotEmpty(parentProdId)) {
			resultSalePolicy = unionPaymentPolicy(resultSalePolicy,
					searchPaymentPolicy(tenantId, tenantProdGrpCd, parentProdId));
		}
		if (StringUtils.isNotEmpty(prodId)) {
			resultSalePolicy = unionPaymentPolicy(resultSalePolicy,
					searchPaymentPolicy(tenantId, tenantProdGrpCd, prodId));
		}
		return resultSalePolicy;
	}

	private TenantSalePolicy getTargetSalePolicy(String tenantGrpCd, List<TenantSalePolicy> tenantSalePolicyList) {
		for (TenantSalePolicy sampleSalePolicy : tenantSalePolicyList) {
			if (StringUtils.equals(tenantGrpCd, sampleSalePolicy.getTenantProdGrpCd())) {
				return sampleSalePolicy;
			}
		}
		if (CollectionUtils.isNotEmpty(tenantSalePolicyList)) {
			return tenantSalePolicyList.get(0);
		} else
			return new TenantSalePolicy();
	}

	private List<TenantSalePolicy> searchPaymentPolicy(String tenantId, String tenantProdGrpCd, String prodId) {
		TenantSalePolicy qryParam = new TenantSalePolicy();
		qryParam.setTenantId(tenantId);
		qryParam.setTenantProdGrpCd(tenantProdGrpCd);
		qryParam.setProcPatternCd(PurchaseCDConstants.POLICY_PATTERN_ADJUST_PAYMETHOD);
		qryParam.setProdId(prodId);

		List<TenantSalePolicy> tenantSalePolicyList = this.commonDao.queryForList(
				"PurchasePolicy.searchPaymentPolicyByProdId", qryParam, TenantSalePolicy.class);
		return tenantSalePolicyList;
	}

	private TenantSalePolicy unionPaymentPolicy(TenantSalePolicy tenantSalePolicy,
			List<TenantSalePolicy> prodSalePolicyList) {
		if (CollectionUtils.isEmpty(prodSalePolicyList)) {
			return tenantSalePolicy;
		} else {
			String applyValue = StringUtils.defaultString(tenantSalePolicy.getApplyValue(), "");
			for (TenantSalePolicy prodSalePolicy : prodSalePolicyList) {
				applyValue = extendsValue(applyValue, prodSalePolicy.getApplyValue());
			}
			tenantSalePolicy.setApplyValue(applyValue);
			return tenantSalePolicy;
		}
	}

	private String extendsValue(String parent, String child) {
		Map<String, String> resultMap;

		// classify parent sample data
		resultMap = classifyValuesToMap(parent);
		resultMap.putAll(classifyValuesToMap(child));

		StringBuffer sb = new StringBuffer();

		for (Map.Entry<String, String> entry : resultMap.entrySet())
			sb.append(entry.getKey()).append(PurchaseCDConstants.DELIMITER).append(entry.getValue())
					.append(PurchaseCDConstants.SEPARATOR);
		if (sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1);
		return StringUtils.defaultString(sb.toString());
	}

	private Map<String, String> classifyValuesToMap(String str) {
		Map<String, String> resultMap = new LinkedHashMap<String, String>();

		for (String sample : StringUtils.splitPreserveAllTokens(StringUtils.defaultString(str),
				PurchaseCDConstants.SEPARATOR))
			resultMap.put(StringUtils.substringBefore(sample, PurchaseCDConstants.DELIMITER),
					StringUtils.substringAfter(sample, PurchaseCDConstants.DELIMITER));
		return resultMap;
	}
}
