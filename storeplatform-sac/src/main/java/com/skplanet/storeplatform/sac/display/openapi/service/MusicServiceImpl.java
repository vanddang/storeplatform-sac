package com.skplanet.storeplatform.sac.display.openapi.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.openapi.vo.*;
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
    public MusicDetail getMusicDetail(MusicDetailParam musicDetailParam) {

        MusicDetail musicDetail = new MusicDetail();

        if (musicDetailParam.isIdType("songId")) {
            musicDetail.setChannel(commonDAO.queryForObject("OpenApi.musicChannelProdBySongId", musicDetailParam, MusicProd.class));
        }
        else if (musicDetailParam.isIdType("tumsSongId")) {
            musicDetail.setChannel(commonDAO.queryForObject("OpenApi.musicChannelProdByTumsSongId", musicDetailParam, MusicProd.class));
        }

        if (musicDetail.getChannel() != null) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("tenantId", musicDetailParam.getTenantId());
            params.put("prodId", musicDetail.getChannel().getProdId());
            musicDetail.setEpisodes(commonDAO.queryForList("OpenApi.musicEpisodeProdsByChannelId", params, MusicProd.class));
        }

        return musicDetail;
    }
}
