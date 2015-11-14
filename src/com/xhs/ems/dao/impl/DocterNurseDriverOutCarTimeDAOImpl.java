package com.xhs.ems.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xhs.ems.bean.DocterNurseDriverOutCarTime;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.DocterNurseDriverOutCarTimeDAO;

/**
 * @datetime 2015年11月14日 下午4:01:21
 * @author 崔兴伟
 */
@Repository
public class DocterNurseDriverOutCarTimeDAOImpl implements
		DocterNurseDriverOutCarTimeDAO {
	private static final Logger logger = Logger
			.getLogger(DocterNurseDriverOutCarTimeDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public List<DocterNurseDriverOutCarTime> getData(Parameter parameter) {
		String sqlComm1 = " ,COUNT(*) helpNumbers,SUM(case when DATEDIFF(SECOND,t.接收命令时刻,t.出车时刻)<=180 then 1 else 0 end) helpNormalNumbers,	"
				+ "SUM(case when DATEDIFF(SECOND,t.接收命令时刻,t.出车时刻)>180 then 1 else 0 end) helpLateNumbers	into #temp1	"
				+ "from AuSp120.tb_PatientCase pc	left outer join AuSp120.tb_Task t on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码	"
				+ "left outer join AuSp120.tb_AcceptDescript a on a.受理序号=t.受理序号 and a.事件编码=t.事件编码	"
				+ "left outer join AuSp120.tb_EventV e on e.事件编码=a.事件编码	"
				+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and e.事件类型编码=1	"
				+ "and  受理时刻 between :startTime and :endTime ";
		String sqlComm2 = " ,COUNT(*) tranforNumbers,SUM(case when DATEDIFF(SECOND,t.接收命令时刻,t.出车时刻)<=180 then 1 else 0 end) tranforNormalNumbers,	"
				+ "SUM(case when DATEDIFF(SECOND,t.接收命令时刻,t.出车时刻)>180 then 1 else 0 end) tranforLateNumbers	into #temp2 "
				+ "from AuSp120.tb_PatientCase pc	left outer join AuSp120.tb_Task t on t.任务序号=pc.任务序号 and t.任务编码=pc.任务编码	"
				+ "left outer join AuSp120.tb_AcceptDescript a on a.受理序号=t.受理序号 and a.事件编码=t.事件编码	"
				+ "left outer join AuSp120.tb_EventV e on e.事件编码=a.事件编码	where e.事件性质编码=1 and a.类型编码 not in (2,4) "
				+ "and e.事件类型编码=2	and  受理时刻 between :startTime and :endTime ";
		String sqlComm3 = "select t1.name,t1.helpLateNumbers,t1.helpNormalNumbers,t1.helpNumbers,isnull(t2.tranforLateNumbers,0) tranforLateNumbers,"
				+ "isnull(t2.tranforNormalNumbers,0) tranforNormalNumbers,isnull(t2.tranforNumbers,0) tranforNumbers	from #temp1 t1 "
				+ "left outer join #temp2 t2 on t1.name=t2.name drop table #temp1,#temp2 ";
		String sql = " ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		logger.info(parameter.getDoctorOrNurseOrDriver());
		if (StringUtils.equals("1", parameter.getDoctorOrNurseOrDriver())) {
			sql = "select pc.随车医生 name " + sqlComm1
					+ " and pc.随车医生<>'' group by pc.随车医生 select pc.随车医生 name "
					+ sqlComm2 + " and pc.随车医生<>''	group by pc.随车医生 "
					+ sqlComm3;
		} else if (StringUtils
				.equals("2", parameter.getDoctorOrNurseOrDriver())) {
			sql = "select pc.随车护士 name " + sqlComm1
					+ " and pc.随车护士<>'' group by pc.随车护士 select pc.随车护士 name "
					+ sqlComm2 + " and pc.随车护士<>''	group by pc.随车护士 "
					+ sqlComm3;
		} else if (StringUtils
				.equals("3", parameter.getDoctorOrNurseOrDriver())) {
			sql = "select pc.司机 name " + sqlComm1
					+ " and pc.司机<>'' group by pc.司机 select pc.司机 name "
					+ sqlComm2 + " and pc.司机<>''	group by pc.司机 " + sqlComm3;
		} else {
			List<DocterNurseDriverOutCarTime> results = new ArrayList<DocterNurseDriverOutCarTime>(
					0);
			return results;
		}

		List<DocterNurseDriverOutCarTime> results = this.npJdbcTemplate.query(
				sql, paramMap, new RowMapper<DocterNurseDriverOutCarTime>() {
					@Override
					public DocterNurseDriverOutCarTime mapRow(ResultSet rs,
							int index) throws SQLException {
						DocterNurseDriverOutCarTime docterNurseDriverOutCarTime = new DocterNurseDriverOutCarTime();
						docterNurseDriverOutCarTime.setHelpLateNumbers(rs
								.getString("helpLateNumbers"));
						docterNurseDriverOutCarTime.setHelpNormalNumbers(rs
								.getString("helpNormalNumbers"));
						docterNurseDriverOutCarTime.setHelpNumbers(rs
								.getString("helpNumbers"));
						docterNurseDriverOutCarTime.setName(rs
								.getString("name"));
						docterNurseDriverOutCarTime.setTranforLateNumbers(rs
								.getString("tranforLateNumbers"));
						docterNurseDriverOutCarTime.setTranforNormalNumbers(rs
								.getString("tranforNormalNumbers"));
						docterNurseDriverOutCarTime.setTranforNumbers(rs
								.getString("tranforNumbers"));
						return docterNurseDriverOutCarTime;
					}
				});
		logger.info("一共有" + results.size() + "条数据");

		// 计算比率
		for (DocterNurseDriverOutCarTime result : results) {
			result.setRate1(CommonUtil.calculateRate(
					Integer.parseInt(result.getHelpNumbers()),
					result.getHelpNormalNumbers()));
			result.setRate2(CommonUtil.calculateRate(
					Integer.parseInt(result.getHelpNumbers()),
					result.getHelpLateNumbers()));
			result.setRate3(CommonUtil.calculateRate(
					Integer.parseInt(result.getTranforNumbers()),
					result.getTranforNormalNumbers()));
			result.setRate4(CommonUtil.calculateRate(
					Integer.parseInt(result.getTranforNumbers()),
					result.getTranforLateNumbers()));
		}
		return results;
	}

}
