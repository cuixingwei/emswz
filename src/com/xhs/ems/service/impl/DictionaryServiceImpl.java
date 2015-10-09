package com.xhs.ems.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.Dictionary;
import com.xhs.ems.dao.DictionaryDAO;
import com.xhs.ems.service.DictionaryService;

/**
 * @author 崔兴伟
 * @datetime 2015年10月9日 下午1:15:05
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {
	@Autowired
	private DictionaryDAO dictionaryDAO;
	@Override
	public List<Dictionary> getTaskResult() {
		return dictionaryDAO.getTaskResult();
	}
	@Override
	public List<Dictionary> getArea() {
		return dictionaryDAO.getArea();
	}

}
