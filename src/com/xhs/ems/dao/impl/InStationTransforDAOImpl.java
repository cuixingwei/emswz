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

import com.xhs.ems.bean.InStationTransfor;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.InStationTransforDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午1:38:10
 */
@Repository
public class InStationTransforDAOImpl implements InStationTransforDAO {

	private static final Logger logger = Logger
			.getLogger(InStationTransforDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select distinct pc.任务编码,pc.任务序号,pc.里程,pc.出诊地址 into #pc from AuSp120.tb_PatientCase pc select pc.出诊地址 station,COUNT(*) outCalls,isnull(SUM(pc.里程),0) distance,"
				+ "isnull(sum(DATEDIFF(Second,t.出车时刻,t.到达医院时刻)),0) time	from AuSp120.tb_EventV e "
				+ "left outer join AuSp120.tb_TaskV t on t.事件编码=e.事件编码	"
				+ "left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and a.受理序号=t.受理序号 	"
				+ "left outer join AuSp120.tb_Station s on t.分站编码=s.分站编码	left outer join #pc pc on t.任务序号=pc.任务序号 and pc.任务编码=t.任务编码 	"
				+ "where e.事件性质编码=1 and pc.出诊地址 is not null and pc.出诊地址<>'' and e.事件类型编码=3 and a.类型编码 not in (2,4) and e.受理时刻 between :startTime and :endTime	group by pc.出诊地址 drop table #pc ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<InStationTransfor> results = this.npJdbcTemplate.query(sql,
				paramMap, new RowMapper<InStationTransfor>() {
					@Override
					public InStationTransfor mapRow(ResultSet rs, int index)
							throws SQLException {

						return new InStationTransfor(rs.getString("station"),
								rs.getString("outCalls"), rs
										.getString("distance"), rs
										.getString("time"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		Grid grid = new Grid();
		for (InStationTransfor result : results) {
			result.setTransforTime(CommonUtil.formatSecond(result
					.getTransforTime()));
		}
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
