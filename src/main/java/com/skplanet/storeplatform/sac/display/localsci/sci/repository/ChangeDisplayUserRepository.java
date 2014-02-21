package com.skplanet.storeplatform.sac.display.localsci.sci.repository;

import com.skplanet.storeplatform.sac.display.localsci.sci.vo.ChangeDisplayUser;

/**
 * 
 * ChangeDisplayUser Repository
 * 
 * 전시 회원 ID, KEY 변경 DAO.
 * 
 * Updated on : 2014. 2. 13. Updated by : 김현일, 인크로스
 */
public interface ChangeDisplayUserRepository {

	/**
	 * 
	 * <pre>
	 * 상품 평점 테이블 변경.
	 * </pre>
	 * 
	 * @param changeDisplayUser
	 *            changeDisplayUser
	 * @return Object
	 */
	public Object changeMbrAvg(ChangeDisplayUser changeDisplayUser);

	/**
	 * 
	 * <pre>
	 * 상품 사용후기 테이블 변경.
	 * </pre>
	 * 
	 * @param changeDisplayUser
	 *            changeDisplayUser
	 * @return Object
	 */
	public Object changeProdNoti(ChangeDisplayUser changeDisplayUser);

	/**
	 * 
	 * <pre>
	 * 상품 사용후기 신고 테이블 변경.
	 * </pre>
	 * 
	 * @param changeDisplayUser
	 *            changeDisplayUser
	 * @return Object
	 */
	public Object changeBadNoti(ChangeDisplayUser changeDisplayUser);

	/**
	 * 
	 * <pre>
	 * 상품 사용후기 추천 테이블 변경.
	 * </pre>
	 * 
	 * @param changeDisplayUser
	 *            changeDisplayUser
	 * @return Object
	 */
	public Object changeProdNotiGood(ChangeDisplayUser changeDisplayUser);

	/**
	 * 
	 * <pre>
	 * 상품별 평점 테이블 변경.
	 * </pre>
	 * 
	 * @param changeDisplayUser
	 * @return
	 */
	public Object changeTenantProdStats(ChangeDisplayUser changeDisplayUser);

	/**
	 * 
	 * <pre>
	 * 메시지 회원 매핑 테이블 변경.
	 * </pre>
	 * 
	 * @param changeDisplayUser
	 *            changeDisplayUser
	 * @return ObjectObject
	 */
	public Object changeMsgMbrMapg(ChangeDisplayUser changeDisplayUser);
}
