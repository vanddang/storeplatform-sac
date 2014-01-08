package com.skplanet.storeplatform.sac.member.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.sci.IDPSCI;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDevice;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.HeaderVo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;

/**
 * 휴대기기 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@Service
public class DeviceServiceImpl implements DeviceService {

	@Autowired
	private IDPSCI idpSCI; // IDP 연동 인터페이스

	@Autowired
	private UserSCI userSCI; // 회원 콤포넌트 사용자 기능 인터페이스

	@Autowired
	private DeviceSCI deviceSCI; // 회원 콤포넌트 휴대기기 기능 인터페이스

	@Override
	public CreateDeviceRes createDevice(HeaderVo headerVo, CreateDeviceReq req)
			throws Exception {

		String mdn = req.getDeviceInfo().getDeviceId();

		/* 기등록된 회원의 휴대기기 정보 처리 */
		this.preRegMemberDeviceRegist(mdn);

		/* 휴대기기 목록 조회 */

		/* 휴대기기 정보 등록 요청 */
		CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
		CreateDeviceResponse createDeviceRes = this.deviceSCI
				.createDevice(createDeviceReq);

		return null;
	}

	@Override
	public ListDeviceRes listDevice(HeaderVo headerVo, ListDeviceReq req)
			throws Exception {

		SearchDeviceListRequest schDeviceListReq = new SearchDeviceListRequest();
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		key.setKeyType("INSD_USERMBR_NO");
		key.setKeyString("IW12312341234");
		keySearchList.add(key);
		schDeviceListReq.setKeySearchList(keySearchList);

		/* 사용자 휴대기기 목록 조회 */
		SearchDeviceListResponse schDeviceListRes = this.deviceSCI
				.searchDeviceList(schDeviceListReq);

		ListDeviceRes res = new ListDeviceRes();
		List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
		for (UserMbrDevice devicdInfo : schDeviceListRes.getUserMbrDevice()) {

		}

		res.setDeviceInfoList(deviceInfoList);

		return res;
	}

	@Override
	public String preRegMemberDeviceRegist(String mdn) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String mergeDeviceInfo(DeviceInfo req) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}