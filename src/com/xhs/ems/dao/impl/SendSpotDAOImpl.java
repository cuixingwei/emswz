package com.xhs.ems.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xhs.ems.bean.SendSpot;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.SendSpotDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午3:51:41
 */
@Repository
public class SendSpotDAOImpl implements SendSpotDAO {

	private static final Logger logger = Logger
			.getLogger(SendSpotDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select pc.送往地点 address, COUNT(*) times,'' rate 	from AuSp120.tb_PatientCase pc	"
				+ "where pc.记录时刻 between :startTime and :endTime and pc.送往地点 is not null and pc.送往地点<>'' 	group by pc.送往地点 ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<SendSpot> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<SendSpot>() {
					@Override
					public SendSpot mapRow(ResultSet rs, int index)
							throws SQLException {

						return new SendSpot(rs.getString("address"), rs
								.getString("times"), rs.getString("rate"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		int total = 0;
		for (SendSpot result : results) {
			total += Integer.parseInt(result.getTimes());
		}
		for (SendSpot result : results) {
			result.setRate(CommonUtil.calculateRate(total, result.getTimes()));
		}
		Grid grid = new Grid();
		if ((int) parameter.getPage() > 0) {
			int page = (int) parameter.getPage();
			int rows = (int) parameter.getRows();

			int fromIndex = (page - 1) * rows;
			int toIndex = (results.size() <= page * rows && results.size() >= (page - 1)
					* rows) ? results.size() : page * rows;
			grid.setRows(results.subList(fromIndex, toIndex));
			grid.setTotal(results.size());

		} else {
			grid.setRows(results);
		}
		return grid;
	}

}
