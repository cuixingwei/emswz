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

import com.xhs.ems.bean.TeleBook;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.TeleBookDAO;

@Repository
public class TeleBookDAOImpl implements TeleBookDAO {

	private static final Logger logger = Logger
			.getLogger(TeleBookDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select (select 机主姓名 from AuSp120.tb_TeleBook where tb.上级编码=编码) department,"
				+ "tb.机主姓名 ownerName,tb.联系电话 contactPhone,tb.固定电话 fixedPhone,tb.分机 extension,	"
				+ "tb.移动电话 mobilePhone,tb.小灵通 littleSmart,tb.备注 remark	"
				+ "from AuSp120.tb_TeleBook tb	where tb.上级编码<>0 ";
		if (!CommonUtil.isNullOrEmpty(parameter.getName())) {
			sql = sql + " and tb.机主姓名 like :name ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getPhone())) {
			sql = sql + " and tb.联系电话 like :phone ";
		}
		sql = sql + " order by tb.上级编码";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("name", "%" + parameter.getName() + "%");
		paramMap.put("phone", "%" + parameter.getPhone() + "%");

		List<TeleBook> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<TeleBook>() {
					@Override
					public TeleBook mapRow(ResultSet rs, int index)
							throws SQLException {

						return new TeleBook(rs.getString("department"), rs
								.getString("ownerName"), rs
								.getString("contactPhone"), rs
								.getString("fixedPhone"), rs
								.getString("extension"), rs
								.getString("mobilePhone"), rs
								.getString("littleSmart"), rs
								.getString("remark"));
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
