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

import com.xhs.ems.bean.TreatEffect;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.TreatEffectDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午5:34:15
 */
@Repository
public class TreatEffectDAOImpl implements TreatEffectDAO {
	private static final Logger logger = Logger
			.getLogger(TreatEffectDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select dr.NameM name, COUNT(*) times,'' rate	from AuSp120.tb_PatientCase pc	"
				+ "left outer join AuSp120.tb_DResult dr on dr.Code=pc.救治结果编码	"
				+ "where pc.记录时刻 between :startTime and :endTime  	group by dr.NameM  ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<TreatEffect> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<TreatEffect>() {
					@Override
					public TreatEffect mapRow(ResultSet rs, int index)
							throws SQLException {

						return new TreatEffect(rs.getString("name"), rs
								.getString("times"), rs.getString("rate"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		int total = 0;
		for (TreatEffect result : results) {
			total += Integer.parseInt(result.getTimes());
		}
		for (TreatEffect result : results) {
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
