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

import com.xhs.ems.bean.AcceptTime;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.AcceptTimeDAO;

@Repository("acceptTimeDAO")
public class AcceptTimeDAOImpl implements AcceptTimeDAO {

	private static final Logger logger = Logger
			.getLogger(AcceptTimeDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select m.姓名 dispatcher,(select avg(datediff(Second,tr.振铃时刻,tr.通话开始时刻)) from AuSp120.tb_TeleRecord tr where  m.工号=tr.调度员编码 group by m.工号) averageOffhookTime,"
				+ "avg(datediff(Second, a.开始受理时刻, a.派车时刻)) averageOffSendCar,	"
				+ "avg(datediff(Second, a.开始受理时刻, a.结束受理时刻)) averageAccept,"
				+ "(select avg(datediff(Second,sl.开始时刻,sl.结束时刻)) from AuSp120.tb_SlinoLog sl where sl.座席状态='就绪' and m.工号=sl.调度员编码 group by m.工号) readyTime,	"
				+ "(select avg(datediff(Second,sl.开始时刻,sl.结束时刻)) from AuSp120.tb_SlinoLog sl where sl.座席状态='离席' and m.工号=sl.调度员编码 group by m.工号) leaveTime	"
				+ "from  AuSp120.tb_AcceptDescriptV a	"
				+ "left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
				+ "left outer join AuSp120.tb_Event e on e.事件编码=t.事件编码	"
				+ "left outer join AuSp120.tb_MrUser m on m.工号=t.调度员编码	"
				+ "where e.事件性质编码=1 and a.开始受理时刻 between :startTime and :endTime and m.人员类型=0";
		if (!CommonUtil.isNullOrEmpty(parameter.getDispatcher())) {
			sql = sql + " and t.调度员编码=:dispatcher ";
		}
		sql = sql + " group by m.姓名,m.工号 ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("dispatcher", parameter.getDispatcher());
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<AcceptTime> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<AcceptTime>() {
					@Override
					public AcceptTime mapRow(ResultSet rs, int index)
							throws SQLException {

						return new AcceptTime(rs.getString("dispatcher"), rs
								.getString("averageOffhookTime"), rs
								.getString("averageOffSendCar"), rs
								.getString("averageAccept"), rs
								.getString("readyTime"), rs
								.getString("leaveTime"));
					}
				});
		for (AcceptTime result : results) {
			result.setReadyTime(CommonUtil.formatSecond(result.getReadyTime()));
			result.setLeaveTime(CommonUtil.formatSecond(result.getLeaveTime()));
		}
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
