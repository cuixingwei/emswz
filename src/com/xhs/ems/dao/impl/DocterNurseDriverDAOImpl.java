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

import com.xhs.ems.bean.DocterNurseDriver;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.DocterNurseDriverDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年5月21日 下午10:04:46
 */
@Repository
public class DocterNurseDriverDAOImpl implements DocterNurseDriverDAO {
	private static final Logger logger = Logger
			.getLogger(DocterNurseDriverDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String doctorOrNurseOrDriver = parameter.getDoctorOrNurseOrDriver();
		Grid grid = new Grid();
		if (null == doctorOrNurseOrDriver) {
			grid.setTotal(0);
			return grid;
		} else {
			String sql = "";
			if ("1".equals(doctorOrNurseOrDriver)) {
				sql = "select s.分站名称 station,pc.随车医生 name, COUNT(*) outCalls,SUM(case when t.结果编码=4 then 1 else 0 end) takeBacks,"
						+ "	SUM(case when t.结果编码 in (2,3) then 1 else 0 end) emptyCars,SUM(case when pc.转归编码=7 then 1 else 0 end) refuseHospitals,	"
						+ "SUM(case when pc.救治结果编码=2 then 1 else 0 end) spotDeaths,SUM(case when pc.救治结果编码  in (6,7) then 1 else 0 end) afterDeaths,	"
						+ "SUM(case when e.事件类型编码=3 then 1 else 0 end) inHospitalTransports,"
						+ "SUM(case when e.事件类型编码=10 then 1 else 0 end) others,	"
						+ "SUM(case when pc.任务编码 is not null then 1 else 0 end) cureNumbers into #temp1	"
						+ "from AuSp120.tb_TaskV  t	left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码	"
						+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	left outer join AuSp120.tb_Station s on  s.分站编码=t.分站编码	"
						+ "where e.事件性质编码=1 and pc.随车医生 is not null and pc.随车医生<>'' and e.受理时刻  between :startTime and :endTime	"
						+ "group by s.分站名称,pc.随车医生    "
						+ "select distinct s.分站名称 station,pc.随车医生 name,pc.任务序号,pc.任务编码,pc.里程,cr.收费金额,	e.受理时刻,t.到达医院时刻,t.到达现场时刻,t.出车时刻 into #temp2	"
						+ "from AuSp120.tb_TaskV  t	left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码	"
						+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	left outer join AuSp120.tb_Station s on  s.分站编码=t.分站编码	"
						+ "left outer join AuSp120.tb_ChargeRecord cr on t.任务编码=cr.任务编码 and cr.车辆标识=t.车辆标识  	"
						+ "where e.事件性质编码=1 and pc.随车医生 is not null and pc.随车医生<>'' and e.受理时刻 between :startTime and :endTime	  "
						+ "select t2.station,t2.name,SUM(t2.里程) distanceTotal,SUM(t2.收费金额) costToal,	"
						+ "AVG(DATEDIFF(Second,t2.受理时刻,t2.到达现场时刻)) averageResponseTime,	sum(datediff(Second,t2.出车时刻,t2.到达医院时刻)) outCallTimeTotal "
						+ "into #temp3	from #temp2 t2	group by t2.station,t2.name "
						+ "select t1.station,t1.name,t1.afterDeaths,t1.cureNumbers,t1.emptyCars,t1.inHospitalTransports,t1.takeBacks,"
						+ "t1.others,t1.outCalls,t1.refuseHospitals,t1.spotDeaths,isnull(t3.averageResponseTime,0) averageResponseTime,"
						+ "isnull(t3.costToal,0) costToal,	t3.distanceTotal,isnull(t3.outCallTimeTotal,0) outCallTimeTotal	"
						+ "from #temp1 t1 left outer join #temp3 t3 on t1.name=t3.name and t1.station=t3.station  "
						+ "drop table #temp1,#temp2,#temp3 ";
			} else if ("2".equals(doctorOrNurseOrDriver)) {
				sql = "select s.分站名称 station,pc.随车护士 name, COUNT(*) outCalls,SUM(case when t.结果编码=4 then 1 else 0 end) takeBacks,"
						+ "	SUM(case when t.结果编码 in (2,3) then 1 else 0 end) emptyCars,SUM(case when pc.转归编码=7 then 1 else 0 end) refuseHospitals,	"
						+ "SUM(case when pc.救治结果编码=2 then 1 else 0 end) spotDeaths,SUM(case when pc.救治结果编码  in (6,7) then 1 else 0 end) afterDeaths,	"
						+ "SUM(case when e.事件类型编码=3 then 1 else 0 end) inHospitalTransports,"
						+ "SUM(case when e.事件类型编码=10 then 1 else 0 end) others,	"
						+ "SUM(case when pc.任务编码 is not null then 1 else 0 end) cureNumbers into #temp1	"
						+ "from AuSp120.tb_TaskV  t	left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码	"
						+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	left outer join AuSp120.tb_Station s on  s.分站编码=t.分站编码	"
						+ "where e.事件性质编码=1 and pc.随车护士 is not null and pc.随车护士<>'' and e.受理时刻  between :startTime and :endTime	"
						+ "group by s.分站名称,pc.随车护士    "
						+ "select distinct s.分站名称 station,pc.随车护士 name,pc.任务序号,pc.任务编码,pc.里程,cr.收费金额,	e.受理时刻,t.到达医院时刻,t.到达现场时刻,t.出车时刻 into #temp2	"
						+ "from AuSp120.tb_TaskV  t	left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码	"
						+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	left outer join AuSp120.tb_Station s on  s.分站编码=t.分站编码	"
						+ "left outer join AuSp120.tb_ChargeRecord cr on t.任务编码=cr.任务编码 and cr.车辆标识=t.车辆标识  	"
						+ "where e.事件性质编码=1 and pc.随车护士 is not null and pc.随车护士<>'' and e.受理时刻 between :startTime and :endTime	  "
						+ "select t2.station,t2.name,SUM(t2.里程) distanceTotal,SUM(t2.收费金额) costToal,	"
						+ "AVG(DATEDIFF(Second,t2.受理时刻,t2.到达现场时刻)) averageResponseTime,	sum(datediff(Second,t2.出车时刻,t2.到达医院时刻)) outCallTimeTotal "
						+ "into #temp3	from #temp2 t2	group by t2.station,t2.name "
						+ "select t1.station,t1.name,t1.afterDeaths,t1.cureNumbers,t1.emptyCars,t1.inHospitalTransports,t1.takeBacks,"
						+ "t1.others,t1.outCalls,t1.refuseHospitals,t1.spotDeaths,isnull(t3.averageResponseTime,0) averageResponseTime,"
						+ "isnull(t3.costToal,0) costToal,	t3.distanceTotal,isnull(t3.outCallTimeTotal,0) outCallTimeTotal	"
						+ "from #temp1 t1 left outer join #temp3 t3 on t1.name=t3.name and t1.station=t3.station  "
						+ "drop table #temp1,#temp2,#temp3 ";
			} else if ("3".equals(doctorOrNurseOrDriver)) {
				sql = "select s.分站名称 station,pc.司机 name, COUNT(*) outCalls,SUM(case when t.结果编码=4 then 1 else 0 end) takeBacks,"
						+ "	SUM(case when t.结果编码 in (2,3) then 1 else 0 end) emptyCars,SUM(case when pc.转归编码=7 then 1 else 0 end) refuseHospitals,	"
						+ "SUM(case when pc.救治结果编码=2 then 1 else 0 end) spotDeaths,SUM(case when pc.救治结果编码 in (6,7) then 1 else 0 end) afterDeaths,	"
						+ "SUM(case when e.事件类型编码=3 then 1 else 0 end) inHospitalTransports,"
						+ "SUM(case when e.事件类型编码=10 then 1 else 0 end) others,	"
						+ "SUM(case when pc.任务编码 is not null then 1 else 0 end) cureNumbers into #temp1	"
						+ "from AuSp120.tb_TaskV  t	left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码	"
						+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	left outer join AuSp120.tb_Station s on  s.分站编码=t.分站编码	"
						+ "where e.事件性质编码=1 and pc.司机 is not null and pc.司机<>'' and e.受理时刻  between :startTime and :endTime	"
						+ "group by s.分站名称,pc.司机    "
						+ "select distinct s.分站名称 station,pc.司机 name,pc.任务序号,pc.任务编码,pc.里程,cr.收费金额,	e.受理时刻,t.到达医院时刻,t.到达现场时刻,t.出车时刻 into #temp2	"
						+ "from AuSp120.tb_TaskV  t	left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码	"
						+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	left outer join AuSp120.tb_Station s on  s.分站编码=t.分站编码	"
						+ "left outer join AuSp120.tb_ChargeRecord cr on t.任务编码=cr.任务编码 and cr.车辆标识=t.车辆标识  	"
						+ "where e.事件性质编码=1 and pc.司机 is not null and pc.司机<>'' and e.受理时刻 between :startTime and :endTime	  "
						+ "select t2.station,t2.name,SUM(t2.里程) distanceTotal,SUM(t2.收费金额) costToal,	"
						+ "AVG(DATEDIFF(Second,t2.受理时刻,t2.到达现场时刻)) averageResponseTime,	sum(datediff(Second,t2.出车时刻,t2.到达医院时刻)) outCallTimeTotal "
						+ "into #temp3	from #temp2 t2	group by t2.station,t2.name  "
						+ "select t1.station,t1.name,t1.afterDeaths,t1.cureNumbers,t1.emptyCars,t1.inHospitalTransports,t1.takeBacks,"
						+ "t1.others,t1.outCalls,t1.refuseHospitals,t1.spotDeaths,isnull(t3.averageResponseTime,0) averageResponseTime,"
						+ "isnull(t3.costToal,0) costToal,	t3.distanceTotal,isnull(t3.outCallTimeTotal,0) outCallTimeTotal	"
						+ "from #temp1 t1 left outer join #temp3 t3 on t1.name=t3.name and t1.station=t3.station  "
						+ "drop table #temp1,#temp2,#temp3 ";
			}

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("startTime", parameter.getStartTime());
			paramMap.put("endTime", parameter.getEndTime());

			List<DocterNurseDriver> results = this.npJdbcTemplate.query(sql,
					paramMap, new RowMapper<DocterNurseDriver>() {
						@Override
						public DocterNurseDriver mapRow(ResultSet rs, int index)
								throws SQLException {

							return new DocterNurseDriver(rs
									.getString("station"),
									rs.getString("name"), rs
											.getString("outCalls"), rs
											.getString("takeBacks"), rs
											.getString("emptyCars"), rs
											.getString("refuseHospitals"), rs
											.getString("spotDeaths"), rs
											.getString("afterDeaths"), rs
											.getString("inHospitalTransports"),
									rs.getString("others"), rs
											.getString("distanceTotal"), rs
											.getString("costToal"), rs
											.getString("averageResponseTime"),
									rs.getString("outCallTimeTotal"), rs
											.getString("cureNumbers"));
						}
					});
			logger.info("一共有" + results.size() + "条数据");
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
}
