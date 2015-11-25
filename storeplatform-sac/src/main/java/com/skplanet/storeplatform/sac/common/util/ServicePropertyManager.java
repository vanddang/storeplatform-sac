/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.common.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * ServicePropertyManager.
 * 서비스에 대한 속성들을 관리한다.
 * TODO 서비스 기동시 속성들을 읽고 이를 로컬캐시에서 관리하고 실시간으로 해당 속성들을 관리할 수 있도록 개선한다.
 * </p>
 * Updated on : 2015. 03. 10 Updated by : 정희원, SK 플래닛.
 */
public class ServicePropertyManager {

    private static List<String> SUPPORT_TENANT_LIST = Collections.unmodifiableList(Arrays.asList("S01", "S02", "S03"));

    /**
     * 서비스중인 테넌트 목록을 조회한다.
     * @return 지원되는 테넌트 목록. Null값은 응답되지 않는다.
     */
    public static List<String> getSupportTenantList() {
        return SUPPORT_TENANT_LIST;
    }
}
