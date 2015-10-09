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

import com.xhs.ems.bean.SendSpotType;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.SendSpotTypeDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午3:51:13
 */
@Repository
public class SendSpotTypeDAOImpl implements SendSpotTypeDAO {
	private static final Logger logger = Logger
			.getLogger(SendSpotTypeDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月15日 下午3:51:13
	 * @see com.xhs.ems.dao.SendSpotTypeDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select dtpt.NameM name,COUNT(pc.送往地点类型编码) times,'' rate 	"
				+ "from AuSp120.tb_PatientCase pc left outer join AuSp120.tb_TaskV t on t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号"
				+ "	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码 	"
				+ "left outer join AuSp120.tb_DTakenPlaceType dtpt on dtpt.Code=pc.送往地点类型编码	"
				+ "where e.事件性质编码=1 and e.受理时刻  between :startTime and :endTime "
				+ "and pc.送往地点类型编码 is not null	group by dtpt.NameM";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<SendSpotType> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<SendSpotType>() {
					@Override
					public SendSpotType mapRow(ResultSet rs, int index)
							throws SQLException {
						return new SendSpotType(rs.getString("name"), rs
								.getString("times"), rs.getString("rate"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");

		int totaltimes = 0;
		// 计算总终止次数
		for (SendSpotType result : results) {
			totaltimes += Integer.parseInt(result.getTimes());
		}
		// 计算比率
		for (SendSpotType result : results) {
			result.setRate(CommonUtil.calculateRate(totaltimes,
					Integer.parseInt(result.getTimes())));
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
