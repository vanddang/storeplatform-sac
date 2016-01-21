package com.skplanet.storeplatform.sac.display.cache.service;

import com.skplanet.spring.data.plandasj.PlandasjTemplate;
import com.skplanet.spring.data.plandasj.operations.SetOperations;
import com.skplanet.spring.data.plandasj.operations.ValueOperations;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.common.util.ServicePropertyManager;
import com.skplanet.storeplatform.sac.display.cache.RedisKeys;
import com.skplanet.storeplatform.sac.display.cache.vo.PkgToAppInfo;
import com.skplanet.storeplatform.sac.display.common.DisplayCryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Updated on : 2016-01-19. Updated by : 양해엽, SK Planet.
 */
@Service
public class PkgToAppInfoManagerImpl implements PkgToAppInfoManager {

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Autowired
    private PlandasjTemplate plandasjTemplate;

    @Override
    public PkgToAppInfo get(String pkgNm) {
        PkgToAppInfo pkgToAppInfo;

        pkgToAppInfo = load(pkgNm);
        if (pkgToAppInfo != null) {
            return pkgToAppInfo;
        }

        pkgToAppInfo = makeValue(pkgNm);
        if (pkgToAppInfo != null) {
            store(pkgNm, pkgToAppInfo);
        }

        return pkgToAppInfo;
    }

    private PkgToAppInfo load(String pkgNm) {
        ValueOperations<String, PkgToAppInfo> opsForValue = plandasjTemplate.opsForValue();

        return opsForValue.get(RedisKeys.PKG_TO_PROD.getKey(pkgNm));
    }

    private void store(String pkgNm, PkgToAppInfo pkgToAppInfo) {
        ValueOperations<String, PkgToAppInfo> opsForValue = plandasjTemplate.opsForValue();
        SetOperations<String, String> opsForSet = plandasjTemplate.opsForSet();

        opsForValue.set(RedisKeys.PKG_TO_PROD.getKey(pkgNm), pkgToAppInfo, 1, TimeUnit.HOURS);

        String prodIdKey = RedisKeys.PKGS_IN_PROD.getKey(pkgToAppInfo.getProdId());
        opsForSet.add(prodIdKey, pkgNm);
        plandasjTemplate.expire(prodIdKey, 1, TimeUnit.HOURS);
    }

    private PkgToAppInfo makeValue(String pkgNm) {
        DisplayCryptUtils.SacSha1Mac sha1Mac = DisplayCryptUtils.createSha1Mac();
        String hashedPkgNm = sha1Mac.hashPkgNm(pkgNm);

        Map<String, Object> req = new HashMap<String, Object>();
        req.put("tenantIds", ServicePropertyManager.getSupportTenantList());
        req.put("hashedPkgNm", hashedPkgNm);

        String prodId = commonDAO.queryForObject("PkgToAppInfoManager.getProdIdByPkgNm", req, String.class);

        return StringUtils.isNotBlank(prodId) ? new PkgToAppInfo(prodId) : null;
    }

    @Override
    public void evict(String prodId) {

        SetOperations<String, String> opsForSet = plandasjTemplate.opsForSet();

        String prodIdKey = RedisKeys.PKGS_IN_PROD.getKey(prodId);
        Set<String> pkgNms = opsForSet.members(prodIdKey);
        for (String pkgNm : pkgNms) {
            plandasjTemplate.delete(RedisKeys.PKG_TO_PROD.getKey(pkgNm));
        }

        plandasjTemplate.delete(prodIdKey);
    }
}
