package com.skplanet.storeplatform.sac.api.util;

import org.apache.log4j.Logger;

/**
 * CMS 기본 코드
 * 
 * @author Sung.HJ. 2009/05/15
 * 
 */
public class BasicCodes {
	private static Logger log = Logger.getLogger(BasicCodes.class);
	private static BasicCodes instance = null;

	/** 예외 메시지용 */
	public static final String EXCEPTION_MSG = "EXCEPTION_MSG";

	/** 오라클/UTF-8에서의 한글 바이트 수 */
	public static final int BYTES_FOR_KOREAN = 3;

	/** 이메일 발송 시, 보낸사람 주소 */
	public static final String SYSTEM_EMAIL_ADDRESS = "bppoc_system@bppoc.co.kr";

	// /** BP 가입 시 등록 문서/이미지 저장 폴더 "BP_FILES" */
	// public static final String REPOSITORY_PATH_FOR_BP_FILES = ContentConstants.REPOSITORY_PATH_FOR_UPLOAD
	// + File.separator + "BP_FILES";

	/** 파일명 중복 방지용 */
	public static final String FILENAME_BP_DOC = "DOC_";
	public static final String FILENAME_BP_THUMBNAIL = "THUMBNAIL_";
	public static final String FILENAME_BP_DESCIMG = "DESCIMG_";

	// SSO
	public final static String SSO_PARAMETER_NAME = "type";
	public final static String SSO_UAK_NAME = "uak";
	public final static String SSO_BP_MENU_PARAMETER_NAME = "smenu";
	public final static String SSO_TYPE_FROM_DEV = "1000"; // DEV의 stype 값: 1000
	public final static String SSO_TYPE_TO_DEV = "3000"; // BP의 stype 값: 3000

	/** 다중 배포용 메타값 헤더. : 로 구분한다. */
	public final static String MULTI_DEPLOY_HEADER = "MUL_DEP";
	public final static String MULTI_DEPLOY_REGEX = ":";

	static {
		BasicCodes.getInstance();
	}

	public synchronized static BasicCodes getInstance() {
		if (instance == null) {
			instance = new BasicCodes();
			log.debug("BasicCodes Instance Create");
		}
		log.debug("BasicCodes Instance is not null");
		return instance;
	}
}
