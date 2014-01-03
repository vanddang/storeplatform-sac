package com.skplanet.storeplatform.sac.sample.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.skplanet.storeplatform.external.client.sample.vo.Sample;
import com.skplanet.storeplatform.sac.sample.repository.SampleRepository;

@RunWith(MockitoJUnitRunner.class)
public class SampleServiceImplTest {

	@Mock
	private SampleRepository repository;

	@InjectMocks
	private SampleServiceImpl service;


	@Test
	public void test() {
		Sample input = new Sample(17);

		Sample expectedOutput = new Sample(17);
		expectedOutput.setId("#713");
		expectedOutput.setName("Sample User");
		expectedOutput.setDescription("Description");
		expectedOutput.setDate(new Date());

		when(this.repository.getDetailFromEC(input)).thenReturn(expectedOutput);

		Sample actualOutput = this.service.getDetail(input);

		assertEquals(expectedOutput, actualOutput);
		verify(this.repository).getDetailFromEC(input);
	}

}
