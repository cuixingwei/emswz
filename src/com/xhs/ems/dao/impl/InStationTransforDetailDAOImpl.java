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
import com.xhs.ems.common.CommonUtil;
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
				+ "pc.医生诊断 diagnose,pc.出诊地址 outCallAddress,pc.科室 sendClass,a.现场地址 spot,pc.里程 distance,	DATEDIFF(Second,t.出车时刻,t.到达医院时刻) time	from AuSp120.tb_EventV e "
				+ "left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=e.事件编码 left outer join AuSp120.tb_TaskV t on t.事件编码=a.事件编码 and t.受理序号=a.受理序号	"
				+ "left outer join AuSp120.tb_PatientCase pc  on t.任务序号=pc.任务序号 and pc.任务编码=t.任务编码 	"
				+ "where e.事件性质编码=1 and pc.任务编码 is not null and a.类型编码 not in (2,4)  and e.事件类型编码=3 and e.受理时刻 between :startTime and :endTime ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<InStationTransforDetail> results = this.npJdbcTemplate.query(sql,
				paramMap, new RowMapper<InStationTransforDetail>() {
					@Override
					public InStationTransforDetail mapRow(ResultSet rs,
							int index) throws SQLException {
						InStationTransforDetail stationOutCallDetail = new InStationTransforDetail();
						stationOutCallDetail.setAge(rs.getString("age"));
						stationOutCallDetail.setDate(rs.getString("date"));
						stationOutCallDetail.setDiagnose(rs
								.getString("diagnose"));
						stationOutCallDetail.setDistance(rs
								.getString("distance"));
						stationOutCallDetail.setGender(rs.getString("gender"));
						stationOutCallDetail.setOutCallAddress(rs
								.getString("outCallAddress"));
						stationOutCallDetail.setPatientName(rs
								.getString("patientName"));
						stationOutCallDetail.setSendClass(rs
								.getString("sendClass"));
						stationOutCallDetail.setSpot(rs.getString("spot"));
						stationOutCallDetail.setTime(rs.getString("time"));
						return stationOutCallDetail;

					}
				});
		logger.info("一共有" + results.size() + "条数据");
		for (InStationTransforDetail stationTransforDetail : results) {
			stationTransforDetail.setTime(CommonUtil
					.formatSecond(stationTransforDetail.getTime()));
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
