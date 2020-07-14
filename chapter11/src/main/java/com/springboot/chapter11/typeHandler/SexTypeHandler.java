package com.springboot.chapter11.typeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.springboot.chapter11.enumeration.SexEnum;

@MappedTypes(SexEnum.class)
@MappedJdbcTypes(JdbcType.INTEGER)
public class SexTypeHandler extends BaseTypeHandler<SexEnum> {

	@Override
	public SexEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
		int code = rs.getInt(columnName);
		return SexEnum.getSexEnum(code);
	}

	@Override
	public SexEnum getNullableResult(ResultSet rs, int index) throws SQLException {
		int code = rs.getInt(index);
		return SexEnum.getSexEnum(code);
	}

	@Override
	public SexEnum getNullableResult(CallableStatement cs, int index) throws SQLException {
		int code = cs.getInt(index);
		return SexEnum.getSexEnum(code);
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int index, SexEnum sex, JdbcType jdbcType) throws SQLException {
		ps.setInt(index, sex.getCode());
	}

}
