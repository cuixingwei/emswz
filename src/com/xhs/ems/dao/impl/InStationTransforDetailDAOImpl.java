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

import com.xhs.ems.bean.InStationTransforDetail;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.InStationTransforDetailDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午1:49:22
 */
@Repository
public class InStationTransforDetailDAOImpl implements
		InStationTransforDetailDAO {

	private static final Logger logger = Logger
			.getLogger(InStationTransforDetailDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select CONVERT(varchar(20),e.受理时刻,120) date,pc.姓名 patientName,pc.年龄 age,pc.性别 gender,"
				+ "pc.医生诊断 diagnose,pc.出诊地址 outCallAddress,pc.科室 sendClass	from AuSp120.tb_EventV e "
				+ "left outer join AuSp120.tb_TaskV t on t.事件编码=e.事件编码	left outer join AuSp120.tb_Station s on t.分站编码=s.分站编码	"
				+ "left outer join AuSp120.tb_PatientCase pc  on t.任务序号=pc.任务序号 and pc.任务编码=t.任务编码 	"
				+ "where e.事件性质编码=1 and t.分站编码 is not null and e.事件类型编码=3 and e.受理时刻 between :startTime and :endTime ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<InStationTransforDetail> results = this.npJdbcTemplate.query(sql,
				paramMap, new RowMapper<InStationTransforDetail>() {
					@Override
					public InStationTransforDetail mapRow(ResultSet rs,
							int index) throws SQLException {

						return new InStationTransforDetail(
								rs.getString("date"), rs
										.getString("patientName"), rs
										.getString("age"), rs
										.getString("gender"), rs
										.getString("diagnose"), rs
										.getString("outCallAddress"), rs
										.getString("sendClass"));
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
