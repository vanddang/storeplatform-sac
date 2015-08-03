package com.skplanet.storeplatform.sac.display.openapi.vo;

import com.skplanet.storeplatform.sac.client.display.vo.openapi.MusicDetailSacRes;

import java.util.LinkedList;
import java.util.List;

public class MusicDetail {
    private MusicProd channel;
    private List<MusicProd> episodes;

    public MusicDetailSacRes toMusicDetailSacRes() {
        if (channel == null || episodes == null) {
            return null;
        }

        MusicDetailSacRes musicDetailSacRes = new MusicDetailSacRes();
        musicDetailSacRes.setChannel(new MusicDetailSacRes.Product());
        musicDetailSacRes.getChannel().setProdId(channel.getProdId());
        musicDetailSacRes.getChannel().setProdStatusCd(channel.getProdStatusCd());
        musicDetailSacRes.setEpisodes(new LinkedList<MusicDetailSacRes.Product>());
        for (MusicProd episode : episodes) {
            MusicDetailSacRes.Product product = new MusicDetailSacRes.Product();
            product.setProdId(episode.getProdId());
            product.setProdStatusCd(episode.getProdStatusCd());
            product.setProdType(episode.getProdType());
            musicDetailSacRes.getEpisodes().add(product);
        }
        return musicDetailSacRes;
    }

    public MusicProd getChannel() {
        return channel;
    }

    public void setChannel(MusicProd channel) {
        this.channel = channel;
    }

    public List<MusicProd> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<MusicProd> episodes) {
        this.episodes = episodes;
    }
}
