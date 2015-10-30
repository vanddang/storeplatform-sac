package com.skplanet.storeplatform.member.client.user.sci.vo;

import java.io.Serializable;
import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * 유휴 회원 전환 전체 데이터 VO
 * 
 * Updated on : 2015. 6. 2. Updated by : incross_cmlee80. I-S Plus.
 */
public class UserMbrMoveAllInfo extends CommonInfo implements Serializable {

	/** The Constant serial Version UID. */
	private static final long serialVersionUID = 1L;

	UserMbrInfo userInfo;
	List<UserMbrDeviceInfo> userMbrDeviceInfoList;
	List<UserMbrDeviceDtlInfo> userMbrDeviceDtlInfoList;
	List<UserMbrDeviceHisInfo> userMbrDeviceHisInfoList;
	List<UserMbrDeviceSetInfo> userMbrDeviceSetInfoList;
	List<UserMbrHisInfo> userMbrHisInfoList;
	List<UserMbrIdMbrTrfmChasInfo> userMbrIdMbrTrfmChasInfoList;
	List<UserMbrLglAgentInfo> userMbrLglAgentInfoList;
	List<UserMbrMangItemPtcrInfo> userMbrMangItemPtcrInfoList;
	List<UserMbrOcbInfo> userMbrOcbInfoList;
	List<UserMbrOneIdInfo> userMbrOneIdInfoList;
	List<UserMbrAuthInfo> userMbrAuthInfoList;
	List<UserMbrPwdInfo> userMbrPwdInfoList;
	List<UserMbrClauseAgreeInfo> userMbrClauseAgreeInfoList;

	/**
	 * @return the userInfo
	 */
	public UserMbrInfo getUserInfo() {
		return this.userInfo;
	}

	/**
	 * @param userInfo
	 *            the userInfo to set
	 */
	public void setUserInfo(UserMbrInfo userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * @return the userMbrDeviceInfoList
	 */
	public List<UserMbrDeviceInfo> getUserMbrDeviceInfoList() {
		return this.userMbrDeviceInfoList;
	}

	/**
	 * @param userMbrDeviceInfoList
	 *            the userMbrDeviceInfoList to set
	 */
	public void setUserMbrDeviceInfoList(List<UserMbrDeviceInfo> userMbrDeviceInfoList) {
		this.userMbrDeviceInfoList = userMbrDeviceInfoList;
	}

	/**
	 * @return the userMbrDeviceDtlInfoList
	 */
	public List<UserMbrDeviceDtlInfo> getUserMbrDeviceDtlInfoList() {
		return this.userMbrDeviceDtlInfoList;
	}

	/**
	 * @param userMbrDeviceDtlInfoList
	 *            the userMbrDeviceDtlInfoList to set
	 */
	public void setUserMbrDeviceDtlInfoList(List<UserMbrDeviceDtlInfo> userMbrDeviceDtlInfoList) {
		this.userMbrDeviceDtlInfoList = userMbrDeviceDtlInfoList;
	}

	/**
	 * @return the userMbrDeviceHisInfoList
	 */
	public List<UserMbrDeviceHisInfo> getUserMbrDeviceHisInfoList() {
		return this.userMbrDeviceHisInfoList;
	}

	/**
	 * @param userMbrDeviceHisInfoList
	 *            the userMbrDeviceHisInfoList to set
	 */
	public void setUserMbrDeviceHisInfoList(List<UserMbrDeviceHisInfo> userMbrDeviceHisInfoList) {
		this.userMbrDeviceHisInfoList = userMbrDeviceHisInfoList;
	}

	/**
	 * @return the userMbrDeviceSetInfoList
	 */
	public List<UserMbrDeviceSetInfo> getUserMbrDeviceSetInfoList() {
		return this.userMbrDeviceSetInfoList;
	}

	/**
	 * @param userMbrDeviceSetInfoList
	 *            the userMbrDeviceSetInfoList to set
	 */
	public void setUserMbrDeviceSetInfoList(List<UserMbrDeviceSetInfo> userMbrDeviceSetInfoList) {
		this.userMbrDeviceSetInfoList = userMbrDeviceSetInfoList;
	}

	/**
	 * @return the userMbrHisInfoList
	 */
	public List<UserMbrHisInfo> getUserMbrHisInfoList() {
		return this.userMbrHisInfoList;
	}

	/**
	 * @param userMbrHisInfoList
	 *            the userMbrHisInfoList to set
	 */
	public void setUserMbrHisInfoList(List<UserMbrHisInfo> userMbrHisInfoList) {
		this.userMbrHisInfoList = userMbrHisInfoList;
	}

	/**
	 * @return the userMbrIdMbrTrfmChasInfoList
	 */
	public List<UserMbrIdMbrTrfmChasInfo> getUserMbrIdMbrTrfmChasInfoList() {
		return this.userMbrIdMbrTrfmChasInfoList;
	}

	/**
	 * @param userMbrIdMbrTrfmChasInfoList
	 *            the userMbrIdMbrTrfmChasInfoList to set
	 */
	public void setUserMbrIdMbrTrfmChasInfoList(List<UserMbrIdMbrTrfmChasInfo> userMbrIdMbrTrfmChasInfoList) {
		this.userMbrIdMbrTrfmChasInfoList = userMbrIdMbrTrfmChasInfoList;
	}

	/**
	 * @return the userMbrLglAgentInfoList
	 */
	public List<UserMbrLglAgentInfo> getUserMbrLglAgentInfoList() {
		return this.userMbrLglAgentInfoList;
	}

	/**
	 * @param userMbrLglAgentInfoList
	 *            the userMbrLglAgentInfoList to set
	 */
	public void setUserMbrLglAgentInfoList(List<UserMbrLglAgentInfo> userMbrLglAgentInfoList) {
		this.userMbrLglAgentInfoList = userMbrLglAgentInfoList;
	}

	/**
	 * @return the userMbrMangItemPtcrInfoList
	 */
	public List<UserMbrMangItemPtcrInfo> getUserMbrMangItemPtcrInfoList() {
		return this.userMbrMangItemPtcrInfoList;
	}

	/**
	 * @param userMbrMangItemPtcrInfoList
	 *            the userMbrMangItemPtcrInfoList to set
	 */
	public void setUserMbrMangItemPtcrInfoList(List<UserMbrMangItemPtcrInfo> userMbrMangItemPtcrInfoList) {
		this.userMbrMangItemPtcrInfoList = userMbrMangItemPtcrInfoList;
	}

	/**
	 * @return the userMbrOcbInfoList
	 */
	public List<UserMbrOcbInfo> getUserMbrOcbInfoList() {
		return this.userMbrOcbInfoList;
	}

	/**
	 * @param userMbrOcbInfoList
	 *            the userMbrOcbInfoList to set
	 */
	public void setUserMbrOcbInfoList(List<UserMbrOcbInfo> userMbrOcbInfoList) {
		this.userMbrOcbInfoList = userMbrOcbInfoList;
	}

	/**
	 * @return the userMbrOneIdInfoList
	 */
	public List<UserMbrOneIdInfo> getUserMbrOneIdInfoList() {
		return this.userMbrOneIdInfoList;
	}

	/**
	 * @param userMbrOneIdInfoList
	 *            the userMbrOneIdInfoList to set
	 */
	public void setUserMbrOneIdInfoList(List<UserMbrOneIdInfo> userMbrOneIdInfoList) {
		this.userMbrOneIdInfoList = userMbrOneIdInfoList;
	}

	/**
	 * @return the userMbrAuthInfoList
	 */
	public List<UserMbrAuthInfo> getUserMbrAuthInfoList() {
		return this.userMbrAuthInfoList;
	}

	/**
	 * @param userMbrAuthInfoList
	 *            the userMbrAuthInfoList to set
	 */
	public void setUserMbrAuthInfoList(List<UserMbrAuthInfo> userMbrAuthInfoList) {
		this.userMbrAuthInfoList = userMbrAuthInfoList;
	}

	/**
	 * @return the userMbrPwdInfoList
	 */
	public List<UserMbrPwdInfo> getUserMbrPwdInfoList() {
		return this.userMbrPwdInfoList;
	}

	/**
	 * @param userMbrPwdInfoList
	 *            the userMbrPwdInfoList to set
	 */
	public void setUserMbrPwdInfoList(List<UserMbrPwdInfo> userMbrPwdInfoList) {
		this.userMbrPwdInfoList = userMbrPwdInfoList;
	}

	/**
	 * @return the userMbrClauseAgreeInfoList
	 */
	public List<UserMbrClauseAgreeInfo> getUserMbrClauseAgreeInfoList() {
		return this.userMbrClauseAgreeInfoList;
	}

	/**
	 * @param userMbrClauseAgreeInfoList
	 *            the userMbrClauseAgreeInfoList to set
	 */
	public void setUserMbrClauseAgreeInfoList(List<UserMbrClauseAgreeInfo> userMbrClauseAgreeInfoList) {
		this.userMbrClauseAgreeInfoList = userMbrClauseAgreeInfoList;
	}

}
