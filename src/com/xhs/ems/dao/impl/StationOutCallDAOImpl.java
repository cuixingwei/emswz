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

import com.xhs.ems.bean.StationOutCall;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.StationOutCallDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午3:59:04
 */
@Repository
public class StationOutCallDAOImpl implements StationOutCallDAO {
	private static final Logger logger = Logger
			.getLogger(StationOutCallDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select distinct pc.任务序号,pc.任务编码,pc.出诊地址,pc.里程 into #pc "
				+ "from AuSp120.tb_PatientCase pc	select pc.出诊地址 station,"
				+ "SUM(case when e.事件类型编码=1 then 1 else 0 end) spotFirstAid,SUM(pc.里程) distance,	"
				+ "SUM(case when e.事件类型编码=2 then 1 else 0 end) stationTransfer,"
				+ "SUM(case when e.事件类型编码=3 then 1 else 0 end) inHospitalTransfer,	"
				+ "SUM(case when e.事件类型编码 in (12,13) then 1 else 0 end) sendOutPatient,"
				+ "SUM(case when e.事件类型编码=5 then 1 else 0 end) safeguard,	"
				+ "SUM(case when e.事件类型编码=6 then 1 else 0 end) auv,"
				+ "SUM(case when e.事件类型编码=7 then 1 else 0 end) volunteer,	"
				+ "SUM(case when e.事件类型编码=8 then 1 else 0 end) train,"
				+ "SUM(case when e.事件类型编码=9 then 1 else 0 end) practice,	"
				+ "SUM(case when e.事件类型编码=10 then 1 else 0 end) other,COUNT(*) outCallTotal into #temp1	"
				+ "from AuSp120.tb_EventV e	left outer join AuSp120.tb_AcceptDescriptV a on e.事件编码=a.事件编码	"
				+ "left outer join AuSp120.tb_TaskV t on t.事件编码=a.事件编码 and t.受理序号=a.受理序号	"
				+ "left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码		"
				+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and e.受理时刻 between :startTime and :endTime	group by pc.出诊地址	"
				+ "select pc.出诊地址 station,SUM(case when pc.转归编码 in (5,6) then 1 else 0 end) death,	"
				+ "SUM(case when pc.转归编码=1 then 1 else 0 end) tackBackTotal,SUM(case when pc.转归编码=10 then 1 else 0 end) emptyCars,	"
				+ "SUM(case when pc.转归编码=7 then 1 else 0 end) refuses into #temp2	from AuSp120.tb_PatientCase pc	"
				+ "left outer join AuSp120.tb_TaskV t on pc.任务编码=t.任务编码 and t.任务序号=pc.任务序号	"
				+ "left outer join AuSp120.tb_AcceptDescriptV a on t.事件编码=a.事件编码 and t.受理序号=a.受理序号	"
				+ "left outer join AuSp120.tb_EventV e on a.事件编码=e.事件编码	"
				+ "where e.事件性质编码=1 and a.类型编码 not in (2,4)	and e.受理时刻 between :startTime and :endTime	group by pc.出诊地址	"
				+ "select t1.station,t1.spotFirstAid,t1.stationTransfer,t1.inHospitalTransfer,t1.sendOutPatient,t1.safeguard,	"
				+ "t1.auv,t1.volunteer,t1.train,t1.practice,t1.other,t1.outCallTotal,t2.tackBackTotal,t2.emptyCars,	t2.refuses,isnull(t2.death,0) death,"
				+ "isnull(t1.distance,0) distance	from #temp1 t1 left outer join #temp2 t2 on t1.station=t2.station	"
				+ "where t1.station is not null and t1.station<>''	drop table #temp1,#temp2,#pc ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<StationOutCall> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<StationOutCall>() {
					@Override
					public StationOutCall mapRow(ResultSet rs, int index)
							throws SQLException {

						return new StationOutCall(rs.getString("station"), rs
								.getString("spotFirstAid"), rs
								.getString("stationTransfer"), rs
								.getString("inHospitalTransfer"), rs
								.getString("sendOutPatient"), rs
								.getString("safeguard"), rs.getString("auv"),
								rs.getString("volunteer"), rs
										.getString("train"), rs
										.getString("practice"), rs
										.getString("other"), rs
										.getString("outCallTotal"), rs
										.getString("tackBackTotal"), rs
										.getString("distance"), rs
										.getString("emptyCars"), rs
										.getString("refuses"), rs
										.getString("death"));
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
