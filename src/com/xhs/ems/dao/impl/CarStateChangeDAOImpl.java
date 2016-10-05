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

import com.xhs.ems.bean.CarStateChange;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.CarStateChangeDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日 下午7:44:12
 */
@Repository
public class CarStateChangeDAOImpl implements CarStateChangeDAO {
	private static final Logger logger = Logger
			.getLogger(CarStateChangeDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月13日 下午7:44:12
	 * @see com.xhs.ems.dao.CarStateChangeDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select  e.事件名称 eventName,am.实际标识 carCode,damS.NameM carState,convert(varchar(20),td.记录时刻,120) recordTime,	td.记录方式 recordClass,td.台号 seatCode,m.姓名 dispatcher	"
				+ "from AuSp120.tb_TaskDT td  left outer join AuSp120.tb_TaskV t on t.任务编码=td.任务编码	"
				+ "left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码	"
				+ "left outer join AuSp120.tb_MrUser m on td.调度员编码= (case td.记录方式 when '终端按键' then m.ID when '手工按键' then m.工号 end)	"
				+ "left outer join AuSp120.tb_Ambulance am on am.车辆编码=td.车辆编码	"
				+ "left outer join AuSp120.tb_DAmbulanceState damS on damS.Code=td.车辆状态编码	"
				+ "where e.事件性质编码=1 and e.事件名称 not like '%测试%' and t.生成任务时刻  between :startTime and :endTime ";
		if (!CommonUtil.isNullOrEmpty(parameter.getCarCode())) {
			sql = sql + " and td.车辆编码=:carCode ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getEventName())) {
			sql = sql + " and e.事件名称  like :eventName ";
		}
		sql += " order by e.事件名称";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("carCode", parameter.getCarCode());
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("eventName", "%" + parameter.getEventName() + "%");

		List<CarStateChange> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<CarStateChange>() {
					@Override
					public CarStateChange mapRow(ResultSet rs, int index)
							throws SQLException {

						return new CarStateChange(rs.getString("eventName"), rs
								.getString("carCode"),
								rs.getString("carState"), rs
										.getString("recordTime"), rs
										.getString("recordClass"), rs
										.getString("seatCode"), rs
										.getString("dispatcher"));
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
