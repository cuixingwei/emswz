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

import com.xhs.ems.bean.DiseaseType;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.DiseaseTypeDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年7月6日 下午12:56:09
 */
@Repository
public class DiseaseTypeDAOImpl implements DiseaseTypeDAO {
	private static final Logger logger = Logger
			.getLogger(DiseaseTypeDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select dc.NameM name,isnull(COUNT(*),0) times,'' rate	"
				+ "from AuSp120.tb_PatientCase pc  left outer join AuSp120.tb_TaskV t on  t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号	"
				+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
				+ "left outer join AuSp120.tb_DDiseaseClass dc on dc.Code=pc.疾病科别编码	"
				+ "where e.事件性质编码=1 and pc.疾病科别编码 is not null and e.受理时刻 between :startTime and :endTime	group by dc.NameM ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<DiseaseType> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<DiseaseType>() {
					@Override
					public DiseaseType mapRow(ResultSet rs, int index)
							throws SQLException {

						return new DiseaseType(rs.getString("name"), rs
								.getString("times"), rs.getString("rate"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		int total = 0;
		for (DiseaseType result : results) {
			total += Integer.parseInt(result.getTimes());
		}
		for (DiseaseType result : results) {
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
