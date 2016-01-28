/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.cache.service;

import com.skplanet.storeplatform.sac.common.util.DateUtils;
import com.skplanet.storeplatform.sac.display.cache.vo.PromotionEvent;
import com.skplanet.storeplatform.sac.display.cache.vo.RawPromotionEvent;
import org.msgpack.MessagePack;
import org.msgpack.MessageTypeException;
import org.msgpack.packer.Packer;
import org.msgpack.unpacker.Unpacker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * <p>
 * PromotionEventConverter
 * </p>
 * Updated on : 2015. 08. 18 Updated by : 정희원, SK 플래닛.
 */
public class PromotionEventConverter {

    private static MessagePack messagePack;

    static {
        messagePack = new MessagePack();
        messagePack.register(PromotionEvent.class);
    }

    public static PromotionEvent convert(RawPromotionEvent rawEvent) {

        if(rawEvent == null)
            return null;

        PromotionEvent event = new PromotionEvent();
        event.setPromId(rawEvent.getPromId());
        event.setRateGrd1(rawEvent.getRateGrd1());
        event.setRateGrd2(rawEvent.getRateGrd2());
        event.setRateGrd3(rawEvent.getRateGrd3());
        event.setAcmlMethodCd(rawEvent.getAcmlMethodCd());
        event.setAcmlDt(DateUtils.format(rawEvent.getAcmlDate()));
        event.setStartDt(rawEvent.getStartDt());
        event.setEndDt(rawEvent.getEndDt());
        event.setTargetId(rawEvent.getPromTypeValue());
        event.setPrvAcmlLimt(rawEvent.getAcmlLimt());
        event.setUserTargetTp(rawEvent.getTargetUserKind());
        event.setUpdDt(rawEvent.getUpdDt());

        return event;
    }

    public static byte[] convert(PromotionEvent event) {
        if(event == null)
            return null;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Packer packer = messagePack.createPacker(out);
        try {
            packer.write(event);
        } catch (IOException e) {
            throw new RuntimeException("데이터 변환에 실패했습니다.", e);
        }

        return out.toByteArray();
    }

    public static PromotionEvent convert(byte[] data) {

        if(data == null || data.length == 0)
            return null;

        Unpacker unpacker = messagePack.createUnpacker(new ByteArrayInputStream(data));
        PromotionEvent event;
        try {
            event = unpacker.read(PromotionEvent.class);
        } catch (IOException e) {
            throw new RuntimeException("데이터 변환에 실패했습니다.", e);
        } catch (MessageTypeException e) {
            throw new RuntimeException("데이터 변환에 실패했습니다.", e);
        }

        return event;
    }
}
