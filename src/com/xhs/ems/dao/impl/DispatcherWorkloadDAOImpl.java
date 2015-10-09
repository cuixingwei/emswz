package com.xhs.ems.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xhs.ems.bean.DispatcherWorkload;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.DispatcherWorkloadDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年8月18日 上午9:54:54
 */
@Repository
public class DispatcherWorkloadDAOImpl implements DispatcherWorkloadDAO {
	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select m.姓名,COUNT(*) 电话总数,sum(case when tr.记录类型编码 in(1,2,3,5,8) then 1 else 0 end) 呼入,"
				+ "sum(case when tr.记录类型编码=6 then 1 else 0 end) 呼出 into #temp1	from AuSp120.tb_TeleRecord tr "
				+ "left outer join AuSp120.tb_DTeleRecordType drt on drt.Code=tr.记录类型编码	"
				+ "left outer join AuSp120.tb_MrUser m on m.工号=tr.调度员编码	where not (tr.调度员编码='' or tr.调度员编码 is null )"
				+ " and  m.人员类型=0 and tr.记录类型编码 in(1,2,3,5,6,8) and tr.产生时刻 between :startTime and :endTime 	group by m.姓名    "
				+ "select m.姓名,a.类型编码,a.救治人数,a.调度员编码,t.结果编码,a.开始受理时刻,t.事件编码,a.派车时刻,t.任务序号,t.任务编码 into #temp2 "
				+ "from AuSp120.tb_AcceptDescriptV a 	left outer join AuSp120.tb_TaskV t on a.受理序号=t.受理序号 and a.事件编码=t.事件编码 "
				+ "left outer join AuSp120.tb_EventV e on e.事件编码=a.事件编码	left outer join AuSp120.tb_MrUser m on m.工号=a.调度员编码	"
				+ "where e.事件性质编码=1 and m.人员类型=0 and a.开始受理时刻 between :startTime and :endTime  "
				+ "select t2.姓名,SUM(t2.救治人数) 分诊数 into #temp5 from #temp2 t2 where t2.类型编码 in (11,12) group by t2.姓名    select t2.姓名,"
				+ "SUM(case when t2.开始受理时刻 is not null and t2.派车时刻 is not null then 1 else 0 end) 有效派车,	SUM(case when t2.结果编码=4 then 1 else 0 end)正常完成,	SUM(case when t2.结果编码=3 then 1 else 0 end) 空车,	"
				+ "SUM(case when t2.结果编码=2 then 1 else 0 end) 中止任务,SUM(case when t2.结果编码=5 then 1 else 0 end) 拒绝出车 into #temp3  	"
				+ "from #temp2 t2 group by t2.姓名   select t2.姓名,SUM(case when pc.转归编码=1 then 1 else 0 end) 救治人数 into #temp4 from  AuSp120.tb_PatientCase pc "
				+ "	left outer join #temp2 t2  on pc.任务序号=t2.任务序号 and t2.任务编码=pc.任务编码 group by t2.姓名 "
				+ " select t1.姓名 dispatcher,t1.电话总数 numbersOfPhone,t1.呼入 inOfPhone,t1.呼出 outOfPhone,isnull(t3.有效派车,0) numbersOfSendCar,	"
				+ "isnull(t3.正常完成,0) numbersOfNormalSendCar,isnull(t3.空车,0) emptyCar,isnull(t3.中止任务,0) numbersOfStopTask,	"
				+ "isnull(t3.拒绝出车,0) refuseCar,isnull(t4.救治人数,0) takeBacks,isnull(t5.分诊数,0) triageNumber from #temp1 t1  	"
				+ "left outer join #temp3 t3 on t1.姓名=t3.姓名 left outer join #temp4 t4 on t1.姓名=t4.姓名 left outer join #temp5 t5 on t5.姓名=t1.姓名   "
				+ "drop table #temp1,#temp2,#temp3,#temp4,#temp5";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<DispatcherWorkload> results = this.npJdbcTemplate.query(sql,
				paramMap, new RowMapper<DispatcherWorkload>() {
					@Override
					public DispatcherWorkload mapRow(ResultSet rs, int index)
							throws SQLException {
						DispatcherWorkload dispatcherWorkload = new DispatcherWorkload();
						dispatcherWorkload.setDispatcher(rs
								.getString("dispatcher"));
						dispatcherWorkload.setEmptyCar(rs.getString("emptyCar"));
						dispatcherWorkload.setInOfPhone(rs
								.getString("inOfPhone"));
						dispatcherWorkload.setNumbersOfNormalSendCar(rs
								.getString("numbersOfNormalSendCar"));
						dispatcherWorkload.setNumbersOfPhone(rs
								.getString("numbersOfPhone"));
						dispatcherWorkload.setNumbersOfSendCar(rs
								.getString("numbersOfSendCar"));
						dispatcherWorkload.setNumbersOfStopTask(rs
								.getString("numbersOfStopTask"));
						dispatcherWorkload.setOutOfPhone(rs
								.getString("outOfPhone"));
						dispatcherWorkload.setRefuseCar(rs
								.getString("refuseCar"));
						dispatcherWorkload.setTakeBacks(rs
								.getString("takeBacks"));
						dispatcherWorkload.setTriageNumber(rs
								.getString("triageNumber"));
						return dispatcherWorkload;
					}
				});
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
