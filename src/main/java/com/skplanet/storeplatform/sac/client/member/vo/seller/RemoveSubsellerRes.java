package com.skplanet.storeplatform.sac.client.member.vo.seller;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 서브계정 삭제
 * 
 * Updated on : 2014. 1. 7. Updated by : 한서구, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class RemoveSubsellerRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private int removeCnt;

	/**
	 * @return the removeCnt
	 */
	public int getRemoveCnt() {
		return this.removeCnt;
	}

	/**
	 * @param removeCnt
	 *            the removeCnt to set
	 */
	public void setRemoveCnt(int removeCnt) {
		this.removeCnt = removeCnt;
	}

}
