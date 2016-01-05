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

import com.xhs.ems.bean.HungEvent;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.HungEventDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午12:18:54
 */
@Repository
public class HungEventDAOImpl implements HungEventDAO {
	private static final Logger logger = Logger
			.getLogger(HungEventDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月15日 下午12:18:54
	 * @see com.xhs.ems.dao.HungEventDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql1 = "select a.事件编码,a.受理序号,e.事件名称 eventName,dat.NameM acceptType,"
				+ "CONVERT(varchar(20),a.开始受理时刻,120) hungTime,da.NameM area,et.NameM eventType,dhr.NameM hungReason,"
				+ "m.姓名 dispatcher,CONVERT(varchar(20),a.结束受理时刻,120) endTime,"
				+ "ISNULL(DATEDIFF(Second,a.开始受理时刻,a.结束受理时刻),0) hungtimes,a.分诊调度医院 station into #temp1	"
				+ "from AuSp120.tb_AcceptDescriptV a	left outer join AuSp120.tb_EventV e on a.事件编码=e.事件编码	"
				+ "left outer join AuSp120.tb_DHangReason dhr on dhr.Code=a.挂起原因编码	"
				+ "left outer join AuSp120.tb_DAcceptDescriptType dat on dat.Code=a.类型编码	"
				+ "left outer join AuSp120.tb_DArea da on da.Code=a.区域编码	"
				+ "left outer join AuSp120.tb_DEventType et on e.事件类型编码=et.Code	"
				+ "left outer join AuSp120.tb_MrUser m on m.工号=e.调度员编码	"
				+ "where e.事件性质编码=1	and a.开始受理时刻 between :startTime and :endTime  and a.挂起原因编码 is not null ";
		if (!CommonUtil.isNullOrEmpty(parameter.getDispatcher())) {
			sql1 = sql1 + " and e.调度员编码= :dispatcher ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getHungReason())) {
			sql1 = sql1 + " and a.挂起原因编码 = :hungReason ";
		}
		String sql2 = "select a.事件编码,a.受理序号,er.NameM 事件结果 into #temp2 	from AuSp120.tb_AcceptDescriptV a	"
				+ "left outer join AuSp120.tb_EventV e on a.事件编码=e.事件编码	"
				+ "left outer join AuSp120.tb_DEventResult er on er.Code=e.事件结果编码	"
				+ "where e.事件性质编码=1 and a.挂起原因编码 is null  and a.开始受理时刻 between :startTime and :endTime	"
				+ "and a.事件编码 in (select a.事件编码	from AuSp120.tb_AcceptDescriptV a	left outer join AuSp120.tb_EventV e on a.事件编码=e.事件编码 where e.事件性质编码=1 and a.挂起原因编码 is not null)	"
				+ "select distinct t1.acceptType,t1.area,t1.dispatcher,t1.endTime,t1.eventName,t1.eventType,t1.hungReason,"
				+ "t1.hungTime,	t1.hungtimes,t1.station,t2.事件结果 result	from #temp1 t1 "
				+ "left outer join #temp2 t2 on t1.事件编码=t2.事件编码 and t2.受理序号>t1.受理序号	"
				+ "drop table #temp1,#temp2";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("dispatcher", parameter.getDispatcher());
		paramMap.put("hungReason", parameter.getHungReason());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("startTime", parameter.getStartTime());

		List<HungEvent> results = this.npJdbcTemplate.query(sql1 + sql2,
				paramMap, new RowMapper<HungEvent>() {
					@Override
					public HungEvent mapRow(ResultSet rs, int index)
							throws SQLException {
						HungEvent hungEvent = new HungEvent();
						hungEvent.setAcceptType(rs.getString("acceptType"));
						hungEvent.setArea(rs.getString("area"));
						hungEvent.setDispatcher(rs.getString("dispatcher"));
						hungEvent.setEndTime(rs.getString("endTime"));
						hungEvent.setEventName(rs.getString("eventName"));
						hungEvent.setEventType(rs.getString("eventType"));
						hungEvent.setHungReason(rs.getString("hungReason"));
						hungEvent.setHungTime(rs.getString("hungTime"));
						hungEvent.setHungtimes(rs.getString("hungtimes"));
						hungEvent.setResult(rs.getString("result"));
						hungEvent.setStation(rs.getString("station"));
						return hungEvent;

					}
				});
		logger.info("一共有" + results.size() + "条数据");

		for (HungEvent result : results) {
			result.setHungtimes(CommonUtil.formatSecond(result.getHungtimes()));
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
