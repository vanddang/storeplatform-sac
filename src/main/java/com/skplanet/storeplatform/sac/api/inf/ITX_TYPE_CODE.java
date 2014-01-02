/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.api.inf;

import java.util.HashMap;
import java.util.Map;

public interface ITX_TYPE_CODE {

	enum TX_TYPE_CODE {
		BD("bd"), CT("ct"), CP("cp"), ST("st"), LS("ls"), DT("dt"), AT("at");
		private final String abbreviation;

		private static final Map<String, TX_TYPE_CODE> LOOKup = new HashMap<String, TX_TYPE_CODE>();
		static {
			for (TX_TYPE_CODE r : TX_TYPE_CODE.values())
				LOOKup.put(r.getAbbreviation(), r);
		}

		private TX_TYPE_CODE(String abbreviation) {
			this.abbreviation = abbreviation;
		}

		public String getAbbreviation() {
			return abbreviation;
		}

		public static TX_TYPE_CODE get(String abbreviation) {
			return LOOKup.get(abbreviation);
		}
	}

	public boolean checkTX_TYPE_CODE();
}
