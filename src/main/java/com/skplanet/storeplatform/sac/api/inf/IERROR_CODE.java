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

public interface IERROR_CODE {
	enum ERROR_CODE {
		TIME_OUT("0100"), SYSTEM_ERROR("0200"), XML_VALIDATION_ERROR("0300"), DATA_ERROR("0400");
		private final String abbreviation;

		private static final Map<String, ERROR_CODE> LOOKup = new HashMap<String, ERROR_CODE>();
		static {
			for (ERROR_CODE r : ERROR_CODE.values())
				LOOKup.put(r.getAbbreviation(), r);
		}

		private ERROR_CODE(String abbreviation) {
			this.abbreviation = abbreviation;
		}

		public String getAbbreviation() {
			return abbreviation;
		}

		public static ERROR_CODE get(String abbreviation) {
			return LOOKup.get(abbreviation);
		}
	}

	public boolean checkERROR_CODE();
}
