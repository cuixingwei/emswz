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

import com.xhs.ems.bean.MedicalTreatment;
import com.xhs.ems.bean.easyui.Grid;
import com.xhs.ems.bean.easyui.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.MedicalTreatmentDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年5月22日 下午1:57:26
 */
@Repository
public class MedicalTreatmentDAOImpl implements MedicalTreatmentDAO {

	private static final Logger logger = Logger
			.getLogger(MedicalTreatmentDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select dm.NameM name,COUNT(*) times,'' rate 	from AuSp120.tb_PatientCase pc	"
				+ "left outer join AuSp120.tb_CureMeasure cm on cm.任务序号=pc.任务序号 and cm.任务编码=pc.任务编码 and cm.病例序号=pc.序号	"
				+ "left outer join AuSp120.tb_DMeasure dm on dm.Code=cm.救治措施编码	"
				+ "where pc.记录时刻 between :startTime and :endTime and dm.NameM is not null	group by dm.NameM ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<MedicalTreatment> results = this.npJdbcTemplate.query(sql,
				paramMap, new RowMapper<MedicalTreatment>() {
					@Override
					public MedicalTreatment mapRow(ResultSet rs, int index)
							throws SQLException {

						return new MedicalTreatment(rs.getString("name"), rs
								.getString("times"), rs.getString("rate"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		int total = 0;
		for (MedicalTreatment result : results) {
			total += Integer.parseInt(result.getTimes());
		}
		for (MedicalTreatment result : results) {
			result.setRate(CommonUtil.calculateRate(total, result.getTimes()));
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

}
