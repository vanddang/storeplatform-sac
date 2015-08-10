package com.skplanet.storeplatform.sac.runtime.bypass.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BypassHeaderMapperTest.class, BypassInvokerTest.class, BypassServiceImplTest.class, BypassUriBuilderTest.class })
public class BypassTestSuite {
}
