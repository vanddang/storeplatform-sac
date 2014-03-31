package com.skplanet.storeplatform.sac.display.product.service;

import com.skplanet.icms.refactoring.deploy.DPProductVO;
import com.skplanet.icms.refactoring.deploy.DPTenantProductVO;
import com.skplanet.icms.refactoring.deploy.NotificationRefactoringSac;
import com.skplanet.icms.refactoring.deploy.NotificationRefactoringSacResult;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.display.product.constant.IFConstants;
import com.skplanet.storeplatform.sac.display.product.vo.CmsVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ProductDeployCompositeServiceImpl CMS 전시 배포 서비스 구현체. Updated on : 2014. 2. 13. Updated by : 차명호, ANB
 */

@Service
public class ProductDeployCompositeServiceImpl implements ProductDeployCompositeService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DisplayProductInitializer initializer;

	@Autowired
	private DisplayProductBuilder builder;

	@Autowired
	private ProductService prodService;

	/** The message source accessor. */
	@Autowired
	private MessageSourceAccessor messageSourceAccessor;

	@Resource
	private AmqpTemplate cmsAmqpTemplate;

	@Override
	public void executeProcess(NotificationRefactoringSac message){

        this.log.info("CMS MQ APP Process Start");

        CmsVo cv = new CmsVo();
        int transactionKey = message.getTransactionKey();
        this.log.info("CMS TransactionKey = " + transactionKey);
        cv.setTransactionKey(String.valueOf(transactionKey));
        cv.setResultCd(IFConstants.CMS_RST_CODE_UNKNOWN_ERROR);
        cv.setResultMsg(this.messageSourceAccessor.getMessage("if.cms.msg.code." + cv.getResultCd()));

        // Deploy
        DPProductVO dpProd = message.getDpProductTotal().getDpProduct();
        if (null != dpProd) {
            String prodId = StringUtils.defaultString(dpProd.getProdId());
            String mbrNo = StringUtils.defaultString(dpProd.getSellerMbrNo());
            this.log.info("CMS Prod Info = " + prodId + " / " + mbrNo);
            cv.setProdId(prodId);
            cv.setMbrNo(mbrNo);
            List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();

            if (!"".equals(prodId) && !"".equals(mbrNo)) {

                try {

                    List<DPTenantProductVO> tenantInfo = message.getDpProductTotal().getDpTenantProduct();
                    if (null != tenantInfo) {
                        if (0 < tenantInfo.size()) {
                            for (DPTenantProductVO vo : tenantInfo) {
                                Map<String, Object> oldProd = this.prodService.selectDpProd(vo);

                                if (null != oldProd) {
                                    tempList.add(oldProd);
                                }
                            }
                        }
                    }

                    /*
                     * 이전에 배포된 전시 상품 데이터 삭제
                     */
                    this.initializer.deleteProdInfo(message);       // FIXME

                    /*
                     * 데이터 재구성
                     */
                    this.builder.insertProdInfo(message, tempList); // FIXME

                    // 결과 설정
                    cv.setResultCd(IFConstants.CMS_RST_CODE_SUCCESS);
                    cv.setResultMsg(this.messageSourceAccessor.getMessage("if.cms.msg.code." + cv.getResultCd()));

                } catch (StorePlatformException ie) {
                    cv.setResultCd(ie.getErrorInfo().getCode()); // Result Code
                    cv.setResultMsg(this.messageSourceAccessor.getMessage("if.cms.msg.code." + ie.getErrorInfo().getCode()));
                }

                this.log.info("CMS Result Code = " + cv.getResultCd());
                this.log.info("CMS Result Message = " + cv.getResultMsg());
            } else {
                cv.setResultCd(IFConstants.CMS_RST_CODE_DP_DATA_INVALID_ERROR);
                cv.setResultMsg("DPProductVO 정보의 PROD_ID 또는 MBR_NO 는 Null 또는 공백이 올 수 없습니다.");
            }

        } else {
            cv.setResultCd(IFConstants.CMS_RST_CODE_DP_DATA_INVALID_ERROR);
            cv.setResultMsg("DPProductVO 정보는 Null 이 올 수 없습니다.");
        }

        // 결과 전송
        NotificationRefactoringSacResult ntr = new NotificationRefactoringSacResult();
        ntr.setTransactionKey(transactionKey);
        ntr.setDeployResultCd(cv.getResultCd());
        ntr.setDeployResultDtlMsg(cv.getResultMsg());
        this.log.info("CMS Transaction Key = " + ntr.getTransactionKey());
        this.log.info("CMS Return Code = " + ntr.getDeployResultCd());
        this.log.info("CMS Return Message = " + ntr.getDeployResultDtlMsg());

        this.cmsAmqpTemplate.convertAndSend(ntr);

	}

}
