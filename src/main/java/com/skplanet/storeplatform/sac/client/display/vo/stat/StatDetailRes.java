package com.skplanet.storeplatform.sac.client.display.vo.stat;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class StatDetailRes {
	private String clsf;
	private String key;
	private Integer cntLike;
	private Integer cntShar;
	private Integer cntBrws;
	
	public String getClsf() {
		return clsf;
	}
	public void setClsf(String clsf) {
		this.clsf = clsf;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Integer getCntLike() {
		return cntLike;
	}
	public void setCntLike(Integer cntLike) {
		this.cntLike = cntLike;
	}
	public Integer getCntShar() {
		return cntShar;
	}
	public void setCntShar(Integer cntShar) {
		this.cntShar = cntShar;
	}
	public Integer getCntBrws() {
		return cntBrws;
	}
	public void setCntBrws(Integer cntBrws) {
		this.cntBrws = cntBrws;
	}
	
}
