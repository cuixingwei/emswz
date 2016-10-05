package com.xhs.ems.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xhs.ems.bean.Dictionary;
import com.xhs.ems.dao.DictionaryDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年10月9日 下午1:15:57
 */
@Repository
public class DictionaryDAOImpl implements DictionaryDAO {

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public List<Dictionary> getTaskResult() {
		String sql = "select * from AuSp120.tb_DTaskResult ";
		List<Dictionary> results = this.npJdbcTemplate.query(sql,
				new RowMapper<Dictionary>() {
					@Override
					public Dictionary mapRow(ResultSet rs, int index)
							throws SQLException {
						Dictionary dictionary = new Dictionary();
						dictionary.setId(rs.getString("Code"));
						dictionary.setName(rs.getString("NameM"));
						return dictionary;
					}
				});
		return results;
	}

	@Override
	public List<Dictionary> getArea() {
		String sql = "select * from AuSp120.tb_DArea ";
		List<Dictionary> results = this.npJdbcTemplate.query(sql,
				new RowMapper<Dictionary>() {
					@Override
					public Dictionary mapRow(ResultSet rs, int index)
							throws SQLException {
						Dictionary dictionary = new Dictionary();
						dictionary.setId(rs.getString("Code"));
						dictionary.setName(rs.getString("NameM"));
						return dictionary;
					}
				});
		return results;
	}

}
