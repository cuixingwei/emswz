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

import com.xhs.ems.bean.NonEmergencyVehicles;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.NonEmergencyVehiclesDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午2:11:18
 */
@Repository
public class NonEmergencyVehiclesDAOImpl implements NonEmergencyVehiclesDAO {
	private static final Logger logger = Logger
			.getLogger(NonEmergencyVehiclesDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select distinct pc.任务编码,pc.任务序号,pc.里程 into #pc from AuSp120.tb_PatientCase pc "
				+ "select 'ids' ids, COUNT(*) hospital_times,sum(pc.里程) hospital_distance into #temp1	"
				+ "from AuSp120.tb_AcceptDescriptV a 	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
				+ "left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码 	"
				+ "where e.事件性质编码=1 and a.类型编码 not in(2,4) and e.受理时刻 between :startTime and :endTime and e.事件类型编码=3  "
				+ "select 'ids' ids, COUNT(*) safeguard_times,sum(pc.里程) safeguard_distance into #temp2	"
				+ "from AuSp120.tb_AcceptDescriptV a 	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
				+ "left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码 	"
				+ "where e.事件性质编码=1 and a.类型编码 not in(2,4) and e.受理时刻 between :startTime and :endTime and e.事件类型编码=5 "
				+ "select 'ids' ids,COUNT(*) clinic_times,sum(pc.里程) clinic_distance into #temp3	 "
				+ "from AuSp120.tb_AcceptDescriptV a 	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
				+ "left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码 	"
				+ "where e.事件性质编码=1 and a.类型编码 not in(2,4) and e.受理时刻 between :startTime and :endTime and e.事件类型编码=7 "
				+ "select 'ids' ids,COUNT(*) practice_times,sum(pc.里程) practice_distance into #temp4	 "
				+ "from AuSp120.tb_AcceptDescriptV a 	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
				+ "left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码 	"
				+ "where e.事件性质编码=1 and a.类型编码 not in(2,4) and e.受理时刻 between :startTime and :endTime and e.事件类型编码 in (8,9) "
				+ "select 'ids' ids,COUNT(*) inHospital_times,sum(pc.里程) inHospital_distance into #temp5  "
				+ "from AuSp120.tb_AcceptDescriptV a 	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
				+ "left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码 	"
				+ "where e.事件性质编码=1 and a.类型编码 not in(2,4) and e.受理时刻 between :startTime and :endTime and e.事件类型编码=3 and t.分站编码='001' "
				+ "select 'ids' ids,COUNT(*) xh_times,sum(pc.里程) xh_distance into #temp6 	 "
				+ "from AuSp120.tb_AcceptDescriptV a 	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
				+ "left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码 	"
				+ "where e.事件性质编码=1 and a.类型编码 not in(2,4) and e.受理时刻 between :startTime and :endTime and e.事件类型编码=6 "
				+ "select 'ids' ids,COUNT(*) other_times,sum(pc.里程) other_distance into #temp7 	"
				+ "from AuSp120.tb_AcceptDescriptV a 	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
				+ "left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码 	"
				+ "where e.事件性质编码=1 and a.类型编码 not in(2,4) and e.受理时刻 between :startTime and :endTime and e.事件类型编码=10 "
				+ "select ISNULL(t1.hospital_times,0) hospital_times,ISNULL(t1.hospital_distance,0) hospital_distance,ISNULL(t2.safeguard_distance,0) safeguard_distance,"
				+ "ISNULL(t2.safeguard_times,0) safeguard_times,	ISNULL(t3.clinic_distance,0) clinic_distance,ISNULL(t3.clinic_times,0) clinic_times,"
				+ "ISNULL(t4.practice_distance,0) practice_distance,ISNULL(t4.practice_times,0) practice_times,	ISNULL(t5.inHospital_distance,0) inHospital_distance,"
				+ "ISNULL(t5.inHospital_times,0) inHospital_times,ISNULL(t6.xh_distance,0) xh_distance,ISNULL(t6.xh_times,0) xh_times,	"
				+ "ISNULL(t7.other_distance,0) other_distance,ISNULL(t7.other_times,0) other_times	"
				+ "from #temp1 t1 left outer join #temp2 t2 on t1.ids=t2.ids	left outer join #temp3 t3 on t2.ids=t3.ids	"
				+ "left outer join #temp4 t4 on t3.ids=t4.ids	left outer join #temp5 t5 on t4.ids=t5.ids	left outer join #temp6 t6 on t5.ids=t6.ids	"
				+ "left outer join #temp7 t7 on t6.ids=t7.ids "
				+ "drop table #pc,#temp1,#temp2,#temp3,#temp4,#temp5,#temp6,#temp7 ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<NonEmergencyVehicles> results = this.npJdbcTemplate.query(sql,
				paramMap, new RowMapper<NonEmergencyVehicles>() {
					@Override
					public NonEmergencyVehicles mapRow(ResultSet rs, int index)
							throws SQLException {

						return new NonEmergencyVehicles(rs
								.getString("hospital_times"), rs
								.getString("hospital_distance"), rs
								.getString("safeguard_times"), rs
								.getString("safeguard_distance"), rs
								.getString("clinic_times"), rs
								.getString("clinic_distance"), rs
								.getString("practice_times"), rs
								.getString("practice_distance"), rs
								.getString("inHospital_times"), rs
								.getString("inHospital_distance"), rs
								.getString("xh_times"), rs
								.getString("xh_distance"), rs
								.getString("other_times"), rs
								.getString("other_distance"));
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
