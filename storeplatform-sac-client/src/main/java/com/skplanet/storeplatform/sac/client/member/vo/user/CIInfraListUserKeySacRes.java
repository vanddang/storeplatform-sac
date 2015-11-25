package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.ArrayList;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] CI Infra 회원키 리스트 조회.
 * 
 * Updated on : 2014. 10. 2. Updated by : 반범진, SK 플래닛.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CIInfraListUserKeySacRes extends CommonInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 회원키 리스트.
	 */
	private ArrayList<CIInfraUserInfoSac> userInfoList;

	/**
	 * <pre>
	 * 회원키 리스트 리턴.
	 * </pre>
	 * 
	 * @return userInfoList
	 */
	public ArrayList<CIInfraUserInfoSac> getUserInfoList() {
		return this.userInfoList;
	}

	/**
	 * <pre>
	 * 회원키 리스트 셋팅.
	 * </pre>
	 * 
	 * @param userInfoList
	 *            CIInfraUserInfoList
	 */
	public void setUserInfoList(ArrayList<CIInfraUserInfoSac> userInfoList) {
		this.userInfoList = userInfoList;
	}

}
