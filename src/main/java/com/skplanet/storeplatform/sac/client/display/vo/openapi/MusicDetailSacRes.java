package com.skplanet.storeplatform.sac.client.display.vo.openapi;


public class MusicDetailSacRes {
    public class Product {
        private String prodId;
        private String prodStatusCd;

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
    }

    private Product channel;

    public MusicDetailSacRes() {
        this.channel = new Product();
    }

    public Product getChannel() {
        return channel;
    }

    public void setChannel(Product channel) {
        this.channel = channel;
    }
}
