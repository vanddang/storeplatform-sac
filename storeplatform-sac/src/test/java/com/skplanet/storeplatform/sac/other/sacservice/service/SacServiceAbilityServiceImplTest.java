package com.skplanet.storeplatform.sac.other.sacservice.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	
	private static final String TEST_SERVICE_CD = "tstore.test";

	@Test
	public void testIsServiceEnabledWithNullService() {		
		SacService testInput = getMockInput1();
		when(dataSvc.selectService(TEST_SERVICE_CD)).thenReturn(null);
		
		boolean enabled = abiltySvc.isServiceEnabled(testInput);
		assertFalse(enabled);
		verify(dataSvc).selectService(TEST_SERVICE_CD);
	}
	
	@Test
	public void testIsServiceEnabledWithNullServiceStatus() {
		SacService testInput = getMockInput1();
		SacService mockOutput = new SacService(TEST_SERVICE_CD);
		mockOutput.setStatus(null);
		when(dataSvc.selectService(TEST_SERVICE_CD)).thenReturn(mockOutput);
		
		boolean enabled = abiltySvc.isServiceEnabled(testInput);
		assertFalse(enabled);
		verify(dataSvc).selectService(TEST_SERVICE_CD);
	}
	
	@Test
	public void testIsServiceEnabledWithServiceStatusDisabled() {
		SacService testInput = getMockInput1();
		SacService mockOutput = new SacService(TEST_SERVICE_CD);
		mockOutput.setStatus(SacServiceStatus.DISABLED);
		when(dataSvc.selectService(TEST_SERVICE_CD)).thenReturn(mockOutput);
		
		boolean enabled = abiltySvc.isServiceEnabled(testInput);
		assertFalse(enabled);
		verify(dataSvc).selectService(TEST_SERVICE_CD);
	}
	
	@Test
	public void testIsServiceEnabledWithServiceStatusEnabledAndSimOperatorNullAndModelNull() {
		SacService testInput = getMockInput1();
		SacService mockOutput = new SacService(TEST_SERVICE_CD);
		mockOutput.setStatus(SacServiceStatus.ENABLED);
		mockOutput.setSimOperatorAuthType(null);
		mockOutput.setModelAuthType(null);
		when(dataSvc.selectService(TEST_SERVICE_CD)).thenReturn(mockOutput);
		
		boolean enabled = abiltySvc.isServiceEnabled(testInput);
		assertFalse(enabled);
		verify(dataSvc).selectService(TEST_SERVICE_CD);
	}
	
	@Test
	public void testIsServiceEnabledWithServiceStatusEnabledAndSimOperatorAllAndModelAll() {
		SacService testInput = getMockInput1();
		SacService mockOutput = new SacService(TEST_SERVICE_CD);
		mockOutput.setStatus(SacServiceStatus.ENABLED);
		mockOutput.setSimOperatorAuthType(SacServiceAuthType.ALL);
		mockOutput.setModelAuthType(SacServiceAuthType.ALL);
		when(dataSvc.selectService(TEST_SERVICE_CD)).thenReturn(mockOutput);
		
		boolean enabled = abiltySvc.isServiceEnabled(testInput);
		assertTrue(enabled);
		verify(dataSvc).selectService(TEST_SERVICE_CD);
	}
	
	@Test
	public void testIsServiceEnabledWithServiceStatusEnabledAndSimOperatorEmptyListAndModelEmptyList() {
		SacService mockService = new SacService(TEST_SERVICE_CD);
		mockService.setStatus(SacServiceStatus.ENABLED);
		mockService.setSimOperatorAuthType(SacServiceAuthType.BLACK);
		mockService.setModelAuthType(SacServiceAuthType.WHITE);
		
		when(dataSvc.selectService(TEST_SERVICE_CD)).thenReturn(mockService);
		when(dataSvc.selectSimOperatorList(TEST_SERVICE_CD)).thenReturn(new ArrayList<String>());
		when(dataSvc.selectModelList(TEST_SERVICE_CD)).thenReturn(new ArrayList<String>());
		
		SacService testInput = getMockInput1();
		assertFalse(abiltySvc.isServiceEnabled(testInput));
		
		verify(dataSvc).selectService(TEST_SERVICE_CD);
		verify(dataSvc).selectSimOperatorList(TEST_SERVICE_CD);
		verify(dataSvc).selectModelList(TEST_SERVICE_CD);
	}
	
	@Test
	public void testIsServiceEnabledWithServiceStatusEnabledAndSimOperatorWhiteAndModelBlack() {
		SacService mockService = new SacService(TEST_SERVICE_CD);
		mockService.setStatus(SacServiceStatus.ENABLED);
		mockService.setSimOperatorAuthType(SacServiceAuthType.WHITE);
		mockService.setModelAuthType(SacServiceAuthType.BLACK);
		List<String> mockSimOperatorList = Arrays.asList("450/03", "450/05", "450/11");
		List<String> mockModelList = Arrays.asList("SHW-M110K");
		
		when(dataSvc.selectService(TEST_SERVICE_CD)).thenReturn(mockService);
		when(dataSvc.selectSimOperatorList(TEST_SERVICE_CD)).thenReturn(mockSimOperatorList);
		when(dataSvc.selectModelList(TEST_SERVICE_CD)).thenReturn(mockModelList);
		
		SacService testInput = getMockInput1();
		assertTrue(abiltySvc.isServiceEnabled(testInput));
		
		testInput = getMockInput2();
		assertFalse(abiltySvc.isServiceEnabled(testInput));
		
		testInput = getMockInput3();
		assertFalse(abiltySvc.isServiceEnabled(testInput));
		
		verify(dataSvc, times(3)).selectService(TEST_SERVICE_CD);
		verify(dataSvc, times(3)).selectSimOperatorList(TEST_SERVICE_CD);
		verify(dataSvc, times(2)).selectModelList(TEST_SERVICE_CD);
	}
	
	private static SacService getMockInput1() {
		SacService service = new SacService(TEST_SERVICE_CD);
		service.setSimOperator("450/05"); // SK
		service.setModel("SHW-M110S");
		return service;
	}
	
	private static SacService getMockInput2() {
		SacService service = new SacService(TEST_SERVICE_CD);
		service.setSimOperator("450/02"); // KT (Wrong Sim Operator)
		service.setModel("SHW-M110S");
		return service;
	}
	
	private static SacService getMockInput3() {
		SacService service = new SacService(TEST_SERVICE_CD);
		service.setSimOperator("450/05");
		service.setModel("SHW-M110K"); // Wrong Model
		return service;
	}

}
