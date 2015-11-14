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

import com.xhs.ems.bean.OutResult;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.OutResultDAO;

/**
 * @datetime 2015年11月14日 下午1:35:08
 * @author 崔兴伟
 */
@Repository
public class OutResultDAOImpl implements OutResultDAO {
	private static final Logger logger = Logger
			.getLogger(OutResultDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public List<OutResult> getData(Parameter parameter) {
		String sql = "select do.NameM resultName,COUNT(*) times,'' rate 	from AuSp120.tb_PatientCase pc	"
				+ "left outer join AuSp120.tb_TaskV t on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码	"
				+ "left outer join AuSp120.tb_AcceptDescript a on a.受理序号=t.受理序号 and a.事件编码=t.事件编码	"
				+ "left outer join AuSp120.tb_EventV e on e.事件编码=a.事件编码	"
				+ "left outer join AuSp120.tb_DOutCome do on do.Code=pc.转归编码	"
				+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and  受理时刻 between :startTime and :endTime	group by do.NameM ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<OutResult> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<OutResult>() {
					@Override
					public OutResult mapRow(ResultSet rs, int index)
							throws SQLException {
						OutResult outResult = new OutResult();
						outResult.setRate(rs.getString("rate"));
						outResult.setResultName(rs.getString("resultName"));
						outResult.setTimes(rs.getString("times"));
						return outResult;
					}
				});
		logger.info("一共有" + results.size() + "条数据");

		int totaltimes = 0;
		// 计算总终止次数
		for (OutResult result : results) {
			totaltimes += Integer.parseInt(result.getTimes());
		}
		// 计算比率
		for (OutResult result : results) {
			result.setRate(CommonUtil.calculateRate(totaltimes,
					Integer.parseInt(result.getTimes())));
		}
		return results;
	}

}
