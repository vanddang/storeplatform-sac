package com.skplanet.storeplatform.sac.client.display.vo.openapi;


import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MusicDetailSacRes {
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public static class Product {
        private String prodId;
        private String prodStatusCd;
        private String prodType;

        public String getProdId() {
            return prodId;
        }

        public void setProdId(String prodId) {
            this.prodId = prodId;
        }

        public String getProdStatusCd() {
            return prodStatusCd;
        }

        public void setProdStatusCd(String prodStatusCd) {
            this.prodStatusCd = prodStatusCd;
        }

        public String getProdType() {
            return prodType;
        }

        public void setProdType(String prodType) {
            this.prodType = prodType;
        }
    }

    private Product channel;
    private List<Product> episodes;

    public Product getChannel() {
        return channel;
    }

    public void setChannel(Product channel) {
        this.channel = channel;
    }

    public List<Product> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Product> episodes) {
        this.episodes = episodes;
    }
}
