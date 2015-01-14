package com.skplanet.storeplatform.sac.other.sacservice.service;

import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.skplanet.storeplatform.sac.other.sacservice.vo.SacService;
import com.skplanet.storeplatform.sac.other.sacservice.vo.SacServiceAuthType;
import com.skplanet.storeplatform.sac.other.sacservice.vo.SacServiceStatus;

@RunWith(MockitoJUnitRunner.class)
public class SacServiceAbilityServiceImplTest {
	
	@InjectMocks
	private SacServiceAbilityServiceImpl abiltySvc;
	
	@Mock
	private SacServiceDataService dataSvc;
	
	@Mock
	private SacServiceAuthService authSvc;
	
	private static final String TEST_SERVICE_CD = "tstore.test";
	private static final String TEST_TENANT_ID = "S01";
	private static final String TEST_SIM_OPERATOR = "450/05";

	@Test
	public void withNullService() {		
		when(dataSvc.selectService(TEST_SERVICE_CD)).thenReturn(null);
		
		SacService testInput = new SacService(TEST_SERVICE_CD);
		assertFalse(abiltySvc.isServiceEnabled(testInput));

		verify(dataSvc).selectService(TEST_SERVICE_CD);
	}
	
	@Test
	public void withNullServiceStatus() {
		SacService mockOutput = new SacService(TEST_SERVICE_CD);
		mockOutput.setStatus(null);
		when(dataSvc.selectService(TEST_SERVICE_CD)).thenReturn(mockOutput);
		
		SacService testInput = new SacService(TEST_SERVICE_CD);
		assertFalse(abiltySvc.isServiceEnabled(testInput));
		
		verify(dataSvc).selectService(TEST_SERVICE_CD);
	}
	
	@Test
	public void withServiceStatusDisabled() {
		SacService mockOutput = new SacService(TEST_SERVICE_CD);
		mockOutput.setStatus(SacServiceStatus.DISABLED);
		when(dataSvc.selectService(TEST_SERVICE_CD)).thenReturn(mockOutput);
		
		SacService testInput = new SacService(TEST_SERVICE_CD);
		assertFalse(abiltySvc.isServiceEnabled(testInput));
		
		verify(dataSvc).selectService(TEST_SERVICE_CD);
	}
	
	@Test
	public void withTeanntUnauthorized() {
		SacService mockOutput = new SacService(TEST_SERVICE_CD);
		mockOutput.setStatus(SacServiceStatus.ENABLED);
		mockOutput.setTenantAuthType(SacServiceAuthType.WHITE);
		when(dataSvc.selectService(TEST_SERVICE_CD)).thenReturn(mockOutput);
		
		when(authSvc.isTenantAuthorized(SacServiceAuthType.WHITE, TEST_SERVICE_CD, TEST_TENANT_ID)).thenReturn(false);
		
		SacService testInput = new SacService(TEST_SERVICE_CD);
		testInput.setTenantId(TEST_TENANT_ID);
		
		assertFalse(abiltySvc.isServiceEnabled(testInput));
		
		verify(dataSvc).selectService(TEST_SERVICE_CD);
		verify(authSvc).isTenantAuthorized(SacServiceAuthType.WHITE, TEST_SERVICE_CD, TEST_TENANT_ID);
	}
	
	@Test
	public void withScheduleUnauthorized() {
		SacService mockOutput = new SacService(TEST_SERVICE_CD);
		mockOutput.setStatus(SacServiceStatus.ENABLED);
		mockOutput.setTenantAuthType(SacServiceAuthType.ALL);
		mockOutput.setScheduleAuthType(SacServiceAuthType.BLACK);
		when(dataSvc.selectService(TEST_SERVICE_CD)).thenReturn(mockOutput);
		
		when(authSvc.isTenantAuthorized(eq(SacServiceAuthType.ALL), anyString(), anyString())).thenReturn(true);
		when(authSvc.isScheduleAuthorized(SacServiceAuthType.BLACK, TEST_SERVICE_CD)).thenReturn(false);
		
		SacService testInput = new SacService(TEST_SERVICE_CD);
		
		assertFalse(abiltySvc.isServiceEnabled(testInput));
		
		verify(dataSvc).selectService(TEST_SERVICE_CD);
		verify(authSvc).isTenantAuthorized(eq(SacServiceAuthType.ALL), anyString(), anyString());
		verify(authSvc).isScheduleAuthorized(SacServiceAuthType.BLACK, TEST_SERVICE_CD);
	}
	
	@Test
	public void withSimOperatorUnauthorized() {
		SacService mockOutput = new SacService(TEST_SERVICE_CD);
		mockOutput.setStatus(SacServiceStatus.ENABLED);
		mockOutput.setTenantAuthType(SacServiceAuthType.ALL);
		mockOutput.setScheduleAuthType(SacServiceAuthType.ALL);
		mockOutput.setSimOperatorAuthType(SacServiceAuthType.WHITE);
		when(dataSvc.selectService(TEST_SERVICE_CD)).thenReturn(mockOutput);
		
		when(authSvc.isTenantAuthorized(eq(SacServiceAuthType.ALL), anyString(), anyString())).thenReturn(true);
		when(authSvc.isScheduleAuthorized(eq(SacServiceAuthType.ALL), anyString())).thenReturn(true);
		when(authSvc.isSimOperatorAuthorized(SacServiceAuthType.WHITE, TEST_SERVICE_CD, TEST_SIM_OPERATOR)).thenReturn(false);
		
		SacService testInput = new SacService(TEST_SERVICE_CD);
		testInput.setSimOperator(TEST_SIM_OPERATOR);
		
		assertFalse(abiltySvc.isServiceEnabled(testInput));
		
		verify(dataSvc).selectService(TEST_SERVICE_CD);
		verify(authSvc).isTenantAuthorized(eq(SacServiceAuthType.ALL), anyString(), anyString());
		verify(authSvc).isScheduleAuthorized(eq(SacServiceAuthType.ALL), anyString());
		verify(authSvc).isSimOperatorAuthorized(SacServiceAuthType.WHITE, TEST_SERVICE_CD, TEST_SIM_OPERATOR);
	}

}
