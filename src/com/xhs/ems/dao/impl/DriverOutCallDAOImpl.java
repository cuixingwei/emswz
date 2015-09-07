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

import com.xhs.ems.bean.DriverOutCall;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.DriverOutCallDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 上午9:19:29
 */
@Repository
public class DriverOutCallDAOImpl implements DriverOutCallDAO {
	private static final Logger logger = Logger
			.getLogger(CaseQualityDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select t.司机 name,COUNT(*) outCalls,SUM(case when t.结果编码=4 then 1 else 0 end) takeBacks,	"
				+ "AVG(DATEDIFF(Second,e.受理时刻,t.到达现场时刻)) averageResponseTime,"
				+ "SUM(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)) outCallTimeTotal,	"
				+ "avg(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)) averageTime into #temp1	"
				+ "from AuSp120.tb_TaskV t	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码	"
				+ "where e.事件性质编码=1 and t.司机 is not null and t.司机<>'' and e.受理时刻 between :startTime and :endTime	group by t.司机  "
				+ "select distinct t.司机 ,pc.任务编码,pc.任务序号,t.事件编码, 里程 into #dis	"
				+ "from AuSp120.tb_PatientCase pc	"
				+ "left outer join AuSp120.tb_TaskV t on t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号 	"
				+ "left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码	"
				+ "where e.事件性质编码=1 and t.司机 is not null and t.司机<>'' and e.受理时刻 between :startTime and :endTime  "
				+ "select 司机 name,SUM(里程) distance into #temp2 from #dis d group by 司机  "
				+ "select t1.name,isnull(t1.averageResponseTime,0) averageResponseTime,"
				+ "isnull(t1.averageTime,0) averageTime,isnull(t1.outCallTimeTotal,0) outCallTimeTotal,	t1.outCalls,t1.takeBacks,isnull(t2.distance,0) distance	"
				+ "from #temp1 t1 left outer join #temp2 t2 on t1.name=t2.name  "
				+ "drop table #dis,#temp1,#temp2 ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		logger.info(sql);
		List<DriverOutCall> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<DriverOutCall>() {
					@Override
					public DriverOutCall mapRow(ResultSet rs, int index)
							throws SQLException {

						return new DriverOutCall(rs.getString("name"), rs
								.getString("outCalls"), rs
								.getString("takeBacks"), rs
								.getString("distance"), rs
								.getString("averageResponseTime"), rs
								.getString("outCallTimeTotal"), rs
								.getString("averageTime"));
					}
				});
		Grid grid = new Grid();
		for (DriverOutCall result : results) {
			result.setAverageResponseTime(CommonUtil.formatSecond(result
					.getAverageResponseTime()));
			result.setAverageTime(CommonUtil.formatSecond(result
					.getAverageTime()));
			result.setOutCallTimeTotal(CommonUtil.formatSecond(result
					.getOutCallTimeTotal()));
		}
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
