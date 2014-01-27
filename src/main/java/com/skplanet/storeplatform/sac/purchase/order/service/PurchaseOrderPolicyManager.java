package com.skplanet.storeplatform.sac.purchase.order.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skplanet.storeplatform.sac.purchase.order.vo.PurchaseOrderPolicy;

public class PurchaseOrderPolicyManager {

	public static final String POLICY_GROUP_ID_FULL = "full"; // 유료 & SKT
	public static final String POLICY_GROUP_ID_FREE = "free"; // 무료
	public static final String POLICY_GROUP_ID_NOTSKT = "notskt"; // 유료 & 타통신사

	public static final int POLICY_ID_BLOCK = 1; // 구매제한
	public static final int POLICY_ID_TESTMDN = 2; // Test MDN
	public static final int POLICY_ID_SKTTESTMDN = 3; // SKT 시험폰
	public static final int POLICY_ID_SKTCORPMDN = 4; // SKT 법인폰
	public static final int POLICY_ID_SKPCORPMDN = 5; // SKP 법인폰
	public static final int POLICY_ID_SKT_LIMIT = 6; // SKT 후불 결제 한도
	public static final int POLICY_ID_SKT_SHOPPINGLIMIT = 7; // 쇼핑상품 SKT 후불 결제 한도
	public static final int POLICY_ID_SKT_SENDLIMIT = 8; // 선물 발신 SKT 후불 결제 한도
	public static final int POLICY_ID_SKT_RECVLIMIT = 9; // 선물 수신 한도

	public static final int POLICY_PATTERN_BLACKLIST = 1; // [패턴] Black List
	public static final int POLICY_PATTERN_WHITELIST = 2; // [패턴] White List
	public static final int POLICY_PATTERN_SKTLIMIT = 3; // [패턴] SKT 결제 한도

	public static final int POLICY_CHECKOBJECT_PAYDEVICE = 1; // [체크대상] 결제 디바이스

	private final Map<String, List<PurchaseOrderPolicy>> policyMap = new HashMap<String, List<PurchaseOrderPolicy>>();

	/**
	 * 정책 load & 정책 관리
	 */
	public PurchaseOrderPolicyManager() {
		// 제한정책 read: 우선순위 정렬
		List<PurchaseOrderPolicy> policyList = this.readPolicy();

		// 정책 리스트&맵 구성
		this.managePolicy(policyList);
	}

	public List<PurchaseOrderPolicy> getPolicyList(boolean bCharge, boolean bSktTelecom) {
		String policyGroupId = null;

		if (bCharge == false) {
			policyGroupId = POLICY_GROUP_ID_FREE;
		} else if (bSktTelecom == false) {
			policyGroupId = POLICY_GROUP_ID_NOTSKT;
		} else {
			policyGroupId = POLICY_GROUP_ID_FULL;
		}

		return this.policyMap.get(policyGroupId);
	}

	/**
	 * 
	 * <pre>
	 * (임시적) 제한 정책 목록 관리.
	 * </pre>
	 * 
	 * @param policyList
	 *            제한 정책 목록
	 */
	private void managePolicy(List<PurchaseOrderPolicy> policyList) {
		List<PurchaseOrderPolicy> fullPolicyList = new ArrayList<PurchaseOrderPolicy>();
		List<PurchaseOrderPolicy> freePolicyList = new ArrayList<PurchaseOrderPolicy>();
		List<PurchaseOrderPolicy> notSktPolicyList = new ArrayList<PurchaseOrderPolicy>();

		for (PurchaseOrderPolicy policy : policyList) {

			fullPolicyList.add(policy);

			if (policy.getbCharge() == false) {
				freePolicyList.add(policy);
			}
			if (policy.getbSktTelecom() == false) {
				notSktPolicyList.add(policy);
			}
		}

		this.policyMap.put(POLICY_GROUP_ID_FULL, fullPolicyList);
		this.policyMap.put(POLICY_GROUP_ID_FREE, freePolicyList);
		this.policyMap.put(POLICY_GROUP_ID_NOTSKT, notSktPolicyList);
	}

	/**
	 * 
	 * <pre>
	 * (임시적) 제한 정책 읽기.
	 * </pre>
	 * 
	 * @return 제한 정책 목록
	 */
	private List<PurchaseOrderPolicy> readPolicy() {
		List<PurchaseOrderPolicy> policyList = new ArrayList<PurchaseOrderPolicy>();
		PurchaseOrderPolicy policy = new PurchaseOrderPolicy();
		policy.setOrder(1);
		policy.setId(POLICY_ID_BLOCK);
		policy.setName("BLOCK_PURCHASE");
		policy.setDescription("결제 차단");
		policy.setPatternType(POLICY_PATTERN_BLACKLIST);
		policy.setTargetCd("");
		policy.setValue("");
		policy.setCheckObject(POLICY_CHECKOBJECT_PAYDEVICE);
		policy.setbCharge(true);
		policy.setbSktTelecom(false);
		policyList.add(policy);

		policy = new PurchaseOrderPolicy();
		policy.setOrder(2);
		policy.setId(POLICY_ID_TESTMDN);
		policy.setName("CHECK_TESTMDN");
		policy.setDescription("TEST MDN");
		policy.setPatternType(POLICY_PATTERN_WHITELIST);
		policy.setTargetCd("");
		policy.setValue("");
		policy.setCheckObject(POLICY_CHECKOBJECT_PAYDEVICE);
		policy.setbCharge(true);
		policy.setbSktTelecom(false);
		policyList.add(policy);

		policy = new PurchaseOrderPolicy();
		policy.setOrder(3);
		policy.setId(POLICY_ID_SKTTESTMDN);
		policy.setName("CHECK_STKTESTMDN");
		policy.setDescription("SKT 시험폰으로 Store에 등록되었는 지 체크");
		policy.setPatternType(POLICY_PATTERN_WHITELIST);
		policy.setTargetCd("");
		policy.setValue("");
		policy.setCheckObject(POLICY_CHECKOBJECT_PAYDEVICE);
		policy.setbCharge(true);
		policy.setbSktTelecom(true);
		policyList.add(policy);

		policy = new PurchaseOrderPolicy();
		policy.setOrder(4);
		policy.setId(POLICY_ID_SKTCORPMDN);
		policy.setName("CHECK_SKTCORPMDN");
		policy.setDescription("SKT 법인폰으로 Store에 등록되었는 지 체크");
		policy.setPatternType(POLICY_PATTERN_WHITELIST);
		policy.setTargetCd("");
		policy.setValue("");
		policy.setCheckObject(POLICY_CHECKOBJECT_PAYDEVICE);
		policy.setbCharge(true);
		policy.setbSktTelecom(true);
		policyList.add(policy);

		policy = new PurchaseOrderPolicy();
		policy.setOrder(5);
		policy.setId(POLICY_ID_SKPCORPMDN);
		policy.setName("CHECK_SKPCORPMDN");
		policy.setDescription("SKP 법인폰으로 Store에 등록되었는 지 체크");
		policy.setPatternType(POLICY_PATTERN_WHITELIST);
		policy.setTargetCd("");
		policy.setValue("");
		policy.setCheckObject(POLICY_CHECKOBJECT_PAYDEVICE);
		policy.setbCharge(true);
		policy.setbSktTelecom(true);
		policyList.add(policy);

		policy = new PurchaseOrderPolicy();
		policy.setOrder(6);
		policy.setId(POLICY_ID_SKT_LIMIT);
		policy.setName("CHECK_SKT_LIMIT");
		policy.setDescription("SKT 후불 결제한도");
		policy.setPatternType(POLICY_PATTERN_SKTLIMIT);
		policy.setTargetCd("");
		policy.setValue("");
		policy.setCheckObject(POLICY_CHECKOBJECT_PAYDEVICE);
		policy.setbCharge(true);
		policy.setbSktTelecom(true);
		policyList.add(policy);

		policy = new PurchaseOrderPolicy();
		policy.setOrder(7);
		policy.setId(POLICY_ID_SKT_SHOPPINGLIMIT);
		policy.setName("CHECK_SKT_SHOPPINGLIMIT");
		policy.setDescription("쇼핑 상품 SKT 후불 결제한도");
		policy.setPatternType(POLICY_PATTERN_SKTLIMIT);
		policy.setTargetCd("");
		policy.setValue("");
		policy.setCheckObject(POLICY_CHECKOBJECT_PAYDEVICE);
		policy.setbCharge(true);
		policy.setbSktTelecom(true);
		policyList.add(policy);

		policy = new PurchaseOrderPolicy();
		policy.setOrder(8);
		policy.setId(POLICY_ID_SKT_SENDLIMIT);
		policy.setName("CHECK_SKT_SENDLIMIT");
		policy.setDescription("선물 발신 SKT 후불 결제한도");
		policy.setPatternType(POLICY_PATTERN_SKTLIMIT);
		policy.setTargetCd("");
		policy.setValue("");
		policy.setCheckObject(POLICY_CHECKOBJECT_PAYDEVICE);
		policy.setbCharge(true);
		policy.setbSktTelecom(true);
		policyList.add(policy);

		return policyList;
	}
}
