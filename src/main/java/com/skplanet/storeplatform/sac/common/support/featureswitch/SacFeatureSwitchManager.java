/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.common.support.featureswitch;

/**
 * <pre>
 * SacFeatureSwitchComponent
 * 기능 스위치 컴포넌트. 스위치 값을 조회하여 동적으로 기능들을 변경할 수 있다.
 *
 * Version 1 (현재)
 * - Redis의 bitmap 이용
 * - Request당 Redis access는 1회로 제한한다.
 * - Redis연결이 원활하지 않아 값을 얻을 수 없으면 0 또는 false값을 리턴하여 동작을 보장해준다.
 *
 * Version 2 (차후: Redis의 pub/sub 기능 필요)
 * - 스위치 값은 Redis 또는 DB를 이용하여 관리한다
 * - 서버 기동시 스위치 값을 읽어 로컬 메모리 구조에 저장한다
 * - Redis의 pub/sub기능을 이용해 동기화 요청을 받으면 스위치값을 다시 읽어 적재한다.
 * - 동기화 처리시에는 lock을 건다
 * </pre>
 * Updated on : 2015. 08. 12 Updated by : 정희원, SK 플래닛.
 */
public interface SacFeatureSwitchManager {

    /**
     * 스위치 값을 로컬에 동기화 한다. (Version 2에서 사용)
     * @return 스위치 값들에 대한 해시값. 값이 정상적으로 반영되었는지 확인하는 용도로 쓰인다.
     */
    int refreshValues();
}
