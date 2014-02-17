/*
 * Copyright (c) 2013 SK planet.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SK planet.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SK planet.
 */
package com.skplanet.storeplatform.sac.runtime.acl.vo;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
*
* Interface Status Type Handler
*
* Updated on : 2014. 2. 17.
* Updated by : 서대영, SK 플래닛
*/
public class InterfaceStatusTypeHandler implements TypeHandler<InterfaceStatus> {

	@Override
	public void setParameter(PreparedStatement ps, int i, InterfaceStatus parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, parameter.getCode());
	}

	@Override
	public InterfaceStatus getResult(ResultSet rs, String columnName) throws SQLException {
		return InterfaceStatus.fromCode(rs.getString(columnName));
	}

	@Override
	public InterfaceStatus getResult(ResultSet rs, int columnIndex) throws SQLException {
		return InterfaceStatus.fromCode(rs.getString(columnIndex));
	}

	@Override
	public InterfaceStatus getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return InterfaceStatus.fromCode(cs.getString(columnIndex));
	}

}
