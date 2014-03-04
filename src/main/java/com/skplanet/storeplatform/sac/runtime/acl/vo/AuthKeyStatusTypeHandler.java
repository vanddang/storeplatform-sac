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

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
*
* Auth Key Status Type Handler
*
* Updated on : 2014. 2. 17.
* Updated by : 임근대, SK 플래닛
*/
public class AuthKeyStatusTypeHandler implements TypeHandler<AuthKeyStatus> {

	@Override
	public void setParameter(PreparedStatement ps, int i, AuthKeyStatus parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, parameter.getCode());
	}

	@Override
	public AuthKeyStatus getResult(ResultSet rs, String columnName) throws SQLException {
		return AuthKeyStatus.fromCode(rs.getString(columnName));
	}

	@Override
	public AuthKeyStatus getResult(ResultSet rs, int columnIndex) throws SQLException {
		return AuthKeyStatus.fromCode(rs.getString(columnIndex));
	}

	@Override
	public AuthKeyStatus getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return AuthKeyStatus.fromCode(cs.getString(columnIndex));
	}

}
