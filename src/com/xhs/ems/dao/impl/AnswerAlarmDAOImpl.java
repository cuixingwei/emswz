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

import com.xhs.ems.bean.AnswerAlarm;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.AnswerAlarmDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午8:36:42
 */
@Repository
public class AnswerAlarmDAOImpl implements AnswerAlarmDAO {
	private static final Logger logger = Logger
			.getLogger(AnswerAlarmDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月15日 下午8:37:26
	 * @see com.xhs.ems.dao.AnswerAlarmDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select t.事件编码,t.受理序号,dr.NameM outResult,pc.出诊地址,"
				+ "SUM(case when pc.转归编码=1 then 1 else 0 end) takeBacks into #temp1	"
				+ "from AuSp120.tb_PatientCase pc left outer join AuSp120.tb_Task t on pc.任务序号=t.任务序号 and pc.任务编码=t.任务编码	"
				+ "left outer join AuSp120.tb_DResult dr on pc.救治结果编码=dr.Code	group by t.事件编码,dr.NameM,"
				+ "pc.出诊地址,t.受理序号 select convert(varchar(20),e.受理时刻,120) answerAlarmTime,m.姓名 dispatcher,"
				+ "a.呼救电话 alarmPhone,a.联系电话 relatedPhone,	a.现场地址 siteAddress,	a.初步判断 judgementOnPhone,"
				+ "t1.出诊地址 station,t1.outResult,t1.takeBacks,convert(varchar(20),a.派车时刻,120) sendCarTime,a.分诊调度医院 triageStation	"
				+ "from AuSp120.tb_EventV e left outer join AuSp120.tb_AcceptDescriptV a on e.事件编码=a.事件编码	"
				+ "left outer join #temp1 t1 on t1.事件编码=a.事件编码 and t1.受理序号=a.受理序号	"
				+ "left outer join AuSp120.tb_MrUser m on m.工号=a.调度员编码	"
				+ "where e.事件性质编码=1 and e.受理时刻 between :startTime and :endTime  ";
		if (!CommonUtil.isNullOrEmpty(parameter.getDispatcher())) {
			sql = sql + "and a.调度员编码= :dispatcher ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getAlarmPhone())) {
			sql += " and  a.呼救电话  like :alarmPhone";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getSiteAddress())) {
			sql += " and a.现场地址 like :siteAddress";
		}
		sql += " drop table #temp1";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("dispatcher", parameter.getDispatcher());
		paramMap.put("alarmPhone", "%" + parameter.getAlarmPhone() + "%");
		paramMap.put("siteAddress", "%" + parameter.getSiteAddress() + "%");

		List<AnswerAlarm> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<AnswerAlarm>() {
					@Override
					public AnswerAlarm mapRow(ResultSet rs, int index)
							throws SQLException {
						AnswerAlarm alarm = new AnswerAlarm();
						alarm.setAlarmPhone(rs.getString("alarmPhone"));
						alarm.setAnswerAlarmTime(rs
								.getString("answerAlarmTime"));
						alarm.setDispatcher(rs.getString("dispatcher"));
						alarm.setJudgementOnPhone(rs
								.getString("judgementOnPhone"));
						alarm.setOutResult(rs.getString("outResult"));
						alarm.setRelatedPhone(rs.getString("relatedPhone"));
						alarm.setSendCarTime(rs.getString("sendCarTime"));
						alarm.setSiteAddress(rs.getString("siteAddress"));
						alarm.setStation(rs.getString("station"));
						alarm.setTakeBacks(rs.getString("takeBacks"));
						alarm.setTriageStation(rs.getString("triageStation"));
						return alarm;
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
