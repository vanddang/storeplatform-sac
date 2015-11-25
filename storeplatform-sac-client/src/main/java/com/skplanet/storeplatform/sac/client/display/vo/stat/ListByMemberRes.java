package com.skplanet.storeplatform.sac.client.display.vo.stat;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

@JsonSerialize(include = Inclusion.NON_NULL)
public class ListByMemberRes extends CommonInfo {

	private static final long serialVersionUID = 1L;
	
	private Integer startKey;
	
	private String hasNext;
	
	private Integer count;
	
	private Integer totalCount;

	private List<LikeRes> likeList;

	public List<LikeRes> getLikeList() {
		return likeList;
	}

	public void setLikeList(List<LikeRes> likeList) {
		this.likeList = likeList;
	}

	public Integer getStartKey() {
		return startKey;
	}

	public void setStartKey(Integer startKey) {
		this.startKey = startKey;
	}

	public String getHasNext() {
		return hasNext;
	}

	public void setHasNext(String hasNext) {
		this.hasNext = hasNext;
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
