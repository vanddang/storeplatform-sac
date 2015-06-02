package com.skplanet.storeplatform.sac.display.openapi.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.openapi.vo.MusicProd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MusicServiceImpl implements MusicService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Override
    public MusicProd getDetailBySongId(String tenantId, String outsdContentsId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("tenantId", tenantId);
        params.put("outsdContentsId", outsdContentsId);

        return commonDAO.queryForObject("OpenApi.musicDetailBySongId", params, MusicProd.class);
    }
}
