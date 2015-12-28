package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import com.skplanet.storeplatform.external.client.idp.sci.IdpSCI;
import com.skplanet.storeplatform.external.client.idp.sci.ImageSCI;
import com.skplanet.storeplatform.external.client.idp.vo.ImageReq;
import com.skplanet.storeplatform.external.client.idp.vo.ImageRes;
import com.skplanet.storeplatform.external.client.idp.vo.WaterMarkAuthEcReq;
import com.skplanet.storeplatform.external.client.idp.vo.WaterMarkAuthImageEcRes;
import com.skplanet.storeplatform.framework.core.util.StringUtils;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmCaptchaReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmCaptchaRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetCaptchaRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * Captcha 기능 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CaptchaServiceImpl.class);

	@Autowired
	private IdpSCI idpSCI; // IDP 연동 Interface.

	@Autowired
	private ImageSCI imageSCI; // 이미지를 String으로 변환 Interface.

	/**
	 * <pre>
	 * Captcha 문자 발급.
	 * </pre>
	 * 
	 * @return GetCaptchaRes
	 */
	@Override
	public GetCaptchaRes getCaptcha() {
		String waterMarkImageUrl = "";
		String waterMarkImageSign = "";
		String waterMarkImageString = "";
		String signData = "";
		GetCaptchaRes response = new GetCaptchaRes();

		/* IDP 연동해서 waterMarkImage URL과 Signature 받기 */

		WaterMarkAuthImageEcRes waterMarkAuthImageEcRes = this.idpSCI.warterMarkImageUrl();

		if (waterMarkAuthImageEcRes != null && StringUtils.isNotBlank(waterMarkAuthImageEcRes.getImageUrl())) {
			waterMarkImageUrl = waterMarkAuthImageEcRes.getImageUrl();
			waterMarkImageSign = waterMarkAuthImageEcRes.getImageSign();
			signData = waterMarkAuthImageEcRes.getSignData();

			LOGGER.debug("[MiscellaneousService.getCaptcha] SAC<-IDP Response : {}", waterMarkAuthImageEcRes);

			String urlPath = waterMarkImageUrl.substring(waterMarkImageUrl.indexOf("/watermark"));

			ImageReq req = new ImageReq();
			req.setProtocol(waterMarkImageUrl.substring(0, 5).equals("https") ? "https" : "http"); // HTTP or HTTPS
			req.setUrlPath(urlPath);
			ImageRes imageRes = this.imageSCI.convert(req);

			if (imageRes != null && StringUtils.isNotBlank(imageRes.getImgData())) {
				waterMarkImageString = imageRes.getImgData();
			}
		}

		response.setImageData(waterMarkImageString);
		response.setImageSign(waterMarkImageSign);
		response.setSignData(signData);

		return response;
	}

	/**
	 * <pre>
	 * Captcha 문자 확인.
	 * </pre>
	 * 
	 * @param request
	 *            ConfirmCaptchaReq
	 * @return ConfirmCaptchaRes
	 */
	@Override
	public ConfirmCaptchaRes confirmCaptcha(ConfirmCaptchaReq request) {

		WaterMarkAuthEcReq waterMarkAuthEcReq = new WaterMarkAuthEcReq();
		waterMarkAuthEcReq.setUserCode(request.getAuthCode());
		waterMarkAuthEcReq.setImageSign(request.getImageSign());
		waterMarkAuthEcReq.setSignData(request.getSignData());

		this.idpSCI.waterMarkAuth(waterMarkAuthEcReq);

		ConfirmCaptchaRes response = new ConfirmCaptchaRes();
		return response;
	}
}
