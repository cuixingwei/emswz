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

import com.xhs.ems.bean.RingToAccept;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.RingToAcceptDAO;

@Repository("ringToAcceptDAO")
public class RingToAcceptDAOImpl implements RingToAcceptDAO {
	private static final Logger logger = Logger
			.getLogger(RingToAcceptDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select m.姓名 dispatcher,convert(varchar(20),tr.振铃时刻,120) ringTime,convert(varchar(20),tr.通话开始时刻,120) callTime,datediff(Second,tr.振铃时刻,tr.通话开始时刻) ringDuration,tr.座席号 acceptCode,tr.备注  acceptRemark	"
				+ "from AuSp120.tb_TeleRecord tr left outer join AuSp120.tb_MrUser m on tr.调度员编码=m.工号 	"
				+ "where m.人员类型=0 and tr.振铃时刻 between :startTime and :endTime  and datediff(Second,tr.振铃时刻,tr.通话开始时刻)> :overtimes ";
		if (!CommonUtil.isNullOrEmpty(parameter.getDispatcher())) {
			sql = sql + " and tr.调度员编码=:dispatcher ";
		}
		sql = sql + "order by tr.调度员编码";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("overtimes", parameter.getOvertimes());
		paramMap.put("dispatcher", parameter.getDispatcher());
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<RingToAccept> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<RingToAccept>() {
					@Override
					public RingToAccept mapRow(ResultSet rs, int index)
							throws SQLException {

						return new RingToAccept(rs.getString("dispatcher"), rs
								.getString("ringTime"), rs
								.getString("callTime"), rs
								.getString("ringDuration"), rs
								.getString("acceptCode"), rs
								.getString("acceptRemark"));
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
