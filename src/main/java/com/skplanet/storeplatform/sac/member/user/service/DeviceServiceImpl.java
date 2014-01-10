package com.skplanet.storeplatform.sac.member.user.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.external.client.idp.vo.IDPReceiverM;
import com.skplanet.storeplatform.framework.core.util.StringUtil;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.CreateDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceListRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceListResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchDeviceResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserRequest;
import com.skplanet.storeplatform.member.client.user.sci.vo.SearchUserResponse;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDevice;
import com.skplanet.storeplatform.member.client.user.sci.vo.UserMbrDeviceDetail;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.HeaderVo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceRes;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceRes;
import com.skplanet.storeplatform.sac.member.common.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.idp.IDPManager;
import com.skplanet.storeplatform.sac.member.common.idp.ImIDPManager;

/**
 * 휴대기기 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@Service
public class DeviceServiceImpl implements DeviceService {

	private static final Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);

	private static final String SYSTEMID = "S001";
	private static final String TENANTID = "S01";
	private static CommonRequest commonRequest;

	static {
		commonRequest = new CommonRequest();
		commonRequest.setSystemID(SYSTEMID);
		commonRequest.setTenantID(TENANTID);
	}

	@Autowired
	private UserSCI userSCI; // 회원 콤포넌트 사용자 기능 인터페이스

	@Autowired
	private DeviceSCI deviceSCI; // 회원 콤포넌트 휴대기기 기능 인터페이스

	@Autowired
	private IDPManager idpManager; // IDP연동 클래스
	
	@Autowired
	private ImIDPManager imIdpManager; // IDP 통합회원 연동 클래스
	
	@Override
	public CreateDeviceRes createDevice(HeaderVo headerVo, CreateDeviceReq req)
			throws Exception {

		logger.info("######################## DeviceServiceImpl createDevice start ############################");
		
		String userKey = req.getUserKey();
		String deviceId = req.getDeviceInfo().getDeviceId();
		
		/* 기등록된 회원의 휴대기기 정보 처리 */
		this.preRegMemberDeviceRegist(userKey, req.getDeviceInfo());
		
		/* sc회원 컴포넌트 휴대기기 목록 조회 */
		ListDeviceReq listDeviceReq = new ListDeviceReq();
		listDeviceReq.setDeviceId(deviceId);
		ListDeviceRes listDeviceRes = this.listDevice(headerVo, listDeviceReq);
		
		
		
		/* IDP 휴대기기 정보 등록 요청 */

		logger.info("######################## DeviceServiceImpl createDevice start ############################");
		
		return null;
	}

	@Override
	public ListDeviceRes listDevice(HeaderVo headerVo, ListDeviceReq req)
			throws Exception {
		
		String userKey = req.getUserKey();
		
		SearchDeviceListRequest schDeviceListReq = new SearchDeviceListRequest();
		schDeviceListReq.setUserKey(userKey);
		schDeviceListReq.setSearchType(""); //여부 - 대표기기/모든기기
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		
		if(req.getDeviceId() != null) {
			key.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
			key.setKeyString(req.getDeviceId());	
		} else if(req.getUserId() != null) {
			key.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
			key.setKeyString(req.getUserId());	
		} else if(req.getUserKey() != null) {
			key.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
			key.setKeyString(req.getUserKey());	
		}  
		
		keySearchList.add(key);
		schDeviceListReq.setKeySearchList(keySearchList);

		/* 사용자 휴대기기 목록 조회 */
		SearchDeviceListResponse schDeviceListRes = this.deviceSCI.searchDeviceList(schDeviceListReq);

		ListDeviceRes res = new ListDeviceRes();
		List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();

		logger.info("=====================>{}", schDeviceListRes.getUserMbrDevice().size());

		if (schDeviceListRes.getUserMbrDevice().size() > 0) {
			for (UserMbrDevice devicdInfo : schDeviceListRes.getUserMbrDevice()) {

			}
		}

		res.setDeviceInfoList(deviceInfoList);

		return res;
	}

	@Override
	public void preRegMemberDeviceRegist(String userKey, DeviceInfo deviceInfo)
			throws Exception {

		String deviceId = deviceInfo.getDeviceId();
		
		UserMbrDevice userMbrDevice = new UserMbrDevice();
		userMbrDevice.setDeviceAccount(deviceInfo.getDeviceAccount());
		userMbrDevice.setUserKey(userKey);
		userMbrDevice.setTenantID(TENANTID);
		userMbrDevice.setDeviceID(deviceId);
		userMbrDevice.setDeviceModelNo(deviceInfo.getDeviceModelNo());
		userMbrDevice.setDeviceNickName(deviceInfo.getDeviceNickName());
		userMbrDevice.setDeviceTelecom(deviceInfo.getDeviceTelecom());
		userMbrDevice.setImMngNum(deviceInfo.getImMngNum());
		userMbrDevice.setIsPrimary(deviceInfo.getIsPrimary());
		userMbrDevice.setIsRecvSMS(deviceInfo.getIsRecvSms());
		userMbrDevice.setNativeID(deviceInfo.getNativeId());
		userMbrDevice.setUserMbrDeviceDetail(this.getConverterUserMbrDeviceDetailList(deviceInfo.getUserDeviceExtraInfo()));
		
		/*	1. 휴대기기 정보 등록 요청	*/
		CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
		createDeviceReq.setCommonRequest(commonRequest);
		createDeviceReq.setIsNew("Y");
		createDeviceReq.setUserKey(userKey);
		createDeviceReq.setUserMbrDevice(userMbrDevice);

		CreateDeviceResponse createDeviceRes = this.deviceSCI.createDevice(createDeviceReq);

		if (StringUtil.equals(createDeviceRes.getCommonResponse().getResultCode(), MemberConstants.RESULT_SUCCES)) {
			
			/*	2. 휴대기기 정보 등록완료 - 완료여부, 구매이력 이관 여부 확인*/
			
			
			/*	3. 구매이관 대상인 경우 구개 이력 이관요청	*/
			
			
			/*	4. 통합회원인 경우 무선회원 해지		*/
			
			/* 회원정보 조회 (userKey) */
			SearchUserRequest schUserReq = new SearchUserRequest();
			schUserReq.setCommonRequest(commonRequest);
			List<KeySearch> keySearchList = new ArrayList<KeySearch>();
			KeySearch key = new KeySearch();
			key.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
			key.setKeyString(userKey);
			keySearchList.add(key);
			schUserReq.setKeySearchList(keySearchList);
			SearchUserResponse schUserRes = this.userSCI.searchUser(schUserReq);
			
			if(!schUserRes.getCommonResponse().getResultCode().equals(MemberConstants.RESULT_SUCCES)){
				throw new Exception("["+schUserRes.getCommonResponse().getResultCode()+"] " + schUserRes.getCommonResponse().getResultMessage());
			}
			if(schUserRes.getUserMbr().getImSvcNo() != null){
				IDPReceiverM idpReceiver = this.idpManager.authForWap(deviceId);
				if (StringUtil.equals(idpReceiver.getResponseHeader().getResult(), IDPManager.IDP_RES_CODE_OK)) {
					/*idpReceiver = this.idpManager.secedeForWap(deviceId);
					if (!StringUtil.equals(idpReceiver.getResponseHeader().getResult(), IDPManager.IDP_RES_CODE_OK)) {
						throw new Exception("IDP secedeForWap fail mdn : [" + deviceId + "] result code : [" + idpReceiver.getResponseHeader().getResult() + "]");
					}*/
				}
			}
			
		} else {
			throw new Exception("["	+ createDeviceRes.getCommonResponse().getResultCode() + "] " + createDeviceRes.getCommonResponse().getResultMessage());
		}
	}

	@Override
	public void mergeDeviceInfo(DeviceInfo req) throws Exception {

		if (req.getDeviceId() == null) {
			throw new Exception("deviceId is null 기기정보 수정 불가");
		}

		/* 기기정보 수정 가능한 필드 */
		String deviceModelNo = req.getDeviceModelNo(); // 단말모델코드
		String nativeId = req.getNativeId(); // nativeId(imei)
		String deviceAccount = req.getDeviceAccount(); // gmailAddr
		String rooting = req.getRooting(); // rooting 여부
		String osVer = req.getOsVer() == null ? req.getOsVerOrg() : req.getOsVer(); // OS버젼,OS오리지날버젼
		String scVer = req.getScVer(); // SC버젼
		String uacd = req.getUacd(); // uacd

		/* 수정 미적용 필드 */
		String imMngNum = req.getImMngNum(); // SKT 서비스 관리번호
		String deviceTelecom = req.getDeviceTelecom(); // 통신사코드
		String deviceNickName = req.getDeviceNickName(); // 휴대폰닉네임
		String isPrimary = req.getIsPrimary(); // 대표폰 여부
		String isRecvSms = req.getIsRecvSms(); // sms 수신여부
		String dotoriAuthDate = req.getDotoriAuthDate(); // 도토리인증일
		String dotoriAuthYn = req.getDotoriAuthYn(); // 도토리인증여부

		/* 기기정보 조회 */
		SearchDeviceRequest schDeviceReq = new SearchDeviceRequest();
		schDeviceReq.setCommonRequest(commonRequest);

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
			userMbrDevice.setDeviceModelNo(deviceModelNo);
		} else if (nativeId != null	&& !nativeId.equals(userMbrDevice.getNativeID())) {
			userMbrDevice.setNativeID(nativeId);
		} else if (deviceAccount != null && !deviceAccount.equals(userMbrDevice.getDeviceAccount())) {
			userMbrDevice.setDeviceAccount(deviceAccount);
		}

		/* 휴대기기 부가 정보 비교 */
		List<UserMbrDeviceDetail> deviceExtraList = userMbrDevice.getUserMbrDeviceDetail();
		if (deviceExtraList.size() > 0) {

			List<UserMbrDeviceDetail> modDeviceExtraList = new ArrayList<UserMbrDeviceDetail>();

			for (UserMbrDeviceDetail extraInfo : deviceExtraList) {

				if (rooting != null && extraInfo.getExtraProfile().equals(MemberConstants.DEVICE_EXTRA_ROOTING_YN)) {
					if (!rooting.equals(extraInfo.getExtraProfileValue())) {
						extraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_ROOTING_YN);
						extraInfo.setExtraProfileValue(rooting);
						modDeviceExtraList.add(extraInfo);
					}
				} else if (osVer != null && extraInfo.getExtraProfile().equals(MemberConstants.DEVICE_EXTRA_OSVERSION)) {
					if (!osVer.equals(extraInfo.getExtraProfileValue())) {
						extraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_OSVERSION);
						extraInfo.setExtraProfileValue(osVer);
						modDeviceExtraList.add(extraInfo);
					}
				} else if (scVer != null && extraInfo.getExtraProfile().equals(MemberConstants.DEVICE_EXTRA_SCVERSION)) {
					if (!scVer.equals(extraInfo.getExtraProfileValue())) {
						extraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_SCVERSION);
						extraInfo.setExtraProfileValue(scVer);
						modDeviceExtraList.add(extraInfo);
					}
				} else if (uacd != null && extraInfo.getExtraProfile().equals(MemberConstants.DEVICE_EXTRA_UACD)) {
					if (!uacd.equals(extraInfo.getExtraProfileValue())) {
						extraInfo.setExtraProfile(MemberConstants.DEVICE_EXTRA_UACD);
						extraInfo.setExtraProfileValue(uacd);
						modDeviceExtraList.add(extraInfo);
					}
				} 
			}

			userMbrDevice.setUserMbrDeviceDetail(modDeviceExtraList);
		}

		/* 기기정보 업데이트 */
		CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
		createDeviceReq.setCommonRequest(commonRequest);
		createDeviceReq.setUserKey(schDeviceRes.getUserKey());
		createDeviceReq.setIsNew("N");
		createDeviceReq.setUserMbrDevice(userMbrDevice);
		CreateDeviceResponse createDeviceRes = this.deviceSCI.createDevice(createDeviceReq);

		if (!createDeviceRes.getCommonResponse().getResultCode().equals(MemberConstants.RESULT_SUCCES)) {
			throw new Exception("result_code : [" + createDeviceRes.getCommonResponse().getResultCode() + "] result_message : [" + createDeviceRes.getCommonResponse().getResultMessage() + "]");
		} 
	}
	
	/**
	 * 
	 * SAC 휴대기기 부가서비스 리스트정보 --> SC회원콤포넌트 휴대기기 부가서비스 리스트정보
	 * 
	 * @param list
	 * @return
	 */
	public List<UserMbrDeviceDetail> getConverterUserMbrDeviceDetailList(List<DeviceExtraInfo> list) {
		
		List<UserMbrDeviceDetail> userMbrDeviceDetailList = null;
		UserMbrDeviceDetail userMbrDeviceDetail = null;
		if(list.size() > 0){
			userMbrDeviceDetailList = new ArrayList<UserMbrDeviceDetail>();
			userMbrDeviceDetail = new UserMbrDeviceDetail();
		}
		
		for(DeviceExtraInfo extraInfo : list){
			
			String extraProfile = extraInfo.getExtraProfile();
			
			if(extraProfile.equals(MemberConstants.DEVICE_EXTRA_OMPDOWNLOADER_YN)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_STANDBYSCREEN_YN)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_UACD)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_OMPSUPPORT_YN)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_OSVERSION)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_SCVERSION)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_APPSTATISTICS_YN)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_DODORYAUTH_DATE)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_DODORYAUTH_YN)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_EMBEDDED_YN)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_OMPUACE)
					|| extraProfile.equals(MemberConstants.DEVICE_EXTRA_ROOTING_YN)) {
				
				userMbrDeviceDetail.setExtraProfile(extraProfile);
				userMbrDeviceDetail.setExtraProfileValue(extraInfo.getExtraProfileValue());
				
				userMbrDeviceDetailList.add(userMbrDeviceDetail);
			}
		}
		
		return userMbrDeviceDetailList;
		
	}
}