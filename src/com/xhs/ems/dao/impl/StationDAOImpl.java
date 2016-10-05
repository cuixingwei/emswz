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

import com.xhs.ems.bean.Station;
import com.xhs.ems.dao.StationDAO;

/**
 * @author CUIXINGWEI
 * @datetime 2015年3月30日 下午5:20:32
 */
@Repository
public class StationDAOImpl implements StationDAO {

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author CUIXINGWEI
	 * @see com.xhs.ems.dao.StationDAO#getData()
	 * @datetime 2015年3月30日 下午5:20:32
	 */
	@Override
	public List<Station> getData() {
		String sql = "select * from AuSp120.tb_Station order by 显示顺序 asc";
		final List<Station> results = new ArrayList<Station>();
		this.npJdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				Station station = new Station(rs.getString("分站编码"), rs
						.getString("分站名称"), rs.getString("显示顺序"));
				results.add(station);
			}
		});
		return results;
	}

}
