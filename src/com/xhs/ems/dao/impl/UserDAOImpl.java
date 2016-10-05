package com.xhs.ems.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.xhs.ems.bean.User;
import com.xhs.ems.dao.UserDAO;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {
	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public List<User> validateMrUser(User user) {
		String sql = "select * from AuSp120.tb_MrUser as t where t.工号= :employeeId and t.有效标志= :isValid";
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(
				user);
		final List<User> results = new ArrayList<User>();
		this.npJdbcTemplate.query(sql, namedParameters,
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						User user = new User();
						user.setEmployeeId(rs.getString("工号"));
						user.setName(rs.getString("姓名"));
						user.setPassword(rs.getString("密码"));
						user.setStationName(rs.getString("部门名称"));
						if (!(rs.getString("人员类型").equals("1") || rs.getString(
								"人员类型").equals("2"))) {
							user.setIsValid(3);
						} else {
							user.setIsValid(0);
						}
						results.add(user);
					}
				});
		return results;
	}

	@Override
	public List<User> getAvailableDispatcher() {
		String sql = "select * from AuSp120.tb_MrUser as t where t.人员类型= 0 and t.有效标志= 1";
		final List<User> results = new ArrayList<User>();
		this.npJdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				User user = new User(rs.getString("工号"), rs.getString("姓名"));
				results.add(user);
			}
		});
		return results;
	}

	@Override
	public int changePwd(User user) {
		String sql = "update AuSp120.tb_MrUser set 密码=:password where 工号=:id";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("password", user.getPassword());
		paramMap.put("id", user.getEmployeeId());
		this.npJdbcTemplate.update(sql, paramMap);
		return this.npJdbcTemplate.update(sql, paramMap);
	}
}
