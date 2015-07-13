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

import com.xhs.ems.bean.OutCallSpan;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.OutCallSpanDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午2:39:32
 */
@Repository
public class OutCallSpanDAOImpl implements OutCallSpanDAO {
	private static final Logger logger = Logger
			.getLogger(OutCallSpanDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select  DATEPART(HOUR,e.受理时刻) span,COUNT(*) times,SUM(case when t.结果编码=4 then 1 else 0 end) takeBacks,	"
				+ "isnull(AVG(DATEDIFF(Second,e.受理时刻,t.到达现场时刻)),0) averageResponseTime,isnull(sum(datediff(Second,t.出车时刻,t.到达医院时刻)),0) outCallTotal,	isnull(avg(datediff(Second,t.出车时刻,t.到达医院时刻)),0) averageTime	"
				+ "from AuSp120.tb_TaskV t left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码	"
				+ "where e.事件性质编码=1 and e.受理时刻 between :startTime and :endTime	"
				+ "group by DATEPART(HOUR,e.受理时刻) 	order by DATEPART(HOUR,e.受理时刻) ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<OutCallSpan> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<OutCallSpan>() {
					@Override
					public OutCallSpan mapRow(ResultSet rs, int index)
							throws SQLException {

						return new OutCallSpan(rs.getString("span"), rs
								.getString("times"), rs.getString("takeBacks"),
								rs.getString("averageResponseTime"), rs
										.getString("outCallTotal"), rs
										.getString("averageTime"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		for (OutCallSpan result : results) {
			result.setAverageResponseTime(CommonUtil.formatSecond(result
					.getAverageResponseTime()));
			result.setAverageTime(CommonUtil.formatSecond(result
					.getAverageTime()));
			result.setOutCallTotal(CommonUtil.formatSecond(result
					.getOutCallTotal()));
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
