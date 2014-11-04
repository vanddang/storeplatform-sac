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

	/**
	 * <pre>
	 * 2.1.44. 휴대기기 PIN 번호 등록.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            CreateDevicePinSacReq
	 * @return CreateDevicePinSacRes
	 */
	public CreateDevicePinSacRes regDevicePin(SacRequestHeader header, CreateDevicePinSacReq req);

	/**
	 * <pre>
	 * 2.1.45. 휴대기기 PIN 번호 수정.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            ModifyDevicePinSacReq
	 * @return ModifyDevicePinSacRes
	 */
	public ModifyDevicePinSacRes modDevicePin(SacRequestHeader header, ModifyDevicePinSacReq req);

	/**
	 * <pre>
	 * 2.1.46. 휴대기기 PIN 번호 찾기.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            SearchDevicePinSacReq
	 * @return SearchDevicePinSacRes
	 */
	public SearchDevicePinSacRes searchDevicePin(SacRequestHeader header, SearchDevicePinSacReq req);

	/**
	 * <pre>
	 * 2.1.46. 휴대기기 PIN 번호 확인.
	 * </pre>
	 * 
	 * @param header
	 *            SacRequestHeader
	 * @param req
	 *            CheckDevicePinSacReq
	 * @return CheckDevicePinSacRes
	 */
	public CheckDevicePinSacRes checkDevicePin(SacRequestHeader header, CheckDevicePinSacReq req);
}
