package com.skplanet.storeplatform.sac.display.openapi.vo;

import com.skplanet.storeplatform.sac.client.display.vo.openapi.MusicDetailSacRes;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Product;

public class MusicDetail {
    private MusicProd channel;

    public MusicDetailSacRes toMusicDetailSacRes() {
        if (channel == null) {
            return null;
        }

        MusicDetailSacRes musicDetailSacRes = new MusicDetailSacRes();
        musicDetailSacRes.getChannel().setProdId(channel.getProdId());
        musicDetailSacRes.getChannel().setProdStatusCd(channel.getProdStatusCd());
        return musicDetailSacRes;
    }

    public MusicProd getChannel() {
        return channel;
    }

    public void setChannel(MusicProd channel) {
        this.channel = channel;
    }
}
