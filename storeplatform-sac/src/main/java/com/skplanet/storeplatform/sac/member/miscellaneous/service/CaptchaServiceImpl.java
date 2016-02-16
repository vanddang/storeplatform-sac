package com.skplanet.storeplatform.sac.member.miscellaneous.service;

import com.skplanet.storeplatform.external.client.idp.vo.WaterMarkAuthEcReq;
import com.skplanet.storeplatform.framework.core.exception.StorePlatformException;
import com.skplanet.storeplatform.member.common.crypto.CryptoCode;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmCaptchaReq;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.ConfirmCaptchaRes;
import com.skplanet.storeplatform.sac.client.member.vo.miscellaneous.GetCaptchaRes;
import nl.captcha.Captcha;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;
import java.util.UUID;

/**
 * 
 * Captcha 기능 관련 인터페이스 구현체
 * 
 * Updated on : 2014. 1. 7. Updated by : 김다슬, 인크로스.
 * Updated on : 2015. 12. 28. Updated by : 임근대, SKP.  - Miscellaneous 클래스에서 Captcha 관련 기능 클래스 분리
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CaptchaServiceImpl.class);

	@Autowired
	private CaptchaGenerator captchaGenerator;

	@Value("${thumbnail.server.url}")
	String thumbnailServerUrl;

	@Value("${captcha.image.store.path}")
	String captchaImageStorePath;

	private static final Integer CAPTCHA_WIDTH = 200;
	private static final Integer CAPTCHA_HEIGHT = 50;

	/**
	 * <pre>
	 * Captcha 문자 발급.
	 * </pre>
	 * 
	 * @return GetCaptchaRes
	 */
	@Override
	public GetCaptchaRes getCaptcha() {
		String imageData;	// base64 encdoed string
		String imageUrl;	// image location
		String imageSign;	// 인증코드 확인을 위한 Signature 발행
		String signData;	// signature를 구성한 source data

		GetCaptchaRes response = new GetCaptchaRes();

		try {
			Long curTimeMillis = System.currentTimeMillis();

			// createCaptcha
			Captcha captcha = captchaGenerator.createCaptcha(CAPTCHA_WIDTH, CAPTCHA_HEIGHT);

			// answer
			String captchaAnswer = captcha.getAnswer();

			BufferedImage bufferedImage = captcha.getImage();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			File storePath = getStorePath();
			File captchaFile = new File(storePath, UUID.randomUUID().toString()+".jpg");

			ImageIO.write(bufferedImage, "jpg", bos);

			// imageUrl : image server url
			imageUrl = getImageUrl(captchaFile);

			// write to file
			writeToFile(bos, captchaFile);

			// base64 encoded string
			imageData = Base64.encodeBase64String(bos.toByteArray());

			// ImageSign : 인증코드 확인을 위한 signature 생성
			// ImageUrl + '|' + currentTimeMilis +  '|' +  CaptchaText 값을 Sha1Encoding 한 값
			imageSign = getImageSign(imageUrl, curTimeMillis, captchaAnswer);

			// signData 는 ImageUrl + '|' + currentTimeMilis
			signData = imageUrl + '|' + curTimeMillis;
		} catch (Exception e) {
			e.printStackTrace();
			throw new StorePlatformException("SAC_MEM_3602"); //자동가입방지 이미지 생성 실패
		}

		response.setImageData(imageData);
		response.setImageSign(imageSign);
		response.setSignData(signData);

		return response;
	}

	private String getImageUrl(File captchaFile) {
		String imageUrl;
		imageUrl = thumbnailServerUrl + "/0_0"/*원본*/ + captchaFile.getAbsolutePath();
		return imageUrl;
	}

	private String getImageSign(String imageUrl, Long curTimeMillis, String captchaAnswer) throws Exception {
		String imageSign;CryptoCode crypto = new CryptoCode();
		imageSign = crypto.getCryptoSHA1EncodeHex(imageUrl + "|" + curTimeMillis + "|" + captchaAnswer);
		return imageSign;
	}

	private void writeToFile(ByteArrayOutputStream bos, File captchaFile) throws IOException {
		OutputStream fileOutputStream = new FileOutputStream(captchaFile);
		bos.writeTo(fileOutputStream);
		fileOutputStream.flush();
		fileOutputStream.close();
	}

	private File getStorePath() {
		File rootPath = new File(captchaImageStorePath);
		if(!rootPath.exists()) rootPath.mkdir();

		File storePath = new File(captchaImageStorePath, DateUtils.formatDate(new Date(), "yyyyMMdd"));
		if(!storePath.exists()) storePath.mkdir();
		return storePath;
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

		//FIXME:
		//this.idpSCI.waterMarkAuth(waterMarkAuthEcReq);

		ConfirmCaptchaRes response = new ConfirmCaptchaRes();
		return response;
	}
}
