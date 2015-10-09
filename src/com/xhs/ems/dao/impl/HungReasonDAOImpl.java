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

import com.xhs.ems.bean.HungReason;
import com.xhs.ems.dao.HungReasonDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午12:50:25
 */
@Repository
public class HungReasonDAOImpl implements HungReasonDAO {

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月15日 下午12:50:25
	 * @see com.xhs.ems.dao.HungReasonDAO#getData()
	 */
	@Override
	public List<HungReason> getData() {
		String sql = "select * from AuSp120.tb_DHangReason";
		final List<HungReason> results = new ArrayList<HungReason>();
		this.npJdbcTemplate.query(sql, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				HungReason hungReason = new HungReason(rs.getString("Code"), rs
						.getString("NameM"));
				results.add(hungReason);
			}
		});
		return results;

	}

}
