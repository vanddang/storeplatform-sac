package com.skplanet.storeplatform.sac.display.openapi.service;

import com.skplanet.storeplatform.external.client.message.sci.MessageSCI;
import com.skplanet.storeplatform.external.client.message.vo.SmsSendEcReq;
import com.skplanet.storeplatform.external.client.message.vo.SmsSendEcRes;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.openapi.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MusicServiceImpl implements MusicService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("sac")
    private CommonDAO commonDAO;

    @Autowired
    private MessageSCI messageSci;

    @Value("#{propertiesForSac['shopclient.baseurl.prod.detail']}")
    private String baseUrl;

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

    @Override
    public MusicDetailSendSmsRes musicDetailSendSms(MusicDetailSendSmsParam musicDetailSendSmsParam) {
        MusicDetailSendSmsRes musicDetailSendSmsRes = new MusicDetailSendSmsRes();
        SmsSendEcReq smsSendEcReq = newSmsSendEcReq(musicDetailSendSmsParam.getMdn(), musicDetailSendSmsParam.getChannelId());
        SmsSendEcRes smsSendEcRes = messageSci.smsSend(smsSendEcReq);
        if ("success".equals(smsSendEcRes.getResultStatus())) {
            musicDetailSendSmsRes.setResultCode(MusicDetailSendSmsRes.ResultCode.SENT);
        }
        else {
            musicDetailSendSmsRes.setResultCode(MusicDetailSendSmsRes.ResultCode.ERROR);
        }
        return musicDetailSendSmsRes;
    }

    private SmsSendEcReq newSmsSendEcReq(String mdn, String channelId) {
        SmsSendEcReq smsSendEcReq = new SmsSendEcReq();
        smsSendEcReq.setSrcId("US004542"); // 음악상품 shortUrl 발송
        smsSendEcReq.setTeleSvcId("0"); // 단문 SMS
        smsSendEcReq.setSendMdn("1600-6573"); // 발시번호
        smsSendEcReq.setRecvMdn(mdn);
        smsSendEcReq.setMsg("[티스토어]" + baseUrl + channelId);
        return smsSendEcReq;
    }
}
