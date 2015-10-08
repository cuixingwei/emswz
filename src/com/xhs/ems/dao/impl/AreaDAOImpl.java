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

import com.xhs.ems.bean.Area;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.AreaDAO;

@Repository
public class AreaDAOImpl implements AreaDAO {

	private static final Logger logger = Logger.getLogger(AreaDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select distinct pc.任务序号,pc.任务编码,pc.里程,pc.转归编码  into #pc	"
				+ "from AuSp120.tb_PatientCase pc select da.NameM area,COUNT(t.任务编码) outCalls,"
				+ "SUM(case when pc.转归编码=1 then 1 else 0 end) takeBacks,isnull(sum(pc.里程),0) distance,	"
				+ "isnull(SUM(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)),0) outCallTime,isnull(AVG(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)),0) averageTime	"
				+ "from AuSp120.tb_AcceptDescriptV a	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
				+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	left outer join AuSp120.tb_DArea da on da.Code=a.区域编码	"
				+ "left outer join #pc pc on t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号 	"
				+ "where e.事件性质编码=1 and e.受理时刻 between :startTime and :endTime	"
				+ "group by da.NameM drop table #pc ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<Area> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<Area>() {
					@Override
					public Area mapRow(ResultSet rs, int index)
							throws SQLException {

						return new Area(rs.getString("area"), rs
								.getString("outCalls"), rs
								.getString("takeBacks"), rs
								.getString("distance"), rs
								.getString("outCallTime"), rs
								.getString("averageTime"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		for(Area result : results){
			result.setAverageTime(CommonUtil.formatSecond(result.getAverageTime()));
			result.setOutCallTime(CommonUtil.formatSecond(result.getOutCallTime()));
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
