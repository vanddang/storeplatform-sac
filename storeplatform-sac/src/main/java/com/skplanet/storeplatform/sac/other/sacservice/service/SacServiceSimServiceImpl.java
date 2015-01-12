package com.skplanet.storeplatform.sac.other.sacservice.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * SacServiceSimeService 클래스
 *
 * Created on 2014. 6. 11. by 서대영. SK 플래닛
 */
@Service
public class SacServiceSimServiceImpl implements SacServiceSimService {

	/**
	 * 450/03 : Power 017 (Shinsegi Telecom, Inc.)
	 * 450/05 : SKTelecom (SK Telecom)
     * 450/11 : SKTelecom (Korea Cable Telecom(t-plus), Eco-mobile)
	 */
	private static final String[] SKT_SIM_OPERATORS = new String[]{"450/03", "450/05", "450/11"};

	@Override
	public boolean belongsToSkt(String simOperator) {
		if (StringUtils.isBlank(simOperator)) {
			return false;
		}

		for (String s : SKT_SIM_OPERATORS) {
			if (s.equals(simOperator)) {
				return true;
			}
		}

		return false;
	}

}
