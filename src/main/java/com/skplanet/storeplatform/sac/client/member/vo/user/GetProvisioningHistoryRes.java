package com.skplanet.storeplatform.sac.client.member.vo.user;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * [RESPONSE] 회원 프로비저닝 이력 조회
 * 
 * Updated on : 2014. 1. 6. Updated by : 강신완, 부르칸.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class GetProvisioningHistoryRes extends CommonInfo {

	private static final long serialVersionUID = 1L;

	List<GameCenterSacRes> gameCenterList;

	public List<GameCenterSacRes> getGameCenterList() {
		return this.gameCenterList;
	}

	public void setGameCenterList(List<GameCenterSacRes> gameCenterList) {
		this.gameCenterList = gameCenterList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
