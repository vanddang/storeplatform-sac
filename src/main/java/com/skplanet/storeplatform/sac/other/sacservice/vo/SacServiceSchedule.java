package com.skplanet.storeplatform.sac.other.sacservice.vo;

import java.util.Date;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

public class SacServiceSchedule extends CommonInfo {

	private static final long serialVersionUID = 1L;

	private int scheduleNo;
	private Date startDt;
	private Date endDt;
	
	public SacServiceSchedule(int scheduleNo, Date startDt, Date endDt) {
		this.scheduleNo = scheduleNo;
		this.startDt = startDt;
		this.endDt = endDt;
	}
	
	public int getScheduleNo() {
		return scheduleNo;
	}
	public void setScheduleNo(int scheduleNo) {
		this.scheduleNo = scheduleNo;
	}
	public Date getStartDt() {
		return startDt;
	}
	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}
	public Date getEndDt() {
		return endDt;
	}
	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}
	
}
