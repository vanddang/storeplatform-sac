/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.device.service;

import com.skplanet.storeplatform.sac.client.display.vo.device.DeviceProfileRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.CommonResponse;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Device;
import com.skplanet.storeplatform.sac.display.cache.service.DeviceProfileManager;
import com.skplanet.storeplatform.sac.display.cache.vo.DeviceProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.skplanet.storeplatform.sac.display.common.constant.DisplayConstants.*;

/**
 * DeviceProfileService Service 인터페이스(CoreStoreBusiness) 구현체.
 * 
 * Updated on : 2013-12-18 Updated by : 오승민, 인크로스
 */
@Service
public class DeviceProfileServiceImpl implements DeviceProfileService {

    @Autowired
    private DeviceProfileManager deviceProfileManager;

    @Override
    public DeviceProfileRes getDeviceProfile(String deviceModelCd, String langCd) {

        Device device;
        DeviceProfile deviceProfile = this.deviceProfileManager.getDeviceProfile(deviceModelCd, langCd);
        if (deviceProfile != null) {
            device = makeDevice(deviceProfile);

            return makeDeviceProfileRes(device, 1);
        } else {
            // 요청한 deviceModelCd가 존재하지 않는경우, android_standard 로 조회해서 내린다.
            deviceProfile = this.deviceProfileManager.getDeviceProfile(DP_ANDROID_STANDARD_NM, langCd);
            device = makeDevice(deviceProfile);

            device.setIdentifier(DP_ANDROID_STANDARD_NM);
            device.setType("restrict");

            return makeDeviceProfileRes(device, 0);
        }
    }

    private DeviceProfileRes makeDeviceProfileRes(Device device, int count) {
        DeviceProfileRes deviceProfileRes = new DeviceProfileRes();

        deviceProfileRes.setCommonResponse(new CommonResponse(count));
        deviceProfileRes.setDevice(device);

        return deviceProfileRes;
    }

    private Device makeDevice(DeviceProfile deviceProfile) {
        Device device = new Device();

        device.setIdentifier(deviceProfile.getMakeCompNm()+"/"+deviceProfile.getDeviceModelCd());
        device.setType(DP_OMD_TYPE_CD.equals(deviceProfile.getCmntCompCd()) ? DP_OMD_TYPE_NM : DP_OMD_NORMAL_NM);
        device.setManufacturer(deviceProfile.getMakeCompNm());
        device.setModel(deviceProfile.getDeviceModelCd());
        device.setUaCd(deviceProfile.getUaCd());
        device.setPlatform(deviceProfile.getVmTypeNm());
        device.setModelExplain(deviceProfile.getModelNm());
        device.setPhoneType(DP_PHONE_DEVICE_TYPE_CD.equals(deviceProfile.getDeviceTypeCd()) ? DP_PHONE_DEVICE_TYPE_NM : DP_TABLET_DEVICE_TYPE_NM);

        device.setSupportedHardware(makeSupportedHardware(deviceProfile));
        device.setServices(makeServices(deviceProfile));

        return device;
    }

    private Map<String,Object> makeServices(DeviceProfile deviceProfile) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("music", deviceProfile.getMusicSprtYn());
        map.put("hdVod", deviceProfile.getHdvSprtYn());
        map.put("freepassVod", deviceProfile.getVodFixisttSprtYn());
        map.put("aom", deviceProfile.getAomSprtYn());
        map.put("comic", deviceProfile.getComicSprtYn());
        map.put("shoppingStore", deviceProfile.getSclShpgSprtYn());
        map.put("embedded", deviceProfile.getEmbeddedYn());
        map.put("ebook", deviceProfile.getEbookSprtYn());
        map.put("magazine", deviceProfile.getMagazineSprtYn());
        map.put("sdVod", deviceProfile.getSdVideoSprtYn());
        map.put("drmVideo", deviceProfile.getVideoDrmSprtYn());
        map.put("drm", deviceProfile.getSktDrmSprtYn());
        map.put("webtoon", deviceProfile.getWebtoonSprtYn());
        map.put("view", deviceProfile.getStrmSprtYn());

        return map;
    }

    private Map<String, Object> makeSupportedHardware(DeviceProfile deviceProfile) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("hdcp", deviceProfile.getHdcpSprtYn());
        map.put("resolution", deviceProfile.getLcdSizeNm());
        map.put("lte", deviceProfile.getNetworkType());
        map.put("dolby", deviceProfile.getDolbySprtYn());
        map.put("hdmi", deviceProfile.getHdmiSprtYn());

        return map;
    }
}
