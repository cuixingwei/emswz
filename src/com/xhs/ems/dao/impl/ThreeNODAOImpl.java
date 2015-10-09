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

import com.xhs.ems.bean.ThreeNO;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.dao.ThreeNODAO;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午5:20:22
 */
@Repository
public class ThreeNODAOImpl implements ThreeNODAO {

	private static final Logger logger = Logger.getLogger(ThreeNODAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select cr.任务编码,cr.任务序号,cr.病历序号,SUM(cr.收费金额) cost into #temp1 from AuSp120.tb_ChargeRecord cr 	"
				+ "group by cr.任务编码,cr.任务序号,cr.病历序号  "
				+ " select pc.任务编码,pc.任务序号,pc.序号,pc.姓名 name,pc.性别 gender,pc.年龄 age,pc.医生诊断 diagnose,pc.现场地点 address,	"
				+ "pc.送往地点 toAddress,pc.里程 distance into #temp2	from AuSp120.tb_PatientCase pc	"
				+ "left outer join AuSp120.tb_TaskV t on t.任务编码=pc.任务编码 and t.任务序号=pc.任务序号 	left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
				+ "where e.事件性质编码=1 and e.受理时刻 between :startTime and :endTime  "
				+ "select  t1.cost,t2.age,t2.address,t2.diagnose,t2.distance,t2.gender,t2.name,t2.toAddress 	"
				+ "from #temp2 t2 left outer join #temp1 t1 on t1.任务序号=t2.任务序号 and t1.任务编码=t2.任务编码 and t1.病历序号=t2.序号  "
				+ "drop table #temp1,#temp2 ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<ThreeNO> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<ThreeNO>() {
					@Override
					public ThreeNO mapRow(ResultSet rs, int index)
							throws SQLException {

						return new ThreeNO(rs.getString("name"), rs
								.getString("gender"), rs.getString("age"), rs
								.getString("diagnose"),
								rs.getString("address"), rs.getString("cost"),
								rs.getString("toAddress"), rs
										.getString("distance"));
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
