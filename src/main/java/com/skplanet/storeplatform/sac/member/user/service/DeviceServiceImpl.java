package com.skplanet.storeplatform.sac.member.user.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDevice;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDeviceDetail;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.HeaderVo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.member.common.MemberConstants;
import com.skplanet.storeplatform.sac.member.user.controller.UserJoinController;

/**
 * 휴대기기 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@Service
public class DeviceServiceImpl implements DeviceService {

	private static final Logger logger = LoggerFactory
			.getLogger(UserJoinController.class);

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
		key.setKeyType("DEVICE_ID");
		key.setKeyString(req.getDeviceId());
		keySearchList.add(key);
		schDeviceListReq.setKeySearchList(keySearchList);

		/* 사용자 휴대기기 목록 조회 */
		SearchDeviceListResponse schDeviceListRes = this.deviceSCI
				.searchDeviceList(schDeviceListReq);

		ListDeviceRes res = new ListDeviceRes();
		List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();

		logger.info("=====================>{}", schDeviceListRes
				.getUserMbrDevice().size());

		if (schDeviceListRes.getUserMbrDevice().size() > 0) {
			for (UserMbrDevice devicdInfo : schDeviceListRes.getUserMbrDevice()) {

			}
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
	public void mergeDeviceInfo(DeviceInfo req) throws Exception {

		if (req.getDeviceId() == null) {
			throw new Exception("deviceId is null 기기정보 수정 불가");
		}

		/* 기기정보 수정 가능한 필드 */
		String deviceModelNo = req.getDeviceModelNo(); // 단말모델코드
		String imei = req.getImei(); // imei
		String deviceAccount = req.getDeviceAccount(); // gmailAddr
		String rooting = req.getRooting(); // rooting 여부
		String osVer = req.getOsVer() == null ? req.getOsVerOrg() : req.getOsVer(); // OS버젼,OS오리지날버젼
		String scVer = req.getScVer(); // SC버젼
		String uacd = req.getUacd(); // uacd
		
		/*	수정 미적용 필드	*/
		String imMngNum = req.getImMngNum(); // SKT 서비스 관리번호
		String deviceTelecom = req.getDeviceTelecom(); // 통신사코드
		String deviceNickName = req.getDeviceNickName(); // 휴대폰닉네임
		String isPrimary = req.getIsPrimary(); // 대표폰 여부
		String isRecvSms = req.getIsRecvSms(); // sms 수신여부
		String dotoriAuthDate = req.getDotoriAuthDate(); // 도토리인증일
		String dotoriAuthYn = req.getDotoriAuthYn(); // 도토리인증여부
		
		/* 기기정보 조회 */
		SearchDeviceRequest schDeviceReq = new SearchDeviceRequest();
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		key.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
		key.setKeyString(req.getDeviceId());
		keySearchList.add(key);
		schDeviceReq.setKeySearchList(keySearchList);

		SearchDeviceResponse schDeviceRes = this.deviceSCI.searchDevice(schDeviceReq);
		UserMbrDevice userMbrDevice = schDeviceRes.getUserMbrDevice();

		/* 파라메터 기기 정보와 SC콤포넌트 기기 정보 비교 */
		if (deviceModelNo != null && !deviceModelNo.equals(userMbrDevice.getDeviceModelNo())) {

		} else if (imei != null && !imei.equals(userMbrDevice.getNativeID())) {

		} else if (deviceAccount != null && !deviceAccount.equals(userMbrDevice.getDeviceAccount())) {

		} /*else if (imMngNum != null && !imMngNum.equals(userMbrDevice.getImMngNum())) {
			
		} else if (deviceTelecom != null && !deviceTelecom.equals(userMbrDevice.getDeviceTelecom())) {

		} else if (deviceNickName != null && !deviceNickName.equals(userMbrDevice.getDeviceNickName())) {

		} else if (isPrimary != null && !isPrimary.equals(userMbrDevice.getIsPrimary())) {

		} else if (isRecvSms != null && !isRecvSms.equals(userMbrDevice.getIsRecvSMS())) {

		}*/
		
		List<UserMbrDeviceDetail> deviceExtraList = userMbrDevice.getUserMbrDeviceDetail();
		if(deviceExtraList.size() > 0){
			for(UserMbrDeviceDetail extraInfo : deviceExtraList){
				
				if (rooting != null && extraInfo.getExtraProfile().equals(MemberConstants.DEVICE_EXTRA_ROOTING_YN)){
					if (!rooting.equals(extraInfo.getExtraProfileValue())){
						
					}
				} else if (osVer != null && extraInfo.getExtraProfile().equals(MemberConstants.DEVICE_EXTRA_OSVERSION)){
					if (!osVer.equals(extraInfo.getExtraProfileValue())){
						
					}
				} else if (scVer != null && extraInfo.getExtraProfile().equals(MemberConstants.DEVICE_EXTRA_SCVERSION)){
					if (!scVer.equals(extraInfo.getExtraProfileValue())){
						
					}
				} else if (uacd != null && extraInfo.getExtraProfile().equals(MemberConstants.DEVICE_EXTRA_UACD)){
					if (!uacd.equals(extraInfo.getExtraProfileValue())){
						
					}
				} /*else if (dotoriAuthDate != null && extraInfo.getExtraProfile().equals(MemberConstants.DEVICE_EXTRA_DODORYAUTH_DATE)){
					if (!dotoriAuthDate.equals(extraInfo.getExtraProfileValue())){
						
					}
				} else if (dotoriAuthYn != null && extraInfo.getExtraProfile().equals(MemberConstants.DEVICE_EXTRA_DODORYAUTH_YN)){
					if (!dotoriAuthYn.equals(extraInfo.getExtraProfileValue())){
						
					}
				}*/
				
			}
		}
		

		/* 기기정보 업데이트 */

	}
}