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

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * PromotionEventWrapper
 * PromotionEvent, RawPromotionEvent, 직렬화된 전문간의 변환 처리를 담당한다.
 * </p>
 * Updated on : 2015. 07. 23 Updated by : 정희원, SK 플래닛.
 */
public class PromotionEventWrapper {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmm");
    private static final Logger logger = LoggerFactory.getLogger(PromotionEventWrapper.class);

    private boolean hasError;
    private Date startDt;
    private Date endDt;
    private String str;
    private String datetimeKey;
    private String bodyPart;
    private PromotionEvent event = null;

    public PromotionEventWrapper(RawPromotionEvent rawEvent) {

        Preconditions.checkNotNull(rawEvent);

        this.datetimeKey = rawEvent.getDatetimeKey();

        Object[] v = new Object[]{Integer.toHexString(rawEvent.hashCode()),
                rawEvent.getPromId(),
                rawEvent.getRateGrd1(),
                rawEvent.getRateGrd2(),
                rawEvent.getRateGrd3(),
                rawEvent.getAcmlMethodCd(),
                rawEvent.getAcmlDt()};

        str = rawEvent.getDatetimeKey() + ":" + StringUtils.join(v, " ");
    }

    public PromotionEventWrapper(String str) {

        if(Strings.isNullOrEmpty(str)) {
            this.hasError = true;
            return;
        }

        this.str = str;

        String[] strPart = null;

        try {
            strPart = StringUtils.split(str, ":");
            Preconditions.checkArgument(strPart.length == 2, "Event message is wrong.");

            String[] datePart = StringUtils.split(strPart[0], "_");
            Preconditions.checkArgument(datePart.length == 2, "Event body date part is wrong.");

            this.datetimeKey = strPart[0];
            this.startDt = DATE_FORMAT.parse(datePart[0]);
            this.endDt = DATE_FORMAT.parse(datePart[1]);

            this.bodyPart = strPart[1];

        } catch (ParseException e) {
            logger.error("데이터 변환시 오류", e);
            hasError = true;
        } catch (RuntimeException e) {
            logger.error("데이터 변환시 오류", e);
            hasError = true;
        }
    }

    public boolean hasError() {
        return this.hasError;
    }

    public Date getStartDt() {
        return this.startDt;
    }

    public Date getEndDt() {
        return this.endDt;
    }

    /**
     * 이벤트가 진행중인지 응답
     * @param now 기준 시간
     * @return 진행중인 경우 true
     */
    public boolean isLive(Date now) {
        return !(this.startDt == null || this.endDt == null || now == null) &&
                now.after(startDt) && now.before(endDt);
    }

    public PromotionEvent getPromotionEvent() {

        // TODO 변환 오류에 대해 인지 및 처리 가능하도록 해야 함
        if (event == null) {

            String[] body = StringUtils.split(this.bodyPart);
            Preconditions.checkArgument(body.length == 7, "Event body is wrong.");

            PromotionEvent event = new PromotionEvent();

            String hashHex;

            try {
                event.setStartDt(startDt);
                event.setEndDt(endDt);

                hashHex = body[0];

                event.setPromId(Integer.parseInt(body[1]));
                event.setRateGrd1(Integer.parseInt(body[2]));
                event.setRateGrd2(Integer.parseInt(body[3]));
                event.setRateGrd3(Integer.parseInt(body[4]));
                event.setAcmlMethodCd(body[5]);
                event.setAcmlDt(body[6]);

            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new RuntimeException(e);
            }

            if (!hashHex.equals(Integer.toHexString(event.hashCode())))
                throw new RuntimeException("해시 값이 다릅니다.");

            this.event = event;
        }
        return this.event;
    }

    public String getStr() {

        if (str == null) {
            // TODO PromotionEvent to str
            throw new IllegalStateException("아직 구현되지 않았습니다.");
        }

        return this.str;
    }

    public String getDatetimeKey() {
        return this.datetimeKey;
    }
}