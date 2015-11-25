package com.skplanet.storeplatform.sac.other.csp.service;

import com.skplanet.storeplatform.external.client.csp.sci.CspSCI;
import com.skplanet.storeplatform.external.client.csp.vo.GetTingPointReq;
import com.skplanet.storeplatform.external.client.csp.vo.GetTingPointRes;
import com.skplanet.storeplatform.external.client.shopping.util.DateUtil;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.sac.client.internal.member.user.sci.DeviceSCI;
import com.skplanet.storeplatform.sac.client.internal.member.user.vo.UpdateLimitChargeYnSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.csp.CspTingPointReq;
import com.skplanet.storeplatform.sac.client.other.vo.csp.CspTingPointRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CSP 연동 ServiceImpl
 *
 * Updated on : 2015. 8. 12. Updated by : 심대진, 다모아 솔루션.
 */
@Service
public class CspServiceImpl implements CspService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CspServiceImpl.class);

    @Autowired
    private CspSCI cpsSCI;

    @Autowired
    private DeviceSCI deviceSCI;

    @Override
    public CspTingPointRes getTingPoint(SacRequestHeader sacHeader, CspTingPointReq req) {

        CspTingPointRes response = new CspTingPointRes();

        try {

            /**
             * EC 팅포인트 조회 연동.
             */
            GetTingPointReq getTingPointReq = new GetTingPointReq();
            getTingPointReq.setSvcMngNum(req.getSvcMngNum());
            GetTingPointRes ecRes = cpsSCI.getTingPoint(getTingPointReq);
            response.setChargeUnit(ecRes.getChargeUnit()); // 단위 세팅.
            response.setChargeBalance(ecRes.getChargeBalance()); // 데이터 잔여량 세팅.

        } catch(StorePlatformException spe) {

            /**
             * CSP 응답 데이타 값이 없을 경우에는 회원 LocalSCI 호출한다.
             * (회원의 한도 요금제 사용여부를 업데이트 한다.)
             */
            if (StringUtils.equals(spe.getErrorInfo().getCode(), "EC_CSP_9998")) {
                updateMember(req);
            }

            throw spe;

        }

        return response;
    }

    /**
     * 2.1.13.	회원 한도 요금제 사용여부 업데이트 (LocalSCI 회원 연동.)
     * @param req
     */
    private void updateMember(CspTingPointReq req) {

        try {

            UpdateLimitChargeYnSacReq updateLimitChargeYnSacReq = new UpdateLimitChargeYnSacReq();
            updateLimitChargeYnSacReq.setUserKey(req.getUserKey()); // 회원 키
            updateLimitChargeYnSacReq.setDeviceKey(req.getDeviceKey()); // 단말 키
            updateLimitChargeYnSacReq.setSearchDt(DateUtil.getToday("yyyyMMddHHmmss")); // 조회 시간
            updateLimitChargeYnSacReq.setLimitChargeYn("N"); // 한도요금제 YN
            deviceSCI.updateLimitChargeYn(updateLimitChargeYnSacReq);

        } catch(StorePlatformException spe) {
            LOGGER.info("회원 한도 요금제 사용여부 업데이트시 에러 Skip 처리.......");
        } catch(Exception e) {
            LOGGER.info("회원 한도 요금제 사용여부 업데이트시 에러 Skip 처리.......");
        }

    }
}
