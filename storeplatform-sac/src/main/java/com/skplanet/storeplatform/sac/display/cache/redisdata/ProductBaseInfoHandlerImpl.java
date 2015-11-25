/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.cache.redisdata;

import com.skplanet.plandasj.Plandasj;
import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import com.skplanet.storeplatform.sac.client.rest.example.SacRestAdaptorExample;
import com.skplanet.storeplatform.sac.common.support.redis.AbstractRedisDataHandler;
import com.skplanet.storeplatform.sac.display.cache.SacRedisKeys;
import com.skplanet.storeplatform.sac.display.cache.vo.ProductBaseInfo;
import org.msgpack.MessageTypeException;
import org.msgpack.packer.Packer;
import org.msgpack.unpacker.Unpacker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * <p>
 * ProductBaseInfoHandlerImpl
 * </p>
 * Updated on : 2015. 11. 03 Updated by : 정희원, SK 플래닛.
 */
public class ProductBaseInfoHandlerImpl extends AbstractRedisDataHandler<String, ProductBaseInfo> {


    @Override
    public Class<ProductBaseInfo> getResultTypeClass() {
        return ProductBaseInfo.class;
    }

    @Override
    public ProductBaseInfo load(String key, Plandasj redis) {
        byte[] data = redis.get(SacRedisKeys.prodBase(key).getBytes());
        if(data == null || data.length == 0)
            return null;

        Unpacker unpacker = messagePack.createUnpacker(new ByteArrayInputStream(data));

        try {
            return unpacker.read(ProductBaseInfo.class);
        }
        catch (IOException e) {
            throw new RuntimeException("데이터 변환에 실패했습니다.", e);
        }
        catch (MessageTypeException e) {
            throw new RuntimeException("데이터 변환에 실패했습니다.", e);
        }
    }

    @Override
    public void store(String key, ProductBaseInfo value, Plandasj redis) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Packer packer = messagePack.createPacker(out);
        try {
            packer.write(value);
        }
        catch (IOException e) {
            throw new RuntimeException("데이터 변환에 실패했습니다.", e);
        }

        redis.set(SacRedisKeys.prodBase(key).getBytes(), out.toByteArray());
        redis.sadd(SacRedisKeys.prodBaseSet(), key);
    }

    @Override
    public ProductBaseInfo makeValue(String key, CommonDAO commonDAO) {
        HashMap<String, Object> req = new HashMap<String, Object>();
        req.put("prodId", key);
        return commonDAO.queryForObject("CachedExtraInfoManager.getProductBaseInfo", req, ProductBaseInfo.class);
    }

    @Override
    public void evict(String key, Plandasj redis) {
        redis.del(SacRedisKeys.prodBase(key).getBytes());
        redis.srem(SacRedisKeys.prodBaseSet(), key);
    }
}
