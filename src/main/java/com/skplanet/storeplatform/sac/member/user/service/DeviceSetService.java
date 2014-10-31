package com.skplanet.storeplatform.sac.member.user.service;

import com.skplanet.storeplatform.sac.client.member.vo.user.CheckDevicePinSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CheckDevicePinSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDevicePinSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDevicePinSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyDevicePinSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ModifyDevicePinSacRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchDevicePinSacReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.SearchDevicePinSacRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;

/**
 * 휴대기기 설정 정보 관련 InterFace
 * 
 * Updated on : 2014. 10. 28. Updated by : Rejoice, Burkhan
 */
public interface DeviceSetService {

	public CreateDevicePinSacRes createDevicePin(SacRequestHeader header, CreateDevicePinSacReq req);

	public ModifyDevicePinSacRes modifyDevicePin(SacRequestHeader header, ModifyDevicePinSacReq req);

	public SearchDevicePinSacRes searchDevicePin(SacRequestHeader header, SearchDevicePinSacReq req);

	public CheckDevicePinSacRes checkDevicePin(SacRequestHeader header, CheckDevicePinSacReq req);
}
