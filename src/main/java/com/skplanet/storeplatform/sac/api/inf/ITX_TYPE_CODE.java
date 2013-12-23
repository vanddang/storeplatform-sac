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
