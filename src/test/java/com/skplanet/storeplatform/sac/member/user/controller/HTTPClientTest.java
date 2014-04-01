package com.skplanet.storeplatform.sac.member.user.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.common.base.Strings;
import com.skplanet.storeplatform.framework.core.exception.vo.ErrorInfo;
import com.skplanet.storeplatform.sac.client.member.vo.common.DeviceExtraInfo;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateBySimpleReq;
import com.skplanet.storeplatform.sac.client.member.vo.user.CreateBySimpleRes;

public class HTTPClientTest {

	private final HttpClient client = HttpClientBuilder.create().build();

	private final ObjectMapper mapper = new ObjectMapper();

	public static void main(String[] args) {

		HTTPClientTest test = new HTTPClientTest();

		try {

			int count = 3; // 생성 갯수
			String userId = "tlaeotest-"; // 생성 User Id + 4자리숫자
			String deviceId = "0151239"; // 생성 DeviceId + 4자리 숫자

			for (int i = 1; i <= count; i++) {

				String number = Strings.padStart(i + "", 4, '0');
				test.testPost(userId + number, deviceId + number);

			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * <pre>
	 * 간편 회원 가입.
	 * </pre>
	 * 
	 * @param userId
	 * @param deviceId
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void testPost(String userId, String deviceId) throws ClientProtocolException, IOException {

		String url = "http://211.188.207.142/sp_sac/member/user/createBySimple/v1"; // 개발
		// String url = "http://qa-store.sungsu.skplanet.com/sp_sac/member/user/createBySimple/v1"; // QA

		HttpPost request = new HttpPost(url);

		request.addHeader("Content-Type", "application/json;charset=UTF-8");
		request.addHeader("Accept", "application/json;charset=UTF-8");

		CreateBySimpleReq reqJson = new CreateBySimpleReq();

		// 사용자 아이디
		reqJson.setUserId(userId); // 대문자 ID는 가입 불가 IDP 정책.
		reqJson.setUserPw("abcd1234");
		reqJson.setUserEmail(userId + "@naver.com");

		// 단말 정보
		reqJson.setDeviceId(deviceId); // 기기 ID
		reqJson.setDeviceIdType("msisdn"); // 기기 ID 타입
		reqJson.setDeviceTelecom("US001202"); // 통신사
		reqJson.setNativeId("A031648EE9TEST"); // 기기 고유 ID (IMEI)
		reqJson.setDeviceAccount(userId + "@naver.com"); // 기기 계정 (Gmail)
		reqJson.setJoinId("US002903"); // 가입채널코드
		reqJson.setIsRecvSms("Y"); // SMS 수신 여부

		// 단말 부가 정보 리스트
		List<DeviceExtraInfo> deviceExtraList = new ArrayList<DeviceExtraInfo>();
		DeviceExtraInfo deviceExtraInfo = new DeviceExtraInfo();
		deviceExtraInfo.setExtraProfile("US011407");
		deviceExtraInfo.setExtraProfileValue("3.0");
		deviceExtraList.add(deviceExtraInfo);
		reqJson.setDeviceExtraInfoList(deviceExtraList);

		String reqTxt = this.mapper.writeValueAsString(reqJson);
		System.out.println(reqTxt);

		request.setEntity(new StringEntity(reqTxt));

		HttpResponse response = this.client.execute(request);

		if (this.hasError(response)) {
			System.out.println("has Error");
			InputStream in = response.getEntity().getContent();

			ErrorInfo resObj = this.mapper.readValue(in, ErrorInfo.class);
			System.out.println(resObj);
		} else {
			System.out.println("no Error");
			InputStream in = response.getEntity().getContent();
			// String resTxt = IOUtils.toString(in);
			// System.out.println(resTxt);

			CreateBySimpleRes resObj = this.mapper.readValue(in, CreateBySimpleRes.class);
			System.out.println(resObj);
		}

		request.releaseConnection();

	}

	private boolean hasError(HttpResponse response) {
		try {
			int statusCode = response.getStatusLine().getStatusCode();
			System.out.println(statusCode);

			if (statusCode >= 300) {
				return true;
			}

			Header resultHeader = response.getHeaders("x-sac-result-code")[0];
			System.out.println(resultHeader.getValue());

			if (!StringUtils.equalsIgnoreCase(resultHeader.getValue(), "SUCC")) {
				return true;
			}
		} catch (Exception e) {
			return true;
		}

		return false;
	}

}
