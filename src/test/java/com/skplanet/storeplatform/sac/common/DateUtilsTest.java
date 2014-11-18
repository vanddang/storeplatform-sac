package com.skplanet.storeplatform.sac.common;

import com.skplanet.storeplatform.sac.common.util.DateUtils;
import org.junit.Test;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: joyspring
 * Date: 2/5/14
 * Time: 7:22 PM
 */
public class DateUtilsTest {
    @Test
    public void test1() {
        Date dt = DateUtils.parseDate("1981-11-22");
        assert dt != null;

        Date dt2 = DateUtils.parseDate("20141122");
        assert dt2 != null;
    }

    @Test
    public void test2() {
        Date dt = DateUtils.parseDate("20140203121100");
        assert dt != null;
    }

    @Test
    public void test3() {
        Date dt = DateUtils.parseDate("20140203192023");
        assert dt != null;
    }

}
