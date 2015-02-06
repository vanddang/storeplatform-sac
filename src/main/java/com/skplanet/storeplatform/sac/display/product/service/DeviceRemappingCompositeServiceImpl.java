package com.skplanet.storeplatform.sac.display.product.service;

import com.skplanet.icms.devicemapping.DeviceMappingCommandCds;
import com.skplanet.icms.devicemapping.DeviceMappingContentVO;
import com.skplanet.icms.devicemapping.DeviceMappingQueueVO;
import com.skplanet.icms.devicemapping.DeviceMappingResultVO;
import com.skplanet.icms.refactoring.deploy.DPProductSubContsVO;
import com.skplanet.icms.refactoring.deploy.DPSprtDeviceVO;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.display.product.constant.IFConstants;
import com.skplanet.storeplatform.sac.display.product.exception.IcmsProcessException;
import com.skplanet.storeplatform.sac.display.product.vo.CmsVo;
import com.skplanet.storeplatform.sac.display.product.vo.ProductVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * DeviceMappingCompositeServiceImpl
 * 
 * CMS DeviceRemapping 서비스 구현체.
 * 
 * Updated on : 2014. 2. 13. Updated by : 차명호, ANB
 */

@Service
public class DeviceRemappingCompositeServiceImpl implements DeviceMappingCompositeService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/** The message source accessor. */
	@Autowired
	private MessageSourceAccessor messageSourceAccessor;

	@Autowired
	@Qualifier("cmsApp")
	private CommonDAO commonDAO;

	@Resource
	private AmqpTemplate deviceRemappingRabbitTemplate;

	/**
	 * @descriion DEVICE-MPING 통합CMS > dev3.0 전문 처리
	 * @param message
	 * @return CmsVo
	 * @exception com.skplanet.storeplatform.framework.core.exception.StorePlatformException
	 */
	@Override
	public void executeProcess(DeviceMappingQueueVO message) {

        this.log.debug("*********************************************************");
        this.log.debug("CmsDeviceController process started!!");
        this.log.debug("***** getTxId         --> txId           :::: {}", message.getTxId());
        this.log.debug("***** getCommandCode  --> type           :::: {}", message.getCommandCode());
        this.log.debug("***** getModule       --> deviceModelCd  :::: {}", message.getModule());
        this.log.debug("*********************************************************");

        // 실패 건수 취합 리스트
        List<DeviceMappingContentVO> resultFailList = new ArrayList<DeviceMappingContentVO>();

        int countSuccess = 0;
        int countFail = 0;

        String txId = message.getTxId();
        String type = message.getCommandCode();
        List<DeviceMappingContentVO> contentList = message.getContentList();
        if (contentList.size() <= 0) {
            countFail = 9999;
        }

        CmsVo cv = new CmsVo();
        cv.setTransactionKey(txId);
        cv.setResultCd(IFConstants.CMS_RST_CODE_UNKNOWN_ERROR); // Result Code
        cv.setResultMsg(this.messageSourceAccessor.getMessage("if.cms.msg.code." + cv.getResultCd()));

        String[] deviceModelCds;
        if(message.getPhoneModelCdList() != null && message.getPhoneModelCdList().size() > 0)
            deviceModelCds = message.getPhoneModelCdList().toArray(new String[message.getPhoneModelCdList().size()]);
        else
            deviceModelCds = new String[]{message.getModule()};

        for(String deviceModelCd : deviceModelCds) {
            this.log.info("Remapping Info = " + txId + " / " + type + " / " + deviceModelCd + " / " + contentList.size());

            for (DeviceMappingContentVO dmcv : contentList) {

                try {
                    String prodId = dmcv.getCid();
                    String subContentsId = dmcv.getScid();
                    String lcdSize = dmcv.getLcdInfo();
                    String vmVer = dmcv.getOsVersion();

                    // 데이터 유효성 체크
                    if (null == prodId || null == type) {
                        throw new IcmsProcessException(IFConstants.CMS_RST_CODE_DP_DATA_INVALID_ERROR);
                    }
                    if (DeviceMappingCommandCds.CMD_DEVICE_REMAP_INS.getValue().equals(type)) {
                        if (null == subContentsId || null == deviceModelCd) { // 단말 추가
                            throw new IcmsProcessException(IFConstants.CMS_RST_CODE_DP_DATA_INVALID_ERROR);
                        }
                    } else if (DeviceMappingCommandCds.CMD_DEVICE_REMAP_DEL.getValue().equals(type)) {
                        if (null == subContentsId || null == deviceModelCd) { // 단말 삭제
                            throw new IcmsProcessException(IFConstants.CMS_RST_CODE_DP_DATA_INVALID_ERROR);
                        }
                    } else if (DeviceMappingCommandCds.CMD_OS_REMAP_INS.getValue().equals(type)) {
                        if (null == subContentsId || null == vmVer) { // OS 변경
                            throw new IcmsProcessException(IFConstants.CMS_RST_CODE_DP_DATA_INVALID_ERROR);
                        }
                    } else if (DeviceMappingCommandCds.CMD_LCDSIZE_REMAP_INS.getValue().equals(type)) {
                        throw new IcmsProcessException(IFConstants.CMS_RST_CODE_DP_DATA_INVALID_ERROR);
                    }

                    this.log.info("Remapping Prod Info = " + prodId + " / " + subContentsId + " / " + deviceModelCd + " / "
                            + lcdSize + " / " + vmVer);

                    // 상품 정보 체크
                    ProductVo dpProd = new ProductVo();
                    dpProd.setProdId(prodId);
                    int dpCount = this.commonDAO.queryForInt("Display_Device.selectDpProdCount", dpProd);
                    if (dpCount <= 0)
                        throw new IcmsProcessException(IFConstants.CMS_RST_CODE_DP_DATA_INVALID_ERROR, "PROD_ID [ " + prodId
                                + " ] 로 등록되어진 상품 정보가 없습니다.");

                    if (DeviceMappingCommandCds.CMD_DEVICE_REMAP_INS.getValue().equals(type)) { // 단말 추가
                        DPSprtDeviceVO sh = new DPSprtDeviceVO();
                        sh.setProdId(prodId);
                        sh.setDeviceModelCd(deviceModelCd);
                        sh.setSubContentsId(subContentsId);
                        try {
                            this.commonDAO.delete("Display_Device.deleteDpSprtHp", sh);
                        } catch (Exception e) {
                            throw new IcmsProcessException(IFConstants.CMS_RST_CODE_DB_ERROR, e);
                        }

                        try {
                            this.commonDAO.insert("Display_Device.insertDpSprtHp", sh);
                        } catch (Exception e) {
                            throw new IcmsProcessException(IFConstants.CMS_RST_CODE_DB_ERROR, e);
                        }

                        if(lcdSize != null)
                            updateSubContents(prodId, subContentsId, lcdSize, null);
                    }
                    else if (DeviceMappingCommandCds.CMD_DEVICE_REMAP_DEL.getValue().equals(type)) { // 단말 삭제
                        DPSprtDeviceVO sh = new DPSprtDeviceVO();
                        sh.setProdId(prodId);
                        sh.setSubContentsId(subContentsId);
                        sh.setDeviceModelCd(deviceModelCd);
                        try {
                            this.commonDAO.delete("Display_Device.deleteDpSprtHp", sh);
                        } catch (Exception e) {
                            throw new IcmsProcessException(IFConstants.CMS_RST_CODE_DB_ERROR, e);
                        }

                        if(lcdSize != null)
                            updateSubContents(prodId, subContentsId, lcdSize, null);
                    }
                    else if (DeviceMappingCommandCds.CMD_OS_REMAP_INS.getValue().equals(type)) { // OS 변경
                        updateSubContents(prodId, subContentsId, null, vmVer);
                    }

                    // 결과 설정
                    cv.setResultCd(IFConstants.CMS_RST_CODE_SUCCESS);
                    cv.setResultMsg(this.messageSourceAccessor.getMessage("if.cms.msg.code." + cv.getResultCd()));

                } catch (IcmsProcessException ie) {
                    cv.setResultCd(ie.getCode());
                    cv.setResultMsg(this.messageSourceAccessor.getMessage("if.cms.msg.code." + cv.getResultCd()));
                } catch (RuntimeException e) {
                    cv.setResultCd(IFConstants.CMS_RST_CODE_UNKNOWN_ERROR);
                    cv.setResultMsg(e.getMessage());
                }

                if (cv.getResultCd().equals(IFConstants.CMS_RST_CODE_SUCCESS)) {
                    ++countSuccess;
                } else {
                    dmcv.setMsg(cv.getResultMsg());
                    dmcv.setResult(cv.getResultCd());
                    resultFailList.add(dmcv);
                    ++countFail;
                }
            }
        }

        this.log.debug(":::::countSuccess {} , countFail {} :::::::", countSuccess, countFail);

        if (countFail == contentList.size() || countFail > 0) {
            cv.setResultCd(IFConstants.CMS_RST_CODE_UNKNOWN_ERROR); // Result Code
            cv.setResultMsg(this.messageSourceAccessor.getMessage("if.cms.msg.code." + cv.getResultCd()));
        } else {
            cv.setResultCd(IFConstants.CMS_RST_CODE_SUCCESS);
            cv.setResultMsg(this.messageSourceAccessor.getMessage("if.cms.msg.code." + cv.getResultCd()));
        }

        // 결과 전송
        DeviceMappingResultVO dmr = new DeviceMappingResultVO();
        dmr.setTxId(txId);
        dmr.setResult(cv.getResultCd()); // Result Code
        dmr.setMsg("[SAC]"+cv.getResultMsg()+" (Success:"+countSuccess+", Fail:"+countFail+")"); // Result Message
        dmr.setContentList(resultFailList);
        dmr.setCommandCode(type);

        this.deviceRemappingRabbitTemplate.convertAndSend(dmr);

        this.log.debug("*********************************************************");
        this.log.debug("***** dmr.getResult         :::: {}", dmr.getResult());
        this.log.debug("***** dmr.getTxId           :::: {}", dmr.getTxId());
        this.log.debug("***** dmr.getMsg            :::: {}", dmr.getMsg());
        this.log.debug("CmsDeviceController process end!!");
        this.log.debug("*********************************************************");

	}

    private void updateSubContents(String prodId, String subContentsId, String lcdSize, String vmVer) {
        DPProductSubContsVO sc = new DPProductSubContsVO();
        sc.setProdId(prodId);
        sc.setSubContentsId(subContentsId);
        sc.setLcdSize(lcdSize);
        sc.setVmVer(vmVer);

        try {
            this.commonDAO.update("Display_Device.updateDpSubConts", sc);
        } catch (Exception e) {
            throw new IcmsProcessException(IFConstants.CMS_RST_CODE_DB_ERROR, e);
        }
    }

}
