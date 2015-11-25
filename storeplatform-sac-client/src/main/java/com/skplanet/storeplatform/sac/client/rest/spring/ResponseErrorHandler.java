package com.skplanet.storeplatform.sac.client.rest.spring;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpMessageConverterExtractor;

import com.skplanet.storeplatform.sac.client.rest.error.SacRestClientError;
import com.skplanet.storeplatform.sac.client.rest.error.SacRestClientException;

public class ResponseErrorHandler extends DefaultResponseErrorHandler {

	private final List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();

	public ResponseErrorHandler() {
		this.messageConverters.add(new MappingJacksonHttpMessageConverter());
	}

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		// It has errors, if the result code is "[FAIL]".
		String resultCode = String.valueOf(response.getHeaders().get("x-sac-result-code"));
		if (StringUtils.equalsIgnoreCase(resultCode, "[FAIL]")) {
			return true;
		}

		// It has errors, if the status code is 4XX or 5XX.
		return super.hasError(response);
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		HttpMessageConverterExtractor<SacRestClientError> responseExtractor = new HttpMessageConverterExtractor<SacRestClientError>(SacRestClientError.class, this.messageConverters);
		SacRestClientError errorInfo = responseExtractor.extractData(response);
		throw new SacRestClientException(errorInfo);
	}

}
