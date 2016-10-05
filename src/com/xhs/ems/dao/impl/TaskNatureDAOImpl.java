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
		String sql = "select distinct et.NameM 事件类型 ,pc.任务编码,pc.任务序号,t.事件编码, 里程,a.区域编码 into #dis 	"
				+ "from AuSp120.tb_AcceptDescriptV a	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
				+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
				+ "left outer join AuSp120.tb_PatientCase pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码	"
				+ "left outer join AuSp120.tb_DEventType et on et.Code=e.事件类型编码	"
				+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and e.受理时刻 between :startTime and :endTime	"
				+ "select 事件类型 outCallType,SUM(里程) distance,COUNT(事件类型) times,SUM(case when 区域编码=1 then 1 else 0 end) shiqu,	"
				+ "SUM(case when 区域编码=2 then 1 else 0 end) wanzhou,SUM(case when 区域编码 not in (1,2) then 1 else 0 end) others "
				+ "into #temp1 from #dis d group by 事件类型	select et.NameM  outCallType,"
				+ "isnull(AVG(DATEDIFF(Second,e.受理时刻,t.到达现场时刻)),0) averageResponseTime,	"
				+ "isnull(AVG(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)),0) averageTime,"
				+ "SUM(case when pc.转归编码=7 then 1 else 0 end) refuseToHospitals,	"
				+ "SUM(case when pc.转归编码 in (5,6) then 1 else 0 end) deaths,SUM(case when pc.转归编码=1 then 1 else 0 end) takeBacks,"
				+ "COUNT(pc.转归编码) takeBackRate,	SUM(case when pc.转归编码=10 then 1 else 0 end) emptyCars into #temp3	"
				+ "from AuSp120.tb_AcceptDescriptV a	"
				+ "left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
				+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
				+ "left outer join AuSp120.tb_PatientCase pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码	"
				+ "left outer join AuSp120.tb_DEventType et on et.Code=e.事件类型编码	"
				+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and e.受理时刻 between :startTime and :endTime	"
				+ "group by et.NameM	select t1.outCallType,isnull(t1.distance,0) distance,t3.emptyCars,t3.takeBackRate,t3.takeBacks,t1.times,"
				+ "t3.averageResponseTime,t3.averageTime,	t3.deaths,t3.refuseToHospitals,ISNULL(t1.shiqu,0) shiqu,"
				+ "isnull(t1.wanzhou,0) wanzhou,ISNULL(t1.others,0) others	from #temp3 t3	"
				+ "left outer join #temp1 t1 on t3.outCallType=t1.outCallType  drop table #temp1,#temp3,#dis ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<TaskNature> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<TaskNature>() {
					@Override
					public TaskNature mapRow(ResultSet rs, int index)
							throws SQLException {
						TaskNature taskNature = new TaskNature();
						taskNature.setAverageResponseTime(rs
								.getString("averageResponseTime"));
						taskNature.setAverageTime(rs.getString("averageTime"));
						taskNature.setDeaths(rs.getString("deaths"));
						taskNature.setDistance(rs.getString("distance"));
						taskNature.setEmptyCars(rs.getString("emptyCars"));
						taskNature.setOthers(rs.getString("others"));
						taskNature.setOutCallType(rs.getString("outCallType"));
						taskNature.setRefuseToHospitals(rs
								.getString("refuseToHospitals"));
						taskNature.setShiqu(rs.getString("shiqu"));
						taskNature.setTakeBackRate(rs.getString("takeBackRate"));
						taskNature.setTakeBacks(rs.getString("takeBacks"));
						taskNature.setTimes(rs.getString("times"));
						taskNature.setWanzhou(rs.getString("wanzhou"));
						return taskNature;

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
					Integer.parseInt(result.getTakeBackRate()),
					result.getTakeBacks()));
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
