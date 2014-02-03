package com.skplanet.storeplatform.sac.client.member.vo.seller;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.BanksByCountry;

/**
 * 나라별 해외은행 정보
 * 
 * Updated on : 2014. 2. 3. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ListBanksByCountryRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	/** 나라별 해외은행 정보. */
	private List<BanksByCountry> banksByCountry;

	public List<BanksByCountry> getBanksByCountry() {
		return this.banksByCountry;
	}

	public void setBanksByCountry(List<BanksByCountry> banksByCountry) {
		this.banksByCountry = banksByCountry;
	}

}
