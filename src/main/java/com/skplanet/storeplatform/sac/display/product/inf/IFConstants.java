package com.skplanet.storeplatform.sac.display.product.inf;

public class IFConstants {
	
	/** ENCODING */
	public static String ENCODING_BASIC = "utf-8";
	
	public static String USE_Y = "Y";
	
	
	/** SUCCESS */
	public static final String COMM_RST_CODE_SUCC = "200";
	/** FAIL */
	public static final String COMM_RST_CODE_FAIL = "999";
	
	/*************************************************
	 *  AOM 
	 *************************************************/
	
	/** 상품_타입_그룹_코드 */
	public static final String AOM_SERVICE_TYPE_GRP = "AM0006";
	
	/** SUCCESS */
	public static final String AOM_RST_CODE_SUCC = "0";	
	/** 등록_되어진_회원_정보_없음 */
	public static final String AOM_RST_CODE_NOT_EXISTS_MDN = "1000";
	/** 해당_회원의_등록_되어진_Token_정보_없음 */
	public static final String AOM_RST_CODE_NOT_EXISTS_TOKEN = "1001";
	/** 잘못된_요청_data */
	public static final String AOM_RST_CODE_INVALID_DATA = "2000";
	/** 기타_오류 */
	public static final String AOM_RST_CODE_FAIL = "4000";
	
	/*************************************************
	 *  CMS 
	 *************************************************/
	public static final String CMS_DATA_CONTROL_TYPE_NONE = "000";
	public static final String CMS_DATA_CONTROL_TYPE_INSERT = "001";
	public static final String CMS_DATA_CONTROL_TYPE_UPDATE = "002";
	public static final String CMS_DATA_CONTROL_TYPE_DELETE = "003";
	public static final String CMS_DATA_CONTROL_TYPE_DI = "004";
	
	public static final String CMS_RST_CODE_SUCCESS = "0000";
	public static final String CMS_RST_CODE_DP_PROD_BE_NOT_DEPLOY_DATA = "0001";
	public static final String CMS_RST_CODE_DP_DATA_INVALID_ERROR = "0002";
	public static final String CMS_RST_CODE_FILE_NOT_FOUND = "0003";
	public static final String CMS_RST_CODE_FTP_ERROR = "0010";
	public static final String CMS_RST_CODE_DB_ERROR = "0020";
	public static final String CMS_RST_CODE_MQ_ERROR = "0030";
	public static final String CMS_RST_CODE_DWNSVR_ERROR = "0040";
	public static final String CMS_RST_CODE_UNKNOWN_ERROR = "9999";
	
	public static final String CONTENT_SALE_STAT_ING 		= "PD000403";
	
}
