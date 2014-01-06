package com.skplanet.storeplatform.sac.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * HttpClient Util
 * 
 * Updated on : 2014. 1. 6. Updated by : 김경복, 부르칸.
 */
public class HttpUtil {

	private static final String CHAR_SET = "UTF-8";

	/**
	 * <pre>
	 * POST : URL + HEADER + CharSet
	 * </pre>
	 * 
	 * @param url
	 * @param headerMap
	 * @param charSet
	 * @return
	 */
	public String httpExecutePost(String url, Map<String, String> headerMap, String charSet) {
		return this.httpExecutePost(url, null, headerMap, null, charSet);
	}

	/**
	 * <pre>
	 * POST : URL + RequestParam + Header + CharSet
	 * </pre>
	 * 
	 * @param url
	 * @param httpMethod
	 * @param requestParam
	 * @param headerMap
	 * @param charSet
	 * @return
	 */
	public String httpExecutePost(String url, String requestParam, Map<String, String> headerMap, String charSet) {
		return this.httpExecutePost(url, requestParam, headerMap, null, charSet);
	}

	/**
	 * <pre>
	 * HttpClient(POST) Execute
	 * </pre>
	 * 
	 * @param url
	 * @param httpMethod
	 * @param requestParam
	 * @param headerMap
	 * @param contentType
	 * @param charSet
	 * @return
	 */
	@SuppressWarnings({ "resource", "deprecation" })
	public String httpExecutePost(String url, String requestParam, Map<String, String> headerMap, String contentType,
			String charSet) {
		String responseStr = "";

		try {
			HttpClient httpClient = new DefaultHttpClient();

			HttpPost httpPost = new HttpPost(url);

			/** ADD HEADER */
			if (headerMap != null) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				for (Entry<String, String> entry : headerMap.entrySet()) {
					paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(paramList, CHAR_SET));
			}

			/** POST - Resuest Param */
			StringEntity entity = new StringEntity(requestParam);
			entity.setContentType(contentType);
			entity.setContentEncoding(charSet);
			httpPost.setEntity(entity);

			/** Example */
			// StringEntity entity = new StringEntity(requestParam);
			// entity.setContentType("application/json");
			// entity.setContentType("application/xml");
			// entity.setContentEncoding("UTF-8");
			// entity.setContentEncoding("MS949");
			// httpPost.setEntity(entity);

			// Exceute Call
			HttpResponse response = httpClient.execute(httpPost);

			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				// throw new HttpException("Invalid status code. code[" + response.getStatusLine().getStatusCode() +
				// "]");
				return "Invalid status code. code[" + response.getStatusLine().getStatusCode() + "]";
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent()), charSet));
			String output;
			while ((output = br.readLine()) != null) {
				responseStr += output;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return responseStr;
	}

	/**
	 * <pre>
	 * GET : URL
	 * </pre>
	 * 
	 * @param url
	 * @return
	 */
	public String httpExecuteGet(String url) {
		return this.httpExecuteGet(url, null, CHAR_SET);
	}

	/**
	 * <pre>
	 * GET : URL + HEADER
	 * </pre>
	 * 
	 * @param url
	 * @param headerMap
	 * @return
	 */
	public String httpExecuteGet(String url, Map<String, String> headerMap) {
		return this.httpExecuteGet(url, headerMap, CHAR_SET);
	}

	/**
	 * <pre>
	 * HttpClient(GET) Exceute
	 * </pre>
	 * 
	 * @param url
	 * @param headerMap
	 * @param charSet
	 * @return
	 */
	@SuppressWarnings({ "deprecation", "resource" })
	public String httpExecuteGet(String url, Map<String, String> headerMap, String charSet) {

		String responseStr = "";

		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);

			// Exceute Call
			HttpResponse response = httpClient.execute(httpGet);

			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				// throw new HttpException("Invalid status code. code[" + response.getStatusLine().getStatusCode() +
				// "]");
				return "Invalid status code. code[" + response.getStatusLine().getStatusCode() + "]";
			}

			if (headerMap != null) {
				Iterator<String> iter = headerMap.keySet().iterator();
				while (iter.hasNext()) {
					String key = iter.next();
					httpGet.addHeader(key, headerMap.get(key));
				}
			}

			HttpEntity entity = response.getEntity();

			BufferedReader br = null;
			if (entity == null)
				return null;
			InputStream is = entity.getContent();
			br = new BufferedReader(new InputStreamReader(is, charSet));
			String output;
			while ((output = br.readLine()) != null) {
				responseStr += output;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return responseStr;
	}
}
