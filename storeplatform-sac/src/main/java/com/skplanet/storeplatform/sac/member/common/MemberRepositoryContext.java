/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.common;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * <p>
 * JPA 저장소 대상을 선택한다.
 * </p>
 * Updated on : 2016. 01. 18 Updated by : 정희원, SK 플래닛.
 */
public class MemberRepositoryContext {

    public static final String NAME = "mbr.repositoryTarget";

    public enum RepositoryTarget { Normal, Idle }

    public static void setTarget(RepositoryTarget tgt) {
        RequestContextHolder.currentRequestAttributes().setAttribute("mbr.repositoryTarget", tgt, RequestAttributes.SCOPE_REQUEST);
    }

    public static RepositoryTarget getTarget() {
        Object v = RequestContextHolder.currentRequestAttributes().getAttribute(NAME, RequestAttributes.SCOPE_REQUEST);
        if( v == null)
            return RepositoryTarget.Normal;

        return (RepositoryTarget) v;
    }

    public static boolean isNormal() {
        return getTarget() == RepositoryTarget.Normal;
    }
}
