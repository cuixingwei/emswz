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

import com.xhs.ems.bean.PauseReason;
import com.xhs.ems.dao.PauseReasonDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日 下午5:10:52
 */
@Repository
public class PauseReasonDAOImpl implements PauseReasonDAO {

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月13日 下午5:10:52
	 * @see com.xhs.ems.dao.PauseReasonDAO#getData()
	 */
	@Override
	public List<PauseReason> getData() {
		String sql = "select * from AuSp120.tb_DPauseReason";
		final List<PauseReason> results = new ArrayList<PauseReason>();
		this.npJdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				PauseReason pauseReason = new PauseReason(rs.getString("Code"),
						rs.getString("NameM"));
				results.add(pauseReason);
			}
		});
		return results;
	}

}
