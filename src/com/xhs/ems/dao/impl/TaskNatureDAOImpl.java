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

import com.xhs.ems.bean.TaskNature;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.TaskNatureDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午5:07:24
 */
@Repository
public class TaskNatureDAOImpl implements TaskNatureDAO {

	private static final Logger logger = Logger
			.getLogger(TaskNatureDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select distinct et.NameM 事件类型 ,pc.任务编码,pc.任务序号,t.事件编码, 里程  into #dis 	from AuSp120.tb_PatientCase pc	"
				+ "left outer join AuSp120.tb_TaskV t on t.任务编码=pc.任务编码  and t.任务序号=pc.任务序号 "
				+ "left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码	left outer join AuSp120.tb_DEventType et on et.Code=e.事件类型编码	"
				+ "where e.事件性质编码=1 and e.受理时刻  between :startTime and :endTime "
				+ "select 事件类型  outCallType,SUM(里程) distance into #temp1 "
				+ "from #dis d group by 事件类型   "
				+ "select et.NameM outCallType,COUNT(et.NameM) times,	"
				+ "SUM(case when t.结果编码=4 then 1 else 0 end) takeBacks,'' takeBackRate	"
				+ "into #temp2	"
				+ "from AuSp120.tb_EventV e	left outer join AuSp120.tb_TaskV t on t.事件编码=e.事件编码  left outer join AuSp120.tb_DEventType et on et.Code=e.事件类型编码	"
				+ "where e.事件性质编码=1 and e.受理时刻 between :startTime and :endTime	group by et.NameM  "
				+ "select et.NameM  outCallType,isnull(AVG(DATEDIFF(Second,e.受理时刻,t.到达现场时刻)),0) averageResponseTime,	"
				+ "isnull(AVG(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)),0) averageTime,"
				+ "SUM(case when pc.转归编码=7 then 1 else 0 end) refuseToHospitals,	"
				+ "SUM(case when pc.救治结果编码 in (2,6,7) then 1 else 0 end) deaths,"
				+ "	SUM(case when t.结果编码 in (2,3) then 1 else 0 end) emptyCars into #temp3	"
				+ "from AuSp120.tb_EventV e	left outer join AuSp120.tb_TaskV t on t.事件编码=e.事件编码	"
				+ "left outer join AuSp120.tb_DEventType et on et.Code=e.事件类型编码	"
				+ "left outer join AuSp120.tb_PatientCase pc on pc.任务编码=t.事件编码 and t.任务序号=pc.任务序号	"
				+ "where e.事件性质编码=1 and e.受理时刻 between :startTime and :endTime	group by et.NameM "
				+ "select t2.outCallType,isnull(t1.distance,0) distance,t3.emptyCars,t2.takeBackRate,"
				+ "t2.takeBacks,t2.times,t3.averageResponseTime,t3.averageTime,	t3.deaths,"
				+ "t3.refuseToHospitals	from #temp2 t2	left outer join #temp3 t3 on t3.outCallType=t2.outCallType	"
				+ "left outer join #temp1 t1 on t1.outCallType=t2.outCallType "
				+ "drop table #temp1,#temp2,#temp3,#dis ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<TaskNature> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<TaskNature>() {
					@Override
					public TaskNature mapRow(ResultSet rs, int index)
							throws SQLException {

						return new TaskNature(rs.getString("outCallType"), rs
								.getString("times"), rs.getString("takeBacks"),
								rs.getString("takeBackRate"), rs
										.getString("distance"), rs
										.getString("averageResponseTime"), rs
										.getString("averageTime"), rs
										.getString("emptyCars"), rs
										.getString("refuseToHospitals"), rs
										.getString("deaths"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		Grid grid = new Grid();
		for (TaskNature result : results) {
			result.setAverageResponseTime(CommonUtil.formatSecond(result
					.getAverageResponseTime()));
			result.setAverageTime(CommonUtil.formatSecond(result
					.getAverageTime()));
			result.setTakeBackRate(CommonUtil.calculateRate(
					Integer.parseInt(result.getTimes()), result.getTakeBacks()));
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
