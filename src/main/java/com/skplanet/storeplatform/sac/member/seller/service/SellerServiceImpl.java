package com.skplanet.storeplatform.sac.member.seller.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.storeplatform.member.client.common.vo.CommonRequest;
import com.skplanet.storeplatform.member.client.common.vo.MbrAuth;
import com.skplanet.storeplatform.member.client.common.vo.MbrClauseAgree;
import com.skplanet.storeplatform.member.client.seller.sci.SellerSCI;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.CreateSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.LogInSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.LogInSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.PWReminder;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.RemoveSellerResponse;
import com.skplanet.storeplatform.member.client.seller.sci.vo.SellerMbr;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateStatusSellerRequest;
import com.skplanet.storeplatform.member.client.seller.sci.vo.UpdateStatusSellerResponse;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.AuthorizeRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CmsReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CmsRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.CreateRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.LockAccountRes;
import com.skplanet.storeplatform.sac.client.member.vo.seller.WithdrawReq;
import com.skplanet.storeplatform.sac.client.member.vo.seller.WithdrawRes;
import com.skplanet.storeplatform.sac.common.header.vo.SacRequestHeader;
import com.skplanet.storeplatform.sac.common.util.HttpUtil;
import com.skplanet.storeplatform.sac.member.common.MemberConstants;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 판매자 회원의 가입/수정/탈퇴/인증 기능정의
 * 
 * Updated on : 2014. 1. 7. Updated by : 김경복, 부르칸.
 */
@Service
public class SellerServiceImpl implements SellerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SellerServiceImpl.class);

	@Autowired
	private SellerSCI sellerSCI;

	@Override
	public CreateRes createSeller(SacRequestHeader header, CreateReq req) {

		/** 1. SC회원 Req 생성 및 주입. */
		CreateSellerRequest createSellerRequest = new CreateSellerRequest();

		// 실명인증정보
		MbrAuth mbrAuth = new MbrAuth();
		// 실명인증여부
		mbrAuth.setIsRealName(req.getIsRealName());
		// CI
		mbrAuth.setCi(req.getSellerCI());
		// DI
		mbrAuth.setDi(req.getSellerDI());
		// 인증방법코드
		mbrAuth.setRealNameMethod(req.getRealNameMethod());
		// mbrAuth.set
		createSellerRequest.setMbrAuth(new MbrAuth());

		// 약관동의 정보 리스트
		List<MbrClauseAgree> mbrClauseAgreeList = null;
		MbrClauseAgree mbrClauseAgree = null;
		// new MbrClauseAgree();
		if (req.getAgreementList() != null) {
			mbrClauseAgreeList = new ArrayList<MbrClauseAgree>();
			for (int i = 0; i < req.getAgreementList().size(); i++) {
				mbrClauseAgree = new MbrClauseAgree();
				// 약관동의 ID
				mbrClauseAgree.setExtraAgreementID(req.getAgreementList().get(i).getExtraAgreementId());
				// 약관동의 여부
				mbrClauseAgree.setIsExtraAgreement(req.getAgreementList().get(i).getIsExtraAgreement());
				// 약관 버전
				mbrClauseAgree.setExtraAgreementVersion(req.getAgreementList().get(i).getExtraAgreementVersion());
				mbrClauseAgreeList.add(mbrClauseAgree);
			}
			createSellerRequest.setMbrClauseAgree(mbrClauseAgreeList);
		}

		// 보안질문 리스트
		List<PWReminder> pWReminderList = null;
		if (req.getPWReminderList() != null) {
			pWReminderList = new ArrayList<PWReminder>();
			for (int i = 0; i < req.getPWReminderList().size(); i++) {
				PWReminder pwReminder = new PWReminder();
				// TODO Req 재정의 요망
				pwReminder.setAnswerString(req.getPWReminderList().get(i).getPwReminderQ());
				pwReminder.setQuestionID(null);
				pwReminder.setQuestionMessage(null);
				pWReminderList.add(pwReminder);
			}
			createSellerRequest.setPWReminderList(pWReminderList);
		}

		// 판매자 회원 정보
		SellerMbr sellerMbr = new SellerMbr();
		// 판매자구분코드
		sellerMbr.setSellerClass(req.getSellerClass());
		// 판매자 분류코드
		sellerMbr.setSellerCategory(req.getSellerCategory());
		// 판매자 main 상태 코드
		sellerMbr.setSellerMainStatus(req.getSellerMainStatus());
		// 판매자 sub 상태 코드
		sellerMbr.setSellerSubStatus(req.getSellerSubStatus());
		// 판매자 회원 ID
		sellerMbr.setSellerID(req.getSellerId());
		// 판매자 회원 PW

		// 이동통신사
		sellerMbr.setSellerTelecom(req.getSellerTelecom());
		// 전화번호 국가코드
		sellerMbr.setSellerPhoneCountry(req.getSellerPhoneCountry());
		// 전화번호
		sellerMbr.setSellerPhone(req.getSellerPhone());
		// SMS 수신여부
		sellerMbr.setIsRecvSMS(req.getIsRecvSMS());
		// 판매자 이메일
		sellerMbr.setSellerEmail(req.getSellerEmail());
		// 이메일 수신여부
		sellerMbr.setIsRecvEmail(req.getIsRecvEmail());
		// 판매자 이름
		sellerMbr.setSellerName(req.getSellerName());
		// 노출 이름
		sellerMbr.setSellerNickName(req.getSellerNickName());
		// 판매자 성별
		sellerMbr.setSellerSex(req.getSellerSex());
		// 판매자 생년월일
		sellerMbr.setSellerBirthDay(req.getSellerBirthDay());
		// 주민등록번호
		sellerMbr.setSellerSSNumber(req.getSellerSSNumber());
		// 우편번호
		sellerMbr.setSellerZip(req.getSellerZip());
		// 거주자 주소
		sellerMbr.setSellerAddress(req.getSellerAddress());
		// 거주자 상세주소
		sellerMbr.setSellerDetailAddress(req.getSellerDetailAddress());
		// (외국인)도시
		sellerMbr.setSellerCity(req.getSellerCity());
		// (외국인)주
		sellerMbr.setSellerState(req.getSellerState());
		// 국가코드
		sellerMbr.setSellerCountry(req.getSellerCountry());
		// 언어코드
		sellerMbr.setSellerLanguage(req.getSellerLanguage());
		// 식별코드
		sellerMbr.setIsForeign(req.getIsForeign());

		// 실명인증여부
		sellerMbr.setIsRealName(req.getIsRealName());

		// 회사명
		sellerMbr.setSellerCompany(req.getSellerCompany());
		// 사업자 등록번호
		sellerMbr.setSellerBizNumber(req.getSellerBizNumber());
		// 고객응대 전화번호 국가코드
		sellerMbr.setCustomerPhoneCountry(req.getCustomerPhoneCountry());
		// 고객 응대 전화번호
		sellerMbr.setCustomerPhone(req.getCustomerPhone());
		// 고객 응대 이메일
		sellerMbr.setCustomerEmail(req.getCustomerEmail());
		// 법인등록번호
		sellerMbr.setSellerBizCorpNumber(req.getSellerBizCorpNumber());

		createSellerRequest.setSellerMbr(new SellerMbr());

		// 최종 vo 에 값 셋팅
		createSellerRequest.setSellerMbr(sellerMbr);

		LOGGER.debug("### 넘긴 데이터 : {}", createSellerRequest.toString());

		/** TODO 2. 테스트용 if 헤더 셋팅 */
		if (header.getTenantHeader() == null) {
			createSellerRequest.setCommonRequest(this.imsiCommonRequest());
		} else {
			CommonRequest commonRequest = new CommonRequest();
			commonRequest.setSystemID(header.getTenantHeader().getSystemId());
			commonRequest.setTenantID(header.getTenantHeader().getTenantId());
			createSellerRequest.setCommonRequest(commonRequest);
		}

		CreateSellerResponse createSellerResponse = this.sellerSCI.createSeller(createSellerRequest);
		LOGGER.debug("code : {}" + createSellerResponse.getCommonResponse().getResultCode());

		/** TODO 통합CMS 연동 */

		return null;
	}

	@Override
	public LockAccountRes lockAccount(SacRequestHeader header, LockAccountReq req) {

		/** 1. SC회원 Req 생성 및 주입 */
		UpdateStatusSellerRequest updateStatusSellerRequest = new UpdateStatusSellerRequest();
		updateStatusSellerRequest.setSellerID(req.getSellerId());
		/*
		 * US010201(정상) US010202(탈퇴) US010203(가가입) US010204(일시정지) US010205(전환)
		 */
		// TODO 임시 코딩
		updateStatusSellerRequest.setSellerMainStatus(MemberConstants.MAIN_STATUS_WATING);
		/*
		 * US010301(정상) US010302(탈퇴신청) US010303(탈퇴완료) US010304(가입승인만료) US010305(가입승인 대기) US010306(이메일변경승인대기)
		 * US010307(로그인 제한) US010308(직권중지) US010309(7일이용정지) US010310(30일이용정지) US010311(영구이용정지) US010312(전환신청)
		 * US010313(전환재신청) US010314(전환거절)
		 */
		// TODO 임시코딩
		updateStatusSellerRequest.setSellerSubStatus(MemberConstants.SUB_STATUS_LOGIN_PAUSE);

		/** TODO 2. 테스트용 if 헤더 셋팅 */
		if (header.getTenantHeader() == null) {
			updateStatusSellerRequest.setCommonRequest(this.imsiCommonRequest());
		} else {
			CommonRequest commonRequest = new CommonRequest();
			commonRequest.setSystemID(header.getTenantHeader().getSystemId());
			commonRequest.setTenantID(header.getTenantHeader().getTenantId());
			updateStatusSellerRequest.setCommonRequest(commonRequest);
		}

		/** 3. SC회원 Call */
		UpdateStatusSellerResponse updateStatusSellerResponse = this.sellerSCI
				.updateStatusSeller(updateStatusSellerRequest);

		// Response Debug
		LOGGER.info("UpdateStatusSellerResponse Code : {}", updateStatusSellerResponse.getCommonResponse()
				.getResultCode());
		LOGGER.info("UpdateStatusSellerResponse Messge : {}", updateStatusSellerResponse.getCommonResponse()
				.getResultMessage());

		// TODO Exception 재정의 - 결과 값 성공(0000)이 아니면 던져~~~
		if (!MemberConstants.RESULT_SUCCES.equals(updateStatusSellerResponse.getCommonResponse().getResultCode())) {
			throw new RuntimeException(updateStatusSellerResponse.getCommonResponse().getResultMessage());
		}
		/** 4. TenantRes Response 생성 및 주입 */
		LockAccountRes res = new LockAccountRes(updateStatusSellerRequest.getSellerID());
		return res;
	}

	@Override
	public AuthorizeRes authorize(SacRequestHeader header, AuthorizeReq req) {

		/** 1. SC회원 Req 생성 및 주입 */
		LogInSellerRequest logInSellerRequest = new LogInSellerRequest();
		logInSellerRequest.setSellerID(req.getSellerId());
		logInSellerRequest.setSellerPW(req.getSellerPW());

		// TODO Exception
		if (req == null) {
			throw new RuntimeException("Parameter 불충분");
		}

		/** TODO 2. 테스트용 if 헤더 셋팅 */
		if (header.getTenantHeader() == null) {
			logInSellerRequest.setCommonRequest(this.imsiCommonRequest());
		} else {
			CommonRequest commonRequest = new CommonRequest();
			commonRequest.setSystemID(header.getTenantHeader().getSystemId());
			commonRequest.setTenantID(header.getTenantHeader().getTenantId());
			logInSellerRequest.setCommonRequest(commonRequest);
		}

		/** 3. SC-로그인인증 Call */
		LogInSellerResponse logInSellerResponse = this.sellerSCI.logInSeller(logInSellerRequest);

		// Response Debug
		LOGGER.info("logInSellerResponse Code : {}", logInSellerResponse.getCommonResponse().getResultCode());
		LOGGER.info("logInSellerResponse Messge : {}", logInSellerResponse.getCommonResponse().getResultMessage());

		// TODO Exception 재정의 - 결과 값 성공(0000)이 아니면 던져~~~
		if (!MemberConstants.RESULT_SUCCES.equals(logInSellerResponse.getCommonResponse().getResultCode())) {
			throw new RuntimeException(logInSellerResponse.getCommonResponse().getResultMessage());
		}

		AuthorizeRes res = new AuthorizeRes();
		com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr sellerMbr = null;

		if (logInSellerResponse != null) {
			sellerMbr = new com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr();
			sellerMbr.setSellerKey(logInSellerResponse.getSellerKey());
			sellerMbr.setSellerClass(logInSellerResponse.getSellerClass());
			sellerMbr.setSellerMainStatus(logInSellerResponse.getSellerMainStatus());
			sellerMbr.setSellerSubStatus(logInSellerResponse.getSellerSubStatus());
			res.setLoginFailCount(String.valueOf(logInSellerResponse.getLoginFailCount()));
			res.setSellerMbr(sellerMbr);
		}
		logInSellerResponse.getLoginFailCount();
		res.setSellerMbr(new com.skplanet.storeplatform.sac.client.member.vo.common.SellerMbr());

		return res;
	}

	@Override
	public WithdrawRes withdraw(WithdrawReq req) throws Exception {

		if (req.getSellerKey() == null | req.getSecedeReasonCode() == null | req.getSecedeReasonMessage() == null)
			throw new Exception("필수 파라미터 미존재");
		if (req.getSellerKey().equals("") | req.getSecedeReasonCode().equals("")
				| req.getSecedeReasonMessage().equals(""))
			throw new Exception("필수 파라미터 미존재");

		RemoveSellerResponse schRes = new RemoveSellerResponse();
		RemoveSellerRequest schReq = new RemoveSellerRequest();
		schReq.setCommonRequest(this.imsiCommonRequest());
		schReq.setSellerKey(req.getSellerKey());
		schReq.setSecedeReasonCode(req.getSecedeReasonCode());
		schReq.setSecedeReasonMessage(req.getSecedeReasonMessage());

		schRes = this.sellerSCI.removeSeller(schReq);

		WithdrawRes response = new WithdrawRes();

		return response;
	}

	/**
	 * <pre>
	 * <<<<<<< .mine
	 * =======
	 * SC SellerMbr To SAC SellerInfo.
	 * </pre>
	 * 
	 * @param sellerInfo
	 * @return SellerInfo
	 */
	private SellerMbr converterSACToSCSellerMbr(SellerMbr sellerMbr) {
		SellerMbr sellerInfo = new SellerMbr();
		// sellerMbr.set
		return sellerInfo;
	}

	/**
	 * <pre>
	 * TODO 임시 SC회원 전달용 공통헤더.
	 * </pre>
	 * 
	 * @return CommonRequest
	 */
	private CommonRequest imsiCommonRequest() {
		/** TODO 임시 공통헤더 생성 주입 */
		CommonRequest commonRequest = new CommonRequest();
		// S001(ShopClient), S002(WEB), S003(OpenAPI)
		commonRequest.setSystemID("S001");
		// TODO SC회원 문의?? - Reamine ID생성 규칙과 다름
		// T01(T-Store), T02(A-Store), T03(B-Store)
		commonRequest.setTenantID("S01");

		return commonRequest;
	}

	/**
	 * <pre>
	 * Object To XML.
	 * </pre>
	 * 
	 * @param type
	 * @param obj
	 * @return String
	 */
	public static String makeData(String type, Object obj) {
		String resultXml = "";
		try {
			XStream xs = new XStream();
			xs.alias(type, obj.getClass());

			resultXml = xs.toXML(obj);
			resultXml = resultXml.replaceAll("__", "_");

			LOGGER.info("resultXml : " + resultXml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultXml;
	}

	/**
	 * <pre>
	 * 통합 CMS 결과 맵핑.
	 * XML TO Object
	 * </pre>
	 * 
	 * @param data
	 * @return CmsRes
	 * @throws Exception
	 */
	public static CmsRes resultFromXml(String data) throws Exception {

		CmsRes sr = new CmsRes();
		XStream xs = new XStream(new DomDriver());

		xs.alias("RESULT", CmsRes.class);

		xs.fromXML(data, sr);

		return sr;
	}

	/**
	 * <pre>
	 * TODO 임시 통합 CMS 테스트 데이터.
	 * </pre>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		StringBuffer sb = new StringBuffer();

		String headerXml = "";
		String memberXml = "";
		String memberHpXml = "";

		CmsReq header = new CmsReq();
		header.setWK_CD("UPDATE");
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		String now = sf.format(date);
		header.setWK_DTTM(now);
		header.setHP_CNT("2");
		headerXml = SellerServiceImpl.makeData("HEADER", header);

		CmsReq memberReq = new CmsReq();
		memberXml = SellerServiceImpl.makeData("MEMBER", memberReq);

		CmsReq memberHp = new CmsReq();
		memberXml = SellerServiceImpl.makeData("MEMBER_HP", memberHp);

		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n");
		sb.append("<CONTENT>\n");
		sb.append(headerXml + "\n");
		sb.append("<BODY>\n");
		sb.append(memberXml + "\n");
		sb.append(memberHpXml + "\n");
		sb.append("</BODY>\n");
		sb.append("</CONTENT>\n");

		System.out.println(sb.toString());

		// Test CMS
		String xmlReq = "";

		// Header 생성
		Map<String, String> headerMap = new HashedMap();
		headerMap.put("Content-Type", "text/xml; charset=utf-8");
		headerMap.put("Host", "");
		headerMap.put("Content-Length", xmlReq.getBytes().length + "");
		headerMap.put("Connection", "close");

		HttpUtil httpUtil = new HttpUtil();

		// HttpClient Exceute
		String resStr = httpUtil.httpExecutePost("http://220.103.237.139/icms-admin/intf/partner/receiveContent",
				xmlReq, headerMap, "text/xml", "UTF-8");

		// StringBuffer resStr = new StringBuffer();
		// resStr.append("<?xml version='1.0' encoding='euc-kr' ?>");
		// resStr.append("<RESULT>");
		// resStr.append("<RES_DTTM >00</RES_DTTM>");
		// resStr.append("<RES_CD>00</RES_CD>");
		// resStr.append("<RES_MSG>정상</RES_MSG>");
		// resStr.append("</RESULT>");

		try {
			// Response XML To Object
			CmsRes res = SellerServiceImpl.resultFromXml(resStr);
			// Debug
			System.out.println(res.getRES_CD());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
