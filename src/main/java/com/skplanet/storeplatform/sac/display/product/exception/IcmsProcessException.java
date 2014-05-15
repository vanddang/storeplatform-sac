/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.product.exception;

/**
 * <p>
 * 개발자센터 전처리 수행시 발생하는 예외. Icms에서 사용하는 오류코드와 메시지를 담기 위해 정의함.
 * </p>
 * Updated on : 2014. 05. 14 Updated by : 정희원, SK 플래닛.
 */
public class IcmsProcessException extends RuntimeException {

    private String code;

    public IcmsProcessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public IcmsProcessException(String code, Throwable e) {
        super(e);
        this.code = code;
    }

    public IcmsProcessException(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
