package com.skplanet.storeplatform.sac.display.openapi.service;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.openapi.vo.MusicDetail;
import com.skplanet.storeplatform.sac.display.openapi.vo.MusicDetailParam;
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
    public MusicDetail getMusicDetail(MusicDetailParam musicDetailParam) {
        MusicProd channelProd = null;

        if (musicDetailParam.isIdType("songId")) {
            channelProd = commonDAO.queryForObject("OpenApi.musicProdBySongId", musicDetailParam, MusicProd.class);
        }
        else if (musicDetailParam.isIdType("tumsSongId")) {
            channelProd = commonDAO.queryForObject("OpenApi.musicProdByTumsSongId", musicDetailParam, MusicProd.class);
        }
        MusicDetail musicDetail = new MusicDetail();
        musicDetail.setChannel(channelProd);
        return musicDetail;
    }
}
