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

import com.xhs.ems.bean.PatientAgeSpan;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.PatientAgeSpanDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午2:50:43
 */
@Repository
public class PatientAgeSpanDAOImpl implements PatientAgeSpanDAO {

	private static final Logger logger = Logger
			.getLogger(PatientAgeSpanDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select pc.年龄 span,COUNT(*) times,'' rate 	from AuSp120.tb_PatientCase pc	"
				+ "where pc.记录时刻 between :startTime and :endTime	and ISNUMERIC(pc.年龄)=0 group by pc.年龄 "
				+ " union select case when CAST(pc.年龄 as float) between 0 and 10 then '0~10岁'	when CAST(pc.年龄 as float) between 10 and 20 then '10~20岁'"
				+ "	when CAST(pc.年龄 as float) between 20 and 30 then '20~30岁'	when CAST(pc.年龄 as float) between 30 and 40 then '30~40岁'	"
				+ "when CAST(pc.年龄 as float) between 40 and 50 then '40~50岁'	when CAST(pc.年龄 as float) between 50 and 60 then '50~60岁'	"
				+ "when CAST(pc.年龄 as float) between 60 and 70 then '60~70岁'	when CAST(pc.年龄 as float) between 70 and 80 then '70~80岁'	"
				+ "when CAST(pc.年龄 as float) between 80 and 90 then '80~90岁'	else '90~' end span,COUNT(*) times,'' rate	"
				+ "from AuSp120.tb_PatientCase pc	where  ISNUMERIC(pc.年龄)=1 and CAST(pc.年龄 as float)>10	"
				+ "and pc.记录时刻 between :startTime and :endTime 	group by case when CAST(pc.年龄 as float) between 0 and 10 then '0~10岁'	"
				+ "when CAST(pc.年龄 as float) between 10 and 20 then '10~20岁'	when CAST(pc.年龄 as float) between 20 and 30 then '20~30岁'	"
				+ "when CAST(pc.年龄 as float) between 30 and 40 then '30~40岁'	when CAST(pc.年龄 as float) between 40 and 50 then '40~50岁'	"
				+ "when CAST(pc.年龄 as float) between 50 and 60 then '50~60岁'	when CAST(pc.年龄 as float) between 60 and 70 then '60~70岁'	"
				+ "when CAST(pc.年龄 as float) between 70 and 80 then '70~80岁'	"
				+ "when CAST(pc.年龄 as float) between 80 and 90 then '80~90岁'	else '90~' end ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<PatientAgeSpan> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<PatientAgeSpan>() {
					@Override
					public PatientAgeSpan mapRow(ResultSet rs, int index)
							throws SQLException {

						return new PatientAgeSpan(rs.getString("span"), rs
								.getString("times"), rs.getString("rate"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		int total = 0;
		for (PatientAgeSpan result : results) {
			total += Integer.parseInt(result.getTimes());
		}
		for (PatientAgeSpan result : results) {
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
