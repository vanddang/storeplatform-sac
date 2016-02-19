package com.skplanet.storeplatform.sac.display.cache.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Updated on : 2016-01-22. Updated by : 양해엽, SK Planet.
 */
public class DeviceProfile extends CommonInfo {
    private static final long serialVersionUID = 1L;

    /**
     * device
     */
    private String deviceModelCd;
    private String makeCompNm;
    private String modelNm; // modelExplain
    private String uaCd;
    private String cmntCompCd; // type
    private String vmTypeNm; // platform
    private String deviceTypeCd; // phoneType

    /**
     * device.supportedHardware
     */
    private String hdcpSprtYn;
    private String lcdSizeNm; // resolsution
    private String networkType; // lte
    private String dolbySprtYn;
    private String hdmiSprtYn;

    /**
     * device.services
     */
    private String ebookSprtYn;
    private String comicSprtYn;
    private String hdvSprtYn; // hdVod
    private String sdVideoSprtYn; // sdVod
    private String sclShpgSprtYn; // shoopingStore
    private String magazineSprtYn;
    private String musicSprtYn;
    private String aomSprtYn;
    private String videoDrmSprtYn; // drmVideo
    private String sktDrmSprtYn; // drm
    private String strmSprtYn; // view
    private String WebtoonSprtYn;
    private String vodFixisttSprtYn; // freepassVod
    private String embeddedYn;
    private String nonUsimPhoneYn;

    public String getDeviceModelCd() {
        return deviceModelCd;
    }

    public void setDeviceModelCd(String deviceModelCd) {
        this.deviceModelCd = deviceModelCd;
    }

    public String getMakeCompNm() {
        return makeCompNm;
    }

    public void setMakeCompNm(String makeCompNm) {
        this.makeCompNm = makeCompNm;
    }

    public String getModelNm() {
        return modelNm;
    }

    public void setModelNm(String modelNm) {
        this.modelNm = modelNm;
    }

    public String getUaCd() {
        return uaCd;
    }

    public void setUaCd(String uaCd) {
        this.uaCd = uaCd;
    }

    public String getCmntCompCd() {
        return cmntCompCd;
    }

    public void setCmntCompCd(String cmntCompCd) {
        this.cmntCompCd = cmntCompCd;
    }

    public String getVmTypeNm() {
        return vmTypeNm;
    }

    public void setVmTypeNm(String vmTypeNm) {
        this.vmTypeNm = vmTypeNm;
    }

    public String getDeviceTypeCd() {
        return deviceTypeCd;
    }

    public void setDeviceTypeCd(String deviceTypeCd) {
        this.deviceTypeCd = deviceTypeCd;
    }

    public String getHdcpSprtYn() {
        return hdcpSprtYn;
    }

    public void setHdcpSprtYn(String hdcpSprtYn) {
        this.hdcpSprtYn = hdcpSprtYn;
    }

    public String getLcdSizeNm() {
        return lcdSizeNm;
    }

    public void setLcdSizeNm(String lcdSizeNm) {
        this.lcdSizeNm = lcdSizeNm;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public String getDolbySprtYn() {
        return dolbySprtYn;
    }

    public void setDolbySprtYn(String dolbySprtYn) {
        this.dolbySprtYn = dolbySprtYn;
    }

    public String getHdmiSprtYn() {
        return hdmiSprtYn;
    }

    public void setHdmiSprtYn(String hdmiSprtYn) {
        this.hdmiSprtYn = hdmiSprtYn;
    }

    public String getEbookSprtYn() {
        return ebookSprtYn;
    }

    public void setEbookSprtYn(String ebookSprtYn) {
        this.ebookSprtYn = ebookSprtYn;
    }

    public String getComicSprtYn() {
        return comicSprtYn;
    }

    public void setComicSprtYn(String comicSprtYn) {
        this.comicSprtYn = comicSprtYn;
    }

    public String getHdvSprtYn() {
        return hdvSprtYn;
    }

    public void setHdvSprtYn(String hdvSprtYn) {
        this.hdvSprtYn = hdvSprtYn;
    }

    public String getSdVideoSprtYn() {
        return sdVideoSprtYn;
    }

    public void setSdVideoSprtYn(String sdVideoSprtYn) {
        this.sdVideoSprtYn = sdVideoSprtYn;
    }

    public String getSclShpgSprtYn() {
        return sclShpgSprtYn;
    }

    public void setSclShpgSprtYn(String sclShpgSprtYn) {
        this.sclShpgSprtYn = sclShpgSprtYn;
    }

    public String getMagazineSprtYn() {
        return magazineSprtYn;
    }

    public void setMagazineSprtYn(String magazineSprtYn) {
        this.magazineSprtYn = magazineSprtYn;
    }

    public String getMusicSprtYn() {
        return musicSprtYn;
    }

    public void setMusicSprtYn(String musicSprtYn) {
        this.musicSprtYn = musicSprtYn;
    }

    public String getAomSprtYn() {
        return aomSprtYn;
    }

    public void setAomSprtYn(String aomSprtYn) {
        this.aomSprtYn = aomSprtYn;
    }

    public String getVideoDrmSprtYn() {
        return videoDrmSprtYn;
    }

    public void setVideoDrmSprtYn(String videoDrmSprtYn) {
        this.videoDrmSprtYn = videoDrmSprtYn;
    }

    public String getSktDrmSprtYn() {
        return sktDrmSprtYn;
    }

    public void setSktDrmSprtYn(String sktDrmSprtYn) {
        this.sktDrmSprtYn = sktDrmSprtYn;
    }

    public String getStrmSprtYn() {
        return strmSprtYn;
    }

    public void setStrmSprtYn(String strmSprtYn) {
        this.strmSprtYn = strmSprtYn;
    }

    public String getWebtoonSprtYn() {
        return WebtoonSprtYn;
    }

    public void setWebtoonSprtYn(String webtoonSprtYn) {
        WebtoonSprtYn = webtoonSprtYn;
    }

    public String getVodFixisttSprtYn() {
        return vodFixisttSprtYn;
    }

    public void setVodFixisttSprtYn(String vodFixisttSprtYn) {
        this.vodFixisttSprtYn = vodFixisttSprtYn;
    }

    public String getEmbeddedYn() {
        return embeddedYn;
    }

    public void setEmbeddedYn(String embeddedYn) {
        this.embeddedYn = embeddedYn;
    }

    public String getNonUsimPhoneYn() {
        return nonUsimPhoneYn;
    }

    public void setNonUsimPhoneYn(String nonUsimPhoneYn) {
        this.nonUsimPhoneYn = nonUsimPhoneYn;
    }
}
