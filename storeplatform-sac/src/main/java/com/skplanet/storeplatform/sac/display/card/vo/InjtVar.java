/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */

package com.skplanet.storeplatform.sac.display.card.vo;

import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.skplanet.storeplatform.framework.core.common.vo.CommonInfo;

/**
 * Class 설명
 * 
 * Updated on : 2014. 10. 10.
 * Updated by : 양해엽, SK Planet
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class InjtVar extends CommonInfo {
	
	private static final long serialVersionUID = 4909559332367647311L;
	
	private Map<String, String> value;
	private Map<String, String> desc;
	
	/**
	 * @return the value
	 */
	public Map<String, String> getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(Map<String, String> value) {
		this.value = value;
	}
	/**
	 * @return the desc
	 */
	public Map<String, String> getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(Map<String, String> desc) {
		this.desc = desc;
	}
	
	
	
	

}
