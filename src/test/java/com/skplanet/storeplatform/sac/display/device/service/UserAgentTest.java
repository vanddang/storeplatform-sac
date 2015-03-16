/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.display.device.service;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * <pre>
 * UserAgent Parsing Test
 * <pre>
 *
 * Updated on : 2015-03-16. Updated By : 양해엽, SK Planet.
 */
public class UserAgentTest {

    @Test
    public void userAgentParsing() {
        /**
         * nested parentheses for two depth
         */
        Pattern pattern = Pattern.compile("\\((([^()]|(\\([^()]*\\)))*)\\)");

        String userAgentString;
        Matcher matcher;

        userAgentString = "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.110 Safari/537.36";
        matcher = pattern.matcher(userAgentString);

        assertTrue(matcher.find());
        assertEquals(matcher.groupCount(), 3);
        assertEquals("(Windows NT 5.1)", matcher.group(0));
        assertEquals("Windows NT 5.1", matcher.group(1));

        userAgentString = "Mozilla/5.0 (Linux; U; Android 2.3.6; ko-kr; SHW-M110S Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1 NAVER(inapp; search; 321; 5.8.6)";
        matcher = pattern.matcher(userAgentString);

        assertTrue(matcher.find());
        assertEquals(matcher.groupCount(), 3);
        assertEquals("(Linux; U; Android 2.3.6; ko-kr; SHW-M110S Build/GINGERBREAD)", matcher.group(0));
        assertEquals("Linux; U; Android 2.3.6; ko-kr; SHW-M110S Build/GINGERBREAD", matcher.group(1));

        userAgentString = "Mozilla/5.0 (Linux; Android 4.0.4; Micromax P500(Funbook) Build/IMM76D) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.59 Safari/537.36";
        matcher = pattern.matcher(userAgentString);

        assertTrue(matcher.find());
        assertEquals(matcher.groupCount(), 3);
        assertEquals("(Linux; Android 4.0.4; Micromax P500(Funbook) Build/IMM76D)", matcher.group(0));
        assertEquals("Linux; Android 4.0.4; Micromax P500(Funbook) Build/IMM76D", matcher.group(1));

    }
}
