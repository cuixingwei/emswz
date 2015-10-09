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
		String sql = "select e.事件名称 eventName,dat.NameM acceptType,CONVERT(varchar(20),a.开始受理时刻,120) hungTime,"
				+ "dhr.NameM hungReason,	m.姓名 dispatcher,CONVERT(varchar(20),a.结束受理时刻,120) endTime,ISNULL(DATEDIFF(Second,a.开始受理时刻,a.结束受理时刻),0) hungtimes,a.分诊调度医院 station	"
				+ "from AuSp120.tb_AcceptDescriptV a	left outer join AuSp120.tb_EventV e on a.事件编码=e.事件编码	"
				+ "left outer join AuSp120.tb_DHangReason dhr on dhr.Code=a.挂起原因编码	"
				+ "left outer join AuSp120.tb_DAcceptDescriptType dat on dat.Code=a.类型编码	"
				+ "left outer join AuSp120.tb_MrUser m on m.工号=e.调度员编码	where e.事件性质编码=1 "
				+ "and a.开始受理时刻 between :startTime and :endTime and a.挂起原因编码 is not null";
		if (!CommonUtil.isNullOrEmpty(parameter.getDispatcher())) {
			sql = sql + " and e.调度员编码= :dispatcher ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getHungReason())) {
			sql = sql + " and a.挂起原因编码 = :hungReason ";
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("dispatcher", parameter.getDispatcher());
		paramMap.put("hungReason", parameter.getHungReason());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("startTime", parameter.getStartTime());

		List<HungEvent> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<HungEvent>() {
					@Override
					public HungEvent mapRow(ResultSet rs, int index)
							throws SQLException {

						return new HungEvent(rs.getString("eventName"), rs
								.getString("acceptType"), rs
								.getString("hungTime"), rs
								.getString("hungReason"), rs
								.getString("dispatcher"), rs
								.getString("endTime"), rs
								.getString("hungtimes"), rs
								.getString("station"));
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
