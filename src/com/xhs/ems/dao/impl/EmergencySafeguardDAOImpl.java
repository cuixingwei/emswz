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
		String sql = "select distinct pc.任务编码,pc.任务序号,pc.里程,pc.随车医生,pc.随车护士,pc.司机 into #pc from AuSp120.tb_PatientCase pc "
				+ "select CONVERT(varchar(20),e.受理时刻,120) date,det.NameM nature,e.事件名称 event,a.现场地址 address,	isnull(pc.里程,0) distance,pc.随车医生 doctor,"
				+ "pc.随车护士 nurse,pc.司机 driver	from AuSp120.tb_TaskV t left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码	"
				+ "left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码 	left outer join AuSp120.tb_DEventType det on det.Code=e.事件类型编码	"
				+ "left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and a.受理序号=t.受理序号 	"
				+ "where e.事件性质编码=1 and e.受理时刻 between :startTime and :endTime and e.事件类型编码 in (1,5) drop table #pc ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<EmergencySafeguard> results = this.npJdbcTemplate.query(sql,
				paramMap, new RowMapper<EmergencySafeguard>() {
					@Override
					public EmergencySafeguard mapRow(ResultSet rs, int index)
							throws SQLException {

						return new EmergencySafeguard(rs.getString("date"), rs
								.getString("nature"), rs.getString("event"), rs
								.getString("address"),
								rs.getString("distance"), rs
										.getString("doctor"), rs
										.getString("nurse"), rs
										.getString("driver"));
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
