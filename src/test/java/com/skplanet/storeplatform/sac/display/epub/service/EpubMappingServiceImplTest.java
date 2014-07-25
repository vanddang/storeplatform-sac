package com.skplanet.storeplatform.sac.display.epub.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.common.Source;
import com.skplanet.storeplatform.sac.client.product.vo.intfmessage.product.Preview;
import com.skplanet.storeplatform.sac.display.common.service.DisplayCommonService;
import com.skplanet.storeplatform.sac.display.epub.vo.EpubDetail;

@RunWith(MockitoJUnitRunner.class)
public class EpubMappingServiceImplTest {

	@Mock
	private DisplayCommonService commonSvc;

	@InjectMocks
	private EpubMappingServiceImpl svc;

	private final String DOMAIN = "http://qa-ids.tstore.co.kr:8012";

	@Before
	public void setUp() {
	}

	@Test
	public void testMapPreviewWithNoUrl() {
		EpubDetail mapperVO = new EpubDetail();
		mapperVO.setSamplUrl(null);

		Preview preview = this.svc.mapPreview(mapperVO);
		System.out.println("# testMapPreviewWithNoUrl :\n" + preview);

		List<Source> list = preview.getSourceList();
		assertNull(list);
	}

	@Test
	public void testMapPreviewWithZip() {
		String path = "/path1/path2/file.zip";
		when(this.commonSvc.makePreviewUrl(path)).thenReturn(this.DOMAIN + path);

		EpubDetail mapperVO = new EpubDetail();
		mapperVO.setSamplUrl(path);

		Preview preview = this.svc.mapPreview(mapperVO);
		System.out.println("# testMapPreviewWithZip : \n" + preview);

		List<Source> list = preview.getSourceList();
		assertSame(1, preview.getSourceList().size());

		Source source = list.get(0);
		assertEquals(this.DOMAIN + path, source.getUrl());
		assertEquals("epub/x-freeview", source.getType());
		assertEquals("application/zip", source.getMediaType());

		verify(this.commonSvc).makePreviewUrl(path);
	}

	@Test
	public void testMapPreviewWithEpub() {
		String path = "/a/b/c/abc.epub";
		when(this.commonSvc.makePreviewUrl(path)).thenReturn(this.DOMAIN + path);

		EpubDetail mapperVO = new EpubDetail();
		mapperVO.setSamplUrl(path);

		Preview preview = this.svc.mapPreview(mapperVO);
		System.out.println("# testMapPreviewWithEpub : \n" + preview);

		List<Source> list = preview.getSourceList();
		assertSame(1, preview.getSourceList().size());

		Source source = list.get(0);
		assertEquals(this.DOMAIN + path, source.getUrl());
		assertEquals("epub/x-freeview", source.getType());
		assertEquals("application/epub+zip", source.getMediaType());

		verify(this.commonSvc).makePreviewUrl(path);
	}

}
