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

import com.xhs.ems.bean.SubstationLateVisit;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.SubstationLateVisitDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月16日 下午8:17:04
 */
@Repository
public class SubstationLateVisitDAOImpl implements SubstationLateVisitDAO {
	private static final Logger logger = Logger
			.getLogger(SubstationLateVisitDAOImpl.class);

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void SetDataSourceTag(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月16日 下午8:17:04
	 * @see com.xhs.ems.dao.SubstationLateVisitDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("station", parameter.getStation());
		String sql = "select distinct pc.随车医生,pc.随车护士,pc.司机,pc.任务序号,pc.任务编码 into #pc from AuSp120.tb_PatientCase pc	"
				+ "select a.现场地址 siteAddress,det.NameM eventType,am.实际标识 carCode,CONVERT(varchar(20),a.开始受理时刻,120) acceptTime,	"
				+ "CONVERT(varchar(20),t.接收命令时刻,120) acceptTaskTime,	CONVERT(varchar(20),t.出车时刻,120) outCarTime,	"
				+ "DATEDIFF(SECOND,t.接收命令时刻,t.出车时刻) outCarTimes,	dtr.NameM taskResult,t.备注 remark,m.姓名 dispatcher,"
				+ "pc.司机 driver,	pc.随车医生 docter,pc.随车护士 nurse,s.分站名称 station		"
				+ "from AuSp120.tb_TaskV t	left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and t.受理序号=a.受理序号 	"
				+ "left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码	"
				+ "left outer join #pc pc on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码	"
				+ "left outer join AuSp120.tb_DEventType det on det.Code=e.事件类型编码	"
				+ "left outer join AuSp120.tb_MrUser m on m.工号=t.调度员编码	"
				+ "left outer join AuSp120.tb_DTaskResult dtr on dtr.Code=t.结果编码	"
				+ "left outer join AuSp120.tb_Station s on t.分站编码 =s.分站编码	"
				+ "left outer join AuSp120.tb_Ambulance am on am.车辆编码=t.车辆编码	"
				+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) 	"
				+ "and t.生成任务时刻 between :startTime and :endTime 	and DATEDIFF(SECOND,t.接收命令时刻,t.出车时刻)>180 ";
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql += " and t.分站编码=:station ";
		}

		List<SubstationLateVisit> results = this.namedParameterJdbcTemplate
				.query(sql, paramMap, new RowMapper<SubstationLateVisit>() {
					@Override
					public SubstationLateVisit mapRow(ResultSet rs, int index)
							throws SQLException {
						SubstationLateVisit substationLateVisit = new SubstationLateVisit();
						substationLateVisit.setAcceptTaskTime(rs
								.getString("acceptTaskTime"));
						substationLateVisit.setAcceptTime(rs
								.getString("acceptTime"));
						substationLateVisit.setCarCode(rs.getString("carCode"));
						substationLateVisit.setDispatcher(rs
								.getString("dispatcher"));
						substationLateVisit.setDocter(rs.getString("docter"));
						substationLateVisit.setDriver(rs.getString("driver"));
						substationLateVisit.setEventType(rs
								.getString("eventType"));
						substationLateVisit.setNurse(rs.getString("nurse"));
						substationLateVisit.setOutCarTime(rs
								.getString("outCarTime"));
						substationLateVisit.setOutCarTimes(rs
								.getString("outCarTimes"));
						substationLateVisit.setRemark(rs.getString("remark"));
						substationLateVisit.setSiteAddress(rs
								.getString("siteAddress"));
						substationLateVisit.setStation(rs.getString("station"));
						substationLateVisit.setTaskResult(rs
								.getString("taskResult"));
						return substationLateVisit;
					}
				});
		logger.info("一共有" + results.size() + "条数据");

		for (SubstationLateVisit result : results) {
			result.setOutCarTimes(CommonUtil.formatSecond(result
					.getOutCarTimes()));
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
