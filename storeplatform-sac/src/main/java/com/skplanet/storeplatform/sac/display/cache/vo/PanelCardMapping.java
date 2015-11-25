/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.cache.vo;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * PanelCardMapping
 * </p>
 * Updated on : 2014. 11. 11 Updated by : 정희원, SK 플래닛.
 */
public class PanelCardMapping extends CommonInfo {
    private static final long serialVersionUID = 1L;

    private String cardId;
    private Date expoStartDt;
    private Date expoEndDt;
    private Date ddStartTm;
    private Date ddEndTm;
    private String expoWkdy;

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setExpoStartDt(Date expoStartDt) {
        this.expoStartDt = expoStartDt;
    }

    public void setExpoEndDt(Date expoEndDt) {
        this.expoEndDt = expoEndDt;
    }

    public void setDdStartTm(Date ddStartTm) {
        this.ddStartTm = ddStartTm;
    }

    public void setDdEndTm(Date ddEndTm) {
        this.ddEndTm = ddEndTm;
    }

    public void setExpoWkdy(String expoWkdy) {
        this.expoWkdy = expoWkdy;
    }

    /**
     * 기준 날짜에 대해 카드가 표시 가능한지의 여부를 응답한다.
     * @param stdDt 기준시간. null값을 입력하면 무조건 표시 가능
     * @return true이면 노출되는 데이터임
     */
    public boolean isVisibleForDate(Date stdDt) {
        if(stdDt == null)
            return true;

        long stdTime = stdDt.getTime();

        // 노출 날짜
        if(expoStartDt.getTime() > stdTime)
            return false;
        if(stdTime > expoEndDt.getTime())
            return false;

        // 요일별
        if(StringUtils.isNotEmpty(expoWkdy)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(stdDt);
            int dayNum = calendar.get(Calendar.DAY_OF_WEEK);    // 1:일 ~ 7:토
            if(expoWkdy.charAt(dayNum - 1) == '0')
                return false;
        }

        // 일일 노출 시간별
        if (ddStartTm != null && ddEndTm != null && (ddStartTm.getTime() != ddEndTm.getTime())) {

            // 오늘 날짜 기준의 일시 데이터로 가공한 후 비교
            long todayZeroTime = DateUtils.truncate(stdDt, Calendar.DAY_OF_MONTH).getTime();
            long startTime = DateUtils.getFragmentInMilliseconds(ddStartTm, Calendar.DATE),
                   endTime = DateUtils.getFragmentInMilliseconds(ddEndTm, Calendar.DATE);

            startTime += todayZeroTime;
            endTime += (endTime == 0 ? DateUtils.MILLIS_PER_DAY: todayZeroTime);

            if (ddStartTm.getTime() < ddEndTm.getTime() ?
                    !(startTime <= stdTime && stdTime <= endTime) :
                    (endTime <= stdTime && stdTime <= startTime))
                    return false;
        }

        return true;
    }
}
