/* Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.display.stat.vo;

import java.util.List;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * StatLike.selectList의 출력 타입
 * 
 * Created on 2014.11.04. by 서대영, SK플래닛
 * Updated on 2014.11.04. by 서대영, SK플래닛 : statsClsfList 필드 추가 (통계구분 필터링을 위함)
 */
public class StatLike extends CommonInfo {
	
	private static final long serialVersionUID = 1L;

	private String tenantId;
	private String userKey;
	private String statsClsf;
	private String statsKey;
	private List<String> statsClsfList; 
	
	private Integer cntLike;
	private Integer cntBrws;
	private Integer cntShar;
	private Integer seq;
	
	private Integer startKey;
	private Integer count;
	private Integer totalCount;
	
	public StatLike() {
	}
	
	public StatLike(String tenantId, String userKey) {
		this.tenantId = tenantId;
		this.userKey = userKey;
	}

	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getStatsClsf() {
		return statsClsf;
	}
	public void setStatsClsf(String statsClsf) {
		this.statsClsf = statsClsf;
	}
	public String getStatsKey() {
		return statsKey;
	}
	public void setStatsKey(String statsKey) {
		this.statsKey = statsKey;
	}
	/**
	 * @return the statsClsfList
	 */
	public List<String> getStatsClsfList() {
		return statsClsfList;
	}

	/**
	 * @param statsClsfList the statsClsfList to set
	 */
	public void setStatsClsfList(List<String> statsClsfList) {
		this.statsClsfList = statsClsfList;
	}

	public Integer getCntLike() {
		return cntLike;
	}
	public void setCntLike(Integer cntLike) {
		this.cntLike = cntLike;
	}
	public Integer getCntBrws() {
		return cntBrws;
	}
	public void setCntBrws(Integer cntBrws) {
		this.cntBrws = cntBrws;
	}
	public Integer getCntShar() {
		return cntShar;
	}
	public void setCntShar(Integer cntShar) {
		this.cntShar = cntShar;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public Integer getStartKey() {
		return startKey;
	}
	public void setStartKey(Integer startKey) {
		this.startKey = startKey;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

}
