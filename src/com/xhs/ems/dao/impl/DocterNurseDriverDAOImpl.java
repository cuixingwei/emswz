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
import com.xhs.ems.common.CommonUtil;
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
		final String doctorOrNurseOrDriver = parameter
				.getDoctorOrNurseOrDriver();
		Grid grid = new Grid();
		if (null == doctorOrNurseOrDriver) {
			grid.setTotal(0);
			return grid;
		} else {
			String sql = "";
			if ("1".equals(doctorOrNurseOrDriver)) {
				sql = "select distinct 任务编码,病例序号,任务序号  into #cm from AuSp120.tb_CureMeasure "
						+ "select s.分站名称 station,pc.随车医生 name,SUM(case when cm.ID is not null then 1 else 0 end) cureNumbers  into #temp6	"
						+ "from AuSp120.tb_AcceptDescriptV a	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
						+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
						+ "left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码	"
						+ "left outer join AuSp120.tb_CureMeasure cm on cm.任务序号=pc.任务序号 and cm.任务编码=pc.任务编码 and cm.病例序号=pc.序号	"
						+ "left outer join AuSp120.tb_Station s on  s.分站编码=t.分站编码	"
						+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and pc.随车医生 is not null and pc.随车医生<>'' and e.受理时刻 between :startTime and :endTime	group by s.分站名称,pc.随车医生 "
						+ "select s.分站名称 station,pc.随车医生 name, SUM(case when pc.转归编码=1 then 1 else 0 end) takeBacks,"
						+ "SUM(case when pc.转归编码=10 then 1 else 0 end) emptyCars,"
						+ "SUM(case when pc.转归编码=7 then 1 else 0 end) refuseHospitals,	"
						+ "SUM(case when pc.转归编码=5 then 1 else 0 end) spotDeaths,"
						+ "SUM(case when pc.转归编码=6 then 1 else 0 end) afterDeaths,	"
						+ "SUM(case when pc.转归编码=8 then 1 else 0 end) others,"
						+ "SUM(case when pc.转归编码=12 then 1 else 0 end) safeOut,	"
						+ "SUM(case when pc.转归编码=11 then 1 else 0 end) noAmbulance into #temp1	"
						+ "from AuSp120.tb_AcceptDescriptV a	"
						+ "left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
						+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
						+ "left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码	"
						+ "left outer join #cm cm on cm.任务序号=pc.任务序号 and cm.任务编码=pc.任务编码 and cm.病例序号=pc.序号	"
						+ "left outer join AuSp120.tb_Station s on  s.分站编码=t.分站编码	"
						+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and pc.随车医生 is not null and pc.随车医生<>'' "
						+ "and e.受理时刻 between :startTime and :endTime	group by s.分站名称,pc.随车医生	"
						+ "select distinct s.分站名称 station,pc.随车医生 name,pc.任务序号,pc.任务编码,pc.里程,cr.收费金额,t.接收命令时刻,	"
						+ "e.受理时刻,t.到达医院时刻,t.到达现场时刻,t.出车时刻 into #temp2	"
						+ "from AuSp120.tb_AcceptDescriptV a	"
						+ "left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
						+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
						+ "left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码	"
						+ "left outer join AuSp120.tb_Station s on  s.分站编码=t.分站编码	"
						+ "left outer join AuSp120.tb_ChargeRecord cr on t.任务编码=cr.任务编码 and cr.车辆标识=t.车辆标识	"
						+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and pc.随车医生 is not null and pc.随车医生<>'' "
						+ "and e.受理时刻 between :startTime and :endTime	"
						+ "select distinct s.分站名称 station,pc.随车医生 name,pc.里程,t.接收命令时刻,	e.受理时刻,t.到达医院时刻,t.到达现场时刻,"
						+ "t.出车时刻 into #temp4	from AuSp120.tb_AcceptDescriptV a	"
						+ "left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
						+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
						+ "left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码	"
						+ "left outer join AuSp120.tb_Station s on  s.分站编码=t.分站编码	"
						+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and pc.随车医生 is not null "
						+ "and pc.随车医生<>'' and e.受理时刻 between :startTime and :endTime	"
						+ "select t2.station,t2.name,SUM(t2.收费金额) costToal into #temp5	from #temp2 t2	"
						+ "group by t2.station,t2.name	select t4.station,t4.name,SUM(t4.里程) distanceTotal,COUNT(*) outCalls,"
						+ "	AVG(DATEDIFF(Second,t4.受理时刻,t4.到达现场时刻)) averageResponseTime,	AVG(DATEDIFF(Second,t4.接收命令时刻,t4.出车时刻)) averageSendTime,	"
						+ "sum(datediff(Second,t4.出车时刻,t4.到达医院时刻)) outCallTimeTotal into #temp3	from #temp4 t4	group by t4.station,"
						+ "t4.name	select t1.station,t1.name,t1.afterDeaths,t6.cureNumbers,t1.emptyCars,t1.safeOut,t1.takeBacks,t1.noAmbulance,"
						+ "t1.others,t3.outCalls,	t1.refuseHospitals,t1.spotDeaths,isnull(t3.averageResponseTime,0) averageResponseTime,"
						+ "isnull(t3.averageSendTime,0) averageSendTime,	isnull(t5.costToal,0) costToal,	t3.distanceTotal,"
						+ "isnull(t3.outCallTimeTotal,0) outCallTimeTotal	from #temp1 t1 left outer join #temp3 t3 on t1.name=t3.name and t1.station=t3.station	"
						+ "left outer join #temp5 t5 on t1.name=t5.name and t1.station=t5.station "
						+ "left outer join #temp6 t6 on t1.name=t6.name and t1.station=t6.station order by t1.station "
						+ "drop table #temp1,#temp2,#temp3,#temp4,#temp5,#cm,#temp6 ";
			} else if ("2".equals(doctorOrNurseOrDriver)) {
				sql = "select distinct 任务编码,病例序号,任务序号  into #cm from AuSp120.tb_CureMeasure	"
						+ "select s.分站名称 station,pc.随车护士 name,SUM(case when cm.ID is not null then 1 else 0 end) cureNumbers  into #temp6	"
						+ "from AuSp120.tb_AcceptDescriptV a	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
						+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
						+ "left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码	"
						+ "left outer join AuSp120.tb_CureMeasure cm on cm.任务序号=pc.任务序号 and cm.任务编码=pc.任务编码 and cm.病例序号=pc.序号	"
						+ "left outer join AuSp120.tb_Station s on  s.分站编码=t.分站编码	"
						+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and pc.随车护士 is not null and pc.随车护士<>'' and e.受理时刻 between :startTime and :endTime	group by s.分站名称,pc.随车护士 "
						+ "select s.分站名称 station,pc.随车护士 name, SUM(case when pc.转归编码=1 then 1 else 0 end) takeBacks,	"
						+ "SUM(case when pc.转归编码=10 then 1 else 0 end) emptyCars,"
						+ "SUM(case when pc.转归编码=7 then 1 else 0 end) refuseHospitals,	"
						+ "SUM(case when pc.转归编码=5 then 1 else 0 end) spotDeaths,"
						+ "SUM(case when pc.转归编码=6 then 1 else 0 end) afterDeaths,	"
						+ "SUM(case when pc.转归编码=8 then 1 else 0 end) others,"
						+ "SUM(case when pc.转归编码=12 then 1 else 0 end) safeOut,	"
						+ "SUM(case when pc.转归编码=11 then 1 else 0 end) noAmbulance into #temp1	"
						+ "from AuSp120.tb_AcceptDescriptV a	"
						+ "left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
						+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
						+ "left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码	"
						+ "left outer join #cm cm on cm.任务序号=pc.任务序号 and cm.任务编码=pc.任务编码 and cm.病例序号=pc.序号	"
						+ "left outer join AuSp120.tb_Station s on  s.分站编码=t.分站编码	"
						+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and pc.随车护士 is not null and pc.随车护士<>'' "
						+ "and e.受理时刻 between :startTime and :endTime	group by s.分站名称,pc.随车护士	"
						+ "select distinct s.分站名称 station,pc.随车护士 name,pc.任务序号,pc.任务编码,pc.里程,cr.收费金额,t.接收命令时刻,	"
						+ "e.受理时刻,t.到达医院时刻,t.到达现场时刻,t.出车时刻 into #temp2	"
						+ "from AuSp120.tb_AcceptDescriptV a	"
						+ "left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
						+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
						+ "left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码	"
						+ "left outer join AuSp120.tb_Station s on  s.分站编码=t.分站编码	"
						+ "left outer join AuSp120.tb_ChargeRecord cr on t.任务编码=cr.任务编码 and cr.车辆标识=t.车辆标识	"
						+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and pc.随车护士 is not null and pc.随车护士<>'' "
						+ "and e.受理时刻 between :startTime and :endTime	"
						+ "select distinct s.分站名称 station,pc.随车护士 name,pc.里程,t.接收命令时刻,	e.受理时刻,t.到达医院时刻,t.到达现场时刻,"
						+ "t.出车时刻 into #temp4	from AuSp120.tb_AcceptDescriptV a	"
						+ "left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
						+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
						+ "left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码	"
						+ "left outer join AuSp120.tb_Station s on  s.分站编码=t.分站编码	"
						+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and pc.随车护士 is not null "
						+ "and pc.随车护士<>'' and e.受理时刻 between :startTime and :endTime	"
						+ "select t2.station,t2.name,SUM(t2.收费金额) costToal into #temp5	from #temp2 t2	"
						+ "group by t2.station,t2.name	select t4.station,t4.name,SUM(t4.里程) distanceTotal,COUNT(*) outCalls,"
						+ "	AVG(DATEDIFF(Second,t4.受理时刻,t4.到达现场时刻)) averageResponseTime,	AVG(DATEDIFF(Second,t4.接收命令时刻,t4.出车时刻)) averageSendTime,	"
						+ "sum(datediff(Second,t4.出车时刻,t4.到达医院时刻)) outCallTimeTotal into #temp3	from #temp4 t4	group by t4.station,"
						+ "t4.name	select t1.station,t1.name,t1.afterDeaths,t6.cureNumbers,t1.emptyCars,t1.safeOut,t1.takeBacks,t1.noAmbulance,"
						+ "t1.others,t3.outCalls,	t1.refuseHospitals,t1.spotDeaths,isnull(t3.averageResponseTime,0) averageResponseTime,"
						+ "isnull(t3.averageSendTime,0) averageSendTime,	isnull(t5.costToal,0) costToal,	t3.distanceTotal,"
						+ "isnull(t3.outCallTimeTotal,0) outCallTimeTotal	from #temp1 t1 left outer join #temp3 t3 on t1.name=t3.name and t1.station=t3.station	"
						+ "left outer join #temp5 t5 on t1.name=t5.name and t1.station=t5.station "
						+ "left outer join #temp6 t6 on t1.name=t6.name and t1.station=t6.station order by t1.station	"
						+ "drop table #temp1,#temp2,#temp3,#temp4,#temp5,#cm,#temp6 ";
			} else if ("3".equals(doctorOrNurseOrDriver)) {
				sql = "select distinct 任务编码,病例序号,任务序号  into #cm from AuSp120.tb_CureMeasure	"
						+ "select s.分站名称 station,pc.司机 name,SUM(case when cm.ID is not null then 1 else 0 end) cureNumbers  into #temp6	"
						+ "from AuSp120.tb_AcceptDescriptV a	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
						+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
						+ "left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码	"
						+ "left outer join AuSp120.tb_CureMeasure cm on cm.任务序号=pc.任务序号 and cm.任务编码=pc.任务编码 and cm.病例序号=pc.序号	"
						+ "left outer join AuSp120.tb_Station s on  s.分站编码=t.分站编码	"
						+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and pc.司机 is not null and pc.司机<>'' and e.受理时刻 between :startTime and :endTime	group by s.分站名称,pc.司机 "
						+ "select s.分站名称 station,pc.司机 name, SUM(case when pc.转归编码=1 then 1 else 0 end) takeBacks,	"
						+ "SUM(case when pc.转归编码=10 then 1 else 0 end) emptyCars,"
						+ "SUM(case when pc.转归编码=7 then 1 else 0 end) refuseHospitals,	"
						+ "SUM(case when pc.转归编码=5 then 1 else 0 end) spotDeaths,"
						+ "SUM(case when pc.转归编码=6 then 1 else 0 end) afterDeaths,	"
						+ "SUM(case when pc.转归编码=8 then 1 else 0 end) others,"
						+ "SUM(case when pc.转归编码=12 then 1 else 0 end) safeOut,	"
						+ "SUM(case when pc.转归编码=11 then 1 else 0 end) noAmbulance into #temp1	"
						+ "from AuSp120.tb_AcceptDescriptV a	"
						+ "left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
						+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
						+ "left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码	"
						+ "left outer join #cm cm on cm.任务序号=pc.任务序号 and cm.任务编码=pc.任务编码 and cm.病例序号=pc.序号	"
						+ "left outer join AuSp120.tb_Station s on  s.分站编码=t.分站编码	"
						+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and pc.司机 is not null and pc.司机<>'' "
						+ "and e.受理时刻 between :startTime and :endTime	group by s.分站名称,pc.司机	"
						+ "select distinct s.分站名称 station,pc.司机 name,pc.任务序号,pc.任务编码,pc.里程,cr.收费金额,t.接收命令时刻,	"
						+ "e.受理时刻,t.到达医院时刻,t.到达现场时刻,t.出车时刻 into #temp2	"
						+ "from AuSp120.tb_AcceptDescriptV a	"
						+ "left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
						+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
						+ "left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码	"
						+ "left outer join AuSp120.tb_Station s on  s.分站编码=t.分站编码	"
						+ "left outer join AuSp120.tb_ChargeRecord cr on t.任务编码=cr.任务编码 and cr.车辆标识=t.车辆标识	"
						+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and pc.司机 is not null and pc.司机<>'' "
						+ "and e.受理时刻 between :startTime and :endTime	"
						+ "select distinct s.分站名称 station,pc.司机 name,pc.里程,t.接收命令时刻,	e.受理时刻,t.到达医院时刻,t.到达现场时刻,"
						+ "t.出车时刻 into #temp4	from AuSp120.tb_AcceptDescriptV a	"
						+ "left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
						+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
						+ "left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码	"
						+ "left outer join AuSp120.tb_Station s on  s.分站编码=t.分站编码	"
						+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and pc.司机 is not null "
						+ "and pc.司机<>'' and e.受理时刻 between :startTime and :endTime	"
						+ "select t2.station,t2.name,SUM(t2.收费金额) costToal into #temp5	from #temp2 t2	"
						+ "group by t2.station,t2.name	select t4.station,t4.name,SUM(t4.里程) distanceTotal,COUNT(*) outCalls,"
						+ "	AVG(DATEDIFF(Second,t4.受理时刻,t4.到达现场时刻)) averageResponseTime,	AVG(DATEDIFF(Second,t4.接收命令时刻,t4.出车时刻)) averageSendTime,	"
						+ "sum(datediff(Second,t4.出车时刻,t4.到达医院时刻)) outCallTimeTotal into #temp3	from #temp4 t4	group by t4.station,"
						+ "t4.name	select t1.station,t1.name,t1.afterDeaths,t6.cureNumbers,t1.emptyCars,t1.safeOut,t1.takeBacks,t1.noAmbulance,"
						+ "t1.others,t3.outCalls,	t1.refuseHospitals,t1.spotDeaths,isnull(t3.averageResponseTime,0) averageResponseTime,"
						+ "isnull(t3.averageSendTime,0) averageSendTime,	isnull(t5.costToal,0) costToal,	t3.distanceTotal,"
						+ "isnull(t3.outCallTimeTotal,0) outCallTimeTotal	from #temp1 t1 left outer join #temp3 t3 on t1.name=t3.name and t1.station=t3.station	"
						+ "left outer join #temp5 t5 on t1.name=t5.name and t1.station=t5.station  "
						+ "left outer join #temp6 t6 on t1.name=t6.name and t1.station=t6.station order by t1.station "
						+ "drop table #temp1,#temp2,#temp3,#temp4,#temp5,#cm,#temp6 ";
			}

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("startTime", parameter.getStartTime());
			paramMap.put("endTime", parameter.getEndTime());
			logger.info(sql);
			List<DocterNurseDriver> results = this.npJdbcTemplate.query(sql,
					paramMap, new RowMapper<DocterNurseDriver>() {
						@Override
						public DocterNurseDriver mapRow(ResultSet rs, int index)
								throws SQLException {
							DocterNurseDriver docterNurseDriver = new DocterNurseDriver();
							docterNurseDriver.setStation(rs
									.getString("station"));
							docterNurseDriver.setName(rs.getString("name"));
							docterNurseDriver.setOutCalls(rs
									.getString("outCalls"));
							docterNurseDriver.setTakeBacks(rs
									.getString("takeBacks"));
							docterNurseDriver.setEmptyCars(rs
									.getString("emptyCars"));
							docterNurseDriver.setOthers(rs.getString("others"));
							docterNurseDriver.setRefuseHospitals(rs
									.getString("refuseHospitals"));
							docterNurseDriver.setSpotDeaths(rs
									.getString("spotDeaths"));
							docterNurseDriver.setAfterDeaths(rs
									.getString("afterDeaths"));
							docterNurseDriver.setDistanceTotal(rs
									.getString("distanceTotal"));
							docterNurseDriver.setCostToal(rs
									.getString("costToal"));
							docterNurseDriver.setAverageResponseTime(rs
									.getString("averageResponseTime"));
							docterNurseDriver.setOutCallTimeTotal(rs
									.getString("outCallTimeTotal"));
							docterNurseDriver.setCureNumbers(rs
									.getString("cureNumbers"));
							docterNurseDriver.setSafeOut(rs
									.getString("safeOut"));
							docterNurseDriver.setNoAmbulance(rs
									.getString("noAmbulance"));
							docterNurseDriver.setAverageSendTime(rs
									.getString("averageSendTime"));
							return docterNurseDriver;
						}
					});
			logger.info("一共有" + results.size() + "条数据");
			for (DocterNurseDriver result : results) {
				result.setAverageResponseTime(CommonUtil.formatSecond(result
						.getAverageResponseTime()));
				result.setOutCallTimeTotal(CommonUtil.formatSecond(result
						.getOutCallTimeTotal()));
				result.setAverageSendTime(CommonUtil.formatSecond(result
						.getAverageSendTime()));
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
}
