package com.skplanet.storeplatform.sac.common.environment;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.env.Environment;

import com.skplanet.storeplatform.sac.common.environment.DbTableNameManager;

@RunWith(MockitoJUnitRunner.class)
public class DbTableNameManagerTest {
	
	@InjectMocks
	private DbTableNameManager manager;
	
	@Mock
	private Environment environment;

	@Test
	public void test() {
		when(environment.getActiveProfiles()).thenReturn(null);
		assertEquals("TB_FW_COMPONENT", manager.fixDbTableName("TB_FW_COMPONENT"));
		
		when(environment.getActiveProfiles()).thenReturn(new String[]{});
		assertEquals("TB_FW_COMPONENT", manager.fixDbTableName("TB_FW_COMPONENT"));
		
		when(environment.getActiveProfiles()).thenReturn(new String[]{"local"});
		assertEquals("TB_FW_COMPONENT", manager.fixDbTableName("TB_FW_COMPONENT"));
		
		when(environment.getActiveProfiles()).thenReturn(new String[]{"qa"});
		assertEquals("TB_FW_COMPONENT", manager.fixDbTableName("TB_FW_COMPONENT"));
		
		when(environment.getActiveProfiles()).thenReturn(new String[]{"stg"});
		assertEquals("TB_FW_COMPONENT_STG$", manager.fixDbTableName("TB_FW_COMPONENT"));
		
		when(environment.getActiveProfiles()).thenReturn(new String[]{"prod"});
		assertEquals("TB_FW_COMPONENT", manager.fixDbTableName("TB_FW_COMPONENT"));
	}

}
