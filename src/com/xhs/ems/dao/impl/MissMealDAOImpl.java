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

import com.xhs.ems.bean.MissMeal;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.MissMealDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午2:04:36
 */
@Repository
public class MissMealDAOImpl implements MissMealDAO {
	private static final Logger logger = Logger
			.getLogger(MissMealDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select pc.随车医生 name,COUNT(*) times,SUM(case when (DATEPART(Hour,t.出车时刻)<12 and DATEPART(Hour,t.到达医院时刻)>12) or (DATEPART(Hour,t.出车时刻)<18 and DATEPART(Hour,t.到达医院时刻)>18) then 1 else 0 end)   rate	"
				+ "from AuSp120.tb_TaskV t	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码	left outer join AuSp120.tb_PatientCase pc on t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号	"
				+ "where e.事件性质编码=1 and e.受理时刻 between :startTime and :endTime	and pc.任务编码 is not null and pc.随车医生<>''	group by pc.随车医生 "
				+ " union  select pc.随车护士 name,COUNT(*) times,	SUM(case when (DATEPART(Hour,t.出车时刻)<12 and DATEPART(Hour,t.到达医院时刻)>12) or (DATEPART(Hour,t.出车时刻)<18 and DATEPART(Hour,t.到达医院时刻)>18) then 1 else 0 end)   rate	"
				+ "from AuSp120.tb_TaskV t	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码	left outer join AuSp120.tb_PatientCase pc on t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号	"
				+ "where e.事件性质编码=1 and e.受理时刻 between :startTime and :endTime	and pc.任务编码 is not null and pc.随车护士<>''	group by pc.随车护士 "
				+ " union  select pc.司机 name,COUNT(*) times,	SUM(case when (DATEPART(Hour,t.出车时刻)<12 and DATEPART(Hour,t.到达医院时刻)>12) or (DATEPART(Hour,t.出车时刻)<18 and DATEPART(Hour,t.到达医院时刻)>18) then 1 else 0 end)   rate	"
				+ "from AuSp120.tb_TaskV t	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码	left outer join AuSp120.tb_PatientCase pc on t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号	"
				+ "where e.事件性质编码=1 and e.受理时刻 between :startTime and :endTime	and pc.任务编码 is not null and pc.司机<>''	group by pc.司机 ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<MissMeal> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<MissMeal>() {
					@Override
					public MissMeal mapRow(ResultSet rs, int index)
							throws SQLException {

						return new MissMeal(rs.getString("name"), rs
								.getString("times"), rs.getString("rate"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		for (MissMeal result : results) {
			result.setRate(CommonUtil.calculateRate(
					Integer.parseInt(result.getTimes()), result.getRate()));
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
