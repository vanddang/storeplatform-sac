/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.common.support.redis;

/**
 * <p>
 * Lock 인터페이스와 유사한 구조로 해봅시다.
 * </p>
 * Updated on : 2015. 07. 22 Updated by : 정희원, SK 플래닛.
 */
public class DistributedLock {

    private String key;
    // lockKey = "$system_$instance_$time"

    public DistributedLock(Object lockKey) {
        this.key = "" + lockKey;

    }

    /**
     * 락을 수행한다. 만약 다른 스레드에 의해 수행중이라면 기다린다.
     */
    public void lock() {

    }

    /**
     * 락을 시도한다. 실행 시점에 lock이 되면 true를 응답하고 아닌 경우 false를 응답한다.
     * @return lock을 획득한 경우 true
     */
    public boolean tryLock() {
        return true;
    }

    /**
     * 락을 해제한다.
     */
    public void unlock() {

    }

    /**
     * 언락 상태를 기다린다. 언락이 되는 시점까지 대기상태가 유지되며 임계 시간동안 언락이 제대로 되지 않으면 false를 응답한다.
     * @return 언락이 제대로 된 경우 true, 임계 시간 안에 언락이 되지 않은 경우 false
     */
    public boolean waitUnlock() {
        return true;
    }
}
