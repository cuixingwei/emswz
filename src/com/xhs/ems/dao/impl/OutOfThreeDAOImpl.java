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

import com.xhs.ems.bean.OutOfThree;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.OutOfThreeDAO;

/**
 * @datetime 2015年11月14日 下午3:27:03
 * @author 崔兴伟
 */
@Repository
public class OutOfThreeDAOImpl implements OutOfThreeDAO {
	private static final Logger logger = Logger
			.getLogger(OutOfThreeDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public List<OutOfThree> getData(Parameter parameter) {
		String sql = "select det.NameM outType,SUM(case when DATEDIFF(SECOND,t.接收命令时刻,t.出车时刻)<=180 then 1 else 0 end) normalNumbers,	"
				+ "'' rate1,SUM(case when DATEDIFF(SECOND,t.接收命令时刻,t.出车时刻)>180 then 1 else 0 end) lateNumbers,"
				+ "'' rate2,	COUNT(*) total from AuSp120.tb_TaskV t	left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 "
				+ "and t.受理序号=a.受理序号	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码	"
				+ "left outer join AuSp120.tb_DEventType det on det.Code=e.事件类型编码	where e.事件性质编码=1 "
				+ "and a.类型编码 not in (2,4)	and t.生成任务时刻 between :startTime and :endTime	group by det.NameM ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<OutOfThree> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<OutOfThree>() {
					@Override
					public OutOfThree mapRow(ResultSet rs, int index)
							throws SQLException {
						OutOfThree outOfThree = new OutOfThree();
						outOfThree.setLateNumbers(rs.getString("lateNumbers"));
						outOfThree.setNormalNumbers(rs
								.getString("normalNumbers"));
						outOfThree.setOutType(rs.getString("outType"));
						outOfThree.setRate1(rs.getString("rate1"));
						outOfThree.setRate2(rs.getString("rate2"));
						outOfThree.setTotal(rs.getString("total"));
						return outOfThree;
					}
				});
		logger.info("一共有" + results.size() + "条数据");

		// 计算比率
		for (OutOfThree result : results) {
			result.setRate1(CommonUtil.calculateRate(
					Integer.parseInt(result.getTotal()),
					result.getNormalNumbers()));
			result.setRate2(CommonUtil.calculateRate(
					Integer.parseInt(result.getTotal()),
					result.getLateNumbers()));
		}
		return results;
	}

}
