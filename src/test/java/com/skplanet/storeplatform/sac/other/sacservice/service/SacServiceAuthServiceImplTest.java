package com.skplanet.storeplatform.sac.other.sacservice.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.skplanet.storeplatform.sac.other.sacservice.vo.SacServiceAuthType;
import com.skplanet.storeplatform.sac.other.sacservice.vo.SacServiceSchedule;

@RunWith(MockitoJUnitRunner.class)
public class SacServiceAuthServiceImplTest {

	private static final String TEST_SERVICE_CD = "tstore.test";
	private static final String TEST_TENANT_ID = "S01";
	
	@InjectMocks
	private SacServiceAuthServiceImpl authSvc;
	
	@Mock
	private SacServiceDataService dataSvc;
	
	@Test
	public void isTenantAuthorized() {
		assertFalse(authSvc.isTenantAuthorized(null, TEST_SERVICE_CD, TEST_TENANT_ID));
		assertTrue(authSvc.isTenantAuthorized(SacServiceAuthType.ALL, TEST_SERVICE_CD, TEST_TENANT_ID));
		
		when(dataSvc.selectTenantList(TEST_SERVICE_CD)).thenReturn(Arrays.asList("S01", "S02", "S03"));
		
		assertTrue(authSvc.isTenantAuthorized(SacServiceAuthType.WHITE, TEST_SERVICE_CD, "S01"));
		assertFalse(authSvc.isTenantAuthorized(SacServiceAuthType.WHITE, TEST_SERVICE_CD, "S00"));
		assertFalse(authSvc.isTenantAuthorized(SacServiceAuthType.BLACK, TEST_SERVICE_CD, "S01"));
		assertTrue(authSvc.isTenantAuthorized(SacServiceAuthType.BLACK, TEST_SERVICE_CD, "S00"));
		
		verify(dataSvc, times(4)).selectTenantList(TEST_SERVICE_CD);
	}
	
	@Test
	public void isScheduleAuthorized() {
		assertFalse(authSvc.isScheduleAuthorized(null, TEST_SERVICE_CD));
		assertTrue(authSvc.isScheduleAuthorized(SacServiceAuthType.ALL, TEST_SERVICE_CD));
		
		Calendar startCalendar;
		Calendar endCalendar;
		
		List<SacServiceSchedule> list = new ArrayList<SacServiceSchedule>();
		
		startCalendar = Calendar.getInstance(); 
		endCalendar = Calendar.getInstance();
		startCalendar.add(Calendar.HOUR_OF_DAY, -10);
		endCalendar.add(Calendar.HOUR_OF_DAY, -5);
		list.add(new SacServiceSchedule(1, startCalendar.getTime(), endCalendar.getTime()));
		
		startCalendar = Calendar.getInstance(); 
		endCalendar = Calendar.getInstance();
		startCalendar.add(Calendar.HOUR_OF_DAY, -5);
		endCalendar.add(Calendar.HOUR_OF_DAY, 5);
		list.add(new SacServiceSchedule(1, startCalendar.getTime(), endCalendar.getTime()));
		
		startCalendar = Calendar.getInstance(); 
		endCalendar = Calendar.getInstance();
		startCalendar.add(Calendar.HOUR_OF_DAY, 5);
		endCalendar.add(Calendar.HOUR_OF_DAY, 10);
		list.add(new SacServiceSchedule(1, startCalendar.getTime(), endCalendar.getTime()));
		
		when(dataSvc.selectScheduleList(TEST_SERVICE_CD)).thenReturn(list);
		
		assertTrue(authSvc.isScheduleAuthorized(SacServiceAuthType.WHITE, TEST_SERVICE_CD));
		assertFalse(authSvc.isScheduleAuthorized(SacServiceAuthType.BLACK, TEST_SERVICE_CD));
		
		verify(dataSvc, times(2)).selectScheduleList(TEST_SERVICE_CD);
	}
	
	@Test
	public void isSimOperatorAuthorized() {
		assertFalse(authSvc.isSimOperatorAuthorized(null, TEST_SERVICE_CD, TEST_TENANT_ID));
		assertTrue(authSvc.isSimOperatorAuthorized(SacServiceAuthType.ALL, TEST_SERVICE_CD, TEST_TENANT_ID));
		
		when(dataSvc.selectSimOperatorList(TEST_SERVICE_CD)).thenReturn(Arrays.asList("450/02", "450/05"));
		
		assertTrue(authSvc.isSimOperatorAuthorized(SacServiceAuthType.WHITE, TEST_SERVICE_CD, "450/05"));
		assertFalse(authSvc.isSimOperatorAuthorized(SacServiceAuthType.WHITE, TEST_SERVICE_CD, "450/06"));
		assertFalse(authSvc.isSimOperatorAuthorized(SacServiceAuthType.BLACK, TEST_SERVICE_CD, "450/05"));
		assertTrue(authSvc.isSimOperatorAuthorized(SacServiceAuthType.BLACK, TEST_SERVICE_CD, "450/06"));
		
		verify(dataSvc, times(4)).selectSimOperatorList(TEST_SERVICE_CD);
	}
	
	@Test
	public void isModelAuthorized() {
		assertFalse(authSvc.isModelAuthorized(null, TEST_SERVICE_CD, TEST_TENANT_ID));
		assertTrue(authSvc.isModelAuthorized(SacServiceAuthType.ALL, TEST_SERVICE_CD, TEST_TENANT_ID));
		
		when(dataSvc.selectModelList(TEST_SERVICE_CD)).thenReturn(Arrays.asList("SHW-M110S"));
		
		assertTrue(authSvc.isModelAuthorized(SacServiceAuthType.WHITE, TEST_SERVICE_CD, "SHW-M110S"));
		assertFalse(authSvc.isModelAuthorized(SacServiceAuthType.WHITE, TEST_SERVICE_CD, "SHW-M110K"));
		assertFalse(authSvc.isModelAuthorized(SacServiceAuthType.BLACK, TEST_SERVICE_CD, "SHW-M110S"));
		assertTrue(authSvc.isModelAuthorized(SacServiceAuthType.BLACK, TEST_SERVICE_CD, "SHW-M110K"));
		
		verify(dataSvc, times(4)).selectModelList(TEST_SERVICE_CD);
	}

}
