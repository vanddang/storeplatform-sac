package com.skplanet.storeplatform.sac.display.cache.service;

import com.skplanet.spring.data.plandasj.PlandasjTemplate;
import com.skplanet.spring.data.plandasj.operations.HashOperations;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.cache.RedisKeys;
import com.skplanet.storeplatform.sac.display.cache.vo.SupportDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Updated on : 2016-01-19. Updated by : 양해엽, SK Planet.
 */
@Service
public class SupportDeviceManagerImpl implements SupportDeviceManager {

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Autowired
    private PlandasjTemplate plandasjTemplate;

    @Override
    public SupportDevice get(String prodId, String deviceModelCd) {
        SupportDevice supportDevice;

        supportDevice = load(prodId, deviceModelCd);
        if (supportDevice != null) {
            return supportDevice;
        }

        supportDevice = makeValue(prodId, deviceModelCd);
        if (supportDevice != null) {
            store(prodId, deviceModelCd, supportDevice);
        }

        return supportDevice;
    }

    private SupportDevice load(String prodId, String deviceModelCd) {
        HashOperations<String, String, SupportDevice> opsForHash = plandasjTemplate.opsForHash();

        return opsForHash.get(RedisKeys.SPRT_DEV.getKey(prodId), deviceModelCd);
    }

    private void store(String prodId, String deviceModelCd, SupportDevice supportDevice) {
        HashOperations<String, String, SupportDevice> opsForHash = plandasjTemplate.opsForHash();

        String sprtDevKey = RedisKeys.SPRT_DEV.getKey(prodId);
        opsForHash.put(sprtDevKey, deviceModelCd, supportDevice);
        plandasjTemplate.expire(sprtDevKey, 1, TimeUnit.HOURS);
    }

    private SupportDevice makeValue(String prodId, String deviceModelCd) {
        Map<String, Object> req = new HashMap<String, Object>();
        req.put("prodId", prodId);
        req.put("deviceModelCd", deviceModelCd);

        return commonDAO.queryForObject("SupportDeviceManager.getSprtDev", req, SupportDevice.class);
    }

    @Override
    public void evict(String prodId) {
        plandasjTemplate.delete(RedisKeys.SPRT_DEV.getKey(prodId));
    }
}
