/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.member.repository;

import com.skplanet.storeplatform.sac.member.domain.mbr.UserCaptcha;

/**
 * Captcha Repository
 * Updated on : 2016. 02. 07 Updated by : 임근대, SK 플래닛.
 */
public interface UserCaptchaRepository {

    void save(UserCaptcha userCaptcha);
    void remove(UserCaptcha userCaptcha);
    UserCaptcha findByImageSignAndSignData(String imageSign, String signData);

}
