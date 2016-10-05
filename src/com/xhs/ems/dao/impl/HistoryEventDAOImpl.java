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

import com.xhs.ems.bean.HistoryEvent;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.HistoryEventDAO;

/**
 * @datetime 2016年7月27日 下午3:16:37
 * @author 崔兴伟
 */
@Repository
public class HistoryEventDAOImpl implements HistoryEventDAO {
	private static final Logger logger = Logger
			.getLogger(HistoryEventDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}


	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select e.事件编码,COUNT(*) 受理次数 into #accepts from AuSp120.tb_Eventv e 	"
				+ "left outer join AuSp120.tb_AcceptDescriptV a on e.事件编码=a.事件编码	where e.事件性质编码=1  group by e.事件编码 "
				+ "select e.事件编码,sum(case when t.任务编码 is not null then 1 else 0 end) 任务次数 into #tasks from AuSp120.tb_EventV e	"
				+ "left outer join AuSp120.tb_AcceptDescriptV a on e.事件编码=a.事件编码	"
				+ "left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
				+ "where e.事件性质编码=1  group by e.事件编码 "
				+ "select e.事件编码,COUNT(*) 病历个数 into #cases from AuSp120.tb_EventV e	"
				+ "left outer join AuSp120.tb_AcceptDescriptV a on e.事件编码=a.事件编码	"
				+ "left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
				+ "left outer join AuSp120.tb_Ambulance am on am.车辆编码=t.车辆编码	"
				+ "left outer join AuSp120.tb_PatientCase pc on pc.任务编码=t.任务编码 and pc.车辆标识=am.实际标识	"
				+ "where e.事件性质编码=1  group by e.事件编码		"
				+ "select e.事件名称 eventName,e.呼救电话 alarmPhone,convert(varchar(120),受理时刻,120) acceptTime,det.NameM eventType,	"
				+ "dls.NameM eventSource,m.姓名 dispatcher,a.受理次数 acceptNumbers,t.任务次数 taskNumbers,c.病历个数 caseNumbers	"
				+ "from AuSp120.tb_Eventv e left outer join AuSp120.tb_DEventType det on det.Code=e.事件类型编码	"
				+ "left outer join AuSp120.tb_MrUser m on m.工号=e.调度员编码	"
				+ "left outer join AuSp120.tb_DLinkSource dls on dls.Code=e.联动来源编码	"
				+ "left outer join #accepts a on a.事件编码=e.事件编码	left outer join #tasks t on t.事件编码=e.事件编码	"
				+ "left outer join #cases c on c.事件编码=e.事件编码	"
				+ "where e.事件性质编码=1 and e.受理时刻 between :startTime and :endTime order by e.事件编码	drop table #accepts,#cases,#tasks";
		Map<String, String> map = new HashMap<String, String>();
		map.put("startTime", parameter.getStartTime());
		map.put("endTime", parameter.getEndTime());
		final List<HistoryEvent> results = this.npJdbcTemplate.query(sql,
				map, new RowMapper<HistoryEvent>() {
					@Override
					public HistoryEvent mapRow(ResultSet rs, int index)
							throws SQLException {
						HistoryEvent historyEvent = new HistoryEvent();
						historyEvent.setAcceptNumbers(rs.getString("acceptNumbers"));
						historyEvent.setAcceptTime(rs.getString("acceptTime"));
						historyEvent.setAlarmPhone(rs.getString("alarmPhone"));
						historyEvent.setCaseNumbers(rs.getString("caseNumbers"));
						historyEvent.setEventName(rs.getString("eventName"));
						historyEvent.setEventSource(rs.getString("eventSource"));
						historyEvent.setEventType(rs.getString("eventType"));
						historyEvent.setTaskNumbers(rs.getString("taskNumbers"));
						historyEvent.setDispatcher(rs.getString("dispatcher"));
						return historyEvent;
					}
				});
		logger.info("一共" + results.size() + "条记录");
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
