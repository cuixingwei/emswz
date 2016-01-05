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

import com.xhs.ems.bean.DriverDoctorNurseDetail;
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
			.getLogger(DriverOutCallDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select distinct pc.司机 ,pc.任务编码,pc.任务序号, 里程 into #pc from AuSp120.tb_PatientCase pc	"
				+ "select pc.司机 name,COUNT(*) outCalls,	AVG(DATEDIFF(Second,e.受理时刻,t.到达现场时刻)) averageResponseTime,	"
				+ "SUM(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)) outCallTimeTotal,"
				+ "avg(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)) averageTime,SUM(里程) distance  into #temp1	"
				+ "from AuSp120.tb_EventV e left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=e.事件编码	"
				+ "left outer join	AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
				+ "left outer join #pc pc on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码	"
				+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and pc.司机 is not null and pc.司机<>'' "
				+ "and e.受理时刻 between :startTime and :endTime	group by pc.司机	"
				+ "select pc.司机 name,SUM(case when pc.转归编码=1 then 1 else 0 end) takeBacks into #temp2 "
				+ "from AuSp120.tb_PatientCase pc left outer join AuSp120.tb_TaskV t on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码	"
				+ "left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
				+ "left outer join AuSp120.tb_EventV e on a.事件编码=e.事件编码	"
				+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and pc.司机 is not null and pc.司机<>'' "
				+ "and e.受理时刻 between :startTime and :endTime	group by pc.司机	"
				+ "select t1.name,isnull(t1.averageResponseTime,0) averageResponseTime,isnull(t1.averageTime,0) averageTime,"
				+ "isnull(t1.outCallTimeTotal,0) outCallTimeTotal,	t1.outCalls,t2.takeBacks,isnull(t1.distance,0) distance	"
				+ "from #temp1 t1 left outer join #temp2 t2 on t1.name=t2.name	drop table #pc,#temp1,#temp2 ";
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

	@Override
	public Grid getDriverDetail(Parameter parameter) {
		String sql = "select convert(varchar(20),e.受理时刻,120) dateTime,pc.姓名 patientName,pc.现场地点 address,pc.出诊地址 outStation,tr.NameM outResult,pc.司机 driver,pc.里程 distance	"
				+ "from AuSp120.tb_PatientCase pc left outer join AuSp120.tb_TaskV t on t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号	"
				+ "left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码	left outer join AuSp120.tb_DTaskResult tr on tr.Code=t.结果编码	"
				+ "where e.事件性质编码=1 and e.受理时刻 between :startTime and :endTime";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql += " and pc.出诊地址 like :station ";
			paramMap.put("station", "%" + parameter.getStation() + "%");
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getDriver())) {
			sql += " and pc.司机 like :driver ";
			paramMap.put("driver", "%" + parameter.getDriver() + "%");
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getTaskResult())) {
			sql += " and t.结果编码=:taskResult ";
			paramMap.put("taskResult", parameter.getTaskResult());
		}
		List<DriverDoctorNurseDetail> results = this.npJdbcTemplate.query(sql,
				paramMap, new RowMapper<DriverDoctorNurseDetail>() {
					@Override
					public DriverDoctorNurseDetail mapRow(ResultSet rs,
							int index) throws SQLException {
						DriverDoctorNurseDetail detail = new DriverDoctorNurseDetail();
						detail.setAddress(rs.getString("address"));
						detail.setDateTime(rs.getString("dateTime"));
						detail.setDistance(rs.getString("distance"));
						detail.setDriver(rs.getString("driver"));
						detail.setOutResult(rs.getString("outResult"));
						detail.setOutStation(rs.getString("outStation"));
						detail.setPatientName(rs.getString("patientName"));
						return detail;
					}
				});
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

	@Override
	public Grid getDoctorNurseDetail(Parameter parameter) {
		String sql = "select convert(varchar(20),e.受理时刻,120) dateTime,pc.姓名 patientName,pc.现场地点 address,pc.出诊地址 outStation,tr.NameM outResult,pc.随车医生 doctor,pc.随车护士 nurse,pc.里程 distance	"
				+ "from AuSp120.tb_PatientCase pc left outer join AuSp120.tb_TaskV t on t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号	"
				+ "left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码	left outer join AuSp120.tb_DTaskResult tr on tr.Code=t.结果编码	"
				+ "where e.事件性质编码=1 and e.受理时刻 between :startTime and :endTime";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql += " and pc.出诊地址 like :station ";
			paramMap.put("station", "%" + parameter.getStation() + "%");
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getDoctor())) {
			sql += " and pc.随车医生 like :doctor ";
			paramMap.put("doctor", "%" + parameter.getDoctor() + "%");
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getNurse())) {
			sql += " and pc.随车护士 like :nurse ";
			paramMap.put("nurse", "%" + parameter.getNurse() + "%");
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getTaskResult())) {
			sql += " and t.结果编码=:taskResult ";
			paramMap.put("taskResult", parameter.getTaskResult());
		}
		List<DriverDoctorNurseDetail> results = this.npJdbcTemplate.query(sql,
				paramMap, new RowMapper<DriverDoctorNurseDetail>() {
					@Override
					public DriverDoctorNurseDetail mapRow(ResultSet rs,
							int index) throws SQLException {
						DriverDoctorNurseDetail detail = new DriverDoctorNurseDetail();
						detail.setAddress(rs.getString("address"));
						detail.setDateTime(rs.getString("dateTime"));
						detail.setDistance(rs.getString("distance"));
						detail.setNurse(rs.getString("nurse"));
						detail.setDoctor(rs.getString("doctor"));
						detail.setOutResult(rs.getString("outResult"));
						detail.setOutStation(rs.getString("outStation"));
						detail.setPatientName(rs.getString("patientName"));
						return detail;
					}
				});
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

	@Override
	public Grid getCenterHospitalOutDetail(Parameter parameter) {
		String sql = "select convert(varchar(20),e.受理时刻,120) dateTime,pc.姓名 patientName,pc.现场地点 address,pc.出诊地址 outStation,	"
				+ "dr.NameM outResult,pc.随车医生 doctor,pc.随车护士 nurse,pc.里程 distance,pc.司机 driver,pc.性别 sex,"
				+ "pc.年龄 age,	pc.医生诊断 diagnose,pc.送往地点 sendAddress,da.NameM area,dc.NameM diseaseDepartment,"
				+ "dcs.NameM classState,dis.NameM diseaseDegree,de.NameM treatmentEffet,et.NameM eventType,t.车辆编码 carCode,"
				+ "DATEDIFF(Second,t.接收命令时刻,t.到达现场时刻) poorTime,	DATEDIFF(Second,t.出车时刻,t.完成时刻) userTime,"
				+ "AuSp120.GetCureMeasure(pc.任务编码,pc.序号) cureMeasure,u.姓名 dispatcher	"
				+ "from AuSp120.tb_PatientCase pc	left outer join AuSp120.tb_TaskV t on t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号	"
				+ "left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码	"
				+ "left outer join AuSp120.tb_DResult dr on dr.Code=t.结果编码	 "
				+ "left outer join AuSp120.tb_AcceptDescriptV  a on a.受理序号=t.受理序号 and a.事件编码=t.事件编码	"
				+ "left outer join AuSp120.tb_DArea da on da.Code=a.区域编码	left outer join AuSp120.tb_DDiseaseClass dc on dc.Code=pc.疾病科别编码	"
				+ "left outer join AuSp120.tb_DDiseaseClassState dcs on dcs.Code=pc.分类统计编码 "
				+ "left outer join AuSp120.tb_DILLState dis on dis.Code=pc.病情编码 "
				+ "left outer join AuSp120.tb_MrUser u on u.工号=t.调度员编码	"
				+ "left outer join AuSp120.tb_DEffect de on de.Code=pc.救治效果编码  "
				+ "left outer join AuSp120.tb_DEventType et on et.Code=e.事件类型编码	"
				+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and pc.出诊地址 in ('三峡中心医院急救分院','三峡中心医院百安分院','三峡中心医院江南分院')  and e.受理时刻 between  :startTime and :endTime";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql += " and pc.出诊地址 like :station ";
			paramMap.put("station", "%" + parameter.getStation() + "%");
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getDoctor())) {
			sql += " and pc.随车医生 like :doctor ";
			paramMap.put("doctor", "%" + parameter.getDoctor() + "%");
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getNurse())) {
			sql += " and pc.随车护士 like :nurse ";
			paramMap.put("nurse", "%" + parameter.getNurse() + "%");
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getTaskResult())) {
			sql += " and t.结果编码=:taskResult ";
			paramMap.put("taskResult", parameter.getTaskResult());
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getDriver())) {
			sql += " and pc.司机 like :driver ";
			paramMap.put("driver", "%" + parameter.getDriver() + "%");
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getArea())) {
			sql += " and a.区域编码=:area ";
			paramMap.put("area", parameter.getArea());
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getSendAddress())) {
			sql += " and pc.送往地点 like :sendAddress ";
			paramMap.put("sendAddress", "%" + parameter.getSendAddress() + "%");
		}

		List<DriverDoctorNurseDetail> results = this.npJdbcTemplate.query(sql,
				paramMap, new RowMapper<DriverDoctorNurseDetail>() {
					@Override
					public DriverDoctorNurseDetail mapRow(ResultSet rs,
							int index) throws SQLException {
						DriverDoctorNurseDetail detail = new DriverDoctorNurseDetail();
						detail.setAddress(rs.getString("address"));
						detail.setDateTime(rs.getString("dateTime"));
						detail.setDistance(rs.getString("distance"));
						detail.setNurse(rs.getString("nurse"));
						detail.setDoctor(rs.getString("doctor"));
						detail.setOutResult(rs.getString("outResult"));
						detail.setOutStation(rs.getString("outStation"));
						detail.setPatientName(rs.getString("patientName"));
						detail.setAge(rs.getString("age"));
						detail.setArea(rs.getString("area"));
						detail.setClassState(rs.getString("classState"));
						detail.setDiagnose(rs.getString("diagnose"));
						detail.setDiseaseDegree(rs.getString("diseaseDegree"));
						detail.setDiseaseDepartment(rs
								.getString("diseaseDepartment"));
						detail.setDriver(rs.getString("driver"));
						detail.setSendAddress(rs.getString("sendAddress"));
						detail.setSex(rs.getString("sex"));
						detail.setTreatmentEffet(rs.getString("treatmentEffet"));
						detail.setEventType(rs.getString("eventType"));
						detail.setCarCode(rs.getString("carCode"));
						detail.setCureMeasure(rs.getString("cureMeasure"));
						detail.setPoorTime(rs.getString("poorTime"));
						detail.setUserTime(rs.getString("userTime"));
						detail.setDispatcher(rs.getString("dispatcher"));
						return detail;
					}
				});
		for (DriverDoctorNurseDetail detail : results) {
			detail.setPoorTime(CommonUtil.formatSecond(detail.getPoorTime()));
			detail.setUserTime(CommonUtil.formatSecond(detail.getUserTime()));
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
