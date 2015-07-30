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

import com.xhs.ems.bean.AcceptMark;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.AcceptMarkDAO;

@Repository("acceptMarkDAO")
public class AcceptMarkDAOImpl implements AcceptMarkDAO {
	private static final Logger logger = Logger
			.getLogger(AcceptMarkDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select  convert(varchar(20),a.开始受理时刻,120)  acceptTime,a.呼救电话  callPhone,m.姓名  dispatcher,"
				+ "a.现场地址  spotAddress,t.备注  taskRemark,a.备注  acceptRemark "
				+ "from  AuSp120.tb_AcceptDescriptV a 	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
				+ "left outer join AuSp120.tb_Event e on e.事件编码=t.事件编码	"
				+ "left outer join AuSp120.tb_MrUser m on m.工号=t.调度员编码	"
				+ "where (a.备注<>'' or t.备注<>'') and e.事件性质编码=1  and a.开始受理时刻 between :startTime and :endTime  ";
		if (!CommonUtil.isNullOrEmpty(parameter.getDispatcher())) {
			sql = sql + " and t.调度员编码=:dispatcher ";
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("dispatcher", parameter.getDispatcher());
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<AcceptMark> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<AcceptMark>() {
					@Override
					public AcceptMark mapRow(ResultSet rs, int index)
							throws SQLException {

						return new AcceptMark(rs.getString("acceptTime"), rs
								.getString("callPhone"), rs
								.getString("dispatcher"), rs
								.getString("spotAddress"), rs
								.getString("taskRemark"), rs
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
