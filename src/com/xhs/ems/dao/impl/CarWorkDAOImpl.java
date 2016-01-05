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

import com.xhs.ems.bean.CarWork;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.CarWorkDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月17日 下午12:23:26
 */
@Repository
public class CarWorkDAOImpl implements CarWorkDAO {

	private static final Logger logger = Logger.getLogger(CarWorkDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月17日 下午12:23:26
	 * @see com.xhs.ems.dao.CarWorkDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select 实际标识 carCode,count( p.车辆编码) as pauseNumbers into #temp1 		"
				+ "from AuSp120.tb_RecordPauseReason p left join AuSp120.tb_Ambulance a on p.车辆编码=a.车辆编码	"
				+ "where p.操作时刻 between :startTime and :endTime group by (实际标识) "
				+ "select am.实际标识 carCode,t.出车时刻 ,t.到达现场时刻,t.生成任务时刻,结果编码 into #temp2	"
				+ "from AuSp120.tb_AcceptDescriptV a	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 "
				+ "and a.受理序号=t.受理序号	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
				+ "left outer join AuSp120.tb_Ambulance am on am.车辆编码=t.车辆编码	"
				+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and t.生成任务时刻 between :startTime and :endTime ";
		if (!CommonUtil.isNullOrEmpty(parameter.getCarCode())) {
			sql = sql + " and t.车辆编码=:carCode ";
		}
		sql += "select carCode,AVG(DATEDIFF(Second,生成任务时刻,出车时刻)) averageOutCarTimes into #temp3 	"
				+ "from #temp2 where 生成任务时刻<出车时刻 group by carCode  "
				+ "select carCode,AVG(DATEDIFF(Second,出车时刻,到达现场时刻)) averageArriveSpotTimes into #temp4	"
				+ "from #temp2 where 出车时刻<到达现场时刻 and 出车时刻 is not null group by carCode	"
				+ "select t.carCode,sum(case when t.出车时刻 is not null then 1 else 0 end) outCarNumbers,	"
				+ "sum(case when t.到达现场时刻 is not null then 1 else 0 end) arriveSpotNumbers into #temp5	"
				+ "from #temp2 t group by t.carCode	select distinct pc.任务编码,pc.任务序号,pc.里程,pc.车辆标识 into #pc "
				+ "from AuSp120.tb_PatientCase pc 	left outer join AuSp120.tb_TaskV t on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码 	"
				+ "where t.生成任务时刻 between :startTime and :endTime "
				+ "select pc.车辆标识 carCode,sum(pc.里程) outDistance into #dis1 from #pc pc group by pc.车辆标识	"
				+ "select gv.实际标识 carCode,(MAX(gv.里程)-MIN(里程)) distance into #dis2 from AuSp120.tb_GPSInfoV gv "
				+ "where gv.时间  between :startTime and :endTime and gv.里程 <>0  group by gv.实际标识 "
				+ "select t5.carCode,outCarNumbers,averageOutCarTimes,arriveSpotNumbers,averageArriveSpotTimes,"
				+ "isnull(pauseNumbers,0) pauseNumbers,isnull(d1.outDistance,0) outDistance,cast(isnull(d2.distance,0) as decimal(18,2)) distance	"
				+ "from  #temp5  t5	left outer join #temp1 t1 on  t1.carCode=t5.carCode	"
				+ "left outer join #temp3 t3 on t3.carCode=t5.carCode	"
				+ "left outer join #temp4 t4 on t5.carCode=t4.carCode left outer join #dis1 d1 on d1.carCode=t5.carCode 	"
				+ "left outer join #dis2 d2 on d2.carCode=t5.carCode	where t5.carCode is not null	"
				+ "drop table #temp1,#temp2,#temp3,#temp4,#temp5,#dis1,#dis2,#pc";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("carCode", parameter.getCarCode());

		logger.info(sql);

		List<CarWork> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<CarWork>() {
					@Override
					public CarWork mapRow(ResultSet rs, int index)
							throws SQLException {
						CarWork carWork = new CarWork();
						carWork.setArriveSpotNumbers(rs
								.getString("arriveSpotNumbers"));
						carWork.setAverageArriveSpotTimes(rs
								.getString("averageArriveSpotTimes"));
						carWork.setAverageOutCarTimes(rs
								.getString("averageOutCarTimes"));
						carWork.setCarCode(rs.getString("carCode"));
						carWork.setDistance(rs.getString("distance"));
						carWork.setOutCarNumbers(rs.getString("outCarNumbers"));
						carWork.setOutDistance(rs.getString("outDistance"));
						carWork.setPauseNumbers(rs.getString("pauseNumbers"));
						return carWork;
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		for (CarWork result : results) {
			result.setAverageArriveSpotTimes(CommonUtil.formatSecond(result
					.getAverageArriveSpotTimes()));
			result.setAverageOutCarTimes(CommonUtil.formatSecond(result
					.getAverageOutCarTimes()));
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
