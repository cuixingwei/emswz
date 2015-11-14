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

import com.xhs.ems.bean.CallNoAccept;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.CallNoAcceptDAO;

@Repository
public class CallNoAcceptDAOImpl implements CallNoAcceptDAO {

	private static final Logger logger = Logger
			.getLogger(CallNoAcceptDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select hr.NameM reason,COUNT(*) times,'' rate 	from AuSp120.tb_EventV e  "
				+ "left outer join AuSp120.tb_AcceptDescriptV a on e.事件编码=a.事件编码	"
				+ "left outer join AuSp120.tb_TaskV t on t.受理序号=a.受理序号 and t.事件编码=a.事件编码	"
				+ "left outer join AuSp120.tb_DHangReason hr on hr.Code=a.挂起原因编码	"
				+ "where e.事件性质编码=1 and a.类型编码 in (2,4) and e.受理时刻 between :startTime and :endTime	"
				+ "group by hr.NameM ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<CallNoAccept> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<CallNoAccept>() {
					@Override
					public CallNoAccept mapRow(ResultSet rs, int index)
							throws SQLException {

						return new CallNoAccept(rs.getString("reason"), rs
								.getString("times"), rs.getString("rate"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		int total = 0; // 总挂起数
		for (CallNoAccept result : results) {
			total += Integer.parseInt(result.getTimes());
		}
		for (CallNoAccept result : results) {
			result.setRate(CommonUtil.calculateRate(total, result.getTimes()));
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
