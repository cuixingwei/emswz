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
		String sql = "select 分站编码 station,实际标识 carCode,count( p.车辆编码) as pauseNumbers into #temp1 	"
				+ "from AuSp120.tb_RecordPauseReason p left join AuSp120.tb_Ambulance a on p.车辆编码=a.车辆编码 	"
				+ "where p.操作时刻 between :startTime and :endTime group by (分站编码),(实际标识) "
				+ "select t.分站编码 station,am.实际标识 carCode,t.出车时刻 ,t.到达现场时刻,t.生成任务时刻,结果编码 into #temp2	"
				+ "from AuSp120.tb_TaskV t left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码	"
				+ "left outer join AuSp120.tb_Ambulance am on am.车辆编码=t.车辆编码 	"
				+ "where e.事件性质编码=1 and t.生成任务时刻 between :startTime and :endTime ";
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql = sql + " and t.分站编码=:station ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getCarCode())) {
			sql = sql + " and t.车辆编码=:carCode ";
		}
		sql += "select station,carCode,AVG(DATEDIFF(Second,生成任务时刻,出车时刻)) averageOutCarTimes into #temp3 	from #temp2 "
				+ "where 生成任务时刻<出车时刻 group by station,carCode	"
				+ "select station,carCode,AVG(DATEDIFF(Second,出车时刻,到达现场时刻)) averageArriveSpotTimes into #temp4	"
				+ "from #temp2 where 出车时刻<到达现场时刻 and 出车时刻 is not null group by station,carCode "
				+ "select t.station,t.carCode,sum(case when t.出车时刻 is not null then 1 else 0 end) outCarNumbers,"
				+ "sum(case when t.到达现场时刻 is not null then 1 else 0 end) arriveSpotNumbers into #temp5	"
				+ "from #temp2 t group by t.station,t.carCode "
				+ "select s.分站名称 station,t5.carCode,outCarNumbers,averageOutCarTimes,arriveSpotNumbers,"
				+ "averageArriveSpotTimes,pauseNumbers 	from AuSp120.tb_Station s "
				+ "left outer join #temp1 t1  on t1.station=s.分站编码	"
				+ "left outer join #temp5 t5 on t1.station=t5.station and t1.carCode=t5.carCode	"
				+ "left outer join #temp3 t3  on t3.station=t5.station and t3.carCode=t5.carCode	"
				+ "left outer join #temp4 t4  on t3.station=t4.station and t3.carCode=t4.carCode	"
				+ "where t5.carCode is not null	order by s.显示顺序 "
				+ " drop table #temp1,#temp2,#temp3,#temp4,#temp5";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("station", parameter.getStation());
		paramMap.put("carCode", parameter.getCarCode());

		List<CarWork> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<CarWork>() {
					@Override
					public CarWork mapRow(ResultSet rs, int index)
							throws SQLException {
						return new CarWork(rs.getString("station"), rs
								.getString("carCode"), rs
								.getString("outCarNumbers"), rs
								.getString("averageOutCarTimes"), rs
								.getString("arriveSpotNumbers"), rs
								.getString("averageArriveSpotTimes"), rs
								.getString("pauseNumbers"));
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
