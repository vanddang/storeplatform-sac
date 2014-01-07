package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 서브계정 ID 중복 조회
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DuplicateBySubsellerIdRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String duplicateYn;

	public String getDuplicateYn() {
		return this.duplicateYn;
	}

	public void setDuplicateYn(String duplicateYn) {
		this.duplicateYn = duplicateYn;
	}

}
