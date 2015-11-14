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

import com.xhs.ems.bean.StationTransforDetail;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.StationTransforDetailDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午4:52:33
 */
@Repository
public class StationTransforDetailDAOImpl implements StationTransforDetailDAO {
	private static final Logger logger = Logger
			.getLogger(StationTransforDetailDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select CONVERT(varchar(20),e.受理时刻,120) date,pc.姓名 patientName,pc.年龄 age,pc.性别 gender,"
				+ "pc.医生诊断 diagnose,pc.出诊地址 outCallAddress,pc.科室 sendClass,a.现场地址 spotAddress,pc.里程 distance,dr.NameM outResult	"
				+ "from AuSp120.tb_EventV e left outer join AuSp120.tb_TaskV t on t.事件编码=e.事件编码 "
				+ "left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
				+ "left outer join AuSp120.tb_PatientCase pc  on t.任务序号=pc.任务序号 and pc.任务编码=t.任务编码  "
				+ "left outer join AuSp120.tb_DResult dr on dr.Code=pc.救治结果编码 "
				+ "where e.事件性质编码=1 and a.类型编码 not in (2,4)  and pc.任务编码 is not null and e.事件类型编码=2 and e.受理时刻 between :startTime and :endTime ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<StationTransforDetail> results = this.npJdbcTemplate.query(sql,
				paramMap, new RowMapper<StationTransforDetail>() {
					@Override
					public StationTransforDetail mapRow(ResultSet rs, int index)
							throws SQLException {
						StationTransforDetail stationTransforDetail = new StationTransforDetail();
						stationTransforDetail.setAge(rs.getString("age"));
						stationTransforDetail.setDate(rs.getString("date"));
						stationTransforDetail.setDiagnose(rs
								.getString("diagnose"));
						stationTransforDetail.setDistance(rs
								.getString("distance"));
						stationTransforDetail.setGender(rs.getString("gender"));
						stationTransforDetail.setOutCallAddress(rs
								.getString("outCallAddress"));
						stationTransforDetail.setOutResult(rs
								.getString("outResult"));
						stationTransforDetail.setPatientName(rs
								.getString("patientName"));
						stationTransforDetail.setSendClass(rs
								.getString("sendClass"));
						stationTransforDetail.setSpotAddress(rs
								.getString("spotAddress"));
						return stationTransforDetail;

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
