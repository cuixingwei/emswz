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

import com.xhs.ems.bean.TriageRefuseReason;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.TriageRefuseReasonDAO;

/**
 * @datetime 2015年11月14日 下午2:07:36
 * @author 崔兴伟
 */
@Repository
public class TriageRefuseReasonDAOImpl implements TriageRefuseReasonDAO {
	private static final Logger logger = Logger
			.getLogger(TriageRefuseReasonDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public List<TriageRefuseReason> getData(Parameter parameter) {
		String sql = "select dr.NameM reason,COUNT(*) times,'' rate from AuSp120.tb_EventV e 	"
				+ "left outer join AuSp120.tb_AcceptDescriptV a on e.事件编码=a.事件编码	"
				+ "left outer join AuSp120.tb_DTriageRefuse dr on dr.Code=a.拒绝分诊调度原因编码	"
				+ "where e.事件性质编码=1 and a.类型编码 in (11,12,13) and a.拒绝分诊调度原因编码 is not null	"
				+ "and  受理时刻 between :startTime and :endTime	group by dr.NameM ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<TriageRefuseReason> results = this.npJdbcTemplate.query(sql,
				paramMap, new RowMapper<TriageRefuseReason>() {
					@Override
					public TriageRefuseReason mapRow(ResultSet rs, int index)
							throws SQLException {
						TriageRefuseReason triageRefuseReason = new TriageRefuseReason();
						triageRefuseReason.setRate(rs.getString("rate"));
						triageRefuseReason.setReason(rs.getString("reason"));
						triageRefuseReason.setTimes(rs.getString("times"));
						return triageRefuseReason;
					}
				});
		logger.info("一共有" + results.size() + "条数据");

		int totaltimes = 0;
		// 计算总终止次数
		for (TriageRefuseReason result : results) {
			totaltimes += Integer.parseInt(result.getTimes());
		}
		// 计算比率
		for (TriageRefuseReason result : results) {
			result.setRate(CommonUtil.calculateRate(totaltimes,
					Integer.parseInt(result.getTimes())));
		}
		return results;
	}

}
