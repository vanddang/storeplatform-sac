package com.skplanet.storeplatform.sac.client.rest.apache;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;

import com.skplanet.storeplatform.sac.client.rest.error.SacRestClientError;
import com.skplanet.storeplatform.sac.client.rest.error.SacRestClientException;
import com.skplanet.storeplatform.sac.client.rest.util.SacRestConvertingUtils;

public class ResponseExtractor {

	public static <T> T extractData(HttpResponse response, Class<T> responseType) {
		String json = extractJson(response);

		if (hasError(response)) {
			SacRestClientError errorInfo = SacRestConvertingUtils.convertToData(json, SacRestClientError.class);
			throw new SacRestClientException(errorInfo);
		} else {
			T data = SacRestConvertingUtils.convertToData(json, responseType);
			return data;
		}
	}

	private static boolean hasError(HttpResponse response) {
		try {
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode >= 300) {
				return true;
			}

			Header resultHeader = response.getHeaders("x-sac-result-code")[0];
			if (!StringUtils.equalsIgnoreCase(resultHeader.getValue(), "SUCC")) {
				return true;
			}
		} catch (Exception e) {
			return true;
		}

		return false;
	}

	private static String extractJson(HttpResponse response) {
		String json;
		try {
			InputStream in = response.getEntity().getContent();
			json = IOUtils.toString(in);
		} catch (Exception e) {
    		throw new RuntimeException("Failed to extract Json String from the response.", e);
		}
		return json;
	}

}
