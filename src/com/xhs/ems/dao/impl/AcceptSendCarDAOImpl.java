package com.xhs.ems.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xhs.ems.bean.AcceptSendCar;
import com.xhs.ems.bean.AcceptSendCarDetail;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.AcceptSendCarDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年5月7日 下午3:09:30
 */
@Repository
public class AcceptSendCarDAOImpl implements AcceptSendCarDAO {
	private static final Logger logger = Logger
			.getLogger(AcceptSendCarDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月7日 下午3:09:31
	 * @see com.xhs.ems.dao.AcceptSendCarDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select a.ID id,m.姓名  dispatcher,CONVERT(varchar(20),a.开始受理时刻,120) startAcceptTime,"
				+ "CONVERT(varchar(20),a.派车时刻,120) sendCarTime,	 "
				+ "dat.NameM acceptType,a.呼救电话  ringPhone,DATEDIFF(SECOND,a.开始受理时刻,a.派车时刻) sendCarTimes,a.备注  remark from AuSp120.tb_Task t	"
				+ "left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and a.受理序号=t.受理序号 "
				+ "left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码	"
				+ "left outer join AuSp120.tb_MrUser m on t.调度员编码=m.工号	"
				+ "left outer join AuSp120.tb_DAcceptDescriptType dat on dat.Code=a.类型编码	"
				+ "where e.事件性质编码=1  and m.人员类型=0 and a.类型编码 not in(3,10)  and a.开始受理时刻 between :startTime and :endTime ";
		if (!CommonUtil.isNullOrEmpty(parameter.getDispatcher())) {
			sql = sql + " and t.调度员编码=:dispatcher ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getOvertimes())) {
			sql = sql + " and DATEDIFF(SECOND,a.开始受理时刻,a.派车时刻)>=:overtimes ";
		}
		sql += " order by m.姓名";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("dispatcher", parameter.getDispatcher());
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("overtimes", parameter.getOvertimes());

		List<AcceptSendCar> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<AcceptSendCar>() {
					@Override
					public AcceptSendCar mapRow(ResultSet rs, int index)
							throws SQLException {

						return new AcceptSendCar(rs.getString("id"), rs
								.getString("dispatcher"), rs
								.getString("startAcceptTime"), rs
								.getString("sendCarTime"), rs
								.getString("acceptType"), rs
								.getString("ringPhone"), rs
								.getString("sendCarTimes"), rs
								.getString("remark"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		for (AcceptSendCar result : results) {
			result.setSendCarTimes(CommonUtil.formatSecond(result
					.getSendCarTimes()));
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

	@Override
	public AcceptSendCarDetail getDetail(String id, HttpServletRequest request) {
		String sql = "select det.NameM eventType,den.NameM eventNature,e.呼救电话 callPhone,a.现场地址 callAddress,a.病人需求 patientNeed,"
				+ "a.初步判断 preJudgment,dis.NameM sickCondition,a.特殊要求 specialNeeds,a.救治人数 humanNumbers,"
				+ "a.患者姓名 sickName,	a.年龄 age,a.性别 gender,di.NameM identitys,a.联系人 contactMan,"
				+ "a.联系电话 contactPhone,a.分机 extension,m.姓名 thisDispatcher,	a.备注 remark,a.是否要担架 isOrNoLitter,a.受理序号 acceptNumber,"
				+ "convert(varchar(20),a.开始受理时刻,120) acceptStartTime,dat.NameM acceptType,	dhr.NameM toBeSentReason,"
				+ "convert(varchar(20),a.结束受理时刻,120) endAcceptTime,convert(varchar(20),a.派车时刻,120) sendCarTime,drr.NameM cancelReason,s.分站名称 sendStation,	"
				+ "am.实际标识 carIndentiy,dts.NameM states,convert(varchar(20),t.接受命令时刻,120) receiveOrderTime,dtr.NameM taskResult,"
				+ "isnull(dsr.NameM,'')+isnull(dtrr.NameM,'')+ISNULL(der.NameM,'') reason,	"
				+ "CONVERT(varchar(20),t.出车时刻,120) outCarTime,t.备注 taskRemark,convert(varchar(20),t.到达现场时刻,120) arriveSpotTime,t.救治人数 takeHumanNumbers,	"
				+ "isnull(a.入院人数,0) toHospitalNumbers,convert(varchar(20),t.离开现场时刻,120) leaveSpotTime,"
				+ "ISNULL(a.死亡人数,0) deathNumbers,	ISNULL(a.留观人数,0) stayHospitalNumbers,"
				+ "'0' backHospitalNumbers,convert(varchar(20),t.完成时刻,120) completeTime,m.姓名 stationDispatcher,"
				+ "t.任务序号 outCarNumbers,et.录音文件名 record from AuSp120.tb_AcceptDescriptV a "
				+ "left outer  join AuSp120.tb_EventV e on a.事件编码=e.事件编码	"
				+ "left outer  join AuSp120.tb_EventTele et on et.事件编码=e.事件编码	"
				+ "left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
				+ "left outer join AuSp120.tb_DEventType det on det.Code=e.事件类型编码	"
				+ "left outer join AuSp120.tb_DEventNature den on den.Code=e.事件性质编码	"
				+ "left outer join AuSp120.tb_DILLState dis on dis.Code=a.病情编码	"
				+ "left outer join AuSp120.tb_DIdentity di on di.Code=a.身份编码	"
				+ "left outer join AuSp120.tb_MrUser m on m.工号=a.调度员编码 or m.工号=t.分站调度员编码	"
				+ "left outer join AuSp120.tb_DAcceptDescriptType dat on dat.Code=a.类型编码	"
				+ "left outer join AuSp120.tb_DHangReason dhr on dhr.Code=a.挂起原因编码	"
				+ "left outer join AuSp120.tb_DRepealReason drr on drr.Code=a.撤消原因编码	"
				+ "left outer join AuSp120.tb_Station s on s.分站编码=t.分站编码	"
				+ "left outer join AuSp120.tb_Ambulance am on am.车辆编码=t.车辆编码	"
				+ "left outer join AuSp120.tb_DTaskState dts on dts.Code=t.状态编码	"
				+ "left outer join AuSp120.tb_DTaskResult dtr on dtr.Code=t.结果编码	"
				+ "left outer join AuSp120.tb_DEmptyReason der on der.Code=t.放空车原因编码	"
				+ "left outer join AuSp120.tb_DRefuseReason dtrr on dtrr.Code=t.拒绝出车原因编码	"
				+ "left outer join AuSp120.tb_DStopReason dsr on dsr.Code=t.中止任务原因编码	where a.ID= :id ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", id);
		logger.info("id:" + id);
		AcceptSendCarDetail result = this.npJdbcTemplate.queryForObject(sql,
				paramMap, new RowMapper<AcceptSendCarDetail>() {

					@Override
					public AcceptSendCarDetail mapRow(ResultSet rs, int index)
							throws SQLException {
						AcceptSendCarDetail acceptSendCarDetail = new AcceptSendCarDetail(
								rs.getString("eventType"), rs
										.getString("eventNature"), rs
										.getString("callPhone"), rs
										.getString("callAddress"), rs
										.getString("patientNeed"), rs
										.getString("preJudgment"), rs
										.getString("sickCondition"), rs
										.getString("specialNeeds"), rs
										.getString("humanNumbers"), rs
										.getString("sickName"), rs
										.getString("gender"), rs
										.getString("age"), rs
										.getString("identitys"), rs
										.getString("contactMan"), rs
										.getString("contactPhone"), rs
										.getString("extension"), rs
										.getString("thisDispatcher"), rs
										.getString("remark"), rs
										.getString("isOrNoLitter"), rs
										.getString("acceptNumber"), rs
										.getString("acceptStartTime"), rs
										.getString("acceptType"), rs
										.getString("toBeSentReason"), rs
										.getString("endAcceptTime"), rs
										.getString("cancelReason"), rs
										.getString("sendCarTime"), rs
										.getString("sendStation"), rs
										.getString("carIndentiy"), rs
										.getString("states"), rs
										.getString("receiveOrderTime"), rs
										.getString("taskResult"), rs
										.getString("reason"), rs
										.getString("outCarTime"), rs
										.getString("taskRemark"), rs
										.getString("arriveSpotTime"), rs
										.getString("takeHumanNumbers"), rs
										.getString("toHospitalNumbers"), rs
										.getString("leaveSpotTime"), rs
										.getString("deathNumbers"), rs
										.getString("stayHospitalNumbers"), rs
										.getString("backHospitalNumbers"), rs
										.getString("completeTime"), rs
										.getString("stationDispatcher"), rs
										.getString("outCarNumbers"), rs
										.getString("record"));
						return acceptSendCarDetail;
					}
				});
		ServletContext sct = request.getServletContext();
		String recordIP = (String) sct.getAttribute("RecordServerIP");
		String year, day, month;
		if (result.getRecord() != null) {
			String recordName = result.getRecord().trim();
			int n = recordName.indexOf("_");
			if (n != -1) {
				String[] name = recordName.subSequence(0, n).toString()
						.split("-");
				year = name[0];
				month = name[1];
				day = name[2];
				String recordPath = recordIP + year + month + "/" + year
						+ month + day + "/" + recordName;
				logger.info("录音文件绝对路径为:" + recordPath);
				result.setRecord(recordPath);
			}
		}
		return result;
	}
}
