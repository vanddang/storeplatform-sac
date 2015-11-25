package com.skplanet.storeplatform.sac.client.rest.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListScorePaticpersSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ListScorePaticpersSacRes;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ModifyFeedbackSacReq;
import com.skplanet.storeplatform.sac.client.other.vo.feedback.ModifyFeedbackSacRes;
import com.skplanet.storeplatform.sac.client.rest.error.SacRestClientError;
import com.skplanet.storeplatform.sac.client.rest.error.SacRestClientException;

/**
 * 어댑터를 사용한 SAC 클라이언트 구현 예제
 *
 * Updated on : 2014. 3. 7.
 * Updated by : 서대영, SK 플래닛.
 */
public class SacRestServiceExample {

	private static final Logger LOGGER = LoggerFactory.getLogger(SacRestServiceExample.class);

	// SacRestClient을 감싸고 있는 어댑터
	private final SacRestAdaptorExample adaptor = new SacRestAdaptorExample();

	/**
	 * <pre>
	 * [GET 호출 예제] SAC 기타 파트의 참여자수 조회 API를 호출한다.
	 * (규격서 : https://project.itopping.co.kr:82/projects/storeplatform/wiki/%EA%B8%B0%ED%83%80)
	 *</pre>
	 *
	 * @param req
	 * 		상품 아이디 송신을 위한 요청 객체
	 * @return
	 * 		참여자 목록 수신을 위한 응답 객체
	 */
	public ListScorePaticpersSacRes listPaticpers(ListScorePaticpersSacReq req) {
		// 호출할 API 정보
		String interfaceId = "I04000013";
		String path = "/other/feedback/listScorePaticpers/v1";

		// 응답 데이터를 담을 객체 타입
		Class<ListScorePaticpersSacRes> responseType = ListScorePaticpersSacRes.class;

		// 템플릿을 이용하여 호출 및 오류 처리
		try {
			ListScorePaticpersSacRes data = this.adaptor.get(interfaceId, path, responseType, req);
			LOGGER.debug("# Data : \n{}", data);
			return data;
		} catch (SacRestClientException e) {
			SacRestClientError error = e.getError();
			LOGGER.debug("# Error : \n{}", error);
			throw new RuntimeException("SAC Interface Error", e);
		}
	}

	/**
	 * <pre>
	 * [POST 호출 예제] SAC 기타 파트의 사용후기/평점 수정 API를 호출한다.
	 * (규격서 : https://project.itopping.co.kr:82/projects/storeplatform/wiki/%EA%B8%B0%ED%83%80)
	 *</pre>
	 *
	 * @param req
	 * 		수정할 사용후기/평점 송신하기 위한 요청 객체
	 * @return
	 * 		수정 결과를 수신하기 위한 응답 객체
	 */
	public ModifyFeedbackSacRes modifyFeedback(ModifyFeedbackSacReq req) {
		// 호출할 API 정보
		String interfaceId = "I04000003";
		String path = "/other/feedback/modify/v1";

		// 응답 데이터를 담을 객체 타입
		Class<ModifyFeedbackSacRes> responseType = ModifyFeedbackSacRes.class;

		// 템플릿을 이용하여 호출 및 오류 처리
		try {
			ModifyFeedbackSacRes data = this.adaptor.post(interfaceId, path, responseType, req);
			LOGGER.debug("# Data : \n{}", data);
			return data;
		} catch (SacRestClientException e) {
			// 오류 발생 시, Tenant 오류 정책에 따라...
			SacRestClientError error = e.getError();
			LOGGER.debug("# Error : \n{}", error);
			throw new RuntimeException("SAC Interface Error", e);
		}
	}

}
