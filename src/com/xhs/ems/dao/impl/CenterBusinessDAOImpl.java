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

import com.xhs.ems.bean.CenterBusiness;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.CenterBusinessDAO;

/**
 * @datetime 2015年11月12日 下午1:03:55
 * @author 崔兴伟
 */
@Repository
public class CenterBusinessDAOImpl implements CenterBusinessDAO {

	private static final Logger logger = Logger
			.getLogger(CenterBusinessDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public List<CenterBusiness> getCenterBusinesseData(Parameter parameter) {
		String sql = "select 'data' data,SUM(case when 联动来源编码=1 then 1 else 0 end) helpPhone,"
				+ "SUM(case when 联动来源编码=2 then 1 else 0 end) phoneOf110,	"
				+ "SUM(case when 联动来源编码=3 then 1 else 0 end) phoneOf119,"
				+ "SUM(case when 联动来源编码=4 then 1 else 0 end) phoneOfOther into #temp1	"
				+ "from AuSp120.tb_Event where 事件性质编码=1 and 受理时刻 between :startTime and :endTime "
				+ "select 'data' data,SUM(case when 记录类型编码 in (1,2,3,5,8) then 1 else 0 end) inPhone,	"
				+ "SUM(case when 记录类型编码=6 then 1 else 0 end) outPhone,COUNT(*) totalPhone into #temp2	"
				+ "from AuSp120.tb_TeleRecord where 产生时刻 between :startTime and :endTime	"
				+ "select 'data' data,SUM(case when e.事件类型编码=1 then 1 else 0 end) spotFirstAid,	"
				+ "SUM(case when e.事件类型编码=2 then 1 else 0 end) stationTransfer,	"
				+ "SUM(case when e.事件类型编码=3 then 1 else 0 end) inHospitalTransfer,	"
				+ "SUM(case when e.事件类型编码=5 then 1 else 0 end) safeguard,	"
				+ "SUM(case when e.事件类型编码 not in (1,2,3,5) then 1 else 0 end) noFirstAid,	"
				+ "SUM(case when t.结果编码=2 then 1 else 0 end) stopStaskNumbers,	"
				+ "SUM(case when a.类型编码 in (2,4) then 1 else 0 end) hungNumbers,	"
				+ "SUM(case when a.类型编码 in (11,12,13) then 1 else 0 end) referralSendCar,	"
				+ "SUM(case when a.类型编码 in (2,4,11,12,13) then 1 else 0 end) centerSendCar into #temp3	"
				+ "from AuSp120.tb_EventV e left outer join AuSp120.tb_AcceptDescript a on e.事件编码=a.事件编码	"
				+ "left outer join AuSp120.tb_TaskV t on a.受理序号=t.受理序号 and a.事件编码=t.事件编码	"
				+ "where e.事件性质编码=1 and 受理时刻 between :startTime and :endTime	select 'data' data,"
				+ "AVG(DATEDIFF(second,a.开始受理时刻,a.结束受理时刻)) firstaidAcceptTime into #temp4	"
				+ "from AuSp120.tb_Event e left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=e.事件编码	"
				+ "where e.事件性质编码=1 and e.事件类型编码=1 and 受理时刻 between :startTime and :endTime	"
				+ "select 'data' data,AVG(DATEDIFF(second,a.开始受理时刻,a.结束受理时刻))  referralAcceptTime into #temp5	"
				+ "from AuSp120.tb_Event e left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=e.事件编码	"
				+ "where e.事件性质编码=1 and e.事件类型编码=2 and 受理时刻 between :startTime and :endTime	"
				+ "select isnull(t1.helpPhone,0) helpPhone,isnull(t1.phoneOf110,0) phoneOf110,isnull(t1.phoneOf119,0) phoneOf119,"
				+ "isnull(t1.phoneOfOther,0) phoneOfOther,isnull(t2.inPhone,0) inPhone,isnull(t2.outPhone,0) outPhone,"
				+ "isnull(t2.totalPhone,0) totalPhone,isnull(t3.centerSendCar,0) centerSendCar,isnull(t3.hungNumbers,0) hungNumbers,"
				+ "isnull(t3.inHospitalTransfer,0) inHospitalTransfer,isnull(t3.noFirstAid,0) noFirstAid,"
				+ "isnull(t3.referralSendCar,0) referralSendCar,isnull(t3.safeguard,0) safeguard,"
				+ "isnull(t3.spotFirstAid,0) spotFirstAid,isnull(t3.stationTransfer,0) stationTransfer,"
				+ "isnull(t3.stopStaskNumbers,0) stopStaskNumbers,isnull(t4.firstaidAcceptTime,0) firstaidAcceptTime,"
				+ "isnull(t5.referralAcceptTime,0) referralAcceptTime	"
				+ "from #temp1 t1 left outer join #temp2 t2 on t1.data=t2.data	left outer join #temp3 t3 on t1.data=t3.data	"
				+ "left outer join #temp4 t4 on t1.data=t4.data	left outer join #temp5 t5 on t1.data=t5.data "
				+ "drop table #temp1,#temp2,#temp3,#temp4,#temp5";
		Map<String, String> map = new HashMap<String, String>();
		map.put("startTime", parameter.getStartTime());
		map.put("endTime", parameter.getEndTime());
		final List<CenterBusiness> results = this.npJdbcTemplate.query(sql,
				map, new RowMapper<CenterBusiness>() {
					@Override
					public CenterBusiness mapRow(ResultSet rs, int index)
							throws SQLException {
						CenterBusiness centerBusiness = new CenterBusiness();
						centerBusiness.setCenterSendCar(rs
								.getString("centerSendCar"));
						centerBusiness.setFirstaidAcceptTime(rs
								.getString("firstaidAcceptTime"));
						centerBusiness.setHelpPhone(rs.getString("helpPhone"));
						centerBusiness.setHungNumbers(rs
								.getString("hungNumbers"));
						centerBusiness.setInHospitalTransfer(rs
								.getString("inHospitalTransfer"));
						centerBusiness.setInPhone(rs.getString("inPhone"));
						centerBusiness.setNoFirstAid(rs.getString("noFirstAid"));
						centerBusiness.setOutPhone(rs.getString("outPhone"));
						centerBusiness.setPhoneOf110(rs.getString("phoneOf110"));
						centerBusiness.setPhoneOf119(rs.getString("phoneOf119"));
						centerBusiness.setPhoneOfOther(rs
								.getString("phoneOfOther"));
						centerBusiness.setReferralAcceptTime(rs
								.getString("referralAcceptTime"));
						centerBusiness.setReferralSendCar(rs
								.getString("referralSendCar"));
						centerBusiness.setSafeguard(rs.getString("safeguard"));
						centerBusiness.setSpotFirstAid(rs
								.getString("spotFirstAid"));
						centerBusiness.setStationTransfer(rs
								.getString("stationTransfer"));
						centerBusiness.setStopStaskNumbers(rs
								.getString("stopStaskNumbers"));
						centerBusiness.setTotalPhone(rs.getString("totalPhone"));
						return centerBusiness;
					}
				});
		for (CenterBusiness reBusiness : results) {
			reBusiness.setFirstaidAcceptTime(CommonUtil.formatSecond(reBusiness
					.getFirstaidAcceptTime()));
			reBusiness.setReferralAcceptTime(CommonUtil.formatSecond(reBusiness
					.getReferralAcceptTime()));
		}
		logger.info("一共" + results.size() + "条记录");
		return results;
	}

}
