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

import com.xhs.ems.bean.RefuseAcceptReason;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.RefuseAcceptReasonDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午3:12:02
 */
@Repository
public class RefuseAcceptReasonDAOImpl implements RefuseAcceptReasonDAO {
	private static final Logger logger = Logger
			.getLogger(RefuseAcceptReasonDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select a.分诊调度医院 station,COUNT(*) totals into #temp1 "
				+ "	from AuSp120.tb_AcceptDescriptV a left outer join AuSp120.tb_EventV e on e.事件编码=a.事件编码	"
				+ "left outer join AuSp120.tb_DTriageRefuse dtr on dtr.Code=a.拒绝分诊调度原因编码"
				+ "	where e.事件性质编码=1 and e.受理时刻 between :startTime and :endTime	and a.类型编码 in (11,12)	group by a.分诊调度医院	order by a.分诊调度医院  "
				+ "select a.分诊调度医院 station,dtr.NameM reason,SUM(case when a.拒绝分诊调度原因编码 is not null then 1 else 0 end) times into #temp2 	"
				+ "from AuSp120.tb_AcceptDescriptV a left outer join AuSp120.tb_EventV e on e.事件编码=a.事件编码	"
				+ "left outer join AuSp120.tb_DTriageRefuse dtr on dtr.Code=a.拒绝分诊调度原因编码	"
				+ "where e.事件性质编码=1 and e.受理时刻 between :startTime and :endTime	and a.类型编码 in (11,12) and dtr.NameM is not null 	group by a.分诊调度医院,dtr.NameM	order by a.分诊调度医院,dtr.NameM  "
				+ "select t1.station,t1.totals,t2.reason,t2.times	"
				+ "from #temp1 t1 left outer join #temp2 t2 on t1.station=t2.station drop table #temp1,#temp2 ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<RefuseAcceptReason> results = this.npJdbcTemplate.query(sql,
				paramMap, new RowMapper<RefuseAcceptReason>() {
					@Override
					public RefuseAcceptReason mapRow(ResultSet rs, int index)
							throws SQLException {

						return new RefuseAcceptReason(rs.getString("station"),
								rs.getString("totals"), rs.getString("reason"),
								rs.getString("times"));
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
