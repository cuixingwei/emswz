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

import com.xhs.ems.bean.StationTransfor;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.StationTransforDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午4:42:28
 */
@Repository
public class StationTransforDAOImpl implements StationTransforDAO {

	private static final Logger logger = Logger
			.getLogger(StationTransforDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select distinct pc.任务编码,pc.任务序号,pc.里程 into #pc "
				+ "from AuSp120.tb_PatientCase pc	select da.NameM area ,a.现场地址 station,COUNT(*) outCalls,	"
				+ "isnull(SUM(pc.里程),0) distance,isnull(sum(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)),0) time into #temp1	"
				+ "from AuSp120.tb_EventV e left outer join AuSp120.tb_TaskV t on t.事件编码=e.事件编码	"
				+ "left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
				+ "left outer join #pc pc on t.任务序号=pc.任务序号 and pc.任务编码=t.任务编码	"
				+ "left outer join AuSp120.tb_DArea da on da.Code=a.区域编码	"
				+ "where e.事件性质编码=1  and a.类型编码 not in (2,4)  and e.事件类型编码=2 "
				+ "and e.受理时刻 between :startTime and :endTime	group by da.NameM,a.现场地址	"
				+ "select da.NameM area ,a.现场地址 station,SUM(case when pc.转归编码=1 then 1 else 0 end) takeBacks "
				+ "into #temp2	from AuSp120.tb_EventV e left outer join AuSp120.tb_TaskV t on t.事件编码=e.事件编码	"
				+ "left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
				+ "left outer join AuSp120.tb_PatientCase pc on t.任务序号=pc.任务序号 and pc.任务编码=t.任务编码	"
				+ "left outer join AuSp120.tb_DArea da on da.Code=a.区域编码	"
				+ "where e.事件性质编码=1  and a.类型编码 not in (2,4)  and e.事件类型编码=2 "
				+ "and e.受理时刻 between :startTime and :endTime	group by da.NameM,a.现场地址	"
				+ "select t1.area,t1.station,t1.distance,t1.outCalls,t2.takeBacks,t1.time	"
				+ "from #temp1 t1 left outer join #temp2 t2 on t1.area=t2.area and t1.station=t2.station order by t1.area	"
				+ "drop table #pc,#temp1,#temp2 ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<StationTransfor> results = this.npJdbcTemplate.query(sql,
				paramMap, new RowMapper<StationTransfor>() {
					@Override
					public StationTransfor mapRow(ResultSet rs, int index)
							throws SQLException {
						StationTransfor stationTransfor = new StationTransfor();
						stationTransfor.setArea(rs.getString("area"));
						stationTransfor.setDistance(rs.getString("distance"));
						stationTransfor.setOutCalls(rs.getString("outCalls"));
						stationTransfor.setStation(rs.getString("station"));
						stationTransfor.setTakeBacks(rs.getString("takeBacks"));
						stationTransfor.setTime(rs.getString("time"));
						return stationTransfor;
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		for (StationTransfor result : results) {
			result.setTime(CommonUtil.formatSecond(result.getTime()));
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
