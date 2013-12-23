/*****************************************************************************
 * SKT TStore Project ::: PARTNER Site :::: Shopping Coupon interface
 *****************************************************************************
 * 1.클래스 개요 :
 * 2.작   성  자 : Kim Hyung Sik
 * 3.작 성 일 자 : 2013. 12. 29.
 * <pre>
 * 4.수 정 일 자 :
 *      . <날짜> : <수정 내용> (성명)
 *      . 2013. 12. 29.  : 최초 생성 (jade)
 * @author Kim Hyung Sik
 * </pre>
 * @version 1.0
 *****************************************************************************/

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
