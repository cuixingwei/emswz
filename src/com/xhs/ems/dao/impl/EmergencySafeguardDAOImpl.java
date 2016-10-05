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

import com.xhs.ems.bean.EmergencySafeguard;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.EmergencySafeguardDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午1:11:12
 */
@Repository
public class EmergencySafeguardDAOImpl implements EmergencySafeguardDAO {
	private static final Logger logger = Logger
			.getLogger(EmergencySafeguardDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select distinct pc.任务编码,pc.任务序号,pc.里程,pc.随车医生,pc.随车护士,pc.司机 into #pc "
				+ "from AuSp120.tb_PatientCase pc	"
				+ "select CONVERT(varchar(20),e.受理时刻,120) date,det.NameM nature,a.初步判断 event,a.现场地址 address,"
				+ "isnull(pc.里程,0) distance,pc.随车医生 doctor,pc.随车护士 nurse,pc.司机 driver,"
				+ "DATEDIFF(SECOND,t.出车时刻,t.到达医院时刻) safeTime	from AuSp120.tb_AcceptDescriptV a	"
				+ "left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
				+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
				+ "left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码	"
				+ "left outer join AuSp120.tb_DEventType det on det.Code=e.事件类型编码	"
				+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and e.受理时刻 between :startTime and :endTime "
				+ "and e.事件类型编码=5	 ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		sql += " drop table #pc";

		List<EmergencySafeguard> results = this.npJdbcTemplate.query(sql,
				paramMap, new RowMapper<EmergencySafeguard>() {
					@Override
					public EmergencySafeguard mapRow(ResultSet rs, int index)
							throws SQLException {
						EmergencySafeguard emergencySafeguard = new EmergencySafeguard();
						emergencySafeguard.setAddress(rs.getString("address"));
						emergencySafeguard.setDate(rs.getString("date"));
						emergencySafeguard.setDistance(rs.getString("distance"));
						emergencySafeguard.setDoctor(rs.getString("doctor"));
						emergencySafeguard.setDriver(rs.getString("driver"));
						emergencySafeguard.setEvent(rs.getString("event"));
						emergencySafeguard.setNature(rs.getString("nature"));
						emergencySafeguard.setNurse(rs.getString("nurse"));
						emergencySafeguard.setSafeTime(rs.getString("safeTime"));
						return emergencySafeguard;
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		for (EmergencySafeguard emergencySafeguard : results) {
			emergencySafeguard.setSafeTime(CommonUtil
					.formatSecond(emergencySafeguard.getSafeTime()));
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
