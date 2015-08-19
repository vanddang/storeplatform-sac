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
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.msgpack.MessagePack;
import org.msgpack.packer.Packer;
import org.msgpack.unpacker.BufferUnpacker;
import org.msgpack.unpacker.Unpacker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * PromotionEventWrapper
 * PromotionEvent, RawPromotionEvent, 직렬화된 전문간의 변환 처리를 담당한다.
 * </p>
 * Updated on : 2015. 07. 23 Updated by : 정희원, SK 플래닛.
 */
@Deprecated
public class PromotionEventWrapper {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
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

        this.datetimeKey = DATE_FORMAT.format(rawEvent.getStartDt()) + "_" + DATE_FORMAT.format(rawEvent.getEndDt());
        this.startDt = rawEvent.getStartDt();
        this.endDt = rawEvent.getEndDt();

        event = new PromotionEvent();
        event.setPromId(rawEvent.getPromId());
        event.setRateGrd1(rawEvent.getRateGrd1());
        event.setRateGrd2(rawEvent.getRateGrd2());
        event.setRateGrd3(rawEvent.getRateGrd3());
        event.setAcmlMethodCd(rawEvent.getAcmlMethodCd());
        event.setAcmlDt(rawEvent.getAcmlDt());
        event.setStartDt(rawEvent.getStartDt());
        event.setEndDt(rawEvent.getEndDt());
        event.setTargetId(rawEvent.getPromTypeValue());

        Object[] v = new Object[]{Integer.toHexString(event.hashCode()),
                rawEvent.getPromId(),
                rawEvent.getRateGrd1(),
                rawEvent.getRateGrd2(),
                rawEvent.getRateGrd3(),
                rawEvent.getAcmlMethodCd(),
                rawEvent.getAcmlDt()};

        bodyPart = StringUtils.join(v, " ");
        str = this.datetimeKey + ":" + bodyPart;

        this.hasError = false;
    }

    public PromotionEventWrapper(byte[] data) {
        MessagePack mp = new MessagePack();
        Unpacker unpacker = mp.createUnpacker(new ByteArrayInputStream(data));
        PromotionEvent event;
        try {
            event = unpacker.read(PromotionEvent.class);
        } catch (IOException e) {
            throw new RuntimeException("데이터 변환에 실패했습니다.", e);
        }



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
                !now.before(startDt) && !now.after(endDt);
    }

    // TODO makePromotionEvent와 통일시켜야 함
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

    public PromotionEvent makePromotionEvent(String key) {
        PromotionEvent promotionEvent = getPromotionEvent();
        if(promotionEvent == null)
            return null;

        promotionEvent.setTargetId(key);
        return promotionEvent;
    }

    public String getStr() {

        if (str == null) {
            // TODO PromotionEvent to str
            throw new IllegalStateException("글쎄요? 이럴리가 없는데.");
        }

        return this.str;
    }

    public static byte[] getData(PromotionEvent event) {
        MessagePack mp = new MessagePack();
        mp.register(PromotionEvent.class);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Packer packer = mp.createPacker(out);
        try {
            packer.write(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return out.toByteArray();
    }

    public static PromotionEvent getPromotionEvent(byte[] data) {
        MessagePack mp = new MessagePack();
        mp.register(PromotionEvent.class);
        Unpacker unpacker = mp.createUnpacker(new ByteArrayInputStream(data));
        PromotionEvent event;
        try {
            event = unpacker.read(PromotionEvent.class);
        } catch (IOException e) {
            throw new RuntimeException("데이터 변환에 실패했습니다.", e);
        }
        return event;
    }

    public String getDatetimeKey() {
        return this.datetimeKey;
    }
}