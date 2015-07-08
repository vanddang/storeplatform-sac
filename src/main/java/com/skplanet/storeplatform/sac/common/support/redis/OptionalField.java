package com.skplanet.storeplatform.sac.common.support.redis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * OptionalField
 * 레디스 적재 데이터를 Object로 변환시 이것이 선언되어 있으면 null 값을 허용한다.
 * 이것이 선언되어 있지 않으면 객체 변환시 필드가 누락되었을때 DB에서 재 적재를 수행한다.
 * User: 1002210
 * Date: 15. 7. 6.
 * Time: 오후 5:19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface OptionalField {
}
