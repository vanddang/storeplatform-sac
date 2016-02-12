/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.user.service;

import com.skplanet.storeplatform.external.client.csp.vo.GetCustomerEcRes;
import com.skplanet.storeplatform.external.client.csp.vo.GetMvnoEcRes;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.KeySearch;
import com.skplanet.storeplatform.member.client.common.vo.MbrAuth;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSCI;
import com.skplanet.storeplatform.member.client.user.sci.DeviceSetSCI;
import com.skplanet.storeplatform.member.client.user.sci.UserSCI;
import com.skplanet.storeplatform.member.client.user.sci.vo.*;
import com.skplanet.storeplatform.sac.api.util.DateUtil;
import com.skplanet.storeplatform.sac.api.util.StringUtil;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacReq;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.ChangedDeviceHistorySacRes;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceTelecomInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.MajorDeviceInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.UserInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.*;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.member.common.MemberCommonComponent;
import com.skplanet.storeplatform.sac.member.common.MemberCommonInternalComponent;
import com.skplanet.storeplatform.sac.member.common.constant.MemberConstants;
import com.skplanet.storeplatform.sac.member.common.util.DeviceUtil;
import com.skplanet.storeplatform.sac.member.common.util.ValidationCheckUtils;
import com.skplanet.storeplatform.sac.member.common.vo.Device;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 휴대기기 관련 인터페이스 구현체.
 * 
 * Updated on : 2014. 1. 6. Updated by : 반범진, 지티소프트.
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceServiceImpl.class);

    @Autowired
    private MemberCommonComponent commService;

    @Autowired
    private UserSCI userSCI;

    @Autowired
    private DeviceSCI deviceSCI;

    @Autowired
    private DeviceSetSCI deviceSetSCI;

	@Autowired
	private UserSearchService userSearchService;

	@Autowired
	@Resource(name = "memberAddDeviceAmqpTemplate")
	private AmqpTemplate memberAddDeviceAmqpTemplate;

    @Autowired
    @Resource(name = "memberDelDeviceAmqpTemplate")
    private AmqpTemplate memberDelDeviceAmqpTemplate;

    @Autowired
    @Resource(name = "memberRetireAmqpTemplate")
    private AmqpTemplate memberRetireAmqpTemplate;

    @Autowired
    @Resource(name = "memberShoppingChangeKeyAmqpTemplate")
    private AmqpTemplate memberShoppingChangeKeyAmqpTemplate;

    @Autowired
    private MemberCommonInternalComponent mcic;

    @Value("#{propertiesForSac['sac.member.device.max.cnt']}")
    private Integer deviceRegMaxCnt;

    @Autowired
    UserMarketPinService userMarketPinService;

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.DeviceService#createDevice
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.CreateDeviceReq)
	 */
    @Override
    public CreateDeviceRes regDevice(SacRequestHeader requestHeader, CreateDeviceReq req) {

		/* 모번호 조회 */
        if (StringUtils.equals(req.getDeviceInfo().getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKT)
                && StringUtils.isNotBlank(req.getDeviceInfo().getMdn())) {
            req.getDeviceInfo().setMdn(this.commService.getOpmdMdnInfo(req.getDeviceInfo().getMdn()));
        }

		/* 보유한 휴대기기 리스트 조회 */
        SearchDeviceListRequest schDeviceListReq = new SearchDeviceListRequest();
        schDeviceListReq.setCommonRequest(commService.getSCCommonRequest(requestHeader));
        schDeviceListReq.setUserKey(req.getUserKey());
        schDeviceListReq.setIsMainDevice(MemberConstants.USE_N); // 대표기기만 조회(Y), 모든기기 조회(N)
        List<KeySearch> keySearchList = new ArrayList<KeySearch>();
        KeySearch key = new KeySearch();
        key.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
        key.setKeyString(req.getUserKey());
        keySearchList.add(key);
        schDeviceListReq.setKeySearchList(keySearchList);
        try {
            SearchDeviceListResponse schDeviceListRes = this.deviceSCI.searchDeviceList(schDeviceListReq);
			/*	기등록된 휴대기기 체크 */
            if (schDeviceListRes != null && schDeviceListRes.getUserMbrDevice() != null) {
                for (UserMbrDevice userMbrDevice : schDeviceListRes.getUserMbrDevice()) {
                    if (StringUtils.equals(req.getDeviceInfo().getDeviceIdType(), MemberConstants.DEVICE_ID_TYPE_MSISDN)) {
                        if (StringUtils.equals(userMbrDevice.getMdn(), req.getDeviceInfo().getMdn())) {
                            throw new StorePlatformException("SAC_MEM_1502");
                        }
                    } else {
                        if (StringUtils.equals(userMbrDevice.getDeviceID(), req.getDeviceInfo().getDeviceId())) {
                            throw new StorePlatformException("SAC_MEM_1502");
                        }
                    }
                }
                if(schDeviceListRes.getUserMbrDevice().size() >= deviceRegMaxCnt) {
                    throw new StorePlatformException("SAC_MEM_1501");
                }
            }
        } catch (StorePlatformException e) {
            if (!StringUtils.equals(e.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)) {
                throw e;
            }
        }

		/* 휴대기기 등록 처리 */
        req.getDeviceInfo().setUserKey(req.getUserKey());
        String deviceKey = this.regDeviceInfo(requestHeader, req.getDeviceInfo());

        CreateDeviceRes res = new CreateDeviceRes();
        if (StringUtils.equals(req.getDeviceInfo().getDeviceIdType(), MemberConstants.DEVICE_ID_TYPE_MSISDN)) {
            res.setDeviceId(req.getDeviceInfo().getMdn());
        } else {
            res.setDeviceId(req.getDeviceInfo().getDeviceId());
        }
        res.setUserKey(req.getUserKey());
        res.setDeviceKey(deviceKey);

        return res;

    }


    /*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.DeviceService#modDevice
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.ModifyDeviceReq)
	 */
    @Override
    public ModifyDeviceRes modDevice(SacRequestHeader requestHeader, ModifyDeviceReq req) {

		/* 모번호 조회 */
        if (StringUtils.isNotBlank(req.getDeviceInfo().getMdn())) {
            req.getDeviceInfo().setMdn(this.commService.getOpmdMdnInfo(req.getDeviceInfo().getMdn()));
        }

        // gmail 정보 파싱
        if (StringUtils.isNotBlank(req.getDeviceInfo().getDeviceAccount())) {
            req.getDeviceInfo().setDeviceAccount(DeviceUtil.getGmailStr(req.getDeviceInfo().getDeviceAccount()));

        }
        ModifyDeviceRequest modifyDeviceRequest = new ModifyDeviceRequest();
        modifyDeviceRequest.setCommonRequest(commService.getSCCommonRequest(requestHeader));
        modifyDeviceRequest.setUserKey(req.getUserKey());
        modifyDeviceRequest.setUserMbrDevice(DeviceUtil.getConverterUserMbrDeviceInfo(req.getDeviceInfo()));
        modifyDeviceRequest.setChgCaseCd(req.getChgCaseCd());
        ModifyDeviceResponse modifyDeviceResponse = deviceSCI.modifyDevice(modifyDeviceRequest);

        ModifyDeviceRes res = new ModifyDeviceRes();
        res.setUserKey(modifyDeviceResponse.getUserKey());
        res.setDeviceKey(modifyDeviceResponse.getDeviceKey());

        return res;
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.DeviceService#listDevice
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.user.ListDeviceReq)
	 */
    @Override
    public ListDeviceRes listDevice(SacRequestHeader requestHeader, ListDeviceReq req) {

        /** 1. 헤더 정보 셋팅 */
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());

        String userKey = req.getUserKey();

        SearchDeviceListRequest schDeviceListReq = new SearchDeviceListRequest();
        schDeviceListReq.setUserKey(userKey);
        schDeviceListReq.setIsMainDevice(req.getIsMainDevice()); // 대표기기만 조회(Y), 모든기기 조회(N)
        schDeviceListReq.setIsUsed(MemberConstants.USE_Y);

        List<KeySearch> keySearchList = new ArrayList<KeySearch>();
        KeySearch key = new KeySearch();

        ListDeviceRes res = new ListDeviceRes();

        /** 2. deviceId가 있으면 단건 조회후 응답 처리*/
        if (StringUtils.isNotEmpty(req.getDeviceId())) {
            /** 2-1. 모번호 조회 및 셋팅 */
            req.setDeviceId(this.commService.getOpmdMdnInfo(req.getDeviceId()));

            /** 2-2. 단건 조회 처리 */
            DeviceInfo deviceInfo = this.srhDevice(requestHeader, MemberConstants.KEY_TYPE_DEVICE_ID,
                    req.getDeviceId(), userKey);
            if (deviceInfo != null) {
                res.setUserId(deviceInfo.getUserId());
                res.setUserKey(deviceInfo.getUserKey());
                List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
                deviceInfoList.add(deviceInfo);
                res.setDeviceInfoList(deviceInfoList);
            }

            return res;

        /** 3. mdn이 있으면 단건 조회후 응답 처리 */
        } else if (StringUtils.isNotEmpty(req.getMdn())) {
            /** 3-1. 모번호 조회 및 셋팅 */
            req.setMdn(this.commService.getOpmdMdnInfo(req.getMdn()));

            /** 3-2. 단건 조회 처리 */
            DeviceInfo deviceInfo = this.srhDevice(requestHeader, MemberConstants.KEY_TYPE_MDN,
                    req.getMdn(), userKey);
            if (deviceInfo != null) {
                res.setUserId(deviceInfo.getUserId());
                res.setUserKey(deviceInfo.getUserKey());
                List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
                deviceInfoList.add(deviceInfo);
                res.setDeviceInfoList(deviceInfoList);
            }
            return res;

        /** 4. deviceKey가 있으면 단건 조회후 응답 처리 */
        } else if (StringUtils.isNotEmpty(req.getDeviceKey())) {
            /** 4-1. 단건 조회 처리 */
            DeviceInfo deviceInfo = this.srhDevice(requestHeader, MemberConstants.KEY_TYPE_INSD_DEVICE_ID,
                    req.getDeviceKey(), userKey);
            if (deviceInfo != null) {
                res.setUserId(deviceInfo.getUserId());
                res.setUserKey(deviceInfo.getUserKey());
                List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
                deviceInfoList.add(deviceInfo);
                res.setDeviceInfoList(deviceInfoList);
            }
            return res;

        /** 5. userId가 있으면 searchkey 셋팅 */
        } else if (StringUtils.isNotEmpty(req.getUserId())) {
            key.setKeyType(MemberConstants.KEY_TYPE_MBR_ID);
            key.setKeyString(req.getUserId());

        /** 6. userKey가 있으면 searchkey 셋팅 */
        } else if (StringUtils.isNotEmpty(req.getUserKey())) {
            key.setKeyType(MemberConstants.KEY_TYPE_INSD_USERMBR_NO);
            key.setKeyString(req.getUserKey());

        /** 7. svc_no가 있으면 searchKey 셋팅 */
        } else if (StringUtils.isNotEmpty(req.getSvcMangNo())) {
            key.setKeyType(MemberConstants.KEY_TYPE_SVC_MANG_NO);
            key.setKeyString(req.getSvcMangNo());
        }

        /** 8. userId > KEY_TYPE_MBR_ID / userKey > KEY_TYPE_INSD_USERMBR_NO / svcMangNo 조회 시 DeviceList 조회 */
        keySearchList.add(key);
		schDeviceListReq.setKeySearchList(keySearchList);
		schDeviceListReq.setCommonRequest(commonRequest);

		/* 사용자 휴대기기 목록 조회 */
		SearchDeviceListResponse schDeviceListRes = null;

		try {
			schDeviceListRes = this.deviceSCI.searchDeviceList(schDeviceListReq);
			/* response 셋팅 */
			res.setUserId(schDeviceListRes.getUserID());
			res.setUserKey(schDeviceListRes.getUserKey());

			List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
			for (UserMbrDevice userMbrDevice : schDeviceListRes.getUserMbrDevice()) {
				DeviceInfo deviceInfo = DeviceUtil.getConverterDeviceInfo(userMbrDevice);
                deviceInfo.setUserId(schDeviceListRes.getUserID());

				/* 폰정보 DB 조회하여 추가 정보 반영 */
				Device device = this.commService.getPhoneInfo(deviceInfo.getDeviceModelNo());
				if (device != null) {
					deviceInfo.setMakeComp(device.getMnftCompCd());
					deviceInfo.setModelNm(device.getModelNm());
					deviceInfo.setVmType(device.getVmTypeCd());
				}
				deviceInfoList.add(deviceInfo);
			}
			res.setDeviceInfoList(deviceInfoList);

		} catch (StorePlatformException ex) {
			if (!StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)) {
				throw ex;
			}
		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.DeviceService#searchDevice
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader, java.lang.String, java.lang.String)
	 */
	@Override
	public DeviceInfo srhDevice(SacRequestHeader requestHeader, String keyType, String keyString, String userKey) {

		/* 헤더 정보 셋팅 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());

		SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();
		searchDeviceRequest.setCommonRequest(commonRequest);

		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();
		key.setKeyType(keyType);
		key.setKeyString(keyString);
		keySearchList.add(key);

		searchDeviceRequest.setUserKey(userKey);
		searchDeviceRequest.setKeySearchList(keySearchList);

		DeviceInfo deviceInfo = null;

		try {

			SearchDeviceResponse schDeviceRes = this.deviceSCI.searchDevice(searchDeviceRequest);

			deviceInfo = new DeviceInfo();
			deviceInfo = DeviceUtil.getConverterDeviceInfo(schDeviceRes.getUserMbrDevice());
			deviceInfo.setUserId(schDeviceRes.getUserID());
			deviceInfo.setUserKey(schDeviceRes.getUserKey());

            /* 폰정보 DB 조회하여 추가 정보 반영 */
            Device device = this.commService.getPhoneInfo(deviceInfo.getDeviceModelNo());
            if (device != null) {
                deviceInfo.setMakeComp(device.getMnftCompCd());
                deviceInfo.setModelNm(device.getModelNm());
                deviceInfo.setVmType(device.getVmTypeCd());
            }

		} catch (StorePlatformException ex) {
			if (!StringUtils.equals(ex.getErrorInfo().getCode(), MemberConstants.SC_ERROR_NO_DATA)) {
				throw ex;
			}
		}

		return deviceInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.DeviceService# insertDeviceInfo(java.lang.String,
	 * java.lang.String, java.lang.String, com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo)
	 */
	@Override
	public String regDeviceInfo(SacRequestHeader requestHeader, DeviceInfo deviceInfo) {

		if(StringUtils.isBlank(deviceInfo.getUserKey())){
			throw new StorePlatformException("SAC_MEM_0001", "userKey");
		}

		if(StringUtils.isBlank(deviceInfo.getDeviceTelecom())){
			throw new StorePlatformException("SAC_MEM_0001", "deviceTelecom");
		}

		/*	통신사 코드 체크 */
		if(!this.commService.isValidDeviceTelecomCode(deviceInfo.getDeviceTelecom())){
			throw new StorePlatformException("SAC_MEM_1509");
		}

		/*	NON 통신사 MDN 체크 */
		if(StringUtils.equals(MemberConstants.DEVICE_TELECOM_NON, deviceInfo.getDeviceTelecom())
				&& StringUtils.isNotBlank(deviceInfo.getMdn())){
			throw new StorePlatformException("SAC_MEM_1514");
		}

		String userKey = deviceInfo.getUserKey();
		CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();

		/* 헤더 정보 셋팅 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());

		/* 서비스관리번호 조회 */
		if(StringUtils.isBlank(deviceInfo.getSvcMangNum()) && StringUtils.isNotBlank(deviceInfo.getMdn())
				&& (StringUtils.equals(MemberConstants.DEVICE_TELECOM_SKT, deviceInfo.getDeviceTelecom())
					|| StringUtils.equals(MemberConstants.DEVICE_TELECOM_KT, deviceInfo.getDeviceTelecom())
					|| StringUtils.equals(MemberConstants.DEVICE_TELECOM_LGT, deviceInfo.getDeviceTelecom())
		)){
			DeviceTelecomInfo deviceTelecomInfo = this.commService.getSvcMangNo(deviceInfo.getMdn(), deviceInfo.getDeviceTelecom(), deviceInfo.getNativeId(), deviceInfo.getSimSerialNo());
			deviceInfo.setSvcMangNum(deviceTelecomInfo.getSvcMangNum());
		}

		/* device header 값 셋팅(단말모델, OS버젼, SC버젼) */
		if(StringUtils.isBlank(deviceInfo.getDeviceModelNo())){ // 휴대기기 등록 API에서는 deviceInfo에 단말모델을 파라메터로 받는다. 그외 API에서는 디바이스헤더정보의 단말모델로 처리한다.
			deviceInfo.setDeviceModelNo(requestHeader.getDeviceHeader().getModel());
		}
		String osVersion = requestHeader.getDeviceHeader().getOs();
		String svcVersion = requestHeader.getDeviceHeader().getSvc();
		if (StringUtils.isNotBlank(osVersion)) {
			deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_OSVERSION,
					osVersion.substring(osVersion.lastIndexOf("/") + 1, osVersion.length()),
					deviceInfo.getDeviceExtraInfoList()));
		}
		if (StringUtils.isNotBlank(svcVersion)) {
			deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(
					MemberConstants.DEVICE_EXTRA_SCVERSION,
					svcVersion.substring(svcVersion.lastIndexOf("/") + 1, svcVersion.length()),
					deviceInfo.getDeviceExtraInfoList()));
		}

		/* gmail 정보 파싱 */
		if(StringUtils.isNotBlank(deviceInfo.getDeviceAccount())){
			deviceInfo.setDeviceAccount(DeviceUtil.getGmailStr(deviceInfo.getDeviceAccount()));
		}

		/* 휴대기기 정보 등록 요청 */
		createDeviceReq.setCommonRequest(commonRequest);
		createDeviceReq.setUserKey(userKey);
		UserMbrDevice userMbrDevice = DeviceUtil.getConverterUserMbrDeviceInfo(deviceInfo);
		userMbrDevice.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_USER_SELECT);
		createDeviceReq.setUserMbrDevice(userMbrDevice);
		CreateDeviceResponse createDeviceRes = this.deviceSCI.createDevice(createDeviceReq);

		if (StringUtils.isBlank(createDeviceRes.getDeviceKey())) {
			throw new StorePlatformException("SAC_MEM_1102"); // 휴대기기 등록에 실패하였습니다.
		}

		/* 기등록된 회원이 존재하는지 확인(모바일 회원에 대해서만 previous* 값들이 리턴됨) */
		String previousUserKey = createDeviceRes.getPreviousUserKey();
		String previousDeviceKey = createDeviceRes.getPreviousDeviceKey();
		String deviceKey = createDeviceRes.getDeviceKey();
		String previousIsDormant = createDeviceRes.getPreviousIsDormant(); // 기등록된 회원의 휴면계정유무
		String preDeviceKey = createDeviceRes.getPreDeviceKey();
		String preUserKey = createDeviceRes.getPreUserKey();
		if (StringUtils.isNotBlank(previousUserKey) && StringUtils.isNotBlank(previousDeviceKey)) {

			LOGGER.info(
					"기등록된 모바일 회원 이관처리 deviceId : {}, PreviousDeviceKey : {}, nowDeviceKey : {}, PreviousUserKey : {}, NowUserKey : {}, PreviousIsDormant : {}, preUserKey : {}, preDeviceKey : {}, ",
					deviceInfo.getDeviceId(), previousDeviceKey, deviceKey, previousUserKey, userKey,
					previousIsDormant, preUserKey, preDeviceKey);

			/* 전시/기타, 구매 파트 키 변경 */
			this.mcic.excuteInternalMethod(true, requestHeader.getTenantHeader().getSystemId(), userKey, previousUserKey, deviceKey,
					previousDeviceKey);

			/* 실명인증 비교 후 이관 or 초기화 */
			SearchRealNameRequest schRealNameReq = new SearchRealNameRequest();
			schRealNameReq.setCommonRequest(commonRequest);
			schRealNameReq.setUserKey(previousUserKey);
			schRealNameReq.setIsDormant(previousIsDormant);
			SearchRealNameResponse preSchRealNameRes = this.userSCI.searchRealName(schRealNameReq);

			schRealNameReq.setUserKey(userKey);
			schRealNameReq.setIsDormant(MemberConstants.USE_N);
			SearchRealNameResponse schRealNameRes = this.userSCI.searchRealName(schRealNameReq);

			if (preSchRealNameRes.getMbrAuth() != null
					&& StringUtils.isNotBlank(preSchRealNameRes.getMbrAuth().getCi())) {

				if (schRealNameRes.getMbrAuth() == null) { // 모바일 회원의 실명인증정보 -> id회원으로 이관

					LOGGER.info("기등록된 모바일 회원 실명인증 정보 이관 deviceId : {}, userKey : {}", deviceInfo.getDeviceId(), userKey);

					UpdateRealNameRequest updateRealNameRequest = new UpdateRealNameRequest();
					updateRealNameRequest.setCommonRequest(commonRequest);
					updateRealNameRequest.setIsOwn(MemberConstants.AUTH_TYPE_OWN);
					updateRealNameRequest.setIsRealName(MemberConstants.USE_Y);
					updateRealNameRequest.setUserKey(userKey);
					updateRealNameRequest.setUserMbrAuth(preSchRealNameRes.getMbrAuth());
					updateRealNameRequest.setIsDormant(MemberConstants.USE_N);
					UpdateRealNameResponse updateRealNameResponse = this.userSCI.updateRealName(updateRealNameRequest);
					if (StringUtils.isBlank(updateRealNameResponse.getUserKey())) {
						throw new StorePlatformException("SAC_MEM_0002", "userKey");
					}
				} else {
					if (!StringUtils.equals(preSchRealNameRes.getMbrAuth().getName(), schRealNameRes.getMbrAuth()
							.getName())
							|| !StringUtils.equals(preSchRealNameRes.getMbrAuth().getBirthDay(), schRealNameRes
									.getMbrAuth().getBirthDay())
							|| !StringUtils.equals(preSchRealNameRes.getMbrAuth().getSex(), schRealNameRes.getMbrAuth()
									.getSex())) { // 이름 생년월일 성별이 다른경우 초기화

						LOGGER.info("기등록된 모바일 회원 실명인증 정보 초기화 deviceId : {}, userKey : {}", deviceInfo.getDeviceId(),
								userKey);
						UpdateRealNameRequest updRealNameReq = new UpdateRealNameRequest();
						updRealNameReq.setCommonRequest(commonRequest);
						updRealNameReq.setIsRealName("N");
						updRealNameReq.setUserKey(userKey);
						updRealNameReq.setIsOwn(MemberConstants.AUTH_TYPE_OWN);
						MbrAuth mbrAuth = new MbrAuth();
						mbrAuth.setCi(" ");
						mbrAuth.setIsRealName("N");
						updRealNameReq.setUserMbrAuth(mbrAuth);
						updRealNameReq.setIsDormant(MemberConstants.USE_N);
						this.userSCI.updateRealName(updRealNameReq);
					}
				}
			}

			/* 배송지 정보 이관 */
			TransferDeliveryRequest transferDeliveryRequest = new TransferDeliveryRequest();
			transferDeliveryRequest.setCommonRequest(commonRequest);
			transferDeliveryRequest.setUserKey(userKey);
			transferDeliveryRequest.setPreUserKey(previousUserKey);
			TransferDeliveryResponse transferDeliveryResponse = this.userSCI.transferDelivery(transferDeliveryRequest);
			if (transferDeliveryResponse != null && StringUtil.isNotEmpty(transferDeliveryResponse.getUserKey())) {
				LOGGER.info("기등록된 모바일 회원 배송지 정보 이관 deviceId : {}, userKey : {}", deviceInfo.getDeviceId(), userKey);
			}

			/* 회원 전환 시 상품권 충전소 정보 이관 */
			TransferGiftChrgInfoRequest transferGiftChrgInfoRequest = new TransferGiftChrgInfoRequest();
			transferGiftChrgInfoRequest.setCommonRequest(commonRequest);
			transferGiftChrgInfoRequest.setPreUserKey(previousUserKey); // 기등록된 회원키
			transferGiftChrgInfoRequest.setUserKey(userKey);
			TransferGiftChrgInfoResponse transferGiftChrgInfoResponse = this.userSCI
					.transferGiftChrgInfo(transferGiftChrgInfoRequest);
			if (transferGiftChrgInfoResponse != null
					&& StringUtil.isNotEmpty(transferGiftChrgInfoResponse.getUserKey())) {
				LOGGER.info("기등록된 모바일 회원 상품권 충전소 정보 이관 deviceId : {}, userKey : {}", deviceInfo.getDeviceId(), userKey);
			}

            /*  market pin 이관*/
            TransferMarketPinRequest transferMarketPinRequest = new TransferMarketPinRequest();
            transferMarketPinRequest.setCommonRequest(commonRequest);
            transferMarketPinRequest.setPreUserKey(previousUserKey);
            transferMarketPinRequest.setUserKey(userKey);
            boolean isSuccessTransferMarketPin = userMarketPinService.transferMarketPin(preUserKey, userKey);
            if (isSuccessTransferMarketPin) {
                LOGGER.info("기등록된 모바일 회원 Market pin 정보 이관 deviceId : {}, userKey : {}", deviceInfo.getDeviceId(), userKey);
            }

			/**
			 * MQ 연동(회원 탈퇴) - 모바일 회원.
			 */
			RemoveMemberAmqpSacReq mqInfo = new RemoveMemberAmqpSacReq();
			try {
				mqInfo.setUserId(createDeviceRes.getPreviousUserID());
				mqInfo.setUserKey(previousUserKey);
				mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
                mqInfo.setDeviceId(deviceInfo.getMdn());
				mqInfo.setProfileImgPath(createDeviceRes.getPreviousProfileImgPath());
				mqInfo.setChgMemberYn(MemberConstants.USE_Y);
				this.memberRetireAmqpTemplate.convertAndSend(mqInfo);

			} catch (AmqpException ex) {
				LOGGER.error("MQ process fail {}", mqInfo);
			}

			/**
			 * MQ 연동(shopping 사용자키 변경)
			 */
			ChangeMemberKeyAmqpSacReq changeMemberKeyAmqpSacReq = new ChangeMemberKeyAmqpSacReq();
			try {
				changeMemberKeyAmqpSacReq.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
				changeMemberKeyAmqpSacReq.setUserKey(userKey);
				changeMemberKeyAmqpSacReq.setOldUserKey(previousUserKey);
				this.memberShoppingChangeKeyAmqpTemplate.convertAndSend(changeMemberKeyAmqpSacReq);
			} catch (AmqpException ex) {
				LOGGER.error("MQ process fail {}", changeMemberKeyAmqpSacReq);
			}
		}

		// 휴대기기 PIN 정보 이관 로직 추가 (2014-11-20)
		if (StringUtils.isNotBlank(preDeviceKey) && StringUtils.isNotBlank(preUserKey)) {

			TransferDeviceSetInfoRequest transferDeviceSetInfoRequest = new TransferDeviceSetInfoRequest();
			transferDeviceSetInfoRequest.setCommonRequest(commonRequest);
			transferDeviceSetInfoRequest.setUserKey(userKey);
			transferDeviceSetInfoRequest.setDeviceKey(deviceKey);
			transferDeviceSetInfoRequest.setIsDormant(MemberConstants.USE_N);
			transferDeviceSetInfoRequest.setPreUserKey(preUserKey);
			transferDeviceSetInfoRequest.setPreDeviceKey(preDeviceKey);
			transferDeviceSetInfoRequest.setPreIsDormant(previousIsDormant);
			TransferDeviceSetInfoResponse transferDeviceSetInfoResponse = this.deviceSetSCI.transferDeviceSetInfo(transferDeviceSetInfoRequest);

			if (transferDeviceSetInfoResponse != null
					&& StringUtil.isNotEmpty(transferDeviceSetInfoResponse.getUserKey())
					&& StringUtil.isNotEmpty(transferDeviceSetInfoResponse.getDeviceKey())) {
				LOGGER.info("기등록된 모바일 회원 휴대기기 설정 정보 이관 deviceId : {}, userKey : {}", deviceInfo.getDeviceId(), userKey);
			}

		}

		/* MQ 연동 - 휴대기기 등록 */
		CreateDeviceAmqpSacReq mqInfo = new CreateDeviceAmqpSacReq();
		try {
			mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
			if (StringUtils.isNotBlank(previousUserKey) && StringUtils.isNotBlank(previousDeviceKey)) {
				mqInfo.setOldUserKey(previousUserKey);
				mqInfo.setOldDeviceKey(previousDeviceKey);
			}
			mqInfo.setUserKey(userKey);
			mqInfo.setDeviceKey(deviceKey);
            if(StringUtils.isNotBlank(deviceInfo.getMdn())){
                mqInfo.setDeviceId(deviceInfo.getMdn());
            }else{
                mqInfo.setDeviceId(deviceInfo.getDeviceId());
            }
			mqInfo.setMnoCd(deviceInfo.getDeviceTelecom());
			this.memberAddDeviceAmqpTemplate.convertAndSend(mqInfo);
		} catch (AmqpException ex) {
			LOGGER.info("MQ process fail {}", mqInfo);
		}

		return deviceKey;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.DeviceService# updateDeviceInfo(java.lang.String,
	 * java.lang.String, com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo)
	 */
	@Override
	public String modDeviceInfo(SacRequestHeader requestHeader, DeviceInfo deviceInfo, boolean isDeviceIdChange) {

		/* 기기정보 조회 */
		SearchDeviceRequest schDeviceReq = new SearchDeviceRequest();
		List<KeySearch> keySearchList = new ArrayList<KeySearch>();
		KeySearch key = new KeySearch();

		if (StringUtils.isNotBlank(deviceInfo.getDeviceKey())) {
			key.setKeyType(MemberConstants.KEY_TYPE_INSD_DEVICE_ID);
			key.setKeyString(deviceInfo.getDeviceKey());
		} else if (StringUtils.isNotBlank(deviceInfo.getDeviceId())) {
            if(ValidationCheckUtils.isDeviceId(deviceInfo.getDeviceId())){
                key.setKeyType(MemberConstants.KEY_TYPE_DEVICE_ID);
                key.setKeyString(deviceInfo.getDeviceId());
            }else {
                key.setKeyType(MemberConstants.KEY_TYPE_MDN);
                key.setKeyString(deviceInfo.getDeviceId());
            }
		}

		keySearchList.add(key);
		schDeviceReq.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));
		schDeviceReq.setUserKey(deviceInfo.getUserKey());
		schDeviceReq.setKeySearchList(keySearchList);

		SearchDeviceResponse schDeviceRes = null;

		try {

			schDeviceRes = this.deviceSCI.searchDevice(schDeviceReq);

		} catch (StorePlatformException ex) {
			if (ex.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_DATA)) {
				throw new StorePlatformException("SAC_MEM_0002", "휴대기기");
			} else {
				throw ex;
			}
		}
		UserMbrDevice dbUserMbrDevice = schDeviceRes.getUserMbrDevice();

		UserMbrDevice userMbrDevice = new UserMbrDevice();
		userMbrDevice.setUserKey(dbUserMbrDevice.getUserKey());
		userMbrDevice.setDeviceKey(dbUserMbrDevice.getDeviceKey());
		userMbrDevice.setDeviceID(dbUserMbrDevice.getDeviceID());

		/* 부가정보 등록시 셋팅할 값들 */
		//deviceInfo.setTenantId(requestHeader.getTenantHeader().getTenantId());
		deviceInfo.setUserKey(dbUserMbrDevice.getUserKey());
		deviceInfo.setDeviceKey(dbUserMbrDevice.getDeviceKey());

		/* 수정 기기정보 필드 */
		String deviceModelNo = deviceInfo.getDeviceModelNo(); // 단말모델코드
		String nativeId = deviceInfo.getNativeId(); // nativeId(imei)
		String deviceAccount = DeviceUtil.getGmailStr(deviceInfo.getDeviceAccount()); // gmailAddr
		String deviceTelecom = deviceInfo.getDeviceTelecom(); // 통신사코드
		//String deviceNickName = deviceInfo.getDeviceNickName(); // 휴대폰닉네임
		String isPrimary = deviceInfo.getIsPrimary(); // 대표폰 여부
		String isRecvSms = deviceInfo.getIsRecvSms(); // sms 수신여부
		String svcMangNum = deviceInfo.getSvcMangNum(); // SKT 휴대기기 통합 관리 번호
		String joinId = deviceInfo.getJoinId(); // 가입채널코드

		StringBuffer deviceInfoChangeLog = new StringBuffer();

		if (StringUtils.isNotBlank(deviceInfo.getDeviceId())
				&& !StringUtils.equals(dbUserMbrDevice.getDeviceID(), deviceInfo.getDeviceId()) && isDeviceIdChange) {

			deviceInfoChangeLog.append("[deviceId]").append(dbUserMbrDevice.getDeviceID()).append("->")
					.append(deviceInfo.getDeviceId());
			userMbrDevice.setDeviceID(deviceInfo.getDeviceId());

		}

		if (StringUtils.isNotBlank(deviceModelNo)
				&& !StringUtils.equals(dbUserMbrDevice.getDeviceModelNo(), deviceInfo.getDeviceModelNo())) {

			deviceInfoChangeLog.append("[deviceModelNo]").append(dbUserMbrDevice.getDeviceModelNo()).append("->")
					.append(deviceModelNo);
			userMbrDevice.setDeviceModelNo(deviceModelNo);

		}

		if (StringUtils.isNotBlank(nativeId)) {

			deviceInfoChangeLog.append("[nativeId]").append(dbUserMbrDevice.getNativeID()).append("->")
					.append(nativeId);
			userMbrDevice.setNativeID(nativeId);

		}

		// gmail은 ""으로 초기화 시킬 수 있다.
		if (deviceAccount != null && !StringUtils.equals(deviceAccount, dbUserMbrDevice.getDeviceAccount())) {

			deviceInfoChangeLog.append("[deviceAccount]").append(dbUserMbrDevice.getDeviceAccount()).append("->")
					.append(deviceAccount);
			userMbrDevice.setDeviceAccount(deviceAccount);

		}

		if (StringUtils.isNotBlank(deviceTelecom)
				&& !StringUtils.equals(deviceTelecom, dbUserMbrDevice.getDeviceTelecom())) {

			deviceInfoChangeLog.append("[deviceTelecom]").append(dbUserMbrDevice.getDeviceTelecom()).append("->")
					.append(deviceTelecom);
			userMbrDevice.setDeviceTelecom(deviceTelecom);

		}

		/*if (StringUtils.isNotBlank(deviceNickName)) {

			deviceInfoChangeLog.append("[deviceNickName]").append(dbUserMbrDevice.getDeviceNickName()).append("->")
					.append(deviceNickName);
			userMbrDevice.setDeviceNickName(deviceNickName);

		}*/

		if (StringUtils.isNotBlank(isPrimary)) {

			deviceInfoChangeLog.append("[isPrimary]").append(dbUserMbrDevice.getIsPrimary()).append("->")
					.append(isPrimary);
			userMbrDevice.setIsPrimary(isPrimary);

		}

		if (StringUtils.isNotBlank(isRecvSms)) {

			deviceInfoChangeLog.append("[isRecvSms]").append(dbUserMbrDevice.getIsRecvSMS()).append("->")
					.append(isRecvSms);
			userMbrDevice.setIsRecvSMS(isRecvSms);

		}

		if (StringUtils.isNotBlank(svcMangNum)) {

			deviceInfoChangeLog.append("[svcMangNum]").append(dbUserMbrDevice.getSvcMangNum()).append("->")
					.append(svcMangNum);
			userMbrDevice.setSvcMangNum(svcMangNum);

		}

		if (StringUtils.isNotBlank(joinId)) {

			deviceInfoChangeLog.append("[joinId]").append(dbUserMbrDevice.getJoinId()).append("->").append(joinId);
			userMbrDevice.setJoinId(joinId);

		}

		/* 휴대기기 부가정보 */
		userMbrDevice.setUserMbrDeviceDetail(DeviceUtil.getConverterUserMbrDeviceDetailList(deviceInfo));
		deviceInfoChangeLog.append("[deviceExtraInfo]").append(deviceInfo.getDeviceExtraInfoList());

		/* 휴대기기 변경 이력 코드 */
		userMbrDevice.setChangeCaseCode(MemberConstants.DEVICE_CHANGE_TYPE_USER_SELECT);

		/* 기기정보 업데이트 */
		CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
		createDeviceReq.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));
		createDeviceReq.setUserKey(dbUserMbrDevice.getUserKey());
		//createDeviceReq.setIsNew("N");
		createDeviceReq.setUserMbrDevice(userMbrDevice);
		CreateDeviceResponse createDeviceRes = this.deviceSCI.createDevice(createDeviceReq);

		LOGGER.info("{} update device field : {}", dbUserMbrDevice.getDeviceID(), deviceInfoChangeLog.toString());

		return createDeviceRes.getDeviceKey();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.DeviceService# updateDeviceInfoForLogin
	 * (com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader,
	 * com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo,
	 * com.skplanet.storeplatform.sac.client.member.vo.common.DeviceInfo, java.lang.String)
	 */
	@Override
	public String modDeviceInfoForLogin(SacRequestHeader requestHeader, DeviceInfo deviceInfo, DeviceInfo dbDeviceInfo,
			String version) {

		String DeviceChangeCaseCode = MemberConstants.DEVICE_CHANGE_TYPE_USER_SELECT;
		String oDeviceTelecom = deviceInfo.getDeviceTelecom(); // request 통신사 코드
		StringBuffer deviceInfoChangeLog = new StringBuffer();

		UserMbrDevice userMbrDevice = new UserMbrDevice();
		userMbrDevice.setUserKey(dbDeviceInfo.getUserKey());
		userMbrDevice.setDeviceKey(dbDeviceInfo.getDeviceKey());
		userMbrDevice.setDeviceID(deviceInfo.getDeviceId());

		// 부가정보 등록시 셋팅할 값들
		//deviceInfo.setTenantId(requestHeader.getTenantHeader().getTenantId());
		deviceInfo.setUserKey(dbDeviceInfo.getUserKey());
		deviceInfo.setDeviceKey(dbDeviceInfo.getDeviceKey());

		/* device header로 넘어온 정보 셋팅 */
		String deviceModelNo = requestHeader.getDeviceHeader().getModel(); // 모델코드
		String osVersion = requestHeader.getDeviceHeader().getOs(); // OS버젼
		String svcVersion = requestHeader.getDeviceHeader().getSvc(); // SC버젼
		deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_OSVERSION,
                osVersion.substring(osVersion.lastIndexOf("/") + 1, osVersion.length()),
                deviceInfo.getDeviceExtraInfoList())); // osVersion 휴대기기
		// 부가속성에 셋팅
		deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_SCVERSION,
                svcVersion.substring(svcVersion.lastIndexOf("/") + 1, svcVersion.length()),
                deviceInfo.getDeviceExtraInfoList())); // svcVersion
		// 휴대기기 부가속성에
		// 셋팅

		boolean isSearchSvcMangNo = false;
		/* 통신사가 자사로 들어왔는데 SKT서비스관리번호가 존재하지 않을 때 보정처리 */
		if (StringUtils.equals(deviceInfo.getDeviceTelecom(), MemberConstants.DEVICE_TELECOM_SKT)
				&& StringUtils.isBlank(dbDeviceInfo.getSvcMangNum())) {
			isSearchSvcMangNo = true;
		}

		/* 주요정보(UACD, OMDUACD, 미지원단말처리, SKT 서비스관리번호) 조회 */
		MajorDeviceInfo majorDeviceInfo = this.commService.getDeviceBaseInfo(deviceModelNo,
				deviceInfo.getDeviceTelecom(), deviceInfo.getDeviceId(), deviceInfo.getDeviceIdType(),
				isSearchSvcMangNo);

		if (isSearchSvcMangNo) {
			deviceInfoChangeLog.append("[svcMangNum]").append(dbDeviceInfo.getSvcMangNum()).append("->")
					.append(majorDeviceInfo.getSvcMangNum());
			userMbrDevice.setSvcMangNum(majorDeviceInfo.getSvcMangNum());
		}

		/* 기기정보 필드 */
		String deviceTelecom = null; // 통신사코드
		String uacd = null; // UACD
		String deviceNickName = null; // 모델닉네임
		String nativeId = deviceInfo.getNativeId(); // nativeId(imei)
		String deviceAccount = DeviceUtil.getGmailStr(deviceInfo.getDeviceAccount()); // gmailAddr
		String rooting = DeviceUtil.getDeviceExtraValue(MemberConstants.DEVICE_EXTRA_ROOTING_YN,
				deviceInfo.getDeviceExtraInfoList()); // rooting 여부

		if (StringUtils.isNotBlank(deviceModelNo) && !this.commService.isDefaultDeviceModel(deviceModelNo)) {

			/* 주요정보 셋팅, 모델이 존재하지 않은경우 미지원단말처리됨 */
			deviceModelNo = majorDeviceInfo.getDeviceModelNo();
			deviceTelecom = majorDeviceInfo.getDeviceTelecom();
			if (StringUtils.equals(majorDeviceInfo.getDeviceNickName(), MemberConstants.NOT_SUPPORT_HP_MODEL_NM)) {
				deviceNickName = majorDeviceInfo.getDeviceNickName();
			} else {

				/* 정상단말로 로그인시 DB에 단말정보가 미지원단말정보이면 디폴트 모델명을 닉네임에 셋팅 */
				/*if (StringUtils.equals(dbDeviceInfo.getDeviceNickName(), MemberConstants.NOT_SUPPORT_HP_MODEL_NM)
						&& StringUtils.equals(dbDeviceInfo.getDeviceModelNo(), MemberConstants.NOT_SUPPORT_HP_MODEL_CD)) {
					deviceNickName = majorDeviceInfo.getDeviceNickName();
				}*/
			}
			uacd = majorDeviceInfo.getUacd();
			deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_UACD,
					majorDeviceInfo.getUacd() == null ? "" : majorDeviceInfo.getUacd(),
					deviceInfo.getDeviceExtraInfoList())); // UACD
			deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_OMDUACD,
					majorDeviceInfo.getOmdUacd() == null ? "" : majorDeviceInfo.getOmdUacd(),
					deviceInfo.getDeviceExtraInfoList())); // OMDUACD

		} else {
			LOGGER.info("{} deviceHeader model 정보 없거나 default모델 : {}", deviceInfo.getDeviceId(), deviceModelNo);
			deviceModelNo = null; // 디폴트 모델명이 넘어온경우도 있으므로 초기화 하여 업데이트 하지 않게 함

			// DB에 저장된 단말정보가 미지원단말이 아닌경우는 통신사정보를 업데이트 한다.
			/*if (!StringUtils.equals(dbDeviceInfo.getDeviceModelNo(), MemberConstants.NOT_SUPPORT_HP_MODEL_CD)
					&& !StringUtils.equals(dbDeviceInfo.getDeviceNickName(), MemberConstants.NOT_SUPPORT_HP_MODEL_NM)) {
				deviceTelecom = majorDeviceInfo.getDeviceTelecom();
			}*/
		}

		if (StringUtils.isNotBlank(deviceModelNo)
				&& !StringUtils.equals(deviceModelNo, dbDeviceInfo.getDeviceModelNo())) {

			if (StringUtils.equals(MemberConstants.DEVICE_TELECOM_SKT, oDeviceTelecom)) {

				// // 폰정보 조회 (deviceModelNo)
				// Device device = this.commService.getPhoneInfo(deviceModelNo);
				//
				// if (device != null) {
				// // OMD 단말이 아닐 경우만
				// if (!StringUtils.equals(MemberConstants.DEVICE_TELECOM_OMD, device.getCmntCompCd())) {
				//
				// DeviceCompareEcReq deviceCompareEcReq = new DeviceCompareEcReq();
				// deviceCompareEcReq.setUserMdn(deviceInfo.getDeviceId());
				// DeviceCompareEcRes deviceCompareEcRes = this.idpSCI.deviceCompare(deviceCompareEcReq);
				//
				// String idpModelId = deviceCompareEcRes.getModelId();
				//
				// // 특정 단말 모델 임시 변경 처리 2013.05.02 watermin
				// if (StringUtils.equals(idpModelId, "SSNU")) { // SHW-M200K->SHW-M200S
				// idpModelId = "SSNL";
				// } else if (StringUtils.equals(idpModelId, "SP05")) { // SHW-M420K->SHW-M420S
				// idpModelId = "SSO0";
				// }
				//
				// // uacd 부가속성 추가
				// deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_UACD,
				// idpModelId, deviceInfo));
				//
				// LOGGER.info("[change uacd] {}", idpModelId);
				// }
				// }

				if (StringUtils.equals(uacd, "SSNU")) { // SHW-M200K->SHW-M200S
					uacd = "SSNL";
				} else if (StringUtils.equals(uacd, "SP05")) { // SHW-M420K->SHW-M420S
					uacd = "SSO0";
				}
				deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_UACD,
						uacd, deviceInfo.getDeviceExtraInfoList()));
			}

			deviceInfoChangeLog.append("[deviceModelNo]").append(dbDeviceInfo.getDeviceModelNo()).append("->")
					.append(deviceModelNo);
			userMbrDevice.setDeviceModelNo(deviceModelNo);

		}

		if (StringUtils.equals(version, "v1")) {

			if (StringUtils.isNotBlank(nativeId)) {

				if (StringUtils.equals(MemberConstants.DEVICE_TELECOM_SKT, oDeviceTelecom)) {

					if (!StringUtils.equals(nativeId, dbDeviceInfo.getNativeId())) {
						/* ICAS IMEI 비교 */
						if (StringUtils.equals(nativeId, this.getIcasImei(deviceInfo.getDeviceId()))) {

							deviceInfoChangeLog.append("[nativeId]").append(dbDeviceInfo.getNativeId()).append("->")
									.append(nativeId);
							userMbrDevice.setNativeID(nativeId);
							DeviceChangeCaseCode = MemberConstants.DEVICE_CHANGE_TYPE_IMEI_CHANGE;
						} else {
							throw new StorePlatformException("SAC_MEM_1503");
						}

					}
				} else { // 타사

					// nativeId 비교 여부
					String isNativeIdAuth = deviceInfo.getIsNativeIdAuth();

					if (StringUtils.isBlank(dbDeviceInfo.getNativeId())) { // DB에 없는 경우만 최초 수집

						deviceInfoChangeLog.append("[nativeId]").append(dbDeviceInfo.getNativeId()).append("->")
								.append(nativeId);
						userMbrDevice.setNativeID(nativeId);

					} else if (StringUtils.equals(rooting, "Y") || StringUtils.equals(isNativeIdAuth, "Y")) { // isNativeIdAuth="Y"인경우
																											  // 루팅여부관계없이비교

						if (!nativeId.equals(dbDeviceInfo.getNativeId())) {
							throw new StorePlatformException("SAC_MEM_1504");
						}

					}
				}

			}

		} else { // v2

			/* DB에 IMEI가 있으면 비교 */
			if (!this.isEqualsLoginDevice(deviceInfo.getDeviceId(), nativeId, dbDeviceInfo.getNativeId(),
					MemberConstants.LOGIN_DEVICE_EQUALS_NATIVE_ID)) {

				if (StringUtils.equals(oDeviceTelecom, MemberConstants.DEVICE_TELECOM_SKT)) {

					/* ICAS IMEI 비교 */
					if (StringUtils.equals(nativeId, this.getIcasImei(deviceInfo.getDeviceId()))) {

						if (!StringUtils.equals(nativeId, dbDeviceInfo.getNativeId())) {
							deviceInfoChangeLog.append("[nativeId]").append(dbDeviceInfo.getNativeId()).append("->")
									.append(nativeId);
							userMbrDevice.setNativeID(nativeId);
							DeviceChangeCaseCode = MemberConstants.DEVICE_CHANGE_TYPE_IMEI_CHANGE;
						}

					} else {
						throw new StorePlatformException("SAC_MEM_1503");
					}

				} else { // 타사는 IMEI가 다르면 에러

					throw new StorePlatformException("SAC_MEM_1504");

				}

			} else if (StringUtils.isBlank(dbDeviceInfo.getNativeId())) { // DB에 IMEI가 없은경우 최초 수집

				if (StringUtils.isNotBlank(nativeId)) {

					if (StringUtils.equals(MemberConstants.DEVICE_TELECOM_SKT, oDeviceTelecom)) {

						/* ICAS IMEI 비교 */
						if (StringUtils.equals(nativeId, this.getIcasImei(deviceInfo.getDeviceId()))) {
							deviceInfoChangeLog.append("[nativeId]").append(dbDeviceInfo.getNativeId()).append("->")
									.append(nativeId);
							userMbrDevice.setNativeID(nativeId);
						} else {
							throw new StorePlatformException("SAC_MEM_1503");
						}

					} else {

						deviceInfoChangeLog.append("[nativeId]").append(dbDeviceInfo.getNativeId()).append("->")
								.append(nativeId);
						userMbrDevice.setNativeID(nativeId);

					}

				}
			}

		}

		// gmail은 ""으로 초기화 시킬 수 있다.
		if (!(StringUtils.isBlank(deviceAccount) && StringUtils.isBlank(dbDeviceInfo.getDeviceAccount()))
				&& !StringUtils.equals(deviceAccount, dbDeviceInfo.getDeviceAccount())) {
			deviceInfoChangeLog.append("[deviceAccount]").append(dbDeviceInfo.getDeviceAccount()).append("->")
					.append(deviceAccount);
			userMbrDevice.setDeviceAccount(deviceAccount);

		}

		if (StringUtils.isNotBlank(deviceTelecom)
				&& !StringUtils.equals(deviceTelecom, dbDeviceInfo.getDeviceTelecom())) {

			deviceInfoChangeLog.append("[deviceTelecom]").append(dbDeviceInfo.getDeviceTelecom()).append("->")
					.append(deviceTelecom);
			userMbrDevice.setDeviceTelecom(deviceTelecom);

		}

		/*if (StringUtils.isNotBlank(deviceNickName)
				&& !StringUtils.equals(deviceNickName, dbDeviceInfo.getDeviceNickName())) {
			deviceInfoChangeLog.append("[deviceNickName]").append(dbDeviceInfo.getDeviceNickName()).append("->")
					.append(deviceNickName);
			userMbrDevice.setDeviceNickName(deviceNickName);
		}*/

		/* 휴대기기 부가정보 세팅 */
		if (deviceInfo.getDeviceExtraInfoList() != null && deviceInfo.getDeviceExtraInfoList().size() > 0) {

			List<UserMbrDeviceDetail> userMbrDeviceDetailList = new ArrayList<UserMbrDeviceDetail>();
			for (DeviceExtraInfo deviceExtraInfo : deviceInfo.getDeviceExtraInfoList()) {
				if (StringUtils.isNotBlank(deviceExtraInfo.getExtraProfile())
						&& StringUtils.isNotBlank(deviceExtraInfo.getExtraProfileValue())) {
					boolean isExsitExtra = false; // 휴대기기 부가속성 DB존재여부
					for (DeviceExtraInfo dbDeviceExtraInfo : dbDeviceInfo.getDeviceExtraInfoList()) {
						if (StringUtils.equals(deviceExtraInfo.getExtraProfile(), dbDeviceExtraInfo.getExtraProfile())) {
							isExsitExtra = true;
							if (!StringUtils.equals(deviceExtraInfo.getExtraProfileValue(),
									dbDeviceExtraInfo.getExtraProfileValue())) { // request 와 DB값이 다른경우만 셋팅
								UserMbrDeviceDetail userMbrDeviceDetail = new UserMbrDeviceDetail();
								userMbrDeviceDetail.setExtraProfile(deviceExtraInfo.getExtraProfile());
								userMbrDeviceDetail.setExtraProfileValue(deviceExtraInfo.getExtraProfileValue());
								userMbrDeviceDetail.setDeviceKey(deviceInfo.getDeviceKey());
								//userMbrDeviceDetail.setTenantID(deviceInfo.getTenantId());
								userMbrDeviceDetail.setUserKey(deviceInfo.getUserKey());
								userMbrDeviceDetailList.add(userMbrDeviceDetail);
								deviceInfoChangeLog.append("[").append(deviceExtraInfo.getExtraProfile()).append("]")
										.append(dbDeviceExtraInfo.getExtraProfileValue()).append("->")
										.append(deviceExtraInfo.getExtraProfileValue());
							}
							break;
						}
					}
					if (!isExsitExtra) { // DB에 존재하지 않은 휴대기기부가속성은 신규로 삽입
						UserMbrDeviceDetail userMbrDeviceDetail = new UserMbrDeviceDetail();
						userMbrDeviceDetail.setExtraProfile(deviceExtraInfo.getExtraProfile());
						userMbrDeviceDetail.setExtraProfileValue(deviceExtraInfo.getExtraProfileValue());
						userMbrDeviceDetail.setDeviceKey(deviceInfo.getDeviceKey());
						//userMbrDeviceDetail.setTenantID(deviceInfo.getTenantId());
						userMbrDeviceDetail.setUserKey(deviceInfo.getUserKey());
						userMbrDeviceDetailList.add(userMbrDeviceDetail);
						deviceInfoChangeLog.append("[").append(deviceExtraInfo.getExtraProfile()).append("]")
								.append("null").append("->").append(deviceExtraInfo.getExtraProfileValue());
					}
				}
			}
			userMbrDevice.setUserMbrDeviceDetail(userMbrDeviceDetailList);
		}

		if (StringUtils.isNotBlank(deviceInfoChangeLog.toString())) { // 변경내역이 있는경우만 휴대기기 수정 API 호출

			/* 휴대기기 변경 이력 코드 */
			userMbrDevice.setChangeCaseCode(DeviceChangeCaseCode);

			/* 기기정보 업데이트 */
			CreateDeviceRequest createDeviceReq = new CreateDeviceRequest();
			createDeviceReq.setCommonRequest(this.commService.getSCCommonRequest(requestHeader));
			createDeviceReq.setUserKey(dbDeviceInfo.getUserKey());
			//createDeviceReq.setIsNew("N");
			createDeviceReq.setUserMbrDevice(userMbrDevice);
			CreateDeviceResponse createDeviceRes = this.deviceSCI.createDevice(createDeviceReq);

			LOGGER.info("{} Login update device field : {}", deviceInfo.getDeviceId(), deviceInfoChangeLog.toString());
			return createDeviceRes.getDeviceKey();

		} else {
			LOGGER.info("{} No Login update device field", deviceInfo.getDeviceId());
			return dbDeviceInfo.getDeviceKey();
		}

	}

	/**
	 * SC회원정보 조회.
	 * 
	 * @param requestHeader
	 *            SacRequestHeader
	 * @param userKey
	 *            String
	 * @return UserInfo
	 */
	public UserInfo srhUser(SacRequestHeader requestHeader, String userKey) {

		DetailReq detailReq = new DetailReq();
		detailReq.setUserKey(userKey);
		SearchExtentReq searchExtent = new SearchExtentReq();
		searchExtent.setUserInfoYn(MemberConstants.USE_Y);
		detailReq.setSearchExtent(searchExtent);
		DetailV2Res detailRes = null;

		try {
			detailRes = this.userSearchService.detailV2(requestHeader, detailReq);
			return detailRes.getUserInfo();
		} catch (StorePlatformException ex) {
			if (ex.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_DATA)
					|| ex.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_USERKEY)) {
				throw new StorePlatformException("SAC_MEM_0003", "userKey", userKey);
			} else {
				throw ex;
			}
		}
	}

	/**
	 * 휴대기기 주요정보 셋팅.
	 * 
	 * @param deviceInfo
	 *            DeviceInfo
	 * @return DeviceInfo
	 */
	public DeviceInfo getDeviceMajorInfo(DeviceInfo deviceInfo) {

		MajorDeviceInfo majorDeviceInfo = this.commService.getDeviceBaseInfo(deviceInfo.getDeviceModelNo(),
				deviceInfo.getDeviceTelecom(), deviceInfo.getMdn(), deviceInfo.getDeviceIdType(), true);

		deviceInfo.setDeviceModelNo(majorDeviceInfo.getDeviceModelNo());
		deviceInfo.setDeviceTelecom(majorDeviceInfo.getDeviceTelecom());
		deviceInfo.setSvcMangNum(majorDeviceInfo.getSvcMangNum());

		deviceInfo
				.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_UACD,
						majorDeviceInfo.getUacd() == null ? "" : majorDeviceInfo.getUacd(),
						deviceInfo.getDeviceExtraInfoList()));

		deviceInfo.setDeviceExtraInfoList(DeviceUtil.setDeviceExtraValue(MemberConstants.DEVICE_EXTRA_OMDUACD,
				majorDeviceInfo.getOmdUacd() == null ? "" : majorDeviceInfo.getOmdUacd(),
                deviceInfo.getDeviceExtraInfoList()));

		return deviceInfo;
	}

	/**
	 * 대표 단말 조회.
	 * @param requestHeader
	 * @param req
	 * @return DetailRepresentationDeviceRes
	 */
	@Override
	public DetailRepresentationDeviceRes detailRepresentationDeviceRes(SacRequestHeader requestHeader,
																	   DetailRepresentationDeviceReq req) {

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());

		DetailRepresentationDeviceRes res = new DetailRepresentationDeviceRes();
		ListDeviceRes listRes = new ListDeviceRes();
		ListDeviceReq listReq = new ListDeviceReq();

		if (req.getUserKey() != null) {
			listReq.setUserKey(req.getUserKey());
			listReq.setIsMainDevice("Y");

			try {

				listRes = this.listDevice(requestHeader, listReq);

			} catch (StorePlatformException ex) {
				if (ex.getErrorInfo().getCode().equals(MemberConstants.SC_ERROR_NO_DATA)) {
					throw new StorePlatformException("SAC_MEM_0002", "휴대기기");
				}
			}

		}

		if (listRes.getDeviceInfoList() != null) {
			for (DeviceInfo info : listRes.getDeviceInfoList()) {
				DeviceInfo addData = new DeviceInfo();
				List<DeviceExtraInfo> listExtraInfo = new ArrayList<DeviceExtraInfo>();

				addData.setDeviceKey(StringUtil.setTrim(info.getDeviceKey()));
				if ( StringUtils.isNotBlank(info.getMdn()) ){
					addData.setDeviceId(StringUtil.setTrim(info.getMdn()));
				} else {
					addData.setDeviceId(StringUtil.setTrim(info.getDeviceId()));
				}
				addData.setDeviceModelNo(StringUtil.setTrim(info.getDeviceModelNo()));
				addData.setSvcMangNum(StringUtil.setTrim(info.getSvcMangNum()));
				addData.setDeviceTelecom(StringUtil.setTrim(info.getDeviceTelecom()));
				addData.setIsPrimary(StringUtil.setTrim(info.getIsPrimary()));
				addData.setIsRecvSms(StringUtil.setTrim(info.getIsRecvSms()));
				addData.setNativeId(StringUtil.setTrim(info.getNativeId()));
				addData.setDeviceAccount(StringUtil.setTrim(info.getDeviceAccount()));
				addData.setJoinId(StringUtil.setTrim(info.getJoinId()));

				for (DeviceExtraInfo extraInfo : info.getDeviceExtraInfoList()) {
					DeviceExtraInfo addExtraInfo = new DeviceExtraInfo();
					addExtraInfo.setExtraProfile(extraInfo.getExtraProfile());
					addExtraInfo.setExtraProfileValue(extraInfo.getExtraProfileValue());
					listExtraInfo.add(addExtraInfo);
				}

				addData.setDeviceExtraInfoList(listExtraInfo);

				res.setDeviceInfo(addData);
			}
		} else {
			throw new StorePlatformException("SAC_MEM_0002", "휴대기기");
		}

		return res;
	}

	/**
	 * 대표 단말 설정.
	 * @param requestHeader
	 * @param req
	 * @return SetMainDeviceRes
	 */
	@Override
	public SetMainDeviceRes modRepresentationDevice(SacRequestHeader requestHeader, SetMainDeviceReq req) {

		SetMainDeviceRequest setMainDeviceRequest = new SetMainDeviceRequest();
		SetMainDeviceRes setMainDeviceRes = new SetMainDeviceRes();

		/** 1. 헤더 정보 셋팅 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());
		setMainDeviceRequest.setCommonRequest(commonRequest);

		/** 2. userKey, deviceKey 회원존재여부 체크 */
		ExistReq existReq = new ExistReq();
		existReq.setUserKey(req.getUserKey());

		ExistRes existRes = this.userSearchService.exist(requestHeader, existReq);

		/** 3. 회원이라면 userKey 셋팅 */
		if (existRes.getUserKey() != null) {
			ListDeviceReq deviceReq = new ListDeviceReq();
			deviceReq.setUserKey(req.getUserKey());
			/** 4. req에 deviceId가 있을 경우 mdn 여부를 판단 후 휴대기기 존재여부 확인 */
			if (req.getDeviceId() != null) {
				String opmdMdn = this.commService.getOpmdMdnInfo(req.getDeviceId());
				req.setDeviceId(opmdMdn);

				if( ValidationCheckUtils.isDeviceId(req.getDeviceId())){
                    deviceReq.setDeviceId(req.getDeviceId());
				}else{
                    deviceReq.setMdn(req.getDeviceId());
				}

				ListDeviceRes deviceRes = this.listDevice(requestHeader, deviceReq);

				if (deviceRes.getDeviceInfoList() != null) {
					String deviceKey = deviceRes.getDeviceInfoList().get(0).getDeviceKey();
					req.setDeviceKey(deviceKey);
				}
			}

			/** 휴대기기가 존재하면 조회된 deviceKey로 대표기기 설정 */
			setMainDeviceRequest.setDeviceKey(req.getDeviceKey());
			setMainDeviceRequest.setUserKey(req.getUserKey());

			SetMainDeviceResponse res = this.deviceSCI.setMainDevice(setMainDeviceRequest);

			setMainDeviceRes.setDeviceKey(res.getDeviceKey());
		}

		return setMainDeviceRes;
	}

	/**
	 * 휴대기기 삭제
	 */
	@Override
	public RemoveDeviceRes remDevice(SacRequestHeader requestHeader, RemoveDeviceReq req) {

		/* 헤더 정보 셋팅 */
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());

		List<String> removeKeyList = new ArrayList<String>();
		for (RemoveDeviceListSacReq id : req.getDeviceIdList()) {
            boolean isDeviceId = true;

            if(!ValidationCheckUtils.isDeviceId(id.getDeviceId())){
                /**
                 * 모번호 조회 (989 일 경우만)
                 */
                id.setDeviceId(this.commService.getOpmdMdnInfo(id.getDeviceId()));
                isDeviceId = false;
            }

            // 회원정보조회
            DetailReq detailReq = new DetailReq();
            detailReq.setDeviceId(id.getDeviceId());
            SearchExtentReq searchExtent = new SearchExtentReq();
            searchExtent.setUserInfoYn(MemberConstants.USE_Y);
            searchExtent.setDeviceInfoYn(MemberConstants.USE_Y);
            detailReq.setSearchExtent(searchExtent);

            DetailV2Res detailRes = this.userSearchService.detailV2(requestHeader, detailReq);

			if (StringUtils.equals(detailRes.getUserInfo().getIsDormant(), MemberConstants.USE_Y)) {
				throw new StorePlatformException("SAC_MEM_0006");
			}

			if (detailRes.getDeviceInfoList() == null &&
                    detailRes.getDeviceInfoList().size() <= 0) {
				throw new StorePlatformException("SAC_MEM_0002", "휴대기기");
			}

			/* 삭제 가능여부 판단 */
            Integer deviceCount = Integer.parseInt(StringUtils.isNotEmpty(detailRes.getUserInfo().getDeviceCount()) ? detailRes.getUserInfo().getDeviceCount() : "0");

			if (detailRes.getUserInfo().getUserType().equals(MemberConstants.USER_TYPE_MOBILE)) {
				/* 모바일회원 단말삭제 불가 */
				throw new StorePlatformException("SAC_MEM_1511");
			}else {
                /* 단말 1개이상 보유이고, 삭제할 단말이 대표기기인 경우 에러 */
                String isPrimary = detailRes.getDeviceInfoList().get(0).getIsPrimary();
                if (deviceCount > 1 && isPrimary.equals(MemberConstants.USE_Y)) {
                    throw new StorePlatformException("SAC_MEM_1510");
                }
            }

            /* MQ 연동 : SC휴대기기 삭제를 하면 정보조회 할수 없어서 미리 처리함. */
            RemoveDeviceAmqpSacReq mqInfo = new RemoveDeviceAmqpSacReq();

            try {
                mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
                mqInfo.setUserKey(detailRes.getUserKey());
                mqInfo.setDeviceKey(detailRes.getDeviceInfoList().get(0).getDeviceKey());
                if(isDeviceId){
                    mqInfo.setDeviceId(detailRes.getDeviceInfoList().get(0).getDeviceId());
                }else {
                    mqInfo.setDeviceId(detailRes.getDeviceInfoList().get(0).getMdn());
                }
                mqInfo.setSvcMangNo(detailRes.getDeviceInfoList().get(0).getSvcMangNum());
                mqInfo.setChgCaseCd(MemberConstants.GAMECENTER_WORK_CD_MOBILENUMBER_DELETE);

                this.memberDelDeviceAmqpTemplate.convertAndSend(mqInfo);
            } catch (AmqpException ex) {
                LOGGER.info("MQ process fail {}", mqInfo);
            }

			removeKeyList.add(detailRes.getDeviceInfoList().get(0).getDeviceKey());
		}

		/* SC 휴대기기 삭제요청 */
		RemoveDeviceRequest removeDeviceRequest = new RemoveDeviceRequest();
		removeDeviceRequest.setCommonRequest(commonRequest);
		removeDeviceRequest.setUserKey(req.getUserKey());
		removeDeviceRequest.setDeviceKey(removeKeyList);

		RemoveDeviceResponse removeDeviceResponse = this.deviceSCI.removeDevice(removeDeviceRequest);

		RemoveDeviceRes removeDeviceRes = new RemoveDeviceRes();
		List<RemoveDeviceListSacRes> resDeviceKeyList = new ArrayList<RemoveDeviceListSacRes>();
		for (String str : removeKeyList) {
			RemoveDeviceListSacRes removeListRes = new RemoveDeviceListSacRes();
			removeListRes.setDeviceKey(str);

			resDeviceKeyList.add(removeListRes);
		}
		removeDeviceRes.setDeviceKeyList(resDeviceKeyList);
		removeDeviceRes.setRemoveDeviceCount(String.valueOf(removeDeviceResponse.getDelDeviceCount()));

		return removeDeviceRes;
	}

	/**
	 * 단말 AOM 지원 여부
	 */
	@Override
	public SupportAomRes getSupportAom(SacRequestHeader sacHeader, SupportAomReq req) {

		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());
		commonRequest.setTenantID(sacHeader.getTenantHeader().getTenantId());

		String deviceId = StringUtil.nvl(req.getDeviceId(), "");
		String userKey = StringUtil.nvl(req.getUserKey(), "");

		req.setDeviceId(deviceId);
		req.setUserKey(userKey);

		/**
		 * 모번호 조회 (989 일 경우만)
		 */
		ListDeviceRes listDeviceRes = new ListDeviceRes();
		if (!req.getDeviceId().equals("")) {
			String opmdMdn = this.commService.getOpmdMdnInfo(req.getDeviceId());
			req.setDeviceId(opmdMdn);

			DetailReq detailReq = new DetailReq();
			detailReq.setDeviceId(req.getDeviceId());

			DetailRes detailRes = this.userSearchService.srhUser(detailReq, sacHeader);
			UserInfo deviceIdUser = detailRes.getUserInfo();

			ListDeviceReq listDeviceReq = new ListDeviceReq();
			listDeviceReq.setUserKey(deviceIdUser.getUserKey());
			listDeviceReq.setDeviceId(req.getDeviceId());
			listDeviceReq.setIsMainDevice("N");

			listDeviceRes = this.listDevice(sacHeader, listDeviceReq);

		}

		/* Req : userKey 정상적인 key인지 회원정보 호출하여 확인 */
		else if (!req.getUserKey().equals("")) {

			// 대표단말 조회
			DetailRepresentationDeviceReq detailRepresentationDeviceReq = new DetailRepresentationDeviceReq();
			detailRepresentationDeviceReq.setUserKey(req.getUserKey());

			DetailRepresentationDeviceRes detailRepresentationDeviceRes = this.detailRepresentationDeviceRes(sacHeader,
					detailRepresentationDeviceReq);

			ListDeviceReq listDeviceReq = new ListDeviceReq();
			listDeviceReq.setUserKey(req.getUserKey());
			listDeviceReq.setDeviceId(detailRepresentationDeviceRes.getDeviceInfo().getDeviceId());
			listDeviceReq.setIsMainDevice("Y");

			listDeviceRes = this.listDevice(sacHeader, listDeviceReq);

		}

		/* PhoneInfo 조회 */
		SupportAomRes res = new SupportAomRes();
		if (listDeviceRes.getDeviceInfoList() != null) {

			Device device = this.commService.getPhoneInfo(listDeviceRes.getDeviceInfoList().get(0).getDeviceModelNo());

			res.setIsAomSupport(device.getAomSprtYn());

		}

		return res;
	}

	/**
	 * <pre>
	 * 기기변경 이력 조회.
	 * </pre>
	 * 
	 * @param sacHeader
	 *            SacRequestHeader
	 * @param request
	 *            ChangedDeviceHistorySacReq
	 * @return ChangedDeviceHistoryRes
	 */
	@Override
	public ChangedDeviceHistorySacRes srhChangedDeviceHistory(SacRequestHeader sacHeader,
			ChangedDeviceHistorySacReq request) {
		// 공통 파라미터 셋팅
		CommonRequest commonRequest = new CommonRequest();
		commonRequest.setSystemID(sacHeader.getTenantHeader().getSystemId());

		String errorValue = "userKey 또는 ";

		// 필수파라미터 미입력.
		if (request.getDeviceId() == null || "".equals(request.getDeviceId())) {
			errorValue = StringUtil.capitalize(errorValue + "deviceKey");
		} else {
			errorValue = StringUtil.capitalize(errorValue + "deviceId");
		}

		// SC 회원 기기변경이력 조회 기능 호출.
		SearchChangedDeviceRequest searchChangedDeviceRequest = new SearchChangedDeviceRequest();
		searchChangedDeviceRequest.setUserKey(request.getUserKey());
		searchChangedDeviceRequest.setDeviceKey(request.getDeviceKey());
		searchChangedDeviceRequest.setDeviceID(request.getDeviceId());
		searchChangedDeviceRequest.setCommonRequest(commonRequest);

		LOGGER.debug("[DeviceSCIController.searchChangedDeviceHistory] SC Request userSCI.searchChangedDevice : {}",
				searchChangedDeviceRequest);
		SearchChangedDeviceResponse searchChangedDeviceResponse = this.userSCI
				.searchChangedDevice(searchChangedDeviceRequest);

		ChangedDeviceHistorySacRes changedDeviceHistorySacRes = new ChangedDeviceHistorySacRes();

		if (searchChangedDeviceResponse != null && searchChangedDeviceResponse.getChangedDeviceLog() != null
				&& StringUtils.isNotBlank(searchChangedDeviceResponse.getChangedDeviceLog().getDeviceKey())) {
			LOGGER.debug(
					"[DeviceSCIController.searchChangedDeviceHistory] SC Response userSCI.searchChangedDevice : {}",
					searchChangedDeviceResponse.getChangedDeviceLog());
			changedDeviceHistorySacRes.setDeviceKey(searchChangedDeviceResponse.getChangedDeviceLog().getDeviceKey());
			changedDeviceHistorySacRes.setIsChanged(searchChangedDeviceResponse.getChangedDeviceLog().getIsChanged());
		} else {
			// Response DeviceKey값이 null일 경우, UserKey or DeviceId or DeviceKey 불량
			throw new StorePlatformException("SAC_MEM_0003", errorValue, "오류");
		}
		return changedDeviceHistorySacRes;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.DeviceService#getIcasImei (java.lang.String)
	 */
	@Override
	public String getIcasImei(String deviceId) {

		String icasImei = null;

		if (!StringUtils.equals(this.commService.getMappingInfo(deviceId, "mdn").getMvnoCD(), "0")) { // MVNO

			GetMvnoEcRes mvnoRes = this.commService.getMvService(deviceId);
			icasImei = mvnoRes.getImeiNum();

		} else {

			GetCustomerEcRes costomerRes = this.commService.getCustomer(deviceId);
			icasImei = costomerRes.getImeiNum();

		}

		LOGGER.info("{} CSP 연동 imei : {}", deviceId, icasImei);

		return icasImei;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skplanet.storeplatform.sac.member.user.service.DeviceService# isEqualsLoginDevice(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isEqualsLoginDevice(String deviceId, String reqVal, String dbVal, String equalsType) {

		// 통신사 : DB값이 NSH, DB값이 없음, Request 값과 DB값이 일치 한경우 일치하다고 판단
		// IMEI : DB값이 없음, Request 값과 DB값이 일치 한경우 일치하다고 판단
		// GMAIL : Request 값과 DB값이 모두 없음, Request 값과 DB값이 일치 한경우 일치하다고 판단

		boolean isEquals = false;

		if (StringUtils.equals(equalsType, MemberConstants.LOGIN_DEVICE_EQUALS_DEVICE_TELECOM)) {

			if (StringUtils.equals(dbVal, MemberConstants.DEVICE_TELECOM_NSH) || StringUtils.isBlank(dbVal)
					|| StringUtils.equals(reqVal, dbVal)) {
				isEquals = true;
			}

		} else if (StringUtils.equals(equalsType, MemberConstants.LOGIN_DEVICE_EQUALS_NATIVE_ID)) {

			if (StringUtils.isBlank(dbVal) || StringUtils.equals(reqVal, dbVal)) {
				isEquals = true;
			}

		} else if (StringUtils.equals(equalsType, MemberConstants.LOGIN_DEVICE_EQUALS_DEVICE_ACCOUNT)) {

			if ((StringUtils.isBlank(reqVal) && StringUtils.isBlank(dbVal))
					|| StringUtils.equals(reqVal, dbVal)
					|| (StringUtils.isBlank(DeviceUtil.getGmailStr(reqVal)) && StringUtils.isBlank(DeviceUtil
							.getGmailStr(dbVal)))) {
				isEquals = true;
			} else { // gmail 계정만 비교처리

				ArrayList<String> reqGmailList = DeviceUtil.getGmailList(reqVal);
				ArrayList<String> dbGmailList = DeviceUtil.getGmailList(dbVal);

				LOGGER.info("{} {} request mail list{}", deviceId, equalsType, reqGmailList.toString());
				LOGGER.info("{} {} db mail list{}", deviceId, equalsType, dbGmailList.toString());

				// 한개만 같으면 Gmail 일치 처리
				for (String reqGmail : reqGmailList) {
					for (String dbGmail : dbGmailList) {
						if (StringUtils.equals(reqGmail, dbGmail)) {
							isEquals = true;
							LOGGER.info("{} {} request : {}, db : {}", deviceId, equalsType, reqGmail, dbGmail);
							break;
						}
					}
					if (isEquals)
						break;
				}
			}

		}

		LOGGER.info("{} {} 일치여부 : {}, request : {}, db : {}", deviceId, equalsType, isEquals, reqVal, dbVal);

		return isEquals;
	}

    /**
     * 휴대기기 삭제V2
     */
    @Override
    public RemoveDeviceRes remDeviceV2(SacRequestHeader requestHeader, RemoveDeviceReq req) {

		/* 헤더 정보 셋팅 */
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.setSystemID(requestHeader.getTenantHeader().getSystemId());

        List<String> removeKeyList = new ArrayList<String>();
        String isDeletePrimary = MemberConstants.USE_N;
        for (RemoveDeviceKeyListSacReq deviceKeyList : req.getDeviceKeyList()) {

            // 회원정보조회
            DetailReq detailReq = new DetailReq();
            detailReq.setDeviceKey(deviceKeyList.getDeviceKey());
            SearchExtentReq searchExtent = new SearchExtentReq();
            searchExtent.setUserInfoYn(MemberConstants.USE_Y);
            searchExtent.setDeviceInfoYn(MemberConstants.USE_Y);
            detailReq.setSearchExtent(searchExtent);

            DetailV2Res detailRes = this.userSearchService.detailV2(requestHeader, detailReq);

            if (StringUtils.equals(detailRes.getUserInfo().getIsDormant(), MemberConstants.USE_Y)) {
                throw new StorePlatformException("SAC_MEM_0006");
            }

            if (detailRes.getDeviceInfoList() == null &&
                    detailRes.getDeviceInfoList().size() <= 0) {
                throw new StorePlatformException("SAC_MEM_0002", "휴대기기");
            }

            if (detailRes.getUserInfo().getUserType().equals(MemberConstants.USER_TYPE_MOBILE)) {
				/* 모바일회원 단말삭제 불가 */
                throw new StorePlatformException("SAC_MEM_1511");
            }else {
                /* 삭제할 단말이 대표기기 여부 - 대표단말 삭제 처리 시 last_login_dt 최신인 단말로 대표기기 설정 함 */
                String isPrimary = detailRes.getDeviceInfoList().get(0).getIsPrimary();
                if (isPrimary.equals(MemberConstants.USE_Y)) {
                    LOGGER.info("{} 대표단말 삭제 처리 : {}", detailRes.getUserKey(), detailRes.getDeviceInfoList().get(0).getDeviceKey());
                    isDeletePrimary = MemberConstants.USE_Y;
                }
            }

            /* MQ 연동 : SC휴대기기 삭제를 하면 정보조회 할수 없어서 미리 처리함. */
            RemoveDeviceAmqpSacReq mqInfo = new RemoveDeviceAmqpSacReq();

            try {
                mqInfo.setWorkDt(DateUtil.getToday("yyyyMMddHHmmss"));
                mqInfo.setUserKey(detailRes.getUserKey());
                mqInfo.setDeviceKey(detailRes.getDeviceInfoList().get(0).getDeviceKey());
                if(StringUtils.isNotEmpty(detailRes.getDeviceInfoList().get(0).getMdn())){
                    mqInfo.setDeviceId(detailRes.getDeviceInfoList().get(0).getMdn());
                }else {
                    mqInfo.setDeviceId(detailRes.getDeviceInfoList().get(0).getDeviceId());
                }
                mqInfo.setSvcMangNo(detailRes.getDeviceInfoList().get(0).getSvcMangNum());
                mqInfo.setChgCaseCd(MemberConstants.GAMECENTER_WORK_CD_MOBILENUMBER_DELETE);

                this.memberDelDeviceAmqpTemplate.convertAndSend(mqInfo);
            } catch (AmqpException ex) {
                LOGGER.info("MQ process fail {}", mqInfo);
            }

            removeKeyList.add(detailRes.getDeviceInfoList().get(0).getDeviceKey());
        }

		/* SC 휴대기기 삭제요청 */
        RemoveDeviceRequest removeDeviceRequest = new RemoveDeviceRequest();
        removeDeviceRequest.setCommonRequest(commonRequest);
        removeDeviceRequest.setUserKey(req.getUserKey());
        removeDeviceRequest.setIsDeletePrimary(isDeletePrimary);
        removeDeviceRequest.setDeviceKey(removeKeyList);

        RemoveDeviceResponse removeDeviceResponse = this.deviceSCI.removeDevice(removeDeviceRequest);

        RemoveDeviceRes removeDeviceRes = new RemoveDeviceRes();
        List<RemoveDeviceListSacRes> resDeviceKeyList = new ArrayList<RemoveDeviceListSacRes>();
        for (String str : removeKeyList) {
            RemoveDeviceListSacRes removeListRes = new RemoveDeviceListSacRes();
            removeListRes.setDeviceKey(str);

            resDeviceKeyList.add(removeListRes);
        }
		removeDeviceRes.setDeviceKeyList(resDeviceKeyList);
        removeDeviceRes.setRemoveDeviceCount(String.valueOf(removeDeviceResponse.getDelDeviceCount()));

        return removeDeviceRes;
    }

}
