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

import com.xhs.ems.bean.StationTaskType;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.StationTaskTypeDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午4:23:08
 */
@Repository
public class StationTaskTypeDAOImpl implements StationTaskTypeDAO {

	private static final Logger logger = Logger
			.getLogger(StationTaskTypeDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select distinct pc.任务编码,pc.任务序号,pc.里程,pc.出诊地址 into #pc "
				+ "from AuSp120.tb_PatientCase pc  select pc.出诊地址 station,COUNT(*) ztimes,"
				+ "AVG(DATEDIFF(Second,e.受理时刻,t.到达现场时刻)) zaverageResponseTime,	"
				+ "avg(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)) zaverageTime,SUM(pc.里程) zdistance into #temp1	"
				+ "from AuSp120.tb_EventV e left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=e.事件编码 "
				+ "left outer join AuSp120.tb_TaskV t on t.事件编码=a.事件编码 and t.受理序号=a.受理序号	"
				+ "left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码 	"
				+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and e.受理时刻 between :startTime and :endTime	group by pc.出诊地址  "
				+ "select pc.出诊地址 station,COUNT(*) xctimes,AVG(DATEDIFF(Second,e.受理时刻,t.到达现场时刻)) xcaverageResponseTime,	"
				+ "avg(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)) xcaverageTime,SUM(pc.里程) xcdistance into #temp2	"
				+ "from AuSp120.tb_EventV e left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=e.事件编码 "
				+ "left outer join AuSp120.tb_TaskV t on t.事件编码=a.事件编码 and t.受理序号=a.受理序号	"
				+ "left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码  	"
				+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and e.事件类型编码=1 and e.受理时刻 between :startTime and :endTime	group by pc.出诊地址  "
				+ "select pc.出诊地址 station,COUNT(*) yytimes,AVG(DATEDIFF(Second,e.受理时刻,t.到达现场时刻)) yyaverageResponseTime,	"
				+ "avg(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)) yyaverageTime,SUM(pc.里程) yydistance into #temp3	"
				+ "from AuSp120.tb_EventV e left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=e.事件编码 "
				+ "left outer join AuSp120.tb_TaskV t on t.事件编码=a.事件编码 and t.受理序号=a.受理序号  	"
				+ "left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码  "
				+ "	where e.事件性质编码=1 and a.类型编码 not in (2,4) and e.事件类型编码=2 and e.受理时刻 between :startTime and :endTime	group by pc.出诊地址 "
				+ " select t1.station,isnull(t1.zaverageResponseTime,0) zaverageResponseTime,	"
				+ "isnull(t1.zaverageTime,0) zaverageTime,isnull(t1.zdistance,0) zdistance,isnull(t1.ztimes,0) ztimes,isnull(t2.xcaverageResponseTime,0) xcaverageResponseTime,	"
				+ "isnull(t2.xcaverageTime,0) xcaverageTime,isnull(t2.xcdistance,0) xcdistance,	"
				+ "isnull(t2.xctimes,0) xctimes,isnull(t3.yyaverageResponseTime,0) yyaverageResponseTime,	isnull(t3.yyaverageTime,0) yyaverageTime,isnull(t3.yydistance,0) yydistance,isnull(t3.yytimes,0) yytimes	"
				+ "from #temp1 t1 left outer join #temp2 t2 on t1.station=t2.station	"
				+ "left outer join #temp3 t3 on t1.station=t3.station  where t1.station is not null and t1.station<>'' "
				+ "drop table #pc,#temp1,#temp2,#temp3 ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<StationTaskType> results = this.npJdbcTemplate.query(sql,
				paramMap, new RowMapper<StationTaskType>() {
					@Override
					public StationTaskType mapRow(ResultSet rs, int index)
							throws SQLException {

						return new StationTaskType(rs.getString("station"), rs
								.getString("ztimes"),
								rs.getString("zdistance"), rs
										.getString("zaverageResponseTime"), rs
										.getString("zaverageTime"), rs
										.getString("xctimes"), rs
										.getString("xcdistance"), rs
										.getString("xcaverageResponseTime"), rs
										.getString("xcaverageTime"), rs
										.getString("yytimes"), rs
										.getString("yydistance"), rs
										.getString("yyaverageResponseTime"), rs
										.getString("yyaverageTime"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		Grid grid = new Grid();
		for (StationTaskType result : results) {
			result.setZaverageResponseTime(CommonUtil.formatSecond(result
					.getZaverageResponseTime()));
			result.setZaverageTime(CommonUtil.formatSecond(result
					.getZaverageTime()));
			result.setXcaverageResponseTime(CommonUtil.formatSecond(result
					.getXcaverageResponseTime()));
			result.setXcaverageTime(CommonUtil.formatSecond(result
					.getXcaverageTime()));
			result.setYyaverageResponseTime(CommonUtil.formatSecond(result
					.getYyaverageResponseTime()));
			result.setYyaverageTime(CommonUtil.formatSecond(result
					.getYyaverageTime()));
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
