package com.skplanet.storeplatform.framework;

import com.skplanet.storeplatform.framework.core.persistence.dao.CommonDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by eunbongc on 2015-05-15.
 */
@ActiveProfiles(value = "local")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/spring-test/context-test.xml"})
@WebAppConfiguration
public class CommonDAOTest {

    @Autowired
    @Qualifier("scMember")
    private CommonDAO commonDAO;

    @Autowired
    @Qualifier("scIdleMember")
    private CommonDAO scIdleMemberDAO;

    @Test
    public void testCreateBean() {
    }
}
