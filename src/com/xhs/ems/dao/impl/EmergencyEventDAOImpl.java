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

import com.xhs.ems.bean.EmergencyEvent;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.EmergencyEventDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午12:49:46
 */
@Repository
public class EmergencyEventDAOImpl implements EmergencyEventDAO {

	private static final Logger logger = Logger
			.getLogger(EmergencyEventDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select gat.NameM eventType,COUNT(gat.NameM) times  into #temp1 	"
				+ "from AuSp120.tb_EventV e	left outer join AuSp120.tb_DGroAccidentType gat on gat.Code=e.事故种类编码	"
				+ "where e.事件性质编码=1 and e.事故种类编码<>0 and e.受理时刻  between :startTime and :endTime 	group by gat.NameM  "
				+ "select gat.NameM  eventType,COUNT(*) casualties,"
				+ "SUM(case when pc.病情编码=3 then 1 else 0 end) light,	"
				+ "SUM(case when pc.病情编码=2 then 1 else 0 end) middle, "
				+ "SUM(case when pc.病情编码=5 then 1 else 0 end) heavy,	"
				+ "SUM(case when pc.救治结果编码 in (2,6,7) then 1 else 0 end) death  into #temp2 	"
				+ "from AuSp120.tb_PatientCase pc	"
				+ "left outer join AuSp120.tb_TaskV t on t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号 	"
				+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
				+ "left outer join AuSp120.tb_DGroAccidentType gat on gat.Code=e.事故种类编码	"
				+ "where e.事件性质编码=1 and e.事故种类编码<>0 and e.受理时刻  between :startTime and :endTime group by gat.NameM  "
				+ "select distinct gat.NameM  eventType,pc.里程,e.受理时刻,t.到达现场时刻,t.出车时刻,t.到达医院时刻,"
				+ "t.任务序号,t.任务编码 into #temp3 	from AuSp120.tb_PatientCase pc	"
				+ "left outer join AuSp120.tb_TaskV t on t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号 	"
				+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
				+ "left outer join AuSp120.tb_DGroAccidentType gat on gat.Code=e.事故种类编码	"
				+ "where e.事件性质编码=1 and e.事故种类编码<>0  and e.受理时刻  between :startTime and :endTime "
				+ "select  t3.eventType,SUM(t3.里程) distance,"
				+ "AVG(DATEDIFF(Second,t3.受理时刻,t3.到达现场时刻)) responseTime,	"
				+ "SUM(DATEDIFF(Second,t3.出车时刻,t3.到达医院时刻)) timeTotal into #temp4	"
				+ "from #temp3 t3	group by t3.eventType  "
				+ "select t1.eventType,t1.times,isnull(t2.casualties,0) casualties,isnull(t2.death,0) death,"
				+ "isnull(t2.heavy,0) heavy,	isnull(t2.light,0) light,isnull(t2.middle,0) middle,"
				+ "isnull(t4.distance,0) distance,isnull(t4.responseTime,0) responseTime,"
				+ "isnull(t4.timeTotal,0) timeTotal	from #temp1 t1 "
				+ "left outer join #temp2 t2 on t1.eventType=t2.eventType	"
				+ "left outer join #temp4 t4  on t1.eventType=t4.eventType  "
				+ "drop table #temp1,#temp2,#temp3,#temp4 ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<EmergencyEvent> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<EmergencyEvent>() {
					@Override
					public EmergencyEvent mapRow(ResultSet rs, int index)
							throws SQLException {

						return new EmergencyEvent(rs.getString("eventType"), rs
								.getString("times"),
								rs.getString("casualties"), rs
										.getString("light"), rs
										.getString("middle"), rs
										.getString("heavy"), rs
										.getString("death"), rs
										.getString("distance"), rs
										.getString("responseTime"), rs
										.getString("timeTotal"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");
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
