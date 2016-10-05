package com.xhs.ems.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xhs.ems.bean.EmptyReason;
import com.xhs.ems.dao.EmptyReasonDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月14日 上午11:15:06
 */
@Repository
public class EmptyReasonDAOImpl implements EmptyReasonDAO {

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月14日 上午11:15:06
	 * @see com.xhs.ems.dao.EmptyReasonDAO#getData()
	 */
	@Override
	public List<EmptyReason> getData() {
		String sql = "select * from AuSp120.tb_DEmptyReason";
		final List<EmptyReason> results = new ArrayList<EmptyReason>();
		this.npJdbcTemplate.query(sql, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				EmptyReason emptyCarReason = new EmptyReason(rs
						.getString("Code"), rs.getString("NameM"));
				results.add(emptyCarReason);
			}
		});
		return results;

	}

}
