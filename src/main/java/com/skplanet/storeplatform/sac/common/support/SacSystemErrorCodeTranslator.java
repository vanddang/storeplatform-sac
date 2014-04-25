/**
 * 
 */
package com.skplanet.storeplatform.sac.common.support;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.skplanet.storeplatform.framework.core.exception.SystemErrorCodeTranslator;

/**
 * Calss 설명
 * 
 * Updated on : 2014. 4. 23. Updated by : 홍占썸동, SK 占시뤄옙占쏙옙.
 */
public class SacSystemErrorCodeTranslator implements SystemErrorCodeTranslator {

	private final Map<String, String> systemErrorCodeMap;

	public SacSystemErrorCodeTranslator() {
		this.systemErrorCodeMap = new HashMap<String, String>();

		this.systemErrorCodeMap.put("com.skplanet.storeplatform.sac", "SAC");
		this.systemErrorCodeMap.put("com.skplanet.storeplatform.sac.api", "SAC_DSP");
		this.systemErrorCodeMap.put("com.skplanet.storeplatform.sac.common", "SAC_CMN");
		this.systemErrorCodeMap.put("com.skplanet.storeplatform.sac.display", "SAC_DSP");
		this.systemErrorCodeMap.put("com.skplanet.storeplatform.sac.example", "SAC_EXP");
		this.systemErrorCodeMap.put("com.skplanet.storeplatform.sac.member", "SAC_MEM");
		this.systemErrorCodeMap.put("com.skplanet.storeplatform.sac.other", "SAC_OTH");
		this.systemErrorCodeMap.put("com.skplanet.storeplatform.sac.product", "SAC_PRD");
		this.systemErrorCodeMap.put("com.skplanet.storeplatform.sac.purchase", "SAC_PUR");
		this.systemErrorCodeMap.put("com.skplanet.storeplatform.sac.runtime", "SAC_CMN");

		this.systemErrorCodeMap.put("com.skplanet.storeplatform.sc", "SC");
		this.systemErrorCodeMap.put("com.skplanet.storeplatform.sc.bibi", "SC_BIBI");

		this.systemErrorCodeMap.put("com.skplanet.storeplatform.member", "SC_MEM");
		this.systemErrorCodeMap.put("com.skplanet.storeplatform.purchase", "SC_PUR");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.common.support.SystemErrorCodeTranslator#translate(java.lang.String)
	 */
	@Override
	public String translate(String packagee) {

		List<String> splitedPackageList = Arrays.asList(StringUtils.split(packagee, "."));

		String result;

		for (int index = splitedPackageList.size() - 1, min = 0; index >= min; index--) {
			result = this.systemErrorCodeMap.get(StringUtils.join(splitedPackageList.subList(0, index), "."));

			if (result != null) {
				return result;
			}
		}

		return null;
	}
}
