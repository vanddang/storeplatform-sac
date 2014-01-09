package com.skplanet.storeplatform.sac.example.sample.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.skplanet.storeplatform.external.client.example.sample.vo.Sample;
import com.skplanet.storeplatform.sac.example.sample.repository.SampleRepository;

@RunWith(MockitoJUnitRunner.class)
public class SampleServiceImplTest {

	@Mock
	private SampleRepository repository;

	@InjectMocks
	private SampleServiceImpl service;


	@Test
	public void test() {
		Sample expectedOutput = new Sample(17);
		expectedOutput.setId("#713");
		expectedOutput.setName("Sample User");
		expectedOutput.setDescription("Description");
		expectedOutput.setDate(new Date());

		when(this.repository.detailFromEC(17)).thenReturn(expectedOutput);

		Sample actualOutput = this.service.detail(17);

		assertEquals(expectedOutput, actualOutput);
		verify(this.repository).detailFromEC(17);
	}

}
