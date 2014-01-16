/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.common.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 
 * CM_DEVICE 테이블 조회 Value Object
 * 
 * Updated on : 2014. 1. 2. Updated by : 김현일, 인크로스
 */
public class Device extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private String deviceModelCd;
	private String modelNm;
	private String engModelNm;
	private String repDeviceYn;
	private String uaCd;
	private String listImgPath;
	private String dtlImgPath;
	private String mnftCompCd;
	private String cmntCompCd;
	private String cpuCypeCd;
	private String vmTypeCd;
	private String vmVerCd;
	private String lcdSizeCd;
	private String colorCd;
	private String audioTypeCd;
	private String audioPolyCd;
	private String gigaVerCd;
	private String drmVerCd;
	private String v4SprtTypeCd;
	private String deviceTypeCd;
	private String prchsPosbTypeCd;
	private String innerMemSize;
	private String networkType;
	private String useYn;
	private String dpYn;
	private String featurePhoneYn;
	private String cameraSprtYn;
	private String extrrMemYn;
	private String touchScreenYn;
	private String bluetoothSprtYn;
	private String gpsSprtYn;
	private String itoppV4SprtYn;
	private String sound3dSprtYn;
	private String virtualKeyYn;
	private String tPlayerSprtYn;
	private String videoDrmSprtYn;
	private String cmfSprtYn;
	private String embeddedYn;
	private String wslSprtYn;
	private String sdVideoSprtYn;
	private String frDvcYn;
	private String verifyDvcYn;
	private String ebookSprtYn;
	private String comicSprtYn;
	private String hdvSprtYn;
	private String kpadSprtYn;
	private String kwacSprtYn;
	private String flashSprtYn;
	private String hdmiSprtYn;
	private String hdcpSprtYn;
	private String giftSprtYn;
	private String sclShpgSprtYn;
	private String magazineSprtYn;
	private String musicSprtYn;
	private String aomSprtYn;
	private String sktDrmSprtYn;
	private String strmSprtYn;
	private String wgtpSprtYn;
	private String armSprtYn;
	private String vodFixisttSprtYn;
	private String dolbySprtYn;
	private String webtoonSprtYn;
	private String regId;
	private String regDt;
	private String updId;
	private String updDt;

	/**
	 * @return String : deviceModelCd
	 */
	public String getDeviceModelCd() {
		return this.deviceModelCd;
	}

	/**
	 * @param deviceModelCd
	 *            String : the deviceModelCd to set
	 */
	public void setDeviceModelCd(String deviceModelCd) {
		this.deviceModelCd = deviceModelCd;
	}

	/**
	 * @return String : modelNm
	 */
	public String getModelNm() {
		return this.modelNm;
	}

	/**
	 * @param modelNm
	 *            String : the modelNm to set
	 */
	public void setModelNm(String modelNm) {
		this.modelNm = modelNm;
	}

	/**
	 * @return String : engModelNm
	 */
	public String getEngModelNm() {
		return this.engModelNm;
	}

	/**
	 * @param engModelNm
	 *            String : the engModelNm to set
	 */
	public void setEngModelNm(String engModelNm) {
		this.engModelNm = engModelNm;
	}

	/**
	 * @return String : repDeviceYn
	 */
	public String getRepDeviceYn() {
		return this.repDeviceYn;
	}

	/**
	 * @param repDeviceYn
	 *            String : the repDeviceYn to set
	 */
	public void setRepDeviceYn(String repDeviceYn) {
		this.repDeviceYn = repDeviceYn;
	}

	/**
	 * @return String : uaCd
	 */
	public String getUaCd() {
		return this.uaCd;
	}

	/**
	 * @param uaCd
	 *            String : the uaCd to set
	 */
	public void setUaCd(String uaCd) {
		this.uaCd = uaCd;
	}

	/**
	 * @return String : listImgPath
	 */
	public String getListImgPath() {
		return this.listImgPath;
	}

	/**
	 * @param listImgPath
	 *            String : the listImgPath to set
	 */
	public void setListImgPath(String listImgPath) {
		this.listImgPath = listImgPath;
	}

	/**
	 * @return String : dtlImgPath
	 */
	public String getDtlImgPath() {
		return this.dtlImgPath;
	}

	/**
	 * @param dtlImgPath
	 *            String : the dtlImgPath to set
	 */
	public void setDtlImgPath(String dtlImgPath) {
		this.dtlImgPath = dtlImgPath;
	}

	/**
	 * @return String : mnftCompCd
	 */
	public String getMnftCompCd() {
		return this.mnftCompCd;
	}

	/**
	 * @param mnftCompCd
	 *            String : the mnftCompCd to set
	 */
	public void setMnftCompCd(String mnftCompCd) {
		this.mnftCompCd = mnftCompCd;
	}

	/**
	 * @return String : cmntCompCd
	 */
	public String getCmntCompCd() {
		return this.cmntCompCd;
	}

	/**
	 * @param cmntCompCd
	 *            String : the cmntCompCd to set
	 */
	public void setCmntCompCd(String cmntCompCd) {
		this.cmntCompCd = cmntCompCd;
	}

	/**
	 * @return String : cpuCypeCd
	 */
	public String getCpuCypeCd() {
		return this.cpuCypeCd;
	}

	/**
	 * @param cpuCypeCd
	 *            String : the cpuCypeCd to set
	 */
	public void setCpuCypeCd(String cpuCypeCd) {
		this.cpuCypeCd = cpuCypeCd;
	}

	/**
	 * @return String : vmTypeCd
	 */
	public String getVmTypeCd() {
		return this.vmTypeCd;
	}

	/**
	 * @param vmTypeCd
	 *            String : the vmTypeCd to set
	 */
	public void setVmTypeCd(String vmTypeCd) {
		this.vmTypeCd = vmTypeCd;
	}

	/**
	 * @return String : vmVerCd
	 */
	public String getVmVerCd() {
		return this.vmVerCd;
	}

	/**
	 * @param vmVerCd
	 *            String : the vmVerCd to set
	 */
	public void setVmVerCd(String vmVerCd) {
		this.vmVerCd = vmVerCd;
	}

	/**
	 * @return String : lcdSizeCd
	 */
	public String getLcdSizeCd() {
		return this.lcdSizeCd;
	}

	/**
	 * @param lcdSizeCd
	 *            String : the lcdSizeCd to set
	 */
	public void setLcdSizeCd(String lcdSizeCd) {
		this.lcdSizeCd = lcdSizeCd;
	}

	/**
	 * @return String : colorCd
	 */
	public String getColorCd() {
		return this.colorCd;
	}

	/**
	 * @param colorCd
	 *            String : the colorCd to set
	 */
	public void setColorCd(String colorCd) {
		this.colorCd = colorCd;
	}

	/**
	 * @return String : audioTypeCd
	 */
	public String getAudioTypeCd() {
		return this.audioTypeCd;
	}

	/**
	 * @param audioTypeCd
	 *            String : the audioTypeCd to set
	 */
	public void setAudioTypeCd(String audioTypeCd) {
		this.audioTypeCd = audioTypeCd;
	}

	/**
	 * @return String : audioPolyCd
	 */
	public String getAudioPolyCd() {
		return this.audioPolyCd;
	}

	/**
	 * @param audioPolyCd
	 *            String : the audioPolyCd to set
	 */
	public void setAudioPolyCd(String audioPolyCd) {
		this.audioPolyCd = audioPolyCd;
	}

	/**
	 * @return String : gigaVerCd
	 */
	public String getGigaVerCd() {
		return this.gigaVerCd;
	}

	/**
	 * @param gigaVerCd
	 *            String : the gigaVerCd to set
	 */
	public void setGigaVerCd(String gigaVerCd) {
		this.gigaVerCd = gigaVerCd;
	}

	/**
	 * @return String : drmVerCd
	 */
	public String getDrmVerCd() {
		return this.drmVerCd;
	}

	/**
	 * @param drmVerCd
	 *            String : the drmVerCd to set
	 */
	public void setDrmVerCd(String drmVerCd) {
		this.drmVerCd = drmVerCd;
	}

	/**
	 * @return String : v4SprtTypeCd
	 */
	public String getV4SprtTypeCd() {
		return this.v4SprtTypeCd;
	}

	/**
	 * @param v4SprtTypeCd
	 *            String : the v4SprtTypeCd to set
	 */
	public void setV4SprtTypeCd(String v4SprtTypeCd) {
		this.v4SprtTypeCd = v4SprtTypeCd;
	}

	/**
	 * @return String : deviceTypeCd
	 */
	public String getDeviceTypeCd() {
		return this.deviceTypeCd;
	}

	/**
	 * @param deviceTypeCd
	 *            String : the deviceTypeCd to set
	 */
	public void setDeviceTypeCd(String deviceTypeCd) {
		this.deviceTypeCd = deviceTypeCd;
	}

	/**
	 * @return String : prchsPosbTypeCd
	 */
	public String getPrchsPosbTypeCd() {
		return this.prchsPosbTypeCd;
	}

	/**
	 * @param prchsPosbTypeCd
	 *            String : the prchsPosbTypeCd to set
	 */
	public void setPrchsPosbTypeCd(String prchsPosbTypeCd) {
		this.prchsPosbTypeCd = prchsPosbTypeCd;
	}

	/**
	 * @return String : innerMemSize
	 */
	public String getInnerMemSize() {
		return this.innerMemSize;
	}

	/**
	 * @param innerMemSize
	 *            String : the innerMemSize to set
	 */
	public void setInnerMemSize(String innerMemSize) {
		this.innerMemSize = innerMemSize;
	}

	/**
	 * @return String : networkType
	 */
	public String getNetworkType() {
		return this.networkType;
	}

	/**
	 * @param networkType
	 *            String : the networkType to set
	 */
	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	/**
	 * @return String : useYn
	 */
	public String getUseYn() {
		return this.useYn;
	}

	/**
	 * @param useYn
	 *            String : the useYn to set
	 */
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	/**
	 * @return String : dpYn
	 */
	public String getDpYn() {
		return this.dpYn;
	}

	/**
	 * @param dpYn
	 *            String : the dpYn to set
	 */
	public void setDpYn(String dpYn) {
		this.dpYn = dpYn;
	}

	/**
	 * @return String : featurePhoneYn
	 */
	public String getFeaturePhoneYn() {
		return this.featurePhoneYn;
	}

	/**
	 * @param featurePhoneYn
	 *            String : the featurePhoneYn to set
	 */
	public void setFeaturePhoneYn(String featurePhoneYn) {
		this.featurePhoneYn = featurePhoneYn;
	}

	/**
	 * @return String : cameraSprtYn
	 */
	public String getCameraSprtYn() {
		return this.cameraSprtYn;
	}

	/**
	 * @param cameraSprtYn
	 *            String : the cameraSprtYn to set
	 */
	public void setCameraSprtYn(String cameraSprtYn) {
		this.cameraSprtYn = cameraSprtYn;
	}

	/**
	 * @return String : extrrMemYn
	 */
	public String getExtrrMemYn() {
		return this.extrrMemYn;
	}

	/**
	 * @param extrrMemYn
	 *            String : the extrrMemYn to set
	 */
	public void setExtrrMemYn(String extrrMemYn) {
		this.extrrMemYn = extrrMemYn;
	}

	/**
	 * @return String : touchScreenYn
	 */
	public String getTouchScreenYn() {
		return this.touchScreenYn;
	}

	/**
	 * @param touchScreenYn
	 *            String : the touchScreenYn to set
	 */
	public void setTouchScreenYn(String touchScreenYn) {
		this.touchScreenYn = touchScreenYn;
	}

	/**
	 * @return String : bluetoothSprtYn
	 */
	public String getBluetoothSprtYn() {
		return this.bluetoothSprtYn;
	}

	/**
	 * @param bluetoothSprtYn
	 *            String : the bluetoothSprtYn to set
	 */
	public void setBluetoothSprtYn(String bluetoothSprtYn) {
		this.bluetoothSprtYn = bluetoothSprtYn;
	}

	/**
	 * @return String : gpsSprtYn
	 */
	public String getGpsSprtYn() {
		return this.gpsSprtYn;
	}

	/**
	 * @param gpsSprtYn
	 *            String : the gpsSprtYn to set
	 */
	public void setGpsSprtYn(String gpsSprtYn) {
		this.gpsSprtYn = gpsSprtYn;
	}

	/**
	 * @return String : itoppV4SprtYn
	 */
	public String getItoppV4SprtYn() {
		return this.itoppV4SprtYn;
	}

	/**
	 * @param itoppV4SprtYn
	 *            String : the itoppV4SprtYn to set
	 */
	public void setItoppV4SprtYn(String itoppV4SprtYn) {
		this.itoppV4SprtYn = itoppV4SprtYn;
	}

	/**
	 * @return String : sound3dSprtYn
	 */
	public String getSound3dSprtYn() {
		return this.sound3dSprtYn;
	}

	/**
	 * @param sound3dSprtYn
	 *            String : the sound3dSprtYn to set
	 */
	public void setSound3dSprtYn(String sound3dSprtYn) {
		this.sound3dSprtYn = sound3dSprtYn;
	}

	/**
	 * @return String : virtualKeyYn
	 */
	public String getVirtualKeyYn() {
		return this.virtualKeyYn;
	}

	/**
	 * @param virtualKeyYn
	 *            String : the virtualKeyYn to set
	 */
	public void setVirtualKeyYn(String virtualKeyYn) {
		this.virtualKeyYn = virtualKeyYn;
	}

	/**
	 * @return String : tPlayerSprtYn
	 */
	public String gettPlayerSprtYn() {
		return this.tPlayerSprtYn;
	}

	/**
	 * @param tPlayerSprtYn
	 *            String : the tPlayerSprtYn to set
	 */
	public void settPlayerSprtYn(String tPlayerSprtYn) {
		this.tPlayerSprtYn = tPlayerSprtYn;
	}

	/**
	 * @return String : videoDrmSprtYn
	 */
	public String getVideoDrmSprtYn() {
		return this.videoDrmSprtYn;
	}

	/**
	 * @param videoDrmSprtYn
	 *            String : the videoDrmSprtYn to set
	 */
	public void setVideoDrmSprtYn(String videoDrmSprtYn) {
		this.videoDrmSprtYn = videoDrmSprtYn;
	}

	/**
	 * @return String : cmfSprtYn
	 */
	public String getCmfSprtYn() {
		return this.cmfSprtYn;
	}

	/**
	 * @param cmfSprtYn
	 *            String : the cmfSprtYn to set
	 */
	public void setCmfSprtYn(String cmfSprtYn) {
		this.cmfSprtYn = cmfSprtYn;
	}

	/**
	 * @return String : embeddedYn
	 */
	public String getEmbeddedYn() {
		return this.embeddedYn;
	}

	/**
	 * @param embeddedYn
	 *            String : the embeddedYn to set
	 */
	public void setEmbeddedYn(String embeddedYn) {
		this.embeddedYn = embeddedYn;
	}

	/**
	 * @return String : wslSprtYn
	 */
	public String getWslSprtYn() {
		return this.wslSprtYn;
	}

	/**
	 * @param wslSprtYn
	 *            String : the wslSprtYn to set
	 */
	public void setWslSprtYn(String wslSprtYn) {
		this.wslSprtYn = wslSprtYn;
	}

	/**
	 * @return String : sdVideoSprtYn
	 */
	public String getSdVideoSprtYn() {
		return this.sdVideoSprtYn;
	}

	/**
	 * @param sdVideoSprtYn
	 *            String : the sdVideoSprtYn to set
	 */
	public void setSdVideoSprtYn(String sdVideoSprtYn) {
		this.sdVideoSprtYn = sdVideoSprtYn;
	}

	/**
	 * @return String : frDvcYn
	 */
	public String getFrDvcYn() {
		return this.frDvcYn;
	}

	/**
	 * @param frDvcYn
	 *            String : the frDvcYn to set
	 */
	public void setFrDvcYn(String frDvcYn) {
		this.frDvcYn = frDvcYn;
	}

	/**
	 * @return String : verifyDvcYn
	 */
	public String getVerifyDvcYn() {
		return this.verifyDvcYn;
	}

	/**
	 * @param verifyDvcYn
	 *            String : the verifyDvcYn to set
	 */
	public void setVerifyDvcYn(String verifyDvcYn) {
		this.verifyDvcYn = verifyDvcYn;
	}

	/**
	 * @return String : ebookSprtYn
	 */
	public String getEbookSprtYn() {
		return this.ebookSprtYn;
	}

	/**
	 * @param ebookSprtYn
	 *            String : the ebookSprtYn to set
	 */
	public void setEbookSprtYn(String ebookSprtYn) {
		this.ebookSprtYn = ebookSprtYn;
	}

	/**
	 * @return String : comicSprtYn
	 */
	public String getComicSprtYn() {
		return this.comicSprtYn;
	}

	/**
	 * @param comicSprtYn
	 *            String : the comicSprtYn to set
	 */
	public void setComicSprtYn(String comicSprtYn) {
		this.comicSprtYn = comicSprtYn;
	}

	/**
	 * @return String : hdvSprtYn
	 */
	public String getHdvSprtYn() {
		return this.hdvSprtYn;
	}

	/**
	 * @param hdvSprtYn
	 *            String : the hdvSprtYn to set
	 */
	public void setHdvSprtYn(String hdvSprtYn) {
		this.hdvSprtYn = hdvSprtYn;
	}

	/**
	 * @return String : kpadSprtYn
	 */
	public String getKpadSprtYn() {
		return this.kpadSprtYn;
	}

	/**
	 * @param kpadSprtYn
	 *            String : the kpadSprtYn to set
	 */
	public void setKpadSprtYn(String kpadSprtYn) {
		this.kpadSprtYn = kpadSprtYn;
	}

	/**
	 * @return String : kwacSprtYn
	 */
	public String getKwacSprtYn() {
		return this.kwacSprtYn;
	}

	/**
	 * @param kwacSprtYn
	 *            String : the kwacSprtYn to set
	 */
	public void setKwacSprtYn(String kwacSprtYn) {
		this.kwacSprtYn = kwacSprtYn;
	}

	/**
	 * @return String : flashSprtYn
	 */
	public String getFlashSprtYn() {
		return this.flashSprtYn;
	}

	/**
	 * @param flashSprtYn
	 *            String : the flashSprtYn to set
	 */
	public void setFlashSprtYn(String flashSprtYn) {
		this.flashSprtYn = flashSprtYn;
	}

	/**
	 * @return String : hdmiSprtYn
	 */
	public String getHdmiSprtYn() {
		return this.hdmiSprtYn;
	}

	/**
	 * @param hdmiSprtYn
	 *            String : the hdmiSprtYn to set
	 */
	public void setHdmiSprtYn(String hdmiSprtYn) {
		this.hdmiSprtYn = hdmiSprtYn;
	}

	/**
	 * @return String : hdcpSprtYn
	 */
	public String getHdcpSprtYn() {
		return this.hdcpSprtYn;
	}

	/**
	 * @param hdcpSprtYn
	 *            String : the hdcpSprtYn to set
	 */
	public void setHdcpSprtYn(String hdcpSprtYn) {
		this.hdcpSprtYn = hdcpSprtYn;
	}

	/**
	 * @return String : giftSprtYn
	 */
	public String getGiftSprtYn() {
		return this.giftSprtYn;
	}

	/**
	 * @param giftSprtYn
	 *            String : the giftSprtYn to set
	 */
	public void setGiftSprtYn(String giftSprtYn) {
		this.giftSprtYn = giftSprtYn;
	}

	/**
	 * @return String : sclShpgSprtYn
	 */
	public String getSclShpgSprtYn() {
		return this.sclShpgSprtYn;
	}

	/**
	 * @param sclShpgSprtYn
	 *            String : the sclShpgSprtYn to set
	 */
	public void setSclShpgSprtYn(String sclShpgSprtYn) {
		this.sclShpgSprtYn = sclShpgSprtYn;
	}

	/**
	 * @return String : magazineSprtYn
	 */
	public String getMagazineSprtYn() {
		return this.magazineSprtYn;
	}

	/**
	 * @param magazineSprtYn
	 *            String : the magazineSprtYn to set
	 */
	public void setMagazineSprtYn(String magazineSprtYn) {
		this.magazineSprtYn = magazineSprtYn;
	}

	/**
	 * @return String : musicSprtYn
	 */
	public String getMusicSprtYn() {
		return this.musicSprtYn;
	}

	/**
	 * @param musicSprtYn
	 *            String : the musicSprtYn to set
	 */
	public void setMusicSprtYn(String musicSprtYn) {
		this.musicSprtYn = musicSprtYn;
	}

	/**
	 * @return String : aomSprtYn
	 */
	public String getAomSprtYn() {
		return this.aomSprtYn;
	}

	/**
	 * @param aomSprtYn
	 *            String : the aomSprtYn to set
	 */
	public void setAomSprtYn(String aomSprtYn) {
		this.aomSprtYn = aomSprtYn;
	}

	/**
	 * @return String : sktDrmSprtYn
	 */
	public String getSktDrmSprtYn() {
		return this.sktDrmSprtYn;
	}

	/**
	 * @param sktDrmSprtYn
	 *            String : the sktDrmSprtYn to set
	 */
	public void setSktDrmSprtYn(String sktDrmSprtYn) {
		this.sktDrmSprtYn = sktDrmSprtYn;
	}

	/**
	 * @return String : strmSprtYn
	 */
	public String getStrmSprtYn() {
		return this.strmSprtYn;
	}

	/**
	 * @param strmSprtYn
	 *            String : the strmSprtYn to set
	 */
	public void setStrmSprtYn(String strmSprtYn) {
		this.strmSprtYn = strmSprtYn;
	}

	/**
	 * @return String : wgtpSprtYn
	 */
	public String getWgtpSprtYn() {
		return this.wgtpSprtYn;
	}

	/**
	 * @param wgtpSprtYn
	 *            String : the wgtpSprtYn to set
	 */
	public void setWgtpSprtYn(String wgtpSprtYn) {
		this.wgtpSprtYn = wgtpSprtYn;
	}

	/**
	 * @return String : armSprtYn
	 */
	public String getArmSprtYn() {
		return this.armSprtYn;
	}

	/**
	 * @param armSprtYn
	 *            String : the armSprtYn to set
	 */
	public void setArmSprtYn(String armSprtYn) {
		this.armSprtYn = armSprtYn;
	}

	/**
	 * @return String : vodFixisttSprtYn
	 */
	public String getVodFixisttSprtYn() {
		return this.vodFixisttSprtYn;
	}

	/**
	 * @param vodFixisttSprtYn
	 *            String : the vodFixisttSprtYn to set
	 */
	public void setVodFixisttSprtYn(String vodFixisttSprtYn) {
		this.vodFixisttSprtYn = vodFixisttSprtYn;
	}

	/**
	 * @return String : dolbySprtYn
	 */
	public String getDolbySprtYn() {
		return this.dolbySprtYn;
	}

	/**
	 * @param dolbySprtYn
	 *            String : the dolbySprtYn to set
	 */
	public void setDolbySprtYn(String dolbySprtYn) {
		this.dolbySprtYn = dolbySprtYn;
	}

	/**
	 * @return String : webtoonSprtYn
	 */
	public String getWebtoonSprtYn() {
		return this.webtoonSprtYn;
	}

	/**
	 * @param webtoonSprtYn
	 *            String : the webtoonSprtYn to set
	 */
	public void setWebtoonSprtYn(String webtoonSprtYn) {
		this.webtoonSprtYn = webtoonSprtYn;
	}

	/**
	 * @return String : regId
	 */
	public String getRegId() {
		return this.regId;
	}

	/**
	 * @param regId
	 *            String : the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * @return String : regDt
	 */
	public String getRegDt() {
		return this.regDt;
	}

	/**
	 * @param regDt
	 *            String : the regDt to set
	 */
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	/**
	 * @return String : updId
	 */
	public String getUpdId() {
		return this.updId;
	}

	/**
	 * @param updId
	 *            String : the updId to set
	 */
	public void setUpdId(String updId) {
		this.updId = updId;
	}

	/**
	 * @return String : updDt
	 */
	public String getUpdDt() {
		return this.updDt;
	}

	/**
	 * @param updDt
	 *            String : the updDt to set
	 */
	public void setUpdDt(String updDt) {
		this.updDt = updDt;
	}

}
