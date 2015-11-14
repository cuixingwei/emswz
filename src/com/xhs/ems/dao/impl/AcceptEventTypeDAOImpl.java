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

import com.xhs.ems.bean.AcceptEventType;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.AcceptEventTypeDAO;

@Repository("AcceptEventTypeDAO")
public class AcceptEventTypeDAOImpl implements AcceptEventTypeDAO {

	private static final Logger logger = Logger
			.getLogger(AcceptEventTypeDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select m.姓名 dispatcher,sum(case when a.电话振铃时刻 is not null then 1 else 0 end) numbersOfPhone,"
				+ "sum(case when t.任务编码 is not null then 1 else 0 end) numbersOfSendCar,	"
				+ "sum(case when a.类型编码=1 then 1 else 0 end) numbersOfNormalSendCar,"
				+ "sum(case when a.类型编码=2 then 1 else 0 end) numbersOfNormalHangUp,	"
				+ "sum(case when a.类型编码=3 then 1 else 0 end) numbersOfReinforceSendCar,"
				+ "sum(case when a.类型编码=4 then 1 else 0 end) numbersOfReinforceHangUp,	"
				+ "sum(case when a.类型编码=5 then 1 else 0 end) numbersOfStopTask,sum(case when a.类型编码=6 then 1 else 0 end) specialEvent,"
				+ "sum(case when a.类型编码=7 then 1 else 0 end) noCar,sum(case when a.类型编码=8 then 1 else 0 end) transmitCenter,	"
				+ "sum(case when a.类型编码=9 then 1 else 0 end) refuseSendCar,sum(case when a.类型编码=10 then 1 else 0 end) wakeSendCar,	"
				+ "sum(case when t.结果编码=2 then 1 else 0 end) stopTask,'' ratioStopTask,	sum(case when t.结果编码=3 then 1 else 0 end) emptyCar,'' ratioEmptyCar,	"
				+ "sum(case when t.结果编码=4 then 1 else 0 end) nomalComplete,'' ratioComplete	"
				+ "from  AuSp120.tb_AcceptDescriptV a	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
				+ "left outer join AuSp120.tb_Event e on e.事件编码=t.事件编码	left outer join AuSp120.tb_MrUser m on m.工号=t.调度员编码	"
				+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and a.开始受理时刻  between :startTime and :endTime and m.人员类型=0	group by m.姓名";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<AcceptEventType> results = this.npJdbcTemplate.query(sql,
				paramMap, new RowMapper<AcceptEventType>() {
					@Override
					public AcceptEventType mapRow(ResultSet rs, int index)
							throws SQLException {

						return new AcceptEventType(rs.getString("dispatcher"),
								rs.getString("numbersOfPhone"), rs
										.getString("numbersOfSendCar"), rs
										.getString("numbersOfNormalSendCar"),
								rs.getString("numbersOfNormalHangUp"),
								rs.getString("numbersOfReinforceSendCar"), rs
										.getString("numbersOfReinforceHangUp"),
								rs.getString("numbersOfStopTask"), rs
										.getString("specialEvent"), rs
										.getString("noCar"), rs
										.getString("transmitCenter"), rs
										.getString("refuseSendCar"), rs
										.getString("wakeSendCar"), rs
										.getString("stopTask"), rs
										.getString("ratioStopTask"), rs
										.getString("emptyCar"), rs
										.getString("ratioEmptyCar"), rs
										.getString("nomalComplete"), rs
										.getString("ratioComplete"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		for (AcceptEventType result : results) {
			result.setRatioStopTask(CommonUtil.calculateRate(
					Integer.parseInt(result.getNumbersOfSendCar().toString()),
					Integer.parseInt(result.getStopTask().toString())));
			result.setRatioComplete(CommonUtil.calculateRate(
					Integer.parseInt(result.getNumbersOfSendCar().toString()),
					Integer.parseInt(result.getNomalComplete().toString())));
			result.setRatioEmptyCar(CommonUtil.calculateRate(
					Integer.parseInt(result.getNumbersOfSendCar().toString()),
					Integer.parseInt(result.getEmptyCar().toString())));
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
